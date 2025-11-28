# Malicious URL Detector

For this lab, clone the following repository and submit your work to Gradescope when you are done:

+   <https://github.com/psosera/malicious-url-detector>

Recall that when detecting malicious URLs, we need to optimize both _lookup speed_ and _storage_.
There are potentially millions of known-to-be-malicious URLs we need to index, making conventional storage in, say, a hash set, impractical.
Furthermore, in this context, we can tolerate _false positives_, situations where a good URL is considered malicious, but we certainly do not want _false negatives_, situations where a malicious URL is considered good!

# Part 1: The Bloom Filter

The [_Bloom Filter_](https://en.wikipedia.org/wiki/Bloom_filter) is an advanced mapping data structures that meets all these criteria!
A Bloom filter over values of type $T$ consists of:

+   An array of booleans of some fixed size $n$.
    Such an array is typically implemented efficiency as a _Bit Set_, a data structure that maintains precisley one bit per boolean.
+   A collection of hash functions $h_1, …, h_k$ of type $T \rightarrow \mathbb{N}$.

With this in mind, implement the constructor of the `BloomFilter` class which takes a bit set size and list of hash functions and makes a new, empty `BloomFilter`.
Java provides a direct implementation of `BitSet` in the `java.util` package.

## Part 2: Addition

Adding an element to a Bloom filter is straightforward:

+   To add an element $v$ to a Bloom filter, we run $v$ through each of the $k$ hash functions (modulo $n$) to generate $k$ indices into the array of booleans and set those indices to `true`.

Note that since we use multiple hash functions, no probing or chaining strategies are necessary, simplifying implementation!

As an example, consider a Bloom filter with a backing bit set of size ten ($n = 10$) that contains elements of type $T$ with three hash functions $h_1$, $h_2$, and $h_3$.
Here is a table of example values of type $T$ and the output of the hash functions on each of these values.

|       | $h_1(-)$ | $h_2(-)$ | $h_3(-)$ |
| ----- | -------- | -------- | -------- |
| $v_1$ | 1        | 3        | 15       |
| $v_2$ | 2        | 14       | 8        |
| $v_3$ | 11       | 4        | 5        |

Imagine beginning with a bit set of 10 bits:

~~~
00000 00000
~~~

First, simulate execution of first adding $v_1$ and then $v_2$ to the Bloom filter.
What is the state of bits after adding each element?
Note that it is ok if a bit is already set in the filter!
By using multiple hash functions, we use a _collection_ of bits to identify whether an element is in the set, so any single conflict is not a problem.

Check with a member of the course staff to make sure you understand how `add` works.
And then, use this example to implement the `void add(T item)` method of the `BloomFilter` class.

## Part 3: Containment

With addition implemented, let's now consider how we perform membership checking.

+   To check to see if an element $v$ is in a Bloom filter, we run $v$ through each of the $k$ hash functions (modulo $n$) to generate $k$ indices into the array of booleans and check to see if _all_ of those indices are true.

Consider the state that we left our Bloom filter in after the previous part.
Check to see if $v_1$, $v_2$, and $v_3$ are contained in the filter according to the membership checking algorithm.
Are these results reasonable given the design goals of the Bloom filter?

Check with a member of the course staff to make sure you under how `contains` works.
Once you have done that, go ahead and implement the `boolean contains(T item)` method of the `BloomFilter` class.

## Part 4: Making Hash Functions

With the filter completed, now we need to specialize it to our specific domain of detecting malicious URLs.
URLs are just strings, so we need to instantiate our generic `BloomFilter` class to `String`.
The first step of this process is to provide the String-specific hash functions that will power the filter.
Java, of course, provides a single hash function for `String` via the `int hashCode()` method.
But the power of the Bloom filter comes precisely from providing an arbitrary number of hash functions to the filter!

We could manually come up with many different hash functions, but this is both time inefficient and problematic if we were to consider data types other than `String`.
There are two approaches that we might consider to solve this problem:

1.  Use one hash function to generate a large hash value.
    We can then divide up the bits to create smaller hash values.
    For example, we could generate a 256-bit hash and then divide up the hash into four 64-bit hash values.
2.  We can use a hashing algorithm that is parameterized by a _seed_ value to generate different hash functions for a datatype by choosing different seeds.

We'll employ this second method, in particular, by using the [MurmurHash](https://en.wikipedia.org/wiki/MurmurHash) hashing algorithm which, indeed, allows you to specify hashing functions that differ by a seed value.
Our implementation will go into the `static List<Function<String, Integer>> makeStringHashFunctions(int num)` method of `MaliciousURLDetector`.

Our project references the [Google Guava](https://github.com/google/guava) libraries which are a set of general-purpose library used internally at Google that fill several niches not already covered by Java's extensive standard library.
In particular, the [`com.google.common.hash`](https://guava.dev/releases/23.0/api/docs/com/google/common/hash/package-summary.html) package of Guava contains a [`Hashing`](https://guava.dev/releases/23.0/api/docs/com/google/common/hash/Hashing.html).
`Hashing` contains a static method:

+   [`HashFunction murmur3_128(int seed)`](https://guava.dev/releases/23.0/api/docs/com/google/common/hash/Hashing.html#murmur3_128-int-): returns a `HashFunction` that implements the Murmur3 algorithm with the given `seed` value.

We can use different `seed` values to produce as many `HashFunction`s as we would like.
We can obtain these seeds via a random number generator, e.g., using [`ThreadLocalRandom.current()`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/concurrent/ThreadLocalRandom.html) from the `java.util.concurrent` package which gives you access to a `Random` instance without the need to `new` one up.

Once we have a [`HashFunction`](https://guava.dev/releases/23.0/api/docs/com/google/common/hash/HashFunction.html) in hand, we can use it to create a lambda of type `Function<String, Integer>` that generates hash values for `String`s.
The following method of `HashFunction` will be useful for this purpose:

+   [`HashCode hashString(String input, Charset charset)`](https://guava.dev/releases/23.0/api/docs/com/google/common/hash/HashFunction.html#hashString-java.lang.CharSequence-java.nio.charset.Charset-): generates a `HashCode` for the given `input` string.
    We can then retrieve the actual hash code from the `HashCode` object using its `asInt()` method.
    The `charset` argument of `hashString` can be filled with the `Charset.defaultCharset()` static method of the `Charset` class found in the `java.nio.charset` package.

Recall that the syntax of lambdas in Java is very lightweight: `arg -> expr` creates a lambda with a single `arg` that evaluates to the value that `expr` evaluates to.

## Part 5: Populating the Filter

Next, we need to populate the filter with malicious URLs.
We'll use an [open dataset of 651,191 URLs](https://www.kaggle.com/datasets/sid321axn/malicious-urls-dataset) by Manu Siddhartha from Kaggle.
Once the filter is populated with this dataset, we can check whether a URL is suspected to be malicious by seeing if it is a member of our Bloom filter!

The dataset is already found in the `data/malicious_phish.csv` file found in the project.
Each line of the file is of the format:

~~~
<url>,<type>
~~~

Where `type` is `"benign"` for non-malicious URLs.
Otherwise, the type classifies the kind of maliciousness, e.g., `"phishing"`.
For our purposes, anything _not_ benign is considered malicious.

Implement this behavior in the `static BloomFilter<String> makeURLFilter(...)` method of `MaliciousURLDetector`.
You can either use a [`Scanner`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/Scanner.html) or [`Files.readAllLines`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/nio/file/Files.html#readAllLines(java.nio.file.Path)) to read the lines of the file and the `split(String sep)` method of the `String` class to break up the line into its parts according to the format above.

## Part 6: The Malicious URL Detector Program

With all of this infrastructure, we can now put together the overall malicious URL detector program (`MaliciousURLDetector`).
The program proceeds as follows:

1.  The program receives the size of the Bloom filter's bitset (in bits) and the number of hash function as input.
2.  We create a `BloomFilter<String>` populated with the data from the malicious URL dataset.
3.  In a loop, we ask the user for a URL (via a `Scanner`), check to see if it is in the filter, and report accordingly.
4.  If the user ever enters `"exit"`, then we quit.

~~~
➜ mvn compile exec:java -Dexec.args="4792530 7"
...
Enter a URL to check (or "exit" to quit):
> cnn.com
✅ The URL is not known to be malicious...
> info-pages.000webhostapp.com
‼️ The URL is possibly malicious...
> cpageconstruction.com
‼️ The URL is possibly malicious...
> exit
...
~~~

Picking the size of the filter and the number of hash functions is dependent on the size of the set we intend on maintaining.
Assuming that our hash functions are well-distributed, we can precisely choose these parameters based on a target [false positive probability](https://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives).

This calculation is an interesting application of probability towards analyzing algorithmic correctness.
For our purposes, we can simply use an online calculator that implements an analysis found on [Stack Overflow](https://stackoverflow.com/questions/658439/how-many-hash-functions-does-my-bloom-filter-need):

+   [Bloom Filter Calculator](https://krisives.github.io/bloom-calculator/)

Use the calculator to determine the appropriate parameters for this data set and try your filter out!
You should also explore what happens when you use less optimal parameters, e.g., reducing the bitset size substantially.
