package com.example.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


//實作UserDetailsService
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public MyUser loadUserByUsername(String username)
								throws UsernameNotFoundException {
		MemberPo memberPo = memberRepo.findByUsername(username);
		return new MyUser(memberPo);
	}
}
