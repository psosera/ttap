# Mathematical Induction

So far, we have applied induction exclusively to lists.
However, are there other structures that are inductive?
It turns out that the _natural numbers_ are the most common structure to perform induction over in mathematics.
Now that we have some experience with inductive proof, let's explore this foundational application of the technique in detail.

## The Natural Numbers

Recall that we can apply induction to any inductively-defined structure, that is, a structure defined in terms of a finite set of (potentially recursively-defined) cases.
How can we define the natural numbers, _i.e._, non-negative integers, in this manner?
One natural choice is to start at zero, the smallest natural number, and work our way up.

Since it is the smallest natural number, zero seems to serve as a base case, similar to the empty list.
However, how can we characterize a non-zero natural number in terms of another natural number?
Consider the number seven, written in unary, _i.e._, tally marks:

~~~
 1111111
\_______/
    |
    7
~~~

Do we see a smaller natural number nested somewhere inside of seven?
The unary representation makes this explicit:

~~~
 1   111111
    \______/
        |
        6
~~~

Seven can be thought of as six but with one extra mark!
In general, any non-zero natural number $k$ is one more than $k - 1$.
This fact leads to the following definition of the natural number as an inductive structure:

~~~admonish info title="Definition (Natural Number)"
A _natural number_ is either:

+   _Zero_, or
+   _The successor_, $k + 1$ of some natural number $k$.
~~~

With lists, we can use list functions list to query and break apart the structure:

+   `is_empty` allows us to test if a list is empty.
+   `head` lets us extract the head of an empty list.
+   `tail` lets us extract the tail of an empty list.
+   `cons` lets us construct a list from a head and tail.

We do the same thing with natural numbers, albeit with functions that you have seen before but may not have thought of in this context:

+   `(==)` allows us to test if a natural number is zero.
+   `(-)` allows us to retrieve the smaller natural number from a larger one.
+   `(+)` allows us to build up larger natural numbers from smaller ones.

~~~admonish problem title="Exercise (Primitive Zero)"
Using the inductive definition of a natural number, write a recursive function `plus(n1, n2)`, which returns the result of adding `n1` and `n2`.
In your implementation, you are only allowed to use:

+ Conditionals,
+ `... == 0`,
+ A recursive function call,
+ Subtraction by one, _i.e._, `n - 1`.

(_Note_: This isn't how we would implement `plus` in a real system.
This is merely an exercise in using the inductive definition of the natural numbers.)
~~~

## Induction Over the Natural Numbers

Now that we have an inductive definition of the natural numbers, we can:

1.  Perform recursive operations over the natural numbers, and
2.  Prove properties involving natural numbers by using inductive reasoning.

Of course, not having a formal inductive definition didn't stop us from writing recursive functions over the natural numbers.
Similarly, with lists, having this definition in-hand can give us more confidence that we are doing the right thing when we begin programming.

With that being said, let us focus on the second activity for the remainder of this section: writing inductive proofs with natural numbers.
Induction over the natural numbers is so common that we give it a generic name: _mathematical induction_.
Consider the following canonical recursive function over the natural numbers:

~~~python
def factorial(n):
    if n == 0:
        return 1
    else
        return n * factorial(n-1)
~~~

And the following claim about `factorial` asserts that factorial produces only positive, non-negative, non-zero results.

~~~admonish problem title="Claim"
For any natural number `n`, `0 < factorial(n)`.
~~~

Note that the definition of `factorial` reflects the structure of the inductive definition of a natural number.
This strongly suggests that we ought to prove this claim using induction.
Let's go ahead and try it, following the same style of proof introduced for lists, but instead invoking the inductive definition of natural numbers.

~~~admonish problem title="Proof Skeleton"
_Proof_.
By induction on `n`.

+   `n` is zero. …

+   `n` is non-zero. …
~~~

The case analysis of our inductive is similar to that of lists.
The "base case" for a natural number is when that number is zero.
The "inductive case" for a natural number is when that number is non-zero.
Note that because a natural number cannot be negative and the inductive case number is not zero, this number must be a positive integer.
If we call that natural number `k`, then we know that `k-1` is well-defined (since `k` is at least `1`).

The proof of the base case follows from evaluation:

