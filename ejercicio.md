Ejercicio Práctico: Validador de 
Descuentos E-Commerce con Spring Boot 
Contexto 
Eres parte del equipo de desarrollo de una tienda online construida con Spring Boot 3.x. Tu 
misión es validar el componente de negocio DiscountService. Este servicio decide cuánto 
descuento aplicar a un carrito de compras basado en el total de la compra, la cantidad de 
productos y el tipo de cliente. 
Para obtener el tipo de cliente, el servicio debe realizar una consulta externa (simulada por un 
servicio de clientes CustomerService). 
Reglas de Negocio 
1. Descuento por Volumen: Si el cliente compra más de 10 artículos, recibe un 15% de 
descuento sobre el total. 
2. Descuento VIP: Si el cliente es catalogado como VIP por el sistema y el total de la 
compra es mayor a $500, recibe un 10% de descuento (no acumulable con el anterior, 
se aplica el mayor). 
3. Restricciones: 
○ El total no puede ser negativo. 
○ La cantidad de productos debe ser mayor a cero. 
○ Si no cumple ninguna condición o el cliente no es VIP, el descuento es 0%. 
Tarea 
Implementar las pruebas unitarias utilizando JUnit 5 y Mockito para cubrir: 
● Casos de éxito (Happy Path) simulando el comportamiento del servicio de clientes 
utilizando @Mock. 
● Casos de borde (Exactamente 10 productos, exactamente $500). 
● Control de excepciones ante entradas inválidas utilizando assertThrows. 
Código de Producción (Spring Boot) 
1. Servicio de Clientes (Dependencia a simular) 
package com.tienda.service; 
 
public interface CustomerService { 
    boolean isVipCustomer(Long customerId); 
} 
 
2. Servicio de Descuentos 
package com.tienda.service; 
 
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