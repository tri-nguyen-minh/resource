package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dtos.BookingDetailDTO;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author TNM
 */
public class DeleteAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(DeleteAction.class);
    private static final String RELOAD = "reload";
    private String resourceId;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public DeleteAction() {
    }

    public String execute() throws Exception {
        String label = RELOAD;
        try {
        Map session = ActionContext.getContext().getSession();
        List<BookingDetailDTO> bookingList = (List<BookingDetailDTO>) session.get("CART");
        for (int i = 0; i < bookingList.size(); i++) {
            if(bookingList.get(i).getResourceId().equals(resourceId)) {
                bookingList.remove(i);
            }
        }
        session.put("CART", bookingList);
        session.put("CARTSIZE", bookingList.size());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return label;
    }

}
