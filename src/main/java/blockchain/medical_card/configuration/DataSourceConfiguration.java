package blockchain.medical_card.configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:app.datasource.properties")
@Data
public class DataSourceConfiguration {

	@Value("${app.datasource.driver.classname}")
	private String driverClassName;

	@Value("${app.datasource.url}")
	private String url;

	@Value("${app.datasource.username}")
	private String username;

	@Value("${app.datasource.password}")
	private String password;

	@Value("${app.datasource.maximum-pool-size}")
	private int maxPoolSize;

	@Value("${app.datasource.init-pool-size}")
	private int initPoolSize;

	@Value("${app.datasource.maximum-wait-time}")
	private long maxWaitTime;

	@Value("${app.datasource.mongo.host}")
	private String mongoHost;

	@Value("${app.datasource.mongo.port}")
	private Integer mongoPort;

	@Value("${app.datasource.mongo.db}")
	private String mongoDbName;



	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaxWait(maxWaitTime);
		dataSource.setMaxActive(maxPoolSize);
		dataSource.setInitialSize(initPoolSize);
		dataSource.setDriverClassName(driverClassName);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(mongoHost, mongoPort);
	}

	@Bean
	public MongoDatabase mongoDatabase(@Autowired MongoClient mongoClient) {
		return mongoClient.getDatabase(mongoDbName);
	}
}
