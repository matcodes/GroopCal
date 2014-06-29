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

public class Event {

    private String mEID = "";
    private String mName = "";
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    

    
    public Event() {
    }
    
    public Event(Connection con) {
        mCon = con;
    }

    public Event(Connection con, String EID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Event ct where EID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mEID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mEID = rset.getString("EID");
                mName = rset.getString("Name");
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
            System.out.println("Event:Constructor(2.1): " + e.getMessage());
        }
    }

    public Event(ResultSet rset) {
        try {
            mEID = rset.getString("EID");
            mName = rset.getString("Name");

            return;
        }
        catch (Exception e) {
            System.out.println("Event:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateEvent() {
        try {
            openConnection();

            String sql = "Update Event set Name = ? WHERE EID = ?";
            if (mEID.isEmpty() )
                sql = "INSERT INTO Event (Name, EID) VALUES (?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mName);
            
            if (!mEID.isEmpty())
                ptNew.setString(10, mEID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mEID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select EID from Event where Name = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mName);
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mEID = rset.getString("EID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Event:updateEvent: " + e.getMessage());
        }
    }

    //getters
    public String getEID() {
        return mEID;
    }

    public String getName() {
        return mName;
    }
    
    //setters
    public void setName(String s) {
        mName = s;
    }
    
    private void openConnection() {
        try {
            if (mCon == null || mCon.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                mCon = DriverManager.getConnection(Helpers.getDBCx(), Helpers.getDBUser(), Helpers.getDBPass());
            }
        }
        catch (Exception ec) {
            System.out.println("Event:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Event:closeConnection: " + ec.getMessage());
        }
    }        
}
