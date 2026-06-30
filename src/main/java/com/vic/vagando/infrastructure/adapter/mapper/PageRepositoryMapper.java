package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.PageModel;
import org.springframework.data.domain.Page;

public class PageRepositoryMapper {

    public static <T> PageModel<T> toDomain(Page<T> page) {
        return new PageModel<>(page.getContent(), page.getNumber(),
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
