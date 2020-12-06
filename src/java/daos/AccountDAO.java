package daos;

import dtos.AccountDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.MyConnection;

/**
 *
 * @author TNM
 */
public class AccountDAO implements Serializable {

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

    public AccountDTO getAccount(String id, String password) throws Exception {
        AccountDTO result = null;
        try {
            String sql = "Select Name, RoleId, StatusId From TblAccount Where Id = ? And Password = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = new AccountDTO();
                result.setEmail(id);
                result.setPassword(password);
                result.setName(rs.getString("Name"));
                result.setRole(rs.getInt("RoleId"));
                result.setStatus(rs.getInt("StatusId"));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public String getUserDescription(String id) throws Exception {
        String userDescription = "";
        try {
            String sql = "Select Name, RoleId From TblAccount Where Id = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                userDescription += rs.getString("Name");
                int roleId = rs.getInt("RoleId");
                userDescription = new OtherDAO().getRole(roleId) + " - " + userDescription;
            }
        } finally {
            closeConnection();
        }
        return userDescription;
    }
}
