package com.sofa.example.sofarpck8sconsumer;

import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.example.app.api.HelloService;
import io.fabric8.kubernetes.client.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ImportResource({"classpath*:sofa-rpc.xml"})
@SpringBootApplication
public class SofaRpcK8sConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SofaRpcK8sConsumerApplication.class, args);

        System.setProperty(Config.KUBERNETES_AUTH_TRYKUBECONFIG_SYSTEM_PROPERTY, "false");
        System.setProperty(Config.KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY, "false");

        ApplicationConfig applicationConfig = new ApplicationConfig()
                .setAppName("sofa-rpc-k8s-consumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("kubernetes");
        registryConfig.setAddress("kubernetes.default.svc");
        registryConfig.setParameter("trustCerts", "true");
        registryConfig.setParameter("namespace", "sofa-rpc-k8s");
        registryConfig.setParameter("useHttps", "true");

        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setUniqueId("standalone")
                .setProtocol("bolt")
                .setRegister(true)
                .setRegistry(registryConfig)
                .setInterfaceId(HelloService.class.getName())
                .setApplication(applicationConfig);

        HelloService helloService = consumerConfig.refer();
        System.out.println("start call sofa-rpc-k8s-provider");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println(helloService.sayHello(" chengming"));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
