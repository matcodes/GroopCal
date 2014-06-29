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
public class Milestones extends Object {
    Vector milestones = new Vector();

    private Connection mCon = null;

    int mUnreadCount = 0;

    public Milestones() {
    }

    public void loadMilestones(String CID) {
        try {
            //clear the list
            milestones.clear();
            
            String query = "select * from Milestone WHERE CID = ? order by DueDate";

            openConnection();
            
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, CID);
            
            ResultSet rset = null;
            rset = pt.executeQuery();

            while (rset.next()) {
                milestones.add(new Milestone(rset));
            }

            rset.close();
            pt.close();
            
            closeConnection();
            
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Milestones:loadMilestones: " + e.getMessage());
        }

        return;
    }

    public int getMilestoneCount() {
        return milestones.size();
    }

    public Milestone getMilestone(int index) {
        return (Milestone) milestones.get(index);
    }
    
    //get milestone from the collection by MID
    public Milestone getMilestone(String MID) {
        for (int i=0; i < milestones.size(); i++) {
            
            if (((Milestone) milestones.get(i)).getMID().equals(MID)) {
                return (Milestone) milestones.get(i);
            }
        }
        return null;
    }
    
    
    public void updateMilestone(Milestone milestone) {
        for (int i=0; i < milestones.size(); i++) {
            //if we already have the employee we'll update it.
            if ( ((Milestone) milestones.get(i)).getMID().contains(milestone.getMID())) {
                milestones.remove(((Milestone) milestones.get(i)));
                
                milestones.add(milestone);
                return;
            }
            //if we get here then it's a new Milestone
            milestones.add(milestone);
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
