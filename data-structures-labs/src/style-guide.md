
# Java Style Guide

*(This style guide has been adopted and evolved from [CIS 120](http://www.cis.upenn.edu/~cis120/) at the University of Pennsylvania.)*

## Formatting

**100-character line length**: Ensure that no lines of code are longer than the maximum number of characters; in the case of CSC 207, this limit is 100 characters due to Java's inherent verbosity.

**Indentation using spaces**: Do not indent with tab characters (`\t`).
Any block of code enclosed in curly braces should be indented one level deeper than the surrounding code.
Choose a reasonable and consistent convention for indentation; generally 2 to 4 spaces is acceptable.
We recommend 4 spaces for indentation.
Make sure to configure your editor to utilize spaces for indentation instead of tabs!

**Use curly braces consistently**: Using "Egyptian"-style curly braces in your code in accordance with standard Java style guidelines.

~~~java
/* Egyptian braces: opening brace on same line, closing brace on own line */
if (a == b) {
    System.out.println("these are Egyptian braces");
} else {
    System.out.println("hello, world!");
}
~~~

**Curly braces around blocks**: Though Java permits the elision of curly braces in cases where the body if-statement or loop consists of a single statement, we require that every block (no matter the number of statements) be enclosed inside curly braces on a new line.
It is far too easy to make programming errors of this form otherwise:

~~~java
// NO
while (x < 5)
    System.out.println("Inside block");
    System.out.println("BUG -- Not inside block!");
~~~

**One statement per line**: There should be no more than one statement (declaration, assignment, or function call) on any line of your program.

**Use vertical whitespace to separate chunks of code**: Within a block of code, use vertical whitespace (blank lines) to separate groups of statements.
This makes code more readable and clarifies which statements are logically related.
Note that if you have to resort to this rule, you should consider breaking your code up into multiple functions instead.

**Always put spaces around operators**: Every Java operator should have spaces around it (except the dot operator, *e.g.* method calls `c.foo()`).
Use parentheses to communicate precedence.
For example: `5 - (x + 4) * 8`

## Naming

**Naming variables and methods**: Use `lowerCamelCase` for variable and method names.
Single-word variables should be all lowercase; subsequent words should be joined with their first character capitalized.
These identifiers should not contain underscores.

**Naming classes**: Use `UpperCamelCase` for class names and enum type names.
Do not capitalize acronyms within camelCased names; the string `"TCP socket ID"` should be written `TcpSocketId` rather than `TCPSocketID`.

**Naming constant and enum values**: Values that are constants, including final static variables and enum values, should follow `CAPITAL_CASE` conventions, in which all-caps words are separated by underscore characters.

**More descriptive names for larger scopes**: Variables that have greater scope should have more descriptive names.
It is often preferred to use a short name like `i` or `j` for a loop index, but as the scope of an identifier increases, so should the meaningfulness of its name.
For example, prefer `leftChild` over `l` for a class field.

## Commenting

**Do not over-comment code**: If the function a piece of code is reasonably obvious to experienced Java programmers, then it likely does not need to be commented.
Be judicious; if you are unsure, it is often best not to include a redundant comment.
If you feel like it is necessary to extensively comment your code, consider how to rewrite it to make it more straightforward.

**Comments should describe purpose over implementation**: The code itself is a description of an implementation, so it is unnecessary to comment what the code is doing (e.g., `x++` does not need a comment like "Increment x").
Instead, describe at a high level the intended use of the piece of code, such as what task the function performs.

**Write Javadoc comments**: Above all public methods and non-trivial private methods, you should include a block comment with the Javadoc syntax `/** … */`.
This should describe the function's intended use, as well as information about the function's parameters, return value, and exceptions it throws (if any).
At a bare minimum, your Javadoc comments should contain `@param` tags for each method parameter and a `@return` tag for the return type, if applicable.

**Do not submit commented-out code**: Remove any dead or commented-out code from your source files before submission.
Use git to save snapshots of code!

## Verbosity

**Use a helper variables or methods to avoid computing values multiple times**: If you find that a particular sequence of computations is being repeated more than twice, you should assign it to a variable and/or abstract it into a helper function which returns the desired value.

**Avoid redundant or verbose expressions**: Write expressions such as if conditions, while loop guards, and the like in the most succinct and expressive way possible.
Especially consider whether a long expression involving multiple `||` or `&&` operators is easily readable and whether it could be written with fewer or simpler sub-expressions.

**Prefer Java idioms over more verbose alternatives**: There are often many ways to write the same code in Java; make use of language features and idioms that make code more compact and easier to understand at a glance.
For example, consider the following loop, which prints the contents of the list `l`:

~~~java
List<String> l = /* ... */
for (int i = 0; i < l.size(); i++) {
    System.out.println(l.get(i));
}
~~~

Contrast this with a more idiomatic version that uses the for-each construct:

~~~java
List<String> l = /* ... */
for (String s : l) {
    System.out.println(s);
}
~~~

The second version has less syntactic clutter, and its intent is much more obvious to the reader of the code.
(It is also much faster if `l` is a `LinkedList`!)

**Make use of library functions when possible**: Java has quite a rich standard library, so you should make use provided functions instead of re-implementing them (unless otherwise directed).
Your own implementation will almost always be less efficient, and is less likely to be correct.

## Organization

**Packaging**: Java source files should be logically grouped according to standard package-naming conventions.
In particular, for our work, we will use the following reverse domain name `edu.grinnell.csc207.<project-name>` as the start of all our packages.
For example, if our project was called `myproject`, all files should be declared under the `edu.grinnell.csc207.myproject` package.

**Ordering of class methods and values**: Classes should be organized internally according to this order:

1. Import statements,
2. Fields,
3. Constructors, and
4. Methods

**Methods and classes should perform one specific and unique task**: Avoid creating a single method or class that "knows too much"; that is, a method or class which serves multiple disjoint purposes or is responsible for too much.
Such implementations are often unwieldy and difficult to extend or debug.
This is known as the principle of separation of concerns—each class and method should be responsible for one thing and one thing only.
Your program should be composed of a group of such classes which cooperate to achieve a goal.

**Use extension only to represent "is-a" relationships**: If you create a subclass, make sure that the subtype `A` "is a" version or variant of the supertype `B`.
If it is more appropriately described as a "has-a" relationship, then consider making `B` a field of `A` instead.

## Data Structures

**Consider using Sets to represent unordered data**: The abstract type `Set` is a good (though not always the best) candidate for representing data that does not require any particular ordering.
Sets also provide efficient verification of object membership.
Example implementation in Java: `TreeSet`.

**Consider using Lists to represent ordered data**: The abstract type `List` is a good (though not always the best) candidate for representing data with an imposed or sorted ordering.
Lists generally provide efficient insertion of elements at their endpoints.
Example implementation in Java: `ArrayList`.

**Consider using Maps to represent associations between data types**: The abstract type `Map` is a good (though not always the best) candidate for representing one-to-one associations or relationships between two objects.
Maps, like Sets, provide efficient verification of object membership, as well as efficient value lookup for a given key.

**Static types should be interfaces**: The declared static type of any collection should be a Java interface rather than an implementation of that interface. For example:

~~~java
Map<String, Integer> ids = new TreeMap<>(); // Map, not TreeMap
List<Point> points = new LinkedList<>();    // List, not LinkedList
~~~

## JUnit Testing

**Tests expecting exceptions**: If you are writing a test case that is expected to throw an exception, you should add an expected value to the `@Test` annotation.
For example, if we expect the body of our test function to throw a `IllegalArgumentException`, we would write `@Test(expected=IllegalArgumentException.class)` above the function.
It will fail if it does not raise an instance of this particular exception.
Do not use try-catch statements in place of the expected parameter.

**Use `assertEquals(a,b)` over `assertTrue(a.equals(b))`**: JUnit provides comparison assertions (which implicitly call objects' `equals` methods), but provide more useful failure information than `assertTrue`.

**Test floating-point values with a threshold**: Do not use JUnit's default `assertEquals(double expected, double actual)` method, as it is deprecated.
Instead, use `assertEquals(double expected, double actual, double epsilon)`, which allows for some wiggle room (an epsilon value) to account for the imprecision of floating-point values.
An epsilon value of 0.0001 usually suffices for most purposes.
