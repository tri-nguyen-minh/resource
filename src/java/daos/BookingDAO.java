package daos;

import dtos.BookingDTO;
import dtos.BookingDetailDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import utils.MyConnection;
import utils.Utilities;

/**
 *
 * @author TNM
 */
public class BookingDAO implements Serializable {

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

    public HashMap<String, Integer> getBookingDetailWithDate(String dateStart, String dateEnd) throws Exception {
        HashMap<String, Integer> result = null;
        try {
            String sql = "Select ResourceId,Quantity From TblBookingDetail Where Id NOT IN "
                    + "(Select Id From TblBookingDetail Where (FromDate > ?) Or (ToDate < ?)) "
                    + "And BookingId IN (Select BookingId From TblBooking Where StatusId  = 1 Or StatusId = 4)";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dateEnd);
            preStm.setString(2, dateStart);
            rs = preStm.executeQuery();
            result = new HashMap<>();
            while (rs.next()) {
                String resourceId = rs.getString("ResourceId");
                int quantity = rs.getInt("Quantity");
                if (result.containsKey(resourceId)) {
                    quantity = result.get(resourceId) + quantity;
                    result.replace(resourceId, quantity);
                } else {
                    result.put(resourceId, quantity);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getAllBooking() throws Exception {
        int result = 0;
        try {
            String sql = "Select COUNT(BookingId) as Total From TblBooking";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("Total");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getAllBookingDetail() throws Exception {
        int result = 0;
        try {
            String sql = "Select COUNT(Id) as Total From TblBookingDetail";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("Total");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean addBookingRequest(String userId, List<BookingDetailDTO> bookingList) throws Exception {
        boolean result = false;
        int bookingIdNumber = getAllBooking() + 1;
        int detailIdNumber = getAllBookingDetail() + 1;
        Date date = Calendar.getInstance().getTime();
        String id = "B" + bookingIdNumber;
        String dateString = new Utilities().formatDateForDB(date);
        try {
            String sql = "Insert Into TblBooking(BookingId,UserId,BookDate,StatusId) "
                    + "values(?,?,?,?)";
            con = MyConnection.getConnection();
            con.setAutoCommit(false);
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            preStm.setString(2, userId);
            preStm.setString(3, dateString);
            preStm.setInt(4, 1);
            if (preStm.executeUpdate() > 0) {
                sql = "Insert Into TblBookingDetail(Id,BookingId,ResourceId,FromDate,ToDate,Quantity) "
                        + "values(?,?,?,?,?,?)";
                for (int i = 0; i < bookingList.size(); i++) {
                    BookingDetailDTO dto = bookingList.get(i);
                    preStm = con.prepareStatement(sql);
                    detailIdNumber += i;
                    String detailId = "D" + detailIdNumber;
                    preStm.setString(1, detailId);
                    preStm.setString(2, id);
                    preStm.setString(3, dto.getResourceId());
                    preStm.setString(4, dto.getDateStart());
                    preStm.setString(5, dto.getDateReturn());
                    preStm.setInt(6, dto.getQuantity());
                    preStm.executeUpdate();
                }
            }
            con.commit();
            result = true;
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<BookingDetailDTO> getBookingDetails(BookingDTO booking) throws Exception {
        List<BookingDetailDTO> result = null;
            String sql = "Select Id, ResourceId, FromDate, ToDate, Quantity From TblBookingDetail "
                    + "Where BookingId = ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, booking.getId());
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("Id");
                String resourceId = rs.getString("ResourceId");
                String dateStart = new Utilities().formatDateForPrint(rs.getString("FromDate"));
                String dateEnd = new Utilities().formatDateForPrint(rs.getString("ToDate"));
                int quantity = rs.getInt("Quantity");
                BookingDetailDTO dto = new BookingDetailDTO(id, booking.getId(), resourceId, dateStart, dateEnd, quantity);
                dto.setResourceDescription(new ResourceDAO().findResource(resourceId).getName());
                result.add(dto);
            }
        return result;
    }

    public List<BookingDTO> getUserBookingRequest(String userId, String resourceName, String bookDate) throws Exception {
        List<BookingDTO> result = null;
        try {
            String sql = "Select BookingId, BookDate, StatusId From TblBooking Where UserId = ? ";
            if (!resourceName.equals("")) {
                sql += "And BookingId IN (Select BookingId From TblBookingDetail Where ResourceId IN "
                        + "(Select Id From TblResource Where ItemName LIKE '%" + resourceName + "%')) ";
            }
            if (!bookDate.equals("")) {
                sql += "And BookDate = '" + bookDate + "' ";
            }
            sql += "Order By BookDate";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setString(1, userId);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String bookingId = rs.getString("BookingId");
                String dateBook = new Utilities().formatDateForPrint(rs.getString("BookDate"));
                int status = rs.getInt("StatusId");
                BookingDTO dto = new BookingDTO(bookingId, userId, dateBook, status);
                dto.setStatus(new OtherDAO().getStatus(status));
                result.add(dto);
            }
            for (int i = 0; i < result.size(); i++) {
                result.get(i).setBookingList(getBookingDetails(result.get(i)));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean ChangeRequestStatus(String bookingId, int status) throws Exception {
        boolean result = false;
        try {
            String sql = "Update TblBooking Set StatusId = ? Where BookingId = ?";
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, status);
            preStm.setString(2, bookingId);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalRequestPage(String resourceName, String userName,
            String dateLow, String dateHigh,
            int status) throws Exception {
        int total = 0;
        try {
            String sql = "Select COUNT(BookingId) as Total From TblBooking Where "
                    + "UserId IN "
                    + "(Select Id From TblAccount Where name LIKE '%" + userName + "%') "
                    + "And BookingId IN "
                    + "(Select BookingId From TblBookingDetail Where ResourceId IN "
                    + "(Select Id From TblResource Where ItemName LIKE '%" + resourceName + "%') ";
            if (!dateLow.equals("")) {
                sql += "And FromDate >= '" + dateLow + "' ";
            }
            if (!dateHigh.equals("")) {
                sql += "And FromDate <= '" + dateHigh + "'";
            }
            sql += ") ";
            if (status != 0) {
                sql += "And StatusId = " + status + " ";
            } else {
                sql += "And StatusId != 3 ";
            }
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
                if ((total % 2) == 0) {
                    total = total / 2;
                } else {
                    total = (total / 2) + 1;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public List<BookingDTO> searchRequest(String resourceName, String userName,
            String dateLow, String dateHigh,
            int status, int page) throws Exception {
        List<BookingDTO> result = null;
        String mainSQL = "Select Top(2) BookingId, UserId, BookDate, StatusId From TblBooking Where "
                + "UserId IN "
                + "(Select Id From TblAccount Where name LIKE '%" + userName + "%') "
                + "And BookingId IN "
                + "(Select BookingId From TblBookingDetail Where ResourceId IN "
                + "(Select Id From TblResource Where ItemName LIKE '%" + resourceName + "%') ";
        String connector = " And BookingId NOT IN ";
        String subSQL = "Select Top(" + (page - 1) * 2 + ") BookingId From TblBooking Where "
                + "UserId IN "
                + "(Select Id From TblAccount Where name LIKE '%" + userName + "%') "
                + "And BookingId IN "
                + "(Select BookingId From TblBookingDetail Where ResourceId IN  "
                + "(Select Id From TblResource Where ItemName LIKE '%" + resourceName + "%') ";
        String orderMethod = " Order by BookDate";
        if (!dateLow.equals("")) {
            mainSQL += "And FromDate >= '" + dateLow + "' ";
            subSQL += "And FromDate >= '" + dateLow + "' ";
        }
        if (!dateHigh.equals("")) {
            mainSQL += "And FromDate <= '" + dateHigh + "'";
            subSQL += "And FromDate <= '" + dateHigh + "'";
        }
        mainSQL += ") ";
        subSQL += ") ";
        if (status != 0) {
            mainSQL += "And StatusId = " + status + " ";
            subSQL += "And StatusId = " + status + " ";
        } else {
            mainSQL += "And StatusId != 3 ";
            subSQL += "And StatusId != 3 ";
        }
        String sql = mainSQL + connector + "(" + subSQL + orderMethod + ")" + orderMethod;
        try {
            con = MyConnection.getConnection();
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                BookingDTO dto = new BookingDTO();
                dto.setId(rs.getString("BookingId"));
                dto.setDateBook(new Utilities().formatDateForPrint(rs.getString("BookDate")));
                String userId = rs.getString("UserId");
                dto.setUserId(userId);
                String userDescription = new AccountDAO().getUserDescription(userId);
                dto.setUserDescription(userDescription);
                int statusId = rs.getInt("StatusId");
                dto.setStatusId(statusId);
                dto.setStatus(new OtherDAO().getStatus(statusId));
                result.add(dto);
            }
            for (int i = 0; i < result.size(); i++) {
                result.get(i).setBookingList(getBookingDetails(result.get(i)));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
