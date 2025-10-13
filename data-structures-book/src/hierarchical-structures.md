# Hierarchical Structures

So far, we have studied sequential structures that assign each of their elements an index.
These indices impose an ordering relationship between elements.
Next, we will examine more complex relationships that we can impose between elements in a structure.
The first of these is the _hierarchy_, which establishes _child-parent_ relationships between values.

## Hierarchies

Hierarchies occur naturally in all sorts of data.
For example, consider the following domains:

1.  The reporting structure in a large corporation.
2.  Classification of animals.
3.  HTML documents, i.e., web pages.

All of these domains possess a hierarchical structure.
A corporation’s reporting structure may look like this:

~~~
                            CEO
                           /   \
                          /     \
    Vice president (Finance)    Vice President (Engineering)
        /                \                   | 
       /                  \                  |
    Manager             Manager             ...
    /     \            /      \
Analyst  Analyst  Accountant  Accountant
~~~

The various individual contributors to a company (e.g., analysts, accountants, and programmers) report to their managers.
The various managers within a division report to the vice president of the division.
Finally, the various vice presidents report to the CEO whom sits at the top of the reporting structure.

Living organisms are classified as follows:

~~~               
                     Living things
                           |
                           |
    -----------------------------------------------                     
    |         |          |        |       |       |
Bacteria  Protozoa  Chromista  Plantae  Fungi  Animalia
~~~

Biologists divide up living things into a hierarchy of classes: kingdoms, phylums within those kingdoms, classes within those phylums, and so forth.

Finally, HTML documents have a hierarchical structure, too!
Most web browsers allow you to view the source of a webpage in a tree-like format, e.g., in Chrome: Options → More Tools → Development Tools.
Every HTML document begins with an outer `<html> ... </html>` tag.
Inside this tag are the elements of the webpage.
For example, `<head> ... </head>` contains header information about the page and `<body> ... </body>` contains the actual page content.

Here is a barebones HTML document with the hierarchy of tags made explicit:

~~~
              <html>
                |
   -------------------------
   |                       |
   |                     <body>
   |                       |
 <head>               ----------
   |                 /          \
<title>          <h1>            <p>
   |               |              |
My Title    Section Header    Paragraph
~~~

Note that the structure imposed by the hierarchies in all three examples is essential.
For example, imagine if we wrote down the corporation reporting structure as a list:

> CEO, Vice President (Finance), Manager, Analyst, Analyst, Manager, Account, Account, Vice President (Engineering).

From the list, it isn’t clear who reports to whom in the company!
Our list representation of the company loses the reporting structure that the hierarchy captured.

## The Tree Abstract Data Type

From our discussion above, it is clear that a list is insufficient for representing data that possesses hierarchical relationships.
We instead use a different abstract data type, the _tree_, to capture these relationships.

A tree is a data type that encodes a hierarchical structure among its elements.
The meaning of these relationships is domain-specific.
As a result, trees typically don’t have a fixed interface like a list—the operations we’d like to perform depend heavily on what the tree is used for.
Nevertheless, we’ll study some essential core operations and programming techniques over trees that we can adapt to any situation.

To begin, we’ll examine the simplest form of a tree to understand these basics.
Trees have an elegant recursive definition. A tree is either:

*   An empty leaf or
*   A node consisting of an element, a left subtree, and a right subtree.

Such a tree is called a _binary tree_ because its nodes feature two children, the subtrees rooted at that node.
We can visualize the second case as follows:

~~~
        v
       / \
      /   \
   ...     ...
~~~

We typically denote the left and right subtrees `left` and `right`, respectively. 
Note that these subtrees are simply recursive occurrences of our tree definition—they’ll either be empty or be a tree itself.

As a concrete example, consider the following tree of integers:

~~~
           5
          / \
         /   \
        /     \
       /       \
      /         \
     3           9
    / \         / \
   /   \       /   \
  1     2     7    11
 / \   / \   / \   / \
·   · ·   · /   · ·   ·
           6
          / \
         ·   · 
~~~

