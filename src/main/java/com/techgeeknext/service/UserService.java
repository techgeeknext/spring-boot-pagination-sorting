package com.techgeeknext.service;

import com.techgeeknext.exception.UserNotFoundException;
import com.techgeeknext.model.User;
import com.techgeeknext.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<User> pagedResult = userRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User does not exist for given id.");
        }
    }

    public User createOrUpdateUser(User user2) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(user2.getId());

        if (user.isPresent()) {
            User user1 = user.get();
            user1.setEmail(user2.getEmail());
            user1.setFirstName(user2.getFirstName());
            user1.setLastName(user2.getLastName());

            return userRepository.save(user1);
        } else {
            return userRepository.save(user2);
        }
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User does not exist for given id.");
        }
    }
}