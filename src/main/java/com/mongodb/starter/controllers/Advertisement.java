package com.mongodb.starter.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.AdvertisementDTO;
import com.mongodb.starter.entity.AdvertisementEntity;
import com.mongodb.starter.usecases.interfaces.AdvertisementUsecase;

@RestController
@RequestMapping("/api")
public class Advertisement {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Advertisement.class);
    private final AdvertisementUsecase advertisementUsecase;

    public Advertisement(AdvertisementUsecase advertisementUsecase) {
        this.advertisementUsecase = advertisementUsecase;
    }

    @PostMapping("advertisements")
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementEntity createAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementUsecase.createAdvertisement(advertisementDTO.toAdvertisementEntity());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
