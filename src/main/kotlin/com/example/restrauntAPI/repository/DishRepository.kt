package com.example.restrauntAPI.repository

import com.example.restrauntAPI.model.Dish
import org.springframework.data.jpa.repository.JpaRepository

interface DishRepository : JpaRepository<Dish, Int> {

}