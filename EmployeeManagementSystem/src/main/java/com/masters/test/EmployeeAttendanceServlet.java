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

public class EmployeeAttendanceServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		String username=req.getParameter("username");
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql:///Employee","root","root");
			PreparedStatement ps=con.prepareStatement("SELECT At_date,In_time,Out_time,Total_Hours FROM EMPLOYEE.EMP_ATTENDANCE WHERE USERNAME=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			
			PreparedStatement ps2=con.prepareStatement("SELECT FIRST_NAME,LAST_NAME,Department FROM EMPLOYEE.EMPLOYEE_DETAILS"
					+ " WHERE USERNAME=? ");
			ps2.setString(1, username);
			ResultSet rs2=ps2.executeQuery();
			
			PreparedStatement ps3=con.prepareStatement("select count(At_Date) as NoOfDaysWorked,sum(total_hours) from employee.emp_attendance where username=?");
			ps3.setString(1, username);
			ResultSet rs3=ps3.executeQuery();
			
			PreparedStatement ps4=con.prepareStatement("SELECT contact_number,gmail FROM employee.emp_contact_info where username=?");
			ps4.setString(1, username);
			ResultSet rs4=ps4.executeQuery();
			
			
			if(rs2.next() && rs4.next()) {
				pw.print("<table align='center' border='4px solid black' width='100%' bgcolor='pink'>");
				pw.print("<tr><th colspan='6'><h2>Attendance Report</h2></th></tr>");
				pw.print("<tr><th>Employee Name</th><td>&nbsp&nbsp&nbsp"+rs2.getString(1)+" "+rs2.getString(2)+"</td><th>Department</th><td colspan='3'>"+rs2.getString(3)+"</td></tr>");
				pw.print("<tr><th>Username</th><td>&nbsp&nbsp&nbsp"+username+"</td><th>Contact</th><td>"+rs4.getString(1)+"</td><th>Mail</th><td>"+rs4.getString(2)+"</td></tr>");
				pw.print("<tr rowspan='2'><th colspan='6'>-</th></tr>");
				pw.print("<br><br><tr><th> </th><th> Dates </th><th> In Time </th><th> Out Time </th><th colspan='2'>Hours</th>");
				while(rs.next()) {
					pw.print("<tr><th> </th><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td  colspan='2'>"+rs.getString(4)+"</td>");
				}
				pw.print("<tr rowspan='2'><th colspan='6'>-</th></tr>");
				if(rs3.next()) {
						pw.print("<tr><th> </th><th colspan='2'>Days Worked</th><th colspan='3'>Total Hours</th>");
						pw.print("<tr><td></td><td colspan='2' ><b>"+rs3.getString(1)+"</b></td><td colspan='3'><b>"+rs3.getString(2)+"</b></td>");
				}
				pw.print("<tr rowspan='2'><th colspan='6'>-</th></tr>");
				pw.print("<tr rowspan='2'><th colspan='6'><a href='EmployeeAttendance.html'><img width='10%' height='10%' src='backButton.jpg'/></a></th></tr>");
				pw.print("</table></body>");
			}else {
				req.getRequestDispatcher("EmployeeNotFound.html").forward(req, res);
			}
			
		}catch(SQLException se) {
			se.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}catch(Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("SomethingWentWrong.html").forward(req, res);
		}
		
	}
}
