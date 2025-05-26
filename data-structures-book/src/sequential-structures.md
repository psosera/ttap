# Sequential Structures

The first of the fundamental data types that we'll study are _sequential types_.
A sequential type is simply a container for data where the container maintains an ordering between elements.
As a result, a common operation we can perform over sequential types is to _iterate_ over the elements of the container in-order.

The canonical example of a sequential type built-in to most languages is the array.
Arrays have the following properties in addition to being sequential:

*   _Homogeneous_.
    An array holds elements of the same type.
*   _Fixed-size_.
    Once created, an array's size cannot change.

Other sequential types differ along these two dimensions.
For example, a heterogeneous fixed-size sequential type is called a _tuple_.
We frequently utilize tuples of two elements, _pairs_, and tuples of three elements, _triples_.

Tuples are built-in to some languages, but not Java.
So we have to define a class for each tuple type that we're interested in using, e.g., a `Point` class:

```java
public class Point {
    private int fst;
    private int snd;
    public Point(int fst, int snd) {
        this.fst = fst;
        this.snd = snd;
    }
    public int getFst() { return fst; }
    public int getSnd() { return snd; }
}
```

Frequently, we do not know the number of elements we need to store into a sequence up front, for example, because the input comes from the user during execution of the program.
Fixed-sized types are insufficient in these situations.
We instead need a _variable-sized_ type that can grow at runtime to accommodate our storage needs.
A homogeneous, variable-sized sequential structure is called a _list_, the focus of the remainder of our studies in this chapter.

Lists are ubiquitous in programming because they are the simplest variable-sized class of data structures we might consider in programming.
In addition to merely serving as the canonical "bag" data structure for holding a bunch of stuff, we will use a list to exploit _sequential relationships_ between data, e.g., ordering.

## The List Abstract Data Type

Before we dive into implementation, we must define the interface of this list abstract data type.
What are the kinds of operations that we want to perform over some list type?
And for simplicity's sake, let's say our list just holds `int` values.

+   We need a way to create a list that is, presumably, initially empty:

    ~~~java
    public List() { /* ... */ }
    ~~~

+   We need a function to add elements to a list, presumably at the end:

    ~~~java
    public void add() { /* ... */ }
    ~~~

+   We need a function to retrieve elements from the list.
    Like arrays, lists are sequential in nature, so it is natural to want to retrieve their elements by index:

    ~~~java
    public int get(int index) { /* ... */ }
    ~~~

+   Since a list is variable-sized, it'll be useful to know the length of a list at any given point in time.

    ~~~java
    public int size() { /* ... */ }
    ~~~

+   At this point, it might be nice to delete elements (by index) from our list, too:

    ~~~java
    public int remove(int index) { /* ... */ }
    ~~~

With this interface defined, we can envision how operating over a list should work, even without knowing its implementation!

~~~java
public static void main(String[] args) {
    List lst = new List();
    System.out.format("The size of the empty list is: %d\n", lst.size());       // 0
    lst.add(5);
    lst.add(9);
    lst.add(7);
    System.out.format("The size of this list is now: %d\n", lst.size());        // 3
    System.out.format("The element at index 1 is: %d\n", lst.get(1));           // 9
    lst.remove(1);
    System.out.format("The element at index 1 now is: %d\n", lst.get(1));       // 7
    System.out.format("And the length of the list is now: %d\n", lst.size());   // 2
}
~~~

Such a transcript of how our abstract list can serve as a powerful _test-driven development_ tool.
We can write tests like this _first_ and then implement our code, aiming to make all our tests pass!

## List Implementations

Now that we know _what_ we are implementing, we must decide how to implement it.
A key decision in designing our data structure is how we _layout the elements of the data structure in memory_.
There are two primary ways to do this:

+   We keep the elements of the structure physically next to each other in memory.
    Such data structures are called _contiguous data structures_.
