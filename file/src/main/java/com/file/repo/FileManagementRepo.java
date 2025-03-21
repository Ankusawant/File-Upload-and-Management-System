package com.file.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.file.entity.FileManagement;

public interface FileManagementRepo extends JpaRepository<FileManagement, Long> {
}
