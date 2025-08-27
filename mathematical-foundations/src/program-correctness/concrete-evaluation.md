# Concrete Evaluation

We begin our journey into the foundations of computer science by first studying one of its key applications to computer programming: _program correctness_.
Rigorously stating and proving program properties will require us to deep-dive into _mathematical logic_, the sub-field of mathematics devoted to modeling _deductive reasoning_.
Deductive reasoning is the foundation for virtually all activities in computer science, whether you are designing an algorithm, verifying the correctness of a circuit,  or assessing a program's complexity.
In this manner, this initial portion of our investigation of the foundations of computer science is, quite literally, the _cornerstone_ of everything you do in this field moving forward.

## Why Functional Programming?

In this course, we'll use the [Python programming language](https://python.org) as our vehicle for studying program correctness.
In particular, we'll focus on a _pure, functional_ subset of Python that corresponds to other function languages such as the Scheme we introduce in CSC 151.
We do this because, as we will see shortly, _pure, functional languages_ admit for a _simple, substitution-based_ model of computation.
By "pure," we mean that the language does not allow functions to produce _side-effects_.
A side-effect is some behavior observable to the caller of the function beyond the function's return value.

The canonical example of a side-effect is the _mutation of global variables_.
For example, in Python:

~~~python
glob = 0    # a global variable

def increment_global():
    # in Python, we have to declare a global variable as
    # locally accessible with a `global` declaration
    global glob
    glob = glob + 1

def main():
    print(glob)           # 0
    increment_global()
    print(glob)           # 1
    increment_global();
    print(glob)           # 2
    increment_global();
    increment_global();
    print(glob)           # 4
~~~

The `increment_global` function takes no parameters and produces no output.
Its sole task is to produce a side-effect: change the state of `global` by incrementing its value.
Since the function does not return a value, `main` can only use `increment_global` for its side-effect by calling the function and observing the change to `global`.

We'll have more to say about side-effects and their relationship with the substitutive model of computation we introduce later in this reading.
For now, note that ultimately it is not the _language_ that is important here.
What is important is whether we are considering a _pure fragment_ of that language, instead.
Indeed, the lessons we learn here regarding program correctness apply to any program that behaves in a pure manner, _i.e._, has no side-effects.
For example, we can easily translate our model of computation to the pure fragment Python!

This fact supports a general maxim about programming you have hopefully heard at some point:

> Minimize the number of side-effects in your code.

Intuitively, complicated code with side-effects is frequently tricky to reason about, _e.g._, explicit pointer manipulation in C.
Our study of program correctness in this course will give us concrete reasons why this is indeed the case.

## Program Correctness

Program correctness is a particularly topical application of logic to start with because every developer cares that their programs are correct.
However, what do we mean by _correctness_?

For this example, we'll first introduce several functions so that we can operate on Python lists like Scheme lists.
These functions give common names to common list indexing and access tricks that we would use in Python to achieve the same effect.

~~~python
# returns the head (first element) of list l
def head(l):
    return l[0]

# return the tail of l, a list that is l but without its first element
def tail(l):
    return l[1:]

# return true iff list l is empty
def is_empty(l):
    return len(l) == 0

# return a new list that is the result of consing x onto the front of l
def cons(x, l):
    return [x, *l]
~~~

With these functions in mind, consider the following Python function that appends two lists together recursively:

~~~python
def list_append(l1, l2):
    if is_empty(l1, l2):
        return l2
    else:
        return cons(head(l1), list_append(tail(l1), l2))
~~~

What does it mean for `list_append` to be "correct"?
Of course, we have a strong intuition about how `list_append` should behave: the result of `list_append` should be a list that "glues" `l2` onto the end of `l1`.
But being more specific about what this means without saying the word "append" is tricky!
As we try to crystallize what it means for `list_append` to be correct, we run into two issues:

**Generality**

A natural way to specify the correctness of `list_append` is through a _test suite_.
For example, in Python, we can write a simple collection of tests using the `unittest` module:

~~~python
import unittest

class list_appendTest(unittest.TestCase):
    def test(self):
      self.assertEqual(list_append([1, 2], [3, 4, 5]), [1, 2, 3, 4, 5])
      self.assertEqual(list_append([], [1, 2, 3, 4 ,5]))
      self.assertEqual(list_append([1, 2, 3, 4, 5], []))

unittest.main()
~~~

