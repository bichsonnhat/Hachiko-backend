package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.AdvertisementDTO;
import com.mongodb.starter.entity.AdvertisementEntity;
import com.mongodb.starter.usecases.interfaces.AdvertisementUsecase;

@RestController
public class Advertisement {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Advertisement.class);
    private final AdvertisementUsecase advertisementUsecase;

    public Advertisement(AdvertisementUsecase advertisementUsecase) {
        this.advertisementUsecase = advertisementUsecase;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementEntity createAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementUsecase.createAdvertisement(advertisementDTO.toAdvertisementEntity());
    }

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertisementEntity> getCategories() {
        return advertisementUsecase.getAdvertisements();
    }

    @GetMapping("get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementEntity getCategory(@PathVariable String id) {
        return advertisementUsecase.getAdvertisement(id);
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementEntity updateCategory(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementUsecase.updateAdvertisement(advertisementDTO.toAdvertisementEntity());
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String id) {
        advertisementUsecase.deleteAdvertisement(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
