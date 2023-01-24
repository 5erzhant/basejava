package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    public static final ListSection EMPTY = new ListSection();

    private List<String> contentList = new ArrayList<>();

    public ListSection() {
    }

    public void addContent(String text) {
        contentList.add(text);
    }

    public void addContent(String[] strings) {
        contentList.addAll(List.of(strings));
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
