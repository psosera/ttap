# Random Variables and Expectation

In this reading, we see how we can use probabilities to compute the expected value arising from an experiment.
This simple _statistic_ is the entry point into the wider world of _statistical analysis_ where we look at common patterns of _probability distributions_ and their properties.
We won't have time in this course to explore statistics in detail, but we will talk about some basics here, so that you are aware of them for future study.

## Random Variables and Expectation

Recall how our fundamental probability definitions are set up:

+   The outcome of an experiment is drawn from the sample space $\Omega$.
+   An event $E \subseteq \Omega$ describes a particular collection of outcomes of interest.
+   For each event, we assign a probability to that event through the probability mass function $\Pr : \mathcal{P}(\Omega) → \mathbb{R}$ which obeys the three axioms of probability of theory.

With our probability mass function, we can state the likelihood of events occurring.
However, we can also use our probability mass function in conjunction with some other machinery to state the _weighted average_ of the possible outcomes of an experiment.
To do this, we first need to define a way to interpret the outcome of an experiment.
We do by way of a function (confusingly) called a _random variable_.

~~~admonish info title="Definition (Random Variable)"
A _random variable_ is a function $X : \Omega \rightarrow T$ for some output type $T$.
A random variable represents some _interpretation_ of the outcomes of some random process.
~~~

Consider our example of rolling three random dice, denoted by the set of outcomes:

$$
\Omega = \{\, (x, y, z) \mid x, y, z \in [1, \ldots, 6] \,\}.
$$

The sum of these dice forms a random variable, $X$:

$$
X(x, y, z) = x + y + z.
$$

The codomain of this random variable is the set of natural numbers in the range $[3, \ldots, 18]$.

Note that the codomain of a random variable need not be a number.
For example, if the sample space $\Omega$ is the set of valid rock-paper-scissor plays:

$$
\Sigma = \set{(p_1, p_2) \mid p_1, p_2 \in P }
$$

Where $P = \set{\text{rock}, \text{paper}, \text{scissors}}$.

Then the random variable $X_w$:

$$
X_w(p_1, p_2) = \begin{cases}
  \text{win} & \text{$p_1$ beats $p_2$} \\
  \text{lose} & \text{otherwise}
\end{cases}
$$

Has type $X_w : P \times P \rightarrow \set{\text{win}, \text{lose}}$.

~~~admonish problem title="Exercise (Random Variables)"
Let \Omega consist of the outcomes of flipping three coins.
Define a random variable $X$ that gives the _parity_ of the coins, _i.e._, the number of coins that turn up heads.
~~~

While the codomain of a random variable can be of any type, we most commonly work with real-valued random variables, _i.e._, $\mathbb{R}$.
Let $X$ be a random variable over a set of outcomes $\Omega$ of type $X : \Omega \rightarrow \mathbb{R}$.
Also suppose the existence of a probability function $pr : \Omega \rightarrow \mathbb{R}$ over these outcomes.
Then the _expected value_ of $X$, written $E[X]$, is defined to be the _weighted average_ of the outcomes and their respective probabilities:

$$
E[X] = \sum_{t \in \Omega} X(t) \cdot pr(t).
$$

~~~admonish check title="Example"
**Example**: consider an experiment where we have a weighed six-sided dice with outcomes $\Omega = \set{1, 2, 3, 4, 5, 6}$.
The probabilities of each outcome are:

$$
\begin{gather*}
f(1) = \frac{1}{20} \qquad f(2) = \frac{1}{6} \qquad f(3) = \frac{1}{6} \qquad \\
f(4) = \frac{1}{6} \qquad f(5) = \frac{1}{5} \qquad f(6) = \frac{1}{4}.
\end{gather*}
$$

Let $X : \Omega → \mathbb{R}$ be a random variable that represents the value of a particular dice roll.
Then the expectation of $X$ is the expected value of the weighted die:

