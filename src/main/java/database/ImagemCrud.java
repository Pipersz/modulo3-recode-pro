package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.Imagem;

public class ImagemCrud {

	public static void inserir(Imagem imagem) {
		
		Connection conn = null;

		try {
			String sql = "INSERT INTO imagem (url) VALUES (?)";
			conn = ConnectionFactory.createConnectionToMySQL();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, imagem.getUrl());
			pstm.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
	}
}
