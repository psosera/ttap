# Trees

Today, we will explore how to implement hierarchical structures, _trees_, in Java.

## Source Code

Fork and clone the code for this project from the following Github repository:

* <https://github.com/psosera/trees-lab>

The project contains a basic unit test suite that you will extend in this lab.
Ensure that all tests pass and submit this repository to Gradescope when you are done!

## Daily Drill Review

For today's daily drill, you were tasked with creating the following tree by-hand with calls to the `Node` constructor:

~~~
      5
     / \
    /   \
   /     \
  2       8
 / \     / \
1   3   7   9
        |   |
        6   10
~~~

Inside `TreeTests.java`, you will find a function `mkSampleTree`.
Fill in the definition of `sampleTree` and ensure that both `size` tests work for your tree definition.

## Part 1: Contains

First, let's implement a basic recursive operation over a tree from scratch.
`Tree.contains(T v)` returns `true` if and only if (iff) `v` is contained within the given tree.

1.  Give an algorithmic description of `contains` based on the recursive definition of a binary tree.
2.  Translate that description into an implementation for the `contains` method and verify that your implementation works for the tests found in `TreeTests.java`.

## Part 2: toString

Now, let's implement a recursive operation that requires some additional case work.
Recall that when we wrote a pretty printing function for an array, we (essentially) needed a special case for the first element of the list.
This is because a comma-separated list of $n$ elements only has $n-1$ commas.

~~~java
import java.util.StringBuffer;

public static void arrayToString(int[] arr) {
    StringBuffer buf = new StringBuffer("[");
    if (arr.length > 0) {
        buf.append(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            buf.append(", ");
            buf.append(arr[i]);
        }
    }
    buf.append("]");
    return buf.toString();
}
~~~

Observe how in `arrayToString` we treat the first element of the array specially (i.e., add it to the string _without_ a comma).
Every other element `v` in the array can then be appended as `", v"`.
Also note how we use a `StringBuffer` rather than naive string concatenation to avoid the classic $\mathcal{O}(n^2)$ time complexity problem associated with incrementally building an immutable string.

Adapt this approach to implement `Tree.toString()` which returns a `String` representation of the elements of tree in a linearized form: `[v1, ..., vk]`.
To do so, you should treat the "first" element of the tree, i.e., the root, specially, similar to the first element of the array in `arrayToString`.

1.  Give an algorithmic description of `toString` based on the recursive definition of a binary tree.
2.  Modify the test for calling `toString` on the example tree based on how you predict your `toString` method will behave.
3.  Translate that description into an implementation for `toString` method and verify it passes the tests.

## Part 3: Traversals

In the reading, we discussed three different kinds of traversals: preorder, in-order, and post-order traversals.
The reading describes them as follows:

+   A preorder traversal "visits" the value at the node, the left subtree, and then the right subtree.
+   An in-order traversal "visits" the left subtree, the value at the node, and then the right subtree.
+   A post-order traversal "visits" the left subtree, the right subtree, and then the value at the node.

First, start with in-order traversals:

1.  Write down a description of the preorder traversal algorithm using the recursive definition of a binary tree, i.e., in terms of leaf and node cases.
2.  Implement `Tree.toListInorder()` based on this description.
    (_Hint_: your recursive helper function will take an additional argument, a list that you will add the elements into.
    This list should initially be empty.)
3.  Verify that your implementation passes the tests.

After you do this:

1.  Adapt your algorithmic description of in-order traversal to preorder and post-order traversal.
    Specifically, how does the description change based on which traversal you are specifying?
2.  With this insight, implement `Tree.toListPreorder()` and `Tree.toListPostorder()`.
    You should find that implementing these methods are trivial provided you have implemented `toListInorder()` correctly!
    Make sure that your code passes all tests!

## For Additional Practice: Pretty Printing

Observe that our `toString` method linearized the tree, losing its hierarchical structure.
This is convenient for a quick implementation, but we might want to visualize the hierarchical structure within a tree.
We can use a bulleted-list style representation where we use indentation and sub-bullets to capture parent-children relationships.
For example, we might visualize our example tree in this style as follows:

~~~
- 5
  - 2
    - 1
    - 3
  - 8
    - 7
      - 6
    - 9
      - 10
~~~

Write a function `Tree.toPrettyString()` that returns a string representation of a tree in this format.

(_Hint_: you will need to keep track of the _indentation level_ in your recursive helper function!)