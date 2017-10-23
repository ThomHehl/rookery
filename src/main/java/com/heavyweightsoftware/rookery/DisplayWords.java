package com.heavyweightsoftware.rookery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayWords {
    private JLabel lblTitle;
    private JTextArea textArea;
    protected JPanel panelMain;
    private JPanel panelText;

    private WordDisplay wordDisplay;

    public DisplayWords(WordDisplay wordDisplay) {
        this.wordDisplay = wordDisplay;

        setupText();
        setupTextArea();

        SwingUtilities.invokeLater(
        new Thread() {
            @Override
            public void run() {
                animateText();
            }
        });

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
        setupTitle();

        Component component;

        switch (wordDisplay.getAnimationType()) {
            case Reader:
                component = textArea;
                break;
            default:
                component = lblTitle;
                break;
        }

        for(KeyListener listener : panelMain.getKeyListeners()) {
            component.addKeyListener(listener);
        }
    }

    private void setupTitle() {
        lblTitle.setText(wordDisplay.getTitle());
        lblTitle.setBackground(wordDisplay.getTitleBackground());
        panelMain.setBackground(wordDisplay.getTitleBackground());
        lblTitle.setOpaque(true);
        lblTitle.setForeground(wordDisplay.getTitleForeground());

        Font titleFont = wordDisplay.getTitleFont();
        lblTitle.setFont(titleFont);
    }

    private void animateText() {
        switch (wordDisplay.getAnimationType()) {
            case Reader:
                animateReader();
                break;
            default:
                System.err.println("Error, unsupported animation type:" + wordDisplay.getAnimationType());
                break;
        }
    }

    private void animateReader() {
        new Thread() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();

                for(String str : wordDisplay.getTextArray()) {
                    sb.append(str);
                    textArea.setText(sb.toString());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        }.start();
    }

    private void setupTextArea() {
        textArea = new JTextArea();
        textArea.setFont(wordDisplay.getTextFont());
        textArea.setBackground(wordDisplay.getTextBackground());
        textArea.setForeground(wordDisplay.getTextForeground());
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        panelText.setLayout(new GridLayout());
        panelText.add(textArea);
    }
}
