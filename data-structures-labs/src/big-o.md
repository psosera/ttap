# Big-O

## Part 1: Function Growth

For each of the following pairs of functions, graph them below on the same axis.
Your graphs do not have to be precise, but they should accurately reflect relative scale and have proper x-intercepts. In the limit as the input of the functions goes to infinity, which of the two functions dominates the other, i.e., is $f \in \mathcal{O}(g)$ or $g \in \mathcal{O}(f)$?

1.  $f(x) = 500$, $g(x) = \frac{x}{10}$
2.  $f(x) = x^4$, $g(x) = x^2$
3.  $f(x) = \ln{x}$, $g(x) = 3^x$

## Part 2: Big-O Manipulation

Recall that the definition of Big-O is:

$$
f \in \mathcal{O}(g) \Leftrightarrow \exists c, x_0 \ldotp \forall x \geq x_0 \ldotp |f(x)| \leq |cg(x)|.
$$

That is, $f \in \mathcal{O}(g)$ if there exists ($\exists$) two constants $c$ and $x_0$ such that for all ($\forall$) $x$s greater than or equal to $x_0$, $|f(x)| \leq |cg(x)|$.
Or to put it another way, $g$ times a constant factor dominates $f$ after some initial value $x_0$.

For each of the following pairs of functions, determine if $f \in \mathcal{O}(g)$ or $g \in \mathcal{O}(f)$.
Give an appropriate $c$ and $x_0$ where one function dominates the other from $x_0$ onward.

1.  $f(n) = n + 5, g(n) = n^2$
2.  $f(n) = n!, g(n) = 1000$
3.  $f(n) = n^2, g(n) = \log{n}$

## Part 3: Function Analysis

For each of the methods below, go through the three-step process:

1.  Identify the relevant input(s) to the method.
2.  Identify the critical operation(s) that the method perform.
3.  Give a mathematical model (*i.e.*, a mathematical function) that relates the size of the input to the number of critical operations the method performs.

To develop a model of the program's time complexity.
Additionally, give a characterization of the mathematical model in terms of Big-O notation, i.e., what the mathematical function Big-O of?
Make sure to explicitly list the relevant inputs, critical operations, and the model itself.

~~~java
public static boolean boundedBy(int lo, int x, int hi) {
    return lo <= x && x <= hi;
}

public static long factorial(int n) {
    long result = 1;
    for (int i = n; i > 1; i--) {
        result = result * n;
    }
    return result;
}

public static void doubleEveryOther(int[] arr1) {
    for (int i = 0; i < arr.length; i += 2) {
        arr[i] = arr[i] * 2;
    }
}

public static int[] sumPairs(int[] arr1, int[] arr2) {
    if (arr1.length != arr2.length) { return null; }
    int[] ret = new int[arr1.length];
    for (int i = 0; i < arr1.length; i++) {
        ret[i] = arr1[i] + arr2[i];
    }
    return ret;
}

public static int[] allPairsProduct(int[] arr) {
    int[] ret = new int[arr.length * arr.length];
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length; j++) {
            ret[i * arr.length + j] = arr[i] * arr[j];
        }
    }
}
~~~

## Part 4: Loopy Analysis 

Here are even more functions to analyze!
For each function below, (a) count the exact number of array accesses each method performs as a function of the input array length $n$ and (b) give the tightest Big-O bound possible for that function.

~~~java
public static int sum1(int[] arr) {
    return arr[0] + arr[1];
}

public static int sum2(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
        sum = sum + arr[i] + arr[i];
    }
    return sum;
}

public static int sum3(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length; j++) {
            sum = sum + arr[i];
        }
    }
}

public static int sum4(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
        for (int j = i; j < arr.length; j++) {
            sum = sum + arr[i];
        }
    }
}
~~~