# Preconditions and Proof States

With quantified variables, we can write rich equality propositions over functions.
However, there is an essential detail in our claims that we have glossed over until now.
Recall our `list_append` claim from the previous readings:

~~~admonish question title="Claim"
For any lists `l1` and `l2`, `len(l1) + len(l2)` ≡ `len(list_append(l1, l2))`.
~~~

We snuck under the radar that we _assumed_ that `l1` and `l2` were lists!
This fact is essential because Python is happy to let us call our functions with _any_ values that we want.
The only caveat is that we may not like the results!

~~~python
>>> list_append("banana", 7)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
  File "<stdin>", line 5, in list_append
  File "<stdin>", line 5, in list_append
  File "<stdin>", line 5, in list_append
  [Previous line repeated 3 more times]
TypeError: Value after * must be an iterable, not int
~~~

Sometimes we might get lucky and get a runtime error indicating that our assumption was violated.
However, in other cases, we aren't so lucky:

~~~python
>>> list_append([], "wrong")
'wrong'
~~~

Here we feed `list_append` a string for its second argument.
Recall that `list_append` performs case analysis on its first argument.
When that first argument is empty, then the second argument is returned immediately.
So even though the second argument had an incorrect type, the function still "works."

Nothing stops the user of `list_append` from passing inappropriate arguments.
`list_append` _assumes_ that the arguments passed obey specific properties, in this particular case, that the arguments are lists.
These assumptions are integral to the correct behavior of the function.
Consequently, when we analyze a program, we will also need to _track_ and _utilize_ these assumptions in our reasoning.
This realization will lead us to a _formal definition of proof_ that will guide our remaining study of the foundation of mathematics.

## Review: Preconditions and Postconditions

In CSC 151, we captured assumptions about our functions' requirements and behavior as _preconditions and postconditions_.

~~~admonish info title="Definitions"
**Precondition**: a property about the inputs to a function that (a) the caller of the function must fulfill and (b) the function assumes are true.

**Post-condition**: a property about the behavior of the function that (a) the caller can assume holds provided they fulfilled the preconditions of the function and (b) the function ensures during its execution.
~~~

Preconditions and post-conditions form a _contract_ between the _caller_ of the function and the function itself, also called the _callee_.
The caller agrees to fulfill the preconditions of the function.
In return, the callee fulfills its post-conditions under the assumption that the preconditions have been fulfilled.

Preconditions and post-conditions are an integral aspect of program design.
They formalize the notion that while a function, in theory, can guard against every possible erroneous input, it is impractical to do so.
For example, recall that mergesort relies on a helper function `merge` that takes two lists and combines them into a single sorted list:

~~~python
def merge(l1, l2):
    '''merge(l1, l2) merges lists l1 and l2 into a sorted list.'''
    # ...
    pass
~~~

The post-condition of `merge` is that the list it returns is sorted, _i.e._, using our language of propositions:

~~~admonish question title="Post-condition"
is_sorted(merge(l1, l2)) ≡ True.
~~~

`is_sorted` is a custom function that takes a list `l` as input and returns `True` if and only if `l` is sorted.

However, what preconditions do we place upon the inputs, `l1` and `l2`?

+   `l1` and `l2` must both be lists.
+   The elements of `l1` and `l2` must all be comparable to each other (presumably with the `(<)` operator).
+   `l1` and `l2` must be sorted.

If the implementor of `merge` was paranoid, they might consider implementing explicit checks for these three preconditions.
However, imagine what these checks might look like in code.
All the checks require that we either:

1.  Scan the lists `l1` and `l2` multiple times, once for each precondition we wish to check.
2.  Integrate all three checks into the merging behavior of the function.

The former option is modular---we can write one helper function per check---but inefficient.
The latter option is more efficient but highly invasive to the `merge` function and leads to an unreadable implementation.

Both options are undesirable.
This fact is why we usually choose a third option: make the preconditions _documented assumptions_ about the inputs of the function.

~~~admonish note title="Checking Preconditions in Code"
Note that how we account for preconditions in our programs is ultimately an _engineering decision_.
You have to consider the context you are in---development ecosystem, company culture--and perform a cost-benefit analysis to determine what the "correct" approach is for your code.

In CSC 151, we introduced a "kernel-husk" implementation that separated precondition checks from the function's primary behavior.
While inefficient, this approach is desirable in some situations because Scheme provides little static error checking.
Other languages provide different mechanisms to capture precondition checks without incurring runtime costs when they are important.
For example, Java has an `assert` statement that you can use to check preconditions only when you compile the program in debug mode.
When you release your software, you can easily switch to release mode, which removes all the `assert` checks.
~~~

## Preconditions as Assumptions During Proof

