package com.heavyweightsoftware.rookery;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

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

        for (KeyListener listener : panelMain.getKeyListeners()) {
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

                for (String str : wordDisplay.getTextArray()) {
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
        panelMain.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        lblTitle = new JLabel();
        lblTitle.setText("");
        panelMain.add(lblTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelText = new JPanel();
        panelText.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelText, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
