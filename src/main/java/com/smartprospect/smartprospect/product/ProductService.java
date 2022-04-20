package com.smartprospect.smartprospect.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service @RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Collection<Product> retrieveAll() {
        return productRepository.findAll();
    }

    public void addNew(Product product) {
        productRepository.save(product);
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }

    @Transactional
    public void edit(Long id, double price, String description) {
        Product product = productRepository.getById(id);
        if (price > 0) {
            product.setPrice(price);
        }
        if (description != null && !description.equals("")) {
            product.setDescription(description);
        }
    }
}
