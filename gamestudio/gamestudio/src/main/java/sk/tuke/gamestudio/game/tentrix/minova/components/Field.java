package sk.tuke.gamestudio.game.tentrix.minova.components;


import java.util.ArrayList;
import java.util.Random;

public class Field {
    private Tile[][] tiles;
    private final int row;
    private final int column;
    private int freedLines = 0;
    private int usedTiles = 0;

    private State  state = State.PLAYING;
    private ArrayList<Tile> availableTiles = new ArrayList<>();

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
        generate();
    }

    public Tile getTile(int row, int column){ return tiles[row][column];}
    public Tile getAvailableTile(int index){ return availableTiles.get(index);}

    public int getCountOfAvailableTiles(){return availableTiles.size();}
    public int getRow() { return row; }

    public int getColumn() { return column; }

    public State getState(){return state;}

    public void addTile(Tile tile, int row, int column){
        boolean available = false;
        boolean tileAdded = false;
        if(tile == null){
            System.out.println("Tile is missing...");
            return;
        }
        if(tile instanceof L){
            tileAdded = addL((L)tile,row,column);
        }
        if(tile instanceof Line){
            tileAdded = addLine((Line)tile,row,column);
        }
        if(tile instanceof Cube){
            tileAdded = addCube((Cube)tile,row,column);
        }
        if(tileAdded){ availableTiles.remove(tile);}
        if(availableTiles.isEmpty()){
            generateTiles();
        }
         fullLine();

        for(Tile t : availableTiles){
            if(freeSpace(t)) available = true;
        }

        if(!available){
            state = State.FAILED;
        }

    }

    private boolean addSmallL(L lTile, int row, int column){
        int available=0;
        if (lTile.getOrientation() == LOrientation.LEFTDOWN) {
            if(positionAvailable(row,column)) /*tiles[row][column] = lTile*/available++; else{return false;}
            if(positionAvailable(row+1,column)) /*tiles[row + 1][column] = lTile*/available++; else {return false;}
            if(positionAvailable(row+1,column+1)) /*tiles[row + 1][column + 1] = lTile*/available++; else{return false;}
            if(available == 3){
                tiles[row][column] = lTile;
                tiles[row + 1][column] = lTile;
                tiles[row + 1][column + 1] = lTile;
            }
        }
        else if (lTile.getOrientation() == LOrientation.LEFTUP) {
            if(positionAvailable(row,column)) /*tiles[row][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row,column+1)) /*tiles[row][column + 1] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column)) /*tiles[row + 1][column] = lTile*/available++;else{return false;}
            if(available == 3){
                tiles[row][column] = lTile;
                tiles[row][column + 1] = lTile;
                tiles[row + 1][column] = lTile;
            }
        }
        else if (lTile.getOrientation() == LOrientation.RIGHTDOWN) {
            if(positionAvailable(row,column)) /*tiles[row][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column)) /*tiles[row + 1][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column-1)) /*tiles[row + 1][column - 1] = lTile*/available++;else{return false;}
            if(available == 3){
                tiles[row][column] = lTile;
                tiles[row + 1][column] = lTile;
                tiles[row + 1][column - 1] = lTile;
            }
        }
        else if (lTile.getOrientation() == LOrientation.RIGHTUP) {
            if(positionAvailable(row,column)) /*tiles[row][column] = lTile*/available++; else{return false;}
            if(positionAvailable(row,column+1)) /*tiles[row][column + 1] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column+1)) /*tiles[row + 1][column + 1] = lTile*/available++;else{return false;}
            if(available == 3){
                tiles[row][column] = lTile;
                tiles[row][column+1] = lTile;
                tiles[row + 1][column + 1] = lTile;
            }
        }
        return true;
    }

    private boolean addBigL(L lTile, int row, int column){
        int available=0;
        if (lTile.getOrientation() == LOrientation.LEFTDOWN) {
            if(positionAvailable(row,column))/*tiles[row][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column))/*tiles[row + 1][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column))/*tiles[row + 2][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column+1))/*tiles[row + 2][column + 1] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column+2))/*tiles[row + 2][column + 2] = lTile*/available++;else{return false;}
            if(available==5){
                tiles[row][column] = lTile;
                tiles[row + 1][column] = lTile;
                tiles[row + 2][column] = lTile;
                tiles[row + 2][column + 1] = lTile;
                tiles[row + 2][column + 2] = lTile;
            }
        }
        else if (lTile.getOrientation() == LOrientation.LEFTUP) {
            if(positionAvailable(row,column)) /*tiles[row][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column)) /*tiles[row + 1][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column)) /*tiles[row + 2][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row,column+1)) /*tiles[row][column + 1] = lTile*/available++;else{return false;}
            if(positionAvailable(row,column+2)) /*tiles[row][column + 2] = lTile*/available++;else{return false;}
            if(available==5){
                tiles[row][column] = lTile;
                tiles[row + 1][column] = lTile;
                tiles[row + 2][column] = lTile;
                tiles[row][column + 1] = lTile;
                tiles[row][column + 2] = lTile;
            }
        }
        else if (lTile.getOrientation() == LOrientation.RIGHTDOWN) {
            if(positionAvailable(row,column))/*tiles[row][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column))/*tiles[row + 1][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column))/*tiles[row + 2][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column-1))/*tiles[row + 2][column - 1] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column-2))/*tiles[row + 2][column - 2] = lTile*/available++;else{return false;}
            if(available == 5){
                tiles[row][column] = lTile;
                tiles[row + 1][column] = lTile;
                tiles[row + 2][column] = lTile;
                tiles[row + 2][column - 1] = lTile;
                tiles[row + 2][column - 2] = lTile;
            }
        }
        else if (lTile.getOrientation() == LOrientation.RIGHTUP) {
            if(positionAvailable(row,column))/*tiles[row][column] = lTile*/available++;else{return false;}
            if(positionAvailable(row,column+1))/*tiles[row][column + 1] = lTile*/available++;else{return false;}
            if(positionAvailable(row,column+2))/*tiles[row][column + 2] = lTile*/available++;else{return false;}
            if(positionAvailable(row+1,column+2))/*tiles[row + 1][column + 2] = lTile*/available++;else{return false;}
            if(positionAvailable(row+2,column+2))/*tiles[row + 2][column + 2] = lTile*/available++;else{return false;}
            if(available == 5){
                tiles[row][column] = lTile;
                tiles[row][column + 1] = lTile;
                tiles[row][column + 2] = lTile;
                tiles[row + 1][column + 2] = lTile;
                tiles[row + 2][column + 2] = lTile;
            }
        }
        return true;
    }

    private boolean addL(L lTile, int row, int column){
        boolean added = false;
        if(lTile.getSizeRow() == 2) {
            added = addSmallL(lTile,row,column);
            usedTiles = usedTiles + 3;
        }
        else if(lTile.getSizeRow() == 3){
            added = addBigL(lTile,row,column);
            usedTiles = usedTiles+5;

        }
        return added;
    }

    private boolean addLine(Line lineTile, int row, int column){
        int available = 0;
        if(lineTile.getOrientation() == LineOrientation.UP){
            for(int i=row; i<row+lineTile.getSizeRow(); i++){
                if(positionAvailable(i,column))  available++;
            }
            if(available == lineTile.getSizeRow()) {
                for(int i=row; i<row+lineTile.getSizeRow(); i++) {
                    tiles[i][column] = lineTile;

                }
                usedTiles = usedTiles+lineTile.getSizeRow();
                return true;
            }
        }
        else if(lineTile.getOrientation() == LineOrientation.DOWN){
            for(int i = column; i<column+lineTile.getSizeColumn(); i++){
                if(positionAvailable(row,i)) available++;
            }
            if(available == lineTile.getSizeColumn()) {
                for (int i = column; i < column + lineTile.getSizeColumn(); i++) {
                    tiles[row][i] = lineTile;

                }
                usedTiles = usedTiles+lineTile.getSizeColumn();
                return true;
            }
        }
        return false;
    }

    private boolean addCube(Cube cubeTile, int row, int column){
        int available = 0;
        for(int i=row; i<row+cubeTile.getSizeRow(); i++){
            for(int j=column; j<column+cubeTile.getSizeColumn(); j++){
                if(positionAvailable(i,j)) available++;
            }
        }

        if(available == (cubeTile.getSizeColumn()*cubeTile.getSizeRow())){
            for(int i=row; i<row+cubeTile.getSizeRow(); i++){
                for(int j=column; j<column+cubeTile.getSizeColumn(); j++){
                    tiles[i][j] = cubeTile;

                }
            }
            usedTiles = usedTiles+(cubeTile.getSizeRow()*cubeTile.getSizeColumn());
            return true;
        }
        return false;

    }

    private boolean positionAvailable(int row, int column) {

        return row >= 0 && row < this.row && column >= 0 && column < this.column && tiles[row][column] instanceof NoShape;
    }

    public void fullLine(){

        int fullRow[] = new int[this.row];
        int fullColumn[] = new int[this.column];
        int numberFullRow = 0;
        int numberFullColumn = 0;

        for(int rowN = 0; rowN < this.row; rowN++){
            boolean rowFull = true;
            for(int columnN = 0; columnN < this.column; columnN++ ){
                if(tiles[rowN][columnN] instanceof NoShape){
                    rowFull = false;
                    break;
                }
            }
            if(rowFull){
                fullRow[numberFullRow] = rowN;
                numberFullRow++;
                freedLines++;
            }
        }
        for(int columnN = 0; columnN < this.column; columnN++){
            boolean columnFull = true;
            for(int rowN = 0; rowN < this.row; rowN++ ){
                if(tiles[rowN][columnN] instanceof NoShape){
                    columnFull = false;
                    break;
                }
            }
            if(columnFull){
                fullColumn[numberFullColumn] = columnN;
                numberFullColumn++;
                freedLines++;
            }
        }
       if(numberFullRow>0){
            for(int i =0; i<numberFullRow;i++) {
                for (int j = 0; j < this.column; j++) {
                    tiles[fullRow[i]][j] = new NoShape();
                }
            }
        }
        if(numberFullColumn>0){
            for(int i =0; i<numberFullColumn;i++) {
                for (int j = 0; j < this.row; j++) {
                    tiles[j][fullColumn[i]] = new NoShape();
                }
            }
        }
    }

    private boolean freeLineSpace(Line tile){

        int available=0;
        int isAvailable;
        if(tile.getOrientation() == LineOrientation.UP){
            for(int i = 0; i<=this.row-tile.getSizeRow(); i++){
                for(int j = 0; j< this.column; j++){
                    isAvailable = 0;
                    for(int k=i; k<i+tile.getSizeRow(); k++){
                        if(!positionAvailable(k,j))  break;

                        isAvailable++;
                    }
                    if(isAvailable == tile.getSizeRow()){
                        available++;
                    }
                }
            }
            if(available == 0){
                return false;
            }
        }
        else if(tile.getOrientation() == LineOrientation.DOWN){
            for(int i = 0; i<this.row; i++){
                for(int j = 0; j <= this.column-tile.getSizeColumn(); j++){
                    isAvailable = 0;
                    for(int k=j; k<j+tile.getSizeColumn(); k++){
                        if(!positionAvailable(i,k))  break;
                        isAvailable++;
                    }
                    if(isAvailable == tile.getSizeColumn()){
                        available++;
                    }
                }
            }
            if(available == 0){
                return false;
            }
        }
        return true;
    }
    private boolean freeCubeSpace(Cube tile){
        int available=0;
        int isAvailable;
        for(int i = 0; i<=row-((Cube)tile).getSizeRow();i++) {
            for (int j = 0; j <= column - ((Cube) tile).getSizeColumn(); j++) {
                isAvailable = 0;
                for (int rCube = i; rCube < i + ((Cube) tile).getSizeRow(); rCube++) {
                    for (int cCube = j; cCube < j + ((Cube) tile).getSizeColumn(); cCube++) {
                        if (positionAvailable(rCube, cCube)) isAvailable++;
                    }
                }
                if(isAvailable == (((Cube)tile).getSizeColumn()*((Cube)tile).getSizeRow())){
                    available++;
                }
            }
        }
        if(available == 0){
            return false;
        }
        return true;
    }
    private boolean freeSmallLSpace(L tile){
        int available=0;
        if (tile.getOrientation() == LOrientation.LEFTDOWN) {
            for (int i = 0; i < row - 1; i++) {
                for (int j = 0; j < column - 1; j++) {
                    for(int help=0; help<1;help++) { //iba pomocny cyklus kvoli prikazu break
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i + 1, j)) break;
                        if (!positionAvailable(i + 1, j + 1)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }
        }
        else if (tile.getOrientation() == LOrientation.LEFTUP) {
            for (int i = 0; i < row - 1; i++) {
                for (int j = 0; j < column - 1; j++) {
                    for(int help=0; help<1;help++) {//pomocny cyklus
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i, j + 1)) break;
                        if (!positionAvailable(i + 1, j)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }
        }
        else if (tile.getOrientation() == LOrientation.RIGHTDOWN) {
            for (int i = 0; i < row - 1; i++) {
                for (int j = 1; j < column; j++) {
                    for(int help=0; help<1;help++) {//pomocny cyklus
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i + 1, j)) break;
                        if (!positionAvailable(i + 1, j - 1)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }
        }
        else if (tile.getOrientation() == LOrientation.RIGHTUP) {
            for (int i = 0; i < this.row - 1; i++) {
                for (int j = 0; j < this.column - 1; j++) {
                    for(int help=0; help<1;help++) {//pomocny cyklus
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i, j + 1)) break;
                        if (!positionAvailable(i + 1, j + 1)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }
        }
        return true;
    }
    private boolean freeBigLSpace(L tile){
        int available=0;
        if (tile.getOrientation() == LOrientation.LEFTDOWN) {
            for (int i = 0; i < row - 2; i++) {
                for (int j = 0; j < column - 2; j++) {
                    for(int help = 0; help<1; help++) {
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i + 1, j)) break;
                        if (!positionAvailable(i + 2, j)) break;
                        if (!positionAvailable(i + 2, j + 1)) break;
                        if (!positionAvailable(i + 2, j + 2)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }

        }
        else if (tile.getOrientation() == LOrientation.LEFTUP) {
            for (int i = 0; i < row - 2; i++) {
                for (int j = 0; j < column - 2; j++) {
                    for(int help=0; help<1; help++) {
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i + 1, j)) break;
                        if (!positionAvailable(i + 2, j)) break;
                        if (!positionAvailable(i, j + 1)) break;
                        if (!positionAvailable(i, j + 2)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }

        }
        else if (tile.getOrientation() == LOrientation.RIGHTDOWN) {
            for (int i = 0; i < row - 2; i++) {
                for (int j = 2; j < column; j++) {
                    for(int help=0; help<1; help++) {
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i + 1, j)) break;
                        if (!positionAvailable(i + 2, j)) break;
                        if (!positionAvailable(i + 2, j - 1)) break;
                        if (!positionAvailable(i + 2, j - 2)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }

        }
        else if (tile.getOrientation() == LOrientation.RIGHTUP) {
            for (int i = 0; i < row - 2; i++) {
                for (int j = 0; j < column - 2; j++) {
                    for(int help=0; help<1; help++) {
                        if (!positionAvailable(i, j)) break;
                        if (!positionAvailable(i, j + 1)) break;
                        if (!positionAvailable(i, j + 2)) break;
                        if (!positionAvailable(i + 1, j + 2)) break;
                        if (!positionAvailable(i + 2, j + 2)) break;
                        available++;
                    }
                }
            }
            if (available == 0) {
                return false;
            }

        }
        return true;
    }

    public boolean freeSpace(Tile tile){

        boolean avaiable = false;
       if(tile instanceof L) {
           if (tile.getSizeRow() == 2) {
               avaiable = freeSmallLSpace((L)tile);
           }
           else if (tile.getSizeRow() == 3) {
               avaiable = freeBigLSpace((L)tile);
           }
        }
       else if(tile instanceof Line){
            avaiable = freeLineSpace((Line)tile);
        }
       else if(tile instanceof Cube){
           avaiable = freeCubeSpace((Cube)tile);
       }
        return avaiable;

    }

    private void generate(){
        tiles = new Tile[row][column];
        for(int i = 0; i<row; i++){
            for (int j = 0; j<column; j++){
                tiles[i][j] = new NoShape();
            }
        }
        generateTiles();
    }

    private void generateTiles(){
        Random randomGenerator = new Random();
        int TilesType;
        for(int i=0; i<3; i++) {
            TilesType = randomGenerator.nextInt(19) + 1;
            if (TilesType == 1) this.availableTiles.add(new L(2, 2, LOrientation.LEFTDOWN));
            if (TilesType == 2) this.availableTiles.add(new L(2, 2, LOrientation.LEFTUP));
            if (TilesType == 3) this.availableTiles.add(new L(2, 2, LOrientation.RIGHTDOWN));
            if (TilesType == 4) this.availableTiles.add(new L(2, 2, LOrientation.RIGHTUP));
            if (TilesType == 5) this.availableTiles.add(new L(3, 3, LOrientation.LEFTDOWN));
            if (TilesType == 6) this.availableTiles.add(new L(3, 3, LOrientation.LEFTUP));
            if (TilesType == 7) this.availableTiles.add(new L(3, 3, LOrientation.RIGHTDOWN));
            if (TilesType == 8) this.availableTiles.add(new L(3, 3, LOrientation.RIGHTUP));
            if (TilesType == 9) this.availableTiles.add(new Line(1, 2, LineOrientation.DOWN));
            if (TilesType == 10) this.availableTiles.add(new Line(1, 3, LineOrientation.DOWN));
            if (TilesType == 11) this.availableTiles.add(new Line(1, 4, LineOrientation.DOWN));
            if (TilesType == 12) this.availableTiles.add(new Line(1, 5, LineOrientation.DOWN));
            if (TilesType == 13) this.availableTiles.add(new Line(2, 1, LineOrientation.UP));
            if (TilesType == 14) this.availableTiles.add(new Line(3, 1, LineOrientation.UP));
            if (TilesType == 15) this.availableTiles.add(new Line(4, 1, LineOrientation.UP));
            if (TilesType == 16) this.availableTiles.add(new Line(5, 1, LineOrientation.UP));
            if (TilesType == 17) this.availableTiles.add(new Cube(1, 1));
            if (TilesType == 18) this.availableTiles.add(new Cube(2, 2));
            if (TilesType == 19) this.availableTiles.add(new Cube(3, 3));
        }

    }

    public int getScore(){
        return freedLines*10+usedTiles;
    }


}


