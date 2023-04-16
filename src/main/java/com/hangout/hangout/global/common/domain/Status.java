package com.hangout.hangout.global.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "STATUS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Status extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    private Long id;

    @Column(name = "STATUS_TYPE", nullable = false)
    private String type;
}
