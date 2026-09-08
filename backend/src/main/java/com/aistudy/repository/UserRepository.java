package com.aistudy.repository;

import com.aistudy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JpaRepository<User, Long>을 상속하면 save(), findById(), findAll(), delete() 같은
 * 기본 메서드가 자동으로 만들어집니다. (직접 SQL을 짤 필요가 없어요)
 *
 * 아래 두 메서드는 "findByEmail", "existsByEmail"이라는 이름 규칙만 지키면
 * Spring Data JPA가 이름을 분석해서 자동으로 "WHERE email = ?" 쿼리를 만들어줍니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
