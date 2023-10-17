package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Destino;
import model.PacoteViagem;

public class PacoteViagemCrud {
	
	
	public static void inserir(PacoteViagem pacote) {
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			
			String sql = "INSERT INTO pacoteviagem"
					+" (data_ida, data_volta, preco, num_max_prestacoes, fk_Destino_id)"
					+" VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setDate(1, Date.valueOf(pacote.getDataIda()));
			pstm.setDate(2, Date.valueOf(pacote.getDataVolta()));
			pstm.setFloat(3, pacote.getPreco());
			pstm.setInt(4, pacote.getNumMaxPrestacoes());
			pstm.setInt(5, pacote.getDestino().getId());
			
			pstm.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<PacoteViagem> buscarTodos() {
		
		List<PacoteViagem> pacotesViagem = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "SELECT id, data_ida, data_volta, preco, num_max_prestacoes, fk_Destino_id"
					+" FROM pacoteviagem ORDER BY id";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rset = pstm.executeQuery();
			while (rset.next()) {
				PacoteViagem pacote = rsetToPacoteViagem(rset);
				pacotesViagem.add(pacote);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return pacotesViagem;
	}
	
	public static PacoteViagem buscarPorId(int id) {
		
		PacoteViagem pacote = null;
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "SELECT id, data_ida, data_volta, preco, num_max_prestacoes"
					+", fk_Destino_id FROM pacoteviagem WHERE id = ?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rset = pstm.executeQuery();
			if (rset.next()) {
				pacote = rsetToPacoteViagem(rset);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return pacote;
	}
	
	public static void atualizar(PacoteViagem pacote) {
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "UPDATE pacoteviagem SET data_ida = ?, data_volta = ?, preco = ?"
					+", num_max_prestacoes = ?, fk_Destino_id = ? WHERE id = ?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setDate(1, Date.valueOf(pacote.getDataIda()));
			pstm.setDate(2, Date.valueOf(pacote.getDataVolta()));
			pstm.setFloat(3, pacote.getPreco());
			pstm.setInt(4, pacote.getNumMaxPrestacoes());
			pstm.setInt(5, pacote.getDestino().getId());
			pstm.setInt(6, pacote.getId());
			
			pstm.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int excluir(int idPacoteViagem) {
		
		int numLinhasExcluidas = 0;
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "DELETE FROM pacoteviagem WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idPacoteViagem);
			
			numLinhasExcluidas = pstm.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return numLinhasExcluidas;
	}
	
	public static PacoteViagem rsetToPacoteViagem(ResultSet rset) throws SQLException {
		
		PacoteViagem pacote = new PacoteViagem();
		pacote.setId(rset.getInt("id"));
		pacote.setDataIda(rset.getDate("data_ida").toLocalDate());
		pacote.setDataVolta(rset.getDate("data_volta").toLocalDate());
		pacote.setPreco(rset.getFloat("preco"));
		pacote.setNumMaxPrestacoes(rset.getInt("num_max_prestacoes"));
		
		Destino destino = DestinoCrud.buscarPorId(rset.getInt("fk_Destino_id"));
		pacote.setDestino(destino);
				
		return pacote;
	}
}