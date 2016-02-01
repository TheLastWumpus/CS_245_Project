package hangman;

import hangman.References.Names;
import hangman.Panels.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* Class Hangman holds the main class
* which initiates a Swing based game 
* of Java.
**/
public class Hangman {
    
    private JFrame mainFrame;
    private JPanel cards;
    private SplashPanel splashPanel;
    private MenuPanel menuPanel;
    private HighScorePanel highScorePanel;
    private GamePanel gamePanel;
    private EndPanel endPanel;
    private CreditsPanel creditsPanel;
    private CardLayout cardLayout;
    
    public Hangman(){
        initComponents();
        splashScreenTimer();
    }
    
    private void initComponents(){
        mainFrame = new JFrame();
        mainFrame.setSize(600,400);
        mainFrame.setMinimumSize(new Dimension(600,400));
        mainFrame.setMaximumSize(new Dimension(600,400));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cards = new JPanel(new CardLayout());
        splashPanel = new SplashPanel();
        menuPanel = new MenuPanel();
        creditsPanel = new CreditsPanel();
        highScorePanel = new HighScorePanel();
        endPanel = new EndPanel();
        gamePanel = new GamePanel(endPanel);
        
        
        cards.add(splashPanel, Names.SPLASH_PANEL);
        cards.add(menuPanel, Names.MENU_PANEL);
        cards.add(gamePanel, Names.GAME_PANEL);
        cards.add(highScorePanel, Names.HS_PANEL);
        cards.add(creditsPanel, Names.CREDITS_PANEL);
        cards.add(endPanel, Names.END_PANEL);
        cardLayout = (CardLayout)(cards.getLayout());
        cardLayout.show(cards, Names.SPLASH_PANEL);
        
        mainFrame.add(cards);
        mainFrame.setLocationRelativeTo(null);
        
        menuPanel.setCardLayout(cardLayout, cards);
        highScorePanel.setCardLayout(cardLayout, cards);
        gamePanel.setCardLayout(cardLayout, cards);
        endPanel.setCardLayout(cardLayout, cards);
        creditsPanel.setCardLayout(cardLayout, cards);
        
        mainFrame.pack();
        mainFrame.setVisible(true);
        
    }
    
    private void splashScreenTimer(){
        Timer splashTimer;
        ActionListener timerAL = new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                cardLayout.show(cards, Names.MENU_PANEL);
            }
        };
        splashTimer = new Timer(3000, timerAL);
        splashTimer.setRepeats(false);
        splashTimer.start();       
    }
    
    public static void main(String [] args){
        SwingUtilities.invokeLater(new Runnable(){
         public void run(){
             new Hangman();
         }
        });
    }
}
