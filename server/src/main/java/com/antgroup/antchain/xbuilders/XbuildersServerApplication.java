package com.antgroup.antchain.xbuilders;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {com.antfinancial.mychain.baas.tool.restclient.RestClientConfiguration.class})
@MapperScan("com.antgroup.antchain.xbuilders.dal.mapper")
public class XbuildersServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(XbuildersServerApplication.class, args);
	}

}
