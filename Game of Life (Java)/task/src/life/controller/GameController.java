package life.controller;

import life.model.Universe;
import life.view.ConsoleView;

public class GameController {
    private final Universe universe;
    private final int generations;

    public GameController(int size) {
        universe = new Universe(size);
        this.generations = 10; // Default number of generations
    }

    public void startSimulation() {
        for (int i = 0; i < generations; i++) {
            ConsoleView.clearConsole();
            ConsoleView.printUniverse(universe, i + 1);
            universe.computeNextGeneration();
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulation interrupted.");
                break;
            }
        }
    }
}