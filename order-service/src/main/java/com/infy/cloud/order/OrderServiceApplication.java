package com.infy.cloud.order;

import com.infy.cloud.order.config.OrderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan
public class OrderServiceApplication {

	public static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {

		ctx = SpringApplication.run(OrderServiceApplication.class, args);
		OrderConfig configuration = ctx.getBean(OrderConfig.class);
        log.info("APPLICATION NAME : "+configuration.name);
		log.info("PROXY PORT : "+configuration.proxyPort);

	}

	public static void restart() {
		ApplicationArguments args = ctx.getBean(ApplicationArguments.class);

		try{
			//Thread.sleep(3000);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		Thread thread = new Thread(() -> {
			ctx.close();
			ctx = SpringApplication.run(OrderServiceApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}


}
