# Propositions and Proofs

Our formal model of computation allows us to reason about the behavior of programs.
But to what ends can we apply this reasoning?
Besides merely checking our work, we can also use our formal model to _prove propositions_ about our programs.

~~~admonish info title="Proposition"
A _proposition_ is an assertion or statement, potentially provable.
~~~

Another word for a proposition is a _claim_.
Here are some example propositions over programs that we could consider:

+   `len("hello world!")` is equivalent to 12.
+   There exists a list `l` for which `len(l)` is `0`.
+   `insertion_sort(l)` performs more comparison operations than `mergesort(l)` for any list `l`.
+   For any number `n`, `2 * n` is greater than `n`.

The first proposition is a _fully concrete_ proposition of the sort we have previously considered.
The second is an _abstract_ proposition because it involves a variable, here `l`.

Ultimately, the first two propositions involve equivalences between expressions.
But propositions do not need to be restricted to equivalences.
The third proposition talks about the number of operations that one expression performs relative to another.

Furthermore, propositions don't even need to be provable!
For example, the final proposition is not provable.
A _counterexample_ to this proposition arises when we consider `n = -1`.
`2 * -1` evaluates to `-2`, and `-2` is not greater than `-1`!

~~~admonish info
**"True" versus "Provable"**: in our discussion of logic and its applications to program correctness, we will need to discuss both _boolean expressions_ as well as propositions.
There exist commonalities between both---they involve truth and falseness.
However, booleans exist _inside_ our model, _i.e._, at the level of programs.
Propositions exist _outside_ of the model as they ultimately are statements _about_ the model.

To distinguish between the two, we'll use the terms `true` and `false` when discussing booleans and "provable" and "refutable" when discussing propositions.
We should think of boolean expressions as _evaluating_ to `true` or `false`.
In contrast, we will employ _logical reasoning_ to show that a proposition is provable or refutable.
~~~

## Equivalences Between Expressions

Of the many sorts of propositions possible, we will work exclusively with _equivalences_ in our discussion of program correctness.

~~~admonish info title="Definition: Program Equivalence"
Two expressions $e_1$ and $e_2$ are equivalent, written $e_1 \equiv e_2$ (LaTeX: `\equiv`, Unicode: ≡) if $e_1 \longrightarrow^* v$ and $e_2 \longrightarrow^* v$.
~~~

Recall that $e \longrightarrow e'$ ("steps to", LaTeX: `\longrightarrow`) means that the Python expression $e$ takes a single step of evaluation to $e'$ in our mental model of computation.
The notation $e \longrightarrow^* e'$ ("evaluates to", LaTeX: `\longrightarrow^*`) means that $e$ takes _zero or more steps_ to arrive at $e'$.
With this in mind, the formal definition of equivalence amounts to saying the following:

> Two expressions are equivalent if they evaluate to _identical_ values.

Thus, we can prove a claim of program equivalence by using our mental model to give a step-by-step derivation (an _execution trace_) of an expression to a final value.
If both sides of the equivalence evaluate to the same value, then we know they are equivalent by our definition!
The execution trace itself is a _proof_ that the equivalence holds.

For example, consider the following recursive definition of `factorial`:

~~~python
def factorial(n):
    if n == 0:
        return 1
    else:
        n * factorial(n-1)
~~~

and subsequent claim about its behavior:

~~~admonish question title="Claim"
**Claim**: `factorial(3)` $\equiv$ `6`.
~~~

We can prove this claim by evaluating the left-hand side of the equivalence and observing that it is identical to the right-hand side:

~~~admonish check title="Proof"
The left-hand side expression evaluates as follows:

```python
    factorial(3)
--> {if 3 == 0: return 1 else: return 3 * factorial(3-1)}
--> {if False: return 1 else: return 3 * factorial(3-1)}
--> {return 3 * factorial(3-1)}
--> {return 3 * factorial(2)}
--> {return 3 * { if 2 == 0: return 1 else: return 2 * factorial(2-1)}}
--> {return 3 * { if False: return 1 else: return 2 * factorial(2-1)}}
--> {return 3 * { return 2 * factorial(2-1)}}
--> {return 3 * { return 2 * factorial(1)}}
--> {return 3 * { return 2 * {if 1 == 0: return 1 else: return 1 * factorial(1-1)}}}
--> {return 3 * { return 2 * {if False: return 1 else: return 1 * factorial(1-1)}}}
--> {return 3 * { return 2 * {return 1 * factorial(1-1)}}}
--> {return 3 * { return 2 * {return 1 * factorial(0)}}}
--> {return 3 * { return 2 * {return 1 * {if 0 == 0: return 1 else: return 0 * factorial(0-1)}}}}
--> {return 3 * { return 2 * {return 1 * {if True: return 1 else: return 0 * factorial(0-1)}}}}
--> {return 3 * { return 2 * {return 1 * {return 1}}}}
--> {return 3 * { return 2 * {return 1 * 1}}}
--> {return 3 * { return 2 * {return 1}}}
--> {return 3 * { return 2 * 1}}
--> {return 3 * { return 2}}
--> {return 3 * 2}
--> {return 6}
--> 6
```

~~~

This precise step-by-step analysis of the behavior of the expression rigorously proves our claim!

## Formatting Proofs

We will use this standard format for writing formal proofs for the remainder of the course.
In summary, we write:

~~~admonish info title="Proof Template"
**Claim**: _(The claim to be proven)_

_Proof_.
_(The proof of the claim)_
~~~

~~~admonish problem title="Exercise (Concrete Evaluation, ‡)"
Prove the following claim over concrete expressions:

**Claim**: `1 + 2 + 3` $\equiv$ `(3 * (3 + 1)) / 2`.

Write our your proof in the format described above.
~~~
