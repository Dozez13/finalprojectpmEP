<%@ tag import="java.util.Locale" %>
<%@ tag language="java" body-content="empty" pageEncoding="utf-8" %>
<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String" %>
<%
Locale locale = new Locale(name);
session.setAttribute("tagLocale",locale);
%>
