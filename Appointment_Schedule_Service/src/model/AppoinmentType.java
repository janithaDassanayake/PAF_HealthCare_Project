package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import beans.AppoinmentTypeBean;
import util.DBconnection;



public class AppoinmentType {
	
	DBconnection dbObj = new DBconnection();
	//===================== View Appointment Types ==========================
	
	public String viewAppointmentTypes() {

		String output = "";
		
		AppoinmentTypeBean  TypeRead = new AppoinmentTypeBean();
		
		try {
			Connection con = dbObj.connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Appointment ID</th>"
					+ "<th>Appointment Type</th><th>Appointment Name</th>" + "<th>Appointment Desc</th></tr>";

			String query = "select * from appointment_type";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				TypeRead.setAppointment_Id(rs.getInt("Appointment_Id"));
				TypeRead.setAppointment_Type(rs.getString("Appointment_Type"));
				TypeRead.setAppointment_Name(rs.getString("Appointment_Name"));
				TypeRead.setAppointment_Desc(rs.getString("Appointment_Desc"));

				// Add into the html table
				output += "<tr><td>" + TypeRead.getAppointment_Id() + "</td>";
				output += "<td>" + TypeRead.getAppointment_Type() + "</td>";
				output += "<td>" + TypeRead.getAppointment_Name() + "</td>";
				output += "<td>" + TypeRead.getAppointment_Desc() + "</td>";
			}
			con.close();
			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the Doctors Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	//========================== Add In To Appointment Types =========================
	
		public String addAppointmentType(AppoinmentTypeBean TypeBean) {

			String output = "";
			try {

				Connection con = dbObj.connect();
				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = " INSERT INTO appointment_type (Appointment_Type, Appointment_Name, Appointment_Desc) VALUES (?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, TypeBean.getAppointment_Type());
				preparedStmt.setString(2, TypeBean.getAppointment_Name());
				preparedStmt.setString(3, TypeBean.getAppointment_Desc());

				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";

			} catch (Exception e) {
				output = "Error while inserting";
				System.err.println(e.getMessage());
			}

			return output;
		}
	
	
		//============================= Update Appointment Type ==============================
		
			public String updateAppointmentType(AppoinmentTypeBean TypeBean) {

				String output = "";

				try {
					Connection con = dbObj.connect();
					if (con == null) {
						return "Error while connecting to the database for updating.";
					}
					// create a prepared statement
					String query = "UPDATE appointment_type SET Appointment_Type=?,Appointment_Name=?,Appointment_Desc=? WHERE appointment_Id =?";
					PreparedStatement preparedStmt = con.prepareStatement(query);

					// binding values

					preparedStmt.setString(1, TypeBean.getAppointment_Type());
					preparedStmt.setString(2, TypeBean.getAppointment_Name());
					preparedStmt.setString(3, TypeBean.getAppointment_Desc());
					preparedStmt.setInt(4, TypeBean.getAppointment_Id());
					// execute the statement
					preparedStmt.execute();
					con.close();
					output = "Updated successfully [ ID : "+TypeBean.getAppointment_Id()+" ]";
				} catch (Exception e) {
					output = "Error while updating the Appoinment type " + TypeBean.getAppointment_Id();
					System.err.println(e.getMessage());
				}
				return output;
			}


	//============================= Delete Appointment Type ==============================	
	
	public String deleteAppointmentTypes(String app_id) {
		String output = "";
		try {

			Connection con = dbObj.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "DELETE FROM appointment_type WHERE appointment_Id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, app_id);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully [ Appointment Id : "+app_id +" ]";

		} catch (Exception e) {

			output = "Error while deleting the Appoinmet Type" + app_id;
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	
}