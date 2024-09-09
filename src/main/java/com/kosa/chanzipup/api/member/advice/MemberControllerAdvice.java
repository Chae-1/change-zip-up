package com.kosa.chanzipup.api.member.advice;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
// 애플리케이션 전역에서 특정 예외가 발생할 때 같은 방식으로 처리
// RESTful API에서 JSON이나 XML 형식으로 예외를 처리하고 응답
// 이와 달리 @ControllerAdvice를 사용하면 회원가입 페이지가 리셋된 채로 다시 랜더링됨
public class MemberControllerAdvice {

    // 폼 입력값 유효성 검사
    @ExceptionHandler(ValidationException.class)
    // @ExceptionHandler를 파라미터 없이 사용하면, 해당 메소드가 모든 종류의 예외를 처리할 수 있기때문에 지정했다
    public ResponseEntity<String> handleExcption (ValidationException validationException) {
        // 클라이언트에게 보낼 응답 본문(ResponseEntity)이 단순한 텍스트 메시지라서 제너릭의 타입은 String
        // 파라미터로 담은 건 폼 작성 입력값의 유효성 검사 ex) 입력값이 null이거나 형식에서 벗어났을 경우
        return ResponseEntity.badRequest()
                .body(validationException.getMessage());
        // 클라이언트에게 404 에러를 보내고 검사 실패한 이유를 메세지로 전송
    }
}
