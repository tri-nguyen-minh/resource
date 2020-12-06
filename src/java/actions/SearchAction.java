/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import daos.ResourceDAO;
import dtos.AccountDTO;
import dtos.ResourceDTO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import utils.Utilities;

/**
 *
 * @author Administrator
 */
public class SearchAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(SearchAction.class);
    private static final String SUCCESS = "success";
    private String resourceName, dateStart, dateEnd, categoryString, pageString;

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

    public SearchAction() {
    }

    public String execute() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Map session = ActionContext.getContext().getSession();
            AccountDTO userDTO = (AccountDTO) session.get("USER");
            int role = userDTO.getRole();
            int category = Integer.parseInt(categoryString);
            int page = Integer.parseInt(pageString);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            if (dateStart.equals("")) {
                dateStart = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            }
            if (dateEnd.equals("")) {
                dateEnd = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            }
            if (new Utilities().checkDateSwitched(dateStart, dateEnd)) {
                String temp = dateStart;
                dateStart = dateEnd;
                dateEnd = temp;
            }
            ResourceDAO dao = new ResourceDAO();
            List<ResourceDTO> result = dao.getResourceList(resourceName, category, dateStart, dateEnd, role, page);
            int totalPage = dao.getTotalSearchPage(resourceName, category, role);
            request.setAttribute("LIST", result);
            request.setAttribute("RESOURCE", resourceName);
            request.setAttribute("DATESTART", dateStart);
            request.setAttribute("DATEEND", dateEnd);
            request.setAttribute("CATEGORY", category);
            request.setAttribute("PAGE", page);
            request.setAttribute("TOTALPAGE", totalPage);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return SUCCESS;
    }

}
