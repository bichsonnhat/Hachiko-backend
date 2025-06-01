package com.mongodb.starter.repositories.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.starter.entity.ReviewEntity;
import com.mongodb.starter.repositories.interfaces.ReviewRepository;
import com.mongodb.starter.dtos.ReviewWithUserInfoDTO;

import static com.mongodb.client.model.ReturnDocument.AFTER;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBReviewRepository implements ReviewRepository {
    private final MongoClient client;
    private MongoCollection<ReviewEntity> reviewCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "reviews";
    private final String USER_COLLECTION_NAME = "users";

    public MongoDBReviewRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        reviewCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, ReviewEntity.class);
    }

    @Override
    public ReviewEntity insertOne(ReviewEntity reviewEntity) {
        reviewCollection.insertOne(reviewEntity);
        return reviewEntity;
    }

    @Override
    public ReviewEntity findOne(String id) {
        return reviewCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public ReviewEntity updateOne(ReviewEntity entity) {
        entity.setUpdatedAt(new Date());
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return reviewCollection.findOneAndReplace(
                eq("_id", entity.getId()),
                entity,
                options);
    }

    @Override
    public void deleteOne(String id) {
        reviewCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    @Override
    public List<ReviewEntity> findByProductId(String productId) {
        return reviewCollection.find(eq("productId", new ObjectId(productId))).into(new ArrayList<>());
    }

    @Override
    public ReviewEntity findByUserIdAndProductId(String userId, String productId) {
        return reviewCollection.find(and(
                eq("userId", userId),
                eq("productId", new ObjectId(productId))
        )).first();
    }

    @Override
    public List<ReviewWithUserInfoDTO> findByProductIdWithUserInfo(String productId) {
        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("productId", new ObjectId(productId))),
                new Document("$lookup", 
                        new Document("from", USER_COLLECTION_NAME)
                                .append("localField", "userId")
                                .append("foreignField", "_id")
                                .append("as", "user")),
                new Document("$unwind", 
                        new Document("path", "$user")
                                .append("preserveNullAndEmptyArrays", true)),
                new Document("$project", new Document()
                        .append("id", new Document("$toString", "$_id"))
                        .append("userId", "$userId")
                        .append("productId", new Document("$toString", "$productId"))
                        .append("userName", new Document("$concat", Arrays.asList("$user.firstName", " ", "$user.lastName")))
                        .append("userAvatar", new Document("$ifNull", Arrays.asList("$user.avatar", "")))
                        .append("rating", "$rating")
                        .append("comment", "$comment")
                        .append("createdAt", "$createdAt")
                        .append("updatedAt", "$updatedAt")),
                new Document("$sort", new Document("createdAt", -1))
        );

        List<Document> results = reviewCollection.aggregate(pipeline, Document.class).into(new ArrayList<>());
        
        return results.stream().map(doc -> new ReviewWithUserInfoDTO(
                doc.getString("id"),
                doc.getString("userId"),
                doc.getString("productId"),
                doc.getString("userName"),
                doc.getString("userAvatar"),
                doc.getInteger("rating"),
                doc.getString("comment"),
                doc.getDate("createdAt"),
                doc.getDate("updatedAt")
        )).toList();
    }

    @Override
    public double calculateAverageRating(String productId) {
        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("productId", new ObjectId(productId))),
                new Document("$group", new Document("_id", null)
                        .append("averageRating", new Document("$avg", "$rating")))
        );

        Document result = reviewCollection.aggregate(pipeline, Document.class).first();
        if (result != null && result.containsKey("averageRating")) {
            Double avg = result.getDouble("averageRating");
            return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
        }
        return 0.0;
    }

    @Override
    public int countByProductId(String productId) {
        return (int) reviewCollection.countDocuments(eq("productId", new ObjectId(productId)));
    }

    @Override
    public void deleteByUserIdAndProductId(String userId, String productId) {
        reviewCollection.deleteOne(and(
                eq("userId", userId),
                eq("productId", new ObjectId(productId))
        ));
    }
}