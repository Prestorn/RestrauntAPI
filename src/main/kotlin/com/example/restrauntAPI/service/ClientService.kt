package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Client
import com.example.restrauntAPI.model.Order
import com.example.restrauntAPI.repository.ClientRepository
import com.example.restrauntAPI.repository.DishInOrderRepository
import com.example.restrauntAPI.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ClientService(val clientRepository: ClientRepository,
                    val orderRepository: OrderRepository,
                    val dishInOrderRepository: DishInOrderRepository) {

    fun findAllClients() : List<Client> {
        return clientRepository.findAll()
    }

    fun findClientById(id: Int) : Client {
        val optionalClient : Optional<Client> = clientRepository.findById(id)

        if (optionalClient.isEmpty) {
            throw IllegalStateException("Клиента с ID: $id не существует")
        }

        return optionalClient.get()
    }

    fun createClient(client: Client) : Client {
        return clientRepository.save(client)
    }

    fun updateClient(id: Int, newFirstName: String?, newLastName: String?) : Client {
        val client: Client = findClientById(id)

        if (newFirstName != null) {
            client.firstName = newFirstName
        }
        if (newLastName != null) {
            client.lastName = newLastName
        }

        clientRepository.save(client)
        return client
    }

    fun deleteClient(id: Int) : String {
        findClientById(id)

        val ordersList: List<Order> = orderRepository.findByClientId(id)
        for (order in ordersList) {
            dishInOrderRepository.deleteAllByOrderId(order.id)
            orderRepository.deleteById(order.id)
        }

        clientRepository.deleteById(id)

        return "Клиент с ID: $id удалён"
    }
}