# Additional Topics in Logic

Logical reasoning and program correctness form the backbone of our mathematical and programming endeavors.
To close this first portion of the course, we look at some final, miscellaneous topics that further tie together these two critical concepts together.

## The Spectrum of Formality

What does it mean for a proof to be "formal?"
Is it enough for a proof to contain symbols and reference relevant proof rules?
In today's class we will answer these important questions!

### Formal Proof Revisited

Previously we discussed the role of intuition and formal definition in our understanding of mathematics.
Recall that mathematics is the science of modeling abstract phenomena.
Our intuition helps us gain a foothold to understand these abstract models, in particular, by instantiating them to real-world examples we understand.
However, these models are ultimately determined by their formal definitions.

If we are interested in _rigorous, logical arguments_, then they must also be rooted in these formal definitions rather than our intuition.
This is what we mean by "formal proof!"

~~~admonish info title="Definition (Formal Proof)"
A _formal proof_ is a mathematical argument rooted in formal definitions.
~~~

To put it another way, a formal proof is a logical argument whose steps of reasoning are justified by the formal definitions of whatever model we are operating in.
For example, consider the inequality proof involving the mathematical factorial function from a previous reading:

~~~admonish info title="Definition (Factorial)"
_factorial_ is a function of type $\mathbb{N} \rightarrow \mathbb{N}$ ($\mathbb{N}$ is the set of natural numbers), written $n!$, defined inductively on $n$ as follows:

$$
\begin{align*}
0! =&\; 1 \\
n! =&\; n \times (n-1)!
\end{align*}
$$
~~~

~~~admonish check title="Proof"
**Claim**: for any natural number $n$, $n! > 0$.

_Proof_.
Let $n$ be a natural number.
We proceed by induction on $n$.

+   Case $n = 0$. $0! = 1 > 0$.
+   Case $n = k + 1$.
    We assume that:

    **IH**: $(n-1)! > 0$

    And must prove:

    **Goal**: $n! > 0$.

    We observe that since $n$ is non-zero that:

    $$
    n! = n \times (n-1)!
    $$

    By our induction hypothesis, $(n-1)! > 0$, _i.e._, a positive natural number.
    Since $n$ is non-zero, we know from multiplying two positive natural numbers results in a positive natural number, thus justifying our desired result:

    $$
    n \times (n-1)! > 0.
    $$
~~~

I think we can all agree this a formal proof!
Observe how our argument proceeds by expanding $n!$ according to its definition and appealing to mathematical facts we know from our previous math education.

In contrast, consider this less formal proof:

~~~admonish check title="(A Less Formal) Proof"
**Claim**: for any natural number $n$, $n! > 0$.

_Proof_.
Let $n$ be a natural number.
We observe that the inequality holds because factorial always produces a non-zero, positive result.
~~~

Why is this proof less formal than the previous one?
Note that this proof does not say anything _inaccurate_!
Formality is not about correctness!
It is a matter of whether appropriate _justification_ exists for the reasoning in the argument.

In this example, we do not justify why factorial always produces a non-zero, positive result.
Here's another attempt that introduces some additional information:

~~~admonish check title="(A Slightly Less Formal) Proof"
**Claim**: for any natural number $n$, $n! > 0$.

_Proof_.
Let $n$ be a natural number.
We observe that the inequality holds because factorial always produces a non-zero, positive result.
This is because $n!$ is the product of all the positive natural numbers from $1$ to $n$, and we know that the product of positive natural numbers is, itself, a positive natural number.
~~~

Now we introduced prose to justify our reasoning, is this proof formal?
I would argue not!
While we have an intuition that factorial is the product of the positive natural numbers from $1$ to $n$, that is _not_ the formal definition we have given factorial.
In this sense, this third proof is still not formal because it does not rely on the formal definitions of our mathematical objects.

With this in mind, look at this excerpt from our original proof:

> Since $n$ is non-zero, we know from multiplying two positive natural numbers results in a positive natural number, thus justifying our desired result:

This is awfully similar to the second proof in that we stated a "fact" without justification.
Do we need to provide justification for this fact?
But wait, we didn't given any justification for our mathematical induction proof principle.
Do we need to justify that as well?

