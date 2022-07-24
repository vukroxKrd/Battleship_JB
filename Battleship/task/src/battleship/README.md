# BattleShip
Developed by Viacheslav Braun (vukroxKrd).
vukrox@gmail.com

#Rules of the game:
https://en.wikipedia.org/wiki/Battleship_(game)

#Description
Battleship (also called Battleships or Sea Battle) is a two-player strategy game whose history traces back to the First World War. It started off as a pencil and paper game, until Milton Bradley coined the rules and published the game. Fun fact: it was one of the first games to be produced as a computer game in 1979! In this project, we will recreate this timeless classic.

There are different variations of the Battleship game, but we will stick to the original rules written by Milton Bradley. You have a 10×10 game field and five ships to arrange on that field. The ships can be placed horizontally or vertically but not diagonally across the grid spaces; the ships should not cross or touch each other. The goal is to sink all the ships of the opponent before your opponent does this to you.

Positioning the ships is exactly where we are going to start! The goal of this first stage is to place all the ships on the game field according to the rules.

#Implementation history

**Stage 1:**

In this stage, you should arrange your ships on the game field. Before you start, let's discuss the conventions of the game:

- On a 10x10 field, the first row should contain numbers from 1 to 10 indicating the column, and the first column should contain letters from A to J indicating the row.
- The symbol ~ denotes the fog of war: the unknown area on the opponent's field and the yet untouched area on your field.
- The symbol O denotes a cell with your ship, X denotes that the ship was hit, and M signifies a miss.
- You have 5 ships: Aircraft Carrier is 5 cells, Battleship is 4 cells, Submarine is 3 cells, Cruiser is also 3 cells, and Destroyer is 2 cells. Start placing your ships with the largest one.
- To place a ship, enter two coordinates: the beginning and the end of the ship.
- If an error occurs in the input coordinates, your program should report it. The message should contain the word Error.

**Stage 2:**

- Players start producing shots one after another.
- Fog of war is added so a player cannot see the ships of the opponent.
- The game ends when all ships of a particular player are hit.

#To start playing

Clone project to Intellij and click "Run" button. 