$$
E[X] = 1 \cdot \frac{1}{20} + 2 \cdot \frac{1}{6} + 3 \cdot \frac{1}{6} + 4 \cdot \frac{1}{6} + 5 \cdot \frac{1}{5} + 6 \cdot \frac{1}{4} = 4.05.
$$

In contrast, if the probabilities of all the sides of the die were equally likely, then the expected value of the die would be:

$$
E[X] = \sum_{i=1}^{6} i \cdot \frac{1}{6} = 3.5.
$$
~~~

We can think of the expectation of a random variable to be the _weighted average_ of that variable where the weights are the probabilities of the various outcomes.

One consequence of the definition of expectation is that we can treat $E[-]$ as an operation on a (random) variable.
With this perspective, we can see that several algebraic properties hold of expectations.
The most important of these is the _linearity of expectation_:

~~~admonish info title="Claim (Linearity of Expectation)"
Let $X$ and $Y$ be real-valued random variables.
Then the following identities hold:

$$
\begin{gather*}
E[X + Y] = E[X] + E[Y] \\
E[aX] = aE[X]
\end{gather*}
$$

For some constant value $a$.
~~~

The linearity of expectation says that addition and multiplication (of a constant) distribute in a natural sense through expectation.
This fact allows us to manipulate and combine random variables as if they were plain old variables.

~~~admonish check title="Example"
let $\Omega$ be the set of all pairs of outcomes of two six-sided dice.
Let $X$ be a random variable defined as follows:

$$
X(k) = \begin{cases}
  1 & \text{$k$ is even} \\
  0 & \text{otherwise}.
\end{cases}
$$

And let $Y$ be a random variable that is defined to be the sum of the two dice values.

By the linearity of expectations, $E[X + Y] = E[X] + E[Y] = \frac{1}{2} + \frac{7}{2} = 4$ is the _sum of averages_ of the two random variables.
Also by the linearity of expectations $E[2Y] = 2E[Y] = 2 \cdot \frac{7}{2} = 7$, the average of $Y$ scaled by a factor of two.
~~~

## Probability Distributions

Many experiments share similar _distributions_ of probabilities among its outcomes.
The study of _probability distributions_ and their properties is an important part of the mathematical subfield of _statistics_.
Here, we explore the basic concepts of probability distributions in light of our fundamental definitions of probability theory.

~~~admonish info title="Definition (Probability Distribution)"
Let $X : \Omega \rightarrow T$ be a random variable over a sample space $\Omega$ and interpretation $T$.
A _probability distribution_ is a function $\Pr(t) : T \rightarrow \mathbb{R}$ that describes the probabilities of the various interpretations of the elements of the sample space.
~~~

More informally, a probability distribution is a _description_ of how a probability function distributes probabilities among the possible outcomes of an experiment.
Many kinds of experiments fall into a handful of well known and understood probability distributions.

### Bernoulli Distributions

Let $X$ be a random variable with codomain $\set{0, 1}$.
Then a probability distribution $\Pr : \{\, 0, 1 \,\} \rightarrow \mathbb{R}$ over $X$ forms a _Bernoulli distribution_ with probability $p$ where:

$$
\begin{align*}
\Pr(1) &=\; p \\
\Pr(0) &=\; 1 - p
\end{align*}
$$

We call $p$ a _parameter_ of the probability distribution.
The Bernoulli distribution describe the outcome of a single experiment with a binary outcome---success or failure.
The use of $0$ and $1$ to _indicate_ boolean values is common in many areas of mathematics.

~~~admonish check title="Example"
**Example**: here are some applications of the Bernoulli distribution.

+   The probability of a single fair coin flip being heads forms a Bernoulli distribution with success $p = 0.5$.

+   Suppose you play a game where you roll two six-sided dice and you win if the sum of the die is greater than 8.
    Then the probability of winning the game forms a Bernoulli distribution with success $p = \frac{10}{36}$.
    (Note that there are 10 ways out of $6 × 6 = 36$ possibilities to get a higher than an 8 with two six-sided dice.)
