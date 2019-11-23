package com.vbtn.taskunite.repository;
import com.vbtn.taskunite.domain.UserInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {

}
