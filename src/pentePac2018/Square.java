package pentePac2018;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Comparator;

public class Square 
{
  
  
      private int xLoc, yLoc;
      private int sideLength;
      private int squareState;
      private int myRow, myCol;
     
      private Color bColor = new Color(253, 247, 201);
      
      private Color redStoneColor = new Color(219, 28, 95);
      private Color redDarkStoneColor = new Color(175, 8, 86);
      private Color redLightStoneColor = new Color(247, 46, 93);
      
      private Color goldStoneColor = new Color(224, 206, 87);
     // private Color goldDarkStoneColor = new Color(165, 141, 33);
      private Color goldDarkStoneColor = new Color(165, 160, 33);
      private Color goldLightStoneColor = new Color(249, 222, 14);
      
      private Color stoneShadowColor = new Color(132, 132, 116);
      
      private int nextMoveOPriority = -1;  //all priorities would positive.
      private int nextMoveDPriority = -1;  //all priorities would positive.
      private int moveForOffense = 0;   // either 0 or 1
      private int moveForDefense = 0;  // either 0 or -1
      
      private Font  f = new Font("Helvetica", Font.PLAIN, 18);
        

      
      
      public Square(int x, int y, int len, int r, int c) 
      {
          xLoc = x;
          yLoc = y;
          sideLength = len;
          squareState = PenteGame.EMPTY;
          myRow = r;
          myCol = c;
          
          
      }
      
      public void drawMe(Graphics g)
      {
            
            //Set Background
            g.setColor(bColor);
            //Draw background Square
            g.fillRect(xLoc, yLoc, sideLength, sideLength);
            
            //for drawing Available moves
           // drawAvailableMoves(g);
            
            //if there is a stone draw a shadow
            if(squareState!= PenteGame.EMPTY)
            {
                  g.setColor(stoneShadowColor);
                  g.fillOval (
                      xLoc + (int)(sideLength * 0.1 +2),
                      yLoc + (int)(sideLength * 0.1 +2 ),
                      (int)(sideLength * 0.8),
                      (int)(sideLength * 0.8 )
                      );
                    
            }
           
          
            // line for placing stone
            g.setColor(Color.darkGray);
            //draw vertical line...
            g.drawLine(xLoc + (sideLength/2), yLoc, xLoc + (sideLength/2), yLoc + (sideLength));
            //draw horizontal line
            g.drawLine(xLoc, yLoc + (sideLength/2) , xLoc + sideLength, yLoc + (sideLength/2));
            
           // g.setColor(Color.WHITE);
           // g.drawRect(xLoc, yLoc, sideLength, sideLength);
          
            
            
            if(squareState != PenteGame.EMPTY)
            {
                    g.fillOval (
                        xLoc + (int)(sideLength * 0.1),
                        yLoc + (int)(sideLength * 0.1),
                        (int)(sideLength * 0.8),
                        (int)(sideLength * 0.8 )
                        );
            }
            
            
            if(squareState == PenteGame.RED)
            {
              
                  g.setColor(redDarkStoneColor); 
                  g.fillOval (
                      xLoc + (int)(sideLength * 0.1),
                      yLoc + (int)(sideLength * 0.1),
                      (int)(sideLength * 0.8),
                      (int)(sideLength * 0.8 )
                      );
              
                  g.setColor(redLightStoneColor); 
                  
                  g.fillOval (
                      xLoc + (int)(sideLength * 0.1),
                      yLoc + (int)(sideLength * 0.1),
                      (int)(sideLength * 0.8-3),
                      (int)(sideLength * 0.8  -3 )
                      );
            }
            
            
            if(squareState == PenteGame.GOLD)
            {
              
                  g.setColor(goldDarkStoneColor); 
                  g.fillOval (
                      xLoc + (int)(sideLength * 0.1),
                      yLoc + (int)(sideLength * 0.1),
                      (int)(sideLength * 0.8),
                      (int)(sideLength * 0.8 )
                      );
              
                  g.setColor(goldLightStoneColor); 
                  
                  g.fillOval (
                      xLoc + (int)(sideLength * 0.1),
                      yLoc + (int)(sideLength * 0.1),
                      (int)(sideLength * 0.8-3),
                      (int)(sideLength * 0.8  -3 )
                      );
            }
            

      }
      
      
      public void drawAvailableMoves(Graphics g)
      {

                  if(moveForOffense == ComputerMoveGenerator.OFFENSE)
                  {
                        g.setColor(Color.GREEN);
                        
                        g.drawOval (
                            xLoc + (int)(sideLength * 0.1) + 3,
                            yLoc + (int)(sideLength * 0.1) + 3,
                            (int)(sideLength * 0.8) - 6,
                            (int)(sideLength * 0.8 ) - 6
                            );
                        
                        g.setColor(Color.GREEN);
                        g.setFont(f);
                        String s = "" + nextMoveOPriority;
                        g.drawString(s, xLoc+ (int)(sideLength * 0.3) -4, yLoc +(int)(sideLength * 0.3) + 4);
                        
                  }
                  
                  if(moveForDefense == ComputerMoveGenerator.DEFENSE)
                  {
                        g.setColor(Color.BLUE);
                        
                        g.drawOval (
                            xLoc + (int)(sideLength * 0.1) ,
                            yLoc + (int)(sideLength * 0.1) ,
                            (int)(sideLength * 0.8) ,
                            (int)(sideLength * 0.8 ) 
                            );
                        
                        
                        g.setColor(Color.BLUE);
                        g.setFont(f);
                        String s = "" + nextMoveDPriority;
                        g.drawString(s, xLoc+ (int)(sideLength * 0.3) + 5, yLoc +(int)(sideLength * 0.3) + 4);
                  }
   
                  
                
      }
      
      
      public void setState(int newState)
      {
          squareState = newState;
      }
      
      
      
