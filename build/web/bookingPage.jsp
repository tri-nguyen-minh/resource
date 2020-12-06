<%-- 
    Document   : bookingPage
    Created on : Jul 1, 2020, 4:25:32 PM
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
                        <s:if test="%{#session.CARTSIZE != 0}">
                            <h1 id="headline-c">Booked Resource(s): <s:property value="%{#session.CARTSIZE}"/></h1>
                            <table border="1" width="1" cellspacing="1" cellpadding="5" style="width: 700px">
                                <thead>
                                    <tr>
                                        <th style="width: 400px">Resource Name</th>
                                        <th style="width: 80px">Quantity</th>
                                        <th style="width: 120px">Booked Date</th>
                                        <th style="width: 120px">Return Date</th>
                                        <th style="width: 80px">Remove</th>
                                    </tr>
                                </thead>
                                <tbody style="text-align: center">
                                    <s:iterator value="%{#session.CART}">
                                        <tr>
                                            <td style="font-size: 17px"><s:property value="resourceDescription"/></td>
                                            <td style="font-size: 17px"><s:property value="quantity"/></td>
                                            <td style="font-size: 17px"><s:property value="dateStart"/></td>
                                            <td style="font-size: 17px"><s:property value="dateReturn"/></td>
                                            <td style="font-size: 17px">
                                                <form action="DeleteAction" method="POST">
                                                    <input type="hidden" name="resourceId" value="<s:property value="resourceId"/>"/>
                                                    <input value="Delete" type="submit" id="delete-button"/>
                                                </form>
                                            </td>
                                        </tr>
                                    </s:iterator>
                                </tbody>
                            </table>
                            <form action="ConfirmAction" method="POST">
                                <input type="submit" id="confirm-button" value="CONFIRM"/>
                            </form>
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
