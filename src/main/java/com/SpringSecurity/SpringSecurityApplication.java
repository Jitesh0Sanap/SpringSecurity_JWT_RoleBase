package com.SpringSecurity;

import com.SpringSecurity.config.AppConstant;
import com.SpringSecurity.entities.Roles;
import com.SpringSecurity.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {

	@Autowired
	RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		try {
			Roles role = new Roles();
			role.setRoleId(Long.valueOf(AppConstant.ROLE_ADMIN));
			role.setName("ROLE_ADMIN");

			Roles role1 = new Roles();
			role1.setRoleId(Long.valueOf(AppConstant.ROLE_USER));
			role1.setName("ROLE_USER");

			List<Roles> roles = List.of(role, role1);
			List<Roles> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
