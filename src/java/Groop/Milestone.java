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

public class Milestone {

    private String mMID = "";
    private String mName = "";
    private java.util.Date mStartDate = null;
    private java.util.Date mDueDate = null;
    private String mPriority = "";
    private String mCID = "";// Campaign associated with this milestone
    private String mStatus = "";
    private int mDuration = -1;
    private java.util.Date mInTheaterDate = null;
    private java.util.Date mTrailerTargetDate = null;
    private String mNotes = "";
    private String mLink = "";
    
    private CheckLists checklists = null;
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    
    public Milestone() {
    }
    
    public Milestone(Connection con) {
        mCon = con;
    }

    public Milestone(Connection con, String MID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Milestone where MID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mMID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mMID = rset.getString("MID");
                mName = rset.getString("Name");
                mStartDate = rset.getDate("StartDate");
                mDueDate = rset.getDate("DueDate");
                mPriority = rset.getString("Priority");
                mCID = rset.getString("CID");
                mStatus = rset.getString("Status");
                mDuration = rset.getInt("Duration");
                mInTheaterDate = rset.getDate("In_Theater_Date");
                mTrailerTargetDate = rset.getDate("Trailer_Target_Date");
                mNotes = rset.getString("Notes");
                mLink = rset.getString("Link");
                rset.close();
            }
            else {
                rset.close();
            }
            
            closeConnection();
            pt.close();
            
            checklists = new CheckLists();
            checklists.loadChecklists(mMID); //load checklists for milestones

            return;
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Milestone:Constructor(2.1): " + e.getMessage());
        }
    }

    public Milestone(ResultSet rset) {
        try {
                mMID = rset.getString("MID");
                mName = rset.getString("Name");
                mStartDate = rset.getDate("StartDate");
                mDueDate = rset.getDate("DueDate");
                mPriority = rset.getString("Priority");
                mCID = rset.getString("CID");
                mStatus = rset.getString("Status");
                mDuration = rset.getInt("Duration");
                mInTheaterDate = rset.getDate("In_Theater_Date");
                mTrailerTargetDate = rset.getDate("Trailer_Target_Date");
                mNotes = rset.getString("Notes");
                mLink = rset.getString("Link");

            return;
        }
        catch (Exception e) {
            System.out.println("Milestone:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateMilestone() {
        try {
            openConnection();

            String sql = "Update Milestone set Name = ?, StartDate = ?, DueDate = ?, Priority = ?, CID = ?, Status = ?, Duration = ?, In_Theater_Date = ?, Trailer_Target_Date = ?, Notes = ?, Link = ? WHERE MID = ?";
            if (mMID.isEmpty() )
                sql = "INSERT INTO Milestone (Name, StartDate, DueDate, Priority, CID, Status, Duration, In_Theater_Date, Trailer_Target_Date, Notes, Link, MID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mName);
            ptNew.setDate(2, new java.sql.Date(mStartDate.getTime()));
            ptNew.setDate(3, new java.sql.Date(mDueDate.getTime()));
            ptNew.setString(4, mPriority);
            ptNew.setString(5, mCID);
            ptNew.setString(6, mStatus);
            ptNew.setInt(7, mDuration);
            ptNew.setDate(8, new java.sql.Date(mInTheaterDate.getTime()));
            ptNew.setDate(9, new java.sql.Date(mTrailerTargetDate.getTime()));
            ptNew.setString(10, mNotes);
            ptNew.setString(11, mLink);
            
            if (!mMID.isEmpty())
                ptNew.setString(10, mMID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mMID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select MID from Milestone where Name = ? AND StartDate = ? AND DueDate = ? AND CID = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mName);
                ptNew.setDate(2, new java.sql.Date(mStartDate.getTime()));
                ptNew.setDate(3, new java.sql.Date(mDueDate.getTime()));
                ptNew.setString(4, mCID);
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mMID = rset.getString("MID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Milestone:updateMilestone: " + e.getMessage());
        }
    }

    //getters
    public String getMID() {
        return mMID;
    }

    public String getName() {
        return mName;
    }
    
    public java.util.Date getStartDate() {
        return mStartDate;
    }
    
    public java.util.Date getDueDate() {
        return mDueDate;
    }
    
    public String getPriority() {
        return mPriority;
    }
    
    public String getCID() {
        return mCID;
    }
    
    public String getStatus() {
        return mStatus;
    }
    
    public int getDuration() {
        return mDuration;
    }
    
    public java.util.Date getInTheaterDate() {
        return mInTheaterDate;
    }
    
    public java.util.Date getTrailerTargetDate() {
        return mTrailerTargetDate;
    }
    
    public String getNotes() {
        return mNotes;
    }
    
    public String getLink() {
        return mLink;
    }
    
    //setters
    public void setName(String s) {
        mName = s;
    }
    
    public void setStartDate(java.util.Date d) {
        mStartDate = d;
    }
    
    public void setDueDate(java.util.Date d) {
        mDueDate = d;
    }
    
    public void setPriority(String s) {
        mPriority = s;
    }
    
    public void setCID(String s) {
        mCID = s;
    }
    
    public void setStatus(String s) {
        mStatus = s;
    }
    
    public void setDuration(int i) {
        mDuration = i;
    }
    
    public void setInTheaterDate(java.util.Date d) {
        mInTheaterDate = d;
    }
    
    public void setTrailerTargetDate(java.util.Date d) {
        mTrailerTargetDate = d;
    }
    
    public String setNotes() {
        return mNotes;
    }
    
    public String setLink() {
        return mLink;
    }
    
    private void openConnection() {
        try {
            if (mCon == null || mCon.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                mCon = DriverManager.getConnection(Helpers.getDBCx(), Helpers.getDBUser(), Helpers.getDBPass());
            }
        }
        catch (Exception ec) {
            System.out.println("Milestone:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Milestone:closeConnection: " + ec.getMessage());
        }
    }     }
