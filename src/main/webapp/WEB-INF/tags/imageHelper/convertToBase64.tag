<%@ tag import="com.example.finalprojectpm.db.util.ImageUtil" %>
<%@ tag language="java" body-content="empty" pageEncoding="utf-8" %>
<%@ attribute name="image" required="true" rtexprvalue="true" type="java.awt.image.BufferedImage" %>
<%
out.print(ImageUtil.getBase64String(ImageUtil.imageToByte(image)));
%>