We can play this game _ad nauseam_ because, like computer programs, our mathematical models are built upon _stacks_ of abstractions.
For example, underlying our proof are our principles of natural deduction.
In particular, the line:

> Let $n$ be a natural number.

Is really an application of our **intro-∀** rule where we prove a universally quantified proposition by instantiating its quantified variable with an arbitrary value.
Did we need to cite this rule in our proof to be truly formal?

Our way out of this madness is to recognize that formality in mathematical arguments is not a _binary_ formal/not-formal question.
Instead, mathematical arguments span a _spectrum_ of formality where their level of formality is defined by what _top-level assumptions_ they make.

~~~admonish info title="Definition (Trusted Reasoning Base)"
In a formal argument, our trusted reasoning base (TRB) is the set of assumptions we make about the world in order to establish our formal argument.
~~~

In our "formal" proof above, our TRB includes:

1.  First-order logic and natural deduction reasoning principles.
2.  The soundness of mathematical induction.
3.  Facts about arithmetic, _i.e.,_ the result of multiplying positive natural numbers.

When making mathematical arguments, we have to be cognizant of the assumptions we are making and, thus, our position on the spectrum of formality.

### Symbols, Formality, and Readability

With all that being said, a common misconception we should address is that the formality of a proof is a function of the number of symbols it includes.
With formality defined above as the size of our TRB, how do symbols fit into the picture?
Here is our original proof again, but this time, without symbols:

~~~admonish check title="(A formal?) Proof"
**Claim**: for any natural number $n$, factorial of $n$ is greater than $0$.

_Proof_.
Let $n$ be a natural number.
We proceed by induction on $n$.

+   In the case where $n$ is zero, we  know that factorial of zero is equal to one which is certainly greater than zero.
+   In the case where $n$ is non-zero, we assume that:

    **IH**: Factorial of $n$ minus one is greater than zero.

    And must prove:

    **Goal**: Factorial of $n$ is greater than zero.

    We observe that since $n$ is non-zero that $n$ factorial is equal to $n$ times $n$ minus one factorial.
    By our induction hypothesis, we know that $n$ minus one is greater than zero, _i.e._, a positive natural number.
    Since $n$ is non-zero, we know from multiplying two positive natural numbers results in a positive natural number, thus justifying our desired result: $n$ times $n$ minus one factorial is indeed greater than zero.
  ~~~

You will probably find the first version of the proof much more readable than this version.
Why is that?
Likely, you see this version of the proof as much more _verbose_ than the first, and it is easy to lose sight of the forest for the trees when you are wading through text.

From this, we see that symbols serve the purpose of acting as _concise shorthand for formal mathematical definitions_.
Symbols pack much information in a small amount of space.
Consequently, we can also overwhelm the reader if we include too many symbols as you likely felt when you first saw logic and natural deduction!
Thus, a _readable_ proof is typically achieved by intermixing prose and symbols, balancing the information we present to the reader to help them remember the salient details, but not overwhelm them.

Where should we sit on the spectrum of formality?
How do we balance readability with all the details that are frequently present in intricate logical arguments?
Just like traditional writing, there are no easy answers here, and the best way to learn is by writing a bunch, and experimenting what works for you as a writing of mathematical arguments!
Throughout the rest of the course, we'll try to hone your proof writing instinct so that you can craft good mathematical prose.

~~~admonish problem title="Exercise (Sequent-to-String, ‡)"
Recall from our lab on natural deduction your proof for this sequent.

**Claim**: $A \vdash (A \rightarrow B) \rightarrow (B \rightarrow C) \rightarrow C$.

You wrote your proof in pure symbols utilizing natural deduction.
Keeping in mind the need to strike a balance between symbols and prose, write a more readable version of your proof by intermixing symbols and prose.
In your writing, try to convey the _big picture_ of the proof while also communicating the critical details, _i.e._, what is the "heart" of the reasoning.
~~~

## Uniting Reasoning and Design

Traditionally, we think of algorithmic design and reasoning as two separate activities.
First, you design an algorithm, and then, in a separate step, you reason about that algorithm.
This is not wrong, but perhaps undesirable in two ways:

+   In terms of workload, you are taking two passes through the code: once for construction and once for verification.
+   By separating the two activities, you are not able to take advantage of one activity to assist in the other.

