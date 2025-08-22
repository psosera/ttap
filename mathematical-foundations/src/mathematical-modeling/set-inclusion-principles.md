# Set Inclusion Principles

## Inclusion and Equality

A basic question we can ask about sets is whether one element is contained in a set.
For example:

~~~admonish problem title="Claim"
If $x \in A \cap B$ then $x \in A \cup B$.
~~~

This claim posits that if an arbitrary element of the intersection of $A$ and $B$, it is also in the union of $A$ and $B$.
Intuitively, we know this is true because the intersection of two sets contains elements that are in both sets whereas the union only demands that the elements are in _at least one_ of the sets.

To formally prove this claim, we will work from our initial assumption that $x \in A \cap B$ and proceed _forwards_ to our goal that $x \in A \cup B$.
To do so, we will utilize the formal definitions of our operators to justify each step of our reasoning explicitly.
Here is a formal proof of the claim above.

~~~admonish check title="Proof"
_Proof_.
We suppose that $x \in A$ and show that $x \in A \cup B$.
By the definition of $(\cup)$, we must show that $x \in A \vee x \in B$.
However, we already know that $x \in A$ by assumption.
~~~

Alternatively, we can present the same proof using a _two-column_ style where each row consists of a _fact_ on the left-hand side and a _rule_ on the right-hand side that justifies how the fact is derivable from the fact on the previous row.

~~~admonish check title="Proof (two-column format)"
$$
\begin{align*}
x \in A        & & [\text{assumption}] \\
x \in A \cup B & & [\text{def. $(\cup)$}]
\end{align*}
$$
~~~

In $\LaTeX$, we can use the `\begin{align*} ... \end{align*}` environment to format the proof in this two-column style.
For example, the following LaTeX code produces the above math prose:

~~~latex
\begin{align*}
x \in A        & & [\text{assumption}] \\
x \in A \cup B & & [\text{def. \$(\cup)\$}]
\end{align*}
~~~

Note that supposing that we have an arbitrary $x \in A$ is equivalent to proving a claim that is universally quantified (_i.e._, $∀$), over that variable $x$.
Therefore this proof also shows that $A \subseteq A \cup B$ as the subset proposition is equivalent to:

$$
A \subseteq A \cup B ≡ ∀x \ldotp x \in A \rightarrow x \in A \cup B.
$$

Our natural deduction rules tells us that to prove this logical proposition, we:

1.  Assume an arbitrary $x$ by the **intro (∀)** rule.
2.  Assume that $x \in A$ by the **intro (→)** rule.
3.  Go on to prove that $x \in A \cup B$.

This is precisely how our proofs above proceeded!
In our prose-based proof, we explicated this reasoning although did not cite natural deduction rules justifying the reasoning.
At this higher level of proof, we don't cite rules of logic although we know that our reasoning is backed by them.
Our symbolic, two-column proof avoids this verbiage, leaving the introductory steps of reasoning implicit so that we can focus on the important parts of the proof: the step-by-step manipulation of sets.

In practice, because we will prove membership of an arbitrary element of a set, we will usually state our claims in terms of _subset relationships_.
For example, here is a similar claim and proof to our original one, but utilizing subset notation instead:

~~~admonish check title="Proof"
**Claim**: $A \cap B \subseteq B$

_Proof_:
Let $x \in A \cap B$.
It suffices to show that if $x \in A \cap B$ then $x \in B$.
However, we know that $x \in A \vee x \in B$ by the definition of $(\cap)$, allowing us to conclude that $x \in B$.
~~~

### Proving Set Inclusion Claims

In summary, when proving that a set $S$ is a subset of another set $T$, we:

1. Assume that we have an arbitrary element $x$ of the set $S$.
2. Give a proof that shows how we can logically reason step-by-step from this initial assumption to our final goal.
3. End our proof by showing that $x$ is an element of $T$, thereby proving our claim.

In logic, this is called *forwards reasoning* because we are reasoning from our assumptions and axioms to our final goal.
This contrasts with our program correctness and natural deduction proofs where we tended to work from our initial goal and generate new assumptions and refined goals from it, a process called _backwards reasoning_.
Note that both forms of reasoning—from assumptions or from our goal—are valid and can be intermixed in a single proof.
Ultimately, whether we operate in a forwards or backwards manner in our proofs is a function of _context_: the domain of the proof and the particular proof state that we are in.

As with all proofs, our proofs in set theory consist of _assumptions_ and a _goal_.
Our assumptions take on various forms:

