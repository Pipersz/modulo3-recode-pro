package gerenciador;

import java.util.List;
import java.util.Scanner;

import database.ContatoCrud;
import model.Contato;

public class GerenciadorContato {
	
	private static Scanner entrada = new Scanner(System.in); 

	public static void exibirMenu() {
		
		while (true) {
			System.out.println();
			System.out.println("---------- Contato ----------");
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
		
		System.out.println("\nCadastro de contato:");
		System.out.print("Digite o nome: ");
		String nome = entrada.nextLine();
		System.out.print("Digite o telefone: ");
		String telefone = entrada.nextLine();
		System.out.print("Digite o e-mail: ");
		String email = entrada.nextLine();
		System.out.print("Digite a opção de contato (1 = whatsapp; 2 = email; 3 = telefone): ");
		int opcaoContato = entrada.nextInt();
		entrada.nextLine();
		
		if (! (opcaoContato >= 1 && opcaoContato <= 3)) {
			System.out.println("Opção de contato inválida\n");
			return;
		}
		
		Contato contato = new Contato();
		
		contato.setNome(nome);
		contato.setEmail(email);
		contato.setTelefone(telefone);
		contato.setOpcaoContato(opcaoContato);
		
		ContatoCrud.inserir(contato);
		
		System.out.println("\nContato cadastrado");
	}
	
	public static void listar() {
		
		System.out.println("\nLista de contatos:");
		List<Contato> contatos = ContatoCrud.buscarTodos();
		for (Contato contato : contatos) {
			System.out.println(contato);
		}
		System.out.println();
	}
	
	public static void excluir() {
		
		System.out.println("\nExclusão de contato:");
		System.out.print("Digite o ID do contato: ");
		int idContato = entrada.nextInt();
		entrada.nextLine();
		
		int numLinhasExcluidas = ContatoCrud.excluir(idContato);
		String mensagem = "\nO contato com ID "+ idContato;
		mensagem += numLinhasExcluidas == 0 ? " não foi encontrado" : " foi excluído";
		
		System.out.println(mensagem);
	}
}
