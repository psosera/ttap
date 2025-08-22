# Sets and Their Operations

As computer scientists, we aim to use mathematics to _model_ computational phenomena in order to

1. Precisely understand how the phenomena works (usually for the purposes of implementing that phenomena as a computer program) and
2. Abstract differences between seemingly unrelated phenomena to discover how they are related.

Very often, these computations involve collections of data, for example, a class management system might store a collection of students, or a server might need to track the set of computers it can directly communicate with on the network.
Furthermore, these computations can frequently be phrased in terms of manipulations of or queries over these data, for example, generating pairs of students for a class activity, or finding if there exists a series of computers that connect the server to some other computer on the network.

To model data, we frequently resort to mathematical _sets_.

~~~admonish info title="Definition (Set)"
A _set_ is a collection of distinct objects, _i.e._, there are no duplicates in a set.
~~~

_Set theory_ is the branch of mathematics that studies how we construct and manipulate sets.
Because virtually any mathematical model includes data of some sort---integers, real numbers, or more abstract objects---sets form the basis of formal mathematics.
In addition to using sets directly in our models, we’ll also see sets in every other field of mathematics.
In this unit, we briefly explore the field of _set theory_ with an eye towards building up a working understanding of what set theory provides us and what questions we can answer by framing our problems in terms of sets.

# Specifying Sets of Objects

When discussing sets, we must first define the _universe_ under consideration.

~~~admonish info title="Definition (Universe, Set Theory)"
The _universe_ or _domain of discourse_ $\mathcal{U}$ (LaTeX: `\mathcal{U}`) is the collection of elements that we may include in our sets.
~~~

As a concrete example, consider representing a collection of students at Grinnell using sets.
We can, therefore, consider our universe to be _all students at the college_.
We typically denote the universe under consideration by the variable $\mathcal{U}$, so we might say:

> Let $\mathcal{U}$ be the collection of all students at the college.

In many cases, the universe may be inferred by context, _e.g._, if our sets contain integers, then we can likely infer from context that $𝒰$ is the collection of integers.
However, regardless of whether $𝒰$ is stated explicitly or inferred, we must keep it in mind as many of our operations over sets will require this knowledge.

With our universe defined, we may now define sets over this universe.
For example, for simplicity's sake, suppose that the college only contains five students: Jessica, Sam, Phillip, Jordan, and Elise.
We might specify a set $S_1$ containing the first three students as
follows:

$$
S_1 = \{\, \text{Jessica}, \text{Sam}, \text{Phillip} \,\}
$$

We say that Jessica, Sam, and Phillip are all _elements_ of the set $S_1$.
The elements of the set are surrounded by curly braces.
(Note that in LaTeX, you will need to _escape_ the curly braces, _i.e._, `\{ ... \}`.
You may also consider putting in an explicit thin space, `\,`, between the braces, _i.e._, `\{\, ... \,\}`.)

The primary query that we can ask of a set is whether a set contains a particular element.
For example, Jessica is an element of $S_1$ or, more colloquially, we say that Jessica is _in_ $S_1$.
We can think of *set inclusion* as a _proposition_, between a potential element of a set and a set.

~~~admonish info title="Definition (Set Inclusion)"
We say that value $x$ is _in_ a set $S$ if $x$, written $x \in S$ (LaTeX: `\in`), is an element of $S$.
~~~

With this, we can write the inclusion relationship between Jessica and $S_1$ as follows:

$$
\text{Jessica} \in S_1.
$$

Likewise, we know that Elise is not in $S_1$.
Like how we write $(\neq)$ (LaTeX: `\neq`) to say that two values are not equal, we write $\notin$ (LaTeX `\notin`) to say that an element is not in a set.
For Elise, we would then write

$$
\text{Elise} \notin S_1.
$$

The order of the elements does not matter in a set, so the following set

$$
S_2 = \{\, \text{Sam}, \text{Jessica}, \text{Phillip} \,\}
$$

