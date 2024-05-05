package com.masters.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddEmployeeServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		
		//Employee Details Info
		String userName=req.getParameter("username");
		String firstName=req.getParameter("firstName");
		String lastName=req.getParameter("lastName");
		String gender=req.getParameter("gender");
		String dateOfBirth=req.getParameter("dateOfBirth");
		String joiningDate=req.getParameter("joiningDate");
		String department=req.getParameter("department");
		String password=req.getParameter("password");
		String bankName=req.getParameter("bankName");
		
		//contact Info Table
		String address=req.getParameter("address");
		String contactNumber=req.getParameter("contactNumber");
		String gmail=req.getParameter("gmail");
		
		//Salary Details
		String accountNumber=req.getParameter("accountNumber");
		String paymentMode=req.getParameter("paymentMode");
		String accountType=req.getParameter("accountType");
		int ratePerHour=Integer.parseInt(req.getParameter("ratePerHour"));
		
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("INSERT INTO EMPLOYEE.EMPLOYEE_DETAILS"
					+ "(USERNAME,FIRST_NAME, LAST_NAME, GENDER, DATE_OF_BIRTH, JOINING_DATE, DEPARTMENT, PASSWORD) "
					+ "VALUES(?,?,?,?,?,?,?,?)");
			ps.setString(1, userName);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, gender);
			ps.setString(5, dateOfBirth);
			ps.setString(6, joiningDate);
			ps.setString(7, department);
			ps.setString(8, password);
			
			PreparedStatement ps2=con.prepareStatement("INSERT INTO EMPLOYEE.EMP_CONTACT_INFO"
					+ "(USERNAME, ADDRESS, CONTACT_NUMBER, GMAIL) "
					+ "VALUES (?,?,?,?)");
			ps2.setString(1, userName);
			ps2.setString(2, address);
			ps2.setString(3, contactNumber);
			ps2.setString(4, gmail);
			
			PreparedStatement ps3=con.prepareStatement("INSERT INTO EMPLOYEE.EMP_SALARY_DETAILS"
					+ "(USERNAME, RATE_PER_HOUR) "
					+ "VALUES(?,?)");
			ps3.setString(1, userName);
			ps3.setInt(2, ratePerHour);
			
			PreparedStatement ps4=con.prepareStatement("INSERT INTO EMPLOYEE.EMP_ACCOUNT_DETAILS"
					+ "(USERNAME, ACCOUNT_NUMBER, PAYMENT_MODE, ACCOUNT_TYPE,Bank_Name) "
					+ "VALUES(?,?,?,?,?)");
			ps4.setString(1, userName);
			ps4.setString(2, accountNumber);
			ps4.setString(3, paymentMode);
			ps4.setString(4, accountType);
			ps4.setString(5, bankName);
			
			int empDetails=ps.executeUpdate();
			int empContactInfo=ps2.executeUpdate();
			int empSalaryDetails=ps3.executeUpdate();
			int empAccountDetails=ps4.executeUpdate();
			if(empDetails==1 && empContactInfo==1 && empSalaryDetails==1 && empAccountDetails==1) {
				req.getRequestDispatcher("RegistrationSuccessfull.html").forward(req, res);
			}
		}catch(SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			req.getRequestDispatcher("UserNameExists.html").forward(req, res);
		}catch(SQLException se) {
			se.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}
	}
}
