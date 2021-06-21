package sk.tuke.gamestudio.game.tentrix.minova;

import sk.tuke.gamestudio.game.tentrix.minova.components.*;
import sk.tuke.gamestudio.game.tentrix.minova.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.RatingException;

import java.util.Scanner;


public class Tentrix {
    public static void main(String[] args) throws CommentException, RatingException {
        boolean playing =true;
        while(playing) {
            Field field = new Field(9, 7);
            ConsoleUI consoleUI = new ConsoleUI(field);
            consoleUI.play();
            System.out.print("Try again? (Y/N):");
            String answer = new Scanner(System.in).nextLine().trim().toUpperCase();
            if (answer.equals("N")) {playing = false;} else{playing=true;}
        }
    }

}
