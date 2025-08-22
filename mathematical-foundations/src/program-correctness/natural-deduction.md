# Natural Deduction

So far we have discussed the objects of study of logic, the _proposition_, defined recursively as:

$$
p ::= A(x) \mid \top \mid \bot \mid \neg p \mid p_1 \wedge p_2 \mid p_1 \vee p_2 \mid p_1 \rightarrow p_2 \mid \forall x.\,p \mid \exists x.\,p
$$

However, recall that our purpose for studying propositional logic was to develop rules for developing logically sound arguments.
Equivalences, while convenient to use in certain situations, _e.g._, rewriting complex boolean equations, do not translate into what we would consider to be "natural" deductive reasoning.

Now, we present a system for performing _deductive reasoning_ within propositional logic.
This system closely mirrors our intuition about how we would prove propositions, thus giving it the name _natural deduction_.
As we explore how we formally define deductive reasoning in this chapter, keep in mind your intuition about how these connectives ought to work to help you navigate the symbols that we use to represent this process.

## The Components of a Proof

To understand what are the components proof that we capture in natural deduction, let us scrutinize a basic proof involving the even numbers.
First we remind ourselves what it means for a number to be even.

~~~admonish info title="Definition (Evenness)"
Call a natural number $n$ even if $n = 2k$ for some natural number $k \geq 0$.
~~~

In other words, an even number is a multiple of two.
This intuition informs how we prove the following fact:

~~~admonish check title="Claim"
**Claim**: For any natural number $n$, if $n$ is even then $4n$ is also even.

_Proof_.
Let $n$ be an even natural number.
Because $n$ is even, by the definition of evenness, $n = 2k$ for some natural number $k$.
Then:

$$
4n = 4(2k) = 8k = 2(4k).
$$

So we can conclude that $2n$ is also even by the definition of evenness because it can be expressed as $2m$ where $m = 4k$.
~~~

From the above proof, we can see that there is an overall proposition that we must prove, called the **goal proposition**:

> For any natural number $n$, if $n$ is even then $4n$ is also even.

As the proof evolves, our goal proposition _changes_ over time according to the **steps of reasoning** that we take.
For example, implicit in the equational reasoning we do in the proof:

$$
4n = 4(2k)
$$

Is a _transformation_ of our goal proposition, "$4n$ is even," to another proposition "$4(2k)$ is even."
Ultimately, our steps of reasoning transform our goal repeatedly until the goal proposition is "obviously" provable.
In the proof above, we transform our goal from "$4n$ is even" to "$2(4k)$ is even" by the rules of arithmetic.
At this point, we can apply the definition of evenness directly which says that a number is even if it can be expressed as a multiple of two.

However, in addition to the goal proposition, there are also **assumptions** that we acquire and use in the proof.
For example, the proposition "$n$ is even" becomes an assumption of our proof after we process the initial goal proposition which is stated in the form of an implication, $P \rightarrow Q$, where:

+   $P =$ "$n$ is even"
+   $Q =$ "$4n$ is even"

We then _utilize_ this assumption in our steps of reasoning.
In particular, it is the assumption that $n$ is even coupled with the definition of evenness that allows us to conclude that $n = 2k$ for some natural number $k$.
There exists other assumptions in our proof as well, _e.g._, $n$ and $k$ are natural numbers, that we use implicitly in our reasoning.

### Proof States and Proofs

We can crystallize these observations into the following definition of "proof."

~~~admonish check title="Definition (Proof State)"
The **state of a proof** or **proof state** is a pair of a _set of assumptions_ and a _goal proposition_ to prove.
~~~

~~~admonish check title="Definition (Proof)"
A **proof** is a sequence of logical steps that manipulate a proof state towards a final, provable result.
~~~

In our above example, we initially started with a proof state consisting of:

+   No assumptions.
+   The initial claim as our goal proposition: "For any natural number $n$, if $n$ is even then $4n$ is also even."

After some steps of reasoning that breaks apart this initial goal, we arrived at the following proof state:

