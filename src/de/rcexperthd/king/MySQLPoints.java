package de.rcexperthd.king;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class MySQLPoints {

	public static boolean isUserExists(UUID uuid) {
		try {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Punkte FROM PunkteSystem WHERE UUID = ?");
		ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		return rs.next();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void update(UUID uuid, int amount, boolean remove, String playername) {
		int points = getPoints(uuid);
		
		int value = 0;

		if(remove) {
			value = points - amount;
		} else {
			value = points + amount;
		}
		
		if(isUserExists(uuid)) {
			
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE PunkteSystem SET Punkte = ? WHERE UUID = ?");
				ps.setInt(1, value);
				ps.setString(2, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO PunkteSystem(UUID,Spielername,Punkte) VALUES(?,?,?)");
				ps.setString(1, uuid.toString());
				ps.setString(2, playername);
				ps.setInt(3, amount);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void delete(UUID uuid) {
		if(isUserExists(uuid)) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE * FROM PunkteSystem WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("[MySQL] Der Spieler mit der UUID: " +uuid.toString()+ " ist nicht in der Datenbank registriert!");
		}
	}
	
	public static Integer getPoints(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Punkte FROM PunkteSystem WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return(rs.getInt("Punkte"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	} 
	
	
}
