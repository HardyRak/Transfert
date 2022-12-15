package client; 
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.*;
import java.lang.*;
import java.net.*;
public class CLientFrame extends JFrame{
    JScrollPane scrollPane;

    public CLientFrame() {
        JTextArea msg = new JTextArea(5,55);
        msg.setMargin(new Insets(5,5,5,5));
        msg.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(msg);

        JFileChooser choose = new JFileChooser();

        JButton openButton = new JButton("Choisir");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int Val = choose.showOpenDialog(CLientFrame.this);

                msg.append("Mes fichiers ...");

                if (Val == JFileChooser.APPROVE_OPTION) {
                    File file = choose.getSelectedFile();
                    msg.append(file.getName() + "\n");


                    MyClient client = new MyClient(file.getPath().toString(),file.getName());
                    if (client.verif())
                        msg.append("reussi" + "\n");
                    else
                        msg.append("echouer" + "\n");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.black);
        buttonPanel.add(openButton);

        Container contentPane = getContentPane();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints grid = new GridBagConstraints();
        contentPane.setLayout(gridbag);
        contentPane.setBackground(Color.pink);
//redimensionnement de l'affichage
       grid.fill = GridBagConstraints.VERTICAL;
       grid.weightx = 0.0;
       grid.gridx = 0;
       grid.gridy = 0;
        gridbag.setConstraints(buttonPanel, grid);
        contentPane.add(buttonPanel);

       grid.weightx = 0.0;
       grid.gridwidth = 60;
       grid.gridx = 0;
       grid.gridy = 1;
        gridbag.setConstraints(logScrollPane, grid);
        logScrollPane.setBorder(new LineBorder(Color.black));
        contentPane.add(logScrollPane);
    }

//partie main
    public static void main(String[] args) {
        JFrame frame = new CLientFrame();
        frame.pack();
        frame.setSize(500,200);
        frame.setVisible(true);
    }

}