Note that these tests exemplify very _specific_ properties about the behavior of `list_append`.
A test case demands that when a function is given a particular set of inputs, _e.g._, `[1, 2]` and `[3, 4, 5]`, that the function must produce the output `[1, 2, 3, 4, 5]`.
You can't get more specific about the behavior of `list-append` than that!

However, are these tests enough?
While they cover a wide variety of the potential behavior of `list_append`, they certainly don't cover _every possible execution_ of the function.
For example, the tests don't cover when `list-append` is given two empty (`[]`) lists.
They also don't cover the case where the expected output is not `[1, 2, 3, 4, 5]`!
In this sense, while tests are _highly specific_, they do not necessarily _generalize_ to the full behavior of the function.
This always leaves us with an inkling of doubt that our functions are truly correct.

**Specificity**

How might we arrive at a more general notion of program correctness?
Rather than thinking about concrete test cases, we might consider more _abstract_ propositions about the behavior of the function.
Such abstract propositions are typically framed in terms of _relationships_ between the inputs and outputs of the function.
With `list_append`, we might think about the lengths of the input and output lists and how we might phrase our intuition about the function "gluing" its arguments together.
This insight leads to the following property that we might check of the function:

> For all lists `l1` and `l2`, `len(l1) + len(l2)` $=$ `len(list_append(l1, l2))`.

To put another way, the lengths of `l1` and `l2` are _preserved_ through `list_append`.
That is, `list_append` doesn't (a) add any elements to the output beyond those found in the input, and (b) does not remove any elements.

By virtue of its statement, this property applies to _all possible inputs_ to `list_append`.
So if we were able to prove that this property held, we knew that it holds for the function, irrespective of the inputs that you pass to the function (assuming that the inputs are lists, of course).
This stands in contrast to test cases where we make claims over individual pairs of inputs and outputs.

However, unlike a test case which is highly indicative of our intuition of correctness for this function, this property is only an _approximation_ of our intuition.
To put it another way, even if we know this property holds of every input to `list_append`, it doesn't mean that the function is correct!
Here is an example implementation of `list_append` where the property holds, but the function is not "correct":

~~~python
define bad_list_append(l1, l2):
    if is_empty(l1):
        return l2
    else:
        cons(0, bad_list_append(tail(l1), l2))
~~~

The lengths of the input lists are preserved with `bad_list_append`.
However, the output is _not_ the result of gluing together the input lists!

~~~python
bad_list_append([1, 2, 3], [4, 5])
> [0, 0, 0, 4, 5]
~~~

Every element of `l1` is replaced with `0`!
In this sense, our length-property of `list_append` is _general_---it applies to every possible set of inputs to the function---but not _specific_---the property only _partially_ implies correctness.

**Balancing Generality and Specificity**

