/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test7View;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */

public class Test7Controller 
    extends TestController 
    implements ActionListener{
    
    private final Test7View view;
    
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<String[]> key = new ArrayList<>();
    private StringBuilder result = new StringBuilder();
    
    public Listener1 l1;
    public Listener2 l2;
    
    private static int step = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Next >")) this.next();
        if(e.getActionCommand().equals("< Prev")) this.prev();
    }
    
    class Listener1 implements ActionListener{
        String str = "";
        Object src;
        int n = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!str.equals(e.getActionCommand()) && Test7Controller.step == n) {
               if (src != null) ((JCheckBox)src).doClick();
            }
            str = e.getActionCommand();
            src = e.getSource();
            n = Test7Controller.step;
        }
    }

    class Listener2  implements ActionListener {
        String str = "";
        Object src;
        int n = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!str.equals(e.getActionCommand()) && Test7Controller.step == n) {
               if (src != null) ((JCheckBox)src).doClick();
            }
            str = e.getActionCommand();
            src = e.getSource();
            n = Test7Controller.step;
        }
    }
    
    public Test7Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        this.l2 = new Listener2();
        this.l1 = new Listener1();
        this.view = new Test7View();
        this.view.setVisible(true);
        this.setListeners();
        this.view.setTitle((String) test.get("name"));
        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {
            while(in.hasNextLine()) {
                StringBuilder str = new StringBuilder();
                str.append("<html><h2><strong>&#32;").append(in.nextLine()).append("</strong></h3><ol type=\"I\"><li>").append(in.nextLine()).append("</li><li>").append(in.nextLine()).append("</li><li>").append(in.nextLine()).append("</li></ol></html>");
                this.questions.add(str.toString());
            }
        } 
        try (Scanner in = new Scanner(new File((String) test.get("key")))) {
            while(in.hasNext()) {
                this.key.add(in.next().split("#"));
            }
        }
        
        this.updateFrame();
    }
    
    private void setListeners() {
        this.view.rb11.addActionListener(this.l1);
        this.view.rb12.addActionListener(this.l1);
        this.view.rb13.addActionListener(this.l1);
        this.view.rb21.addActionListener(this.l2);
        this.view.rb22.addActionListener(this.l2);
        this.view.rb23.addActionListener(this.l2);
        this.view.btnNext.addActionListener(this);
        this.view.btnPrev.addActionListener(this);
    }
    
    @Override
    int getTestCnt() {
        int[] cnt = new int[3];
        for (int i = 0; i < this.answers.size(); i++) {
            String[] ans = this.answers.get(i).split(" ");
            for (int j = 0; j < this.key.get(i).length; j++) {
                if (this.key.get(i)[j].equals(ans[0])) {
                    cnt[j]+=2;
                }
                if (!this.key.get(i)[j].equals(ans[0]) && !this.key.get(i)[j].equals(ans[1])) {
                    cnt[j]+=1;
                }
            }
        }
        this.result.append("направленность на себя (Я);").append(cnt[0]).append("\n");
        this.result.append("направленность на общение (О);").append(cnt[1]).append("\n");
        this.result.append("направленность на дело (Д);").append(cnt[2]).append("\n");
        return 0;
    }
    
    public void finishTest() {
        this.getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", this.result.toString(), n.byteValue());
        try {
            FileWorker.write("users/users.json", usr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Невозможно записать результаты теста");
        }
        this.view.setVisible(false);
    }
    
    public void next() {
        if (Test7Controller.step +1 == this.questions.size()) {
            this.finishTest();
            return;
        }
        StringBuilder ans = new StringBuilder();
        if (!this.l1.str.equals(this.l2.str) && (this.view.rb11.isSelected() ||
                this.view.rb12.isSelected() || this.view.rb13.isSelected()) &&
                (this.view.rb21.isSelected() || this.view.rb22.isSelected() ||
                this.view.rb23.isSelected())) {
            ans.append(this.l1.str).append(" ").append(this.l2.str);
            if (this.answers.size() == Test7Controller.step) {
                this.answers.add(ans.toString());
                Test7Controller.step++;
            } else {
                this.answers.set(Test7Controller.step++, ans.toString());
            }
            this.updateFrame();
        } else {
            JOptionPane.showMessageDialog(this.view, "Некорректный ответ");
        }
        
    }

    public void prev() {
        if (Test7Controller.step > 0) {
            Test7Controller.step--;
            this.updateFrame();
        }
    }
    
    private void updateFrame() {
        this.view.labelQuestion.setText(this.questions.get(Test7Controller.step));
        this.view.rb11.setSelected(false);
        this.view.rb12.setSelected(false);
        this.view.rb13.setSelected(false);
        this.view.rb21.setSelected(false);
        this.view.rb22.setSelected(false);
        this.view.rb23.setSelected(false);
        if (this.answers.size() != Test7Controller.step) {
            this.updCheckBoxes();
        }
    }  
    private void updCheckBoxes() {
        String[] ans = this.answers.get(Test7Controller.step).split(" ");
        switch (ans[0]) {
            case "1":
                this.view.rb11.doClick();
                break;
            case "2":
                this.view.rb12.doClick();
                break;
            case "3":
                this.view.rb13.doClick();
                break;
            }
        switch (ans[1]) {
            case "1":
                this.view.rb21.doClick();
                break;
            case "2":
                this.view.rb22.doClick();
                break;
            case "3":
                this.view.rb23.doClick();
                break;
        }
    }
}
