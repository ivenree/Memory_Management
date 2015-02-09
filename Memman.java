import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

// -------------------------------------------------------------------------
/**
 * This is the main class.
 *
 * @author wenfeng ren (rwenfeng)
 * @version Sep 11, 2014
 */

public class Memman
{
    /**
     * the initial table size for hash table.
     */
    public static int    tableSize;
    /**
     * the initial memory pool size.
     */
    public static int    blockSize;
    /**
     * the name of the file which contains the commands we need to to parser.
     */
    public static String fileName;


    // ----------------------------------------------------------
    /**
     * main method.
     *
     * @param args
     *            input arguments
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void main(String[] args)
        throws IOException,
        IllegalArgumentException,
        IllegalAccessException,
        InvocationTargetException
    {

        if (args.length != 3)
        {
            System.out.println("invalid command, please follow: "
                + "java Memman{initial-hash-size}");
        }

        // get the argument from the command line
        tableSize = Integer.parseInt(args[0]);
        blockSize = Integer.parseInt(args[1]);
        fileName = args[2];

        // create memory manager object and initialize it with blockSize
        MemManager manager = new MemManager(blockSize);

        // create client object and initialize it
        @SuppressWarnings("unused")
        Client client = new Client(manager, tableSize, fileName);

    }

}
