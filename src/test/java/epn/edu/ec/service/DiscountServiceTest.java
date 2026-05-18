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

    @Test
    void calculateDiscount_shouldApplyVipDiscount_whenTotalIsGreaterThanFiveHundredAndCustomerIsVip() {
        when(customerService.isVipCustomer(2L)).thenReturn(true);

        double discount = discountService.calculateDiscount(600.0, 10, 2L);

        assertEquals(60.0, discount, 0.0001);
        verify(customerService).isVipCustomer(2L);
    }

    @Test
    void calculateDiscount_shouldReturnZero_whenQuantityIsExactlyTenAndTotalIsExactlyFiveHundred() {
        double discount = discountService.calculateDiscount(500.0, 10, 3L);

        assertEquals(0.0, discount, 0.0001);
        verify(customerService, never()).isVipCustomer(3L);
    }

    
}
