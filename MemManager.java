import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

// -------------------------------------------------------------------------
/**
 * this class deal with the byte array that need to be inserted into memry pool.
 * insert, remove the record to/from memory pool and update the freeblock list.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 8, 2014
 */
public class MemManager
{
    // data files
    /**
     * memory pool array
     */
    private MemoryPool pool;
    /**
     * the freeblock list to track memory pool.
     */
    private Freeblock  list;

    /**
     * the initial block size.
     */
    private int        blockSize;
    /**
     * the number of time that the pool allocate.
     */
    private int        numOfAllocate = 0;


    // ----------------------------------------------------------
    /**
     * Create a new MemManager object. initialize the pool array and freeblock
     * list.
     *
     * @param blockSize
     *            the size of the block
     */
    public MemManager(int blockSize)
    {
        // initialize the pool array.
        pool = new MemoryPool(blockSize);

        // initialize the freeblock list, the position is initially 0.
        list = new Freeblock(blockSize);

        this.blockSize = blockSize;

    }


    // ----------------------------------------------------------
    /**
     * Insert a record and return its position handle.
     *
     * @param space
     *            contains the record to be inserted
     * @param size
     *            of the record
     * @return handle position handle
     */
    public Handle insert(byte[] space, int size)
    {
        // update freeblock list tracking info.
        int position = list.findPosition(size + 2);
        if (position != -1) // successfully insert the record.
        {

            pool.store(space, position);
            list.updateList(size + 2);
        }

        else
        // no best block fit the record, need to reallocate the pool.
        {

            while (position == -1)
            {
                // reallocate the pool and store the record again
                pool.reallocate();
                numOfAllocate++;
                // new free block need to be insert to freeblock list.
                Node newFreeBlock =
                    new Node(blockSize, numOfAllocate * blockSize);
                list.insert(newFreeBlock);

                // update the list: block size and block positon.
                position = list.findPosition(size + 2);

            }
            pool.store(space, position);
            list.updateList(size + 2);

        }
        // the handle need to be return with position
        Handle handle = new Handle(position);
        return handle;
    }


    // ----------------------------------------------------------
    /**
     * Free a block at the position specified by theHandle. Merge adjacent free
     * blocks.
     *
     * @param handle
     *            with position of the record that need to be removed
     */
    public void remove(Handle handle)
    {
        int position = handle.getPosition();

        // get byte size from pool array and convert to int
        int recordSize = pool.read(position) & 0xFFFF;
        // after remove record, the free block needs to be insert back to list
        Node freeBlock = new Node(recordSize + 2, position);
        list.insert(freeBlock);
    }


    // ----------------------------------------------------------
    /**
     * Return the record with handle posHandle, up to size bytes, by copying it
     * into space. Return the number of bytes actually copied into space.
     *
     * @param space
     *            that the record needs to be copied into.
     * @param handle
     *            with the position of the record
     * @param size
     *            the given size of the record
     * @return copySize the actual size of the record that copies into the space
     */
    public int get(byte[] space, Handle handle, int size)
    {
        int copySize;
        int memSize = pool.read(handle.getPosition()) & 0xFFFF;

        if (memSize > size)
        {
            copySize = size;
        }
        else
        {
            copySize = memSize;
        }
        pool.read(space, handle.getPosition() + 2, size);
        return copySize;
    }


    // ----------------------------------------------------------
    /**
     * get the String with handle.
     *
     * @param handle to get the string
     * @return string returned by handle
     */
    public String get(Handle handle)
    {
        int size = pool.read(handle.getPosition());
        byte[] space = new byte[size];
        pool.read(space, handle.getPosition() + 2, size);
        return byteToString(space, size);
    }


    // ----------------------------------------------------------
    /**
     * printout the freeblock list.
     */
    public void dump()
    {
        System.out.println(list.toString());
    }


    // ----------------------------------------------------------
    /**
     * convert byte array to string.
     *
     * @param data to convert to string
     * @param size
     *            the actual size that used
     * @return str converted from data
     */
    public String byteToString(byte[] data, int size)
    {
        String str = null;
        // load data to buffer
        ByteBuffer buffer = ByteBuffer.wrap(data);

        byte[] buf = new byte[size];
        buffer.get(buf);
        try
        {
            str = new String(buf, "US-ASCII");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return str;
    }

}
