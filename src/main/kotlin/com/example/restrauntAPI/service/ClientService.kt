package com.example.restrauntAPI.service

import com.example.restrauntAPI.model.Client
import com.example.restrauntAPI.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ClientService(val clientRepository: ClientRepository) {

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

    // TODO Каскадное удаление
    fun deleteClient(id: Int) : String {
        findClientById(id)
        clientRepository.deleteById(id)

        return "Клиент с ID: $id удалён"
    }
}