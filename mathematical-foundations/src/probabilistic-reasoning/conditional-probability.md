# Conditional Probability

So far, we've considered probability computations in the absence of additional information.
However, how does knowledge of one event _influence_ the probability of another event occurring?
By modeling this phenomena with _conditional probabilities_, we can begin to model the notion of _learning_ where the discovery of new information influences our current knowledge.
This is the basis of modern _machine learning_ techniques that are so prevalent in modern-day computing.

~~~admonish info title="Definition (Conditional Probability)"
The conditional probability of an event $A$ given that an event $B$ has occurred, written $\Pr(A \mid B)$ is:

$$
\Pr(A \mid B) = \frac{\Pr(A ∩ B)}{\Pr(B)}.
$$
~~~

We can pronounce $\Pr(A \mid B)$ as the probability of event $A$ occurring _given_ that $B$ has occurred.
This is a sort of implication, but for probabilities.

For example, consider the random value $X$ representing the sum of rolling two six-sided dice.
Here are all the possible outcomes of $X$:

+   $X = 2$, 1 possibility: $1+1$.
+   $X = 3$, 2 possibilities: $1+2$, $2+1$.
+   $X = 4$, 3 possibilities: $1+3$, $3+1$, $2+2$.
+   $X = 5$, 4 possibilities: $1+4$, $4+1$, $2+3$, $3+2$.
+   $X = 6$, 5 possibilities: $1+5$, $5+1$, $2+4$, $4+2$, $3+3$.
+   $X = 7$, 6 possibilities: $1+6$, $6+1$, $2+5$, $5+2$, $3+4$, $4+3$.
+   $X = 8$, 5 possibilities: $2+6$, $6+2$, $3+5$, $5+3$, $4+4$.
+   $X = 9$, 4 possibilities: $3+6$, $6+3$, $4+5$, $5+4$.
+   $X = 10$, 3 possibilities: $4+6$, $6+4$, $5+5$.
+   $X = 11$, 2 possibilities: $5+6$, $6+5$.
+   $X = 12$, 1 possibility: $6+6$.

The probability of $X = 8$ is $\Pr(8) = \frac{5}{36}$.
However, what if we know that the first die is a $3$?
Then, we only consider the dice rolls where the first die $x$ is a $3$:

$$
3+1, 3+2, 3+3, 3+4, 3+5, 3+6
$$

Of these six possibilities, only one results in a sum of $8$, so we have that $\Pr(8 \mid x = 3) = \frac{1}{6}$.
Alternatively, we can calculate this directly using the definition of conditional probability:

$$
\Pr(8 ∣ x = 3) = \frac{\Pr(8 ∩ x = 3)}{\Pr(x = 3)} = \frac{ \frac{1}{36} }{ \frac{6}{36} } = \frac{1}{6}.
$$

## Independence

In the example above, knowing that the first dice is a 3 influences the probability that their sum is 8.
We say that the two events---"the first dice is a 3" and "the sum of the two dice is 8"---are dependent on each other.
However, we have an intuition that some events are _independent_ of each other.
For example, consider the two events:

+   $E_1$ = "The first dice is a two."
+   $E_2$ = "The second dice is even."

$Pr(E_1) = \frac{6}{36}$ since there are six possibilities for the second dice when the first is fixed to two.
$Pr(E_2) = \frac{3 ⋅ 6}{36}$ since there are 3 possibilities for the second dice to be even and then 6 possibilities for the first dice once the second has been fixed.
However, since we believe that $E_1$ is independent of $E_2$ that $\Pr(E_1 \mid E_2) = \Pr(E_1)$, _i.e._, knowledge of $E_2$ does not change the probability of $E_1$.

We formalize the notion of independence in probability theory as follows:

~~~admonish info title="Definition (Independence)"
We say that two events $E_1, E_2 ⊆ Ω$ are independent if $\Pr(E_1 ∩ E_2) = \Pr(E_1) \cdot \Pr(E_2)$.
~~~

That is, independence is the condition necessary for us to apply the combinatorial _product rule_ to probabilities.
If two events are independent, then we can reason about their probabilities in sequence.

~~~admonish check title="Proof"
**Claim**: If two events $E_1, E_2 ⊆ Ω$ are independent, then $Pr(E_1 \mid E_2) = E_1$.

_Proof_.
By the definition of conditional probability and independence:

$$
\Pr(E_1 \mid E_2) = \frac{\Pr(E_1 ∩ E_2)}{\Pr(E_2)} = \frac{\Pr(E_1) \Pr(E_2)}{Pr(E_2)} = \Pr(E_1).
$$

