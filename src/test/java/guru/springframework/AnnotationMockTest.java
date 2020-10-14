package guru.springframework;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AnnotationMockTest {
	
	@Mock
	Map<String, Object> mapMock;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);	
	}

	
	@Test
	void testMock() {
		mapMock.put("key", "value");
	}
}
