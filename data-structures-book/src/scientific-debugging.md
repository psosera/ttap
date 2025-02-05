# Scientific Debugging

We have a variety of tools to reason about programs:

+   Two kinds of mental models of computation—a _substitutive model_ and a _stack-heap model_—that allow us to simulate execution of a program.
+   A vocabulary of _preconditions_, _postconditions_, and _invariants_ that allow us to state properties of our program's behavior.

We can use these tools to verify the correctness of our programs either _before_ program execution or _after_ program execution.
In particular, let's focus first on how we might use these tools after program process, i.e., during the _debugging process_.
By doing so, we can systematize the debugging process so that we can rapidly find errors in our code.

## Hypothesis-driven Debugging

When debugging, it is easy to immediately dive into the code, especially with a debugger, and try to ferret out the problem.
However, by doing so in an ad hoc, unfocused manner, we might waste time looking at areas of our program that, with a bit of thought, we can rule out.
In the worst case, we might myopically focus on the wrong part of our code, blinding us to a more obvious, direct solution.

The debugging method we introduce in this reading, _hypothesis-driven debugging_, is inspired by the scientific method.
In the scientific method, you don't immediately jump into the lab.
You first make observations of the world and form a hypothesis that you wish to test.
Only at this point do you perform experiments, and you do so specifically to verify or refute your hypothesis.

Likewise, hypothesis-driven debugging has the following steps:

1.  Observe an error and gather data.
2.  State assumptions about how your program ought to be working.
3.  Predict what the root cause is.
4.  Use debugging tools to verify or refute your prediction.
5.  Analyze your results.

In this reading, we'll go through the first three steps and in lab, we'll introduce two debugging tools, print statements and the debugger, to complete the process.

### Observation and Data Collection

~~~admomish question title="Discovering Errors"
Consider the following method which performs the _selection sort_ algorithm on an array of numbers:

```java
public static void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        int minIdx = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIdx]) {
                minIdx = j;
            }
        }
        int tmp = arr[i];
        arr[minIdx] = arr[i];
        arr[i] = tmp;
    }
}
```

Create a sample program that utilizes this function.
What error arises when you use this function?
~~~

Perhaps obvious, but it needs to said: in order to debug code, we must first _observe_ something problematic about our program!
This is the "bug" in the debugging process.
We can observe bugs in our code at two points:

+   _Before_ program execution.
    These bugs are called _static_ bugs.
+   _During_ program execution.
    These bugs are called _dynamic_ bugs.

Errors in our program observed before program execution come from the tools that we use during development.
Our compiler is typically the tool that performs most of this work.
However, we may rely on other tools, especially in more real-world contexts, such as linters and static analysis tools.
In modern development environments, your editor itself may perform some amount of code checking, independent of the compilation process.

Nevertheless, these static errors come in a few varieties:

+   **Syntax errors** where the code is malformed in some way.
+   **Unbound variable errors** where a variable is used before it is defined.
+   **Type errors** where the values of a program are used in an inconsistent manner, e.g., adding a number and a boolean together.

Our tools usually produce _error messages_ in response to static errors.
The quality of these error messages is sometimes suspect, but we must extract as much value from them as possible!
Each kind of error message begets its own interpretation and different tools have their own idiosyncrasies that we must account for.
For example, interpreting a C type error message requires similar but different trains of thought versus interpreting a Java type error message.

If an error is not one of these static errors, then it is a dynamic error!
Dynamic errors come in many varieties:

+   **Exceptions** where the program raises an error during the course of execution.
    Exceptions are thrown in response to bad situations detected as runtime, e.g., indexing outside the bounds of an array.
    Additionally, most languages use exceptions as the primary method for programmers to signal _exceptional error conditions_ in their programs.
+   **Logic errors** where the program is semantically correct, i.e., language constructs are used consistently, but the program produces incorrect output or performs unintended behavior.

Exceptions, by their nature, produce error messages, usually accompanied by a _stack trace_ denoting the precise stack of function calls and code locations that caused the exception.
Logic errors in contrast are usually _silent_.
We only observe logic errors by running our code and interpreting its results!

~~~admonish check title="Discovering Errors"
The following `main` function exercises `selectionSort` on an unsorted array of numbers.
It also features a helper function that uses a [StringBuffer](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/lang/StringBuffer.html) to incrementally build up a formatted string for the given array.

```java
public static String arrToString(int[] arr) {
    StringBuffer buf = new StringBuffer(arr.length * 2);
    buf.append("[");
    if (arr.length > 0) {
        buf.append(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            buf.append(",");
            buf.append(" ");
            buf.append(arr[i]);
        }
    }
    buf.append("]");
    return buf.toString();
}

public static void main(String[] args) {
    int[] arr = { 3, 8, 5, 2, 1, 0, 6, 9, 4, 7 };
    selectionSort(arr);
    System.out.println(arrToString(arr));
}
```

The program outputs `[3, 8, 5, 5, 8, 5, 6, 9, 8, 9]` which is certainly not correct!
~~~

### State Assumptions

~~~admonish question title="Establishing invariants"
Inspect the following version of the incorrect `selectionSort` implementation and fill in the comments:

```java
public static void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        // 1. What is invariant about minIdx with respect
        //    to the computation of the nested for-loop?
        int minIdx = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIdx]) {
                minIdx = j;
            }
        }

        // 2. What ought to be true about about arr[i]
        //    and arr[minIdx] after execution of
        //    the following three lines?
        int tmp = arr[i];
        arr[minIdx] = arr[i];
        arr[i] = tmp;
    }
}
```
~~~

