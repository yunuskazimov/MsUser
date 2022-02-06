package az.xazar.msuser.queue;


import az.xazar.msuser.model.MailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMailSender {
    private final RabbitTemplate rabbitTemplate;

    public UserMailSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMail(MailDto dto) {
        rabbitTemplate.convertAndSend("MAIL_SENDER_Q", dto);
    }
}
