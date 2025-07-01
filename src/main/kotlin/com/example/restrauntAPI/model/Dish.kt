package com.example.restrauntAPI.model

import jakarta.persistence.*

@Entity
@Table(name = "dishes")
class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var name: String = ""
    var description: String = ""
    var cost: Int = 0
}