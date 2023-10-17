package gerenciador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;

import database.ClienteCrud;
import database.ConnectionFactory;
import database.PacoteViagemCrud;
import database.VendaCrud;
import model.Cliente;
import model.PacoteViagem;
import model.Venda;

public class GerenciadorVenda {
	
	private static Scanner entrada = new Scanner(System.in);

	public static void exibirMenu() {
		
		while (true) {
			System.out.println();
			System.out.println("------- Venda --------");
			System.out.println("0 - Sair");
			System.out.println("1 - Cadastrar");
			System.out.println("2 - Listar");
			System.out.println("3 - Excluir");
			
			int opcao = entrada.nextInt();
			entrada.nextLine();
			
			if (opcao == 0) {
				break;
			}
			
			else if (opcao == 1) {
				cadastrar();
			}
			
			else if (opcao == 2) {
				listar();
			}
			
			else if (opcao == 3) {
				excluir();
			}
		}
	}
	
	public static void cadastrar() {
		
		List<Cliente> clientes = ClienteCrud.buscarTodos();
		List<PacoteViagem> pacotesViagens = PacoteViagemCrud.buscarTodos();
		System.out.println("\nCadastro de venda:");
		
		System.out.println("\nClientes:");
		
		
		for (Cliente cliente : clientes) {
			System.out.println(cliente.getCpf() +") "+ cliente);
		}
		
		System.out.print("\nDigite o CPF do cliente: ");
		String cpf = entrada.nextLine();
		
		Cliente clienteSelecionado = filtrarClientePorCpf(cpf, clientes);
		
		if (clienteSelecionado == null) {
			System.out.println("\nO cliente com CPF "+ cpf + " não foi encontrado");
			return;
		}
		
		System.out.println("\nPacotes de viagens:");
		
		for (PacoteViagem pacote: pacotesViagens) {
			System.out.println(pacote.getId() + ") " + pacote);
		}
		
		System.out.print("\nDigite o ID do pacote de viagem: ");
		int idPacote = entrada.nextInt();
		entrada.nextLine();
		
		PacoteViagem pacoteSelecionado = filtrarPacoteViagemPorId(idPacote, pacotesViagens);
		
		if (pacoteSelecionado == null) {
			System.out.println("\nO pacote de viagem com ID "+ idPacote + " não foi encontrado");
			return;
		}
		
		System.out.print("Digite a quantidade de prestações: ");
		int numPrestacoes = entrada.nextInt();
		entrada.nextLine();
		
		Venda venda = new Venda();
		venda.setCliente(clienteSelecionado);
		venda.setPacoteViagem(pacoteSelecionado);
		venda.setNumPrestacoes(numPrestacoes);
		
		VendaCrud.inserir(venda);
		
		System.out.println("\nPacote de viagem cadastrado");
	}
	
	public static void listar() {
		
		List<Venda> vendas = VendaCrud.buscarTodos();
		
		System.out.println("\nLista de vendas:");
		for (Venda venda : vendas) {
			System.out.println(venda);
		}
		System.out.println();
	}
	
	public static void excluir() {
		
		System.out.println("\nExcluir Venda:");
		System.out.print("Digite o ID da venda (0 para sair): ");
		
		int idVenda = entrada.nextInt();
		entrada.nextLine();
		
		if (idVenda == 0) {
			return;
		}
		
		int numLinhasExcluidas = VendaCrud.excluir(idVenda);
		String mensagem = "A venda com ID "+ idVenda;
		mensagem += (numLinhasExcluidas == 0) ? " não foi encontrada\n" : " foi excluída\n";
		
		System.out.println(mensagem + "\n");
	}

	private static PacoteViagem filtrarPacoteViagemPorId(int idPacote, List<PacoteViagem> pacotesViagens) {

		for (PacoteViagem pacote : pacotesViagens) {
			if (idPacote == pacote.getId()) {
				return pacote;
			}
		}
		return null;
	}

	private static Cliente filtrarClientePorCpf(String cpf, List<Cliente> clientes) {

		for (Cliente cliente : clientes) {
			if (cpf.equals(cliente.getCpf())) {
				return cliente;
			}
		}
		return null;
	}
	
}
