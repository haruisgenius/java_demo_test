//package com.example.java_demo_test.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration //跟 spring 說此 class 為配置類
//@EnableOpenApi //通過此註解來啟用 Swagger
//@EnableWebMvc //引入DelegatingWebMVcConfiger 配置額, 並啟用spring MVC
//public class SwaggerConfig {
//	private ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
//			.title("java_demo_test Restful API") //標題 > 要改
//			.description("java_demo_test API") //說明 > 要改
////			.termsOfServiceUrl("urn:tos") //服務條款網址, 默認urn:tos
////			.contact(new Contact("DEFAULT", "", "")) //聯絡人
////			.license("Apache 2.0")
////			.version("v3") //版本
////			.licenseUrl("https://www.apache.org/licenses/LICENSE_2.0.txt")
//			.build();
//	
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(DEFAULT_API_INFO)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.example.java_demo_test.controller")) //路徑 > 要改
//				.paths(PathSelectors.any())
//				.build();
//	}
//	
//	
//}
