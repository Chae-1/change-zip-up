package com.kosa.chanzipup.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HelloTest {

    @Autowired
    JPAQueryFactory factory;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Querydsl 기본 동작 확인")
    @Rollback(false)
    void hello_test() {
        // given
        Hello hello1 = new Hello("hello");
        em.persist(hello1);

        // when
        Hello hello = factory.selectFrom(QHello.hello)
                .where(QHello.hello.id.eq(hello1.getId()))
                .fetchFirst();

        // then
        assertThat(hello.getId()).isEqualTo(hello1.getId());
    }
}