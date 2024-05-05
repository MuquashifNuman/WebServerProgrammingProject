package com.masters.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		
		
		String firstName=req.getParameter("firstName");
		String lastName=req.getParameter("lastName");
		String gender=req.getParameter("gender");
		String dateOfBirth=req.getParameter("dateOfBirth");
		String department=req.getParameter("department");
		String userName=req.getParameter("username");
		String password=req.getParameter("password");
		String address=req.getParameter("address");
		String contactNumber=req.getParameter("contactNumber");
		String gmail=req.getParameter("gmail");
		
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("INSERT INTO employee.admin_details"
					+ "(FIRST_NAME,LAST_NAME,GENDER,DATE_OF_BIRTH,CONTACT_NUMBER,GMAIL,USERNAME,PASSWORD) "
					+ "VALUES(?,?,?,?,?,?,?,?)");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, gender);
			ps.setString(4, dateOfBirth);
			ps.setString(5, contactNumber);
			ps.setString(6, gmail);
			ps.setString(7, userName);
			ps.setString(8, password);			
			int employeeDetailsTableValue=ps.executeUpdate();
			System.out.println("EmployeeDetails"+employeeDetailsTableValue);
			
			if(employeeDetailsTableValue==1 ){
				req.getRequestDispatcher("RegistrationSuccessfull.html").forward(req, res);
			}else {
				req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
			}
		}catch(SQLIntegrityConstraintViolationException se) {
			req.getRequestDispatcher("UserNameExists.html").forward(req, res);
		}catch(Exception e){
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}
	}
}
