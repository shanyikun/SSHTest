package com.ssh.syk.servlet.error;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Error404 extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Error404() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.write("this is a 404 page!!");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}
