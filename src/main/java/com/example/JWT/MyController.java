package com.example.JWT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private MemberRepo memberRepository;

    //用來加密密碼    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    
    @PostMapping("/members")
    public MemberPo createMember(@RequestBody MemberPo member) {
    	// 加密密碼
        var encodedPwd = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPwd);
    	
        member.setId(null);
        memberRepository.save(member);
        return member;
    }

    @GetMapping("/members")
    public List<MemberPo> getMembers() {
        return memberRepository.findAll();
    }
    
    
    @RequestMapping("/guest")
    public String guest() {
        return "所有人都可進";
    }

    @RequestMapping("/getEmail")
    public String getEmail() {
    	Authentication authentication = 
                SecurityContextHolder.getContext().getAuthentication();
    	MyUser myUser = (MyUser) authentication.getPrincipal();
        return myUser.getEmail();
    }

    @RequestMapping("/user")
    public String user() {
        return "只有USER可進";
    }
    @RequestMapping("/admin")
    public String admin() {
        return "只有Admin可進";
    }
}
