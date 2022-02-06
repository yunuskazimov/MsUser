package az.xazar.msuser.config;

import az.xazar.msuser.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public UserMapper dayOffMapper() {
        return UserMapper.INSTANCE;
    }

}
