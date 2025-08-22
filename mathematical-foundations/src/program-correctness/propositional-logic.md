Have you ever been in an argument where the other person "just wasn't being reasonable?"
What were they doing that was "without reason?"

-   Making "facts" up out of thin air?
-   Forming connections between claims without appropriate evidence?
-   Jumping between unrelated arguments?
-   Not addressing the problem at hand?

How do you know that you were being reasonable and your partner was the unreasonable one?
Is there an agreed-upon set of rules for what it means to operate "within reason" or is it simply a matter of perspective?

_Mathematical logic_ is the study of formal logical reasoning.
Coincidentally, logic itself is one of the foundations of both mathematics and computer science.
Recall that mathematical prose contains _propositions_ that assert relationships between definitions.
These propositions are stated in terms of mathematical logic and thus form one of the cornerstones of the discipline.

As computer scientists, we care about logic for similar reasons.
We care about the properties of the algorithms we design and implement, in particular, their correctness and complexity.
Logic gives us a way of stating and reasoning about these properties in a precise manner.

# Propositional Logic

Let us begin to formalize our intuition about propositions.
First, how can we classify propositions?
We can classify them into two buckets:

+   **Atomic propositions** that make elementary claims about the world.
    "It is beautiful today" and "it is rainy today" are both elementary claims about the weather.

+   **Compound propositions** that are composed of smaller propositions
    "It is either rainy today or it is beautiful today" is a single compound proposition made of up two atomic propositions.

This is not unlike arithmetic expressions that contain atomic parts---integers---and compound parts---operators.
As you learned about arithmetic throughout grade school, you learned about each operator and how they transformed their inputs in different ways.
Likewise, we will learn about the different logical operators---traditionally called _connectives_ in mathematical logic---and how they operate over propositions.

We can formally define the _syntax_ of a proposition $p$ similarly to arithmetic using a grammar:

$$
p ::= A \mid \top \mid \bot \mid \neg p \mid p_1 \wedge p_2 \mid p_1 \vee p_2 \mid p_1 \rightarrow p_2.
$$

A grammar defines for a sort (the proposition $p$) its set of allowable syntactic forms, each written between the pipes ($\mid$).
A proposition can take on one of the following forms:

+   **Atomic propositions**, elementary claims about whatever domain we are considering.
    When we don't care about the particular domain (_e.g._, we want to hold the domain _abstract_), we'll use capital letters such as $A$ as meta-variables for atomic propositions.

+   **Top**, $\top$ (LaTeX: `\top`), pronounced "top", the proposition which is _always provable_.

+   **Bot**, $\bot$ (LaTeX: `\bot`), pronounced "bottom" or "bot", the proposition which is _never provable_.

+   **Logical negation**, $\neg p$ (LaTeX: `\neg`), pronounced "not", which stands for proposition $p$, but _negated_.
    For example, the negation of $p$ = "It is beautiful today"  is $\neg p$ = "It is _not_  beautiful today".

+   **Logical conjunction**, $p_1 \wedge p_2$ (LaTeX: `\wedge`), pronounced "and", which stands for the proposition where both $p_1$ and $p_2$ are provable.
    For example, the conjunction of $p_1$ = "It is beautiful today" and $p_2$ = "The sun is out" claims that it is _both_ beautiful and the sun is out.

+   **Logical disjunction**, $p_1 \vee p_2$ (LaTeX: `\vee`), pronounced "or", which stands for the proposition where at least one of $p_1$ or $p_2$ is provable.
    For example, the disjunction of $p_1$ = "It is beautiful today" and $p_2$ = "It is raining today" claims that it is either beautiful today or it is raining today (or both are true).

+   **Logical implication**, $p_1 \rightarrow p_2$ (LaTeX: `\rightarrow`), pronounced "implies", which stands for the proposition where $p_2$ is provable _assuming_ $p_1$ is provable.
    For example, if $p_1$ = "It is cloudy" and $p_2$ = "It is raining", then $p_1 \rightarrow p_2$ claims that it is raining, assuming that it is cloudy.

Notably, logical implication are the _conditional_ propositions we have seen previously of the form "if … then … ."
For example, if we had the following program correctness claim:

~~~admonish question title="Claim"
**Claim**: for all natural numbers `n`, if `n >= 0` then `factorial(n) > 0`.
~~~

We might write it concisely using the formal notation above as:

~~~admonish question title="Claim"
**Claim**: for all natural numbers `n`, `n >= 0` $\rightarrow$ `factorial(n) > 0`.
~~~

This form of logic is called _propositional logic_ because it focuses on propositions and basic logic connectives between them.
There exist more exotic logics that build upon propositional logic with additional connectives that capture richer sorts of propositions we might consider.

## Modeling with Propositions

Recall that mathematics is the study of modeling the world.
In the case of mathematical logic, we would like to model real-world propositions using the language of mathematical propositions we have defined so far.
This amounts to _translating_ our informal natural language descriptions into formal mathematical descriptions.

To do this, keep in mind that mathematical propositions are formed from atomic and compound propositions.
We then need to:

1.  Identify the atomic propositions in the statement.

2.  Determine how the atomic propositions are related through various
    compound propositions.

As an example, let's consider a proposition more complicated than the previous ones that we have encountered:

> "If it is either the case that the grocer delivers our food on time, or I get out to the store this morning, then the party will start on time and people will be happy.

First, we identify the domain under which we are making this proposition: _preparing for a party_.
Thus, the atomic propositions are the statements that directly talk about preparing for a party.
These propositions are:

+   $p_1$ = "The grocer delivers our food on time."
+   $p_2$ = "I get out to the store this morning."
+   $p_3$ = "The party will start on time."
+   $p_4$ = "People will be happy."

