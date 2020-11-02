package challenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JumbledNumbersTest {

	JumbledNumbers jn = new JumbledNumbers();
	
	@Test
    public void test1() {
        String result = jn.calculateTotalValue("reuonnoinfe");
        assertEquals("149", result);
    }
	
	@Test
    public void test2() {
        String result = jn.calculateTotalValue("veiifogvweesotwnetnvfeheiot");
        assertEquals("1225578", result);
    }
}
