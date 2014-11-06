/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Workers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author artemsamsonov
 */
public class JSONWorker {
    /**
     * Method is used in TestController().finishTest() to add test results
     * @param usr   JSONObject which will be updated
     * @param key   key to element which must update
     * @param v     new value of element
     * @param n     in that case number of test 
     */
    static public void updateUsr(JSONObject usr, CharSequence key, Object v, int n) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n, v);
        usr.put(key, temp);
    }
    /**
     * Method is used to read JSONArray from file
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws Exception 
     */
    static public JSONArray open(CharSequence path) throws FileNotFoundException, Exception {
        JSONParser parser = new JSONParser();
        File f = new File((String) path);
        if (!f.isFile()) {
            if (path.equals("users/users.json")) {
                JSONWorker.createUsersFile(path, f);
                throw new FileNotFoundException("<html>Файл <i>users.json</i> не был найден"
                        + "<p>Был создан пустой список пользователeй</html>");
            } else {
                throw new Exception("Отсутствует файл с тестами");
            }
        }
        Object arr;
        FileReader file = new FileReader((String) path);
        try {
            arr = parser.parse(file);
            if (arr instanceof JSONArray) {
                for(int i = 0; i < ((JSONArray)arr).size(); i++) {
                    convertArraysToInt((JSONObject)((JSONArray)arr).get(i));
                }
                return (JSONArray)arr;
            } else {
                if (path.equals("users/users.json")) {
                    JSONWorker.createUsersFile(path, f);
                    throw new FileNotFoundException("<html>Файл <i>users.json</i> повреждён"
                            + "<p>Был создан пустой список пользователeй</html>");
                } else {
                    throw new Exception("Повреждён файл с тестами");
                }
            }
        } catch (Exception ex) {
            if (path.equals("users/users.json")) {
                JSONWorker.createUsersFile(path, f);
                throw new FileNotFoundException("<html>Файл <i>users.json</i> повреждён"
                    + "<p>Был создан пустой список пользователeй</html>");
            } else {
                throw new Exception("Повреждён файл с тестами");
            }
        }
        
    }
    /**
     * Method is used to read JSONObject from file with JSONArray
     * @param path
     * @param id
     * @return
     * @throws FileNotFoundException
     * @throws Exception 
     */
    static public JSONObject open(CharSequence path, int id) throws FileNotFoundException, Exception {
        JSONParser parser = new JSONParser();
        JSONArray arr;
        try (FileReader file = new FileReader((String) path)) {
            arr = (JSONArray) parser.parse(file);
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(ex.getLocalizedMessage() + "\nФайл "+path+" не найден");
        } catch (Exception ex){
            throw new Exception(ex.getLocalizedMessage() + "\nОшибка чтения файла " + path);
        } 
        JSONObject usr = (JSONObject) arr.get(id);
        convertArraysToInt(usr);
        return usr;
    }
    static private JSONArray longToInt(JSONArray arr) {
        JSONArray a = new JSONArray();
        for (Object i : arr) {
            if (i instanceof Long) a.add((int)((Long)i).intValue());
            else a.add(i);
        }
        return a;
    }
    /**
     * Recomend use this method to get JSONObject from JSONArray
     * @param arr
     * @param id
     * @return
     */
    static public JSONObject get(JSONArray arr, int id) {
        JSONObject obj = (JSONObject) arr.get(id);
        convertArraysToInt(obj);
        return obj;
    }
    /**
     * 
     * @param usr JSONObject whose arrays will
     * be converted to array<int>
     */
    static private void convertArraysToInt(JSONObject usr){
        if (usr.containsKey("testsArray")) {
            usr.put("testsArray", longToInt((JSONArray) usr.get("testsArray")));
        }    
        if (usr.containsKey("sendedResults")) {
            usr.put("sendedResults", longToInt((JSONArray) usr.get("sendedResults")));
        }
        if (usr.containsKey("ID") && usr.get("ID") instanceof Long) {
            usr.put("ID", ((Long)usr.get("ID")).intValue());
        }
        if (usr.containsKey("type") && usr.get("type") instanceof Long) {
            usr.put("type", ((Long)usr.get("type")).intValue());
        }
        if (usr.containsKey("fact") && usr.get("fact") instanceof Long) {
            usr.put("fact", ((Long)usr.get("fact")).intValue());
        }
        if (usr.containsKey("ans1") && usr.get("ans1") instanceof Long) {
            usr.put("ans1", ((Long)usr.get("ans1")).intValue());
        }   
        if (usr.containsKey("ans2") && usr.get("ans2") instanceof Long) {
            usr.put("ans2", ((Long)usr.get("ans2")).intValue());
        }   
    }

    private static void createUsersFile(CharSequence path, File f) throws Exception {
        if (path.equals("users/users.json")) {
            try {
                FileWriter fw = new FileWriter(f);
                fw.write("[]");
                fw.flush();
            } catch (IOException ex) {
                throw new Exception("Не удалось создать пустой файл " + path);
            }
            }
    }
}
