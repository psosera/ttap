# Counting Operations

To analyze the correctness of a computer program, we reasoned about the flow of variable values throughout that program and verified logical
properties using this information.
In addition to correctness, another key property of computer programs we wish to understand is their _complexity_.
We can break up complexity into two components:
   
*   _Temporal Complexity_. How long does a program take to execute?
*   _Spacial Complexity_. How much memory does a program take to execute?
   
To get a handle on how to analyze the complexity of our programs, we'll build up an appropriate mathematical model of our programs that accounts
for complexity.
First, we'll study how to _identify_ and _count_ the important operations that our programs perform in terms of their inputs.
Then we'll learn how to _characterize_ how these amounts change as the size of our inputs grow.

## Real-world Measurement Versus Mathematical Models

To measure the amount of time that a program takes, we can simply use a clock and measure execution of a program from start to finish.
In practice, this is only true way get a sense of what the _real_ execution time of a program, independent of any mathematical models we develop about our program.
Such a mathematical model is necessarily _incomplete_, it cannot capture all the effects that go into the performance of a program, so it is an _approximation_ of the true run time of a program.
However, even though this is the case, mathematical models are usually preferable to work with than with the physical process of time itself.
This is due to a variety of reasons:

*   Computers are fast enough that virtually all algorithms on small inputs perform identically.
*   At the same time, developing large enough inputs that deviations appear in a program may be impractical for certain problems.
*   To see trends in particular programs, we must run performance tests over many sorts of inputs which can be time-consuming, especially if the programs take a long time to run.
*   The parts of a program that we want to analyze may not be amendable to real-world timing, e.g., because that portion of the code is deeply intertwined in the system and cannot be isolated.
*   Mathematical models can be platform-independent, or to put it another way, the actual run time of a particular program on a particular machine depends on that machine's configuration which may not be easily duplicable.

To this end, we develop and study mathematical models for understanding the complexity of our programs.
Even though our focus is on these models rather than real-world measurement, it is important to keep in mind that such models are not the end-all-be-all of understanding program performance.
These models are just one additional tool in your toolbox that you should be able to use when appropriate.

## Identifying Relevant Operations

Each of the operations that our programs perform costs time which contribute to the overall run time of the program.
Therefore, rather than measuring the actual time a program takes, we can count the number of operations the program performs.
However, different operations take different amounts of time, e.g., the cost of function call is (usually) more than accessing an element of an array.
Rather than trying to get a more precise characterization of the run time of a program in terms of all the operations it performs, we can _approximate_ its behavior by only counting the most "interesting" operations it performs.

For example, consider the following method:
~~~java
public static boolean contains(int[] arr, int k) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == k) {
            return true;
        }
    }
    return false;
}
~~~
This method performs a variety of potentially-interesting operations: variable initialization and assignments, comparisons of integers and returning values.
In contrast, the variable initializations (the parameters `arr` and `n` and the local `i`) are uninteresting because:
   
*   They don't directly deal with what the function is computing (whether `n` is contained in `arr`).
*   The number of times they occur does not vary with the size of `arr` and `n`.
   
In contrast, the array accesses (`arr[i] == n`) are interesting precisely because they are the heart of the `contains` operation, and the number of such operations depends on the size of the input array.
Consequently, reporting the number of variable initializations that `contains` performs is _less interesting_ than the number of array accesses because it doesn't give us an accurate sense of how the method behaves.
Thus, we would build our model of the method around the number of array accesses instead.

In summary, to build a model of the complexity of our program, we must identify which operations we wish to count as relevant.
Such relevant operations (usually) directly perform the computation that is the intent of the program and they have dependence on the input in some way, e.g., the size of some input integer or array.

When there are multiple relevant operations, our choice of which operations to count is arbitrary.
Therefore, we choose the operation that makes our further calculations easier.
For example, consider the following method:
~~~java
public static int addAdjacentPairs(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length - 1; i++) {
        sum = sum + arr[i] + arr[i + 1];
    }
}
~~~
Two choices of relevant operations to count are:
   
*   The number of assignments to `sum`.
*   The number of array accesses.
   
Clearly there is a relationship between the two operations—for every assignment to `sum` there are two array accesses.
However, we ought to choose assignments for the simple reason that there are less of them to count.

## Counting Operations

Once we have identified which operations we wish to count, then we need to go about the business of counting them.
For example, consider the following method:
~~~java
public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];    // read
    arr[i] = arr[j]       // read, write
    arr[j] = temp;        // write
}
~~~
If we identify that we wish to count _array accesses_—both _reads_ and _writes_ to the input array—then this `swap` method performs 4 such operations, annotated in the comments above.
Note that because our programs execute in a sequential manner, we simply count up the number of operations that occur in each statement.
If we wanted to count the number of array operations of the following code snippet:
~~~java
int[] arr = new int[10];
// ...
swap(arr, 0, 1);    // 4 ops
swap(arr, 10, 20);  // 4 ops
~~~
It would be the sum of the two `swap` calls—8 array operations overall.

