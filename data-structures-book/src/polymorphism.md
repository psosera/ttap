# Polymorphism

Previously, we developed two implementations of the list abstract data type: the array list and the linked list.
As is, our code has two significant shortcomings:

1.  The _carrier type_ of the lists, i.e., the type of elements the list holds, is fixed.
2.  Even though the two lists expose identical methods to users, the list implementations are _not_ interchangeable.

We can address these shortcomings by utilizing Java's support for _type polymorphism_.
Polymorphism literally means "many forms."
Type polymorphic code, therefore, is code that can be applied to many types.
Java supports two different kinds of type polymorphism that directly address these concerns:

1.  _Parametric polymorphism_ allows us to parameterize a class or method by a type, similar to how functions parameterize statements by a value.
2.  _Subtype polymorphism_ allows us to specify a _subtyping relationship_ between types that states in a machine-checkable fashion that one type has all the functionality of another type.

## Parametric Polymorphism

As a starting point, let's consider the `Node` class that supports our `LinkedList`:

~~~java
public class Node {
    int value;
    Node next;
    public Node(int value, node next) {
        this.value = value;
        this.next = next;
    }
}
~~~

This implementation of `Node` is fixed to hold `int` values.
If we wanted to hold other elements in our linked list, we would need to design another `Node` class, e.g., a `StringNode` class that holds `String`s instead of `int`s:

~~~java
public class StringNode
    String value;
    node next;
    public node(String value, node next) {
        this.value = value;
        this.next = next;
    }
~~~

You should find this strategy highly offensive!
This approach clearly leads to redundant code.
Notably, we can see from inspection of the `Node` class (and the `LinkedList` class, too) that the implementation of the `Node` class does not _depend_ on the type of `value` in any way.
In particular, all our linked list data structure does to values are:

1.  Store values in nodes and
2.  Shuffle values in and out of methods.

Whenever we encounter code that behaves similarly, i.e., does not care about the type of the values it manipulates, we have an opportunity to use parametric polymorphism to (a) reduce redundancy and (b) enshrine this non-dependency of types in our code.

### Generics

Java's _generic type_ mechanism allows us to make our code parametrically polymorphic by parameterizing a chunk of code, either a class or a method in Java, by one or more types.
As a first example, we can write our Node using generics as follows:

~~~java
public class Node<T> {
    T value;
    Node<T> next;
    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }
}
~~~

We declare a _generic class_ by specifying one or more _type paramaters_ to our class.
These type parameters are introduced alongside the name of the class declaration:

