package main;

/**
 * Created by bleeben on 9/14/2015.
 */
public class Position {
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position up(){
        return new Position(row-1,column);
    }

    public Position down(){
        return new Position(row+1,column);
    }

    public Position left(){
        return new Position(row,column-1);
    }

    public Position right(){
        return new Position(row,column+1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (getRow() != position.getRow()) return false;
        return getColumn() == position.getColumn();

    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getColumn();
        return result;
    }

    @Override
    public String toString() {
        return "{" + row + "," + column +'}';
    }
}
