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

public class EmployeePaymentDetailsServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
			
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		String userName=req.getParameter("username");
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("SELECT first_name,last_name FROM employee.employee_details where username=?");
			ps.setString(1, userName);
			ResultSet rs=ps.executeQuery();
			
			PreparedStatement ps2=con.prepareStatement("SELECT address,contact_number,gmail FROM employee.emp_contact_info where username=?");
			ps2.setString(1, userName);
			ResultSet rs2=ps2.executeQuery();
			
			PreparedStatement ps3=con.prepareStatement("SELECT count(At_Date),sum(Total_Hours) FROM employee.emp_attendance where Username=?");
			ps3.setString(1, userName);
			ResultSet rs3=ps3.executeQuery();
			
			PreparedStatement ps4=con.prepareStatement("SELECT AmountPaid, Status, Pay_Date, Payment_id FROM employee.emp_payment_details where Username=?");
			ps4.setString(1, userName);
			ResultSet rs4=ps4.executeQuery();
		
			PreparedStatement ps5=con.prepareStatement("SELECT Rate_Per_Hour,Tax FROM employee.emp_salary_details where username=?");
			ps5.setString(1, userName);
			ResultSet rs5=ps5.executeQuery();
			
			PreparedStatement ps6=con.prepareStatement("SELECT Account_Number, payment_mode, Account_Type FROM employee.emp_account_details where username=?");
			ps6.setString(1, userName);
			ResultSet rs6=ps6.executeQuery();
			int paymentId=0;
			int amountPaid=0;
			String status="";
			String payDate="";
				if(rs4.next()) {
					paymentId=rs4.getInt(4);
					amountPaid=rs4.getInt(1);
					status=rs4.getString(2);
					payDate=rs4.getString(3);
				}
				if(rs.next()) {
					pw.print("<table border='1px solid black' width='100%' bgcolor='pink'>");
					pw.print("<tr><th colspan='4'><h2>Employee Pay Slip</h2></th><th colspan='2'><h2>Payment Id - TX100"+paymentId+"</h2></th></tr>");
					pw.print("<tr><th><h3>Name</h3></th><td>"+rs.getString(1)+" "+rs.getString(2)+"</td>"
							+ "<th><h3>Username</h3></th><td>"+userName+"</td><th colspan='2'><h2>Date : "+java.time.LocalDate.now()+"</h2></th></tr>");
				}else {
					req.getRequestDispatcher("EmployeeNotFound.html").forward(req, res);
				}
			
				if(rs2.next()) {
					pw.print("<tr><th>Address:</th><td>"+rs2.getString(1)+"</td><th>Contact:</th><td>"
				+rs2.getString(2)+"</td><th>Gmail</th><td>"+rs2.getString(3)+"</td></tr>");
				}
			
				if(rs5.next()) {
					pw.print("<tr><th>Rate Per Hour</th><td>"+rs5.getInt(1)+""
							+ "</td><th>Tax</th><td>"+rs5.getString(2)+"%</td></tr>");
				}
				
				if(rs6.next()) {
					pw.print("<tr><th>Account Number</th><td>"+rs6.getString(1)+""
							+ "</td><th>Account Type</th><td>"+rs6.getString(3)+"</td><th>Payment Mode</th><td>"+rs6.getString(2)+"</td></tr>");
				}
				
				if(rs3.next()) {
					pw.print("<tr><th>Days Worked</th><td>"+rs3.getString(1)+"</td><th>Total Hours Worked</th><td>"+rs3.getString(2)+"</td></tr>");
				}
				pw.print("<tr><th>Amount</th><td>"+amountPaid+"</td><th>Status</th><td>"+status+"</td><th>Pay Date</th><td>"+payDate+"</td></tr>");
				pw.print("<tr><th colspan='6'><a href='EmployeePaymentDetails.html'><img width='10%' height='10%' src='backButton.jpg'/></a>&nbsp"
						+ "<a href='HomePage.html'><img width='10%' height='10%' src='Home.jpg'/></a></th></tr>");
		}catch(SQLException se) {
			se.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}catch(Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}
	}

}
