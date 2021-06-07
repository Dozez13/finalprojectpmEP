package com.example.finalprojectpm.web.handler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class LValueParamHandler extends SimpleTagSupport {
    private String params;
    private String message;
    private boolean checkParam;

    public void setCheckParam(boolean checkParam) {
        this.checkParam = checkParam;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String messageFrom = message;
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();
        Locale locale = (Locale)session.getAttribute("tagLocale");
        ResourceBundle bundle = ResourceBundle.getBundle((String) session.getAttribute("Bundle"),locale);
        Object[] param;
        if(checkParam){
            param = Arrays.stream(params.split(" ")).map(value -> {
                if (!isNumeric(value)) {
                    value = bundle.getString(value);
                }
                return value;
            }).toArray();
        }else {
            param = Arrays.stream(params.split(" ")).toArray();
        }

        String format = MessageFormat.format(bundle.getString(messageFrom), param);
        out.print(new String(format.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

    }

    public static boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
