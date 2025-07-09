package com.example.restrauntAPI.controller

import com.example.restrauntAPI.model.Order
import com.example.restrauntAPI.service.OrderService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class OrderController(val orderService: OrderService) {

    @GetMapping(path = ["orders"])
    fun findAll() : List<Order> {
        return orderService.findAllOrders()
    }

    @GetMapping(path = ["order/{id}"])
    fun findById(@PathVariable id: Int) : Order {
        return orderService.findOrderById(id)
    }

    @GetMapping(path = ["client_orders/{id}"])
    fun findClientOrders(@PathVariable id: Int) : List<Order> {
        return orderService.findOrdersByClientId(id)
    }

    @GetMapping(path = ["orders_with_dish/{id}"])
    fun findOrdersWithDish(@PathVariable id: Int) : List<Order> {
        return orderService.findOrdersWithDish(id)
    }

    @PostMapping(path = ["new_order"])
    fun create(@RequestBody order: Order) : Order {
        return orderService.createOrder(order)
    }

    @PutMapping(path = ["change_client"])
    fun changeClient(@RequestParam(required = true) id: Int,
                     @RequestParam(required = true) clientId: Int) : Order {
        return orderService.changeClient(id, clientId)
    }

    @DeleteMapping(path = ["delete_order/{id}"])
    fun delete(@PathVariable id: Int) : String {
        return orderService.deleteOrder(id)
    }
}