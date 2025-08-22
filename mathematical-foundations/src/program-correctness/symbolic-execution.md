# Symbolic Execution

Previously, we introduced a model of computation for the Racket programming language.
This model allows us to prove program properties when concrete values are involved.
However, we frequently wish to prove properties where the values are _unknown_.
For example, we might consider a proposition about the standard `list_append` function:

> For all lists `l1` and `l2`, `len(l1) + len(l2)` = `len(list_append(l1, l2))`

This proposition ranges over _unknown_, rather than concrete, lists.
We, therefore, need to upgrade our mental model to work with these unknown quantities.

## Abstract Propositions

Up to this point, we have considered concrete expressions, _i.e._, expressions that do not contain variables.
What happens if we allow expressions to contain variables.
As an example, consider the following implementation of the boolean `and` function:

~~~python
# N.B. non-short-circuiting version of `and`
def my_and(b1, b2):
    if b1:
        return b2
    else:
        return False
~~~

Now, let's consider the following equivalence claim:

**Claim**: `my_and(True, b)` ≡ `False`

Here, `b` is a variable, presumed to be of boolean type.
However, how do we interpret `b`?
It turns out there are two interpretations we might consider:

+   Does there _exist_ a boolean value to `b` so that the proposition is provable?
+   Is the proposition provable _for all_ possible boolean values that `b` can take on?

The former interpretation is called an _existential quantification_ of `b`.
We alternatively say that `b` is existentially quantified or is an "existential."
_Quantification_ refers to the fact that our interpretation tells us "how many" values of `b` to consider in the proposition.
In existential quantification, we consider a single value.

In contrast, the latter interpretation is called a _universal quantification_ of `b`.
In universal quantification, we mean that the proposition holds for all possible values of `b`.
Note that the above proposition is provable if `b` is interpreted existentially: if we let `b` be `#f` then:

~~~python
    my_and(True, b)
--> my_and(True, False)
--> { if True: return False else: return False }
--> { return False }
--> False
~~~

However, the proposition does not hold when `b` is universally quantified.
More specifically, while it holds when `b` is `False`, it does not hold when `b` is `True`.

~~~python
    my_and(True, b)
--> my_and(True, True)
--> { if True: return False else: return False }
--> { return False }
--> False
~~~

Because of this, we must be explicit when introducing variables into our proposition.
For each such variable, we must _declare_ whether it is existentially quantified and universally quantified.
To do so, we can use the words:

+   _For all_ for universal quantification, _e.g._, "for all lists `l` …" and
+   _There exists_ for existential quantification, _e.g._, "there exists a number `n` …".

Furthermore, we reason about the variable differently depending on its quantification, as we see in the following sections.

~~~admonish problem title="Exercise (Quantified Propositions)"
Write down an additional _existential_ and _universal_ claim involving the `my_and` function.
~~~

## Existential Propositions

If a variable $x$ appears as an existential in a proposition, we interpret that variable as: _there exists_ a value for $x$ such that the proposition holds.
In other words, the proposition is provable if we can give a _single value_ to substitute for $x$ so that the resulting proposition is provable.
Thus, to prove an existential claim, we can choose such a value, substitute for the existential variable, and then use concrete evaluation as before.

As an example, let's formally prove the existential version of the `my_and` claim that we introduced above:

~~~admonish check title="Proof"
**Claim**: there exists a boolean `b` such that `my_and(True, b)` ≡ `False`.

_Proof_.
Let `b` be `False`.
Then we have:

```python
    my_and(True, False)
--> my_and(True, False)
--> { if True: return False else: return False }
--> { return False }
--> False
```
~~~

Note how our proof has changed now that our proposition is abstract:

1.  In our claim, we explicitly quantify the variable `b` by declaring it existential by using "there exists" to describe it.
2.  In our proof, we explicitly _choose_ a value for the existentially quantified variable ("Let `b` be `False`").

We can also existentially quantify over multiple variables.
In these situations, we provide instantiations for each variable but otherwise proceed as normal.

~~~admonish check title="Proof"
**Claim**: There exists lists `l1` and `l2` such that `list_append(l1, l2)` ≡ `[1 2 3]`.

_Proof_.
Let `l1` be `[]` (the empty list) and `l2` be `[1 2 3]`.

```python
    list_append([], [1, 2, 3])
--> { if is_empty([]):
          return [1, 2, 3]
      else:
          return cons(head([]), list_append(tail([]), [1, 2, 3]))
    }
--> { if True:
          return [1, 2, 3]
      else:
          return cons(head([]), list_append(tail([]), [1, 2, 3]))
    }
--> { return [1, 2, 3] }
--> [1, 2, 3]
```
~~~

~~~admonish problem title="Exercise (Alternative Instantiation)"
Revise the proof of `list-append` above by choosing _alternative instantiations_ for `l1` and `l2` and deriving an alternative execution trace for the expression.
~~~

## Universal Quantification

When a variable is universally quantified, it stands for _any possible_ value.
Let's take a look at a simple squaring function:

~~~python
def square(n):
    return n * n
~~~

And a simple universal claim about this function:

~~~admonish question title="Claim"
for all numbers `n`, `square(n)` ≡ `n * n`.
~~~

Because the claim holds for all possible values of `n`, we can't _choose_ a `n` like with existentials.
Instead, we must _hold `n` abstract_, _i.e._, consider it to be an arbitrary number, and then proceed with the proof.
In effect, because `n` is universally quantified, we treat `n` like a constant, yet unknown, quantity in our reasoning.

