package az.xazar.msuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
public class MailDto implements Serializable {
    private String text;
    private String username;
    private String password;
    private String email;
}