The leaves of the tree are denoted by single dots (·).
The top-most element of the tree is called its _root_.
Here the element 5 is the root.
The root has two subtrees.
The left subtree contains the elements 1, 2, and 3.
The right subtree contains the elements, 6, 7, 9, and 11.
We can identify any of the subtrees by its root, e.g., the subtree rooted at 3 contains itself, 1, and 2.
The subtree rooted at 7 contains itself and 6.
As a degenerate case, the subtree rooted at 11 only contains itself, but it is still a tree, nevertheless.

For any two elements in the tree we can talk about the relationship induced by the tree’s structure.
For example, 7 appears as the root of the left subtree rooted at 9.
Therefore, we say that 7 is a _child_ of 9; conversely, 9 is the _parent_ of 7. 
We’ll use all sorts of similar terminology to denote these parent-child relations as is appropriate for the domain, e.g., subordinate and boss for the corporate domain or subclass and superclass for the living organism domain.

Drawing out the empty leaves is usually unnecessary.
Therefore, we frequently leave them out to simplify the diagram:

~~~
      5
     / \
    /   \
   /     \
  3       9
 / \     / \
1   2   7  11
       /
      6
~~~

### Placement of Data

Our initial definition of a tree places the data at the _interior nodes_ of the tree—i.e., the non-leaf nodes of the tree.
However, there is nothing essential about this choice.
Indeed, we may give an alternative definition of a tree that places the data at the leaves rather than the nodes.
Such a tree is either:

*   A _leaf_ containing a value or
*   A _node_ containing a left and right subtree.

With this definition, our sample tree above may be represented as follows:

~~~
         ·
        / \
       /   \
      /     \
     /       \
    ·         ·
   / \       / \
  ·   3     /   \
 / \       /     \
1   2     ·       ·
         / \     / \
        6   7   9   11
~~~

While this tree is structurally distinct from our original tree, they both encode the same information.
Choosing one representation over another is simply a matter of choosing the representation that best fits the domain that the tree is being used in.

## Tree Representation and Operations

Recall that we can adopt a sequential (array-based) strategy or a linked strategy for implementing a structure.
We'll first investigate implementing trees using a linked strategy since the nature of trees admits a natural implementation that builds on top of a linked list.
In essence, a binary tree _is_ a linked list except that instead of a single `next` field that contains the "rest of the list," it has two fields `left` and `right` that contain the "rest of the tree."
Therefore, we adopt a similar strategy to represent a tree in Java—a class to represent the nodes of a tree and a class to represent the tree itself:


~~~java
// In Tree.Java

public class Tree<T> {
    private static class Node<T> {
        public T value;
        public Node<T> left;
        public Node<T> right;
        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
        public Node(T value) { this(value, null, null); }
    }

    private Node<T> root;
    public Tree() { root = null; }
}
~~~

Here, the leaves of the tree are represented with a `null` `Node<T>` value.

Because our tree, ultimately, is a container for data, we ought to perform similar sorts of operations that we can perform over lists, e.g., adding elements, querying for an element, or checking the size of the container.
Imagine implementing this last operation, `size()` for a linked list.
We would maintain a `cur` reference to the current node in the list we are examining and increment a counter for each one.
Let’s try the same approach for our tree:

~~~java
// In the Tree class...
public int size() {
    Node<T> cur = root;
    int size = 0;
    while (cur != null) {
        size += 1;
        cur = cur.left;
        // But what about cur.right...?
    }
    return size;
}
~~~

However, we run into a problem if we try to apply our linked list traversal techniques to trees.
This attempt at `size()` traverses the left-hand nodes of the tree but doesn’t visit the right-hand nodes.
But once we leave a node to visit its left subtree, we have no way of "coming back" to visit its right subtree!

To remember these nodes, we need to appeal to an auxiliary data structure, e.g., a list.

~~~java
// In the Tree class...

public int size() {
    List<T> pending = new LinkedList<>();
    pending.add(root);
    int size = 0;
    // Loop invariant:
    //   pending contains the current frontier of nodes
    //   that we still need to visit.
    while (pending.size() != null) {
        Node<T> cur = pending.remove(0);
        if (cur.left != null) { pending.add(cur.left); }
        if (cur.right != null) { pending.add(cur.right); }
        size += 1;
    }
    return size;
}
~~~

