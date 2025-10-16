package py.edu.ucom.gustodivino.repository;

import py.edu.ucom.gustodivino.domain.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {}
