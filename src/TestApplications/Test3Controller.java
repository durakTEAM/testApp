/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test3Controller {
    public List<String[]> test;
    private TreeSet<String> key = new TreeSet<>();
    private JSONObject usr;
    private Test3View view;
    private Long n;
    
    Test3Controller(Test3View view, JSONObject t, JSONObject usr) throws FileNotFoundException, IOException {
        this.view = view;
        this.usr = usr;
        n = (Long) t.get("number");
        CSVReader reader = new CSVReader(new FileReader((String) t.get("path")), ';');
        test = reader.readAll();
        String str = (String) t.get("key");
        for (String i : str.split("; ")) {
            key.add(i);
        }
    }
    
    void fillTestTable() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        DefaultTableModel model = this.makeModel();
        this.view.tableTests.setModel(model);
        for (int i = 0; i < model.getColumnCount(); i++) {
            this.view.tableTests.getColumnModel().getColumn(i).setResizable(false);
            if (i % 2 == 0 && i != 0) {
                this.view.tableTests.getColumnModel().getColumn(i);
            }
            else {
                
            }
        }
        this.view.tableTests.getColumnModel().getColumn(0).setMinWidth(20);
        this.view.tableTests.getColumnModel().getColumn(0).setPreferredWidth(20);
        for (int i = 1; i < test.size(); i++) {
            this.view.tableTests.getModel().setValueAt(i, i-1, 0);
            for (int j = 1; j < (test.get(i).length * 2 - 1); j+=2) {
                this.view.tableTests.getModel().setValueAt(test.get(i)[(j+1)/2], i-1, j);
            }
        }
        for (int i = 0; i < this.view.tableTests.getRowCount(); i++) {
            for (int j = 2; j < this.view.tableTests.getColumnCount(); j+=2) {
                this.view.tableTests.getModel().setValueAt(false, i, j);
            }
        }
    }
    DefaultTableModel makeModel(){
        String[] a = test.get(0);
        int r = test.size();
        DefaultTableModel model = new DefaultTableModel() {
            @Override 
            public int getColumnCount() { 
                return a.length*2-1; 
            } 
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col != 0 && col % 2 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
            @Override
            public int getRowCount() {
                return r-1;
            }
            @Override 
            public String getColumnName(int index) { 
                return a[(index+1)/2];
            } 
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex % 2 == 0 && columnIndex != 0) {
                    return Boolean.class;
                } else {
                    return a[(columnIndex+1)/2].getClass();
                }
            }
        };
        return model;
    }
    
    Long getTestCnt() {
        Long res = new Long("0");
        Integer [] ans = new Integer[2];
        for (int i = 0; i < this.view.tableTests.getRowCount(); i++) {
            int cnt = 0;
            for (int j = 2; j < this.view.tableTests.getColumnCount(); j+=2) {
                if ((Boolean) this.view.tableTests.getModel().getValueAt(i, j)) {
                    cnt++;
                    ans[0] = i+1;
                    ans[1] = j/2;
                }
            }
            if (cnt != 1) {
                JOptionPane.showMessageDialog(this.view, "Некорректный ответ в строке " + (i+1));
                return new Long("-1");
            } else {
                String a = ans[0].toString() + '/' + ans[1].toString();
                res += this.key.contains(a) ? 1 : 0;
            }
        }
        return res;
    }
    
    void finishTest() throws Exception {
        Long res = getTestCnt();
        if (res >= 0) {
            this.updateUsr(usr, "testsArray", new Long("1"));
            this.updateUsr(usr, "testsResults", res);
            FileWorker.write("users/users.json", usr);
            this.view.setVisible(false);
        }
    }
    
    void updateUsr(JSONObject usr, CharSequence key, Object v) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n.intValue(), v);
        usr.put(key, temp);
    }
}
