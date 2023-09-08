package com.example.prioritask.repository

import com.example.prioritask.model.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findCategoryById(id: Long?) : Category
}
