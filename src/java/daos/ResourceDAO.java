package daos;

import dtos.ResourceDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;
import utils.Utilities;

/**
 *
 * @author TNM
 */
public class ResourceDAO implements Serializable {

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

    public ResourceDAO() {
    }

    public ResourceDTO findResource(String id) throws Exception {
        ResourceDTO dto = null;
        try {
            String sql = "Select ItemName,RoleId From TblResource Where Id = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                dto = new ResourceDTO();
                dto.setId(id);
                dto.setName(rs.getString("ItemName"));
                dto.setRoleLevel(rs.getInt("RoleId"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public int getTotalSearchPage(String search, int category, int role) throws Exception {
        int total = 0;
        try {
            String sql = "Select COUNT(iD) as Total From TblResource "
                    + "Where ItemName LIKE '%" + search + "%' And RoleId >= " + role;
            if (category > 0) {
                sql += " And CategoryId = " + category;
            }
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
                if ((total % 10) == 0) {
                    total = total / 10;
                } else {
                    total = (total / 10) + 1;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public List<ResourceDTO> getResourceList(String search, int category,
            String dateStart, String dateEnd,
            int role, int page) throws Exception {
        List<ResourceDTO> result = null;
        try {
            if (getTotalSearchPage(search, category, role) > 0) {
                String mainSQL = "Select Top(10) Id,ItemName,Color,CategoryId,RoleId,Quantity From TblResource "
                        + "Where ItemName LIKE '%" + search + "%' And RoleId >= " + role;
                String subSQL = "Select top(" + (page - 1) * 5 + ") Id From TblResource "
                        + "Where ItemName LIKE '%" + search + "%' And RoleId >= " + role;
                String sortMethod = " Order by ItemName";
                if (category > 0) {
                    mainSQL += " And CategoryId = " + category;
                    subSQL += " And CategoryId = " + category;
                }
                String sql = mainSQL + "And Id NOT IN (" + subSQL + sortMethod + ") " + sortMethod;
                con = MyConnection.getConnection();
                preStm = con.prepareStatement(sql);
                rs = preStm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    ResourceDTO resource = new ResourceDTO();
                    resource.setId(rs.getString("Id"));
                    resource.setName(rs.getString("ItemName"));
                    resource.setColor(rs.getString("Color"));
                    int resourceCategory = rs.getInt("CategoryId");
                    resource.setCategory((new OtherDAO()).getCategory(resourceCategory));
                    resource.setRoleLevel(rs.getInt("RoleId"));
                    int quantity = rs.getInt("Quantity");
                    resource.setQuantity(quantity);
                    resource.setAmountAvailable(quantity);
                    result.add(resource);
                }
                if (result.size() > 0) {
                    result = new Utilities().manageSearchList(result,
                            new BookingDAO().getBookingDetailWithDate(dateStart, dateEnd));
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

}
