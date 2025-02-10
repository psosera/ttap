# Recursion and Recurrences

So far, we have analyzed the complexity of programs that contain loops by straightforward counting, e.g., if a loop executes $n$ times and performs $k$ operations on each iteration, the total number of operations performed is $k \times k$.
However, what about recursive programs?
How do we account for recursive calls within these programs?
To build mathematical models for these programs, we introduce _recurrence relations_, a particular sort of mathematical function defined in terms of itself, just like recursive programs!

## An Example: Factorial

Consider the standard recursive definition of factorial:

~~~java
public static long factorial(int n) {
    if (n == 0) {
        return 1;
    } else {
        return factorial(n - 1) * n;
    }
}
~~~

How do we analyze the complexity of this function?
First we must choose what operations we will track and the corresponding "input" to our model.
We compute factorial by "stripping" off one unit from the input, $n$, performing a multiplication in the process.
Therefore, we should analyze this function by counting multiplications; our input should be $n$ itself.

Note that our recursive definition of `factorial` is defined by a conditional on $n$ and in general, you should recognize this as an instance of the general recursive program _skeleton_:

~~~java
if (<base case condition) {
    // <base case>
} else {
    // <recursive case>
}
~~~

Our model will have similar structure.
We will define it in terms of the number of operations performed at the base case (when $n = 0$) and at the recursive case (when $n ≠ 0$):

$$
\begin{gather*}
T(0) = \ldots \\
T(n) = \ldots
\end{gather*}
$$

And now we need to give the number of operations that occur in each of these cases.
$T(0)$ is straightforward: in the base case, we perform no multiplications, so $T(0) = 0$.
However, what about $T(n)$?
We perform one multiplication and then perform a recursive call where the input is reduced by one.
Our definition for $T(n)$ reflects this exactly:

$$
T(n) = 1 + T(n - 1).
$$

Thus, our complete _recurrence relation_ that models `factorial` is

$$
\begin{gather*}
T(0) = 0 \\
T(n) = 1 + T(n - 1)
\end{gather*}
$$

## From Recurrences to Big-O

So far, we have used Big-O notation to "summarize" the behavior of our model as its input grows in size.
We cannot immediately use Big-O notation to summarize our recurrence relations because they are not in the form of one of our standard function classes.
We therefore need to either (a) _solve_ our recurrence relation and derive an explicit formula or (b) derive an _approximate_ formula for our relation.
Some relations are solvable (i.e., have a closed-form solution), but many others are not, and thus we have to resort to approximations.

It turns out that our recurrence above has an easy closed-form solution that we can derive using the _substitutive method_ in the follow manner:

1.  First, let's expand the recurrence a few steps to get a feel for the pattern of its behavior:

    $$ 
    T(n) = 1 + T(n - 1) = 1 + (1 + T(n - 2)) = 1 + (1 + 1 + T(n - 3))
    $$
   
2.  Next, let's try to rewrite $T(n)$ in terms of the number of expansions or _unfoldings_ that we perform of the function.
    Call this number $k$.
    
    $$
    T(n) = 1 + T(n - 1) = k + T(n - k)
    $$
    
    We arrive at this formula by noting that after $k$ unfoldings, we add $k$ 1s and make a recursive call to $T(n - k)$ in the final unfolding.

3.  Finally, we note that the base case of our recursion is when $n = 0$.
    So we consider the case where the recursive call in the formula above results in the base case.
    This is when $n = k$ so $T(n - k) = T(n - n) = T(0)$.
    Plugging in for $k$ gives us an explicit formula for the overall recurrence:

    $$
    T(n) = k + T(n - k) = n + T(0) = n + 0 = n.
    $$

Thus, the explicit formula for the number of multiplications that `factorial` performs in terms of its input $n$ is $T(n) = n$.
In terms of big-O notation, we say that $T \in \mathcal{O}(n)$---that is, `factorial` takes _linear_ time with respect to its input.
