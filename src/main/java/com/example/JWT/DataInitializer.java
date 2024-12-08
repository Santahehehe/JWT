package com.example.JWT;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MemberRepo memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 檢查是否已經有該帳號，避免重複插入
        if (memberRepository.findByUsername("admin") == null) {
            MemberPo admin = new MemberPo();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password")); // 加密密碼
            admin.setEmail("111@gmail.com");
            admin.setAuthority("ADMIN,USER");   // 設定角色
            memberRepository.save(admin);
            System.out.println("Admin account created: username=admin, password=password");
        }
    }
}