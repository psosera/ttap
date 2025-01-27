# The String Class

Last week, we used a small portion of the String class's functionality in the labs and homework.
In this lab, we use the String as our vehicle for exploring what an object can provide us as a client as well as get practice using the behavior of objects to solve problems.

## The Standard Library Documentation

Rather than summarize the set of behavior that String objects expose, we'll use this opportunity to explore the *Java standard library*.
The standard library is built-in to Java; it contains a ton of classes for you to use that cover your basic needs as a programmer: containers, graphical interfaces, networking, and parsing among others.
You can find the standard library documentation for Java 23 below:

* <https://docs.oracle.com/en/java/javase/23/docs/api/java.base/module-summary.html>

(Technically, this is the link to the `java.base` module page which contains the standard library packages.)

In my humble opinion, the Java standard library documentation is the best organized API document out there, and is a large reason why Java is so popular.
On this page, you'll see the following things:

* The main page, which features a description of every *package* available in the Java standard library.
Whereas a Java source file (roughly) corresponds to a single Java class, a package corresponds to a *folder* in the file system.
For example, the `java.util` package corresponds to the folder `java/util` in the standard library's source tree.
Thus, packages are a way of organizing collections of classes into logical units.

* The sidebar, which features an abbreviated list of all the packages as well as all the classes in those packages.

In the "All Classes" list, find the `String` class and click through to see its documentation.
All the pages of the Java documentation are organized as follows:

* An *overview* of the class, in particular its purposes and how to use it.
* A summary of the *fields*, *constructors*, and *methods* of that class.
Recall that the fields and methods correspond to the state and behavior of objects of this class.
The constructors correspond to how we construct objects of this class.
* Detailed descriptions of the fields, constructors, and methods.

Feel free to skim through the API page; there's a lot of stuff here!

## Lab Write-up and Turn In

For this lab, create a NetBeans project called `StringExercises`. 
Recall that you should:

+ Create a "Java with Maven" → "Java Application" project.
+ Use the project name: `StringExercises`.
+ Use the group ID: `edu.grinnell.csc207`.
+ Use the package name: `edu.grinnell.csc207.stringexercises`.

And inside this project, put your code in the default Java file provided (`StringExercises.java`).
You can rename the initial class created by NetBeans to `StringExercises` using the refactor feature of the IDE.

