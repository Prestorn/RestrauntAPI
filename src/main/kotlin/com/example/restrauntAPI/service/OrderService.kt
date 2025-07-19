package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Order
import com.example.restrauntAPI.repository.ClientRepository
import com.example.restrauntAPI.repository.DishInOrderRepository
import com.example.restrauntAPI.repository.DishRepository
import com.example.restrauntAPI.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class OrderService(val orderRepository: OrderRepository,
                   val clientRepository: ClientRepository,
                   val dishRepository: DishRepository,
                   val dishInOrderRepository: DishInOrderRepository) {

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
        if (dishRepository.findById(id).isEmpty) {
            throw IllegalStateException("Блюда с ID: $id не существует")
        }

        return orderRepository.findAllOrdersWithDish(id)
    }

    fun createOrder(order: Order) : Order {
        if (clientRepository.findById(order.clientId).isEmpty) {
            throw IllegalStateException("Клиента с ID: ${order.clientId} не существует")
        }

        return orderRepository.save(order)
    }

    fun changeClient(orderId: Int, newClientId: Int) : Order {
        val order: Order = findOrderById(orderId)

        if (clientRepository.findById(newClientId).isEmpty) {
            throw IllegalStateException("Клиента с ID: $newClientId не существует")
        }

        order.clientId = newClientId
        return orderRepository.save(order)
    }

    fun deleteOrder(id: Int) : String {
        findOrderById(id)
        dishInOrderRepository.deleteAllByOrderId(id)
        orderRepository.deleteById(id)
        return "Заказ с ID: $id удалён"
    }
}