When we reason about concrete propositions, preconditions are unnecessary because we work with actual values.
However, with abstract propositions, preconditions become _assumptions_ about (universally quantified) variables.

We initially obtain these assumptions as part of the proposition we are verifying.
For example, in our `list_append` claim:

~~~admonish question title="Claim"
For any lists `l1` and `l2`, `len(l1) + len(l2)` ≡ `len(list_append(l1, l2))`.
~~~

The text "for any _lists_ …" introduces the precondition that `l1` and `l2` are lists.

As another example, consider the post-condition of `merge` now realized as a proposition:

~~~admonish question title="Claim"
For any lists of numbers `l1` and `l2`, if `is_sorted(l1) ≡ True` and `is_sorted(l2) ≡ True` then `is_sorted(merge(l1, l2)) ≡ True`.
~~~

In addition to the assumptions we have about the _type_ of the variables, we can also have arbitrary _properties_ about these variables.

Our claim is of the form "if … then …" where the preconditions sit between the "if" and "then"
We assume these preconditions and then go on to prove the claim which follows the "then."
In this example, the additional preconditions are that:

+   `is_sorted(l1) ≡ True`
+   `is_sorted(l2) ≡ True`

And then the claim we prove with these assumptions is:

+   `is_sorted(merge(l1, l2))`.

### Short-hand for Propositions Involving Booleans

As we discuss preconditions throughout this reading and beyond, we will frequently work with preconditions that assert an equivalence between two expressions of boolean type.
For example, to state the proposition that `x` is less than `y`, we would declare that it is equivalent to `True`:

+    `x < y ≡ True`

This notation is quite taxing to write where we have many preconditions in our reasoning.
Instead, we'll use shorthand, taking advantage of the similarities between boolean expressions and propositions.
When we write the following proposition:

+    `x < y`

We really mean the complete equivalence `x < y ≡ True`.
With this notation, we can rewrite the above claim above more elegantly as:

~~~admonish question title="Claim"
For any lists of numbers `l1` and `l2`, if `is_sorted(l1)` and `is_sorted(l2)` then `is_sorted(merge(l1, l2))`.
~~~

~~~admonish info title="On Mathematical Short-hand"
This short-hand is our first example of a common mathematics practice.
In our pursuit of a rigorous, formal definition, we might create a situation where it is highly inconvenient to use this definition in prose.
Perhaps this definition is too verbose.
Or maybe it is highly parameterized, but in the common case, the parameterization is unnecessary.

In these situations, we introduce short-hand notation in mathematics to concisely write down our ideas.
An example of this notation are _symbols_, _e.g._, $x | y$ for "$x$ divides $y$."
Alternatively, we may introduce _puns_, seemingly invalid mathematical syntax, given special meaning in context.
For example, a Racket expression is not a program equivalence.
However, in the above example, we introduce the pun that a _boolean_ Python expression is, implicitly, a program equivalence with `True`.

Mathematical short-hand is a secret sauce of writing mathematics.
It allows us to write beautifully concise yet thoroughly precise prose.
However, if you aren't aware of the meaning of symbols and puns in a piece of mathematical writing, you can quickly become lost.
Keep an eye out for such short-hand when reading mathematical prose, and you will find your comprehension to go up rapidly as a result.
~~~

~~~admonish question title="Exercise"
Write a claim about the behavior of the following function that implies its correctness.
Make sure to include any necessary preconditions in your claim.

```Python
def index_of(x, l):
    '''Returns the (0-based) index of element x in list l'''
    # ...
    pass
```
~~~

### Tracking and Utilizing Assumptions

Assumptions about our variables initially come from preconditions we write into our claims.
How do we use these assumptions in our proofs of program correctness?
It turns out we have been doing this already without realizing it!

For example, in our analysis of the boolean `my_and` function from our previous reading, we said the following:

> Because `b` is a boolean, either `b` is `True` or `b` is `False`.

This line of reason is only correct because we _assumed_ via a precondition:

~~~admonish question title="Claim"
For all booleans `b`, `my_and(b, False) ≡ False`.
~~~

That `b` is indeed a boolean.
If we did not have such a precondition, this line of reasoning would be invalid!

When we have an assumption that asserts the type of a universally quantified variable, we can use that assumption to conclude that the variable is of a particular _shape_ according to that type.
For example:

+   A variable that is a natural number is zero or a positive integer.
+   A variable that is a function is bound to a `lambda` value, _e.g._, `(lambda (n) (+ n 1))`.
+   A variable that is a boolean is either `#t` or `#f`.

Furthermore, if an operation requires a value of a particular type as a precondition, a type assumption about a variable allows us to conclude that the variable fulfills that precondition.
For example, if we know that `x` is a number then we know that `x > 3` will produce a boolean result instead of potentially throwing an error.