+   Assumptions: "$n$ is a natural number," "$n$ is even"
+   Goal: "$4n$ is even."

At the end of the proof, we rewrote the goal in terms of a new variable $m$ and performed some factoring, leading to the final proof state:

+   Assumptions: "$n$ and $m$ are natural numbers," "$n$ is even," "$m = 4k$"
+   Goal: "$2m$ is even"

This final goal is provable directly from the definition of evenness since the quantity $2m$ is precisely two times a natural number.

## Rules of Natural Deduction

Our proof rules manipulate proof states, ultimately driving them towards goal propositions that are directly provable through our assumptions.
Because of the syntax of our propositions breaks up propositions into a finite set of cases, our rules operate by case analysis on the syntax or _shape_ of the propositions contained in the proof state.
Specifically, these propositions can either appear as _assumptions_ or in the _goal proposition_.
Therefore, for each kind of proposition, we give one or more rules describing how we "process" that proposition depending on where it appears in the proof goal.

+   Rules that operate on propositions in the goal proposition are called _introduction rules_.
    This is because we typically first _introduce_ various propositions when they initially appear in our goal.
+   Rules that operate on propositions in assumptions are called _elimination rules_.
    These rules use or "consume" assumptions, resulting in more assumptions and/or an updated set of goals.

### A Note on the Presentation of Proof Rules

When we talk about _rules of proof_ we really mean the set of "allowable actions" that we can take on the current proof state.
We describe these rules in the following form:

> To prove a proof state of the form … we can prove proof state(s) of the form …

One of our proof rules _applies_ when it has the form specified by the rule, _e.g._, the goal proposition is a conjunction or there is an implication in the assumption list.
The result of the proof rule is one or more _new_ proof states that we must now prove instead of the current state.
In effect, the proof rule _updates_ the current state to be a new state (or set of states) that becomes the new proof state under consideration.

In terms of notation, we will use $p$ and $q$ to denote arbitrary propositions.
Similarly, for assumptions, we will use the traditional metavariable $\Gamma$ (_i.e._, Greek uppercase Gamma, $\LaTeX$: `\Gamma`) to represent an arbitrary set of assumptions.
When writing down our proof rules, it is very onerous to describe the components of proof states in prose:

> If our proof state contains assumptions $\Gamma$ and goal proposition $p$ … .

Instead, we will write down a proof state as a _pair_ of a set of assumptions and a proof state.
Traditionally, we write pairs using parentheses, separating the components of the pair with commas, _e.g._, a coordinate pair $(x, y)$.
However, in logic, we traditionally write this pair as a stylized pair called a _sequent_:

$$
\Gamma \vdash p.
$$

The turnstile symbol ($\LaTeX$, `\vdash`) acts like the comma in the coordinate pair; it merely creates visual separation between the assumptions $\Gamma$ and the goal proposition $p$.

### Conjunction and Assumptions

A conjunction, $p_1 \wedge p_2$, is a proposition that asserts that _both_ propositions $p_1$ and $p_2$ are provable.
For example, the proposition "I'm running out of cheeseburgers and the sky is falling" is a conjunction of two propositions: "I'm running out of cheeseburgers" and "the sky is falling."
We call each of the sub-propositions of a conjunction a _conjunct_.

First, let's consider how we _prove_ a proposition that is a conjunction.
If we have to prove the example proposition above, our intuition tells that we must prove both "sides" of the conjunction.
That is, to show that the proposition "I'm running out of cheeseburgers and they sky is falling", we must show that both

+   "I'm running out of cheeseburgers" and
+   "the sky is falling"

Are true _independently_ of each other.
In particular, we don't get to rely on fact being true when going to prove the other fact.

We can codify this intuition into the following _proof rule_ describing how we can process a conjunction when it appears as our proof goal:

~~~admonish info title="Proof Rule [intro-∧]"
To prove $\Gamma \vdash p_1 \wedge p_2$, we may prove the following two proof states separately:

+   $\Gamma \vdash p_1$.
+   $\Gamma \vdash p_2$.
~~~

Recall that $\Gamma \vdash p_1 \wedge p_2$ represents the following proof state:

+   We assume that the assumptions in set $\Gamma$ are provable.
+   Our goal proposition we must prove is $p_1 \wedge p_2$.

Our proof rule says that whenever we have a proof state of this form, we can prove it by proving $\Gamma \vdash p_1$ and $\Gamma \vdash p_2$ as two separate cases.
More generally, our _introduction rules_ describe, for each kind of proposition, how to prove that proposition when it appears as a goal.

Note that this proof rule applies only when the _overall goal proposition is of the form of a conjunction_.
For example:

+   If our goal is $A \wedge B$, then the intro-∧ rule applies with $p_1 = A$ and $p_2 = B$.
+   If our goal is $(A \rightarrow B) \wedge (C \wedge D)$, then the intro-∧ rule applies with $p_1 = A \rightarrow B$ and $p_2 = C \wedge D)$.

This rule cannot be applied if only a _sub-component_ of the goal proposition is a conjunction.
For example, if our goal is $A \rightarrow (B \wedge C)$, the rule does not apply even though $B \wedge C$ is part of the goal.

Now, what happens if the conjunction appears to the left of the turnstile, _i.e._, as an assumption?
From our **intro-∧** rule, we know that if the conjunction is provable, then _both_ conjuncts are provable individually.
So if we know that "I'm running out of cheeseburgers and the sky is falling" is provable, then we know that both conjuncts are true, _i.e._, "I'm running out of chesseburgers" and "the sky is falling."
The following pair of rules captures this idea:

~~~admonish info title="Proof Rule [elim-∧-left]"
To prove $\Gamma \vdash p$, if $p_1 \wedge p_2 \in \Gamma$, then we may prove $p_1, \Gamma \vdash p$.
~~~

~~~admonish info title="Proof Rule [elim-∧-right]"
To prove $\Gamma \vdash p$, if $p_1 \wedge p_2 \in \Gamma$, then we may prove $p_2, \Gamma \vdash p$.
~~~

If we are trying to prove some proposition $p$ and we assume that a conjunction $p_1 \wedge p_2$ is true, then the two rules say we can continue trying to prove $p$, _but with an additional assumption gained from the conjunction_.
In terms of the new notation in these rules:

+   To state that $p_1 \wedge p_2$ is contained in $\Gamma$, we write $p_1 \wedge p_2 \in Gamma$ where the $(\in)$ symbol ($\LaTeX$, `\in`) is pronounced "in."
+   To add a new assumption to the set of assumptions $\Gamma$, we "cons" on the new assumption, _e.g._, $p_1$ in the elim-∧-left rule, by adding it to the front of $\Gamma$ with a comma, _i.e._, $p_1, \Gamma$.

The **elim-∧-left** rule allows us to add the left conjunct to our assumptions, and the **elim-∧-right** rule allows us to add the right conjunct.
In effect, these _elimination rules_ allow us to _decompose_ or _extract information_ from assumptions.
However, how do we use these assumptions to prove a goal?
The **assumption** rule allows us to prove a goal proposition directly if it is one of our assumptions.

~~~admonish info title="Proof Rule [assumption]"
If $p \in \Gamma$, then $\Gamma \vdash p$ is proven immediately.
~~~

### Proofs in Natural Deduction

First-order logic and natural deduction give us all the definitions necessary for rigorously defining every step of reasoning in a proof.
Let's see this in action with a simple claim within formal propositional logic.

**Claim**: $A, B \vdash A \wedge B$.

First, let's make sure we understand what the claim says.
To the left of the turnstile are our set of assumptions.
Here we assume that propositions $A$ and $B$ are provable.
To the right of the turnstile is our goal.
We are trying to prove that $A \wedge B$ is provable.

Next, let's develop a high-level proof strategy.
Because we are working at such a low-level of proof, it is important to ask ourselves:

