package com.codewithmosh.store.products;

import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.products.categories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping()
    List<ProductDto> getAllProducts(@RequestParam(required = false) Byte categoryId) {

        if (categoryId == null) return productRepository.findAllWithCategory()
                .stream()
                .map(productMapper::toProductDto)
                .toList();


        return productRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productMapper.toProductDto(product));
    }


    @PostMapping
    ResponseEntity<ProductDto> createProduct(
            @RequestBody CreateProductRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if (category == null) return ResponseEntity.badRequest().build();

        var product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(productMapper.toProductDto(product));
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request
    ) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        if (request.getCategoryId() != null && !product.getCategory().getId().equals(request.getCategoryId())) {
            var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
            if (category == null) return ResponseEntity.badRequest().build();
            product.setCategory(category);
        }
        productMapper.update(request, product);

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toProductDto(product));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
