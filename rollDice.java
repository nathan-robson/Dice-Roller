package Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;

public class rollDice extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    // Buttons
    JButton btnRoll = new JButton("Roll");
    JButton btnClear = new JButton("Clear");
    JButton btnLast = new JButton("< Previous");
    JButton btnNext = new JButton("Next >");

    // Text input fields
    JTextField boxNumSides = new JTextField();
    JTextField boxNumDice = new JTextField();

    // Outputs
    JList<String> list;
    DefaultListModel<String> listModel;

    // roll history
    ArrayList<String> rolls = new ArrayList<String>();
    int rollNum = 0;
    int pageNum = 1;
    int firstElement = 0;
    int lastElement = rolls.size();

    public rollDice()
    {

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        // inputs and input layout
        panel1.setLayout(new GridLayout(3,2));
        add(panel1,BorderLayout.NORTH);
        panel1.add(new JLabel("Number of sides: "));
        panel1.add(boxNumSides);
        boxNumSides.setText("6");
        panel1.add(new JLabel("Number of dice: "));
        boxNumDice.setText("1");
        panel1.add(boxNumDice);

        // buttons and button layout
        add(panel2,BorderLayout.CENTER);
        panel2.add(btnLast);
        btnLast.addActionListener(this);
        panel2.add(btnRoll);
        btnRoll.addActionListener(this);
        panel2.add(btnClear);
        btnClear.addActionListener(this);
        panel2.add(btnNext);
        btnNext.addActionListener(this);

        // List and list layout
        listModel = new DefaultListModel<String>();

        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setAutoscrolls(true);
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3.add(list);
        add(panel3,BorderLayout.SOUTH);

        
        setVisible(true);
        setSize(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    } // end RollDice
    
    @Override
    public void actionPerformed(ActionEvent e) throws NumberFormatException
    {
        // roll @boxNumDice many dice, with @boxNumSides number of sides on each die
        if(e.getSource() == btnRoll) {
          try {
            int numDice = Integer.parseInt(boxNumDice.getText());
            if(numDice < 1) {
              throw new NumberFormatException();
            }

            int numSides = Integer.parseInt(boxNumSides.getText());
            if(numSides < 2) {
              throw new NumberFormatException();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Roll number: " + (rolls.size() + 1) + " | ");
            for(int i = 0; i < numDice; i++) {
              Dice d = new Dice(numSides);
              sb.append(d.getResult() + " ");
            }
            sb.append(" | ");
            rolls.add(sb.toString());
            rolls.trimToSize();

            listModel.addElement(sb.toString());
            if(listModel.size() > 16) {
              listModel.remove(0);
            }
          } // end try
          catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Both the number of dice and the number of sides need positive integer values");
            boxNumSides.setText("6");
            boxNumDice.setText("1");
          }
          catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
          }
        } // end btnRoll if

        else if(e.getSource() == btnClear) { // clear the list
          rolls.clear();
          listModel.removeAllElements();
          rollNum = rolls.size();
        } // end btnClear

        else if(e.getSource() == btnLast) { // jump to last page in history
          listModel.removeAllElements();
          firstElement = rollNum - (pageNum * 16);
          if(firstElement < 0) { firstElement = 0; }
          lastElement = firstElement + 16;
          if(lastElement > rolls.size()) { lastElement = rolls.size(); }
          for(int i = firstElement; i < lastElement; i++) {
            listModel.addElement(rolls.get(i));
          }
          pageNum++;
        } // end btnLast

        else if(e.getSource() == btnNext) { // go to next page in history
          listModel.removeAllElements();
          lastElement = lastElement + 16;
          if(lastElement > rolls.size()) { lastElement = rolls.size(); }
          firstElement = lastElement - 16 ;
          if(firstElement < 0) { firstElement = 0; }

          for(int i = firstElement; i < lastElement; i++) {
                  listModel.addElement(rolls.get(i));
          }
          pageNum--;
        } // end btnNext

    } // end actionPerformed(Event e)

    public static void main(String [] args)
    {
        new rollDice();
    }

} // end class RollDice

