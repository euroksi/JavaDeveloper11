package org.example;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String timezoneParam = request.getParameter("timezone");

        if (timezoneParam != null) {
            TimeZone tz = TimeZone.getTimeZone(timezoneParam);
            if ("GMT".equals(tz.getID()) && !timezoneParam.equalsIgnoreCase("GMT")) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
