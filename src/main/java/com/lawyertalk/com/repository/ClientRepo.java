package com.lawyertalk.com.repository;

import com.lawyertalk.com.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client ,Long> {
}
