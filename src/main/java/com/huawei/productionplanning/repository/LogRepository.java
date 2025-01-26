package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.Log;
import com.huawei.productionplanning.enums.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findLogByMessageContaining(String message);

    List<Log> findByLevel(LogLevel level);

    @Query("SELECT l FROM Log l WHERE l.createdAt BETWEEN :startDate AND :endDate")
    List<Log> findLogByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

}