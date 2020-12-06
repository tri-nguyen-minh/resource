/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import daos.AccountDAO;
import daos.BookingDAO;
import daos.OtherDAO;
import dtos.AccountDTO;
import dtos.BookingDTO;
import dtos.BookingDetailDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Administrator
 */
public class LoginAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(LoginAction.class);
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private String id, password, recaptchaResult;

    public LoginAction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecaptchaResult() {
        return recaptchaResult;
    }

    public void setRecaptchaResult(String recaptchaResult) {
        this.recaptchaResult = recaptchaResult;
    }

    public String execute() throws Exception {
        String label = ERROR;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            AccountDAO dao = new AccountDAO();
            if (recaptchaResult.equals("True")) {
                AccountDTO dto = dao.getAccount(id, password);
                if (dto != null) {
                    if (dto.getStatus() == 1) {
                        request.setAttribute("ERROR", "This Account has not been activated!");
                    } else {
                        Map session = ActionContext.getContext().getSession();
                        session.put("USER", dto);
                        if (dto.getRole() > 1) {
                            List<BookingDetailDTO> bookingList = new ArrayList<>();
                            session.put("CART", bookingList);
                            session.put("CARTSIZE", bookingList.size());
                            List<BookingDTO> requestList = new BookingDAO().getUserBookingRequest(id, "", "");
                            session.put("REQUESTLIST", requestList);
                            session.put("REQUESTLISTSIZE", requestList.size());
                        }
                        session.put("CATEGORYLIST", new OtherDAO().getCategoryList());
                        label = SUCCESS;
                    }
                } else {
                    request.setAttribute("ERROR", "Email or Password is incorrect!");
                }
            } else {
                request.setAttribute("ERROR", "ReCAPTCHA Validation is required!");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return label;
    }
}
