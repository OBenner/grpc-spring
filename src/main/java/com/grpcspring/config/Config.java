package com.grpcspring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Pilot configurations.
 */
@Configuration
@EnableSwagger2
public class Config {

    /**
     * Query data source data source.
     *
     * @return the data source
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource queryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Query jdbc template jdbc template.
     *
     * @param queryDataSource the query data source
     * @return the jdbc template
     */
    @Bean
    JdbcTemplate queryJdbcTemplate(DataSource queryDataSource) {
        return new JdbcTemplate(queryDataSource);
    }

    /**
     * Api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(""))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo()).useDefaultResponseMessages(false);
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Example Microserivce",
                "Microservice example architecture",
                null,
                null,
                null,
                null,
                null,
                Stream.of(new StringVendorExtension("Company", "")).collect(Collectors.toList())
        );
    }
}
