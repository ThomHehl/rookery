package com.heavyweightsoftware.rookery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CollectData {
    private static String[] fontList;

    private JPanel panelMain;
    private JTextField textTitle;
    private JLabel lblTitle;
    private JLabel lblText;
    private JTextArea textAreaText;
    private JLabel lblTitleFont;
    private JComboBox comboTitleFont;
    private JLabel lblTitleSize;
    private JComboBox comboTitleSize;
    private JLabel lblTextFont;
    private JComboBox comboTextFont;
    private JLabel lblTextSize;
    private JComboBox comboTextSize;
    private JLabel lblTitleColor;
    private JLabel lblTitleBackground;
    private JLabel lblTextColor;
    private JLabel lblTextBackground;
    private JLabel lblTitleColorDemo;
    private JLabel lblTitleBackgroundDemo;
    private JButton buttonDisplay;
    private JButton buttonSave;
    private JLabel lblTextColorDemo;
    private JLabel lblTextBackgroundDemo;
    private JLabel lblAnimation;
    private JLabel lblDelay;
    private JComboBox comboAnimation;
    private JTextField textField1;
    private JButton buttonLoad;

    /**
     *
     */
    public CollectData() {
        super();

        setupFontLists();
        createButtonListeners();
    }

    private void createButtonListeners() {
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadButtonPressed();
            }
        });
        buttonDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayButtonPressed();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonPressed();
            }
        });
    }

    private void displayButtonPressed() {
    }

    private void loadButtonPressed() {
    }

    private void saveButtonPressed() {
    }

    private void setupFontLists() {
        //initialize the font lists
        for(String fontName : getFontList()) {
            comboTitleFont.addItem(fontName);
            comboTextFont.addItem(fontName);
        }
    }

    protected static synchronized String[] getFontList() {
        if(fontList == null) {
            fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        }

        return fontList;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(CollectData.class.getSimpleName());
        frame.setContentPane(new CollectData().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
