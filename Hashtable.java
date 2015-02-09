// -------------------------------------------------------------------------
/**
 * hash table class stores the song and artist information.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 9, 2014
 */
public class Hashtable
{
    private Entry[] table;
    /**
     * Song name or Artist name.
     */
    /**
     * the hash table size.
     */
    private int             tableSize;
    /**
     * the number of slots that are used.
     */
    private int             usedSize;


    // ----------------------------------------------------------
    /**
     * Create a new Hashtable object.
     *
     * @param tableSize
     *            the size of table
     */
    public Hashtable(int tableSize)
    {
        table = new Entry[tableSize];
        this.tableSize = tableSize;
        usedSize = 0;
    }


    // ----------------------------------------------------------
    /**
     * hash function to get hashcode.
     *
     * @param str
     *            the string to hash
     * @return the hashCode
     */
    public long hash(String str)
    {
        int intLength = str.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++)
        {
            char[] c = str.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++)
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = str.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++)
        {
            sum += c[k] * mult;
            mult *= 256;
        }

        return Math.abs(sum);
    }


    /**
     * find the target key or first empty slot using quatratic probe.
     *
     * @param key
     *            to find the index
     * @return index of the target or the first empty
     */
    public int findIndex(long key)
    {
        int home;
        int probeNum = 0;
        int index = (int)(key % tableSize);
        home = index;
        while (table[index] != null && !table[index].getTomb())
        {
            probeNum++;
            index = (home + probeNum * probeNum) % tableSize;
        }
        return index;
    }


    // ----------------------------------------------------------
    /**
     * check whether rehash is needed.
     *
     * @param string
     *            to check
     * @return true if needs, otherwise false
     */
    public Boolean rehashNeed(String string)
    {
        long hashKey = hash(string);
        boolean foundKey = search(hashKey);
        boolean need = false;
        if (!foundKey && (usedSize + 1 > tableSize / 2))
        {
            need = true;
        }
        return need;
    }


    // ----------------------------------------------------------
    /**
     * insert the key and value into the hash table and return true if insertion
     * is successful else false.
     *
     * @param key
     *            String
     * @param value
     *            Handle
     * @return !foundkey when key is found return false, otherwise return true
     */
    public boolean insert(String key, Handle value)
    {
        long hashKey = hash(key);
        boolean foundKey = search(hashKey);
        if (!foundKey)
        {
            if (usedSize + 1 > tableSize / 2)
            {

                rehash();
            }
            int index = findIndex(hashKey);
            Entry pair = new Entry(hashKey, value);
            table[index] = pair;
            usedSize++;
        }
        return !foundKey;
    }


    // ----------------------------------------------------------
    /**
     * remove entry and replace by tombstone.
     *
     * @param key
     *            to be removed
     */
    public void remove(String key)
    {
        long hashKey = hash(key);
        // search the key
        int home;
        int probeNum = 0;
        int index = (int)(hashKey % tableSize);
        home = index;
        while (table[index].getKey() != hashKey)
        {
            probeNum++;
            index = (home + probeNum * probeNum) % tableSize;
        }

        // key find, remove the value, replace by a tombstone
        Entry tomb = new Entry(true);
        table[index] = tomb;
        usedSize--;
    }


    // ----------------------------------------------------------
    /**
     * get the handle of the key.
     *
     * @param key
     *            of the handle
     * @return null or handle related to key
     */
    public Handle getHandle(String key)
    {
        long hashKey = hash(key);
        boolean found = search(hashKey);

        if (!found)
        {
            return null;
        }
        else
        {
            int index = get(hashKey);
            return table[index].getValue();
        }
    }


    // ----------------------------------------------------------
    /**
     * search the duplicates while insertion.
     *
     * @param key
     *            to search in the table
     * @return true if found, otherwise false
     */
    public boolean search(long key)
    {

        boolean found;
        int home;
        int probeNum = 0;
        int index = (int)(key % tableSize);
        home = index;
        while (table[index] != null && table[index].getKey() != key)
        {
            probeNum++;
            index = (home + probeNum * probeNum) % tableSize;
        }

        if (table[index] == null)
        {
            found = false;
        }
        else
        {
            found = true;
        }

        return found;
    }


    // ----------------------------------------------------------
    /**
     * get the index of the key.
     *
     * @param key
     *            to find the index
     * @return index of the key
     */
    public int get(long key)
    {
        int home;
        int probeNum = 0;
        int index = (int)(key % tableSize);
        home = index;
        while (table[index].getKey() != key)
        {
            probeNum++;
            index = (home + probeNum * probeNum) % tableSize;
        }
        return index;

    }


    // ----------------------------------------------------------
    /**
     * re-hash the hash table by extend the size * 2.
     */
    public void rehash()
    {
        int oldSize = tableSize;
        tableSize = tableSize * 2;
        Entry[] newTable = new Entry[tableSize];
        // traverse the old hash table
        for (int i = 0; i < oldSize; i++)
        {
            if (table[i] != null && !table[i].getTomb())
            {
                int probeNum = 0;
                int home;
                int index = (int)(table[i].getKey() % tableSize);
                home = index;
                while (newTable[index] != null)
                {
                    probeNum++;
                    index = (home + probeNum * probeNum) % tableSize;
                }

                newTable[index] = table[i];
            }
        }
        table = newTable;

    }


    // ----------------------------------------------------------
    /**
     * print out the hash table content.
     *
     * @param mem
     *            the memory manager to
     * @return str the content of the hash table;
     */
    public String print(MemManager mem)
    {

        String str = "";
        if (usedSize == 0)
        {
            str = "total ";
            return str;
        }
        else
        {
            for (int i = 0; i < tableSize; i++)
            {
                if (table[i] != null && !table[i].getTomb())
                {
                    str += "|" + mem.get(table[i].getValue()) + "| " + i + "\n";

                }
            }

            return str + "total ";
        }
    }


    // ----------------------------------------------------------
    /**
     * get usedSize of hash table.
     *
     * @return usedSize
     */
    public int usedSize()
    {
        return usedSize;
    }


    // -------------------------------------------------------------------------
    /**
     * inner class Entry store two data: key and value for hash table.
     */
    private class Entry
    {
        private long    key;
        private Handle  value;
        private boolean tomb;


        // ----------------------------------------------------------
        /**
         * create a object with key and value.
         * @param key of the entry
         * @param value of the value
         */
        public Entry(long key, Handle value)
        {
            this.key = key;
            this.value = value;
            tomb = false;
        }


        // ----------------------------------------------------------
        /**
         * create a object with tomb.
         * @param tomb of tombstone
         */
        public Entry(boolean tomb)
        {
            this.tomb = tomb;
        }


        // ----------------------------------------------------------
        /**
         * get key of the entry.
         *
         * @return key to get
         */
        public long getKey()
        {
            return key;
        }


        // ----------------------------------------------------------
        /**
         * get value of entry.
         * @return value the handle
         */
        public Handle getValue()
        {
            return value;
        }

        // ----------------------------------------------------------
        /**
         * get the boolean tomb.
         * @return tomb with is a boolean
         */
        public boolean getTomb()
        {
            return tomb;
        }

    }
}
