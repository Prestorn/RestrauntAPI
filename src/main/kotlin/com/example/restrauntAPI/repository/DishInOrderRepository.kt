package com.example.restrauntAPI.repository

import com.example.restrauntAPI.model.DishInOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface DishInOrderRepository: JpaRepository<DishInOrder, Int> {
    @Query(value = "select * from dishes_in_orders where order_id = :id", nativeQuery = true)
    fun findAllByOrderId(id: Int) : List<DishInOrder>

    @Query(value = "select * from dishes_in_orders where order_id = :orderId and dish_id = :dishId", nativeQuery = true)
    fun findDataByOrderIdAndDishId(orderId: Int, dishId: Int) : Optional<DishInOrder>

    @Modifying
    @Transactional
    @Query(value = "delete from dishes_in_orders where dish_id = :id", nativeQuery = true)
    fun deleteDishInAllOrders(id: Int)

    @Modifying
    @Transactional
    @Query(value = "delete from dishes_in_orders where order_id = :id", nativeQuery = true)
    fun deleteAllByOrderId(id: Int)
}