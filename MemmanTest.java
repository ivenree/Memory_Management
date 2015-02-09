
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 *  Memman Test class.
 *
 *  @author ree
 *  @version Sep 14, 2014
 */
public class MemmanTest extends TestCase
{

    @Before
    public void setUp()
        throws Exception
    {
        //
    }


    // ----------------------------------------------------------
    /**
     * test main class.
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     */
    @Test
    public void test() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException
    {
        String[] str = {"10", "32", "input.txt"};
        Memman.main(str);
    }

}
