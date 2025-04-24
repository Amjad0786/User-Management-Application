package in.amjadIt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import in.amjadIt.dto.EmailRequest;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private Environment env;

    public boolean sendHtmlEmail(EmailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getMessage(), true);
            helper.setFrom(env.getProperty("spring.mail.username"));

            
            if (emailRequest.getAttachment() != null && !emailRequest.getAttachment().isEmpty()) {
                helper.addAttachment(emailRequest.getAttachment().getOriginalFilename(),
                        emailRequest.getAttachment());
            }

            mailSender.send(mimeMessage);
            System.out.println("HTML email sent successfully to " + emailRequest.getTo());
            return true;
        } catch (Exception e) {
            System.out.println("Error while sending HTML email: " + e.getMessage());
            return false;
        }
    }
	

}
