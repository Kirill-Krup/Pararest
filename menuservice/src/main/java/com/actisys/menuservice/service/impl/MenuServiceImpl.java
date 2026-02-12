package com.actisys.menuservice.service.impl;

import com.actisys.menuservice.dto.AddMenuItemDTO;
import com.actisys.menuservice.dto.MenuItemDTO;
import com.actisys.menuservice.exception.MenuItemNotFoundException;
import com.actisys.menuservice.mapper.MenuItemMapper;
import com.actisys.menuservice.model.MenuItem;
import com.actisys.menuservice.repository.MenuRepository;
import com.actisys.menuservice.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public List<MenuItemDTO> getAllMenu() {
        List<MenuItem> allItems = menuRepository.findAll();
        return allItems.stream().map(menuItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MenuItemDTO addNewMenuItem(AddMenuItemDTO newMenuItemDTO) {
        return null;
    }

    @Override
    public MenuItemDTO editMenuItem(Long id, MenuItemDTO menuItemDTO) {
        return null;
    }

    @Override
    public MenuItemDTO editAvailability(Long id) {
        MenuItem menuItem = menuRepository.findById(id).orElseThrow(()-> new MenuItemNotFoundException(id));
        menuItem.setActive(!menuItem.getActive());
        return menuItemMapper.toDto(menuRepository.save(menuItem));
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuRepository.findById(id).orElseThrow(()-> new MenuItemNotFoundException(id));
        menuRepository.delete(menuItem);
    }
}