+ Element inclusion, _e.g._, $x \in A$.
+ Subsets, *e.g.*, $S \in T$.
+ Equality, *e.g.*, $x = (y, z)$ or $S = T \cap V$.

Like propositional logic, how we reason about our different set operations depends on whether the operation appears in an assumption (something we already know) or a goal (something we are trying to prove).
As an assumption:

+ If we know $x \in S \cup T$ then $x$ is in either $S$ or $T$.
+ If we know $x \in S \cap T$ then $x$ is in both $S$ and $T$.
+ If we know that $x \in S - T$ then $x$ is in $S$ and not in $T$.
+ If we know that $x \in {\overline{S}}$ then we know $x$ is not in $S$.
+ If we know that $x \in S × T$ then we know that $x = (s, t)$ where $s \in S$ and $t \in T$.
+ If we know that $x \in \mathcal{P}(S)$ then we know that $x \subseteq S$.

All of these rules of inference follow directly from our formal definitions for our operations.
Likewise, if these operations instead appear as our goal:

+ If we must show $x \in S \cup T$ then we must show $x$ is in either $S$ or $T$.
+ If we must show $x \in S \cap T$ then we must show that $x$ is in both $S$ and $T$.
+ If we must show $x \in S - T$ then we must show that $x$ is in $S$ and not in $T$.
+ If we must show $x \in {\overline{S}}$ then we must show $x$ is not in $S$.
+ If we must show $x \in S × T$ then we must show that $x = (s, t)$, $s \in S$, and $t \in T$.
+ If we must show $x \in \mathcal{P}(S)$ then we show that $x \subseteq S$.

To show these different rules in action, consider the following claim and proof over a more complicated subset relationship:

~~~admonish check title="Proof"
**Claim**: $A × (B \cup C) \subseteq (A × B) \cup (A × C)$

_Proof_:
Let $(x, y) \in A × (B \cup C)$ with $x \in A$ and $y \in B \cup C$.
By the definition of $(×)$, it suffices to show that $(x, y) \in (A × B) \cup (A × C)$.
And by the definition of $(\cap)$, we know that $y \in B ∨ y \in C$.
Now consider whether $y \in B$ or $y \in C$.

+   If $y \in B$, then by the definition of $(×)$, $(x, y) \in A × B$ and by the definition of $(\cup)$, $(x, y) \in (A × B) \cup (A × C)$.
+   If $y \in C$, then by the definition of $(×)$, $(x, y) \in A × C$ and by the definition of $(\cup)$, $(x, y) \in (A × B) \cup (A × C)$.
~~~

Note several things with this proof:

+   When we know that an element a member of a union, we can perform _case analysis_ to refine which set that element comes from.
+   When we show that an element is in a Cartesian product, we must show that it is a pair and that each of the pair’s components come from the appropriate sets.
    Because the justification for these parts may not all come from the previous line of the proof, we state which of the lines these justifications come from.

### Equality Proofs

Recall that we defined set equality in terms of subsets:

$$
  S = T \stackrel{\mathsf{def}}{=} S \subseteq T ∧ T \subseteq S.
$$

Thus, to prove that two sets are equal, we need to perform two subset proofs, one in each direction.
In the previous section, we proved that $A × (B \cup C) \subseteq (A × B) \cup (A × C)$.
By showing that $(A × B) \cup (A × C) \subseteq A × (B \cup C)$, we can then conclude that the two sets are indeed equal.
Here is a two-column proof of the right-to-left direction of the claim:

~~~admonish check title="Proof"
**Claim**: $(A × B) \cup (A × C) \subseteq A × (B \cup C)$.

_Proof_:
Let $x \in (A × B) \cup (A × C)$.
Consider whether $x \in A × B$ or $x \in A × C$.

+   Suppose $x \in A × B$.

    $$
    \begin{align*}
    x \in (A × B)                   & & [\text{assumption}] \\
    x = (a, b), a \in A, b \in B    & & [\text{def. (×)}] \\
    b \in B \cup C                  & & [\text{def. ($\cup$)}] \\
    (a, b) \in A × (B \cup C)       & & [\text{def. (×)}].
    \end{align*}
    $$

    Now suppose $x \in A × C$.

    $$
    \begin{align*}
    x \in (A × C)                   & & [\text{assumption}] \\
    x = (a, c), a \in A, c \in C    & & [\text{def. (×)}] \\
    c \in B \cup C                  & & [\text{def. ($\cup$)}] \\
    (a, c) \in A × (B \cup C)       & & [\text{defn (×)}].
    \end{align*}
    $$
