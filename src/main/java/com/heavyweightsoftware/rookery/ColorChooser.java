package com.heavyweightsoftware.rookery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ColorChooser extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel panelMain;

    private JColorChooser colorChooser;
    private OnColorChosen onColorChosen;

    public ColorChooser(Color color, OnColorChosen onColorChosen) {
        this.onColorChosen = onColorChosen;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setupButtons();
        setupColorChooser(color);
    }

    private void setupColorChooser(Color color) {
        colorChooser = new JColorChooser(color);
        new Thread() {
            @Override
            public void run() {
                panelMain.add(colorChooser);
            }
        }.start();
    }

    private void setupButtons() {
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        onColorChosen.colorChosen(colorChooser.getColor());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public abstract static class OnColorChosen {
        public abstract void colorChosen(Color color);
    }
}
