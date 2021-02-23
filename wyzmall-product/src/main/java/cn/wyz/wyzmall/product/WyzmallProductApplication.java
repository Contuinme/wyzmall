package cn.wyz.wyzmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WyzmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyzmallProductApplication.class, args);
    }

}
