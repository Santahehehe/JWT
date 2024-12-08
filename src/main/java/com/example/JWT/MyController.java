package com.example.JWT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class MyController {

    @Autowired
    private MemberRepo memberRepository;

    //用來加密密碼    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

//    //登入API
//    @PostMapping("/login")
//    public MemberPo login(@RequestBody MemberPo memberPo, HttpServletRequest request) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                    memberPo.getUsername(), 
//                    memberPo.getPassword()
//                )
//            );
//         
//         // 將認證結果放入 SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            
//            // 如果你正在使用 Session 驗證，將 SecurityContext 存儲在 HttpSession 中
//            HttpSession session = request.getSession(true); // 確保 Session 存在
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//            
//            System.out.println(SecurityContextHolder.getContext().getAuthentication());
//            // 假設這裡返回 token（你可以整合 JWT）
//            return memberPo;
//        } catch (AuthenticationException e) {
//            return memberPo;
//        }
//    }
    
    //用來看現在的session
    @GetMapping("/current")
    public String getCurrentSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 不創建新 Session
        if (session != null) {
            return "Session ID: " + session.getId();
        } else {
            return "No active session";
        }
    }
    
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
