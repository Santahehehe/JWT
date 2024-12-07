package com.example.JWT;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepo extends JpaRepository<MemberPo, String> {
	MemberPo findByUsername(String username);
}
