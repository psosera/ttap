# Variations of Inductive Proof

We have learned that operations and proofs over inductively-defined objects follow from their inductive definitions.
For example, recall the inductive definition of a list:

~~~admonish info title="Definition (List)"
A _list_ is either:

+ Empty, or
+ Non-empty, a combination of a _head_ element and the rest of the list, its _tail_.
~~~

But this isn't the only way of sorting the infinite possible lists into a finite set of cases.
For example, here is an alternative definition:

~~~admonish info title="Definition (List, Alternative)"
A _list_ is either:

+ Empty, or
+ A _singleton_ list containing exactly one element, or
+ Non-empty with at least _two elements_ and a _tail_.
~~~

This definition differs from the previous one because it explicitly calls out the singleton list case.
Both definitions are equivalent---every possible list is described by exactly one of the cases in each definition.
But in some situations, using an alternative inductive definition is appropriate to better fit the operation we are describing or reasoning about.

We'll look at an inductive proof that takes advantage of this alternative definition.
This is just one example of the _variations_ we might encounter when performing inductive proof.
There are many others out there, more than we can cover in the brief time we have in this course.
However, be aware that these variations exist, but they all ultimately rest on our basic definition of inductive reasoning:

~~~admonish info title="Definition (Induction)"
A _proof by induction_ is a proof that proceeds by case analysis on the inductive structure of an object where you may assume an _inductive hypothesis_ in the inductive cases of the proof.
~~~

## Case Study: Intersperse

Previously, we introduced the `intersperse` function takes an element `x` and a list `l` and returns a new list that puts `x` between each element of `l`.

~~~python
def intersperse(v, l):
    match l:
        case []:
            return []
        case [x]:
            return [x]
        case [h, *tail]:
            return [h, v, *intersperse(v, tail)]
~~~

~~~admonish question title="Exercise (Why Three?)"
It is not immediately obvious why `intersperse` requires that we have a separate case for the singleton list.
Try implementing `intersperse` naively with our standard, two-case definition of lists, _i.e._, the list is empty or non-empty.
Test your implementation and discover what bug arises with only two cases!
Try to then summarize in a few sentence why this bug occurs and how the singleton case in the above implementation fixes the problem.
~~~

Suppose we want to prove the following claim:

~~~admonish question title="Claim (Length of Intersperse)"
For all values `v` and lists `l`. If `not is_empty(l)` then `length(intersperse(v, l))` = `2 * length(l) - 1`.
~~~

Note that `intersperse` is defined according to our alternative, singleton-based definition of a list.
Our proof, therefore, follows the cases outlined by this definition.
If the cases include recursive sub-structure, *i.e.*, tails of lists drawn from the subject of the inductive proof, then we gain the induction hypotheses for all of these sub-structures.
These are our _inductive cases_ whereas cases with no recursive sub-structures are called _base cases_.

Here is a formal proof the claim as an exemplar of the concepts we've learned so faralong with some of these additional concerns we've introduced such as using an alternative definition of lists.
Study this proof carefully, paying attention to its structure and formatting.

~~~admonish check title="Proof"
_Proof_.
Let `v` be an arbitrary value and `l` be an arbitrary list.
We assume that `not is_empty(l)` *i.e.*, `l` is non-empty.
We proceed by induction on the structure of `l`.

+   **`l` is empty**.
    By assumption, we know that `l` is not empty.
    Thus, we do not need to consider this case.

+   **`l` has one element.**
    Call the singleton element of this list `x`.
    The left-hand side of the equality evaluates as follows:

    ```python
    length(intersperse(v, [x])) -->* length([x]) -->* 1
    ```

    And the right-side evaluates to:

    ```python
    2 * length([x]) - 1 -->* 2 * 1 - 1 -->* 1
    ```

+   **`l` has more than one element.**
    Let the first element be `h` and the remainder of the list be `tail`.
    We assume our inductive hypothesis holds:

    **IH**: If `not is_empty(tail)` then `length(intersperse(v, tail))` = `2 * length(tail) - 1`.

    We must prove:

    **Goal**: `length(intersperse(v, l))` = `2 * length(l) - 1`.

    The left-hand side of the equality evaluates to:

    ```python
         length(interserse(v, l))
    -->* length([h, v, *intersperse(v, tail)])
    -->* 2 + length(intersperse(v, tail))
    ```

    In this case, we know that `l` has at least two elements, so `not is_empty(tail)`.
    Therefore, our induction hypothesis applies and so we can rewrite this final quantity to `2 + 2 * length(tail) - 1`.

    Recalling that in this case, `l` has at least two elements, the right-hand side of the equality evaluates to:

    ```python
        2 * length(l) - 1
    --> 2 * (1 + length(tail)) - 1
      ≡ 2 + 2 * length(tail) - 1
    ```

    So both sides of the equality evaluate to `2 + 2 * length(tail) - 1`, completing the proof. 
~~~

Note that our claim is the form of a conditional: "if … then … ."
This means that our induction hypothesis must also be of this form!
This form of proposition is called a _logical implication_.
When _trying to prove_ an implication, we assume the "if" portion (the premise) and go on the prove the "then" portion (the conclusion).
However, if we _assume that an implication is true_, we must first prove the "if" portion and then we can go on to assume the "then" portion.
We will learn more about logical implication and, more generally, mathematical logic soon!

~~~admonish problem title="Exercise (Proof Busters, Intersperse Edition, ‡)"
Consider the following implementation of intersperse where we do not introduce a special case for a 1-element list:

```python
def intersperse(v, l):
    match l:
        case []:
            return []
        case [head, *tail]:
            return [head, v, *intersperse(v, tail)]
```

This implementation is not correct.
(If you don't see why, I encourage you to experiment with the function in the Python interpreter!)

Nevertheless, try applying the proof from the reading to these function.
How does the proof change?
The claim should not hold for this function---where does the proof go wrong in your analysis?
~~~