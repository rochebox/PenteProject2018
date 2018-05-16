package pentePac2018;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGame extends JPanel implements MouseListener
{
  
        public static final int SQUARES_ON_SIDE = 19;  //literal
        public static final int EMPTY = 0;
        public static final int RED = 1;
        public static final int GOLD = -1;
        public static final int EDGE = 2;
        
        //Fields to get computer playing.....
        public String player1, player2;
        public String whoIsRED, whoIsGOLD;
        public ComputerMoveGenerator cmg = null;
       
        
        private int width, height;
        private Color bColor = Color.MAGENTA;
        //Two new variables....for frame awesomeness...
        private JFrame myFrame;
        private int frameBorder;
        //I'm adding this for turns
        private int whoseTurn = RED;
        
        //We need variables to remember captures
        private int redCaptures = 0;
        private int goldCaptures = 0;
        
        // We make an array to hold all the board Squares
        private Square[ ][ ] board;
  
        ScorePanel scorePanel;
       
        //This is called by the constructor to get setup information
        public void setUpPlayers()
        {
          
          //set up for Game
          player1 = JOptionPane.showInputDialog(this,"What is your name?");
          String answer = JOptionPane.showInputDialog(this,"Would you like the comptuer to play (y or n)");
          if( answer.toLowerCase().matches("y")   ||  answer.toLowerCase().matches("yes") )
          {
              player2  = "Computer";
              //Make the computer move generator here:
              cmg = new ComputerMoveGenerator(board, this);
           
            
          } else {
              player2 = JOptionPane.showInputDialog(this,"Who is player2?");
          }
          
          answer = JOptionPane.showInputDialog(this, "Is " + player1 + " going to be RED or GOLD (r or g)");
          if( answer.toLowerCase().matches("r")   ||  answer.toLowerCase().matches("red") )
          {
              whoIsRED = player1;
              scorePanel.setRedPlayerName(player1);  
              whoIsGOLD = player2;
              scorePanel.setGoldPlayerName(player2);
            
          } else {
              whoIsRED = player2;
              scorePanel.setRedPlayerName(player2);
              whoIsGOLD = player1;   
              scorePanel.setGoldPlayerName(player1);
          }
          
      
      
          answer = JOptionPane.showInputDialog(this,"Would " + player1 + " like to go First or Second (f or s) ");
          if( answer.toLowerCase().matches("f")   ||  answer.toLowerCase().matches("first") )
          {
             if(whoIsRED == player1)
             {
               whoseTurn = RED;
             } else {
               whoseTurn = GOLD;
             }
            
          } else {
            
            if(whoIsRED == player1)
            {
              whoseTurn = GOLD;
            } else {
              whoseTurn = RED;
            }
                     
          }
          
          scorePanel.setCurrentTurn(whoseTurn);
          
          //allow for computer to generate move on first turn
          if(
              (whoseTurn == GOLD && whoIsGOLD == "Computer")
              ||
              (whoseTurn == RED && whoIsRED == "Computer")
              ) {
            
          
              
                  
                  cmg.makeAFirstMove(); 
                  scorePanel.setCurrentTurn(whoseTurn);
          }
          
         
          
          
        }
        
        //Constructor
        public PenteGame ( int w, int h, int b, JFrame f )
        {
          
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

              //capture info about frame and border...
              myFrame = f;
              frameBorder = b;
              
              width = w;
              height = h;
              
              
              //Making the object....
              
              // Initializing the array to size 19
              board = new Square[SQUARES_ON_SIDE] [SQUARES_ON_SIDE];

              //now use loops to put in the squares on the board
              //This makes 361 Squares....
              int squareLength = (int)(Math.round(width/ this.SQUARES_ON_SIDE));
              
              width = (SQUARES_ON_SIDE * squareLength) + (frameBorder * 2);
              height = SQUARES_ON_SIDE * squareLength + (frameBorder * 2);
              
              int scorePanelHeight = 300;
              scorePanel = new ScorePanel((int) width/2, height);
              scorePanel.setMinimumSize(new Dimension((int) width/2, height));
              scorePanel.setMaximumSize(new Dimension((int) width/2, height));
              
              //myFrame.setResizable(true);
              myFrame.setSize(width + (int)( width/2 ),  height+ 20);
              myFrame.setMinimumSize(new Dimension (width + (int)( width/2 ),  height+ 20)  );
              myFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
              
             
              this.setMinimumSize(new Dimension(width, height));
              this.setMaximumSize(new Dimension(width, height));
 
  
              myFrame.add(this);
              myFrame.add(scorePanel);
            
              myFrame.repaint();
              this.setSize(width, height);
              
              int xStart = frameBorder;
              int yStart = frameBorder;
              
              // These loops generate all the squares.
              for(int c = 0; c < SQUARES_ON_SIDE; c++)
              {
                     yStart = frameBorder;
                      for(int r = 0; r < SQUARES_ON_SIDE; r++)
                      {
                         board[r][c] = new Square(xStart, yStart, squareLength, r, c);
                         yStart += squareLength;
                         // this is to test square states
                        
                           //board[r][c].setState(this.RED);
                      }
                         xStart += squareLength;
              }
              
              
              
              container.add(this);
              container.add(scorePanel);
             f.add(container);
            
              f.setVisible(true);
              this.addMouseListener(this);
              this.setUpPlayers();
    
        }
        
        
       public void paintComponent(java.awt.Graphics g)
       {
         
           myFrame.setSize(width, height+20);
           g.setColor(bColor);
           g.fillRect(0, 0, width, height);
           
          //testSquare.drawMe(g);
           
           
           // These loops draw all the squares.
           for(int r = 0; r < SQUARES_ON_SIDE; r++)
           {
                   for(int c = 0; c < SQUARES_ON_SIDE; c++)
                   {
                      board[r][c].drawMe(g);    
                   }  
           }
           
         
       }


      public void mouseClicked(MouseEvent e)
      {
        // TODO Auto-generated method stub
        
      }


      public void mousePressed(MouseEvent e)
      {
        // TODO Auto-generated method stub
        
            //System.out.println("Hi -- I can't talk either because I have no voice");
            
            System.out.println("You clicked at " + e.getX() + ", " + e.getY() );
            
            
            this.checkClick(e.getX(), e.getY() );
        
      }
      
      public  void checkClick(int mouseX, int mouseY){
        Square ClickedSquare;   //temporary square that holds a link to where you clicked
        for(int r = 0; r < SQUARES_ON_SIDE; r++)
        {
                for(int c = 0; c < SQUARES_ON_SIDE; c++)
                {
                      if(board[r][c].thisSquareClicked(mouseX, mouseY)  == true){
                        ClickedSquare = board[r][c];
                        
                        System.out.println("you clicked on Square " + r + ", " + c);
                     
                        if(ClickedSquare.getState()==EMPTY){
                            ClickedSquare.setState(whoseTurn);
                            
                            //NOW Check for Captures....
                            checkForCaptures(r, c);
                            
                            this.changeTurn();
                            this.repaint();
                            scorePanel.setCurrentTurn(whoseTurn);
                            this.paintImmediately(0, 0, width, height);
                           
     
                            
                            
                            if(player2.equals("Computer")){
                              //System.out.println("Computer should be playing now.");
                              //computerMoveStartTime = moveMillisecondCounter;
                              if(
                                  (whoseTurn == GOLD && whoIsGOLD == "Computer")
                                  ||
                                  (whoseTurn == RED && whoIsRED == "Computer")
                                  ) {
                                
                              
                                      try
                                      {
                                           Thread.sleep(1500); // sleep for 1500 milliseconds
                                      }
                                      catch (Exception e)
                                      {
                                           e.printStackTrace();
                                      }
                                      
                                      cmg.makeAComputerMove(r,c); 
                                      scorePanel.setCurrentTurn(whoseTurn);
                              }
                            }
         
                        } else {
                           javax.swing.JOptionPane.showMessageDialog(null, "YOU CAN'T CLICK HERE");
                        }
                        
                      }
                }        
          }
      }
      
     
      
      
      public void checkForCaptures(int row, int col){
           int RIGHT = 1;
           int LEFT = -1;
           int UP = 1;
           int DOWN = -1;
           checkHorizontal(row, col, RIGHT);
           checkHorizontal(row, col, LEFT);
           
           checkVertical(row, col, RIGHT);
           checkVertical(row, col, LEFT);
           
           checkDiagonalRight(row, col, UP);
           checkDiagonalRight(row, col, DOWN);
           
           checkDiagonalLeft(row, col, UP);
           checkDiagonalLeft(row, col, DOWN);
           
        
      }
      
      public void checkHorizontal(int row, int col,  int dir)
      {
        repaint();
        // Step1 -- find out who we are looking for....
          int lookingFor;
          if(whoseTurn == PenteGame.RED)
          {
               lookingFor = PenteGame.GOLD;
               
          } else {
                lookingFor = PenteGame.RED;
             
          }
          
          if((dir ==1 && col <=15) || (dir == -1 && col >=3) )
          {
                if(board[row][col+(1 * dir)].getState()==lookingFor)
                  if(board[row][col+(2 * dir)].getState()==lookingFor)
                    if(board[row][col+(3 * dir)].getState()==whoseTurn)
                    {
                          System.out.println("This should be a capture");
                          
                          board[row][col+(1 * dir)].setState(PenteGame.EMPTY);
                          board[row][col+(2 * dir)].setState(PenteGame.EMPTY);
                          repaint();
                          
                          if(whoseTurn == PenteGame.GOLD)
                          {
                            goldCaptures ++;
                            scorePanel.setGoldCaptures(goldCaptures);
                            String g = "Good Job, Gold!!! You have " + goldCaptures + " captures!";
                            javax.swing.JOptionPane.showMessageDialog(null, g);
                          } else {
                            redCaptures ++;
                            scorePanel.setRedCaptures(redCaptures);
                            String r = "Good Job, Red!!! You have " + redCaptures + " captures!";
                            javax.swing.JOptionPane.showMessageDialog(null, r);
                          }
                          
                         
                     
                    }
          }
          
          
      }     
          
          public void checkVerticalUp(int row, int col)
          {
            repaint();
            // Step1 -- find out who we are looking for....
              int lookingFor;
              if(whoseTurn == PenteGame.RED)
              {
                   lookingFor = PenteGame.GOLD;
                   
              } else {
                    lookingFor = PenteGame.RED;
                 
              }
              
              if( row >=3)
              {
                    if(board[row + 1][col].getState()==lookingFor)
                      if(board[row + 2][col].getState()==lookingFor)
                        if(board[row +3][col].getState()==whoseTurn)
                        {
                              System.out.println("This should be a capture vert up");
                              
                              board[row + 1][col].setState(PenteGame.EMPTY);
                              board[row + 2][col].setState(PenteGame.EMPTY);
                              repaint();
                              
                              if(whoseTurn == PenteGame.GOLD)
                              {
                                goldCaptures ++;
                                scorePanel.setGoldCaptures(goldCaptures);
                                String g = "Good Job, Gold!!! You have " + goldCaptures + " captures!";
                               javax.swing.JOptionPane.showMessageDialog(null, g);
                              } else {
                                redCaptures ++;
                                scorePanel.setRedCaptures(redCaptures);
                               String r = "Good Job, Red!!! You have " + redCaptures + " captures!";
                               javax.swing.JOptionPane.showMessageDialog(null, r);
                              }
                              
                             
                  
                        }       
              }
          
          
          
       
      }
      
          
      
      public void checkVertical(int row, int col,  int dir)
      {
        repaint();
        // Step1 -- find out who we are looking for....
          int lookingFor;
          if(whoseTurn == PenteGame.RED)
          {
               lookingFor = PenteGame.GOLD;
               
          } else {
                lookingFor = PenteGame.RED;
             
          }
          
          if((dir ==1 && row <=15 ) || (dir == -1 && row >=3) )
          {
                if(board[row+(1 * dir)][col].getState()==lookingFor)
                  if(board[row+(2 * dir)][col].getState()==lookingFor)
                    if(board[row+(3 * dir)][col].getState()==whoseTurn)
                    {
                          System.out.println("This should be a capture vertical");
                          
                          board[row+(1 * dir)][col].setState(PenteGame.EMPTY);
                          board[row+(2 * dir)][col].setState(PenteGame.EMPTY);
                          repaint();
                          
                          if(whoseTurn == PenteGame.GOLD)
                          {
                            goldCaptures ++;
                            scorePanel.setGoldCaptures(goldCaptures);
                            String g = "Good Job, Gold!!! You have " + goldCaptures + " captures!";
                             javax.swing.JOptionPane.showMessageDialog(null, g);
                          } else {
                            redCaptures ++;
                            scorePanel.setRedCaptures(redCaptures);
                            String r = "Good Job, Red!!! You have " + redCaptures + " captures!";
                            javax.swing.JOptionPane.showMessageDialog(null, r);
                          }
                          
                         
                     
                    }       
          }
       
      }
      
      
      
      public void checkDiagonalRight(int row, int col,  int dir)
      {
        repaint();
        // Step1 -- find out who we are looking for....
          int lookingFor;
          if(whoseTurn == PenteGame.RED)
          {
               lookingFor = PenteGame.GOLD;
               
          } else {
                lookingFor = PenteGame.RED;
             
          }
          
          if(  (dir ==1   &&  row >= 3 && col <=15 )  ||  (dir == -1   && row <=15  && col >=3)  )
          {
                if(board[row -(1 * dir)][col+(1 * dir)].getState()==lookingFor)
                  if(board[row -(2 * dir)][col+(2 * dir)].getState()==lookingFor)
                    if(board[row-(3 * dir)][col+(3 * dir)].getState()==whoseTurn)
                    {
                          System.out.println("This should be a capture");
                          
                          board[row-(1 * dir)][col+(1 * dir)].setState(PenteGame.EMPTY);
                          board[row-(2 * dir)][col+(2 * dir)].setState(PenteGame.EMPTY);
                          repaint();
                          
                          if(whoseTurn == PenteGame.GOLD)
                          {
                            goldCaptures ++;
                            scorePanel.setGoldCaptures(goldCaptures);
                            String g = "Good Job, Gold!!! You have " + goldCaptures + " captures!";
                            javax.swing.JOptionPane.showMessageDialog(null, g);
                          } else {
                            redCaptures ++;
                            scorePanel.setRedCaptures(redCaptures);
                           String r = "Good Job, Red!!! You have " + redCaptures + " captures!";
                            javax.swing.JOptionPane.showMessageDialog(null, r);
                          }
                     
                    }       
              }
       
      }
      
    
      
      public void checkDiagonalLeft(int row, int col,  int dir)
      {
        repaint();
        // Step1 -- find out who we are looking for....
          int lookingFor;
          if(whoseTurn == PenteGame.RED)
          {
               lookingFor = PenteGame.GOLD;
               
          } else {
                lookingFor = PenteGame.RED;
             
          }
          
          if(  (dir ==1 && col >= 3  &&  row >= 3)   || (dir == -1 && col <=15  && row <=15 ) )
          {
                if(board[row -(1 * dir)][col-(1 * dir)].getState()==lookingFor)
                  if(board[row -(2 * dir)][col-(2 * dir)].getState()==lookingFor)
                    if(board[row-(3 * dir)][col-(3 * dir)].getState()==whoseTurn)
                    {
                          System.out.println("This should be a capture");
                          
                          board[row-(1 * dir)][col-(1 * dir)].setState(PenteGame.EMPTY);
                          board[row-(2 * dir)][col-(2 * dir)].setState(PenteGame.EMPTY);
                          repaint();
                          
                          if(whoseTurn == PenteGame.GOLD)
                          {
                            goldCaptures ++;
                            scorePanel.setGoldCaptures(goldCaptures);
                            //String g = "Good Job, Gold!!! You have " + goldCaptures + " captures!";
                            //javax.swing.JOptionPane.showMessageDialog(null, g);
                          } else {
                            redCaptures ++;
                            scorePanel.setRedCaptures(redCaptures);
                            //String r = "Good Job, Red!!! You have " + redCaptures + " captures!";
                            //javax.swing.JOptionPane.showMessageDialog(null, r);
                          }
                          
                         
                     
                    }       
          }
       
      }
      
      
      
      
      public void changeTurn(){
        whoseTurn *= -1;
        scorePanel.setCurrentTurn(whoseTurn);
      }

      public int getWhoseTurn()
      {
        return whoseTurn;
      }
      
      public String getWhoIsRed(){
        return whoIsRED;
      }
      
      public String getWhoIsGold(){
        return whoIsGOLD;
      }
      
      public int getRedCaptures(){
        return redCaptures;
      }

      public int getGoldCaptures(){
        return goldCaptures;
      }
      
      public void mouseReleased(MouseEvent e)
      {
        // TODO Auto-generated method stub
        
      }


      public void mouseEntered(MouseEvent e)
      {
        // TODO Auto-generated method stub
        
      }


      public void mouseExited(MouseEvent e)
      {
        // TODO Auto-generated method stub
        
      }

      public synchronized void actionPerformed(ActionEvent e)
      {
        // TODO Auto-generated method stub
//        moveMillisecondCounter++;
//        System.out.println("Timer Counter is at " + moveMillisecondCounter);
//        if(moveMillisecondCounter > 1000000000)
//          moveMillisecondCounter = (long) 0;
        
      }
      
      
     
        
        
        
        
       
}
