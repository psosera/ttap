# Case Study: Finite Automata

Graphs are a foundational data structure in many areas of computer science.
To close this portion of the course, we'll take a look at a particular application of graphs towards an older theme: program correctness.
We'll use graphs to develop a simple model from the _theory of computation_, the _finite automata_.
While simple, the model captures a wide variety of computations that we might consider in a computer program.
This model will put together everything we have learned so far towards the task of using mathematics productively in programming and tease at what future courses in the foundations of computer science will cover!

## Strings

A finite automata is an abstract machine that consumes strings of characters as input and produces a boolean, a _yes_ or _no_ answer, as output.
Before we explore the finite automata itself, we must first formalize the notion of a string.

~~~admonish title="Definition (String)"
A _string_ $s$ drawn from an alphabet $\Sigma$ is a sequence of zero or more characters drawn from $\Sigma$, _i.e._, $s = x_1 \cdots x_k$ where $x_1, \ldots, x_k \in \Sigma$.
~~~

Note that when we write $s = x_1 \cdots x_k$, this implicitly binds $x_1, \ldots, x_k$ to the individual characters of $s$.
Because there are $k$ variables bound in this manner, we know that the string has length $k$.

Unlike strings in a programming language, we explicitly define the set of possible characters that make up our strings.
We call this set the _alphabet_ under consideration and denote it with $\Sigma$ ($\LaTeX$: `\Sigma`).
Here are some examples of alphabets and possible strings drawn from those alphabets:

~~~admonish info title="Examples"
**Example 1**: let $\Sigma_1$ be the set of the 26 lowercase English letters and the space characters.
Then $s = \text{hello world}$ is a string drawn from $\Sigma_1$.

**Example 2**: let $\Sigma_2 = \set{ 0, 1 }$.
Then $s = 010010110$ is a string drawn from $\Sigma_2$.
Strings drawn form $\Sigma_2$ are _binary strings_, _i.e._, sequences of 1s and 0s.

**Example 3**: note that the definition of string is a sequence of _zero_ or more characters.
Therefore, the _empty string_, written $\epsilon$ ($\LaTeX$: `\epsilon`), is a string drawn from _any_ alphabet, including $\Sigma_1$ or $\Sigma_2$ as defined above.
~~~

In a conventional programming language, we would write the empty string $\epsilon$ as `""`.
However, since we traditionally do not use quotes to delineate strings, we must rely on a special symbol to denote the empty string.

# Overview of Finite Automata

Now let's look at our first finite automata to get a feel for this sort
of abstract machine.

~~~
    ┌─────────┬────────┐
    │         a        b
    ↓         │        │
-→ (q0) -a→ (q1) -b→ (q2) -a→ [q3]──┐
   │  ↑                         ↑  a,b
   └b-┘                         └───┘
~~~

This simple finite automata recognizes strings drawn from the alphabet $\Sigma = \set{a, b}$.
We can think of a finite automata as a _labeled, directed graph_ where the nodes are _states_ and the edges are _transitions between states_.

Informally, a finite automata operates as follows:

1.  The machine begins initially in its _start state_, denoted in the graph above as the state $q_0$ with an incoming edge but no out-going state.
2.  Next it reads in an input string character-by-character.
    As it reads each character, the machine _transitions_ from its current state to a new state by moving along the edge annotated with the character that was read in.
3.  Once the input string is completely consumed, we check to see what state the machine ended on.
    If the state is an _accepting state_, then the machine _accepts the input string_, _i.e._, returns "true."
    Otherwise, the machine _rejects the input string_, _i.e._, returns "false."
    In our example, we denote a final state in brackets rather than parentheses, so $q_3$ above is an accepting state whereas all the other states are normal.

As an example, here is how the machine operates over the string $ababa$:

+   The machine initially starts in $q_0$.
+   The machine reads an $a$ and transitions from $q_0$ to $q_1$.
+   The machine reads a $b$ and transitions from $q_1$ to $q_2$.
+   The machine reads an $a$ and transitions from $q_2$ to $q_0$.
+   The machine reads a $b$ and transitions from $q_0$ back to $q_0$.
+   The machine reads an $a$ and transitions from $q_0$ back to $q_1$.

So when reading the string $ababa$, the machine ends on state $q_1$.
$q_1$ is not an accepting state, so the automata rejects this string.
In contrast, the machine would accept the string $bbababa$.

~~~admonish problem title="Exercise (Simulation)"
Trace the execution of the finite automata above on the input string $bbababa$ to show that the automata accepts this string.
~~~

With some effort, we can also verify that the strings:

+   $bbbababbb$,
+   $abab$, and
+   $aba$,

Are accepted by this automata, whereas the strings

+   $bbbb$,
+   $aaaa$, and
+   The empty string $\epsilon$

Are _not_ accepted by this automata.

What are the set of strings that the automata accepts?
It turns out that the automata accepts any string that contains $aba$!
We can observe this by inspecting the states and transitions of the automata and ask the question: how do we reach the acceptance state $q_3$?

