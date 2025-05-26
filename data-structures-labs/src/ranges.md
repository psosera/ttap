# Ranges

In today's short lab, you'll enhance your `Range` class from the reading into a full-blown class for iterating over ranges of numbers.
Your current version of the `Range` implements the `iterable` interface and has a single constructor `Range(int end)` that constructs a range from `0` to `end`, exclusive.
Enhance your implementation in the following ways:

1.  If it does not do so already, a `Range` object should take $\mathcal{O}(1)$ (constant) space.

2.  Because it looks janky to `new` up an object only to pass it to a for-each loop, write a `static` function:

    *   `static Range over(int end)`: creates a new `Range` from `0` to `end`, exclusive.

    That makes constructing a `Range` more convenient.
    This allows you to write the following code which is more asthetically pleasing:

    ~~~java
    for (int n : Range.over(10)) {
        System.out.println(n);
    }
    ~~~

    Once `over` is written, mark your `Range` constructor `private` to force users of `Range` to use `over` rather than `new`!

3.  Enhance your `Range` class with additional _overloads_ to `over` to cover additional scenarios:

    *   `static Range over(int start, int end)`: creates a new `Range` from `start` to `end`, exclusive or `end` to `start`, exclusive, if `start > end`.
    *   `static Range over (int start, int end, int step)`: creates a new `Range` from `start` to `end`, exclusive, incrementing by `step` or decrementing by `step` if `step < 0`.

    To implement these functions, you will likely need to create and/or modify the (`private`) constructors of `Range`.
    (_Hint_: observe that of the three versions of `over`, one of them likely subsumes the rest
    You should model your code based on this observation!)

When you are done, you can turn in your `Range.java` file directly to Gradescope; no need for a Github turn-in since this is a single file!