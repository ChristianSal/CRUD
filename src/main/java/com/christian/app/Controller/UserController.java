package com.christian.app.Controller;

import com.christian.app.Entity.User;
import com.christian.app.Service.S3Service;
import com.christian.app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;


    @PostMapping
    public ResponseEntity<?> create (@RequestBody User user){
        //ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        userService.save(user);
        user.setImageUrl(s3Service.getObjectUrl(user.getImagePath()));
        user.setDocUrl(s3Service.getObjectUrl(user.getDocPath()));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value="id") Long userId){
        List<User> optionalUser=userService.findById(userId).stream()
                .peek(user->user.setImageUrl(s3Service.getObjectUrl(user.getImagePath())))
                .peek(user->user.setDocUrl(s3Service.getObjectUrl(user.getDocPath())))
                .collect(Collectors.toList());
        if(optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }





        return ResponseEntity.ok(optionalUser);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody User userDatails, @PathVariable(value = "id") Long userId){
        Optional<User> user =userService.findById(userId);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        user.get().setName(userDatails.getName());
        user.get().setPassword(userDatails.getPassword());
        user.get().setEmail(userDatails.getEmail());
        user.get().setEnabled(userDatails.getEnabled());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }
    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId){
        if(!userService.findById(userId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
    //Read all Users
    @GetMapping
    public List<User> readAll(){
        List<User> users= StreamSupport.stream(userService.findAll().spliterator(),false)
                .peek(user->user.setImageUrl(s3Service.getObjectUrl(user.getImagePath())))
                .peek(user->user.setDocUrl(s3Service.getObjectUrl(user.getDocPath())))
                .collect(Collectors.toList());
        return users;

    }
}
