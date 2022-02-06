package az.xazar.msuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private LocalDate birthday;
    private String position;
    private String department;
    private String phoneNumber;
    private boolean isDeleted;
    private String username;
    private String email;
}
