# Counting

Recall that the _cardinality_ or _size_ of a set $S$, written $|S|$, is the number of elements contained in $S$.
In some cases, computing the size of a set is straightforward.
For example, if $S = \set{a, b, c, d, e}$ then $|S| = 5$ by inspection of the definition of $S$.
However, suppose we have:

$$
\begin{gather*}
S_1 = \set{a, b, c, d} \\
S_2 = \set{a, c, e, f}.
\end{gather*}
$$

What is $|\mathcal{P}({S_1 \cup S_2})|$?
We can compute the contents of $\mathcal{P}({S_1 \cup S_2})$ and then count the number of elements.
However, if there are _many_ elements in the set, it might be impractical to compute-and-count.
Furthermore, what if don't know the contents of $S_1$ and $S_2$?
We would like to express the cardinality of this quantity in terms of $|S_1|$ and $|S_2|$.

In this chapter, we focus on techniques for calculating the cardinality of _finite sets_, a branch of mathematics called _enumerative combinatorics_.
As computer scientists, we are interested in not just modeling data but also performing operations over this data.
Thus, we care greatly about techniques for calculating the sizes of sets as their sizes ultimately influence the expected runtime of the algorithms we development.

For example, consider the problem of determining the optimal route for a delivery truck to visit a number of businesses in a city and return back to the delivery center.
This problem, a variant of the _traveling salesman problem_, is a fundamental problem with applications to a wide range of domains.
A simple algorithm is as follows:

> Enumerate every possible path through the city that originates from the delivery center and pick the shortest among them that (a) visits every business and (b) returns to the center.

How long would this program take to run?
This is equivalent to asking the following question: what is the _size_ of the set of all possible paths through the city?
It turns out for a sufficiently well-connected city, there are an _exponential_ number of paths to consider, far too many to simply enumerate in a reasonable amount of time.
To see why this is the case, we will develop _principles_ for counting sets of increasing complexity, using our set operations as a guide.

## The Sum and Product Rules

Let's first explore how we might calculate the cardinality of the _union_ of two sets.
Suppose that we have the following sets:

$$
\begin{gather*}
S_1 = \set{a, b, c} \\
S_2 = \set{d, e}
\end{gather*}
$$

$S_1 \cup S_2 = \set{a, b, c, d, e}$ so $|S_1 \cup S_2| = 5$.
$|S_1| = 3$ and $|S_2| = 2$ so it is tempting to infer that the cardinality of the union of two sets is the sum of their cardinalities.
However, consider the following alternative sets:

$$
\begin{gather*}
S_1 = \set{a, b, c} \\
S_2 = \set{a, c}
\end{gather*}
$$

$|S_1| = 3$ and $|S_2| = 2$ but $S_1 \cup S_2 = \set{a, b, c}$ and thus $|S_1 \cup S_2| = 3$.
Therefore, in order to assert that the cardinality of the union of sets is the sum of the cardinalities of the sets, we must also require that the sets do not possess elements in common!
This gives rise to the _sum rule_ for set cardinalities:

~~~admonish info title="Definition (Sum Rule for Sets)"
If $S_1 \cap S_2 = ∅$ then $|S_1 \cup S_2| = |S_1| + |S_2|$.
~~~

Note that we capture the notion of "sets do not possess elements in common" with the condition $S_1 \cap S_2 = ∅$.

~~~admonish problem title="Exercise (Boundaries"
Give a _lower bound_ and _upper bound_ for the cardinality of the union of two sets $S_1$ and $S_2$.
Justify your bounds in a sentence or two a piece.
~~~

We can generalize the sum rule to _any_ number of sets as long as they are _pairwise disjoint_.

~~~admonish info title="Definition (Pairwise Disjoint)"
Say that a collection of $k$ sets $S_1, \ldots, S_k$ are _pairwise disjoint_ if for any pair of such sets $S_i$ and $S_j$ with $i \neq j$ that $S_i \cap S_j = ∅$.
~~~

Then we can say that for a collection of sets $S_1, \ldots, S_k$, if they are pairwise disjoint, then $|S_1 \cup \cdots \cup S_k| = |S_1| + \cdots + |S_k|$.

Next, let's consider the _Cartesian product_, $S_1 × S_2$.
Suppose that we again have:

$$
\begin{gather*}
S_1 = \set{a, b, c} \\
S_2 = \set{d, e}
\end{gather*}
$$

Then:

$$
\begin{align*}
S_1 \times S_2 =&\; \{\, (a, d), (a, e) \\
&\; , (b, d), (b, e) \\
&\; , (c, d), (c, e) \,\}
\end{align*}
$$

So $|S_1 \times S_2| = 6$.
Because $|S_1| = 3$ and $|S_2| = 2$, we can infer that the cardinality of the Cartesian product is the product of the input sets.

Indeed, this is the case, which gives us the _product rule for sets_.

