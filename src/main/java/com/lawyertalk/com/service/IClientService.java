package com.lawyertalk.com.service;

import com.lawyertalk.com.dtos.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface IClientService {
        /**
         * Creates a new client.
         *
         * @param clientDTO the client data transfer object containing client details
         * @return the created client data transfer object
         */
        ClientDTO createClient(ClientDTO clientDTO);

        /**
         * Retrieves a client by its ID.
         *
         * @param id the ID of the client
         * @return an optional containing the client data transfer object if found, or empty if not found
         */
        Optional<ClientDTO> getClientById(Long id);

        /**
         * Retrieves all clients.
         *
         * @return a list of client data transfer objects
         */
        List<ClientDTO> getAllClients();

        /**
         * Updates an existing client.
         *
         * @param id the ID of the client to update
         * @param clientDTO the client data transfer object containing updated client details
         * @return the updated client data transfer object
         */
        ClientDTO updateClient(Long id, ClientDTO clientDTO);

        /**
         * Deletes a client by its ID.
         *
         * @param id the ID of the client to delete
         */
        void deleteClient(Long id);
}
