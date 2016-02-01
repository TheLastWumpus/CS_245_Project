package hangman.Panels;
import hangman.References.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Class GamePanel creates the game screen
 * for a game of hangman. The game screen 
 * is where the game is actually played. 
 * It offers the user with buttons for each
 * letter in the alphabet. The user can use 
 * these buttons to select letters they think
 * are in a given word. 
 * 
 * The GamePanel class keeps track of the game
 * state and ends the game when the user runs
 * out of guesses or if they guess they word. 
 * 
 */
public class GamePanel extends javax.swing.JPanel 
                        implements ActionListener{
    private int score;
    private int curState;
    private String word;
    private boolean gameOver;
 
    private JPanel deck;
    private JPanel[] states;
    private JLabel[] wordSpace;
    private JButton[] buttons;
    private WordsList wordList;
    private CardLayout cardLayout;
    private EndPanel gameOverPanel;
    private final int END_STATE = 7;
    /**
     * Creates new form GamePanel
     */
    public GamePanel(EndPanel endPanel) {
        score = 100;
        curState = 1;
        gameOver = false;
            
        wordList = new WordsList();
        word = wordList.getRandomWord();
        states = new JPanel[END_STATE];
        buttons = new JButton[26];
        gameOverPanel = endPanel;
        
        initComponents();
        initStates();
        initWordSpace();
        initButtonActionListeners();
        
        scoreLabel.setText("SCORE: " + Integer.toString(score));
        
        systemTime();
    }
    
    public void actionPerformed(ActionEvent ae){
       
        removeActionListener(ae.getActionCommand());
        System.out.println("Action Command: " +  ae.getActionCommand());
        if((!word.contains(ae.getActionCommand().toLowerCase())) && curState < END_STATE){
            score = score - 10;
            scoreLabel.setText("SCORE: " + Integer.toString(score));
            gameOverPanel.getScoreLabel().setText(Integer.toString(score));
            hangmanPanel.removeAll();
            hangmanPanel.repaint();
            hangmanPanel.revalidate();
        
            hangmanPanel.add(states[curState++]);
            hangmanPanel.repaint();
            hangmanPanel.revalidate();
        }
        else{
            
            for(int i = 0; i < word.length(); i++){
                if(word.charAt(i) == ae.getActionCommand().toLowerCase().charAt(0)){
                    System.out.println("Setting text");
                    wordSpace[i].setText("" + word.charAt(i));
                    System.out.println("Text set");
                }
            }
            System.out.println("Repaint");
            wordPanel.repaint();
            wordPanel.revalidate();
            System.out.println("revalidated");
        }
        for(int i = 0; i < word.length(); i++){
            if(wordSpace[i].getText().equals("-")){
                break;
            }
            if(i == word.length() - 1){
                gameOver = true;
                System.out.println("Gameover true");
                reset();
                cardLayout.show(deck, Names.END_PANEL);
                break;
            }
        }
        if(curState == END_STATE){
            gameOver = true;
            gameOverPanel.getWordLabel().setText("WORD: " +  word);
            gameOverPanel.repaint();
            gameOverPanel.revalidate();
            reset();
            cardLayout.show(deck, Names.END_PANEL);
        }       
    }
    
    public void setCardLayout(CardLayout cLayout, JPanel cards){
        cardLayout = cLayout;
        deck = cards;
    }
    
    private void systemTime(){
        Timer seconds;
        DateFormat dateFormat = new SimpleDateFormat(" dd, yyyy      HH:mm:ss");
        ActionListener secondsAL = new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               Calendar calendar = Calendar.getInstance();
               String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
               currentTime.setText(month + dateFormat.format(calendar.getTime()));
               
               timePanel.repaint();
               timePanel.revalidate();
            }
        };
        seconds = new Timer(1000, secondsAL);
        seconds.setRepeats(true);
        seconds.start();       
    }
    
    private void newWord(){
        String oldWord = word;
        do{
            word = wordList.getRandomWord();
        }
        while(word.equals(oldWord));
    }
    
    private void removeActionListener(String s){
        for(int i = 0; i < buttons.length; i++){
            if(buttons[i].getActionCommand().equals(s)){
                buttons[i].removeActionListener(this);
            }
        }      
    }
    
    private void reset(){
        System.out.println("Resetting");
        curState = 1;
        score = 100;
        scoreLabel.setText("SCORE: " + Integer.toString(score));
        gameOver = false;
        newWord();
        initWordSpace();
        removeButtonActionListeners();
        initButtonActionListeners();
        for(int i = 0; i < wordSpace.length; i++){
            wordSpace[i].setText("-");        
        }
        hangmanPanel.removeAll();
        hangmanPanel.repaint();
        hangmanPanel.revalidate();
        
        hangmanPanel.add(states[0]);
        hangmanPanel.repaint();
        hangmanPanel.revalidate();
        System.out.println("Done resetting");
    }    
    
    private void initStates(){
        states[0] = state0;
        states[1] = state1;
        states[2] = state2;
        states[3] = state3;
        states[4] = state4;
        states[5] = state5;
        states[6] = state6;
    }
    
    private void initWordSpace(){
        if(word.length() == 5){
            
            wordPanel.removeAll();
            wordPanel.repaint();
            wordPanel.revalidate();
        
            wordPanel.add(fiveWordPanel);
            wordPanel.repaint();
            wordPanel.revalidate();
            
            wordSpace = new JLabel[5];
            wordSpace[0] = letter05;
            wordSpace[1] = letter15;
            wordSpace[2] = letter25;
            wordSpace[3] = letter35;
            wordSpace[4]= letter45; 
        }
        if(word.length() == 8){
            
            wordPanel.removeAll();
            wordPanel.repaint();
            wordPanel.revalidate();
        
            wordPanel.add(eightWordPanel);
            wordPanel.repaint();
            wordPanel.revalidate();
            
            wordSpace = new JLabel[8];
            wordSpace[0] = letter08;
            wordSpace[1] = letter18;
            wordSpace[2] = letter28;
            wordSpace[3] = letter38;
            wordSpace[4]= letter48;
            wordSpace[5] = letter58;
            wordSpace[6] = letter68;
            wordSpace[7] = letter78;
        }
    }    
    
    private void initButtonActionListeners(){
        buttons[0] = aButton;
        buttons[1] = bButton;
        buttons[2] = cButton;
        buttons[3] = dButton;
        buttons[4] = eButton;
        buttons[5] = fButton;
        buttons[6] = gButton;
        buttons[7] = hButton;
        buttons[8] = iButton;
        buttons[9] = jButton;
        buttons[10] = kButton;
        buttons[11] = lButton;
        buttons[12] = mButton;
        buttons[13] = nButton;
        buttons[14] = oButton;
        buttons[15] = pButton;
        buttons[16] = qButton;
        buttons[17] = rButton;
        buttons[18] = sButton;
        buttons[19] = tButton;
        buttons[20] = uButton;
        buttons[21] = vButton;
        buttons[22] = wButton;
        buttons[23] = xButton;
        buttons[24] = yButton;
        buttons[25] = zButton;
         
        aButton.addActionListener(this);
        bButton.addActionListener(this);
        cButton.addActionListener(this);
        dButton.addActionListener(this);
        eButton.addActionListener(this);
        fButton.addActionListener(this);
        gButton.addActionListener(this);
        hButton.addActionListener(this);
        iButton.addActionListener(this);
        jButton.addActionListener(this);
        kButton.addActionListener(this);
        lButton.addActionListener(this);
        mButton.addActionListener(this);
        nButton.addActionListener(this);
        oButton.addActionListener(this);
        pButton.addActionListener(this);
        qButton.addActionListener(this);
        rButton.addActionListener(this);
        sButton.addActionListener(this);
        tButton.addActionListener(this);
        uButton.addActionListener(this);
        vButton.addActionListener(this);
        wButton.addActionListener(this);
        xButton.addActionListener(this);
        yButton.addActionListener(this);
        zButton.addActionListener(this);
        
        skipButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                score = 0;
                gameOverPanel.getScoreLabel().setText(Integer.toBinaryString(score));
                gameOverPanel.repaint();
                gameOverPanel.revalidate();
                cardLayout.show(deck, Names.END_PANEL);
                reset();
            }
        });
    }

    private void removeButtonActionListeners(){
        aButton.removeActionListener(this);
        bButton.removeActionListener(this);
        cButton.removeActionListener(this);
        dButton.removeActionListener(this);
        eButton.removeActionListener(this);
        fButton.removeActionListener(this);
        gButton.removeActionListener(this);
        hButton.removeActionListener(this);
        iButton.removeActionListener(this);
        jButton.removeActionListener(this);
        kButton.removeActionListener(this);
        lButton.removeActionListener(this);
        mButton.removeActionListener(this);
        nButton.removeActionListener(this);
        oButton.removeActionListener(this);
        pButton.removeActionListener(this);
        qButton.removeActionListener(this);
        rButton.removeActionListener(this);
        sButton.removeActionListener(this);
        tButton.removeActionListener(this);
        uButton.removeActionListener(this);
        vButton.removeActionListener(this);
        wButton.removeActionListener(this);
        xButton.removeActionListener(this);
        yButton.removeActionListener(this);
        zButton.removeActionListener(this);
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton14 = new javax.swing.JButton();
        skipButton = new javax.swing.JButton();
        bButton = new javax.swing.JButton();
        cButton = new javax.swing.JButton();
        dButton = new javax.swing.JButton();
        eButton = new javax.swing.JButton();
        fButton = new javax.swing.JButton();
        hButton = new javax.swing.JButton();
        gButton = new javax.swing.JButton();
        iButton = new javax.swing.JButton();
        jButton = new javax.swing.JButton();
        kButton = new javax.swing.JButton();
        lButton = new javax.swing.JButton();
        xButton = new javax.swing.JButton();
        wButton = new javax.swing.JButton();
        vButton = new javax.swing.JButton();
        uButton = new javax.swing.JButton();
        tButton = new javax.swing.JButton();
        sButton = new javax.swing.JButton();
        rButton = new javax.swing.JButton();
        qButton = new javax.swing.JButton();
        pButton = new javax.swing.JButton();
        oButton = new javax.swing.JButton();
        nButton = new javax.swing.JButton();
        mButton = new javax.swing.JButton();
        yButton = new javax.swing.JButton();
        zButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        hangmanPanel = new javax.swing.JPanel();
        state0 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        state1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        state2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        state3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        state4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        state5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        state6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        aButton = new javax.swing.JButton();
        timePanel = new javax.swing.JPanel();
        currentTime = new javax.swing.JLabel();
        wordPanel = new javax.swing.JPanel();
        fiveWordPanel = new javax.swing.JPanel();
        letter05 = new javax.swing.JLabel();
        letter15 = new javax.swing.JLabel();
        letter25 = new javax.swing.JLabel();
        letter35 = new javax.swing.JLabel();
        letter45 = new javax.swing.JLabel();
        eightWordPanel = new javax.swing.JPanel();
        letter08 = new javax.swing.JLabel();
        letter18 = new javax.swing.JLabel();
        letter28 = new javax.swing.JLabel();
        letter38 = new javax.swing.JLabel();
        letter58 = new javax.swing.JLabel();
        letter48 = new javax.swing.JLabel();
        letter78 = new javax.swing.JLabel();
        letter68 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();

        jButton14.setText("jButton14");

        setBackground(new java.awt.Color(153, 0, 0));
        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setPreferredSize(new java.awt.Dimension(600, 400));

        skipButton.setBackground(new java.awt.Color(0, 0, 0));
        skipButton.setForeground(new java.awt.Color(255, 255, 255));
        skipButton.setText("SKIP");

        bButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        bButton.setText("B");

        cButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cButton.setText("C");

        dButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        dButton.setText("D");

        eButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        eButton.setText("E");

        fButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        fButton.setText("F");

        hButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hButton.setText("H");

        gButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        gButton.setText("G");

        iButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        iButton.setText("I");

        jButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton.setText("J");

        kButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        kButton.setText("K");

        lButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lButton.setText("L");

        xButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        xButton.setText("X");

        wButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        wButton.setText("W");

        vButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        vButton.setText("V");

        uButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        uButton.setText("U");

        tButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tButton.setText("T");

        sButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        sButton.setText("S");

        rButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        rButton.setText("R");

        qButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        qButton.setText("Q");
        qButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qButtonActionPerformed(evt);
            }
        });

        pButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        pButton.setText("P");

        oButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        oButton.setText("O");

        nButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        nButton.setText("N");

        mButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mButton.setText("M");

        yButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        yButton.setText("Y");

        zButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zButton.setText("Z");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/leftPole.png"))); // NOI18N

        hangmanPanel.setBackground(new java.awt.Color(204, 0, 0));
        hangmanPanel.setLayout(new java.awt.CardLayout());

        state0.setBackground(new java.awt.Color(153, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState0.png"))); // NOI18N

        javax.swing.GroupLayout state0Layout = new javax.swing.GroupLayout(state0);
        state0.setLayout(state0Layout);
        state0Layout.setHorizontalGroup(
            state0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state0Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state0Layout.setVerticalGroup(
            state0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state0Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state0, "card2");

        state1.setBackground(new java.awt.Color(153, 0, 0));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState1.png"))); // NOI18N

        javax.swing.GroupLayout state1Layout = new javax.swing.GroupLayout(state1);
        state1.setLayout(state1Layout);
        state1Layout.setHorizontalGroup(
            state1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state1Layout.setVerticalGroup(
            state1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, state1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state1, "card3");

        state2.setBackground(new java.awt.Color(153, 0, 0));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState2.png"))); // NOI18N

        javax.swing.GroupLayout state2Layout = new javax.swing.GroupLayout(state2);
        state2.setLayout(state2Layout);
        state2Layout.setHorizontalGroup(
            state2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state2Layout.setVerticalGroup(
            state2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state2, "card4");

        state3.setBackground(new java.awt.Color(153, 0, 0));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState3.png"))); // NOI18N

        javax.swing.GroupLayout state3Layout = new javax.swing.GroupLayout(state3);
        state3.setLayout(state3Layout);
        state3Layout.setHorizontalGroup(
            state3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state3Layout.setVerticalGroup(
            state3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, state3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state3, "card5");

        state4.setBackground(new java.awt.Color(153, 0, 0));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState4.png"))); // NOI18N

        javax.swing.GroupLayout state4Layout = new javax.swing.GroupLayout(state4);
        state4.setLayout(state4Layout);
        state4Layout.setHorizontalGroup(
            state4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state4Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state4Layout.setVerticalGroup(
            state4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state4Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state4, "card6");

        state5.setBackground(new java.awt.Color(153, 0, 0));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState5.png"))); // NOI18N

        javax.swing.GroupLayout state5Layout = new javax.swing.GroupLayout(state5);
        state5.setLayout(state5Layout);
        state5Layout.setHorizontalGroup(
            state5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state5Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state5Layout.setVerticalGroup(
            state5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state5Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state5, "card7");

        state6.setBackground(new java.awt.Color(153, 0, 0));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/hangmanState6.png"))); // NOI18N

        javax.swing.GroupLayout state6Layout = new javax.swing.GroupLayout(state6);
        state6.setLayout(state6Layout);
        state6Layout.setHorizontalGroup(
            state6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state6Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        state6Layout.setVerticalGroup(
            state6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(state6Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        hangmanPanel.add(state6, "card8");

        aButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        aButton.setText("A");

        currentTime.setText("time");

        javax.swing.GroupLayout timePanelLayout = new javax.swing.GroupLayout(timePanel);
        timePanel.setLayout(timePanelLayout);
        timePanelLayout.setHorizontalGroup(
            timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timePanelLayout.createSequentialGroup()
                .addComponent(currentTime, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        timePanelLayout.setVerticalGroup(
            timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(currentTime))
        );

        wordPanel.setLayout(new java.awt.CardLayout());

        fiveWordPanel.setBackground(new java.awt.Color(102, 102, 102));
        fiveWordPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fiveWordPanel.setPreferredSize(new java.awt.Dimension(541, 69));

        letter05.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter05.setText("-");

        letter15.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter15.setText("-");

        letter25.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter25.setText("-");

        letter35.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter35.setText("-");

        letter45.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter45.setText("-");

        javax.swing.GroupLayout fiveWordPanelLayout = new javax.swing.GroupLayout(fiveWordPanel);
        fiveWordPanel.setLayout(fiveWordPanelLayout);
        fiveWordPanelLayout.setHorizontalGroup(
            fiveWordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fiveWordPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(letter05)
                .addGap(84, 84, 84)
                .addComponent(letter15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(letter25)
                .addGap(95, 95, 95)
                .addComponent(letter35)
                .addGap(97, 97, 97)
                .addComponent(letter45)
                .addGap(66, 66, 66))
        );
        fiveWordPanelLayout.setVerticalGroup(
            fiveWordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fiveWordPanelLayout.createSequentialGroup()
                .addGroup(fiveWordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(letter45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letter35)
                    .addComponent(letter25)
                    .addComponent(letter15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letter05))
                .addContainerGap())
        );

        wordPanel.add(fiveWordPanel, "card2");

        eightWordPanel.setBackground(new java.awt.Color(102, 102, 102));
        eightWordPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        eightWordPanel.setPreferredSize(new java.awt.Dimension(541, 69));

        letter08.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter08.setText("-");

        letter18.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter18.setText("-");

        letter28.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter28.setText("-");

        letter38.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter38.setText("-");

        letter58.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter58.setText("-");

        letter48.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter48.setText("-");

        letter78.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter78.setText("-");

        letter68.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        letter68.setText("-");

        javax.swing.GroupLayout eightWordPanelLayout = new javax.swing.GroupLayout(eightWordPanel);
        eightWordPanel.setLayout(eightWordPanelLayout);
        eightWordPanelLayout.setHorizontalGroup(
            eightWordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eightWordPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(letter08)
                .addGap(47, 47, 47)
                .addComponent(letter18)
                .addGap(53, 53, 53)
                .addComponent(letter28)
                .addGap(56, 56, 56)
                .addComponent(letter38)
                .addGap(52, 52, 52)
                .addComponent(letter48)
                .addGap(55, 55, 55)
                .addComponent(letter58)
                .addGap(48, 48, 48)
                .addComponent(letter68)
                .addGap(53, 53, 53)
                .addComponent(letter78)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        eightWordPanelLayout.setVerticalGroup(
            eightWordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eightWordPanelLayout.createSequentialGroup()
                .addGroup(eightWordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(letter48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letter58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letter68)
                    .addComponent(letter78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letter38)
                    .addComponent(letter28)
                    .addComponent(letter18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(letter08))
                .addContainerGap())
        );

        wordPanel.add(eightWordPanel, "card2");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hangman/References/Hangman_Female.png"))); // NOI18N

        scoreLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        scoreLabel.setText("SCORE: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(hangmanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scoreLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(vButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(wButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(xButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(yButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(zButton)
                                    .addGap(91, 91, 91)
                                    .addComponent(skipButton))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(aButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(mButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(nButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(oButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(pButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(qButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(sButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(uButton))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(bButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(dButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(eButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(fButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(gButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(hButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(iButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(kButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lButton))))))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scoreLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addComponent(hangmanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(wordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bButton)
                    .addComponent(cButton)
                    .addComponent(dButton)
                    .addComponent(eButton)
                    .addComponent(fButton)
                    .addComponent(gButton)
                    .addComponent(hButton)
                    .addComponent(iButton)
                    .addComponent(jButton)
                    .addComponent(kButton)
                    .addComponent(lButton)
                    .addComponent(aButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mButton)
                    .addComponent(nButton)
                    .addComponent(oButton)
                    .addComponent(pButton)
                    .addComponent(qButton)
                    .addComponent(rButton)
                    .addComponent(sButton)
                    .addComponent(tButton)
                    .addComponent(uButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yButton)
                    .addComponent(zButton)
                    .addComponent(xButton)
                    .addComponent(wButton)
                    .addComponent(vButton)
                    .addComponent(skipButton))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void qButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aButton;
    private javax.swing.JButton bButton;
    private javax.swing.JButton cButton;
    private javax.swing.JLabel currentTime;
    private javax.swing.JButton dButton;
    private javax.swing.JButton eButton;
    private javax.swing.JPanel eightWordPanel;
    private javax.swing.JButton fButton;
    private javax.swing.JPanel fiveWordPanel;
    private javax.swing.JButton gButton;
    private javax.swing.JButton hButton;
    private javax.swing.JPanel hangmanPanel;
    private javax.swing.JButton iButton;
    private javax.swing.JButton jButton;
    private javax.swing.JButton jButton14;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton kButton;
    private javax.swing.JButton lButton;
    private javax.swing.JLabel letter05;
    private javax.swing.JLabel letter08;
    private javax.swing.JLabel letter15;
    private javax.swing.JLabel letter18;
    private javax.swing.JLabel letter25;
    private javax.swing.JLabel letter28;
    private javax.swing.JLabel letter35;
    private javax.swing.JLabel letter38;
    private javax.swing.JLabel letter45;
    private javax.swing.JLabel letter48;
    private javax.swing.JLabel letter58;
    private javax.swing.JLabel letter68;
    private javax.swing.JLabel letter78;
    private javax.swing.JButton mButton;
    private javax.swing.JButton nButton;
    private javax.swing.JButton oButton;
    private javax.swing.JButton pButton;
    private javax.swing.JButton qButton;
    private javax.swing.JButton rButton;
    private javax.swing.JButton sButton;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton skipButton;
    private javax.swing.JPanel state0;
    private javax.swing.JPanel state1;
    private javax.swing.JPanel state2;
    private javax.swing.JPanel state3;
    private javax.swing.JPanel state4;
    private javax.swing.JPanel state5;
    private javax.swing.JPanel state6;
    private javax.swing.JButton tButton;
    private javax.swing.JPanel timePanel;
    private javax.swing.JButton uButton;
    private javax.swing.JButton vButton;
    private javax.swing.JButton wButton;
    private javax.swing.JPanel wordPanel;
    private javax.swing.JButton xButton;
    private javax.swing.JButton yButton;
    private javax.swing.JButton zButton;
    // End of variables declaration//GEN-END:variables
}