~~~

Note how the Bernoulli distribution allows us to concisely describe the distribution of a set of probabilities.
Different distributions exist in statistics that capture a wide variety of possible probabilities and situations.

### Binominal Distributions

We can describe a particular probability distribution using a variety of _statistics_ which summarize salient characteristics of that distribution.
These include statistics you ought to be familiar with already, _e.g._,

+   The average (the expected value of a random value),
+   Median (the value that splits the probability distribution in half), and
+   Mode (the most frequent value).

Let $X : \Omega \rightarrow \mathbb{N}$ be a random variable that records the number of successes $k$ after running $n$ independent experiments.
Then a probability distribution $\Pr : \mathbb{N} \rightarrow \mathbb{R}$ over $X$ forms a _Binomial distribution_ with the probability of generating $k$ successes is given by:

$$
\Pr(k) = {n \choose k} p^{k} (1-p)^{n-k}
$$

Where $p$ is the probability of a single experiment generating a success.
As shorthand, we write $\mathcal{B}(n, p)$ for the binomial distribution consisting of $n$ independent experiments with probability of success $p$ for an individual experiment.

The probability function is derived combinatorially as follows:

-   The probability of getting $k$ successes is $\underbrace{p \cdot \cdots \cdot p}_{k} = p^k$.

-   The remaining experiments must be failures, and there are $n-k$ of them, so the probability of this is $\underbrace{(1-p)\cdot \cdots \cdot (1-p)}_{n-k} = (1-p)^{n-k}$.

-   Finally, any $k$-subset of the $n$ experiments may succeed, so there are ${n \choose k}$ such combinations where we have $k$ successes and $n-k$ failures.

This final point is why we alternatively call the "choose" operator ${n \choose k}$ the _binomial coefficient_.

The binomial distribution is a generalization of the Bernoulli distribution where we conduct $n$ such experiments rather than a single one.
As such, it is potentially relevant whenever we are discussing the outcomes of running repeated trials with a binary result.

~~~admonish check title="Example"
Suppose that we flip a biased coin with probability $p = 0.75$ heads and $1 - p = 0.25$ tails $20$ times with success defined as obtaining heads.
This forms a binomial distribution $\mathcal{B}(20, 0.75)$.
~~~

As a consequence of identifying a probability distribution is binomial, we can apply known formulae to quickly derive statistics for that distribution.

Let $\mathcal{B}(n, p)$ be a binomial distribution. Then:

+   The _mean_ or _expected value_ of the distribution is $np$.

+   The _median_ of the distribution is either $⌊np⌋$ or $⌈np⌉$.
    $⌊-⌋$ is the unary _flooring_ function which rounds its argument down.
    In contrast, $⌈-⌉$ is the unary _ceiling_ function which rounds its argument up.

+   The _mode_ of the distribution is either $⌊(n+1)p⌋$ or $⌈(n+1)p⌉ - 1$.

~~~admonish check title="Example"
**Example**: For our biased coin example above, the expected number of heads is $20 \cdot 0.75 = 15$.
~~~

~~~admonish problem title="Exercise (More Gambling, ‡)"
Let's expand on the simple gambling game from the previous readings' exercise.

> Suppose that to play the game, you need to put in \$1.
> Roll three six-sided dice in sequence.
> Say that a die _wins_ if it is a five or a six.
>
> +   If the first die wins you get \$1.
> +   If the first and second die win you get \$2.
> +   If the first, second, and third die win you get \$4.
> +   In all other cases, you get nothing.

a.  Write down the sample space $\Omega$ of possible outcomes and a set of four disjoint events $E_1, \ldots, E_4$ that describe the outcomes above.
b.  Write down the definition of a random variable $X$ that describes the amount of money you may win from a single play of the game.
c.  Calculate the expected value $E[X]$ of the random variable you defined above.
    Based on the computed value of $E[X]$, is it worthwhile to play this game?
~~~
