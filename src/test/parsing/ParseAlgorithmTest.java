package parsing;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mgunes on 18.12.2016.
 */
public class ParseAlgorithmTest {

    @Test
    public void clean() {
        ParseAlgorithm p = new ParseAlgorithm();

        assertEquals("test", p.clean("test."));
        assertEquals("test", p.clean("!test"));
        assertEquals("test", p.clean("test"));
        assertEquals("Güneş", p.clean("Güneş,"));
        assertEquals("Güneş", p.clean("Güneş."));
        assertEquals("Güneş", p.clean("!Güneş"));
        assertEquals("Güneş", p.clean("Güneş"));
    }
}
