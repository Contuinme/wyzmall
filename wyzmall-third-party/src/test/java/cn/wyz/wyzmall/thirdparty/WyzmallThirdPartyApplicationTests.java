package cn.wyz.wyzmall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class WyzmallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Test
    public void testUpload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("/Users/wangnanxiang/Pictures/1a374f1629daeb4dea9782d09b47d823.jpg");
        ossClient.putObject("wyzmall-hello","桌面.jpg",inputStream);
        ossClient.shutdown();
        System.out.println("上传成功");
    }

    @Test
    void contextLoads() {
    }

}
