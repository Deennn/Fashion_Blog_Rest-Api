package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.CategoryDto;
import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.repositories.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void givenCategoryObject_whenSaveObject_thenReturnCategoryObject() {

        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("Electronics")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();


        given(categoryRepository.save(any())).willReturn(category);


        Category category1 = categoryService.createCategory(categoryDto);

        Assertions.assertThat(category1).isNotNull();
        verify(modelMapper, atLeastOnce()).map(any(), any());
        verify(categoryRepository, times(1)).save(any());


    }
}
