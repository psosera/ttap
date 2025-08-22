# Equivalences and Orderings

There are a number of special kinds of relations that are ubiquitous in mathematics.
We have already studied functions-as-relations.
Now we will explore two other kinds of common relations:

1.  The _equivalence_, which captures the notion of equality between objects in a universe 
2.  The _ordering_, which captures the notion of, literally just that, ordering between objects.

## Equivalences

Like functions, equivalences are a refinement of relations.
In particular, a relation that enjoys these three properties, _reflexivity_, _symmetry_, and _transitivity_, is considered an equivalence.

~~~admonish info title="Definition (Reflexivity)"
A relation $R$ is *reflexive* if it relates every element in the universe to itself.

$$
  \forall x \ldotp (x, x) \in R
$$
~~~

~~~admonish info title="Definition (Symmetry)"
A relation $R$ is *symmetric* if any pair of related elements are also related "in the opposite direction."

$$
  \forall x, y \ldotp (x, y) \in R \rightarrow (y, x) \in R
$$
~~~

~~~admonish info title="Definition (Transitivity)"
A relation $R$ is *transitive* if whenever any pair of elements are related with a common element in the middle, the first and last elements are also related.

$$
  \forall x, y, z \ldotp (x, y) \in R \rightarrow (y, z) \in R \rightarrow (x, z) \in R
$$
~~~

These three concepts form the definition of an equivalence relation.

~~~admonish info title="Definition (Equivalence)"
A relation an *equivalence* if it is reflexive, symmetric, and transitive.
~~~

The standard equality relation $(=)$ over the natural numbers $ℕ$ is an equivalence relation as it fulfills all three properties of an
equivalence:

**Reflexive**
: Identical numbers are considered equal.

**Symmetric**
: Order doesn't matter when asserting equality between numbers.

**Transitive**
: When declaring $x = y$ and $y = z$, we know that these two facts establish that $x$ and $y$ are the same number and $z$ and $y$ are the same number.
  From this, we can conclude that $x$ and $z$ must also be the same number.

### Reasoning About Equivalences

To formally show that a relation is an equivalence, we must show that it obeys the three properties of an equivalence: reflexivity, symmetry, and transitivity.
We show the outline of such a proof using the following real-world example, arithmetic expressions, _e.g._, $3 + 5$ or $3 \cdot (2 - 1)$.

Let $(\equiv)$ be the following relation:

$$
(\equiv) = \{\, (e_1, e_2) \mid \text{\( e_1 \) and \( e_2 \) are arithmetic expressions that evaluate to the same value \( v \)} \,\}
$$

> **Claim**: $(\equiv)$ is an equivalence relation.

To show that $(\equiv)$ is an equivalence relation, we must show that it is reflexive, symmetric, and transitive.

~~~admonish check title="Proof"
*Proof*. We show that $(\equiv)$ is a reflexive, symmetric, and transitive relation:

**Reflexive**
: Because an arithmetic expression evaluates to a unique value, it must be the case that $\forall e \ldotp e \equiv e$

**Symmetric**
: Let $e_1, e_2 \in U$ and assume that $e_1 \equiv e_2$.
  By the definition of $(\equiv)$, since the pair of expressions is related, they must evaluate to the same value $v$.
  Because of this fact and the definition of $(\equiv)$, we know that the pair is related in the other direction, *i.e.*, $e_2 \equiv e_1$.

**Transitive**
: Let $e_1, e_2, e_3 \in U$ and assume that $e_1 \equiv e_2$ and $e_2 \equiv e_3$.
  By the definition of $R$, this means that $e_1$ and $e_2$ evaluate to the same value, call it $v_1$ and $e_2$ and $e_3$ evaluate evaluate to the same value, call it $v_2$.
  However, we know that an arithmetic expression evaluates to a unique value, so it must be the case that $v_1$ and $v_2$ are identical since they are both the result from evaluating $e_2$.
  This means that $e_1 \equiv e_3$ as well.
~~~

### Equivalence Closures

The _closure_ of a set $S$ under a property $P$ is the (smallest) set $S^* \subseteq S$ whose elements all satisfy $P$.
The concept of closure lifts to relations in the expected way.
For example, let $U = \{\, 0, \ldotp, 10 \,\}$ and $P$ be the property of symmetry $\forall x, y \ldotp (x, y) \in R \rightarrow (y, x) \in R$, then if $R$ is the relation:

$$
  R = \{\, (0, 3), (2, 5), (6, 9), (5, 2) \,\},
$$

Then the *symmetric closure* of $R$ is the relation $R^*$:

$$
  S = \{\, (0, 3), (3, 0), (2, 5), (5, 2), (6, 9), (9, 6) \,\}.
