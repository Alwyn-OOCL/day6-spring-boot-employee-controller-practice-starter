package com.oocl.springbootemployee;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;

import java.util.ArrayList;
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
class SpringBootEmployeeApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JacksonTester<List<Employee>> employeesJacksonTester;

    @Autowired
    private JacksonTester<List<Company>> companiesJacksonTester;

    private List<Employee> employees;

    private List<Company> companies;


    @BeforeEach
    public void setUp() {
        initialEmployees();
        initialCompanies();
    }

    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given

        //When

        //Then
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Employee> responseEmployees = employeesJacksonTester.parse(responseJson).getObject();
        assertEquals(3, responseEmployees.size());

        assertIterableEquals(employees, responseEmployees);
    }


    @Test
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
        Integer id = employees.get(0).getId();

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employees.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employees.get(0).getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employees.get(0).getGender().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employees.get(0).getSalary()));
    }

    @Test
    void should_return_employees_when_get_by_gender_given_MALE() throws Exception {
        //Given
        String gender = "MALE";

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", gender))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employees.get(1).getName()));
    }

    @Test
    void should_save_employee_when_create_employee_given_employee() throws Exception {
        //Given
        String givenEmployee = "{\n" +
                "    \"name\": \"d\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"salary\": 5000.0\n" +
                "}";

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("d"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5000.0));
    }

    @Test
    void should_update_employee_age_and_salary_when_update_employee_given_age_and_salary() throws Exception {
        //Given
        String givenEmployee = "{\n" +
                "    \"age\": 30,\n" +
                "    \"salary\": 8000.0\n" +
                "}";

        Integer id = 1;
        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("a"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(8000.0));
    }

    @Test
    void should_return_null_when_delete_given_id() throws Exception {
        //Given
        int id = 2;
        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
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

    @Test
    void should_return_employees_when_get_employees_by_company_id_given_company_id() throws Exception {
        //Given
        Integer companyId = 1;

        //When

        //Then
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/employees/listByCompanyId/" + companyId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Employee> responseEmployees = employeesJacksonTester.parse(responseJson).getObject();
        assertIterableEquals(employees
                        .stream()
                        .filter(employee -> employee.getCompanyId().equals(companyId))
                        .toList(),
                responseEmployees);
    }

    private void initialEmployees() {
        employees = employeeRepository.getAll();
        employees.clear();
        employees.add(new Employee(1, "a", 20, Gender.MALE, 5000.0, 1));
        employees.add(new Employee(2, "b", 20, Gender.MALE, 5000.0, 1));
        employees.add(new Employee(3, "c", 20, Gender.FEMALE, 5000.0, 2));
    }

    private void initialCompanies() {
        companies = companyRepository.getAll();
        companies.clear();
        companies.add(new Company(1, "companyA"));
        companies.add(new Company(2, "companyB"));
        companies.add(new Company(3, "companyC"));
        companies.add(new Company(4, "companyD"));
        companies.add(new Company(5, "companyE"));
        companies.add(new Company(6, "companyF"));
        companies.add(new Company(7, "companyG"));
    }
}
