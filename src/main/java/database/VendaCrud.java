package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.PacoteViagem;
import model.Venda;

public class VendaCrud {

	public static void inserir(Venda venda) {
		
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "INSERT INTO venda (fk_Cliente_cpf, fk_PacoteViagem_id, num_prestacoes)"
					+" VALUES (?, ?, ?)";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, venda.getCliente().getCpf());
			pstm.setInt(2, venda.getPacoteViagem().getId());
			pstm.setInt(3, venda.getNumPrestacoes());
			
			pstm.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Venda> buscarTodos() {
		
		List<Venda> vendas = new ArrayList<>();
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "SELECT id, fk_Cliente_cpf, fk_PacoteViagem_id, num_prestacoes FROM venda";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rset = pstm.executeQuery();
			
			while (rset.next()) {
				Venda venda = rsetToVenda(rset);
				vendas.add(venda);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return vendas;
	}
	
	public static int excluir(int idVenda) {
		
		int numLinhasExcluidas = 0;
		try (Connection conn = ConnectionFactory.createConnectionToMySQL()) {
			String sql = "DELETE FROM venda WHERE id = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, idVenda);
			
			numLinhasExcluidas = pstm.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return numLinhasExcluidas;
	}
	
	private static Venda rsetToVenda(ResultSet rset) throws SQLException {
		
		Venda venda = new Venda();
		
		venda.setId(rset.getInt("id"));
		venda.setNumPrestacoes(rset.getInt("num_prestacoes"));
		
		Cliente cliente = ClienteCrud.buscarPorCpf(rset.getString("fk_Cliente_cpf"));
		PacoteViagem pacoteViagem = PacoteViagemCrud.buscarPorId(rset.getInt("fk_PacoteViagem_id"));
		
		venda.setCliente(cliente);
		venda.setPacoteViagem(pacoteViagem);
		
		return venda;
	}
}
