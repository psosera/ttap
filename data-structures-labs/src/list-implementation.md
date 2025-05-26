# List Implementation

In today's lab, you'll implement the list abstract data type in two ways: arrays and links.
Fork the repository found here to your Github account:

*   <https://github.com/psosera/listlab>

Clone your copy of the repository to your computer and complete the basic implementation of the `ArrayList` and `LinkedList` classes.
This includes filling out all the basic list methods found in each class.

Additionally, complete the test suite for each of the classes (`ArrayListTests.java` and `LinkedListTests.java`, respectively).
To do this, make sure to write unit tests that cover normal-case and base-case scenarios for each method.
Also, write at least _two additionally_ property-based tests for your list implementations.
(_Hint_: the two list implementations share the same set of methods, so they can probably share the same tests...!)

When you turn in this lab to Gradescope, use the option to upload a repository directly from your Github account.
This will ensure that your directory structure is consistent with what the autograder expects!
However, you should make sure to **push all your changes** to your Github repo before you submit the lab!

## Additional List Methods

Once you complete your basic list implementation, implement the following methods for additional practice working with array-based and linked-based lists:

+   `boolean isEmpty()`: returns `true` if and only if the list has no elements.
+   `void clear()`: removes all the elements from the list.
+   `int indexOf(int value)`: returns the index of the first occurrence of `value` in the list or `-1` if `value` is not in the list.
+   `boolean contains(int value)`: returns `true` if and only if `value` is found in the list.
+   `void add(int index, int value)`: adds `value` to the list at `index`, throwing an `IndexOutOfBoundsException` if `index` is out of range (`index < 0 || index > size()`).

## Advanced Operations

Want additional practice with list operations?
Try implementing the following methods:

+   `String toString()`: returns a string representation of the list in the form `"[x1, x2, x3, ...]"`.
+   `boolean equals(ArrayList other)` and `boolean equals(LinkedList other)`: returns `true` if this `list` is equal to `other`, i.e., they contain exactly the same elements in the same order.
+   `ArrayList concat(ArrayList other)` and `LinkedList concat(LinkedList other)`: returns a new list that contains the elements of this list and the elements of the `other` list.