~~~admonish info title ="Definition (Product Rule for Sets)"
$|S_1 \times S_2| = |S_1| \cdot |S_2|$.
~~~

~~~admonish problem title="Exercise (Duplicate Denouncement)"
The sum rule places a pairwise disjointness restriction on its input sets.
Is the same restriction necessary for the product rule?
Calculate the size of the Cartesian product $S_1 \times S_2$ of the following sets:

$$
\begin{gather*}
S_1 = \set{a, b, c} \\
S_2 = \set{a, b, c}
\end{gather*}
$$

And use this example to answer the question of whether pairwise disjointness is necessary to apply the product rule.
~~~

### Counting as Choices

Set operations give us a formal, definition-based view of counting elements in sets.
A useful, higher-level view of our counting principles are phrasing them as the number of possible _choices_ we can make from a given set.
This view of _counting as choice_ is particularly useful for applying counting principles to real-world examples.

For example, let's consider the sum rule.
Suppose that we have on a field trip:

+   10 first grade students,
+   15 second grade students, and
+   8 third grade students.

There are $10 + 15 + 8 = 23$ different students we can choose from, overall.
If we label the sets $S_1$, $S_2$, and $S_3$, respectively, then the sum rule tells us that:

$$
|S_1 \cup S_2 \cup S_3| = |S_1| + |S_2| + |S_3| = 10 + 15 + 8 = 23.
$$

Set union and the sum rule allow us to consider choices when we _combine sets into a single set_.
In contrast, Cartesian product allows us to make _independent choices from a collection of sets_.
Each of the $k$ sets of a Cartesian product represents a different pool of elements to choose from.
The Cartesian product enumerates all the different ways we can generate a _tuple_ of $k$ elements by choosing one element from each pool.

~~~admonish info title="Definition (Tuple)"
A _tuple_ is a fixed-size collection of $k$ elements, written as a $(x_1, \ldots, x_k)$ where the order between elements is relevant.
We call a tuple of $k$ elements a _$k$-tuple_, _e.g._, a pair is a 2-tuple.
~~~

Suppose that we have two hats, five shirts, three pairs of pants, and two pairs of shoes.
The total number of outfits we can put together consisting of a hat, shirt, pants, and shoes is:

$$
2 × 5 × 3 × 2 = 60.
$$

Alternatively, we can think of the problem as having four sets, one for hats, shirts, pants, and shoes. An outfit is, therefore, a 4-tuple with elements drawn from each of these sets.
The Cartesian product of these four sets then gives us all possible outfits as 4-tuples.

### Combinatorial Descriptions

Consider the total quantity of outfits we derived previous:

$$
2 × 5 × 3 × 2.
$$

This _unsimplified formula_ actually tells us quite a bit about the quantity in question.
Because of the product rule, we know that the quantity represents the number of ways we can form choices from pools of two, five, three, and two choices, respectively.

In contrast, consider in isolation the value that this formula evaluates to:

$$
60.
$$

While technically accurate, this value tells us very little about the _structure_ of the quantity or object that we are counting!

When counting quantities, we will universally favor giving _unsimplified formulae_ for our set cardinalities rather than simplifying the formulae to a final result.
We call these formulae _combinatorial descriptions_ because these unsimplified cardinality formulae communicate the various choices we made in constructing an object in terms of set operations and our counting principles.
In effect, a combinatorial description serves as a _terse proof_ that an object can be decomposed used our counting principles as long as we know how to interpret the arithmetic operations contained within!

## The Power Set

We saw in the previous reading that the power set of a set $S$ is the set of all subsets that you can make from $S$.
If $S = \set{a, b, c}$, then:

$$
\begin{align*}
S =&\; \{\, \emptyset \\
&\; , \set{a}, \set{b}, \set{c} \\
&\; , \set{a, b}, \set{b, c}, \set{a, c} \\
&\;, \set{a, b, c} \,\}.
\end{align*}
$$

So $|S| = 2$ and $|\mathcal{P}({S})| = 8$.

~~~admonish problem title="Exercise (Powerful Data)"
Calculate the power sets and their cardinalities for a variety of
sizes from $0$ to $4$.
You can also try computing the power set for a set of size $5$ or larger.
However, be wary that the size of the power set grows very quickly as we shall discuss next!
~~~

With additional data points, we can see that the $|\mathcal{P}({S})|$ seems to grow _exponentially_ with the size of the input set!
Indeed, the following property of the size of a power set is true:

~~~admonish info title="Claim (Power Set Cardinality)"
For any set $S$ with cardinality $k$, $|\mathcal{P}(S)| = 2^{k}$.
~~~

It is difficult to validate this empirically.
If this proposition is true, then trying to calculate the power set of a 10-element set ought to result in $2^{10} = 1024$ elements which is certainly not reasonable to do by-hand!
Instead, we would like to _prove_ that this formula holds through a _counting argument_ that establishes the cardinality of a set without needing to enumerate elements.
We instead use our _counting principles_ in a systematic fashion to describe how to _build_ or _choose elements_ from that set.

