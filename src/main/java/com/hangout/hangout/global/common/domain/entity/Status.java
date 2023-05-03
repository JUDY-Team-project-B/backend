package com.hangout.hangout.global.common.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "STATUS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    private Long id;

    @Column(name = "STATUS_TYPE", nullable = false)
    private String type; // 1: 공개, 2 : 삭제, 3: 관리자 삭제

    public void updateStatus(Long statusId) {
        this.id = statusId;
    }
}
