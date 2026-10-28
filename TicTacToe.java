 
import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {

    private JButton[] buttons = new JButton[9];
    private JLabel status;
    private JButton restart;
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private Color backgroundColor = new Color(17,24,39);
    private Color  buttonColor = new Color(31,41,55);
    private Color xColor = new Color(96,165,250);
    private Color oColor = new Color(248,113,113);
    private Color textColor = new Color(249,250,251);

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(backgroundColor);
        getContentPane().setForeground(textColor);

        status = new JLabel("Hey Player X it's your turn !", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 22));
        status.setForeground(xColor);
        status.setBorder(
            BorderFactory.createEmptyBorder(15,5,10,5)
        );
        add(status, BorderLayout.NORTH);

        JPanel board = new JPanel(new GridLayout(3, 3, 8, 8));
        board.setBackground(backgroundColor);
        board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            final int position = i;

            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 48));
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(buttonColor);
            buttons[i].setForeground(textColor);
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
            buttons[i].setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );

            buttons[i].addActionListener(e -> play(position));

            board.add(buttons[i]);
        }

        add(board, BorderLayout.CENTER);

        restart = new JButton("New Game");
        restart.setFont(new Font("Arial", Font.BOLD, 18));
        restart.setForeground(Color.WHITE);
        restart.setBackground(new Color(37, 99, 235));
        restart.setOpaque(true);
        restart.setBorderPainted(false);
        restart.setFocusPainted(false);
        restart.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        restart.addActionListener(e -> resetGame());

        add(restart, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void play(int position) {
        if (gameOver || !buttons[position].getText().isEmpty()) {
            return;
        }

        buttons[position].setText(String.valueOf(currentPlayer));
        buttons[position].setForeground(
            currentPlayer == 'X' ? xColor : oColor
        );

        if (checkWinner()) {
            status.setText("Player " + currentPlayer + " wins!");
            gameOver = true;
            disableBoard();
        } else if (isDraw()) {
            status.setText("It's a draw!");
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            status.setText("Player " + currentPlayer + "'s turn");
        }
    }

    private boolean checkWinner() {
        int[][] wins = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] line : wins) {
            String a = buttons[line[0]].getText();
            String b = buttons[line[1]].getText();
            String c = buttons[line[2]].getText();

            if (!a.isEmpty() && a.equals(b) && b.equals(c)) {
                for (int index : line) {
                    buttons[index].setBackground(new Color(22,101,52));
                    buttons[index].setForeground(Color.WHITE);
                }
                return true;
            }
        }

        return false;
    }

    private boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void disableBoard() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        currentPlayer = 'X';
        gameOver = false;

        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(buttonColor);
            button.setForeground(textColor);
        }

        status.setText("Player X's turn");
        status.setForeground(xColor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
