package com.mutation.runner;

import java.util.Set;

import com.mutation.runner.utililties.ToStringUtils;

public interface IClassSetResolver {

	public class ClassDescription {

		String className;

		String packageName;

		String classFile;

		public String getClassFile() {
			return classFile;
		}

		public void setClassFile(String classFile) {
			this.classFile = classFile;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		@Override
		public String toString() {
			return ToStringUtils.toString(this);
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
	}

	Set<ClassDescription> determineClassNames();

}
