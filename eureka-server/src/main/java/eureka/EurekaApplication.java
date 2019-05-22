package eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }


//    For AWS
//    @Bean
//    @Profile("!default")
//    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
//        EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
//        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
//        b.setDataCenterInfo(info);
//        return b;
//    }
}


