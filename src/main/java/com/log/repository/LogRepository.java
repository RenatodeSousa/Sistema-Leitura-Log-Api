package com.log.repository;

import com.log.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    @Transactional(readOnly = true)
    Optional<List<Log>> findByIpOrderByDate(String ip);

}
