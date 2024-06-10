package br.com.literalura.principal;

import br.com.literalura.model.DadosLivro;
import br.com.literalura.model.Livro;
import br.com.literalura.repository.AutorRepository;
import br.com.literalura.repository.LivroRepository;
import br.com.literalura.service.ConsumoAPI;
import br.com.literalura.service.LivroService;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private String ENDERECO = "https://gutendex.com/books?search=";
    private ConsumoAPI consumo = new ConsumoAPI();
    private LivroService servico;

    public Principal(LivroRepository repositorio, AutorRepository autorRepository) {
        servico = new LivroService(repositorio, autorRepository);
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                                        
                    Escolha o numero da sua opcao:\s""";

            System.out.print(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivro() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = scanner.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "%20"));
        DadosLivro dados = servico.obterDados(json);
        Livro livro = new Livro(dados);
        servico.salvarLivro(livro);

        System.out.println(livro);
    }

    private void listarLivros() {
        servico.listarLivros();
    }

    private void listarAutores() {
        servico.listarAutores();
    }

    private void listarAutoresVivosPorAno() {
        System.out.print("Digite o ano para busca: ");
        var ano = scanner.nextInt();

        servico.listarAutoresVivosPorAno(ano);
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Digite o idioma para busca: ");
        String idioma = scanner.nextLine();

        servico.listarLivrosPorIdioma(idioma);
    }
}