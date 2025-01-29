# Speed Reader

_(This project originally appeared as a [SIGCSE 2025 Nifty Assignment](http://nifty.stanford.edu/2015/posera-speed-reader/) by Peter-Michael Osera!)_

Everyone has wished they could read faster at some point in their life, for example, to get through the required reading for their English class, to cram for an exam, or to simply get through their ever-growing list of novels to read.
Since the 1950s, psychologists, linguists, and educators have devoted significant efforts into [speed reading](http://en.wikipedia.org/wiki/Speed_reading) techniques that can dramatically increase your reading speed with relatively little loss of comprehension.
In this homework, we will prototype one approach to speed reading called [Rapid Serial Visual Presentation (RSVP)](http://en.wikipedia.org/wiki/Rapid_Serial_Visual_Presentation)---recently popularized by [Spritz Inc.](http://www.spritzinc.com/) and apps like [Outread (iOS)](https://outreadapp.com/) and [Reedy (Android)](https://reedy-reader.com/)---and run some small user studies to test its effectiveness.

## Background

Many modern speed reading techniques are based on the insights of school teacher Evelyn Wood.
In the 1950s, Wood observed that, among other things, (1) using your finger or some other pointing device to train your eyes and focus while reading and (2) eliminating [subvocalization](http://en.wikipedia.org/wiki/Subvocalization), internally speaking words while reading them, can dramatically increase your reading speed.

Since then, countless speed reading courses have been developed to help students develop these skills.
However, these courses rely on the student's discipline to develop good reading habits, and it is easy for an untrained student to learn "the wrong way" and thus never seen the purported benefits of speed reading.
Computer programs in this context can act a tutor or personal support system, ensuring that students practice the right skills even while learning alone.

RSVP, in essence, takes these ideas of pointing-while-reading and removing sub-vocalization to their limit.
With RSVP, a series of objects—here, words—are presented quickly in succession. By design, the reader is only able to focus on a single word at a time.
And furthermore, the words appear at such a speed that the reader is unable to sub-vocalize like normal.
Such a presentation style is only really practical with a computer program!

Here are some examples of RSVP:

![Spritz @ 250 WPM](./images/spritz1.gif)

Note that while somewhat disorientating at first, the text becomes readable with a little bit of practice, especially once you consciously try to stop yourself from subvocalizing each word. This text is being presented at 250 words per minute. Here are examples of the technique used to read 350 and 500 words per minute, respectively.

![Spritz @ 350 WPM](./images/spritz2.gif)

![Spritz @ 500 WPM](./images/spritz3.gif)

Note that the average reading speed is approximately [250-300 words per minute](http://bit.ly/1EQpgxC) for reading adult prose. So with some practice, you can use a speed reader to read at approximately 2x the average reading rate. In contrast the world record for speed reading is a blistering [4251 words per minute!](http://www.mentalworldrecords.com/worldspeedreadingcouncil/).

## Project Setup

For this project, create a new NetBeans project called `SpeedReader`.
Using appropriate NetBeans defaults, your source files for this package should be located in the `edu.grinnell.csc207.speedreader` package.

In your project, add a `README.md` Markdown file with the following information:

1.  The name of this project.
2.  Your name.
3.  A one sentence description of the content of this submission.
4.  A list of the resources you used---organic or inorganic---to complete this assignment and how they helped you complete the assignment, one sentence per such resource.
    Remember to include all resources, including course materials, editors, peers, and online resources!

In the final part of this project, you will add details of your user study in your `README.md`, too!

## Part 1: WordGenerator

We can break up the functionality of the Speed Reader into three components: reading text from a file, rendering that text to the screen, and animating that rendering process.
First, we'll build a class, `WordGenerator`, that reads in text from a text file and logs stats about the text that is read.
The simplest way to read in files in Java is through the [Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html) class.
In general, a Scanner breaks up a stream of text into words or *tokens* that can be read from the Scanner.
We can attach a Scanner to a variety of text sources; most commonly, we'll attach the Scanner to [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) objects that represent files on disk.

Here is an example usage of a Scanner where we read in files from a text file and print them to the console, one word per line:

~~~java
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

// ...

public void printWords(String filename) throws IOException {
    Scanner text = new Scanner(new File(filename));
    while (text.hasNext()) {
        System.out.println(text.next());
    }
}
~~~

Things to note about using a `Scanner`:

* The classes we must use: `Scanner`, `File`, and `IOException` exist in the standard libraries but appear in different packages.
To use these classes without having to specify the *fully-qualified name* of the class (package + class name), we use `import` declarations to tell Java that, *e.g*, when we reference `Scanner`, we really mean `java.util.Scanner`.
Note that `import` declarations appear at the top of Java source files outside of any class definitions.
* To instantiate a `Scanner`, we pass in a `File` object.
It is tempting to pass in the filename directly, but this is a mistake because the resulting `Scanner` will parse the filename string rather the file it denotes!
* The critical methods of `Scanner` are `hasNext()` to see if the `Scanner` has text left and `next()` to get the next token out of it.
See the API documentation linked above for more information on these methods.
* Note that our function signature has a `throws IOException` clause following the argument list (but before the opening curly brace).
This *throws clause* is necessary because the creation of the `Scanner` object may generate a *checked exception*, namely an `IOException`.
Exceptions can be handled using try-catch blocks or they be explicitly passed on to calling functions using the throws clause.
For now, using the throws clause is easier as we don't have much to do if such an exception occurs; we'll talk about handling exceptions in Java in the coming weeks.

Our `WordGenerator` will act as a wrapper around this functionality of the `Scanner`.
Your `WordGenerator` class needs to have the following constructor and methods:

* `WordGenerator(String filename)`: constructs a new generator that processes text from the given file.
* `boolean hasNext()`: returns `true` iff the underlying `Scanner` of this `WordGenerator` has text left to process.
* `String next()`: returns the next word of the underlying `Scanner`.
  If the `Scanner` does not have words left, then the behavior of `next()` is undefined (i.e., you don't have to check or handle this case).
* `int getWordCount()`: returns the number of words produced by the `WordGenerator` so far.
* `int getSentenceCount()`: returns the number of sentences produced by the `WordGenerator` so far.
  Define a sentence to be the number of occurrences of words where a punctuation mark appears at the end---`'.'`, `'!'`, or `'?'`.

## Part 2: Displaying Text

Separate from reading text from the text file is displaying the text to the screen.
Normally, we would use the Java Swing API to do this which is Java's built-in GUI framework.
However, Swing is a complicated (albeit well-engineered) library, so rather than using it directly, we'll use a *wrapper class* that makes drawing stuff to a window easy: the `DrawingPanel` (credit to Stuart Reges and Marty Stepp at the University of Washington who use this class in their book, [Building Java Programs](http://www.buildingjavaprograms.com/)).

Here is the essential usage of `DrawingPanel`:

~~~java
import java.awt.*;

public void demonstratePanel() {
    DrawingPanel panel = new DrawingPanel(400, 300);
    Graphics g = panel.getGraphics();
    Font f = new Font("Courier", Font.BOLD, 46);
    g.setFont(f);
    g.drawString("Hello World!", 100, 100);
}
~~~

* The relevant classes we need to draw to the *DrawingPanel* are found in the `java.awt` package.
  To make all of these classes available without using their fully-qualified names, instead of naming each one individually, we use `*` to import them all.
* We create a `DrawingPanel` by invoking the `DrawingPanel(width, height)` constructor which creates a panel of the specified dimensions and immediately makes it visible on the screen.
* We grab the *graphics context* object (an instance of the `Graphics`) class with the `getGraphics()` method.
* We set the font by creating a new [`Font`](http://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/awt/Font.html#Font(java.lang.String,%20int,%20int)) object.
  The constructor for a `Font` takes the name of the font, its style, and the size (in points).
  We then set the font for the graphics context with the `setFont` method.
* Finally, we use the [graphics context](http://docs.oracle.com/en/java/javase/23/docs/api/java/awt/Graphics.html) to render text to the window.
  Here we use the `drawString` method which takes the string to render and where to render it.

The `DrawingPanel` class is available as a separate Java file to include in our project.
Download the file here:

* [DrawingPanel.java](./files/DrawingPanel.java)

NetBeans does not provide a way to import a Java file directly into a project.
The easiest way I found to do this is to:

1.  Create a new file Java file in your project via the menu: File → New File → Java → Java Class.
    In the resulting dialog:
    -   Name the class `DrawingPanel`.
    -   Ensure the package is the same as the project package: `edu.grinnell.csc207.speedreader`.
    This will create a new file, `DrawingPanel.java`, in:

    ~~~ 
    SpeedReader/src/main/java/edu/grinnell/csc207/speedreader/DrawingPanel.java
    ~~~

2.  Open the file in NetBeans, delete the contents of the file, and copy-paste the contents of the file linked above.
3.  Add the following package declaration statement at the top of the file to reflect the package structure:

    ~~~java
    package edu.grinnell.csc207.speedreader;
    ~~~

After doing this, you will be able to use the `DrawingPanel` in either of your source files!

## Part 3: Animating Text

The final component to rendering the text is doing so in an animated way.
To do this, we'll simply render the text in a loop but in each iteration of the loop, *delay* execution by causing the program to sleep.
To do this, we'll use the `sleep(milliseconds)` function of the [`Thread`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/lang/Thread.html) class:

~~~java
public void printStaggered() throws InterruptedException {
    while(true) {
        System.out.println("Hello World!");
        Thread.sleep(1000);
    }
}
~~~

This snippet of code constantly prints `Hello World` to the console but in one second intervals.
The argument to `Thread.sleep` is the amount of time the program should wait in milliseconds.
Note that like the `Scanner` above, `Thread.sleep` throws the checked exception `InterruptedException`.
We use a `throws` clause here to avoid handling the exception explicitly.

## Part 4: Putting it Together

Finally, you should put all these concepts together to create a program `SpeedReader` that exists in a file `SpeedReader.java` which reads in a file and displays it in the RSVP style to a `DrawingPanel`.
Your program should use your `WordGenerator` class to read the file and after displaying the text file report the number of words and number of sentences it processed.

Your program should take a number of command-line arguments to customize its behavior.
Here's a description of its usage:

~~~
Usage: SpeedReader <filename> <width> <height> <font size> <wpm>
~~~

* `filename` is the text that should be read.
* `width` is the width of the window.
* `height` is the height of the window.
* `font size` is the size of the font used.
* `wpm` is the speed at which the words are displayed in *words per minute*

If the user does not specify this exact amount of arguments, then you should print a usage message and exit.
You may assume without checking that:

* The `filename` points to a valid file on disk.
* The `width`, `height`, `font size`, and `wpm` are all positive integers. 

You can use the `parseInt` static method of the `Integer` class, i.e., [Integer.parseInt(s)](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String)) to convert a string into an `int`.

The text should be rendered in the "Courier" font, and the text can be rendered in a location of your choosing.
Ideally, you would render the text to the middle of the screen, but this is slightly complicated because the size of the font does not necessarily correlate with how large the string is.
Centering the text (and other visual improvements) is an optional improvement you can make to your program!

## Part 5: Usability Testing

Now that your speed reader is complete, you can now run *usability tests* over it.
In particular, we will conduct a small test to answer the question: "does speed reading impact our ability to comprehend what we read?"
In the field of [human-computer interaction](https://en.wikipedia.org/wiki/Human%E2%80%93computer_interaction), researchers use usability tests to understand how real people interact with technology.
They then use this information to decide how to better design software and hardware that people can use.

The setup for the tests proceeds as follows:

1. Develop a corpus of texts to be used by your study participants.
   This should include approximately 8--10 excerpts of texts found on the Internet, e.g., [wikipedia.org](htts://www.wikipedia.org) articles or [gutenberg.org](https://www.gutenberg.org/).
   Each excerpt should contain several paragraphs, ideally enough text so that speed reading at 350 WPM takes a minute or two per excerpt.
2. For four of the excerpts, develop a small comprehension quiz consisting of _five comprehension questions each_ that require the reader to comprehend the text they are reading.
   These questions should be about facts regarding the _content_ of the excerpts, not the particulars of the text, e.g., do not have questions to the effect of "how many three-letter words did the excerpt contain?".
   The remaining texts will be used to help the user _train_ on the system.

To run the test, find _at least one, ideally three, participants_ that are willing to take your test.
These should be individuals not currently in CSC 207---ideally, they shouldn't be computer science majors!
With each participant, do the following:

1. Tell the participant what the study is about, what you are trying to analyze, and what the procedure for the test is, i.e., the steps below.
   You do not need to hide anything from your participants!
2. Instruct the users on how to use your speed reader application.
   Show them the application and how it works.
   You may run the application for them to avoid the need to explain how to run a Java program!
3. Give your participants _15 minutes_ to train using the application.
   Using the non-quiz texts in your corpus, let your participant use the speed reader at various speeds to become accustomed to the application.
   At this stage, the participant may go back and re-read texts as much as they would like to gain experience speed reading.
4. When they are ready, choose one of your quiz-texts at random and allow the participant to read the text without the use of the speed reader, i.e., in a plain text editor.
   They may read the text at their own pace.
   Once they are done, administer the quiz—the participant is not allowed to consult the text again at this point.
   Record which text was chosen along with the results of the quiz.
5. Repeat the previous step for the remaining quizzes.
   However, rather than letting the participant read the text in a text editor, have them use your speed reader instead.
   Each text should be read at a different speed—250, 350, and 500 WPM.
   Furthermore, the participant should only be allowed to speed read the text once.
   Again, record the text chosen, the WPM the text is read at, and the results of the quiz.
6. After this step, the test is done.
   Make sure to thank your participant!

You should include your excerpts in your repository as additional text files.
In the `README.md` file in your repository, include the following information:

* The sources of each of the excerpts, i.e., the URLs where you found them.
* The questions for the four texts with associated quizzes.
* The names and Grinnell emails of your participant(s).
* The results of your quizzes as described above.

Finally, add a paragraph in your `README.md` file briefly analyzing your results by answering the following question:

> Did your participants demonstrate that they were able to comprehend what they were reading with your speed reader?

## Project Structure and Gradescope Turn-in

When you are done, your project should have the following contents (and, perhaps, additional files):

~~~
SpeedReader/
┠─ pom.xml
┠─ README.md
┠─ <Text files used in your study>
┠─ target/
│  └─ ... 
└─ src/main/java/edu/grinnell/csc207/speedreader/
   ┠─ DrawingPanel.java       
   ┠─ SpeedReader.java
   └─ WordGenerator.java
~~~

Please delete the `SpeedReader/target` folder before you bundle your project for submission; this folder contains compiled `.class` files that are not necessary to submit!
You can either manually delete this directory or run `mvn clean` from the terminal in the `SpeedReader/` directory.

Once the `target` folder is deleted, you should:

1.  Compress the `SpeedReader/` directory into a zip file.
    On many operating systems, you can navigate to this directory in your file explorer-equivalent, right-click the folder, and choose an option that roughly says "Compress ..." or "Create zip archive ... ."
    Alternatively, on Mac or Linux, you can navigate to the directory that contains the `SpeedReader/` directory and use the `zip` program:

    ~~~console
    > zip SpeedReader.zip -r SpeedReader/
    ~~~

2.  Upload your `SpeedReader.zip` zip file to Gradescope!

Again, make sure that your program passes the autograder tests on Gradescope.
Some of these tests will check whether your project has the correct directory structure.
Other tests will check the correctness of your `WordGenerator` class.
We will manually check the overall application since it is graphics-based!

## More Enhancements

There are lots of improvements to be made to your speed reader!
Enhancing your program beyond the requirements stated above is not necessary, but if you want more practice, here are some suggestions:

### Checking for Invalid Arguments

In the interest of focusing on the larger task at hand, we did not require that you perform type checking on the arguments to the program.
We should probably do that!

To check to see if a file exists on disk, [File objects](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/io/File.html) have an `exists` method, e.g., `file.exists()`, that returns `true` if and only if the file exists on disk.

To perform error checking on the integers, note that `Integer.parseInt(s)` throws an `NumberFormatException` if `s` cannot be parsed as an integer.
We can use a `try { ... } catch (NumberFormatException e) { ... }` block to catch this error if it is thrown.

### Centering the Text

To center the text on the screen, you need to appeal to an additional class, [`FontMetrics`](http://docs.oracle.com/javase/8/docs/api/java/awt/FontMetrics.htm), to discover how much space a given string will take up on the screen.
To obtain an appropriate `FontMetrics` object, use the `getFontMetrics()` method of the `Graphics` class.
From there, you'll need to figure out the appropriate methods to use as well as the math to get the text to be centered on the screen.

### Focus Letters

Even after centering the text on the screen, you can still improve how words are displayed!
You may have noticed that excessively long words can sometimes disrupt your speed reading when they are naively centered on the screen.
Spritz and other systems use the following heuristic to align text on the screen:

Choose a focus letter based off the length of the overall word and center the word around the focus letter:

* length = 0-1 => first letter
* length = 2-5 => second letter
* length = 6-9 => third letter
* length 10-13 => fourth letter
* length >13 => fifth letter

Also, color the focus letter differently from the other letters, e.g., in red.
