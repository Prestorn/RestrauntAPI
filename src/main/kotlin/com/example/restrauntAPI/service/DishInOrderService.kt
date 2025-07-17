package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Dish
import com.example.restrauntAPI.model.DishInOrder
import com.example.restrauntAPI.model.Order
import com.example.restrauntAPI.repository.DishInOrderRepository
import com.example.restrauntAPI.repository.DishRepository
import com.example.restrauntAPI.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class DishInOrderService(val dishInOrderRepository: DishInOrderRepository,
                         val orderRepository: OrderRepository,
                         val dishRepository: DishRepository) {
    fun findAllDishesInOrders() : List<DishInOrder> {
        return dishInOrderRepository.findAll()
    }

    fun findAllDishesInOrder(id: Int) : List<DishInOrder> {
        if (orderRepository.findById(id).isEmpty) {
            throw IllegalStateException("Заказа с ID: $id не существует")
        }

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
        val optionalOrder: Optional<Order> = orderRepository.findById(dishInOrder.orderId)
        val optionalDish: Optional<Dish> = dishRepository.findById(dishInOrder.dishId)

        if (optionalOrder.isEmpty) {
            throw IllegalStateException("Заказа с ID: ${dishInOrder.orderId} не существует")
        }
        if (optionalDish.isEmpty) {
            throw IllegalStateException("Блюда с ID: ${dishInOrder.dishId} не существует")
        }

        val order: Order = optionalOrder.get()
        val dish: Dish = optionalDish.get()
        val optionalDishInOrder: Optional<DishInOrder> = dishInOrderRepository.findDataByOrderIdAndDishId(order.id, dish.id)

        if (optionalDishInOrder.isPresent) {
            return updateCount(optionalDishInOrder.get().id, optionalDishInOrder.get().count + 1)
        }

        dishInOrder.count = 1
        order.cost += dish.cost

        orderRepository.save(order)
        return dishInOrderRepository.save(dishInOrder)
    }

    fun updateCount(id: Int, newCount: Int) : DishInOrder {
        val dishInOrder: DishInOrder = findDishInOrderById(id)
        val optionalOrder: Optional<Order> = orderRepository.findById(dishInOrder.orderId)
        val optionalDish: Optional<Dish> = dishRepository.findById(dishInOrder.dishId)

        if (optionalOrder.isEmpty) {
            throw IllegalStateException("Заказа с ID: ${dishInOrder.orderId} не существует")
        }
        if (optionalDish.isEmpty) {
            throw IllegalStateException("Блюда с ID: ${dishInOrder.dishId} не существует")
        }

        val order: Order = optionalOrder.get()

        order.cost += optionalDish.get().cost * (newCount - dishInOrder.count)
        dishInOrder.count = newCount

        orderRepository.save(order)
        return dishInOrderRepository.save(dishInOrder)
    }

    fun deleteDishInOrder(id: Int) : String {
        val dishInOrder: DishInOrder = findDishInOrderById(id)
        val optionalOrder: Optional<Order> = orderRepository.findById(dishInOrder.orderId)
        val optionalDish: Optional<Dish> = dishRepository.findById(dishInOrder.dishId)

        if (optionalOrder.isEmpty) {
            throw IllegalStateException("Заказа с ID: ${dishInOrder.orderId} не существует")
        }
        if (optionalDish.isEmpty) {
            throw IllegalStateException("Блюда с ID: ${dishInOrder.dishId} не существует")
        }

        val order: Order = optionalOrder.get()
        order.cost -= optionalDish.get().cost * dishInOrder.count

        orderRepository.save(order)
        dishInOrderRepository.deleteById(id)

        return "Запись $dishInOrder удалена"
    }

}