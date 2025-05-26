# Text Adventure

A *text adventure* is a sort of [interactive fiction](https://en.wikipedia.org/wiki/Interactive_fiction) that is entirely text-based.
The world is presented in text and the way that the user interacts with the world is with natural language-like text.
In the early days of computers where terminals and text-user interfaces (TUIs) were the norm, text adventures were one of the primary kinds of computer games that people played.
In the modern age of computing, text adventures remain alive thanks to the dedication of smaller communities dedicated to developing quality text adventure games.

For reference, you can check out these classic text adventure games online for inspiration:

* [Adventure, Zork, Jigsaw, Galatea from webadventures.org](http://www.web-adventures.org/)
* [Hitchhiker's Guide to the Galaxy (via bbc.co.uk)](http://www.bbc.co.uk/programmes/articles/1g84m0sXpnNCv84GpN2PLZG/the-hitchhikers-guide-to-the-galaxy-game-30th-anniversary-edition)

A text adventure game is made up of several systems:

* A *language parser* that interprets user commands, *e.g.*, looking around.
* A collection of *rooms* that constitute the game world.
  The player navigates these rooms, interacting with objects in the room, to acquire items, solve puzzles, and advance deeper into the world.
* An *inventory of items* that the user collects as they traverse the world.
  Frequently, these items act as keys, allowing the user to change the state of a room (*e.g.*, unlocking a chest which reveals an item) or a unlock a door that allows them to progress.

In this homework, you will implement these systems in your program and put them together to create a final text adventure game of your own design.
Your work will be graded primarily on your implementation of these systems, but a small portion of the grade will also be based on your creativity in building your adventure.

## An Example Text Adventure

Here is an example of how your text adventure might play out:

~~~
$> java TextAdventure

Welcome to the "You're Late" Adventure Game!
Your goal is to get to class on time.

001 =====
Bzzzzt.  Bzzzzt.  Bzzzzt.
> Wait

002 =====
Bzzzzt.  Bzzzzt.  Bzzzzt.
> Wait

003 =====
Bzzzzt.  Bzzzzt.  Bzzzzt.
(You head is swimming and the alarm isn't helping.  You should probably get
up.)
> Get up.

004 =====
You sit upright.  The sudden burst of activity from lethargy makes you
nauseous. But nevertheless, you power through the pain and open your eyes.

You are greeted with darkness.  Panic begins to fester in your gut.  "Am I
blind?  What is going on!?"
> Rub eyes.

005 =====
You rub your eyes and light quickly pours into your vision.  You see your
clothes in a crumpled heap at the foot of your bed.  On your nightstand,
your alarm clock continues buzzing without a care in the world.  Finally,
to the north of your room, the door to the hall awaits you.

You rub your eyes again and note the time on your wall clock slowly becomes
readily.  "Seven... forty-five!"  Oh no, you only have 15 minutes to get
to class!
> Stand up.

006 =====
You get out of bed.  Miracously the room has not changed its configuration
since you last looked at it.
> Move north.

007 =====
You open the door and begin running to class.  You make it 10 feet from
your door when you realize that you are stark naked save for your underwear.
You retreat back to your room and quickly shut the door hoping that no one
saw you in your haste.
> Pick up clothes.

008 =====
You pick up your clothes and hold them in your hands.  Clothes are important,
but you feel like you need to do more than just cherish them in this manner.
> Put on clothes.

009 =====
You put on your clothes.  Now you are decent!  You feel like you can tackle
the day, confident in the knowledge that no one can see your underwear.
> Move north.

010 =====
You open the door and begin running to class.  What dangers await you outside
on the way to class?  Boy aren't you excited to see where this adventure
takes you next!
~~~

The text adventure takes the form of a series of _turns_ where the user is given a description of the room they are in and its current state.
The user is then prompted to give a command.
The command is processed by the game, advancing the room's state or moving the player to another room.

## Language Parser

The first component of your text adventure is the _interpreter_ for commands that the user will enter.
The commands take the form of simple imperative sentences, for example:

* Go north
* Stand up
* Talk to the barber
* Use key

In a text adventure game, the user can enter a surprisingly diverse amount of commands.
To keep our game simple, we'll only draw on a specific set of commands, although you are free to extend the language of commands as you deem necessary:

* `Wait`: wait in the room for one turn
* `Go <direction>`: go in the given cardinal direction, e.g., `north` or `south`
* `Talk to <object>`: talk to the given object found in the room
* `Pick up <item>`: pick up the given item found in the room
* `Use <item>`: use the given item found in the player's inventory
* `Attack <object>`: attack the given object found in the room
* `Look at <object>`: look at the given object found in the room

The behavior of these commands depends on the room that we are in.
Therefore, your parser should merely _identify_ what type of command the user has entered and the subject of that command, if any.
It will be up to your rooms (see below) to perform an action based on the given command.

## Rooms

The collection of rooms of your text adventure form the core of the game.
A room contains dialog that is presented when the player enters the room and the responses to each of the player's commands while they are in the room.
Commands can have arbitrary behavior that the room dictates, for example:

*   Picking up an object that is present in the room may add it to the player's inventory.
*   Looking at an object in the room gives a description of that object.
*   Using an item in a room may change the state of the room.
    For example, the player may use a key to open an impassable door.

Changing the state of the room via a command is particularly common.
A fun thing that text adventure games do is simply have the player `wait` a number of turns, changing the state of the room each time with new dialog.

Finally, rooms are typically connected to one another and navigated by the player using the `Go` command.
For example, `Go north` would move the player to the room north of this one.
However, it might not be possible to `Go north` if, e.g., the door is locked or the passageway has not opened yet.
Frequently the gameplay mechanics of a text adventure has the player solving puzzles and acquiring keys to enable them to move onto subsequent rooms.

## Inventory

Finally, the game maintains an inventory of items that the player has picked up throughout the course of the game.
More often than not, these items are simply _keys_ that the player can use in a room to unlock or enable new paths.
You might design a room that requires the use of an item to enable another key item to be retrieved, e.g., a chest that opens with the use of a hammer.
You might also have collections of items that the player must find and then use in conjunction with each other to produce the key to the next room.

Optionally, your text adventure may add in role-playing game-like elements.
Items may be _equipment_ that boosts the player's stats or otherwise let them defeat monsters that they would not be able to defeat otherwise.

## Program Design

In the description of the text adventure game, we have left the specifics of the program intentionally vague.
This is your opportunity to take what you have learned in the course regarding data structures and object-oriented programming to put together a well-designed program.
Because the game is open-ended, there are a lot of variations that your program can take on, e.g., you might need to keep track of player stats such as health and armor if your game takes on the form of a role-playing game, or it might need a subsystem for crafting items if your game allows it.

As a starting point, we recommend that you decompose your program as follows:

*   Each of the major systems of the game should have its own class, i.e., a `Parser`, `Room`, and `Inventory` class.
    These systems should be all used in the main driver for the game, `TextAdventure`.
*   Several of these systems require additional classes for components that they manage---for example, `Command`s generated by the `Parser` and `Item`s held by the inventory.
*   Furthermore, several of these systems may potentially be realized as class hierarchies related with some top-level interface or superclass—for example, the `Room`s themselves, the `Command`s, and the `Item`s.
    You will need to decide how best to represent these components in your program.

Make sure to keep your program design simple!
You should only add in functionality to your program to handle your particular game; you do not need to generalize different constructs except when it makes your program more concise and readable.
It is a good idea for you to sit down and develop an initial idea of what your text adventure will look like before you write your code so that you have an idea of what additional features you will need.

Ultimately, your program `TextAdventure` should operate as follows:


+   Greet the user of the program
+   Set up the initial state of the game, in particular, put the player in the initial room.
+   Print the entry text for that initial room.
+   While the game has not yet ended:
    -   Get a command from the user.
    -   Process that command along with the current room and player state and print out the results of that command.
