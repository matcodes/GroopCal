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

public class Film {

    private String mFID = "";
    private String mName = "";
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    

    
    public Film() {
    }
    
    public Film(Connection con) {
        mCon = con;
    }

    public Film(Connection con, String FID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM Film ct where FID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mFID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mFID = rset.getString("FID");
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
            System.out.println("Film:Constructor(2.1): " + e.getMessage());
        }
    }

    public Film(ResultSet rset) {
        try {
            mFID = rset.getString("FID");
            mName = rset.getString("Name");

            return;
        }
        catch (Exception e) {
            System.out.println("Film:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateFilm() {
        try {
            openConnection();

            String sql = "Update Film set Name = ? WHERE FID = ?";
            if (mFID.isEmpty() )
                sql = "INSERT INTO Film (Name, FID) VALUES (?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mName);
            
            if (!mFID.isEmpty())
                ptNew.setString(10, mFID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mFID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select FID from Film where Name = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mName);
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mFID = rset.getString("FID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("Film:updateFilm: " + e.getMessage());
        }
    }

    //getters
    public String getFID() {
        return mFID;
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
            System.out.println("Film:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("Film:closeConnection: " + ec.getMessage());
        }
    }     }
