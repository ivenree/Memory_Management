
// -------------------------------------------------------------------------
/**
 * freeblock list which is a double link list keeps track of the free blocks in
 * memeory pool.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 7, 2014
 */
public class Freeblock
{

    // data files
    /**
     * head node
     */
    private Node head;
    /**
     * tail node
     */
    private Node tail;


    // ----------------------------------------------------------
    /**
     * Create a new Freeblock object and initialize. insert the node with
     * position = 0, free block size = blockSize.
     *
     * @param blockSize
     *            the initial blockSize
     */
    public Freeblock(int blockSize)
    {
        head = new Node();
        tail = new Node();

        Node first = new Node(blockSize, 0);

        first.setPrev(head);
        first.setNext(tail);

        head.setNext(first);
        tail.setPrev(first);
    }


    // ----------------------------------------------------------
    /**
     * use best fit method to find where to insert the record.
     *
     * @param recordSize
     *            the size of the record
     * @return best fit node
     */
    public Node bestFit(int recordSize)
    {
        Node best = null;
        Node temp = head;
        int fitBlock = Integer.MAX_VALUE;
        while ((temp = temp.next()) != tail)
        {
            if (recordSize <= temp.getBlockSize()
                && temp.getBlockSize() < fitBlock)
            {
                best = temp;
                fitBlock = best.getBlockSize();
            }
        }
        return best;
    }


    // ----------------------------------------------------------
    /**
     * find the position of record.
     *
     * @param recordSize
     * @return position of record
     */
    public int findPosition(int recordSize)
    {
        Node best = bestFit(recordSize);
        int position;
        if (best == null)
        {
            position = -1;
        }
        else
        {
            position = best.getPosition();
        }
        return position;
    }


    // ----------------------------------------------------------
    /**
     * when a best fit block is found, this block need to change the position
     * and size info. If best is not found, return position = -1
     *
     * @param recordSize
     *            the size of the block
     * @return position of the best fit block
     */
    public int updateList(int recordSize)
    {
        Node best = bestFit(recordSize);
        int position;
        if (best == null)
        {
            position = -1;
        }
        else
        {
            if (recordSize == best.getBlockSize())
            {
                remove(best);
            }
            else
            {
                // update the blockSize and position
                best.setBlockSize(best.getBlockSize() - recordSize);
                best.setPosition(best.getPosition() + recordSize);
            }
            position = best.getPosition();

        }
        return position;
    }


    // ----------------------------------------------------------
    /**
     * append the node to the list.
     *
     * @param newNode needs to be appended
     */
    public void append(Node newNode)
    {
        tail.prev().setNext(newNode);
        newNode.setPrev(tail.prev());
        tail.setPrev(newNode);
        newNode.setNext(tail);
    }


    // ----------------------------------------------------------
    /**
     * insert a free block, which has position and size reference.
     *
     * @param newNode to be inserted
     */
    public void insert(Node newNode)
    {
        Node temp = head;
        // check whether is a special case: append the node.
        boolean append = true;
        while (temp.next() != tail)
        {
            if (temp.next().getPosition() > newNode.getPosition())
            {
                // the node need to be inserted in the middle of the list.
                append = false;
                temp = temp.next();
                break;
            }
            temp = temp.next();
        }

        if (append) // append is needed
        {

            append(newNode);
        }
        else
        {
            temp.prev().setNext(newNode);
            newNode.setPrev(temp.prev());
            temp.setPrev(newNode);
            newNode.setNext(temp);
        }

        if (isMergeNeed(newNode))
        {
            merge(newNode);
        }

    }


    // ----------------------------------------------------------
    /**
     * remove the node from list.
     *
     * @param remove
     *            the node that need to be removed
     */
    public void remove(Node remove)
    {
        //
        remove.prev().setNext(remove.next());
        remove.next().setPrev(remove.prev());
    }


    // ----------------------------------------------------------
    /**
     * merge the adjacent node when merge is needed.
     *
     * @param node
     *            that will need to right || left
     */
    public void merge(Node node)
    {
        if (node.getPosition() == node.prev().getPosition()
            + node.prev().getBlockSize()
            && node.next().getPosition() != node.getPosition()
                + node.getBlockSize())
        {
            // only merge left
            //System.out.println("merge left------");
            node.prev().setBlockSize(
                node.prev().getBlockSize() + node.getBlockSize());
            remove(node);
        }
        if (node.next().getPosition() == node.getPosition()
            + node.getBlockSize()
            && node.getPosition() != node.prev().getPosition()
                + node.prev().getBlockSize())
        {
            // only merge right
            //System.out.println("merge left------");
            node.setBlockSize(node.next().getBlockSize() + node.getBlockSize());
            remove(node.next());
        }
        if (node.next().getPosition() == node.getPosition()
            + node.getBlockSize()
            && node.getPosition() == node.prev().getPosition()
                + node.prev().getBlockSize())
        {
            // merge both
            //System.out.println("merge both------");
            node.prev().setBlockSize(
                node.prev().getBlockSize() + node.getBlockSize()
                    + node.next().getBlockSize());
            remove(node.next());
            remove(node);
        }

    }


    // ----------------------------------------------------------
    /**
     * the boolean method to check whether merge is needed.
     *
     * @param node
     *            that needs to check whether it is needed to merge
     * @return merge or not in boolean type
     */
    public boolean isMergeNeed(Node node)
    {
        boolean merge;
        if (node.getPosition() != node.prev().getPosition()
            + node.prev().getBlockSize()
            && node.next().getPosition() != node.getPosition()
                + node.getBlockSize())
        {
            merge = false;
        }
        else
        {
            merge = true;
        }

        return merge;
    }


    // ----------------------------------------------------------
    /**
     * print out the list information.
     * @return toString list
     */
    public String toString()
    {
        Node temp = head;
        String list = "";
        if(temp.next().equals(tail))
        {
            return list;
        }
        else {
        while (!temp.next().next().equals(tail))
        {
            list = list + temp.next().toString() + " -> ";
            temp = temp.next();
        }
        return list + temp.next().toString();
        }
    }
}
