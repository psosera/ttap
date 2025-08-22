# First-order Logic

Propositional logic gives us a formal language for expressing propositions about mathematical objects.
However, is it sufficiently _expressive_?
That is, can we write down _any_ proposition we might have in mind using propositional logic?

On the surface, this seems to be the case because we can instantiate our atomic propositions to be whatever we would like.
For example, consider the following proposition:

> Every month has a day in which it rains.

We can certainly take this _entire_ proposition to be an atomic proposition, _i.e._,

$$
P = \text{Every month has a day in which it rains}.
$$

However, this is not an ideal encoding of the proposition in logic!
Why is this the case?
We can see that there is _structure_ to the claim that is lost by taking the whole proposition to be atomic.
In this case, the notion of "every" month is not distinguished from the rest of the proposition.
This is a common idea in propositions, for example:

+   For every natural number $n$, $n > 0$.
+   For any boolean expression $e$, $e \longrightarrow^* \mathsf{true}$.
+   For any pair of people in the group, they are friends with each other.

We would like our formal language of logic to capture this idea of "every" as a distinguished form so that we can reason about it precisely.
However, propositional logic has no such form!

To this end, we introduce a common extension to proposition logic, _predicate logic_ (also known as _first-order-logic_), which introduces a notion of _quantification_ to propositions.
Quantification captures precisely this concept of "every" we have identified, and more!

## Introducing Quantification

When we express the notion of "every month" or "any pair of people," we are really introducing unknown quantities, _i.e._, variables, into our propositions.
For example, our sample proposition above introduces two such variables, months and days.
At first glance, we might try expressing the proposition as an abstract proposition, _i.e._, a function that expects a month and day and produces a proposition:

$$
P(x, y) = x\;\text{has}\;y ∧ \text{it rains during}\;y.
$$

We call such functions that produce propositions _predicates_.

However, this encoding of our proposition is not quite accurate!
If we think carefully about the situation, we will note that the interpretation of $x$ and $y$ are subtlety different!
We can make this more clear by a slight rewriting of our original proposition:

> For every month, there exists a day in which it rains.

Note how we are interested in "every month."
However, we are not interested in "every day"; we only identify a _single_ day in which the property holds.
The property may hold for more than one day for any given month, but we only care that there is at least one such day.

Thus, we find ourselves needing to _quantify_ our variables in two different ways:

+   Any value (of a given type).
+   At least one value (of a given type).

Observe that our predicate notation $P(x, y)$ does not tell us which quantification we are using!

First-order logic extends propositional logic with two additional connectives that make explicit this quantification:

$$
p ::= \ldots \mid A(x) \mid \forall x \ldotp p \mid \exists x.\,p
$$

+   **Universal quantification of a proposition**, written $\forall x \ldotp p$ (pronounced "for all $x$, $p$", LaTeX: `\forall`) introduces a universally quantified variable $x$ that may be mentioned inside of $p$.
+   **Existential quantification of a proposition**, written $\exists x \ldotp p$ (pronounced "there exists $x$, $p$", LaTeX: `\exists`) introduces an existentially quantified variable $x$ that may be mentioned inside of $p$.

These quantified variables may then appear inside of our atomic propositions.
We write $A(x)$ to remind ourselves that our atomic propositions may be _parameterized_ by these quantified variables.

With quantifiers, we can now fully specify our example in first-order logic:

$$
P = ∀x \ldotp ∃y \ldotp x\;\text{has}\;y ∧ \text{it rains during}\;y.
$$

The first quantifier $∀x$ introduces a new variable $x$ that is interpreted _universally_.
That is, the following proposition holds _for every possible value of $x$_.
The second quantifier $∃y$ introduces a new variable $y$ that is interpreted _existentially_.
That is, the following proposition holds _for at least one possible value of $y$_.
When taken together, our English pronunciation of this formal proposition now lines up with our intuition:

> For all months $x$, there exists a day $y$ where $x$ has $y$ and it rains during $y$.

## Variables and Scope in Mathematics

Like function headers in a programming language, quantifiers _introduce_ variables into our propositions.
For example, consider the following Python function definition:

~~~python
def list_length(l):
    match l:
        case []:
            return 0
        case [_, *tail]:
            return 1 + list_length(tail)
~~~

Here, the function declaration introduces a new program variable `l` that may be used anywhere inside of the function `list_length`.
However, `l` has no meaning outside of `list_length`.
The location where a variable has meaning is called its _scope_:

~~~admonish info title="Definition (Scope)"
The _scope_ of a variable is the region in which that variable has meaning.
~~~

In the case of functions, the scope of a parameter is the body of its enclosing function.
So if we try to evaluate `l` outside of the definition of `list_length`, we receive an error:

~~~python
>>> list_length([1, 2, 3])
3
>>> l
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
NameError: name 'l' is not defined
~~~

The same principles hold for logical variables.
The quantifiers $∀x \ldotp p$ and $∃x \ldotp p$ introduce an appropriately quantified variable $c$ that has scope inside of $p$.
$x$ does not have meaning outside of $p$.
For example, the proposition:

$$
P = x > 5 ∧ ∀x \ldotp x ≠ 0.
$$

Is malformed because the $x$ outside the quantifier is not well-scoped.

## Implicit Types and Quantification

Variables are always quantified according to a particular set of values, its _type_.
However, you may have noticed that we never mention the type of a variable in its quantification.
How do we know what the type of $x$ is in $∀x \ldotp p$?
Traditionally, we _infer_ the types of variables based on their usage.

For example, in the proposition:

$$
∀x \ldotp ∃y \ldotp x > y.
$$

We can see that $x$ and $y$ are used in a numeric comparison $(>)$, so we assume that the quantified variables range over numbers.
Could the variables be quantified more specifically?
Certainly!
$x$ and $y$ could certainly be real numbers, integers, or even the natural numbers (_i.e._, the positive integers or zero).
We would need to look at more surrounding context, _e.g._, how this proposition is used in a larger mathematical text, to know whether the variables can be typed in more specific ways.

In addition to implicit types, sometimes quantification is also implicit.
For example, the standard statement of the Pythagorean theorem is:

$$
a^2 + b^2 = c^2
$$

Where the quantification of $a$, $b$, and $c$ is implicit.
What is the implied quantification of these variables?
It turns out that when a quantifier is omitted, we frequently intend for the variables to be _universally_ quantified, _i.e._, the Pythagorean theorem holds for any $a$, $b$, and $c$ of appropriate types.

When writing mathematics, you should always explicitly quantify your variables so your intent is clear.
However, you should be aware that implicit typing and quantification is pervasive in mathematical texts, so you will need to use inference and your best judgment to make sense of a variable.

~~~admonish question title="Exercise (Quantified Translation, ‡)"

Consider the following parameterized propositions:

+   $A_1(x)$ = "$x$ is a kitty."
+   $A_2(x, y)$ = "$x$ likes $y$."
+   $A_3(x)$ = "$x$ is a dog."

Translate these formal propositions into informal English descriptions:

1. $\exists x \ldotp \exists y.\,A_1(x) \wedge A_3(y)$.
2. $\forall x \ldotp \exists y.\,A_1(y) \rightarrow A_2(x, y)$.
3. $\forall x \ldotp \forall y.\,(A_1(x) \wedge A_3(y)) \rightarrow \neg A_2(x, y)$.
~~~