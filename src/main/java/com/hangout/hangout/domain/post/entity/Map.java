package com.hangout.hangout.domain.post.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Map {
    protected String state;
    protected String city;
    protected double latitude; // 위도
    protected double longitude; // 경도

}
