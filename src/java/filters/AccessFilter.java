/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dtos.AccountDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class AccessFilter implements Filter {

    private static final boolean debug = true;

    private static final String ACCEPTACTION = "AcceptAction";
    private static final String BOOKINGACTION = "BookingAction";
    private static final String CANCELACTION = "CancelAction";
    private static final String CONFIRMACTION = "ConfirmAction";
    private static final String DELETEACTION = "DeleteAction";
    private static final String DENYACTION = "DenyAction";
    private static final String LOGINACTION = "LoginAction";
    private static final String LOGOUTACTION = "LogoutAction";
    private static final String SEARCHACTION = "SearchAction";
    private static final String SEARCHHISTORY = "SearchHistoryAction";
    private static final String SEARCHREQUEST = "SearchRequestAction";
    private static final String LOGINPAGE = "login.jsp";
    private static final String SEARCHPAGE = "search.jsp";
    private static final String BOOKINGPAGE = "bookingPage.jsp";
    private static final String HISTORYPAGE = "requestHistoryPage.jsp";
    private static final String REQUESTPAGE = "requestPage.jsp";
    private static final String CSS = "style.css";
    private static final String LOGIN_LOGO = "Loginlogo.jpg";
    private static final String HEAD_BACKGROUND = "top-bg.gif";

    private final List<String> ACCESSFORMANAGER;
    private final List<String> ACCESSFORUSER;
    private final List<String> ACCESSFORGUESS;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AccessFilter() {
        ACCESSFORMANAGER = new ArrayList<>();
        ACCESSFORMANAGER.add(LOGINACTION);
        ACCESSFORMANAGER.add(LOGINPAGE);
        ACCESSFORMANAGER.add(LOGOUTACTION);
        ACCESSFORMANAGER.add(SEARCHACTION);
        ACCESSFORMANAGER.add(SEARCHPAGE);
        ACCESSFORMANAGER.add(SEARCHREQUEST);
        ACCESSFORMANAGER.add(REQUESTPAGE);
        ACCESSFORMANAGER.add(ACCEPTACTION);
        ACCESSFORMANAGER.add(DENYACTION);

        ACCESSFORUSER = new ArrayList<>();
        ACCESSFORUSER.add(LOGINACTION);
        ACCESSFORUSER.add(LOGINPAGE);
        ACCESSFORUSER.add(LOGOUTACTION);
        ACCESSFORUSER.add(SEARCHACTION);
        ACCESSFORUSER.add(SEARCHPAGE);
        ACCESSFORUSER.add(BOOKINGACTION);
        ACCESSFORUSER.add(BOOKINGPAGE);
        ACCESSFORUSER.add(SEARCHHISTORY);
        ACCESSFORUSER.add(HISTORYPAGE);
        ACCESSFORUSER.add(CONFIRMACTION);
        ACCESSFORUSER.add(CANCELACTION);
        ACCESSFORUSER.add(DELETEACTION);

        ACCESSFORGUESS = new ArrayList<>();
        ACCESSFORGUESS.add(LOGINACTION);
        ACCESSFORGUESS.add(LOGINPAGE);

        ACCESSFORMANAGER.add(HEAD_BACKGROUND);
        ACCESSFORUSER.add(HEAD_BACKGROUND);
        ACCESSFORGUESS.add(HEAD_BACKGROUND);
        ACCESSFORMANAGER.add(CSS);
        ACCESSFORUSER.add(CSS);
        ACCESSFORGUESS.add(CSS);
        ACCESSFORGUESS.add(LOGIN_LOGO);
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AccessFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AccessFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        boolean accessDenied = false;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        AccountDTO dto = (AccountDTO) session.getAttribute("USER");
        String uri = req.getRequestURI();
        String resource = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println("Resource: " + resource);
        if(resource.contains(".action")) {
            resource = resource.substring(0, resource.lastIndexOf("."));
        }
        if (dto != null) {
            if (dto.getRole() == 1) {
                if (!ACCESSFORMANAGER.contains(resource)) {
                    accessDenied = true;
                }
            } else if (!ACCESSFORUSER.contains(resource)) {
                accessDenied = true;
            }
        } else if (!ACCESSFORGUESS.contains(resource)) {
            accessDenied = true;
        }
        if (accessDenied) {
            res.sendRedirect(LOGINPAGE);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AccessFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AccessFilter()");
        }
        StringBuffer sb = new StringBuffer("AccessFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