We can only reach this state by moving from $q_0$ through $q_1$ and $q_2$ and finally to $q_3$.
According to the transitions, we can only do so by reading in the characters $a$, $b$, and $a$ in order.
Also note that:

+   If we have not seen $aba$ yet, then any character not involved in this pattern returns us to $q_0$, _i.e._, our search for this
    substring resets.
+   Once we have seen $aba$ and land in $q_3$, anything that we read keeps us in this acceptance state.
    In other words, once we reach $q_3$, we will always accept the string!

# Finite Automata Formally Defined

Now that we have a high-level idea of how finite automata operate, let's look at their formal description.

~~~admonish title="Definition (Finite Automata)"
A (deterministic) finite automata $D$ is a 5-tuple $D = (Q, \Sigma, q_0, \delta, F)$ where:

+   $Q$ is the set of states.
+   $\Sigma$ is the alphabet.
+   $q_0 \in Q$ is the initial state.
+   $\delta : Q \times \Sigma \rightarrow Q$ is the state-transition function.
+   $F \subseteq Q$ is the set of accepting states.
~~~

Note that even though the automata is a directed graph, we do not model it directly as such!
Instead, we model it as a collection of five components---the set of states, alphabet, initial state, transition function, and accepting states.
The graph-like nature of an automata is _inferred_ by this choice of representation:

+   States are the nodes.
+   The transition function contains the (directed) edges.

In particular, note that the transition function, when viewed as a relation, can be thought of a set of pairs, just like the edges of a graph!

Our example automata from above can be formally represented as follows:

+   $Q = \set{ q_0, q_1, q_2, q_3 }$.
+   $\Sigma = \set{ a, b }$.
+   $q_0$ is the initial state.
+   $F = \set{ q_3 }$.

The transition function is defined by the following transition pairs
expressed in a _state transition table_:

|       |  $a$  |  $b$  |
| ----- | ----- | ----- |
| $q_0$ | $q_1$ | $q_0$ |
| $q_1$ | $q_0$ | $q_2$ |
| $q_2$ | $q_3$ | $q_0$ |
| $q_3$ | $q_3$ | $q_3$ |

For example, in state $q_0$, if the automata reads an $a$, then the automata transitions to state $q_1$.

~~~admonish info title="Example"
**Example**: define an automata $D = (Q, \Sigma, q_\mathsf{even}, \delta, F)$ as follows:

+   $\Sigma = \set{ 0, 1 }$.
+   $Q = \set{ q_\mathsf{even}, q_\mathsf{odd} }$.
+   $F = \set{ q_\mathsf{even} }$.

Observe that there are two states and two characters of our alphabet.
Therefore, we expect there to be $2 \times 2 = 4$ state-transition pairs in $\delta$.
We'll, therefore, define the transition function $\delta$ by cases as follows:

$$
\begin{align*}
\delta(q_\mathsf{even}, 0) =&\; q_\mathsf{odd} \\
\delta(q_\mathsf{even}, 1) =&\; q_\mathsf{even} \\
\delta(q_\mathsf{odd}, 0)  =&\; q_\mathsf{even} \\
\delta(q_\mathsf{odd}, 1)  =&\; q_\mathsf{odd} \\
\end{align*}
$$
~~~

~~~admonish problem title="Exercise (Formal Picture)"
Draw the graph representation of the automata $D$ from the example above.
~~~

## Acceptance

In order to formally verify that an automata is correct, we also need to formalize the notion of acceptance.
Acceptance has a somewhat complicated definition; let's take a look:

~~~admonish info title="Definition (Acceptance)"
Consider a finite automata $D = (Q, q_0, \Sigma, \delta, F)$ and $s = x_1 \cdots x_k$ be a string drawn from $\Sigma$. We say that automata $D$ _accepts_ string $s$ if there exists a sequence of states $q_1, \ldots, q_k \in Q$ where:

+   $\forall i \ldotp 0 \leq i < k \rightarrow \delta(q_i, x_1) = q_{i+1}$ and
+   $q_k \in F$.
~~~

Intuitively, this definition says that an automata accepts a string if the string drives the automata from its starting state to a final state.

~~~admonish problem title="Exercise (Back and Forth)"
Take this intuition about what the formal definition of automata acceptance is saying and try to map the intuition
on the symbols.
In particular, how does the formal definition capture the idea that the input string "drives the automata from its starting state to a final state?"
~~~

~~~admonish problem title="Exercise (Scrutinize)"
According to the formal definition of acceptance, does an automata accept a string if during execution of that string, the automata enters a final state, but is _not_ in a final state at the execution?
~~~

~~~admonish problem title="Exercise (Spell It Out, ‡)"
Consider the formally defined automata $D$ in the example above that reads binary strings.
Apply the definition of acceptance to show that $D$ accepts the string $01100101$.

(_Hint_: what does the formal definition of acceptance say we must construct and what must we show about this construction to show that $D$ accepts the string?)
~~~