is equivalent to $S_1$ because they contain exactly the same elements.
As mentioned previously, sets only contain unique elements, so our sets cannot duplicates.
From this definition, we cannot have a set that, for example, contains Jessica twice.

# Set Comprehensions

An alternative to explicitly enumerating all the elements of a set, we can also specify a set by way of a *set comprehension*.
For example, the following set:

$$
S_3 = \{\, s \mid s \in 𝒰, \text{$s$ is a student whose name starts with "J"} \,\}
$$

is equivalent to the set $\{\, \text{Jessica}, \text{Jordan} \,\}$.

A set comprehension is broken up into two parts separate by a pipe ($\mid$) (LaTeX: `\mid`).
To the left of pipe is the _output expression_ which normally involves one or more variables.
To the right of the of the pipe is a collection of _qualifiers_ that refine which elements are included in the set.
There are two sorts of qualifiers we might include:

+   *Generators* describe what values the variables of the output expression take on.
    In the example above, we quantify that $s$ is drawn from our universe $\mathcal{U}$ by stating $s \in \mathcal{U}$.
    This quantification is implicitly *universal* in nature: we really intend $\forall s \ldotp s \in 𝒰$ however, we traditionally do not include the extra verbiage of the $\forall$ symbol.
    (Note that we can think of our universe itself as a set!)

-   _Predicates_ describe conditions that must hold of the values involved to include it in the set.
    For an element to be included in the set, it must satisfy _all_ of the predicates of the comprehension.

By combining the generator and the predicate of $S_3$, we see that $S_3$ will contain all the students of the college whose name starts with "J".

Note that we frequently elide the generator from our set comprehensions when it is clear from context what the variable ranges over.
For example, the predicate already describes $s$ as a student, so it is unnecessary to express that it is also a member of $\mathcal{U}$.
We can more concisely write $S_3$ as follows:

$$
S_3 = \{\, s \mid \text{$s$ is a student whose name starts with "J"} \,\}.
$$

Set comprehensions are a powerful, flexible method for describing the members of a set.
To illustrate this, let's consider another simple universe, the universe of *natural numbers*.
The natural numbers consist of zero and the positive integers.
First, let's define a set over this universe, _e.g._,

$$
  S_4 = \{\, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 \,\}.
$$

Now let's consider specifying some more complicated set comprehensions.
For example, we may denote the sets of even and odd numbers in the range zero through ten as:

$$
\begin{align*}
S_\mathsf{even} =\;& \{\, n \mid n \in S_4, n \bmod 2 = 0 \,\} \\
S_\mathsf{odd} =\;& \{\, n \mid n \in S_4, n \bmod 2 = 1 \,\}.
\end{align*}
$$

In contrast, the following set comprehension contains a non-trivial expression:

$$
S_5 = \{\, 2n \mid n \in S_4 \,\}
$$

For each $n \in U$, $S_5$ contains the value $2n$.
Thus, $S_5 = \{\, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20 \,\}$.

When a comprehension includes a single variable, the set ranges over all the elements from the variable is drawn from.
When a comprehension includes multiple variables, the set ranges over all the possible _combinations_ of values from which the variables are drawn.
For example, consider the following set:

$$
S_6 = \{\, (n_1, n_2) \mid n_1 \in S_\mathsf{even}, n_2 \in S_\mathsf{odd} \,\}
$$

Is equivalent to the larger set:

$$
\begin{align*}
  S_6 = \{ & (0, 1), (0, 3), (0, 5), (0, 7), (0, 9) \\
  & (2, 1), (2, 3), (2, 5), (2, 7), (2, 9) \\
  & (4, 1), (4, 3), (4, 5), (4, 7), (4, 9) \\
  & (6, 1), (6, 3), (6, 5), (6, 7), (6, 9) \\
  & (8, 1), (8, 3), (8, 5), (8, 7), (8, 9) \\
  & (10, 1), (10, 3), (10, 5), (10, 7), (10, 9) \,\}.
