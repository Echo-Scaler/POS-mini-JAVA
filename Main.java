package com.pos;

import com.pos.ui.POSForm;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // Always start Swing UI inside this
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new POSForm();
            }
        });

    }
}