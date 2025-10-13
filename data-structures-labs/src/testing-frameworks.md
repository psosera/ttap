# Testing Frameworks

You have certainly written tests in other programming languages, but perhaps in an ad hoc fashion.
One of the niceties of Java is its strong support for testing through the [JUnit testing framework](https://junit.org/junit5/) for _unit testing_ and the [Jqwik library](https://jqwik.net/) for _property-based testing_.
Not only are the libraries robust, but there is also deep integration of these frameworks into Java IDEs such as VSCode.
This makes test development, execution, and monitoring painless... dare, I say, joyful, in Java!

## Part 1: Git and Github

Before we jump into testing, we'll first briefly introduce [Git](https://git-scm.com/), the primary [version control system](https://en.wikipedia.org/wiki/Version_control) used in the software industry along with [Github](https://github.com), the primary cloud-hosting service for Git repositories.
Git is a powerful tool for large-scale collaborative work and much of its feared complexity comes from supporting all the ways that a team of developers might coordinate their efforts.
For the purposes of our course, we are happy for you to learn just enough of Git to be able to use it to manage your personal projects, i.e., as a versioning and backup tool.

To get started, you should:

+   Create an account on [Github](https://github.com), if you have not done so already.
    Keep in mind that Github is frequently used for professional development, i.e., hosting projects that you want to feature on your resume.
    So make sure to choose a professional username and maintain a clean presence on the service!

+   Set up Git on your machine by following the [Getting Started with Git](https://docs.github.com/en/get-started/getting-started-with-git/set-up-git) guide on Github.com
    On MathLAN, the `git` program is already installed, so you won't need to install any additional programs.
    However, you will need to follow the "Connecting with SSH" option when setting up how to Authenticate with Github from Git.
    If you are setting up Git on your machine, you may need to install additional programs, e.g., Github Desktop.

Once Git is setup, you can clone the repository for this lab at the following URL:

+ <https://github.com/psosera/TestingLab>

You can then open the repository in VSCode by opening the folder containing this repository which, conveniently, is set up to be a Maven project!

## Part 2: The JUnit Testing Framework

JUnit is a combination of:

+   A Java _library_ for writing unit tests.
+   An _execution engine_ for executing tests.
+   An _API_ for extending the framework with additional functionality.

Subsequently, it would be quite a challenge to manually integrate JUnit into our project.
Thankfully, there is where Maven shines!

To add JUnit to our project, we only need to add the appropriate dependencies to the Maven's `pom.xml` file and Maven will take care of downloading all the appropriate libraries and integrating them into the project.
This has already been done for you with this lab, but inspect `pom.xml` and observe the following additional lines:

~~~xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.junit</groupId>
      <artifactId>junit-bom</artifactId>
      <version>5.11.4</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>net.jqwik</groupId>
    <artifactId>jqwik</artifactId>
    <version>1.9.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.5.2</version>
    </plugin>
  </plugins>
</build>
~~~

Here's what we added:

+   Within the `<dependencies>` tag we added two Java modules:
    -   `junit-jupiter` is the JUnit library that we will use to author unit tests.
    -   `jqwik` is the Jqwik library that we will use to author property-based tests.
    Notably, both modules are only available with the `test` scope (via the `<scope>` tag) so that these libraries are only accessible during testing and not during normal development.
    We'll see how Maven separates the "main build" from the "test build" below.
+   Within the `<build>` tag, we add the `maven-surefire-plugin`.
    This is Maven's direct integration point with JUnit, allowing us to run tests directly from Maven via the `test` subcommand.
+   Finally, under the `<dependencyManagement>` tag, we add the `junit-bom` dependency which helps Maven ensure that it uses consistent versions of the various `junit` framework libraries it downloads.

Additionally, the structure of our project has changed slightly.
Look inside the `src` directory and see that there are now two sub-directories:

+   `src/main` contains the code used in the main build of the project.
    Within this directory is a `java` directory which, in turns, contains the normal package structure of our Java project.
+   `src/test` contains the code used in the _test build_ of the project.
    Similarly to `src/main`, this directory all contains a `java` directory which contains our Java test code.

Maven manages the build so that code inside `src/test` can reference code inside `src/main`, but not vice versa.
This makes sense because we want to test our code, but our code does not necessarily need to know about the tests!

In future labs and projects, you will need to set up a Java Maven project so that you can write unit tests.
You can use this lab as a template.
But also make sure you understand how all these additional pieces interoperate!

~~~admonish info title="JUnit 4 vs. JUnit 5"
In the future, you'll inevitably run into build issues and need to diagnose things.
Be aware that the current version of JUnit is _JUnit 5_ which features many architectural changes from _JUnit 4_.
If you search around for help, be aware of what JUnit version your resource is talking about!
When in doubt, try to extract as much information from <https://junit.org> as possible which will, hopefully, be up-to-date!
~~~

## Part 3: Unit Testing

With all that front-matter out of the way, let's get to testing!
First, we'll investigate _unit testing_, isolated testing of individual program components, usually at the level of classes or individual methods.

In `edu.grinnell.csc207.testing.Functions`, you will find the (broken) implementation of the `thirdGreatest(arr)` function that returns the third-greatest element found in `arr` from the previous lab.
If you debugged this function from the previous lab, feel free to fix the method.
Regardless we will write tests to verify our work!

Our tests for the `thirdGreatest` function are found in `edu.grinnell.csc207.testing.ThirdGreatestTests`.
Each test is realized as a _single member function_ of the `ThirdGreatestTests` class.
Below is the code relevant to the single unit test we've provided in the lab as an example:

~~~java
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// ...

public class ThirdGreatestTests {
    @Test
    public void exampleUnitTest() {
      int[] arr = { 3, 8, 4, 7, 2 };
      assertEquals(4, Functions.thirdGreatest(arr));
    }
  // ...
}
~~~

The test in question is `exampleUnitTest`.
We use the `@Test` _annotation_ to signal to the JUnit testing framework to treat `exampleUnitTest` as a test.
Annotations are user-defined metadata we can hang on different components of our program.
These annotations can then be programmatically manipulated by language analysis tools, in this case, the JUnit framework.

Within `exampleUnitTest`, we use the `assertEquals(expected, actual)` static function to demand that some `expected` value is equal to the `actual` value being produced.
In this specific case, we assert that the third-greatest value of `arr` should be `4`.

To run our tests in VSCode, we can proceed by clicking the testing vial icon in the left-hand side of VSCOde.
From the testing panel, you can see a list of the tests mined from the project and selective run all or some of the tests.
We can also run all tests directly through Maven on the command-line with `mvn test`.
Although, since we are already in VSCode, we should favor using the VSCode UI to enjoy the prettier output!

Regardless of how you do it, run this test and observe the output below your source code in VSCode.
You should see that successful tests are highlighted in green.
If a test fails, it is highlighted in red with a relevant error message to help you in the debugging process!

With this in mind, write a _complete unit test suite_ for `thirdGreatest`, using `exampleUnitTest` as a template.
Create one additional method per test, making sure to use the `@Test` annotation to flag the method as a test.
We will have more to talk about regarding the different dimensions to _test comprehensiveness_ later.
For now, you can take a _white-box_ approach to testing the function known as _optimizing test coverage_: consider writing tests so that _every line of code_ of the method is exercised in some fashion by some test.
Note that by doing so, you naturally cover most of the regular case/corner case scenarios that you would have otherwise written!

## Part 4: Property-based Testing

Unit testing is a natural extension of the testing you have done in the past.
But this form of testing is _narrow_ in the sense that we can only test modules on particular inputs and outputs.
There is always a question of whether there is some input that we didn't consider that might cause the program to break!

An alternative to specific, narrow unit testing is reasoning about _properties_ of our programs instead.
General properties have the benefit of being broad, i.e., applying to all possible outputs, they are frequently less specific.
That is, a general property may only capture some part of correctness, so we need a _collection_ of properties to gain confidence that the right thing is being done.

Another problem is how we verify properties.
Previously, this has been the realm of _mathematical proof_ which is not the subject of the class!
Instead, we'll use a relatively new technique—_property-based testing_—to verify properties of our programs.
In property-based testing, rather than relying on proof, we automatically generate a large number of randomly-generated inputs to our program and then check to see if the property holds of those particular inputs.
If we believe we sampled the inputs in a well-distributed fashion and sampled enough of them, we can have confidence that the property holds!

A property-based testing library provides two things to accomplish this goal:

+   An API for specifying properties within a program.
+   An API for automatically generating different values.

Let's look at how the Jqwik library accomplishes this in `ThirdGreatestTests`.

~~~java
// ...

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

public class ThirdGreatestTests {
    // ...
    @Property
    public boolean examplePropertyTest(
            @ForAll @IntRange(min = 1, max = 1000) int sz,
            @ForAll int k) {
        int[] arr = new int[sz];
        for (int i = 0; i < sz; i++) {
            arr[i] = k;
        }
        return Functions.thirdGreatest(arr) == k;
    }
~~~

Whereas JUnit tests are marked with `@Test`, Jqwik tests are marked with `@Property`.
Each property is realized as a method that returns a boolean value.
`examplePropertyTest` captures the property that for any input array of arbitrary size `sz`, if that array is made up of the same value `k`, then `thirdGreatest` should return that value `k`.

How does Jquiwk know to generate random inputs to `examplePropertyTest`?
Via the `@ForAll` annotations on each of the arguments to the method!
Marking `k` as `@ForAll` instructs Jqwik to randomly sample all possible integers to provide example values for `k`.
Not all possible integer values are valid as an array size, e.g., negative integers.
Thus, we also use the `@IntRange` annotation to specify a `min` and `max` range to the random numbers that may be generated for `sz`.

So what does Jqwik do?
Jqwik generates (by default) 1000 sets of random arguments to `examplePropertyTest`.
If any of the sets causes `examplePropertyTest`, then Jqwik has found an example that _falsifies_ the property!
It reports that specific example back to you, so that you can use that information to fix your code.
Otherwise, Jqwik did not find any falsifying examples so it reports success.
Note that this doesn't _definitively_ mean the property holds of our function, but it gives us _significant confidence_ that the property holds!

Like with JUnit, write a collection of property-based tests using Jqwik for `thirdGreatest` using `examplePropertyTest` as a template.
You'll immediately notice a distinction between writing unit tests and property-based tests: property-based tests are much harder to come up with!
We have to be somewhat creative in coming up with meaningful properties that exercise our program in useful ways.
Try to come up with at least _two_ additional properties.
A starting point might be to consider generalizing the behavior of one or more of your unit tests to arbitrary elements or array sizes.

## Part 5: Test-driven Design

Now that you have experience using JUnit and Jqwik, let's get additional practice both writing fundamental Java code and tests with JUnit and Jqwik.
For each function below:

+   Implement the function in `edu.grinnell.csc207.testing.Functions`.
    Make sure to include a Javadoc comment with all the sailent tags!
+   Create a new testing class in the `src/test` source hierarchy for that function.
    Write both a comprehensive unit testing suite and at least two property-based tests for the function.

### Summation

Write a function called `sum` that takes an integer `n` as input and returns the sum of the first `n` integers (*i.e.*, 0 through `n` inclusive).
You should use a loop rather than an explicit formula in your method.
(*Note:* this is an intentionally easy problem so that you can get in the habit of using the testing tools!)

### Minimum

Write a function called `min` that takes an array of integers `arr` and returns the minimum element in the array.
If there are no elements in the array, you should throw an `IllegalArgumentException`.

### Lucas Sequence

Write a function called `lucas` that takes an integer `n` and returns the `n`th [Lucas Number](https://en.wikipedia.org/wiki/Lucas_number).
The Lucas sequence $L(n)$ is similar to the Fibonacci sequence in that the recurrence that defines the numbers is $L(n) = L(n-1) + L(n-2)$.
However, $L(0) = 2$ and $L(1) = 1$, unlike the Fibonacci sequence which defines the first two numbers to be $0$ and $1$.
You should use a loop rather than an explicit formula or recursion in your method.

### Substring

Write a function called `substringIndex` that takes two arguments, a string `s` and a string `t`, and returns an integer corresponding to the starting index of the first occurrence of `t` in `s`.
The method returns `-1` if `t` does not occur in `s`.
If `s` or `t` are `null`, the method throws an `IllegalArgumentException`.

### Sort

Write a method called `sort` that takes an array of integers `arr` and sorts the array in-place.
`sort`.
(*Hint*: write an loop invariant that divides up the array into an unsorted and sorted region in terms of the loop variable `i`.
Your for-loop should then be responsible for preserving the invariant on every iteration of the loop.)

## Turning in Your Work

When you are done, feel free to turn in your entire git repository/project folder `TestingLab` as a zip archive to Gradescope!