~~~admonish problem title="Proof (Base Case)"
+   `n` is zero.
    `factorial(n) -->* 1` and so `0 < (factorial 0) -->* 0 < 1`.
~~~

The proof of the recursive case also follows from evaluation, invoking the induction hypothesis, and collecting assumed constraints about the different pieces of the inequality.

~~~admonish problem title="Proof (Inductive Case)"
+   `n` is non-zero.
    We assume our induction hypothesis:

    **Induction Hypothesis**: `0 < factorial(n-1)`

    And prove:

    **Goal**: `0 < factorial(n)`

    The left-hand side of the equivalence evaluates as follows:

    ~~~python
         0 < factorial(n)
    -->* 0 < n * factorial(n-1)
    ~~~

    Our induction hypothesis tells us that `0 < factorial(n-1)`, _i.e._, `factorial(n-1)` is a strictly positive integer.
    Furthermore, we know that `n` is non-zero, so `n` is also a strictly positive integer.
    Therefore, we know that their product `n * factorial(n-1)` is also strictly positive and thus `0 < n * factorial (- n 1)`.
~~~

Note that in the inductive case, we acquire the following assumptions:

+   `n` is non-zero because we are in the inductive case.
+   `0 < factorial(n-1)` from our induction hypothesis.

These two facts combined with the knowledge that two multiplying two positive numbers produces a positive number tell us that the body of `factorial` produces a strictly positive result.

~~~admonish problem title="Exercise (Racket Sum, ‡)"
Consider the following Python definition.

```python
def sum(n)
    if n == 0:
        return 0
    else:
        return n + sum(n-1)
```

Prove the following claim using mathematical induction:

**Claim**: for all natural numbers `n`, `sum(n) ≡ (n * (n+1)) / 2`.

(_Hint_: when manipulating the right-hand side of the equation, you will likely need to employ several arithmetic identities to get the goal into a form where the induction hypothesis applies.)
~~~

## Structural Versus Mathematical Induction

Now, we have two objects that can be the subject of induction: lists and numbers.
In some situations, we might have both types available as candidate subjects of inductive analysis.
Does it matter which one that we pick?
Naturally, _it depends_ on the operation we are analyzing!
However, here are some general considerations when choosing one kind of induction over the other:

### You can only perform induction on things you have

For example, let's consider the function `replicate(n, v)` which produces a list of `n` copies of `v`.
And consider the following claim about this function:

~~~admonish problem title="Claim"
For all natural numbers `n` and values `v`, `length(replicate(n, v)) ≡ n`.
~~~

Since our claim is ultimately about the length of a list and `replicate` produces a list, it is tempting to say that we will prove this claim by induction on a list.
However, note that our assumptions don't give us a list!
As part of quantifying our variables, we assume the existence of some natural number `n` and a value (of arbitrary type) `v`.
We do not have an actual list to perform induction over.

### Perform induction on the object your function performs case analysis over

In other cases, we have multiple objects, say, for example, a natural number and a list, available to us.
Which one should we perform induction over?
We should likely perform induction on the object _that our function is performing case analysis over_.
That way, the cases allow us to make progress in evaluating the function call.

For example, consider the function `list_inc(l, n)`, which increments every element of `l` by `n`.
Both `l` and `n` are inductively defined.
However, the implementation of `list_inc`:

~~~scheme
def list_inc(l, n):
    match l:
        case []:
            return []
        case [head, *tail]:
            return cons(n+head, list_inc(tail, n))
~~~

Relies on a case analysis on `l`, not `n`!
Thus, it is likely that we should analyze the function by structural induction on `l` rather than mathematical induction on `n`.

~~~admonish problem title="Exercise (What If?)"
Imagine the following claim over `list_inc`:

**Claim**: for all lists `l` and natural numbers `n`, `length(list_inc(l, n)) ≡ length(l)`.

Where do you specifically get stuck in proving this claim if you perform mathematical induction on `n`?
~~~

That being said, mathematical induction can be applied in _any_ context where we have access to a natural number, even if it is not part of the immediate goal.
For example, consider a claim over lists we've seen previously:

~~~admonish problem title="Claim"
For all lists `l`, `append(l, []) ≡ l`.
~~~

While there is no natural number variable introduced by the claim, we do have access to one number: `length(l)` produces a natural number since `l` is assumed to be a list!
In other words, we could try to proceed by induction on the _length_ of `l` rather than its structure.