In summary, we've seen that test cases and general program properties sit on opposite ends of the generality-specificity spectrum.
When we think about program correctness and verifying the behavior of programs, we are always trying to find the balance between generality and specify in our verification techniques.
Ultimately, you can never be 100% sure that your program works in all cases---after all, [a stray gamma ray can ruin whatever verification you have done about your program](https://www.wnycstudios.org/podcasts/radiolab/articles/bit-flip).
So when employing program verification techniques---testing, automated analysis, or formal proof---you must ultimately make an _engineering decision_ based on context, needs, and available time and resources.
We won't have time to dedicate substantial time to these pragmatic concerns in this course, but keep them in the back of your mind as we introduce formal reasoning for program correctness in the sections that follow.

~~~admonish problem title="Exercise"
Consider the Python function `lcm(x, y)` from the `math` module which returns the _least-common multiple_ (LCM) of the numbers `x` and `y`.
For example, the LCM of 3 and 4 is 12, and the LCM of 6 and 15 is 30.

1.  Write a few _tests_ for this function.
2.  Write a few _properties_ of this function's behavior that implies its correctness.
~~~

## A Substitutive Model of Computation

In order to rigorously prove that a property holds of a program, we must first have a rigorous _model_ of how a program executes.
You likely have some intuition about how Python programs operate.
For example, try to deduce what this poorly named function does:

~~~python
def foo(bar):
    if is_empty(bar):
        return 0
    else:
        1 + foo(tail(bar))
~~~

How might you proceed?
You might imagine taking an example input, _e.g._, `[9, 1, 2]`, and predicting how the function processes that input.
With a few examples and prior knowledge of how conditionals and recursion work, you can likely deduce that this function calculates the length of the input list given to it.
Our first goal, therefore, is to _formalize_ this intuition, namely, explicate the _rules_ that govern how Python programs operate.

For a pure, functional programming language like the subset of Python that we work with, we can use a simple _substitutive model of computation_ that extends how we evaluate arithmetic expressions.
While simple, this model is capable of capturing the behavior of most Python programs we might consider in this course.

### Review: Arithmetic Expressions

First, let's recall the basic definitions and rules surrounding arithmetic expressions.
We can divide these into two sorts:

+   **Syntactic** definitions and rules that govern when a collection of symbols is a _well-formed arithmetic expression_.
+   **Semantic** definitions and rules that give meaning to a well-formed arithmetic expression.
    "Meaning," in this case, can be thought of as "how the arithmetic expression computes."

**Syntax**

Here is an example of a well-formed arithmetic expression:

$$
8 × (10 - 5 ÷ 2)
$$

In contrast, here is an ill-formed arithmetic expression:

$$
(4 + 5) - (\\; × 9)
$$

In this ill-formed arithmetic expression, the multiplication operator (×) is missing a left-hand argument.

We can formalize this intuition about how to create a well-formed arithmetic expression with a _grammar_.
Similarly to natural language, grammars concisely define _syntactic categories_ as well as _rules of formation_ for various textual objects.
Here is a grammar for arithmetic expressions:

~~~
e ::= <number> | e1 + e2 | e1 - e2 | e1 × e2 | e1 ÷ e2 | (e)
~~~

To the left of the `::=` symbol is a variable, `e`, that represents a particular syntactic category.
To the right are the rules for forming an element of that category.
Here, the syntactic category is "arithmetic expressions," traditionally represented with the variable `e`.
The rules are given as a collection of _possible forms_ or _alternatives_, separated by pipes (`|`).
The grammar says that a well-formed expression is either:

+   A number (`<number>` is a placeholder for any number), or
+   An _addition_ of the form `e1 + e2` where `e1` and `e2` are expressions, or
+   A _subtraction_ of the form `e1 - e2` where `e1` and `e2` are expressions, or
+   A _multiplication_ of the form `e1 × e2` where `e1` and `e2` are expressions, or
+   A _division_ of the form `e1 ÷ e2` where `e1` and `e2` are expressions, or
+   A _parenthesized expression_ of the form `(e)` where `e` is an expression.

~~~admonish note
**About Pronunciation**: it may not seem like it, but pronunciation is an excellent tool for internalizing the meaning of a collection of mathematical symbols.
Every collection of mathematical symbols is really a _sentence_ in the natural language sense.
In our grammar, we can pronounce `::=` as "is" and `|` as "or," so we obtain: "`e` is a number, or an addition, or … ."
This makes it more intuitive and clear what the symbols represent.
As we introduce more mathematical symbols throughout the course, always look for ways to pronounce these symbols as cohesive sentences.
~~~

Importantly, the grammar is _recursive_ in nature: the various alternatives contain expressions inside of them.
This allows us to systematically break down an arithmetic expression into smaller parts, a fact that we leverage when we evaluate expressions.
For example:

+ $8 × (10 - 5 ÷ 2)$ is an expression.
+ $8$ is an expression.
+ $(10 - 5 ÷ 2)$ is an expression.
+ $10 - 5 ÷ 2$ is an expression.
+ $10$ is an expression.
+ $5 ÷ 2$ is an expression.
+ $5$ is an expression.
+ $2$ is an expression.

Note that when interpreting symbols, there is sometimes ambiguity as to how different symbols _group together_.
For example, consider the sub-expression from above:

$$
10 - 5 ÷ 2.
$$

Should we interpret the expression as $10$ and $5 ÷ 2$ as in the example or alternatively $10 - 5$ and $2$.
Our knowledge of arithmetic tells us that the first interpretation is correct because the $(÷)$ operator takes _precedence_ over the $(-)$ operator.
Rules of precedence are, therefore, additional syntactic rules that we must consider in some cases.
There also exist _rules of associativity_ for some grammars that govern the _order_ in which we group symbols when the same operator is invoked multiple times.
For example, we traditionally interpret subtraction as _left-associative_.
That is $1 - 2 - 3$ is understood to be $(1 - 2) - 3$ rather than $1 - (2 - 3)$, _i.e._, $1 - 2$ goes first rather than $2 - 3$.

**Semantics**

Once we have established what a well-formed expression looks like, we then need to define what it _means_.
In general, the meaning of a well-formed object is dependent entirely on the purpose of said object.
For arithmetic expressions, we care about computing the result of such an expression, so we take that to be the _meaning_ or _semantics_ of an expression.

Traditionally, we define the rules for computing or _evaluating_ an arithmetic expression as follows:

1.  Find the next sub-expression to evaluate using the rules of associativity and precedence to resolve ambiguity.
2.  Substitute the value that the sub-expression evaluates to for that sub-expression in question.
3.  Repeat the process until you are left with a final value.

A _value_ is simply an expression that can not be evaluated or simplified any further.
For arithmetic expressions, any number is a value.

By repeating this process, we obtain a _series of evaluation steps_ that an arithmetic expression takes before arriving at a final value.
For example, here are the steps of evaluation taken for our sample expression from above:

~~~python
    8 × (10 - 5 ÷ 2)
--> 8 × (10 - 2.5)
--> 8 × 7.5
--> 60
~~~

Note that at each step, the resulting expressions are equivalent, _e.g._, $8 × (10 - 5 ÷ 2)$ and $8 × 7.5$ are equivalent; both of these expressions are also equivalent to the value $60$.

~~~admonish question title="Exercise"
For each of the following arithmetic expressions, determine if (a) they are well-formed and (b) if they are well-formed, what is their step-by-step evaluation to a final value.

1.  $100 ÷ (25 × 5 - 3)$.
2.  $38 - × 5 + 2)$.
3.  $1 + 2 - 3 + 4 × 5 ÷ 6 - 7$.
~~~

