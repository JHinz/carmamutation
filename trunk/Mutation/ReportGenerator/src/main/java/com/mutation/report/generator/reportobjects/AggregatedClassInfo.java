package com.mutation.report.generator.reportobjects;


public class AggregatedClassInfo {
	private long sumSurvivors;
	private long sumMutants;
	private int numClasses;

	public int getNumClasses() {
		return numClasses;
	}

	public long getSumMutants() {
		return sumMutants;
	}

	public long getSumSurvivors() {
		return sumSurvivors;
	}

	public double getMutationCoverageMean(){
		if(sumMutants == 0){
			return 1.0;
		}
		
		double cov = (double)(sumMutants - sumSurvivors) / (double)sumMutants;
		return cov;
		
	}

	public AggregatedClassInfo(long sumMutants, long sumSurvivors, int numClasses) {
		super();
		this.sumSurvivors = sumSurvivors;
		this.sumMutants = sumMutants;
		this.numClasses = numClasses;
	}
}
