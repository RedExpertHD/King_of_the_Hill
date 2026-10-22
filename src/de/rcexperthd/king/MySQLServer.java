package de.rcexperthd.king;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MySQLServer {
	
	public static boolean isServerExists(String Servername) {
		try {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Status FROM ServerSystem WHERE Servername = ?");
		ps.setString(1, Servername.toString());
		ResultSet rs = ps.executeQuery();
		return rs.next();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void update(String Servername, int status) {

		
		if(isServerExists(MySQL.Servername)) {
			
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE ServerSystem SET Status = ? WHERE Servername = ?");
				ps.setInt(1, status);
				ps.setString(2, Servername.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO ServerSystem(Servername, Status) VALUES(?,?)");
				ps.setString(1, Servername.toString());
				ps.setInt(2, status);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void delete(String Servername) {
		if(isServerExists(MySQL.Servername)) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE * FROM PunkteSystem WHERE Servername = ?");
				ps.setString(1, Servername.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("[MySQL] Der Server " +Servername.toString()+ " ist nicht in der Datenbank registriert!");
		}
	}
	
	public static Integer getPoints(String Servername) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Status FROM ServerSystem WHERE SERVERNAME = ?");
			ps.setString(1, Servername.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return(rs.getInt("Status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	} 

}
