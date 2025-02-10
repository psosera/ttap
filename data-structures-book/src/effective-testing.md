# Effective Testing

While effective debugging technique allows us to minimize the time we spend diagnosing and fixing errors, we can still spend a substantial time debugging our code if we aren't careful.
How can we apply our correctness toolkit towards _preventing_ errors in our code?
This is, ultimately, the central problem that the field of _formal verification_ tries to solve: what tools and techniques (usually mathematical in nature) can we employ to design and verify software?

The primary distinction between these tools and techniques are _when_ they are employed in the software development process.

1.  Tools that operate before the program runs are called _static analysis tools_.
    An example of a static analysis tool that we might not think of as such is the _type system_ of a statically typed language.
    The type-checking performed by the Java compiler, for example, checks that values are used in consistent ways before a program runs.
2.  Tools that operate during program execution are called _dynamic analysis tools_.
    An example of a dynamic analysis tool you might have used before is [Valgrind](https://valgrind.org/), a tool that monitors a running program and reports memory errors as they occur.

Static analysis tools have the benefit of operating earlier in the development process where the costs of fixing the issue are less.
An egregious example of the real costs of finding bugs late is the [Pentium FDIV Bug](https://en.wikipedia.org/wiki/Pentium_FDIV_bug) where the design of floating-point processing units of early Intel Pentium CPUs had a bug.
This bug, caught well after production and marketing, cost Intel 475 million dollars to recall and replacement the affected chips.

While static analysis tools allow us to catch bugs before they become costly mistakes, such tools are necessary _incomplete_ in their analysis.
It turns out this is a fundamental result in the theory of computation!

~~~admonish info title="Claim (Undecidability of Static Analysis, Rice's Theorem)"
Suppose you have a non-trivial property of a program that you wish to verify.
There exists no algorithm that can determine whether that property holds of an arbitrary program.
~~~

A corollary to this result is that _any_ at analyze a program must be incomplete, i.e., there might be:

+   _False positives_ where the tool says a program obeys a property when it does not and
+   _False negatives_ where the tool says a program does not obey a property but it does.

Thus, while static analysis tools are important, they cannot be our only way to verify programs.
Dynamic analyses, by virtue of working alongside _actual program execution_ do not run afoul of these issues.
But because programs of sufficient complexity can operate in an infinite number of ways, dynamic analyses are _narrow in their scope_.
The results of such an analysis are necessarily about particular executions of a program which always leaves the question open of whether _some other execution_ might cause a problem.
Subsequently, real-world programming always involves a mixture of static and dynamic analyses to minimize the cost of discovering and fixing bugs in our code.

## Testing as Program Verification

How does testing fit into this framework?
Testing is the most commonly applied dynamic analysis in real-world code.
When we test our code, we make a very precise, yet narrow, machine-checked claim about our program.
For example, consider the following code which tests whether a sorting function works:

~~~java
public static boolean isSorted(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        if (arr[i-1] > arr[i]) {
            return false;
        }
    }
    return true;
}

public void testInsertionSort() {
    int[] arr = { 3, 1, 8, 4, 5, 9, 0, 7, 2, 6 };
    insertionSort(arr);   // Defined elsewhere...
    if (isSorted(arr)) {
        System.out.println("Test succeeded!");
    } else {
        System.out.println("Test failed!");
    }
}
~~~

`testInsertionSort` verifies whether `insertionSort` sorts the particular array `arr`!
This claim is:

+ Precise because it directly captures what we want `insertionSort` to do.
+ Narrow because it only talks about one particular input.
+ Machine-checked because we rely on actual program execution to verify or refute the claim.

This last point is particularly noteworthy and why testing is appealing.
