package configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class DataConfig {	
	
	// Note : the dataSource object is already inside the Spring container
	// because was registered in Global constructor
    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
   
    // Extract from : http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/jdbc.html
    // 
    // The JdbcTemplate can be used within a DAO implementation through direct instantiation
    // with a DataSource reference, or be configured in a Spring IoC container and given to 
    // DAOs as a bean reference.
    // 
    // == JdbcTemplate best practices == 
    // Instances of the JdbcTemplate class are threadsafe once configured. This is important
    // because it means that you can configure a single instance of a JdbcTemplate and then 
    // safely inject this shared reference into multiple DAOs (or repositories). 
    // The JdbcTemplate is stateful, in that it maintains a reference to a DataSource, 
    // but this state is not conversational state.
    //
    // It is seldom necessary to create a new instance of a JdbcTemplate class 
    // each time you want to execute SQL. Once configured, a JdbcTemplate instance is threadsafe. 
    // You may want multiple JdbcTemplate instances if your application accesses multiple databases, 
    // which requires multiple DataSources, and subsequently multiple differently configured JdbcTemplates.
    //
    @Bean    
    public JdbcTemplate jdbcTemplate(DataSource ds) {    
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(ds); // notice this is calling the other Bean method
        jdbcTemplate.afterPropertiesSet();

        return jdbcTemplate;
    }

}