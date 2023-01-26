package by.belstu.it.lyskov.service;

import by.belstu.it.lyskov.model.NvidiaDriver;

public class NvidiaDriverCreator {

    private static final String TESTDATA_DRIVER_PRODUCT = "testdata.driver.product";
    private static final String TESTDATA_DRIVER_SERIES = "testdata.driver.series";
    private static final String TESTDATA_DRIVER_MODEL = "testdata.driver.model";
    private static final String TESTDATA_DRIVER_OS = "testdata.driver.os";
    private static final String TESTDATA_DRIVER_LANG = "testdata.driver.lang";
    private static final String TESTDATA_DRIVER_TYPE = "testdata.driver.type";

    public static NvidiaDriver withParametersFromProperty() {
        return new NvidiaDriver(
                TestDataReader.getTestData(TESTDATA_DRIVER_PRODUCT),
                TestDataReader.getTestData(TESTDATA_DRIVER_SERIES),
                TestDataReader.getTestData(TESTDATA_DRIVER_MODEL),
                TestDataReader.getTestData(TESTDATA_DRIVER_OS),
                TestDataReader.getTestData(TESTDATA_DRIVER_LANG),
                TestDataReader.getTestData(TESTDATA_DRIVER_TYPE));
    }
}
