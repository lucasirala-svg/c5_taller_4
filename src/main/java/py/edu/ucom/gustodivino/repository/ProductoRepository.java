package py.edu.ucom.gustodivino.repository;

import py.edu.ucom.gustodivino.domain.Producto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<Producto> {}
