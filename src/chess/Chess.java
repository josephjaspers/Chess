/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Chess extends JFrame
{
   JButton[][] tiles;
   JPanel board; 
   int X1; int X2;
   int Y1; int Y2;
   int turncount = 0; 
   
   boolean lastclick = false;
   boolean legalmove;
   
   ImageIcon bb = new ImageIcon("bb.png");ImageIcon bk = new ImageIcon("bk.png");ImageIcon bn = new ImageIcon("bn.png");
   ImageIcon br = new ImageIcon("br.png");ImageIcon bq = new ImageIcon("bq.png");ImageIcon bp = new ImageIcon("bp.png");
   ImageIcon wb = new ImageIcon("wb.png");ImageIcon wk = new ImageIcon("wk.png");ImageIcon wn = new ImageIcon("wn.png");
   ImageIcon wr = new ImageIcon("wr.png");ImageIcon wq = new ImageIcon("wq.png");ImageIcon wp = new ImageIcon("wp.png");
 
   ChessPiece GAMESTATE = new ChessPiece();
      
   public Chess()
   {
      BuildBoard();
      add(board);
      StartGame();
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(300,300);      
   }
   private void BuildBoard() //build board 
   {
      tiles = new JButton[8][8];
      board = new JPanel();
      board.setLayout(new GridLayout(8,8));
      
      for (int x = 0; x < tiles.length; x++)
         {
            for (int y = 0; y < tiles.length; y++)
               {
                  if (y%2 == 0)
                     {
                        if (x%2 == 0)
                           {
                              tiles[x][y] = new JButton();
                              tiles[x][y].setBackground(Color.BLUE);
                              tiles[x][y].addActionListener(new PieceListener());    
                           }
                        else 
                           {
                              tiles[x][y] = new JButton();
                              tiles[x][y].setBackground(Color.RED);
                              tiles[x][y].addActionListener(new PieceListener());
                           }
                     }
                  else 
                     {
                        if (x%2 == 0)
                           {
                              tiles[x][y] = new JButton();
                              tiles[x][y].setBackground(Color.RED);
                              tiles[x][y].addActionListener(new PieceListener());
                           }
                        else 
                           {
                              tiles[x][y] = new JButton();
                              tiles[x][y].setBackground(Color.BLUE);
                              tiles[x][y].addActionListener(new PieceListener());
                           }
                     }
               }
         }
         for (int y = 0; y < tiles.length; y++)
            {
               for (int x = 0; x < tiles.length; x++)
                  {
                     board.add(tiles[x][7-y]); //orients the board towards ourselves
                  }                           // use 7-y to orient white to ourselves and just y to set black as base 
            }
   }

private class PieceListener implements ActionListener
{
   public void actionPerformed(ActionEvent e)
   {
      for (int y = 0; y < tiles.length; y++)
         {
            for (int x = 0; x < tiles.length; x++)
               {
                  if (e.getSource() == tiles[x][y])
                     {
                        X1 = x;
                        Y1 = y;
                        HighlightSquare();
                        CheckMove();
                        X2 = X1;
                        Y2 = Y1;
                        HighlightLegalMoves();
                     } 
               }
          }
    }
}
private void StartGame()
   {
      int[][] Board = GAMESTATE.StartBoard();
      Move();   
    }
private void HighlightSquare() // hightlights last clicked square 
    { 
      tiles[X2][Y2].setBorder(BorderFactory.createLineBorder(tiles[X2][Y2].getBackground(),1));
      tiles[X1][Y1].setBorder(BorderFactory.createLineBorder(Color.YELLOW,1));  
      if (lastclick = true) {HighlightLegalMoves();}    
    }
private void HighlightLegalMoves()
   {
      for (int x = 0; x < tiles.length; x++)
         {
            for (int y = 0; y < tiles.length; y++)
               {
                  if (GAMESTATE.LegalMove(X2,Y2,x,y) == true)
                     {
                        tiles[x][y].setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
                     }
                  else 
                     {
                        tiles[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                     }
                }
          }
    }
                  
private void CheckMove() // check if move is a move
   {
      if (lastclick == false) 
      { 
         lastclick = true;
      }
   else
      {
         if (tiles[X2][Y2] != tiles[X1][Y1]) // check if differnet squares
            {   
               if (GAMESTATE.LegalMove(X2, Y2, X1, Y1) == true && GAMESTATE.KingCheck() == false) // change to double == when Legal moves is completled 
                  {  System.out.println("legalmove");
                     lastclick = false;
                     tiles[X1][Y1].setBorder(BorderFactory.createLineBorder(tiles[X1][Y1].getBackground(),1));
                     GAMESTATE.movePiece();
                     Move();
                     if (GAMESTATE.CheckMate() == true)
                     {EndGame();}
                  }               
            }
         else {lastclick = false;}
      }
   }       
   private void Move() //moves the text around 
   {
      int[][] Board = GAMESTATE.getBoard();
            
      for (int x = 0; x < tiles.length; x++)
         {
            for (int y = 0; y < tiles.length; y++)
               {
                  if (Board[x][y] == 0)
                  {tiles[x][y].setText(null); tiles[x][y].setIcon(null);}
                  else
                  {
                  
                  switch (Board[x][y]) {
                  case 10: tiles[x][y].setIcon(wp);break;
                  case 20: tiles[x][y].setIcon(wr);break;
                  case 22: tiles[x][y].setIcon(wr);break;
                  case 30: tiles[x][y].setIcon(wn);break;
                  case 40: tiles[x][y].setIcon(wb);break;
                  case 50: tiles[x][y].setIcon(wk);break;
                  case 60: tiles[x][y].setIcon(wq);break;
                  
                  case 11: tiles[x][y].setIcon(bp);break;
                  case 21: tiles[x][y].setIcon(br);break;
                  case 23: tiles[x][y].setIcon(br);break;
                  case 31: tiles[x][y].setIcon(bn);break;
                  case 41: tiles[x][y].setIcon(bb);break;
                  case 51: tiles[x][y].setIcon(bk);break;
                  case 61: tiles[x][y].setIcon(bq);break;
                  
                  }
                  
                  }
               }
         }
   
   }

   public void EndGame()
   {
      if (turncount%2 == 0)
         {
            JOptionPane.showMessageDialog(null, "White Wins");
         }
      else 
         {
            JOptionPane.showMessageDialog(null, "Black Wins");
         }         
   }
   
   public static void main(String [] args) 
   {
      new Chess();
   }
}
