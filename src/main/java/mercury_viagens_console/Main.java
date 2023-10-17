/**
 * 
 */
package mercury_viagens_console;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import database.ClienteCrud;
import database.PacoteViagemCrud;
import gerenciador.GerenciadorCliente;
import gerenciador.GerenciadorContato;
import gerenciador.GerenciadorDestino;
import gerenciador.GerenciadorPacoteViagem;
import gerenciador.GerenciadorVenda;
import model.Cliente;
import model.PacoteViagem;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 */
public class Main {
	
	static Scanner entrada = new Scanner(System.in);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		exibirMenu();
	}
	
	public static void exibirMenu() {
		
		int opcao = -1;
		
		while (true) {
			System.out.println("====== Mercury Viagens ======");
			System.out.println("0 - Sair");
			System.out.println("1 - Cliente");
			System.out.println("2 - Destino");
			System.out.println("3 - Pacote");
			System.out.println("4 - Venda");
			System.out.println("5 - Contato");
			
			opcao = entrada.nextInt();
			 // para capturar o Enter que vem logo após digitar o número da opção, caso contrário, esse Enter
			// influenciaria a próxima leitura de valor do teclado.
			entrada.nextLine();
			
			if (opcao == 0) {
				break;
			}
			
			else if (opcao == 1) {
				GerenciadorCliente.exibirMenu();
			}
			
			else if (opcao == 2) {
				GerenciadorDestino.exibirMenu();
			}
			
			else if (opcao == 3) {
				GerenciadorPacoteViagem.exibirMenu();
			}
			
			else if (opcao == 4) {
				GerenciadorVenda.exibirMenu();
			}
			
			else if (opcao == 5) {
				GerenciadorContato.exibirMenu();
			}
			
		}
		
		entrada.close();
	}
}
