package life.model;

import java.util.Random;

public class Universe {
    private Cell[][] currentGeneration;
    private Cell[][] nextGeneration;
    private int generationCount = 0;
    private final int size;

    public Universe(int size) {
        this.size = size;
        currentGeneration = new Cell[size][size];
        nextGeneration = new Cell[size][size];
        initialize();
    }

    private void initialize() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolean isAlive = random.nextBoolean();
                currentGeneration[i][j] = new Cell(isAlive);
                nextGeneration[i][j] = new Cell(false); // Initialize with dead cells
            }
        }
    }

    public void computeNextGeneration() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                if (currentGeneration[i][j].isAlive()) {
                    // A live cell survives with 2 or 3 live neighbors; otherwise, it dies.
                    nextGeneration[i][j].setAlive(liveNeighbors == 2 || liveNeighbors == 3);
                } else {
                    // A dead cell is reborn with exactly 3 live neighbors.
                    nextGeneration[i][j].setAlive(liveNeighbors == 3);
                }
            }
        }

        // Swap generations
        Cell[][] temp = currentGeneration;
        currentGeneration = nextGeneration;
        nextGeneration = temp;
        generationCount++;
    }

    private int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself

                int neighborRow = (row + i + size) % size;
                int neighborCol = (col + j + size) % size;

                if (currentGeneration[neighborRow][neighborCol].isAlive()) {
                    liveNeighbors++;
                }
            }
        }

        return liveNeighbors;
    }

    public int countAliveCells() {
        int aliveCount = 0;
        for (Cell[] row : currentGeneration) {
            for (Cell cell : row) {
                if (cell.isAlive()) {
                    aliveCount++;
                }
            }
        }
        return aliveCount;
    }

    public Cell[][] getCurrentGeneration() {
        return currentGeneration;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public int getSize() {
        return size;
    }

    public boolean isCellAlive(int i, int j) {
        return currentGeneration[i][j].isAlive();
    }

    public void reset() {
        initialize();
        generationCount = 0;
    }
}