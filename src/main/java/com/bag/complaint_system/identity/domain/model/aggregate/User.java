package com.bag.complaint_system.identity.domain.model.aggregate;

import com.bag.complaint_system.identity.domain.model.valueObject.Email;
import com.bag.complaint_system.identity.domain.model.valueObject.Phone;
import com.bag.complaint_system.identity.domain.model.valueObject.UserRole;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Email email;
    private String password;
    private Phone phone;
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User() {
    }

    //Método factory para crear un nuevo usuario

    public static User createUser(
            String firstName,
            String lastName,
            Email email,
            String hashedPassword,
            Phone phone,
            UserRole role
    ) {
        validateName(firstName, "First name");
        validateName(lastName, "Last name");

        validatePassword(hashedPassword);
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = hashedPassword;
        user.phone = phone;
        user.role = role;
        user.isActive = true;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        return user;
    }

    //Método para crear una victima-usuario

    public static User createVictim(
            String firstName,
            String lastName,
            Email email,
            String hashedPassword,
            Phone phone
    ) {
        return createUser(firstName, lastName, email, hashedPassword, phone, UserRole.VICTIM);
    }

    //Método para crear un admin-usuario

    public static User createAdmin(
            String firstName,
            String lastName,
            Email email,
            String hashedPassword,
            Phone phone
    ) {
        return createUser(firstName, lastName, email, hashedPassword, phone, UserRole.ADMIN);
    }

    //Método para actualizar información
    public void updateProfile(String firstName, String lastName, Phone phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    //Método para actualizar la contraseña usuario
    public void updatePassword(String newHashedPassword) {
        validatePassword(newHashedPassword);
        this.password = newHashedPassword;
        this.updatedAt = LocalDateTime.now();
    }

    //Desactivar la cuenta
    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    //Activar la cuenta
    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }

    public boolean isVictim() {
        return this.role == UserRole.VICTIM;
    }


    private static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        if (name.trim().length() > 100) {
            throw new IllegalArgumentException(fieldName + " cannot exceed 100 characters");
        }
    }

    //Validar contraseña

    public static void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.trim().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }
    }

}
