package com.masters.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteEmployeeServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		
		String userName=req.getParameter("username");
		
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("DELETE FROM EMPLOYEE.EMP_ACCOUNT_DETAILS WHERE USERNAME=?");
			ps.setString(1, userName);
			
			PreparedStatement ps1=con.prepareStatement("DELETE FROM EMPLOYEE.EMP_SALARY_DETAILS WHERE USERNAME=?");
			ps1.setString(1, userName);
			
			PreparedStatement ps2=con.prepareStatement("DELETE FROM EMPLOYEE.EMP_PAYMENT_DETAILS WHERE USERNAME=?");
			ps2.setString(1, userName);
			
			PreparedStatement ps3=con.prepareStatement("DELETE FROM EMPLOYEE.EMP_CONTACT_INFO WHERE USERNAME=?");
			ps3.setString(1, userName);
			
			PreparedStatement ps4=con.prepareStatement("DELETE FROM EMPLOYEE.EMP_ATTENDANCE WHERE USERNAME=?");
			ps4.setString(1, userName);
			
			PreparedStatement ps5=con.prepareStatement("DELETE FROM EMPLOYEE.EMPLOYEE_DETAILS WHERE USERNAME=?");
			ps5.setString(1, userName);
			
			int emp_account_details=ps.executeUpdate();
			int emp_salary_details=ps1.executeUpdate();
			int emp_payment_details=ps2.executeUpdate();
			int emp_contact_info=ps3.executeUpdate();
			int emp_attendance=ps4.executeUpdate();
			int emp_details=ps5.executeUpdate();
			System.out.println(emp_account_details);
			System.out.println(emp_salary_details);
			System.out.println(emp_payment_details);
			System.out.println(emp_contact_info);
			System.out.println(emp_attendance);
			System.out.println(emp_details); 
			if(emp_account_details==1 || emp_salary_details==1 || emp_payment_details==1 || emp_contact_info==1 || emp_attendance==1 || emp_details==1) {
				req.getRequestDispatcher("EmployeeDeleted.html").forward(req, res);
			}else {
				req.getRequestDispatcher("EmployeeNotFound.html").forward(req, res);
			}
			
		}catch(SQLException se) {
			se.printStackTrace();
		}
		
	}
}
