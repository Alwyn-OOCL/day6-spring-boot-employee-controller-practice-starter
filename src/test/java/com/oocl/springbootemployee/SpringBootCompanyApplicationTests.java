package com.oocl.springbootemployee;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class SpringBootCompanyApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private JacksonTester<List<Company>> companiesJacksonTester;

    private List<Company> companies;

    @BeforeEach
    public void setUp() {
        companies = companyRepository.getAll();
        companies.clear();
        companies.add(new Company(1, "a"));
        companies.add(new Company(2, "b"));
        companies.add(new Company(3, "c"));
    }
    @Test
    void should_return_Companies_when_get_all_companies() throws Exception {
        //Given

        //When

        //Then
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Company> responseCompanies = companiesJacksonTester.parse(responseJson).getObject();
        assertEquals(3, responseCompanies.size());

        assertIterableEquals(companies, responseCompanies);
    }
}
