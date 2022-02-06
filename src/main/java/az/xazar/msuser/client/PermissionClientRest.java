package az.xazar.msuser.client;

import az.xazar.msuser.client.model.permission.PermissionDto;
import az.xazar.msuser.client.model.permission.RolesAddDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class PermissionClientRest {
    private final RestTemplate restTemplate;
    private final String apiUrl;

    public PermissionClientRest(RestTemplate restTemplate
            , @Value("${client.permission.int.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public boolean getRoleByUserId(Long userId, String userRole) {
        UserRoleForm form = new UserRoleForm();
        form.setUserId(userId);
        form.setUserRole(userRole);
        String url = String.format("%s", apiUrl);
        log.info("getRoleByUserId start with user Id: {}, Role: {}", userId, userRole);

        return Boolean.TRUE.equals(restTemplate.postForObject(url, form, Boolean.class));
    }

    public String deleteRoleByUserId(Long userId) {
        log.info("deleteRoleByUserId start with user Id: {}", userId);
        String url = String.format("%s/%s/%d", apiUrl, "all/uid", userId);
        return restTemplate.getForObject(url, String.class);
    }

    public List<PermissionDto> getRoleList() {
        String url = String.format("%s", apiUrl);
        log.info("getRoleList start");
        PermissionDto[] list = restTemplate.getForObject(url, PermissionDto[].class);
        assert list != null;
        return Arrays.stream(list).collect(Collectors.toList());
    }

    public String addRoleToUser(RolesAddDto rolesDto) {
        String url = String.format("%s/%s", apiUrl, "add");
        log.info("addRoleToUser start with user Id: {}, Role: {}",
                rolesDto.getUserId(), rolesDto.getRole());

        return restTemplate.postForObject(url, rolesDto, String.class);
    }
}

@Data
class UserRoleForm {
    private Long userId;
    private String userRole;
}
