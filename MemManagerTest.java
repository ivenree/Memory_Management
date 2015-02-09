import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


// -------------------------------------------------------------------------
/**
 *  test memmanager class.
 *
 *  @author wenfeng ren
 *  @version Sep 16, 2014
 */
public class MemManagerTest extends TestCase
{
    /**
     * declaration
     */
    MemManager mem;
    /**
     * declare artist name to test
     */
    String name = "Blind Lemon Jefferson";

    @Before
    public void setUp()
        throws Exception
    {
        //
        mem = new MemManager(32);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    @Test
    public void testGet()
    {
        byte[] data = Client.stringToByte(name);
        Handle handle = mem.insert(data, data.length);
        byte[] space = new byte[data.length];
        int copySize = mem.get(space, handle, space.length);
        assertEquals(21, copySize);
    }

}
