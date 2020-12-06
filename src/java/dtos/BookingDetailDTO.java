
package dtos;

import java.io.Serializable;

/**
 *
 * @author TNM
 */
public class BookingDetailDTO implements Serializable {

    private String id, bookingId, resourceId, resourceDescription, dateStart, dateReturn;
    private int quantity;
    
    public BookingDetailDTO() {
    }

    public BookingDetailDTO(String id, String bookingId, String resourceId, String dateStart, String dateReturn, int quantity) {
        this.id = id;
        this.bookingId = bookingId;
        this.resourceId = resourceId;
        this.dateStart = dateStart;
        this.dateReturn = dateReturn;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
