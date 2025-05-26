# Spellchecker

For this lab, clone the following repository and submit your work to Gradescope when you are done:

+   <https://github.com/psosera/spellchecker>

## Part 1: Try Out Tries

First, let's check our understanding of the Trie data structure by building a Trie by-hand for the following set of words.
Simulate how the `add` operation of the Trie will work by adding each word in sequence to an initially empty Trie.
Draw this Trie on a whiteboard or piece of paper for future use!

+ dog
+ doge
+ dogma
+ doggo
+ dogy
+ dig
+ digit
+ dign
+ dega
+ degas
+ degum
+ degs

In your diagram, make sure to be explicit where individual characters and whole words "exist" in the structure.

## Part 2: Representation

Now that you've walked through the creation of a Trie, implement the following members of `SpellChecker`:

+   The `Node` class.
+   `void add(String word)`: adds `word` to the trie.
+   `SpellChecker(List<String> words)`: creates a new spell checker with the given set of words.

## Part 3: Basic Spellchecking

For the remainder of this lab, we'll follow the pattern set up in the previous parts.

1.  We'll use our running paper example to explore how we might implement a spell-checking operations.
2.  We'll then implement the corresponding method in the project.

First, let's use our trie to check whether a word is spelled correctly.

Use your paper trie to trace through how you would check whether the following words are in the set:

+   doggo
+   digy
+   dog

From this, implement `boolean isWord(String word)` that checks whether the given `word` is in the SpellChecker's trie.

Once you have implemented this method, you can run the `check` command of the `SpellChecker` program.
For example, you can run the program at the terminal as follows:

~~~console
$> mvn compile exec:java -Dexec.args="check doggo"
~~~

Check out the `main` method in `SpellChecker` to see how everything is wired together.

The `SpellChecker` programs pulls its dictionary from a comprehensive list of English words found at the following Github repository:

+ <https://github.com/dwyl/english-words>

The dictionary file, `words-alpha.txt` is included at the root of the project.
Feel free to inspect it to determine what inputs to feed to your program to test its functionality!

## Part 4: Autocompletion

Next, let's move beyond checking into offering suggestions to the user.
The first kind of suggestion we'll consider is autocompleting a word.
For simplicity's sake, we'll constrain ourselves to autocompleting a word _by adding a single character onto the end_.
However, once you implement this process, you can likely see how you might generalize this to capture any n-character completion of a given word.

Use your example trie and imagine how we might determine the one-character completions of the following word prefixes:

+   deg
+   dog

Once you have completed this, feel free to implement `List<String> getOneCharCompletions(String word)` which gives all the one-character completions of `word` found in the dictionary.

## Part 5: Simple Suggestions

Autocompletion, is perhaps, a bit forward-thinking.
Really, when we think of spell checking, we think not only of being told if a word is misspelled, but also what are likely _suggestions_ for fixing the misspelling!
To begin, let's consider making autocorrection suggestions for spelling errors that arise because _the last letter of the word is wrong_.

Imagine we are given the following word, `digy`.
Trace through how you would navigate the trie to discover all possible suggestions to correct this misspelling by replacing the last character 'y' of the word.

With this in mind, implement `List<String> getOneCharEndCorrections(String word)` which gives all possible autocorrects of `word` that come about by replacing the last character of `word` with a new character.

## Part 6: One-letter Errors

In the previous part, you only considered errors and subsequent fixes that arise by fixing the last letter of the word.
Generalize this procedure to consider corrections that arise by fixing _any one letter_ found in the word.
Consequently, implement `List<String> getOneCharCorrections(String word)` and change the `correct` command to use this function!

## Additional Extensions

In this lab, we've considered basic spellchecking functionality, but there are many additional features to add!

+   How might we handle _multi-character_ corrections?
+   How might we _prioritize_ different corrections, e.g., by number of edits or common errors?
+   How might we filter/include/exclude _word extensions_, e.g., pluralization?