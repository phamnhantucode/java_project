/*
 * Created by JFormDesigner on Wed Jun 08 09:04:19 ICT 2022
 */

package Client;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class LoadPanel extends JPanel {
    public final static LoadPanel instance = new LoadPanel();
    public LoadPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();

        //======== this ========
        setBackground(Color.black);
        setLayout(null);

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/load.gif")));
        add(label1);
        label1.setBounds(new Rectangle(new Point(55, 120), label1.getPreferredSize()));

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