Let's see how we can do this to justify our claim about power sets.

~~~admonish check title="Proof"
The power set of $S$ contains all the possible subsets of $S$.
Consider constructing one such subset.
Each of the $k$ elements of $S$ can be either included in the subset or not.
By the product rule, this means that the total number of possible such subsets we can construct is

$$
\underbrace{2 \times \ldots \times 2}_{k} = 2^k
$$
~~~

In other words, we can _choose_ a subset of a set by forming a $k$-tuple.
Each element of the $k$-tuple corresponds to one of the elements of the set.
We can then assign a boolean value to each position indicating whether that element is _in_ or _out_ of the subset.

As a concrete example, suppose we have $S = \set{a, b, c}$.
Then the tuple $(t, f, t)$ corresponds to the subset $\set{a, c}$.

This particular counting argument, _the in-out argument_, is particularly useful in computer science because we frequently work with binary data (0--1) or boolean choices (yes-or-no).
As an example, suppose we have a piece of datum that is $k$ bits wide, _e.g._, 32 bits for an integer.
Recall that a bit can either be set to 0 or 1.
We can think of each integer as the collection of bits in the 32 bit sequence that are set 1.
Since there are 32 bits in the collection, there must be $2^{32}$ such possible sets and thus $2^{32}$ possible integers.
(Note that other bits of the datum might be devoted to other tasks, so the number of _effective_ datum possible from $k$-bits might be different than this raw quantity!)

## The Inclusion-Exclusion Principle

The sum rule gives us a cardinality of the union of sets provided they are pairwise disjoint.
However, can we use intersection to precisely characterize the size of the union without the need for a restriction?

To explore this idea, consider the following statistics about college majors:

-   There are 45 computer science majors.
-   There are 20 math majors.
-   There are 30 economics majors.

And suppose that we want to compute the total number of CS, math, and econ majors at the college.
It would be tempting to say that the total is $45 + 20 + 30 = 95$ but what about a double major?
If a person majors in both computer science and math, they would be counted _twice_, once for the count of CS majors and once for the count of math majors.
If we knew how many double majors there were _of all possible combinations_, we can subtract them out once to account for this overcounting.

Suppose that we know that:

-   There are 10 computer science and math double majors.
-   There are 5 computer science and economics double majors.
-   There are 5 math and economics double majors.

Then, we might say that the total number of majors is:

$$
45 + 20 + 30 - 10 - 5 - 5 = 75.
$$

However, what about those rare _triple majors_?
Consider such a single triple major:

+   The triple major appears once in each of the individual major counts, so we _triple counted_ them in our addition.
+   The triple major appears once in each of the double major counts, so we _triple counted_ them again in our subtraction.

That means we need to add them back in one last time!
If we know that:

-   There are 2 computer science, math, and economics triple majors.

Then the total number of majors is:

$$
45 + 20 + 30 - 10 - 5 - 5 + 2 = 77.
$$

Intuitively, adding up all the singleton sets of majors overcounts the double major overlap, so we subtract them out.
But by subtracting out the double major overlap, we undercount the triple majors, so we add them back in.

We generalize this alternation of addition and subtraction to account for overlap in the _Inclusion-exclusion Principle_ or _Generalized sum rule_:

~~~admonish info title="Definition (Inclusion-exclusion Principle)"
Let $S_1, \ldots, S_k$ be a collection of sets.
Then the cardinality of the union of these sets is given by:

$$
\begin{align*}
|S_1 \cup \cdots \cup S_k| &=\; |S_1| + \cdots + |S_k| \\
&\; - |S_1 \cap S_2| - \cdots - |S_{k-1} \cap S_{k}| \\
&\; + |S_1 \cap S_2 \cap S_3| + \cdots + |S_{k-2} \cap S_{k-1} \cap S_{k}| \\
&\; \ldots
\end{align*}
$$
~~~

In other words, the cardinality of the union of a set of sets $S_1 ∪ … ∪ S_k$, written $|S_1 ∪ … ∪ S_k|$ is the sum of all the cardinalities of the singleton sets, the difference of all the intersection of pairs of sets, the sum of all the intersection of triples of sets, and so forth.

~~~admonish problem title="Exercise (Culinary Master, ‡)"
Imagine that you are experimenting in the kitchen with a new dish and you need to add some vegetables, spices, colors, and sweeteners.
Suppose that you have $a$ kinds of vegetables, $b$ kinds of spices, $c$ kinds of colors, and $d$ kinds of sweeteners to choose from.
Give combinatorial descriptions (_i.e._, unevaluated counting formulae) for each of the following quantities:

1.  The total number of vegetables and spices to choose from.
2.  The number of ways to combine a single kind of vegetable, spice, and sweetener into the dish.
3.  The number of ways to combine pairs of vegetables and spices and pairs of colors and sweeteners into the dish.
~~~
