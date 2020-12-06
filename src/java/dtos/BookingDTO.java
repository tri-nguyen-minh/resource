
package dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author TNM
 */
public class BookingDTO implements Serializable {
    
    private String id, userId, userDescription, dateBook, status;
    private int statusId;
    private List<BookingDetailDTO> bookingList;
    
    public BookingDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getDateBook() {
        return dateBook;
    }

    public void setDateBook(String dateBook) {
        this.dateBook = dateBook;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public List<BookingDetailDTO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingDetailDTO> bookingList) {
        this.bookingList = bookingList;
    }
    
    public BookingDTO(String id, String userId, String dateBook, int statusId) {
        this.id = id;
        this.userId = userId;
        this.dateBook = dateBook;
        this.statusId = statusId;
    }
    
}