## Core Python and the Substitutive Model

Our substitutive computational model for Python extends the rules for evaluating arithmetic expressions.
Like arithmetic expressions, we will think of Python programs as an _expression_ that evaluates to a final value.
And like arithmetic, programs operate by stepwise evaluation, where we find the next sub-expression to evaluates and substitute.
Python's rules for precedence mirror those from arithmetic.
So we only need to consider precedence for Python's additional constructs beyond arithmetic.

### Expressions

For our purposes, well-formed Python expressions can be defined by the following grammar:

~~~
e ::= x                         (variables)
    | <number>                  (numbers)
    | True                      (true)
    | False                     (false)
    | lambda (x1, ..., xk): e   (lambdas)
    | e(e1, ..., ek)            (function application)
~~~

In other words, a Python expression is either:

+   A variable `x`.
+   A number.
+   The true value `True`.
+   The false value `False`.
+   A Lambda with arguments `x1, ..., xk` and a body expression `e`.
+   A function application applying arguments `e1, ..., ek` to function `e`.

As an example, the following expression taken from our definition of `list_append` above:

~~~python
cons(head(l1), list_append(tail(l1), l2))
~~~

Is a well-formed expression that is a function application of two arguments to the function bound to the variable `cons`.
Each argument is, itself, a function application.

### Values

In arithmetic, numbers were our only kind of value.
In Python, we have multiple kinds of values corresponding to the different types of Python:

+   Numbers are still values (of type `int` or `float` depending on whether number is integral or floating-point).
+   `True` and `False` are values (of `bool` type)
+   `lambda (x1, ..., xk): e` is also a value (of `function` type).

### Statements

In addition to expressions, Python also has _statements_ defined by the following grammar:

~~~
s ::= s1                    (sequenced statements)
      s2
    | x = e                 (variable declaration/assignment)
    | def f(x1, ..., xk):   (function declaration)
          s
    | return e              (returns)
    | if e1:                (conditional statements)
          s1
      else:
          s2
~~~

The sequenced statement looks syntactically odd on its own.
But it captures idea that wherever one statement is expected, multiple statements can appear by placing them one line after the other at the same indentation level.
For example:

~~~python
def f(x):
    y = x + 1
    z = y + 1
    return x + y + z
~~~

Here, the body of `f` can be thought of as three statements or a single sequenced statement (`y = x + 1`) that, itself, precedes a single sequenced statement (`z = y + 1`), that, itself, precedes a final statement (`return x + y + z`).

In contrast to expression, statements do _not_ produce values.
Instead, they have some kind of _effect_ on the execution of our program.
For example:

+ Variable and function declarations introduce new variables into scope.
+ Return statements causes the program to return from a function with a specified value.
+ Conditional statements transfer the flow of execution into one of several possible branches.

Reviewing our `list_append` definition, we see that it is a function declaration.
The body of `list_append` is a single conditional and inside each branch of the conditional is a `return` statement.

