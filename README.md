
#Memory_Management
Java Project

You will write a memory management package for storing variable-length records in a large memory space. For background on this project, view the modules on sequential fit memory managers. The records that you will store for this project are artist names and track names from a subset of the Million Song database. This project will be the first in a series over the course of the semester that will gradually build up the necessary data structures for doing search and analysis on a large song database.

Your memory pool will consist of a large array of bytes. You will use a doubly linked list to keep track of the free blocks in the memory pool. This list will be referred to as the freeblock list. You will use the best fit rule for selecting which free block to use for a memory request. That is, the smallest free block in the linked list that is large enough to store the requested space will be used to service the request (if any such block exists). If not all space of this block is needed, then the remaining space will make up a new free block and be returned to the free list. If there is no free block large enough to service the request, then you will grow the memory pool, as explained below. 

Be sure to merge adjacent free blocks whenever a block is released. To do the merge, whenever a block is released it will be necessary to search through the freeblock list, looking for blocks that are adjacent to either the beginning or the end of the block being released. Do not consider the first and last memory positions of the memory pool to be adjacent. That is, the memory pool itself is not considered to be circular. 

Aside from the memory manager's memory pool and freeblock list, the other major data structure for your project will be two closed hash tables, one for accessing artist names and the other for accessing song titles. You will use the second string hash function described in the book, and you will use simple quadratic probing for your collision resolution method (the i'th probe step will be i^2 slots from the home slot). The key difference from what the book describes is that your hash tables must be extensible.That is, you will start with a hash table of a certain size (defined when the program starts). If the hash table exceeds 50% full, then you will replace the array with another that is twice the size, and rehash all of the records from the old array. For example, say that the hash table has 100 slots. Inserting 50 records is OK. When you try to insert the 51st record, you would first re-hash all of the original 50 records into a table of 200 slots. Likewise, if the hash tablestarted with 101 slots, you would also double it (to 202) just before inserting the 51st record. The hash table will actually store "handles" to the relevant data records that are currently stored in the memory pool. A handle is the value returned by the memory manager when a request is made to insert a new record into the memory pool. This handle is used to recover the record.


Invocation and I/O Files:

The program will be invoked from the command-line as:

java Memman {initial-hash-size} {block-size} {command-file}

The name of the program is memman. Parameter {initial-hash-size} is the initial size of the hash table (in terms of slots). Parameter {block-size} is the initial size of the memory pool (in bytes). Whenever the memory pool has insufficient space to insert the next request, it will be replaced by a new array that adds an additional {block-size} bytes. All data from the old array will be copied over to the new array, the freeblock list will be updated appropriately, and then the new string will be added.

Your program will read from text file {command-file} a series of commands, with one command per line. The program should terminate after reading the end of the file. The formats for the commands are as follows. The commands are free-format in that any number of spaces may come before, between, or after the command name and its parameters. All commands should generate a suitable output message (some have specific requirements defined below). All output should be written to standard output. Every command that is processed should generate some sort of output message to indicate whether the command was successful or not.


insert {artist-name}<SEP>{song-name}

Note that the characters <SEP> are literally a part of the string (this is how the raw data actually comes to us, and we are preserving this to minimize inconsistencies in later projects), and are used to separate the artist name from the song name. Check if {artist-name} appears in the artist hash table, and if it does not, add it to the memory pool. Check if {song-name} appears in the artist hash table, and if it does not, add it to the memory pool. You should print a message if the insert causes the hash table or memory pool to expand in size.


remove {artist|song} {name}

Remove the specfied artist or song name from the appropriate hash table and the memory pool. Report the outcome (whether the name appears, and whether it was successfully removed).


print {artist|song|blocks}

Depending on the parameter value, you will print out either a complete listing of the artists contained in the database, or the songs, or else the free block list for the memory manager. For artists or songs, simply move sequentially through the associated hash table, retrieving the strings and printing them in the order encountered (along with the slot number where it appears in the hash table). Then print the total number of artists or total number of songs. If the parameter is blocks, then print a listing of the freeblocks, in order of their occurrence in the freeblock list. For each block, print its start position and its length.


Design Considerations:
Your main design concern for this project will be how to construct the interface for the memory manager class. While you are not required to do it exactly this way, we recommend that your memory manager class include something equivalent to the following methods.


// Constructor. poolsize defines the size of the memory pool in bytes

MemManager(int poolsize);


// Insert a record and return its position handle. space contains the record to be inserted, of length size.

Handle insert(byte[] space, int size);



// Free a block at the position specified by the Handle. Merge adjacent free blocks.

void remove(Handle theHandle);


// Return the record with handle posHandle, up to size bytes, by copying it into space. Return the number of bytes actually copied into space.

int get(byte[] space, Handle theHandle, int size);


// Dump a printout of the freeblock list.

void dump();


Another design consideration is how to deal with the fact that the records are variable length. One option is to encode the length in the record's handle. An alternative is to store the record's length in the memory pool along with the record. Both implementations have advantages and disadvantages. We will adopt the second approach. The records stored in the memory pool must have the following format. The first two bytes will be the (unsigned) length of the record, in (encoded) characters. Thus, the total length of a record may not be more than 216 = 65; 536 characters or bytes. Following that will be the string itself.


End.

