package mut.executer;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class loader loads unit test and mutant.
 * 
 * Order:
 * 1. look for mutant byte code
 * 2. look in tests class path
 * 3. delegate to parent

 * @author mike
 *
 */
public class MutationClassLoader extends URLClassLoader {

	private byte[] mutantByteCode;
	private String mutantClassName;
	public MutationClassLoader(String mutantClassName, byte[] mutantByteCode) {
		this(null, mutantClassName, mutantByteCode);
	}

	public MutationClassLoader(URL[] urls, String mutantClassName, byte[] mutantByteCode) {
		super(urls);
		this.mutantClassName = mutantClassName;
		this.mutantByteCode = mutantByteCode;

	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class clazz;
		if(name.equals(mutantClassName)){
			clazz = super.defineClass(name, this.mutantByteCode, 0, this.mutantByteCode.length);
			log("Loading mutant class: " + name);
		}else{
			try{
				clazz = findClass(name);
				log("Loaded locally: " + name);
			}catch(ClassNotFoundException e){
				clazz = super.loadClass(name, false);
				log("Loaded by parent: " + name);
			}
		}
		return clazz;
	}


	private void log(String string) {
		//System.out.println(getClass().getSimpleName() +" # MyClassLoader: " + string);

	}
	
}
