package py.edu.ucom.gustodivino.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity extends PanacheEntity {

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    public LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_edicion")
    public LocalDateTime fechaEdicion;
}
