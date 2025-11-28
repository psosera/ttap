# Graphs and Social Networks

In this lab, we'll explore how we can implement graphs in Java and apply a graph to study real-world social network data.
For this lab, write your code in a fresh Java file `SocialNetworks.java` and submit it to Gradescope when you are done.

## Part 1: Implementing a Generic Graph

Write a class `Graph<T>` that implements a generic graph data structure using an _adjacency list_, i.e., a mapping from nodes to lists of nodes they are connected to via an edge.
We'll further assume that a `Graph<T>` is an _undirected_ graph over elements of type `T`.
You can define `Graph<T>` as a package-visible class (_i.e._, without a `public` or `private` visibility modifier) directly in `SocialNetworks.java`.

+   `Graph()` constructs a new, empty graph.
+   `void add(T v1, T v2)` adds an edge between values `v1` and `v2` in this graph.
    Note that since `Graph<T>`s are undirected, our implementation of add must account for the fact that `(v1, v2)` and `(v2, v1)` are valid edges in the graph.
+   `boolean isNode(T v)` returns `true` iff `v` is a node of this graph.
+   `List<T> getNodes()` returns a list of the nodes (values) in this graph.
+   `List<Edge<T>> getEdges()` returns a list of the edges in this graph.
    Define an `Edge` to be the following record class:

    ~~~java
    record Edge<T> (T v1, T v2) { }
    ~~~

    Which you can define directly in `SocialNetworks.java`, too.
    
In subsequent parts, you will add additional methods to this class to support your exploration of the Facebook dataset that you will process with your `Graph<T>` class.

## Part 2: Loading the Facebook Dataset

To exercise your `Graph<T>` class, you will use the following data set that captures _social circles_ in social networks.
You can access the dataset from the Stanford Network Analysis Project (SNAP) website:

+ <https://snap.stanford.edu/data/egonets-Facebook.html>

While not necessary for this lab, I recommend checking out the paper that spawned this dataset in your spare time if network analysis sounds interesting to you:

+ J. McAuley and J. Leskovec. [Learning to Discover Social Circles in Ego Networks](http://i.stanford.edu/~julian/pdfs/nips2012.pdf). NIPS, 2012.

From the website, download and expand the `facebook.tar.gz` which contains a collection of text files file.
Of interest are the `<id>.edges` files.
This file contains a collection of _edges_ of the dataset's graphs.
Each graph represents the friends of a user with the given `<id>`, called the _ego_ in the paper, and the friend relationships between these people.
The friends of the ego are called _alters_, so we can say each graph represents the friend relationships between the alters of a particular ego drawn from the social network.
Note that by definition, the ego would be friends with all the alters in the network, so the ego does not appear in the dataset to avoid muddying up analysis.

The purpose of this dataset is to identify communities/clusters within these graphs, e.g., the set of alters that form an ego's college friend network.
The remainder of the datafiles pertain to this task.
For our purposes, we will only care about friend relationships between alters!

Each `*.edges` file has a simple format.
Each line is one edge of the graph of the form: `<friend id #1> <friend id #2>` denoting that the two alters are friends in the network.
For example, here are a few lines for from the text file for reference:

~~~
49 192
49 241
49 255
50 109
50 113
~~~

These five lines describe friend relationships between alters `49` and `50` are others in the graph.
For example, alters `49` and `255` are friends according to this snippet.

In a class called `SocialNetworks`, write a `main` method that expects one command-line argument, one of the `.edges` files, and converts it into a `Graph` object.
To check your work, use `getNodes` and `getEdges` to fetch the nodes and edges from the graph and check whether your results are consistent with the contents of the text file.

# Part 3: Basic Search and Bacon's Law

A key insight from network science is ["six degrees of separation"](https://en.wikipedia.org/wiki/Six_degrees_of_separation), the idea that all people are connected via six or fewer friendship relationships.
This notion is more popularly known as _Bacon's Law_, a game where you try to connect actors to Kevin Bacon by shared film credit relationships.

Let's use our `Graph<T>` implementation to explore this idea in our dataset.

In `Graph<T>`, add a method:

+   `boolean areConnected(T v1, T v2, int n)` returns `true` if there exist a sequence of at most `n` friend relationships that connect `v1` and `v2`.

Implement `areConnected` as a depth-first search of size `n` through the graph.

Finally, augment the `SocialNetwork` program to take three additional arguments, two values and an integer, and return whether `v1` and `v2` are connected in the given graph within `n` relations.
