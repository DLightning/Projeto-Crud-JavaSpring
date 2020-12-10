package edu.ieso.projeto.Repository;

import edu.ieso.projeto.Model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente,Long>{

}
