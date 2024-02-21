package com.mybatis.member.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
// member 테이블과 데이터 호환을 위한 클래스
// 변수명과 컬럼명이 일치되도록 작성
@Getter
@Setter
@ToString
public class Member {
    private Integer memberId; // mysql auto_increment 자동 부여
    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDateTime regDate; // default로 처리
}