Here, we use a list essentially like a _queue_, adding nodes to be explored to the end of the list and then removing nodes to visit next from the front.

This approach works, however, the use of an auxiliary data structure is undesirable.
Furthermore, our solution does not reflect the recursive definition of the tree.
Because of this, we’ll pursue a recursive definition of the `size()` operation that mirrors our definition for a tree.

In the absence of a particular programming language, we can define the size operation as follows:

*   The size of a leaf is zero.
*   The size of a node is one plus the sum of the sizes of its left and right subtrees.

There are several ways to reflect this definition in Java with varying trade-offs of complexity, elegance, and handling of corner cases.
Here, we present a particular style that allows our code to reflect this definition directly:

~~~java
// In the Tree class...
private static int sizeH(Node<T> cur) {
    if (cur == null) {
        return 0;
    } else {
        return 1 + sizeH(cur.left) + sizeH(cur.right);
    }
}

public int size() { return sizeH(root); }
~~~

We establish a static helper function, `sizeH`, that computes the size of a tree rooted at a given `Node<T>`.
The method proceeds by case analysis on the shape of that `Node<T>`—it is either a leaf (`null`) or a node (non-`null`).
In this manner, the helper function mirrors the pseudocode definition given above. Finally, we define the actual `size` method to simply call this helper method starting with the root of the tree.

### Tree Traversals

`size()` is a simple example of a _tree traversal method_.
To compute the size of a tree, we must visit or _touch_ every element.
With a sequence, there was only one logical way to visit its elements: left-to-right order.
However, because a tree node has two children, we have a choice in the _order_ we perform the following operations:

+ "Processing" the value at the node.
+ Recursively diving into the node's left subtree.
+ Recursively diving into the node's right subtree.

Observe that the _order_ we visit the elements of the tree is irrelevant in calculating the size of the tree because addition is commutative.
But this not always the case!
For example, imagine a method `toString` that prints the elements of the tree.
Here, the order in which we visit the elements does matter!

Consider the following sample tree:

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

And the following pseudocode description of `toString`:

*   If the tree is a leaf, its string representation is the empty string.
*   If the tree is a node, its string representation is the string representation of the value, followed by the string representations of the left-hand and right-hand trees, in-order.

This version of `toString` first “stringifies” the value at a node before recursively descending into its subtrees.
This results in the following output for the sample tree:

~~~
[5, 2, 1, 3, 8, 7, 6, 9, 10]
~~~

This is an example of a _pre-order traversal_ of the tree where we “visit” the value at the node first, then the left-hand subtree, and the right-hand subtree.

We can exchange this order to obtain two other traversal strategies:

*   _In-order traversal_: Recursively process the left-hand subtree, “visit” the value at the node, recursively process the right-hand subtree.
*   _Post-order traversal_: Recursively process the left-hand subtree, recursively process the right-hand subtree, and “visit” the value at the node.

An in-order traversal of the sample tree yields the list `[1, 2, 3, 5, 6, 7, 8, 9, 10]`.
The post-order traversal of the sample tree yields the list `[1, 3, 2, 6, 7, 10, 9, 8, 5]`.

Each traversal order has several use cases:

*   An in-order traversal of a _binary search tree_, described below, yields the elements of the tree in sorted order.
*   Pre-order traversal provides a convenient way for serializing a tree into a linear form appropriate for storage in a file that can be used to recreate the tree later.
*   If we interpret the interior nodes of the tree as operators and the leaves as values, post-order traversal yields _postfix notation_ or _reverse polish notation (RPN)_ which does not require expressions to be parenthesized.
    For example, the mathematical expression written in traditional infix style `3 × (4 + 5)` has the unambiguous representation in RPN: `3 4 5 + ×`.

~~~admonish question title="Making a Tree (‡)"
Using the definition of the `Tree<T>` class, write code that manually creates the tree of integers:

```
      5
     / \
    /   \
   /     \
  2       8
 / \     / \
1   3   7   9
        |   |
        6   10
```

