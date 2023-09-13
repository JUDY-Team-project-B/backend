package com.hangout.hangout.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseType {

    // COMMON
    SUCCESS(200 ,"CM00", "success :)"),
    FAILURE(500,"CM99", "failure :("), // Internal Server Error

    ARGUMENT_NOT_VALID(403,"CM01", "Argument 유효성 검증에 실패하였습니다."),
    REQUEST_NOT_VALID(403,"CM02", "유효하지 않는 요청입니다."),
    REQUEST_UNAUTHORIZED(403,"CM03", "비인증된 요청입니다."),
    JWT_NOT_VALID(403,"CM04", "올바르지 않은 토큰입니다."),
    JWT_MALFORMED(403,"CM05", "위조된 토큰입니다."),
    JWT_UNSUPPORTED(403,"CM06", "지원하지 않는 토큰입니다."),
    JWT_SIGNATURE(403,"CM07", "시그니처 검증에 실패한 토큰입니다."),
    JWT_EXPIRED(403,"CM08", "만료된 토큰입니다."),
    JWT_NULL_OR_EMPTY(403,"CM09", "토큰이 없거나 값이 비어있습니다."),
    DATA_DUPLICATED(409,"CM10", "이미 존재하는 값입니다"),
    INVALID_FORMAT(409,"CM11", "입력 형식이 올바르지 않습니다."),

    // AUTH
    AUTH_NULL_TOKEN(403,"AU01", "토큰을 찾을 수 없습니다."),
    AUTH_NOT_SAME_TOKEN(403,"AU02", "저장된 토큰과 일치하지 않습니다."),
    AUTH_NOT_SAME_USER(403,"AU03", "Access와 Refresh 토큰의 사용자가 일치하지 않습니다."),
    AUTH_INVALID_EMAIL(409,"AU04", "이미 사용 중인 이메일입니다."),
    AUTH_INVALID_NICKNAME(409,"AU05", "이미 사용 중인 닉네임입니다."),
    AUTH_NOT_SUPPORT_PROVIDER(403,"AU06", "지원하지 않는 Provider입니다."),
    AUTH_NOT_MATCH_PROVIDER(403,"AU07", "가입된 OAuth 제공자와 일치하지 않습니다."),
    AUTH_REQUIRE_LOGIN(403,"AU08", "재로그인이 필요합니다."),
    AUTH_INVALID_PROVIDER(403,"AU09", "지원하지 않는 소셜 로그인입니다"),
    OAUTH2_USER_NOT_EXIST_EMAIL(409,"AU10", "OAuth2 provider의 email이 존재하지 않습니다"),
    OAUTH2_INVALID_REDIRECT_URL(403,"AU11", "redirect url이 유효하지 않습니다"),
    INVALID_PASSWORD(401, "AU12", "패스워드가 일치하지 않습니다."),


    // USER
    USER_NOT_EXIST_EMAIL(400,"US01", "유저 정보를 찾을 수 없습니다."),
    USER_NOT_EXIST_ID(400,"US02", "유저 정보를 찾을 수 없습니다."),

    // POST
    POST_NOT_FOUND(400,"PO01", "해당 게시글을 찾을 수 없습니다."),
    STATUS_NOT_FOUND(400,"PO02", "상태값을 찾을 수 없습니다."),

    // COMMENT
    COMMENT_NOT_FOUND(400,"C001", "해당 댓글을 찾을 수 없습니다."),

    // ABUSE REPORT
    COMMENT_ABUSE_REPORT_NOT_FOUND(400,"R001", "해당 댓글 신고건을 찾을 수 없습니다."),
    POST_ABUSE_REPORT_NOT_FOUND(400,"R002", "해당 게시글 신고건을 찾을 수 없습니다."),

    // IMAGE
    INVALID_FILE_TYPE(409,"I001", "해당 파일은 지원하는 파일 형식이 아닙니다."),
    FILE_NOT_UPLOAD(409,"I002","이미지를 업로드하지 않았습니다."),
    MAX_UPLOAD_SIZE_EXCEEDED(409,"I003", "이미지 용량이 초과되었습니다.");

    private final int status;
    private final String code;
    private final String message;
}