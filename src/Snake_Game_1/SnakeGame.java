package Snake_Game_1;

import javax.swing.JFrame;

public class SnakeGame {

	public static void main(String[] args) {
		
		int boardWidth=600;
		int boardHeight=boardWidth;
		JFrame frame =new JFrame("Snake Game");
		frame.setVisible(true);
		frame.setSize(boardWidth,boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnakeGamePanel sgp=new SnakeGamePanel(boardWidth, boardHeight);
		frame.add(sgp);
		frame.pack();
		sgp.requestFocus();
	}

}
