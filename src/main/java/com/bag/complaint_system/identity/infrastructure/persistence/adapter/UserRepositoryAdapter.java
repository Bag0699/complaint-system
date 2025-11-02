package com.bag.complaint_system.identity.infrastructure.persistence.adapter;

import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.model.valueObject.Email;
import com.bag.complaint_system.identity.domain.model.valueObject.Phone;
import com.bag.complaint_system.identity.domain.model.valueObject.UserRole;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.identity.infrastructure.persistence.entity.UserEntity;
import com.bag.complaint_system.identity.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = this.toEntity(user);
        return this.toUser(jpaUserRepository.save(userEntity));
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(this::toUser);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaUserRepository.findByEmail(email.getValue())
                .map(this::toUser);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaUserRepository.existsByEmail(email.getValue());
    }

    @Override
    public List<User> findAllActive() {
        return jpaUserRepository.findAllByIsActive(true)
                .stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByRole(String role) {
        UserEntity.RoleEntity roleEntity = UserEntity.RoleEntity.valueOf(role);
        return jpaUserRepository.findAllByRole(roleEntity)
                .stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllById(List<Long> ids) {
    return jpaUserRepository.findAllById(ids).stream()
            .map(this::toUser)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail().getValue());
        entity.setPassword(user.getPassword());
        entity.setPhone(user.getPhone().getValue());
        entity.setRole(UserEntity.RoleEntity.valueOf(user.getRole().name()));
        entity.setIsActive(user.isActive());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        return entity;
    }

    private User toUser(UserEntity entity) {
        User user = User.createUser(
                entity.getFirstName(),
                entity.getLastName(),
                new Email(entity.getEmail()),
                entity.getPassword(),
                new Phone(entity.getPhone()),
                UserRole.valueOf(entity.getRole().name())
        );
        user.setId(entity.getId());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());

        if (!entity.getIsActive()) {
            user.deactivate();
        }
        return user;
    }
}
