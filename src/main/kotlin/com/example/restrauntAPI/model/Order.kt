package com.example.restrauntAPI.model

import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(name = "client_id")
    var clientId: Int = 0
    var cost: Int = 0
}