

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.util.EventObject;
import java.lang.Object;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;

public class TicTacToe extends JComponent {
    
    private int turn;
    private int[][] board = new int[3][3];
    
    public TicTacToe(int size) {

        JFrame frame = new JFrame("TicTacToe"); 
        frame.setContentPane(this);
        frame.setSize(size,size);
        frame.setLocation(100, 100);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int posX;
                int posY;

                System.out.println("Coucou" + board[1][1]);
                System.out.println("turn" + turn);
                posX = e.getX() / (size / 3);
                posY = e.getY() / (size / 3);
                System.out.println("CLICK!");

                    System.out.println(e.getX());
                    System.out.println(e.getY());
                    System.out.println(posX);
                    System.out.println(posY);
                    if (board[posX][posY] == 0)
                    {
                        board[posX][posY] = turn % 2 + 1;
                        repaint();
                        incTurn();
            }}
        });
        int x = 0;
        int y = 0;
        while (x < 3)
        {
            while (y < 3)
            {
                System.out.print(board[x][y]);
                y++;
            }
            x++;
            System.out.println("");
        }
}    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor(Color.WHITE); 
        g.fillRect(0, 0, getWidth(), getHeight()); 
        g.setColor(Color.BLACK);
        drawGrid(g, getWidth(), getHeight());
        colorBoard(g, board); 
    }
    private void drawGrid(Graphics g, int width, int height) {
        g.drawLine(width / 3, 0, width / 3, height);
        g.drawLine(width * 2 / 3, 0, width * 2 / 3, height);
        g.drawLine(0, height / 3, width, height / 3);
        g.drawLine(0, height * 2 / 3, width, height * 2 / 3);
    }

    private void colorBox(Graphics g, int i, int j) {
        g.fillRect(getWidth() * i / 3, getHeight() * j / 3, getWidth()/3, getHeight()/3);
    }

    private void colorBoard(Graphics g, int[][] b)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (b[i][j] == 1)
                {
                    g.setColor(Color.RED);
                    colorBox(g, i, j);
                }
                else if (b[i][j] == 2)
                {
                    g.setColor(Color.BLUE);
                    colorBox(g, i, j);
                }
                else
                {
                    continue; 
                }
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    void incTurn() {
        turn++;
    }
    public int getTurn() {
        return turn;
    }
}
