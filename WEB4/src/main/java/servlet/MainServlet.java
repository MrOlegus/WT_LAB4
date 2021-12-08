package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import javax.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = (String)req.getParameter("email");
        String password = (String)req.getParameter("password");
        String rightPassword = GetPassword(email);
        
        resp.setContentType("text/html;charset=Windows-1251");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Вход</title>");
        out.println("<meta charset=\"windows-1251\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("</head>");
        out.println("<body>");
    

        if ((!password.equals("")) && (password.equals(rightPassword)))
        {
            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            out.println("Добро пожаловать на сайт!<br>");
            out.println("Забронировать место можно по ссылке ниже<br>");
            out.println("<a href=\"floors.html\">забронировать место</a>");
        } else
        out.println("Неверный логин или пароль");

        out.println("</body>");
        out.println("</html>");
    }
    
    private String GetPassword(String email)
    {
        String query = "select * from users where email='" + email + "'";

        Connection con;
        Statement stmt;
        ResultSet rs;
    
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String password = rs.getString("password");
                return password;
            };
         }
         catch(Exception ex){
             System.out.println("Connection failed...");
             System.out.println(ex);
         }
        
        return "";
    }

}