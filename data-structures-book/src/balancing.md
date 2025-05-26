# Balancing

We would like the nodes of our trees to be evenly distributed among its branches, i.e., _balanced_.
When this is the case, the height of the tree is $\mathcal{O}(\log N)$ where $N$ is the number of elements stored in the tree.
Consequently, any operation that traverses the tree from the root down to a constant number of its branches is also $\mathcal{O}(\log N)$.
For example, finding an element in a binary search tree requires that we traverse down one branch of the tree thanks to the binary search property.

However, we saw that it is very easy to unbalance a tree through naive implementation of our tree-modifying operations such as insertion and deletion.
What we would like to do is maintain the balanced nature of the tree as an additional _invariant_ of the structure, similar to the binary search tree invariant.
In the previous chapter, we saw how we could maintain the BST invariant _locally_ with insertion, leading to a (relatively) straightforward implementation. 
In contrast, deletion required _non-local_ changes to the tree in order to maintain the invariant, leading to a more complex operation.

The additional operations required to maintain a balanced tree fall into this latter category.
Our usual strategy here will be to perform the modification to the tree, e.g., an insertion or deletion, and then fix up the tree after the fact.
This _balancing_ operation requires significant, non-local operations to restore balancing to the tree.

But what does it mean for a tree to be balanced?
Unlike the BST invariant which is straightforward to state and enforce, there is much more "play" in how we define "balanced."
To see why this is the case, observe how we can likely agree that this tree is maximally balanced:

~~~
      o
     / \
    /   \
   o     o
  / \
 o   o
~~~

And this tree is maximally unbalanced:

~~~
o
 \
  o
   \
    o
     \
      o
       \
        o
~~~

But what about this tree?
Is it "balanced enough?"

~~~
    o
   / \
  o   o
 / \   \
o   o   o
   / \
  o   o
~~~

In addition to identifying the right properties the tree on which to hang out definition of "balanced," we must also, by virtue of our definition, weigh in on in-between cases like the above tree.
If we are more strict in our definition, we likely improve the results of balancing and thus performance of subsequent operations but at the cost of making the balancing operation itself more complex.
A less strict definition leads to potentially less balanced trees and complex balancing.

## AVL Trees

To arrive at a suitable balancing invariant, let's try to crystalize the salient property of trees that we are "seeing" when we label a tree balanced versus unbalanced:

~~~
       o
      / \
     /   \
    /     \ 
   o       o
  / \     /
 o   o   o
    /
   o
~~~

In this example, it appears that the tree is somewhat unbalanced.
Note that our perception doesn't have to do with the number of nodes on each side of tree.
Relative to the root, the left and right subtrees have the same number of nodes.
However, the _heights_ of the subtrees—the maximal lengths of the paths from the root to a leaf—are different!
It seems promising to use the heights of the subtrees as the basis for our balancing invariant.

More specifically, let's define the _balance factor_ $/mathrm{bf}(t)$ to be the differences in heights of the left and right subtrees of tree $t$.
For example, the balance factor for this tree is $2-1 = 1$.

~~~
       o
      / \
     /   \
    /     \ 
   o       o
  / \     /
 o   o   o
    /
   o
~~~

And the balance factor of this tree is $0-3 = - 3$:

~~~
o
 \
  o
   \
    o
     \
      o
       \
        o
~~~

With $\mathrm{bf}(t)$ defined, we now need to determine which balance factors are acceptable.
If we enforce the following invariant:

> For any subtree $t$ of the overall tree, $|\mathrm{bf}(t)| \leq 1$. In other words, the balance factor for any subtree is, at worst, one node.

We arrive at the _AVL tree_ (named after its designers, Gregory Aldeson-Velsky and Evgenil Landis).
An AVL tree is a binary search tree that enforces this AVL tree invariant.
For example, the first example tree above with a balance factor of $1$ is an AVL tree but the second example tree is not because its balance factor is $-3$.

Note that operations over an AVL tree that do not modify its nodes, e.g., `contains` or `size`, do not change relative to a standard binary search tree.
However, when we `insert` or `delete` into the tree, we may _invalidate_ the AVL tree invariant regarding the balance factor of the tree.
As we will explore in the lab exercises for this class, we must employ a series of fixup operations called _tree rotations_ to rebalance the tree efficiently!
