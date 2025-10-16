package py.edu.ucom.gustodivino.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "detalles_pedidos")
public class DetallePedido extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto")
    public Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    public Pedido pedido;

    public int cantidad;
    public Double precioUnitario;
}
