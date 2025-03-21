package com.file.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fileManagement")
public class FileManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String fileName;
	private String fileUrl;
	private long size;
	private LocalDateTime uploadDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public FileManagement(long id, String fileName, String fileUrl, long size, LocalDateTime uploadDate) {
		this.id = id;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.size = size;
		this.uploadDate = uploadDate;
	}

	public FileManagement(){

	}

}
