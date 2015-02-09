import junit.framework.TestCase;
import org.junit.Before;

// -------------------------------------------------------------------------
/**
 * node class test.
 *
 *  @author wenfeng ren (rwenfeng)
 *  @version Sep 13, 2014
 */
public class NodeTest extends TestCase
{
    /**
     * test node object
     */
    Node node;

    @Before
    public void setUp()
        throws Exception
    {
        node = new Node(1,1);
    }

    // ----------------------------------------------------------
    /**
     * test getBlockSize method.
     */
    public void testGetBlockSize()
    {
        assertEquals(1, node.getBlockSize());
    }


    // ----------------------------------------------------------
    /**
     * test setBlockSize method.
     */
    public void testSetBlockSize()
    {
        node.setBlockSize(2);
        assertEquals(2, node.getBlockSize());
    }

    // ----------------------------------------------------------
    /**
     * test getPosition method.
     */
    public void testGetPosition()
    {
        assertEquals(1, node.getPosition());
    }

    // ----------------------------------------------------------
    /**
     * test set position method.
     */
    public void testSetPosition()
    {
        node.setPosition(3);
        assertEquals(3, node.getPosition());
    }

    // ----------------------------------------------------------
    /**
     * test next and prev method.
     */
    public void testNextAndPrev()
    {
        //test next
        assertEquals(null, node.next());
        Node newNode = new Node(1,2);
        node.setNext(newNode);
        assertEquals(newNode, node.next());
        //test prev
        assertEquals(null, node.prev());
        node.setPrev(newNode);
        assertEquals(newNode, node.prev());
    }

    // ----------------------------------------------------------
    /**
     * test toString method.
     */
    public void testToString()
    {
        Node node1 = new Node(2,5);
        String nodeString = "(" + 5 + "," + 2 + ")";
        assertEquals(nodeString, node1.toString());
    }

}
