package com.travelstory.controllers;

import com.travelstory.dto.*;
import com.travelstory.entity.TokenModel;
import com.travelstory.entity.User;
import com.travelstory.repositories.UserRepository;
import com.travelstory.services.SendMailService;
import com.travelstory.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SendMailService sendMailService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, SendMailService sendMailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.sendMailService = sendMailService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/followers/{userId}/{page}/{size}")
    public Page<UserSearchDTO> getFollowers(@PathVariable(value = "userId") Long userId,
            @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        return userService.getFollowers(userId, page, size);
    }

    @GetMapping("/users/following/{userId}/{page}/{size}")
    public Page<UserSearchDTO> getFollowing(@PathVariable(value = "userId") Long userId,
            @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        return userService.getFollowing(userId, page, size);
    }

    @PostMapping("/uploadProfilePic")
    public User uploadProfilePicture(@RequestBody UserPicDTO dto) throws IOException {
        return userService.uploadProfilePicture(dto);
    }

    @PostMapping("/uploadBackgroundPic")
    public User uploadBackgroundPicture(@RequestBody UserPicDTO dto) {
        return userService.uploadBackgroundPicture(dto);
    }

    @PutMapping("/updateSettings")
    public User updateSettings(@RequestBody UserSettingsDTO dto) {
        return userService.updateSettings(dto);
    }

    @PostMapping("/resetProfilePic")
    public User resetProfilePic(@RequestBody long id) {
        return userService.resetProfilePic(id);
    }

    @GetMapping("/users/{term}/{page}/{size}")
    public Page<UserSearchDTO> getUsersByTerm(@PathVariable(value = "term") String term,
            @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        return userService.getUsersByTerm(term, page, size);
    }

    @PostMapping("/registrate")
    public ResponseEntity registrateUser(@RequestBody RegistrationDTO registrationDTO) {
        if (userService.getUserByEmail(registrationDTO.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        } else {
            userService.registrateUser(registrationDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        TokenModel token = null;
        if (userService.checkCredentials(loginDTO)) {
            token = userService.signIn(loginDTO);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            log.error("There is no user with such credentials");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/forgotPass/{email}")
    public ResponseEntity sigUpSuccess(@PathVariable String email) {
        sendMailService.sendNewPassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
