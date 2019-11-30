package org.tensorflow.lite.examples.classification.rover;

public class FieldActivity {
    double fieldWidth;
    double fieldLength;

    double blockWidth = 2.5;
    double blockLength = 5;

    private int blocksPerLane;
    private int lanes;

    static int notSearched = 0;
    static int searched = 1;
    static int found = 2;

    public int[][] grid = new int[lanes][blocksPerLane];


    //Sets blocksPerLane, lanes
    void calculateFieldParams(double fieldWidth, double fieldLength) {
        lanes = (int) (fieldWidth / blockWidth);
        blocksPerLane = (int) (fieldLength / blockLength);
    }

    //Creates the grid similar to battleship
    void createGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = notSearched;
            }
        }
    }

    //Changes value next to key from notSearched to searched
    void searchedBlock(int lane, int block) {
        grid[lane][block] = searched;
    }

    //Changes value next to key from notSearched to found
    void foundInBlock(int lane, int block) {
        grid[lane][block] = found;
    }

    public int getBlocksPerLane() {
        return blocksPerLane;
    }

    public int getLanes() {
        return lanes;
    }
}