In other words, you should write an expression consisting of `new Node(...)` calls that constructs this tree.
Store the results of this expression as a local variable called `exampleTree`.
To access the `Node<T>` class of `Tree<T>`, you can write a `main` method within the `Tree<T>` class that defines `exampleTree`.
~~~

## Binary Search Trees

Before we discuss other tree operations, we must narrow our domain of interest to take advantage of the hierarchical relationships that the tree offers.
Recall that linear search over an unsorted sequential structure has $\mathcal{O}(n)$ time complexity.
However, if the structure is already sorted then we can employ binary search, which has $\mathcal{O}(\log n)$ time complexity.
The catch is that we must now keep the structure sorted which requires additional work on top of the sequential operations we’ve discussed previously.

A _binary search tree_ is a tree-based structure that maintains this sortedness property among its elements.
It does this by way of an invariant that is baked into the definition of the tree.
A binary search tree is a tree consisting of either:

*   An empty leaf.
*   A node consisting of a value and left- and right-subtrees with the property that all the elements in the left subtree are less than the value and all the elements in the right subtree are greater than or equal to the value.

We can visualize this _binary search tree invariant_ as follows:

~~~
        v
       / \
      /   \
     /     \
 {< v}    {≥ v}
~~~

This invariant gives us guidance as to where to place elements in the tree.
For example, consider starting out with an empty binary tree and then adding the elements 3, 5, 2, 6, and 4.
Here is the evolution of our tree after each insertion.

~~~
                         3          3
                        / \        / \
       3        3      2   5      2   5
      / \      / \        / \        / \
3    ·   5    2   5      ·   6      4   6
~~~

In general, our insertion strategy is to traverse the tree according to the binary search tree invariant to find a leaf.
We then replace the leaf with a node containing the value to be inserted.
In the above example:

1.   Initially, we replace the single leaf of the empty tree with a node containing the value 3.
2.   To insert 5, we note that 5 is greater than 3, so we recursively dive into the right-hand subtree, find that it is a leaf, and replace it with a node containing 5.
3.  To insert 2, we note that 2 is less than 5, so we recursively dive into the left-hand subtree, find that it is a leaf, and replace it with a node containing 2.
4.  To insert 6, we note that 6 is greater than 3 and 5, so it goes into the right-most subtree.
5.  Finally, to insert 4, we note than 4 is greater than 3 but less than 5, so goes into the left subtree of 5.

We can generalize these examples into a procedure for inserting elements into a binary search tree.
When inserting a value `v` into a binary search tree:

*   If you are inserting into a leaf, then replace that leaf with a node containing `v` and no left or right subtrees.
*   If you are inserting into a node that contains some value `w`, then recursively insert into the left subtree if `v < w`.
    Otherwise, recursively insert into the right subtree.

We may realize this in Java as follows:

~~~java
public class BinarySearchTree<T extends Comparable<T>> {
    // Node class same as before...
    private Node<T> root;
    public BinarySearchTree() { root = null; }

    /** @return the updated tree after inserting h into the given tree */
    private static Node<T> insertH(T v, Node<T> cur) {
        if (cur == null) {
            return new Node<>(v);
        } else {
            if (v.compareTo(cur.value) < 0) {
                cur.left = insertH(v, cur.left);
            } else {
                cur.right = insertH(v, cur.right);
            }
            return cur;
        }
    }
    public void insert(T v) { root = insertH(v, root); }
}
~~~

The definition of the `BinarySearchTree<T>` class is identical to our regular `Tree` class.
The exception is that to maintain the binary search tree invariant, we must be able to compare elements contained within the Tree.
This means that we must constraint the generic type `T` to be any type that implements the `Comparable<T>` interface, i.e., `T` defines how to compare elements against itself.

The definition of insert follows the skeleton we established for `size` above.
However, unlike `size`, `insert` modifies the underlying tree once it finds a leaf—a node that is `null`.
To avoid having to write `null` checks for each of the subtrees of a node, we employ a recursive design pattern called the _update pattern_.
Our recursive method, `insertH` takes the `Node<T>` that is the root of the tree as well as the element to insert as input.
The method also returns a value, _the updated root_, as output.
When we insert into a leaf, the root is `null`, so the method returns a new node.
When we insert into a node, the root is non-`null`, so the method simply returns the node that was passed to it.
However, along the way, `insertH` modifies this node with an updated subtree.

