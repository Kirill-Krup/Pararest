package com.actisys.paymentservice.service.impl;

import com.actisys.kafkacommunication.wallet.CreateWalletEvent;
import com.actisys.paymentservice.dto.CreateReplenishment;
import com.actisys.paymentservice.dto.PaymentDTO;
import com.actisys.paymentservice.mapper.PaymentMapper;
import com.actisys.paymentservice.model.Payment;
import com.actisys.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void createNewReplenishment_createsPaymentAndSendsWalletEvent() {
        UUID userId = UUID.randomUUID();
        CreateReplenishment request = CreateReplenishment.builder()
                .totalAmount(new BigDecimal("50.00"))
                .build();
        Payment savedPayment = Payment.builder().id(9L).userId(userId).totalAmount(new BigDecimal("50.00")).build();
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(paymentMapper.toDTO(savedPayment)).thenReturn(PaymentDTO.builder().id(9L).build());

        PaymentDTO result = paymentService.createNewReplenishment(request, userId);

        assertEquals(9L, result.getId());
        ArgumentCaptor<CreateWalletEvent> eventCaptor = ArgumentCaptor.forClass(CreateWalletEvent.class);
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("REPLENISHMENT_EVENT"), eventCaptor.capture());
        assertEquals(userId, eventCaptor.getValue().getUserId());
    }
}