## Substitutive Evaluation for Python

The heart of Python evaluation is function application.
Function application directly generalizes arithmetic evaluation for operators:
In arithmetic evaluation:

1.  We first evaluate the left- and right-hand arguments to the operator to values.
2.  We then apply the operator to those values.

For example, for $e_1 + e_2$, we first evaluate $e_1$ to a value, call it $v_1$ and then evaluate $e_2$ to a value, call it $v_2$.
We then carry out the addition of $v_1$ and $v_2$.

To evaluate a function application, we first evaluate each of the sub-expressions of the function application to values.
For example, consider the `inc` function defined to be:

~~~python
def inc(n):
    return n + 1
~~~

if we have the expression, `inc(3 + (5 * 2))`, it evaluates as follows:

~~~python
    inc(3 + (5 * 2))
--> inc(3 + 10)
--> inc(13)
~~~

Once we arrive at this point, we need to evaluate the actual function application itself.
In the case of primitive operations, we just carry them out directly and substitute their resulting values into the overall expression.
However, arbitrary functions will perform arbitrary operations, so we cannot simply just "substitute the result"; we must calculate it by hand!

To do this, we want to perform the following steps:

1.  We substitute the _body of the function be applied to_ for the overall function application.
2.  We substitute the actual arguments passed to the function for each of its parameters.
3.  We then continue evaluation of the resulting expression like normal.

However, you may have noticed in the Python grammar we presented earlier that the body of functions are _statements_ and not _expressions_.
This poses a conundrum for our evaluation model because we want each step to be a valid expression in our language.
In other words, we don't want to an expression to step to a statement; that doesn't make sense!

To reconcile this fact, we will introduce one additional expression form that only appears in our step-by-step evaluative model.
The _statement-expression_, written `{s1; ...; sk}` represents a series of statements that are currently executing that _must_ end in a `return` statement.
The value that this `return` statement evaluates to is what this statement-expression evaluates to when it is done executing.

For example, with our `inc` expression above:

1.  We substitute the statement-expression `{return n + 1}` for the function call `inc(13)`.
2.  We substitute `13` for `n` everywhere it occurs in this new statement-expression: `{return 13 + 1}`.
3.  We continue evaluation of the resulting expression like normal.

To evaluate a statement-expression, we execute the first statement in the list of statements left to execute.
In our example, this means we must evaluate `return 13 + 1`.
Evaluating a return statement is straightforward: we evaluate the `return`'s expression to a value and then substitute this value for the _entire statement-expression_.

This results in the following steps of evaluation:

~~~python
    (inc 13)
--> {return 13 + 1}
--> {return 14}
--> 14
~~~

Observe how the body of `inc` took two steps to evaluate: one to evaluate the `return`'s argument and one to return from the function call.

These rules apply to functions of multiple arguments as well.
For example, consider the following definition of a function that averages three numbers:

~~~python
def avg3(x1, x2, x3):
    return (x1 + x2 + x3) / 3
~~~

Then the expression `avg3(2 * 5, 8, 1 + 2)` evaluates as follows:

~~~python
    avg3(2 * 5, 8, 1 + 2)
--> avg3(10, 8, 1 + 2)
--> avg3(10, 8, 3)
--> {return (10 + 8 + 3) / 3}
--> {return (18 + 3) / 3}
--> {return 21 / 3}
--> {return 7}
--> 7
~~~

Note that we evaluate the arguments to `avg3` in left-to-right order, one argument at a time.

~~~admonish question title="Exercise"
Consider the following Python top-level definitions:

```python
def f(x, y):
    return x + y

def g(a, b):
    return f(a, a) - f(b, b)
```

Use your mental model of computation to give the step-by-step evaluation of the following Python expression: `g(5, 3)`.
Check your work by evaluating this expression in Python and verifying that you get the same final result.
~~~

### Return Statements

As described above, to evaluate a `return` statement:

1.  Evaluate the return's argument to a value.
2.  Substitute this value for the `return` statement's _immediate surrounding statement-expression_.

In other words, `{return v} --> v` for any value `v`.

Statement-expressions can be nested because functions call other functions.
In these situations, we simply evaluate the innermost statement-expression.
This corresponds to evaluating the most recent function call!

For example, consider the following example:

~~~python
def f(n):
    return n + 1

def g(a, b)
    return f(a) + f(b)
~~~

And consider the step-by-step evaluation of `g(8, 2)`:

~~~python
    g(8, 2)
