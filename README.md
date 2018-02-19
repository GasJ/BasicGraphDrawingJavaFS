# Build a simple GUI in JavaFX

Two Views and View Navigation

Clicking on the background of the main panel creates a new vertex, drawn as a blue circle with an integer label
Clicking down on an existing vertex selects that vertex (shown by drawing the vertex in orange); dragging then moves the vertex. Releasing the mouse returns the vertex to blue
If the user Shift-Clicks a vertex, the vertex gets a thicker black border, and then when the user drags, an edge is drawn to the mouse location. If the user releases the mouse on another vertex, the edge is added to the graph (if the user releases on the background, the edge is discarded).


Clicking and dragging on the main view’s background now moves the main view to a new location in the logical workspace.
Clicking and dragging on the green viewfinder in the mini view also moves the main view to a new location (and updates the viewfinder rectangle)

 The mini view shows all vertices and edges (including the selected vertex) and the temporary edge, but does not need to show vertex labels.
 
 
 
 How to open the program:
 open NetBeans, choose File button on the top of the whole screen.
 Choose open project, and then print the path you download or open the zip.
 Click the projects in the Projects panel at the left part and choose the Run button in the menu to run it.



