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

public class ItemTypes {

    private String mTID = "";
    private String mName = "";
    
    //connection object, retrieved from Helpers class
    private Connection mCon = null;
    

    
    public ItemTypes() {
    }
    
    public ItemTypes(Connection con) {
        mCon = con;
    }

    public ItemTypes(Connection con, String TID) {
        try {
            mCon = con;
            
            //it will be null if it's the first instance
            openConnection();

            String query = "select * FROM ItemTypes ct where TID = ?";
            PreparedStatement pt = mCon.prepareStatement(query);
            pt.setString(1, mTID);
            ResultSet rset = null;
            rset = pt.executeQuery();
            if (rset.next()) {
                mTID = rset.getString("TID");
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
            System.out.println("ItemTypes:Constructor(2.1): " + e.getMessage());
        }
    }

    public ItemTypes(ResultSet rset) {
        try {
            mTID = rset.getString("TID");
            mName = rset.getString("Name");

            return;
        }
        catch (Exception e) {
            System.out.println("ItemTypes:Constructor(3): " + e.getMessage());
        }
    }
    
    public void updateItemTypes() {
        try {
            openConnection();

            String sql = "Update ItemTypes set Name = ? WHERE TID = ?";
            if (mTID.isEmpty() )
                sql = "INSERT INTO ItemTypes (Name, TID) VALUES (?, UUID())";

            PreparedStatement ptNew = mCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ptNew.setString(1, mName);
            
            if (!mTID.isEmpty())
                ptNew.setString(10, mTID);

            ptNew.executeUpdate();
            ptNew.close();

            if (mTID.isEmpty()) {
                //get the record ID of the newly added Hospital
                /*
                ResultSet rset = ptNew.getGeneratedKeys();
                if (rset.first())
                    mUID = rset.getString(1);
                 */
                String sSql = "select TID from ItemTypes where Name = ?";
                PreparedStatement pt = mCon.prepareStatement(sSql);
                ptNew.setString(1, mName);
                ResultSet rset = pt.executeQuery();

                if (rset.next()) {
                    mTID = rset.getString("TID");
                }

                rset.close();
                pt.close();
            }
            closeConnection();
        }
        catch (Exception e) {
            closeConnection();
            System.out.println("ItemTypes:updateItemTypes: " + e.getMessage());
        }
    }

    //getters
    public String getTID() {
        return mTID;
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
            System.out.println("ItemTypes:openConnection: " + ec.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (!mCon.isClosed())
                mCon.close();
        }
        catch (Exception ec) {
            System.out.println("ItemTypes:closeConnection: " + ec.getMessage());
        }
    }     
}
