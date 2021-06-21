package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.tentrix.minova.components.*;
import sk.tuke.gamestudio.service.*;


import java.util.Date;


//http://localhost:8080/tenrix-minova
@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/tentrix-minova")
public class TentrixController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserController userController;

    private Field field;

    private boolean state;

    @RequestMapping
    public String tentrix(@RequestParam(value = "index", required = false) String index,
                        @RequestParam(value = "row", required = false) String row,
                        @RequestParam(value = "column", required = false) String column,
                        Model model) throws CommentException, RatingException {


        if(field == null){
            createField();

        }
        try{
            if(field.getState() == State.PLAYING){
                field.addTile(field.getAvailableTile(Integer.parseInt(index)),Integer.parseInt(row),Integer.parseInt(column));
                if (userController.isLogged() && field.getState() == State.FAILED){
                    scoreService.addScore(new Score("tentrix",userController.getLoggedUser(),field.getScore(),new Date()));
                }
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        prepareModel(model);
        return "tentrix"; //same name as the template

    }
    @RequestMapping("/new")
    public String newGame(Model model) throws CommentException, RatingException {
        createField();
        prepareModel(model);
        return "tentrix";
    }

    public boolean getGameState() {
        switch(field.getState()){
            case PLAYING:
                state=true;
                break;
            case FAILED:
                state=false;
                break;
        }
        return state;
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div \n>");

        sb.append("<table class='field'>\n");
        for(int row = 0; row<field.getRow(); row++){
            sb.append("<tr>\n");
            for(int column = 0; column < field.getColumn(); column++){
                Tile tile = field.getTile(row,column);
                sb.append("<td ondragover='onDragOver(event);' ondrop='onDrop(event,"+String.format("%s",row)+","+String.format("%s",column)+");'>\n");
                sb.append("<img src='/images/tentrix/"+getImageName(tile)+".png'>");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        sb.append("</div>\n");
        sb.append("<br>\n");
        int size = field.getCountOfAvailableTiles();
        sb.append("<div class='rows'>\n");
        for(int i=0; i<size; i++){
            Tile tile = field.getAvailableTile(i);
            if(tile instanceof Cube){
                getHtmlCube(sb,(Cube) tile,i);
            }else if(tile instanceof Line){
                getHtmlLine(sb,(Line) tile,i);
            }else if(tile instanceof L){
                getHtmlL(sb,(L) tile,i);
            }
        }
        sb.append("</div>\n");

        return sb.toString();
    }


    private void getHtmlL(StringBuilder sb, L tile,int index){
        sb.append("<div class = columnTile draggable='true' ondragstart='onDragStart(event,"+String.format("%s",index)+ ");' id='drag'>\n");
        if(tile.getSizeRow() == 2){
            switch(tile.getOrientation()){
                case LEFTDOWN:
                    sb.append("<img src='/images/tentrix/orangeld.png'>");
                    break;
                case LEFTUP:
                    sb.append("<img src='/images/tentrix/orangelu.png'>");
                    break;
                case RIGHTDOWN:
                    sb.append("<img src='/images/tentrix/orangerd.png'>");
                    break;
                case RIGHTUP:
                    sb.append("<img src='/images/tentrix/orangeru.png'>");
                    break;
            }
        }else if(tile.getSizeRow() == 3){
            switch(tile.getOrientation()){
                case LEFTDOWN:
                    sb.append("<img src='/images/tentrix/greenld.png'>");
                    break;
                case LEFTUP:
                    sb.append("<img src='/images/tentrix/greenlu.png'>");
                    break;
                case RIGHTDOWN:
                    sb.append("<img src='/images/tentrix/greenrd.png'>");
                    break;
                case RIGHTUP:
                    sb.append("<img src='/images/tentrix/greenru.png'>");
                    break;
            }
        }
        sb.append("</div>\n");
        sb.append("<br>\n");

    }
    private void getHtmlLine(StringBuilder sb, Line tile,int index){
        sb.append("<div class = columnTile draggable='true' ondragstart='onDragStart(event,"+String.format("%s",index)+ ");' id='drag'>\n");
        if(tile.getOrientation() == LineOrientation.UP){
            switch(tile.getSizeRow()){
                case 2:
                    sb.append("<img src='/images/tentrix/2redup.png'>");
                    break;
                case 3:
                    sb.append("<img src='/images/tentrix/3yellowup.png'>");
                    break;
                case 4:
                    sb.append("<img src='/images/tentrix/4purpleup.png'>");
                    break;
                case 5:
                    sb.append("<img src='/images/tentrix/5purpleup.png'>");
            }
        }else if(tile.getOrientation() == LineOrientation.DOWN){
            switch(tile.getSizeColumn()){
                case 2:
                    sb.append("<img src='/images/tentrix/2reddown.png'>");
                    break;
                case 3:
                    sb.append("<img src='/images/tentrix/3yellowdown.png'>");
                    break;
                case 4:
                    sb.append("<img src='/images/tentrix/4purpledown.png'>");
                    break;
                case 5:
                    sb.append("<img src='/images/tentrix/5purpledown.png'>");
            }
        }
        sb.append("</div>\n");
        sb.append("<br>\n");
    }
    private void getHtmlCube(StringBuilder sb,Cube tile,int index){
        sb.append("<div class = columnTile draggable='true' ondragstart='onDragStart(event,"+String.format("%s",index)+ ");' id='drag'>\n");
        switch(tile.getSizeRow()){
            case 1:
                sb.append("<img src='/images/tentrix/blue.png'>");
                break;
            case 2:
                sb.append("<img src='/images/tentrix/2blue.png'>");
                break;
            case 3:
                sb.append("<img src='/images/tentrix/3blue.png'>");
                break;
        }
        sb.append("</div>\n");
        sb.append("<br>\n");
    }

    private String getImageName(Tile tile) {
        String name="";
        if(tile instanceof NoShape){name = "pozadie";}
        else if(tile instanceof Cube){name = "blue";}
        else if(tile instanceof Line){
            if(((Line)tile).getOrientation() == LineOrientation.UP){
                switch (tile.getSizeRow()){
                    case 2:
                        name="red";
                        break;
                    case 3:
                        name="yellow";
                        break;
                    case 4:
                        name="purple";
                        break;
                    case 5:
                        name="purple";
                        break;
                }
            }else{
                switch (tile.getSizeColumn()){
                    case 2:
                        name="red";
                        break;
                    case 3:
                        name="yellow";
                        break;
                    case 4:
                        name="purple";
                        break;
                    case 5:
                        name="purple";
                        break;
                }
            }
        }
        else if(tile instanceof L){
            switch (tile.getSizeRow()){
                case 2:
                    name="orange";
                    break;
                case 3:
                    name="green";
                    break;
            }
        }
         return name;
    }

    private void prepareModel(Model model) throws CommentException, RatingException {

        model.addAttribute("scores", scoreService.getBestScores("tentrix"));
        model.addAttribute("comments", commentService.getComments("tentrix"));
        model.addAttribute("averageRating", ratingService.getAverageRating("tentrix"));
        if(userController.isLogged()){
            model.addAttribute("playerRating", ratingService.getRating("tentrix",userController.getLoggedUser()));
        }

    }

    private void createField() {
        field = new Field(9, 7);
    }

    @RequestMapping("/addCom")
    public String addComment(String player,String comment,Model model) throws CommentException, RatingException {

        commentService.addComment(new Comment(player,"tentrix",comment,new Date()));
        prepareModel(model);

        return "tentrix";
    }

    @RequestMapping("/addRat")
    public String addRating(String player,String rating,Model model) throws RatingException, CommentException {
        ratingService.setRating(new Rating(player,"tentrix",Integer.parseInt(rating),new Date()));
        prepareModel(model);

        return "tentrix";
    }



}
