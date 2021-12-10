import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFrame {
    private JFrame frame = new JFrame("Server setup");
    private JPanel upperPanel = new JPanel();
    private JPanel midPanel = new JPanel();

    private JPanel lowerPanel = new JPanel();
    private JTextField ipTf = new JTextField( 30);
    private JTextField portTf = new JTextField(30);
    private JTextField localPortTf = new JTextField(30);
    private  JLabel labelIP = new JLabel("IP Address:");
    private  JLabel labelPort = new JLabel("Port number:");
    private JLabel labelLocalPort = new JLabel("Local port number:");
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");

    private String[] values;

    //UDPClientbe, hogy ne dobjon nullvalue hibat
    private boolean closed = false;

    public void createFrame(){
        ipTf.setEditable(true);
        portTf.setEditable(true);
        upperPanel.add(labelIP, BorderLayout.WEST);
        upperPanel.add(ipTf, BorderLayout.CENTER);
        midPanel.add(labelPort, BorderLayout.WEST);
        midPanel.add(portTf, BorderLayout.CENTER);
        midPanel.add(localPortTf, BorderLayout.WEST);

        lowerPanel.add(cancel, BorderLayout.WEST);
        lowerPanel.add(ok, BorderLayout.EAST);

        ok.setActionCommand("ok");
        ActionListener okListener = new OKButtonListener();
        ok.addActionListener(okListener);

        cancel.setActionCommand("cancel");
        ActionListener cancelListener = new CancelButtonListener();
        cancel.addActionListener(cancelListener);

        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(midPanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }



    public class OKButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent ae){

            if("ok".equals(ae.getActionCommand())){
                String[] ertekek = {ipTf.getText(), portTf.getText(), localPortTf.getText()};
                values = ertekek;
                closed = true;
            }
            frame.dispose();
        }
    }
    public class CancelButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            if("cancel".equals(ae.getActionCommand())){
                frame.dispose();
            }
        }
    }
    public String[] getValues(){
        return values;
    }
    public boolean getClosed(){
        return closed;
    }
}
