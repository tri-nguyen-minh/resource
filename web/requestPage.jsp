<%-- 
    Document   : requestPage
    Created on : Jul 2, 2020, 1:30:58 PM
    Author     : TNM
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>Resource Request</title>
        <meta charset="utf-8">
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header class="header">
            <div class="top">
                <div class="welcome">
                    Welcome, Manager 
                    <s:property value="%{#session.USER.name}"/>!
                </div>
                <div class="logout">
                    <form action="LogoutAction" method="POST">
                        <input type="submit" value="Logout">
                    </form>
                </div>
            </div>
            <div class="head">
            </div>
            <nav style="margin-top: -10px">
                <ul>
                    <li>
                        <a href="search.jsp">Resource Page</a>
                    </li>
                    <li>
                        <a href="requestPage.jsp">All Requests</a>
                    </li>
                </ul>
            </nav>
        </header>
        <div class="main">
            <section class="user-main">
                <div class="info">
                    <div class="main-info" style="margin-right: 400px">
                        <div style="margin-top: -40px; margin-bottom: 60px">
                            <h1 style="font-size: 20px">Search Request</h1>
                            <form action="SearchRequestAction" method="POST">
                                <input type="text" style="width: 170px" name="resource" value="<s:property value="%{#request.RESOURCE}"/>" placeholder="Resource's Name"/>
                                <input type="text" style="width: 170px" name="user" value="<s:property value="%{#request.USERNAME}"/>" placeholder="User's Name"/>
                                <select name="status"  style=" font-size: 18px;width: 100px; height: 35px">
                                    <option value="0">All</option>
                                    <option value="1">New</option>
                                    <option value="4">Accepted</option>
                                    <option value="5">Deleted</option>
                                </select></br>
                                <label>From</label>
                                <input style="font-size: 18px;width: 200px; height: 35px; margin-left: 20px" type="date" name="dateBookLow" value="<s:property value="%{#request.DATELOW}"/>"/>
                                <label>To</label>
                                <input style="font-size: 18px;width: 200px; height: 35px; margin-left: 20px" type="date" name="dateBookHigh" value="<s:property value="%{#request.DATEHIGH}"/>"/>
                                <input type="hidden" name="pageString" value="1"/>
                                <input style="margin: 5px 20px;" type="submit" value="Search"/>
                            </form>
                        </div>
                        <s:if test="%{#request.REQUESTLIST != null}">
                            <h1 id="headline-b">Total Result Page(s): <s:property value="%{#request.TOTALPAGE}"/></h1></br>
                            <form action="SearchRequestAction" method="POST">
                                <input type="hidden" name="resource" value="<s:property value="%{#request.RESOURCE}"/>"/>
                                <input type="hidden" name="user" value="<s:property value="%{#request.USERNAME}"/>"/>
                                <input type="hidden" name="dateBookLow" value="<s:property value="%{#request.DATELOW}"/>"/>
                                <input type="hidden" name="dateBookHigh" value="<s:property value="%{#request.DATEHIGH}"/>"/>
                                <input type="hidden" name="status" value="<s:property value="%{#request.STATUS}"/>"/>
                                <div style="margin-bottom: 30px">
                                    <h1 style="font-size: 17px">Page List:</h1>
                                    <s:iterator begin="1" end="%{#request.TOTALPAGE}" status="counter">
                                        <input id="page-switch" type="submit" name="pageString" value="<s:property value="%{#counter.count}"/>"/>
                                    </s:iterator>
                                    </br>
                                </div>
                            </form>
                            <div>
                                <table border="1" cellspacing="5" cellpadding="5" style="width: 1000px">
                                    <thead style="font-size: 17px">
                                        <tr>
                                            <th style=" width: 100px; font-size: 16px">User</th>
                                            <th style=" width: 100px; font-size: 16px">Booked Time</th>
                                            <th style=" width: 550px; font-size: 16px">Booking Detail</th>
                                            <th style=" width: 60px; font-size: 16px">Status</th>
                                            <th style=" width: 80px; font-size: 16px">Accept</th>
                                            <th style=" width: 80px; font-size: 16px">Deny</th>
                                        </tr>
                                    </thead>
                                    <tbody style="text-align: center">
                                        <s:iterator value="%{#request.REQUESTLIST}" var="booking" status="counter">
                                            <tr>
                                                <td style="font-size: 15px"><s:property value="userDescription"/></td>
                                                <td style="font-size: 15px"><s:property value="dateBook"/></td>
                                                <td>
                                                    <table cellspacing="3" cellpadding="3">
                                                        <thead>
                                                            <tr>
                                                                <th style="border: #657786 solid; border-width: 0px 0px 1px">Resource</th>
                                                                <th style="border: #657786 solid; border-width: 0px 0px 1px">Quantity</th>
                                                                <th style="border: #657786 solid; border-width: 0px 0px 1px">From</th>
                                                                <th style="border: #657786 solid; border-width: 0px 0px 1px">To</th>
                                                            </tr>
                                                        </thead>
                                                        <s:iterator value="%{#booking.bookingList}">
                                                            <tbody style="text-align: center">
                                                                <tr>
                                                                    <td style="text-align: left; width: 220px; font-size: 14px"><b> - </b><s:property value="resourceDescription"/></td>
                                                                    <td style="width: 80px; font-size: 14px"><s:property value="quantity"/></td>
                                                                    <td style="width: 120px; font-size: 14px"><s:property value="dateStart"/></td>
                                                                    <td style="width: 120px; font-size: 14px"><s:property value="dateReturn"/></td>
                                                                </tr>
                                                            </tbody>
                                                    </s:iterator>
                                                        </table>
                                                </td>
                                                <td style="font-size: 16px; color: blue"><s:property value="status"/></td>
                                                <td>
                                                    <s:if test="statusId == 1">
                                                        <form action="AcceptAction" method="POST">
                                                            <input type="hidden" name="resource" value="<s:property value="%{#request.RESOURCE}"/>"/>
                                                            <input type="hidden" name="user" value="<s:property value="%{#request.USERNAME}"/>"/>
                                                            <input type="hidden" name="dateBookLow" value="<s:property value="%{#request.DATELOW}"/>"/>
                                                            <input type="hidden" name="dateBookHigh" value="<s:property value="%{#request.DATEHIGH}"/>"/>
                                                            <input type="hidden" name="status" value="<s:property value="%{#request.STATUS}"/>"/>
                                                            <input type="hidden" name="pageString" value="<s:property value="%{#request.PAGE}"/>"/>
                                                            <input type="hidden" name="bookingId" value="<s:property value="id"/>"/>
                                                            <input type="submit" value="Accept" id="cancel-button"/>
                                                        </form>
                                                    </s:if>
                                                </td>
                                                <td>
                                                    <s:if test="statusId == 1">
                                                        <form action="DenyAction" method="POST">
                                                            <input type="hidden" name="resource" value="<s:property value="%{#request.RESOURCE}"/>"/>
                                                            <input type="hidden" name="user" value="<s:property value="%{#request.USERNAME}"/>"/>
                                                            <input type="hidden" name="dateBookLow" value="<s:property value="%{#request.DATELOW}"/>"/>
                                                            <input type="hidden" name="dateBookHigh" value="<s:property value="%{#request.DATEHIGH}"/>"/>
                                                            <input type="hidden" name="status" value="<s:property value="%{#request.STATUS}"/>"/>
                                                            <input type="hidden" name="pageString" value="<s:property value="%{#request.PAGE}"/>"/>
                                                            <input type="hidden" name="bookingId" value="<s:property value="id"/>"/>
                                                            <input type="submit" value="Deny" id="cancel-button"/>
                                                        </form>
                                                    </s:if>
                                                </td>
                                            </tr>
                                        </s:iterator>
                                    </tbody>
                                </table>
                            </div>
                        </s:if>
                        <p>
                            <font color="red">
                            <s:property value="%{#request.MESSAGE}" />
                            </font>
                        </p>
                    </div>
                </div>
            </section>
        </div>
    </body>
</html>