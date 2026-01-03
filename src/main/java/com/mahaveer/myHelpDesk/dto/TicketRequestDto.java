package com.mahaveer.myHelpDesk.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class TicketRequestDto {
    private String userName;
    private String query;

    public TicketRequestDto() {
    }

    public TicketRequestDto(String userName, String query) {
        this.userName = userName;
        this.query = query;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "TicketRequestDto{" +
                "userName='" + userName + '\'' +
                ", query='" + query + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketRequestDto that = (TicketRequestDto) o;
        return Objects.equals(userName, that.userName) && Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, query);
    }
}
