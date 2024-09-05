package com.lawyertalk.com.mapper;

import com.lawyertalk.com.dtos.ClientDTO;
import com.lawyertalk.com.entity.Client;

public class ClientMapper {

    // Converts ClientDTO to Client
    public static Client toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }
        Client client = new Client();
        client.setFullName(dto.getFullName());
        client.setEmail(dto.getEmail());
        client.setMobileNumber(dto.getMobileNumber());
        client.setPassword(dto.getPassword());
        return client;
    }

    // Converts Client to ClientDTO
    public static ClientDTO toDTO(Client entity) {
        if (entity == null) {
            return null;
        }
        ClientDTO dto = new ClientDTO();
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}