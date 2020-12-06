
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

/**
 *
 * @author TNM
 */
public class OtherDAO {

    private Connection con;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    public String getRole(int id) throws Exception {
        String role = "";
        try {
            String sql = "Select Role From TblRole Where RoleId = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if(rs.next())
                role = rs.getString("Role");
        } finally {
            closeConnection();
        }
        return role;
    }
    
    public String getStatus(int id) throws Exception {
        String status = "";
        try {
            String sql = "Select Status From TblStatus Where StatusId = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if(rs.next())
                status = rs.getString("Status");
        } finally {
            closeConnection();
        }
        return status;
    }
    
    public String getCategory(int id) throws Exception {
        String category = "";
        try {
            String sql = "Select Category From TblCategory Where CategoryId = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if(rs.next())
                category = rs.getString("Category");
        } finally {
            closeConnection();
        }
        return category;
    }
    
    public List<String> getCategoryList() throws Exception {
        List<String> result = null;
        try {
            String sql = "Select Category From TblCategory";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while(rs.next()) {
                result.add(rs.getString("Category"));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
