# Java Reference

This Java reference is an abridged, summarized version of the grammar presented in the [Java Language Specification](https://docs.oracle.com/javase/specs/jls/se23/html/index.html).

## Programs

A program is made up of a collection of declarations:

~~~
Declaration:
  | import <Name>;            # import declaration
  | <ClassDeclaration>        # class declaration
  | <InterfaceDeclaration>    # interface declaration
~~~

## Class Declarations

~~~
ClassDeclaration:
  | <VisibilityModifier> class <Name> { <Member>* }

Member:
  | <VisibilityModifier> [static] <Type> <Name> ( <Argument>* ) { <Statement> }