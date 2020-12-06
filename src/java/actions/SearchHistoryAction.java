package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import daos.BookingDAO;
import dtos.AccountDTO;
import dtos.BookingDTO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author TNM
 */
public class SearchHistoryAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(SearchHistoryAction.class);
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private String resource, dateBook;

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

    public SearchHistoryAction() {
    }

    public String execute() throws Exception {
        try {
            if (resource == null) {
                resource = "";
            }
            if (dateBook == null) {
                dateBook = "";
            }
            HttpServletRequest request = ServletActionContext.getRequest();
            Map session = ActionContext.getContext().getSession();
            BookingDAO dao = new BookingDAO();
            AccountDTO user = (AccountDTO) session.get("USER");
            List<BookingDTO> requestList = dao.getUserBookingRequest(user.getEmail(), resource, dateBook);
            session.put("REQUESTLIST", requestList);
            session.put("REQUESTLISTSIZE", requestList.size());
            request.setAttribute("RESOURCE", resource);
            request.setAttribute("DATE", dateBook);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return SUCCESS;
    }

}
