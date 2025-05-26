# Refactoring with Inheritance

In this lab, we practice using inheritance to refactor code to avoid code duplication.
We'll also revisit graphs and practice traversing a graph represented using mapping data structures.

## Problem 1: Redundancy Reduction Factory Factory

Consider the following interface and classes that represent employees in an organization:

~~~java
// Employee.java
public interface Employee {
    public String getName();
    public String getId();
    public int getSalary();
    public String makeWorkNoise();
}
~~~

~~~java
// Programmer.java
public class Programmer {
    private String name;
    private String id;

    public Programmer(String name, String id) {
        this.name = "(PROG) " + name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getSalary() { return 80000 * 2; }
    public String makeWorkNoise() { return "Clack clack clack"; }
}
~~~

~~~java
// Accoutant.java
public class Accountant {
    private String name;
    private String id;

    public Accountant(String name, String id) {
        this.name = "(ACCT) " + name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getSalary() { return 80000; }
    public String makeWorkNoise() { return "$$$"; }
}
~~~

~~~java
// Manager.java
import java.util.List

public class Manager {
    private String name;
    private String id;
    private List<Employee> reportees;

    public Programmer(String name, String id, List<Employee> reportees) {
        this.name = "(MGR) " + name;
        this.id = id;
        this.reportees = reportees;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getSalary() { return 120000 * 3; }
    public String makeWorkNoise() { return "Do Work Do Work Do Work"; }
}
~~~

~~~java
// CEO.java
import java.util.List;

public class CEO {
    private String name;
    private String id;
    private List<Employee> reportees;

    public Programmer(String name, String id, List<Employee> reportees) {
        this.name = "(CEO) " + name;
        this.id = id;
        this.reportees = reportees;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getSalary() { return 80000 * 100; }
    public String makeWorkNoise() { return "Do More Work More Do More Work More Do More Work"; }
}
~~~

Using inheritance, refactor this code to eliminate redundancy while capturing the hierarchical nature of the employee structure.

## Problem 2: Catching Up with Graphs

Consider the following partial implementation of a social network graph, including an example graph for testing purposes:

~~~java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Program {
    private static final Rel[] EXAMPLE_NETWORK = new Rel[] {
        Rel.of("A", "B"), Rel.of("A", "D"), Rel.of("A", "E"),
        Rel.of("B", "A"), Rel.of("B", "C"),
        Rel.of("C", "B"), Rel.of("C", "E"),
        Rel.of("D", "A"),
        Rel.of("E", "A"), Rel.of("E", "C"),
    };

    public static void main(String[] args) {
        SocialNetwork net = new SocialNetwork(EXAMPLE_NETWORK);
        System.out.println(net.toString());
    }
}

/**
 * A single friend relationship between two people: "p1 is friends with p2."
 */
record Rel (String p1, String p2) {
    public static Rel of(String p1, String p2) {
        return new Rel(p1, p2);
    }
}

/**
 * A social network captures friend-relationships between people.
 */
public class SocialNetwork {
    private Map<String, List<String>> nodes;

    public SocialNetwork(Rel[] pairs) {
        // TODO: implement me!
    }

    public List<String> getPeople() {
        // TODO: implement me!
    }

    public boolean areMutualFriends(String p1, String p2) {
        // TODO: implement me!
    }

    public List<String> friendCircleOf(String p) {
        // TODO: implement me!
    }

    @Override
    public String toString() {
        List<String> rels = new ArrayList<>();
        for (String p1 : nodes.keySet()) {
            List<String> neighbors = nodes.get(p1);
            for (String p2 : neighbors) {
                rels.add(Rel.of(p1, p2).toString());
            }
        }
        return String.format("[%s]", String.join(", ", rels));
    }
}
~~~

Extend `SocialNetwork` with the following functionality:

1.  Implement the constructor `SocialNetwork(Rel[] pairs)` which creates a new social network with the given friendship pairs.
2.  Implement `List<String> getPeople()` which returns a list of all the people in the social network.
    (_Hint_: where are all the people stored?
    How can you easily retrieve them?)
3.  Implement `boolean areMutualFriends(String p1, String p2)` that returns true iff `p1` and `p2` are mutual friends.
4.  Implement `List<String> friendCircleOf(String p)` which returns the _friend circle_ of `p`.
    The friend circle of `p` are the friends of `p`, the friends of the friends of `p`, the friends of the friends of the friends of `p`, and so forth.

    (_Hint_: to compute this list, you need to mindful of (a) duplicates names in your result and (b) avoiding cycles in the graph.
    It is prudent to treat this like a recursive tree traversal, passing along extra information as parameters to gather results and track who you have visited so far, so that you don't walk into a cycle.)