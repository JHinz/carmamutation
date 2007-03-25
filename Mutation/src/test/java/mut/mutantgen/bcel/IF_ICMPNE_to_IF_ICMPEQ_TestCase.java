package mut.mutantgen.bcel;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import mut.Mutant;
import mut.MutationOperator;

public class IF_ICMPNE_to_IF_ICMPEQ_TestCase extends TestCase {

	// FileWriter writer = new FileWriter("mod.class");
	// writer.write(new String(byteCode[0]));
	// writer.flush();
	// writer.close();

	private static final String TEMPLATE_CLASS_NAME = "mut.mutantgen.bcel.IF_ICMPNE_to_IF_ICMPEQ_TemplateClass";

	private class TestClassLoader extends ClassLoader {

		private void override(String binaryName, byte[] byteCode) {
			super.defineClass(binaryName, byteCode, 0, byteCode.length);
		}
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_CheckNumberOfMutants() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		assertEquals("Number of first level mutants incorrect", 2, mutants.size());
	}

	public void test_OriginalClassConditionalCases() {
		assertEquals("Unmodified expectation not met", 3, new IF_ICMPNE_to_IF_ICMPEQ_TemplateClass().branch(5, 5));
		assertEquals("Unmodified expectation not met", 2, new IF_ICMPNE_to_IF_ICMPEQ_TemplateClass().branch(1, 5));
		assertEquals("Unmodified expectation not met", 1, new IF_ICMPNE_to_IF_ICMPEQ_TemplateClass().branch(1, 2));
		assertEquals("Unmodified expectation not met", 3, new IF_ICMPNE_to_IF_ICMPEQ_TemplateClass().branch(5, 2));
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case1() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);
		
		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(0).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 5, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(2, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case2() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(0).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case3() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(0).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_FirstBranchModifiedOnlyMutant_Case4() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(0).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 5, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(1, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case1() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(1).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 5, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case2() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(1).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1, 5 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(1, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case3() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(1).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 1, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(2, resultObject);
	}

	public void test_IF_CMPNE_to_IF_CMPEQ_SecondBranchModifiedOnlyMutant_Case4() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		TestClassLoader loader = new TestClassLoader();

		loader.override(TEMPLATE_CLASS_NAME, mutants.get(1).getByteCode());

		Class modifiedInputClass = loader.loadClass(TEMPLATE_CLASS_NAME);

		Object modifiedInputClassInstance = modifiedInputClass.newInstance();

		Method branchMethod = modifiedInputClass.getMethod("branch", new Class[] { int.class, int.class });

		Object resultObject = branchMethod.invoke(modifiedInputClassInstance, new Object[] { 5, 2 });

		assertNotNull(resultObject);
		assertTrue(resultObject.getClass().isInstance(new Integer(2)));
		assertTrue(resultObject.getClass().isAssignableFrom(Integer.class));
		assertEquals(3, resultObject);
	}

	public void test_SourceMappings() throws Exception {

		BCELMutantCreator bcel = new BCELMutantCreator();

		Set<MutationOperator> operators = new HashSet<MutationOperator>();
		operators.add(MutationOperator.ROR);

		List<Mutant> mutants = bcel.generateMutants(TEMPLATE_CLASS_NAME, operators);

		assertEquals("Incorrect number of mutants received", 6, mutants.get(0).getSourceMapping().getLineNo());
		assertEquals("Incorrect number of mutants received", TEMPLATE_CLASS_NAME, mutants.get(0).getSourceMapping()
				.getClassName());

		assertEquals("Incorrect number of mutants received", 7, mutants.get(1).getSourceMapping().getLineNo());
		assertEquals("Incorrect number of mutants received", TEMPLATE_CLASS_NAME, mutants.get(1).getSourceMapping()
				.getClassName());

	}

}
