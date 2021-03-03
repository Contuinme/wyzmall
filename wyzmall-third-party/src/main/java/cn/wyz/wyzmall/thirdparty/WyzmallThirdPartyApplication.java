package cn.wyz.wyzmall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WyzmallThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyzmallThirdPartyApplication.class, args);
    }

}
