package org.slaughter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xinyang
 */
@SpringBootApplication
//@EnableSwaggerBootstrapUI
@EnableSwagger2
public class UserProjectApplicationController {

    public static void main(String[] args) {
        SpringApplication.run(UserProjectApplicationController.class, args);
    }

}
