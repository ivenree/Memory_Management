

// -------------------------------------------------------------------------
/**
 * handle stores a position information of the record returned by memmanager.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 7, 2014
 */
public class Handle
{
    // data fields
    /**
     * the position that MemManager returned.
     */
    private int position;


    // ----------------------------------------------------------
    /**
     * Create a new Handle object with initial position.
     *
     * @param position
     */
    public Handle(int position)
    {
        this.position = position;
    }


    // ----------------------------------------------------------
    /**
     * get the position stored in handle.
     *
     * @return position stores in handle
     */
    public int getPosition()
    {
        return position;
    }


    // ----------------------------------------------------------
    /**
     * set new position for handle.
     *
     * @param newPosition
     *            that needs to be set
     */
    public void setPosition(int newPosition)
    {
        this.position = newPosition;
    }

}
