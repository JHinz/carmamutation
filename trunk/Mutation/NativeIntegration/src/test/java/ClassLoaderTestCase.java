import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class ClassLoaderTestCase extends TestCase {

	public void test() throws MalformedURLException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException {

		HelperClassLoader loader1 = new HelperClassLoader(new URL[] { new URL("file:src/test/it/it0003/") }, "loader1",
				this.getClass().getClassLoader());
		HelperClassLoader loader2 = new HelperClassLoader(new URL[] { new URL("file:src/test/it/it0003/") }, "loader2",
				this.getClass().getClassLoader());

		Class classA1 = loader1.loadClass("A");
		Class classB1 = loader1.loadClass("B");
		Class classA2 = loader2.loadClass("A");
		Class classB2 = loader2.loadClass("B");

		assertFalse(classA1.isAssignableFrom(classA2.getClass()));

		Object a1Instance = classA1.newInstance();
		Object a2Instance = classA2.newInstance();

		assertFalse(a1Instance.getClass().isAssignableFrom(a2Instance.getClass()));

		Method a1MethodGetB = classA1.getMethod("getB", new Class[] {});
		Method a2MethodGetB = classA2.getMethod("getB", new Class[] {});

		Object bFromA1 = a1MethodGetB.invoke(a1Instance, new Object[] {});
		Object bFromA2 = a2MethodGetB.invoke(a2Instance, new Object[] {});

		assertFalse(bFromA1.getClass().isAssignableFrom(bFromA2.getClass()));

		Method a2MethodSetBFromA2 = classA2.getMethod("setB", new Class[] { classB2 });

		a2MethodSetBFromA2.invoke(a2Instance, new Object[] { bFromA2 });

		try {
			Method a2MethodSetBFromA1 = classA2.getMethod("setB", new Class[] { classB1 });
			a2MethodSetBFromA1.invoke(a2Instance, new Object[] { bFromA2 });
			assertTrue(false);
			return;
		} catch (NoSuchMethodException e) {
		}

		Method a1MethodGetC = classA1.getMethod("getC", new Class[] {});
		Object cFromA1 = a1MethodGetC.invoke(a1Instance, new Object[] {});
		
		Method a2MethodSetCFromA1 = classA2.getMethod("setC", new Class[] { cFromA1.getClass() });

		a2MethodSetCFromA1.invoke(a2Instance, new Object[] { cFromA1 });
	}

}
