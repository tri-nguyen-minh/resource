/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import daos.BookingDAO;
import dtos.AccountDTO;
import dtos.BookingDTO;
import dtos.BookingDetailDTO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Administrator
 */
public class ConfirmAction extends ActionSupport {
    
    private static final Logger LOGGER = Logger.getLogger(ConfirmAction.class);
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    
    public ConfirmAction() {
    }
    
    public String execute() throws Exception {
        String label = ERROR;
        try {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map session = ActionContext.getContext().getSession();
        AccountDTO user = (AccountDTO)session.get("USER");
        List<BookingDetailDTO> bookingList = (List<BookingDetailDTO>) session.get("CART");
        BookingDAO dao = new BookingDAO();
        if(dao.addBookingRequest(user.getEmail(), bookingList)) {
            bookingList.clear();
            session.put("CART", bookingList);
            session.put("CARTSIZE", bookingList.size());
            List<BookingDTO> requestList = dao.getUserBookingRequest(user.getEmail(),"","");
            session.put("REQUESTLIST", requestList);
            session.put("REQUESTLISTSIZE", requestList.size());
            label = SUCCESS;
        } else {
            label = ERROR;
            request.setAttribute("MESSAGE", "An error has occured!");
        }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return label;
    }
    
}
