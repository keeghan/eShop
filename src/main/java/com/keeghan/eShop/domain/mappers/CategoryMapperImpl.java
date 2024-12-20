package com.keeghan.eShop.domain.mappers;

import com.keeghan.eShop.domain.entities.Category;
import com.keeghan.eShop.domain.entities.dtos.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<Category, CategoryDTO> {
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }


    @Override
    public CategoryDTO mapToDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public Category mapToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
