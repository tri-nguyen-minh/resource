package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author TNM
 */
public class LogoutAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(LogoutAction.class);
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    public LogoutAction() {
    }

    public String execute() throws Exception {
        String label = ERROR;
        try {
            Map session = ActionContext.getContext().getSession();
            session.clear();
            label = SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return label;
    }

}
