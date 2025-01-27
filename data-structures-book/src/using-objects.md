# Using Objects

Previously, we studied programming-in-the-small in Java, mapping our knowledge of C into this new, C-like language.
Next, we will explore *object-oriented programming* in Java and how this style of programming is fundamentally different from writing programs in C.

## What is an Object?

You may have heard the term "object" used in Racket or C as a synonym for "data".
However, in an object-oriented programming language like Java, *object* refers to a very specific kind of program entity:

~~~admonish info title="Definition (Object)"
An object possess *state* and *behavior*.
~~~

So far we have seen two types of objects in Java:

-   **Strings**: A string's state is the (immutable) sequence of characters it contains.
    An example of its behavior is the `trim()` method which produces a new string that is identical to the old string except the whitespace on either end is removed.

-   **Arrays**: An array's string is the sequence of elements it contains (accessed with index notation) along with its length (accessed via dot notation, i.e., `arr.length`).
    It has no associated behavior.

In Java, we realize the state of an object as *fields* or *instance variables* of an object.
Recall that a C `struct` is a collection of a data fields.
The instance variables of an object are analogous to these data fields of a `struct`.

We realize the behavior of an object with *methods*.
A method is a function that we "call on" a particular object.
For example with the `trim()` method above, if we have a variable `s` of type `String`, then we can call its `trim` method as follows: `s.trim()`.

### An Example of Object-oriented Design: a Student

When we design a program in an object-oriented style, we think about modeling the data of our program as a collection of objects that interact with each other through their methods.
For example, in a program that manages a course registration database, we will likely need to model a student.
In this context, what are the *state* and *behavior* of a student?
Here are some examples:

-   **State**: First name, last name, birthday, gender, age.
-   **Behavior**: Register (for a course), drop (from a course), withdraw (from the semester).

And we can consider other pieces of state and behavior as our problem demands it.
The state of the student becomes fields or instance variables of our student objects.
The behavior becomes methods, e.g., we might realize the register behavior as the method `void register(Course c)` which takes a `Course` object (yet to be defined) as input, registers the student for the course, and returns nothing.
But which student is registered for the course?
The student that we *call the method on*, for example:

~~~java
Student s = /* Some initialization... */ ;
Course csc207 = /* Some more initialization... */ ;

// Registers student `s` for csc207!
s.register(csc207);
~~~

We use *dot notation* to invoke a method on an object, just like how we use dot notation to access a field of an object, e.g., the `length` field of an array.
Unlike field access, however, we do not provide only the name of the method of interest, we also provide the list of arguments to pass to the method.
In effect, the student `s` is the *implicit* first argument to the method---it is neither specified in the method declaration nor provided in list of arguments to the method.

Note that we have not chosen all the possible pieces of state and behavior for our student objects.
For example, a student certainly has a height.
However, we likely do not need to know a person's height to manage their course registration.
This is the fundamental *design* question of object-oriented programming: how do we best and most concisely represent the salient features of the data in our program?
To answer this question, we must take into account the context in which we are designing the object, what types best capture our requirements, and whether we can be clever about our representation to avoid unneeded complexity.

### The Author and Client Perspective on Objects

Another way to look at objects is from two dual perspectives.
Someone is responsible for designing the objects we use, i.e., deciding what the state and behavior of that object is.
We call this party the *author* of the object.
Once we have a definition of an object, people can then use those objects in their own program.
We'll call these people *clients* of the object.

These two perspectives on objects are especially important for understanding the relationship between author and client when *interfaces* are involved.
There are potential obligations on either side of the object line---author or client---that must be met to guarantee that the program works correctly.
We'll discuss these various sorts of *pre-conditions*, *post-conditions*, and *invariants* in the next chapter.

For now, this division is important for our purposes because it outlines our trajectory for discussing objects.
We'll first talk about using objects as clients: creating already-defined objects, accessing their state, and invoking their behavior.
All of this will feel eerily like using `struct`s in C.
Then we'll talk about all the details of authoring objects---defining templates for objects called *classes* as well as all features we can throw into our objects to make them robust abstractions for others to use.

## The Client Perspective on Objects

As a client of objects, there are three fundamental operations we can perform:

1.  We create or *instantiate* objects.

2.  We access the state or *instance variables* of objects.

3.  We invoke the behavior or *methods* of objects.

We'll explore how we do each of these three operations in Java.

### Object Instantiation

