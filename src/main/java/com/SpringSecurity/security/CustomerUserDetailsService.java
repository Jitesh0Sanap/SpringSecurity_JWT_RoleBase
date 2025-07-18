package com.SpringSecurity.security;

import com.SpringSecurity.entities.UserPrinciple;
import com.SpringSecurity.entities.Users;
import com.SpringSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🧠 loadUserByUsername CALLED for: " + username);
        Users users = userRepo.findByName(username).orElseThrow(() -> new RuntimeException("user not found with username : " + username));
        if(users == null){
            System.out.println("user not found");
        }

        System.out.println(users.getName());
        System.out.println("pass:" + users.getPassword());
        System.out.println("role:" + users.getRoles());


        return new UserPrinciple(users);
    }
}
