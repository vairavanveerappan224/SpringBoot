package com.example.applicationSpring.model;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class PaginationInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalpage;
}
