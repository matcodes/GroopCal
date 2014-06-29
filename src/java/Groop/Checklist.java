package Groop;

import java.sql.DriverManager;
import java.sql.Date;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Checklist {

    private String mCLID = "";
    private String mMID = "";// Checklist associated with this Checklist Item
    private int mAlert = -1;
    private int mComplete = -1;
    private String mTask = "";
    private String mNotes = "";
    private String mOwner = "";
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    
    public Checklist() {
    }
    
    public Checklist(Connection con) {
        mCon = con;
    }

    public Checklist(Connection con, String MID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Checklist where CLID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mCLID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mCLID = rset.getString("CLID");
                mMID = rset.getString("CMID");
                mAlert = rset.getInt("Alert");
                mComplete = rset.getInt("Complete");
                mTask = rset.getString("Task");
                mNotes = rset.getString("Notes");
                mOwner = rset.getString("Owner");
                rset.close();
            }
            else {
                rset.close();
            }
            
            closeConnection();
            pt.close();

            return;
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Checklist:Constructor(2.1): " + e.getMessage());
        }
    }

    public Checklist(ResultSet rset) {
        try {
            mCLID = rset.getString("CLID");
            mMID = rset.getString("CMID");
            mAlert = rset.getInt("Alert");
            mComplete = rset.getInt("Complete");
            mTask = rset.getString("Task");
            mNotes = rset.getString("Notes");
            mOwner = rset.getString("Owner");

            return;
        }
        catch (Exception e) {
            System.out.println("Checklist:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateChecklist() {
        try {
            openConnection();

            String sql = "Update Checklist set MID = ?, Alert = ?, Complete = ?, Task = ?, Notes = ?, Owner = ? WHERE CLID = ?";
            if (mMID.isEmpty() )
                sql = "INSERT INTO Checklist (MID, Alert, Complete, Task, Notes, Owner, CLID) VALUES (?, ?, ?, ?, ?, ?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mMID);
            ptNew.setInt(2, mAlert);
            ptNew.setInt(3, mComplete);
            ptNew.setString(4, mTask);
            ptNew.setString(5, mNotes);
            ptNew.setString(6, mOwner);
            
            if (!mCLID.isEmpty())
                ptNew.setString(7, mCLID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mCLID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select CLID from Checklist where Task = ? AND MID = ? AND Notes = ? AND Owner = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mTask);
                ptNew.setString(2, mMID);
                ptNew.setString(3, mNotes);
                ptNew.setString(4, mOwner);
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mCLID = rset.getString("CLID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Checklist:updateChecklist: " + e.getMessage());
        }
    }

    //getters
    public String getCLID() {
        return mCLID;
    }

    public String getMID() {
        return mMID;
    }

    public int getAlert() {
        return mAlert;
    }
    
    public int getComplete() {
        return mComplete;
    }

    public String getTask() {
        return mTask;
    }
    
    public String getNotes() {
        return mNotes;
    }
    
    public String getOwner() {
        return mOwner;
    }
        
    //setters
    public void setMID(String s) {
        mMID = s;
    }
    
    public void setAlert(int i) {
        mAlert = i;
    }
    
    public void setComplete(int i) {
        mComplete = i;
    }
    
    public void setTask(String s) {
        mTask = s;
    }
    
    public String setNotes() {
        return mNotes;
    }
    
    public String setOwner() {
        return mOwner;
    }
    
    private void openConnection() {
        try {
            if (mCon == null || mCon.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                mCon = DriverManager.getConnection(Helpers.getDBCx(), Helpers.getDBUser(), Helpers.getDBPass());
            }
        }
        catch (Exception ec) {
            System.out.println("Checklist:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Checklist:closeConnection: " + ec.getMessage());
        }
    }     }
