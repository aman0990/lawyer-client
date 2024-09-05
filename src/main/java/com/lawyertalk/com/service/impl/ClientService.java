package com.lawyertalk.com.service.impl;

import com.lawyertalk.com.dtos.ClientDTO;
import com.lawyertalk.com.entity.Client;
import com.lawyertalk.com.mapper.ClientMapper;
import com.lawyertalk.com.repository.ClientRepo;
import com.lawyertalk.com.service.IClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService {
private  ClientRepo clientRepo;

    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    /**
     * Creates a new client.
     *
     * @param clientDTO the client data transfer object containing client details
     * @return the created client data transfer object
     */
    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = ClientMapper.toEntity(clientDTO);
        Client savedClient = clientRepo.save(client);
//        log.info("Created new client with id: {}", savedClient.getId());
        return ClientMapper.toDTO(savedClient);
    }

    /**
     * Retrieves a client by its ID.
     *
     * @param id the ID of the client
     * @return an optional containing the client data transfer object if found, or empty if not found
     */
    @Override
    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepo.findById(id)
                .map(ClientMapper::toDTO);
    }

    /**
     * Retrieves all clients.
     *
     * @return a list of client data transfer objects
     */
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepo.findAll().stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing client.
     *
     * @param id        the ID of the client to update
     * @param clientDTO the client data transfer object containing updated client details
     * @return the updated client data transfer object
     */
    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        if (!clientRepo.existsById(id)) {
            throw new RuntimeException("Client not found with id " + id);
        }
        Client client = ClientMapper.toEntity(clientDTO);
        client.setId(id);
        Client updatedClient = clientRepo.save(client);
        return ClientMapper.toDTO(updatedClient);
    }

    /**
     * Deletes a client by its ID.
     *
     * @param id the ID of the client to delete
     */
    @Override
    public void deleteClient(Long id) {
        if (!clientRepo.existsById(id)) {
            throw new RuntimeException("Client not found with id " + id);
        }
        clientRepo.deleteById(id);
    }
}
