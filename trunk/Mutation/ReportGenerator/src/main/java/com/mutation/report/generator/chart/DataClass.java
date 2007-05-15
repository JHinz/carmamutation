package com.mutation.report.generator.chart;

import java.util.ArrayList;
import java.util.List;

import com.mutation.report.generator.reportobjects.ClassInfo;

public class DataClass {
	private String name;

	private List<ClassInfo> instances = new ArrayList<ClassInfo>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumInstances() {
		return instances.size();
	}

	public void addInstance(ClassInfo instance) {
		instances.add(instance);
	}
	public DataClass(String name) {
		super();
		this.name = name;
	}
	public List<ClassInfo> getInstances() {
		return instances;
	}
}
