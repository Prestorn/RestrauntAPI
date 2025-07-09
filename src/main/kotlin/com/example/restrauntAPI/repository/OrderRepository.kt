package com.example.restrauntAPI.repository

import com.example.restrauntAPI.model.Order
import org.aspectj.weaver.ast.Or
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Int> {

    @Query(value = "select * from orders where client_id = :id", nativeQuery = true)
    fun findByClientId(id: Int) : List<Order>

    @Query(value = "select orders.id, client_id, cost from " +
            "orders inner join dishes_in_orders on orders.id = dishes_in_orders.order_id " +
            "where dish_id = :id", nativeQuery = true)
    fun findAllOrdersWithDish(id: Int) : List<Order>
}