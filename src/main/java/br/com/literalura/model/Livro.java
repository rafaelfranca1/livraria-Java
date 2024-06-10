package br.com.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Double numeroDownloads;

    public Livro() {}

    public Livro(DadosLivro dados) {
        this.titulo = dados.titulo();
        Autor autor1 = new Autor(dados.autor().getFirst());
        this.autor = autor1;
        autor1.addLivro(this);
        this.idioma = dados.idioma().getFirst();
        this.numeroDownloads = dados.numeroDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "----- LIVRO -----" +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor.getNome() +
                "\nIdioma: " + idioma +
                "\nNumero de downloads: " + numeroDownloads +
                "\n-----------------";
    }
}
