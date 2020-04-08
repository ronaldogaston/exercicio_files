package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Cliente;
import model.entities.ItensDoPedido;
import model.entities.Pedido;
import model.entities.Produto;
import model.entities.enums.StatusDoPedido;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Bem vindo ao restaurante Gaston Sushi !");
		System.out.println();
		System.out.println("Para efetuarmos o seu cadastro informe: ");
		System.out.print("Nome e Sobrenome: ");
		String cliente = sc.nextLine();
		System.out.println("Bem vindo(a) " + cliente + "!");
		System.out.println();
		//------------------------------------------GERANDO UM PEDIDO----------------------------------------//
		try {
			System.out.print("Quantos produtos deseja pedir: ");
			int n = sc.nextInt();
			String status = "AGUARDANDO_PAGAMENTO";
			System.out.println();
			
			Pedido pedido = new Pedido(new Date(), 
					new Cliente (cliente),
					StatusDoPedido.valueOf(status));
			//------------------------------------------INFORMANDO DADOS DOS ITENS DO PEDIDO----------------------------------------//		
			for (int i = 0; i < n; i++) {
				int cont = 1 + i;
				System.out.println("Qual seu " + cont + "� produto: ");
				System.out.print("Descri��o: ");
				sc.nextLine();
				String nomeP = sc.nextLine();
				System.out.print("Valor R$ ");
				double preco = sc.nextDouble();
				System.out.print("Quantidade: ");
				int quantidade = sc.nextInt();
				System.out.println();
				
				Produto produto = new Produto(nomeP, preco);
				
				ItensDoPedido itens = new ItensDoPedido(quantidade, preco, produto);
				pedido.addItem(itens);
			}
			
			System.out.println();
			System.out.println(pedido.toString());
			//------------------------------------------GERANDO UM ARQUIVO TXT----------------------------------------//
			System.out.println();
			System.out.println("Ser� gerado um arquivo 'out.txt' no caminho: 'C:\\Temp'.");
			
			String caminhoDoArquivo = "C:\\temp\\out.txt"; // CAMINHO COMPLETO AT� O ARQUIVO. NECESS�RIO INFORMAR ARQUIVO E EXTENS�O PARA PODER USAR O MESMO
	
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoDoArquivo))) { 
					bw.write(pedido.File()); // Escrevea no arquivo OU ESCREVE A INFORMA��O CONTIDA NA VARI�VEL, depende da informa��o contida no 'ARGUMENTO'
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			File localDoArquivo = new File(caminhoDoArquivo); 
			String pastaDeOrigem = localDoArquivo.getParent(); // getParent = PARA PEGAR O CAMINHO SEM O ARQUIVO OU SUA EXTENS�O
			boolean sucesso = new File(pastaDeOrigem + "\\Out").mkdir(); // CRIANDO UMA NOVA PASTA
			System.out.println("Diret�rio criado com sucesso: " + sucesso); // MENSAGEM SE A PASTA FOI CRIADA COM SUCESSO OU N�O. TRUE = SIM // FALSE = N�O

			//------------------------------------------GERANDO UM ARQUIVO CSV----------------------------------------//
			
			String arquivoDeDestino = pastaDeOrigem + "\\Out\\Sumario.csv";
			//------------------------------------------FAZENDO A LEITURA DE UM ARQUIVO PARA ORDERNAR O MESMO EM UMA 'LISTA'----------------------------------------//
			try (BufferedReader br = new BufferedReader(new FileReader(caminhoDoArquivo))) {

				List<ItensDoPedido> list = new ArrayList<>();
				
				String arquivoCSV = br.readLine(); // LEITURA DA PR�XIMA LINHA
				while (arquivoCSV != null) { // Enquanto o 'arquivoCSV' for diferente de 'nulo' fa�a !

					String[] campo = arquivoCSV.split(","); // COMANDO 'SPLIT' ESTOU SEPARANDO AS INFORMA��ES POR V�RGULAS
					String nome = campo[0]; // TUDO DIGITADO AT� A 1� V�RGULA � NOME
					double preco = Double.parseDouble(campo[1]); // TUDO DIGITADO AT� A 2� V�RGULA � PRE�O 'parseDouble' PARA CONVERTER STRING EM DOUBLE
					int quantidade = Integer.parseInt(campo[2]); // TUDO DIGITADO AT� A 3� V�RGULA OU FINAL DO ARQUIVO � PRE�O 'parseInt' PARA CONVERTER STRING EM INT

					list.add(new ItensDoPedido(quantidade, preco, new Produto (nome, preco))); // AP�S COMANDOS ACIMA, ADICIONA H� UMA LISTA

					arquivoCSV = br.readLine(); // LEITURA DA PR�XIMA LINHA
				}
				//------------------------------------------INFORMA��ES QUE SER�O ESCRITAS NO ARQUIVO----------------------------------------//
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDeDestino))) {

					for (ItensDoPedido x : list) {
						bw.write(x.getProduto().getNomeP() + ", R$ " 
								+ String.format("%.2f", x.getProduto().getPreco())
								+ ", Quantidade solicitada: "
								+ x.getQuantidade()
								+ ", Total do Produto: R$ " 
								+ String.format("%.2f", x.Total()));
						bw.newLine();
					}

					System.out.println(arquivoDeDestino + " Criado!");
					//------------------------------------------POSS�VEIS 'EXCE��ES (ERROS) DO PROGRAMA'----------------------------------------//
				} catch (IOException e) { // ERRO AO DIGITAR NO ARQUIVO
					System.out.println("Error writing file: " + e.getMessage());
				}

			} catch (IOException e) { // ERRO AO LER ALGO NO ARQUIVO
				System.out.println("Error reading file: " + e.getMessage());
			}

			
		}
		catch (InputMismatchException e) { // FOI DIGITADO OUTRO CARACTER DIFERENTE DE UM N�MERO
			System.out.println("O valor informado � inv�lido!");
		}
		finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

}
