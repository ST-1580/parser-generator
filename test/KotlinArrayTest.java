import kotlinArray.KotlinArrayLexer;
import kotlinArray.KotlinArrayParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KotlinArrayTest {

    @Test
    public void testSample() {
        try {
            KotlinArrayLexer lexer = new KotlinArrayLexer("var x: Array<Int>;");
            KotlinArrayParser parser = new KotlinArrayParser(lexer);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testArrayArray() {
        try {
            KotlinArrayLexer lexer = new KotlinArrayLexer("var x: Array<Array<Int>>;");
            KotlinArrayParser parser = new KotlinArrayParser(lexer);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testIntInt() {
        try {
            KotlinArrayLexer lexer = new KotlinArrayLexer("var x: Array<Int, Int>;");
            KotlinArrayParser parser = new KotlinArrayParser(lexer);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private boolean exceptionHelper(String context) {
        try {
            KotlinArrayLexer lexer = new KotlinArrayLexer(context);
            KotlinArrayParser parser = new KotlinArrayParser(lexer);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    @Test
    public void exceptionOne() {
        assertTrue(exceptionHelper("var x: Int;"));
    }

    @Test
    public void exceptionTwo() {
        assertTrue(exceptionHelper("var x: Array<Int;"));
    }

    @Test
    public void exceptionThree() {
        assertTrue(exceptionHelper("var x: Array<Int>"));
    }
}
