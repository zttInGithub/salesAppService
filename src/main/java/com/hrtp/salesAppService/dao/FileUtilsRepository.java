package com.hrtp.salesAppService.dao;

import com.hrtp.salesAppService.entity.ormEntity.FileUtilsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * MistakeRepository
 * description
 * sl
 **/
@Repository
public interface FileUtilsRepository extends JpaRepository<FileUtilsModel,String> {
	
	@Query("select new FileUtilsModel(upload_path) from FileUtilsModel where title = ?1 ")
	FileUtilsModel getUploadPathByTitle(String title);
}
