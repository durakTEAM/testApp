/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import TestApplications.Views.Test3View;
import au.com.bytecode.opencsv.CSVReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test3Controller extends TestController implements KeyListener,
        ActionListener, WindowListener, TableModelListener{
    
    public List<String[]> test;
    private TreeSet<String> key = new TreeSet<>();
    private final Test3View view;
    
    private TreeSet<Integer> isReady = new TreeSet<>();
    
    public Test3Controller(Test3View view, JSONObject t, JSONObject usr) throws FileNotFoundException, IOException {
        super(usr, t);
        this.view = view;
        CSVReader reader = new CSVReader(new FileReader((String) t.get("path")), ';');
        test = reader.readAll();
        String str = (String) t.get("key");
        for (String i : str.split("; ")) {
            key.add(i);
        }
    }
    
    public void fillTestTable() {
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
    private DefaultTableModel makeModel(){
        final String[] a = test.get(0);
        final int r = test.size();
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
    
    @Override
    int getTestCnt() {
        int res = 0;
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
            
                String a = ans[0].toString() + '/' + ans[1].toString();
                res += this.key.contains(a) ? 1 : 0;
            }
        
        return res;
    }
    
    @Override
    public void finishTest() throws Exception {
        int res = getTestCnt();
        if (res >= 0) {
            JSONWorker.updateUsr(usr, "testsArray", 1, n.intValue());
            JSONWorker.updateUsr(usr, "testsResults", res, n.intValue());
            FileWorker.write("users/users.json", usr);
            this.view.setVisible(false);
        }
    }   

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER && this.isReady.size() == 30) {
            try {
                this.finishTest();
            } catch (Exception ex) {
                Logger.getLogger(Test3Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Готово")) {
            try {
                this.finishTest();
            } catch (Exception ex) {
                Logger.getLogger(Test3View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        this.fillTestTable();
        this.view.tableTests.getModel().addTableModelListener(this);
        this.view.btnReady.setEnabled(false);
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

    @Override
    public void tableChanged(TableModelEvent e) {
        int i = e.getLastRow();
        int j = e.getColumn();
        boolean x = !((Boolean)((TableModel)e.getSource()).getValueAt(i, j));
        if (!x) {
            for (int l = 2; l <=6; l+=2) {
                if (l==j) continue;
                ((TableModel)e.getSource()).setValueAt(x, i, l);
            }
        }
        this.isReady.add(i);
        if (this.isReady.size() == 30) {
            this.view.btnReady.setEnabled(true);
        }
    }
}
