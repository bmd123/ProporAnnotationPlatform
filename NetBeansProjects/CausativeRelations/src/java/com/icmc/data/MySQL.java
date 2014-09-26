/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.icmc.data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.Date;
import java.sql.*;

/**
 *
 * @author posdoc
 */
@WebServlet(name = "MySQL", urlPatterns = {"/MySQL"})
public class MySQL extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected int sentenceID = 0;
    
    
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
        int x = r.nextInt(1999)+1;
        
        try {
            Connection con =  this.returnConnection();
            String sql = "select sentenceid, sentence from story_sentence "
                + "where sentenceid >=? limit 1 ";
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, x);
            ResultSet rs = stmt.executeQuery();
            String sentence = "";
            while (rs.next())
                sentence = rs.getString(2);
            rs.close();
            stmt.close();
            con.close();
            
            this.sentenceID = x;
            return sentence;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        
        
        
    }
    
  
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            String phrase = this.returnRandomPhrase();
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MySQL</title>"); 
            out.println(phrase);
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MySQL at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
