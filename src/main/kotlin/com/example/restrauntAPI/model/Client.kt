package com.example.restrauntAPI.model

import jakarta.persistence.*

@Entity
@Table(name = "clients")
class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(name = "first_name")
    var firstName : String = ""
    @Column(name = "last_name")
    var lastName : String = ""

    constructor() {}

    constructor(id: Int, firstName: String, lastName: String) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
    }


}