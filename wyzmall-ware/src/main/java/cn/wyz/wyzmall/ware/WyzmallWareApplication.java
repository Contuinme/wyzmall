package cn.wyz.wyzmall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WyzmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyzmallWareApplication.class, args);
    }

}
