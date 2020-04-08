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
				System.out.println("Qual seu " + cont + "º produto: ");
				System.out.print("Descrição: ");
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
			System.out.println("Será gerado um arquivo 'out.txt' no caminho: 'C:\\Temp'.");
			
			String caminhoDoArquivo = "C:\\temp\\out.txt"; // CAMINHO COMPLETO ATÉ O ARQUIVO. NECESSÁRIO INFORMAR ARQUIVO E EXTENSÃO PARA PODER USAR O MESMO
	
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoDoArquivo))) { 
					bw.write(pedido.File()); // Escrevea no arquivo OU ESCREVE A INFORMAÇÃO CONTIDA NA VARIÁVEL, depende da informação contida no 'ARGUMENTO'
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			File localDoArquivo = new File(caminhoDoArquivo); 
			String pastaDeOrigem = localDoArquivo.getParent(); // getParent = PARA PEGAR O CAMINHO SEM O ARQUIVO OU SUA EXTENSÃO
			boolean sucesso = new File(pastaDeOrigem + "\\Out").mkdir(); // CRIANDO UMA NOVA PASTA
			System.out.println("Diretório criado com sucesso: " + sucesso); // MENSAGEM SE A PASTA FOI CRIADA COM SUCESSO OU NÃO. TRUE = SIM // FALSE = NÃO

			//------------------------------------------GERANDO UM ARQUIVO CSV----------------------------------------//
			
			String arquivoDeDestino = pastaDeOrigem + "\\Out\\Sumario.csv";
			//------------------------------------------FAZENDO A LEITURA DE UM ARQUIVO PARA ORDERNAR O MESMO EM UMA 'LISTA'----------------------------------------//
			try (BufferedReader br = new BufferedReader(new FileReader(caminhoDoArquivo))) {

				List<ItensDoPedido> list = new ArrayList<>();
				
				String arquivoCSV = br.readLine(); // LEITURA DA PRÓXIMA LINHA
				while (arquivoCSV != null) { // Enquanto o 'arquivoCSV' for diferente de 'nulo' faça !

					String[] campo = arquivoCSV.split(","); // COMANDO 'SPLIT' ESTOU SEPARANDO AS INFORMAÇÕES POR VÍRGULAS
					String nome = campo[0]; // TUDO DIGITADO ATÉ A 1ª VÍRGULA É NOME
					double preco = Double.parseDouble(campo[1]); // TUDO DIGITADO ATÉ A 2ª VÍRGULA É PREÇO 'parseDouble' PARA CONVERTER STRING EM DOUBLE
					int quantidade = Integer.parseInt(campo[2]); // TUDO DIGITADO ATÉ A 3ª VÍRGULA OU FINAL DO ARQUIVO É PREÇO 'parseInt' PARA CONVERTER STRING EM INT

					list.add(new ItensDoPedido(quantidade, preco, new Produto (nome, preco))); // APÓS COMANDOS ACIMA, ADICIONA HÁ UMA LISTA

					arquivoCSV = br.readLine(); // LEITURA DA PRÓXIMA LINHA
				}
				//------------------------------------------INFORMAÇÕES QUE SERÃO ESCRITAS NO ARQUIVO----------------------------------------//
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
					//------------------------------------------POSSÍVEIS 'EXCEÇÕES (ERROS) DO PROGRAMA'----------------------------------------//
				} catch (IOException e) { // ERRO AO DIGITAR NO ARQUIVO
					System.out.println("Error writing file: " + e.getMessage());
				}

			} catch (IOException e) { // ERRO AO LER ALGO NO ARQUIVO
				System.out.println("Error reading file: " + e.getMessage());
			}

			
		}
		catch (InputMismatchException e) { // FOI DIGITADO OUTRO CARACTER DIFERENTE DE UM NÚMERO
			System.out.println("O valor informado é inválido!");
		}
		finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

}