+   Is the proof state as I understand it provable?
+   How do the different parts of the proof state relate to each other?

In our example claim, we see that the proposition consists of atomic propositions $A$ and $B$ joined by conjunction and those same propositions appear as assumptions in the initial sequent.
Thus, our overall strategy in our proof will be to _decompose_ the proof goal into cases where we need to prove these individual cases directly via our hypotheses.

With this in mind, let's see what the formal proof looks like:

~~~admonish check title="Proof"
**Claim**: $A, B \vdash A \wedge B$.

_Proof_.
By the intro-∧ rule, we must prove two new goals:

+   Case $A, B ⊢ A$.
    $A$ is an assumption, so we are done.
+   Case $A, B ⊢ B$.
    $B$ is an assumption, so we are done.
~~~

Because each proof rule creates _zero or more_ additional proof states that we must prove or _discharge_, our proofs take on a _tree-like shape_.
In a diagram, our reasoning would look as follows:

~~~
      A, B |- A ∧ B
        /        \
  [intro-∧ (1)]   [intro-∧ (2)]
      /            \
 A, B |- A     A, B |- B
    /                \
[assumption: A]  [assumption: B]
~~~

Because the **intro-∧** rule creates two sub-goals we must prove, we have two branches of reasoning emanating from our initial proof state, one for each goal (labeled `(1)` and `(2)`, respectively).
We then prove each branch immediately by invoking the appropriate assumption.
_Every_ proof, not just in formal logic but in any context, has a hierarchical structure like this.

We can write this hierarchical structure in linear prose with a bulleted list where indentation levels correspond to branching.
Whenever we perform case analysis, we should be clear when we are entering different cases, usually through some kind of sub-heading or bullet-like structure.

Finally, note that we cite _every_ step of our proof.
This "luxury" is afforded to us because our proof rules are now explicit and precise---we can justify every step of reasoning as an invocation of one of our proof rules!

~~~admonish question title="Exercise (Adaption)"
Formally prove the following claim in propositional logic:

**Claim**: $A, B \vdash A \wedge B \wedge A.$

(_Hint_: remember that $(\wedge)$ is a left-associative operator!)
~~~

### Implication

Next, let's look at implication.
An implication, $p_1 \rightarrow p_2$, is a proposition that asserts that whenever $p_1$ is provable, then $p_2$ is provable as well.
For example, the proposition "If I'm running out of cheeseburgers then the sky is falling" is an implication where "I'm running out of cheeseburgers" is the _premise_ of the implication and "the sky is falling" is the _conclusion_.

Implication is closely related to the preconditions and postconditions we analyzed in our study of program correctness.
Pre- and postconditions form an implication where the preconditions are premises and the postcondition is a conclusion.
When we went to prove a claim that involved pre- and postconditions, we assumed that preconditions held and went on to prove the postcondition.
Likewise here, to prove an implication, we _assume_ the premises and then go on to _prove_ the conclusion.

~~~admonish info title="Proof Rule [intro-→]"
To prove $\Gamma \vdash p_1 \rightarrow p_2$ we must prove $p_1, \Gamma \vdash p_2$.
~~~

What do we do if the implication appears as a hypothesis, _i.e._, to the left of the turnstile, rather than the right?
We saw this process, too, in our discussion of program correctness.
If we had an auxiliary claim that we proven that was the form of a conditional, or if our induction hypothesis was a conditional, we needed to first prove the preconditions and then we could assume that the conclusion held.
Thus, to use an assumed implication, we must first prove its _premise_ and then we can _assume_ the conclusion as a new hypothesis:

~~~admonish info title="Proof Rule [elim-→]"
To prove $\Gamma \vdash p$, if $p_1 \rightarrow p_2 \in \Gamma$, then we may prove both $\Gamma \vdash p_1$ and $p_2, \Gamma \vdash p$.
~~~

After using **elim-→**, we must prove two proof goals:

1.  The first requires us to prove that the premise of the assumed implication is provable.
2.  The second requires us to prove our original goal, but with the additional information of the conclusion of implication.

