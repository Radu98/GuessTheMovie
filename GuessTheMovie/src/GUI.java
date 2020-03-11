import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class GUI extends JPanel implements ActionListener {
    private JFrame frame;
    private JPanel panel1, panel2;
    private JButton button1, buttonStart;
    private Line2D line1, line2, line3, line4;
    private JTextField textField;
    private JLabel labelFilm, labelText, labelText2, labelError, labelGresite, labelIntroductiv;
    private char lit;
    private boolean gameStarted;
    private Game newGame;
    private Font myFont = new Font("Comic Sans MS", Font.BOLD, 30);
    private Font myFont2 = new Font("Arial", Font.BOLD, 25);
    private Font myFont3 = new Font("Century Gothic", Font.BOLD, 40);
    private Font myFont4 = new Font("Comic Sans MS", Font.BOLD, 40);
    private Thread t1, t2;

    public GUI() {
        makeGUI();
    }

    private void makeGUI() {
        ImageIcon img = new ImageIcon("91HqVNALz-L._SY355_.jpg");
        frame = new JFrame("Hanging Man");
        frame.setLayout(new BorderLayout());
        frame.setLocation(700, 150);
        frame.setIconImage(img.getImage());

        arrangeComponents();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void arrangeComponents() {

        panel1 = new JPanel();
        panel2 = new JPanel();

        panel1.setLayout(null);

        button1 = new JButton("Incearca :)");
        button1.setFont(myFont);
        button1.setBounds(50, 500, 700, 200);
        panel1.add(button1);
        button1.setVisible(false);
        button1.addActionListener(this);

        buttonStart = new JButton("Start!");
        buttonStart.setFont(myFont);
        buttonStart.setBounds(400, 250, 400, 300);
        buttonStart.setVisible(true);
        panel1.add(buttonStart);
        buttonStart.addActionListener(this);

        labelText = new JLabel("Introdu o singura litera (mica): ");
        labelText.setFont(myFont2);
        labelText.setBounds(50, 100, 400, 50);
        panel1.add(labelText);
        labelText.setVisible(false);

        textField = new JTextField("");
        textField.setBounds(480, 100, 200, 50);
        textField.setFont(myFont);
        panel1.add(textField);
        textField.setVisible(false);

        labelText2 = new JLabel("Cuvant de ghicit: ");
        labelText2.setFont(myFont2);
        labelText2.setBounds(50, 200, 300, 50);
        panel1.add(labelText2);
        labelText2.setVisible(false);

        labelFilm = new JLabel("");
        labelFilm.setFont(myFont2);
        labelFilm.setBounds(50, 285, 700, 50);
        panel1.add(labelFilm);
        labelFilm.setVisible(false);

        labelError = new JLabel("");
        labelError.setFont(myFont2);
        labelError.setBounds(50, 350, 700, 50);
        labelError.setForeground(Color.RED);
        panel1.add(labelError);
        labelError.setVisible(false);

        labelGresite = new JLabel("");
        labelGresite.setBounds(50, 420, 700, 50);
        labelGresite.setFont(myFont2);
        labelGresite.setForeground(Color.magenta);
        panel1.add(labelGresite);
        labelGresite.setVisible(false);

        labelIntroductiv = new JLabel("Ai voie 7 greseli. Succes!");
        labelIntroductiv.setBounds(50, 10, 750, 100);
        labelIntroductiv.setFont(myFont3);
        labelIntroductiv.setForeground(Color.blue);
        labelIntroductiv.setVisible(false);
        panel1.add(labelIntroductiv);

        panel1.setPreferredSize(new Dimension(800, 400));
        panel2.setPreferredSize(new Dimension(400, 400));
        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);

        SwingUtilities.getRootPane(button1).setDefaultButton(button1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            if (gameStarted)
                if (newGame.checkIfWon()) {
                    JOptionPane.showMessageDialog(frame, "Felicitari! Ai castigat!");
                    System.exit(0);
                } else {
                    labelError.setText("");
                    if (!textField.getText().equals("")) {
                        lit = textField.getText().charAt(0);
                        textField.setText("");
                        newGame.verif(lit);
                        labelGresite.setText(newGame.getVectorCaract());
                        labelFilm.setText(newGame.getMovieName());
                        if (!newGame.getLiteraValida()) labelError.setText("Introdu o litera valida!");
                        else if (newGame.getLiteraDejaIntrod())
                            labelError.setText("Litera '" + lit + "' a fost deja introdusa. Introdu alta litera!");
                        else if (newGame.checkIfWon()) {
                            JOptionPane.showMessageDialog(frame, "Felicitari! Ai castigat!");
                            System.exit(0);
                        }
                    }
                }

            Graphics g = panel2.getGraphics();
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(8));
            g2.setColor(Color.RED);

            switch (newGame.getNrGresite()) {
                case 1:
                    g2.drawOval(250, 225, 100, 100);
                    break;
                case 2:
                    g2.drawLine(300, 325, 300, 350);
                    break;
                case 3:
                    g2.drawLine(300, 350, 250, 325);
                    break;
                case 4:
                    g2.drawLine(300, 350, 350, 325);
                    break;
                case 5:
                    g2.drawLine(300, 350, 300, 400);
                    break;
                case 6:
                    g2.drawLine(300, 400, 250, 500);
                    break;
                case 7: {
                        g2.drawLine(300, 400, 350, 500);
                        JOptionPane.showMessageDialog(frame, "Ai pierdut! Cuvantul era: " + "'" + newGame.numeFilm + "'");
                        System.exit(0);
                }
            }
        } else if (e.getSource() == buttonStart) {
            t1 = new Thread(() -> {
                buttonStart.setVisible(false);
                button1.setVisible(true);
                labelText.setVisible(true);
                labelText2.setVisible(true);
                textField.setVisible(true);
                labelFilm.setVisible(true);
                labelError.setVisible(true);
                labelGresite.setVisible(true);
                labelIntroductiv.setVisible(true);

                newGame = new Game();
                newGame.chooseTitle();
                labelFilm.setText(newGame.getMovieName());
                labelGresite.setText(newGame.getVectorCaract());
                gameStarted = true;
            });

            t2 = new Thread(() -> {
                //creare linii spanzuratoare
                line1 = new Line2D.Double();
                line2 = new Line2D.Double();
                line3 = new Line2D.Double();
                line4 = new Line2D.Double();

                line1.setLine(70, 100, 70, 650);
                line2.setLine(70, 100, 300, 100);
                line3.setLine(300, 100, 300, 225);
                line4.setLine(40, 650, 100, 650);

                //desenare linii create
                Graphics g = panel2.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(7));
                g2.setColor(Color.BLACK);
                g2.draw(line1);
                g2.draw(line2);
                g2.draw(line3);
                g2.draw(line4);
            });

            t1.start();
            t2.start();
        }
    }

}