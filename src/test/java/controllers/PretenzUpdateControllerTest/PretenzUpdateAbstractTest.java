package controllers.PretenzUpdateControllerTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * abstract class for testing
 **/
@PropertySource("classpath:config.properties")
abstract class PretenzUpdateAbstractTest {
    @Value("${testCurrentMonth}")
    String currentMonth;
    @Value("${testOtchetMonth}")
    String othcetMonth;
    @Value("${testCurrentYear}")
    String currentYear;
    @Value("${testOtchetYear}")
    String otchetYear;
    @Value("${testWrongOtchetMonth}")
    String wrongOtchetMonth;
}
