<%-- 
    Document   : search
    Created on : Jun 29, 2020, 5:58:17 PM
    Author     : TNM
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <s:if test="%{#session.USER.role == 1}">
            <title>All Resources Page</title>
        </s:if>
        <s:elseif test="%{#session.USER.role == 2}">
            <title>Leader's Resources Page</title>
        </s:elseif>
        <s:else>
            <title>Employee's Resources Page</title>
        </s:else>
        <meta charset="utf-8">
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header class="header">
            <div class="top">
                <div class="welcome">
                    Welcome, 
                    <s:if test="%{#session.USER.role == 1}">
                        Manager 
                    </s:if>
                    <s:elseif test="%{#session.USER.role == 2}">
                        Leader 
                    </s:elseif>
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
            <nav>
                <ul>
                    <li>
                        <a href="search.jsp">Resource Page</a>
                    </li>
                    <s:if test="%{#session.USER.role == 1}">
                        <li>
                            <a href="requestPage.jsp">All Requests</a>
                        </li>
                    </s:if>
                    <s:else>
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
                    </s:else>
                </ul>
            </nav>
        </header>
        <div class="main">
            <section class="user-main">
                <div class="info">
                    <div class="search-info">
                        <h1 id="headline-a">Search Resource</h1>
                        <form action="SearchAction" method="POST">
                            </br></br><h1 style="margin-bottom: 15px">Search by Resource's Name</h1>
                            <input style="width: 200px; margin-left: -1px" type="text" name="resourceName" value="<s:property value="%{#request.RESOURCE}"/>" placeholder="Resource's Name"/></br>
                            <h1 style="margin-bottom: 15px">Booking Time</h1>
                            <h1 style="margin-bottom: 7px">From:</h1><input type="date" id="date-input" name="dateStart" value="<s:property value="%{#request.DATESTART}"/>"/></br>
                            <h1 style="margin-bottom: 7px">To:</h1><input type="date" id="date-input" name="dateEnd" value="<s:property value="%{#request.DATEEND}"/>"/>
                            </br></br>
                            <h1 style="margin-bottom: 15px">Search by Category</h1>
                            <select class="comboBox" name="categoryString" style="width: 200px; height: 30px">
                                <option value="0" <s:if test="%{#request.CATEGORY == 0}">selected="selected"</s:if>>All</option>
                                <s:iterator value="%{#session.CATEGORYLIST}" var="category" status="counter">
                                    <option value="<s:property value="%{#counter.count}"/>" 
                                            <s:if test="%{#request.CATEGORY == #counter.count}">
                                                selected
                                            </s:if>>
                                        <s:property value="%{category}"/>
                                    </option>
                                </s:iterator>
                            </select>
                            <input type="hidden" name="pageString" value="1"/>
                            <input style="margin-left: 0px" type="submit" value="Search"/>
                        </form>
                    </div>
                    <div class="main-info">
                        <s:if test="%{#request.LIST != null}">
                            <h1 id="headline-b">Total Result Page(s): <s:property value="%{#request.TOTALPAGE}"/></h1></br>
                            <form action="SearchAction" method="POST">
                                <input type="hidden" name="resourceName" value="<s:property value="%{#request.RESOURCE}"/>"/>
                                <input type="hidden" name="dateStart" value="<s:property value="%{#request.DATESTART}"/>"/>
                                <input type="hidden" name="dateEnd" value="<s:property value="%{#request.DATEEND}"/>"/>
                                <input type="hidden" name="categoryString" value="<s:property value="%{#request.CATEGORY}"/>"/>
                                <div style="margin-bottom: 30px">
                                    <h1 style="font-size: 17px">Page List:</h1>
                                    <s:iterator begin="1" end="%{#request.TOTALPAGE}" status="counter">
                                        <input id="page-switch" type="submit" name="pageString" value="<s:property value="%{#counter.count}"/>"/>
                                    </s:iterator>
                                    </br>
                                </div>
                            </form>
                            <table border="1" width="2" cellspacing="2" style="width: 820px">
                                <thead>
                                    <tr>
                                        <th style="width: 320px">Resource Name</th>
                                        <th style="width: 100px">Category</th>
                                        <th style="width: 120px">Color</th>
                                        <th style="width: 200px">Open for</th>
                                        <th style="width: 80px">Quantity</th>
                                        <th style="width: 80px">Available</th>
                                        <th style="width: 220px">Book Resource</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <s:iterator value="%{#request.LIST}">
                                        <tr style="text-align: center">
                                            <td><s:property value="name"/></td>
                                            <td><s:property value="category"/></td>
                                            <td><s:property value="color"/></td>
                                            <td>
                                                <s:if test="%{roleLevel == 2}">
                                                    Leader
                                                </s:if>
                                                <s:elseif test="%{roleLevel == 3}">
                                                    Leader & Employee
                                                </s:elseif>
                                            </td>
                                            <td><s:property value="quantity"/></td>
                                            <td><s:property value="amountAvailable"/></td>
                                            <td>
                                                <s:if test="amountAvailable > 0">
                                                    <form action="BookingAction" method="POST">
                                                        <input type="hidden" name="resourceName" value="<s:property value="%{#request.RESOURCE}"/>"/>
                                                        <input type="hidden" name="dateStart" value="<s:property value="%{#request.DATESTART}"/>"/>
                                                        <input type="hidden" name="dateEnd" value="<s:property value="%{#request.DATEEND}"/>"/>
                                                        <input type="hidden" name="categoryString" value="<s:property value="%{#request.CATEGORY}"/>"/>
                                                        <input type="hidden" name="pageString" value="<s:property value="%{#request.PAGE}"/>"/>
                                                        <select name="amountBook" class="comboBox" id="slot-box">
                                                            <s:iterator begin="1" end="amountAvailable" status="counter">
                                                                <option value="<s:property value="%{#counter.count}"/>">
                                                                    <s:property value="%{#counter.count}"/>
                                                                </option>
                                                            </s:iterator>
                                                        </select>
                                                        <input type="hidden" name="resourceId" value="<s:property value="id"/>"/>
                                                        <input type="submit" value="Book" id="book-button"/>
                                                    </form>
                                                </s:if>
                                                <s:else>
                                                    Not Available
                                                </s:else>
                                            </td>
                                        </tr>
                                    </s:iterator>
                                </tbody>
                            </table>
                        </s:if>
                    </div>
                </div>
            </section>

    </body>
</html>