If our initial observation phase is about collecting data about our error, then the assumption phase is about collecting data about the _remainder of our program_.
In other words, we need to explicitly state what the program was _trying_ to do in the first place!
This allows us to focus the remainder of our debugging process on answering the following question:

> Why did the program behave in the way we observed when it should have behaved in the way that we predicted?

In program design, we emphasize breaking up a bigger problem into smaller parts for the purposes of making the work tractable.
Likewise, when we state assumptions about our program, we need to decompose our assumptions so that they are actually usable in the debugging process.

For example, consider writing a program that calculates a student's grade.
While it is factually correct to say:

> Given a concrete set of scores, the program should output the student's grade.

This stated assumption is too high level to be of use when reason about how specific lines of code failed to operate!
We must think about _how our code is supposed to be accomplishing this goal_ in order to gain traction on our problem.

At this point, this is where we use the language of preconditions, postconditions, and invariants to state usable assumptions about the behavior of our program.
In particular:

+   Preconditions and postconditions allow us to state _contracts_ about the relationship between caller and callees of functions.
    In other words, they specify what a caller must provide as input and what the function guarantees will be true about its behavior if the contract is satisfied.
+   Invariants allow us to characterize repetitive behavior, e.g., loops, or stateful behavior, e.g., methods of objects.

~~~admonish check title="Establish invariants"
1.  Inside the inner for-loop, we see that `minIdx` is assigned `j` whenever `arr[j] < arr[minIdx]`.
    `j` effectively runs from `i` to the end of the array since `minIdx` is initially loaded with `i` and the loop starts at `j = i + 1`, so it appears that `minIdx` will be loaded with the _index of the smallest value_ found in the array from `i` to the end of the array.
    If you recall the high-level description of selection sort, you may recognize this invariant as the key property that selection sort maintains during its execution.
2.  Without thinking too hard about it, you might recognize this particular 3 statement-sequence as the _array swap pattern_ which involves using a temporary variable to hold one of the values being swapped.
    Thinking about the overall problem the function is trying to solve, it becomes clear that the intended effect of this swap is to put the minimum value of the array from `i` to the end of the array in position `i`.
    If we repeatedly select and swap each minimum value in this manner, we should have a sorted array by the end of the function!
~~~

### Predict the Root Cause

~~~admonish question title="Predicting the Problem"
In the previous question, we established invariants about `minIdx` (point A) and the swapping code at the end of the for-loop (point B).
Now it is time to make a prediction!
Which of the two invariants do you believe is broken about this function?

```java
public static void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        // Point A
        int minIdx = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIdx]) {
                minIdx = j;
            }
        }

        // Point B
        int tmp = arr[i];
        arr[minIdx] = arr[i];
        arr[i] = tmp;
    }
}
```
~~~

With our data about the error (what we observed) and surrounding program (what we believe should have happened), we can now attempt to predict what went wrong.
It is tempting to skip this prediction step since we have the data, but I argue that making an explicit statement of prediction is the key to taming complexity in the debugging process.
In the scientific method, if we don't make an explicit prediction and begin to experiment, we run the ethical risk of _predicting what we observe_ which is not a valid way to establish the validity of a prediction!
We don't encounter that problem with debugging, but we run the risk of our attention being _fragmented_ when we dive into the code.
In other words, it would be a shame to do all this work to gather data in a systematic fashion only to lose sight of the bigger picture once we're using the debugger!

In this sense, predictions are a key _focusing tool_ in the debugging process.
As you may have noticed, as our programs get more and more complex, it is harder to keep track of how all the different pieces work together.
This knowledge of how "it all works" is integral to reconciling the observed bug, but if we don't put guard rails in place, we may, by distraction or by force, have to try to keep too much of our program in our head at one time.
Good program design alleviates this problem by quite a bit, but explicit predictions help us be even more razor-focused in our search.

That being said, what do these predictions look like?
Simply put, if we've done a good job of capturing our assumptions about how our program works, we should predict that one or more of these assumptions is incorrect!
For example, if we establish a class invariant that a field of an object is non-`null` our prediction can simply be that this invariant was broken!
When convenient, we can try to be more refined in our prediction, e.g., the field in question is non-`null` because we didn't initialize it in a particular situation.
However, we should not enter _decision paralysis_ when debugging!
Sometimes it is more prudent to make a simple, almost trivial prediction, verify it, and then use the information we gather during the verification phase to make a more refined prediction!

~~~admonish check title="Predicting the Problem"
One way to approach the business of predicting the problem is to use our mental models of computation to head-verify whether the invariants hold.
By doing so, you will likely notice that `minIdx` code seems to be doing its job and perhaps the swap code is suspect!
Subsequently, our prediction can be as simple as:

> The swapping portion of `insertionSort` is not doing the right thing.

However, you may not see this is the problem just yet, and that's fine!
A prediction is just that: something for us to verify in the following steps.
So it is equally valid to predict that `minIdx` is being computed incorrect:

> The invariant on `minIdx` is _not_ being preserved appropriately.
~~~

~~~admonish question title="Making Change (‡)"
Consider the following buggy Java implementation of a function that makes change.
It does so by returning an array of four integers that reports the amount of cents (¢1), nickels (¢5), dimes (¢10), and quarters (¢25) used to make change for the given amount (in cents).

```java
public static int[] makeChange(int cents) {
    int[] change = new int[] { 0, 0, 0, 0 };
    change[3] = cents / 25;
    cents     = cents % 25;
    change[2] = cents / 25;
    cents     = cents % 10;
    change[1] = cents / 10;
    cents     = cents % 5;
    change[0] = cents;
    return change;
}
```

Go through the first three steps of the hypothesis-driven debugging process to identify a problem in the method and eventually make a prediction as to why this method is incorrect.
~~~