We can think of `insertH` as returning an _updated_ version of its input `Node<T>`.
This is why the `public` version of `insert` has the following form:

~~~java
root = insertH(v, root);
~~~

We have updated the root of the tree with the result of inserting `v` into the tree.

Other tree-updating methods, e.g., deletion can also be written this style.
We will explore deletion in the lab corresponding to this reading!

### Complexity Analysis

Finally, let’s consider the complexity of the various tree operations we’ve discussed in this chapter.

#### Time Complexity

The various traversals, like their sequential counterparts, visit every element of the structure; they, therefore, all take $\mathcal{O}(N)$ time, where $N$ is the number of elements in the tree.

More interesting is the cost of lookup and insertion into a binary search tree.
In the worst case of lookup, we search one path from the root of the tree to one of its leaves.
For example, in the following binary search tree:

~~~
  3
 / \
2   5
   / \
  4   6
~~~

If we look for the value 4, we’ll visit the nodes 3, 5, and 4 during the search process.
Thus, the runtime of lookup is dependent on the length of such a path.

Let’s consider a degenerate example of a binary search tree.

~~~
  1
 / \
⋅   2
   / \
  ⋅   3
     / \
    ⋅   4
       / \
      ⋅   5
         / \
        ⋅   ⋅
~~~

This tree is a binary search tree, however, it is far from an ideal one.
It is essentially a linked list!
Searching this binary search tree takes $\mathcal{O}(N)$ time in the worst case, the same as linked list search.

Now let’s consider an ideal binary search tree:

~~~
      5
     / \
    /   \
   /     \
  3       7
 / \     / \
1   2   6   8
~~~

This binary search tree has three _levels_ of nodes.
The first level contains the element 5.
The second level contains the elements 3 and 7.
The third level contains the elements 1, 2, 6, and 8.
Each of these levels are _full_, that is, they have the maximum number of possible elements.
We call such a tree _perfect_—all interior nodes have two children and all leaves exist at the same level.

To assess the length of a path in this perfect tree from root to leaf, we must consider the number of nodes at each level of a perfect binary tree.
The first level contains 1 element, the second contains 2, the third contains 4, the fourth level contains 8, the fifth level contains 16, and so forth. 
It is reasonable to hypothesize that the number of nodes at level $i$ is $2^i$.
This turns out to be true and provable with a quick proof by mathematical induction:

~~~admonish question title="Claim"
The number of nodes at level $i$ of a perfect binary search tree is $2^i$.
~~~

~~~admonish check title="Proof"
Proof by induction on the level $i$.

*   $i = 0$: at level 0 (the first level), there is one node, the root, and $2^0 = 1$.
*   $i = k + 1$: by our inductive hypothesis, level $k$ contains $2^k$ nodes.
    Because each node of level $k$ contributes two nodes, a left and right child, to level $k + 1$, then the number of nodes at level $k + 1$ is $(2^k) \cdot 2 = 2^{k+1}$.
~~~

The total number of nodes in a perfect binary search tree of height $h$ is therefore given by summing up the nodes at each level:

$$
N = 2^{0} + 2^{1} + 2^{2} + \cdots + 2^{h} = \sum_{i=0}^{h} 2^{i}.
$$

This sum relates the total number of nodes of the tree with its height.
It has a closed-form solution:

$$
N = \sum_{i=0}^{h} 2^{i} = 1 - \frac{2^{h+1}}{2-1} = -(1-2^{h}) = 2^{h+1} - 1.
$$

Therefore, a perfect binary tree of height $h$ has $2^{h+1} - 1$ nodes.
From this, we can solve for $h$ in terms of $N$.

$$
\begin{align*}
N &= 2^{h+1} - 1 \\
N + 1 &= 2^{h+1} \\
\log_{2} N + 1 &= \log_{2} 2^{h+1} \\
\log_{2} N + 1 &= h + 1 \\
\log_{2} N + 1 - 1 &= h
\end{align*}
$$

