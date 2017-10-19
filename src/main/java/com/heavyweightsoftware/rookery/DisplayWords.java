package com.heavyweightsoftware.rookery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DisplayWords {
    private JLabel lblTitle;
    private JTextArea textText;
    protected JPanel panelMain;

    private WordDisplay wordDisplay;

    public DisplayWords(WordDisplay wordDisplay) {
        this.wordDisplay = wordDisplay;

        setupText();

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(250);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                animateText();
            }
        }.start();

        setupClose();
    }

    private void setupClose() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                closeJframe();
            }
        };

        panelMain.addKeyListener(keyAdapter);
        lblTitle.addKeyListener(keyAdapter);
        textText.addKeyListener(keyAdapter);
    }

    private void closeJframe() {
        Component comp = panelMain.getParent();
        while (!(comp instanceof JFrame)) {
            comp = comp.getParent();
        }

        JFrame mainFrame = (JFrame) comp;
        mainFrame.dispose();
    }

    private void setupText() {
        lblTitle.setText(wordDisplay.getTitle());
        lblTitle.setBackground(wordDisplay.getTitleBackground());
        panelMain.setBackground(wordDisplay.getTitleBackground());
        lblTitle.setOpaque(true);
        lblTitle.setForeground(wordDisplay.getTitleForeground());

        Font titleFont = new Font(wordDisplay.getTitleFontName(), Font.PLAIN, wordDisplay.getTitleFontSize());
        lblTitle.setFont(titleFont);

        textText.setBackground(wordDisplay.getTextBackground());
        textText.setForeground(wordDisplay.getTextForeground());
        Font textFont = new Font(wordDisplay.getTextFontName(), Font.PLAIN, wordDisplay.getTextFontSize());
        textText.setFont(textFont);
    }

    private void animateText() {
        textText.setText(wordDisplay.getText());
    }
}
