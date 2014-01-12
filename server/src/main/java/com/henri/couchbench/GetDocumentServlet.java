package com.henri.couchbench;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
 
@SuppressWarnings("serial")
public class GetDocumentServlet extends HttpServlet {
  Logger logger = Logger.getLogger(this.getClass());

  Session s;
  Database db;

  @Override
  public void init() throws ServletException {
    s = new Session(getServletContext().getInitParameter("couchdb"), 5984);
    db = s.getDatabase("testdb");
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException {
    String id = req.getPathInfo().substring(1);
    PrintWriter out = res.getWriter();
    Document doc = db.getDocument(id);
    if (doc==null){
      res.setContentType("text/plain");
      out.println("Error: no document with id " + id +" found.");
    } else {
      res.setContentType("application/json");
      out.println(doc.getJSONObject());
    }
    out.close();
  }
}
