package com.heavyweightsoftware.rookery;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollectData {
    private static final String     NAME_TEXT_COLOR = "textColor";
    private static final String     NAME_TEXT_BG_COLOR = "textBackgroundColor";
    private static final String     NAME_TITLE_COLOR = "titleColor";
    private static final String     NAME_TITLE_BG_COLOR = "titleBackgroundColor";

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
    private JTextField textDelay;
    private JButton buttonLoad;

    /**
     *
     */
    public CollectData() {
        super();

        setupFontLists();
        setupNumberFilters();
        createButtonListeners();
        createColorListeners();
    }

    private void createColorListeners() {
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                JLabel label = (JLabel) event.getComponent();
                showColorChooser(label);
            }
        };

        lblTextColorDemo.setName(NAME_TEXT_COLOR);
        lblTextBackgroundDemo.setName(NAME_TEXT_BG_COLOR);
        lblTitleColorDemo.setName(NAME_TITLE_COLOR);
        lblTitleBackgroundDemo.setName(NAME_TITLE_BG_COLOR);

        lblTextColorDemo.addMouseListener(listener);
        lblTextBackgroundDemo.addMouseListener(listener);
        lblTitleColorDemo.addMouseListener(listener);
        lblTitleBackgroundDemo.addMouseListener(listener);
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

    private WordDisplay buildDisplayWords() {
        WordDisplay wordDisplay = new WordDisplay();

        String delay = textDelay.getText().trim();
        int val = Integer.parseInt(delay);
        wordDisplay.setAnimationSpeed(val);

        return wordDisplay;
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

    private void setupNumberFilters() {
        PlainDocument doc = (PlainDocument) textDelay.getDocument();
        doc.setDocumentFilter(new IntOnlyFilter());
    }

    private void showColorChooser(JLabel label) {
        final String labelName = label.getName();
        Color color = getLabelColor(label, labelName);

        ColorChooser chooser = new ColorChooser(color, new ColorChooser.OnColorChosen(){
            @Override
            public void colorChosen(Color color) {
                setLabelColor(labelName, color);
            }
        });

        chooser.setName(labelName);
        chooser.pack();
        chooser.setVisible(true);
    }

    private Color getLabelColor(JLabel label, String labelName) {
        Color result;

        switch (labelName) {
            case NAME_TEXT_COLOR:
            case NAME_TITLE_COLOR:
            case NAME_TEXT_BG_COLOR:
            case NAME_TITLE_BG_COLOR:
                result = label.getForeground();
                break;
            default:
                throw new IllegalArgumentException(labelName);
        }

        return result;
    }

    private void setLabelColor(String labelName, Color color) {
        switch (labelName) {
            case NAME_TEXT_COLOR:
                lblTextColorDemo.setForeground(color);
                break;
            case NAME_TITLE_COLOR:
                lblTitleColorDemo.setForeground(color);
                break;
            case NAME_TEXT_BG_COLOR:
                lblTextBackgroundDemo.setForeground(color);
                break;
            case NAME_TITLE_BG_COLOR:
                lblTitleBackgroundDemo.setForeground(color);
                break;
            default:
                throw new IllegalArgumentException(labelName);
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
