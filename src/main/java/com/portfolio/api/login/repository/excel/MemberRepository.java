package com.portfolio.api.login.repository.excel;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.portfolio.api.entity.excel.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
    Optional<Member> findById(String email);
}