~~~

We call such equality proofs _double-inclusion proofs_.
Double-inclusion or _proving "both sides" of the equality_ is a powerful, alternative technique for showing that two objects are equal.
While it is the primary way we show the equality of sets, we can also apply it to other "equality-like" operations.
For example:

+   To show that two _logical propositions_ are equivalent, $p \equiv q$, we can show $p \rightarrow q$ and $q \rightarrow p$.
+   To show that two _numbers_ are equivalent, $x = y$, we can show $x \leq y$ and $y \leq x$.

### Empty Set Proofs

Our proof techniques for set inclusion runs into a snag when we consider the empty set.
For example, consider the following claim:

~~~admonish problem title="Claim"
**Claim**: $A \cap {\overline{A}} = ∅$.
~~~

Intuitively, ${\overline{A}}$ contains precisely the elements that are not in $A$.
Thus, we expect the intersection to be empty.
To prove this equality, we must show that the left- and right-hand sides are subsets of each other.
In one direction, this proof proceeds trivially:

~~~admonish check title="Proof"
**Claim**: $∅ \subseteq A \cap {\overline{A}}$

_Proof_.
There is no such $x$ such that $x \in ∅$, so the claim is _vacuously_ true.
~~~

Recall that the definition of subset says that $A \subseteq B \stackrel{\mathsf{def}}{=} ∀x \ldotp x \in A → x \in B$.
No elements are contained in $∅$ by definition, so the logical proposition holds trivially, _i.e._, there are no $x$ that fulfill $x \in A$.

In the other direction, we become stuck with our standard proof machinery:

~~~admonish check title="Proof"
**Claim**: $A \cap {\overline{A}} \subseteq ∅$

_Proof_.
We assume that $x \in A \cap \overline{A}$.
We must show that $x \in ∅$. But… .
~~~

We begin the proof by assuming $x \in A \cap {\overline{A}}$.
Note that we know this is not possible because the intersection should be empty, but this is precisely what we are trying to prove!
However, we encounter a worse problem: our proof requires us to show that $x \in ∅$ and that is certainly impossible!

Because of this, we need an alternative proof strategy to prove set emptiness—that a set is equivalent to the empty set.
The strategy we'll employ is our final fundamental proof technique, _proof by contradiction_.

To prove that a proposition $P$ holds using a proof by contradiction:

1.  We assume $¬P$ is provable.
2.  We then show how this assumption allows us to prove a contradiction, *i.e.*, $⊥$ or $Q ∧ ¬Q$ for some proposition $Q$.
3.  Because we cannot logically conclude a contradiction holds and our proof proceeds logically, the only thing that could have caused the contradiction was our initial assumption that $¬P$ holds.
    Therefore, $¬P$ must not hold and so $P$ must hold.

We will apply the technique of proof by contradiction to set emptiness proofs where we show that some set $S = ∅$ as follows:

1.  First assume for the sake of contradiction that $x \in S$.
2.  Then we will show a contradiction.
    In the context of set theory, this usually means showing that some element $y$ (not necessarily $x$) is both in a set and not in a set, *e.g.*, $y \in U ∧ y ∉ U$.
3.  From this contradiction, we can conclude that our assumption that $x \in S$ is false and thus $x ∉ S$ for all $x$ and thus $S = ∅$.

Let us use this proof technique to show that our claim above holds directly without the use of subsets.

~~~admonish check title="Proof"
**Claim**: $A \cap \overline{A} = ∅$.

_Proof_.
We prove this claim by assuming that some $x \in A \cap \overline{A}$ and deriving a contradiction.

$$
\begin{align*}
x \in A \cap \overline{A}   & & [\text{assumption}] \\
x \in A, x \in \overline{A} & & [\text{def. ($\cap$)}] \\
x ∉ A                       & & [\text{def. ($\overline{⋅}$)}]
\end{align*}
$$

$x \in A$ and $x ∉ A$ cannot both be true.
Our original assumption that there exists an $x \in A \cap \overline{A}$ must then be false and thus no such $x$ exists.
Therefore, $A \cap \overline{A} = ∅$.
~~~

~~~admonish problem title="Exercise (Arr, ‡)"
Prove the left-to-right direction of DeMorgan's Law:

**Claim**: $\overline{A \cup B} \subseteq \overline{A} \cap \overline{B}$.
~~~
