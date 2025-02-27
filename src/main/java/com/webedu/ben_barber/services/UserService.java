package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.exceptions.DatabaseException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            if (!(user.getUsername() == null)) { userToUpdate.setUsername(user.getUsername()); }
            if (!(user.getEmail() == null)) { userToUpdate.setEmail(user.getEmail()); }
            if (!(user.getPassword() == null)) { userToUpdate.setPassword(user.getPassword()); }

            return userRepository.save(userToUpdate);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
