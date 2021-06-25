package com.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Render extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {

        if(o instanceof JButton){
            JButton button = (JButton) o;
            return button;
        }

        this.setHorizontalAlignment(JLabel.CENTER);
        return super.getTableCellRendererComponent(jTable, o, b, b1, i, i1);
    }
}
