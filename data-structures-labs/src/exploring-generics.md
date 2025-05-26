# Exploring Generics

In today's lab, we'll explore how generics can both help us:

1.  Write less redundant code.
2.  Capture in a machine-checkable fashion that our code frequently doesn't care about the _identity_ of the values it manipulates.

To begin, feel free to fork the following Github repo to your personal account:

*   <https://github.com/psosera/genericslab>

And clone down a copy of your repo to your local machine.
Additionally, also add your partner as a collaborator to the repository on your personal Github account (Settings → Collaborators), so they can have access to your work when you are done!

## Part 1: Generic Linked Lists

`genericslab` contains two implementations of our linked list data structure, one specialized to integers (`LinkedListInt`) and one specialized to strings (`LinkedListString`).
Use generics to write a generic `LinkedList` class that makes the specialized versions unnecessary.

Additionally, the current project includes a test suite for both `LinkedListInt` and `LinkedListString`.
Create a new test suite `LinkedListTests` that unifies the two test suites into a single test suite that adequately covers your generic `LinkedList` class.

Importantly, your `LinkedListTests` suite should be _minimal_ in the sense that you include only the tests necessary to fully cover all possible cases of `LinkedList`.
You should take advantage of the fact that within a generic class or method, the code cannot presuppose anything about the potential instantiations of its type variables.

## Part 2: Generic Array Lists

`genericslab` also contains an implementation of array lists specialized to `int`s in the `ArrayList` class.
Modify this class so that it is generic in the type of elements the list holds.
Copy your fully pruned `LinkedListTests` file to a new `ArrayListTests` file to ensure that everything works.
(_Note_: at this point, you should be able to search-and-replace `LinkedList` to `ArrayList` in the file!
Totally disagreeable, but the best we can do for now.
We'll deal with this kind of redundancy in our next class!)

## Part 3: Generic List Operations

Choose either of your list classes and attempt to implement each of the following methods for that class.
If you are unable to implement the method, have the method throw an `UnsupportedOperationException()`:

~~~java
throw new UnsupportedOperationException();
~~~

And in the method's Javadoc comment, explain why the method cannot be implemented.

*   `void intersperse(T sep)`: inserts `sep` in _between_ each element of this list.
*   `T maximum()`: returns the maximum element found in the list.
*   `String toString()`: returns a string representation of the list in the form: `[x1, x2, ..., xk]` where `x1`, `x2`, ..., `xk` are the elements of the list.
*   `void insertionSort()`: sorts this list using the _insertion sort_ algorithm.
    Insertion sort proceeds by looping over the elements of the array, maintaining the following invariant:

    ~~~
    [ <sorted region> | <unsorted region> ]
                        ^
                        i
    ~~~

    In other words, the left-hand side of the array consists of the first `i` elements of the array in sorted order.
    `i` is the index of the first element of the right-hand side of the array which is the unsorted region of the array.
    Insertion sort maintains this invariant by taking the `i`th element and inserting it into the sorted region in sorted order.

## Part 4: The Optional<T> Data Structure

Now, let's design a new data structure, the `Optional` datatype that addresses a common problem found in Java: `null` pointers.
While Java does not explicitly have pointers, since any reference type can be `null`, the `null` value is frequently used to quickly denote that "nothing" has been returned, usually due to some erroneous condition being met.

However, this design choice frequently leads to spurious `NullPointerExceptions` that are difficult to track down and diagnose.
This problem is so bad that computer scientist Sir Tony Hoare, the creator of the null reference calls it his "billion-dollar mistake" for all the damage to the software industry null pointers have caused!

### 4.1: Why Optional?

First, let's explore where using `null` references can lead to ruin.
As a simple example, consider the `HashMap` class which efficiently implements a dictionary-style data structure, mapping keys to values.
A critical method of this class is `V get(K key)` which given a key value of type `K` returns the value of type `V` associated with that key.

Review this method's documentation to see how it uses `null`:

*   <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html#get(java.lang.Object)>

Brainstorm a list of the potential problems that `get`'s usage of `null` can produce in our programs if we aren't careful.

### 4.2: The Optional Class

We can alleviate the problems you identified in the previous part by defining a new type that makes it explicit that a value _may_ be present.
In effect, this type is a small data structure that either holds zero or one element!

The `Optional` class in the standard library fulfills the purpose, and for this part of the lab, we'll replicate the basic functionality of `Optional` to see how this little class works.

Complete the definition of `Optional` in the lab as follows.

First, add no-argument constructor `private Optional()` that does nothing.
This seems odd, but what we are doing with this is _restricting_ how an `Optional` value can be created.
We'll force the user to create an `Optional` through a pair of `static` generic methods that you should implement:

*   `static <T> Optional<T> empty()` creates a new, empty `Optional` value.
*   `static <T> Optional<T> of(T value)` creates a new `Optional` value that contains `value`.

With these static functions in place, implement the following basic methods of the Optional class:

*   `boolean isEmpty()` returns `true` iff this `Optional` does not have a value.
*   `boolean isPresent()` returns `true` iff this `Optional` has a value.
*   `T get()` returns the value held in the optional or throws `NoSuchElementException` if the value is not found.
*   `T orElse(T other)` returns the value held in the optional or `other` if the `Optional` is empty.