A similar argument holds for $\Pr(E_2)$ as well.
~~~

## Bayes' Theorem

Is the probability $\Pr(A \mid B)$ related to $\Pr(B \mid A)$ in any way?
We can use the definition of conditional probability to explore this idea:

$$
\begin{gather*}
\Pr(A \mid B) = \frac{\Pr(A ∩ B)}{\Pr(B)} \\
\Pr(B \mid A) = \frac{\Pr(B ∩ A)}{\Pr(A)}.
\end{gather*}
$$

But set intersection is symmetric, so we have that:

$$
\Pr(A ∩ B) = \Pr(A \mid B) \Pr(B) = \Pr(B \mid A) \Pr(A).
$$

But now we can remove $A ∩ B$ entirely from discussion and reason exclusively about conditional probabilities.
This insight leads us to _Bayes' Theorem_:

~~~admonish info title="Theorem (Bayes' Theorem)"
For any events $A, B ⊆ Ω$:

$$
\Pr(A \mid B) = \frac{\Pr(B \mid A) \Pr(A)}{\Pr(B)}.
$$
~~~

Bayes' Theorem allows us to talk concretely about our _updated belief of an event $A$ occurring_ given the _new knowledge_ that $B$ occurred.
A classical example of this concerns drug testing.
Suppose that we have a drug test that has the following characteristics:

+   The _true positivity rate_ of the drug test is 95%.
    This is the rate at which the drug test reports "yes" when the drug is actually present.
+   The _true negativity rate_ of the drug test is 90%.
    This is the rate at which the drug test reports "no" when the drug is not present.

Furthermore, suppose that we assume that 1% of people use this drug.
What is $\Pr(\text{user} \mid \text{pos})$ the probability that a person is a user of a drug given that they tested positive?
By Bayes' Theorem, this quantity is given by:

$$
\Pr(\text{user} \mid \text{pos}) = \frac{\Pr(\text{pos} \mid \text{user}) \Pr(\text{user})}{\Pr(pos)}.
$$

What are these various probabilities on the right-hand side of the equation?

+   $\Pr(\text{pos} \mid \text{user})$ is the probability of a test reporting positive when the person is actually a user.
    This is precisely the true positivity rate, 0.95 in our case.
+   $\Pr(\text{user})$ is the probability that a person is a user of the drug, assumed to be 1% in our example.
+   $\Pr(\text{pos})$ is the probability that a given test is positive.

We don't have immediate access to this last value.
However, we can _reconstruct_ it using the probabilities that we have!
We observe that the following equality holds:

$$
\Pr(\text{pos}) = \Pr(\text{pos} ∩ \text{user}) + \Pr(\text{pos} ∩ \text{non-user})
$$

Because every person is either a user or non-user of a drug.
We can then use the definition of conditional probability to rewrite the equation in terms of the conditional probabilities that we know:

$$
\begin{align*}
   &\; \Pr(\text{pos} ∩ \text{user}) + \Pr(\text{pos} ∩ \text{non-user}) \\
  =&\; \Pr(\text{pos} \mid \text{user}) \Pr(\text{user}) + \Pr(\text{pos} \mid \text{non-user}) \Pr(\text{non-user})
\end{align*}
$$

The probability $\Pr(\text{pos} \mid \text{non-user})$ is the _false negativity rate_, which is $1 - 0.90 = 0.10$.

Putting all of this together, we obtain:

$$
\begin{align*}
  \Pr(\text{user} \mid \text{pos}) =&\; \frac{\Pr(\text{pos} \mid \text{user}) \Pr(\text{user})}{\Pr(\text{pos} \mid \text{user}) \Pr(\text{user}) + \Pr(\text{pos} \mid \text{non-user}) \Pr(\text{non-user})} \\
=&\; \frac{0.95 ⋅ 0.01}{0.95 ⋅ 0.01 + 0.10 ⋅ 0.99} \\
=&\; 0.0876
\end{align*}
$$

In other words, the probability of a positive test given the person is a drug user is only 8.8%!
Since many more people are non-users than users, it is more important that our drug functions correctly in the _negative_ cases rather than the positive _cases_.
To see this, observe that the drug always reporting "yes" would result in more false claims than the situation where the drug always reports "no."
This is because, by default, there are many more "no" cases than there are "yes" cases.

~~~admonish problem title="Exercise (False Negativity, ‡)"
redo the drug test calculation two more times:

+ In the first, raise the true positivity rate to 100%.
+ In the second, raise the true negativity rate to 95%.

Which calculation produces the better probability for $\Pr(\text{pos} \mid \text{user})$?
~~~
