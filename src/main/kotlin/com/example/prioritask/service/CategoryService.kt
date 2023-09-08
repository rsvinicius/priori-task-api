package com.example.prioritask.service

import com.example.prioritask.model.entity.Category
import com.example.prioritask.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun getCategoryById(categoryId: Long?): Category? = try {
        categoryRepository.findCategoryById(categoryId)
    } catch (ex: IllegalArgumentException) {
        null
    }
}
