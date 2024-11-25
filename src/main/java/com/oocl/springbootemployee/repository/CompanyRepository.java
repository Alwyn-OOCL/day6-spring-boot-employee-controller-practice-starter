package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {

    private final List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(1, "a"));
        companies.add(new Company(2, "b"));
        companies.add(new Company(3, "c"));
    }

    public List<Company> getAll() {
        return companies;
    }

}
