package cn.wyz.wyzmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "cn.wyz.wyzmall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class WyzmallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyzmallMemberApplication.class, args);
    }

}
