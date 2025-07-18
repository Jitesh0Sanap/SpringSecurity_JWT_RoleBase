package com.SpringSecurity.serviceImpl;

import com.SpringSecurity.dto.JwtAuthRequest;
import com.SpringSecurity.dto.UsersDTO;
import com.SpringSecurity.entities.Roles;
import com.SpringSecurity.entities.Users;
import com.SpringSecurity.repo.RoleRepo;
import com.SpringSecurity.repo.UserRepo;
import com.SpringSecurity.security.JWTservice;
import com.SpringSecurity.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserServicesImpl implements UsersService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTservice jwTservice;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    public UsersDTO register(UsersDTO userDTO) {
        Users users = new Users();
        users.setName(userDTO.getName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());

        //set default role
        Roles roles = this.roleRepo.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("User role NOT FOUND"));
        users.setRoles(Set.of(roles));

        Users save = userRepo.save(users);
        return this.modelMapper.map(save , UsersDTO.class);
    }

    @Override
    public UsersDTO getUserById(Long userId) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id : " + userId));
        return this.modelMapper.map(users , UsersDTO.class);
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        List<Users> all = userRepo.findAll();
        List<UsersDTO> collect = all.stream().map((user) -> this.modelMapper.map(user, UsersDTO.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteUser(Long userId) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id : " + userId));
        userRepo.deleteById(userId);
    }

    @Override
    public UsersDTO updateUser(Long userId, UsersDTO userDTO) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id : " + userId));
        users.setName(userDTO.getName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        Users save = userRepo.save(users);
        return this.modelMapper.map(save , UsersDTO.class);
    }

    @Override
    public UsersDTO registerNewAdmin(UsersDTO userDTO) {
        return null;
    }

    @Override
    public void verifyEmailOtp(Integer otp, String email) {

    }

    @Override
    public String verify(JwtAuthRequest request) {
        System.out.println("Request username: " + request.getUsername());
        System.out.println("Request password: " + request.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            System.out.println("✅ Authentication: " + authentication.isAuthenticated());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            if (authentication.isAuthenticated()) {
                System.out.println("✅ Authentication successful");
                String token = jwTservice.generateToken(userDetails);
                System.out.println("Generated Token: " + token);
                return token;
            }
        } catch (Exception e) {
            System.out.println("❌ Authentication Exception: " + e.getMessage());
            e.printStackTrace();
        }

        return "fail";
    }

    @Override
    public UsersDTO registerNewUser(UsersDTO userDTO) {
        return null;
    }
}
