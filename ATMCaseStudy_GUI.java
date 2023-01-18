import java.awt.*;
import javax.swing.*;
public class ATMCaseStudy_GUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ATM atm = new ATM();
        //theATM.run();
        atm.setSize(600,600);
        atm.setResizable(false);
        atm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        atm.setVisible(true);
    }
    
}
