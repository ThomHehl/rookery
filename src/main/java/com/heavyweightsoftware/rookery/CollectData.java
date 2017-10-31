package com.heavyweightsoftware.rookery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class CollectData {
    private static final String NAME_TEXT_COLOR = "textColor";
    private static final String NAME_TEXT_BG_COLOR = "textBackgroundColor";
    private static final String NAME_TITLE_COLOR = "titleColor";
    private static final String NAME_TITLE_BG_COLOR = "titleBackgroundColor";

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

    private ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     *
     */
    public CollectData() {
        super();

        setupAnimationTypes();
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

        buttonDisplay.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    displayButtonPressed();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

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

        setAnimationProperties(wordDisplay);
        setTextProperties(wordDisplay);
        setTitleProperties(wordDisplay);

        return wordDisplay;
    }

    public void setAnimationProperties(WordDisplay wordDisplay) {
        String animString = comboAnimation.getSelectedItem().toString();
        AnimationType animationType = AnimationType.valueOf(animString);
        wordDisplay.setAnimationType(animationType);

        String delay = textDelay.getText().trim();
        int val = Integer.parseInt(delay);
        wordDisplay.setAnimationSpeed(val);
    }

    public void setTextProperties(WordDisplay wordDisplay) {
        wordDisplay.setText(textAreaText.getText());
        wordDisplay.setTextBackground(lblTextBackgroundDemo.getForeground());
        wordDisplay.setTextFontName(comboTextFont.getSelectedItem().toString());
        wordDisplay.setTextFontSize(Integer.parseInt(comboTextSize.getSelectedItem().toString()));
        wordDisplay.setTextForeground(lblTextColorDemo.getForeground());
    }

    public void setTitleProperties(WordDisplay wordDisplay) {
        wordDisplay.setTitle(textTitle.getText());
        wordDisplay.setTitleBackground(lblTitleBackgroundDemo.getForeground());
        wordDisplay.setTitleFontName(comboTitleFont.getSelectedItem().toString());
        wordDisplay.setTitleFontSize(Integer.parseInt(comboTitleSize.getSelectedItem().toString()));
        wordDisplay.setTitleForeground(lblTitleColorDemo.getForeground());
    }

    private void displayButtonPressed() {
        WordDisplay wordDisplay = buildDisplayWords();

        JFrame frame = new JFrame(DisplayWords.class.getSimpleName());
        frame.setContentPane(new DisplayWords(wordDisplay).panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadButtonPressed() {
        final JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Rookery files", "rky");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            WordDisplay wordDisplay;
            try {
                wordDisplay = objectMapper.readValue(file, WordDisplay.class);
            } catch (IOException ioe) {
                throw new RuntimeException("Error reading from file:" + file.getAbsolutePath(), ioe);
            }

            displayWordDisplay(wordDisplay);
        }
    }

    private void saveButtonPressed() {
        final JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Rookery files", "rky");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getAbsolutePath().toLowerCase().endsWith(".rky")) {
                file = new File(file.getAbsolutePath() + ".rky");
            }

            WordDisplay wordDisplay = buildDisplayWords();
            try {
                objectMapper.writeValue(file, wordDisplay);
            } catch (IOException ioe) {
                throw new RuntimeException("Error writing to file:" + file.getAbsolutePath(), ioe);
            }
        }
    }

    private void setupAnimationTypes() {
        for (AnimationType animType : AnimationType.values()) {
            String str = animType.toString();
            comboAnimation.addItem(str);
        }
        comboAnimation.setSelectedIndex(0);
    }

    private void setupFontLists() {
        //initialize the font lists
        for (String fontName : getFontList()) {
            comboTextFont.addItem(fontName);
            comboTitleFont.addItem(fontName);
        }

        for (int ix = 10; ix < 65; ix += 2) {
            String str = Integer.toString(ix);
            comboTextSize.addItem(str);
            comboTitleSize.addItem(str);
        }
    }

    private void setupNumberFilters() {
        PlainDocument doc = (PlainDocument) textDelay.getDocument();
        doc.setDocumentFilter(new IntOnlyFilter());
    }

    private void showColorChooser(JLabel label) {
        final String labelName = label.getName();
        Color color = getLabelColor(label, labelName);

        ColorChooser chooser = new ColorChooser(color, new ColorChooser.OnColorChosen() {
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

    private void displayWordDisplay(WordDisplay wordDisplay) {
        comboAnimation.setSelectedItem(wordDisplay.getAnimationType().toString());
        textDelay.setText(Integer.toString(wordDisplay.getAnimationSpeed()));

        textAreaText.setText(wordDisplay.getText());
        lblTextBackgroundDemo.setForeground(wordDisplay.getTextBackground());
        comboTextFont.setSelectedItem(wordDisplay.getTextFontName());
        comboTextSize.setSelectedItem(Integer.toString(wordDisplay.getTextFontSize()));
        lblTextColorDemo.setForeground(wordDisplay.getTextForeground());

        textTitle.setText(wordDisplay.getTitle());
        lblTitleBackgroundDemo.setForeground(wordDisplay.getTitleBackground());
        comboTitleFont.setSelectedItem(wordDisplay.getTitleFontName());
        comboTitleSize.setSelectedItem(Integer.toString(wordDisplay.getTitleFontSize()));
        lblTitleColorDemo.setForeground(wordDisplay.getTitleForeground());
    }

    protected static synchronized String[] getFontList() {
        if (fontList == null) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(13, 2, new Insets(10, 10, 10, 10), -1, -1));
        lblTitle = new JLabel();
        lblTitle.setText("Title");
        panelMain.add(lblTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textTitle = new JTextField();
        panelMain.add(textTitle, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblText = new JLabel();
        lblText.setText("Text");
        panelMain.add(lblText, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textAreaText = new JTextArea();
        textAreaText.setLineWrap(true);
        panelMain.add(textAreaText, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        lblTitleFont = new JLabel();
        lblTitleFont.setText("Title Font");
        panelMain.add(lblTitleFont, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboTitleFont = new JComboBox();
        panelMain.add(comboTitleFont, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTitleSize = new JLabel();
        lblTitleSize.setText("Title Size");
        panelMain.add(lblTitleSize, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboTitleSize = new JComboBox();
        panelMain.add(comboTitleSize, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTextFont = new JLabel();
        lblTextFont.setText("Text Font");
        panelMain.add(lblTextFont, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboTextFont = new JComboBox();
        panelMain.add(comboTextFont, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTextSize = new JLabel();
        lblTextSize.setText("Text Size");
        panelMain.add(lblTextSize, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboTextSize = new JComboBox();
        panelMain.add(comboTextSize, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTitleColor = new JLabel();
        lblTitleColor.setText("Title Color");
        panelMain.add(lblTitleColor, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTitleBackground = new JLabel();
        lblTitleBackground.setText("Title Background");
        panelMain.add(lblTitleBackground, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTextColor = new JLabel();
        lblTextColor.setText("Text Color");
        panelMain.add(lblTextColor, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTextBackground = new JLabel();
        lblTextBackground.setText("Text Background");
        panelMain.add(lblTextBackground, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTitleColorDemo = new JLabel();
        lblTitleColorDemo.setForeground(new Color(-256));
        lblTitleColorDemo.setText("Title Color");
        panelMain.add(lblTitleColorDemo, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTitleBackgroundDemo = new JLabel();
        lblTitleBackgroundDemo.setForeground(new Color(-16776961));
        lblTitleBackgroundDemo.setText("Title Background");
        panelMain.add(lblTitleBackgroundDemo, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonDisplay = new JButton();
        buttonDisplay.setText("Display");
        panel1.add(buttonDisplay, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSave = new JButton();
        buttonSave.setText("Save");
        panel1.add(buttonSave, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonLoad = new JButton();
        buttonLoad.setText("Load");
        panel1.add(buttonLoad, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTextColorDemo = new JLabel();
        lblTextColorDemo.setForeground(new Color(-256));
        lblTextColorDemo.setText("Text Color");
        panelMain.add(lblTextColorDemo, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTextBackgroundDemo = new JLabel();
        lblTextBackgroundDemo.setForeground(new Color(-16776961));
        lblTextBackgroundDemo.setText("Text Background");
        panelMain.add(lblTextBackgroundDemo, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblAnimation = new JLabel();
        lblAnimation.setText("Animation");
        panelMain.add(lblAnimation, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblDelay = new JLabel();
        lblDelay.setText("Delay");
        panelMain.add(lblDelay, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboAnimation = new JComboBox();
        panelMain.add(comboAnimation, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textDelay = new JTextField();
        textDelay.setText("200");
        panelMain.add(textDelay, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
