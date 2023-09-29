package com.example.student_portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;




//public class StudentPortalApplication {
//
////    public static void main(String[] args) {
////
////        String JDBC_URL = "jdbc:mysql://localhost:3306/test";
////        String JDBC_USER = "root";
////        String JDBC_PASSWORD = "password";
////        //Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
////        SpringApplication.run(StudentPortalApplication.class, args);
////    }
//
//
//}
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@ComponentScan({"com.example.student_portal.controller","com.example.student_portal.Service","com.example.student_portal.ServiceImpl","com.example.student_portal.Components"})
@MapperScan("com.example.student_portal.Mapper")
public class StudentPortalApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StudentPortalApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
