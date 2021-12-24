package az.xazar.msuser.controller;

import az.xazar.msuser.model.UserDto;
import az.xazar.msuser.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/int/users")
public class UserControllerInt {
    private final UserService service;

    public UserControllerInt(UserService service) {
        this.service = service;
    }

    @GetMapping("/id/{id}")
    public UserDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
