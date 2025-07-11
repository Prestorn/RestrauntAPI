package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Dish
import com.example.restrauntAPI.model.DishInOrder
import com.example.restrauntAPI.model.Order
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
        val order: Order = orderService.findOrderById(dishInOrder.orderId)
        val dish: Dish = dishService.findDishById(dishInOrder.dishId)
        val optionalDishInOrder: Optional<DishInOrder> = dishInOrderRepository.findDataByOrderIdAndDishId(order.id, dish.id)

        if (optionalDishInOrder.isPresent) {
            return updateCount(optionalDishInOrder.get().id, optionalDishInOrder.get().count + 1)
        }

        dishInOrder.count = 1
        orderService.changeCost(order.id, dish.cost)
        return dishInOrderRepository.save(dishInOrder)
    }

    fun updateCount(id: Int, newCount: Int) : DishInOrder {
        if (newCount < 1) {
            throw IllegalStateException("Количество не может быть меньше 1")
        }

        val dishInOrder: DishInOrder = findDishInOrderById(id)
        val order: Order = orderService.findOrderById(dishInOrder.orderId)
        val dish: Dish = dishService.findDishById(dishInOrder.dishId)

        orderService.changeCost(order.id, dish.cost * (newCount - dishInOrder.count))
        dishInOrder.count = newCount

        return dishInOrderRepository.save(dishInOrder)
    }

    fun deleteDishInOrder(id: Int) : String {
        val dishInOrder: DishInOrder = findDishInOrderById(id)
        val order: Order = orderService.findOrderById(dishInOrder.orderId)
        val dish: Dish = dishService.findDishById(dishInOrder.dishId)

        orderService.changeCost(order.id, dish.cost * dishInOrder.count * -1)
        dishInOrderRepository.deleteById(id)

        return "Запись удалена"
    }

}