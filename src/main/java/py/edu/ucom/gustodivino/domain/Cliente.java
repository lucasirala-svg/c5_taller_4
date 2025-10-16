package py.edu.ucom.gustodivino.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente extends BaseEntity {
    public String nombre;
    public String apellido;
    public String telefono;
    public String direccion;
}
