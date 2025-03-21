package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.exceptions.DatabaseException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        log.info("New User created");
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User user) {
        log.info("Finding user by Id");
        Optional<User> userOptional = userRepository.findById(id);
        log.info("User found");
        log.info("Updating User");
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            if (!(user.getUsername() == null)) { userToUpdate.setUsername(user.getUsername()); }
            if (!(user.getEmail() == null)) { userToUpdate.setEmail(user.getEmail()); }
            if (!(user.getPassword() == null)) { userToUpdate.setPassword(user.getPassword()); }

            log.info("User updated");
            return userRepository.save(userToUpdate);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Transactional
    public List<User> findAllUsers() {
        log.info("Finding all users");
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) {
        log.info("Finding user by Id");
        Optional<User> userOptional = userRepository.findById(id);
        log.info("User found");
        return userOptional.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting user by Id");
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("Integrity violation");
            throw new DatabaseException(e.getMessage());
        }
    }
}
