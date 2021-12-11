package com.minortechnologies.workr_frontend.framework.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ListingsPane {

    public static void main(String[] args) {
        new ListingsPane();
    }

    public ListingsPane() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new LPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class LPane extends JPanel {

        private final JPanel mainList;

        public LPane() {
            setLayout(new BorderLayout());

            mainList = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            mainList.add(new JPanel(), gbc);

            add(new JScrollPane(mainList));

            JButton add = new JButton("Add");
            add.addActionListener(e -> {
                JPanel panel = new JPanel();
                panel.add(new JLabel("Hello"));
                panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.gridwidth = GridBagConstraints.REMAINDER;
                gbc1.weightx = 1;
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                mainList.add(panel, gbc1, 0);

                validate();
                repaint();
            });

            add(add, BorderLayout.SOUTH);

        }

    }
}
