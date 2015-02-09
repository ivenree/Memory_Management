

import junit.framework.TestCase;
import org.junit.Before;

// -------------------------------------------------------------------------
/**
 *  freeblock class test.
 *
 *  @author wenfeng ren(rwenfeng)
 *  @version Sep 14, 2014
 */
public class FreeblockTest extends TestCase
{
    private Freeblock freelist;
    /**
     * the initial blockSize
     */
    int blockSize = 30;

    /**
     * append node 1.
     */
    private Node append1 = new Node(2, 30);
    /**
     * append node 2.
     */
    private Node append2 = new Node(10, 32);
    /**
     * append node 3.
     */
    private Node append3 = new Node(5,45);
    @Before
    public void setUp()
        throws Exception
    {

        freelist = new Freeblock(blockSize);
    }

    // ----------------------------------------------------------
    /**
     * test append method.
     */
    public void testAppend()
    {
        freelist.append(append1);
        assertEquals(30, append1.prev().getBlockSize());
        assertEquals(0, append1.prev().getPosition());
        assertEquals(null, append1.next().next());
        assertEquals(-1, append1.next().getBlockSize());
        assertEquals(-1, append1.next().getPosition());
    }

    // ----------------------------------------------------------
    /**
     * test bestfit method.
     */
    public void testBestFit()
    {
        freelist.append(append1);
        freelist.append(append2);
        //bestfit found
        assertEquals(append1, freelist.bestFit(1));
        //bestfit not found
        assertEquals(null, freelist.bestFit(31));
    }

    // ----------------------------------------------------------
    /**
     * test insert method.
     */
    public void testInsert()
    {
        freelist.append(append2);
        freelist.append(append3);

        //insert condition
        Node insert = new Node(1,43);
        freelist.insert(insert);
        assertEquals(append2, insert.prev());
        assertEquals(append3, insert.next());
        //append condition
        Node append = new Node(5,51);
        freelist.insert(append);
        assertEquals(append3, append.prev());
    }

    // ----------------------------------------------------------
    /**
     * test remove method.
     */
    public void testRemove()
    {
        freelist.append(append1);
        freelist.append(append2);
        freelist.append(append3);

        freelist.remove(append2);
        assertEquals(append1, append3.prev());
        assertEquals(append3, append1.next());
    }

    // ----------------------------------------------------------
    /**
     * test merge method and isMergeNeed.
     */
    public void testMergeAndIsMergeNeed()
    {
        //merge need?
        freelist.append(append1);
        freelist.append(append2);
        freelist.append(append3);
        freelist.toString();
        assertEquals(false, freelist.isMergeNeed(append3));
        assertEquals(true, freelist.isMergeNeed(append1));

        //merge both
        freelist.merge(append1);
        assertEquals(0, append3.prev().getPosition());
        assertEquals(42, append3.prev().getBlockSize());

        //merge right
        Node right = new Node(3, 50);
        freelist.insert(right);
        assertEquals(8, append3.getBlockSize());

        //merge left
        Node left = new Node(5,40);
        freelist.insert(left);
        assertEquals(40, left.getPosition());
        assertEquals(13, left.getBlockSize());

        assertEquals(-1, left.next().getBlockSize());

    }

    // ----------------------------------------------------------
    /**
     * test updateList method.
     */
    public void testUpdateList()
    {
        freelist.append(append2);
        freelist.append(append3);
        //best fit not found
        assertEquals(-1, freelist.updateList(32));
        //recordSize == blockSize
        int index = freelist.updateList(10);
        assertEquals(32, index);
        assertEquals(0, append3.prev().getPosition());
        assertEquals(30, append3.prev().getBlockSize());
        //recordSize < blockSize
        int position = freelist.updateList(3);
        assertEquals(2, append3.getBlockSize());
        assertEquals(48, position);
    }


}
