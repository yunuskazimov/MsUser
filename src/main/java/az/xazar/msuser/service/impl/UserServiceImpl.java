package az.xazar.msuser.service.impl;

import az.xazar.msuser.client.PermissionClientRest;
import az.xazar.msuser.client.model.permission.RolesAddDto;
import az.xazar.msuser.dao.entity.UserEntity;
import az.xazar.msuser.dao.repository.UserRepository;
import az.xazar.msuser.mapper.UserMapper;
import az.xazar.msuser.model.*;
import az.xazar.msuser.model.exception.EmailExistsException;
import az.xazar.msuser.model.exception.EmailNotCorrectException;
import az.xazar.msuser.model.exception.PasswordWrongFoundException;
import az.xazar.msuser.queue.UserMailSender;
import az.xazar.msuser.service.UserService;
import az.xazar.msuser.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Pattern;

import static az.xazar.msuser.model.exception.ErrorCodes.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;
    private final PermissionClientRest roleClient;
    private final UserMailSender mailSender;

    @Override
    public UserCreateDto createUser(UserCreateDto userCreateDto) {
        checkEmail(userCreateDto);
        checkIsNull(userCreateDto);
        checkUsername(userCreateDto);
        emailCheck(userCreateDto.getEmail());
        //TODO departament ve sobeleri ayri DB ye cixarmaq.
        // duzgun adlanmaya gore   // vezifeye gore get list olsun

        //TODO add SUPER ADMIN
        UserDto userDto = mapper.createDtoToUserEntity(userCreateDto);
        String password = generateRandomPassword(10);
        userDto.setDeleted(false);
        userDto.setPassword(passwordEncoder.encode(password));
        UserEntity entity = repo.save(mapper.toUserEntity(userDto));
        userCreateDto.setId(entity.getId());
        userDto.setPassword(password);
        sendPasswordToUserWithEmail(userDto);
        return userCreateDto;
    }

    @Override
    public UserEditDto editUser(Long id, UserEditDto editDto) {
        checkNullEditDto(editDto);
        checkDtoIdWithPathId(id, editDto);
        userUtil.findById(id);
        return mapper.toUserEditDto(repo.save(mapper.editDtoToUserEntity(editDto)));
    }

    @Override
    public void addRoleToUser(RolesAddDto roles) {
        log.info("addRoleToUser start with role {} to user Id {}", roles, roles.getUserId());
        userUtil.findById(roles.getUserId());
        String result = roleClient.addRoleToUser(roles);
        log.info("addRoleToUser completed with Roles: {}", result);
    }

    @Override
    public UserEditDto getById(Long id) {
        return mapper.toUserEditDto(
                userUtil.findById(id));
    }

    public UserAuthClientDto getByUsername(String username) {
        return mapper.toUserAuthClientDto(userUtil.findByUsername(username));
    }

    @Override
    public String deleteById(Long id) {
        UserEntity entity = userUtil.findById(id);
        entity.setDeleted(true);
        roleClient.deleteRoleByUserId(entity.getId());
        repo.save(entity);
        return "User is Deleted";
    }

    @Override
    public List<UserEditDto> getUsers() {
        return mapper.toUserEditDtoList(repo.findAll());
    }

    @Override
    public boolean changePassword(PasswordDto dto) {
        log.info("changePassword start with user id: {}", dto.getUserId());
        UserEntity entity = userUtil.findById(dto.getUserId());
        String rawPassword = dto.getCurrentPassword();
        String encodedPassword = entity.getPassword();
        if (checkCurrentPassword(rawPassword, encodedPassword)
                && checkNewPassword(dto)
                && passwordCheck(dto.getNewPassword())) {
            entity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            repo.save(entity);
            log.info("changePassword completed with user id: {}", dto.getUserId());
            return true;
        }
        return false;
    }

    private boolean checkCurrentPassword(String rawPassword, String encodedPassword) {
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            return true;
        } else {
            throw new PasswordWrongFoundException(PASSWORD_WRONG);
        }
    }

    private boolean checkNewPassword(PasswordDto dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new PasswordWrongFoundException(NEW_PASSWORD_WRONG);
        } else {
            return true;
        }
    }

    private void checkIsNull(UserCreateDto userDto) {
        if (userDto.getUsername() == null &&
                userDto.getEmail() == null &&
                userDto.getName() == null &&
                userDto.getSurname() == null &&
                userDto.getBirthday() == null) {
            throw new RuntimeException("Fill empty blank");
        }
    }

    private void checkNullEditDto(UserEditDto editDto) {
        if (editDto.getUsername() == null &&
                editDto.getEmail() == null &&
                editDto.getName() == null &&
                editDto.getSurname() == null &&
                editDto.getBirthday() == null) {
            throw new RuntimeException("Fill empty blank");
        }
    }

    private void checkDtoIdWithPathId(Long id, UserEditDto editDto) {
        if (!id.equals(editDto.getId())) {
            throw new RuntimeException("User id not same with dto id.");
        }
    }

    private void checkUsername(UserCreateDto userDto) {
        if (repo.findByUsername(userDto.getUsername()).isPresent()) {
            throw new EmailExistsException(
                    "There is an account with that Username : " + userDto.getEmail());
        }
    }

    private void checkEmail(UserCreateDto userDto) {
        if (repo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailExistsException(
                    "There is an account with that email adress : " + userDto.getEmail());
        }
    }

    private void sendPasswordToUserWithEmail(UserDto userDto) {
        mailSender.sendMail(MailDto
                .builder()
                .text(userDto.getName() + " " + userDto.getSurname())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build());
    }

    private String generateRandomPassword(int len) {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    private boolean emailCheck(String email) {
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (patternMatches(email, emailPattern)) {
            return true;
        } else {
            throw new EmailNotCorrectException(EMAIL_WRONG);
        }
    }

    private boolean passwordCheck(String email) {
        String passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String error = " # start-of-string; " +
                "\n# a digit must occur at least once; " +
                "\n# a lower case letter must occur at least once;" +
                "\n# an upper case letter must occur at least once;" +
                "\n# a special character must occur at least once;" +
                "\n# no whitespace allowed in the entire string;" +
                "\n# anything, at least eight places though;" +
                "\n# end-of-string";
        if (patternMatches(email, passwordPattern)) {
            return true;
        } else {
            throw new PasswordWrongFoundException(PASSWORD_WRONG_REGEX+" | "+error);
        }
    }

    private static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }


}
