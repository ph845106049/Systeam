package org.slaughter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author xinyang
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CodeProjectApplicationController {

    public static void main(String[] args) {
        SpringApplication.run(CodeProjectApplicationController.class, args);
    }

}
