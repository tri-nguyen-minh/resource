<%-- 
    Document   : requestHistoryPage
    Created on : Jul 1, 2020, 8:25:45 PM
    Author     : TNM
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title><s:property value="%{#session.USER.name}"/>'s Booked Resources</title>
        <meta charset="utf-8">
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header class="header">
            <div class="top">
                <div class="welcome">
                    Welcome, 
                    <s:if test="%{#session.USER.role == 2}">
                        Leader 
                    </s:if>
                    <s:else>
                        Employee
                    </s:else>
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
                    <s:if test="%{#session.CARTSIZE == 0}">
                        <li>
                            <a href="bookingPage.jsp">No Booked Resource</a>
                        </li>
                    </s:if>
                    <s:else>
                        <li>
                            <a href="bookingPage.jsp">Booked Resource (<s:property value="%{#session.CARTSIZE}"/>)</a>
                        </li>
                    </s:else>
                    <li>
                        <a href="SearchHistoryAction">Request History</a>
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
                            <form action="SearchHistoryAction" method="POST">
                                <input type="text" style="width: 200px" name="resource" value="<s:property value="%{#request.RESOURCE}"/>" placeholder="Resource's Name"/>
                                <input style="font-size: 18px;width: 200px; height: 35px; margin-left: 20px" type="date" name="dateBook" value="<s:property value="%{#request.DATE}"/>"/>
                                <input style="margin: 5px 20px;" type="submit" value="Search"/>
                            </form>
                        </div>
                        <s:if test="%{#session.REQUESTLISTSIZE != 0}">
                            <h1 id="headline-c">Number of Request(s): <s:property value="%{#session.REQUESTLISTSIZE}"/></h1>
                            <div>
                                <table border="1" cellspacing="5" cellpadding="5" style="width: 1000px">
                                    <thead style="font-size: 17px">
                                        <tr>
                                            <th style=" width: 40px; font-size: 17px">No.</th>
                                            <th style=" width: 120px; font-size: 17px">Booked Time</th>
                                            <th style=" width: 610px; font-size: 17px">Booking Detail</th>
                                            <th style=" width: 100px; font-size: 17px">Status</th>
                                            <th style=" width: 80px; font-size: 17px">Cancel</th>
                                        </tr>
                                    </thead>
                                    <tbody style="text-align: center">
                                        <s:iterator value="%{#session.REQUESTLIST}" var="booking" status="counter">
                                            <tr>
                                                <td style="font-size: 17px"><s:property value="%{#counter.count}"/></td>
                                                <td style="font-size: 17px"><s:property value="dateBook"/></td><td>
                                                    <s:iterator value="%{#booking.bookingList}">
                                                        <table cellspacing="3" cellpadding="3">
                                                            <tbody style="text-align: center">
                                                                <tr>
                                                                    <td style="text-align: left; width: 210px; font-size: 15px"><b> - </b><s:property value="resourceDescription"/></td>
                                                                    <td style="width: 100px; font-size: 15px">Quantity: <s:property value="quantity"/></td>
                                                                    <td style="width: 150px; font-size: 15px">From:<s:property value="dateStart"/></td>
                                                                    <td style="width: 150px; font-size: 15px">To:<s:property value="dateReturn"/></td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </s:iterator>
                                                </td>
                                                <td style="font-size: 20px; color: blue"><s:property value="status"/></td>
                                                <td>
                                                    <s:if test="statusId == 1">
                                                        <form action="CancelAction" method="POST">
                                                            <input type="hidden" name="resource" value="<s:property value="%{#request.RESOURCE}"/>"/>
                                                            <input type="hidden" name="dateBook" value="<s:property value="%{#request.DATE}"/>"/>
                                                            <input type="hidden" name="bookingId" value="<s:property value="id"/>"/>
                                                            <input type="submit" value="Cancel" id="cancel-button"/>
                                                        </form>
                                                    </s:if>
                                                </td>
                                            </tr>
                                        </s:iterator>
                                    </tbody>
                                </table>

                            </div>
                        </s:if>
                        <s:else>
                            <h1 id="headline-b">No Resource Booked!</h1>
                        </s:else>
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
