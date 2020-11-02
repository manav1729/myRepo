package challenge;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class JumbledNumbersTest {

	JumbledNumbers jn = new JumbledNumbers();
	
	@Test
    void test1() {
        String result = jn.calculateTotalValue("reuonnoinfe");
        assertEquals("149", result);
    }
	
	@Test
    void test2() {
        String result = jn.calculateTotalValue("veiifogvweesotwnetnvfeheiot");
        assertEquals("1225578", result);
    }
}
