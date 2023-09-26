package com.hangout.hangout.global.handler;


import com.hangout.hangout.global.error.ResponseEntity;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.BaseException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@Slf4j
@RestControllerAdvice // controller의 exception catch
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    protected ResponseEntity handleException(BaseException e, HttpServletResponse response) {
        response.setStatus(e.getResponseType().getStatus());
        StackTraceElement ste = e.getStackTrace()[0];
        log.error("[{}-{}] : {}", ste.getClassName(), ste.getLineNumber(),
            e.getResponseType().getMessage());
        return ResponseEntity.failureResponse(e.getResponseType());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);
        StackTraceElement ste = e.getStackTrace()[0];
        log.error("[{}-{}] : {}", ste.getClassName(), ste.getLineNumber(), e.getMessage());
        return ResponseEntity.failureResponse(ResponseType.FAILURE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity fileSizeLimitExceededException() {
        return ResponseEntity.failureResponse(ResponseType.MAX_UPLOAD_SIZE_EXCEEDED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuthentication(
        AuthenticationException e, HttpServletResponse response) {
        if (e instanceof UsernameNotFoundException) {
            response.setStatus(ResponseType.USER_NOT_FOUND.getStatus());
            return ResponseEntity.failureResponse(ResponseType.USER_NOT_FOUND);
        } else if (e instanceof BadCredentialsException) {
            response.setStatus(ResponseType.INVALID_USER_PASSWORD.getStatus());
            return ResponseEntity.failureResponse(ResponseType.INVALID_USER_PASSWORD);
        } else if (e instanceof AccountExpiredException) {
            response.setStatus(ResponseType.USER_ACCOUNT_EXPIRED.getStatus());
            return ResponseEntity.failureResponse(ResponseType.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof CredentialsExpiredException) {
            response.setStatus(ResponseType.USER_PASSWORD_EXPIRED.getStatus());
            return ResponseEntity.failureResponse(ResponseType.USER_PASSWORD_EXPIRED);
        } else if (e instanceof LockedException) {
            response.setStatus(ResponseType.USER_ACCOUNT_LOCKED.getStatus());
            return ResponseEntity.failureResponse(ResponseType.USER_ACCOUNT_LOCKED);
        } else if (e instanceof DisabledException) {
            response.setStatus(ResponseType.USER_ACCOUNT_DISABLED.getStatus());
            return ResponseEntity.failureResponse(ResponseType.USER_ACCOUNT_DISABLED);
        } else {
            return ResponseEntity.failureResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return ResponseEntity.failureResponse(
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
