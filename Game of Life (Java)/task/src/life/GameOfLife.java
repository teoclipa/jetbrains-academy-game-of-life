package life;

import life.model.Cell;
import life.model.Universe;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    private final JLabel generationLabel;
    private final JLabel aliveLabel;
    private final GamePanel gamePanel;
    private final JToggleButton playToggleButton;
    private final Timer timer;
    private final Universe universe;
    private boolean isRunning = true;

    public GameOfLife() {
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize the top panel with labels
        JPanel topPanel = new JPanel();
        generationLabel = new JLabel("Generation #0");
        generationLabel.setName("GenerationLabel");
        aliveLabel = new JLabel("Alive: 0");
        aliveLabel.setName("AliveLabel");
        topPanel.add(generationLabel);
        topPanel.add(aliveLabel);
        add(topPanel, BorderLayout.NORTH);

        // Initialize universe and game panel
        universe = new Universe(10); // The size can be dynamic or based on user input
        gamePanel = new GamePanel(universe);
        add(gamePanel, BorderLayout.CENTER);

        // Initialize the bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        playToggleButton = new JToggleButton("Pause");
        playToggleButton.setName("PlayToggleButton");
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");

        bottomPanel.add(playToggleButton);
        bottomPanel.add(resetButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Timer to update the universe
        timer = new Timer(150, e -> {
            if (isRunning) {
                universe.computeNextGeneration();
                updateGameState(universe);
            }
        });
        // PlayToggleButton ActionListener
        playToggleButton.addActionListener(e -> {
            isRunning = !isRunning; // Toggle the state

            if (isRunning) {
                playToggleButton.setText("Pause");
                playToggleButton.setSelected(true); // Ensure the button is selected when running
                timer.start(); // Start the timer if the game is running
            } else {
                playToggleButton.setText("Resume");
                playToggleButton.setSelected(false); // Ensure the button is not selected when paused
                timer.stop(); // Stop the timer if the game is paused
            }
        });

// ResetButton ActionListener
        resetButton.addActionListener(e -> {
            universe.reset();
            isRunning = false; // Set the game to paused state
            playToggleButton.setText("Resume");
            playToggleButton.setSelected(false); // Ensure the button reflects the paused state
            timer.stop(); // Ensure the timer is stopped
        });

        timer.start();

        setVisible(true);
    }

    public void updateGameState(Universe universe) {
        generationLabel.setText("Generation #" + universe.getGenerationCount());
        aliveLabel.setText("Alive: " + universe.countAliveCells());
        gamePanel.setUniverse(universe);
        repaint();
    }

    // Inner class for the game panel to draw the universe
    private static class GamePanel extends JPanel {
        private Universe universe;

        public GamePanel(Universe universe) {
            this.universe = universe;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (universe != null) {
                int cellSize = Math.min(getWidth(), getHeight()) / universe.getSize();
                Cell[][] cells = universe.getCurrentGeneration();
                for (int i = 0; i < universe.getSize(); i++) {
                    for (int j = 0; j < universe.getSize(); j++) {
                        int x = j * cellSize;
                        int y = i * cellSize;
                        if (cells[i][j].isAlive()) {
                            g.fillRect(x, y, cellSize, cellSize);
                        }
                    }
                }
            }
        }

        public void setUniverse(Universe universe) {
            this.universe = universe;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameOfLife::new);
    }
}
