package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import daos.ResourceDAO;
import dtos.AccountDTO;
import dtos.BookingDetailDTO;
import dtos.ResourceDTO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import utils.Utilities;

/**
 *
 * @author TNM
 */
public class BookingAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(BookingAction.class);
    private static final String RELOAD = "reload";
    private static final String FAIL = "unauthorized";
    private String resourceName, dateStart, dateEnd, categoryString, pageString,
            resourceId, amountBook;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public String getPageString() {
        return pageString;
    }

    public void setPageString(String pageString) {
        this.pageString = pageString;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAmountBook() {
        return amountBook;
    }

    public void setAmountBook(String amountBook) {
        this.amountBook = amountBook;
    }

    public BookingAction() {
    }

    public String execute() throws Exception {
        String label = RELOAD;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Map session = ActionContext.getContext().getSession();
            AccountDTO user = (AccountDTO) session.get("USER");
            ResourceDTO resource = (new ResourceDAO().findResource(resourceId));
            if (user.getRole() == 1) {
                label = FAIL;
                request.setAttribute("ERROR", "The current account is not authorized to book the resource.");
            } else if (user.getRole() > resource.getRoleLevel()) {
                label = FAIL;
                request.setAttribute("ERROR", "The current account is not authorized to book the resource.");
            } else {
                int quantity = Integer.parseInt(amountBook);
                Utilities util = new Utilities();
                BookingDetailDTO dto = new BookingDetailDTO("", "", resourceId,
                        util.formatDateForPrint(dateStart), util.formatDateForPrint(dateEnd),
                        quantity);
                dto.setResourceDescription(resourceId + " - " + resource.getName());
                List<BookingDetailDTO> bookingList = (List<BookingDetailDTO>) session.get("CART");
                for (int i = 0; i < bookingList.size(); i++) {
                    if (bookingList.get(i).getResourceId().equals(resourceId)) {
                        bookingList.remove(i);
                    }
                }
                bookingList.add(dto);
                session.put("CART", bookingList);
                session.put("CARTSIZE", bookingList.size());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return label;
    }

}
