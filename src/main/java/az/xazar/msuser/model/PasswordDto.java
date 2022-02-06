package az.xazar.msuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
    private Long userId;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
