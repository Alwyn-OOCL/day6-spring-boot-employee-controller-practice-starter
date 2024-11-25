package com.oocl.springbootemployee;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.util.List;
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
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Employee> jsonTester;

    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employees.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(employees.get(2).getName()));
    }


    @Test
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();
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
        List<Employee> employees = employeeRepository.getAll();
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

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
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
        String json = mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + id))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Then

        Employee originEmployee = jsonTester.parse(json).getObject();
        assertEquals(20, originEmployee.getAge());
        assertEquals(5000.0, originEmployee.getSalary());

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
}
