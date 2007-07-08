package com.retroduction.carma.report.generator.chart;

import java.util.ArrayList;
import java.util.List;

import com.retroduction.carma.report.generator.reportobjects.ClassInfo;

public class DataClass {
	private ClassRange range;

	private List<ClassInfo> instances = new ArrayList<ClassInfo>();
	public int getNumInstances() {
		return this.instances.size();
	}

	public void addInstance(ClassInfo instance) {
		this.instances.add(instance);
	}
	public DataClass(ClassRange range) {
		this.range = range;
	}
	public List<ClassInfo> getInstances() {
		return this.instances;
	}
	public ClassRange getRange() {
		return this.range;
	}
}
