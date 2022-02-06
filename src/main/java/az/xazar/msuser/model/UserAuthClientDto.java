package az.xazar.msuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthClientDto {

    private Long id;
    private boolean isDeleted;
    private String username;
    private String password;
    private String email;
}