Note that these counts so far have not depended on the size of the array.
In contrast, consider the following method:
~~~java
public static int sum(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
        sum += arr[i];
    }
    return sum;
}
~~~
The number of array operations `sum` performs is the number of times the for-loop runs times the number of array operations performed in each iteration.
One array access is performed during each iteration and we iterate through the loop `arr.length` number of times.
Therefore, the `sum` method performs $n$ operations where $n$ is `arr.length`.

We express the number of operations formally as a _mathematical function_ $T(n)$ where $n$ is the size of the input that we have identified in our method.
Note that the name of the function $T$ and the input $n$ are arbitrary.
We could name them anything, e.g., $f(x)$ is a more traditional notation for an arbitrary function.
Throughout, we'll use the convention of $T$ to represent a time complexity function, $S$ to be a space complexity function, and the input of these functions to be $n$.

This function serves as our _model_ of our method's behavior.
For example, we may describe the `sum` method as performing $T_1(n) = n$ operations where $n$ is the size of the input array.
Analogously, the function describing the run time of the `swap` function is $T_2(n) = 4$ where $n$ is the size of the input array.
Note that while `swap` has three parameters—`arr`, `i`, and `j`—we only identify the length of `arr` as the input to our model.
Like choosing which operations to count, we must also ensure that we choose appropriate inputs to our mathematical function so that our model is accurate.

### Counting Operations in Loops

Because a loop, by design, performs repetitive behavior, we can derive a formula for the number of operations any loop performs:

$$
T = I \times L
$$

Where $T$ is the total number of operations, $I$ is the number of operations and $L$ is the number of operations performed in the loop.

This is an instance of the _product rule_ from a field of a mathematics called _combinatorics_, the study of counting.
We saw how to apply this with the `sum` function:

$$
\begin{align*}
  I &= n \\
  L &= 1 \\
  T &= n \times 1 = n
\end{align*}
$$

Where $n$ is the size of the input array.
This formula generalizes to loops with irregular bounds and odd numbers of operations per loop, for example:

~~~java
for (int i = arr.length - 1; i >= 2; i -= 3) {
    arr[i] = arr[i-1] - arr[i-2];
}
~~~

Here, the total number of iterations is difficult to see at first glance because the termination condition and decrement step are non-standard.
If we write a few examples down:

*   $n = 0$, $\textsf{no. of iterations} = 0$
*   $n = 1$, $\textsf{no. of iterations} = 0$
*   $n = 2$, $\textsf{no. of iterations} = 1$
*   $n = 3$, $\textsf{no. of iterations} = 1$
*   $n = 4$, $\textsf{no. of iterations} = 1$
*   $n = 5$, $\textsf{no. of iterations} = 2$
*   $n = 6$, $\textsf{no. of iterations} = 2$
*   $n = 7$, $\textsf{no. of iterations} = 2$
*   $n = 8$, $\textsf{no. of iterations} = 3$

We can derive an explicit formula for the number of iterations: $\lfloor \frac{n + 1}{3} \rfloor$ where $\lfloor - \rfloor$ is the _floor function_ which rounds its argument down to the next whole number.
Thus, our formula for the total number of operations is $\lfloor \frac{n + 1}{3} \rfloor \times 3$ because the loop performs three array accesses—two reads and one write—with each iteration.

The formula also works for nested loops where we simply calculate the total number of iterations of the inner-most loop and work outwards from there.
For example, consider the following doubly-nested for-loop:

~~~java
int sum = 0;
for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr.length; j++) {
        sum = sum + i + j;
    }
}
~~~

We know that the inner for-loop performs $3n$ additions (two in the update of `sum` and one to increment ${j}$) where $n$ is the length of the input array.
Thus, we know that the outer for-loop performs $(3n + 1) \times n = 3n^2 + n$ such operations by accounting for the operations done by the inner loop and the increment of `i`.

The same technique applies when the bounds of the loops are different, for example, the slightly modified example:

~~~java
int sum = 0;
for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < i; j++) {
        sum = sum + i + j;
    }
}
~~~

Now the inner loop's range depends on the current value of the outer loop variable.
Again, going through examples to get a sense of the pattern of iteration:
  
*   $i = 0$, $\textsf{No. Iterations of Inner Loop} = 0$
*   $i = 1$, $\textsf{No. Iterations of Inner Loop} = 1$
*   $i = 2$, $\textsf{No. Iterations of Inner Loop} = 2$
*   $i = 3$, $\textsf{No. Iterations of Inner Loop} = 3$

Thus, if the length of the array is $n$, the inner loop will perform $1 + 2 + \cdots + n$ iterations.
We can use summation notation to concisely write down this pattern:

