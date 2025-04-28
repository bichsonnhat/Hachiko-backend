package com.mongodb.starter.dtos;

import java.util.List;

public class FilteredProductResponse<T> {
    private List<T> items;
    private int totalPages;

    public FilteredProductResponse(List<T> items, int totalPages) {
        this.items = items;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public int getTotalPages() {
        return totalPages;
    }
}