If you are familiar with logic already, _e.g._, from a symbolic logic class, you should recognize **elim-→** as the _modus ponens_ rule that says:

> If $p$ implies $q$ and $p$ is true, then $q$ is true as well.

Here is a pair of simple example proofs that illustrate the basic usage of these rules:

~~~admonish check title="Proof"
**Claim**: $⋅ \vdash A \rightarrow A$.

_Proof_.
By intro-→, we first assume that $A$ is provable and go on to show that $A$ is provable.
However, this is simply the assumption that we just acquired.
~~~

~~~admonish check title="Proof"
**Claim**: $A \rightarrow B, A \vdash B$.

_Proof_.
By elim-→, we must show that $A$ is provable and then we may assume $B$ is provable.
$A$ is provable by assumption and the goal $B$ is provable by the assumption we gained from eliminating the assumption.
~~~

Compare the flow of these proofs with the rules presented in this section to make sure you understand how the rules translate into actual proof steps.

### Disjunction

Now, let's look at disjunction.
A disjunction, $p_1 \vee p_2$, is a proposition that asserts that _one or both_ of $p_1$ and $p_2$ are provable as well.
For example, the proposition "I'm running out of cheeseburgers or the sky is falling" is a disjunction where at least one of "I'm running out of cheeseburgers" and "the sky is falling" must be provable.

Again, using our intuition, we can see that if we have to _prove_ a disjunction, our job is easier than with a conjunction.
With a conjunction we must prove both conjuncts; with disjunction, we may prove _only one_ of the _disjuncts_.

~~~admonish info title="Proof Rule [intro-∨-left]"
To prove $\Gamma \vdash p_1 ∨ p_2$, we may prove $\Gamma \vdash p_1$.
~~~

~~~admonish info title="Proof Rule [intro-∨-right]"
To prove $\Gamma \vdash p_1 ∨ p_2$, we may prove $\Gamma \vdash p_2$.
~~~

The **intro-∨-left** and **intro-∨-right** rules allow us to explicit choose the left- and right-hand sides of the disjunction to prove, respectively.

We have flexibility in proving a disjunction.
However, that flexibility results in complications when we reasoning about what we know if a disjunction is assumed to be true.
As an example, suppose that our example disjunction from above is assumed to be true.
What do we know as a result of this fact?
Well, we know that _at least one_ of "I'm running out of cheeseburgers" and "the sky is falling" is true.
The problem is that _we don't know which one_ is true!

We seem to be stuck!
It doesn't seem like we can extract any interesting information from a disjunction.
Indeed, a disjunction doesn't give us the same _direct_ access to new information like a conjunction.
However, disjunctions can be used in an _indirect_ manner to prove a claim!

Suppose that we are trying to prove the claim that "the apocalypse is now" and we know our disjunction is true.
We know that at least of the disjuncts is true, so if we can do the following:

+   Assume that "I'm running out of cheeseburgers" and then prove "the apocalypse is now."
+   Assume that "the sky is falling" and then prove "the apocalypse is now."

It must be the case that apocalypse is happening because _both_ disjuncts imply the apocalypse and we know _at least_ one of the disjuncts is true!

This logic gives rise to the following left-rule for disjunction:

~~~admonish info title="Proof Rule [elim-∨]"
To prove $\Gamma \vdash p$, if $p_1 \vee p_2 \in \Gamma$, then we may prove $p_1, \Gamma \vdash p$ and $p_2, \Gamma \vdash p$.
~~~

In effect, a disjunction gives us _additional assumptions_ when proving a claim, but we must consider _all possible cases_ when doing so.
This reasoning should sound familiar: this is precisely the kind of reasoning we invoke when analyzing the guard of a conditional in a computer program: "either the guard evaluates to true or false."
This reasoning is possible because we know that the guard of a conditional produces a boolean value and booleans can only take on two values.

### Top, Bottom, and Negation

