package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Client
import com.example.restrauntAPI.model.Order
import com.example.restrauntAPI.repository.ClientRepository
import com.example.restrauntAPI.repository.OrderRepository
import org.aspectj.weaver.ast.Or
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class OrderService(val orderRepository: OrderRepository,
                   val clientRepository: ClientRepository,
                   val dishService: DishService) {

    fun findAllOrders() : List<Order> {
        return orderRepository.findAll()
    }

    fun findOrderById(id: Int) : Order {
        val optionalOrder: Optional<Order> = orderRepository.findById(id)
        if (optionalOrder.isEmpty) {
            throw IllegalStateException("Заказа с ID: $id не существует")
        }
        return optionalOrder.get()
    }

    fun findOrdersByClientId(id: Int) : List<Order> {
        if (clientRepository.findById(id).isEmpty) {
            throw IllegalStateException("Клиента с ID: $id не существует")
        }
        return orderRepository.findByClientId(id)
    }

    fun findOrdersWithDish(id: Int) : List<Order> {
        dishService.findDishById(id)
        return orderRepository.findAllOrdersWithDish(id)
    }

    fun createOrder(order: Order) : Order {
        if (clientRepository.findById(order.clientId).isEmpty) {
            throw IllegalStateException("Клиента с ID: ${order.clientId} не существует")
        }
        return orderRepository.save(order)
    }

    fun changeClient(orderId: Int, newClientId: Int) : Order {
        val optionalOrder: Optional<Order> = orderRepository.findById(orderId)
        if (optionalOrder.isEmpty) {
            throw IllegalStateException("Заказа с ID: $orderId не существует")
        }

        if (clientRepository.findById(newClientId).isEmpty) {
            throw IllegalStateException("Клиента с ID: $newClientId не существует")
        }

        val order: Order = optionalOrder.get()
        order.clientId = newClientId
        return orderRepository.save(order)
    }

    //TODO fun changeCost

    fun deleteOrder(id: Int) : String {
        if (orderRepository.findById(id).isEmpty) {
            throw IllegalStateException("Заказа с ID: $id не существует")
        }
        orderRepository.deleteById(id)
        return "Заказ с ID: $id удалён"
    }
}