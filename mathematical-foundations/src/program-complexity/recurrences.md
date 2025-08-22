# Recurrences

Loops are one way to obtain repetitive behavior in a program, and we represent them in mathematics with summations.
The other method we use to obtain repetitive behavior is _recursion_.
For recursion, we use _recurrence relations_, recursively-defined mathematical functions, to capture their complexity.

Let us return to the `factorial` function we defined earlier:

~~~racket
def factorial(n):
    if n = 0:
        return 1
    else:
        return n * factorial(n-1)
~~~

We've established that we wish to count the number of multiplications that `factorial` performs.
Intuitively, we know the answer to this question already: `factorial(n)` should perform $n$ multiplications, once for each number it multiplies from $n$ to $1$.
However, let's use this simple example as an opportunity to develop a technique for analyzing the complexity of recursive functions and, more generally, the size of any recursively defined object.

Ultimately, we want to define a function $f : \mathbb{N} \rightarrow \mathbb{N}$ where the input is the size of the input to `factorial`, _i.e._, the value of `n`, and the output is the number of multiplications that performs.
Because the function ultimately performs a case analysis on $n$, our function will also be conditionally defined based on $n$:

-   $n = 0$: the function immediately returns `1` and performs no multiplications.

-   $n > 0$: we can see that the function directly performs one multiplication, but it also makes a recursive call to `factorial(n-1)`.
    We can express this fact directly by recursively calling $f(n-1)$.

Our function $f$ is defined directly in terms of this case analysis:

$$
\begin{align*}
f(0) =&\; 0 \\
f(n) =&\; 1 + f(n-1)
\end{align*}
$$

Because $f$ is recursive, we call it a _recurrence relation_.
Recurrence relations arise naturally when talking about the complexity of recursive functions.

## Solving Recurrence Relations

To determine the complexity of a recursive function, we need to find an equivalent _closed-form equation_ for the recurrence relation.
The definition of "closed-form equation" varies based on context; we will interpret a "closed-form equation" as an equation that does not involve any recursion.
There are many methods for solving recurrences, _e.g._, characteristic equations, recursion trees, the master theorem, that require mathematics that are outside the scope of our course.
We instead present a simple _substitution-based technique_ to first _guess_ what the closed-form equation is for a simple recurrence relation and then _check_ that we were correct by using inductive proof.

## Guessing a Closed-Form Equation

To guess a closed-form equation for a recurrence, we repeatedly substitute for the recursive calls of our occurrence until we see a pattern.
From that pattern, we extrapolate a likely equation for the recurrence.
In the case of factorial, we may start with $f(k)$ for some arbitrary $k > 0$ and perform some substitutions to see what happens:

$$
\begin{align*}
f(k) =&\; 1 + f(k-1) \\
     =&\; 1 + (1 + f(k-2)) \\ =&\; 1 + (1 + (1 + f(k-3))) \\
     =&\; 1 + (1 + (1 + (1 + f(k-3)))) \\
     =&\; \cdots
\end{align*}
$$

We see that for every recursive call, we add one to the overall result.
This immediately suggests the following equation: $f(n) = n$.
Alternatively, if this leap wasn't clear, we might consider operating more symbolically to mechanically derive this result.
We observe that after $m$ expansions, we have:

$$
\begin{align*}
f(k) =&\; 1 + f(k-1) \\
     =&\; 1 + (1 + f(k-2)) \\
     =&\; \cdots \\
     =&\; m + f(k-m).
\end{align*}
$$

Now we ask: when do these recursive calls end?
These recursive call ends when $k - m = 0$ (the base case of the recurrence) which occurs when $k = m$.
Substituting back into the equation yields:

$$
\begin{align*}
f(k) =&\; m + f(k-m) \\
     =&\; k + f(k-k) \\
     =&\; k + f(0) \\
     =&\; k + 0 \\
     =&\; k
\end{align*}
$$

## Checking a Closed-Form Equation

Now that we have guessed a closed-form solution to our recurrence, we must now check it for correctness.
Since our recurrence is recursive in nature, it is not surprising that we must use induction to check that our recurrence is equivalent to our guessed closed-form equation.
To differentiate between these two equations, we'll use $f(n)$ to denote the recurrence and $g(n)$ to denote our guessed equation.

Now our claim and subsequent simply equates these two functions:

~~~admonish info title="Claim"
$\forall n \ldotp f(n) = g(n) = n$.
~~~

~~~admonish check title="Proof"
We proceed by induction $n$.

+   $n = 0$: $f(0) = 0$ by the definition of the recurrence and $g(n) = n$ so $g(0) = 0$.

+   $n > 0$:
    Our inductive hypothesis says that $f(n-1) = g(n-1) = n-1$ and we must show that $f(n) = g(n) = n$.
    By the definition of our recurrence $f(n) = 1 + f(n-1)$.
    We can substitute for $f(n-1)$ by our induction hypothesis, yielding $f(n) = 1 + n-1 = n$, completing the proof.
~~~

In summary, when we encounter recursively defined structures, we count them using recurrence relations we can solve for closed-form solutions through a combination of the substitution method and induction to guess and check, respectively.

~~~admonish problem title="Exercise (Once More, With Feeling, ‡)"
Repeat the analysis of `factorial` using recurrence relations but instead count the number of _comparisons_ the function makes.
Your should derive a new (but similar) recurrence, guess a closed-form equation, and then prove that closed-form equation correct with induction.
~~~