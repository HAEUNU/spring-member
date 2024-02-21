package com.mybatis.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class MemberMapperTests {

    // Mapper 인터페이스 자동 주입
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void mapperTest() {
        log.info("memberMapper : {}", memberMapper);
    }

}