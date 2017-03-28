/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.repository;

import com.dazito.komoot.model.db.NotificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daz
 */
@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, Long>{
    NotificationEntity findById(long id);
    
    @Query("SELECT n FROM NotificationEntity n WHERE n.processed = FALSE ORDER BY n.timestamp ASC")
    List<NotificationEntity> findByTimestampHigherThan();
}
