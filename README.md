# Conveys Game of Life Simulator
## Introduction
I am a big fan of Robert C. Martins books and blog. Also, I love to do Code-Katas and Challenges. 
When I then read about the challange to implement Conway's Game of Life based on TDD in a Code-Challenge format in one of
Roberts Book ( I guess it was "Clean Coder", not so sure any more). I decided to implement the game myself. So here is the ***Conwey's Game of Life Simulater***.
###About the Game
Conveys Game of Life *https://en.wikipedia.org/wiki/Conway's_Game_of_Life* is a simple Simulation Game. 
The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, 
each of which is in one of two possible states, live or dead (or populated and unpopulated, respectively). In my this implementation 
I'm using a bounded board. Maybe the infinite board will be implemented in the future.

Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally 
adjacent. At each step in time, the following transitions occur:

* Any live cell with fewer than two live neighbours dies, as if by underpopulation.
* Any live cell with two or three live neighbours lives on to the next generation.
* Any live cell with more than three live neighbours dies, as if by overpopulation.
* Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

These rules, which compare the behavior of the automaton to real life, can be condensed into the following:

* Any live cell with two or three live neighbours survives.
* Any dead cell with three live neighbours becomes a live cell.
* All other live cells die in the next generation. Similarly, all other dead cells stay dead.


### Implementation
I have tried to use TDD, but my test coverage is poor for this application, as i tried to focus on architectural concepts like MVC, MVVM and Event and Property Listeners.
During research I found an excellent resource about this particular topic. Byte Smith has also implemented this game and is
explaning his process in detail. If you are interested in this topic
I would suggest you visit his channel on Youtube.


## About the UI
My implementation isn't feature rich. You can only to the following things:
* Resize the board
* Change the speed of simulation.
* Draw, Reset and Erase

**It's important to know that you can only Draw the board when it is in the Editing State.**
To enter the Editing State you need
to reset or resize the board.

# Run the Application
###Build
If you have cloned or downloaded the code there should be no need to rebuild. If you have adjusted the code please use
* *mvn clean javafx:jlink* to build.
###Run
Go to the target folder and navigate over the image to the bin folder
* */target/images/bin/*
* use the following command to start the app
  * *./java -m de.creode/de.creode.App*

you need to specify the module and the main app. So if your are changing the structure or the naming please adjust the comment.


### Conclusion

This App is not big but already demonstrates the benefit of following patterns like MVVM, Using Events and Data Binding, SRP etc.
If you achieve decoupling you can add features more easily and robust.
The implementation of the concepts might not be perfect and can be optimized. 
I hope you find the code helpful and enjoyed playing with the simulation.
If there are any problems or you need help feel free to contact me







