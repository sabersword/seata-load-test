package org.ypq;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaojing
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@EnableAutoDataSourceProxy
public class AccountApplication {

	public static void main(String[] args) {
//		for (int i = 1; i <= 10000; i++) {
//			System.out.println("INSERT INTO account_tbl (user_id, money) VALUES ('user" + i + "', 1000000);");
//		}
//		for (int i = 1; i <= 10000; i++) {
//			System.out.println("INSERT INTO storage_tbl (commodity_code, count) VALUES ('commodity" + i + "', 1000000);");
//		}
		SpringApplication.run(AccountApplication.class, args);
	}

}
