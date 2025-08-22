# Proof by Contradiction

So far, we have looked exclusively at _proofs by construction_ where our goal is to construct an object, _e.g._, an evaluation trace that provides evidence that a proposition holds.
However, in some cases, it is not feasible to construct such evidence directly.
This is particularly true when we to prove that an object _does not_ obey some property of interest.
Indeed, as we've seen from our study of mathematical logic, reasoning about the _negation_ of a property can be subtly tricky!

In these situations, we can employ a different proof technique, _proof by contradiction_, to show the proposition of interest.
Proving a proposition $P$ by contradiction proceeds as follows:

+   For the sake of contradiction, assume that $\neg P$, _i.e._, the negation of the goal proposition, is true.
+   From this assumption, derive additional facts until we can exhibit a _logical contradiction_.
+   If we are able to derive a contradiction, we know that it must be because we assumed $\neg P$ holds.
    Thus, it must be the case that $\neg P$ _does not_ hold and therefore $P$ holds instead.

Note that in terms of our formal natural deduction rules, this final step invokes the law of the excluded middle:

~~~admonish info title="Definition (Law of the Excluded Middle)"
For any proposition $P$, exactly one of $P$ or $\neg P$ is provable.
~~~

We saw this briefly when we reasoned about set inclusion proofs involving the empty set.
However, reasoning via contradiction is pervasive throughout mathematics.
A classical example of contradiction proof is showing that $\sqrt{2}$ is _irrational_.
Recall that the definition of a _rational_ number.

~~~admonish info title="Definition (Rational Number)"
A number $n$ is considered _rational_ whenever there exists numbers $a$ and $b$ such that $n = \frac{a}{b}$.
~~~

In other words, a number is rational if it can be expressed as a fraction.

In contrast, an irrational number is precisely a number that is _not_ rational!
By "negating" the definition of rational number, we see that we must show that there is _no way_ to decompose the number into a fraction.
But that means that we have to show that _every_ possible fractional decomposition does not work, a much harder task than exhibiting a _single_ fractional decomposition!

Instead of this route, we can use a _proof by contradiction_.
We will assume that $\sqrt{2}$ is irrational and then follow our nose until we arrive at a contradiction.

Take the time to read and scrutinize this classic proof!
It is especially important to understand the details of a proof by contradiction because one misstep or unstated assumption can lead to a contradiction that may not be real.

~~~admonish check title="Proof (Irrationality of square root of two)"
**Claim**: $\sqrt{2}$ is irrational.

_Proof_.
Assume for the sake of contradiction that $\sqrt{2}$ is rational.
By the definition of rational, there exists $a$ and $b$ such that $\sqrt{2} = \frac{a}{b}$.
Furthermore, assume that $\frac{a}{b}$ is simplified, _i.e._, $a$ and $b$ have no common factors.
Now consider the following algebraic manipulation:

$$
\begin{align*}
\sqrt{2} =&\; \frac{a}{b} \\
b \sqrt{2} =&\; a \\
(b \sqrt{2})^2 =&\; a^2 \\
2b^2 =&\; a^2
\end{align*}
$$

Observe that $a^2$ must be even because it is divisible by 2 (precisely because it is equal to $2 b^2$).
Because the square of an even number is also even, $a$ must be even as well.
Therefore, $a = 2m$ for some integer $m$.
Substituting back for $a$ yields:

$$
\begin{align*}
2b^2 =&\; (2m)^2 \\
2b^2 =&\; 4m^2 \\
b^2 =&\; 2m^2
\end{align*}
$$

Now observe that $b^2$ must be even as well because it is divisible by 2 as well and thus $b$ is even.
However, we have now established that _both_ $a$ and $b$ are even.
This is a contradiction because we assumed they had no factors in common, but, in fact, they do---the common factor is 2.

Thus, our original assumption that $\sqrt{2}$ is rational is incorrect; $\sqrt{2}$ must be irrational, instead.
~~~

## Reading Exercises

### Check 1: Empty Set Inclusion (‡)

Prove the following set equality using a proof by contradiction:

~~~admonish problem title="Claim"
For any sets $S$ and $T$, $S - (T \cup S) = \emptyset$.
~~~
