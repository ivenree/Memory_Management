import junit.framework.TestCase;
import org.junit.Before;

// -------------------------------------------------------------------------
/**
 * memmory manager class test.
 *
 * @author wenfeng ren
 * @version Sep 15, 2014
 */
public class HashtableTest
    extends TestCase
{
    private Hashtable songTable;
    private MemManager mem;
    /**
     *
     */
    private String            name1 = "Long Lonesome Blues";
    /**
     *
     */
    private String            name2 = "Ma Rainey's Black Bottom";
    /**
     *
     */
    private String            name3 = "Mississippi Boweavil Blues";
    /**
     *
     */
    private Handle handle1 = new Handle(1);
    private Handle handle2 = new Handle(2);

    @Before
    public void setUp()
        throws Exception
    {
        // initialize
        songTable = new Hashtable(5);
        mem = new MemManager(10);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */


    // ----------------------------------------------------------
    /**
     * test findindex method.
     */
    public void testFindIndex()
    {
        //
        long key = songTable.hash(name1);
        long keyy = songTable.hash(name2);
        assertEquals(0, songTable.findIndex(key));
        songTable.insert(name1, handle1);
        assertEquals(1, songTable.findIndex(key));
        songTable.insert(name2, handle2);
        songTable.remove(name1);
        assertEquals(2, songTable.findIndex(keyy));


    }

    // ----------------------------------------------------------
    /**
     * test insert method.
     */
    public void testInsert()
    {
        assertEquals(true, songTable.insert(name1, handle1));
        assertFalse(songTable.rehashNeed(name1));
        assertEquals(true, songTable.insert(name2, handle2));
        assertTrue(songTable.rehashNeed(name3));
        assertEquals(true, songTable.insert(name3, handle2));
        songTable.remove(name3);
        songTable.insert(name3, handle2);
        assertFalse(songTable.rehashNeed(name1));
        assertEquals(false, songTable.insert(name2, handle2));
        assertEquals(3, songTable.usedSize());
    }

    // ----------------------------------------------------------
    /**
     * test remove method.
     */
    public void testRemove()
    {
        songTable.insert(name1, handle1);
        songTable.insert(name2, handle2);
        songTable.insert(name3, handle2);
        songTable.remove(name3);
        songTable.remove(name2);
        assertEquals(1, songTable.usedSize());
    }

    // ----------------------------------------------------------
    /**
     * test search method.
     */
    public void testSearch()
    {
        long key = songTable.hash(name2);
        long keyy = songTable.hash(name3);
        assertEquals(false, songTable.search(key));
        songTable.insert(name1, handle1);
        songTable.insert(name2, handle2);
        assertEquals(true, songTable.search(key));
        songTable.insert(name3, handle2);
        assertEquals(true, songTable.search(keyy));

    }

    // ----------------------------------------------------------
    /**
     * test get method.
     */
    public void testGet()
    {
        long key = songTable.hash(name2);
        long keyy = songTable.hash(name3);
        songTable.insert(name1, handle1);
        songTable.insert(name2, handle2);
        songTable.insert(name3, handle2);
        assertEquals(6, songTable.get(key));
        assertEquals(2, songTable.get(keyy));

    }

    // ----------------------------------------------------------
    /**
     * test print method.
     */
    public void testPrint()
    {
        assertEquals("total ", songTable.print(mem));
        byte[] space = Client.stringToByte(name1);
        Handle handle3 = mem.insert(space, space.length);
        songTable.insert(name1, handle3);
        String string = "|Long Lonesome Blues| " + 0 + "\n";
        assertEquals(string + "total ", songTable.print(mem));
        byte[] space2 = Client.stringToByte(name2);
        Handle handle4 = mem.insert(space2, space2.length);
        songTable.insert(name2, handle4);
        songTable.remove((name2));
        assertEquals(string + "total ", songTable.print(mem));
    }


}
