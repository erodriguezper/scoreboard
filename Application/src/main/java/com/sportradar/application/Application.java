/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.sportradar.application;

import com.sportradar.scoreboard.Scoreboard;
import com.sportradar.scoreboard.entity.Match;
import com.sportradar.scoreboard.exceptions.NotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author enrodpre
 */
@SpringBootApplication(scanBasePackages = "com.sportradar.scoreboard")
public class Application {

    private final static BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
    private static Scoreboard scoreboard;

    public static void main(String[] args) {

        ConfigurableApplicationContext c = SpringApplication.run(Application.class, args);
        scoreboard = c.getBean(Scoreboard.class);

        int chosenOption;

        do {
            printMenu();

            chosenOption = chooseFromMenu();

            print();

            switch (chosenOption) {
                case 1 ->
                    startGameDialog();
                case 2 ->
                    updateGameDialog();
                case 3 ->
                    finishGameDialog();
                case 4 ->
                    listGamesInOrder();
                default -> { }

            }
        } while (chosenOption != 6);
    }

    private static void printMenu() {
        print("------------------------------------------------------");
        print("1. Start game");
        print("2. Update game");
        print("3. Finish game");
        print("4. List games in total score and recently added score");
        print("5. Print this dialog");
        print("6. Exit");
        print("------------------------------------------------------");
    }

    private static int chooseFromMenu() {
        print("Choose number from menu");
        return getInteger();
    }

    private static void startGameDialog() {
        print("Introduce the home team name");
        String homeTeamName = getString();
        print();
        print("Introduce the away team name");
        String awayTeamName = getString();

        scoreboard.startGame(homeTeamName, awayTeamName);
    }

    private static void updateGameDialog() {
        int id = askForGameId();

        print();
        print("Enter the home team score");
        int homeTeamScore = getInteger();
        print();
        print("Enter the away team score");
        int awayTeamScore = getInteger();

        try {
            scoreboard.updateGame(id, homeTeamScore, awayTeamScore);
        } catch (NotFoundException nfe) {
            print("Id not found");
        }
    }

    private static void finishGameDialog() {
        int id = askForGameId();

        try {
            scoreboard.finishGame(id);
        } catch (NotFoundException nfe) {
            print("Id not found");
        }
    }

    private static void printMatch(Match m) {
        System.out.println(
                String.format("%d. %s", m.getId(), m.toString())
        );
    }

    private static void listGamesInOrder() {
        scoreboard.getSummaryByTotalScore()
                .stream()
                .forEach(m -> printMatch(m));
    }

    private static int askForGameId() {
        print();
        print("Introduce the game id");

        return getInteger();
    }

    private static String getString() {
        try {
            return inReader.readLine();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static int getInteger() {
        String input = getString();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            print("Not a number, try again");
            return askForGameId();
        }
    }

    private static void print() {
        System.out.println("");
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}
