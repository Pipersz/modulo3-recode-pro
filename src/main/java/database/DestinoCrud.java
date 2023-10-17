package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import model.Destino;
import model.Imagem;

public class DestinoCrud {

	public static void inserir(Destino destino) {
		
		Connection conn = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			String sql = "INSERT INTO destino (nome, descricao) VALUES (?, ?)";
			PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, destino.getNome());
			pstm.setString(2, destino.getDescricao());
			pstm.execute();
			
			ResultSet generatedKeys = pstm.getGeneratedKeys();
			if (generatedKeys.next() == false) {
				throw new Exception("Erro ao inserir destino");
			}
			
			destino.setId(generatedKeys.getInt(1));
			int idDestino = destino.getId();
			
			for (Imagem imagem : destino.getImagens()) {
				String sqlImagem = "INSERT INTO imagem (url, fk_Destino_id) VALUES (?, ?)";
				PreparedStatement pstmImagem = conn.prepareStatement(sqlImagem);
				pstmImagem.setString(1, imagem.getUrl());
				pstmImagem.setInt(2, idDestino);
				pstmImagem.execute();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
	}
	
	public static List<Destino> buscarTodos() {
		
		List<Destino> destinos = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sqlDestinos = "SELECT id, nome, descricao FROM destino";
			
			PreparedStatement pstm = conn.prepareStatement(sqlDestinos);
			ResultSet rset = pstm.executeQuery();
			
			while (rset.next()) {
				 Destino destino = resultSetToDestino(rset, conn);
				 destinos.add(destino);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return destinos;
	}
	
	public static List<Destino> buscarPorNome(String nome) {
		
		List<Destino> destinos = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			
			String sql = "SELECT id, nome, descricao FROM destino WHERE nome = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, nome);
			
			ResultSet rset = pstm.executeQuery();
			
			while (rset.next()) {
				
				Destino destino = resultSetToDestino(rset, conn);
				destinos.add(destino);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return destinos;
	}
	
	public static Destino buscarPorId(int id) {
		
		Destino destino = null;
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			
			String sql = "SELECT id, nome, descricao FROM destino WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
			
			ResultSet rset = pstm.executeQuery();
			
			if (rset.next()) {
				
				destino = resultSetToDestino(rset, conn);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return destino;
	}
	
	public static void atualizar(Destino destino) {
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			
			String sql = "UPDATE destino SET nome = ?, descricao = ? WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, destino.getNome());
			pstm.setString(2, destino.getDescricao());
			pstm.setInt(3, destino.getId());
			pstm.execute();
			
			String sqlDeleteImagens = "DELETE FROM imagem WHERE fk_Destino_id = ?";
			PreparedStatement pstmDelete = conn.prepareStatement(sqlDeleteImagens);
			pstmDelete.setInt(1, destino.getId());
			pstmDelete.execute();
			
			for (Imagem imagem: destino.getImagens()) {
				String sqlInsertImagem = "INSERT INTO imagem (url, fk_Destino_id) VALUES (?, ?)";
				PreparedStatement pstmInsert = conn.prepareStatement(sqlInsertImagem);
				pstmInsert.setString(1, imagem.getUrl());
				pstmInsert.setInt(2, destino.getId());
				pstmInsert.execute();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int excluir(int id) {
		
		int affectedRowsCount = 0;
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "DELETE FROM destino WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			affectedRowsCount = pstm.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return affectedRowsCount;
	}
	
	private static Destino resultSetToDestino(ResultSet rset, Connection conn) throws SQLException {
		Destino destino = new Destino();
		
		destino.setId(rset.getInt("id"));
		destino.setNome(rset.getString("nome"));
		destino.setDescricao(rset.getString("descricao"));
		
		String sqlImagens = "SELECT id, url FROM imagem WHERE fk_Destino_id = ?";
		
		PreparedStatement pstmImagens = conn.prepareStatement(sqlImagens);
		pstmImagens.setInt(1, destino.getId());
		
		ResultSet rsetImagens = pstmImagens.executeQuery();
		
		List<Imagem> imagens = new ArrayList<>();
		while (rsetImagens.next()) {
			Imagem imagem = resultSetToImagem(rsetImagens);
			imagens.add(imagem);
		}
		
		destino.setImagens(imagens);
		
		return destino;
	}
	
	private static Imagem resultSetToImagem(ResultSet rset) throws SQLException {
		Imagem imagem = new Imagem();
		
		imagem.setId(rset.getInt("id"));
		imagem.setUrl(rset.getString("url"));
		
		return imagem;
	}
}
