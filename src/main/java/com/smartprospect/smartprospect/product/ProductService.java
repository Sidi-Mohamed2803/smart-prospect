package com.smartprospect.smartprospect.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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

    public Product getByID(String userID, String ref) {
        return productRepository.findByUserAndReference(userID, ref);
    }

    public void remove(ProductId id) {
        productRepository.delete(productRepository.findByUserAndReference(id.getUser(), id.getReference()));
    }

    @Transactional
    public void edit(ProductId id, double price, String description, String name, MultipartFile pic, Boolean selected) {
        Product product = productRepository.findByUserAndReference(id.getUser(), id.getReference());
        product.setPrice(price);
        product.setDescription(description);
        product.setName(name);
        product.setSelected(selected);
        if (!pic.isEmpty() && pic != null) {
            try {
                product.setImage(Base64.getEncoder().encodeToString(pic.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
