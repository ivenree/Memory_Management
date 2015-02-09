

// -------------------------------------------------------------------------
/**
 * This is the Node class.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 7, 2014 the general type for node
 */

public class Node
{
    // data fields
    private int  blockSize = -1;
    private int  position  = -1;
    private Node next      = null;
    private Node prev      = null;


    // ----------------------------------------------------------
    /**
     * Create a new Node object.
     */
    public Node()
    {
        //
    }


    // ----------------------------------------------------------
    /**
     * Create a new Node object with data element.
     *
     * @param blockSize
     * @param position
     */
    public Node(int blockSize, int position)
    {
        this.blockSize = blockSize;
        this.position = position;
    }


    // ----------------------------------------------------------
    /**
     * get the blockSize.
     *
     * @return blockSize
     */
    public int getBlockSize()
    {
        return blockSize;
    }


    // ----------------------------------------------------------
    /**
     * set blockSize.
     *
     * @param blockSize
     */
    public void setBlockSize(int blockSize)
    {
        this.blockSize = blockSize;
    }


    // ----------------------------------------------------------
    /**
     * get position.
     *
     * @return position
     */
    public int getPosition()
    {
        return position;
    }


    // ----------------------------------------------------------
    /**
     * set position.
     *
     * @param position
     */
    public void setPosition(int position)
    {
        this.position = position;
    }


    // ----------------------------------------------------------
    /**
     * Get the next node.
     *
     * @return the next node
     */
    public Node next()
    {
        return next;
    }


    // ----------------------------------------------------------
    /**
     * set next node.
     *
     * @param next
     */
    public void setNext(Node next)
    {
        this.next = next;
    }


    // ----------------------------------------------------------
    /**
     * Get the prev node.
     *
     * @return the prev node
     */
    public Node prev()
    {
        return prev;
    }


    // ----------------------------------------------------------
    /**
     * set the previous node.
     *
     * @param prev
     */
    public void setPrev(Node prev)
    {
        this.prev = prev;
    }


    // ----------------------------------------------------------
    /**
     * print out the info of the node.
     *
     * @return str
     */
    public String toString()
    {
        String str = "";
        str = "(" + position + "," + blockSize + ")";

        return str;
    }

}
