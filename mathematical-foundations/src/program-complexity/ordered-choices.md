# Ordered and Unordered Choice

So far, we have developed a number of counting principles based on our fundamental set operations.
We will now use those principles to development two of the most foundational counting principles in combinatorics: _permutations_ and _combinations_.

## Ordered Choices with Replacement

Consider a set of 21 pre-school students.
Every week, one student is randomly chosen to be the teacher's assistant.
How many different _sequences_ of assistants can we have over a 4-week period?

We can model this situation using a 4-tuple.
If $T$ is the set of students, then each element of the 4-tuple is a member of this set.
The 4-tuple, therefore, has the following type:

$$
(⋅, ⋅, ⋅, ⋅) : T \times T \times T \times T
$$

The product rule tells us that the number of such tuples is:

$$
|T| \times |T| \times |T| = 21 \times 21 \times 21 \times 21.
$$

Recall that this unsimplified expression, a _combinatorial description_, is preferred over its final value: $21^4 = 194481$.
While this value is useful for understanding the magnitude of the number of possibilities, it does not tell us _how_ this expression was derived.

Alternatively, we can view this problem as making choices from a set _with replacement_.
We choose an element from the set $T$---there are 21 such choices---place that element back in the set and then make another choice from the same set.
Since we do this process four times, there must be $21 \times 21 \times 21 \times 21$ such choices.

~~~admonish problem title="Exercise (Choices, Part 1)"
Give a combinatorial description for the number of sequences (_i.e._, order is relevant) of size $6$ you can generate from the set of lowercase letters $S = \set{a, \ldots, z}$.
~~~

## Ordered Choices without Replacement

You might have felt that the above arrangement of assistants to be unfair.
For example, a possible set of choices we can make is:

$$
(\text{Roy}, \text{Roy}, \text{Roy}, \text{Roy}).
$$

Once Roy is teacher's assistant, we might want to avoid choosing Roy in the future.
How can we account for this?
When making successive choices, we can simply _not replace_ Roy in the set of students.
Of course, we should not single out Roy.
We should do this for every student that we pick!

If we were to form the same set of sequence of four teaching assistants from the set $T$ of 21 students using this strategy of _no replacement_, we would have:

+   $21$ choices for the first assistant.
+   $21 - 1 = 20$ choices for the second assistant.
+   $21 - 2 = 19$ choices for the third assistant.
+   $21 - 3 = 18$ choices for the fourth assistant.

In summary, the total number of possible sequences of four teaching assistants without replacement is:

$$
21 \times 20 \times 19 \times 18.
$$

Intuitively, the first choice is made with the entire set $T$.
The second choice is with $T - \set{x}$ where $x$ is the first choice.
The third choice is made with $T - \set{x, y}$ where $y$ is the second choice.
The final choice is made with $T - \set{x, y, z}$ where $z$ is the third choice.

In general, if we are creating a sequence of size $k$ from $n$ elements without replacement, there are:

$$
n \times (n - 1) \times \cdots \times (n - k + 1).
$$

This quantity is so common in counting that we give it a name, the _falling factorial_.

~~~admonish info title="Definition (Falling Factorial)"
We define:

$$
(n)_k = n \times (n - 1) \times \cdots \times (n - k + 1).
$$

$(n)_k$, pronounced "$n$ to the falling $k$" is the _falling factorial_ of $n$ down to $k$.
~~~

In terms of our counting principles, $(n)_k$ is the number of sequences of size $k$ from $n$ elements without replacement.
Use this falling factorial notation in your combinatorial descriptions whenever you want to capture this quantity.

~~~admonish problem title="Exercise (Check It)"
Why is the final term in the definition of $(n)_k$ $(n - k + 1)$?
What would happen if we removed "$+ 1$" from this term?
Is the resulting formula equivalent to the original?
~~~

## Permutations

