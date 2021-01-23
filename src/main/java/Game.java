import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private final int height ;
    private final int width;

    private int[][] field;
    private int score;
    private boolean modified;
    private boolean gameOver;

    private GameStringBuilder gameStringBuilder;

    public Game() {

        height = 4;
        width = 4;

        field = new int[height][width];
        initField(field);

        gameStringBuilder = new GameStringBuilder();

    }

    @Override
    public String toString() {

        return gameStringBuilder.buildGameString(this);

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] getField() {
        return field;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void initField(int[][] field){

        ArrayList<Point2D> pointsArray = new ArrayList<>();

        for (int currentColumn = 0; currentColumn < width; currentColumn++){
            for (int currentRow = 0; currentRow < height; currentRow++){
                pointsArray.add(new Point2D.Double(currentRow, currentColumn));
            }
        }

        int firstPointIndex = (int) (Math.random() * (pointsArray.size() - 1));;

        Point2D firstPoint = pointsArray.get(firstPointIndex);

        field[(int)firstPoint.getX()][(int)firstPoint.getY()] = 2;

        pointsArray.remove(firstPointIndex);


        int secondPointIndex = (int) (Math.random() * (pointsArray.size() - 1));;

        Point2D secondPoint = pointsArray.get(secondPointIndex);

        field[(int)secondPoint.getX()][(int)secondPoint.getY()] = 2;

    }

    public void moveUp(){

        for (int currentColumn = 0; currentColumn < width; currentColumn++){

            collapseColumn(currentColumn, ColumnCollapseDirection.UP);

        }

        generateNewValue();

        checkGameIsOver();

    }

    public void moveDown(){

        for (int currentColumn = 0; currentColumn < width; currentColumn++){

            collapseColumn(currentColumn, ColumnCollapseDirection.DOWN);

        }

        generateNewValue();

        checkGameIsOver();

    }

    public void moveLeft(){

        for (int currentRow = 0; currentRow < height; currentRow++){

            collapseRow(currentRow, RowCollapseDirection.LEFT);

        }

        generateNewValue();

        checkGameIsOver();

    }

    public void moveRight(){

        for (int currentRow = 0; currentRow < height; currentRow++){

            collapseRow(currentRow, RowCollapseDirection.RIGHT);

        }

        generateNewValue();

        checkGameIsOver();
    }

    private void collapseColumn(int column, ColumnCollapseDirection direction) {

        ArrayList<Integer> columnArray = new ArrayList<Integer>(height);

        for (int i = 0; i < height; i++) {
            columnArray.add(field[i][column]);
        }

        ArrayList<Integer> copyColumnArray = new ArrayList<>(columnArray);

        if (direction == ColumnCollapseDirection.DOWN) {
            Collections.reverse(columnArray);
        }

        collapseArray(columnArray);

        if (direction == ColumnCollapseDirection.DOWN) {
            Collections.reverse(columnArray);
        }

        for (int i = 0; i < columnArray.size(); i++) {

            if (field[i][column] != columnArray.get(i)) {

               field[i][column] = columnArray.get(i);
            }
        }

       if (!columnArray.equals(copyColumnArray)){
            modified = true;
        }

    }

    private void collapseRow(int row, RowCollapseDirection direction){

        ArrayList<Integer> rowArray = new ArrayList<Integer>(width);

        for (int i = 0; i < width; i++) {
            rowArray.add(field[row][i]);
        }

        ArrayList<Integer> copyRowArray = new ArrayList<>(rowArray);

        if (direction == RowCollapseDirection.RIGHT) {
            Collections.reverse(rowArray);
        }

        collapseArray(rowArray);

        if (direction == RowCollapseDirection.RIGHT) {
            Collections.reverse(rowArray);
        }

        for (int i = 0; i < rowArray.size(); i++) {

            if (field[row][i] != rowArray.get(i)) {

                modified = true;

                field[row][i] = rowArray.get(i);

            }
        }

        if (!rowArray.equals(copyRowArray)){
            modified = true;
        }

    }

    private void collapseArray(ArrayList<Integer> array) {

        while (array.remove(Integer.valueOf(0))) ;

        if (array.size() > 0) {

            for (int i = 0; i < array.size() - 1; i++) {

                int currentValue = array.get(i);
                int nextValue = array.get(i + 1);

                if (currentValue == nextValue) {

                    array.set(i, currentValue + nextValue);
                    array.set(i + 1, 0);

                    score = score + currentValue + nextValue;

                }
            }

            while (array.remove(Integer.valueOf(0))) ;

            for (int i = array.size(); i < width; i++) {
                array.add(0);
            }
        }
    }

    private void generateNewValue(){

        if (modified){

            ArrayList<Point2D> pointsArray = new ArrayList<>();

            for (int currentColumn = 0; currentColumn < width; currentColumn++){
                for (int currentRow = 0; currentRow < height; currentRow++){
                    if (field[currentRow][currentColumn] == 0) {
                        pointsArray.add(new Point2D.Double(currentRow, currentColumn));
                    }
                }
            }

            int pointIndex = (int) (Math.random() * (pointsArray.size() - 1));;

            Point2D firstPoint = pointsArray.get(pointIndex);

            field[(int)firstPoint.getX()][(int)firstPoint.getY()] = 2;

            modified = false;

        }

    }

    public void checkGameIsOver(){

        gameOver = true;

        for (int currentColumn = 0; currentColumn < width - 1; currentColumn++){

            if (!gameOver){break;}

            for (int currentRow = 0; currentRow < height - 1; currentRow++){

                if (!gameOver){break;}

                if (field[currentRow][currentColumn] == 0){
                    gameOver = false;
                }

                if (field[currentRow][currentColumn] == field[currentRow][currentColumn + 1]
                        || field[currentRow][currentColumn] == field[currentRow + 1][currentColumn]){
                    gameOver = false;
                }

            }

        }

    }

    public enum ColumnCollapseDirection{
        UP,
        DOWN
    }

    public enum RowCollapseDirection{
        LEFT,
        RIGHT
    }

}
