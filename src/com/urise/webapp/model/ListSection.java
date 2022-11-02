package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private final List<String> contentList = new ArrayList<>();

    public void addContent(String text) {
        contentList.add(text);
    }

    public List<String> getContentList() {
        return contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(contentList, that.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentList);
    }

    @Override
    public String toString() {
        return contentList.toString();
    }
}
