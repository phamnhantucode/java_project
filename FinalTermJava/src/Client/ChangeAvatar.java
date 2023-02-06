/*
 * Created by JFormDesigner on Sun Jul 03 16:16:26 ICT 2022
 */

package Client;

//import Server.ClientCon;
//import org.apache.commons.io.FilenameUtils;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class ChangeAvatar extends JFrame {
    Avatar selected;

    ArrayList<Avatar> avatars = new ArrayList<>();

    public ChangeAvatar() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        initComponents();
    }

    private void uploadBtn(ActionEvent e) {
        // TODO add your code here
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            Avatar avatar = new Avatar(selectedFile.getAbsolutePath(), selectedFile.getName());
            avatar.setIconAsP();
            avatars.add(avatar);
            panel1.add(avatar);
            avatar.setBounds(0, 0, 300, 330);
            panel1.repaint();
        }


    }

    private void OKBtn(ActionEvent e) {
        // TODO add your code here
        try {
            File f = new File(selected.path);
            BufferedImage bufferedImage = ImageIO.read(f);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String s = "png";
            if (f.getName().lastIndexOf('.') != 0) {
                s = f.getName().substring(f.getName().lastIndexOf('.') + 1);
            };
//            System.out.println(f.length());
            ImageIO.write(bufferedImage, s, byteArrayOutputStream);
            ClientView.getInstance().serverCon.service.sendImageAvatar(byteArrayOutputStream);
            this.dispose();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        OKBtn = new JButton();
        cancelBtn = new JButton();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();
        uploadBtn = new JButton();

        //======== this ========
        setAlwaysOnTop(true);
        setTitle("Avatar");
        setIconImage(new ImageIcon(getClass().getResource("/user-icon.png")).getImage());
        setBackground(Color.white);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- OKBtn ----
        OKBtn.setText("OK");
        OKBtn.addActionListener(e -> OKBtn(e));
        contentPane.add(OKBtn);
        OKBtn.setBounds(new Rectangle(new Point(185, 365), OKBtn.getPreferredSize()));

        //---- cancelBtn ----
        cancelBtn.setText("Cancel");
        contentPane.add(cancelBtn);
        cancelBtn.setBounds(new Rectangle(new Point(290, 365), cancelBtn.getPreferredSize()));

        //======== scrollPane1 ========
        {
            scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane1.setBorder(null);

            //======== panel1 ========
            {
                panel1.setBackground(Color.white);
                panel1.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            scrollPane1.setViewportView(panel1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 380, 355);

        //---- uploadBtn ----
        uploadBtn.setText("Upload");
        uploadBtn.addActionListener(e -> uploadBtn(e));
        contentPane.add(uploadBtn);
        uploadBtn.setBounds(new Rectangle(new Point(85, 365), uploadBtn.getPreferredSize()));

        contentPane.setPreferredSize(new Dimension(380, 420));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton OKBtn;
    private JButton cancelBtn;
    private JScrollPane scrollPane1;
    private JPanel panel1;
    private JButton uploadBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    class Avatar extends JLabel {
        String path;
        String name;
        public Avatar(String path, String name) {
            this.setText(name);
            this.path = path;
            this.setVerticalTextPosition(SwingConstants.BOTTOM);
            this.setHorizontalTextPosition(SwingConstants.CENTER);
            this.setBorder(new EtchedBorder());
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ActionmouseClicked(e);
                }
            });
        }

        private void ActionmouseClicked(MouseEvent e) {
            setBorder(new EtchedBorder(new Color(0, 171, 255), null));
            if (selected != null) {
                selected.setBorder(new EtchedBorder());
            }
            selected = this;
        }

        public void setIconAsP() {
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage();
            this.setIcon(new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
        }
    }
}
