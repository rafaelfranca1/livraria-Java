package br.com.literalura.repository;

import br.com.literalura.model.Autor;
import br.com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT a FROM Livro l JOIN l.autor a WHERE a.anoDeNascimento <= :ano AND a.anoDeFalecimento >= :ano")
    List<Autor> findAutoresVivosPorAno(int ano);

    @Query("SELECT l FROM Livro l WHERE l.idioma = :idioma")
    List<Livro> findLivrosPorIdioma(String idioma);
}
