package com.example.JWT;

//照抄chatgpt全局設置CORS方法
//網址:https://chatgpt.com/c/67330136-9428-8010-9c5b-6176f523c19d

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
//              .allowedOrigins("http://localhost:3000")  // 允許來自 http://localhost:3000 的請求
              //允許所有來源的請求
      		.allowedOrigins("*")
      		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 許可的 HTTP 方法
              .allowedHeaders("*")  // 允許的請求標頭
              //.allowCredentials(true);  // 允許攜帶 Cookie
      		.allowCredentials(false);
  }
}