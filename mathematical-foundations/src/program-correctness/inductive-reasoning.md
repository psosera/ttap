# Inductive Reasoning

## Review of Recursive Design

The workhorse of functional programming is _recursion_.
Recursion allows us to perform repetitive behavior by defining an operation in terms of a smaller version of itself.
However, recursion is not just something you learn in Racket and summarily forget about for the rest of your career.
Recursion is pervasive everywhere in computer science, especially in algorithmic design.

Because of this, we must know how to reason about recursive programs.
Our model of computation is robust enough to trace the execution of recursive programs.
However, our formal reasoning tools fall short in letting us prove properties of these programs.
We introduce _induction_ one of the foundational reasoning principles in mathematics and computer science, to account for this disparity.

### A Review of Lists

Lists are a common data structure found in most modern programming languages.
Python is no exception!

Like numbers, there are an infinite number of possible lists, _e.g._,

+   `[]`
+   `[3, 5, 8]`
+   `["Hi", "goodbye", "!"]`
+   `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`.

However, our _recursive_ definition of a list categorizes these infinite possibilities into a _finite_ set of cases.

~~~admonish info title="Definition (List)"
A _list_ is either:

+   Empty, or
+   Non-empty, consisting of a _head_ element and the remainder of the list, its _tail_.
~~~

This definition is similar to that of a boolean, where we define a boolean as one of two possible values: `True` or `False`.
However, the definition of a list is recursive because the non-empty case includes a component that is also a list.

#### Accessing Lists Recursively

In Python, we typically operate over lists much like an array with indexing operations and mutation.
However, we can easily write functions that allow us to operate on lists according to our recursive definition, similarly to Scheme.

~~~python
def is_empty(l):
    '''is_empty(l) returns true if list l is empty'''
    return len(l) == 0

def head(l):
    '''head(l) returns the element at the front of (non-empty) list l'''
    return l[0]

def tail(l):
    '''tail(l) returns (non-empty) list l, but without its head element'''
    return l[1:]
~~~

The implementation of the `tail` function, in particular, takes advantage of Python's _list slicing_ expressions.
`l[n:m]` returns a list containing the elements of `l` starting at index `n`, ending at index `m` (exclusive).
When we elide `m` in a list slice, `m` becomes `len(l)`, _i.e._, the last index of the list.

We can create lists explicitly with list literal notation, _e.g._, `[1, 2, 3, 4, 5]`.
Additionally, we can write a "cons" function:

~~~python
def cons(x, l):
    '''cons(x, l) returns list l but with element x at the front'''
    return [x, *l]
~~~

That allows us to add an element to the front of a list.
The implementation of `cons` uses Python's _sequence unpacking_ expression where `*l` takes the elements of list `l` and, effectively, injects them into a context where a sequence is expected.
Here, the elements of `l` become the remaining elements of the list literal that `cons` returns.

~~~admonish question title="Exercise"
Predict the results of each of the following expressions:

1.  `head([1, 2, 3])`
2.  `tail([1, 2, 3])`
3.  `head(tail(tail([1, 2, 3])))`
4.  `is_empty(tail(tail(tail([1, 2, 3]))))`
~~~

#### Recursive Design with Lists

Because lists are defined via a finite set of cases, we define operations over lists using a combination of the `is_empty` function to determine which kind of list we have and `head` and `tail` to access parts of the list.
For some simple operations, this is enough to get by, _e.g._, a function that retrieves the second element of a list:

~~~python
def second(l):
    if is_empty(l):
        raise ValueError('empty list given')
    elif is_empty(tail(l)):
        raise ValueError('singleton list given')
    else:
        return head(tail(l))
~~~

We can translate this code to a high-level description:

