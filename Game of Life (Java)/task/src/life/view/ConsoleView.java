package life.view;

import life.model.Cell;
import life.model.Universe;

public class ConsoleView {

    public static void printUniverse(Universe universe, int generation) {
        clearConsole();
        System.out.println("Generation #" + generation);
        System.out.println("Alive: " + universe.countAliveCells());
        Cell[][] cells = universe.getCurrentGeneration();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print(cell.isAlive() ? "O" : " ");
            }
            System.out.println();
        }
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }
}