Thus, the height is bounded by $\log N$.
When the tree is perfect, lookup has worst-case time complexity $\mathcal{O}(\log N)$.
Note that insertion into a binary search tree operates identically, so it too has worst-case $\mathcal{O}(\log N)$ time complexity in this situation.

However, what is the appropriate average case time complexity of lookup?
This turns out to be difficult to analyze precisely—what is the layout of the average tree?
This depends on the effects of the insertion and deletion operations performed on the tree.
In particular, deletion favors the rotation of one side of the nodes, so we might expect that the tree becomes more unbalanced with repeated deletions.

To make progress, we can restrict our question to the layout of the average tree created by only a chain of insertion.
It turns out that, on average, the height of such a tree is $\mathcal{O}(\log N)$, i.e., the average height is within some constant factor of the optimal height.
Therefore, in this situation, the average case of insertion is $\mathcal{O}(\log N)$.
However, even when we consider only insertions, we can still obtain the degenerate binary search tree if we insert elements in-order.

In general, our current insertion policy does not allow us to maintain a _balanced_ tree shape, one that looks roughly like a perfect tree.
To get around this problem, we employ various balancing techniques to maintain a balanced tree while maintaining good performance of our fundamental tree operations.
Examples of trees employing balancing techniques include AVL trees, red-black trees, and B-trees.
All of these structures place additional constraints or invariants on the structure of the tree that ensure that it remains well-balanced.

#### Space Complexity

None of the operations we’ve examined requires additional heap allocations.
However, we must be cognizant of the fact that our recursion itself takes up space—namely space on the stack.
With non-recursive functions, we always make a constant number of function calls relative to the input.
However, with recursive functions, the number of function calls depends on the size of the input in some way.
This means potentially that we require a non-constant amount of stack space to execute our functions!

Recall that the pending function calls in our program occupy one _stack frame_ per call.
What is the largest number of pending function calls that we build up while executing our operations over trees?
First let’s consider the _size_ operation.
Recall we implement _size_ using the following recursive helper function:

~~~java
private static int sizeH(Node<T> cur) {
    if (cur == null) {
        return 0;
    } else {
        return 1 + sizeH(cur.left) + sizeH(cur.right);
    }
}
~~~

First, note that we will make one recursive call per element of the tree.
However, it is not necessarily the case that all of these function calls will be active at the same time.
To see this, consider the binary search tree from before:

~~~
      5
     / \
    /   \
   /     \
  3       7
 / \     / \
1   2   6   8
~~~

And consider the implementation of `sizeH`.
Keeping in mind that we evaluate expressions left-to-right, `sizeH` descends the left-hand side of the tree (exploring `cur.left`) and then the right-hand side of the tree (exploring `cur.right`).
Before we explore the right-hand side of the tree, we return from all the recursive calls corresponding to exploring from the left-hand side.

Dually, any given call to `sizeH` does not return until its recursive calls to its left and right subtrees return.
That means that the number of recursive calls active at a given node corresponds to the _length of the path_ from the root to that node.
For example, in the above tree, when we make the recursive call to compute the size of the tree rooted at 6, the recursive calls to 7 and 5 are pending.
Thus, the amount of stack space used up by our recursion is proportional to the length of the longest path from root to a leave of the tree.

The length of such a path depends on the tree's shape as discussed in the previous section.
With a degenerate tree, the maximal length is $\mathcal{O}(N)$; with a balanced tree, the maximal length is $\mathcal{O}(\log N)$.
Thus, we expect that the space complexity of our tree operations is $\mathcal{O}(\log N)$, assuming that the tree is roughly balanced.

Note that this space complexity cost is essential to the operations; it is not an artifact of our implementation choice of recursion.
In particular, if we used iteration to implement `sizeH`, we would need an explicit stack data structure to hold pending nodes that we must visit.
We can perform a similar analysis to discover that we will need to hold at most $\mathcal{O}(\log N)$ nodes in our stack at any given point in time, assuming that the tree is roughly balanced.

~~~admonish question title="Making a Tree (‡)"
On paper, build a binary search tree by inserting the following values into the tree in the order given:

+   5, 8, 1, 3, 9, 10, 2

Show the evolution of the tree after each insertion.
~~~