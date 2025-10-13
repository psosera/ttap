# Assertions and Debugging

In this lab, we'll practice reasoning about program assertions and then put that knowledge into practice by debugging programs.

While we will write code in today's lab, it will be all in the service of _answering questions about how programs work_.
Consequently, you'll be turning in your answers directly to Gradescope.

## Part 1: Assertions

In these problems, you'll work your ability to reason about program properties and whether they are _preserved_ throughout execution.
Knowing whether a property is preserved within a program is a cornerstone of efficient debugging as we will discuss in the coming days.

### Problem 1a

Consider the following method:

~~~java
public static int foo1(int x1, int x2) {
    // Point A
    if (x1 < 0 || x2 < 0) {
        return -1;
    }
    // Point B
    int y1 = x1 % 10;
    int y2 = x2 % 10;
    int z = 0;
    // Point C
    if (y1 < y2) {
        z = y1 - y2;
    } else {
        z = y2 - y1;
    }
    // Point D
    return z;
}
~~~

Now, consider whether each of the propositions holds at the given point in the method all the time (✓), some of the time (?), or none of the time (✗):

*   Point A:
    + `x1 == 0`
    + `x2 < 0`
*   Point B:
    + `x1 == 0`
    + `x2 < 0`
*   Point C:
    + `y1 < 5`
    + `y2 > 0`
*   Point D:
    + `z > y1`
    + `z < 0`

### Problem 1b

~~~java
public static String foo2(String s, char c) {
    String ret = "";
    // Point A
    if (s == null) { throw new IllegalArgumentException(); }
    if (s.length < 2) { return s; }
    // Point B
    for (int i = 1; i < s.length; i++) {
        // Point C
        ret += s.charAt(i);
        ret += c;
        // Point D
    }
    // Point E
    return ret;
}
~~~

Now, consider whether each of the propositions holds at the given point in the method all the time (✓), some of the time (?), or none of the time (✗):

|         | `s.length >= 2` | `ret.length() > 0` | `ret.length() % 2 == 0`
| ------- | --------------- | ------------------ | -----------------------
| Point A |                 |                    |
| Point B |                 |                    |
| Point C |                 |                    |
| Point D |                 |                    |
| Point E |                 |                    |

# Part 2: Debugging Practice

To get practice with the debugger, try debugging the following erroneous methods.
Remember to apply the *scientific debugging process* when fixing these methods:

1. Understand the problem with test cases/examples and write the code/understand the code base (if you did not write the code yourself).
2. Run the code against these test cases and discover a bug.
3. Review your assumptions (pre-conditions, post-conditions, and invariants) that you formed in step 1 and form a hypothesis as to which of these assumptions could have been violated, causing the bug you are witnessing.
4. Use debugging tools to verify or refute this hypothesis.

For each method, you should:

+ Identify the problematic line(s) of code.
+ Describe the problem in a sentence or two.
+ Describe how you fix the problem, in particular, by modifying the identified lines of code or adding new lines.

Create a project called `DebuggingExercises` that utilizes Maven as discussed in class.
You can use the following basic template file as a starting point:

+ [pom.basic.xml](./files/pom.basic.xml)

Make sure to rename the file to `pom.xml` and place it in the root of the `DebuggingExercises` project.
Your source files should be found in the `edu.grinnell.csc207.debugging` package, so they should be found in the `src/main/java/edu/grinnell/csc207/debugging` directory.
For this project, you can create a single source file, `DebugggingExercises.java`, that contains the functions below.

*(Note: these programs may have more than one bug in them, so test-and-diagnose thoroughly!)*

~~~java
import java.lang.Math;

/**
 * @return the third greatest element of arr
 */
public static void thirdGreatest(int[] arr) {
    if (arr.length == 0) {
        throw new IllegalArgumentException();
    } else if (arr.length == 1){
        return arr[0];
    } else if (arr.length == 2) {
        return Math.max(arr[0], arr[1]);
    } else {
        int g1 = Math.max(arr[0], Math.max(arr[1], arr[2]));
        int g2 = Math.min(arr[0], Math.min(arr[1], arr[2]));
        int g3 = Math.max(arr[0], Math.min(arr[1], arr[2]));
        for (int i = 3; i < arr.length; i++) {
            if (arr[i] > g1) {
                g3 = g2;
                g2 = g1;
                g1 = arr[i];
            }
            if (arr[i] > g2) {
                g3 = g2;
                g2 = arr[i];
            }
            if (arr[i] > g1) {
                g1 = arr[i];
            }
        }
        return g3;
    }
}
~~~

~~~java
/**
 * Sorts the given array.  Selection sort procedes by maintaining
 * the following invariant during its (outer loop):
 *
 * [ smallest elts (in order) | unsorted elts]
 *                             ∧
 *                             i
 */
public static void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        int min = arr[i];
        for (int j = i; j < arr.length; j++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
    }
}
~~~

~~~java
/**
 * Performs binary search over the given sorted array for the given element.
 * @param arr the (sorted) array to search
 * @param n the element to search for
 * @return the index of the first occurrence of n found in the array or -1 if
 *         the element is not in the array
 */
public static int binarySearch(int[] arr, int n) {
    binarySearchHelper(0, arr.length - 1, arr, n);
}

public static int binarySearchHelper(int lo, int hi, int[] arr, int n) {
    int mid = arr.length / 2 + 1;
    if (arr[mid] < n) {
        return binarySearchHelper(mid + 1, hi, arr, n);
    } else if (arr[mid] >= n) {
        return binarySearchHelper(lo, mid, arr, n);
    } else {
        return mid;
    }
}
~~~
