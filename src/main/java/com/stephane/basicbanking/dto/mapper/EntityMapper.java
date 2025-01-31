package com.stephane.basicbanking.dto.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);


    List<E> toEntity(List<D> dtos);

    List<D> toDto(List<E> entities);
}