+   We can allow the elements to float freely in memory.
    In order to find the elements, we must employ some kind of linking scheme to connect the elements in some fashion.
    These data structures are called _linked data structures_.

Notably, a contiguous data structure is usually implemented with an _array_ as arrays guarantee that (with some caveats) their elements are next to each other in memory.
When implementing linked structures, elements must be equipped with some kind of _pointer or reference_ to one or more other elements in the structure.

These layout choices beget the two different kinds of list implementations we might consider:

+   An _array list_ is a contiguous implementation of a list.
+   A _linked list_ is a linked implementation of a list.

## Array-based Lists

The design of an array-based list comes rather naturally by observing that an array is _almost_ a list!
We just need to solve the problem of _maintaining the illusion_ that there is an unbounded amount of space available in the array.

Let's look at an example of adding elements to an array to discover the solution to this problem.
For example, here we add the characters `'a'`, `'b'`, and `'c'` to an initially empty array:

~~~
  [   ][   ][   ][   ][   ]
⇒ ['a'][   ][   ][   ][   ]
⇒ ['a']['b'][   ][   ][   ]
⇒ ['a']['b']['c'][   ][   ]
~~~

From the diagram, we can see that we naturally maintain two regions in the array, a _used_ region (currently containing `'a'`, `'b'`, and `'c'`) and an _unused_ region (currently with unknown contents).
We'll enforce as an invariant on our data structure that the used region is always to the left of the unused region, i.e., the used region does not contain any unused "gaps."
While it is clear from inspection of the diagram where these regions lie, we can't "see" this from code directly!
We need some _additional state_ that allows us to immediately tell, in particular, where the used region ends and the unused region begins.

What state do we need?
If we inspect the current state of the array:

~~~
['a']['b']['c'][   ][   ]
~~~

We see that there are three elements in the array we are tracking, and the array has five elements overall.
We can always retrieve the length of the array via its `.length` field.
However, the number of elements is not immediately known!
Thus, it is sensible to track this value directly.

To distinguish between the two concepts, we'll use the names:

+   _Capacity_ to refer to the length of the backing array.
+   _Size_ to refer to the number of _actual elements_ in the array list.

~~~
['a']['b']['c'][   ][   ]
size = 3
~~~

Coincidentally, the size of the array list is also the index of the first available slot in the unused region of the array, which is useful for implementing `add`.
In general, we should look for opportunities of this nature where a convenient choice of representation, e.g., storing the size of the list or choosing a 0-index versus 1-index-based bound, makes our implementation easier!

With this mind, what happens when our array is full?

~~~
['a']['b']['c']['d']['e']
size = 5
~~~

To maintain the illusion of unbounded size, we need to _grow our array_ before adding the next element.
Growing an array list behind the scenes is the key operation that makes the data structure work!
To grow the backing array we:

1.  Allocate a new array, larger than the original array.
2.  Copy the elements from the old array to the new array.
3.  Set the backing array to be the new array.

In Java, we can achieve this with a one-liner thanks to the static helper functions found in the `Arrays` class.
Note that `Arrays` is in the `java.util` package, and so we must important it:

~~~java
import java.util.Arrays;

public class ArrayList {
    int[] data;
    int size;

    // ...

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }
}
~~~

We choose to grow the array by a factor of two, i.e., doubling the length of the previous array.
While our choice of initial size of the array does not affect the overall runtime, it turns out this choice of _growth factor_ impacts the runtime substantially.
We'll revisit this idea later when we analyze the complexity of our list operations and introduce a new tool, amortized analysis, for dealing with the pattern of behavior that array lists exhibit.

## Linked-based Lists

While array lists are both relatively easy to implement and versatile, they are infeasible to implement or deploy in many scenarios.
The _linked list_, an implementation of a list that _does not_ guarantee contiguous elements, is our alternative choice in these situations.

### Setup

Pictorally, we represent linked lists of data using box-and-pointer diagrams, e.g.

