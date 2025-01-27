# Classical Encryption

In this project, you'll practice your basic programming-in-the-small skills to implement a number of *[classical encryption](https://en.wikipedia.org/wiki/Classical_cipher)* techniques.
Classical encryption is based on the principle of substituting letters for other letters.
These may be simple schemes like the ones we'll implement in this homework or more complicated schemes such as they employed in the [Enigma Machine](https://en.wikipedia.org/wiki/Enigma_machine).
The Enigma machine was used by Nazi Germany during World War II and was subsequently broken by the allies, in particular the researchers at the British [Government Code and Cypher School](https://en.wikipedia.org/wiki/Government_Communications_Headquarters#Government_Code_and_Cypher_School_.28GC.26CS.29), notably [Alan Turing](https://en.wikipedia.org/wiki/Alan_Turing) who led development of the [Bombe](https://en.wikipedia.org/wiki/Bombe) machines that were used to break the Enigma machines.

## Project Formatting and Submission

To help you become acquainted with the Java compilation pipeline for this first project, you will write your program in a file called `Encryption.java`.
You will submit this file to Gradescope when you are done.

The top of `Encryption.java` must contain the following header with the relevant information filled in:

~~~java
/**
 * CSC 207-02 (Spring 2025)
 * Project 1: Classical Encryption
 * 
 * <Your name>
 * <Your email address>
 * 
 * Acknowledgements:
 * - <List of citations>
 * - ...
 * - ...
 */
~~~

As per our guidelines on citation, you must cite _all tools, people, and sources_ that you used or consulted in creation of your work.
In the age of generative AI, it is especially important to be mindful and acknowledge all the resources you used when creating a program.
Note that this includes tools that you use such as your text editor and version of Java that you used!

## Encryption Preliminaries

The theory of encryption is a cornerstone of *[cryptography](http://en.wikipedia.org/wiki/Cryptography)* and *[computer security](http://en.wikipedia.org/wiki/Computer_security)* where we make our communication and/or data secure from adversaries.

Before the advent of computers, *classical cryptography* used various *transposition* and *substitution ciphers* to encrypt data.
Here are some basic definitions you should know before proceeding.

* A **cipher** is a pair of algorithms for encrypting and decrypting data.
* **Plaintext** is un-encrypted data.
* **Ciphertext** is encrypted data.
* A **transposition** cipher creates ciphertext from plaintext by re-arranging the letters in the text in some pre-determined fashion.
* A **substitution** cipher creates ciphertext from plaintext by substituting one letter for another letter.

Ciphers in classical cryptography were designed to be executed by hand with analog tools.
For example, the [Solitaire Cipher](http://en.wikipedia.org/wiki/Solitaire_(cipher)) was designed to be hand-executed by secret agents in the field with nothing more than a deck of cards.
More elaborate encryption schemes required the use of mechanical computing devices that became the forefathers of the modern-day digital computers, the Enigma machine described in the introduction being the quintessential example.
If you are curious, here is a [fun simulation](http://www.enigmaco.de/enigma/enigma.html) of an Enigma machine.
However, in the age of digital computers, classical cryptography methods are no longer useful for most practical purposes because a computer can either brute-force through these encryption schemes, or otherwise greatly assist an
educated individual in breaking the code.

To implement these classical cryptographic schemes, we need to understand how to map the mathematical models that underlie them into Java.
Luckily this is relatively straightforward.
For sake of simplicity, let's assume that we're only working with lowercase English letters and that each letter is assigned a number---`'a'` starting at `0` and `'z'` ending with 25---called the *character code* of that letter.
Given a single letter `ch` and a single letter `key` to encrypt the letter with:

* To encrypt `ch` with `key`, "add" `key` to `ch`.
That is, add their corresponding character codes, and the result is the character code of the corresponding encrypted letter.
If you go over 25, wrap around.
For example, If we encrypt `c` with `j`, then we get `l` because the code for `c` is 2 and the code for `j` is 9.
2 + 9 = 11 which is the code for `l`.
If we encrypt `x` with `e`, then we get `b` because the code for `x` is 23 and the code for `e` is 4.
23 + 4 = 27 but since 27 isn't a valid code, we wrap around and get 1 which is the code for `b`.
* To decrypt `ch` with `key`, "subtract" `key` from `ch`.  In the case when the difference is negative, we wrap around like with addition.

By thinking of characters as numbers, we can formalize this style of encryption as well as directly implement it in Java.
With character values, encryption can be thought of the formula:

~~~
E = (ch + key) mod 26.
~~~

Decryption is described by the formula:

~~~
D = (ch - key) mod 26.
~~~

Mod here is *almost* the `%` operator, but not quite.
The problem is that negative integers do not "wrap around" like you expect, *e.g.*, `-2 % 26` is `-2` rather than `24`.
You will need to do a little bit of extra work to obtain the desired behavior in this case.

To get the character value of a single character, we can convert it to an integer by *casting* to the appropriate type:

~~~java
(int) 'e'   // 101
~~~

However, we said that the character code for `e` is `4`.
What is going on here?
It turns out that we assign character codes to not just lowercase letters but to *all possible letters*.
Imagine putting all the possible letters on a line.
The lowercase letters occupy indices 97 through 122 on that line:

~~~java
(int) 'a'   // 97
(int) 'z'   // 122
~~~

To "re-base" these numbers at index zero, we simply need to subtract the character value of `a`.

~~~java
(int) 'a'     // 97
'a' - base    // 0
'z' - base    // 25
~~~

When we want to get a letter back given a computed character value in the range 0-25, we simply reverse the process by adding back in `(int) 'a'` and then casting back to `char`.

~~~java
int result = 22;
(char) (result + base)    // 'w'
~~~

## The Caesar Cipher

With the fundamentals of manipulating characters-as-numbers out of the way, we will now implement a number of classic ciphers based off these cryptographic principles.
First, we will implement the [Caesar Cipher](http://en.wikipedia.org/wiki/Caesar_cipher), so named after Julius Caesar who used this encryption for his own private correspondence.

In terms of the formulae described above, the `key` we use to add and subtract to each character is constant.
That is, for any message, we pick a particular value `n` and encrypt a message with:

~~~
E = (ch + n) mod 26.
~~~

And we decrypt a message with

~~~
D = (ch - n) mod 26.
~~~

For example, say we want to encrypt the message `hello`, we would pick a key `n`, say `n = 11`.
Then, to encode the message, we calculate:

~~~
'h' + 11 = 7 + 11 = 18 = 's'
'e' + 11 = 4 + 11 = 15 = 'p'
'l' + 11 = 11 + 11 = 22 = 'w'
'l' + 11 = 11 + 11 = 22 = 'w'
'o' + 11 = 14 + 11 = 25 = 'z'
~~~

To decrypt the message, we subtract the key rather than add it:

~~~
's' - 11 = 18 - 11 = 7
'p' - 11 = 15 - 11 = 4
'w' - 11 = 22 - 11 = 11
'w' - 11 = 22 - 11 = 11
'z' - 11 = 25 - 11 = 14
~~~

Your task is to write a program in a class called **Encryption** that encodes and decodes messages using the Caesar cipher as described above.
Because there are only 26 letters in the English alphabet, rather than shifting according to a user-defined value, we can simply show the user the result of applying all 26 possible shifts!

Here are some example executions of the program you should create.

~~~
> java Encryption encode helloworld
n = 0: helloworld
n = 1: ifmmpxpsme
n = 2: jgnnqyqtnf
n = 3: khoorzruog
n = 4: lippsasvph
n = 5: mjqqtbtwqi
n = 6: nkrrucuxrj
n = 7: olssvdvysk
n = 8: pmttwewztl
n = 9: qnuuxfxaum
n = 10: rovvygybvn
n = 11: spwwzhzcwo
n = 12: tqxxaiadxp
n = 13: uryybjbeyq
n = 14: vszzckcfzr
n = 15: wtaadldgas
n = 16: xubbemehbt
n = 17: yvccfnficu
n = 18: zwddgogjdv
n = 19: axeehphkew
n = 20: byffiqilfx
n = 21: czggjrjmgy
n = 22: dahhksknhz
n = 23: ebiiltloia
n = 24: fcjjmumpjb
n = 25: gdkknvnqkc

> java Encryption decode dahhksknhz
n = 0: dahhksknhz
n = 1: czggjrjmgy
n = 2: byffiqilfx
n = 3: axeehphkew
n = 4: zwddgogjdv
n = 5: yvccfnficu
n = 6: xubbemehbt
n = 7: wtaadldgas
n = 8: vszzckcfzr
n = 9: uryybjbeyq
n = 10: tqxxaiadxp
n = 11: spwwzhzcwo
n = 12: rovvygybvn
n = 13: qnuuxfxaum
n = 14: pmttwewztl
n = 15: olssvdvysk
n = 16: nkrrucuxrj
n = 17: mjqqtbtwqi
n = 18: lippsasvph
n = 19: khoorzruog
n = 20: jgnnqyqtnf
n = 21: ifmmpxpsme
n = 22: helloworld
n = 23: gdkknvnqkc
n = 24: fcjjmumpjb
n = 25: ebiiltloia

> java Encryption
Usage: java Encryption <encode|decode> <text>

(And similarly for any invalid arguments to the program.)
~~~

Note that the program takes two command-line arguments:

*   Either `encode` or `decode` corresponding to encoding or decoding the supplied text.
*   The supplied text.
    If `encode` is given, then the text is encoded using the Caeser cipher algorithm.
    If `decode` is given, then the text is decoded using the Caeser cipher algorithm.

If the program does not receive appropriate command-line arguments:

+ Too little or too many arguments
+ Neither encode nor decode is provided for the first argument
+ Non-lowercase characters are used in the ciphertext/plaintext.

Then the program reports its help/usage string and exits.

Your program must follow this format *exactly* and producible *identical* output to the sample execution above.
This includes the blank line after the end of the output of the program!

For this program, you will need to use a handful of [String methods and constructors](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html).
We'll talk more about methods and constructors in a future class, but for now, you can use these as presented:

* `s.charAt(n)` retrieves the character at the `n`th index of string `s`.
* `s.toCharArray()` creates a character array (*i.e.,* a value of type `char[]`) containing the characters of string `s`.
* `s.length()` returns the length of the string `s`.
* `new String(arr)` creates a new string from the given character array `arr`.

## Program Design and Decomposition

In this project (and all work that you do), you should keep an eye out for appropriate *decomposition* of the program into smaller pieces.
In particular, you should not have a monolith `main` function; you should break up the problem into **at least two helper functions** that give meaning to the various tasks that the program performs.

Additionally, you should format your code according to the course's [Style Guide](./style-guide.md).
Since we have not yet introduced Javadoc comments, you do not need to use them, but you should document each of your functions in the style that you learned from CSC 161.

## Submission and Grading

When you are done, navigate to Canvas and find the turnin page for this project.
The page will redirect you to Gradescope where you can submit your work.
For this project, you will only need to turn in `Encryption.java`.

When grading projects, we will evaluate them on two criteria:

* External correctness: does your program function correctly?
* Internal correctness: does your program follow good design practices?

External correctness is graded primarily by the autograders that exist alongside every project's Gradescope turnin page.
If you pass the autograders, your code is likely externally correct (although there may be some manual correctness checking that we do after the fact).
If you turn in your assignment and one or more autograder tests fail, you should fix those issues and resubmit your work!

For internal correctness, we'll look at the following:

1. Whether you followed the course [style guide](./style-guide.md).
2. Your actual program design: do you have a monolithic main function (bad), or did you appropriately use helper functions (at least two!) to decompose the problem (good)?
3. General program style: does your program have appropriate variable names, indentation, and spacing?
