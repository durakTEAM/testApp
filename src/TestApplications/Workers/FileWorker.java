/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Workers;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class FileWorker {
    /**
     * Method to write JSONObject to file with JSONArray
     * @param file path to write
     * @param obj obj to write
     * @throws FileNotFoundException
     * @throws Exception 
     */
    static public void write(CharSequence file, JSONObject obj) throws FileNotFoundException, Exception {
        try (InputStreamReader f =  new InputStreamReader(new FileInputStream((String) file), "UTF-8")) {
            JSONArray x = JSONWorker.open(file);
            if (x.size() == (int)obj.get("ID")) {
                x.add(obj);
            } else {
                x.set((int)obj.get("ID"), obj);
            }
            BufferedWriter fileW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((String) file), "UTF-8"));
            fileW.append(x.toString());
            fileW.flush();
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(":Файл " + file + " не существует");
        }
    }
    static public void createFile(CharSequence path, String content) throws IOException {
        BufferedWriter fw;
        try {
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((String) path), "UTF-8"));
            fw.append(content);
            fw.flush();
        } catch (IOException ex) {
            throw new IOException();
        }
    }
}
