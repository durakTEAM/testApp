/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.ReorderListener;
import TestApplications.Views.Test8View;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test8Controller 
    extends TestController
    implements KeyListener, ActionListener {
    
    public Test8View view;
    protected JSONObject usr;
    protected JSONObject test;
    protected Long n;
    String[] res1 = new String[18];
    String[] res2 = new String[18];
    
        

    Test8Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(test, usr);
        this.test = test;
        this.view = new Test8View();
        view.setVisible(true);
        this.setListeners();
        DefaultListModel listmodel = new DefaultListModel();
        view.list.setModel(listmodel);
        view.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        MouseAdapter listener = new ReorderListener(view.list);
        view.list.addMouseListener(listener);
        view.list.addMouseMotionListener(listener);

        DefaultListModel listmodel2 = new DefaultListModel();
        view.list2.setModel(listmodel2);
        view.list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        MouseAdapter listener2 = new ReorderListener(view.list2);
        view.list2.addMouseListener(listener2);
        view.list2.addMouseMotionListener(listener2);

        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {
            for (int i = 0; i < 18; i++) {
                listmodel.addElement(in.nextLine());
            }
            for (int i = 0; i < 18; i++) {
                listmodel2.addElement(in.nextLine());
            }
        }
        view.setLocationRelativeTo(null);
    }
    
    final void setListeners(){
        view.jButtonFinish.addActionListener(this);
    }
    public void finishTest() throws Exception {
        
        StringBuilder str = new StringBuilder();
        
        for (String res11 : res1) {
            str.append(res11);
        }
        StringBuilder str2 = new StringBuilder();
        
        for (String res22 : res2){
            str2.append(res22);
        }
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults","Терминальные ценности;\n"+str.toString()+"Инструментальные ценности\n"+str2.toString(), n.byteValue());
        FileWorker.write("users/users.json", usr);
        view.setVisible(false); 
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.finishTest();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Невозможно записать результаты теста");
        }
    }

    @Override
    int getTestCnt() {
        for (int i = 0; i < 18; i++) {
            res1[i] = view.list.getModel().getElementAt(i).toString() + "\n";
            res2[i] = view.list2.getModel().getElementAt(i).toString() + "\n";
        }
        return 0;
    }
}
