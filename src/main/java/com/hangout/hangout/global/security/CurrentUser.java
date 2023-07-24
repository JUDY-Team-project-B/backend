package com.hangout.hangout.global.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? new com.hangout.hangout.domain.user.entity.EmptyUser() : user")
@JsonIgnore
public @interface CurrentUser {

}