\end{align*}
$$

First, let's consider the expression portion of the comprehension.
The expression consists of a _pair_ of elements $(n_1, n_2)$, so we expect the elements of $S_6$ to be pairs.
Furthermore, $n_1$ and $n_2$ are drawn from the sets $S_\mathsf{even}$ and $S_\mathsf{odd}$, respectively, so we expect the pairs to contain natural numbers.

In effect, by including two generators, we consider all possible *pairs* of elements drawn from the two generators.
We can alternatively interpret how these set comprehension "compute" their elements through a series of nested for-loops.
In effect, the comprehension computes:

~~~python
result = []
for n_1 in S_even:
    for n_2 in S_odd:
        pair = (n1, n2)
        result.append(pair)
~~~

In general, when we have any collection of generators, we can think of them as a collection of nested for-loops where the body of the loop includes a new element in the set according to the output expression.

**Standard Sets of Objects**

Very often, our sets a drawn from a standard universes, usually over numbers.
Rather than repeating the description of these sets, we denote them using the following variables written in blackboard font (LaTeX: `\mathbb{...}`):

$$
\begin{align*}
\mathbb{N}   =\;& \{\, 0, 1, 2, \ldots \,\} & \text{Natural Numbers} \\
\mathbb{Z}   =\;& \{\, \ldots, -2, -1, 0, 1, 2, \ldots \,\} & \text{Integers} \\
\mathbb{Z}^+ =\;& \{\, n \mid n \in \mathbb{Z}, n > 0 \,\} & \text{Positive Integers} \\
\mathbb{Z}^- =\;& \{\, n \mid n \in \mathbb{Z}, n < 0 \,\} & \text{Negative Integers} \\
\mathbb{Q}   =\;& \{\, \ldots, -1, 0, 1, \frac{1}{2}, \ldots \,\} & \text{Rationals} \\
\mathbb{R}   =\;& \{\, \ldots, -3, 2.5, \pi, \ldots \,\} & \text{Reals}
\end{align*}
$$

# Finite and Infinite Sets

In the situation where our set contains a finite number of elements, we denote the *size* of a set by $|-|$, the set surrounded by pipes.
For example, the size of $S_6$, the set of pairs of even and odd elements drawn from the range zero through ten is: $|S_6| = 30$.

However, a set need not contain a finite number of elements.
The universes of numbers we discussed previous all contain an infinite number of elements.
However, we can also directly construct sets that are also infinite in size.
For example, consider the set:

$$
S_7 = \{\, n \;|\; n \in \mathbb{N}, n \bmod 10 = 0 \,\}.
$$

$S_7$ contains all natural numbers that are a multiple of 10.
There is clearly an infinite amount of such numbers, but this poses no problem for defining what the set $S_7$ contains.
As we shall see, whether a set is finite or infinite does not change the behavior how the basic operations over sets that we consider next.
However, infinite sets pose some significant conundrums for set theory that will briefly explore at the end of this chapter.

~~~admonish problem title="Reading Exercise (Descriptions)"
Write down formal set comprehensions for each of the following descriptions of sets:

1. The set of all natural numbers that are either less than five or greater than 20.
2. The set of all pairs of integers such that the sum of the pair of numbers is equal to zero.
3. The set of all real numbers that are also positive integers.
~~~

# Set Operations

With our basic definitions for sets—inclusion and set comprehension—we can define the _fundamental operations_ over sets.

## Union and Intersection

The union of two sets $S_1$ and $S_2$, written $S_1 ∪ S_2$, produces a set that contains all of the elements drawn from either of these sets.
For example, if $S_1 = \{\, 2, 3, 5 \,\}$ and $S_2 = \{\, 3, 5, 9 \,\}$ then $S_1 ∪ S_2 = \{\, 2, 3, 5, 9 \,\}$ (keeping in mind that duplicates are discarded with sets).

