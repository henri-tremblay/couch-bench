package com.henri.couchbench;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
		@SuppressWarnings("unchecked")
    List<JSONObject> rows = (List<JSONObject>) results.get("rows");
		int i  = 0;
		for(JSONObject doc : rows) {
		    String line = i + ":" + doc.getString("id");
		    out.println(line);
		    i++;
        if(i % 100 == 0) {
          out.flush();
        }
		}
		out.close();
	}

}
