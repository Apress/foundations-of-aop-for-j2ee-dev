<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*,com.sun.ebank.util.*" %>

<center>
<jsp:useBean id="beanManager" class="com.sun.ebank.web.BeanManager" scope="application"/>
<jsp:useBean id="atmBean" class="com.sun.ebank.web.ATMBean" scope="request"/>
<%
   Date currentDate = new Date();
   int currentMonth = DateHelper.getMonth(currentDate);
   int currentYear = DateHelper.getYear(currentDate);
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
%>
<%=messages.getString("CustomerString")%> <jsp:getProperty name="atmBean" property="operationString" /> $<jsp:getProperty name="atmBean" property="amount"/> <jsp:getProperty name="atmBean" property="prepString" /> <a href="<%=request.getContextPath()%>/accountHist?accountId=<%= atmBean.getAccountId() %>&date=0&year=<%=currentYear%>&sinceMonth=<%=currentMonth%>&sinceDay=1&beginMonth=<%=currentMonth%>&beginDay=1&endMonth=<%=currentMonth%>&endDay=1&activityView=0&sortOption=0"><%=atmBean.getSelectedAccount().getDescription()%></a>.
<p><%=messages.getString("BalanceString")%> $<%= atmBean.getSelectedAccount().getBalance() %>.
</center>






