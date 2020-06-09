package com.example.demo.util;

import java.io.File;
import java.util.LinkedList;

public class DataInfo {
	
	private File file;
	private int numberOfRecords;
	private int numberOfColumns;
	private LinkedList<ColumnIndexName> loadedColumns;
	private LinkedList<Record> records;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public LinkedList<ColumnIndexName> getLoadedColumns() {
		return loadedColumns;
	}
	public void setLoadedColumns(LinkedList<ColumnIndexName> loadedColumns) {
		this.loadedColumns = loadedColumns;
	}
	public LinkedList<Record> getRecords() {
		return records;
	}
	public void setRecords(LinkedList<Record> records) {
		this.records = records;
	}
	
	
}
