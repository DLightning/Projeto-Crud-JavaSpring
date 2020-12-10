package edu.ieso.projeto.Repository;


import edu.ieso.projeto.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

}
