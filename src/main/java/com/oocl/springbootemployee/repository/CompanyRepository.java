package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Company;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {

    private final List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(1, "companyA"));
        companies.add(new Company(2, "companyB"));
        companies.add(new Company(3, "companyC"));
        companies.add(new Company(4, "companyD"));
        companies.add(new Company(5, "companyE"));
        companies.add(new Company(6, "companyF"));
        companies.add(new Company(7, "companyG"));

    }

    public List<Company> getAll() {
        return companies;
    }

    public List<Company> getCompaniesByPage(Integer page, Integer size) {
        return companies.stream()
                .skip((page - 1) * size)
                .limit(size)
                .toList();
    }
}
