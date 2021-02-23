package cn.wyz.wyzmall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class WyzmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyzmallCouponApplication.class, args);
    }

}
