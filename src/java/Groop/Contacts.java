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

public class Contacts {

    private String mCID = "";
    private String mDID = "";
    private String mFirstName = "";
    private String mLastName = "";
    private String mEmail = "";
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    

    
    public Contacts() {
    }
    
    public Contacts(Connection con) {
        mCon = con;
    }

    public Contacts(Connection con, String DID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Contacts ct where CID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mCID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mCID = rset.getString("CID");
                mDID = rset.getString("DID");
                mFirstName = rset.getString("FirstName");
                mLastName = rset.getString("LastName");
                mEmail = rset.getString("Email");
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
            System.out.println("Contacts:Constructor(2.1): " + e.getMessage());
        }
    }

    public Contacts(ResultSet rset) {
        try {
            mCID = rset.getString("CID");
            mDID = rset.getString("DID");
            mFirstName = rset.getString("FirstName");
            mLastName = rset.getString("LastName");
            mEmail = rset.getString("Email");

            return;
        }
        catch (Exception e) {
            System.out.println("Contacts:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateContacts() {
        try {
            openConnection();

            String sql = "Update Contacts set FirstName = ?, LastName = ?, Email = ? WHERE CID = ?";
            if (mDID.isEmpty() )
                sql = "INSERT INTO Contacts (FirstName, LastName, Email, DID) VALUES (?, ?, ?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mFirstName);
            ptNew.setString(2, mLastName);
            ptNew.setString(3, mEmail);
            
            if (!mDID.isEmpty())
                ptNew.setString(3, mCID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mDID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select CID from Contacts where FirstName = ? AND LastName = ? AND Email = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mFirstName);
                ptNew.setString(2, mLastName);
                ptNew.setString(3, mEmail);
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
            System.out.println("Contacts:updateContacts: " + e.getMessage());
        }
    }

    //getters
    public String getDID() {
        return mDID;
    }

    public String getFirstName() {
        return mFirstName;
    }
    
    public String getLastName() {
        return mLastName;
    }
    
    public String getEmail() {
        return mEmail;
    }
    
    //setters
    public void setFirstName(String s) {
        mFirstName = s;
    }
    
    public void setLastName(String s) {
        mLastName = s;
    }
    
    public void setEmail(String s) {
        mEmail = s;
    }
    
    private void openConnection() {
        try {
            if (mCon == null || mCon.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                mCon = DriverManager.getConnection(Helpers.getDBCx(), Helpers.getDBUser(), Helpers.getDBPass());
            }
        }
        catch (Exception ec) {
            System.out.println("Contacts:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Contacts:closeConnection: " + ec.getMessage());
        }
    }     }
