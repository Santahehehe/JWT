package com.example.JWT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//實作UserDetails
public class MyUser implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MemberPo memberPo;
	
	public MyUser(MemberPo memberPo) {
		this.memberPo = memberPo;
	}
	
	@Override
	public List<SimpleGrantedAuthority> getAuthorities(){
		List<SimpleGrantedAuthority> authorities = 
				Arrays.stream(memberPo.getAuthority().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		System.out.println(authorities);
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return memberPo.getPassword();
	}
	
	@Override
	public String getUsername() {
		return memberPo.getUsername();
	}
	
	public String getEmail() {
		return memberPo.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
