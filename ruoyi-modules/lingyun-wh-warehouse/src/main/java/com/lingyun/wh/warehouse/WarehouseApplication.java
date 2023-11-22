package com.lingyun.wh.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author : Tang
 * @Description :
 * @CreateDate : 2023/11/20 9:39
 */
@SpringBootApplication
@MapperScan("com.lingyun.wh.warehouse.mapper")
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

}
