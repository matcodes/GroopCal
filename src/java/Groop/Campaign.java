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

public class Campaign {
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    
    private String mCID = "";
    private String mTitle = "";
    private String mVisual = "";
    private java.util.Date mUSRelease = null;
    private String mReleaseTimeframe = "";
    private int mReleaseYear = -1;
    private String mMovieType = "";
    private String mTargetAudience = "";
    private String mSecondaryAudience = "";
    private String mSecurityTitle = "";
    private String mNotes = "";
    private String mStatus = "";
    private Milestones milestones = null;
    
    
    public Campaign() {
    }
    
    public Campaign(Connection con) {
        mCon = con;
    }

    public Campaign(Connection con, String cid) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Campaign c order by US_Release";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, cid);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mCID = rset.getString("CID");
                mTitle = rset.getString("Title");
                mVisual = rset.getString("Visual");
                mUSRelease = rset.getDate("US_Release");
                mReleaseTimeframe = rset.getString("Release_Timeframe");
                mReleaseYear = rset.getInt("Release_Year");
                mMovieType = rset.getString("Movie_Type");
                mTargetAudience = rset.getString("Target_Audience");
                mSecondaryAudience = rset.getString("Secondary_Audience");
                mSecurityTitle = rset.getString("Security_Title");
                mNotes = rset.getString("Notes");
                mStatus = rset.getString("Status");
                rset.close();
            }
            else {
                rset.close();
            }
            
            closeConnection();
            pt.close();

            milestones = new Milestones();
            milestones.loadMilestones(mCID);
            
            return;
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Campaign:Constructor(2.1): " + e.getMessage());
        }
    }

    public Campaign(ResultSet rset) {
        try {
            mCID = rset.getString("CID");
            mTitle = rset.getString("Title");
            mVisual = rset.getString("Visual");
            mUSRelease = rset.getDate("US_Release");
            mReleaseTimeframe = rset.getString("Release_Timeframe");
            mReleaseYear = rset.getInt("Release_Year");
            mMovieType = rset.getString("Movie_Type");
            mTargetAudience = rset.getString("Target_Audience");
            mSecondaryAudience = rset.getString("Secondary_Audience");
            mSecurityTitle = rset.getString("Security_Title");
            mNotes = rset.getString("Notes");
            mStatus = rset.getString("Status");

            milestones = new Milestones();
            milestones.loadMilestones(mCID);
            
            return;
        }
        catch (Exception e) {
            System.out.println("Campaign:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateCampaign() {
        try {
            openConnection();

            String sql = "Update Campaign set Title = ?, Visual = ?, US_Release = ?, Release_Timeframe = ?, Release_Year = ?, Movie_Type = ?, Target_Audience = ?, Secondary_Audience = ?, Security_Title = ?, Notes = ?, Status = ? WHERE CID = ?";
            if (mCID.isEmpty() )
                sql = "INSERT INTO Campaign (Title, Visual, US_Release, Release_Timeframe, Release_Year, Movie_Type, Target_Audience, Secondary_Audience, Security_Title, Notes, Status, CID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mTitle);
            ptNew.setString(2, mVisual);
            ptNew.setDate(3, new java.sql.Date(mUSRelease.getTime()));
            ptNew.setString(4, mReleaseTimeframe);
            ptNew.setInt(5, mReleaseYear);
            ptNew.setString(6, mMovieType);
            ptNew.setString(7, mTargetAudience);
            ptNew.setString(8, mSecondaryAudience);
            ptNew.setString(9, mSecurityTitle);
            ptNew.setString(10, mNotes);
            ptNew.setString(11, mStatus);
            
            if (!mCID.isEmpty())
                ptNew.setString(12, mCID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mCID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select CID from Campaign where Title = ? AND US_Release = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mTitle);
                ptNew.setDate(2, new java.sql.Date(mUSRelease.getTime()));
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mCID = rset.getString("CID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Campaign:updateCampaign: " + e.getMessage());
        }
    }
    
    //getters
    public String getCID() {
        return mCID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getVisual() {
        return mVisual;
    }

    public java.util.Date getUSRelease() {
        return mUSRelease;
    }

    public String getReleaseTimeframe() {
        return mReleaseTimeframe;
    }

    public int getReleaseYear() {
        return mReleaseYear;
    }

    public String getMovieType() {
        return mMovieType;
    }

    public String getTargetAudience() {
        return mTargetAudience;
    }
    
    public String getSecondaryAudience() {
        return mSecondaryAudience;
    }
    
    public String getSecurityTitle() {
        return mSecurityTitle;
    }
    
    public String getNotes() {
        return mNotes;
    }
    
    public String getStatus() {
        return mStatus;
    }

    
    //setters
    public void setTitle(String s) {
        mTitle = s;
    }

    public void setVisual(String s) {
        mVisual = s;
    }

    public void setUSRelease(java.util.Date d) {
        mUSRelease = d;
    }

    public void setReleaseTimeframe(String s) {
        mReleaseTimeframe = s;
    }

    public void setReleaseYear(int i) {
        mReleaseYear = i;
    }

    public void setMovieType(String s) {
        mMovieType = s;
    }

    public void setTargetAudience(String s) {
        mTargetAudience = s;
    }
    
    public void setSecondaryAudience(String s) {
        mSecondaryAudience = s;
    }
    
    public void setSecurityTitle(String s) {
        mSecurityTitle = s;
    }
    
    public void setNotes(String s) {
        mNotes = s;
    }
    
    public void setStatus(String s) {
        mStatus = s;
    }
    
    private void openConnection() {
        try {
            if (mCon == null || mCon.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                mCon = DriverManager.getConnection(Helpers.getDBCx(), Helpers.getDBUser(), Helpers.getDBPass());
            }
        }
        catch (Exception ec) {
            System.out.println("Campaign:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Campaign:closeConnection: " + ec.getMessage());
        }
    }    
}
