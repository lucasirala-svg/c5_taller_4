package py.edu.ucom.gustodivino.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesas")
public class Mesa extends BaseEntity {
    public int numeroMesa;
    public int capacidad;
    public String estado;
}
