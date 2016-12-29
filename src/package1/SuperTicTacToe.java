/***********************************************************************
 *Creates a Super Tic Tac Toe game.
 *
 *Author: Aaron Teague
 **********************************************************************/
package package1;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SuperTicTacToe {
	
	/*******************************************************************
	 * Creates the board.
	 ******************************************************************/
	public static void main(String[] args) {
		try {
		
		int size;
		String input;
		String inputTwo;
		int player = 0;
		
		try {
		//Gets the size of the game from the user
		input = JOptionPane.showInputDialog(null, "Enter in the size "
				+ "for the Tic Tac Toe board: ");
		size = Integer.parseInt(input);
		
		//Checks if the board is a valid
		while((size < 3)  || (size > 15)){
		input = JOptionPane.showInputDialog(null, "Invalid number" +
		" please try again, or press cancel.");
			size = Integer.parseInt(input);
		}
		
		
		}
		
		catch(NumberFormatException e){
		input = JOptionPane.showInputDialog(null, "Please enter a" +
		" number, or press cancel.");
			size = Integer.parseInt(input);
		}
			//Asks player who is playing first
			inputTwo = JOptionPane.showInputDialog(null, "Who Starts" 
					+ " first? X or O");
			
			//Displays if X is first
			if(inputTwo.equals("X")){
				player = 1;
				JOptionPane.showMessageDialog(null,"X will start.");
			}
			
			//Displays if O is first
			else if(inputTwo.equals("O")){
				player = 2;
			    JOptionPane.showMessageDialog(null,"O will start.");
			}
			
			//Displays if X or O isn't selected
			else
			{
			JOptionPane.showMessageDialog(null,"An invalid value " +
			"has been entered, X will start.");
				player = 1;
			}
				
			

		
		JFrame frame = new JFrame("Super TicTacToe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
		SuperTicTacToePanel panel = 
				new SuperTicTacToePanel(size, player);
		
		frame.getContentPane().add(panel);
	
		frame.pack();
		frame.setSize(600, 600);
		frame.setVisible(true);
		}
		
		//If every thing fails this message will display
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Super Tic Tac Toe" +
		" has unexpectedly stopped working.");
		}
	
	}
}

