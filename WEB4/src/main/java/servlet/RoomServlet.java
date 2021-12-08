package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

public class RoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=Windows-1251");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>¬ход</title>");
        out.println("<meta charset=\"windows-1251\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("</head>");
        out.println("<body>");
        
        String floor = req.getParameter("floor");
        String rooms = GetFreeRooms(floor);

        String room = "";
        for (int i = 0; i < rooms.length(); i++)
        {
            if (rooms.charAt(i) != ' ')
            {
                room += rooms.charAt(i);
            }
            if ((rooms.charAt(i) == ' ') || (i == rooms.length() - 1))
            {
                out.print("<a href=\"Buy?floor=" + floor + "&room=" + room + "\">" + room + "</a><br>");
                room = "";
            }
        }
                
        out.println("</body>");
        out.println("</html>");
    }

    private String GetFreeRooms(String floor)
    {
        String query = "select * from floors where number='" + floor + "'";

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
                String rooms = rs.getString("freeRooms");
                return rooms;
            };
         }
         catch(Exception ex){
             System.out.println("Connection failed...");
             System.out.println(ex);
         }
        
        return "";
    }
}