~~~admonish check title="Proof"
_Proof_.
By induction on the result of `length(l)`.
Because `l` is a list `length(l)` must produce a natural number; call it `n`.

+   `n` is zero.
    Because `n` is zero, that means `l` has no elements and thus `l` is the empty list and `append([], []) -->* '()`.

+   `n` is non-zero.
    Our induction hypothesis states that:

    **Inductive Hypothesis**: for any list `l1` where `length(l1) = n-1`, `append(l1, []) ≡ l1`.

    **Goal**: `append(l, []) ≡ l`.

    Because `n` is non-zero, we know that `l` has at least one element and is thus non-empty.
    The left-hand side of the equivalence evaluates as follows:

    ~~~python
         append(l, [])
    -->* cons(head, append(tail, []))
    ~~~

    Because `length(l) ≡ n`, `length(tail) ≡ n-1`, _i.e._, `tail` has one less element than `l`.
    Thus we can invoke our induction hypothesis to rewrite `append(tail, [])` to `l`, thus proving the claim.

    ~~~scheme
        cons(head, append(tail, []))
      ≡ cons(head, tail)
    --> l
    ~~~

    The derivation's final line follows from the fact that `cons` attaches its first argument onto the front of the second argument.
~~~

It turns out that the proof works out, but our reasoning is a lot more complicated!

+   We have to continually conclude that the input list `l` is either empty or non-empty from its length.
    Note that we shouldn't assume this fact is true---we ought to prove it true as an auxiliary claim or _lemma_.
+   Our induction hypothesis is far more complicated to state and utilize.
    We'll talk more about the specifics of what it says when we discuss logic in detail.
    But intuitively, the induction hypothesis says that our original claim holds for _a list that has length one less than the original list_.
    Because we assume that the induction hypothesis is true, we get to _choose_ an instantiation for `l1` that benefits our reasoning, here the `tail` of the list.

In short, when we can perform structural induction on an object, we can frequently figure out a way to also perform mathematical induction on that same object, _e.g._, through some notion of "length."
However, when possible, we should use structural induction because it allows us to work directly with the object's definition rather than indirectly through that "length."

## Mathematical Induction Beyond Program Correctness

As a final note, mathematical induction also serves as a bridge between our current focus on program correctness and the wider world of formal mathematics.
We can perform induction on natural numbers that appear at the level of mathematics, not just programs!
The mechanics are identical, although the _domain of proof_ may be different, something we'll discuss in the coming weeks.
We close by highlighting how we can directly translate our proof of the positivity fact for `factorial`-the-Racket-function to factorial-the-math-function.

First, we define factorial in mathematical notation as follows:

$$
\begin{align*}
0! =&\; 1 \\
n! =&\; n \times (n-1)!
\end{align*}
$$

And then we can state and prove our positivity claim as follows:

~~~admonish check title="Proof"
**Claim**: for all natural numbers $n$, $n! > 0$.

_Proof_.
By induction on $n$.

+   $n$ is zero, then $0! = 1 > 0$ by definition.
+   $n$ is non-zero.

    **Induction Hypothesis**: $(n-1)! > 0 $

    **Goal**: $n! > 0$.

    Since $n$ is non-zero, $n! = n \times (n - 1)!$.
    We know from our IH and by our case assumption that both $n$ and $n - 1$ are strictly positive so $n \times (n - 1)! > 0$ as well.
~~~

Compare the proof of positive for `factorial` versus $n!$ and note how they are identical save for the fact that we use program simplification for `factorial` and arithmetic for $n!$.
Notably, while the domains---programs versus arithmetic---are different, the proof technique---mathematical induction---remains the same!

~~~admonish problem title="Exercise (Math Sum, ‡)"
In the **Racket Sum** exercise, you showed that the Racket `sum` function followed the arithmetic sum identity $1 + \cdots + n = \frac{n(n+1)}{2}$.
Following the proof template above for mathematical induction for arithmetic to prove that the arithmetic sum identity is true:

**Claim (Arithmetic Summation)**: $1 + \cdots + n = \frac{n(n+1)}{2}$.

(_Hint_: $1 + \cdots + n$ is short-hand for the summation of $1$ through $n$.
Where is the smaller summation sequence instead of this longer one?)
~~~

