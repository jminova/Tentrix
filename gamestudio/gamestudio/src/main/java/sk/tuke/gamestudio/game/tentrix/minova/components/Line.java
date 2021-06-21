package sk.tuke.gamestudio.game.tentrix.minova.components;

public class Line extends Tile {
    private LineOrientation orientation;

    public Line(int sizeRow, int sizeColumn, LineOrientation orientation) {
        super(sizeRow, sizeColumn);
        this.orientation = orientation;
    }

    public LineOrientation getOrientation() {
        return orientation;
    }

    void setOrientation(LineOrientation lineOrientation){
        orientation = lineOrientation;
    }
}
