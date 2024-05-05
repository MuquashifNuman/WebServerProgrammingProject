package com.masters.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		String uname=req.getParameter("uname");
		String passwd=req.getParameter("passwd");
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("SELECT * FROM ADMIN_DETAILS WHERE USERNAME=? AND PASSWORD=?");
			ps.setString(1, uname);
			ps.setString(2, passwd);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				req.getRequestDispatcher("HomePage.html").forward(req, res);
			}else {
				req.getRequestDispatcher("InvalidCredentials.html").forward(req, res);
			}
		}catch(SQLException se) {
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}
	}
}
