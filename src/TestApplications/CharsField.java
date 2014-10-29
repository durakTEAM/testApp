package TestApplications;


import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author artemsamsonov
 */
public class CharsField extends JTextField {
 
     public CharsField(int cols) {
         super(cols);
     }
 
     protected Document createDefaultModel() {
         return new CharsDocument();
     }
 
     class CharsDocument extends PlainDocument {
 
         public void insertString(int offs, String str, AttributeSet a) 
             throws BadLocationException {
 
             if (str == null) {
                 return;
             }
             char [] temp = str.toCharArray();
             StringBuilder chars = new StringBuilder();
             for (int i = 0; i < temp.length; i++) {
                if (Character.isLetter(temp[i])) {
                    chars.append(temp[i]);
                }
             }
             super.insertString(offs, chars.toString(), a);
         }
     }
 }