package com.example.restrauntAPI.model

import jakarta.persistence.*

@Entity
@Table(name = "dishes_in_orders")
class DishInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(name = "order_id")
    var orderId: Int = 0
    @Column(name = "dish_id")
    var dishId: Int = 0
    var count: Int = 0
}