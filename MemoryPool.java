
import java.util.Arrays;

// -------------------------------------------------------------------------
/**
 * memorypool class stores the record in byte type to memory pool.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 7, 2014
 */
public class MemoryPool
{
    /**
     * memory pool array.
     */
    private byte[] pool;
    /**
     * the blockSize uses to reallocate.
     */
    private int    blockSize;


    // ----------------------------------------------------------
    /**
     * Create a new MemoryPool object and initial with blockSize.
     *
     * @param blockSize
     *            of the pool array
     */
    public MemoryPool(int blockSize)
    {
        this.blockSize = blockSize;
        pool = new byte[blockSize];
    }


    // ----------------------------------------------------------
    /**
     * store data to pool array at index "position".
     *
     * @param data
     *            to be stored into pool array
     * @param position
     *            where the data inserted
     */
    public void store(byte[] data, int position)
    {

        // store the length of record in first two bytes
        pool[position] = (byte)(data.length >> 8);
        pool[position + 1] = (byte)(data.length);
        // copy the data into memory pool
        for (int i = 0; i < data.length; i++)
        {
            pool[position + 2 + i] = data[i];
        }
    }


    // ----------------------------------------------------------
    /**
     * get data in pool array with index "position".
     *
     * @param position
     *            where the data is
     * @return data in pool array
     */
    public byte read(int position)
    {
        byte size;
        // read the size of record from first two bytes
        size = (byte)(pool[position] << 8);
        size = (byte)(size + pool[position + 1]);
        return size;
    }


    // ----------------------------------------------------------
    /**
     * read from pool array to space array.
     *
     * @param space
     *            the record will be copied to
     * @param position
     *            of the record
     * @param size
     *            of the record
     */
    public void read(byte[] space, int position, int size)
    {
        System.arraycopy(pool, position, space, 0, size);
    }


    // ----------------------------------------------------------
    /**
     * extends the size of memory pool array by twice of the original array.
     */
    public void reallocate()
    {

        int newLength = pool.length + blockSize;
        pool = Arrays.copyOf(pool, newLength);
        System.out.println("Memory pool expanded to be " + newLength
            + " bytes.");
    }

}
