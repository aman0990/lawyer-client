package com.lawyertalk.com.controller;

import com.lawyertalk.com.dtos.ClientDTO;
import com.lawyertalk.com.exception.ErrorResponse;
import com.lawyertalk.com.service.IClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clients")
@Validated
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    // Endpoint to create a client (authenticated via OAuth2)
    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDTO clientDTO,
                                          BindingResult bindingResult,
                                          @AuthenticationPrincipal OAuth2User principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // If OAuth2User (Google User) is present, associate it with the client
        if (principal != null) {
            String email = principal.getAttribute("email"); // Fetch email from Google profile
            clientDTO.setEmail(email); // Set or update the email of the clientDTO
            log.info("OAuth2 User email: {}", email);
        } else {
            log.info("No OAuth2 User principal found.");
        }

        ClientDTO createdClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    // Endpoint to update an existing client
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id,
                                          @Valid @RequestBody ClientDTO clientDTO,
                                          BindingResult bindingResult,
                                          @AuthenticationPrincipal OAuth2User principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // Optionally update email or other fields using OAuth2User info
        if (principal != null) {
            String email = principal.getAttribute("email");
            clientDTO.setEmail(email);  // Update the client DTO email based on OAuth2 profile
            log.info("Updating client with email: {}", email);
        }

        try {
            ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Client not found with id: " + id));
        }
    }

    // Endpoint to get all clients
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    // Endpoint to delete a client
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Client not found with id: " + id));
        }
    }
}
