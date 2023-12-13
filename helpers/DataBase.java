package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Classes.FrameModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class DataBase {
    @FXML
    TableView<FrameModel> frameTable;
	public static Connection connecterBase() {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/can";
		String user = "root";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("connexion �tablie.");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("erreur de connexion !");
		}
		return con;
	}
	public void insertFrame(String identifier, String dlc, String data, String frameName) {
        Connection con = connecterBase();

        if (con != null) {
            try {
                String query = "INSERT INTO can_frames (IDENTIFIER, DLC, DATA, Frame_Name) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, identifier);
                    pstmt.setString(2, dlc);
                    pstmt.setString(3, data);
                    pstmt.setString(4, frameName);

                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}