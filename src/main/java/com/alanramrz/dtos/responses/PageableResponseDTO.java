package com.alanramrz.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PageableResponseDTO<T> {
    private List<T> data;
    private PaginationDTO pagination;

    public PageableResponseDTO(List<T> data, int size, int pageNumber, long totalElements, boolean lastPage) {
        this.data = data;
        this.pagination = new PaginationDTO(size, pageNumber, totalElements, lastPage);
    }
}

@Data
@AllArgsConstructor
class PaginationDTO {
    private int size;
    private int pageNumber;
    private long totalElements;
    private boolean lastPage;
}
