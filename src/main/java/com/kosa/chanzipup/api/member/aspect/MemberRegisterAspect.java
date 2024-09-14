package com.kosa.chanzipup.api.member.aspect;

import com.kosa.chanzipup.api.member.controller.response.MemberRegisterResponse;
import com.kosa.chanzipup.application.VerificationMailSender;
import com.kosa.chanzipup.application.VerificationCode;
import com.kosa.chanzipup.application.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MemberRegisterAspect {

    private final VerificationService verificationService;
    private final VerificationMailSender sender;

    @Around("execution(* com.kosa.chanzipup.api.member.service.MemberService.registerMember(..))")
    public Object sendVerificationEmailAfterRegistration(ProceedingJoinPoint joinPoint) throws Throwable {
        Object executionResult = joinPoint.proceed();

        if (executionResult instanceof MemberRegisterResponse response) {
            String email = response.getEmail();
            VerificationCode verificationCode = verificationService.createVerificationCode(email);
            boolean sendResult = sender.sendVerificationTo(verificationCode.getEmail(),
                    verificationCode.getVerificationCode());

            if (sendResult) {
                log.info("이메일 전송 완료!");
            }

            return executionResult;
        }

        return executionResult;
    }
}
