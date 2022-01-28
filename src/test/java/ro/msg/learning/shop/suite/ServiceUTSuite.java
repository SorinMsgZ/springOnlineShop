package ro.msg.learning.shop.suite;


import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import ro.msg.learning.shop.service.unit_test.CsvSerializationDeserializationServiceUnitTest;


@RunWith(Suite.class)
@Suite.SuiteClasses
        ({
                CsvSerializationDeserializationServiceUnitTest.class,

        })
public class ServiceUTSuite {
}
