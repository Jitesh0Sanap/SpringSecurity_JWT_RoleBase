package com.SpringSecurity.services;

import com.SpringSecurity.dto.JwtAuthRequest;
import com.SpringSecurity.dto.UsersDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    UsersDTO registerNewUser(UsersDTO userDTO);
    UsersDTO register(UsersDTO userDTO);
    UsersDTO getUserById(Long userId);
    List<UsersDTO> getAllUsers();
    void deleteUser(Long userId);
    UsersDTO updateUser(Long userId , UsersDTO userDTO);
    UsersDTO registerNewAdmin(UsersDTO userDTO);
    void verifyEmailOtp(Integer otp, String email);

    String verify(JwtAuthRequest request);
}