~~~java
public class Node<T> {
~~~

Here, we introduce a new type parameter `T` to our class, exactly analogous to the (value) parameters we specify in function declarations.
`T` is a _type variable_ that will be filled in or _instantiated_ by a user of the `Node` class.
Again, analogous to function parameters, we can use `T` throughout the definition of `Node`, and all occurrences of `T` will be replaced with the type that the user specifies upon instantiation.

To instantiate a generic class with an actual type, we use distractingly similar syntax to the class declaration.
For example, to create a `Node` specialized to `String`, we would write:

~~~java
Node<String> n = new Node<String>("hi", null);
~~~

We use the same angle bracket syntax to "call" or instantiate a generic class.
Note how we have to do this whenever we use the `Node` class: as the type of `n` and to invoke the `Node` constructor.

The need to provide the type instantiations for all usages of generic classes is quite onerous.
Consequently, Java provides rudimentary _type inference_ where it will deduce the instantiating type from the surrounding context, in particular for constructors.
Type inference allows us to write the following shorter declaration:

~~~java
Node<String> n = new Node<>("hi", null);
~~~

Where the instantiated type of `Node<>` is derived from the type of `n` which is known to be `Node<String>`.

Interestingly, because the `Node` is a recursively-defined type—`Node` objects hold a reference to a `Node`—we also instantiated the `Node` type within the `Node` class, e.g., on the field declaration for `next`:

~~~java
Node<T> next;
~~~

This occurrence of `Node<T>` is actually an instantiation of the `Node` class where we pass the type variable `T` to the class!

Less common in Java, but an occasionally useful fact is that we can also make individual methods generic.
For example, we can write a truly generic swap method as follows:

~~~java
public static <T> void swap(T[] arr, int i, int j) {
    T tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
}
~~~

Observe how `swap` is an excellent candidate for being generic.
`swap` does not care about _what_ it is swapping, so we would be writing highly redundant code to swap the contents of a `String` array versus an `Integer` array.

Note how the syntax for introducing a generic type variable in a method is different from a class.
We introduce type variables by placing the `<T>` before the _return type_ of the method.

Luckily, to use a generic method, we can simply call the method like normal, and Java will infer the generic type instantiation for us:

~~~java
String[] arr = /* ... */;
swap(arr, 1, 3);
~~~

Finally, we can introduce multiple type parameters to either a generic class or method by providing a comma-separated list of type variables.
For example, a generic _pair type_ that holds two elements with potentially different types can be declared as:

~~~java
public class Pair<T, U> {
    public T first;
    public U second;
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
~~~

~~~admonish question title="Cell"
Write a generic class `Cell<T>` that is a data structure that holds a _single_ value of type `T`.
`Cell<T>` exposes the following public methods that you should implement:

*   `Cell<T>(T value)` constructs a cell that holds `value`.
*   `T get()` retrives the `value` held by the cell.
*   `void set(T value)` mutates the cell so that it holds a new `value`.
~~~

### Wrapper Classes

You may have noticed that we instantiated our `Node<T>` class with `String`, but what about `int`?
It turns out that we run into issues if we attempt this:

~~~java
Node<int> n = new Node<>(42, null);
~~~

This code produces the following error:

~~~terminal
Test.java:13: error: unexpected type
        Node<int> n = new Node<>(42, null);
             ^
  required: reference
  found:    int
~~~

This error arises because Java requires that we only pass _reference types_ to generics.
In other words, we cannot instantiate generics with our seven primitive types.

But, we clearly want to have generic structures that contain primitives, e.g., linked lists of `int`s or `boolean`s!
The way to accomplish this in Java are with the standard library _wrapper classes_, also called _boxed types_, which wrap up each of the seven primitive types into an object that can be used in generic code:

*   `boolean` ⟶ `Boolean`.
*   `char` ⟶ `Character`.
*   `int` ⟶ `Integer`.
*   `long` ⟶ `Long`.
*   `float` ⟶ `Float`.
*   `double` ⟶ `Double`.

(These seven classes are in the `java.lang` package, so they are automatically imported in every Java program, so you can use them without `import` statements!)

Thus, if we want a `Node` that contains `int`, we use the boxed type `Integer` instead:

~~~java
Node<Integer> n = new Node<>(/* ... */);
~~~

However, how do we create an `Integer` from an `int`?
We can explicitly create an `Integer` from an `int` using the `Integer.valueOf(int value)` static method.
Each wrapper class provides similar `valueOf` static methods for converting from primitive types to the wrapper type.
Dually, the wrapper classes also provide conversion methods in the other direction, e.g., `int intValue()` of the `Integer` class returns the `int` wrapped by this `Integer` object.

This is yet another pain to go through!
So Java provides a mechanism, _autoboxing_, that automatically converts between primitive types and boxed types.
Autoboxing allows us to complete the declaration of our node without any extra code (other than using `Integer` rather than `int`):

~~~java
Node<Integer> n = new Node<>(42, null);
int v = n.value;
~~~

The `42` here is automatically _boxed_ by Java into an `Integer` and passed to the `Node` constructor where it is stored.
On the next line, the `42` stored in `n.value` as an `Integer` is automatically _unboxed_ into an `int`, which is then stored in `v`.

~~~admonish question title="Generic Linked List (‡)"
Using `Node<T>` as a starting point, make your `LinkedList` implementation fully generic.
Your `LinkedList` class should have, at a minimum, the following methods:

*   `LinkedList()`.
*   `void add(T value)`.
*   `T get(int index)`.
*   `int size()`.
~~~

## Subtype Polymorphism

Another point of disagreeableness in our list implementation, how they share the same set of methods, and how we can't take advantage of this fact!
What do I mean by this?
Recall the methods of our list abstract data type (now made parametric polymorphic with generics):

~~~java
public void add();
public T get(int index);
public int size();
public T remove(int index);
~~~

Since both classes implemented these methods, we ought to expect that any code that only uses these list methods should not care about whether we use a `LinkedList` or an `ArrayList`.
While the two implementations have different internal behavior—especially when we think about complexity!—they are _substitutable_ with respect to being list.
If I need any list, I should be able to swap out a `LinkedList` or `ArrayList` at will with minimal changes to my code.

This need for substitutability becomes very apparent with testing, something you may have noticed when developing your two list implementations.
Recall the trace from the previous chapter demonstrating basic usage of the list abstract data type, now realized as a JUnit test:

~~~java
@Test
public void arrayListBasicUsageTest() {
    ArrayList<Integer> lst = new ArrayList<>();
    assertEquals(0, lst.size());
    lst.add(5);
    lst.add(9);
    lst.add(7);
    assertEquals(3, lst.size());
    assertEquals(9, lst.get(1));
    assertEquals(9, lst.remove(1));
    assertEquals(7, lst.get(1));
    assertEquals(2, lst.size());
}
~~~

Now suppose we wanted to write a similar test for our linked list.
Because the linked list shares the same operations as the `ArrayList`, we expect the test proceeds identically.
That is true!
However, the test is specialized to `ArrayList` is already.
So our only recourse is to _duplicate_ the test for our `LinkedList` class:

~~~java
@Test
public void linkedListBasicUsageTest() {
    LinkedList<Integer> lst = new LinkedList<>();
    assertEquals(0, lst.size());
    lst.add(5);
    lst.add(9);
    lst.add(7);
    assertEquals(3, lst.size());
    assertEquals(9, lst.get(1));
    assertEquals(9, lst.remove(1));
    assertEquals(7, lst.get(1));
    assertEquals(2, lst.size());
}
~~~

Ugh.
Duplicated code again!
We would like to write a helper function where we can pass in _any_ class that has the methods of the list abstract datatype.
We can then create two tests, each of which instantiates one of our lists and passes it to our helper:

~~~java
public void listBasicUsageTest(/* ??? */ lst) {
    assertEquals(0, lst.size());
    lst.add(5);
    lst.add(9);
    lst.add(7);
    assertEquals(3, lst.size());
    assertEquals(9, lst.get(1));
    assertEquals(9, lst.remove(1));
    assertEquals(7, lst.get(1));
    assertEquals(2, lst.size());
}

@Test
public void linkedListBasicUsageTest() {
    listBasicUsageTest(new LinkedList());
}

@Test
public void arraylistBasicUsageTest() {
    listBasicUsageTest(new ArrayList());
}
~~~

But, what type can we give to `lst`, now a parameter to `listBasicUsageTest`, that means "either `ArrayList` or `LinkedList`?"
We need to define such a type using the _interface_ construct of Java.
An interface declaration is similar to a class in that it defines the name of a new type.
However, it differs from a class in that it provides _no implementation details_.
Instead, an interface specifies a set of method signatures that any _implementing class_ of that interface must define!
In turn, that class can be used where ever the interface type is expected.

We, therefore, first need to define an interface that captures what it means to be a list.
We'll call this the `List` interface (which, like public classes, would need to exist in its own file, `List.java`):

~~~java
public interface List<T> {
    public void add();
    public T get(int index);
    public int size();
    public T remove(int index);
}
~~~

We can then specify that our `ArrayList` and `LinkedList` classes implement the `List` interface through an `implements` clause:

~~~java
public class ArrayList<T> implements List<T> { /* ... */ }
~~~

As a result of this declaration, the compiler will check to ensure that `ArrayList` implements the four methods specified in the `List` interface.
For example, here is the implemented `size` method for `ArrayList`:

~~~java
public class ArrayList<T> implements List<T> {
    T[] data;
    int size;
    /* ... */

