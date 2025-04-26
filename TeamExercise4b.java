package teamExercises;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class TeamExercise4b extends JFrame implements ActionListener {   //setup listener
    static int height;
    static int width;
    Container gameFrame;
    JPanel mainPanel;
    JPanel sidePanel;
    JLabel clickLabel;
    JButton[][] buttonArray;
    int gridSize = 4;
    int clickCount = 0;
    ImageIcon emptyIcon;
    int emptyRow, emptyCol;

    public TeamExercise4b() {
        this.setTitle("Tile Puzzle Game");
        this.setSize(600, 600);
        gameFrame = getContentPane();
        gameFrame.setLayout(new BorderLayout());

        emptyIcon = new ImageIcon("cat.png"); // Added SAU/cat Image here

        makeBoard(gridSize);                  // based on gridSize
        makeMenu();

        clickLabel = new JLabel("You clicked: 0 times", SwingConstants.CENTER);
        gameFrame.add(clickLabel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void makeBoard(int size) {
        height = size;
        width = size;
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(height, width));
        buttonArray = new JButton[height][width];

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < height * width; i++) {
            numbers.add(i);
        }
        numbers.add(0);                                     // Empty tile
        Collections.shuffle(numbers);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = numbers.remove(0);
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                if (value == 0) {
                    btn.setIcon(emptyIcon);
                    emptyRow = i;
                    emptyCol = j;
                } else {
                    btn.setText(String.valueOf(value));
                }
                btn.addActionListener(this);
                buttonArray[i][j] = btn;
                mainPanel.add(btn);
            }
        }
        gameFrame.add(mainPanel, BorderLayout.CENTER);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    public void makeMenu() {
        JPanel sidePanel = new JPanel();                    //Build Menu
        sidePanel.setLayout(new GridLayout(15, 1));
        gameFrame.add(sidePanel, BorderLayout.EAST);

        ButtonGroup group = new ButtonGroup();

        JRadioButton x3 = new JRadioButton("3x3 Game");     //Build grid
        JRadioButton x4 = new JRadioButton("4x4 Game");
        JRadioButton x5 = new JRadioButton("5x5 Game");
        JRadioButton x6 = new JRadioButton("6x6 Game");

        group.add(x3);
        group.add(x4);
        group.add(x5);
        group.add(x6);

        JButton startOver = new JButton("Start Over");

        sidePanel.add(x3);
        sidePanel.add(x4);
        sidePanel.add(x5);
        sidePanel.add(x6);
        sidePanel.add(startOver);

        x3.addActionListener(this);
        x4.addActionListener(this);
        x5.addActionListener(this);
        x6.addActionListener(this);
        startOver.addActionListener(this);
    }

    public void resetBoard() {                     //Reset board to starting point after start over or new grid
        gameFrame.remove(mainPanel);
        clickCount = 0;
        clickLabel.setText("You clicked: 0 times");
        makeBoard(gridSize);
    }

    public boolean isSolved() {
        // Standard Win Check (full solved board)  Verifies every number is in order.
        int expected = 1;
        boolean fullSolved = true;  
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == height - 1 && j == width - 1) {
                    // Last tile should be the empty/icon image
                    if (buttonArray[i][j].getIcon() == null) {
                        fullSolved = false;
                    }
                } else {
                    if (buttonArray[i][j].getText() == null || Integer.parseInt(buttonArray[i][j].getText()) != expected++) {
                        fullSolved = false;
                    }
                }
            }
        }

        // Additional 4-corner win check
        try {
            String topLeft = buttonArray[0][0].getText();
            String topRight = buttonArray[0][width - 1].getText();
            String bottomLeft = buttonArray[height - 1][0].getText();
            String bottomRight = buttonArray[height - 1][width - 1].getText();

            boolean cornerWin = "1".equals(topLeft) &&          // check to see if 1,2,3,4 are in the corners
                                 "2".equals(topRight) &&
                                 "3".equals(bottomLeft) &&
                                 "4".equals(bottomRight);

            return fullSolved || cornerWin;                     // Win if either condition is true
        } catch (Exception e) {
            return fullSolved;                                  // If error happens, rely on fullSolved
        }
    }

    public void actionPerformed(ActionEvent e) {     // handle user move
        String cmd = e.getActionCommand();

        if (cmd != null) { // Safely check first
            if (cmd.equals("3x3 Game")) {
                gridSize = 3;
                resetBoard();
                return;
            } else if (cmd.equals("4x4 Game")) {
                gridSize = 4;
                resetBoard();
                return;
            } else if (cmd.equals("5x5 Game")) {
                gridSize = 5;
                resetBoard();
                return;
            } else if (cmd.equals("6x6 Game")) {
                gridSize = 6;
                resetBoard();
                return;
            } else if (cmd.equals("Start Over")) {
                resetBoard();
                return;
            }
        }

        // Handle clicking a tile (prevent error if it's the blank/image tile)
        JButton clicked = (JButton) e.getSource();
        int r = -1, c = -1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (buttonArray[i][j] == clicked) {
                    r = i;
                    c = j;
                }
            }
        }
        if ((Math.abs(r - emptyRow) == 1 && c == emptyCol) || (Math.abs(c - emptyCol) == 1 && r == emptyRow)) {
            buttonArray[emptyRow][emptyCol].setText(clicked.getText());
            buttonArray[emptyRow][emptyCol].setIcon(null);
            clicked.setText(null);
            clicked.setIcon(emptyIcon);

            emptyRow = r;
            emptyCol = c;

            clickCount++;                                                     //update number of clicks
            clickLabel.setText("You clicked: " + clickCount + " times");

            if (isSolved()) {                                                //if winner, display message
                JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle in " + clickCount + " clicks!");
                resetBoard();
            }
        }
    }

    public static void main(String[] args) {                        // starting point to build GUI
        SwingUtilities.invokeLater(TeamExercise4b::new);
    }
}
