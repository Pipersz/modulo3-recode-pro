package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Cliente;

import java.util.ArrayList;

public class ClienteCrud {

	public static void inserir(Cliente cliente) {
		
		Connection conn = null;

		try {
			String sql = "INSERT INTO cliente (cpf, nome, email) VALUES (?, ?, ?)";
			conn = ConnectionFactory.createConnectionToMySQL();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, cliente.getCpf());
			pstm.setString(2,  cliente.getNome());
			pstm.setString(3, cliente.getEmail());
			pstm.execute();
		}
		catch (java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println("O CPF "+ cliente.getCpf() +" já foi cadastrado anteriormente\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
		
	}
	
	public static List<Cliente> buscarTodos() {
		
		Connection conn = null;
		List<Cliente> clientes = new ArrayList<>();
		
		try {
			String sql = "SELECT cpf, nome, email FROM cliente ORDER BY nome";
			conn = ConnectionFactory.createConnectionToMySQL();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rset = pstm.executeQuery();
			
			while (rset.next()) {
				Cliente cliente = rsetToCliente(rset);
				clientes.add(cliente);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
		
		return clientes;
	}

	public static Cliente buscarPorCpf(String cpf) {
		
		Connection conn = null;
		Cliente cliente = null;
		
		try {
			String sql = "SELECT cpf, nome, email FROM cliente WHERE cpf = ?";
			conn = ConnectionFactory.createConnectionToMySQL();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, cpf);
			ResultSet rset = pstm.executeQuery();
			
			if (rset.next()) {
				cliente = rsetToCliente(rset);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
		
		return cliente;
	}

	public static void atualizar(Cliente cliente) {

		Connection conn = null;

		try {
			String sql = "UPDATE cliente SET nome = ?, email = ? WHERE cpf = ?";
			conn = ConnectionFactory.createConnectionToMySQL();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,  cliente.getNome());
			pstm.setString(2, cliente.getEmail());
			pstm.setString(3, cliente.getCpf());
			pstm.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
	}

	public static int excluir(Cliente cliente) {

		Connection conn = null;
		
		int affectedRowsCount = 0;
		try {
			String sql = "DELETE FROM cliente WHERE cpf = ?";
			conn = ConnectionFactory.createConnectionToMySQL();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, cliente.getCpf());
			affectedRowsCount = pstm.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(conn);
		}
		
		return affectedRowsCount;
	}
		
	private static Cliente rsetToCliente(ResultSet rset) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setCpf(rset.getString("cpf"));
		cliente.setNome(rset.getString("nome"));
		cliente.setEmail(rset.getString("email"));
		return cliente;
	}
	
}
