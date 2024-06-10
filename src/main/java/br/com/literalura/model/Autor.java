package br.com.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor autor) {
        this.nome = autor.nome();
        this.anoDeNascimento = autor.anoDeNascimento();
        this.anoDeFalecimento = autor.anoDeFalecimento();
    }

    public void addLivro(Livro livro) {
        livros.add(livro);
        livro.setAutor(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Integer anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        List<String> titulosLivros = livros.stream()
                .map(Livro::getTitulo)
                .collect(Collectors.toList());

        return "Autor: " + nome +
                "\nAno de nascimento: " + anoDeNascimento +
                "\nAno de falecimento: " + anoDeFalecimento +
                "\nLivros: " + titulosLivros +
                "\n-----------------";
    }
}
