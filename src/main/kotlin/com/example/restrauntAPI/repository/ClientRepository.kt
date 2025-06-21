package com.example.restrauntAPI.repository

import com.example.restrauntAPI.model.Client
import org.springframework.data.jpa.repository.JpaRepository


interface ClientRepository: JpaRepository<Client, Int> {
}