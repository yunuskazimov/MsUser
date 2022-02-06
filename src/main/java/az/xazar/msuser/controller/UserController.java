package az.xazar.msuser.controller;

import az.xazar.msuser.client.model.permission.RolesAddDto;
import az.xazar.msuser.model.PasswordDto;
import az.xazar.msuser.model.UserCreateDto;
import az.xazar.msuser.model.UserEditDto;
import az.xazar.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService service;

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'SUPER'})")
    @PostMapping()
    public ResponseEntity<UserCreateDto> saveUser(@RequestHeader(name = "User-Id") String userId,
                                                  @RequestBody UserCreateDto dto) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(service.createUser(dto));
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'ADMIN'})")
    @PutMapping("/id/{id}")
    public ResponseEntity<UserEditDto> editUser(@RequestHeader(name = "User-Id") String userId,
                                                @PathVariable Long id, @RequestBody UserEditDto dto) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/id").toUriString());
        return ResponseEntity.created(uri).body(service.editUser(id, dto));
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @PostMapping("/pass")
    public boolean changePassword(@RequestHeader(name = "User-Id") String userId,
                                  @RequestBody PasswordDto dto) {
        return service.changePassword(dto);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'SUPER'})")
    @PostMapping("/role")
    public void addRole(@RequestHeader(name = "User-Id") String userId,
                        @RequestBody RolesAddDto rolesDto) {
        service.addRoleToUser(rolesDto);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @GetMapping("/id/{id}")
    public UserEditDto getById(@RequestHeader(name = "User-Id") String userId,
                               @PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'ADMIN'})")
    @GetMapping()
    public List<UserEditDto> getList(@RequestHeader(name = "User-Id") String userId) {
        return service.getUsers();
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'SUPER'})")
    @DeleteMapping("/id/{id}")
    public String deleteById(@RequestHeader(name = "User-Id") String userId,
                             @PathVariable Long id) {
        return service.deleteById(id);
    }


}
