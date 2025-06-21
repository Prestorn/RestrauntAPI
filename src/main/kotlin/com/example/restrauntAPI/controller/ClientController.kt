package com.example.restrauntAPI.controller

import com.example.restrauntAPI.model.Client
import com.example.restrauntAPI.service.ClientService
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
class ClientController(val clientService: ClientService) {

    @GetMapping(path = ["clients"])
    fun getAll() : List<Client> {
        return clientService.findAllClients()
    }

    @GetMapping(path = ["client/{id}"])
    fun getById(@PathVariable id: Int) : Client {
        return clientService.findClientById(id)
    }

    @PostMapping(path = ["new_client"])
    fun create(@RequestBody client: Client ) : Client {
        return clientService.createClient(client)
    }

    @PutMapping(path = ["update_client"])
    fun update(@RequestParam id: Int,
               @RequestParam(required = false) firstName: String?,
               @RequestParam(required = false) lastName: String?) : Client {
        return clientService.updateClient(id, firstName, lastName)
    }

    @DeleteMapping(path = ["delete_client/{id}"])
    fun delete(@PathVariable id: Int) : String {
        return clientService.deleteClient(id)
    }
}