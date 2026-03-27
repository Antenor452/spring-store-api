package com.codewithmosh.store.mappers;

import com.codewithmosh.store.carts.CartProductDto;
import com.codewithmosh.store.products.CreateProductRequest;
import com.codewithmosh.store.products.ProductDto;
import com.codewithmosh.store.products.UpdateProductRequest;
import com.codewithmosh.store.products.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toProductDto(Product product);

    CartProductDto toCartProductDto(Product product);


    Product toEntity(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UpdateProductRequest request, @MappingTarget Product product);
}