$$

We can compute the closure of any relation under a property by repeatedly applying the property to generate new pairs to add to the relation until we can no longer add new pairs.

We can apply the notion of closure to *all* the properties of an equivalence relation simultaneously to form an equivalence closure of a set of elements.
Intuitively, the equivalence closure of a set of elements captures all the different equalities induced by the properties of equivalences.

For example, consider an artificial set $S = \{\, a, b, c \,\}$ and suppose we know that some relation $R$ relates the elements as follows:

$$
  (a, b) \in R, (b, c) \in R.
$$

If we furthermore know that $R$ ought to be an equivalence relation, then we can compute the *equivalence closure* of $R$ as follows:

+ The *reflexive closure* of the relation relates every element to itself: $(a, a), (b, b), (c, c) \in R$.
+ The *symmetric closure* of the relation relates every pair "in the other direction": $(b, a), (c, b) \in R$.
+ The *transitive closure* of the relation connects every transitive pair of elements: $(a, c) \in R$.
+ Finally, we also have to consider the symmetric closure again for this new pair: $(c, a) \in R$.

In this particular case, the equivalence closure of $R$ is all nine possible pairs of $S$ with itself, *i.e.*, $S × S$.
This case captures the intuition that the two original equalities are sufficient to deduce that all the elements of $S$ are equal.

~~~admonish problem title="Exercise (Closure, ‡)"
Consider the following relation $R$ over universe $\mathcal{U} = \{\, a, b, c, d, e, f \,\}$:

$$
R = \{\, (a, b), (c, e), (d, b), (f, e) \,\}.
$$

Compute the _equivalence closure_ of $R$.
~~~

### Equivalence Classes

Intuitively, an equivalence relation captures some notion of equality between objects.
We can then think about grouping together sets of _mutually equal_ objects.
For example, let's return to arithmetic expressions.
The following expressions are all equivalent to each other:

+   $10$.
+   $5 + 5$.
+   $2 \times (2 + 3)$.

Because they all evaluate to $10$.
Consider creating a set of such expressions, call it $S_4$ with the property that they all evaluate to $4$:

$$
S_4 = \{\, e \mid \text{ \( e \) is an arithmetic expression that evaluates to \( 4 \)} \,\}.
$$

Any pair of expressions within $S_4$ are equivalent to each other.
We call such a set an _equivalence class_.

~~~admonish info title="Definition (Equivalence Classes)"
An equivalence class of an equivalence relation $R$ over universe $U$ is a set $S$ of elements drawn from $U$ that are pairwise equivalent according to $R$, _i.e._,

$$
\forall x, y \in S \ldotp (x, y) \in R.
$$
~~~

Recall that $x \mod y$ is the whole-number remainder of $x \div y$.
Because of the nature of division, the result of $x \mod y$ takes on the values $0, \ldots, y-1$.
Because of this, we can consider, _e.g._, the equivalences classes induced by taking a number and modding it by 3.
$x \mod 3$ takes on three values, $0$, $1$, and $2$, and thus, $\mod 3$ _induces_ three equivalence classes:

+   $E_1 = \{\, 0, 3, 6, 9, \ldots \,\}$.
+   $E_2 = \{\, 1, 4, 7, 10, \ldots \,\}$.
+   $E_3 = \{\, 2, 5, 8, 11, \ldots \,\}$.

In the context of the modulus operator, we can say that any pair of numbers in an equivalence class are _equivalent modulo 3_, _e.g._, $4$ and $11$ are equivalent modulo $3$.

## Orderings

There are many ways that we might order data.
For a collection of strings, for example, we might order by:

+   Simple length.
    For example, "dog" is less than "doghouse" since it is shorter.
+   _Lexicographical_, _i.e._, dictionary order, comparing letter-by-letter.
    For example, "alphabet" is less than "zoo" in lexicographical ordering.
+   A more arbitrary measure such as the number of consecutive trailing constants in the word.
    For example, "correctness" (-ss) comes before "algorithms" (-thms).

What is the _essential nature_ of these relationships that make it a valid "ordering?"
Let's start with the properties from an equivalence—reflexivity, symmetry, and transitivity—and use them
In other words, which of the three properties of an equivalence—reflexivity, symmetry, and transitivity—are necessary for a relation to be considered an ordering?
Let's consider numeric ordering $(\leq)$ as our quintessential example of orderings and each of these properties in turn:

+   **Reflexivity**: $(\leq)$ appears to be reflexive because any number is equal to itself!
+   **Symmetry**: $(\leq)$ appears to _not_ be symmetric.
    For example, $3 \leq 5$ but $5 \not\leq 3$.
