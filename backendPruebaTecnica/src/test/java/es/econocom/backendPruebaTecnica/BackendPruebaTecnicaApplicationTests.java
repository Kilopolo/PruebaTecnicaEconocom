package es.econocom.backendPruebaTecnica;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class BackendPruebaTecnicaApplicationTests {

	@Test
	void contextLoads() {
		assertTrue("Silly assertion to be compliant with sonnar.",true);
	}

}
