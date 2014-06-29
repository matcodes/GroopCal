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

public class Departments {

    private String mDID = "";
    private String mName = "";
    private String mEmail = "";
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    

    
    public Departments() {
    }
    
    public Departments(Connection con) {
        mCon = con;
    }

    public Departments(Connection con, String DID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Departments ct where DID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mDID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mDID = rset.getString("DID");
                mName = rset.getString("Name");
                mEmail = rset.getString("PrimaryEmail");
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
            System.out.println("Departments:Constructor(2.1): " + e.getMessage());
        }
    }

    public Departments(ResultSet rset) {
        try {
            mDID = rset.getString("DID");
            mName = rset.getString("Name");
            mEmail = rset.getString("PrimaryEmail");

            return;
        }
        catch (Exception e) {
            System.out.println("Departments:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateDepartments() {
        try {
            openConnection();

            String sql = "Update Departments set Name = ?, PrimaryEmail = ? WHERE DID = ?";
            if (mDID.isEmpty() )
                sql = "INSERT INTO Departments (Name, PrimaryEmail, DID) VALUES (?, ?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mName);
            ptNew.setString(2, mEmail);
            
            if (!mDID.isEmpty())
                ptNew.setString(3, mDID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mDID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select DID from Departments where Name = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mName);
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mDID = rset.getString("DID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Departments:updateDepartments: " + e.getMessage());
        }
    }

    //getters
    public String getDID() {
        return mDID;
    }

    public String getName() {
        return mName;
    }
    
    public String getEmail() {
        return mEmail;
    }
    
    //setters
    public void setName(String s) {
        mName = s;
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
            System.out.println("Departments:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Departments:closeConnection: " + ec.getMessage());
        }
    }     }
