<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setContentType("application/json");
String value = (String)request.getAttribute("ordersJsonValues");
%>
<%=value%>