Finally, we arrive at our two "trivial" propositions.
Recall that $\top$ ("top") is the proposition that is always provable and $\bot$ ("bottom") is the proposition that is never provable.

First let's consider $\top$.
Since $\top$ is always provable, its introduction is straightforward to write down:

~~~admonish info title="Proof Rule [intro-⊤]"
$\Gamma \vdash \top$ is always provable.
~~~

But what if we know $\top$ holds as an assumption?
Well that doesn't mean much because **intro-⊤** tells us that we can always prove $\top$!
Indeed, there is no left-rule for $\top$ because knowing $\top$ holds _does not tell us anything new_.

Similarly, because $\bot$ is never provable, there is no introduction rule for $\bot$ because we should never be able to prove it!
But what does it means if $\bot$ somehow becomes an assumption in our proof state?
Think about what this implies:

+   $\bot$ is defined to never be provable.
+   We assume that $\bot$ is provable as a hypothesis.

This is a _logical contradiction_: we are assuming something we know is not true!
We are now in an _inconsistent state of reasoning_ where, it turns out, _anything_ is provable.
This gives rise to the following elimination rule for $\bot$:

~~~admonish info title="Proof Rule [elim-⊥]"
If $\Gamma \vdash p$ and $\bot \in \Gamma$ then our goal $p$ is always provable.
~~~

Finally, how do we handle negation?
It turns out rather than giving proof rules for negation, we'll employ an equivalence, relating negation to implication:

~~~admonish info title="Definition (Negation)"
$\neg p \equiv p \rightarrow \bot$.
~~~

$p \rightarrow \bot$ means that whenever we can prove $p$ we can "prove" a contradiction.
Since contradictions cannot arise, it must be the case that $\neg p$ must be true instead.

~~~admonish question title="Exercise (Follow the Rules, ‡)"
Formally prove the following claim:

**Claim**: $A, B \vdash A \wedge (B \vee C)$.
~~~

## Reasoning About Quantifiers

We have given rules for all of the connectives in propositional logic.
But what about the additional constructs from first-order logic: universal and existential quantification?
Let's study each of these constructs in turn.

**Universal Quantification**

A universally quantified proposition, for example, $\forall x \ldotp x \geq 5$ holds _for all possible values_ of its quantified variable.
Here, $x \geq 5$ means that _every possible value of $x$_ is greater than five.

Because the proposition must hold for all possible values, when we go to _prove_ a universally quantified proposition, we must consider the value as _arbitrary_.
In other words, we don't get to assume anything about the universally quantified variable.
As we discussed during the program correctness section of the course, this amounts to substituting an _unknown, yet constant value_ for that variable when we go to reason about the proposition.
In practice, we think of the variable as the unknown, yet constant value, but it is important to remember that we need to _remove_ the variable, either implicitly or explicitly, from the proposition before we can continue processing it.

In contrast, when we have a universally quantified proposition as an assumption, we can take advantage of the fact that the proposition holds for all values.
We do so by _choosing_ a particular value for the proposition's quantified variable, a process called _instantiation_.
We can choose whatever value we want, or even instantiate the proposition multiple times to many variables, depending on the situation at hand.

We can summarize this behavior as introduction and elimination rules in our natural deduction system:

~~~admonish info title="Proof Rule [intro-∀]"
To prove $\Gamma \vdash \forall x \ldotp p$, we must prove $\Gamma \vdash \forall [c/x]p$ where $[c/x]p$ is the _substitution_ of some unknown constant $c$ for $x$ everywhere $x$ occurs in proposition $p$.
~~~

~~~admonish info title="Proof Rule [elim-∀]"
To prove $\Gamma \vdash p$, if $\forall x \ldotp p \in \Gamma$, then we may prove $[v/x]p, \Gamma \vdash p$ where $[v/x]p$ is the substitution of some chosen value $v$ for $x$ everywhere $x$ occurs in $p$.
~~~

The difference between the rules is subtle but important to summarize:

+   In **intro-∀**, we _hold the quantified variable abstract_.
+   In **elim-∀**, we _choose a particular value_ for the variable.

