package com.example.JWT;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
		throws Exception{
		return httpSecurity
		        .authorizeHttpRequests(requests -> requests
		        		.requestMatchers(HttpMethod.GET, "/getEmail/*").authenticated()
		                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ADMIN")
		                .requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("USER")
		                .requestMatchers(HttpMethod.GET, "/guest").permitAll()
		                .anyRequest().authenticated()
		        )
		        //快速啟用SpringSecurity的表單登入功能，適合在開發測試階段使用		        
		        .formLogin(Customizer.withDefaults())
		        //停	用 CSRF 保護
		        .csrf(AbstractHttpConfigurer::disable)
		        .build();
		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
//		return NoOpPasswordEncoder.getInstance();
    }
	
	
	@Autowired
	private MemberRepo memberRepo;
	
    public MemberPo initmember() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encryptedPassword = encoder.encode("111");
		System.out.println("Encrypted Password:"+encryptedPassword);
		
		
		MemberPo memberPo = new MemberPo();
		memberPo.setEmail("111@gmail.com");
		memberPo.setUsername("user1");
		memberPo.setPassword(encryptedPassword);
		memberPo.setAuthority("ADMIN,USER");
		memberRepo.save(memberPo);
		return memberPo;
	}
	
}
