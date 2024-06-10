package br.com.literalura.service;

import br.com.literalura.model.Autor;
import br.com.literalura.model.DadosLivro;
import br.com.literalura.model.Livro;
import br.com.literalura.repository.AutorRepository;
import br.com.literalura.repository.LivroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LivroService {
    private LivroRepository repositorio;
    private AutorRepository autorRepository;

    public LivroService(LivroRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }

    public DadosLivro obterDados(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode resultsNode = rootNode.get("results").get(0);

            DadosLivro livro = null;

            livro = mapper.treeToValue(resultsNode, DadosLivro.class);
            return livro;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void salvarLivro(Livro livro) {
        Optional<Autor> autorExistente = autorRepository.findByNome(livro.getAutor().getNome());

        if (autorExistente.isPresent()) {
            livro.setAutor(autorExistente.get());
        } else {
            Autor novoAutor = autorRepository.save(livro.getAutor());
            livro.setAutor(novoAutor);
        }

        repositorio.save(livro);
    }

    public void listarLivros() {
        List<Livro> livros = repositorio.findAll();
        livros.forEach(System.out::println);
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    public void listarAutoresVivosPorAno(int ano) {

        List<Autor> autores = repositorio.findAutoresVivosPorAno(ano)
                .stream()
                .distinct()
                .collect(Collectors.toList());

        if (!autores.isEmpty()) {
            System.out.println("Autores vivos no ano de " + ano);
            autores.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor vivo nesse ano");
        }
    }

    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = repositorio.findLivrosPorIdioma(idioma);

        if (!livros.isEmpty()) {
            System.out.println("Livros em " + idioma);
            livros.forEach(System.out::println);
        } else {
            System.out.println("Nenhum livro em " + idioma);
        }
    }
}
