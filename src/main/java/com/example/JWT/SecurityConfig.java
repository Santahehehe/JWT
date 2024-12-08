package com.example.JWT;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
		                .requestMatchers(HttpMethod.POST, "/login").permitAll()
		                .anyRequest().authenticated()
		        )
		        //快速啟用SpringSecurity的表單登入功能，適合在開發測試階段使用		        
		        .formLogin(Customizer.withDefaults())
//		        .formLogin(form ->form
//		        	.loginPage("http://localhost:3000/FrontEnd/login.html")
//		            .defaultSuccessUrl("/current")      // 登入成功後跳轉的頁面
//		            
//		        )
		        .cors()//啟用CORS，才能啟用webconfig的配置，因為spring security跟webconfig的配置是分開的
		        .and()
		        //停	用 CSRF 保護
		        .csrf(AbstractHttpConfigurer::disable)
		        .logout(logout ->logout
		        		.logoutUrl("/logout")
		        		.deleteCookies("JSESSIONID")
		        		.logoutSuccessUrl("http://localhost:3000/FrontEnd/login.html")
		        		.permitAll()
		        )
		        
		        .build();
		
	}
	
	// 定義 AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
