package com.kosa.chanzipup.api.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailSendClient {

    private final JavaMailSender mailSender;

    private final String target;

    public MailSendClient(JavaMailSender mailSender, @Value("${target.address}") String target) {
        this.mailSender = mailSender;
        this.target = target;
    }

    // 회원 가입을 수행했을 때 가입 당시 이메일에 verificationCode 링크를 전송한다.
    public boolean sendVerificationCode(String toEmail, String verificationCode) {
        String clientUrl = String.format("%s/verify-email", target);  // 프론트엔드 URL
        String verificationLink = clientUrl + "?code=" + verificationCode;
        String subject = makeSubject();
        String makeVerificationContent = makeVerificationContent(toEmail, verificationLink);
        return sendToLocalUserAuthenticationCode(toEmail, subject, makeVerificationContent);
    }

    private String makeSubject() {
        return "체인집업: 회원 인증";
    }

    private String makeVerificationContent(String toEmail, String verificationLink) {
        String htmlContent = """
                <html>
                    <head>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f4f4f4;
                                margin: 0;
                                padding: 0;
                                -webkit-text-size-adjust: none;
                            }
                            table {
                                border-collapse: collapse;
                            }
                            .email-container {
                                max-width: 600px;
                                margin: 0 auto;
                                background-color: #ffffff;
                                padding: 20px;
                                border-radius: 8px;
                                box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
                            }
                            h1 {
                                color: #333333;
                                font-size: 24px;
                            }
                            p {
                                color: #666666;
                                font-size: 16px;
                            }
                            .verify-btn {
                                display: inline-block;
                                padding: 10px 20px;
                                margin-top: 20px;
                                background-color: #4CAF50;
                                color: #ffffff;
                                text-decoration: none;
                                border-radius: 5px;
                                font-size: 16px;
                            }
                            .verify-btn:hover {
                                background-color: #45a049;
                            }
                        </style>
                    </head>
                    <body>
                        <table class="email-container">
                            <tr>
                                <td>
                                    <h1>안녕하세요, %s님!</h1>
                                    <p>아래의 링크를 클릭하면 인증이 수행됩니다. 이후 로그인하실 수 있습니다.</p>
                                    <a href="%s" class="verify-btn">이메일 인증 하기</a>
                                    <p>If you did not request this email, please ignore it.</p>
                                </td>
                            </tr>
                        </table>
                    </body>
                </html>
                """.formatted(toEmail, verificationLink);  // address 값을 URL에 삽입
        return htmlContent;
    }

    private boolean sendToLocalUserAuthenticationCode(String toEmail, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(toEmail);  // 수신자 이메일 주소
            helper.setSubject(subject);  // 이메일 제목
            helper.setText(htmlContent, true);  // true는 HTML 콘텐츠를 의미함
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            throw new IllegalStateException("인증 메시지를 전송하지 못했습니다.", e);
        }
    }

}
