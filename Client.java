package Networking.Chat_App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Freame_2 implements ActionListener {

    JTextField textField;
    JPanel a1;
    Box vertical = Box.createVerticalBox();
    JFrame frame = new JFrame();
    DataOutputStream dataOutputStream;

    Freame_2() {
        frame.setSize(380, 580);
        frame.setLocation(800, 50);

        frame.getContentPane().setBackground(Color.white); // set Frame background (black)

        frame.setLayout(null);

        JPanel panel = new JPanel();

        // assign color to panel
        panel.setBackground(new Color(7, 94, 84));

        // set size of panel
        panel.setBounds(0, 0, 400, 60);
        frame.add(panel);
        panel.setLayout(null);

        // Set Arrow image to panel
        ImageIcon Arrow_Image = new ImageIcon(ClassLoader
                .getSystemResource("Networking\\Chat_App\\arrow-removebg-preview.png"));

        Image image = Arrow_Image.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon imageIcon_1 = new ImageIcon(image);
        JLabel Back = new JLabel(imageIcon_1); // back
        Back.setBounds(5, 20, 25, 25);
        panel.add(Back);

        Back.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent mouseEvent) {

                System.exit(0);
            }
        });

        // Set Profile image
        ImageIcon profile_Image = new ImageIcon(ClassLoader
                .getSystemResource("Networking\\Chat_App\\download.jpeg"));

        Image image_2 = profile_Image.getImage().getScaledInstance(45, 45,
                Image.SCALE_DEFAULT);
        ImageIcon imageIcon_2 = new ImageIcon(image_2);
        JLabel label_2 = new JLabel(imageIcon_2);

        label_2.setBounds(40, 10, 45, 45);
        panel.add(label_2);

        JLabel name = new JLabel("Galtonde");
        name.setBounds(100, 13, 500, 20);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panel.add(name);

        JLabel Status = new JLabel("Active Now");
        Status.setBounds(100, 35, 100, 18);
        Status.setForeground(Color.white);
        Status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        panel.add(Status);

        a1 = new JPanel();
        a1.setBounds(4, 64, 372, 480);
        a1.setBackground(Color.lightGray);
        frame.add(a1);

        // text
        textField = new JTextField();
        textField.setBounds(5, 547, 270, 30);
        textField.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
        frame.add(textField);

        // Button
        JButton button = new JButton("Send");
        button.setBounds(285, 547, 85, 30);
        button.setBackground(new Color(7, 94, 84));
        button.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
        button.addActionListener(this);
        frame.add(button);

        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String OutPut = textField.getText();
        Right(OutPut);

        textField.setText("");
        // relode
        frame.repaint();
        frame.invalidate();
        frame.validate();
    }

    public void Right(String OutPut) {

        try {

            JPanel p2 = formatLabel(OutPut);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());

            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createHorizontalStrut(13));
            a1.add(vertical, BorderLayout.PAGE_START);

            // sending msg
            dataOutputStream.writeUTF(OutPut);

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void Left(String Input) {

        JPanel panel = formatLabel(Input);

        JPanel left = new JPanel(new BorderLayout());
        left.add(panel, BorderLayout.LINE_START);
        vertical.add(left);

        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
    }

    public static JPanel formatLabel(String out) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 10, 10, 30));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

}

public class Client {

    public static void main(String[] args) {

        Freame_2 freame_2 = new Freame_2();

        Socket client;
        try {

            client = new Socket("localhost", 5000);
            System.out.println("Client is connected!");

            // for receive the msg
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            freame_2.dataOutputStream = new DataOutputStream(client.getOutputStream());
            
            while (true) {

                freame_2.a1.setLayout(new BorderLayout());
                // this UTF protocall use to read msg from client
                String msg = dataInputStream.readUTF();
                
                freame_2.Left(msg);
                freame_2.vertical.validate();

            }
        } catch (Exception e) {

            System.out.println(e);
        }
    }

}
