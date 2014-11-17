/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test6View;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test6Controller 
    extends TestController 
    implements ActionListener, ChangeListener {

    private final Test6View view;
    private LinkedList<String[]> questions = new LinkedList<>();
    private int step = 0;
    ArrayList<int[]> answers = new ArrayList<>();
    ArrayList<Integer[]> keys = new ArrayList<>();
    int[] results = new int[8];
    String strOfResults = new String();
    
    public Test6Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        this.view = new Test6View();
        this.view.setVisible(true);
        this.view.setTitle((String) test.get("name"));
        this.view.jButtonNext.setEnabled(false);
        this.view.text1.addChangeListener(this);
        this.view.text2.addChangeListener(this);
        this.view.jButtonNext.addActionListener(this);
        this.view.jButtonPrev.addActionListener(this);
        
        for (int i = 0; i < 28; i++) {
            int[] tmp = new int[2];
            answers.add(tmp);
        }
        try (Scanner in = new Scanner(new File((String) test.get("qPath")), "UTF-8")) {

            for (int i = 0; i < 28; i++) {
                String[] str = new String[3];
                for (int j = 0; j < 3; j++) {
                    str[j] = in.nextLine();
                }
                this.questions.add(str);
            }
        }
        try (Scanner in = new Scanner(new File((String) test.get("key")), "UTF-8")) {
            while (in.hasNextLine()) {
                for (int i = 0; i < 28; i++) {
                    Integer[] str = new Integer[2];
                    for (int j = 0; j < 2; j++) {
                        str[j] = in.nextInt();
                    }
                    keys.add(str);
                }
            }
        }
        this.updateLabels();
    }

    private void updateLabels() {
        int[] temp = new int[2];
        temp[0] = (int) view.text1.getValue();
        temp[1] = (int) view.text2.getValue();
        int sum = (int) temp[0] + temp[1];
        if (sum == 5) {
            this.view.jButtonNext.setEnabled(true);
        } else {
            this.view.jButtonNext.setEnabled(false);
        }
        if (step == 0) {
            this.view.jButtonPrev.setEnabled(false);
        }
        else {
            this.view.jButtonPrev.setEnabled(true);
        }
        if (step == 27) {
            this.view.jButtonNext.setText("Finish");

        } else {
            this.view.jButtonNext.setText("Next");
        }
        updateSpinners();
        view.jLabelQuestion.setText("<html><div align='leading'>&#32;&#32;&#32;" + this.questions.get(step)[0] + "</div></html>");
        view.jLabelAns1.setText("<html><li>" + this.questions.get(step)[1] + "</li></html>");
        view.jLabelAns2.setText("<html><li>" + this.questions.get(step)[2] + "</li></html>");
    }

    public void next() {
        int[] temp = new int[2];
        temp[0] = (Integer) this.view.text1.getValue();
        temp[1] = (Integer) this.view.text2.getValue();
        answers.set(step, temp);
        step++;
        if (step == 28) {
            this.finishTest();
            return;
        }
        this.updateSpinners(this.answers.get(step));
        this.updateLabels();
    }

    public void prev() {
        step--;
        this.updateSpinners(this.answers.get(step));
        updateLabels();
    }

    @Override
    int getTestCnt() {

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 2; j++) {
                int indexRes = keys.get(i)[j];
                int[] arr = (int[]) answers.get(i);
                results[indexRes] += arr[j];
            }
        }
        
        strOfResults = "1. Финансовые мотивы;" + results[0] + ";\n"
                + "2. Общественное признание;" + results[1] + ";\n"
                + "3. Ответственность работы;" + results[2] + ";\n"
                + "4. Отношение с руководством;" + results[3] + ";\n"
                + "5. Карьера, продвижение по службе;" + results[4] + ";\n"
                + "6. Достижение личного успеха;" + results[5] + ";\n"
                + "7. Содержание работы;" + results[6] + ";\n"
                + "8. Сотрудничество в коллективе;" + results[7] + ";\n";
                
        return 0;
    }

    @Override
    public void finishTest() {
        
        this.getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", this.strOfResults, n.byteValue());
        try {
            FileWorker.write("users/users.json", usr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Не удалось записать результаты");
        }
        this.view.setVisible(false);
        this.view.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) this.next();
        if (e.getActionCommand().equals("Prev")) this.prev();
        if (e.getActionCommand().equals("Finish")) this.finishTest();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.updateSpinners();
        this.updateLabels();
    }
    
    private void updateSpinners() {
        int[] temp = new int[2];
        temp[0] = (int) view.text1.getValue();
        temp[1] = (int) view.text2.getValue();
        int sum = temp[0] + temp[1];
        
        this.view.text1.setModel(new SpinnerNumberModel((int) this.view.text1.getValue(), 0, 5-sum+(int) this.view.text1.getValue(), 1));
        this.view.text2.setModel(new SpinnerNumberModel((int) this.view.text2.getValue(), 0, 5-sum+(int) this.view.text2.getValue(), 1));
    }
    private void updateSpinners(int[] a) {
        int sum = a[0] + a[1];
        
        this.view.text1.setModel(new SpinnerNumberModel(a[0], 0, 5-sum+a[0], 1));
        this.view.text2.setModel(new SpinnerNumberModel(a[1], 0, 5-sum+a[1], 1));
    }
}
