/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.ImagePanel;
import TestApplications.Views.Test9View;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test9Controller extends TestController implements MouseListener, ActionListener {

    private Test9View view;
    public ImagePanel panel;
  
    Test9Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        this.test = test;
        view = new Test9View();
        view.setVisible(true);
        panel = new ImagePanel(new ImageIcon("tests/table.gif").getImage());
        panel.setSize(602, 601);
        view.add(panel);
        //panel.setLocation(600, 20);
        view.pack();
        
        view.buttonEnd.setEnabled(false);
        this.createLabelsArray();
        for (int i = 0; i < 15; i++) {
            JLabel a = new JLabel();
            a.setText(String.valueOf(i + 1));
            a.setSize(new Dimension(40, 40));
            a.setHorizontalAlignment(JLabel.CENTER);
            panel.add(a);
            a.setLocation(i * 40, 0);
        }
        this.listOfTerms();
        this.setListeners();
    }

    private void createLabelsArray() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (i + j < 14) {
                    labelsArray[i][j] = new JLabel();
                    labelsArray[i][j].setSize(new Dimension(40, 40));
                    labelsArray[i][j].setHorizontalAlignment(JLabel.CENTER);
                }
            }
        }

    }
    public void listOfTerms(){
        JLabel instructionLabel = new JLabel ();
        StringBuilder instStr = new StringBuilder();
        try(Scanner instr = new Scanner(new File("tests/instructionTest9.txt"))){           
            while(instr.hasNextLine()){
                instStr.append(instr.nextLine());
            }
            view.add(instructionLabel);
            instructionLabel.setText("<html>"+instStr.toString()+"</html>");
            instructionLabel.setSize(new Dimension(350,250));
            instructionLabel.setLocation(930, 0);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test9Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try(Scanner in1 = new Scanner(new File ((String)test.get("qPath")))){
            for (int i = 0; i < 15; i++){
                JLabel term = new JLabel();
                view.add(term);
                view.setPreferredSize(new Dimension(1300,620));
                term.setLocation(620,30*i);
                term.setText("<html>"+in1.nextLine()+"</html>");
                term.setSize(new Dimension(300,40));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test9Controller.class.getName()).log(Level.SEVERE, null, ex);
        };
        view.pack();
    }
    final void setListeners() {
        panel.addMouseListener(this);
        view.buttonEnd.addActionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if ((x / 40 + y / 40 < 15) && y>40) {
           List  ij = new ArrayList();
            int i = x / 40;
            int j = y / 40 - 1;
           ij.add(i);
           ij.add(j);
           answersSet.add(ij);
            if (labelsArray[i][j].getText().isEmpty() || labelsArray[i][j].getText().equals(String.valueOf(i + 1))) {
                Integer value = x / 40 + y / 40 + 1;
                labelsArray[i][j].setText(value.toString());
            } else {
                labelsArray[i][j].setText(String.valueOf(i + 1));
            }
            panel.add(labelsArray[i][j]);
            labelsArray[i][j].setLocation((x / 40) * 40, (y / 40) * 40);
            if (answersSet.size() == 105) {
                view.buttonEnd.setEnabled(true);
            }
            view.pack();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    int getTestCnt() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (i + j < 14) {                    
                    int a = Integer.parseInt(labelsArray[i][j].getText());
                    res[a - 1]++;
                }
            }
        }
        return 0;
    }

   
    
    public void finishTest()  {
        this.getTestCnt();
        StringBuilder finishResults = new StringBuilder();
        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {
                for (int i = 0; i < 15; i++) {
                    finishResults.append(in.nextLine()).append(";").append(res[i]).append(";\n");
                }  
            }
        catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(this.view, ex.getMessage());
            view.setVisible(false);
            view.dispose();
        }
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults",finishResults.toString(), n.byteValue());
        try {FileWorker.write("users/users.json", usr);}
        catch (Exception ex){
            JOptionPane.showMessageDialog(view, "Не удалось записать результаты");
        }
        view.setVisible(false); 
        view.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
            this.finishTest();
        
    }
}
