# Applying Integer Maps

In this lab, we'll apply integer maps that count frequency of objects towards two different tasks: gathering statistics about text and sorting.
For this lab, write your code in a fresh Java file `IntegerMaps.java` and submit it to Gradescope when you are done.

## Part 1: Counting Letters

First, write a static method `void reportCounts(String path)` that prints to stdout the occurrences of each of the 26 English letters found in the text file found at `path`, one letter and its count per line, *e.g.* `a: 205807`.
You should consider the text in a *case-insensitive manner* and ignore any characters that are not English letters.
To maintain these counts, you should use an integer map.

(_Hint_: How can you *map* the English alphabet into the indices of this array?)

Try out your method on several books, e.g., books found on Project Gutenberg such as _War and Peace_ by Leo Tolstoy:

* [http://www.gutenberg.org/ebooks/2600](http://www.gutenberg.org/ebooks/2600)

Observe that the [frequency of letters in English text is known](https://en.wikipedia.org/wiki/Letter_frequency).
List each book that you use as a comment in your source file (with the URL you used to download the book) and in a sentence or two, describe whether the text is consistent with the known frequency of letters.
If it isn't, feel free to comment on why you think this is the case.

## Part 2: Counting Characters

Your solution to part 1 is serviceable for the English alphabet, but does it scale up to handle arbitrary character sets?
Write a static method `int countChars(String path)` that reports the number of unique characters found in the text file located at `path`.
In addition, you should print out each character and its corresponding character value.
Because there will be many unique characters, you can print out all these pairs on a single line, separated by spaces.
For this exercise, you may use the [Set](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/Set.html) interface and the [TreeSet](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/TreeSet.html) class from the standard library.
Unlike part 1, you should consider letters in a case-sensitive manner.

Based on your findings, can you apply the technique you developed in part 1 to count the occurrences of all these characters?
Try applying `countChars` to another book, *e.g.*, Plato's *Republic*,

* [https://www.gutenberg.org/ebooks/39493](https://www.gutenberg.org/ebooks/39493)

Will your technique work with this book?

## Part 3: The Letter Counter

One way we can resolve the difficulties you discovered in part 2 is to *mod* the character value by the length of the backing array.
That way, every element has a proper index in the array.
However, now we must account for *collisions* that occur between characters that map onto the same index.

Review the reading for this week and write a class called `LetterCounter` that maps arbitrary characters to integers.
You can create `LetterCounter` as a (non-`public`) class within `IntegerMaps.java`.
You class should support the following operations:

* `LetterCounter()`: creates a new, empty `LetterCounter` map.
* `boolean hasKey(char ch)`: returns true iff this map contains an entry for `ch`.
* `void put(char ch, int v)`: associates `ch` with `v` in the map, overwriting the prior entry for `ch` if it exists.
* `int get(char ch)`: returns the value for `ch` in the map if it exists; throws an `IllegalArgumentException` if `ch` does not have an entry in the map.

To resolve collisions, you should either use the *probing* or *chaining* methods described in the reading.
You may use the `Pair` class we developed last week along with other data structures from the standard library, in particular, lists.
You are, of course, not allowed to use maps from the standard library in your solution!

Use `LetterCounter` to rewrite `reportCounts` to report the counts of _all_ characters found in the texts you tried in part 1.

## Additional Problem: Linear Sorts

In the previous part, you maintained a mapping between characters and their frequency for the purposes of analyzing texts.
Now, we'll apply our "fast" integer maps for a different purpose: sorting!
Recall that the time of our best sorting algorithms (mergesort and quicksort) were $\mathcal{O}(n)$.
Can we do better?
It turns out we provably cannot do better with _comparison-based sorts_ where our primary operation is comparing two elements relative to some ordering.

Let's explore how integers maps can help us perform a sort without comparing elements, leading to a more efficient sorting algorithm (albeit with _plenty_ of caveats).

### Part A: Sorting from Frequencies

First, imagine that you are given an array of non-negative integers in the range 0 through 99, but you don't know the contents.
However, you _are_ also given a mapping (implemented as an integer map) between the possible integers in the array and their frequencies.
For each of the following sample frequency mappings A–C, sort the associated array (really, "give" the associated array, but sorted):

| Number | A  | B  | C  |
| ------ | -- | -- | -- |
| 0      | 1  | 3  | 0  |
| 1      | 0  | 0  | 2  |
| 2      | 0  | 2  | 0  |
| 3      | 1  | 5  | 0  |
| 4      | 0  | 0  | 2  |
| 5      | 0  | 0  | 0  |
| 6      | 1  | 0  | 0  |
| 7      | 1  | 0  | 2  |
| 8      | 1  | 2  | 0  |
| 9      | 0  | 1  | 2  |


From these examples, give an algorithm for sorting an array given that you have a frequency map of its elements.

### Part B: Counting Sort

Write a static function `void countingSort(int range, int[] arr)` that takes two arguments, an array `arr` whose elements range from `0` to `range`, exclusive, and sorts the elements of `arr`.

(_Hint_: what is missing from your algorithm your derived in the previous part?)

### Part C: Analysis

Suppose that you have an array of size $n$ that contains elements in the range $0$ to $r$, exclusive.
Give both the time and space complexity of counting sort.

Based on your analysis, what do you believe are the pros and cons to counting sort?
Check with a member of the course staff before completing this part!
