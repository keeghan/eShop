package com.keeghan.eShop.domain.mappers;

import com.keeghan.eShop.domain.dtos.ProductDTO;
import com.keeghan.eShop.domain.entities.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements Mapper<Product, ProductDTO> {
    final ModelMapper modelMapper;

    @Autowired
    public ProductMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public ProductDTO mapToDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Product mapToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
