package com.scm.dashboard.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:conf/email.properties")
public class EmailConfiguration {

}
