## About ####
ModuleGraphics is a library designed to draw data visualizations usually linked to expression data. Rather than drawing directly on a Graphics object, resulting in hard to maintain java code, programmers can use the graphical elements provided by this library to compose the figures they want to generate.

## Elements ####

Rather than drawing directly on `Graphics` objects, ModuleGraphics provides generic drawing Elements: a matrix, a label, a tree structure, etc... In this approach, each part of the figure extends the abstract class `Element`.
An Element knows its own Dimensions, margins, unit, etc... so it is able to draw itself on a `Graphics2D` object and report its properties to a parent `Element`.

## Grouping Elements #####

Elements can be added to other elements. 
* A `Canvas` is an `Element` that groups and layouts other Elements. It arranges the Elements into a grid, according to the Dimension each Element returns, taking into account the spacing of the grid itself and the margins each Element returns. As a `Canvas` is also an `Element`, adding a Canvas to a Canvas is a method of creating nested layouts.
* Each Element can be built from other Elements. The parent Element should then register itself as a parent of the subelement. A canvas does this automatically, so the convenient way of drawing more complicated Elements, is extending the Canvas class (instead of Element) and overriding its paintElement method (not forgetting to first call `super()`).

## Scale #####

The 'unit' is the Dimension used to decide how much space to give to one square in an expression matrix, or to one line for a label. An Element automatically asks the unit to its parent when its not explicitly set. In this way, you have to set the unit only once, for the main canvas (optional, as a canvas can return a default unit).

## Interactivity #####

Technically, only the enclosing `JLabel` can listen for and react to mouse interactions. You can however add MouseListeners to every Element. The `ElementEventPassThrough` will pass through any clicks from the `JLabel` to cascade through Canvas and Elements.

A developer creating new (composed) elements, can add a new `ElementEventChildForwarder(this)` as a `MouseListener` to its Element. This will pass on any Events to its registered child Elements, so they can be notified of interactions. 

A clicked Element, will check all its child Elements to find out which child has been clicked by `getHitChild(int x, int y)`. You can override this method in a newly created Element if you would have a more efficient way than iterating over all elements.

As needed, Elements are allowed to provide methods like `getHitLabel()`, `getHitData()`, etc...


## Display #####

The library provides two containers to display a Canvas. 

The `CanvasLabel` extends `JLabel` and the constructor takes a Canvas object. This gives you a JLabel that can easily be included into JPanel, JScrollPane, etc...

`CanvasFigure` takes a Canvas object and a file name. Currently a CanvasFigure can be written to a .pdf, .eps or .png file.

## Development #####

ModuleGraphics intends to be a generic graphical library. It's supposed to be independent of the visualized data, so it should contain no references to genes, modules, etc... specific features. It should stay focused on asking generic data as input (Strings, matrices of doubles, ...) and outputting figures on `Graphics2D` objects. Other applications (like [ModuleViewer][]) then can extend or override the Elements to create application specific graphics. Also parsers, GUI, etc... are the responsibility of applications that use this library and not of the library itself.

New Elements should be added to the `elements` package. When extending the abstract `Element` class, at least two methods should be implemented:
* `paintElement(Graphics2D, xOffset, yOffset)`:
Defines how to draw the element on a Graphics2D object, with xOffset, yOffset the top left coordinates. The parent element will take care of possible margins and the exact location of the element. This method should return the dimensions of the element, either by simply returning getRawDimension(g2), or by implementing a more efficient way, by making use of the information gathered while drawing.

Take care to undo any changes made to the `Graphics2D` object, as these might influence the drawing of 
subsequent `Element`s. 

* `getRawDimension(Graphics2D)`:
Returns the dimensions of the element without taking margins into account.

Elements consisting of other elements, should call `setParentElement(this)` on each child element, to ensure information is passed on.

[ModuleViewer]: <https://github.com/thpar/ModuleViewer>
