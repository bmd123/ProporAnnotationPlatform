package br.usp.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author posdoc
 */
public class Data extends HttpServlet {

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

    
    protected String Export() {
        //com.sun.rowset.CachedRowSetImpl crs = null;
        java.lang.StringBuffer textExport = new java.lang.StringBuffer();        
        try {
           
            Connection connection = this.returnConnection();
            String sql = " select sentence, category, subject, verb, object"
                    + " from  sugar.Vexport ";
            PreparedStatement stmt = connection.prepareCall(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                textExport.append(rs.getString(1).replace('\n', ' '));
                textExport.append("\t");
                
                textExport.append(rs.getString(2).replace('\n', ' '));
                textExport.append("\t");
                
                textExport.append(rs.getString(3).replace('\n', ' '));
                textExport.append("\t");
                
                textExport.append(rs.getString(4).replace('\n', ' '));
                textExport.append("\t");
                
                textExport.append(rs.getString(5).replace('\n', ' '));
                textExport.append("\n");
            }
            
            rs.close();
            stmt.close();
            connection.close();
        }
        
        catch (Exception e){
            
            System.out.println(e.getMessage());
            
        }
        return textExport.toString(); 
        
        
        
    }
    
    protected Connection returnConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost/sugar",
                    "root", "jar6677____;");
            System.out.println(conn);
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }

    public void updateAnnotation(String sid, String subject, String object,
            String verb) {

        try {

            String tableName = "annotations";
            String sql = "insert into " + tableName;
            sql += " (sentenceid,subject, verb,object) values (?,?,?,?); ";
            int id = Integer.parseInt(sid);

            Connection con = this.returnConnection();
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setString(2, subject);
            stmt.setString(3, verb);
            stmt.setString(4, object);
            stmt.execute();
            stmt.close();
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateAnnotation(String category, String sid) {
        try {

            String tableName = "story_sentence_category";
            String sql = "insert into " + tableName;
            sql += " (sentenceid,category) values (?,?); ";

            System.out.println(sid);
            
            int id = Integer.parseInt(sid);

            Connection con = this.returnConnection();
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setString(2, category);
            stmt.execute();
            stmt.close();
            
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }

        }  catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String returnRandomPhrase() {

        Date dt = new Date();
        Random r = new Random(dt.getTime());
        int x = r.nextInt(1999) + 1;

        try {

            Connection con = this.returnConnection();
            System.out.println("entered");

            String sql = "select sentenceid, sentence from story_sentence "
                    + "where sentenceid >=? and length(sentence)> 10 limit 1 ";
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, x);
            ResultSet rs = stmt.executeQuery();
            String sentence = "";
            while (rs.next()) {
                sentence = rs.getString(2);
            }

            System.out.println(sentence);

            rs.close();
            stmt.close();
            con.close();

            this.sentenceID = x;
            return sentence;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            System.out.println(e.getMessage());
            return e.getMessage();
        }

    }

    public void exportAnnotations(HttpServletResponse response) throws Exception {
        String export = this.Export();
     
        response.setContentType("text/Plain");
        response.setHeader("Content-Disposition", 
                "attachment;filename=data.txt");
        
        int read = 0;
        InputStream input = new ByteArrayInputStream(export.getBytes("UTF8"));
        byte[] bytes = new byte[1024];
        
        OutputStream os = response.getOutputStream();
        while ((read=input.read(bytes))!=-1){
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
        
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String export = request.getParameter("export");
            if (export!=null && !export.isEmpty()){
                try {
                 this.exportAnnotations(response);
                }
                
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                
            }
            
            
            String id = request.getParameter("id");
            String non = request.getParameter("non");

            id = id.trim();

            if (id != null && !id.isEmpty()) {

                if (non.equals("True")) {
                    non = "Non-Causative";
                } else {
                    non = "Causative";
                }

                this.updateAnnotation(non, id);
            }

            String phrase = this.returnRandomPhrase();
            phrase = "<font size='2'>" + phrase + "</font>";

            String tmp = "<input type=\"hidden\" name=\"ID\" "
                    + "value=\"" + sentenceID + "\">";

            if (phrase.trim().length() > 1) {
                out.println(phrase);
                out.println(tmp);
            }

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
