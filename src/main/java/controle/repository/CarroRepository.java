package controle.repository;

import controle.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca como repositório Spring
public interface CarroRepository extends JpaRepository<Carro, Integer> {
    // Herdamos métodos prontos: findAll, findById, save, deleteById
}
