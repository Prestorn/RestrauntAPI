package com.example.restrauntAPI.controller

import com.example.restrauntAPI.model.DishInOrder
import com.example.restrauntAPI.service.DishInOrderService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class DishInOrderController(val dishInOrderService: DishInOrderService) {

    @GetMapping(path = ["dishes_in_orders"])
    fun findAll() : List<DishInOrder> {
        return dishInOrderService.findAllDishesInOrders()
    }

    @GetMapping(path = ["dish_in_order/{id}"])
    fun findById(@PathVariable id: Int) : DishInOrder {
        return dishInOrderService.findDishInOrderById(id)
    }

    @GetMapping(path = ["dishes_in_order/{id}"])
    fun findByOrderId(@PathVariable id: Int) : List<DishInOrder> {
        return dishInOrderService.findAllDishesInOrder(id)
    }

    @PostMapping(path = ["new_dish_in_order"])
    fun create(@RequestBody dishInOrder: DishInOrder) : DishInOrder {
        return dishInOrderService.createDishInOrder(dishInOrder)
    }

    @PutMapping(path = ["update_dish_count"])
    fun update(@RequestParam id: Int, @RequestParam count: Int) : DishInOrder {
        return dishInOrderService.updateCount(id, count)
    }

    @DeleteMapping(path = ["delete_dish_from_order/{id}"])
    fun delete(@PathVariable id: Int) : String {
        return dishInOrderService.deleteDishInOrder(id)
    }
}