# Functions and Relations

Previously, we explored the mathematical formalism of sets.
Sets allow us to model collections of data.
However, we frequently wish to capture relevant _relationships_ in our data.
Structured data is called as such because the data in the collection is _related_ in some way.
For example:

+   An element _precedes_ another element in a list.
+   A person is _friends with_ another person in a social network.
+   One number is a _divisor_ of another number.

To model structured data, we need some way of modeling these relationships between individual datum.
In this chapter, we use sets to develop the theory of _relations_ which will allow us to formally reason about these relationships.

## Definitions and Notation

Intuitively, a relation relates two objects by some property as defined by the relation.
To capture this correspondence we combine pairs with sets:

~~~admonish info title="Definition (Relation)"
A *relation* $R$ over a universe $U$ is a subset of pairs of elements drawn from $U$, _i.e._, $R \in \mathcal{P}(U \times U)$.
~~~

Suppose we have a relation $R$.
An element of $R$, $(a, b) \in R$ denotes that $a$ and $b$ are _related_ by $R$, which we can express in different ways:

| Notation            | Form              |
|---------------------|-------------------|
| $(a, b) \in R$      | Set notation      |
| $R(a, b)$           | Function notation |
| $a \; R \; b$       | Infix notation    |

For example, let our universe $U = \{\, \text{"Mary"}, \text{"Miguel"}, \text{"Li"}, \text{"Lana"} \,\}$.
Then we can define a relation $\mathsf{owes} : U \times U$ that captures whether one person owes another money.
With this relation, the following expressions:

+ $(\text{"Miguel"}, \text{"Li"}) \in \mathsf{owes}$.
+ $\mathsf{owes}(\text{"Miguel"}, \text{"Li"})$.
+ $\text{"Miguel"} \mathsf{owes} \text{"Li"}$.

All posit that Miguel owes Li money.

Note that because the order of a pair matters, these expressions do not automatically assert that Li owes Miguel money.
We would need to include this fact separately: $(\text{"Li"}, \text{"Miguel"}) \in \mathsf{owes}$.

## Operations Over Relations

Because we define relations in terms of sets, we can define our fundamental operations over relations using set-theoretic notation.

### Domain and Range

First, we can _project_ out the left-hand and right-hand elements of a relation, typically called the _domain_ and _range_, respectively.

~~~admonish info title="Definition (Domain)"
Let $R$ be a relation.
Define the *domain* of $R$, written $\mathrm{dom}(R)$, as:

$$
\mathrm{dom}(R) = \{\, x \mid \exists y \ldotp (x, y) \in R \,\}.
$$
~~~

~~~admonish info title="Definition (Range)"
Let $R$ be a relation.
Define the *range* of $R$ as:

$$
\mathrm{range}(R) = \{\, y \mid \exists x \ldotp (x, y) \in R \,\}.
$$
~~~

Alternatively, the range may also be called the _codomain_ of the relation.

~~~admonish example title="Cardinality" 
Frequently, we may want to have the domain and range of a relation come from _disparate sets_ $S$ and $T$.
This is no problem for our definition of relation; we can simply define the universe to be the union of $S$ and $T$.
Then our pairs are drawn from this union where the domain is always an element of $S$ and the range is always an element of $T$.

In this case, we can define a relation between sets $S$ and natural numbers $n$ where $n$ is the number of elements in $S$.
We commonly call this the _cardinality_ of a set.
This is normally written $|S| = n$, but to align with our relation notation, we can write $\mathsf{card}(S, n)$ to denote this fact.
For example:

+   $(\{\, 1, 2, 3 \,\}, 3) \in \mathsf{card}$.
+   $(\{\, a \,\}, 10) \notin \mathsf{card}$.
+   $(\emptyset, 0) \in \mathsf{card}$.
~~~

### Lifted Operations

Because relations are sets, we can _lift_ any binary operation over sets to relations.
As examples, let $R$ and $S$ be two relations.
Then define the following _lifted operations over sets_ to relations as:

$$
\begin{align}
R \cup S     &= \{\, (a, b) \mid (a, b) \in R \vee (a, b) \in S \,\} \\
R \cap S     &= \{\, (a, b) \mid (a, b) \in R \wedge (a, b) \in S \,\} \\
\overline{R} &= \{\, (a, b) \mid (a, b) \notin R \,\}
\end{align}
$$

~~~admonish example title="Definition (Relational Union)"
As a practical example of applying set-theoretic operations to relations, consider using relations to map items in a store to their stock, _i.e._, a relation whose domain is (abstract) objects and the codomain is natural numbers.
We might have two different stores, with their own separate stocks of disparate items:

+   $R_1 = \{\, (a, 2), (b, 0), (c, 3) \,\}$.
+   $R_2 = \{\, (d, 1), (e, 5), (f, 0) \,\}$.

Then $R_1 \cup R_2$ might represent joining together the stocks into a single store:

+   $R_1 \cup R_2 = \{\, (a, 2), (b, 0), (c, 3), (d, 1), (e, 5), (f, 0) \,\}$.
~~~

### Transformations

Beyond lifted operations, we can also define several fundamental transformations over relations.

~~~admonish info title="Definition (Inverse)"
Let $R$ be a relation.
Define the _inverse_ of $R$, written $R^{-1}$, as:

$$
R^{-1} = \{\, (b, a) \mid (a, b) \in R \,\}
$$
~~~