~~~
[0][o]-->[1][o]-->[2][o]-->[3][o]-->[/]
~~~

This diagram represents the list `{0, 1, 2, 3}`.
Each pair of boxes represents a single object we'll call a _node_ that holds:

1.  A value and
2.  A reference to the next node in the list.

The reference to the final node in the list (`[3][o]-->`) is `null`, i.e., a pointer to nothing, and is represented by a pointer to a "null box" (`[/]`).

In Java, we can capture this structure with a `Node` class with appropriate fields for these two values:

~~~java
public class Node {
    int value
    Node next;
    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
~~~

We _could_ consider a linked list to be a `Node` because by following the node's `next` pointer, we can access all the elements of the list.
But we quickly run into a technicality if we take this approach.
In short:

1.  If a linked list is simply a `Node`, then how do we represent the empty list?
    The natural choice here is that a `null` `Node` is the empty list which aligns with how the last node of the list points to `null`.
2.  If we make this choice of representation, then we have an immediate problem: we can't call methods on a `null` object!

To resolve this issue, we need to add a layer of indirection so that we are not representing an empty list directly as a `null` reference.
An approach that "works" but isn't, in my opinion, clean, is using a _sentinel value_, i.e., a dummy first `Node` that is guaranteed to be non-`null` that we can call methods on it.

This approach has its own issues that we won't discuss further (but are worthwhile to try out on your own as practice).
Instead, we'll make this layer of indirection distinct from our `Node` class and directly call it our `LinkedList` class.
Such an implementation strategy, in my experience, leads to the cleanest linked list code we can write.

We introduce a `LinkedList` class with a singular field, `first`, which is a reference to the first `Node` in the list.
This class serves two purposes:

+   The `LinkedList` acts as a _wrapper_ for the `Node`s of the list.
    If the list is empty, the `LinkedList`'s `first` field is `null`.
+   As a wrapper, the `LinkedList` allow us to call methods on our list even though there are no nodes present.

~~~java
public class LinkedList {
    private static class Node {
        int value
        Node next;
        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node first;

