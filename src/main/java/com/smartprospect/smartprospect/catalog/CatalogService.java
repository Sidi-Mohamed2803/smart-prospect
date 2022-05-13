package com.smartprospect.smartprospect.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public void addNew(Catalog catalog) {
        if (catalog==null) {
            throw new IllegalStateException("Catalog is null...");
        }
        catalogRepository.save(catalog);
    }
}
