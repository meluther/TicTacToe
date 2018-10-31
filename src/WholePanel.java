// CSE 205 Honors Contract
// Description: The WholePanel class creates a Tic Tac Toe game that allows 
// two players to mark a board until there is a tie or there are 3 X's or O's in
// a row/column/diagonal. The game also keeps track of the score after each 
// round in a panel. Players can play again or quit after each round. 

import java.awt.*; // to use fonts, colors, layouts
import javax.swing.*; // to use JPanel
import java.awt.event.*; //to use listener interfaces

public class WholePanel extends JPanel
{
    private JPanel buttonPanel, scorePanel;
    private JButton buttonArray[] = new JButton[9];
    private JLabel xLabel, oLabel; 
    private JTextField xField, oField; 
    private Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 100);
    private Font labelFont = new Font("Comic Sans MS", Font.BOLD, 50);
    
    //components all aranged here
    public WholePanel()
    {
        // creates button panel that looks like tic tac toe board
        ButtonListener bLis = new ButtonListener();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,3)); 
        
        // initializes buttons, adds listeners, and adds them to the panel 
        for (int i = 0; i < 9; i++)
        {
            buttonArray[i] = new JButton(); 
            buttonArray[i].setForeground(Color.RED);
            buttonArray[i].setBackground(Color.BLUE);
            buttonArray[i].setOpaque(true); 
            buttonArray[i].setFont(buttonFont); 
            buttonArray[i].addActionListener(bLis);
            buttonPanel.add(buttonArray[i]); 
        }

        // initializes score labels 
        oLabel = new JLabel("O: ");
        oLabel.setForeground(Color.RED);
        oLabel.setFont(labelFont); 
        xLabel = new JLabel("X: ");
        xLabel.setForeground(Color.RED);
        xLabel.setFont(labelFont); 
        oField = new JTextField("0"); // initializes score to 0
        oField.setForeground(Color.BLUE);
        oField.setFont(labelFont); 
        xField = new JTextField("0"); // initializes score to 0 
        xField.setForeground(Color.BLUE);
        xField.setFont(labelFont); 
        
        // creates score panel
        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout()); // creates score region's layout
        scorePanel.add(oLabel);
        scorePanel.add(oField);
        scorePanel.add(xLabel);
        scorePanel.add(xField);
        
        // creates entire GUI
        setLayout(new BorderLayout());
        add(scorePanel, BorderLayout.SOUTH);   
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    // resets board once a round is completed 
    public void reset()
    {
        for (int i = 0; i < 9; i++)
        {
            buttonArray[i].setText("");
        }
    }
        //nested class of WholePanel; listens for button click
        private class ButtonListener implements ActionListener
        {
            int state = 0; // represents a button change to an O or X
            String player = ""; // represents last player in control  
            boolean victory = false; // represents if there are the same 
            // characters in vertical, horizontal, or diagonal directions
            int anotherRound = Integer.MAX_VALUE; // allows user to play another 
            // round
            int countO = 0; // represents number of O's on the board
            int countX = 0; // represents number of X's on the board
            
            //stores whether or not button is clicked
            @Override
            public void actionPerformed(ActionEvent event)
            {
                Object obj = event.getSource(); 
                JButton clicked = (JButton)obj;
                if (state % 2 == 0) // first player (O) makes a move
                {
                    if (clicked.getText().equals("")) // only makes a move if 
                        // the button has not already been clicked
                    {
                        clicked.setText("O"); 
                        player = "O";
                        state++;
                    }
                }
                else // second player (X) makes a move
                {
                    if (clicked.getText().equals("")) // only makes a move if 
                        // the button has not already been clicked
                    {
                        clicked.setText("X"); 
                        player = "X";
                        state++;
                    }
                }
                  
                // calls checkForVictory (see below) for each winning combination
                if (checkForVictory(0, 3) && checkForVictory(3, 6) || // vertical
                    checkForVictory(1, 4) && checkForVictory(4, 7) || // vertical
                    checkForVictory(2, 5) && checkForVictory(5, 8) || // vertical
                    checkForVictory(0, 1) && checkForVictory(1, 2) || // horizontal
                    checkForVictory(3, 4) && checkForVictory(4, 5) || // horizontal
                    checkForVictory(6, 7) && checkForVictory(7, 8) || // horizontal
                    checkForVictory(0, 4) && checkForVictory(4, 8) || // diagonal
                    checkForVictory(2, 4) && checkForVictory(4, 6)) // diagonal    
                {
                    victory = true; 
                }
                
                // updates score, declares winner and asks the user if they want
                // to play another round
                if (victory)
                {
                    if (player.equals("O"))
                    {
                        countO++; 
                        oField.setText(Integer.toString(countO));
                    }
                    else if (player.equals("X"))
                    {
                        countX++; 
                        xField.setText(Integer.toString(countX));
                    }
                    state = 0; // resets the state to reflect a new round 
                    Object[] options = { "Play again", "Quit" };
                    anotherRound = JOptionPane.showOptionDialog(null, 
                        player + " wins this round!", 
                        "The winner is...",
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE,
                        null, 
                        options, 
                        options[0]);
                }
                else if (state == 9)
                {
                    state = 0; // resets the state to reflect a new round 
                    Object[] options = { "Play again", "Quit" };
                    anotherRound = JOptionPane.showOptionDialog(null, 
                        "No one wins this round.", 
                        "The winner is...",
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE,
                        null, 
                        options, 
                        options[0]);
                    reset(); // resets the board 
                }
                
                // resets display based on if user wants to play another round
                if (victory && anotherRound == JOptionPane.YES_OPTION)
                {
                    victory = false; 
                    reset(); 
                }
                else if (anotherRound == JOptionPane.NO_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "The final score is:\n"
                        + "O: " + oField.getText() + "\nX: " + xField.getText());
                    System.exit(0); // exits if user does not want to play again
		}

                repaint(); 
            } // end of Action Performed class 
            
            // checks for victories based on button text 
            public boolean checkForVictory(int first, int second)
            {
                return (buttonArray[first].getText().equals
                    (buttonArray[second].getText()) && !buttonArray[second].
                    getText().equals(""));
            }
        } // end of Button Listener Class
} // end of Whole Panel Class
