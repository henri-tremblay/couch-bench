package com.henri.couchbench;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;

@SuppressWarnings("serial")
public class InjectDocumentsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Session s = new Session("localhost", 5984);
		Database db = s.getDatabase("testdb");

		Random rand = new Random();
		byte[] value = new byte[4000];
		
		PrintWriter out = res.getWriter();
	    res.setContentType("text/plain");
	    
		for (int i = 0; i < 10000; i++) {
			Document newdoc = new Document();
			rand.nextBytes(value);
			newdoc.put("value", value);
			db.saveDocument(newdoc);
			out.println(i + ":" + newdoc.getId());
		}
		out.println("Done!");
		out.close();
	}

}
