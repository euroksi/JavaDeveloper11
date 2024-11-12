package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class TimeController {

    @RequestMapping("/time")
    public String getTime(@RequestParam(value = "timezone", required = false) String timezone,
                          @CookieValue(value = "lastTimezone", defaultValue = "UTC") String lastTimezone,
                          HttpServletResponse response, Model model) {

        if (timezone != null && !timezone.isEmpty()) {
            Cookie cookie = new Cookie("lastTimezone", timezone);
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1 рік
            response.addCookie(cookie);
        } else {
            timezone = lastTimezone;
        }

        ZoneId zoneId = ZoneId.of(timezone);

        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = currentTime.format(formatter);

        model.addAttribute("currentTime", formattedTime);
        model.addAttribute("timezone", timezone);

        return "time";
    }
}