### Utilizing General Assumptions

Besides type assumptions, we can also assert general properties about our variables as preconditions.
In our `merge` example, we assumed that the input lists satisfied the `is_sorted` predicate, _i.e._, were sorted.
We can then use this fact to deduce other properties of our lists that will help us ascertain correctness, _e.g._, that the smallest elements of each list are at the front.

Because these properties are general, we have to reason about them in a context-specific manner.
Let's look at a simple example of reasoning about one common kind of assumption: _numeric constraints_.
Consider the following simple numeric function:

~~~python
def double(n):
    return n * 2
~~~

And suppose we want to prove the following claim about this function:

~~~admonish question title="Claim"
For all numbers `n`, if `n > 0` then `double(n) > 0`.
~~~

Employing our symbolic techniques, we first assume that `n` is an arbitrary number.
However, the claim that follows the quantification of `n` includes a precondition---it is of the form "if … then … ".
Therefore, in addition to `n`, we also gain the assumption that `n > 0`, _i.e._, `n` is positive.

When we go prove that `double(n) > 0`, we know from our model of computation that:

~~~python
double(n) > 0 --> n * 2 > 0
~~~

This resulting expression is not always true.
In particular, if `n` is non-negative, then `n * 2` will be negative and thus less than `0`.
However, our assumption that `n > 0)` tells us this is not the case!

Here is a complete proof of the claim that employs this observation.

~~~admonish check title="Proof"
**Claim**: For all numbers `n`, if `n > 0` then `(double n) > 0`.

_Proof_.
Let `n` be a number and assume that `n > 0`.
By the definition of `double` we have that:

```python
double(n) > 0 --> n * 2 > 0.
```

However, by our assumption that `n > 0` we know that `n * 2` is a positive quantity---multiplying two positive numbers results in a positive number.
Therefore, `n * 2 > 0` holds.
~~~

### Being Explicit When Invoking Assumptions

In the above proof, I was explicit about:

1.  _When_ I used an assumption in a step of reasoning, and
2.  _How_ I used that assumption to infer new information.

At this stage of your development as a mathematician, you should do the same for _any_ other assumption you employ in a proof.
As an example of what _not to do_, here is the same proof but without the explicit call-out of the assumption:

~~~admonish check title="Proof"
**Claim**: For all numbers `n`, if `n > 0` then `double(n) > 0`.

_Proof_.
Let `n` be a number and assume that `n > 0`.
By the definition of `double` we have that:

```python
double(n) > 0 --> n * 2 > 0.
```

Therefore, `n * 2 > 0` holds.
~~~

This proof is _valid_.
The steps presented and the conclusion are sound.
However, this is a _less formal proof_ because it has left some steps of reasoning _implicit_.
In this course, we aim for rigorous, formal proof, and so we should not leave _any_ steps implicit in our reasoning.

That being said, we will quickly find that dogmatically following this guidance will lead us to ruin.
Analogous to programming, if we write our proofs at too low of a level, we will get lost in the weeds with low-level details and lose sight of the bigger picture.

For now, let's be ultra-explicit about our reasoning and follow the formula above whenever we invoke an assumption.
However, because reasoning about type constraints, _e.g._, happens so frequently, it is okay if you elide _when_ you use a type assumption in your proofs.
For example, instead of saying:

> By our assumptions, we know `b` is a boolean.
> Therefore, `b` must either be `True` or `False`.

You can, instead, say:

> `b` is either `True` or `False`.

However, you should still be clear when you are assuming the type of a variable using a "let"-style statement, _e.g._,

> Let `b` be a boolean.

### Assumptions that Arise During Analysis

Assumptions arise not only when we initially process our claim.
We also gain new assumptions during the proving process.
For example, let's consider the following simple function:

~~~python
def nat_sub(x, y):
    if x < y:
        return 0
    else:
        return x - y
~~~

And the following claim about this function:

~~~admonish question title="Claim"
For all numbers `x` and `y`, `nat_sub(x, y) >= 0`.
~~~

Here is a partial proof of this claim:

~~~admonish check title="Proof"
Let `x` and `y` be numbers.
Then we have that:

```python
    nat_sub(x, y)
--> { if x < y: return 0 else: return x - y }
```
~~~

But now, we're stuck!
We need to evaluate the guard to proceed, but we don't know how the expression `x < y` will evaluate.
However, we do know the following:

+   `x` and `y` are numbers.
+   `x < y` will produce a result because `x` and `y` are numbers.
+   The result of `x < y` is either `True` or `False` because the expression has boolean type.

Because of this, we can _proceed by case analysis_ on the result of `x < y`: it evaluates to either `True` or `False`.
Let us consider each case in turn:

