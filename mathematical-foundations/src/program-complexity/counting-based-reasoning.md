# Counting-based Reasoning

Recall in our previous readings how we derived the number of $k$-subsets drawn from $n$ elements, $n \choose k$:

> Generate an ordered sequence of size $k$ drawn from $n$ elements, $(n)_k$ such sequences in all.
> For every unique subset of $k$ elements, remove the $k!$ permutations of that subset that appear in the set of ordered sequences.
> What remains is a subset of size $k$ drawn from the $n$ elements.
> Therefore, ${n \choose k} = \frac{(n)_k}{k!}$.

This is not the only way we can derive $n \choose k$.
Consider this alternative derivation.

> Generate an ordered sequence of size $n$, $n!$ such sequences in all and select the first $k$ elements from the sequence to be a subset.
> Observe that once we have distinguished the first $k$ elements from the remaining $n-k$ elements of the sequence, that the first $k$ elements can be permuted in $k!$ ways and the remaining elements can be permuted in $(n-k)!$ ways.
> Therefore, for every such unique subset of $k$ elements, remove the $k!(n-k)!$ equivalent sequences that contain this subset.
> Thus, ${n \choose k} = \frac{n!}{k!(n-k)!}$.

Observe that from both derivations we can conclude that:

$$
{n \choose k} = \frac{(n)_k}{k!} = \frac{n!}{k!(n-k)!}
$$

In other words, if we can count a collection of objects in two different ways, those two different ways must be equal.
This is the principle of _double counting_ to establish the equivalence of arithmetic formulae.

As a second example, let's consider the following combinatorial identity:

$$
{n \choose k} = {n \choose n-k}.
$$

We could use arithmetic to demonstrate this identity.
However, let's use double counting instead which will immediately unveil _why_ the two quantities are true.
To do so, we must demonstrate that both formulae count the same object.
It is clear from the left-hand side that the quantity in question is likely the number of $k$-sized subsets drawn from $n$ elements.
The left-hand side is precisely this quantity, so we must argue that the right-hand also computes this quantity.

~~~admonish check title="Proof"
**Claim**: the number of $k$-sized subsets drawn from $n$ elements is $n \choose n-k$.

_Proof_.
Observe that $n \choose n-k$ is the number of possible subsets of size $n-k$.
Consider such a subset and note that $k$ elements _are not included_ in this subset.
The elements not included in the $n-k$ subset form the $k$-sized subset in question.
Finally, because we consider all such possible $n-k$-sized subsets, then we will also consider all $k$-sized subsets in this manner.
~~~

Because we demonstrated that the two formulae count the same collection---the number of $k$-subsets drawn from $n$ elements---we can conclude that they are equal.

Also note that this particular example is interesting because it demonstrates yet another counting technique: _implicit counting_.
Sometimes it is difficult to construct an object directly.
Instead, we can construct another object that _implies_ the desired object.
Here, we constructed $n-k$ subsets whose existence implied the $k$-sized subset we wanted.

~~~admonish problem title="Exercise (The One-two Punch, ‡)"
Use the double counting principle to justify _Pascal's Identity_:

$$
{n \choose k} = {n-1 \choose k-1} + {n-1 \choose k}
$$

(_Hint_: recall that summation means that you are making an "or"-style choice, _i.e._, breaking up the problem into two mutually-exclusive choices.
For this problem, think about distinguishing one of the $n$ elements of the set under consideration.
Work from the premise that your "or"-choice is whether this element is in the generated $k$-sized subset or not.
~~~
