/* 
 * Description: Final Project - A GUI-based Tic-Tac-Toe game built using Java Swing.
 */

package tictaktoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class for the Tic-Tac-Toe game, extends JFrame to create a GUI window
public class TicTacToe extends JFrame {

    // Constructor to set up the game window and initialize the game components
    public TicTacToe() {
        // Set the title of the game window
        setTitle("Tic-Tac-Toe");

        // Ensure the application closes when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the game window
        setSize(1000, 500);

        // Get the desktop path to access image files
        String desktopPath = System.getProperty("user.home") + "/Desktop/";

        // Create the background panel with an image
        BackgroundPanel backgroundPanel = new BackgroundPanel(desktopPath + "background.jpg");

        // Create the Tic-Tac-Toe game panel
        TicTacToePanel gamePanel = new TicTacToePanel(desktopPath);

        // Add the game panel to the background panel
        backgroundPanel.add(gamePanel, BorderLayout.CENTER);

        // Set the background panel as the content pane of the JFrame
        setContentPane(backgroundPanel);

        // Make the window visible
        setVisible(true);
    }

    // Inner class for creating a background panel with an image
    private static class BackgroundPanel extends JPanel {
        private final String backgroundImagePath;

        // Constructor to set the path of the background image
        public BackgroundPanel(String backgroundImagePath) {
            this.backgroundImagePath = backgroundImagePath;
        }

        // Override the paintComponent method to draw the background image
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image backgroundImage = new ImageIcon(backgroundImagePath).getImage();
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Inner class for the Tic-Tac-Toe game logic and GUI
    private static class TicTacToePanel extends JPanel implements ActionListener {
        private JButton[][] buttons;  // 2D array to hold the game buttons
        private boolean isX = true;  // Keeps track of whose turn it is (X or O)
        private ImageIcon iconX, iconO;  // Icons for X and O

        // Constructor to set up the game grid and load the icons
        public TicTacToePanel(String desktopPath) {
            // Set the layout as a 3x3 grid for the game board
            setLayout(new GridLayout(3, 3));

            // Load the icons for X and O
            iconX = new ImageIcon(desktopPath + "x2.jpg");
            iconO = new ImageIcon(desktopPath + "o3.jpg");

            // Initialize the button array and add buttons to the panel
            buttons = new JButton[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j] = new JButton();
                    buttons[i][j].setFocusPainted(false);
                    buttons[i][j].addActionListener(this);
                    buttons[i][j].setPreferredSize(new Dimension(150, 150));  // Set button size
                    add(buttons[i][j]);  // Add the button to the panel
                }
            }
        }

        // Handles button clicks during the game
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            // Check if the clicked button is empty
            if (clickedButton.getIcon() == null) {
                // Set the icon based on the current player's turn
                if (isX) {
                    clickedButton.setIcon(iconX);
                } else {
                    clickedButton.setIcon(iconO);
                }

                // Switch the turn to the other player
                isX = !isX;

                // Check if there's a winner or if the board is full
                if (checkWin()) {
                    JOptionPane.showMessageDialog(this, (isX ? "O" : "X") + " wins!");
                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resetGame();
                }
            }
        }

        // Checks if there's a winning condition on the board
        private boolean checkWin() {
            for (int i = 0; i < 3; i++) {
                // Check rows and columns for a winning line
                if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                    return true;
                }
            }
            // Check diagonals for a winning line
            return checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                   checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
        }

        // Helper method to check if three buttons have the same icon
        private boolean checkLine(JButton b1, JButton b2, JButton b3) {
            return b1.getIcon() != null && b1.getIcon().equals(b2.getIcon()) && b2.getIcon().equals(b3.getIcon());
        }

        // Checks if the game board is completely filled
        private boolean isBoardFull() {
            for (JButton[] row : buttons) {
                for (JButton button : row) {
                    if (button.getIcon() == null) {
                        return false;
                    }
                }
            }
            return true;
        }

        // Resets the game board for a new game
        private void resetGame() {
            for (JButton[] row : buttons) {
                for (JButton button : row) {
                    button.setIcon(null);
                }
            }
            isX = true;
        }
    }

    // Main method to launch the Tic-Tac-Toe game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}
