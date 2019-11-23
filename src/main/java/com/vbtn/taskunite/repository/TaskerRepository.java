package com.vbtn.taskunite.repository;
import com.vbtn.taskunite.domain.Tasker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Tasker entity.
 */
@Repository
public interface TaskerRepository extends JpaRepository<Tasker, Long> {

    @Query(value = "select distinct tasker from Tasker tasker left join fetch tasker.taskCategories",
        countQuery = "select count(distinct tasker) from Tasker tasker")
    Page<Tasker> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct tasker from Tasker tasker left join fetch tasker.taskCategories")
    List<Tasker> findAllWithEagerRelationships();

    @Query("select tasker from Tasker tasker left join fetch tasker.taskCategories where tasker.id =:id")
    Optional<Tasker> findOneWithEagerRelationships(@Param("id") Long id);

}
