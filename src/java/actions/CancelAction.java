
package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import daos.BookingDAO;
import dtos.AccountDTO;
import dtos.BookingDTO;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author TNM
 */
public class CancelAction extends ActionSupport {
    
    private static final Logger LOGGER = Logger.getLogger(CancelAction.class);
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private String bookingId, resource, dateBook;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDateBook() {
        return dateBook;
    }

    public void setDateBook(String dateBook) {
        this.dateBook = dateBook;
    }
    
    public CancelAction() {
    }
    
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            Map session = ActionContext.getContext().getSession();
        AccountDTO user = (AccountDTO)session.get("USER");
        BookingDAO dao = new BookingDAO();
        if(dao.ChangeRequestStatus(bookingId,3)) {
            List<BookingDTO> requestList = dao.getUserBookingRequest(user.getEmail(),"","");
            session.put("REQUESTLIST", requestList);
            session.put("REQUESTLISTSIZE", requestList.size());
        }
        } catch (Exception e) {
            label = ERROR;
            LOGGER.error(e.getMessage());
        }
        
        return label;
    }
    
}
