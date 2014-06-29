/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Groop;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author William Crown
 */
public class Helpers {
    private static final String mDBCx = "jdbc:mysql://localhost:3306/GROOP";
    private static final String mDBUser = "root";
    private static final String mDBPass = "";
    
    private static Set<String> customerIds = new HashSet<String>();
    private static Random random = new Random();

    public static final String getDBCx() {
        return mDBCx;
    }
    
    public static final String getDBUser() {
        return mDBUser;
    }
    
    public static final String getDBPass() {
        return mDBPass;
    }
}    
    
