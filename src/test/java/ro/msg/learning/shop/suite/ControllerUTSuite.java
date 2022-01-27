package ro.msg.learning.shop.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ro.msg.learning.shop.controller.unit_test.CsvSerializationDeserializationRestTest;
import ro.msg.learning.shop.service.unit_test.CsvSerializationDeserializationServiceUnitTest;
import ro.msg.learning.shop.service.unit_test.LocationStrategiesProfileTest;


@RunWith(Suite.class)
@Suite.SuiteClasses
        ({
                CsvSerializationDeserializationRestTest.class,
        })
public class ControllerUTSuite {
}
