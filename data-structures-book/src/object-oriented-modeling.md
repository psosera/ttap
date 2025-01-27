# Object-oriented Modeling

Now that we have seen how to interact with objects from the client perspective, let's now discuss how to specify objects.
To do this, we'll formally introduce the *class* construct that has been in every one of our Java programs so far.

## Anatomy of a Class

Recall that an object is a programming entity that contains state and behavior.
To specify what kinds of state and behavior an object contains, we use *classes*.
We say that an object is an *instance* of some class.
For example, recall from the previous section we defined a Student and defined the following pieces of state and behavior for it:

- **State**: First name, last name, and age.
- **Behavior**: Register, drop, and withdraw.

Here is how we would take these pieces of state and behavior and define them in a Java class:

~~~java
public class Student {
    public String firstName;
    public String lastName;
    public int age;

    public Student (String firstName, String lastName, int age) {
        /* ... */
    }

    public boolean register(String course) {
        /* ... */
    }

    public void drop(String course) {
        /* ... */
    }

    public void withdraw() {
        /* ... */
    }
}
~~~

For the time being, we've elided the implementations of the constructor and methods.
But regardless, we can use this class as follows:

    Student s = new Student("Ada", "Lovelace", 23);
    s.register("csc 207");

The state of an object translates into *field* or *instance variable declarations* in our class.
You should recognize these from C; these look and behave the like the field declarations of a `struct` definition.
The behavior of an object translates into *method* definitions.
These are like function declarations but appear within the class definition *without the `static` modifier*.
Adding `static` no longer associates the function with an instance of the class---a distinction that made the function a method---but rather with the class itself.
We'll explore this distinction in more detail later as it is one of the greatest points of confusion for students transitioning from the programming-in-the-small world of C to the object-oriented world of Java.

The `public` annotations on the class, the fields, and the methods determine the *visibility* of that particular program entity.
Keeping the client versus author distinction in mind, an entity marked `public` is usable to everyone---clients and author alike.
In contrast, an entity marked `private` is usable only by the author.
This is useful for hiding fields and methods that concern the *implementation* of the class, e.g., auxiliary functions and state that we don't want the outside world to know about.

A class is a sort of swiss-army knife in object-oriented languages like Java; it does a bunch of stuff:

-   Classes act as a blueprints for objects as we discussed before.

-   Classes are the way of defining *user-defined* types in Java.

-   Classes act as a *namespace* for collections of `static` functions and variables.

-   Classes act as an abstraction mechanism separating *features* from *implementation* through *interfaces*.

It is easy to conflate all of these features together, especially if Java is your first language.
However, with multiple languages under our fingertips, we can see that all of these features serve their own distinct purposes.

## Class Declaration Syntax

Class declarations take on the following form:

~~~java
<visibility modifier> class <class name> {
    <field and method declarations>
}
~~~

A program in Java is defined to be a collection of class declarations.
The class declaration may optionally be preceded by a visibility modifier, e.g., `public` or `private`.
The visibility modifier may be left out which turns out to have a different meaning from `public` or `private` ("package"-protected which we'll discuss later when we talk about Java's package system).

A class definition contains a number of *declarations*:

1.  Field declarations.

2.  Method declarations.

3.  Constructor declarations.

Field declarations look like local variable declarations but exist outside of any particular method but inside a class definition:

~~~java
<visibility modifier> <type> <name>;
~~~

For example, `public String name;` in the declaration of `Student` above declares a field of type `String` named `name`.
Every instance of a `Student` has their own `name` field.

Method declarations look a lot like the function declarations we have seen so far:

~~~java
<visibility modifier> <type> <name>( <arguments> ) {
    <statements>
}
~~~

Except that there is no `static` in the signature of the method.
As discussed earlier, this is the distinction between a *method*, a function tied to a particular object, and a *static function*, a function not tied to a particular object but the overall class.

## Constructors

Recall that fields and methods gives us way of using objects.
Constructors give us ways of creating objects, a process called *instantiation*.
The constructor defines how we should *initialize* a freshly-created object of the given class.
We define a constructor in a class similarly to how we define a method except:

1.  The name of the method is the name of the class.

2.  There is no return type.

For example, recalling the constructor of our `Student` class above:

~~~java
public Student (String firstName, String lastName, int age) {
    /* ... */
}
~~~

This is a constructor that takes three arguments: two strings corresponding to the person's name as well as their age.
This constructor allows us to create a `Student` using a `new` expression as follows: `new Student("Ada", "Lovelace", 23)`.

## The "this" Keyword

In the example above, we have omitted the implementation of the constructor.
What should the constructor do to initialize a new `Student`?
A sensible sketch of an approach is:

~~~java
public Student (String firstName, String lastName, int age) {
    // Initialize the firstName field of Student with the parameter firstName
    // Initialize the lastName field of Student with the parameter lastName
    // Initialize the age field of Student with the parameter age
}
~~~

However, how do we access the fields of the object that we are creating?
Recall that the syntax of a field access is `<object>.<field name>`, but what goes to the left-hand side of the dot?
If we need to refer to the object that we are currently instantiating (in a constructor) or called the method on, we use the `this` keyword:

~~~java
public Student (String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName  = lastName;
    this.age       = age;
}
~~~

In a method or constructor, `this` is an expression that evaluates to the object that is the subject of the constructor or method call.

## An Example: The Counter Class

As a complete example to study, let's consider creating a class that represents a simple counter that we can increment.
What is the state and behavior of a counter?

- **State**: The current value of the counter, an integer.
- **Behavior**: Incrementing the counter.

Now let's translate this into a simple class:

~~~java
public class Counter {
    public int value;

    public Counter() {
        this.value = 0;
    }

    public void increment() {
        this.value += 1;
    }
}
~~~

Here's an example of using this `Counter` class:

~~~java
Counter c1 = new Counter();
Counter c2 = new Counter();
System.out.println(c1.value);   // 0
System.out.println(c2.value);   // 0
c2.increment();
c1.increment();
c1.increment();
c2.increment();
c2.increment();
System.out.println(c1.value);   // 2
System.out.println(c2.value);   // 3
~~~

Note that each instance of the `Counter` possesses a distinct `value` field.
So each call to `increment()` increments the `value` field of the counter that the method is called on.

A final note: because we annotated `value` with the `public` visibility modifier, anyone can change the value of a counter.
For example:

~~~java
c1.value = 5;
System.out.println(c1.value);   // 5
System.out.println(c2.value);   // 3
~~~

This may be fine for our simple purposes, but we may want to `hide` this field so that non-counter code cannot change the value directly.
We can accomplish this by marking the field `private` instead of `public`.
We'll discuss the design considerations that may compel to choose one modifier over the other in more detail shortly.

