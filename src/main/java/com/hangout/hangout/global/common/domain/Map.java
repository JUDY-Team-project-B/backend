package com.hangout.hangout.global.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "MAP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Map extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAP_ID")
    private Long id;

}