    @Override
    public int size() {
        return size;
    }
}
~~~

Observe that we implement the method in exactly the same way we did beforehand!
The only difference is that modern versions of Java strongly recommend that we acknowledge that methods like `size` are implementing interface behavior via the `@Override` annotation.
(If you exclude this annotation, you will get a warning!)
The term "override" comes from the fact that the method participates in _dynamic dispatch_, a feature of object-oriented languages like Java where the version of a method to invoke is chosen at runtime.
With interfaces, there is only choice, so this decision is easy, but as we shall see later with class inheritance, we can introduce several possible candidate implementations for a method call.

Again, note that while interfaces look like classes, they are not actually classes!
In particular, we cannot instantiate an interface:

~~~java
List<Integer> l = new List<>();   /* Compiler error! */
~~~

In this sense, you can think of an interface as a _contract_ that any class can participate in.
If a class fulfills this contract (by implementing all the required methods of the interface) then it may be considered the interface type.

### Interfaces and Subtyping

With the `List` interface defined and `ArrayList` and `LinkedList` both implementing the interface, we can now complete the definition of `listBasicUsageTest`:

~~~java
public void listBasicUsageTest(List<Integer> lst) {
    assertEquals(0, lst.size());
    lst.add(5);
    lst.add(9);
    lst.add(7);
    assertEquals(3, lst.size());
    assertEquals(9, lst.get(1));
    assertEquals(9, lst.remove(1));
    assertEquals(7, lst.get(1));
    assertEquals(2, lst.size());
}
~~~

The `List` interface type becomes the "umbrella" type under which we can provide an `ArrayList`, `LinkedList`, or any other class that implements the `List` interface.

Importantly, interfaces form an important relationship between types.
We say that the `ArrayList` and `LinkedList` types are _subtypes_ of the `List` type.
More generally, if $S$ and $T$ are types, we write (on paper) $S <: T$ to mean that $S$ is a _subtype_ of $T$.
If $S <: T$, then values of type $S$ are _substitutable_ whenever values of type $T$ are expected.

We see substitutability occur within variable assignment, either passing a value to a function or a variable declaration.
For example, here is the same substitutability principle in play for variable declarations:

~~~java
List<Integer> l1 = new ArrayList<Integer>();
List<Integer> l2 = new LinkedList<Integer>();
~~~

Our notion of substitutability depends on the programming language in question and its goals.
With Java, and most object-oriented programming languages, we can think of substitutability in terms of capabilities:

> If we know $S$ is a subtype of $T$, then $S$ necessarily supports all the operations of a $T$.

Interface implementation clearly satisfies this property.
A class $S$ that implements an interface $T$ necessarily provides all the methods demanded of the interface.
Thus, we should be able to use a $S$ where ever a $T$ is expected!

### Static and Dynamic Types

Normally, a variable contains exactly the type of value assigned to the variable.
For example:

~~~java
String s = "hello world!";
~~~

`s` is a variable of type `String` that is assigned a value that is also a `String`!

In the presence of subtyping, however, the type of a variable can be _different_ from the type of value assigned to that variable!
For example, revisiting our `List` variable assignment above:

~~~java
List<Integer> l1 = new ArrayList<Integer>();
~~~

The type of the variable `l1` is `List<Integer>` because we recall the syntax of a variable declaration:

~~~java
<type> <identifier> = <expr>;
~~~

And note that the `<type>` to the left of the name of the variable is its declared type.
However, the actual value assigned to `l1` is not a list—indeed, we can't instantiate a `List` because it is an interface!
Instead, it is a known subtype of the `List` type, `ArrayList` in this case.

In Java, we distinguish these two "kinds" of types as follows:

+   The _static type_ of a variable is the variable's declared type.
    This is the type used the typechecking phase of compilation.
+   The _dynamic type_ of a variable is the type of the actual value assigned to the variable.
    We use the term "dynamic" to denote that this is the type known during runtime.

Static versus dynamic typing can be confusing nomenclature, so I prefer using the less overloaded phrases "variable type" and "runtime identity (of an object)" to distinguish between the two concepts.
However, "static" and "dynamic" is the common language used in object-oriented programming, so we should be comfortable with it!

To see the effect of this distinction, suppose that our `ArrayList` class exposed a method not found in the `List` interface, e.g., `trimToSize()` which reduces the backing array to fit exactly the elements of the list.

~~~java
public class ArrayList<T> {
    private T[] data;
    int size;

