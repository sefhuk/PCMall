package com.team5.project2.user.service;

import com.team5.project2.user.domain.Email;
import com.team5.project2.user.repository.EmailCheckRespository;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final EmailCheckRespository emailCheckRespository;
    private static final String senderEmail= "asw59412@gmail.com";
    private final long EXPIRATION_TIME_MINUTES = 5;
    private static String num;

    public static void createNumber(){
        int number = (int)(Math.random() * (90000)) + 100000;
        num = "" + number;
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + num + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void sendMail(String mail){
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        Email emailCheck = new Email(mail, num);
        emailCheckRespository.save(emailCheck);
    }

    public Email findByEmail(String email) {
        Optional<Email> codeFoundByEmail = emailCheckRespository.findFirstByEmailOrderByCreatedAtDesc(email);
        return codeFoundByEmail.orElse(null);
    }

    public boolean verifyEmailCode(String email, String code) {
        Email emailVerification = findByEmail(email);
        if (emailVerification.verifyEmailCode(code) && emailVerification.getCreatedAt().isAfter(
            LocalDateTime.now().minusMinutes(EXPIRATION_TIME_MINUTES))) {
            emailCheckRespository.save(emailVerification);
            return true; // 인증 성공
        }

        return false; // 인증 실패
    }
}