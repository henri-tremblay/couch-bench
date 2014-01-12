package com.henri.couchbench;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;

@SuppressWarnings("serial")
public class InjectDocumentsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

    PrintWriter out = res.getWriter();
    res.setContentType("text/plain");

    if(req.getPathInfo() == null) {
      out.println("You should provide a number of documents to insert. Example: /inject/10");
      out.close();
      return;
    }

    String param = req.getPathInfo().substring(1);
    int size = Integer.valueOf(param);

		Session s = new Session(getServletContext().getInitParameter("couchdb"), 5984);
		Database db = s.getDatabase("testdb");

		Random rand = new Random();
		byte[] value = new byte[4000];
	    
		for (int i = 0; i < size; i++) {
			Document newdoc = new Document();
			rand.nextBytes(value);
			newdoc.put("value", value);
			db.saveDocument(newdoc);
			out.println(i + ":" + newdoc.getId());
        if(i % 100 == 0) {
          out.flush();
        }
		}
		out.close();
	}

}
