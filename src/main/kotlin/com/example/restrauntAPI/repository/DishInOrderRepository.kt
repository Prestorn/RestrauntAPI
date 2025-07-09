package com.example.restrauntAPI.repository

import com.example.restrauntAPI.model.DishInOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DishInOrderRepository: JpaRepository<DishInOrder, Int> {
    @Query(value = "select * from dishes_in_orders where order_id = :id", nativeQuery = true)
    fun findAllByOrderId(id: Int) : List<DishInOrder>

}