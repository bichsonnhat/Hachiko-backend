package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.StoreDTO;
import com.mongodb.starter.entity.StoreEntity;
import com.mongodb.starter.usecases.interfaces.StoreUsecase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stores")
public class StoreController {
    private final static Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
    private final StoreUsecase storeUsecase;

    public StoreController(StoreUsecase storeUsecase) {
        this.storeUsecase = storeUsecase;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoreEntity> getStores() {
        return storeUsecase.getStores();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StoreEntity getStore(@PathVariable String id) {
        return storeUsecase.getStore(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreEntity createStore(@RequestBody StoreDTO storeDTO) {
        return storeUsecase.createStore(storeDTO.toStoreEntity());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StoreEntity updateStore(@RequestBody StoreDTO storeDTO) {
        return storeUsecase.updateStore(storeDTO.toStoreEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable String id) {
        storeUsecase.deleteStore(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
