package gerenciador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import database.DestinoCrud;
import database.PacoteViagemCrud;
import model.Destino;
import model.PacoteViagem;

public class GerenciadorPacoteViagem {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static Scanner entrada = new Scanner(System.in);

	public static void exibirMenu() {
		while (true) {
			System.out.println();
			System.out.println("--------- Pacote de viagem --------");
			System.out.println("0 - voltar");
			System.out.println("1 - cadastrar");
			System.out.println("2 - listar");
			System.out.println("3 - atualizar");
			System.out.println("4 - excluir");

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
				atualizar();
			}
			
			else if (opcao == 4) {
				excluir();
			}
		}
	}
	
	public static void cadastrar() {
		
		System.out.println("\nCadastro de pacote de viagem");
		System.out.println("Destinos:");
		
		List<Destino> destinos = DestinoCrud.buscarTodos();
		
		for (Destino destino : destinos) {
			System.out.println(destino.getId() + ") " + destino);
		}
		
		Destino destinoSelecionado = null;
		
		while (true) {
			
			System.out.print("Selecione o ID do destino (0 para sair): ");
			int idDestino = entrada.nextInt();
			entrada.nextLine();
			
			if (idDestino == 0) {
				return;
			}
			
			destinoSelecionado = filtrarDestino(idDestino, destinos);
			
			if (destinoSelecionado != null) {
				break;
			}
			
			System.out.println("Destino de ID "+ idDestino + " não encontrado");
		}
		
		System.out.print("Data de ida (dd/mm/aaaa): ");
		LocalDate dataIda = LocalDate.parse(entrada.nextLine(), formatter);
		
		System.out.print("Data de volta (dd/mm/aaaa): ");
		LocalDate dataVolta = LocalDate.parse(entrada.nextLine(), formatter);
		
		System.out.print("Preço: ");
		float preco = entrada.nextFloat();
		entrada.nextLine();
		
		System.out.print("Número máximo de prestações: ");
		int numMaxPrestacoes = entrada.nextInt();
		entrada.nextLine();
		
		PacoteViagem pacote = new PacoteViagem();
		pacote.setDestino(destinoSelecionado);
		pacote.setDataIda(dataIda);
		pacote.setDataVolta(dataVolta);
		pacote.setPreco(preco);
		pacote.setNumMaxPrestacoes(numMaxPrestacoes);
		
		PacoteViagemCrud.inserir(pacote);
		
		System.out.println("\nPacote de viagem cadastrado");
	}
	
	private static Destino filtrarDestino(int idDestino, List<Destino> destinos) {
		
		for (Destino destino : destinos) {
			if (idDestino == destino.getId()) {
				return destino;
			}
		}
		
		return null;
	}

	public static void listar() {
		
		System.out.println("\nLista de pacotes de viagens");
		for (PacoteViagem pacote : PacoteViagemCrud.buscarTodos()) {
			System.out.println(pacote);
		}
		System.out.println();
	}
	
	
	public static void atualizar() {
		
		System.out.println("\nAtualização de pacote de viagens");
		System.out.print("Digite o ID do pacote de viagem (0 para sair): ");
		int idPacoteViagem = entrada.nextInt();
		
		if (idPacoteViagem == 0) {
			return;
		}
		
		PacoteViagem pacote = PacoteViagemCrud.buscarPorId(idPacoteViagem);
		
		if (pacote == null) {
			System.out.println("Pacote com id "+ idPacoteViagem +" não encontrado");
			return;
		}
		
		List<Destino> destinos = DestinoCrud.buscarTodos();
		System.out.println("Destinos:");
		for (Destino destino : destinos) {
			System.out.println(destino.getId() + ") " + destino);
		}
		
		Destino destinoSelecionado = null;
		while (true) {
			System.out.println("ID do destino (0 para sair): ");
			int idDestino = entrada.nextInt();
			entrada.nextLine();
			
			if (idDestino == 0) {
				return;
			}
			
			destinoSelecionado = filtrarDestino(idDestino, destinos);
			
			if (destinoSelecionado != null) {
				break;
			}
		}
		
		System.out.print("Data de ida (dd/mm/aaaa): ");
		LocalDate dataIda = LocalDate.parse(entrada.nextLine(), formatter);
		
		System.out.print("Data de volta (dd/mm/aaaa): ");
		LocalDate dataVolta = LocalDate.parse(entrada.nextLine(), formatter);
		
		System.out.print("Preço: ");
		float preco = entrada.nextFloat();
		entrada.nextLine();
		
		System.out.print("Número máximo de prestações: ");
		int numMaxPrestacoes = entrada.nextInt();
		entrada.nextLine();
		
		pacote.setDataIda(dataIda);
		pacote.setDataVolta(dataVolta);
		pacote.setPreco(preco);
		pacote.setNumMaxPrestacoes(numMaxPrestacoes);
		pacote.setDestino(destinoSelecionado);
		
		PacoteViagemCrud.atualizar(pacote);
		
		System.out.println("\nPacote de viagem atualizado");
	}
	
	public static void excluir() {
		
		System.out.println("\nExclusão de pacote de viagem");
		System.out.print("ID: ");
		int idPacoteViagem = entrada.nextInt();
		entrada.nextLine();
		
		int numLinhasExcluidas = PacoteViagemCrud.excluir(idPacoteViagem);
		
		String message = "\nO pacote de viagem com ID " + idPacoteViagem;
		message += numLinhasExcluidas == 0 ? " não foi encontrado" : " foi excluído";
		
		System.out.println(message);
	}

}
