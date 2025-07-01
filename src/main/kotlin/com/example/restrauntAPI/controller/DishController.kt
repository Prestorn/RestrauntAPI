package com.example.restrauntAPI.controller

import com.example.restrauntAPI.model.Dish
import com.example.restrauntAPI.service.DishService
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
class DishController(val dishService: DishService) {

    @GetMapping(path = ["dishes"])
    fun getAll() : List<Dish> {
        return dishService.findAllDishes()
    }

    @GetMapping(path = ["dish/{id}"])
    fun getById(@PathVariable id: Int) : Dish {
        return dishService.findDishById(id)
    }

    @PostMapping(path = ["new_dish"])
    fun create(@RequestBody dish: Dish) : Dish {
        return dishService.createDish(dish)
    }

    @PutMapping(path = ["update_dish"])
    fun update(@RequestParam id: Int,
               @RequestParam name: String?,
               @RequestParam description: String?,
               @RequestParam cost: Int?) : Dish {
        return dishService.updateDish(id, name, description, cost)
    }

    @DeleteMapping(path = ["delete_dish/{id}"])
    fun delete(@PathVariable id: Int) : String {
        return dishService.deleteDish(id)
    }
}