Recursive design and inductive reasoning, when employed correctly, alleviates these concerns.
To see this, observe the similarities between our recursive design and inductive reasoning templates:

~~~admonish info title="Definition (Recursive design, lists)"
If a function takes a list as input, define the behavior of the function in terms of possible shapes of a list:

+   The list is empty.
+   The list is non-empty with an element at the front of the list (its _head_) and a remaining sublist (its _tail_).
    In designing this case, assume that the function _just works_ when applied recursively to the tail of the list.
~~~

~~~admonish info title="Definition (Inductive proof, lists)"
When proving a property of a function that takes a list as input, prove the property according to the possible shapes of a list:

+   The list is empty.
+   The list is non-empty with an element at the front of the list (its _head_) and a remaining sublist (its _tail_).
    When proving this case, assume an _inductive hypothesis_ that states that the property holds for the tail of the list.
~~~

If we follow these templates, we can _unite_ recursive design and inductive proof, so that when we build a recursive program, we can have some confidence that our program is correct, simply by following the template!
To do this, we not only build a program according to the template, we also have a _property_ in mind that we can use to _check our work_ as we design the algorithm.
We design the program in such a way that the property is "obviously" fulfilled.
This has the dual benefit of not only ensuring we have fulfilled the property, but we also receive concrete guidance as to how to proceed!

# An Example: Designing Map

As an example, let's consider re-designing the standard `map` function over lists.
For example `map((lambda x: x+1), [1, 2, 3, 4, 5]) -->* [2, 3, 4, 5, 6]`.

Before we apply the recursive design template, let's consider a property of `map` that ought to hold.
After some thought, we might note that a simple property of `map` is that it should not change the number of elements in the list.
Thus, we can consider the property:

~~~admonish question title="Claim"
For any list `l` and unary function `f` that takes elements of `l` as input, `length(map(f, l))` ≡ `length(l)`.
~~~

Now let's design the function, `(map f l)`, with this property in mind.
For this function, `f` is a function and `l` is a list.

+   When the input list `l` is empty, our property says that map should produce a list of length `length([]) -->* 0`.
    The only list of this sort is the empty list, so `map` should return an empty list in this case.
+   When is `l` is non-empty, the list has a `head` and `tail`, and we assume that `length(map(f, tail))` ≡ `length(tail)`.
    We know that `length(tail)` is precisely one less than the length of `l`.
    So, if we take the list generated by `map(f, tail)`, we just need to extend it by one additional element.
    To obtain such an element, we can transform `head` by `f` and then `cons` that onto `map(f, tail)`.

Note in our design how we leverage our induction hypothesis, `length(map(f, tail))` ≡ `length(tail)`, to steer the design of the function.

In this sense, we see how _verification_ can directly inform program design.
I argue that you've likely done this when writing your programs whether you were cognizant of it or not!
However, now that we've seen the connection directly, you can begin to leverage this connection whenever you write programs!

~~~admonish problem title="Exercise (Chessing It Up, ‡)"
As we move away from program correctness, we'll apply all our logical techniques to other domains.
As a fun example of this, consider a classical induction problem: _tours on a chessboard_.

Chess is played on a $n \times n$ board (with $n = 8$ usually).
There are a variety of pieces in chess, each of which move in a different way.
For these claims, we will consider two pieces:

+   The rook which can move any number of squares in a cardinal (non-diagonal) direction.
+   The knight which moves in a L-shaped pattern: 2 squares horizontally, 1 square vertically or 2 squares vertically, 1 square horizontally.

Furthermore, we will consider two problems (really, thought experiments because these specific situations never arise in a real chess game) when only one such piece is on the board.

+   The _walk_ is a sequence of moves for a single piece that causes the piece to visit every square of the board.
    It is ok if the piece visits the same square multiple times.
+   A tour is a walk with the additional property that the piece visits every square exactly once.
    In a tour, a piece _cannot_ visit the same square multiple times.

When considering walks and tours, we are free to initially place our piece on the board at any position.
In addition, we only consider a square visited if the piece ends its movement on that square.
With these things in mind, use induction to prove the following fact:

Claim (Rook's Tours). There exists a rook's tour for any chessboard of size $n \geq 1$.

(_Hint_: we need an object to perform induction over.
What inductively defined structures are present in the claim?)
~~~