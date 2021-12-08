package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Buy extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            String floor = (String)request.getParameter("floor");
            String room = (String)request.getParameter("room");
            
            HttpSession session = request.getSession();
            String email = (String)session.getAttribute("email");
            BuyRoom(email, floor, room);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Забронировано</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Номер забронирован</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void BuyRoom(String email, String floor, String room)
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
            String rooms = "";
            while (rs.next()) {
                rooms = rs.getString("rooms");
            };
            
            query = "UPDATE `users` SET `rooms`=\"" + rooms + " " + room + "\" WHERE `email`='" + email + "'";
            stmt.executeUpdate(query);
            
            query = "select * from floors where number='" + floor + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                rooms = rs.getString("freeRooms");
            };
            
            rooms = rooms.replace(room + " ", "");
            rooms = rooms.replace(" " + room, "");
            
            query = "UPDATE `floors` SET `freeRooms`=\"" + rooms + "\" WHERE `number`=" + floor;
            stmt.executeUpdate(query);
         }
         catch(Exception ex){
             System.out.println("Connection failed...");
             System.out.println(ex);
         }
        
    }
}
