package com.mongodb.starter.repositories.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.entity.FavouriteProductEntity;
import com.mongodb.starter.repositories.interfaces.FavouriteProductRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBFavouriteProductRepository implements FavouriteProductRepository {
    private final MongoClient client;
    private MongoCollection<FavouriteProductEntity> favouriteProductCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "favouriteProducts";

    public MongoDBFavouriteProductRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        favouriteProductCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, FavouriteProductEntity.class);
    }

    @Override
    public FavouriteProductEntity addNewProductIntoFavouriteProductList(FavouriteProductEntity favouriteProductEntity) {
        favouriteProductCollection.insertOne(favouriteProductEntity);
        return favouriteProductEntity;
    }

    @Override
    public List<FavouriteProductEntity> getFavouriteProductsByUserId(String userId) {
        return favouriteProductCollection.find(eq("userId", new ObjectId(
                userId))).into(new java.util.ArrayList<>());
    }

    @Override
    public void removeProductFromFavouriteProductList(String id) {
        favouriteProductCollection.deleteOne(new org.bson.Document("_id", new org.bson.types.ObjectId(id)));
    }

    @Override
    public boolean checkIfProductIsInFavouriteProductList(String userId, String productId) {
        return favouriteProductCollection.find(eq("userId", new ObjectId(userId)))
                .filter(eq("productId", new ObjectId(productId))).first() != null;
    }

}
