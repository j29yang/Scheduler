package com.scm.dashboard.conf;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
//@PropertySource("classpath:conf/db.properties")
public class DataSourceConfiguration {

	@Profile("development")
	@Bean
	@ConfigurationProperties(prefix = "development.db")
	public DataSource developmentDataSource() {
//		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
//				.addScript("classpath:schema.sql")
//				.addScript("classpath:data.sql").build();
		 return DataSourceBuilder.create().build();
	}

	@Profile("production")
	@Bean
	@ConfigurationProperties(prefix = "production.db")
	public DataSource productionDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Profile("development")
	@Bean
	public JpaVendorAdapter developmentJpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.H2);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		return adapter;
	}

	@Profile("production")
	@Bean
	public JpaVendorAdapter productionJpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		return adapter;
	}
}
