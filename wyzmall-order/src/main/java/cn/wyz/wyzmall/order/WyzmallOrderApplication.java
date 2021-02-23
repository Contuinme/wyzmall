package cn.wyz.wyzmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WyzmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyzmallOrderApplication.class, args);
    }

}