Continuing with our teaching assistant example, it is clear that if there are 21 students in the class (represented by the set $T$) that we will have 21 choices to make.
How many such sequences can we make where we eventually choose every student in the class to be an assistant?
We can specify this using the falling factorial:

$$
(|T|)_{|T|} = |T| \times (|T|-1) \times \cdots \times (|T|-|T|+1) = |T| \times \cdots \times 1.
$$

The right-hand side of this quantity is simply our standard factorial function!
So we can view $(n)_n = n!$ to be the number of sequences of size $n$ made from a set of $n$ elements without replacement.

However, there is another way we can view this quantity.
We can, instead, consider choosing an arbitrary ordering of the students, a _permutation_.
Reading off this ordering from left-to-right gives us the desired sequence since each element has a unique position in the ordering.
We can think of $n!$ as the number of _permutations_ of a $n$-element set.

Permutations of a collection of objects arise in a variety of circumstances.
For example, consider a list of $n$ elements.
Can we efficiently sort a list by randomly generating a permutation until we arrive at a sorted one?
This seemingly silly sorting algorithm has a name, _Bogo Sort_.

There are $n!$ possible permutations of a list.
How many of these permutations are sorted?
For simplicity's sake, let's assume that all the elements of the list are distinct.
How many choices are there for each of the $n$ slots of the permutation?

+   There is only one choice for the first slot of the sorted permutation, the smallest element of the list.
+   There is only one choice for the second slot of the sorted permutation, the second smallest element of the list.

And so forth, for every slot of the permutation.
In other words, there is a single permutation that is sorted for any list!

So Bogo Sort must randomly generate one permutation out of the $n!$ possible permutations for the list of $n$ elements.
How likely is this?
We are aware that $n!$ grows very quickly as $n$ grows:

+   $0! = 1$.
+   $3! = 6$.
+   $5! = 120$.
+   $10! = 3628800$.

According to _Stirling's approximation_:

$$
k! \approx \sqrt{2 \pi k}(\frac{k}{e})^k
$$

So factorial grows exponentially in $k$.
This means that for a list of any reasonable size, Bogo Sort is very unlikely to ever produce a correct answer!

Generally speaking, if an algorithm demands that we brute-force analyze the various permutations of a collection of data, that algorithm is likely computationally infeasible in practice on non-trivial inputs.
If we recognize that we are in this situation, we ought to redesign our algorithm to rule out some of these possibilities and thus regain tractability.

## Overcounting

In some cases, we will find it difficult to arrive at a direct combinational description of a quantity.
It is sometimes easier instead to _over-count_ the quantity and _remove_ out redundant amounts.
We saw this with the Principle of Inclusion-Exclusion where we subtracted out over-counted elements.
However, we can generalize this technique to any situation, not just ones involving unions of sets.

As an example, consider the following situation.
You are trying to count the number of sheep at a farm.
However, there is wall in the way that only allows you to see the sheep's feet.
Can you use this information to count the number of sheep?
Assuming that all the sheep has four legs, you know that you can count the number of legs and divide by four to get the total number of sheep.

In effect, counting sheep feet _over-counts_ the true number of sheep.
However, we know that we're over-counting by a _factor_ of 4---each sheep has 4 feet---so we can _remove_ this factor by division.

As a second example of employing over-counting, consider counting the number of possible triangles formed by three distinct points, $A$, $B$, and $C$.
What is different about this problem relative to counting permutations is that certain permutations are considered _equal_ to each other.
For example, the triangles:

~~~
   A        B        C
  / \      / \      / \
 /   \    /   \    /   \
C-----B  A-----C  B-----A
~~~

Are all really the same triangle because if we rotate the first triangle counterclockwise, we obtain the second triangle, and again for the third.

How do we count the number of such unique triangles?
One way to do this is to first consider the possible permutations of the three points $A$, $B$, and $C$:

$$
\begin{gather*}
ABC \quad BAC \quad CAB \\
ACB \quad BCA \quad CBA
\end{gather*}
$$

