

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.usp.data.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import sun.rmi.runtime.Log;

/**
 *
 * @author brett
 */
@WebServlet(urlPatterns = {"/Label"})
public class Label extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private long sentenceID = 0;

    protected Connection returnConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost/sugar",
                "root", "jar6677____;");
        return conn;

    }

    protected String returnRandomPhrase() {

        Date dt = new Date();
        Random r = new Random(dt.getTime());
        int x = r.nextInt(1999) + 1;

        try {

            Connection con = this.returnConnection();
            String sql = "select sentenceid, sentence from story_sentence "
                    + "where sentenceid >=? and length(sentence)> 10 limit 1 ";
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, x);
            ResultSet rs = stmt.executeQuery();
            String sentence = "";
            while (rs.next()) {

                sentence = rs.getString(2);
                this.sentenceID = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            con.close();

            System.out.println(x);
            //this.sentenceID = x;
            return sentence;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* TODO output your page here. You may use following sample code. */
        String category = "";
        String verb = "";
        String object = "";
        String subject = "";
        String sid = "";

             
        PrintWriter out = response.getWriter();

        
        try {

            response.setContentType("text/html;charset=UTF-8");
            if (request.getParameterMap().containsKey("category")) {

                Data data = new Data();

                category = request.getParameter("category");

                verb = request.getParameter("verb");

                subject = request.getParameter("subject");
                System.out.println(category);
                object = request.getParameter("object");
                sid = request.getParameter("sentid");
                sid = sid.trim();

                data.updateAnnotation(category, sid);
                if (category.equalsIgnoreCase("causative")) {
                    data.updateAnnotation(sid, subject, object, verb);
                }
            }

            String phrase = this.returnRandomPhrase();
            out.println(phrase + "###" + this.sentenceID);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
