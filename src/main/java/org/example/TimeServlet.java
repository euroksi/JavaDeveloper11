package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timezone = request.getParameter("timezone");
        String lastTimezone = "UTC";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastTimezone".equals(cookie.getName())) {
                    lastTimezone = cookie.getValue();
                }
            }
        }

        if (timezone != null && !timezone.isEmpty()) {
            Cookie cookie = new Cookie("lastTimezone", timezone);
            cookie.setMaxAge(60 * 60 * 24 * 365); // Зберігаємо на 1 рік
            response.addCookie(cookie);
        } else {
            timezone = lastTimezone;
        }

        ZoneId zoneId = ZoneId.of(timezone);
        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = currentTime.format(formatter);

        request.setAttribute("currentTime", formattedTime);
        request.setAttribute("timezone", timezone);

        request.getRequestDispatcher("/time.jsp").forward(request, response);
    }
}
