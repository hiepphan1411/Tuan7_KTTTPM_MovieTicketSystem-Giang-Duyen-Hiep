package iuh.fit.paymentnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PaymentNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentNotificationServiceApplication.class, args);
    }

}
