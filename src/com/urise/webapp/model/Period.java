package com.urise.webapp.model;

import com.urise.webapp.util.XmlLocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
    private LocalDate begin;

    @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
    private LocalDate end;
    private String title;
    private String description;

    public Period() {
    }

    public Period(LocalDate begin, LocalDate end, String title, String description) {
        this.begin = begin;
        this.end = end;
        this.title = title;
        this.description = description;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(begin, period.begin) && Objects.equals(end, period.end) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end, title, description);
    }

    @Override
    public String toString() {
        return begin + " - " + end + "\n" + description;
    }
}
