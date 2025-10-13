# BSTs

Today, we will extend the Binary Search Tree implementation found in the reading with traversal and deletion methods.

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

* <https://github.com/psosera/bsts-lab>

The project contains a basic unit test suite that you will extend in this lab.
Ensure that all tests pass and submit this repository to Gradescope when you are done!

## Daily Drill Review

For today's daily drill, you were tasked to visualize the step-by-step evolution of a binary search tree when adding the values 3, 5, 2, 6, and 4.

1.  Check your work on the daily drill with your partner.
2.  Next, review the implementation of `BinarySearchTree.insert` and its associated helper method from the reading.
    Make sure that you understand how `insert` takes advantage of the BST property to perform insertion.
    Ask a member of the course staff if you have any questions.
3.  Port the implementation of `BinarySearchTree.insert` from the reading to your lab and complete the definition of `mkSampleTree` found in `BSTTests.java` by calling `insert` to add the values 3, 5, 2, 6, and 4 to the tree (in this order).
    Make sure that the `size` tests pass!

## Part 1: Contains

Last class, we implemented the `contains(T v)` method of a tree that returns `true` if and only if (iff) `v` is contained within the given tree.
Let's implement this method for our binary search tree, taking advantage of the BST property to search more efficiently.

1.  Give an algorithm description of `contains` based on the recursive definition of a binary search tree.
2.  Translate that description into an implementation for the `contains` method, verifying that your implementation passes the unit

## Part 2: Ordered Traversal

Last class, we talked about the three traversal methods for trees: preorder, in-order, and post-order traversal.
Thanks to the binary search tree property, one of these methods is especially beneficial to implement whenever we traverse a BST.

Discuss with your partner which traversal strategy produces an _ordered_ result and use that implementation strategy to implement:

1.  `BinarySearchTree.toString()`
2.  `BinarySearchTree.toList()`

Observe that these traversal methods _do not_ depend on our tree being a binary search tree, so you should be able to port your implementations from the previous lab unmodified!

## Part 3: BST Sorting

Next, let's take advantage of the BST property in a more direct way!
With `toList()` implemented, implement `BinarySearchTree.sort(List<T> lst)` which takes a list `lst` as input and returns a list containing the elements of `lst` in sorted order.
Make sure the relevant tests found in `BSTTests.java` pass!

Additionally, analyze the runtime of `sort` assuming that the series of insertions produces a reasonably balanced binary tree.
Include your runtime in your comment for `sort`.

## Part 4: Deletion

Finally, let's tackle a more complex function: deletion!
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