    public LinkedList() {
        first = null;
    }
}
~~~

~~~admonish info title="Nested classes"
We do not expect that the `Node` class will by anyone other than the `LinkedList` class to organize its internal structure.
Thus, in the interest of keeping our code as _loosely coupled_ as possible, we nest the definition of `Node` within `LinkedList` and mark it `private` so that code outside of `LinkedList` cannot access it.
Furthermore, we also mark the class `static` so that a `Node` stands on its own and is not necessarily tied to a particular `LinkedList` we create.
~~~

Producing a list using our `LinkedList` constructor, e.g., `LinkedList l = new LinkedList()`, produces the following stack-and-heap diagram:

~~~
Stack      Heap
-----      ----
           --------------
l [o]----->| LinkedList |
           | first: o---|-->[/]
           --------------
~~~

The single box in the heap is our `LinkedList` object that has a single field, `first`, that is initially `null`.
For simplicity's sake, we'll reduce this `LinkedList` to a single box that is its `first` field.

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[/]
~~~

If we want to add elements onto our list, we can explicitly modify the pointers of the list and its underlying nodes, e.g., `l.first = new Node(0, null)` changes the diagram as follows:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[/]
~~~

To add onto the end of the list, we chain together field accesses to modify the last pointer in the list, e.g., `l.first.next = new Node(1, new Node(2, NULL)):

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->[/]
~~~

### Motions

When applying data structures to real-world problems, we will frequently need to adapt them to particular domains.
In order to have the flexibility to write a variety of operations over a data structure, we need to build a vocabulary of basic methods for manipulating that structure, something I call _motions_.
For example, one motion that you are well-acquainted with is _array traversal_:

~~~java
int[] arr = /* ... */;
int len   = /* ... */;
for (int i = 0; i < len; i++) {
  ... arr[i] ...
}
~~~

This motion is something that should come almost-automatically to you at this point.
If you identify that you need to traverse an array, you can produce this skeleton of code without much thought.
Furthermore, you can adapt this skeleton to various situations, e.g., traversing every other element starting with the second:

~~~java
int[] arr = /* ... */;
int len   = /* ... */;
for (int i = 2; i < len; i += 2) {
  ... arr[i] ...
}
~~~

In this section, we investigate the three basic motions you need to know to use a linked list: `insertion`, `traversal`, and `deletion`.

#### Insertion

Suppose that we wish to insert a new element into the front of the list.
Concretely, consider the list:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[1][o]-->[2][o]-->
~~~

How can we insert the value `0` into the front of the list?
First, we can create a new node that contains `0` and points to the rest of the list:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[1][o]-->[2][o]-->
                ^
                |
            [0][o]
~~~

Now, to fix up the diagram, we need to make the `first` pointer of our list point to this new node.

~~~
Stack      Heap
-----      ----
l [o]----->[o]   [1][o]-->[2][o]-->
            |     ^
            |     |
            ->[0][o]
~~~

Summarizing the required operations

*   Create a new node that contains the value to insert and points to the current first node of the list.
*   Make the list point to this new node as the head of the list.

We can directly translate them into a method `insertFront` that inserts an element into the front of the list

~~~java
public void insertFront(int value) {
    Node n = new Node(v, this.first);
    this.first = n;
}
~~~

We can also collapse these two statements into one:

~~~java
public void insertFront(int value) {
    this.first = new Node(v, this.first);
}
~~~

It is worthwhile to understand how this final function works precisely.
Because we evaluate the right-hand side of the assignment first, `new Node(...)` is passed what `this.first` initially points to.
The assignment then changes `this.first` to point to the result of `new Node(...)`.

This compact line can be summarized as the _insertion motion for linked lists_:

~~~java
int v;
Node n;
n = new Node(v, n);
~~~

We can think the line as saying:

> _Update_ `n` to point to a new node that appears _before_ what `n` is pointing to.

As a final point, it is worthwhile to note that this method works in the case where the list is empty, too:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->
~~~

`this.first` is `null` which means our new node points to `null`:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->
~~~

But that is exactly the right thing to do for `insertFront`!
The lesson here is that it is tempting to resort to covering corner cases, e.g., `null` checks, but if we design our code in the right way, we can avoid these warts in our code!

#### Traversal

Next, let's consider writing a function that computes the total number of elements in the list, its _size_.
To do this for our example list:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->
~~~

We must _traverse_ each element, counting each element along the way.
If we were to perform a similar operation an array, e.g., summing up its elements, we would use a for-loop:

~~~java
int sum = 0;
for (int i = 0; i < len; i++) {
    sum = sum + arr[i];
}
~~~

where the variable `i` represents the _current index_ we are examining in the array.

We would like to apply a similar sort of logic here, but we cannot directly access a linked list's elements by index.
Instead, we will directly hold a _pointer_ to the current node that we are examining:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->
                    ^
                    |
                    cur
~~~

Initially this pointer, traditionally called `cur`, points to the first node in the list.
We can declare this auxiliary variable as follows:

~~~java
Node cur = this.first;
~~~

We then advance the pointer to examine the next element.
How do we advance `cur`?
Advancing `cur` means that we need to assign it a new value.
The new value we would like to assign it is a pointer to the node that `cur`'s node is pointing to, i.e., `cur->next`.
Thus, the line of code to perform the update is:

~~~java
cur = cur.next;
~~~

This results in the following updated diagram.

~~~ 
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->
                            ^
                            |
                            cur
~~~

We can then continue to advance the pointer in a loop until we reach the end of the list:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->
                                             ^
                                             |
                                            cur
~~~

From the diagram, we see that this situation arises when `cur` is `null`.
Putting this all together, we can implement the `size` function as follows;

~~~java
public int size() {
    int ret = 0;
    Node cur = this.first;
    while (cur != null) {
        ret += 1;
        cur = cur.next;
    }
    return ret;
}
~~~

In summary, the _traversal_ motion looks as follows:

~~~java
Node cur = this->first;
while (cur != null) {
    ... cur-> value ...
    cur = cur->next;
}
~~~

Most linked list operations require some form of traversal and each puts their own little spin on it, so it is worthwhile to investigate this motion on another example.
Let's next consider how to insert at the end of the list.
If we want to insert 3 into the end of the following list:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->
~~~

We need to traverse the list to the end in order to modify its last pointer.
Our standard traversal motion stops when `cur` is `null`:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[0][o]-->[1][o]-->[2][o]-->
                                             ^
                                             |
                                            cur
~~~

But this is too late!
We need to stop when `cur` is on the last node, so we can modify its `next` field.
We can accomplish this behavior by modifying the guard of the while-loop so that we traverse through the list until `cur.next` is `null`:

~~~java
while (cur.next != null) {
~~~

However, this introduces one additional complication.
In the case where the list is empty, `cur` is initially `null` itself.
Therefore, the first check of this guard will result in a _null pointer exception_ where we try to access the `next` field of a `null` pointer, resulting in undefined behavior.

Because of this, our `insertEnd` function requires two cases: whether the list is empty or not.

~~~java
void insertEnd(int value) {
    Node n = new Node(v, null);
    if (this.first == null) {
        this.first = n;
    } else {
        Node cur = this.first;
        while (cur.next != null) { cur = cur.next; }
        cur.next = n;
    }
}
~~~

In the case when the list is empty, `insertEnd` behaves like `insertFront`.
Otherwise, we traverse the list until we `cur` is on the last node.
At this point, we fix up the last node's pointer to point to our new element.
Many list operations that we write have this basic skeleton:

~~~java
if (this.first == NULL) {
    // Do the operation on an empty list
} else {
    // Do the operation on a non-empty list
}
~~~

It is worthwhile to (a) design our functions with this mindset up-front---what does our operation do on the empty list and non-empty list?---and (b) test our list functions on both empty and non-empty lists.

#### Deletion

Finally, how do we remove a particular element, call it `v`, from a linked list?
Let's consider some cases.
First, if there is nothing in the list:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[/]
~~~

Then there is nothing to do.
In a non-empty list, if `v` is the first element:

~~~
Stack      Heap
-----      ----
l [o]----->[o]-->[v][o]-->[0][o]-->[1][o]-->[/]
~~~

We must fix up `this.first` to point to the node that `this.first` points to.
This pointer is `this.first.next`, so we have the assignment:

~~~java
this.first = this.first.next;
~~~

That performs the deletion.

However, if `v` is not the first element:

~~~
... -->[0][o]-->[v][o]-->[1][o]--> ...
    ^
    |
    cur
~~~

Then we will need to modify our `cur`'s `next` pointer to point to what `cur.next` points to.
Like the `l.first` case, this node is `cur.next.next` which gives us the assignment:

~~~java
cur.next = cur.next.next;
~~~

These three cases give us the following implementation for `remove`:

~~~java
/**
 * Removes <code>value</code> from this list.
 * @param value the value to remove
 * @return true iff the first occurrence of value is removed from the list
 */
boolean remove(int value) {
    if (this.first == null) {
        return false;
    } else if (this.first.value == v) {
        this.first = this.first.next;
        return true;
    } else {
        Node *cur = this.first;
        while (cur.next != null) {
            if (cur.next.value == v) {
                cur.next = cur.next.next;
                return true;
            }
        }
        return false;
    }
}
~~~

Deletion from a linked list provides to be tricker than insertion with several cases, but nevertheless, the _deletion motion_ is consistent in each case:

~~~java
Node n;
// Note: deletes n.next from the list
n.next = n.next.next;
~~~