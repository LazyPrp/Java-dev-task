package Snake_Game_1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGamePanel extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile fruit;
    Random random;

    Timer gameloop;
    int speedx;
    int speedy;
    boolean gameOver;
    boolean gameRunning;

    SnakeGamePanel(int boardWidth, int boardHeight) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        fruit = new Tile(10, 10);
        random = new Random();
        placeFood();
        speedx = 0;
        speedy = 0;
        gameOver = false;
        gameRunning = true;

        gameloop = new Timer(100, this);
        gameloop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
       // g.fillRect(fruit.x * tileSize, fruit.y * tileSize, tileSize, tileSize);
        //g.fill3DRect(fruit.x * tileSize, fruit.y * tileSize, tileSize, tileSize, true);
        g.fillOval(fruit.x * tileSize, fruit.y * tileSize, tileSize, tileSize);
        g.setColor(Color.black);
        g.drawOval(fruit.x * tileSize, fruit.y * tileSize, tileSize, tileSize);
        
        g.setColor(Color.green);
      //  g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
           // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over : " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score : " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    public void placeFood() {
        fruit.x = random.nextInt(boardWidth / tileSize);
        fruit.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(snakeHead, fruit)) {
            snakeBody.add(new Tile(fruit.x, fruit.y));
            placeFood();
        }

        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevsnakePart = snakeBody.get(i - 1);
                snakePart.x = prevsnakePart.x;
                snakePart.y = prevsnakePart.y;
            }
        }
        snakeHead.x += speedx;
        snakeHead.y += speedy;
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            move();
            repaint();
            if (gameOver) {
                gameRunning = false;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE ||e.getKeyCode()== KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (gameOver) {
                restartGame();
            }
        }

        if (gameRunning) {
            if (e.getKeyCode() == KeyEvent.VK_UP && speedy != 1) {
                speedx = 0;
                speedy = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && speedy != -1) {
                speedx = 0;
                speedy = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && speedx != 1) {
                speedx = -1;
                speedy = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && speedx != -1) {
                speedx = 1;
                speedy = 0;
            }
        }
    }

    private void restartGame() {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        placeFood();
        speedx = 0;
        speedy = 0;
        gameOver = false;
        gameRunning = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
