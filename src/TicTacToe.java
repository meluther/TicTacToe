// CSE 205 Honors Contract
// Description: The TicTacToe class initializes the whole panel of a Tic 
// Tac Toe game and sets it to a large size on the screen. 

import javax.swing.*;

public class TicTacToe extends JApplet
{
    @Override
    public void init()
    {
        // create a WholePanel object and add it to the applet
        WholePanel wholePanel = new WholePanel();
        getContentPane().add(wholePanel);

        //set applet size to 800 X 800
        setSize (800, 800);
    }
}