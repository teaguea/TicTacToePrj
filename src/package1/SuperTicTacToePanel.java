package package1;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;

public class SuperTicTacToePanel extends JPanel
{
	/**The quit button **/
    private JButton quitButton;
    /**The reset button **/
    private JButton resetButton;
    /**The undo button **/
    private JButton undoButton;
    /**The save button **/
    private JButton saveButton;
    /**The load button **/
    private JButton loadButton;
    /**The top pane **/
    private JPanel top;
    /**The middle pane **/
    private JPanel middle;
    /**Array of buttons that make up board **/
    private JButton[][] board;
    /**Corresponding cell values for each button **/
    private Cell[][] iboard;
    /**The X graphic **/
    private ImageIcon xIcon;
    /**The O graphic **/
    private ImageIcon oIcon;
    /**The empty graphic **/
    private ImageIcon emptyIcon;
    /**The Tic Tac Toe game **/
    private SuperTicTacToeGame game;
    /**Label X wins **/
    private JLabel xLabel;
    /**Label O wins **/
    private JLabel oLabel;
    /**The point, coordinates of a button **/
    private Point point;

    
    public SuperTicTacToePanel(Integer size, int player)
    {
    	
        ButtonListener listener = new ButtonListener();
        
        game = new SuperTicTacToeGame(size, player);
                
        top = new JPanel(); 
        middle = new JPanel();
        
        xIcon = new ImageIcon("x.png");
        oIcon  = new ImageIcon("o.jpg");
        emptyIcon = new ImageIcon("empty.png");
        
        quitButton = new JButton("Quit");
        quitButton.addActionListener(listener);
        top.add(quitButton);
        
        resetButton = new JButton("Reset");
        resetButton.addActionListener(listener);
        top.add(resetButton);
        
        undoButton = new JButton("Undo");
        undoButton.addActionListener(listener);
        top.add(undoButton);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(listener);
        top.add(saveButton);
        
        loadButton = new JButton("Load");
        loadButton.addActionListener(listener);
        top.add(loadButton);
        
        xLabel = new JLabel("X Wins: ");
        top.add(xLabel);
        oLabel = new JLabel("O Wins: ");
        top.add(oLabel);
        
        middle.setLayout(new GridLayout(size,size));
        
        //Creates the board buttons
        board = new JButton[size][size];
        for(int row = 0; row < size; row++)
            for(int col = 0; col < size; col++)
            {
                board[row][col] = new JButton ("", emptyIcon);
                board[row][col].addActionListener(listener);
                middle.add(board[row][col]);
            }
            
        //Creats the cells for the board    
        iboard = new Cell[size][size];
        for(int row = 0; row < size; row++)
            for(int col = 0; col < size; col++)
                iboard[row][col] = Cell.EMPTY;
        
        
        setLayout(new BorderLayout ());
        add(top, BorderLayout.NORTH);
        add(middle, BorderLayout.CENTER);
        
    }
    
    /******************************************************************
     * Displays the board
     * @param none
     * @return none
     ******************************************************************/
    private void displayBoard(){
    	
        iboard = game.getBoard();
        
        for(int row = 0; row < game.getSize(); row++)
            for(int col = 0; col < game.getSize(); col++){
                if(iboard[row][col] == Cell.O)
                    board[row][col].setIcon(oIcon);
                if(iboard[row][col] == Cell.X)
                    board[row][col].setIcon(xIcon);
                if(iboard[row][col] == Cell.EMPTY)
                    board[row][col].setIcon(emptyIcon);
            }
    }
    
    
    
    private class ButtonListener implements ActionListener
    {
    	
    	/**************************************************************
    	 * Perform the correct action
    	 **************************************************************/
        public void actionPerformed(ActionEvent event) {
                        
            JComponent comp = (JComponent) event.getSource();
            //if the quit button is clicked the game exits
            if(comp == quitButton)
                System.exit(1);
            //if the reset button is clicked the game resets
            if(comp == resetButton)
                game.reset();
            //if the undo button is clicked the move is undone
            if(comp == undoButton)
            	game.undo();
            //if the save button is clicked the game is saved
            if(comp == saveButton)
            	game.save();
            //if the load button is clicked the game is loaded
            if(comp == loadButton)
				try {
					game.load();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
            //When a board is selected the game selects the button
            for(int row = 0; row < game.getSize(); row++)
                for(int col = 0; col < game.getSize(); col++){
                    if(board[row][col] == comp){
                       game.select(row, col);
                       //a point is created from the row and column
                       //that is selected
                       point = new Point(row, col);
                       //point is added to the game undo list
                       game.addundoList(point);
                    }
                        
                }
           //the board is displayed
            displayBoard();
            //the game's status is set
            game.setGameStatus();
            
            //if x has won a pane is displayed to show the win
            if(game.getGameStatus() == GameStatus.O_WON){
            
                JOptionPane.showMessageDialog(null, "O won and X" + 
                " lost!\n The game will reset");
                game.reset();
                displayBoard();
                
            }
            
            //if o has won a pane is displayed to show the win
            if(game.getGameStatus() == GameStatus.X_WON){
            
                JOptionPane.showMessageDialog(null, "X won and O" + 
                " lost!\n The game will reset");
                game.reset();
                displayBoard();
                
            }
            
            //if there are no empty spaces the game is declared a draw
            if(game.getGameStatus() == GameStatus.CATS){
            
                JOptionPane.showMessageDialog(null, "The Game has " + 
                "ended in a draw. And will reset.");
                game.reset();
                displayBoard();
                
            }
            
            //displays the number of X wins
            xLabel.setText("X Wins: " + game.getXwin());
            //displays the number of O wins
            oLabel.setText("O Wins: " + game.getOwin());
            
            
        }
    }
}