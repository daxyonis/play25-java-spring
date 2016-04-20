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
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost\\SQLEXPRESS08;databaseName=GroupeBO;");
        dataSource.setUsername("GBOusager");
        dataSource.setPassword("$$gboX23L14u07*15");
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