~~~admonish check title="Proof"
+   `x < y --> True`:
    In this case:

    ```python
        { if x < y: return 0 else: return x - y }
    --> { if True: return 0 else: x - y }
    --> { return 0 }
    --> 0
    ```

    So `nat_sub(x, y) -->* 0` which is greater-than or equal to `0`.
+   `x < y --> False`:
    In this case

    ```python
        { if x < y: return 0 else: return x - y }
    --> { if False: return 0 else: x - y }
    --> { return x - y }
    --> x - y
    ```
~~~

Here, we seem to be stuck again.
We don't know how to proceed with the subtraction since `x` and `y` are held abstract.
However, we must remember that in this case, we are assuming that `x < y` is `False`.
Therefore, we know that `x` is greater than or equal to `y`.
Because we know the difference between a larger number and a smaller number is positive, we can conclude that `x - y <= 0` as desired.

Let's see this reasoning together in a complete proof of our claim.

~~~admonish check title="Proof"
**Claim**: for all numbers `x` and `y`, `nat_sub(x, y) >= 0`.

_Proof_.
Let `x` and `y` be numbers.
Then we have that:

```python
    nat_sub(x, y)
--> { if x < y: return 0 else: return x - y }
```

Either `x < y --> #t` or `x < y --> #f`.

+   `x < y --> True`:
    In this case:

    ```python
        { if x < y: return 0 else: return x - y }
    --> { if True: return 0 else: x - y }
    --> { return 0 }
    --> 0
    ```

    Thus, `nat_sub(x, y) -->* 0`, a non-negative result.

+   `x < y --> False`.
    Then we have:

    ```python
        { if x < y: return 0 else: return x - y }
    --> { if False: return 0 else: x - y }
    --> { return x - y }
    --> x - y
    ```

    From our case analysis, we assume that `x < y` does not hold.
    Thus, we know that `x >= y`.
    Because we know subtracting an equal-or-smaller number from a larger number results in a non-negative quantity, we can conclude that `x - y <= 0`.
~~~

In summary, we can obtain assumptions from places other than our claims, _e.g._, through case analysis or the post-condition of an applied function.
We can then use these newly acquired facts to complete our proofs.

~~~admonish question title="Exercise (Nesting)"
Consider the following conditional statement in Python:

```python
if x < 0:
    return e1
elif x <= 3:
    return e2
elif x == 5:
    return e3
elif x <= 8:
    return e4
else
    return e5
```
~~~

For each branch expressions `e1` through `e5` above, describe the _assumptions about `x`_ you can make by entering that branch of the `cond`.

(_Hint_: make sure you account for the fact that to reach a branch, all the previous branches must have returned false.)

## Proof States and Proving

In summary, we have introduced _assumptions_ into our proofs of program correctness.
These assumptions come from preconditions or through analysis of our code.
We use these assumptions to prove our claim.
Because our claim evolves throughout the proof, _e.g._, it is more accurate to say that we use assumptions to prove a _goal proposition_ where the initial goal proposition is our original claim.

Surprisingly, it turns out that _all_ mathematical proofs can be thought of in these terms!

~~~admonish info title="Definitions"
**Proof State**: the **state of a proof** or **proof state** is a pair of a _set of assumptions_ and a _goal proposition_ to prove.

**Proof**: a **proof** is a sequence of logical steps that manipulate a proof state towards a final, provable result.
~~~

When either reading or writing a mathematical proof, we should keep these definitions in mind.
In particular, we have to be aware at _every point of the proof_:

+   What is our set of assumptions?
+   What are we trying to prove?

~~~admonish question title="Exercise (Entry Fees, ‡)"
Consider the following function that calculates the entry fee of a single ticket to Disney World in 2021 (mined from [the Disney website](https://disneyworld.disney.go.com/admission/tickets/theme-parks/)):

```scheme
def calculate_price(day_of_week, age, is_park_hopper):
    return 115 + \
           -5 if age < 10 else 0 + \
           14 if is_friday_or_weekend(day_of_week) else 0 + \
           65 if is_park_hopper else 0
```

(For conciseness sake, the code above utilizes two lesser known features of Python:

+   You can split up a long line into multiple lines by adding '\\' to the end of each line.
+   The _conditional expression_ operator `e2 if e1 else e3` is analogous to the Scheme conditional expression `(if e1 e2 e3)`.
    If `e1` evaluates to `True`, the conditional expression evaluates to whatever `e2` evaluates to.
    Otherwise, it evaluates to whatever `e3` evaluates to.)

Prove the following claim:

**Claim**: For all days of the week `d`, natural numbers `n`, and boolean `b`, if `b ≡ True` then `calculate-price(d, n, b) >= 175`.

(_Note_: yea, Disney World is expensive, huh?)
~~~

