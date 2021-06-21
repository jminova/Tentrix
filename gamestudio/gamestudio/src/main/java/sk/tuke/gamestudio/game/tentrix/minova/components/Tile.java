package sk.tuke.gamestudio.game.tentrix.minova.components;

public abstract class Tile {
    private final int sizeRow;
    private final int sizeColumn;

    public Tile(int sizeRow, int sizeColumn) {
        this.sizeRow = sizeRow;
        this.sizeColumn = sizeColumn;
    }

    public int getSizeRow() {
        return sizeRow;
    }

    public int getSizeColumn() {
        return sizeColumn;
    }

}
