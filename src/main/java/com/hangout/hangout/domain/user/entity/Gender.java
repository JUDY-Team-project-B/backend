package com.hangout.hangout.domain.user.entity;

public enum Gender {
    MAN("남성"),
    WOMAN("여성");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return  this.gender;
    }
}
