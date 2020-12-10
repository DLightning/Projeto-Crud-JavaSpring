package edu.ieso.projeto.Repository;

import edu.ieso.projeto.Model.Cliente;
import edu.ieso.projeto.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}
