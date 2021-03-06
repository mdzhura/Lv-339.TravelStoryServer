package com.travelstory.controllers;

import com.travelstory.dto.ProfileDTO;
import com.travelstory.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("addUser")
    public ResponseEntity addUser(@RequestBody ProfileDTO addedUser) {
        if (adminService.addUser(addedUser)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("editUser")
    public ResponseEntity editUser(@RequestBody ProfileDTO editedUser) {
        if (adminService.editUser(editedUser)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("getAllUsers/{position}/{quantity}")
    public List<ProfileDTO> getAllUsers(@PathVariable(value = "position") int position,
            @PathVariable(value = "quantity") int quantity) {
        return adminService.getAllUsers(position, quantity);
    }

    @GetMapping("getAllAdmins/{position}/{quantity}")
    public List<ProfileDTO> getAllAdmins(@PathVariable(value = "position") int position,
            @PathVariable(value = "quantity") int quantity) {
        return adminService.getAllAdmins(position, quantity);
    }

    @GetMapping("getUser/{id}")
    public ProfileDTO getUser(@PathVariable(value = "id") long id) {
        return adminService.getUserById(id);
    }

    @GetMapping("deleteUser/{id}")
    public void deleteUser(@PathVariable(value = "id") long id) {
        adminService.deleteUser(id);
    }

    @GetMapping("markDeletedUser/{id}")
    public void markDeletedUser(@PathVariable(value = "id") long id) {
        adminService.markAsDeleted(id);
    }

    @GetMapping("markActiveUser/{id}")
    public void markActiveUser(@PathVariable(value = "id") long id) {
        adminService.markAsActive(id);
    }

    @GetMapping("markBannedUser/{id}")
    public void markBannedUser(@PathVariable(value = "id") long id) {
        adminService.markAsBanned(id);
    }

    @GetMapping("setAdminStatus/{id}")
    public void setAdminStatus(@PathVariable(value = "id") long id) {
        adminService.setAdminStatus(id);
    }

    @GetMapping("setUserStatus/{id}")
    public void setUserStatus(@PathVariable(value = "id") long id) {
        adminService.setUserStatus(id);
    }

    @GetMapping("deleteComment/{id}")
    public void deleteComment(@PathVariable(value = "id") long id) {
        adminService.deleteComment(id);
    }

    @GetMapping("deleteTravelStory/{id}")
    public void deleteTravelStory(@PathVariable(value = "id") long id) {
        adminService.deleteTravelStory(id);
    }
}
