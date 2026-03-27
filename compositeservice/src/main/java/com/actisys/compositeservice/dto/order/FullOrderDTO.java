package com.actisys.compositeservice.dto.order;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullOrderDTO {
    private OrderDTO order;
    private MenuItemDTO menuItem;
}
