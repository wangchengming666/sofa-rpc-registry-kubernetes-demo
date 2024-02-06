package com.example.appboot;

import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.registry.Registry;
import com.alipay.sofa.rpc.registry.RegistryFactory;
import com.example.app.api.HelloService;
import com.example.appboot.impl.HelloServiceImpl;
import io.fabric8.kubernetes.client.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AppbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppbootApplication.class, args);

        System.out.println("AppbootApplication start");

        System.setProperty(Config.KUBERNETES_AUTH_TRYKUBECONFIG_SYSTEM_PROPERTY, "false");
        System.setProperty(Config.KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY, "false");

        ApplicationConfig applicationConfig = new ApplicationConfig()
                .setAppName("sofa-rpc-k8s-provider");

        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // 设置一个协议，默认bolt
                .setPort(12200) // 设置一个端口，默认12200
                .setDaemon(false); // 非守护线程

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("kubernetes");
        registryConfig.setAddress("kubernetes.default.svc");
        registryConfig.setParameter("trustCerts", "true");
        registryConfig.setParameter("namespace", "sofa-rpc-k8s");
        registryConfig.setParameter("useHttps", "true");

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setApplication(applicationConfig)
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setRegistry(registryConfig)
                .setRegister(true)
                .setUniqueId("standalone")
                .setRef(new HelloServiceImpl()) // 指定实现
                .setServer(serverConfig); // 指定服务端

        providerConfig.export(); // 发布服务

        List<RegistryConfig> registryConfigs = providerConfig.getRegistry();
        registryConfigs.forEach((config) -> {
            Registry registry = RegistryFactory.getRegistry(config);
            registry.register(providerConfig);
        });

        System.out.println("AppbootApplication start success");
    }

}
