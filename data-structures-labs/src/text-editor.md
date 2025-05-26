# Text Editor

Text editors are seemingly simple applications since all they do is allow a user to edit plain text files which amounts to simple string manipulation.
However, are deceptively complicated programs!
A naive approach to implementing a text editor can lead to poor performance especially when viewing and editing large files.
We must be mindful of our choice of data structures that back a text editor, taking advantage of the _particular ways_ a text editor edits its text.

Thus, a text editor is an excellent case study bridging together our discussion of complexity analysis with the pragmatic engineering decisions we must make when choosing data structures!
In this project, you'll:

1.  Implement a core engine for a text editor, a _text buffer_, in two different ways, with a simple string and a [gap buffer](https://en.wikipedia.org/wiki/Gap_buffer).
2.  Create a test suite for these two engines to verify their correctness.
3.  Analyze the complexity of insertion into these two different buffers to demonstrate the inefficiencies of one model and the efficiencies of the other.
4.  Build a very simple, terminal-based GUI for your text editor using the [Lanterna](https://github.com/mabe02/lanterna/tree/master) text-user interface (TUI) library.

## A Note on Projects and Exploring Computer Science

In my opinion, the best way to get better at computer science is by immersion.
Because computer programming allows us build real, tangible artifacts with a relatively low barrier to entry, we can always dive deep into a subject of interest simply by trying to recreate the _canonical program_ in that area.
Even we are not successful, simply getting our hands dirty helps us exercise all the skills we learn in our coursework!

Moving forward, most of the projects will have this flavor of _the beginnings_ of a deep dive into a more complex topic.
If the topic of a project interests you, I highly encourage to use your project as a starting point for a deeper exploration into this space.
In particular, text editors are well-studied, and there are many resources online to help you flesh out your project further!
While older, I like pointing people to Finseth's _The Craft of Text Editing --or-- Emacs for the Modern World_ which walks the reader through the implementation of an Emacs-like text editor in C.
It is freely available at the URL below:

+ <https://www.finseth.com/craft/>

As we move forward in the course, I will point out additional "deep dive" resources to you, to hopefully inspire you to pursue your interests outside of class!
If you would like additional resources in an area we investigate or any resources that we do not investigate, let me know!
Also, if you feel that you are not particularly inspired by any area of computing we discuss, that's ok!
I encourage you to _pick anything even remotely interesting_ and deep-dive; it is the act of doing, not the end result, that is the point!

### Personal Projects and Public Hosting

I highly encourage you to use any of your projects as a starting point for a deeper exploration into computing.
As an author, be aware that you hold the copyright on the code you create.
However, I ask that you _do not_ make your project code publicly available unless you make _substantial_ modifications to your program.
This is to maintain the usefulness of these projects as assignments for other instructors.
Additionally, if you use a project as part of your portfolio for the purposes of employment, companies will want to see work done _outside_ of class of your own volition versus work that was required!

## Getting Started

The starter code for this project is located at the following Github repository:

+   <https://github.com/psosera/texteditor>

You should fork this repository to your Github account.
There are no specific requirements to your Git usage for this project, but you should practice good Git habits:

+   Commit changes often, whenever you make significant progress on a task.
+   Only commit code that you verified builds, i.e., don't ever leave your repository in a broken state!
+   Give your commits brief, but informative messages that summarize your changes.
+   Make sure to frequently push your changes to Github, so that your work is backed up in the cloud.

When you are done with this project, you should _upload your Github repository to Gradescope_.
To do this, you should link your Github account to your Gradescope account (via your account settings).
Once this is done, on project submission, you can choose to submit this Github repository directly.
Before you do this, make sure that all your changes have been both committed to your local repository and pushed to your Github repository!

## Background: Text Buffers

An important architectural pattern for most applications is the [Model-view-controller pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) where we divide up our program into three parts:

1.  A _model_ that is the core "engine" that drives the program.
2.  A _view_ that renders the model to the user.
3.  A _controller_ that allows the user to manipulate the model.

The core engine of a text editor is a _text buffer_, a sequence of characters, with operations specific to text editing.
In particular, we don't expect to insert text into arbitrary positions of the buffer.
Instead, our operations on the text buffer revolve around the position of the _cursor_ of our text editor, which we maintain as a piece of state alongside the character sequence.
We can insert and delete characters at the cursor.
Additionally, we can move the cursor forwards and backwards in the buffer.

For example, consider editing the text `Hello world!` with the cursor at the end of the buffer.
We might represent this state as follows:

~~~terminal
Hello world!▮
~~~

Where the `▮` character captures the location of the cursor.
Depending on the text editor you use, the cursor may be rendered differently.
For example, it might be a vertical bar (`|`) between characters such that inserted characters appear to the right of the bar, and deletions remove characters to the left of the bar.

For consistency's sake, we'll take the stance that _the cursor position_ is:

+   The index where text is inserted.
+   One index to the right of where text is deleted.

In our above example, this means that the cursor position is index `12` of the string.

With this in mind, if we type the characters `abc` into our editor, we expect the buffer to change with each keystroke as follows:

~~~terminal
  Hello world!▮
⇒ Hello world!a▮
⇒ Hello world!ab▮
⇒ Hello world!abc▮
~~~

We can move the cursor to the left and right of the buffer, one position a time.
For example, moving the cursor left nine times results in the following changes:

~~~terminal
  Hello world!abc▮
⇒ Hello world!ab▮c
⇒ Hello world!a▮bc
⇒ Hello world!▮abc
⇒ Hello world▮!abc
⇒ Hello worl▮d!abc
⇒ Hello wor▮ld!abc
⇒ Hello wo▮rld!abc
⇒ Hello w▮orld!abc
⇒ Hello ▮world!abc
~~~

Now, insertions and deletions occur in the middle of the buffer.
After deleting six times and then inserting the character `!`, the state of our buffer becomes:

~~~terminal
  Hello ▮world!abc
⇒ Hello▮world!abc
⇒ Hell▮world!abc
⇒ Hel▮world!abc
⇒ He▮world!abc
⇒ H▮world!abc
⇒ ▮world!abc
⇒ !▮world!abc
~~~

At this point, the buffer contains the contents `!world!abc` and the cursor is at index `1` so that subsequent insertions happen _after_ the left-most exclamation mark.

## Part 1: A Simple String Buffer

First, we will implement a simple text buffer whose backing data is a Java `String`.
The cursor in this setup is simply an index $i$ in the range $0 \leq i \leq \textsf{sz}$ where $\textsf{sz}$ is the current size of the buffer.
Note that the cursor is an essentially an index into the backing `String` except it can be one character past the last valid index, so that text insertion can occur onto the end of the buffer.

Inside `SimpleStringBuffer.java`, you'll find stubs for the following constructor and methods.
You should implement them all _and_ provide appropriate Javadoc comments for each:

+   `SimpleStringBuffer()`: constructs a new, empty `SimpleStringBuffer`
+   `void insert(char ch)`: inserts character `ch` into the buffer at the cursor's current position, advancing the cursor one position forward.
+   `void delete()`: deletes the character at the cursor's current position, moving the cursor one position backwards.
    Does nothing if there are no characters in the buffer.
+   `int getCursorPosition()` returns the current position of the cursor.
+   `void moveBackwards()` moves the cursor one position backwards.
    The cursor stays put if it is already at the beginning of the buffer.
+   `void moveForwards()` moves the cursor one position forwards.
    The cursor stays put if it is already at the end of the buffer.
+   `char getChar(int i)` returns the `i`th character of the buffer, zero-indexed.
    Throws an `IndexOutOfBoundsException` if `i` is an invalid index into the buffer.
+   `String toString()` returns the contents of the buffer as a `String`.

When you are done, you should also write a _unit test suite_ for `SimpleStringBuffer` in `SimpleStringBufferTests.java` (found in the `src/test/...` folder of the project).
Since all the methods in `SimpleStringBuffer` are interrelated, it is most fruitful to write unit tests at the level of the _whole class_ rather than individual methods.
Each unit test should cover a scenario of operations on the buffer, e.g., the transcript of edits in the Background section.
Your completed unit test suite should ultimately:

+   Exercise all methods of `SimpleStringBuffer`.
+   Cover both normal and corner cases of the buffer.

In addition to your unit test suite, use Jqwik to write at least one _property-based test_ for the buffer.
Your property can be any property that ought to be true of the buffer over arbitrary characters and integers.

## Part 2: Analyzing the Simple String Buffer

As we have discussed previously, strings in Java are _immutable_, i.e., they cannot be modified once created.
Any operations over strings, e.g., string concatenation and substring, generate new strings that copy the data from their inputs.
Review your `SimpleStringBuffer` implementation in light of this fact.
You should note that, perhaps, using a `String` as the backing data structure for your buffer may be highly inefficient!

In the appropriate section of `README.md` analyze the runtime of the `insert` method of `SimpleStringBuffer` to demonstrate this inefficiency.
Make sure to provide:

1.  The relevant input(s) to the method.
2.  The critical operation(s).
3.  A mathematical model of the runtime of `insert` as a function of the inputs and operations you chose.
4.  A Big-O characterization of the model, i.e., "`insert` is $\mathcal{O}(\ldots)$."

You should include a paragraph justifying your model, explaining the different operations that `insert` performs and how they contribute to the runtime.

(_Hint_: in your justification, you should appeal to the fact that Java strings are immutable and the consequences of this design decision in some fashion!)

## Part 3: Gap Buffers

It should be clear at this point that we need an alternative data structure to back our text editor!
In this part, you'll implement a _gap buffer_, an array-based sequence that allows for efficient insertion and deletion at the cursor point!

A gap buffer maintains a singular array broken up into three parts:

+   The text _before_ the cursor.
+   An empty gap that new characters can be inserted into.
+   The text _after_ the cursor.

For example, imagine that our text editor contains the text `Helloworld!` and the cursor is positioned between the 'o' and the 'w'.
Our gap buffer would represent this with the following array:

~~~
 0                  10
-------------------------------
|H|e|l|l|o| | | | |w|o|r|l|d|!|
-------------------------------
           ^       ^
~~~

The text before the cursor is `Hello` and the text after the cursor is `world!`, so these characters appear to the left and right of the array, respectively.
In between the two chunks of text is the _gap_, a portion of the array empty space that will be filled with newly inserted characters.
The key _invariant_ of the gap buffer is that these three sections always appear in this order:

> In a gap buffer, the text before the cursor, the unused gap, and the text after the cursor always appear in this order.

Our implementation of the various text buffer operations must maintain this invariant!
To do this, the gap buffer must explicitly track where the gap is in the overall buffer.
There are several ways to do this; we recommend tracking the _starting index of the gap_ and the _first index of the after-cursor text_.
These are indices 5 and 9 of the array above, respectively.

To demonstrate how the gap buffer operates, we'll walk through the canonical operations of a text editor and show how the data structure evolves over time.
From these examples, you should _extrapolate how insertion, deletion, and cursor movement_ works with this data structure.

**Inserting `abc`.**

~~~
 0                  10
-------------------------------
|H|e|l|l|o| | | | |w|o|r|l|d|!|
-------------------------------
           ^       ^
-------------------------------
|H|e|l|l|o|a| | | |w|o|r|l|d|!|
-------------------------------
             ^     ^
-------------------------------
|H|e|l|l|o|a|b| | |w|o|r|l|d|!|
-------------------------------
               ^   ^
-------------------------------
|H|e|l|l|o|a|b|c| |w|o|r|l|d|!|
-------------------------------
                 ^ ^
~~~

Insertion proceeds straightforwardly (as long as there is space in the gap):

1.  Inserting the character at the start of the gap region and
2.  Moving the cursor to the next empty space of the gap.

**Moving the cursor 3 spaces to the left.**

~~~
 0                  10
-------------------------------
|H|e|l|l|o|a|b|c| |w|o|r|l|d|!|
-------------------------------
                 ^ ^
-------------------------------
|H|e|l|l|o|a|b| |c|w|o|r|l|d|!|
-------------------------------
               ^ ^
-------------------------------
|H|e|l|l|o|a| |b|c|w|o|r|l|d|!|
-------------------------------
             ^ ^  
-------------------------------
|H|e|l|l|o| |a|b|c|w|o|r|l|d|!|
-------------------------------
           ^ ^    
~~~

Observe how moving the cursor left (dually to the right) requires that we:

1.  Move the end character of the left section to the start of the right section.
2.  Update our cursor start and end points to reflect that the buffer has moved.

**Delete 3 characters.**

~~~
 0                  10
-------------------------------
|H|e|l|l|o| |a|b|c|w|o|r|l|d|!|
-------------------------------
           ^ ^    
-------------------------------
|H|e|l|l| | |a|b|c|w|o|r|l|d|!|
-------------------------------
         ^   ^    
-------------------------------
|H|e|l| | | |a|b|c|w|o|r|l|d|!|
-------------------------------
       ^     ^    
-------------------------------
|H|e| | | | |a|b|c|w|o|r|l|d|!|
-------------------------------
     ^       ^    
~~~

Like insertion, deletion involves removing the character to the left of the gap and moving the start index of the gap.
Note that while I physically deleted the character in the example above, your code does not need to do this!
Our invariant ensures that any values in the gap buffer are not being used, so they can be overwritten as needed.
Thus, simply moving the start index of the gap buffer "deletes" characters implicitly!

**Insert `y`.**
~~~
 0                  10
-------------------------------
|H|e| | | | |a|b|c|w|o|r|l|d|!|
-------------------------------
     ^       ^    
-------------------------------
|H|e|y| | | |a|b|c|w|o|r|l|d|!|
-------------------------------
       ^     ^    
~~~

After inserting `y`, we arrive at the following array state.
Note that even though there is a physical gap in the array, that gap does not appear in our output!
So the result of calling `toString` on the buffer at this point should be: `Heyabcworld!`.

### Expanding the Gap Buffer

There is only one major complication with the gap buffer: what happens if we fill the array?

~~~
-----------
|a|b|c|d|e|
-----------
     ^
     ^
~~~

In this situation, the buffer contains the text `abcde` and the cursor is located between the `c` and `d` (at index 2).
However, if we try to insert another character, we find that there is no room!
(_Hint_: how do we know this is the case from the gap buffer state?)

To alleviate this situation, we must perform a common operation over array-based lists: we must _expand_ the array to make space!
This involves two steps:

1.  Allocating a new array that is "significantly" bigger than the current array.
    We'll justify this choice when we analyze the complexity of _array lists_ later in the course, but for now, let's _a priori_ choose to _double_ the array size every time we expand it.
2.  We copy over the elements from the old array to the new array.
    In the case of gap buffers, we want to ensure that the before-cursor text segment starts at the first index of the array and the after-cursor text segment ends at the end.
    The unused space between the two segments becomes our new gap!

For the example above, expanding the array results in the following updated buffer:

~~~
---------------------
|a|b| | | | | |c|d|e|
---------------------
     ^         ^
~~~

You should implement your gap buffer in `GapBuffer.java`.
Additionally, you should write a unit test suite and at least one property-based test for the data structure in `GapBufferTests.java`.
Note that `SimpleStringBuffer` and `GapBuffer` have the exact same methods!
So you should simply copy your tests for `SimpleStringBuffer` to `GapBuffer` as a starting point.
However, you should add additional tests to cover additional corner cases that might arise with the gap buffer, in particular, around array expansion.

## Part 4: A Simple TUI

With the model of our text editor completed, now we'll construct a _very_ basic user interface to complete the application!
We'll use the cross-platform [Lanterna](https://github.com/mabe02/lanterna) library for creating cross-platform text-based UIs (TUIs).
The beauty of Lanterna is that it exposes a single API that can target console-based or graphics-based TUIs, no matter what operating system you are on!

Lanterna is a third-party library, so we instruct Maven to download and integrate it into our build via the following `dependency` addition to `pom.xml` (already done for you):

~~~xml
<dependency>
  <groupId>com.googlecode.lanterna</groupId>
  <artifactId>lanterna</artifactId>
  <version>3.1.2</version>
</dependency>
~~~

Lanterna offers several levels of abstraction for writing to the terminal.
It turns out that it'll be most beneficial for us to operate of Lanterna's _screen_ which provides a "medium"-level access to the internals of the terminal.

Creating a screen object requires navigating a few classes of the Lanterna library:

+   We first create a `DefaultTerminalFactory` object which, as you might guess from the name, is responsible for making terminals!
    We can simply `new` up a `DefaultTerminalFactory()` with `new DefaultTerminalFactory()`.
+   The `DefaultTerminalFactory` object has a method, `createScreen()` that creates a screen tied to a (default) terminal window.

We can do these two steps as follows:

~~~java
// At the top of your file...
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;

// ...

DefaultTerminalFactory factory = new DefaultTerminalFactory();
Screen screen = factory.createScreen();
~~~

The `import` statements allow us to reference the `DefaultTerminalFactory` and `Screen` classes from the Lanterna library.
More specifically, once we have included the library in our build (via the `pom.xml` declaration), we can always reference `DefaultTerminalFactory` and `Screen` using their _fully-qualified names_, i.e., their names prepended with their full package paths:

~~~java
com.googlecode.lanterna.terminal.DefaultTerminalFactory factory =
    new com.googlecode.lanterna.terminal.DefaultTerminalFactory();
~~~

It should go without saying, but using the fully-qualified names for classes is usually undesirable!
Thus, we will usually use `import` statements to cut down on this verbosity.

~~~admonish hint title="IDE autocompletion"
`import` statements can be annoying to add to your code.
Without additional assistance, you would need to keep a copy of the library's documentation open and search for where relevant classes are located, so that you can plug in the relevant `import` statements.

However, modern IDE like NetBeans and VSCode (with the standard Java extensions) can do this for you!
If you type:

```java
DefaultTerminalFactory factory = new DefaultTerminalFactory();
```

You should receive an error—displayed as a red or yellow squiggle underneath `DefaultTerminalFactory`.
If you hover over this squiggle, you should receive a prompt to access _hints_ for potentially fixing the error.
In NetBeans, the hotkey to access hints for an error is alt-enter; in VSCode, it is either ctrl-space (Windows and Linux) or command-space (Mac).
With your `pom.xml` file correctly configured, your IDE is smart enough to search relevant libraries, find an entry for DefaultTerminalFactory in the Lanterna library and offer the suggested fix to add the relevant `import` statement!
Feel free to use this shortcut for any classes that require `import` statements in your code!
~~~

Note that we'll never use the `DefaultTerminalFactory` after making the screen!
Thus, it is more idiomatic to get rid of the intermediate `factory` local variable and combine everything onto one line:

~~~java
Screen screen = new DefaultTerminalFactory().createScreen();
~~~

Note that `screen` throws an `IOException`, so you will need to add a `throws IOException` clause to `main`'s method signature.

### Rendering

Once we have access to a `screen` object, the following methods will be useful for writing to the terminal:

+   `screen.startScreen()` initializes the state of `screen` so that it is ready to render text.
+   `screen.stopScreen()` cleans up the `screen` and its underlying terminal, so that it is ready to be used by other programs.
    This method _must_ be called after you are done with the `screen` but before the program exits!
+   `screen.refresh()` moves the contents of screen's back-buffer to its front-buffer.
    It turns out the screen maintains two views of the terminal, a visible front-buffer and a back-buffer.
    All writes to the screen are first applied to the back-buffer and are not visible until `screen.refresh()` is called.
    This allows the library to provide a `setCharacter` method that takes a `row` and `col` versus being constrained to input text only at the terminal's cursor.
+   `screen.setCharacter(int row, int col, TextCharacter ch)` prints character `ch` to position `(row, col)` to the screen's back-buffer.
    To create a `TextCharacter` from a regular `char`, you can use the `TextCharacter.fromCharacter(char ch)` static function of the `TextCharacter` class.
+   `screen.setCursorPosition(TerminalPosition pos)` sets the cursor to the given position in the back-buffer.
    Note that since `setCharacter` allows you to write characters at arbitrary positions of the screen, this method is purely cosmetic.
    It causes the terminal to render the cursor at the given position.
    The `TerminalPosition` class provides a two-argument constructor, `new TerminalPosition(int row, int col)`, that you can provide to `setCursorPosition`.

Because of the back-buffer/front-buffer nature of the `screen` object, it'll be useful to write a helper method `drawBuffer(GapBuffer buf, Screen screen)` that renders the _entire_ `GapBuffer` to the given `screen`, calling `screen.refresh()` to update the display.
Lanterna takes care to only render the _difference_ between the back-buffer and the front-buffer, making this "full screen refresh" style of rendering as efficient as possible.

### Input-Output

To perform input-output with the `screen` object, you can use `screen.readInput()` to grab a `KeyStroke` from the keyboard.
Note that `readInput()` waits for the user to press a key before returning.

The `KeyStroke` object has two important methods to determine what key was pressed:

+   `stroke.getKeyType()` returns a `KeyType` object that you can check (via the `equals()` method) to see what kind of key was pressed.
    Important for our text editor are the key types:
    -   `KeyType.Character` is the type of keys that correspond to characters.
    -   `KeyType.ArrowLeft` is the left arrow key.
    -   `KeyType.ArrowRight` is the right arrow key.
    -   `KeyType.Backspace` is the backspace key.
    -   `KeyType.Escape` is the escape key.
+   `stroke.getCharacter()` returns the `char` corresponding to the key pressed if the key type is `KeyType.Character`.

Your text editor should respond to key presses as follows:

+   Characters are added to gap buffer.
+   The backspace key deletes from the gap buffer.
+   The arrow keys move the cursor.
+   The escape key exits the program.

To accomplish this, you'll want to set up a loop in `main` that repeatedly processes `screen.readInput()` calls until escape is pressed.
You can then use your `drawBuffer` function at the end of each iteration of the loop to render whatever changes the keystroke made to the buffer:

~~~java
boolean isRunning = true;
while (isRunning) {
    KeyStroke stroke = screen.readInput();
    // TODO: Process the key stroke!
    drawBuffer(buf, screen)
}
~~~

### File Input/Output

Finally, you'll want your text editor to read from and write to files!
More specifically, the program takes a file as input.
The contents of the file (if it exists) become the initial contents of the editor.
When the program exists, the contents of the text editor are written to the file (overwritten if it already exists or newly created if it didn't exist).

Luckily, in modern Java, these behaviors are pithy one-liners thanks to the "new I/O" (`java.nio`) package of the standard library!
There are two relevant static methods of the `Files` class for reading and writing the contents of a file as a String:

+   `Files.readString(Path path)` returns the contents of the file specified by the given `path` as a `String`.
+   `Files.writeString(Path path, String contents)` writes `contents` to the file specified the given `path`.

To create a `Path`, you can use the static `Paths.get(String path)` function which creates a `Path` object from a `String` representing a path.

You can check whether the path points to a valid file (and not a directory) with a combination of two methods:

+   `Files.exists(Path path)` returns true if and only if `path` points to a valid file or directory on disk.
+   `Files.isRegularFile(Path path)` returns true if and only if the `path` in question points to a file (and not a directory).

If the path given to the text editor does not exist, then the text editor is loaded with an empty buffer.
Note that `Files.writeString` will overwrite an existing file or create a new file if it does not exist already, so there is no need to do any checks as you write the file to disk and exit the program.