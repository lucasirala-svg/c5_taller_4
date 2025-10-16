package py.edu.ucom.gustodivino.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    public Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mesa")
    public Mesa mesa;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<DetallePedido> detalles;

    public String tipoPedido;
    public String estado;
    public Double total;

    @CreationTimestamp
    public LocalDateTime createdAt;
}