+   If the list is empty, throw an error (via Python's `raise` statement).
+   If the list contains one element, throw an error.
+   If the list has at least two elements, retrieve the second element.

However, mere case analysis doesn't allow us to write more complex behavior.
Consider the prototypical example of a recursive function: computing the length of a list.
Let's first consider a high-level _recursive design_ of the operation of this function, call it `length(l)`.
Because a list is either empty or non-empty, this leads to straightforward case analysis.
The empty case is straightforward:

+   If the list is empty, its length is `0`.

However, in the non-empty case, it isn't immediately clear how we should proceed.
We can draw the non-empty case of a list `l` with head element `v` and tail `tl` as follows:

~~~
l = [v][ ?? tl ?? ]
~~~

Besides knowing that `tl` is a list, we don't know anything about it---it is an _arbitrary list_.
So how can we compute the length of `l` in this case?
We proceed by _decomposing_ the length according to the parts of the lists we can access:

~~~
length =  1 + length t
l      = [h][ ?? t ?? ]
~~~

We know that the head element `h` contributes `1` to the overall length of `l`.
How much does the tail of the list `t` contribute?
Since `t` is, itself, a list, `t` contributes whatever `length(t)` produces.
But this is a call to the same function that we are defining, albeit with a smaller list than the original input!

Critically, as long as the call to `length(t)` works, the overall design of `length` is correct.
This assumption that we make---that the recursive call "just works" as long as it is passed a "smaller" input---is called the _recursive assumption_, and it is the distinguishing feature of recursion compared to simple case analysis.

In summary, we define `length` recursively as follows:

> The length of a list `l` is:
>
> +   `0` if `l` is empty.
> +   `1` plus the length of the tail of `l` if `l` is non-empty.

In the recursive case of the definition, our recursive assumption allows us to conclude that the "length of the tail of `l`" is well-defined.

Our high-level recursive design of `length` admits a direct translation into Racket using our list functions:

~~~python
def length(l):
    if is_empty(l):
        return 0
    else:
        return 1 + length(tail(l))
~~~

Because the translation is immediate from the high-level recursive design, we can be confident our function is correct provided that the design is correct.

#### Pattern Matching with Lists

Recall that one of our design goals is to write programs that are correct from inspection.
In particular, when we have a recursive design, we want our code to look like that design.
Let's see how our recursive definition of `length` fares in this respect.
Below, we have replicated the definition of `length` with the recursive design in-lined in comments:

~~~python
def length(l):
    if is_empty(l):             # A list is either empty or non-empty.
        return 0                # + The empty list has zero length.
    else:                       # + A non-empty list has...
        hd = head(l)            #     - A head element hd and
        tl = tail(l)            #     - A tail element tl.
        return 1 + length(tl)   #   The length of a non-empty list is
                                #   plus the length of the tail.
~~~

In this version of the code, we explicitly bind the head and tail of `l` to be clear where these components of `l` are manipulated.

Overall, this isn't too bad!
Like our design, the code is clearly conditioned on whether `l` is empty or non-empty.
Furthermore, the results of the cases clearly implement the cases of our design, so we can believe our implementation is correct as long as we believe our design is correct.

Is there anything we can improve here?
Yes—some subtle, yet important things, in fact:

+   We need to make sure that the guard of our conditional accurately reflects the cases of our data structure.
    Here, our list is either empty or non-empty which is captured by an `is_empty` check.
+   We know that in the recursive case that our non-empty list is made up of a head and tail which we need to manually access using `head` and `tail`, respectively.
    We locally bind names to these individual pieces so that we don't interchange `head` and `tail` calls in our code, but these bindings add additional complexity to our implementation.

To fix these issues, we'll use the _pattern matching_ facilities of Python to express our recursive design directly without the need for a guard expression or let-binding.
Note that when we talk about pattern matching here, we don't mean regular expression matching but instead a separate facilities of Python for writing code that looks at the _shape_ of a data type.

First, we'll revise our list definition slightly based on the functions we use to construct lists.

> A list is either:
> +   `[]`, the empty list.
> +   `cons(v, l)`, a non-empty list constructed that consists of a head element `v` and a list `l`.

Remember, in this recursive scheme, a list is ultimately composed of repeated `cons` calls ending in `[]`.
For example:

~~~python
>>> [1, 2, 3, 4, 5]
[1, 2, 3, 4, 5]
> cons(1, cons(2, cons(3, cons(4, cons(5, []))))) 
[1, 2, 3, 4, 5]
~~~

Because of this, we know that our _constructive definition_ of a list covers all possible lists.
Now, we'll use pattern matching to directly express `length` in terms of this constructive definition:

~~~python
def length(l):
    match l:
        case []:
            return 0
        case [hd, *tl]:
            return 1 + length(tl)
~~~

This version of `length` behaves identically to the previous version of the code but is more concise, directly reflecting our constructive definition of a list.

The _pattern matching statement_ in Python allows us to directly perform a case analysis on data:

1.  After the `match` keyword is the _scrutinee_ or _subject_ of the pattern match, `l`.
2.  Following the scrutinee are _branches_, each one corresponding to a particular shape of the data we're analyzing.
    -   Following the `case` keyword is a _pattern_, a particular value shape that the scrutinee must match in order for this branch to be selected.
    -   On the next line after each `case` is the _body_ of the pattern match that is evaluated when the scrutinee matches the branch's pattern.

Importantly, patterns may contain variables that are bound to parts of the scrutinee on a successful match.
The first pattern, the empty list `[]`, does not contain any such variables.
But the second pattern, a list literal pattern combined with an _unpacking_ operator, binds the first element of the list to `hd` and the tail of the list to `tl`.
By using pattern matching, we no longer need to bind locals to name the subcomponents of a list!

### A Recursive Skeleton for Lists

Ultimately, the recursive design of a function contains two parts:

+   Case analysis over a recursively-defined structure.
+   A recursive assumption allowing us to use the function recursively on a smaller object than the input.

When we fix the structure, _e.g._, lists, we arrive at a _skeleton_ or template for defining recursive functions that operate on that structure.
This skeleton serves as a starting point for our recursive designs.
The skeleton always mimics the recursive definition of the structure:

~~~admonish info title="Recursive Skeleton for Lists"
For an input list `l`:

+   What do we do when `l` is empty (the _base case_)?
+   What do we do when `l` is non-empty (the _recursive case_)?
    When `l` is non-empty, we have access to the head of `l` and the tail of `l` with pattern matching.
    Furthermore, when `l` is non-empty, we can use our recursive assumption to recursively call our function on the tail of `l`.
~~~

Note that this skeleton is only a starting point in our recursive design.
We may need to generalize or expand the skeleton, _e.g._, by adding additional base cases depending on the problem.

~~~admonish question title="Exercise (Intersperse)"
Write a high-level recursive design for the `list_intersperse` function.
`list_intersperse(v, l)` returns `l` but with `v` placed between every element of `l`.
For example:

```python
>>> list_intersperse(0, [1, 2, 3, 4, 5])
[1, 0, 2, 0, 3, 0, 4, 0, 5]
>>> list_intersperse(0, [])
[]
>>> list_intersperse(0, [1])
[1]
```

(_Hint_: this is an example of a function where its most elegant implementation comes from having _multiple base cases_.
Consider an additional base case in your recursive design.)
~~~

## Inductive Reasoning with Lists

Now that we've discussed how we write recursive programs over lists, we'll develop our primary technique for reasoning about recursive structures, _structural induction_.

### Reasoning About Recursive Functions

Let's come back to the proposition about `append` that we used to start our discussion of program correctness:

~~~admonish question title="Claim"
For all lists `l1` and `l2`, `length(l1) + length(l2)` ≡ `length(append(l1, l2))`.
~~~

To prove this claim, we need the definitions of both `length` and `append`.

~~~admonish question title="Exercise (Append)"
Try to design and implement `append(l1, l2)`, which returns the result of appending `l1` onto the front of `l2` without peeking below!
~~~

~~~python
def length(l)
    match l:
        case []:
            return 0
        case [head, *tail]:
            return 1 + length(tail)

def append(l1, l2):
    match l1:
        case []:
            return l2
        case [head, *tail]:
            return cons(head, append(tail))
~~~

The proof proceeds similarly to all of our symbolic proofs so far: assume arbitrary values for the universally quantified variables and attempt to use symbolic evaluation.

~~~admonish info title="Proof"
_Proof_.
Let `l1` and `l2` be arbitrary lists.
The left-hand side of the equivalence evaluates to:

```scheme
    length(l1) + length(l2)
--> { match l1:
          case []:
              return 0
          case [head, *tail]:
              return 1 + length(tail) } + length(l2)
```

The right-hand side of the equivalence evaluates to:

```python
    length(append(l1, l2))
--> length({ match l1:
                 case []:
                     return l2
                 case [head, *tail]:
                     return cons(head, append(tail, l2)) })
```
~~~

At this point, both sides of the equivalence are stuck.
However, we know that because `l1` is a list, it is either empty or non-empty.
Therefore, we can proceed with a case analysis of this fact!

~~~admonish info title="Proof (Empty Case)"
Either `l1` is empty or non-empty.

+   `l1` is empty, _i.e._, `l1` is `[]`.
    The left-hand side of the equivalence evaluates as follows:

    ```python
    ...
    --> { match []:
              case []:
                  return 0
              case [head, *tail]:
                  return 1 + length(tail) } + length(l2)
    --> { return 0 }
    --> 0 + length(l2)
    --> length(l2)
    ```

    On the right-hand side of the equivalence, we have:

    ```python
    ...
    --> length({ match []:
                    case []:
                        return l2
                    case [head, *tail]:
                        return cons(head, append(tail, l2)) })
    --> length({ return l2 })
    --> length(l2)
    ```

    Both sides evaluate to `(length l2)`, so they are equivalent!
~~~

So the empty case works out just fine.
What about the non-empty case?

~~~admonish info title="Proof (Non-empty Case)"
+   `l1` is non-empty.
    Since `l1` is non-empty, `l1` is `(cons h t)` for some value `h` and list `t`, so on the left-hand side of the equivalence, we have:

    ```python
    ...
    --> { match l1:
              case []:
                  return 0
              case [head, *tail]:
                  return 1 + length(tail) } + length(l2)
    --> { return 1 + length(tail) } + length(l2)
    --> (1 + length(tail)) + length(l2)
    --> 1 + (length(tail) + length(l2))
    ```

    The final step of evaluation comes from the commutative property of addition: $(1 + x) + y = 1 + (x + y)$.

    On the right-hand side of the equivalence, we have:

    ```python
    ...
    --> length({ match l1:
                    case []:
                        return l2
                    case [head, *tail]:
                        return cons(head, append(tail, l2)) })
    --> length({ return cons(head, append(tail, l2)) })
    --> length(cons(head, append(tai1, l2)))
    --> { match cons(head, append(tail, l2)):
              case []:
                  return 0
              case [h2, t2]:
                  return 1 + length(t2) }
    --> { return 1 + length(t2) }
    --> return 1 + length(t2)
    --> return 1 + length(append(tail, l2))
    ```
~~~

Note that this evaluation is a bit trickier than the previous ones that we have seen.
In particular, we have to observe that the tail of `cons(x, y)` is simply `y`!
Nevertheless, if we push through accurately, we can persevere!

At this point, our equivalence in the non-empty case is:

~~~python
1 + length(tail) + length(l2) ≡ 1 + length(append(tail, l2))
~~~

`tail` is still abstract, so we can't proceed further.
One way to proceed is to note that `tail` itself is a list.
Therefore, we can perform case analysis on it---is `tail` empty or non-empty?

~~~admonish info title="Proof (Case analysis on the tail)"
(Still in the case where `l1` is non-empty.)

`tail` is either empty or non-empty.

+   `tail` is empty.
    The left-hand side of the equivalence evaluates to:

    ```python
    ...
    --> 1 + length([]) + length(l2)
    --> 1 + { match []:
                  case []:
                      return 0
                  case [head2, *tail2]:
                      return 1 + length(tail2) } + length(l2)
    --> 1 + { return 0 } + length(l2)
    --> 1 + 0 + length(l2)
    --> 1 + length(l2)
    ```

    The right-hand side of the equivalence evaluates to:

    ```python
    ...
    --> 1 + length(append([], l2))
    --> 1 + length({ match []:
                         case []:
                             return l2
                         case [head2, *tail2]:
                             return cons(head2, append(tail2, l2)) })
    --> 1 + length({ return l2 })
    --> 1 + length(l2)
    ```

    Both sides of the equivalence are `1 + length(l2)`, completing this case.
~~~

Note that when `tail` is empty, the original list `l1` only contains a single element.
Therefore, it should not be surprising that the equivalence boils down to demonstrating that both sides evaluates to `1 + length(l2)`.

Again, while the empty case works out, the non-empty case runs into problems.

~~~admonish info title="Proof (Case analysis on the tail, non-empty case)"
(Still in the case where `l1` is non-empty.)

+   `tail` is non-empty.
    The left-hand side of the equivalence evaluates to:

    ```python
    ...
    --> 1 + length([]) + length(l2)
    --> 1 + { match []:
                  case []:
                      return 0
                  case [head2, *tail2]:
                      return 1 + length(tail2) } + length(l2)
    --> 1 + { return 1 + length(tail2) } + length(l2)
    --> 1 + (1 + length(tail2)) + length(l2)
    --> 1 + 1 + length(tail2) + length(l2)
    ```
~~~

`tail2` here is the tail of `tail`, _i.e._, `tail(tail(l1))`!

Notice a pattern yet?
Here is where our case analyses have taken the left-hand side of the equivalence so far:

~~~python
     length(l1) + length(l2)
-->* <... l1 is non-empty ...>
-->* 1 + length(tail) + length(l2)
-->* <... tail of l1 is non-empty ...>
-->* 1 + 1 + length(tail2) + length(l2)
~~~

We could now proceed with case analysis on `tail2`.
We'll find that the base/empty case is provable because in that case, we assume that `l1` has exactly two elements.
But then, we'll end up in the same situation we are in, but with one additional `(+ 1 ...` at the front of the expression!
Because the inductive structure is defined in terms of itself, and we are proving this property over _all_ possible lists, we don't know when to stop our case analysis!

~~~admonish problem title="Exercise (The Other Side)"
We demonstrated that case analysis and evaluation of the equivalence's left-hand side seemingly has no end.
Perform a similar analysis of the equivalence's right-hand side, starting when `tail` is non-empty.
You should arrive at the point where the right-hand side evaluates to:

```python
1 + 1 + length(append(tail2, l2))
```
~~~

### Inductive Reasoning

How do we break this seemingly infinite chain of reasoning?
We employ an _inductive assumption_ similar to the recursive assumption we use to design recursive functions.
The recursive assumption is that our function "just works" for the tail of the list.
Our inductive assumption states that our original claim holds for the tail of the list!

Recall that our original claim stated:

~~~admonish question title="Claim"
For all lists `l1` and `l2`, `length(l1) + length(l2) ≡ length(append(l1, l2))`.
~~~

Our _inductive assumption_ is precisely the original claim but specialized to the tail of the list that we perform case analysis over.
We also call this inductive assumption our _inductive hypothesis_.

~~~admonish info title="Inductive Hypotheiss"
`length(tail) + length(l2) ≡ length(append(tail, l2))`.
~~~

While we are trying to prove the claim, the inductive hypothesis is an _assumption_ we can use in our proof.

Let's unwind our proof back to the case analysis of `l1`.
The case where `l1` was empty was provable without this inductive hypothesis, so let's focus on the non-empty case.
Recall that before we performed case analysis, we arrived at a proof state where our goal equivalence to prove was:

~~~python
1 + length(tail) + length(l2) ≡ 1 + length(append(tail, l2))
~~~

Compare this goal equivalence with our induction hypothesis above.
We see that the left-hand side of the induction hypothesis equivalence, `length(tail) + length(l2)`, is contained in the left-hand side of the goal equivalence.
Because our induction hypothesis states that this expression is equivalent to `length(append(tail, l2))`, we can _rewrite_ the former expression to the latter expression in our goal!
This fact allows us to finish the proof as follows:

~~~admonish info title="Proof (Completed, finally!)"
+   `l1` is non-empty.
    Our induction hypothesis states that:

    ```python
    `length(tail) + length(l2) ≡ length(append(tail, l2))`
    ```

    Since `l1` is non-empty, evaluation simplifies the goal equivalence to:

    ```python
    1 + length(tail) + length(l2) ≡ 1 + length(append(tail, l2))
    ```

    By our induction hypothesis, we can rewrite this goal to:

    ```python
    1 + length(append(tail, l2)) ≡ 1 + length(append(tail, l2))
    ```

    Which completes the proof.
~~~

We call a proof that uses an induction hypothesis a _proof by induction_ or _inductive proof_.
Like recursion in programming, inductive proofs are pervasive in mathematics.

In summary, here is a complete inductive proof of the `append` claim.
In this proof, we'll step directly from a call to `length` or `append` directly to the branch of the `match` that we would have selected.
We'll take this evaluation shortcut moving forward to avoid cluttering our proof.

Note in our proof that we declare that we "proceed by induction on `l1`" and then move into a case analysis.
This exemplifies how we should think of inductive proof moving forward:

~~~admonish info title="Inductive proof"
An inductive proof is a _case analysis_ over a _recursively-defined structure_ with the additional benefit of an _induction hypothesis_ to avoid infinite reasoning.
~~~

~~~admonish check title="Proof"
**Claim**: for all lists `l1` and `l2`, `length(l1) + length(l2)` ≡ `length(append(l1, l2))`.

_Proof_.
We proceed by induction on `l1`.

+   `l1` is empty, thus `l1` is `[]`.
    The left-hand side of the equivalence evaluates as follows:

    ```python
        length([]) + length(l2)
    --> 0 + length(l2)
    --> length(l2)
    ```

    On the right-hand side of the equivalence, we have:

    ```python
        length(append([], l2))
    --> length(l2)
    ```

+   `l1` is non-empty.
    Let `head` and `tail` be the head element and tail of `l1`, respectively.
    Our induction hypothesis is:

    **Inductive hypothesis**: `length(tail) + length(l2)` ≡ `length(append(tail, l2))`.

    On the left-hand side of the equivalence, we have:

    ```python
    --> length(l1) + length(l2)
    --> (1 + length(tail)) + length(l2)
      ≡ 1 + (length(tail) + length(l2))
    ```

    The final step comes from the commutative property of addition: $(1 + x) + y = 1 + (x + y)$.

    On the right-hand side of the equivalence, we have:

    ```scheme
    --> length(append(l1, l2))
    --> length(cons(head, append(tail, l2)))
    --> 1 + length(append(tail, l2))
    ```

    In summary, we now have:

    ```python
    1 + (length(tail) + length(l2)) ≡ 1 + length(append(tail, l2))
    ```

    We can use our induction hypothesis to rewrite the left-hand side of the equivalence to the right-hand side:

    ```python
    1 + length(append(tail, l2)) ≡ 1 + length(append(tail, l2))
    ```

    Completing the proof.
~~~

~~~admonish problem title="Exercise (Switcharoo, ‡)"
In our proof of the correctness of `append`, we performed induction on `l1`.
Could we have instead performed induction on `l2`?
Try it out!
And based on your findings, explain why or why not in a few sentences.
~~~

