package com.example.jobtracker_api.config;

import com.aston.jobtracker_api.model.Role;
import com.aston.jobtracker_api.model.UserEntity;
import com.aston.jobtracker_api.model.enums.RoleName;
import com.aston.jobtracker_api.repository.RoleRepository;
import com.aston.jobtracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**

 Initialise les rôles de base et (optionnellement) un super administrateur au démarrage.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Variables d'environnement (ou application.properties)
    @Value("${app.admin.email:}")
    private String adminEmail;
    @Value("${app.admin.password:}")
    private String adminPassword;
    @Value("${app.admin.firstName:Super}")
    private String adminFirstName;
    @Value("${app.admin.lastName:Admin}")
    private String adminLastName;

    @Override
    public void run(String... args) throws Exception {
        // Crée les rôles si absents
        Role roleUser = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleName.ROLE_USER)));
        Role roleMentor = roleRepository.findByName(RoleName.ROLE_MENTOR)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleName.ROLE_MENTOR)));
        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleName.ROLE_ADMIN)));
        log.info("Base roles ensured: USER={}, ADMIN={}", roleUser.getId(), roleAdmin.getId());

        // Crée un super admin si credentials fournis et utilisateur inexistant
        if (adminEmail != null && !adminEmail.isBlank() && adminPassword != null && !adminPassword.isBlank()) {
            userRepository.findByEmail(adminEmail).ifPresentOrElse(
                    u -> log.info("Super admin already exists: {}", adminEmail),
                    () -> {
                        UserEntity sa = new UserEntity();
                        sa.setEmail(adminEmail);
                        sa.setPassword(passwordEncoder.encode(adminPassword));
                        // Champs séparés: prénom / nom
                        sa.setFirstName(adminFirstName);
                        sa.setLastName(adminLastName);
                        Set<Role> roles = new HashSet<>();
                        roles.add(roleUser);
                        roles.add(roleAdmin);
                        sa.setRoles(roles);
                        userRepository.save(sa);
                        log.info("Super admin created: {}", adminEmail);
                    }
            );
        } else {
            log.info("No super admin credentials provided (app.admin.email / app.admin.password)");
        }
    }
}
