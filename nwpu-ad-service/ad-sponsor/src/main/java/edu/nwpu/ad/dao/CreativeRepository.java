package edu.nwpu.ad.dao;

import edu.nwpu.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 创意查询接口
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
