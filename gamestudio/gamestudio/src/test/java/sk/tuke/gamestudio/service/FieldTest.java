package sk.tuke.gamestudio.service;

import org.junit.Test;
import sk.tuke.gamestudio.game.tentrix.minova.components.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    private Random randomGenerator = new Random();
    private Field field;
    private int row;
    private int column;

    public FieldTest(){
        row = randomGenerator.nextInt(10)+8;
        column = randomGenerator.nextInt(10)+4;
        field = new Field(row,column);
    }

    @Test
    public void NewFieldShouldHaveNoShapeTile(){
        int Counter = 0;
        for (int row = 0; row < this.row; row++) {
            for (int column = 0; column < this.column; column++) {
                if (field.getTile(row, column) instanceof NoShape) {
                    Counter++;
                }
            }
        }
        assertEquals(this.row*this.column,Counter);
    }

    @Test
    public void addTileShouldAddTileIfPossible(){
        int rowN = randomGenerator.nextInt(row);
        int columnN = randomGenerator.nextInt(column);
        field.addTile(new Cube(1,1),rowN,columnN);
        assertTrue(field.getTile(rowN,columnN) instanceof Cube);

        rowN = randomGenerator.nextInt(row-1);
        columnN = randomGenerator.nextInt(column-1);
        field.addTile(new L(2,2, LOrientation.LEFTDOWN),rowN,columnN);
        assertTrue(field.getTile(rowN,columnN) instanceof L);
    }

    @Test
    public void addingTileOnAnotherOneShouldNotBePossible(){
        int rowN = randomGenerator.nextInt(row);
        int columnN = randomGenerator.nextInt(column);
        field.addTile(new Cube(1,1),rowN,columnN);
        field.addTile(new L(2,2,LOrientation.LEFTDOWN),rowN,columnN);
        assertTrue(field.getTile(rowN,columnN) instanceof Cube);
    }

    @Test
    public void fullLineShouldRemoveTilesAndAddNoShape(){
        int Tile=0;
        for(int rowN = 0; rowN< 1;rowN++){
            for(int columnN=0; columnN<column;columnN++){
                field.addTile(new Cube(1,1),rowN,columnN);
            }
        }

        for(int rowN = 0; rowN <1; rowN++){
            for(int columnN=0; columnN<column; columnN++){
                if(field.getTile(rowN,columnN) instanceof Cube){
                    Tile++;
                }
            }
        }
        assertEquals(0,Tile);

        Tile=0;

        for(int rowN = 0; rowN< row;rowN++){
            for(int columnN=0; columnN<1;columnN++){
                field.addTile(new Cube(1,1),rowN,columnN);
            }
        }

        for(int rowN = 0; rowN <row; rowN++){
            for(int columnN=0; columnN<1; columnN++){
                if(field.getTile(rowN,columnN) instanceof Cube){
                    Tile++;
                }
            }
        }
        assertEquals(0,Tile);

    }

    @Test
    public void freeSpaceShouldDecideIfTileHaveSpace(){
        //herne pole je prazdne, vlozena dlazdica by mala mat miesto
        boolean Assert = false;
        Assert = field.freeSpace(new Cube(2,2));
        assertTrue(Assert == true);

        Assert = true;

        //cyklom je zaplnene pole tak aby sa tam nevosla dana dlazdica typu kocky velkosti 2x2, funkcia vrati false
        //v tomto testovanom pripade netestujeme stav hry, ten by sa nemal menit
        for(int i=0 ;i<field.getRow()-1; i++){
            for(int j=0; j<field.getColumn()-1;j++){
                field.addTile(new Cube(1,1),i,j);
            }
        }
        Assert = field.freeSpace(new Cube(2,2));
        assertTrue(Assert == false);

    }

    @Test
    public void NewFieldShouldAlsoGenerateThreeNewTiles(){
            assertEquals(3,field.getCountOfAvailableTiles());

    }

    @Test
    public void AfterAddingAllAvailableTilesNewTilesShouldBeGeneratedAgain(){
        field.addTile(field.getAvailableTile(0),0,1);
        assertEquals(2,field.getCountOfAvailableTiles());
        field.addTile(field.getAvailableTile(0),3,2);
        assertEquals(1,field.getCountOfAvailableTiles());
        field.addTile(field.getAvailableTile(0),6,4);
        assertEquals(3,field.getCountOfAvailableTiles());


    }

    @Test
    public void GameStateShouldBePlayingIfThereIsSpaceForTiles(){
        //pole je prazdne, dlazdice by mali mat miesto, hra by teda mala pokracovat dalej a nemala by byt prehrata
        field.addTile(field.getAvailableTile(0),0,1);
        field.addTile(field.getAvailableTile(0),4,2);
        assertTrue(field.getState() == State.PLAYING);


    }
}