~~~admonish info title="Definition (Composition)"
Let $R$ and $S$ be relations.
Define the *composition* of $R$ and $S$, written $S \circ R$ (LaTeX: `\circ`) as:

$$
S \circ R = \{\, (a, c) \mid (a, b) \in R, (b, c) \in S \,\}
$$
~~~

Note that with composition that we "run the relation" from right-to-left, first through $R$ and then through $S$.

~~~admonish info title="Definition (Image)"
Let $R$ be a relation.
Define the *image* of an element $a$, written $R(a)$, as:

$$
R(a) = \{\, b \mid (a, b) ∈ R \,\}
$$
~~~

This final transformation is particularly useful when talking about _functions_ which (we will discover shortly) are a special case of relations.
In particular, note that if we have a function $f$, then both the definition and notation of image coincides with "run the function", $f(x)$.

## Function-like Relations

Functions form the heart of computation within mathematics.
Consider the following partially specified relation:

$$
R_1 = \{\, (0, 1), (1, 2), (2, 3), (3, 4), \ldots \,\}
$$

From inspection, you would rightfully conclude that $R$ relates a natural number to the number one greater than it, _i.e._, $R$ is the increment function.
We can see that the left-hand element of a pair represents an _input_ to the function and the right-hand element of a pair is its corresponding _output_.

Based on this example, it may feel like functions and relations are the same.
However, not all relations are functions.
For example, consider the following relation:

$$
R_2 = \{\, (0, 1), (0, 2), (0, 3) \,\}.
$$

If we think of $R_2$ as a function, what is the result of $R_2(0)$?
It appears there are three choices—$1$, $2$, and $3$!
This does not align with our intuition about how a function works where a single input to a function should generate a single output.

In actuality, functions can be thought of as a special case of relations.
Next, we'll develop the definitions necessary to classify certain relations as functions.
These definitions will help us understand better the nature of functions as well as leverage the functions-as-relations view in our own mathematical models.

~~~admonish note
This next section is light on exposition by intention!
You should employ the strategies we've discussed in the course to _understand and internalize_ these definitions.
Create small example sets that exhibit each of these definitions and try to understand the _essence_ of the definitions by generalizing the structure of the examples.
~~~

### Totality and Uniqueness

The two main properties that separate functions from other relations are _totality_ and _uniqueness_.
Because functions distinguish between inputs and outputs in a non-symmetric fashion, totality and uniqueness can apply either to the inputs of the function (the "left") or the outputs of the function (the "right").

#### Totality

*Totality* concerns whether all the elements in the universe of some relation appear in the relation.

~~~admonish info title="Definition (left totality)"
A relation $R$ is *left-total* if all elements are related by $R$ on the left

$$
\forall x \ldotp \exists y \ldotp (x, y) \in R.
$$
~~~

~~~admonish info title="Definition (right totality)"
A relation $R$ is *right-total* if all elements are related by $R$ on the right: 

$$
\forall y \ldotp \exists x \ldotp (x, y) \in R.
$$
~~~

#### Uniqueness

*Uniqueness* concerns whether an element is related to a single other element.
The way that we express this property formally is that if an element is mapped to two elements, those two elements are in fact the same.

~~~admonish info title="Definition (left-unique)"
A relation $R$ is *left-unique* if every element in the relation on the left-hand side is mapped to a unique element right.

$$
\forall x, y, z \ldotp (x, y) \in R \rightarrow (z, y) \in R \rightarrow x = z.
$$
~~~

~~~admonish info title="Definition (right-unique)"
A relation $R$ is *right-unique* if every element in the relation on the right-hand side is mapped to a unique element on the left.

$$
\forall x, y, z \ldotp (x, y) \in R \rightarrow (x, z) \in R \rightarrow y = z.
$$
~~~

### Refinements of Relations

With totality and uniqueness defined, we can define particular refinements relations in terms of these properties.

~~~admonish info title="Definition (partial function)"
A relation is a *partial function* if it is right-unique.
~~~

~~~admonish info title="Definition (function)"
A relation is a *function* if it is both right-unique and left-total.
~~~

To better distinguish from partial functions, we also call right-unique and left-total relations *total* functions.
Note that a total function is one that is *well-defined*, *i.e.*, "has an answer" every possible input.
In contrast, a partial function may be *undefined* on some inputs; this corresponds to the non-existence of a pair mentioning the undefined element on the left-hand side.

~~~admonish info title="Definition (injectivity)"
**Definition (injectivity)**: a relation is an *injective function* if it is a function (right-unique and left-total) as well as left-unique.
~~~

~~~admonish info title="Definition (surjectivity)"
**Definition (surjectivity)**: A relation is a *surjective function* if it is a function (right-unique and left-total) as well as right-total.
~~~

~~~admonish info title="Definition (bijection)"
**Definition (bijection)**: A relation is a *bijection* if it is a function (right-unique and left-total) as well as injective and surjective (left-unique and right-total).
~~~

~~~admonish problem title="Reading Exercise (Definitions, ‡)"
Consider the following relation $R$ over $U = \{\, a, b, c, d, e \,\}$:

$$
R = \{\, (a, c), (b, c), (c, c), (d, c), (e, c) \,\}.
$$

Does the relation fulfill each of the given properties?
If so, you can simply say "yes".
If not, give a single sentence explaining why not.

1. Left-total
2. Right-total
3. Left-unique
4. Right-unique
5. Partial function
6. Function
7. Injective function
8. Surjective function
9. Bijection
~~~