In addition to `StringExercises.java`, you will write your answers to the first two parts in a separate [Markdown file](https://www.markdownguide.org/) file called `README.md`.
Markdown is a simple, text-based, markup language that is common when writing programming documentation.
You should install the NetBeans Markdown plugin via the Tools menu, and then you can create Markdown files in your project.

You can use the following template for your `README.md` file:

~~~markdown
# Lab: StringExercises

+ Authors: _(Insert lab partner names here!)_
+ Acknowledgements:
    - _(Insert citations here!)_
    - ...

## Part 1: Old Hat, New Hat

...

## Part 2: Iceberg, Right Ahead

...
~~~

## Part 1: Old Hat, New Hat

You have already worked with string datatypes in other languages.
Keeping in mind our theme of "mapping old knowledge to new setting," let's brainstorm what we could do with strings in Scheme and C and either find equivalents in the Java standard library or note that these are things we must implement ourselves.

1.  With your partner, brainstorm a list of (a) useful functions over strings you've used in other languages and (b) operations, i.e., common programming patterns, over strings.
2.  For each entry in your list, peruse the String class documentation and try to find an equivalent String method that performs the function/operation in question.
    If you can't find such a method, write a code snippet that performs the same effect.

## Part 2: Iceberg, Right Ahead!

One of the operations you may have identified that you want to perform over strings is _equality_.
Equality is trickier than it seems and requires some in-depth discussion.
Sometimes, seemingly simple operations hide language complexity, some of which your prior experience may account for and others, you just need to be ready to learn something new!

When you are done with section, please check your answers with a member of the course staff!

1.  First, does the standard equality operator work over strings, (`==`)?
    Try running the following code snippets (either in your `main` function or in JShell) and see if they work as you expect:

    ~~~java
    String s1 = "hello world!";
    String s2 = "hello world!";
    System.out.println(s1 == s2);     // A

    String s3 = s1.substring(0, 5);
    String s4 = s2.substring(0, 5);
    System.out.println(s3 == s4);     // B

    String s5 = new String(new char[] { 'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd', '!' });
    System.out.println(s1 == s5);     // C
    ~~~

2.  You may have noticed that there is an `equals(obj)` method of the string class!
    Try replacing all the uses of `==` with appropriate invocations of the `equals` method (does it matter which of the two strings you call `equals` on?).
    Do you obtain the results that you were expecting now?
    Based on your results, complete the following sentence:

    > To compare two strings for equality, we must ...

3.  But _why_ is this the case?
    Let's focus first on the equality checks marked `B` and `C` above.
    I'll appeal to your knowledge of C and state a subtlety about Java values not apparent until now:

    > Values of object type (of which `String` is one) are actually pointers (or more specifically, _references_) to those values in memory.

 
    With this in mind, in a sentence or two, explain why `==` does not work for the `B` and `C` string examples listed above.

4.  Now, let's revisit the equality check marked `A`.
    You might observe that your answer to the previous part doesn't account for the results in this case.
    It turns out that Java does something special that our previous languages do not do!

    Search the String class documentation for the `intern()` method and read about it.
    Based on this, can you explain the results of this equality check?

## Part 3: A Trio of Problems

With this documentation in hand, tackle these three programming problems.
You are free to use any of the methods of the String class as you see fit.

### Problem 1: Intersperse

Write a function, `intersperse(arr)`, that produces a string that is the result of *interspersing* a comma among all the strings found in the given array.
For example, if you pass the array `{ "ab", "cd", "ef" }` to `intersperse`, you should receive the string `"ab,cd,ef"`.

(_Hint_: there is a `concat` method of the string class.
However, the plus (`+`) operator also performs concatenation between strings in Java!)

### Problem 2: Comma-separated Value Parsing

Write a function `parseName(String s)` that parses a full name from `s` and returns it in the order of: `<first name> <middle name> <last name>`.
You should assume that the string `s` is in the following format:

~~~
<last name>,<first name>,<middle name>
~~~

For example, `parseName("Turing,Alan,Mathison")` should produce the result `Alan Mathison Turing`.
You may assume that commas do not appear in the given name.

### Problem 3: Forgiving Prompt

For this problem, we'll use a new class, the [Scanner](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/Scanner.html) to get input from the user.
Scanner objects provide the same functionality as the C `scanf` or `readline` functions, but are much easier to use!
Here is an example of using a `Scanner` attached to `System.in`, i.e., standard input:

~~~java
// This `import` statement is necessary to make the Scanner class visible
// since it is not in the `java.lang` package.
import java.util.Scanner;

public static String fetchString() {
    Scanner scanner = new Scanner(System.in);
    // the nextLine() method returns the next line from the stream
    // that is used to construct the scanner.
    return scanner.nextLine();
}
~~~

With this in mind, write a function, `forgivingPrompt(question)`, that prompts the user to answer yes or no to given question (specified as `String`).
The function returns `true` if the user answers positively and `false` if the user answers negatively.

The prompt is "forgiving" in that it accepts a variety of answers:

* Y, Yes, Yep
* N, No, Nope, and
* Any lower-case or upper-case variants of these.

If the user does not enter in one of these responses, the function prompts the user again.
The function will prompt the user repeatedly until one of these responses is given.

### Bonus Problem: Simple Calculator

Write a function `add(String line)` that returns the result of adding all the natural numbers found in `line`.
The `line` is in the following format, for example:

~~~
0+31+4+81+9+10
~~~

Note that the numbers are separated by plus signs with no whitespace in-between.
`add` should produce the `int` 135 when passed this number.
Recall that the natural numbers are the positive integers and zero.
You may want to look at the `Integer` class for a way to turn a `String` into an `int` that you can add.

Once you have solved this, make things more challenging by allowing whitespace to appear before, after, and inside the expression!
For example, this would be a valid String to pass to `add`:

~~~
  0 + 8+6     + 9+3
~~~
