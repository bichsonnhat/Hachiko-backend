package com.mongodb.starter.repositories.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.starter.dtos.FilteredProductResponse;
import com.mongodb.starter.entity.ProductEntity;
import com.mongodb.starter.repositories.interfaces.ProductRepository;

import static com.mongodb.client.model.ReturnDocument.AFTER;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBProductRepository implements ProductRepository {
    private final MongoClient client;
    private MongoCollection<ProductEntity> productCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "products";
    private final String CATEGORY_COLLECTION_NAME = "categories";

    public MongoDBProductRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        productCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, ProductEntity.class);
    }

    @Override
    public ProductEntity insertOne(ProductEntity productEntity) {
        productCollection.insertOne(productEntity);
        return productEntity;
    }

    @Override
    public List<ProductEntity> findAll() {
        return productCollection.find().into(new ArrayList<>());
    }

    @Override
    public ProductEntity findOne(String id) {
        return productCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public ProductEntity updateOne(ProductEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return productCollection.findOneAndReplace(
                eq("_id", entity.getId()),
                entity,
                options);
    }

    @Override
    public void deleteOne(String id) {
        productCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    @Override
    public List<ProductEntity> findByCategory(String id) {
        return productCollection.find(eq("categoryID", new ObjectId(id))).into(new ArrayList<>());
    }

    @Override
    public List<Document> findAllGroupedByCategoryAggregation() {
        return productCollection.aggregate(Arrays.asList(
                new Document("$lookup", 
                        new Document("from", CATEGORY_COLLECTION_NAME)
                                .append("localField", "categoryID")
                                .append("foreignField", "_id")
                                .append("as", "category")),
                new Document("$unwind", 
                        new Document("path", "$category")
                                .append("preserveNullAndEmptyArrays", true)),
                Aggregates.group("$categoryID",
                        Accumulators.push("products", "$$ROOT"),
                        Accumulators.first("categoryName", "$category.name")),
                new Document("$addFields",
                        new Document("products",
                                new Document("$map",
                                        new Document("input", "$products")
                                                .append("as", "product")
                                                .append("in", new Document()
                                                        .append("id", new Document("$toString", "$$product._id"))
                                                        .append("categoryID",
                                                                new Document("$toString", "$$product.categoryID"))
                                                        .append("categoryName", "$categoryName")
                                                        .append("description", "$$product.description")
                                                        .append("imageUrl", "$$product.imageUrl")
                                                        .append("price", "$$product.price")
                                                        .append("title", "$$product.title"))))),
                new Document("$project",
                        new Document("_id", 0)
                                .append("categoryID", new Document("$toString", "$_id"))
                                .append("categoryName", 1)
                                .append("products", 1)),
                Aggregates.sort(Sorts.ascending("categoryID"))), Document.class).into(new ArrayList<>());
    }

    @Override
    public List<ProductEntity> findAllByProductIds(List<String> productIds) {
        return productCollection.find(in("_id", productIds.stream()
                .filter(id -> ObjectId.isValid(id))
                .map(id -> new ObjectId(id))
                .toList())).into(new ArrayList<>());
    }

    @Override
    public FilteredProductResponse<ProductEntity> filterProduct(String search, Integer page) {
        int pageSize = 10;
        int skip = (page - 1) * pageSize;

        Document regexQuery = new Document("$regex", ".*" + search + ".*").append("$options", "i");

        long totalCount = productCollection.countDocuments(new Document("title", regexQuery));
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<ProductEntity> products = productCollection.find(new Document("title", regexQuery))
                .skip(skip)
                .limit(pageSize)
                .into(new ArrayList<>());

        return new FilteredProductResponse<>(products, totalPages);
    }
}