package com.inovex.main.file;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FileDao extends CrudRepository<File, Long> {
    File findFirstByOrderByIdDesc();

    Optional<File> findByFileName(String fileName);

    @Transactional
    @Modifying
    @Query(value = "delete from organizations_file where organizations_file.file_id = ?1 ", nativeQuery = true)
    public int deleteRef(long id);

    @Transactional
    @Modifying
    @Query(value = "delete FROM files where id=?1", nativeQuery = true)
    int delete(long id);

}