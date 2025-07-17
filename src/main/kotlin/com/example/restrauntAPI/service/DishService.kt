package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Dish
import com.example.restrauntAPI.model.Order
import com.example.restrauntAPI.repository.DishInOrderRepository
import com.example.restrauntAPI.repository.DishRepository
import com.example.restrauntAPI.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class DishService(val dishRepository: DishRepository,
                val dishInOrderRepository: DishInOrderRepository,
                val orderRepository: OrderRepository) {

    fun findAllDishes() : List<Dish> {
        return dishRepository.findAll()
    }

    fun findDishById(id: Int) : Dish {
        val optionalDish : Optional<Dish> = dishRepository.findById(id)

        if (optionalDish.isEmpty) {
            throw IllegalStateException("Блюда с ID: $id не существует")
        }

        return optionalDish.get()
    }

    fun createDish(dish: Dish) : Dish {
        return dishRepository.save(dish)
    }

    @Transactional
    fun updateDish(id: Int, newName: String?, newDescription: String?, newCost: Int?) : Dish {
        val optionalDish : Optional<Dish> = dishRepository.findById(id)

        if (optionalDish.isEmpty) {
            throw IllegalStateException("Блюда с ID: $id не существует")
        }

        val dish: Dish = optionalDish.get()

        if (newName != null) {
            dish.name = newName
        }

        if (newDescription != null) {
            dish.description = newDescription
        }

        if (newCost != null) {
            if (newCost < 0) {
                throw IllegalStateException("Стоимость блюда не может быть меньше 0")
            }
            dish.cost = newCost
        }

        return dish
    }

    fun deleteDish(id: Int) : String {
        val optionalDish : Optional<Dish> = dishRepository.findById(id)

        if (optionalDish.isEmpty) {
            throw IllegalStateException("Блюда с ID = $id не существуте")
        }

        val dish: Dish = optionalDish.get()
        val ordersList : List<Order> = orderRepository.findAllOrdersWithDish(dish.id)

        for (order in ordersList) {
            order.cost -= dish.cost * dishInOrderRepository.findDataByOrderIdAndDishId(order.id, dish.id).get().count
        }

        dishInOrderRepository.deleteDishInAllOrders(id)
        dishRepository.deleteById(id)

        return "Блюдо с ID = $id удалено"
    }

}