--> {return f(8) + f(2)}
--> {return {return 8 + 1} + f(2)}
--> {return {return 9} + f(2)}
--> {return 9 + f(2)}
--> {return 9 + {return 2 + 1}}
--> {return 9 + {return 3}}
--> {return 9 + 3}
--> {return 12}
--> 12
~~~

### Conditionals

Recall that conditional statements have the form:

~~~python
if e:
    s1
else:
    s2
~~~

To evaluate a conditional:

1.  We first evaluate the guard `e` to a value.
    This value ought to be a boolean value, _i.e._, `True` or `False`.
2.  If the guard evaluates to `True`, we substitute statement `s1` for the entire conditional.
3.  Otherwise, the guard must have evaluated to `False`.
    We, therefore, substitute `s2` for the entire conditional.
4.  We then continue evaluating the resulting expression.

Here is an example of a conditional within our substitutive model.

~~~python
    1 + { if 3 < 5:
              return 2
          else:
              return 5 * 5 }
--> 1 + { if False:
              return 2
          else:
              return 5 * 5 }
--> 1 + { return 2 }
--> 1 + 2
--> 3
~~~

Note that it is sometimes quite onerous to write a conditional statement over multiple lines.
When it is more prudent to do so, we will collapse everything onto a single line, _e.g._,

~~~python
{if 3 < 5: return 2 else: return 5 * 5}
~~~

### Variable Declarations

Variable declarations allow us assign names to intermediate computations for the purposes of readability or performance (_i.e._, to avoid redundant work).
To evaluate a variable declaration of the form `x = e`:

1.  We evaluate `e` to a value `v`.
2.  We substitute every occurrence of `x` for `v` in the immediate statement-expression that encloses this assignment.
3.  Finally, we remove this variable declaration from the statement-expression that we are evaluating.

Consider the following function that declares local variables:

~~~python
def f():
    x = 1 + 1
    y = x * 2
    z = x * y
    return x + y + z
~~~

Here's an example of the step-by-step evaluation of a call to this no-argument function:

~~~python
    f()
--> { x = 1 + 1
      y = x * 2
      z = x * y
      return x + y + z }
--> { x = 2
      y = x * 2
      z = x * y
      return x + y + z }
--> { y = 2 * 2
      z = 2 * y
      return 2 + y + z }
--> { y = 4
      z = 2 * y
      return 2 + y + z }
--> { z = 2 * 4
      return 2 + 4 + z }
--> { z = 8
      return 2 + 4 + z }
--> { return 2 + 4 + 8 }
--> { return 6 + 8 }
--> { return 14 }
--> 14
~~~

~~~admonish note
**A note on precision**: while performing these step-by-step derivations, you might be tempted to skip steps, _e.g._, evaluating all the arguments of a function call at once down to a single value.
Resist this urge!
One of the "hidden" skills we want to develop in working through these derivations is _unfailing attention to detail_.
We want to be able to carry out mechanical processes like these derivations _exactly_ as advertised.

This is useful for two purposes:

1.  Such attention to detail is a skill necessary for effective algorithmic design.
    By maintaining this level of precision, you are more likely to catch corner cases and oddities in your design that you can fix before they later become bugs in code.
2.  More immediate for our purposes, skipping steps in deductive reasoning is the number one way to introduce erroneous reasoning in your proofs!
    Mechanics don't lie, so being able to explicate every step of a logical argument is necessary to be certain that your reasoning is sound.

Later in the course, we will discuss _lifting_ our reasoning to more high-level concerns where we are free to skip low-level steps so that we don't lose sight of the bigger picture of what we are trying to prove.
However, our high-level reasoning _is always backed by low-level mechanics_.
It is imperative your develop those mechanics now, otherwise, your high-level reasoning will be baseless!
~~~

## Additional Exercises

~~~admonish problem title="Exercise (Additional Tracing, ‡)"
Consider the following Python top-level definitions:

```python
def compute3(x, y, z):
    intermediate = x + y
    return z * intermediate + intermediate

def triple(n):
    return compute3(n, n, n)
```

Give the step-by-step evaluation (i.e., evaluation traces) for each of the following expressions.
Make sure to write down all steps of evaluation as required by our substitutive model of computation!

1.  `3 + (5 * (2 / (10 - 5)))`
2.  `compute3(2 * 3, 8 + 3, 1 / 2)`
3.  `triple(5 + 0.25)`

Make sure to check your work by entering these expressions into Python!
~~~
