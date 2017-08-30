package com.scm.dashboard;

import com.scm.dashboard.schedulers.JobWatchDistributedSystem;
import com.scm.dashboard.schedulers.ParseJenkinsLogJob;
import com.scm.dashboard.utils.DataCorrect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring starter. （启动）
 *
 * @author amyqu.
 */

@EnableScheduling
@SpringBootApplication
public class Application implements InitializingBean {

	@Autowired
	private JobWatchDistributedSystem jobWatchDistributedSystem;

	@Autowired
	private DataCorrect dataCorrect;

	@Override
	public void afterPropertiesSet() throws Exception {
		jobWatchDistributedSystem.addServer();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(ParseJenkinsLogJob parseJenkinsLogJob) {
		return (args) -> {
			parseJenkinsLogJob.comsume();
//			dataCorrect.data();
		};
	}

}