      public int getState()
      {
          return squareState;
      }
      
      public boolean thisSquareClicked(int checkX, int checkY)
      {
        //This gives each square an ability to check to see if it was clicked.
          boolean gotClicked = false;
          
          if( checkX >= xLoc &&  checkX < xLoc + sideLength)
            if( checkY >= yLoc &&  checkY < yLoc + sideLength)
              gotClicked = true;
          
          
          return gotClicked;
      }
      
     //accessor methods. 
      public void setNextMovePriority(int newP, int OorD)
      {
        
              System.out.println(" setting Next Move priority for " + OorD + " its: " + newP);
              if(OorD == ComputerMoveGenerator.DEFENSE){
                
                  if( newP >= nextMoveDPriority)
                  {
                    
                    nextMoveDPriority = newP;
                    moveForDefense = OorD;
                    System.out.println("NextMoveDPriority is " + nextMoveDPriority);
                  }
              }
              
              if(OorD == ComputerMoveGenerator.OFFENSE){
                if (newP >=  nextMoveOPriority)
                {
                    nextMoveOPriority = newP;
                    moveForOffense = OorD;
                    System.out.println("NextMoveOPriority is " + nextMoveOPriority);
                }
            }
      }
      
      
      public int getNextMoveDPriority()
      {
            return nextMoveDPriority;
      }
      
      public int getNextMoveOPriority()
      {
            return nextMoveOPriority;
      }
      
      public void resetNextMovePriorities()
      {
            nextMoveOPriority = -1;
            nextMoveDPriority = -1;
            moveForOffense = 0;   // either 0 or 1
            moveForDefense = 0;  // either 0 or -1
      }
      
      public int getMyRow(){
        return myRow;
      }
      
      public int getMyCol() {
        return myCol;
      }


      
      public static Comparator<Square> OffenseCompare = new Comparator<Square>() {
        
        
                          public int compare(Square s1, Square s2){
                                 int oScore1 = s1.getNextMoveOPriority();
                                 int oScore2 = s2.getNextMoveOPriority();
                            
                                 return oScore2-oScore1;
                          }
        
      };

      
      public static Comparator<Square> DefenseCompare = new Comparator<Square>() {
        
        
        public int compare(Square s1, Square s2){
               int dScore1 = s1.getNextMoveDPriority();
               int dScore2 = s2.getNextMoveDPriority();
          
               return dScore2-dScore1;
        }

};
      

}
