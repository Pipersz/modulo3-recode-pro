package gerenciador;

import java.util.List;
import java.util.Scanner;

import database.ClienteCrud;
import model.Cliente;

public class GerenciadorCliente {

	public static Scanner entrada = new Scanner(System.in);
	
	public static void exibirMenu() {
		
		while (true) {
			System.out.println();
			System.out.println("--------- Cliente --------");
			System.out.println("0 - voltar");
			System.out.println("1 - cadastrar");
			System.out.println("2 - listar");
			System.out.println("3 - buscar");
			System.out.println("4 - atualizar");
			System.out.println("5 - excluir");

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
				buscar();
			}
			
			else if (opcao == 4) {
				atualizar();
			}
			
			else if (opcao == 5) {
				excluir();
			}
		}
	}
	
	public static void cadastrar() {
		System.out.println("\nCadastro de cliente");
		System.out.print("CPF: ");
		String cpf = entrada.nextLine();
		System.out.print("Nome: ");
		String nome = entrada.nextLine();
		System.out.print("E-mail: ");
		String email = entrada.nextLine();
		Cliente cliente = new Cliente();
		cliente.setCpf(cpf);
		cliente.setNome(nome);
		cliente.setEmail(email);
		ClienteCrud.inserir(cliente);
		
		System.out.println("\nCliente cadastrado");
	}
	
	public static void listar() {
		List<Cliente> clientes = ClienteCrud.buscarTodos();
		
		System.out.println("\nLista de clientes:");
		for (Cliente c : clientes) {
			System.out.println("Cliente: "+ c);
		}
		
		System.out.println();
	}
	
	public static void buscar() {
		System.out.println("\nBusca de cliente por CPF");
		System.out.print("CPF: ");
		String cpf = entrada.nextLine();
		
		Cliente cliente = ClienteCrud.buscarPorCpf(cpf);
		
		if (cliente == null) {
			System.out.println("O Cliente de CPF "+ cpf +" não foi encontrado\n");
			return;
		}
		
		System.out.println(cliente);
	}
	
	public static void atualizar() {
		
		System.out.println("\nAtualização de cliente");
		System.out.print("CPF: ");
		String cpf = entrada.nextLine();
		
		Cliente cliente = ClienteCrud.buscarPorCpf(cpf);
		
		if (cliente == null) {
			System.out.println("Cliente de CPF "+ cpf +" nao encontrado\n");
			return;
		}
		
		System.out.print("Nome: ");
		String nome = entrada.nextLine();
		System.out.print("E-mail: ");
		String email = entrada.nextLine();
		
		cliente.setNome(nome);
		cliente.setEmail(email);
		
		ClienteCrud.atualizar(cliente);
		
		System.out.println("\nCliente atualizado");
	}
	
	public static void excluir() {
		
		System.out.println("\nExclusão de cliente");
		System.out.print("CPF: ");
		
		String cpf = entrada.nextLine();
		
		Cliente cliente = ClienteCrud.buscarPorCpf(cpf);
		
		if (cliente == null) {
			System.out.println("\nO cliente de CPF "+ cpf +" não foi encontrado");
			return;
		}
		
		ClienteCrud.excluir(cliente);
		System.out.println("\nO cliente de CPF "+ cpf +" foi excluído");
	}
}
