package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
  private JFrame jframe;

  public GameWindow(GamePanel gamePanel){
    jframe = new JFrame();

    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Shuts down when clicking Exit
    jframe.add(gamePanel); //add panel in window
    jframe.setResizable(false);
    jframe.pack();
    jframe.setLocationRelativeTo(null); //open window in the middle of your screen
    jframe.setVisible(true); //Make window visible
    jframe.addWindowFocusListener(new WindowFocusListener() {
      @Override
      public void windowGainedFocus(WindowEvent e) {
      }

      @Override
      public void windowLostFocus(WindowEvent e) {
        gamePanel.getGame().windowFocusLost();
      }
    });
  }
}
