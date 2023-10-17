package gerenciador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import database.DestinoCrud;
import model.Destino;
import model.Imagem;

public class GerenciadorDestino {

	public static Scanner entrada = new Scanner(System.in);
	
	public static void exibirMenu() {
		
		while (true) {
			System.out.println();
			System.out.println("--------- Destino --------");
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
		System.out.println("\nCadastro de destino");
		Destino destino = new Destino();
		destino = preencherCampos(destino);
		
		DestinoCrud.inserir(destino);
		
		System.out.println("\nDestino cadastrado");
	}
	
	public static void listar() {
		List<Destino> destinos = DestinoCrud.buscarTodos();
		
		System.out.println("\nLista de destinos:");
		for (Destino destino : destinos) {
			System.out.println(destino);
		}
		
		System.out.println();
	}
	
	public static void buscar() {

		System.out.println("\nBusca de destino");
		System.out.print("Nome do destino: ");
		
		String nome = entrada.nextLine();
		
		List<Destino> destinos = DestinoCrud.buscarPorNome(nome);
		
		System.out.println("\nDestinos de nome "+ nome + ":");
		for (Destino destino : destinos) {
			System.out.println(destino);
		}
		
		System.out.println();
	}
	
	public static void atualizar() {
		
		System.out.println("\nAtualização de destino");
		System.out.println("Digite o ID do destino (0 para sair): ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		if (id == 0) {
			return;
		}
		
		Destino destino = DestinoCrud.buscarPorId(id);
		
		if (destino == null) {
			System.out.println("O destino com ID "+ id +" não foi encontrado");
			return;
		}
		
		destino = preencherCampos(destino);
		
		DestinoCrud.atualizar(destino);
		
		System.out.println("\nDestino atualizado");
	}
	
	public static void excluir() {
		
		System.out.println("\nExclusão de destino");
		System.out.print("Digite o ID do destino (0 para sair): ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		if (id == 0) {
			return;
		}
		
		int deletedRowsCount = DestinoCrud.excluir(id);
		
		String message = "\nO Destino com ID "+ id;
		message += deletedRowsCount == 0 ? " não foi encontrado" : " foi excluído";
		
		System.out.println(message);
	}
	
	private static Destino preencherCampos(Destino destino) {
		
		System.out.print("Nome: ");
		String nome = entrada.nextLine();
		System.out.print("Descrição: ");
		String descricao = entrada.nextLine();
		System.out.println();
		destino.setNome(nome);
		destino.setDescricao(descricao);
		destino.setImagens(new ArrayList<>());
		
		while (true) {
			
			System.out.print("Deseja adicionar imagem (s/n)? ");
			String opcao = entrada.nextLine();
			
			if (opcao.equals("n")) {
				break;
			}
			
			if (!opcao.equals("s")) {
				continue;
			}
			
			System.out.print("URL da imagem: ");
			String url = entrada.nextLine();
			
			Imagem imagem = new Imagem();
			imagem.setUrl(url);
			
			destino.getImagens().add(imagem);
		}
		
		return destino;
	}
}
