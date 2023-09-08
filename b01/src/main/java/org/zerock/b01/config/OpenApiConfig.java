package org.zerock.b01.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
					contact = @Contact(
							name = "leopard2",
							email = "test@test.com"
							),
					description = "OpenApi Documentation of Boot b01 Api",
					title = "Boot b01 Project Swagger",
					version = "1.0",
					license = @License ( 
							name = "leopard2 license",
							url ="https://내사이트.co.kr"
							),
					termsOfService = "이용약관 페이지 주소"
				),
		servers =  {
						@Server(
							description = "Local ENV",
							url="http://localhost:8080"
						),
						@Server(
								description = "Prod ENV",
								url="https://내사이트.co.kr"
						)				
					}
		)
public class OpenApiConfig {
}