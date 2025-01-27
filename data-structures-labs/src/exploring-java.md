# Exploring Java

In the spirit of one of the meta-learning goals of the course, we'll use these first weeks not just to learn the Java programming language but also the refine our skills at *learning new programming languages*.
The jump from C to Java is no where near as dramatic as the jump from Racket to C, so we can use this opportunity to develop some best practices for migrating from language to language whether it's from Java to C#, Java to Python, or to some more exotic language.

## Partners, Collaboration, and Turn-ins

For the labs, you will work with a randomly-assigned partner.
We'll mix the partners every couple of days (no more than a week) to keep things fresh.
Please make sure you understand the collaboration policies for the course as presented in the syllabus.
And finally, whenever you do work with your partner, keep in mind the golden rule when working with a group:

> You are there to help your partner learn and vice versa!

Next time, we'll discuss the tools that we will use in this course—Netbeans and Git—and how we should organize our course work in light of them.
For now, save your programs in a directory dedicated to the course (just like CSC 161) and share them with your partner when you are done.

## Part 1: Basic Compilation Pipeline

When learning a new programming language, our first concern before worrying about how the programming language operates, *i.e.*, its *semantics*, is how to get stuff to appear on the screen---anything!
Imagine the computer program development process as a *pipeline*, a series of steps where the end result is a computer program.
For Scamper, the pipeline was very straightforward:

~~~
Type code               Use those definitions
into DrRacket's   --->  by typing expressions
definitions pane        into DrRacket's
                        interactions pane
~~~

Which is part of the reason we choose Racket/Scheme for CSC 151!
C is a little bit more involved:

~~~
Write complete        Compile the        Run the
C programs in   --->  program      --->  program
a text file           using gcc
~~~

C doesn't have an interactive environment (commonly known as a *REPL* or a read-eval print loop) to try our C commands or expressions.
Instead, we must write complete programs, compile them using a *compiler*, and run the resulting executable.

You may have recalled initially having difficult getting a program to work because you messed up one of these steps---for example, getting the syntax of a complete program wrong, not having your source files in the correct place, or invoking `gcc` with the wrong parameters.
But once you had that template of a basic program and the series of commands you needed to invoke, you were set!

Being an ancestor of C, Java's pipeline is nearly identical:

~~~
Write complete          Compile the        Run the
Java programs in  --->  program      --->  program
a text file             using javac        using java
~~~

In fact, rather than using `gcc`, you simply use the `javac` program instead which compiles Java programs.
However, unlike `gcc`, the `javac` program produces a *Java class file* as output, a file with a `.class` extension.
This is not a standalone program like what `gcc` produces.
It is a file that contains *Java bytecode* which is your code in a low-level form that the *Java virtual machine* can execute.
The Java virtual machine is located in the `java` program which we can point at a `.class` file to run it.

For example, here is an example workflow for compiling at running the canonical "Hello World!" program:

~~~console
> javac HelloWorld.java
> java HelloWorld
Hello World!
~~~

Note that when passing the `.class` file to the `java` program, you do not specify the extension.
`java` looks for a file called `HelloWorld.class` for you!

~~~admonish question title="Hello World!"
With this in mind, write the "Hello World!" program in Java in a file called `HelloWorld.java`, compile it, and run it to verify that everything works!
~~~

### Exploring the Negative Space

When trying out a new language, you'll run into plenty of errors and mistakes.
This is helpful because while you might burn more time than you'd like fixing those problems initially, they become trivial to fix if you see them in the future ("oh, I recognize this error message from before. You just need to do this to fix it...").

Once you've established your basic programming pipeline, it's a good idea to explore the space and intentionally try to break it in various ways.
Because you are starting from a good pipeline, you can diagnose the error immediately on top of knowing exactly what you did to cause it!

~~~admonish question title="The Negative Space"
Answer this following set of questions by playing around with your working `HelloWorld.java` program.
Leave your answers in a block comment (`/* ... */`) at the bottom of the file.

1. **(File extensions).**
   Is it necessary to use `.java` file extension for a source file?
   If not, what sort of error do you get when you use a different extension?