~~~admonish question title="An Example Class: Dogs"
Define a class called `Dog` in an appropriately named Java file that defines a class that represents dogs.
Define your `Dog` so that it has at least three fields, a constructor, and a method.
You may define whatever fields and methods for your `Dog` class that you would like.
(I didn't give you any additional parameters so you can design your class within any context you desire).
If you are at a loss for creativity, one recommendation is to define your method to be a `bark` method which returns nothing and makes the dog bark the value of its properties to the console.
~~~

## Thinking with Objects

In Java, we decompose our problems not in terms of mathematical functions---a _functional_ style---as in Racket, not in terms of procedures (functions with side effects)---a _procedural_ style---as in C but in terms of objects---an *object-oriented style*.
At a first glance, the differences between functional or procedural and the object-oriented programming seem insignificant.
We still have to think about both data and functions in either a functional or procedural style, and we frequently reason about them together.
However, in Java, we unite data and methods together under the all-encompassing object construct.
This simple change in code organization fundamentally changes the way that we approach program design in Java.

The classes that we identify, design, and implement in order to solve our problem become little packages of code.
Ideally, these little packages satisfy a few properties:

-   They are _small and as simple as possible_.
    It would defeat the purpose of decomposing a problem into classes if the classes were as complex as the original problem!
-   Related, they are _limited in scope_, ideally, serving only a single distinct purpose in the overall program.
-   They function as _independent units_ (as much as possible).
    This allows us to reason about the packages for correctness independently, either during debugging or testing.
-   They possess a _well-defined interface_ with clear guarantees about inputs and outputs.
-   We are able to _hide_ the details of the package that are unnecessary for clients of the package to know about, i.e., its implementation details.

The process of bundling data and behavior together into packages that satisfy these properties is called _encapsulation_.
Java allows us to accomplish this with the _class_ construct.
However, classes alone only allow us to bundle code; it is up to us to enforce these properties through good object-oriented design principles.

## Abstraction

The first three properties imply that our classes should be kept small and specific in their purpose.
The final two properties deal with _abstraction_, the hiding of a system's implementation through an interface.
For example, consider the `Counter` class we've used as our running example so far:

~~~java
public class Counter {
    public int value;

    public Counter() {
        this.value = 0;
    }

    public void increment() {
        this.value +=1 ;
    }
}
~~~

The interface that a class specifies (for one of its instances) contains all (accessible) fields and methods of that class.
For example, the `Counter` class exposes:

- A way to construct an instance via a no-argument constructor,
- A `value` field that is the current value of the counter, and
- An `increment()` method to increment the counter.
\end{itemize}

These three things constitute a user's _interface_ to a counter.
One suspicious design decision here is that we have exposed the \texttt{value} field to the user by marking it \texttt{public}
This may be undesirable because a user can set the value of a counter to any value that they want, e.g.,

~~~java
Counter counter = new Counter();
counter.value = -42;
~~~

If we wished to restrict the counter from ever going negative or more specifically, only allow a user to change the value of the counter through `increment()` then this choice of interface is not sufficient.
However, we can't simply remove `value` from the class because it needs it to keep track of the number of calls to `increment()`!
We need some mechanism to _hide_ `value` from clients but still keep it around so that a counter can use it internally.

In Java, we accomplish this sort of hiding of members with _privacy modifiers_.
So far, we have seen the `public` privacy modifier which makes a member visible to everyone.
We fix this problem by modifying the _value_ field with the _private_ modifier which makes a member visible to only the class containing the member.

~~~java
public class Counter {
    private int value;

    public Counter() {
        this.value = 0;
    }

    public void increment() {
        this.value +=1 ;
    }
}
~~~

Now, with the `value` field marked as `private`, clients of the class can no longer access it.
In particular, the code above that changes the counter's value to `-42` produces the following compiler error:

~~~console
Counter.java:16: error: value has private access in Counter
        c.value = -42;
         ^
1 error
~~~

This is what we want!
But there is one problem: we cannot access `value` at all!
For example, if we wanted to print out `value`:

~~~java
System.out.println(counter.value);
~~~

We receive the same error.

So how do we fix this problem?
We create an alternative route to access `value`: a `public` method that simply returns `value`:

~~~java
public class Counter {
    private int value;

    public Counter() {
        this.value = 0;
    }

    public void increment() {
        this.value +=1 ;
    }

    public int getValue() {
        return value;
    }
}
~~~

Now, we can use `getValue()` to retrieve `value` without exposing a way to change it via assignment.

~~~java
System.out.println(counter.getValue());
~~~

Such a kind of method that simply returns the value of a field is so commonplace in Java that we have a special name for it: a _getter method_.
If we also wanted to enable a client to change a field, we could create a corresponding _setter method_:

~~~java
// In Counter...
public void setValue(int value) {
    this.value = value;
}
~~~

A getter and setter method combined provides all functionality that a _public_ field provides but does not actually expose the field itself.
So why would we want to create both a getter and setter?
There may be an _invariant_ of the counter---a property---that we would like to preserve, e.g., that the counter should never be negative:

~~~java
// In Counter...
public void setValue(int value) {
    if (value >= 0) {
        this.value = value;
    } else {
        throw new IllegalArgumentException();
    }
}
~~~

We can use _setValue_ to enforce this property.
If the user provides an inappropriate argument, then we signal an error with an exception.
We'll discuss these mechanics and design considerations when we talk about interfaces in the next chapter.

## Comments and Style

When designing abstractions, we rely on our type system (when available) to enforce those abstractions.
Take a look at the signature of `setValue(value)` again:

~~~java
public void setValue(int value) { /* ... */ }
~~~

Java enforces that we must call `setValue` with exactly one argument and that argument must be an `int`.
However, the signature alone does not tell the user that they are not allowed to provide a non-negative argument.
The exception we threw in the implementation signals to the user that they messed up, but at runtime.
Ideally we would like to catch these sorts of errors at compile time, but we have no way of enforcing these properties with Java.
Instead, we must resort to documenting them with comments.

Java provides excellent facilities for commenting code: _Javadocs_.
Here is an example of using the Javadoc facilities in Java:

~~~Java
/**
 * Sets the value of the counter.  This value must be non-negative.
 *
 * @param value the new, non-negative value the counter.
 * @throws IllegalArgumentException if a non-negative value is given.
 */
public void setValue(int value) { /* ... */ }
~~~

Javadocs are special comments above declarations of program elements (e.g., classes or methods).
They start with `/**` and end with `*/` delimeters.
They include special `tags` for various parts of the documentation.
The most important of these for methods are:

- `@param <name> <description>`: Used to document a method parameter.
- `@return <description>`: Used to document a return value.
- `@throws <name> <description>: Used to document an exception the method may throw.

When building rich abstractions, comments become a necessary tool to ensure people know how to use your code and what to expect from it!

~~~admonish question title="The Student Class Revisited"
Fix the version of the `Student` class below so that it (a) does not expose its fields directly and (b) has appropriate Javadoc comments.
Your updated class should use privacy modifiers and setter and getter methods to expose read/write access to fields as necessasry.
Your Javadoc comments should contain tags for the return values and parameters of any methods or constructors that you document.

```java
public class Student {
    public String firstName;
    public String lastName;
    public int id;
    public int age;

    public Student (String firstName, String lastName, int id, int age) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.id        = id;
        this.age       = age;
    }
}
```
~~~