+   **Transitivity**: $(\leq)$ also appears to be transitive.
    If we establish that $x \leq y$ and $y \leq z$, we form the following chain of comparisons $x \leq y \leq z$ with the understanding that this notation implies that $x \leq z$, too.

So it seems like an ordering is _reflexive_, _transitive_, but _not symmetric_!
However, it seems like we need to say something stronger than "not symmetric."
To see this, observe that we _never_ want different numbers to be related to each other in both directions.
In other words, it should never be the case that $x \leq y$ and $y \leq x$ for different $x$ and $y$!
We call this property _anti-symmetry_:

~~~admonish info title="Definition (Anti-symmetry)"
A relation $R$ is *anti-symmetric* if any pair of elements are related, at most, in one direction:

$$
  \forall x, y \ldotp (x, y) \in R \rightarrow (y, x) \in R \rightarrow x = y.
$$
~~~

Observe how we have to define this "zero-or-one" property in the same way we do with uniqueness.
Intuitively, the formal definition of anti-symmetry says that whenever two elements are related in both directions, they must be the same element.

With the definition of anti-symmetry, we can now formally define a _partial ordering_:

~~~admonish info title="Definition (Partial Ordering)"
A relation $R$ is a _partial ordering_ if it is reflexive, anti-symmetric, and transitive.
~~~

As an example of a partial ordering, consider a hierarchy of employees where an employee has a manager, their manager has a manager, and so forth.
We say that one employee is _higher_ in the hierarchy than another if the first employee is the direct or indirect manager (_i.e._, manager of their manager, manager of their manager's manager, _etc_.) of the second.
We can consider the following notion of employee equality:

$$
(=_e) = \{\, (e_1, e_2) \mid \text{$e_1$ and $e_2$ have the same position} \,\}
$$ 

Along with an ordering based on the employee hierarchy:

$$
(\leq_e) = \{\, (e_1, e_2) \mid \text{$e_1 =_e e_2$ or $e_2$ is higher than $e_1$ in the company hierarchy. } \,\}
$$

We can show that this relation obeys the properties of a partial ordering:

~~~admonish info title="Proof"
**Claim**: $(\leq_e)$ is a partial ordering.

_Proof_.
We show that $(\leq_e)$ is reflexive, anti-symmetric, and transitive.

: Reflexive.
    For any employee $e$, $e \leq_e e$ since they have the same position.

: Anti-symmetric.
    Suppose we have two employees $e_1$ and $e_2$ and we know $e_1 \leq_e e_2$ and $e_2 \leq e_1$.
    From the definition of $(\leq_e)$, either it is the case that $e_1 = e_2$ or $e_1$ and $e_2$ are mutually higher in the company hierarchy than the other.
    The former case is impossible because this would imply there is a cycle in the hierarchy, but that would violate the definition of a hierarchy.
    Thus, it must be the case that $e_1 = e_2$.

: Transitivity:
    Suppose we have three employees $e_1$, $e_2$, and $e_3$ and $e_1 \leq_e e_2$ and $e_2 \leq_e e_3$.
    Since our definition of "higher-up" in the hierarchy includes both direct and indirect managerial relationships, since $e_2$ is higher-up than $e_1$ and $e_3$ is higher-up than $e_2$, then $e_3$ is also higher up than $e_1$.
    In other words, $e_3$ is either identical to $e_1$ or is at least one level of manager above $e_1$.
~~~

Note that two employees that _don't_ have a direct manager in common are incomparable by this definition because they don't appear in each other's chain of managers to the top of the hierarchy.
This is why partial orders are named as such: some employees are _incomparable_ to each other under this relation.
To obtain this property, we add it directly to our definition, giving us a _total ordering_:

~~~admonish info title="Definition (Total Ordering)"
A relation $R$ is a _total ordering_ if it is a partial ordering with the additional property that any pair of elements is related in either direction:

$$
\forall x, y \ldotp R(x, y) \vee R(y, x)
$$
~~~

~~~admonish problem title="Exercise (Orderings, ‡)"
Consider the set $\mathcal{U} = \{\, a, b, c, d, e \,\}$ and the following relation $R$ over this set:

$$
\begin{align*}
R = \{\,& (a, a), (b, b), (c, c), (d, d), (e, e), \\
        & (a, b), (a, c), (b, d), (c, d), (d, e)  \\ 
        & (a, d), (a, e), (b, e), (c, e)     \,\} \\
\end{align*}
$$

1.  Prove that $R$ is a partial order.
2.  Is $R$ a total order?
    If so, prove it.
    If not, provide a counterexample demonstrating that some pair of elements in $\mathcal{U}$ is incomparable.
~~~