2. **(Missing files).**
   What happens if you specify a source file to `javac` that does not exist or exists elsewhere on disk?
3. **(.class and the java program).**
   What happens if you specify the program-to-run to `java` with the `.class` extension, i.e., `java HelloWorld.java`?
4. **(Code formatting).**
   Is Java whitespace-sensitive (i.e., do spaces and newlines matter)?
   Case-sensitive (i.e., is `main` different from `Main`)?
5. **(The main function).**
   Speaking of which, what happens if the signature of `main` is not exactly as presented in the reading, e.g., different function name, return type, or argument name? What parts are _necessary_ for your program to work?
5. **(Necessary boilerplate #1: classes).**
   The biggest visual difference between C and Java source code is that Java functions must be housed within a class.
   What happens if you write a free-floating function, i.e., a function not declared within a class?
6. **(Necessary boilerplate #2: public and static).**
   The other major difference is the presence of `public` and `static` on the class and function declarations.
   Add a helper function to your `HelloWorld.java` program that performs some trivial computation that `main` then calls.
   Use this function to investigate whether `public` and `static` are necessary.
   Which of these `public` and `static` keywords can you remove?
   For the `public` and `static` keywords you can't remove, what errors do you get?
~~~

## Part 2: Building Up Your Bag of Programs

With a basic programming pipeline established, you are now in the position to begin writing real programs.
I typically break down what I can do in a programming language into two buckets:

1. What I can do with the language itself.
2. What I can do with the language's libraries.

For solving more interesting problems, we'll need external libraries (either the built-in libraries or some third-party libraries), for example, to perform file I/O or create graphics.
But it is worthwhile to tackle the two buckets independently.
In particular, learning what primitive operations the language provides gives you insight into how you should model your problems and structure your solutions.

Again, Java is an ancestor of C, so much of these primitive operations are carried over without any changes.
In particular, minus some slightly different syntax and subtly different semantics (which will be exposed as we write more Java):

* Basic types,
* Variable declarations and assignments,
* Basic expressions and statements, and
* Function declarations.

Are identical between Java and C.

With this in mind, try writing a program that solves the canonical Fizzbuzz interview problem:

~~~admonish question title="Fizzbuzz"
Write a `static` function `fizzbuzz(n)` that takes an integer `n` and prints the integers from 1 to `n` (inclusive) to the console, one integer per line.
However:

* When `n` is a multiple of 3, print `fizz`,
* When `n` is a multiple of 5, print `buzz`, and
* When `n` is both a multiple of 3 and 5, print `fizzbuzz`.

You should write this program in a Java file called `FizzBuzz.java`.
Your program should take a single command-line argument that is the value to pass to `fizzbuzz`, e.g.,

```console
> java Fizzbuzz 5
1
2
fizz
4
buzz
```

Your program does not need to perform any error-checking on the command-line arguments, i.e., it can assume it receives exactly one argument that is an integer.

To convert a `String` to an `int`, use the `Integer.parseInt(n)` function.
~~~

The `fizzbuzz` problem is an example of one of the standard programs I try to write down whenever I learn a new language.
It's ideal for this purpose because it:

1. Is a short program to write, yet is complex enough to be non-trivial.
2. Tests the language's expressiveness.
   In other words, how do I express repetitive and conditional behavior?

Over the next few class periods, we'll be working through the canonical programs that I like to write when learning a new language.
Feel free to add these to your arsenal whenever you pick up a new language, too!

### Bonus: Project Euler

For additional problems to help you exercise a new programming language, I recommend checking out [Project Euler](https://projecteuler.net) which is a repository of math-based programming problems for you to solve.
You don't need to do any Project Euler problems for this lab, however, I highly recommend trying out the first couple of problems on your own or with your study group for practice!

## Part 3: Arrays in Java

We'll now use lab time to explore some of the more significant, heavyweight features of Java and how the language improves over what C provides.
One example of this is the `array` which is a data structure that holds a *homogeneous* (*i.e.*, same type), fixed-size collection of values.
When working with a new type of data, you should always ask yourself the following two questions:

1. How do I *create* values of this type?
2. How do I *use* or *consume* values of this type?

Luckily, Java array syntax is largely identical to C:

* The type of an array that holds values of type `T` in Java is `T[]`, *e.g.*, `int[]` for an array of integers.
* To create a new array, we use either an *array literal* or a *new* expression passing in the size of the array.

For example, the following code snippet creates an array of 5 elements.
The first initializes the array in two different ways:

~~~java
int[] arr1 = { 0, 1, 2, 3, 4 };     // An array literal
int[] arr2 = new int[5];            // A "new" expression
~~~

Array indexing (`arr1[0]`) works identically to C.
Finally, one nice convenience is that Java arrays, unlike C arrays, know their length via the `length` field:

~~~java
System.out.println(arr2.length);    // Prints 5
~~~

~~~admonish question title="Array Exploration"
With all this in mind, in a file called `ArrayExploration.java`, write code to answer the following questions.
Write your answers in a block comment at the bottom of the file.

1.  **(Initialization).** Perhaps the largest departure from C is that the following code snippet in C

    ```c
    int arr [5];
    ```

    Is how you declare an array with five elements.
    Note that there is no array literal or `new` expression present.
    What happens if you try this with Java, i.e., declare a variable with an array type, do not use an array literal or new expression to initialize it, and then use that array?

2.  **(`new` Expressions).** The array literal allows you to specify the contents of the array (if you know them at compile time).
    What value(s) does the `new` expression use to initialize each element of an array if any?
    How does this differ from C?

3.  **(Out-of-bounds).**  Recall that with C arrays, you are free to walk off the end of the array into arbitrary parts of memory (because an array is morally a pointer)!
    Can you do this in Java?
    If not, what error(s) do you get when you try to do this?
    Do the errors happen at compile time, or runtime?
    Can you think of a legitimate use case for walking off the end of an array?
~~~

~~~admonish question title="Array Problems"
Next, exercise your array manipulation skills on these problems.
For all of these problems, continue writing your solutions in `ArrayExploration.java`.

1.  **(Max)**.
    Write two functions, `min(arr)` and `max(arr)` that both take an array of integer as input and returns the minimum value and maximum value of the array, respectively.
    Your `main` method should demonstrate that your two functions work over the following array `{3, 7, -10, 2, 9, 1}`.

2.  **(Range)**.
    Write a function, `range(arr)`, that returns the range of the given array of integers.
    The range of an array is the difference between its minimum and maximum value.
    Your `main` method should demonstrate that your function works over the following array `{3, 7, -10, 2, 9, 1}`.

3.  **(Rev)**.
    Write a function, `rev(arr)`, that takes an array of integers as input and mutates the array so that its elements are reversed.
    Your `main` method should demonstrate that your function works over the following array `{3, 7, -10, 2, 9, 1}`.

4.  **(Longest Increasing Subsequence)**.
    Write a function `longestIncreasingSubsequence(arr)` that returns the *size* of the longest increasing subsequence found in the given array of integers.
    For example, if the input array is `{3, 7, -10, 2, 8, 9, 5, 1}`, then the function returns `4` corresponding to the sub-sequence `{-10, 2, 8, 9}`.
    Your `main` method should demonstrate that your function works on this sample array.

5.  **(Fib)**.
    Write a function `fib(n)` that takes an integer `n` and returns an integer array of size `n` where the *i*-th element of that array contains the *i*-th fibonacci number.
    For example, `fib(10)` should return an integer array containing the elements `{1, 1, 2, 3, 5, 8, 13, 21, 34, 55}`.
    *Use a loop to do this computation instead of recursion.*
    Your `main` function should demonstrate the results of calling `fib(10)`.
    
    (Additional food for thought: why is a loop preferable to a recursive approach to computing `fib`?)
~~~

## Turn-in

To turn in your lab work, navigate to Canvas and find the entry for the "Exploring Java" lab.
Following the link will lead you to the Gradescope entry for the lab.
Submit all three files that you created:

* `HelloWorld.java`
* `FizzBuzz.java`
* `ArrayExploration.java`

And make sure to include your partner to your submission!

Pay attention to the autograder for each Gradescope turn-in as it will give you valuable feedback on your submission.
For this lab, the autograder will ensure that you have turned in all the appropriate files with the correct names.