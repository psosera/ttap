# The World's Best Internship

Welcome to Gamewerks Corporation!
You landed your dream internship at the best game manufacturer on the planet according to the advertisement you saw on a bulletin board in JRC.
You are sitting in your office on your first day with five of your best internship friends in a rundown building in downtown Des Moines when you boss comes in and screams:

> "[Insert your name here], we have a crisis!
> We have a game that we need to ship by *next Wednesday* and we've been such slackers in get it out the door!
> If we don't get this to our publisher by then, we'll go out of business!
> We need you to fix it, add the last couple of features, and ship it!"

Well this seems completely unreasonable, but because your boss has absolute power over you, you acquiesce to their demands.

## Github

First thing first: you need to get the code.
Gamewerks Corporation uses [Github](http://github.com) to store their source code.

* [psosera/blocky](https://github.com/psosera/blocky)

In keeping with good Github practices, your boss wants you to *fork* the repository to your personal account rather than clone it.
That way, you can make all the changes you want in your personal repository without fear of blowing away anyone else's work in the company.
To fork the repository from the Github website, you should click the "Fork" button in the top-right corner of the repository's homepage.
This makes a linked clone of the repository in your Github account which you can then clone locally and work with.

## Emergency Debugging

With a local repository on disk, you look at the source code.
It's a mess!
The code is uncommented and appears to be poorly designed.
Furthermore, the game immediately crashes when you try to run the program!

It seems like your first task is to get the game into a *playable state*.
You should:

1. Review the code so that you understand how the different classes in the program interact and
2. Fix bugs in the program until you can run the game to completion.

For each some bug you fix, you should file a *Git commit* with an appropriate comment describing the fix (so your boss knows that you did some work).
And for some odd reason, you have a strange feeling that you will need to fix *at least five such bugs* before the game can be played from start-to-finish.

Of course, you have no idea what kind of game this is.
Without knowing how the game works, you don't really have any way of fixing bugs.
So you ask your boss what exactly this Blocky stuff is:

> "...it's [Tetris](https://en.wikipedia.org/wiki/Tetris)."

Oh, well that explains it.
You should make the program behave like the classic game, Tetris.
You know you don't really need docs for this because you can simply Google one of the billion Tetris clones out there to get a feel for how it works.

## Random Block Generation

After getting the game into a playable state, you have noticed that it is still horribly broken!
In particular, it looks like only the "I" block spawns every time a new block is required; this isn't how ~~Tetris~~ Blocky should work!
You look up online on what to do, and it appears that the official ~~Tetris~~ Blocky standard states that you should:

*   Have (a) an array containing all the possible block kinds and, as a piece of state, (b) the current index of the array you are on.
*   Shuffle the contents of the array.
*   When you need to get a random block kind, return the piece corresponding to the current index you are on and then increment the index.
*   When the array is exhausted, *i.e.*, the last piece has been returned, reshuffle the array and reset the current index.

In this manner, it looks like that you will never randomly generate two block kinds without first generating all the other kinds beforehand.

From this algorithm you have identified that you need:

1. An array to hold the blocks.
2. An integer to remember the current index.

However, your boss says that you aren't allowed to use the Java standard library to shuffle the array for you!
This makes absolutely no sense because the rest of the code base does this, but again, they have power over you.

Luckily, you have identified the [Fisher-Yates Shuffle](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle) as an appropriate algorithm for shuffling an array.
You will likely need to create a `shuffle` method that takes an array of pieces and shuffles them as a result.
So you concede the point and implement these things along with the random piece generator, taking care to integrate your changes appropriately into the pre-existing code.

Once you are done, you should file a Git commit marking the changes.

## Update Your Changelog

Whew.
At this point, the game is in... well, some playable condition.
Mission accomplished!

At this point, you remember that it is Gamewerks policy to include a _changelog_ in the project's `README.md` file that captures all the changes you made to your codebase.
You find it a bit redundant since you could always query git for this information, but this changelog is supposed to contain _the output of the `git log` command_, verbatim.
Oh well, you go ahead and make your edits, also making sure to fill in the remaining `TODO`s in `README.md`, e.g., your name and resources used.

## Turn-in

Finally, once you are done, you know that you should create a `.zip` archive containing your _Git repository_ and submit it to Gradescope.
Why Gradescope when this is an internship?
Who knows, but you know you'll want to ensure that your `.zip` has the following folder structure:

~~~
Blocky/
┠─ pom.xml
┠─ README.md
┠─ .git/
│  └─ ... 
└─ src/
│  └─ ... 
~~~

You also know that once you are done submitting your work, you should get your things and leave the building as soon as possible, so you don't go down with this sinking ship.
