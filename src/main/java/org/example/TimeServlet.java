package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    // я не використовував клас TimeZone тому що воно погано працювало у випадках
    // коли до UTC щось додається чи віднімаеться

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss zzz").withZone(ZoneId.of("UTC"));


        Date actualDate = new Date();

        String timezoneParam = request.getParameter("timezone");

        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            timezoneParam = timezoneParam.replace(" ", "+");
            dateFormat = dateFormat.withZone(ZoneId.of(timezoneParam));
        }

        out.println("<html>");
        out.println("<head><title>Current Time</title></head>");
        out.println("<body>");
        out.println("<h1>Current Time</h1>");
        out.println("<p>" + dateFormat.format(actualDate.toInstant()) + "</p>");
        out.println("</body></html>");
        out.close();
    }
}