package az.xazar.msuser.controller;

import az.xazar.msuser.model.UserAuthClientDto;
import az.xazar.msuser.model.UserEditDto;
import az.xazar.msuser.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/int/api/user")
@Slf4j
@CrossOrigin
public class UserControllerInt {
    private final UserService service;

    public UserControllerInt(UserService service) {
        this.service = service;
    }

    @GetMapping("/id/{id}")
    public UserEditDto getById(@PathVariable Long id) {
        log.info("getById started with id: {}", id);
        return service.getById(id);
    }

    @PostMapping("/u")
    public UserAuthClientDto getByUsername(@RequestBody GetUsernameForm form) {
        log.info("getById started with username: {}", form.getUsername());
        return service.getByUsername(form.getUsername());
    }
}

@Data
class GetUsernameForm {
    private String username;
}
