# Scoreboard

> Keep your follow-up of your favourite football matches

With this project, you can store and fetch any number of matches.

# Project architecture

* Parent
  * Application
  * Library

# How to use the library

**Scoreboard** comes with the library that can be extracted to be used in any other project.

The API that Scoreboard class defines is:

  1. startGame(String, String)
    * **Starts a game**. It needs the home team and away team name.
  2. updateGame(int, int, int)
    * **Updates a game**. It needs the id of the match and the scores.
  3. finishGame(int)
    * **Finish a game**. It needs the id of the match.
  4. getSummaryByTotalScore()
    * **Lists the matches in order**
