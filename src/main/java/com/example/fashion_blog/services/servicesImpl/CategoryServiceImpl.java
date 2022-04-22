package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.CategoryDto;
import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.repositories.CategoryRepository;
import com.example.fashion_blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Category createCategory(CategoryDto categoryDto) {
        return categoryRepository.save(mapToEntity(categoryDto));
    }

    private Category mapToEntity(CategoryDto categoryDto) {

        return modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDto mapToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
