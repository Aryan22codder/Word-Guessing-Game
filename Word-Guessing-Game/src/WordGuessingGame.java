import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class WordGuessingGame extends JFrame implements ActionListener {
    private JLabel guessLabel, guessedWordLabel, guessesLeftLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JPanel panel;
    private String[] words = {"apple", "banana", "cherry", "date", "elderberry"};
    private String word;
    private int guesses = 5;
    private boolean[] guessedLetters;
    private Random random = new Random();

    public WordGuessingGame() {
        super("Word Guessing Game");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("background3.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        guessLabel = new JLabel("Guess a letter:");
        guessLabel=new JLabel("HINT :Find the missing letters !");
        guessField = new JTextField(5);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        guessedWordLabel = new JLabel("");
        guessesLeftLabel = new JLabel("Guesses left: " + guesses);

        panel.add(guessLabel);
        panel.add(guessField);
        panel.add(guessButton);
        panel.add(guessedWordLabel);
        panel.add(guessesLeftLabel);

        add(panel);

        setVisible(true);
        startNewGame();
    }

    private void startNewGame() {
        word = words[random.nextInt(words.length)];
        guessedLetters = new boolean[word.length()];
        guesses = 10;
        guessedWordLabel.setText(getGuessedWord());
        guessesLeftLabel.setText("Guesses left: " + guesses);
        guessField.setText("");

    }

    private String getGuessedWord() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters[i]) {
                sb.append(word.charAt(i));
            } else {
                sb.append("_");
            }
            sb.append(" ");
        }

        return sb.toString();
    }

    private void checkGuess() {
        String guess = guessField.getText();
        char letter = guess.charAt(0);
        boolean foundLetter = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedLetters[i] = true;
                foundLetter = true;
            }
        }

        if (foundLetter) {
            guessedWordLabel.setText(getGuessedWord());
            if (word.equals(getGuessedWord().replaceAll("\\s", ""))) {
                JOptionPane.showMessageDialog(panel, "You win!");
                startNewGame();
            } else {
                guessField.setText("");
                guessField.requestFocus();
            }
        } else {
            guesses--;
            guessesLeftLabel.setText("Guesses left: " + guesses);
            if (guesses == 0) {
                JOptionPane.showMessageDialog(panel, "You lose. The word was " + word + ".");
                startNewGame();
            } else {
                guessField.setText("");
                guessField.requestFocus();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkGuess();
    }

    public static void main(String[] args) {
        new WordGuessingGame();
    }
}
