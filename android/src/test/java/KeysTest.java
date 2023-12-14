import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.e2ee.Keys;

public class KeysTest {
    @Test
    public void test() {
        Keys keys = new Keys();
        double result = keys.multiply(2.0, 3.0);
        assertEquals(6.0, result, 0.0);
    }
}
