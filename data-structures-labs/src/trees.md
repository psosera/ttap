# Trees

Today, we will extend the Binary Search Tree implementation found in the reading with traversal and deletion methods.
This will allow us to practice implementing recursive functions over tree-like data structures, following the recursive definition of a tree.

## Review: Binary Search Trees

Recall that a binary search tree (BST) is a binary tree defined recursively as either:

+   A _leaf_ containing no data or
+   A _node_ containing a datum, left subtree, and right subtree.

Furthermore, every node of a BST satisfies the _binary search tree invariant_:

> Let $v$ be the value at the node.
> Then every value found in the left subtree of this node is less than $v$.
> And every value found in the right subtree of this node is greater than or equal to $v$.

Before going to implementation, we define our recursive operations over trees directly in terms of this definition.
For example, the size of a BST is either:

+   $0$ if the BST is a leaf.
+   $1 + l + r$ where $l$ and $r$ are the sizes of the left and right subtrees is the BST is a node.

## Source Code

Fork and clone the code for this project from the following Github repository:

* <https://github.com/psosera/trees-lab>

The project contains a basic unit test suite that you will extend in this lab.
Ensure that all tests pass and submit this repository to Gradescope when you are done!

## Part 1: Traversals

In the reading, we discussed three different ways to traverse a binary tree: preorder, inorder, and postorder.
The reading describes them as follows:

+   A preorder traversal "visits" the value at the node, the left subtree, and then the right subtree.
+   An inorder traversal "visits" the left subtree, the value at the node, and then the right subtree.
+   A postorder traversal "visits" the left subtree, the right subtree, and then the value at the node.

First, start with inorder traversals:

1.  Write down a description of the preorder traversal algorithm using the recursive definition of a binary tree, i.e., in terms of leaf and node cases.
2.  Implement `BinarySearchTree.toListInorder()` based on this description.
    (_Hint_: your recursive helper will take an additional argument, a list that you will add the elements into.
    This list should initially be empty.)
3.  Write additional test cases for `toListInorder()` to verify that your implementation is correct.

After you do this:

1.  Adapt your algorithmic description of inorder traversal to preorder and postorder traversal.
    Specifically, how does the description change based on which traversal you are specifying?
2.  With this insight, implement `BinarySearchTree.toListPreorder()` and `BinarySearchTree.toListPostorder()`.
    You should find that implementing these methods are trivial provided you have implemented `toListInorder()` correctly!
    Make sure to include additional test cases for these methods.

## Part 2: Contains

Next, let's implement a basic recursive operation over a tree from scratch.
`BinarySearchTree.contains(T v)` returns `true` if and only if (iff) `v` is contained within the given tree.

1.  Give an algorithm description of `contains` based on the recursive definition of a binary tree.
2.  Translate that description into an implementation for the `contains` method, verifying your implementation by extending the test suite accordingly.

## Part 3: Pretty Printing

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

Adapt this approach to implement `BinarySearchTree.toStringPreorder()` which returns a `String` representation of the elements of tree obtained via a preorder traversal.
To do so, you should treat the "first" element of the tree, i.e., the root, specially, similar to the first element of the array in `arrayToString`.

Again, make sure to verify your approach by adding test cases to `BinarySearchTreeTests`.

## Part 4: Deletion

Now, let's tackle a more complex function: deletion!
Deletion is a non-trivial operation to implement for a binary search tree because of the need to preserve the binary search tree invariant.
As a starting exploratory problem, consider the following binary search tree:

~~~
        5
       / \
      /   \
     /     \
    2       15
   / \     / \
  1   3   10  17
         / \   \
        7   12  20
            /
           11
~~~

Now imagine deleting the element `15` from the tree.
While multiple trees are possible, draw a binary search tree that could result from the deletion of `15` that _minimizes_ the movement of elements in the tree.

Once you have done this, you can see that deletion is inherently more complicated than any other operations we've discussed previously!
This is because deletion potentially results in _non-local_ changes to the tree, i.e., changes that involve more than a parent and its immediate children.

### Part 4.1: Deletion Cases

For these more complicated tree operations, it is useful to break up the critical operation into cases.
Finding the node to delete is a simple traversal, but actually performing the deletion can be difficult!
Thus, we'll assume that we are in the following situation:

~~~
    v
   / \
left  right
~~~

We have found the element that we want to delete `v` and `v`'s corresponding node has a `left` and `right` subtree.
Our goal is to return a new subtree that is the result of deleting `v` from the tree.

We can break up this operation into _three cases_, two of which are easy to implement, based on the contents of `left` and `right`.
Create and explore a variety of small, concrete examples of this situation to determine these three cases and write them down in your source file.
Check with a member of the course staff to ensure you have identified the appropriate cases!

Once you have verified you are correct, you can partially implement `delete` to, first, find the value of interest in the tree and then implement the two easy cases, leaving the third unimplemented.
Make sure to test these easy cases before moving on!

### Part 4.2: The Hard Case

Now, let's explore what to do in this final case by exploring some examples.
Similarly to the beginning of this lab, try to carry out the deletion operation on the following sequence of trees, minimizing the amount of movement of the values in the tree.

Deleting 10:

~~~
  5
 / \
2   10
   / \
  7   12 
~~~

Deleting 12:

~~~
  5
 / \
1   12
   / \
  7   15
 /
6
~~~

Deleting 15 (this is the example from the beginning of the lab!):

~~~
        5
       / \
      /   \
     /     \
    2       15
   / \     / \
  1   3   10  17
         / \   \
        7   12  20
            /
           11
~~~

Try to generalize these three examples into a general algorithm for deleting nodes in this final, third case.
(_Hint_: the algorithm essentially involves finding, swapping, and deleting elements.
The deletion can be performed recursively, but you should observe that the deletion will _always_ be one of the two trivial cases!)

Check with a member of the course staff to ensure your algorithm is correct and, if it is, go ahead and implement it, adding sufficient tests to ensure that deletion works in all cases!