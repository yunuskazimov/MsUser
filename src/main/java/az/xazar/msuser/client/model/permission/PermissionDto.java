package az.xazar.msuser.client.model.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private Long userId;
    private Role role;
}
