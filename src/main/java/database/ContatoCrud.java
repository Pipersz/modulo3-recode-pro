package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Contato;

public class ContatoCrud {

	public static void inserir(Contato contato) {
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "INSERT INTO contato (nome, telefone, email, opcao_contato)"
					+" VALUES (?, ?, ?, ?)";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, contato.getNome());
			pstm.setString(2, contato.getTelefone());
			pstm.setString(3, contato.getEmail());
			pstm.setInt(4, contato.getOpcaoContato());
			
			pstm.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Contato> buscarTodos() {
		
		List<Contato> contatos = new ArrayList<>();
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "SELECT id, nome, telefone, email, opcao_contato FROM contato";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			ResultSet rset = pstm.executeQuery();
			
			while (rset.next() ) {
				Contato contato = rsetToContato(rset);
				contatos.add(contato);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return contatos;
	}
	
	public static int excluir(int id) {
		
		int numLinhasExcluidas = 0;
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "DELETE FROM contato WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
			
			numLinhasExcluidas = pstm.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return numLinhasExcluidas;
	}
	
	private static Contato rsetToContato(ResultSet rset) throws SQLException {
		
		Contato contato = new Contato();
		
		contato.setId(rset.getInt("id"));
		contato.setNome(rset.getString("nome"));
		contato.setTelefone(rset.getString("telefone"));
		contato.setEmail(rset.getString("email"));
		contato.setOpcaoContato(rset.getInt("opcao_contato"));
		
		return contato;
	}
}