$$
1 + 2 + \cdots + n = \sum_{i = 1}^{n} i
$$

Euler gives us an explicit formula for this summation:

$$
\sum_{i = 1}^{n} i = \frac{n(n+1)}{2}
$$

Thus, the total number of additions performed by this loop is

$$
3 \cdot \frac{n(n+1)}{2} + n
$$

Where every iteration of the inner loop produces three additions and there are $\frac{n(n+1)}{2}$ such iterations.
The additional $n$ comes from the $n$ increments of the outer loop variable.

Indeed, with arbitrary nestings of loops and bounds, we'll sometimes derive complicated nestings of summations.
For example, we can express the number of array operations of the following triply-nested loop:

~~~java
for (int i = 0; i < arr.length; i++) {
    for (int j = i / 2; j < arr.length - 1; j++) {
        for (int k = arr.length - 1; k > j; k--) {
            // 1 array operation performed
        }
    }
}
~~~

With a straightforward translation of loop bounds to summation bounds:

$$
\sum_{i = 0}^{n - 1} \sum_{j = \frac{i}{2}}^{n - 2} \sum_{k = n - 1}^{k > j} 1
$$

Note how the bounds of the for-loops translates directly into the bounds of the summations.
The only complication to consider is that our for-loop bounds are typically exclusive on the upper bound whereas a summation is inclusive.
In this particular case, we set up our summations to match the values that the iteration variables of the loops take, for example, $0$ to $n-1$ in the case of the outermost for-loop.
In general, though, we may find it more convenient to choose different, yet equivalent range of numbers, for example, $1$ to $n$ in order to match a summation identity that we know of.

Frequently, this results in summations that are not trivial to simplify without summation identities and algebraic manipulation.
Luckily, for the purposes of asymptotic analysis which we discuss shortly later, we don't need to be so precise with our counting because we are concerned more about the _trend_ in the number of operations performed as the input size increases rather than the exact amount.

Eventually, we will reach the point where we can apply _informal reasoning_ to our analysis and note that there are simply three nested for-loops with linear-like behavior and conclude that the number of operations behaves like $T(n) ≈ n^3$.
However, it is important to understand the fundamental mathematics involved in this analysis so that you can understand its corresponding strengths and weaknesses.

## Cases of Execution

With this machinery in place, let's revisit our `contains` function:

~~~java
public static boolean contains(int[] arr, int k) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == k) {
            return true;
        }
    }
    return false;
}
~~~

What is our model $T(n)$ of the time complexity of this function?
Again, we choose to model the complexity by counting the number of array accesses of `arr` so $n$ is the size of the input array.
However, the analysis here is slightly trickier than `swap` and `sum`.
This is because the number of array accesses depends not just on $n$ but ultimately _where the contained element is in the array_, if it is in the array at all.
How can we reconcile this problem?

To do this, we need to perform _case analysis_ on the execution of the function.
More specifically, we'll define three interesting cases—the _best case_, the _worst case_, and the _average case_—make assumptions about the input based on these cases, and then proceed to build our model.

The _best case_ assumes that our input is set up in a way that leads to the fastest execution of the program possible.
In the case of `contains`, this occurs whenever the requested element `k` is at the front of the array.
In this situation, our best-case model for the function is $T(n) = 1$ because our for-loop only runs for one iteration.

The _worst case_ assumes that our input is set up in the worst way possible, leading the slowest execution of the program.
With `contains`, this occurs when the requested element `k` is either the last element in the array or is not in the array at all.
In either case case, the for-loop runs $n$ times (where $n$ is the length of the input array), resulting in the model: $T(n) = n$.

The _average case_ assumes something between the best and worst case.
On average, what does the input look like and thus what sort of performance can we usually expect from the function?
For `contains`, if our element has equal probability of being in any position in the array, its expected index is $\frac{n}{2}$.
In this situation, the for-loop runs $\frac{n}{2}$ times resulting in the model: $T(n) = \frac{n}{2}$.

The case that we want to use depends on the circumstances and the questions that we are asking about our program.
Furthermore, analyzing the cases may be trivial in some cases and non-trivial in others.
For example, while the average case may be desirable in most circumstances, it may be difficult to assess the "average" case for a particular problem.
In these situations, we may have to resort to using the best and worst cases to put a _lower_ and _upper_ bound on the performance of our program.

```admonish question title="Counting Operations (‡)"
Consider the following method:
~~~java
public static int max(int[] arr) {
    if (arr.length == 0) {
        throw new IllegalArgumentException();   // ignore this case
    } else {
        int ret = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (ret < arr[i]) {
                ret = arr[i];
            }
        }
        return ret;
    }
}
~~~
Build three mathematical models of the `max` method describing its best, worst, and average case performance.
Identify the relevant inputs to the method, operations to count, and give functions that describes the number of operations the method performs in terms of the inputs that you identify.
```