    // ...

    public void trimToSize() {
        data = Arrays.copyOf(data, size)
    }
}
~~~

If our `ArrayList` class has this method, observe how we cannot call this method through our `List` variable, even though we definitively know it is a `ArrayList`!

~~~java
List<Integer> l1 = new ArrayList<Integer>();
l1.trimToSize(); /* Compiler error! */
~~~

The compiler complains that `List` does not have a `trimToSize` method, and this is correct!
What is happening is that the compiler using the declared type of the variable, its _static type_, to determine if a method call will work.
The runtime identity of the object, its dynamic type, is ignored.

This is because, in general, we _don't know the runtime identity of an object until runtime_!
To see this fact, consider the testing code we refactored in the previous section:

~~~java
public void listBasicUsageTest(List<Integer> lst) {
    assertEquals(0, lst.size());
    lst.add(5);
    lst.add(9);
    lst.add(7);
    assertEquals(3, lst.size());
    assertEquals(9, lst.get(1));
    assertEquals(9, lst.remove(1));
    assertEquals(7, lst.get(1));
    assertEquals(2, lst.size());
}
~~~

Here, `lst` is a `List`, so the method can be called with either a `LinkedList` or `ArrayList`.
Thus, we can't invoke methods not specified the `List` interface on `lst` because we may receive an object that does not implement those additional methods!

### The Object Class

When working with generic types, you may have noticed that there _are_ some operations that you call on values of unknown type!

~~~java
public <T> void exampleFunction(T value) {
    System.out.println(value.toString());   // ...?
}

exampleFunction("Hello");               // hello
exampleFunction(10);                    // 10
exampleFunction(new int[] { 1, 2, 3});  // [I@4edde6e5
~~~

The one requirement we have when choosing a generic type that it is a non-primitive type, i.e., a reference type.
However, it turns out that _every_ reference type is considered to be a subtype of the `Object` class.
(This arises not through interfaces but through a related mechanism, _inheritance_, that we will discuss later!)

The [`Object` class](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/lang/Object.html) exposes several methods that will be worthwhile for us to consider overriding.

+   `String toString()`: returns a string representation of the object.
+   `int hashCode()`: returns a has code value for this object.
    We'll ignore `hashCode` for now until we talk about hashing later in the course!
+   `boolean equals(Object obj)`: returns true whether some other `Object` is equal to this one.

`toString()` is rather self-explanatory: if we want to obtain a printable version of an object, e.g., for debugging purposes, we should `@Override` the object's `toString()` method.

For example, consider our generic `Pair` class from before:

~~~java
public class Pair<T, U> {
    private T first;
    private U second;
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() { return first; }
    public U getSecond() { return second; }
}
~~~

If we try to print out a pair, we get something not useful:

~~~java
Pair<T, U> p = new Pair<>(0, 0);
System.out.println(p);      // Pair@4769b07b
~~~

The default implementation of `toString()` for a class prints out the name of the class and its _address in memory_, most likely not the thing we want to see!
We need to override `toString` to obtain more useful behavior:

~~~java
public class Pair<T, U> {
    // ...
    @Override
    public String toString() {
        return String.format("(%s, %s)", first.toString(), second.toString());
    }
}

// ...

Pair<T, U> p = new Pair<>(0, 0);
System.out.println(p);      // (0, 0)
~~~

~~~admonish info title="Efficient String Building with StringBuilder"
As you are aware, Strings are immutable in Java, so if aren't careful, we can take a quadratic amount of time building up a `String` representation of an object through repeated `String` concatenation.
`String.format` is one way to get around this, but, we may be unable to specify a single format string to "print out" an entire object, e.g., because the object contains a list of values that we need to process with a loop.

The Java standard library provides the [`StringBuilder` class](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/StringBuilder.html) (found in the `java.util` package) which allows you to incrementally build up a string in a mutating fashion.
In essence, the `StringBuilder` is an array list of characters that you can `append` to.
When you are done, you can then use the `toString` method of the `StringBuilder` to receive the final string.

Here's an example of using `StringBuilder` to construct a string representation of an array:

```java
public static <T> String arrayToString(T[] arr) {
    StringBuilder buf = new StringBuilder();
    buf.append('[');
    if (arr.length > 0) {
        buf.append(arr[0].toString());
        for (int i = 1; i < arr.length ;i++) {
            buf.append(", ");
            buf.append(arr[i].toString());
        }
    }
    buf.append(']');
    return buf.toString();
}
```

Also note that the `Arrays` class of `java.util` does have an `Arrays.toString(arr)` method that does return a string for an array in the same way we implemented above!
~~~

Just like `toString` the default `equals` implementation will check for _pointer equality_ between this object and the `other` object passed to `equals`.
This is rarely what we want, so we will want to override `equals` if we believe we'll ever want to compare our objects for equality!

As an example, let's implement equality over pairs.
Awkwardly, if we look at the method signature for `equals`:

~~~java
public class Pair<T, U> {
    // ...
    @Override
    public boolean equals(Object other) { /* ... */ }
}
~~~

We see that the `other` object is _not_ necessarily a `Pair`!
Perhaps we want to consider a `Pair` to be equal to some other `Pair` type, but this is rarely the case.
So, our `equals` implementation will proceed in two steps:

1.  Check to see if `other` is a `Pair` object.
2.  If it is, then check to see if the corresponding fields are `equals` to each other.

To this first point, we can use the `instanceof` operator to check the type of `other`.
`e instanceof T` is a comparison operator that returns `true` iff the object identity (dynamic type) of the value that expression `e` evaluates to is `T`.

With this in mind, here is our implementation of `equals` for the `Pair` class:

~~~java
public class Pair<T, U> {
    // ...
    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair<T, U> o = (Pair<T, U>) other;
            return first.equals(o.first) && second.equals(o.second);   
        }
        return false;
    }
}
~~~

