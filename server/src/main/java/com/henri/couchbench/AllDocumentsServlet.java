package com.henri.couchbench;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;

@SuppressWarnings("serial")
public class AllDocumentsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Session s = new Session(getServletContext().getInitParameter("couchdb"), 5984);
		Database db = s.getDatabase("testdb");

		ViewResults results = db.getAllDocuments();
		PrintWriter out = res.getWriter();

		res.setContentType("text/plain");
		for(Object doc : results.entrySet()) {
		    out.println(doc);
		}
		out.close();
	}

}