~~~admonish info title="Definition (Set Union)"
The _union_ of two sets $S_1$ and $S_2$, written $S_1 \cup S_2$ (LaTeX: `\cup`) is defined as:

$$
S_1 \cup S_2 = \{\, x \mid x \in S_1 \vee x \in S_2 \,\}.
$$
~~~

In contrast, the intersection of two sets $S_1$ and $S_2$, written $S_1 \cap S_2$, produces a set that contains the elements that are found in both $S_1$ and $S_2$.
For example, if $S_1 = \{\, 2, 3, 5 \,\}$ and $S_2 = \{\, 3, 5, 9 \,\}$ then $S_1 ∩ S_2 = \{\, 3, 5 \,\}$.

~~~admonish info title="Definition (Set Intersection)"
The _intersection_ of two sets $S_1$ and $S_2$, written $S_1 \cap S_2$ (LaTeX: `\cap`) is defined as:

$$
S_1 \cap S_2 = \{\, x \mid x \in S_1 \wedge x \in S_2 \,\}.
$$
~~~

Note the parallels between the definitions of these set theoretic operations and the logical connectives we explored earlier:

+   Set union $(\cup)$ is defined in terms of logical disjunction $(\vee)$.
+   Set intersection $(\cap)$ is defined in terms of logical conjunction $(\wedge)$.

This is no coincidence!
We can think of union and intersection as the _set-theoretic realization_ of logical disjunction and conjunction, respectively.
Because they are defined directly in terms of their logical counterparts, union and intersection behave similarly to them as well.

## Difference and Complement

The difference of two sets $S_1$ and $S_2$, written $S_1 - S_2$, produces a set that contains the elements of $S_1$ that are not also in $S_2$.
For example, if $S_1 = \{\, 2, 3, 5 \,\}$ and $S_2 = \{\, 3, 5, 9 \,\}$ then $S_1 - S_2 = \{\, 2 \, \}$.
Note that $3$ and $5$ are removed from the difference since they are in $S_2$.

~~~admonish info title="Definition (Set Difference)"
The _difference_ of two sets $s_1$ and $s_2$, written $s_1 - s_2$ is defined as:

$$
s_1 - s_2 = \{\, x \mid x \in s_1 ∧ x ∉ s_2 \,\}.
$$
~~~

The complement of a set $S$, written ${\overline{S}}$, is the set of elements that are not found in this set.
Note that this requires knowledge of what our universe $U$ is in order to constrain what elements are not in the set in question.
Say that our universe $U$ is over the finite set $U = \{\, 1, 2, 3, 4, 5 \,\}$.
Then if $S = \{\, 2, 3, 5 \,\}$, then ${\overline{S}} = \{\, 1, 4 \,\}$.
In contrast, if we expand our universe to be the natural numbers, *e.g.*, $U = ℕ$, then ${\overline{S}} = \{\, x \mid x ∈ ℕ ∧ x ≠ 2 ∧ x ≠ 3 ∧ x ≠ 5 \,\}$.
Formally, we can write this as:

~~~admonish info title="Definition (Set Complement)"
The _complement_ of a set $S$, written $\overline{S}$ (LaTeX: `\overline{...}`) is defined as:

$$
\overline{S} = \{\, x \mid x ∉ S \,\}.
$$
~~~

Note that set complement is defined in terms of _logical negation_ and is, thus, strongly identified with logical negation in the same way union and intersection identify with conjunction and disjunction.
There is not a direct analog to set difference in logic, but we can see that difference can be written in terms of the other three operators via a direct translation of its formal definition:

$$
S_1 - S_2 \equiv S_1 \cap \overline{S_2}.
$$

~~~admonish problem title="Exercise (Different Strokes)"
Consider the following sets:

$$
\begin{gather*}
S_1 = \{\, 1, 3, 4, 6, 8 \,\} \\
S_2 = \{\, 3, 4, 5, 7, 9 \,\}
\end{gather*}
$$

Demonstrate the equivalence of set difference's definition with the equivalent formulation in terms of intersection and complement by (a) deriving $S_1 - S_2$ in terms of the formal definition of set difference and (b) checking the equivalence on this example by "executing" $S_1 \cap \overline{S_2}$ and observing that you obtain identical results.
~~~

## Cartesian Product

The Cartesian product of two sets $S_1$ and $S_2$, written $S_1 × S_2$, is the set of all the possible _pairs_ of elements drawn from $S_1$ and $S_2$.
The first element of these pairs is drawn from $S_1$, and the second element of these pairs is drawn from $S_2$.
It is a bit easier to see how the Cartestian product works by using sets of abstract symbols rather than numbers.
First, let's consider $S_1 = \{\, †, ‡, ⊞ \,\}$ and $S_2 = \{\, ▷, ◁ \,\}$.
The Cartestian product of these two sets is:

$$
\begin{align*}
S_1 × S_2 = \{\,&
  (†, ▷), (†, ◁), \\
& (‡, ▷), (‡, ◁), \\
& (⊞, ▷), (⊞, ◁) \,\}.
\end{align*}
$$

Note that each element of $S_1$ is paired with each possible element of $S_2$.
When writing out the Cartesian product, it’ll be useful to do so in a systematic, grid-like manner like above where each row corresponds to a choice of element from $S_1$ and each column corresponds to a choice of element from $S_2$.

With this in mind, we can return to our universe of natural numbers and consider our canonical sample sets.
For example, if $S_1 = \{\, 2, 3, 5, 8 \,\}$ and $S_2 = \{\, 3, 5, 9 \,\}$ then:

$$
\begin{align*}
S_1 × S_2 = \{\,
& (2, 3), (2, 5), (2, 9), \\
& (3, 3), (3, 5), (3, 9), \\
& (5, 3), (5, 5), (5, 9), \\
& (8, 3), (8, 5), (8, 9) \,\}.
\end{align*}
$$

Note here that elements shared in common between $S_1$ and $S_2$ are not discarded like with disjunction.
This is because such elements always result in unique pairs in the resulting set.

~~~admonish info title="Definition (Cartesian Product)"
The _Cartesian product_ of two sets $S_1$ and $S_2$, written $S_1 \times S_2$ (LaTeX: `\times`) is defined as:

$$
S_1 × S_2 = \{\, (x_1, x_2) \mid x_1 ∈ S_1 ∧ x_2 ∈ S_2 \,\}.
$$
~~~

## Subsets and Set Equality

A set is a subset of another set if all the elements of the first set
are contained in the second set.
For example, if we have $S_1 = \{\, 2, 5 \,\}$ and $S_2 = \{\, 1, 2, 3, 4, 5 \,\}$, then $S_1$ is a subset of $S_2$, written $S_1 ⊆ S_2$.
In contrast, $S_2$ is not a subset of $S_1$, written $S_2 \not\subseteq S_1$.
Note that this is not an operation over sets (that produces another set) but, rather, a proposition over sets (that is potentially provable).

The basic proposition we can assert about a set $S$ is whether an
element $x$ is found inside the set, written $x ∈ S$.
We can use this
inclusion proposition to formally define subset:

~~~admonish info title="Definition (Subset)"
We say that $S_1$ is a _subset_ of $S_2$, written $S_1 \subseteq S_2$ (LaTeX: `\subseteq`) if:

$$
S_1 \subseteq S_2 \Leftrightarrow \forall x \ldotp x \in S_1 \rightarrow x \in S_2.
$$
~~~

In other words, if $S_1$ is a subset of $S_2$ then every element of $S_1$ must also be an element of $S_2$.

