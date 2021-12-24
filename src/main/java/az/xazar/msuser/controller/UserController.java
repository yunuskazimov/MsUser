package az.xazar.msuser.controller;

import az.xazar.msuser.model.UserDto;
import az.xazar.msuser.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        return new ResponseEntity<>(
                service.createUser(dto), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public UserDto getById(@PathVariable Long id){
        return service.getById(id);
    }
}