We formalize this through _substitution notation_.
The proposition $[e/x] p$ is proposition $p$ but every occurrence of variable $x$ is replaced with $e$.
Furthermore, we use the convention that:

+   $c$ is a unknown, yet constant value.
+   $v$ is a chosen value.

From this, we see that in the **intro-∀** rule, we substitute an unknown, constant value $c$ for the quantified variable $x$.
In contrast, in the **elim-∀** rule, we substitute a chosen value $v$ for $x$.

**Existential Quantification**

An existentially quantified proposition, for example, $\exists x \ldotp x \geq 5$ holds _for at least possible value_ that its quantified variable can take on.
Here, $x \geq 5$ means that _there is at least one value_ for $x$ such that $x$ is greater than five.

Since an existentially quantified proposition holds if a single value makes the proposition true, then we get the luxury of _choosing_ such a value when going to prove an existential.
For example, we might choose $x = 10$ and then we would be tasked with proving that $10 \geq 5$.
However, this flexibility comes at a price.
When know an existential is provable, we do _not_ know what value(s) make the proposition true.
So the only thing we know is the existentially quantified proposition is true for some _unknown, constant value_.

We can summarize this behavior with a pair of rules as well:

~~~admonish info title="Proof Rule [intro-∃]"
To prove $\Gamma \vdash \exists x \ldotp p$, we must prove $\Gamma \vdash \exists [v/x]p$ where $[v/x]p$ is the substitution of some chosen $v$ for $x$ in $p$.
~~~

~~~admonish info title="Proof Rule [elim-∃]"
To prove $\Gamma \vdash p$, if $\exists x \ldotp p \in \Gamma$, then we may prove $[c/x]p, \Gamma \vdash p$ where $[c/x]p$ is the substitution of some arbitrary constant $c$ for $x$ everywhere $x$ occurs in $p$.
~~~

**Summary of Reasoning with Universals and Existentials**

We can summarize our reasoning principles using universals and existentials with the following table:

| Position/Quantifier | $\forall x \ldotp p$ | $\exists x \ldotp p$ |
| ------------------- | -------------------------- | -------------------------- |
| Goal                | $x$ is arbitrary     | $x$ is chosen        |
| Assumption          | $x$ is chosen        | $x$ is arbitrary     |

Note how the rules of reasoning about universal and existential quantification are flipped depending on whether the proposition appears in goal or assumption position.
We call mathematical objects that have a reciprocal relationship of this nature _duals_.
In other words, universal and existential quantification are _duals_ of each other.
This dual nature leads to a number of interesting properties between the two connectives, _e.g._, De Morgan's law-style reasoning:

$$
\neg \forall x \ldotp p \equiv \exists x \ldotp \neg p \qquad \neg \exists x \ldotp p \equiv \forall x \ldotp \neg p.
$$

Another example of duals in logic are conjunction and disjunction.
Compare their introduction and elimination rules:

| Position/Connective | $p \wedge q$ | $p \vee q$ |
| ------------------- | ------------------ | ---------------- |
| Goal                | Prove both         | Prove one        |
| Assumption          | Assume one         | Assume both      |

Here, the duality manifests itself in whether we choose to analyze _one or both_ of the arguments to the connective.

Duals highlight an important goal of mathematical modeling.
When we model a phenomena, we are interested in understanding the _relationship_ between objects in our models.
As mathematicians, we care about these relationships so that we discover and ultimately prove properties about these objects.
As computer scientists and programmers, we can exploit these relationships to write more efficient or concise code.

~~~admonish problem title="Exercise (Alternation, ‡)"
Consider the following abstract first-order proposition:

$$
\forall x \ldotp \exists y \ldotp \forall z \ldotp p(x, y, z).
$$

For each of the variables $x$, $y$, and $z$ identify whether you must _hold the variable abstract_ or when you get to _choose a value for that variable_ when you are:

1.  Proving the proposition.
2.  Utilizing the proposition as an assumption.
~~~
