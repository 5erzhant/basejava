package com.urise.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final CompanySection EMPTY = new CompanySection();
    static {
        Company.EMPTY.addPeriod(Period.EMPTY);
        EMPTY.addCompany(Company.EMPTY);
    }

    private List<Company> companies = new ArrayList<>();

    public CompanySection() {}

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companies);
    }

    @Override
    public String toString() {
        return companies.toString();
    }
}
