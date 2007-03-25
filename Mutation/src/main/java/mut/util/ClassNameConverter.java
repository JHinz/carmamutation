package mut.util;

public class ClassNameConverter {
	public static String classNameToPackageName(String className){
		int lastDot = className.lastIndexOf('.');
		
		String packageName = className.substring(0, lastDot > 0 ? lastDot : className.length());
		return packageName;
	}
	
	public static void main(String[] args) {
		System.out.println(classNameToPackageName("a"));
	}
	
}
