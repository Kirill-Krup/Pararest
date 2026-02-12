package com.actisys.menuservice.mapper;

import com.actisys.menuservice.dto.CategoryDTO;
import com.actisys.menuservice.dto.MenuCategoryDTO;
import com.actisys.menuservice.model.MenuCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {
    MenuCategoryDTO toDto(MenuCategory menuCategory);
    CategoryDTO toNameDto(MenuCategory category);
}
