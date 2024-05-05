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

public class EmployeeDetailsServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		String username=req.getParameter("username");
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("SELECT username, First_name, last_name, gender, date_of_birth, joining_date, department "
					+ "FROM employee.employee_details where username=?");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			
			PreparedStatement ps1=con.prepareStatement("SELECT address,Contact_number,gmail FROM employee.emp_contact_info where username=?");
			ps1.setString(1, username);
			ResultSet rs1=ps1.executeQuery();
			
			PreparedStatement ps2=con.prepareStatement("SELECT Account_Number, payment_mode, Account_Type FROM employee.emp_account_details where username=?");
			ps2.setString(1, username);
			ResultSet rs2=ps2.executeQuery();
			
			PreparedStatement ps3=con.prepareStatement("SELECT Rate_Per_Hour,Tax FROM employee.emp_salary_details where username=?");
			ps3.setString(1, username);
			ResultSet rs3=ps3.executeQuery();
			
		
			if(rs.next() || rs1.next() || rs2.next() || rs3.next() ) {
				pw.print("<body>");
				pw.print("<table border='1px solid black' width='100%' bgcolor='pink'>");
				pw.print("<tr><th colspan='2'><h2>Employee Personal Details</h2></th></tr>");
				pw.print("<tr><th>Name</th><td><b>"+rs.getString(2)+" "+rs.getString(3)+"</b></td></tr>"
						+ "<tr><th>Username</th><td><b>"+rs.getString(1)+"</b></td></tr>"
								+ "<tr><th>Gender</th><td>"+rs.getString(4)+"</td></tr>"
										+ "<tr><th>Date of Birth</th><td>"+rs.getString(5)+"</td></tr>"
												+ "<tr><th>Joining Date</th><td>"+rs.getString(6)+"</td></tr>"
								+ "<tr><th>Department</th><td>"+rs.getString(7)+"</td></tr>");
				pw.print("<tr><th colspan='2'><h2>Contact Details</h2></th></tr>");
				
				if(rs1.next()) {
				pw.print("<tr><th>Address</th><td>"+rs1.getString(1)+"</td></tr>"
						+ "<tr><th>Contact Number</th><td>"+rs1.getString(2)+"</td></tr>"
								+ "<tr><th>Gmail</th><td>"+rs1.getString(3)+"</td></tr>");
				}else {
					pw.print("<tr><th colspan='2'><h3>Contact Details Not Found</h3></th></tr>");
				}
				
				pw.print("<tr><th colspan='2'><h2>Bank Details</h2></th></tr>");
				if(rs2.next()) {
					pw.print("<tr><th>Account Number</th><td>"+rs2.getString(1)+"</td></tr>"
							+ "<tr><th>Account Type</th><td>"+rs2.getString(2)+"</td></tr>"
									+ "<tr><th>Payment Mode</th><td>"+rs2.getString(3)+"</td></tr>");
				}else {
					pw.print("<tr><th colspan='2'><h3>Bank Details Not Found</h3></th></tr>");
				}
				
				
				pw.print("<tr><th colspan='2'><h2>Salary Details</h2></th></tr>");
				if(rs3.next()) {
					pw.print("<tr><th>Rate Per Hour</th><td>"+rs3.getInt(1)+"</td></tr>"
							+ "<tr><th>Tax</th><td>"+rs3.getString(2)+"%</td></tr>");
				}else {
					pw.print("<tr><th colspan='2'><h3 color='Red'>Salary Details Not Found</h3></th></tr>");
				}
				pw.print("<tr><th colspan='2' ><a href='EmployeeDetails.html'><img width='10%' height='10%' src='backButton.jpg'/></a>&nbsp"
						+ "<a href='HomePage.html'><img width='10%' height='10%' src='Home.jpg'/></a></th></tr>");
			}else {
				req.getRequestDispatcher("EmployeeNotFound.html").forward(req, res);
			}
			pw.print("</table>");
			pw.println("</body>");
		}catch(SQLException se) {
			se.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}catch(Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}
	}
}
