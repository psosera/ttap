# Grin Compression

<p>
<center>
  <em>(Credit to Stuart Reges and Marty Stepp at the University of Washington and Stanford University whose Huffman Encoding assignment formed the basis for this homework!)</em>
</center>
</p>

A [Huffman Code](https://en.wikipedia.org/wiki/Huffman_coding) is a scheme for lossless compression of data.
Invented by David Huffman while he was a student at MIT, Huffman codes are a relatively simple and open compression technique used in a variety of commonly-used programs and file formats, *e.g*., various "zip" programs---gzip, pkzip, and bzip2---and image formats like jpegs and pngs.
For us, they serve as a real-world example of priority queues in action!
In this homework, we'll write a program to encode and decode files in the *.grin* format, a simple compressed file format designed for this assignment.
The core of a *.grin* file is an encoding of the file using Huffman's algorithm.

## Github Repository

* [psosera/grin-compression](https://github.com/psosera/grin-compression)

## Data Compression with Huffman Codes

The key insight behind compressing data whether it is text, music, images, or any other file on our system is that there are *patterns* in our data that we can discover and represent more concisely.
For now, let's constrain our view to text files to simplify the discussion, although our final program will work over any file type.
Recall that all data in our program are ultimately represented in *bits*, sequences of zeros and ones.
Characters are no different; as we know, we represent characters as a number indicating its *character value* as dictated by some standard, *e.g.*, the ASCII standard which assigns a value in the range 0-255 (a byte or 8 bits) to each character.

Consider the following simple text file:

~~~
a ab bza
~~~

The character values and the binary encoding for each character of this file are:

<table border="1">
<tr>
<td><strong>Character</strong></td><td><strong>Value</strong></td><td><strong>Binary Rep.</strong></td>
</tr>
<tr>
<td>'a'</td><td>97</td><td>01100001</td>
</tr>
<tr>
<td>' '</td><td>32</td><td>00100000</td>
</tr>
<tr>
<td>'b'</td><td>98</td><td>01100010</td>
</tr>
<tr>
<td>'z'</td><td>122</td><td>01111010</td>
</tr>
</table>

Note that the binary representation of the values is simply the character value in binary using 8 bits (as dictated by the ASCII standard).
The binary representation of this file is the concatenation of all the binary representations of the characters in sequence:

~~~
01100001 00100000 01100001 01100010 00100000 01100010 01111010 01100001
~~~

There are actually no spaces in this bit-level representation of the file—I added them to make it clear where each character began and ended.

We dedicate 8 bits to each character to cover the complete range of possible character values, but clearly the above example does not benefit from this as there are only four characters that are used!
Because of this, we could choose to represent the character much more concisely, *e.g.*, using the following *file-specific encoding* of the characters in the file:

<table border="1">
<tr>
<td><strong>Character</strong></td><td><strong>Huffman Code</strong></td>
</tr>
<tr>
<td>'a'</td><td>11</td>
</tr>
<tr>
<td>' '</td><td>00</td>
</tr>
<tr>
<td>'b'</td><td>10</td>
</tr>
<tr>
<td>'z'</td><td>010</td>
</tr>
<tr>
<td>eof</td><td>011</td>
</tr>
</table>

We introduce one extra special character, an *end-of-file* marker—eof—to tell us when the file ends.
Shorter codes are assigned to more frequently used characters, *e.g.*, `'a'` and space, and longer codes are assigned to less frequently used characters, *e.g.*, `'z'`.
With this representation of the characters, we obtain the following *compressed binary file* (spaces again inserted for readability):

~~~
11 00 11 10 00 10 010 11 011
~~~

Even with the additional eof character, note the savings in size.
We went from a file of $8 \times 8 = 64$ bits to a compressed file containing a payload of only $20$ bits!

The choice of these codes is not arbitrary—these are precisely the codes that the Huffman tree produces for this particular text file!
In particular, these codes have an important property: no code is a *prefix* of another code.
Because of this, recovering the original data given the mapping from characters-to-Huffman codes is easy: we simply walk the compressed binary file from left-to-right and once we encounter a valid code, we replace it with its corresponding character.

## Huffman Trees

The key data structures in deriving these character codes is the *Huffman tree* which is built using a *priority queue*.
Recall that a queue is a first-in-first-out (FIFO) sequential data structure.
A priority queue is a queue that uses the ordering of the carrier type, e.g., numeric ordering for integers, instead of insertion order to determine which element to dequeue first.

As a concrete example, let's derive the Huffman codes for the example file in the introduction:

~~~
a ab bza
~~~

From this file, we obtain a map of *character frequencies* dictating how many times each character occurs in the file.
On top of this, we introduce the *end-of-file* character *eof* that marks when the file ends---it always occurs once in the file.

~~~
'a' -> 3
' ' -> 2
'b' -> 2
'z' -> 1
eof -> 1
~~~

With this map, we can construct a *Huffman tree* which encodes these frequencies in a tree-like structure.
A Huffman tree is a binary tree with two types of nodes:

1. *Leaves* that contain a character from the file and its frequency.
2. *Internal nodes* that contain two children and a frequency that is the sum of the frequencies of its children.

For example, the following leaf:

~~~
['a', 3]
~~~

Represents the character 'a' which occurs 3 times in the file.
The following internal node:

~~~
       [5]
      /   \
[' ', 2] ['a', 3]
~~~

Contains two leaves that represent the frequencies of ' ' (space) and 'a' in the file.
The internal node *summarizes* their frequencies by containing their sum: $2 + 3 = 5$.

To construct a Huffman Tree, we use a priority queue that contains nodes from this tree.
To insert these nodes into a priority queue, we define the *ordering* on the nodes to be the ordering of their frequencies.
For example, a node with frequency 2 is "less than" a node with frequency 5.

We begin by building a leaf node for each of the characters in our character frequency map and placing those nodes into the priority queue:

~~~
(top of queue) [z, 1] [eof, 1] [' ', 2] ['b', 2] ['a', 3]
~~~

With this set up, the algorithm to build a Huffman tree can be concisely described as:

~~~
while there are at least two nodes in the priority queue:
    + create a new internal node using the top two nodes of the priority queue as its children
    + add this internal node back into the priority queue
~~~

After the end of the algorithm, the priority queue contains a single node which is the root of the Huffman Tree.

Executing this algorithm on the example priority queue above, we first take the nodes for the eof and 'z' characters and combine them into an internal node:

~~~
        [2]
       /   \
['z', 1]  [eof, 1]
~~~

Note that the frequency of the internal node is two, the sum of the frequencies of its children.
We then add this internal node back into the priority queue:

~~~
[' ', 2]   [2]   ['b', 2]   ['a', 3]
          /   \
    ['z', 1] [eof, 1]
~~~

Note that because the new internal node has the same frequency as `'b'`, it may appear either before or after `'b'`'s node.
This is because Java's [PriorityQueue](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html) implementation does not guarantee that the oldest element is pushed to the front of the queue.
This behavior is fine as the ordering with respect to nodes of the same frequency does not matter for the purposes of our tree construction algorithm.
Our choice above reflects what Java's `PriorityQueue` class produces when utilized in the reference implementation of the Grin program.

We repeat this process until we are out of nodes to combine.
Step-by-step, this process looks as follows:

~~~
(2)   ['b', 2]   ['a', 3]   [4]
                           /   \
                     [' ', 2]  [2]
                              /   \
                      ['z', 1']   [eof, 1]

(3)    [4]                     [5]
      /   \                   /   \
[' ', 2]  [2]          ['b', 2]   ['a', 3]
         /   \
 ['z', 1']   [eof, 1]

(4)                    [9]
                      /   \
               -------     ------
              /                  \
            [4]                  [5]
           /   \                /   \
     [' ', 2]  [2]       ['b', 2]   ['a', 3]
              /   \
      ['z', 1]    [eof, 1]
~~~

The final Huffman tree encodes the Huffman codes for each of the characters present in the file.
To derive these codes, we simply walk from root to leaf for each character and record the *path* as a binary number where going left corresponds to 0 and right corresponds to 1.
The codes for our characters are therefore:

<table border="1">
<tr>
<td><strong>Character</strong></td><td><strong>Huffman Code</strong></td>
</tr>
<tr>
<td>'a'</td><td>11</td>
</tr>
<tr>
<td>'&nbsp;'</td><td>00</td>
</tr>
<tr>
<td>'b'</td><td>10</td>
</tr>
<tr>
<td>'z'</td><td>010</td>
</tr>
<tr>
<td>eof</td><td>011</td>
</tr>
</table>

Note that this isn't the only possible set of Huffman codes for this file.
Depending on how the priority queue resolves ordering between nodes with the same frequencies, we may arrive at a different, yet equally-efficient encoding.

### Encoding and Decoding with Huffman Trees

With our Huffman tree we can either *encode* a file whose letter frequencies were used to generate the tree or *decode* a file that was encoded with this Huffman tree.
The encoding process is straightforward: once we have recovered these Huffman codes, we can simply walk a file, character-by-character, and replace the character with its code (in binary).
To decode a file, we repeatedly walk the tree from root to leaf, using the bits found in the encoded file as a guide.
Whenever we hit a leaf, we output its correspond character, terminating the process once we output the end-of-file character.

Using the tree we derived above to decode this binary file:

~~~
11001110001001011011
~~~

1. We then read in 1 (right) and 1 (right) ending on a leaf that contains 'a'; we output 'a'.
   We reset our position back to the root of the tree and continue processing the binary file.
2. We read 0 (left) and 0 (left) and output a ' '.
3. We read 1 (right) and 1 (right) and output a 'a'.
4. We read 1 (right) and 0 (left) and output a 'b'.
5. We read 0 (left) and 0 (left) and output a ' '.
6. We read 1 (right) and 0 (left) and output a 'b'.
7. We read 0 (left), 1 (right), and 0 (left) and output a 'z'.
8. We read 1 (right) and 1 (right) and output a 'a'.
9. We read 0 (left), 1 (right), and 1 (right) and terminate because we've encountered the end-of-file character.

The end result is the decoded string:

~~~
a ab bza
~~~

which is what we started with!

### The HuffmanTree Class

Your first---and most important task---is to write a class called `HuffmanTree` that captures the above process.
Your class should have the following constructor and methods:

* `HuffmanTree(Map<Short, Integer> m)`: construct a `HuffmanTree` from the given frequency map of 9-bit values.
  The EOF character has not yet been added to this map so you will need to add it in this constructor (with a frequency of 1).
  Because we assume that we are encoding 8-bit chunks (values in the range 0--255), we'll use the value 256 to represent the EOF character, requiring that we need at least 9 bits to encode all of these values.
  The smallest such integral type that can handle this capacity is a `short` (16 bits).
* `HuffmanTree(BitInputStream in)`: constructs a `HuffmanTree` from the given file encoded in a serialized format (see "Serializing Huffman Trees below).
* `void serialize(BitOutputStream out)`: writes the `HuffmanTree` to the given file as a stream of bits in a serialized format (see "Serializing Huffman Trees" below).
* `void encode(BitInputStream in, BitOutputStream out)`: Encodes the file given as a stream of bits into a compressed format using this Huffman tree.
  The encoded values are written, bit-by-bit to the given `BitOuputStream`.
* `void decode(BitInputStream in, BitOutputStream out)`: Decodes a stream of huffman codes from a file given as a stream of bits into their uncompressed form, saving the results to the given output stream.
  Note that the EOF character is not written to `out` because it is not a valid 8-bit chunk (it is 9 bits).

Bit-level input and output is quite finicky in Java.
To help with this, we have provided two classes for you to use, `BitInputStream` and `BitOuputStream`, found in the project's sources.
The relevant constructor and methods of the `BitInputStream` class are:

* `BitInputStream(String filename)`: constructs a new `BitInputStream` pointed at the given file
* `int readBit()`: reads a single bit of data from the stream, returning -1 if the stream is empty
* `int readBits(int n)`: reads in `n` bits of data (from 0 to 32 bits), returning -1 if the stream runs out of bits in the process

Note that the return types of `readBit` and `readBits` are integers even though an integer is larger than a single bit or byte (8 bits).
In particular, you should immediately cast the result of `readBits` to a primitive of the appropriate size.

The relevant constructor and methods of the `BitOutputStream` class are:

* `BitOutputStream(String filename)`: constructs a new `BitOutputStream` pointed at the given file.
* `int writeBit(int bit)`: writes a single bit of data from the stream (0 or 1)
* `int writeBits(int bits, int n)`: writes `n` bits of data to the stream (from 0 to 32 bits)

Both constructors of `BitInputStream` and `BitOutputStream` throw `IOExceptions` that you are free to handle using a `try-catch` block or a `throws` declaration on the relevant constructor and method signatures.

When designing the `HuffmanTree`, we identified two different types of nodes---leaf and interior nodes.
Rather than representing them with two separate classes related by an interface, we will instead single class, `Node`, to represent them.
In your `Node` class, add appropriate fields to support both a leaf and interior node.
Depending on whether the `Node` is a leaf or an interior node, you'll selectively ignore some of the fields, *e.g.*, a leaf does not have children and an interior node does not have a byte value associated with it.

### Serializing Huffman Tree

In order to decompress our `.grin` files, we store the Huffman tree used to compress the file within the `.grin` file itself.
The tree is stored in a serialized, compact format.
Recall that we can serialize a binary tree, *i.e.*, write it to a sequential structure such as a file, by performing a *pre-order traversal* of the tree (similarly to the decision tree we built in the Learning Genie homework).
When performing such a pre-order traversal, we serialize the tree as follows:

* If the node is a leaf, we write a `0` bit and then 9 bits corresponding to the byte value stored at the leaf.
* If the node is an interior node, we write a `1` bit and then recursively write the left and right children of this node.

For example, consider our Huffman tree from before:

~~~
                    [9]
                   /   \
            -------     ------
           /                  \
         [4]                  [5]
        /   \                /   \
  [' ', 2]  [2]       ['b', 2]   ['a', 3]
           /   \
   ['z', 1]    [eof, 1]
~~~

An in-order traversal of the tree yields:

* The `[9]` interior node.
* The `[4]` interior node.
* The `[' ', 2]` leaf.
* The `[2]` interior node.
* The `['z', 1]` leaf.
* The `[eof, 1]` leaf.
* The `[5]` interior node.
* The `['b', 2]` leaf.
* The `['a', 3]` leaf.

Keeping in mind that each of our byte values are represented by the following series of 9-bit values:

* `'a'`: `001100001`
* `' '`: `000100000`
* `'b'`: `001100010`
* `'z'`: `001111010`
* `eof`: `100000000`

Our serialization scheme yields the following compressed representation of our tree.
Note that I've formatted the bit string so that it visually maps onto our in-order traversal, but in reality, this would be a single sequence of 54 bits:

~~~
1
1
0 000100000
1
0 001111010
0 100000000
1
0 001100010
0 001100001
~~~

De-serialization of the tree from the `.grin` file simply reverses this process.
We read and interpret the bits of the serialized tree and reconstruct the tree, emulating a pre-order traversal in the process.

## Decoding Grins

With the core engine of our compression program taken care of, we will now proceed on completing the plumbing required to decode a `.grin` file.
A `.grin` file is a text file that has been compressed using the Huffman encoding scheme.
Note that to decompress a file, we require its associated Huffman tree to perform the decoding.
Therefore, in addition to the compressed data, a `.grin` file also contains the information necessary to reconstruct the Huffman tree for that file.

A `.grin` file has the following *binary file format*

~~~
[magic Number (32 bits)][serialized tree][payload]
|                                       |
-----------------------------------------
                     |
                   header
~~~

The portion of the file corresponding to the magic number and the serialized tree is called the *header* of the file.
The *payload* is the actual compressed binary data.

The magic number or *file signature* is an integer (recall that integers are 32 bits) that denotes the type of data that this file contains.
There is no standard behind this---it is simply a quick and dirty way for a program to check to see if a given file is in the format of interest.
Lists of common file signatures can be found on [Wikipedia](https://en.wikipedia.org/wiki/List_of_file_signatures).
For the .grin file format, the magic number is a single integer, **0x736**, which is the (hexadecimal) encoding of **1846**, the year that Grinnell College was founded.
The other part of the header is dedicated to a serialized version of the Huffman tree that we can use to decode the payload.

## Encoding Grins

Encoding `.grin` files proceeds similarly to decoding.
The primary difference is that we must derive the frequency map for the file that we are encoding and then use that to construct our Huffman tree.
Up to this point, we have only considered encoding ASCII characters---8-bit values---but what about arbitrary data?
To handle arbitrary data, we simply break up the file, regardless of its format, into 8-bit chunks and compress them like normal!
The `BitInputStream` class allows us to do this with its `readBits(n)` method.


## The Grin Program

We tie everything into a single program, `Grin`, that acts as a driver to the encoder and decoder portions of your program.
`Grin` is a command-line program with the following syntax:

~~~
java Grin <encode|decode> <infile> <outfile>
~~~

When running `Grin`, you can specify whether the program should encode or decode a file (matching exactly the strings `"encode"` or `"decode"`).
If grin is given invalid arguments, *e.g.*, the user doesn't specify either encode or decode as the first argument, then the program should report an error and exit.

The decode path of your driver should call out to the following static helper method of the `Grin` class:

* `void decode(String infile, String outfile)`: decodes the .grin file denoted by `infile` and writes the output to the `.grin` file denoted by `outfile`.

Decode should perform the following operations in sequence:

1. Open a `BitInputStream` to the `infile`
2. Open a `BitOutputStream` to the `outfile`
2. Parse the header of the `.grin` file.
4. Constructs a `HuffmanTree` from the serialized version of the tree.
5. Decodes the payload using the Huffman Tree and the remainder of the stream, writing the results to the `outfile`

If the `infile` is not a valid `.grin` file (*i.e.*, the magic number is not correct), then decode should throw an `IllegalArgumentException`.

The encoding path of your driver will require the following static helper method:

* `Map<Short, Integer> createFrequencyMap(String file)`: creates a mapping from 8-bit sequences to number-of-occurrences of those sequences in the given file.
  To do this, read the `file` using a `BitInputStream`, consuming 8 bits at a time.

Using this method, create the following helper method to encode a `.grin` file:

* `void encode(String infile, String outfile)`: encodes the given file denoted by `infile` and writes the output to the `.grin` file denoted by `outfile`.

Encode should perform the following operations in sequence:

1. Creates a frequency map of the input file.
2. Opens a `BitInputStream` to the input file and a `BitOutputStream` to the output file.
4. Constructs a `HuffmanTree` from the frequency map.
3. Writes the header information to the output file---the magic number and the serialized tree.
5. Encodes the input file using the Huffman tree, writing the encoded file to the output file.

While the process of encoding and decoding has many steps, most of them are implemented in your `HuffmanTree` class.
On top of using the `HuffmanTree` class, you should write additional (static) helper functions within the `Grin` class to help factor out your code according to this outline.


## Development

To exercise your Grin program, we have provided some ssample files and their encoded `.grin` equivalents in the `tests/` directory of the starter project:

* The worked example from the beginning of the assignment: huffman-example.txt, huffman-example.grin
* The Wikipedia article on Huffman Coding: wikipedia-huffman-coding.txt, wikipedia-huffman-coding.grin
* War and Peace by Leo Tolstoy (from Project Gutenberg): pg2600.txt, pg2600.grin

I highly recommend to first implement the *decoding* path in your program.
This mean implementing the portions of the `HuffmanTree` and `Grin` classes that are dedicated to decoding `.grin` files.
You can then test your program on the provided `.grin` files above.

From there, you can implement your encoder and then test your entire project by encoding the text files to `.grin` files, decoding them back to the text files, and finally observing that you get the original file back.
This process is called *roundtripping* and ensures that your program's two pathways---encoding and decoding---are consistent with each other.

To check the results of your encoding and decoding process, employ the following utilities:

* Use the Unix `diff` utility to check that the original file is identical to the file produced after decoding the encoded file (*i.e.*, *round-tripping* works).
* Use the Unix `xxd` utility with the `-b` flag to dump a file to its binary representation.
  This allows you to check that the encoded file is laid out exactly as you expect it to.

Here some additional notes to aid you in development:

* You should not have to perform direct bit-level manipulation in this program (*i.e.*, use the shift or logical bit operators).
  All your bit operations should be performed through `BitInputStream` and `BitOutputStream`.
* Most of the work is building the Huffman Tree!
  Don't move onto the encoder and decoder until you are certain that you can construct a Huffman Tree correctly and use it to encode and decode files.
* Close your streams after you are done!
  In particular, make sure to explicitly close your `BitOutputStream` as you are done with it so that any remaining bits that have not yet been written to the file are indeed written.
