package actions;

import com.opensymphony.xwork2.ActionSupport;
import daos.BookingDAO;
import dtos.BookingDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author TNM
 */
public class SearchRequestAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(SearchRequestAction.class);
    private static final String SUCCESS = "success";
    private String resource, user, dateBookLow, dateBookHigh, status, pageString;

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

    public SearchRequestAction() {
    }

    public String execute() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            BookingDAO dao = new BookingDAO();
            int requestStatus = Integer.parseInt(status);
            int page = Integer.parseInt(pageString);
            List<BookingDTO> requestList = dao.searchRequest(resource, user,
                    dateBookLow, dateBookHigh, requestStatus, page);
            request.setAttribute("REQUESTLIST", requestList);
            request.setAttribute("TOTALPAGE", dao.getTotalRequestPage(resource, user,
                    dateBookLow, dateBookHigh, requestStatus));
            request.setAttribute("RESOURCE", resource);
            request.setAttribute("USERNAME", user);
            request.setAttribute("DATELOW", dateBookLow);
            request.setAttribute("DATEHIGH", dateBookHigh);
            request.setAttribute("STATUS", status);
            request.setAttribute("PAGE", page);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return SUCCESS;
    }

}
