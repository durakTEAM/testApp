/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test4View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test4Controller 
    extends TestController
    implements ActionListener, WindowListener {
    
    private Test4View view;
    public ArrayList<String> questions = new ArrayList<>();
    private int[] ans = new int[25];
    private int step = 0;
    
    public Test4Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        
        this.view = new Test4View();
        this.view.setVisible(true);
        this.view.setTitle((String) test.get("name"));
        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {
            while(in.hasNext())
                this.questions.add(in.nextLine());
        }
        
        this.setListeners();
    }
    
    private void setListeners() {
        this.view.btnNext.addActionListener(this);
        this.view.btnPrev.addActionListener(this);
        this.view.addWindowListener(this);
    }
    
    public void update() {
        if (step == 0) {
            this.view.btnPrev.setEnabled(false);
        } else {
            this.view.btnPrev.setEnabled(true);
        }
        if (step == this.questions.size()-1) {
            this.view.btnNext.setText("Finish");
        } else {
            this.view.btnNext.setText("Next >");
        }
        this.view.slider.setValue(ans[step]);
        this.view.labelQuestins.setText("<html><div align='center'>" + String.valueOf(step+1) + ") " 
                + this.questions.get(step) + "</div></html>");
    }
    
    public void next() throws Exception {
        ans[step++] = this.view.slider.getValue();
        update();
    }
    
    public void prev() {
        if (step - 1 >= 0) {
            step--;
            update();
        } 
    }
    void updateUsr(JSONObject usr, CharSequence key, Object v) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n.intValue(), v);
        usr.put(key, temp);
    }
    @Override
    int getTestCnt() {
        int res = 0;
        for (int i : this.ans) {
            res += i;
        }
        return res;
    }
    @Override
    protected void finishTest() {
        try {
            super.finishTest();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Не удалось записать результаты теста");
        }
        this.view.setVisible(false);
        this.view.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next >")) try {
            this.next();
        } catch (Exception ex) {
            Logger.getLogger(Test4Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (e.getActionCommand().equals("< Prev")) this.prev();
        if (e.getActionCommand().equals("Finish")) this.finishTest();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        this.update();
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
