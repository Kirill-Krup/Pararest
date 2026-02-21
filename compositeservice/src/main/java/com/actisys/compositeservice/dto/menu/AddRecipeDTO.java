package com.actisys.compositeservice.dto.menu;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRecipeDTO {
    private Long menuItemId;
    private HashMap<Long, Integer> ingredientsMap;
}
