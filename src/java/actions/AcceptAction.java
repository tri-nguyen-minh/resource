
package actions;

import com.opensymphony.xwork2.ActionSupport;
import daos.BookingDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author TNM
 */
public class AcceptAction extends ActionSupport {
    
    private static final Logger LOGGER = Logger.getLogger(AcceptAction.class);
    private static final String RELOAD = "reload";
    private String resource, user, dateBookLow, dateBookHigh, status, pageString;
    private String bookingId;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateBookLow() {
        return dateBookLow;
    }

    public void setDateBookLow(String dateBookLow) {
        this.dateBookLow = dateBookLow;
    }

    public String getDateBookHigh() {
        return dateBookHigh;
    }

    public void setDateBookHigh(String dateBookHigh) {
        this.dateBookHigh = dateBookHigh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPageString() {
        return pageString;
    }

    public void setPageString(String pageString) {
        this.pageString = pageString;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    
    public AcceptAction() {
    }
    
    public String execute() throws Exception {
        String label = RELOAD;
        try {
        BookingDAO dao = new BookingDAO();
        dao.ChangeRequestStatus(bookingId, 4);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return label;
    }
    
}
