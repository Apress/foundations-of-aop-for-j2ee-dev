<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ page import="com.sun.ebank.util.*,com.sun.ebank.ejb.exception.*" %>
<%@ page import="java.util.*" %>

<% ResourceBundle messages = (ResourceBundle)session.getAttribute("messages"); %> 
<center>
<jsp:useBean id="beanManager" class="com.sun.ebank.web.BeanManager" scope="application"/>
<jsp:useBean id="transferBean" class="com.sun.ebank.web.TransferBean" scope="request"/>
<table border=1 cellpadding=2 cellspacing=0 width=500>
<tr>
<td valign=bottom>
  <b><%=messages.getString("AccountName")%></b>
</td>
<td valign=bottom>
  <b><%=messages.getString("AccountId")%></b>
</td>
<td valign=bottom>
  <b><%=messages.getString("AccountBalance")%></b>
</td>
<td valign=bottom>
  <b><%=messages.getString("AccountCredit")%></b>
</td>
</tr>

<% 
  AccountDetails ad = null;
  try { 
    ad = beanManager.getAccountController().getDetails(transferBean.getFromAccountId()); 
  } catch (InvalidParameterException e) {
  }
  pageContext.setAttribute("ad", ad); 
  Date currentDate = new Date();
  int currentMonth = DateHelper.getMonth(currentDate);
  int currentYear = DateHelper.getYear(currentDate);
%>
<tr>
  <td><a href="<%=request.getContextPath()%>/accountHist?accountId=<jsp:getProperty name="ad" property="accountId"/>&date=0&year=<%=currentYear%>&sinceMonth=<%=currentMonth%>&sinceDay=1&beginMonth=<%=currentMonth%>&beginDay=1&endMonth=<%=currentMonth%>&endDay=1&activityView=0&sortOption=0"><jsp:getProperty name="ad" property="description"/></td>
  <td><jsp:getProperty name="ad" property="accountId"/></td>
  <td align="right">$<jsp:getProperty name="ad" property="balance"/></td>
  <td align="right"><logic:notEqual name ="ad" property="creditLine" value="0">
      $<%= ad.getCreditLine().subtract(ad.getBalance()) %></td>
     </logic:notEqual>
     <logic:equal name="ad" property="creditLine" value="0">
     	&nbsp;</td>
     </logic:equal>
</tr>
<%  ad = beanManager.getAccountController().getDetails(transferBean.getToAccountId()); 
    pageContext.setAttribute("ad", ad); 
%>
<tr>
  <td><a href="<%=request.getContextPath()%>/accountHist?accountId=<jsp:getProperty name="ad" property="accountId"/>&date=0&sinceMonth=<%=currentMonth%>&sinceDay=1&beginMonth=<%=currentMonth%>&beginDay=1&endMonth=<%=currentMonth%>&endDay=1&activityView=0&sortOption=0"><jsp:getProperty name="ad" property="description"/></td>
  <td><jsp:getProperty name="ad" property="accountId"/></td>
  <td align="right">$<jsp:getProperty  name="ad" property="balance"/></td>
  <td align="right">
     <logic:notEqual name ="ad" property="creditLine" value="0">
      	$<%= ad.getCreditLine().subtract(ad.getBalance()) %></td>
     </logic:notEqual>
     <logic:equal name="ad" property="creditLine" value="0">
     	&nbsp;</td>
     </logic:equal>
</tr>
</table>

<p><%=messages.getString("TransferAck")%> $<jsp:getProperty name="transferBean" property="transferAmount"/>.  
</center>
