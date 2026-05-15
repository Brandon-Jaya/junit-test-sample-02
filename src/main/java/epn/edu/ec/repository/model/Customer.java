package epn.edu.ec.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    private long id;
    private String name;
    private String phone;
    
}
