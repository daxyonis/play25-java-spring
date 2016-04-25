import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Eva
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@ComponentScan({"services"})
public class TestDataConfig {
	
	@Bean
    public DataSource dataSource() {    	
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/myblog");
        dataSource.setUsername("guest");
        dataSource.setPassword("og9X679!nS03~6c");
        return dataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
      
    @Bean
    public JdbcTemplate jdbcTemplate() {    
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource()); // notice this is calling the other Bean method
        jdbcTemplate.afterPropertiesSet();

        return jdbcTemplate;
    }

}
