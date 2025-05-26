# Rooms

In this lab, you will get a head start on the [final homework](./text-adventure.md) by implementing the architecture of the rooms for your text adventure.

The heart of a text adventure is its collection of rooms.
The player traverses these rooms, collecting items and solving puzzles, to advance to the end of the game.
They do so by interacting with the rooms using a fixed set of commands, e.g., walking from one room to another, looking around a room, or just waiting.

For example, we may have a simple text adventure consisting of four rooms:

~~~
Bedroom --> Hallway --> Bathroom --> Living Room
~~~

Where the game starts with the player in the bedroom, and they have to get ready for school and leave the house via the living room.
They might have to explicitly get up, put on their clothes, go to the bathroom, eat breakfast, and gather their things in the living room before leaving.

## Representing Rooms

We can represent the rooms of our game in a variety of ways.
In this homework, we will implement our rooms using a class hierarchy where the main piece of state in our game is the _current room that the player is in_.
Recall from the homework write-up that we recommend the following set of commands that you will need to support:

* `Wait`: wait in the room for one turn
* `Go <direction>`: go in the given cardinal direction, *e.g.*, `north` or `south`
* `Talk to <object>`: talk to the given object found in the room
* `Pick up <item>`: pick up the given item found in the room
* `Use <item>`: use the given item found in the player's inventory
* `Attack <object>`: attack the given object found in the room
* `Look at <object>`: look at the given object found in the room

With this in mind, we can envision a room as being defined by how it responds to each of these commands.
We can define an interface `Room` that contains "response methods" for each of the commands that the game supports, e.g, a `waitResponse` method or a `pickUpResponse` method.
Each method would be responsible for updating the game state and perhaps printing flavor text to the console based on the command issued.

We can then define classes that implement this interface, one for each room in our game.
For example, we might create the following class hierarchy for the set of rooms above:

~~~
       Room
         |
      -------
    /  |  |  \
   /   |  |  Living room
 Bedroom  |
       | Bathroom
       |
    Hallway
~~~

Each room can then implement appropriate, room-specific responses to each of the commands.
The commands might change the state of room, the state of the game, print text to the console, or some combination of all these things.

Initially, we can start with `Room` as an interface, but there will likely be shared state and behavior between many of the different rooms.
To factor out this redundancy, you should then use abstract classes and inheritance as necessary.

## Plan Out Your Game

Spend this lab planning out your game with your partner, in particular:

1. Decide on a premise for your game.
   What is the story?
   What is the player's objective?
2. Plan out the rooms of your game.
   What rooms are there?
   How are they connected?
3. Begin implementing your game by building an appropriate `Room` interface and subclasses of this interface, one for each concrete room in your game.