We can think of each permutation as reading the points of the triangle in some predefined starting point and order, _e.g._, the top-most point in a clockwise direction.
Note that in this light, we have two sets of triangles that are equivalent.

$$
\begin{gather*}
ABC = CAB = BCA \\
BAC = CBA = ACB
\end{gather*}
$$

Thus, even though there are $3! = 6$ permutations of the three points, we only have two different triangles according to this definition:

\begin{gather*}
ABC \quad BAC
\end{gather*}

Note that with three possible nodes, we will generate three equivalent triangles, corresponding to the three ways we can shift the sequence one element to the right: $ABC$, $CAB$, $BCA$. 
To account for this redundancy, we can divide out the three expected equivalent triangles from each unique triangle that we generate, yielding:

$$
\frac{3!}{3} = 2.
$$

To summarize, when we try to count a collection of objects, it is sometimes convenient to over-count and then remove the excess elements that do not meet our criteria.
In the above example, the excess elements are equivalent triangles, and we know that for every triangle that we care to count, three equivalent triangles are introduced. To remove this redundancy, we divide accordingly.

## Unordered Choices

With the technique of overcounting, we can now consider how to count the number of possible _subsets_ of size $k$ with elements drawn from a collection of $n$ elements.
In our counting terminology, sets are collections of objects where _their order is irrelevant_.
We call such a set a _combination_ drawn from the collection.

To derive a counting principle for combinations, we will first count the number of subsequences of size $k$ that we can create from $n$ elements.
We can then remove the repetitive subsequences that contain the same set of elements but in a different order.

To summarize, the quantity that we want can be described as:

$$
\frac{\text{\#/subsequences of size $k$ from $n$ elements}}{\text{\#/repeated subsequences of size $k$}}.
$$

The numerator is simply $(n)_k$.
Now, how many of these subsequences are redundant?
Note that there are $k!$ permutations of a $k$-sequence.
Of these permutations, only one is relevant because every other permutation is simply a re-ordering of the others!
Therefore, the number of repeated subsequences is $k!$.
This leads to the final formula:

$$
\frac{(n)_k}{k!} = \frac{n \times (n-1) \times \cdots \times (n-k+1)}{k!}.
$$


We frequently count the number of $k$-combinations drawn from a set of $n$ elements, so denote it with the following notation:

$$
{n \choose k} = \frac{(n)_k}{k!}.
$$

${n \choose k}$ is pronounced "$n$ choose $k$", which denotes the number of $k$ subsets drawn from $n$ elements.
This is also called the _binominal coefficient_, so-called because the formula also denotes the number of occurrences of $x^{n-k} y^k$ in the expansion of $(x+y)^{n}$.

## Summary

Here is a summary of the various counting principles we have discussed so far:

+   **The Sum Rule**: $|S_1 ∪ S_2| = |S_1| ∪ |S_2|$ whenever $S_1 ∩ S_2 = ∅$.
+   **The Product Rule**: $|S_1 \times S_2| = |S_1| \times |S_2|$.
+   **The Principle of Inclusion-Exclusion**: $|S_1 ∪ S_2| = |S_1| + |S_2| - |S_1 ∩ S_2|$.
+   **$k$-sequences from $n$ elements**: $(n)_k$.
+   **Permutations of $n$ elements**: $(n)_n = n!$.
+   **$k$-subsets from $n$ elements**: ${n \choose k}$.

~~~admonish problem title="Exercise (Choices, Part 2, ‡)"
In poker, you play with a deck of 52 unique cards.
Give combinatorial descriptions of the following quantities:

+   The number of ways that we can draw-and-replace sequences of 5 cards.
+   The number of five card hands (_N.B._, the arrangement of your hand is irrelevant).
+   The number of ways we can draw _three pairs_ of cards from the deck.

    (_Hint_: apply both your counting principles for sequences and combinations here.)
~~~
