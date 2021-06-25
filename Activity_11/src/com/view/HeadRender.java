package com.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class HeadRender implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean b, boolean b1, int i, int i1) {
        JLabel jcomponent = null;

        if(value instanceof String) {
            jcomponent = new JLabel((String) value);
            jcomponent.setHorizontalAlignment(SwingConstants.CENTER);
            jcomponent.setSize(30, jcomponent.getWidth());
            jcomponent.setPreferredSize(new Dimension(6, jcomponent.getWidth()));
        }

        assert jcomponent != null;
        jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
        jcomponent.setOpaque(true);
        jcomponent.setBackground(Color.black);
        jcomponent.setForeground(Color.white);

        return jcomponent;
    }

}