Inside of the conditional, we guarantee that `other` is a `Pair`, so we can safely _cast_ `other` to a `Pair` so that we can access the other pair's fields in a type-safe manner (through the `o` variable).

(_Note_: because `instanceof` works at runtime and generic types are erased during compile-time, we have to provide the _raw type_ `Pair` to `instanceof` instead of the fully generic type `Pair<T, U>`.)

To the second point, we see that we define two pairs to be equal whenever _their components are equal_ (recursively, via calls to the fields' respective `equals` methods).
Declaring two values equal if their respective components are equal is called _structural equality_ and typically "what we want" when we implement equality between objects.
This leads to the following common template we'll use to implement `equals` methods for some arbitrary type `T`:

~~~java
@Override
public boolean equals(Object other) {
    if (other instanceof T) {
        T o = (T) other;
        return /* compare all fields for equality */;
    }
    return false;
}
~~~

### The Principle of Least Privilege

Subtyping exposes an important design principle we should follow in our code.
Whenever possible, we should author our programs to _minimize dependencies_ between components.
By minimizing dependencies—concretely, the methods that one class relies on from another class—we:

1.  Reduce the amount of code we have to consider if we suspect a bug results from an interaction between the two classes.
2.  Minimize the disruption caused by a change in the class's set of exposed methods.

In the security world, this concept is also referred to as _the principle of least privilege_.
By granting users the minimal level of access necessary to perform their duties, we minimize both the likelihood of a security issue and the surface area we need to investigate if a security issue arises.

Even though we're typically not operating in a security-oriented setting, the principle of least privilege is an excellent way to design software!
We should:

1.  Minimize the public methods of a class to only those "necessary" for others to use the class.
    We do this primarily through privacy modifiers, i.e., `public`, and `private`.
2.  Hide implementation details of key data structures.
    We do this primarily through interfaces, in particular:

    *   Implementing relevant interfaces to advertise capabilities to clients.
    *   Accessing functionality through interface, rather than concrete types, when possible to limit our own dependency on a particular implementation.

~~~admonish question title="Static and Dynamic (‡)"
Consider the following toy interface `I` and classes `C1` and `C2` that implement that interface:

```java
interface I {
    void f1();
    void f2();
}

class C1 implements I {
    @Override void f1() {
        System.out.println("C1.f1");
    }
    @Override void f2() {
        System.out.println("C1.f2");
    }
}

class C2 implements I {
    @Override void f1() {
        System.out.println("C2.f1");
    }
    @Override void f2() {
        System.out.println("C2.f2");
    }
    @Override void f3() {
        System.out.println("C2.f3");
    }
}
```

And now consider the following combinations of variable declarations and method calls to those variables:

declaration/call   | `v.f1();` | `v.f2();` | `v.f3();` |
-------------------|-----------|-----------|-----------|
`I v = new C1();`  |           |           |           |
`C1 v = new C1();` |           |           |           |
`I v = new C2();`  |           |           |           |
`C2 v = new C2();` |           |           |           |
`C2 v = new C2();` |           |           |           |

For each combination of declaration and method call, write down the output of the method call or the _compiler error_ that arises if you wrote down this combination of declaration and method call.

~~~