/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Groop;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author wcrown
 */
public class CheckLists extends Object {
    Vector checklists = new Vector();

    private Connection mCon = null;

    int mUnreadCount = 0;

    public CheckLists() {
    }

    public void loadChecklists(String MID) {
        try {
            //clear the list
            checklists.clear();
            
            String query = "select * from Checklist WHERE MID = ? order by DueDate";

            openConnection();
            
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, MID);
            
            ResultSet rset = null;
            rset = pt.executeQuery();

            while (rset.next()) {
                checklists.add(new Checklist(rset));
            }

            rset.close();
            pt.close();
            
            closeConnection();
            
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Checklists:loadChecklists: " + e.getMessage());
        }

        return;
    }

    public int getChecklistCount() {
        return checklists.size();
    }

    public Checklist getChecklist(int index) {
        return (Checklist) checklists.get(index);
    }
    
    //get checklist from the collection by CLID
    public Checklist getChecklist(String CLID) {
        for (int i=0; i < checklists.size(); i++) {
            
            if (((Checklist) checklists.get(i)).getCLID().equals(CLID)) {
                return (Checklist) checklists.get(i);
            }
        }
        return null;
    }
    
    
    public void updateChecklist(Checklist checklist) {
        for (int i=0; i < checklists.size(); i++) {
            //if we already have the employee we'll update it.
            if ( ((Checklist) checklists.get(i)).getCLID().contains(checklist.getCLID())) {
                checklists.remove(((Checklist) checklists.get(i)));
                
                checklists.add(checklist);
                return;
            }
            //if we get here then it's a new Checklist
            checklists.add(checklist);
        }
    }
    
    private void openConnection() {
        try {
            if (mCon == null || mCon.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                mCon = DriverManager.getConnection(Helpers.getDBCx(), Helpers.getDBUser(), Helpers.getDBPass());
            }
        }
        catch (Exception ec) {
            System.out.println("Employees:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Employees:closeConnection: " + ec.getMessage());
        }
    }        
}