~~~admonish info title="Variables versus Unknown Constants"
It may seem pedantic to distinguish between a variable and a constant of unknown quantity.
However, there a subtle yet essential difference between the two concepts.
A variable is an object in a proposition that must be _quantified_ to give it meaning.
An unknown constant already has meaning---it is known to be a single value.
However, we don't assume anything about the variable's identity beyond what we already know, _e.g._, whether it is a list or a number.
~~~

When we use our mental model of computation, we immediately arrive at a problem: both `square(n)` and `n * n` cannot take any evaluation steps!
`square(n)` cannot step because `n` needs to be a value before we perform the function application, and we said that values were numbers, boolean constants, or lambdas.
`n * n` cannot step because since we don't know what `n` is, we don't know what concrete value the multiplication will produce.
We say that both expressions are _stuck_: they are not values, but they cannot take any additional evaluation steps.

We can't reconcile the `n * n` case.
Without knowing what `n` is, we cannot carry out the multiplication.
However, if we treat the constant-yet-unknown `n` as a _value_, then we can proceed with the function application:

~~~python
    square(n)
--> n * n
~~~

Even though the left- and right-hand sides of the equivalence are not values, they are identical.
This fact is sufficient to conclude that the two original expressions are equivalent according to our definition of program equivalences!
Let's put these ideas together into a complete proof of the proposition:

~~~admonish check title="Proof"
**Claim**: for all numbers `n`, `square(n)` ≡ `n * n`.

_Proof_.
Let `n` be an arbitrary number.
Then the left-hand side of the equivalence simplifies to `square(n) --> n * n`, which is identical to the right-hand side.
~~~

In summary, when we encounter a universally quantified variable in a proposition, we:

1.  Consider the variable to be a constant, yet unknown value.
    For convenience, we keep the name of this constant to be the same as the (universally quantified) variable, but we understand that the two are different objects!
2. When reasoning about the constant, we assume that it is a value for the purposes of our mental model of computation.

## Case Analysis

Sometimes when we work with universally quantified variables, we can get away without thinking about their actual values.
However, more often or not, our reasoning must consider their possible values.
This reasoning will ultimately depend on the types of values involved, and this is where our proofs get more intricate in their design!

As an introduction to these concepts, let's consider the case where we know the type in question only allows for a _finite set of values_.
At present, only the _boolean type_ has this property.
Booleans only allow for two values, `True` and `False`, whereas there are an infinite number of numbers and functions to choose from!

To see how we can take advantage of the finiteness of the boolean type in our proofs, let's consider the following simple claim, again using the `my_and` function:

~~~admonish question title="Claim"
For all booleans `b`, `my_and(b, False)` ≡ `False`.
~~~

If we let `b` be arbitrary, we can begin evaluating the left-hand expression.

~~~python
    my_and(b, False)
--> { if b: return False else: return False }
~~~

We can see pretty readily that no matter how the conditional evaluates, we will return `False`.
Be careful, though; this intuition is not sufficient for formal proof!
We must instead rely on our evaluation model directly to ultimately show that this intuition is correct.
However, if `b` is unknown, we don't know which branch the conditional will produce.

Thankfully, we assumed that `b` was a boolean, so it must either be `True` or `False`.
Therefore, we can proceed by _case analysis_: we will consider two separate cases, `b` is `True` and `b` is `False`, and show that the claim holds in _both_ cases.
If the claim holds for both cases, we know that the claim holds for _every possible value of `b`_ and, thus, have completed the proof.

~~~admonish check title="Proof"
_Proof_.
Let `b` be an arbitrary boolean.
The left-hand side of the equivalence evaluates as follows:

```scheme
    my_and(b, False)
--> { if b: return False else: return False }
```

Because `b` is a boolean, either `b` is `True` or `b` is `False`.

+   **`b` is `True`**.
    Then `{ if True: return False else: return False } --> { return False } --> False`
+   **`b` is `False`**.
    Then `{ if False: return False else: return False } --> { return False } --> False`

In both cases, the left-hand side steps to `False`, precisely the right-hand side of the equivalence.
~~~

~~~admonish note title="Typesetting Cases"
When performing a case analysis, it is imperative to state the proof's different cases explicitly.
To format this in LaTeX, use a bulleted lists, _e.g._,

```markdown
\begin{proof}
  Because \code{b} is a boolean, either \code{b} is \code{True} or \code{b} is \code{False}.

  \begin{itemize}
  \item \textbf{\code{b} is \code{True}}.
    Then \code{ \{ if True: return False else: return False \} --> \{ return False \} --> False }
  \item \textbf{\code{b} is \code{False}}.
    Then \code{ \{ if False: return False else: return False \} --> \{ return False \} --> False }`
  \end{itemize}

  In both cases, the left-hand side steps to \code{#f}, precisely the right-hand side of the equivalence.
\end{proof}
```

Note how I bold each case at the start of the bullet to clearly separate the statement of the case from the subsequent proof.
~~~

~~~admonish problem title="Exercise (Order of Reasoning)"
In the previous proof, we performed the case analysis on \code{b} _after_ partially evaluating \code{my_and}.
Rewrite the proof so that the case analysis happens _before_ any evaluation occurs.
Was either approach more concise? Easier to reason about? Why?
~~~

~~~admonish problem title="Exercise (Quantified Propositions, ‡)"
**Exercise (Quantified Propositions, ‡)**: consider the following Python definition of the boolean disjunction, \code{or}, function:

```python
def my_of(b1, b2)
    if b1:
        True
    else:
        b2
```

Prove the following claims over this function:

+   **Claim 1**: there exists booleans `b1` and `b2` such that `my_or(b1, b2)` ≡ `False`.
+   **Claim 2**: for all booleans `b`, `my_or(b, True)` ≡ `True`.
~~~