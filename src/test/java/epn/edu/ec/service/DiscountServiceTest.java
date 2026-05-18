package epn.edu.ec.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        
    }

    @Test
    void calculateDiscount_shouldApplyVolumeDiscount_whenQuantityIsGreaterThanTen() {
        double discount = discountService.calculateDiscount(100.0, 11, 1L);

        assertEquals(15.0, discount, 0.0001);
        verify(customerService, never()).isVipCustomer(1L);
    }

    
}