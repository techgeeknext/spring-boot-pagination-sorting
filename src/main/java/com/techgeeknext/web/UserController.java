package com.techgeeknext.web;

import com.techgeeknext.exception.UserNotFoundException;
import com.techgeeknext.model.User;
import com.techgeeknext.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<List<User>>(userService.getAllUsers(pageNo, pageSize, sortBy),
                new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable("id") Long id)
            throws UserNotFoundException {
        return new ResponseEntity<User>(userService.getUserById(id),
                new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createOrUpdateUser(User user)
            throws UserNotFoundException {
        return new ResponseEntity<User>(userService.createOrUpdateUser(user),
                new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Long id)
            throws UserNotFoundException {
        userService.deleteUserById(id);
        return HttpStatus.FORBIDDEN;
    }

}