package com.lingyun.wh.contactunits;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/20 9:36
 */
@SpringBootApplication
@MapperScan("com.lingyun.wh.contactunits.mapper")
public class ContactUnitsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactUnitsApplication.class, args);
    }
}
