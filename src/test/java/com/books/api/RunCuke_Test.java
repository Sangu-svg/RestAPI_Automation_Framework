package com.books.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.util.Objects;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/books/api/features",
        glue = {"com/books/api/stepDefs"}, dryRun = false,
        tags =  "@CreateBook or @GetAllBooks or @UpdateBook or @DeleteBooks or " +
                "@Error_validations or @Error_validation_for_duplicate_book_creation or @Error_validation_for_InvalidBookId",

        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)

public class RunCuke_Test extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = false)
    @Override
    public Object[][] scenarios() {return super.scenarios();}

}