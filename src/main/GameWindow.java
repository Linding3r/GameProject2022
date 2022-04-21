package main;

import javax.swing.*;

public class GameWindow {
  private JFrame jframe;

  public GameWindow(GamePanel gamePanel){
    jframe = new JFrame();

    jframe.setSize(400,400);
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Shuts down when clicking Exit
    jframe.add(gamePanel); //add panel in window
    jframe.setLocationRelativeTo(null); //open window in the middle of your screen
    jframe.setVisible(true); //Make window visible
  }
}
