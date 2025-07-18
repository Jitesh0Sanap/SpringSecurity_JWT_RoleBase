package com.SpringSecurity.controller;

import com.SpringSecurity.dto.UsersDTO;
import com.SpringSecurity.entities.Users;
import com.SpringSecurity.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping("/post")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO){
        UsersDTO user = usersService.register(usersDTO);
        return new ResponseEntity<>(user , HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Long userId){
        UsersDTO user = usersService.getUserById(userId);
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsersDTO>> getAllUsers(){
        List<UsersDTO> user = usersService.getAllUsers();
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<UsersDTO> updateUSer(@PathVariable Long userId, @RequestBody UsersDTO usersDTO){
        UsersDTO user = usersService.updateUser(userId , usersDTO);
        return new ResponseEntity<>(user , HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        usersService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully with id: " + userId , HttpStatus.OK );
    }

    @GetMapping("/whoami")
    public String whoAmI(Authentication authentication) {
        if (authentication == null) {
            return "Authentication is null (Not logged in)";
        }
        System.out.println("✅ Authenticated as: " + authentication.getName());
        System.out.println("✅ Roles: " + authentication.getAuthorities());
        return "You are: " + authentication.getName() + " with roles: " + authentication.getAuthorities();
    }



}
