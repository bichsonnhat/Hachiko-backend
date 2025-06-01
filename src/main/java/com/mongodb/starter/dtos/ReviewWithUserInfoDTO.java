package com.mongodb.starter.dtos;

import java.util.Date;

public record ReviewWithUserInfoDTO(
    String id,
    String userId,
    String productId,
    String userName,
    String userAvatar,
    int rating,
    String comment,
    Date createdAt,
    Date updatedAt
) {
}