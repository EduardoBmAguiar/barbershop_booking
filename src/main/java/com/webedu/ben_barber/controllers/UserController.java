package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @TrackExecutionTime
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("Adding user: initiated");
        user = userService.addUser(user);
        log.info("Adding user: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @TrackExecutionTime
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        log.info("Updating user: initiated");
        User updateUser = userService.updateUser(id, user);
        log.info("Updating user: completed");
        return ResponseEntity.ok(updateUser);
    }

    @TrackExecutionTime
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        log.info("Finding all users: initiated");
        List<User> list = userService.findAllUsers();
        log.info("Finding all users: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        log.info("Finding user by id: initiated");
        User user = userService.findById(id);
        log.info("Finding user by id: completed");
        return ResponseEntity.ok(user);
    }

    @TrackExecutionTime
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        log.info("Deleting user by id: initiated");
        userService.delete(id);
        log.info("Deleting user by id: completed");
        return ResponseEntity.noContent().build();
    }
}
