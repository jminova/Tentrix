package sk.tuke.gamestudio.game.tentrix.minova.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.tentrix.minova.components.*;
import sk.tuke.gamestudio.entity.*;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private final Field field;
    private static final Pattern Input = Pattern.compile("([1-3])([1-9])([A-I])");
    private static final String GAME_NAME = "tentrix";
    /*private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();
    private RatingService ratingService = new RatingServiceJDBC();*/

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    public ConsoleUI(Field field){
        this.field = field;
    }

    public void play() throws CommentException, RatingException {
        System.out.println("Hello! Let's play Tentrix! Before you begin let me give you some introduction:");
        System.out.println("Tentrix is a game similar to Tetris, meaning you have to organize available tiles so they form a full row or column.");
        System.out.println("You'll get three available tiles you can use, when you use all three you'll get another three.");
        System.out.println("Remember, if you won't be able to add another tile, game's over and you lose.");
        System.out.println("If you want to add a tile into the field, type the number associated with the tile and coordinates.");
        System.out.println("Info: the coordinates you'll type are coordinates of the leftmost position of the tile(except the tiles in this shape:");
        System.out.println("  L         L");
        System.out.println("L L  or     L");
        System.out.println("        L L L");
        System.out.println("in those two cases it is the rightmost position) so be attentive while choosing coordinates.");
        System.out.println("Enjoy! :)");

            do {
                printField();
                handleInput();
            } while (field.getState() == State.PLAYING);
            printField();
            System.out.println("Game over!");
            scoreService.addScore(new Score(GAME_NAME, System.getProperty("user.name"), field.getScore(), new Date()));
            printScores();
            askForComment();
            askForRating();



    }

    private void printField(){
        System.out.print(" ");
        for (int column = 0; column < field.getColumn(); column++) {
            System.out.print("  ");
            System.out.print((char) ('A' + column));

        }
        System.out.println();

        for (int row = 0; row < field.getRow(); row++) {
            System.out.print(row + 1);
            for (int column = 0; column < field.getColumn(); column++) {
                System.out.print("  ");
                printTile(row, column);
            }
            System.out.println();
        }

        printAvailableTiles();


    }
    private void printTile(int row, int column){
        Tile tile = field.getTile(row,column);
        if(tile instanceof NoShape){ System.out.print("-"); }
        else if(tile instanceof L){ System.out.print("L"); }
        else if(tile instanceof Line){ System.out.print("T"); }
        else if(tile instanceof Cube){ System.out.print("C"); }
    }

    private void printAvailableTiles(){
        int size = field.getCountOfAvailableTiles();
        for(int i=0; i<size; i++){
            Tile tile = field.getAvailableTile(i);
            System.out.print(i+1+") ");
            if(tile instanceof L){
                if(tile.getSizeRow() == 2){
                    if(((L)tile).getOrientation() == LOrientation.LEFTDOWN){
                        System.out.println("L");
                        System.out.println("   L L");
                    }
                    if(((L)tile).getOrientation() == LOrientation.LEFTUP){
                        System.out.println("L L");
                        System.out.println("   L");
                    }
                    if(((L)tile).getOrientation() == LOrientation.RIGHTDOWN){
                        System.out.println("  L");
                        System.out.println("   L L");
                    }
                    if(((L)tile).getOrientation() == LOrientation.RIGHTUP){
                        System.out.println("L L");
                        System.out.println("     L");
                    }
                }
                else if(tile.getSizeRow() == 3){
                    if(((L)tile).getOrientation() == LOrientation.LEFTDOWN){
                        System.out.println("L");
                        System.out.println("   L");
                        System.out.println("   L L L");
                    }
                    if(((L)tile).getOrientation() == LOrientation.LEFTUP){
                        System.out.println("L L L");
                        System.out.println("   L");
                        System.out.println("   L");
                    }
                    if(((L)tile).getOrientation() == LOrientation.RIGHTDOWN){
                        System.out.println("    L");
                        System.out.println("       L");
                        System.out.println("   L L L");
                    }
                    if(((L)tile).getOrientation() == LOrientation.RIGHTUP){
                        System.out.println("L L L");
                        System.out.println("       L");
                        System.out.println("       L");
                    }
                }
            }
            else if(tile instanceof Line){
                for(int row=0; row<tile.getSizeRow(); row++){
                    if (row != 0 && ((Line)tile).getOrientation()==LineOrientation.UP) {
                        System.out.print("   ");
                    }
                    for(int column = 0; column<tile.getSizeColumn(); column++){
                        System.out.print("T");
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }
            else if(tile instanceof Cube){
                for(int row=0; row<tile.getSizeRow(); row++){
                    if(row!=0){
                        System.out.print("   ");
                    }
                    for(int column = 0; column<tile.getSizeColumn(); column++){
                        System.out.print("C");
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    private void printScores() {
        List<Score> scores = scoreService.getBestScores(GAME_NAME);

        System.out.println("Top scores:");
        for (Score s : scores) {
            System.out.println(s);
        }
    }
    private void printComments() throws CommentException {
        List<Comment> comments = commentService.getComments(GAME_NAME);

        System.out.println("Other comments:");
        for (Comment c : comments) {
            System.out.println(c);

        }
    }

    private void askForComment() throws CommentException {
        System.out.print("Would you like to add a comment? (Y/N):");
        String answer = new Scanner(System.in).nextLine().trim().toUpperCase();
        if (answer.equals("Y")) {
            System.out.println("Go on then, tell us what do you think :) :");
            String comment = new Scanner(System.in).nextLine().trim();
            commentService.addComment(new Comment(System.getProperty("user.name"),GAME_NAME,comment,new Date()));
            printComments();
        }
    }

    private void askForLatestRating() throws RatingException {
        System.out.print("Before you do that, would you like to see your latest rating? (If you added some already of course) (Y/N):");
        String answer = new Scanner(System.in).nextLine().trim().toUpperCase();
        if (answer.equals("Y")) {
            int rating = ratingService.getRating(GAME_NAME, System.getProperty("user.name"));
            if(rating > 0){ System.out.println("Your rating: " + rating);}
        }
    }
    private void askForRating() throws RatingException {
        System.out.print("Would you like to rate this game? (Y/N):");
        String answer = new Scanner(System.in).nextLine().trim().toUpperCase();
        if (answer.equals("Y")) {
            askForLatestRating();
            System.out.print("Do not hesistate with your rating then :):");
            int rating = new Scanner(System.in).nextInt();
            ratingService.setRating(new Rating(System.getProperty("user.name"),GAME_NAME,rating ,new Date()));
            System.out.println("Average rating of the game now: " + ratingService.getAverageRating(GAME_NAME));
        }
    }

    private void handleInput(){
        while (true) {
            System.out.print("Enter tile and coordinates (e.g. 15B, 31D, X): ");
            String input = new Scanner(System.in).nextLine().trim().toUpperCase();

            if ("X".equals(input))
                System.exit(0);

            Matcher inputMatch = Input.matcher(input);
            if (inputMatch.matches()) {
                try {
                    int row = Integer.parseInt(inputMatch.group(2)) - 1;
                    int column = inputMatch.group(3).charAt(0) - 'A';
                    if (row >= 0 && row < field.getRow() && column >= 0 && column < field.getColumn()) {
                        try{
                            field.addTile(field.getAvailableTile(Integer.parseInt(inputMatch.group(1))-1),row,column);
                            return;
                        } catch(IndexOutOfBoundsException e){
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Bad input, try again!");
                }
            }
        }
    }
}
