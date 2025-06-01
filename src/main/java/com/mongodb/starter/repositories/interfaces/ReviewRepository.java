package com.mongodb.starter.repositories.interfaces;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.mongodb.starter.entity.ReviewEntity;
import com.mongodb.starter.dtos.ReviewWithUserInfoDTO;

@Repository
public interface ReviewRepository {

    ReviewEntity insertOne(ReviewEntity reviewEntity);

    void deleteOne(String id);

    ReviewEntity findOne(String id);

    ReviewEntity updateOne(ReviewEntity entity);

    List<ReviewEntity> findByProductId(String productId);

    ReviewEntity findByUserIdAndProductId(String userId, String productId);

    List<ReviewWithUserInfoDTO> findByProductIdWithUserInfo(String productId);

    double calculateAverageRating(String productId);

    int countByProductId(String productId);

    void deleteByUserIdAndProductId(String userId, String productId);
}