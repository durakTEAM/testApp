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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test6Controller 
    extends TestController 
    implements ActionListener {

    private final Test6View view;
    private LinkedList<String[]> questions = new LinkedList<>();
    private int step = 0;
    ArrayList answers = new ArrayList();
    ArrayList<Integer[]> keys = new ArrayList<>();
    int[] results = new int[8];
    String strOfResults = new String();
    public Test6Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        this.view = new Test6View();
        this.view.setVisible(true);
        for (int i = 0; i < 28; i++) {
            int[] tmp = new int[2];
            answers.add(tmp);
        }
        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {

            for (int i = 0; i < 28; i++) {
                String[] str = new String[3];
                for (int j = 0; j < 3; j++) {
                    str[j] = in.nextLine();
                }
                this.questions.add(str);
            }
        }
        try (Scanner in = new Scanner(new File((String) test.get("key")))) {
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
        if (step == 0) {
            this.view.jButtonPrev.setEnabled(false);
        }
        else this.view.jButtonPrev.setEnabled(true);
        if (step == 27) {
            this.view.jButtonNext.setText("Завершить  тест");

        } else {
            this.view.jButtonNext.setText("Next");
        }

        int[] textfield = (int[]) answers.get(step);
        view.text1.setText(Integer.toString(textfield[0]));
        view.text2.setText(Integer.toString(textfield[1]));
        view.jLabelQuestion.setText("<html>" + this.questions.get(step)[0] + "</html>");
        view.jLabelAns1.setText("<html>" + this.questions.get(step)[1] + "</html>");
        view.jLabelAns2.setText("<html>" + this.questions.get(step)[2] + "</html>");
    }

    public void next() throws Exception {
        int[] temp = new int[2];
        temp[0] = Integer.parseInt(view.text1.getText());
        temp[1] = Integer.parseInt(view.text2.getText());
        int sum = (int) temp[0] + temp[1];
        if (sum == 5) {
            answers.set(step, temp);
            step++;
            if (step == 28) {
                this.finishTest();
                return;
            }
            this.updateLabels();
        } else {
            JOptionPane.showMessageDialog(null, "<html>Распределите баллы корректно<p>"
                    + "Необходимо распределить все 5 баллов по 2 пунктам</html>");
        }

    }

    public void prev() {
        step--;
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
        
        strOfResults = "\n1. Финансовые мотивы;" + results[0] + ";\n"
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
    public void finishTest() throws Exception {
        
        this.getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", this.strOfResults, n.byteValue());
        FileWorker.write("users/users.json", usr);
        this.view.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) try {
            this.next();
        } catch (Exception ex) {
            Logger.getLogger(Test6Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (e.getActionCommand().equals("Prev")) this.prev();
    }

}
