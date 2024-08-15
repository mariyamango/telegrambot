package com.github.mariyamango.tb.repository;

import com.github.mariyamango.tb.repository.entity.GroupSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for {@link GroupSub} entity.
 */

@Repository
public interface GroupSubRepository extends JpaRepository<GroupSub, Integer> {
}
