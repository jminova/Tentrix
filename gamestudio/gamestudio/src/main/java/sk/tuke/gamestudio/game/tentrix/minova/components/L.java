package sk.tuke.gamestudio.game.tentrix.minova.components;

public class L extends Tile {
    private LOrientation orientation;

    public L(int sizeRow, int sizeColumn, LOrientation orientation ) {
        super(sizeRow, sizeColumn);
        this.orientation = orientation;
    }

    public LOrientation getOrientation() {
        return orientation;
    }


    void setOrientation(LOrientation lOrientation){
        this.orientation = lOrientation;
    }
}
