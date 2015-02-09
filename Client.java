import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Parser the command file.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 12, 2014
 */
public class Client
{
    /**
     * the hash table to store song name.
     */
    private Hashtable  songTable;
    /**
     * the hash table to store artist name.
     */
    private Hashtable  artistTable;

    /**
     * memory manager to handle with the memory pool and freeblock list.
     */
    private MemManager manager;


    /**
     * Create a new Client object.
     *
     * @param manager
     *            the memory manager that handles memory pool and freeblock list
     * @param tableSize
     *            the initial table size
     * @param fileName
     *            the command file that needs to read
     * @throws FileNotFoundException
     */
    public Client(MemManager manager, int tableSize, String fileName)
        throws FileNotFoundException
    {
        songTable = new Hashtable(tableSize);
        artistTable = new Hashtable(tableSize);
        this.manager = manager;

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(new File(fileName));
        String command;
        while (scanner.hasNext())
        {
            command = scanner.nextLine();

            if (command.startsWith("insert"))
            {
                String[] content = command.split("insert ");
                content = content[1].split("<SEP>");
                insertArtist(content[0]);
                insertSong(content[1]);
            }
            else if (command.startsWith("remove song"))
            {
                String[] songName = command.split("remove song ");
                removeSong(songName[1]);
            }
            else if (command.startsWith("remove artist"))
            {
                String[] artistName = command.split("remove artist ");
                removeArtist(artistName[1]);
            }
            else if (command.startsWith("print artist"))
            {
                System.out.println(artistTable.print(manager) + "artists: "
                    + artistTable.usedSize());
            }
            else if (command.startsWith("print song"))
            {
                System.out.println(songTable.print(manager) + "songs: "
                    + songTable.usedSize());
            }
            else if (command.startsWith("print blocks"))
            {

                manager.dump();
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * insert song name into songTable and memory pool.
     *
     * @param song
     *            that need to be inserted
     */
    public void insertSong(String song)
    {
        // insert into memory pool and get the handle
        Handle songHandle = songTable.getHandle(song);
        // insert into hash table
        if (songHandle != null)
        {
            System.out.println("|" + song
                + "| duplicates a record already in the song database.");
        }
        else
        {
            byte[] songByte = stringToByte(song);
            if (songTable.rehashNeed(song))
            {
                System.out.println("Song hash table size doubled.");
            }
            Handle newHandle = manager.insert(songByte, songByte.length);
            songTable.insert(song, newHandle);
            System.out.println("|" + song + "| is added to the song database.");
        }
    }


    // ----------------------------------------------------------
    /**
     * insert artist name into songTable and memory pool.
     *
     * @param artist
     *            to be inserted
     */
    public void insertArtist(String artist)
    {
        // check exist
        Handle artistHandle = artistTable.getHandle(artist);
        // insert into hash table
        if (artistHandle != null)
        {
            System.out.println("|" + artist
                + "| duplicates a record already in the artist database.");
        }
        else
        {

            byte[] artistByte = stringToByte(artist);
            if (artistTable.rehashNeed(artist))
            {
                System.out.println("Artist hash table size doubled.");
            }
            Handle newHandle = manager.insert(artistByte, artistByte.length);
            artistTable.insert(artist, newHandle);
            System.out.println("|" + artist
                + "| is added to the artist database.");
        }
    }


    // ----------------------------------------------------------
    /**
     * remove song name from hash table.
     *
     * @param song
     *            name
     */
    public void removeSong(String song)
    {
        Handle songHandle = songTable.getHandle(song);
        if (songHandle == null)
        {
            System.out.println("|" + song
                + "| does not exist in the song database.");
        }
        else
        {
            songTable.remove(song);
            manager.remove(songHandle);
            System.out.println("|" + song
                + "| is removed from the song database.");
        }
    }


    // ----------------------------------------------------------
    /**
     * remove artist name from hash table.
     *
     * @param artist
     *            name
     */
    public void removeArtist(String artist)
    {
        Handle artistHandle = artistTable.getHandle(artist);
        if (artistHandle == null)
        {
            System.out.println("|" + artist + "|"
                + " does not exist in the artist database.");
        }
        else
        {
            artistTable.remove(artist);
            manager.remove(artistHandle);
            System.out.println("|" + artist
                + "| is removed from the artist database.");
        }
    }


    // ----------------------------------------------------------
    /**
     * string to byte method.
     *
     * @param name
     *            needs to convert to String
     * @return data arry
     */
    public static byte[] stringToByte(String name)
    {
        byte[] data = new byte[name.length()];

        try
        {
            ByteBuffer.wrap(data).put(name.getBytes("US-ASCII"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return data;
    }

}
