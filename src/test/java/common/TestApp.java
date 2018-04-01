package common;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = MedicalApp.class)
@PropertySource("classpath:app.datasource.properties")
public class TestApp {
}
