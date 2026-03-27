package com.actisys.menuservice.repository;

import com.actisys.menuservice.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {
    boolean existsByName(String name);
}