Now, we re-analyze the statement to determine how the propositions are related, translating those relations into logical connectives.
You may find it helpful to replace every occurrence of the atomic propositions with variables to make the connecting language pop out:

> "If it is either the case that $p_1$ or $p_2$ then $p_3$ and $p_4$."

From this reformulation of the statement, we can see that:

+   $p_1$ and $p_2$ are related by the word "or" which implies disjunction.
+   $p_3$ and $p_4$ are related by the word "and" which implies conjunction.
+   These previous two compound conjunctions are related by the words "if" and "then" which implies implication.

Thus we arrive at our full formalization of the statement:

$$
p_1 \vee p_2 \rightarrow p_3 \wedge p_4.
$$

(Note that $(\rightarrow)$ has lower precedence than $(\vee)$ or $(\wedge)$ so the statement written as-is is not ambiguous.)

When translating statements in this manner, it is useful to keep in mind some key words that usually imply the given connective:

+   $(\neg)$: "not"
+   $(\wedge)$: "and"
+   $(\vee)$: "or"
+   $(\rightarrow)$: "implies", "if ...then"

## Reasoning with Propositions

Recall that our goal is to formally model reasoning.
While equivalences allow us to show that two propositions are equivalent, they do not tell us how to _reason_ with propositions.
That is, how do we _prove_ propositions once they have been formally stated?

First, we must understand the reasoning process itself so that we can understand its parts.
From there, we can state rules in terms of manipulating these parts.
When we are trying to prove a proposition, whether it is in the context of a debate, an argument with a friend, or a mathematical proof, our proving process can be broken up into two parts:

+   A set of _assumptions_, propositions that we are assuming are true.
+   The proposition that we are currently trying to prove, our _proof goal_.

We call this pair of objects our _proof state_.
Our ultimate goal when proving a proposition is to _transform_ the proof goal into an "obviously" correct statement through valid applications of _proof rules_.

For example, if we are in the midst of a political debate, we might be tasked with proving the statement:

> "80s-era Reaganomics is the solution to our current economic crisis."

Our proof goal is precisely this statement and our set of assumptions includes our knowledge of Reaganomics and the current economy.
We might take a step of reasoning---_unfolding_ the definition of Reaganomics, for example---to refine our goal:

> "**Economic policies associated with tax reduction and the promotion of the free-market** are the solution to our current economic crisis."

We can then apply additional assumptions and logical rules to transform this statement further.
Once our logical argument has transformed the statement into a self-evident form, for example:

> "Everyone needs money to live."

Then, if everyone agrees this is a true statement, we're done!
Because the current proof goal follows logically from assumptions and appropriate uses of logical rules, we know that if this proposition is true, then our original proposition is true.
In a future reading, we will study these rules in detail in a system called _natural deduction_.

## Propositional Logic Versus Boolean Algebra

Throughout this discussion, you might have noted similarities between propositions and a concept you have encountered previously in programming: booleans.
Values of _boolean type_ take on one of two forms, `true` and `false`, and there are various operators between booleans, `&&` and `||`,logical-AND and logical-OR, respectively.
It seems like we can draw the following correspondence between propositions and booleans (using the boolean operators found in a C-like language):

+   $\top$ is `true`.
+   $\bot$ is `false`.
+   $(\neg)$ is `!`.
+   $(\wedge)$ is `&&`.
+   $(\vee)$ is `||`.

This correspondence is quite accurate!
In particular, the equivalences we discussed in the previous section for propositions also work for boolean expressions.
The only caveat is that there is no analog to implication for the usual boolean expressions, but the equivalence:

$$
p_1 \rightarrow p_2 \equiv \neg p_1 \vee p_2
$$

Allows us to translate an implication into an appropriate boolean expression.

So why do we talk about propositions when booleans exist?
It turns out they serve subtle, yet distinct purposes!

+   A proposition is a statement that may be proven.
+   A boolean expression is an expression that evaluates to `true` or `false`.

Note that a proposition does not say anything about whether the claim is true or false.
It also doesn't say anything _computational_ in nature, unlike a boolean expression which carries with it an _evaluation semantics_ for how to evaluate it in the context of a larger computer program.

Furthermore, the scope of a boolean expression is narrow: we care about booleans values insofar as they assist in controlling the flow of a program, _e.g._, with conditionals and loops.
We don't need to complicate booleans any further!
In contrast, propositions concern arbitrary statements, and our language of propositions may need to be richer than the corresponding language of booleans to capture the statements we have in mind.

~~~admonish question title="Exercise (Translation, ‡)"
Consider the following propositions

+   $p_1$ = "The sky is cloudy."
+   $p_2$ = "I will go running today."
+   $p_3$ = "I'm going to eat a cheeseburger."

Write down English translations of these formal statements:

1.  $p_2 \vee p_3$.
2.  $p_1 \rightarrow p_3 \rightarrow p_2$.
    (_Note_: implication is a right-associative operator!
    This means that this statement is equivalent to $p_1 \rightarrow (p_3 \rightarrow p_2)$).
3.  $p_1 \rightarrow p_2 \wedge \neg p_3$.
~~~

~~~admonish question title="Exercise (Funny Nesting)"
Recall that implication is _right-associative_.
That is, sequences of $(\rightarrow)$ operators parenthesize "to the right", _i.e._,

$$
p_1 \rightarrow p_2 \rightarrow p_3 ≡ p_1 \rightarrow (p_2 \rightarrow p_3).
$$

Now, consider the abstract proposition where we instead parenthesize to the left:

$$
(p_1 \rightarrow p_2) \rightarrow p_3.
$$

Come up with concrete instantiations of $p_1$, $p_2$, and $p_3$ and write down the English translation of this proposition.
~~~

