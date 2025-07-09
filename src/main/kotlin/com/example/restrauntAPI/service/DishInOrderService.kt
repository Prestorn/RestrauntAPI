package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.DishInOrder
import com.example.restrauntAPI.repository.DishInOrderRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class DishInOrderService(val dishInOrderRepository: DishInOrderRepository,
                        val orderService: OrderService,
                        val dishService: DishService) {
    fun findAllDishesInOrders() : List<DishInOrder> {
        return dishInOrderRepository.findAll()
    }

    fun findAllDishesInOrder(id: Int) : List<DishInOrder> {
        orderService.findOrderById(id)
        return dishInOrderRepository.findAllByOrderId(id)
    }

    fun findDishInOrderById(id: Int) : DishInOrder {
        val optionalDishInOrder: Optional<DishInOrder> = dishInOrderRepository.findById(id)
        if (optionalDishInOrder.isEmpty) {
            throw IllegalStateException("Записи с ID = $id не существует")
        }
        return optionalDishInOrder.get()
    }

    fun createDishInOrder(dishInOrder: DishInOrder) : DishInOrder {
        orderService.findOrderById(dishInOrder.orderId)
        dishService.findDishById(dishInOrder.dishId)
        dishInOrder.count = 1
        return dishInOrderRepository.save(dishInOrder)
    }

    fun updateCount(id: Int, newCount: Int) : DishInOrder {
        if (newCount < 1) {
            throw IllegalStateException("Количество не может быть меньше 1")
        }

        val dishInOrder: DishInOrder = findDishInOrderById(id)
        dishInOrder.count = newCount
        return dishInOrderRepository.save(dishInOrder)
    }

    fun deleteDishInOrder(id: Int) : String {
        findDishInOrderById(id)
        dishInOrderRepository.deleteById(id)
        return "Запись удалена"
    }
}