A _proper subset_, written $S_1 \subset S_2$ (LaTeX: `\subset`), is where $S_1 \subseteq S_2$ but $S_1 \neq S_2$.
Note that when we say "subset", we will implicitly mean "subset-or-equal" and use the term "proper subset" to denote this case where we require that the two sets are also not equal.

If we know that $x$ and $y$ are numbers and $x ≤ y$ and $y ≤ x$, we know that $x = y$.
This is because if $x$ and $y$ cannot both be less than each other; they must, therefore, be equal to each other.
Likewise, if we know that $S_1 ⊆ S_2$ and $S_2 ⊆ S_1$, we know that $S_1$ and $S_2$ must be equal.

This realization gives us an alternative definition of set equality in terms of subsets.

~~~admonish info title="Definition (Set Equality)"
We say that sets $S_1$ and $S_2$ are equal if and only if they are subsets of each other.
In other words:

$$
  S_1 = S_2 ⇔ S_1 ⊆ S_2 ∧ S_2 ⊆ S_1.
$$
~~~

This definition, in turns, gives us a principle for reasoning about the equality of sets, the so-called _double inclusion_ principle, that we will later use to prove that two sets are equal.

## Power Set

Finally, the power set of a set $S$ is a set that contains all the subsets of $S$.

~~~admonish info title="Definition (Power Set)"
The _power set_ of a set $S$, written $𝒫(S)$, is:

$$
𝒫(S) = \{\, T \mid T ⊆ S \,\}
$$
~~~

Note in our formal definition that $T$ implicitly is a _set_ because it is, by definition, a _subset_ of $S$.
This is not a problem!
Sets can certainly contain other sets which allows us to create more complex structures.

As an example of the power set operation, let $S = \{\, 1, 2, 3, 4 \,\}$.
Then:

$$
\begin{align*}
𝒫(S) = \{\,& \emptyset, \\
& \{\, 1 \,\}, \{\, 2 \,\}, \{\, 3 \,\}, \{\, 4\ \,\} \\
& \{\, 1, 2 \,\}, \{\, 1, 3 \,\}, \{\, 1, 4 \,\}, \{\, 2, 3 \,\}, \{\, 2, 4 \,\}, \{\, 3, 4 \,\} \\
& \{\, 1, 2, 3 \,\}, \{\, 1, 2, 4 \,\}, \{\, 1, 3, 4 \,\}, \{\, 2, 3, 4 \,\} \\
& \{\, 1, 2, 3, 4 \,\} \,\}.
\end{align*}
$$

To make it easier to enumerate all the possible subsets of $S$ in a systematic way, we arrange them in order of size.

-   There is one subset of size zero, the set containing no elements, _e.g._, the empty set.
    We can write the empty set using set literal notation, $\{\, \,\}$ but we traditionally use the empty set symbol $\emptyset$ (LaTeX: `\emptyset`) to denote the empty set.

-   There are four subsets of size one corresponding to the _singleton_ sets, each containing of the elements of $S$.

-   There are six subsets of size two.

-   There are four subsets of size three.

-   Finally, there is a single subset of size four which is $S$ itself
    (because it has size four).

In this particular case, this results in 16 subsets overall.
You can imagine that the number of such subsets grows dramatically as the size of the input set increases.
We will revisit this point in our discussion of counting.

~~~admonish problem title="Problem (Execution, ‡)"
Define the following universe and sets drawn from that universe:

$$
\begin{gather*}
\mathcal{U} = \{\, 1, 2, 3, 4, 5 \,\} \\
S = \{\, 1, 3, 4, 5 \,\} \\
T = \{\, 2, 4, 5 \, \}.
\end{gather*}
$$

Write down the contents of the resulting set operations use set literals:

1.  $S \cap T$.
2.  $S \cup T$.
3.  $\overline{T}$.
4.  $(S - T) \times T$.
5.  $𝒫(\overline{T})$.
~~~