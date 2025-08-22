# Frequentist Probability

_Uncertainty_ is a fundamental part of life.
For example, imagine interviewing for an internship position.
We ask ourselves, instinctually, _what are the chances that I will get the job?_
Perhaps you feel like your chances are higher that day because you slept and ate well that morning.
But maybe you know the company uses a programming language you aren't entirely comfortable with.
You weigh these factors and arrive at an intuition of the _likelihood_ that the interview is successful.
However, you know that even though you feel like your chances are high, you may still not get the job.

How do we model this uncertainty?
Up until this point, everything that we have done has been _deterministic_ in nature, _i.e._, there has only been a singular, definite outcome of an event, whether that it is evaluating a program or simplifying an arithmetic expression.
However, uncertainty introduces the need to consider multiple possible outcomes arising from a single event.
Can we precisely define what it means for one of these events to be more likely to occur?

_Probability theory_ models uncertainty by capturing our intuition that some events are more likely to occur than others.
In this chapter, we'll study probability theory as an application of counting.
If we can count of the number of occurrences of an event, we can assert a _probability value_ that describes the likelihood of that event.
As computer scientists, probability theory is particularly important for two reasons:

1.  Because uncertainty is a natural phenomenon, we need ways of capturing and reasoning about uncertainty to accurately model real-world objects.
2.  Uncertainty holds special potential for algorithmic design.
    Can we trade certainty like a resource to make our algorithms more efficient?

You likely have seen probability computations in your pre-collegiate math education.
Such a probability value of a particular event occurring is computed using the following formula:

$$
\frac{\text{Number of times that event occurs}}{\text{Total number of possible events}}.
$$

In this reading, we'll introduce the fundamental definitions of this _frequentist_ perspective on probability theory as well as some key concepts: expectation and conditional probabilities.
We'll only scratch the surface of probability theory in this course.
I highly recommend pursuing additional course work in this area, _e.g._, STA 209, because probability theory is becoming increasingly important for _all_ computer scientists to understand in a world where statistical and machine learning-based techniques are gaining prevalence.

## The Foundations of Frequentist Probability Theory

The probability of an event occurring can only be described in terms of other events occurring.
We thus define a _sample space_ that captures all the possible events under consideration.

~~~admonish info title="Definition (Sample Space)"
A _sample space_, $\Omega$, is the set of all possible outcomes of an experiment.
Such a sample space is considered _discrete_ if $\Omega$ has finite cardinality.
Otherwise, the sample space is considered _continuous_.
~~~

In this class, we focus exclusively on discrete probability.
It is in the title of the class, after all.

~~~admonish check title="Example"
The sample space of an experiment where we flip a pair of coins is denoted by:

$$
\Omega_{1} = \set{(H, H), (H, T), (T, H), (T, T)}.
$$

The sample space of an experiment where we roll three six-sided dice is denoted by:

$$
\Omega_{2} = \set{(x, y, z) \mid x, y, z \in [1, \ldots, 6]}.
$$
~~~

Note that the sample space captures precisely the set of possible outcomes.
Other outcomes, by definition, are not under consideration, _e.g._, the coins landing on their sides, unless they are included in $\Omega$.

Formally, an _event_, $E \subseteq \Omega$, describes the _outcome_ of a particular experiment.

~~~admonish check title="Example"
The event describing when we obtain at least one head in two coin flips is denoted by: 

$$
E_1 = \set{(H, H), (H, T), (T, H)}.
$$

The event describing when the sum of the three die we roll is exactly 4 is denoted by: 
$$
E_2 = \set{(x, y, z) \mid (x, y, z) \subseteq \Omega_{2}, x + y + z = 4}
$$
~~~

With an event formally defined, we can now define its likelihood, _i.e._, probability through a _probability mass function_.
A probability mass function is a function $f : \mathcal{P}(\Omega) → \mathbb{R}$ that obeys the following properties:

1.  $\forall E \in \mathcal{P}(\Omega).\,f(E) \geq 0$: the probability function produces non-negative probabilities.
2.  $P(\Omega) = 1$: the probability that _any_ event happens in the sample space is 1.
3.  If $E_1, \ldots, E_k$ are pairwise disjoint events, then:

    $$
    f(E_1 \cup \ldots \cup E_k) = \sum_{i = 1}^{k} f(E_i),
    $$

    The probability of the union of a collection of disjoint events is the sum of their individual probabilities, _i.e._, _the sum rule_ for probability theory.

We take the event as the domain of our probability function rather than a single outcome because we can always represent outcomes as singleton sets of events.

~~~admonish check title="Example"
If our coins are fair, then we expect that the probability of obtaining a heads or tails with a single coin to be equal.

$$
f(e) = \frac{1}{4}
$$

Now, suppose that we wish to know the probability of obtaining at least one head in two flips.
This is denoted by the event:

$$
E = \set{(H, H), (H, T), (T, H)}.
$$

And by the sum rule, the probability of this event is:

$$
\begin{align*}
f(E) =&\; f(\{\, (H, H) \,\}) + f(\{\, (H, T) \,\}) + f(\{\, (T, H) \,\}) \\
=&\; \frac{1}{4} + \frac{1}{4} + \frac{1}{4} = \frac{3}{4}.
\end{align*}
$$
~~~

~~~admonish problem title="Exercise (Gambling, ‡)"
Consider the following gambling game:

> Roll three six-side dice in sequence.
> Say that a die _wins_ if it is a five or a six.

1.  Write down the sample space $\Omega$ of possible outcomes.
2.  Assuming that the dice are all fair, what is the probability that you will win one occurrence of the gambling game?
~~~
