import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Eva
 * Abstract class for integration tests that need the spring context
 * Comprises a Spring test context and transaction management
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestDataConfig.class})
@Transactional
public abstract class AbstractIntegrationTest {

}