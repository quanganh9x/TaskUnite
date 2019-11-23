package com.vbtn.taskunite.repository;
import com.vbtn.taskunite.domain.TaskCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

}
