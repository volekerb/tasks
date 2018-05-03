package integration;

import com.test.calculator.CalculatorApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CalculatorApplication.class})
public final class IntegrationTest extends AbstractTestNGSpringContextTests {

    private final ManualRestDocumentation restDocumentation = new ManualRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setUp(Method method) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation)).build();
        this.restDocumentation.beforeTest(getClass(), method.getName());
    }

    @AfterMethod
    public void tearDown() {
        this.restDocumentation.afterTest();
    }

    @DataProvider(name = "positive")
    private Object[][] positiveDataProvider() {
        return new Object[][]{
                {"1.0", "0.2", "ADD", "1.2"},
                {"1.0", "0.2", "MUL", "0.2"},
                {"0.0", "-1", "ADD", "-1.0"},
        };
    }

    @Test(dataProvider = "positive")
    public void testRestPositive(String number1,
                                 String number2,
                                 String operation,
                                 String expectedResult) throws Exception {
        MvcResult response = mockMvc.perform(get(RestTestHelper.buildUrl(number1, number2, operation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        response.getResponse().getContentAsString();
        assertEquals(response.getResponse().getContentAsString(),
                expectedResult,
                "Response contains wrong calculated number");
    }

    @DataProvider(name = "negative")
    private Object[][] negativeDataProvider() {
        return new Object[][]{
                {"1.0", "0.2", "+", Arrays.asList("\"+: Such operation is not supported\"")},
                {"1.0", "0.2", null, Arrays.asList("\"null: Such operation is not supported\"")},
                {"1.0", "Test", "ADD", Arrays.asList("\"For input string: \\\"Test\\\"\"")},
                {"1.0", null, "ADD", Arrays.asList("\"For input string: \\\"null\\\"\"")},
        };
    }

    @Test(dataProvider = "negative")
    public void testRestNegative(String number1,
                                 String number2,
                                 String operation,
                                 List<String> expectedErrors) throws Exception {
        MvcResult response = mockMvc.perform(get(RestTestHelper.buildUrl(number1, number2, operation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        response.getResponse().getContentAsString();
        assertEquals(response.getResponse().getContentAsString(),
                expectedErrors.toString(),
                "Response contains unexpected error message");
    }
}