For the two objects that we've seen so far, arrays and strings, we saw special syntax for their creation.
For arrays, we could either:

1.  Instantiate an array of a given size and type whose elements are initialized to be the default values of that type or

2.  Instantiate an array to an initial set of contents with special *array initialization syntax*.

~~~java
int[] arr1 = new int[20];                     // initialized to an array of 20 zeros.
int[] arr2 = new int[] { 0, 1, 2, 3, 4, 5 };  // initialized to an array containing 0--5
~~~

For strings, we used string literals to create strings, e.g., `"Hello World!"`.
However, we have already learned of an alternative way to create a string.
We can create a string from an array of characters as follows:

~~~java
char[] arr = new char[] { 'h', 'e', 'l', 'l', 'o' };    // Note: no null character!
String s = new String(arr);                             // The string "hello"
~~~

This is an example of the primary way of creating objects: *new expressions*.
In general, a new expression has the following form:

~~~java
new <class name>( <parameters> )
~~~

This syntax invokes the *constructor* of an object which:

1.  Allocates the memory for that object.

2.  Initializes that object given the arguments to the constructor.

Here, the expression `new String(arr)` creates a new string object by invoking the `String` constructor that takes a `char[]` as input.
The result is a string containing the characters found in that array.

Another example of object instantiation that we've seen is the `Scanner`:

~~~java
Scanner in = new Scanner(System.in);
~~~

`new Scanner(System.in)` creates a new `Scanner` object by invoking the constructor that takes an `InputStream` object as input.
(It turns out that `System.in` is an object of type `InputStream`.
More specifically, `in` is the object, and it is a *static* member of the `System` class, an important distinction we'll discuss shortly.)

### Accessing State

To access the state of an object, we use *dot notation*.
It has the following syntax:

~~~java
<object>.<field name>
~~~

An example of this syntax is accessing the length of an array:

~~~java
int[] arr = new int[5];
System.out.println(arr.length);
~~~

Think of the dot ('.') as a *binary* operation like addition, except that the left-hand side of the dot is an expression that must evaluate to an object and the right-hand side is the name of the field of the object that we would like to access.

Normally, we would be able to *mutate* or change a field that we have access to, just like how we can modify the fields of `struct`s in C.
However, if we try to do this to an array we get the following error:

~~~
arr.length = 6
/* ERROR: cannot assign a value to final variable length */
~~~

This is because the `length` field of the array is constant or `final`; it cannot be changed.

### Invoking Behavior

Recall that the behavior of an object corresponds to the set of *methods* that the object exposes to a client.
To invoke a method on an object, we also use dot notation:

~~~java
<object>.<method name>(<parameters>)
~~~

For example, we used the `charAt` method of String objects extensively in previous labs and homework:

~~~java
String isbn = "123456789X";
System.out.println(isbn.charAt(9));   // 'X'
~~~

`s.charAt(n)` fetches the `n`th character from the string `s`.
Here, the object is `s`, a string, and the name of the method is `charAt`.
The single parameter to `charAt` is the index of the `char` we wish to fetch.

Method invocation looks a lot like function calls and indeed they are very similar.
If we're not being ultra-precise about our language, we may call `charAt` a function call or function call-looking thing a method invocation, and that is fine when discussing code casually.
However, let's make the distinction between the two explicit, especially since we are coming from a C background:

1.  A *method invocation* is always invoked on a particular instance of an object, namely, the object that appears to the left of the dot.

2.  A *function call* or *application* is not invoked on a particular object.
It looks like the normal C function calls that you are used to.

As we transition into the world of objects, it'll be important to keep the two types of function-like calls straight in our head.

~~~admonish question title="String Methods"
In a class called `StringMethods`, write a `static` method called `endsWithRep` with method signature:

```java
public static boolean endsWithRep(String s1, String s2, int n)
```

That returns `true` if `s1` ends with `n` repetitions of `s2`.

Here are some example invocations of `endsWithRep`:

```java
endsWithRep("foobarbar", "bar", 2);    /* returns true  */
endsWithRep("foobarbar", "baz", 1);    /* returns false */
```

To write this method, you can use string concatenation (the `+` operator) to build up a string that is `n` repetitions of `s2` and the `boolean endsWith(String suffix)` method of the `String` class which returns `true` if `suffix` is indeed a `suffix` of the string the method is called on.
In the `main` method of `StringMethods`, demonstrate that your method works by printing the results of the two examples above to the console.
~~~
