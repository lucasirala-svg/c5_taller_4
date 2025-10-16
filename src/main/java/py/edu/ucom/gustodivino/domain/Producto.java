package py.edu.ucom.gustodivino.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "productos")
public class Producto extends BaseEntity {

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    public String nombre;

    public String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @PositiveOrZero(message = "El precio debe ser cero o mayor")
    public Double precio;

    @NotBlank(message = "La categoría no puede estar en blanco")
    public String categoria;

    public boolean disponible;
}