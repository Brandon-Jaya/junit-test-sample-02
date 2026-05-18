package epn.edu.ec.service;

import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    private final CustomerService customerService; 
 
    // Inyección por constructor (Buena práctica recomendada por Sonar) 
    public DiscountService(CustomerService customerService) { 
        this.customerService = customerService; 
    } 
 
    public double calculateDiscount(double total, int quantity, Long customerId) { 
        if (total < 0 || quantity <= 0 || customerId == null) { 
            throw new IllegalArgumentException("Valores de entrada inválidos"); 
        } 
 
        double discount = 0; 
 
        // Regla 1: Descuento por Volumen (Mayor a 10 productos) 
        if (quantity > 10) { 
            discount = 0.15; 
        }  
        // Regla 2: Descuento VIP (Cliente VIP y compra mayor a $500) 
        else if (total > 500 && customerService.isVipCustomer(customerId)) { 
            discount = 0.10; 
        } 
 
        return total * discount; 
    } 
}
