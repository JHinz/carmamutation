/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 *
 */public class PackageListingBeanBuilder {

	public List<PackageDetailBean> get(MutationRun report) {

		Map<String, PackageDetailBean> packageDetailBeanMap = new HashMap<String, PackageDetailBean>();

		PackageDetailBean projectBean = new PackageDetailBean();
		projectBean.setFqName("");
		projectBean.setNumberOfClasses(0);
		projectBean.setNumberOfDefeatedMutations(0);
		projectBean.setNumberOfSurvivedMutations(0);
		projectBean.setCoverageLevel(null);

		for (ClassUnderTest clazz : report.getClassUnderTest()) {

			if (!packageDetailBeanMap.containsKey(clazz.getPackageName())) {
				PackageDetailBean bean = new PackageDetailBean();
				bean.setFqName(clazz.getPackageName());
				bean.setNumberOfClasses(0);
				bean.setNumberOfDefeatedMutations(0);
				bean.setNumberOfSurvivedMutations(0);
				bean.setCoverageLevel(null);
				packageDetailBeanMap.put(bean.getFqName(), bean);
			}

			PackageDetailBean bean = packageDetailBeanMap.get(clazz.getPackageName());

			bean.setNumberOfClasses(bean.getNumberOfClasses() + 1);

			bean.setNumberOfDefeatedMutations(bean.getNumberOfDefeatedMutations()
					+ clazz.getMutationRatio().getMutationCount()
					- clazz.getMutationRatio().getSurvivorCount());

			bean.setNumberOfSurvivedMutations(bean.getNumberOfSurvivedMutations()
					+ clazz.getMutationRatio().getSurvivorCount());

			if (bean.getNumberOfDefeatedMutations() + bean.getNumberOfSurvivedMutations() > 0) {
				bean.setCoverageLevel(bean.getNumberOfDefeatedMutations()
						/ (double) (bean.getNumberOfDefeatedMutations() + (double) bean
								.getNumberOfSurvivedMutations()));
			} else {
				bean.setCoverageLevel(null);
			}

		}

		List<PackageDetailBean> result = new ArrayList<PackageDetailBean>();

		for (PackageDetailBean packageBean : packageDetailBeanMap.values()) {
			result.add(packageBean);
			projectBean.setNumberOfClasses(projectBean.getNumberOfClasses() + packageBean.getNumberOfClasses());
			projectBean.setNumberOfDefeatedMutations(projectBean.getNumberOfDefeatedMutations()
					+ packageBean.getNumberOfDefeatedMutations());

			projectBean.setNumberOfSurvivedMutations(projectBean.getNumberOfSurvivedMutations()
					+ packageBean.getNumberOfSurvivedMutations());

			if (projectBean.getNumberOfDefeatedMutations() + projectBean.getNumberOfSurvivedMutations() > 0) {
				projectBean
						.setCoverageLevel(projectBean.getNumberOfDefeatedMutations()
								/ (double) (projectBean.getNumberOfDefeatedMutations() + (double) projectBean
										.getNumberOfSurvivedMutations()));
			} else {
				projectBean.setCoverageLevel(null);
			}
		}
		
		result.add(projectBean);

		Collections.sort(result, new Comparator<PackageDetailBean>() {

			public int compare(PackageDetailBean o1, PackageDetailBean o2) {
				return o1.getFqName().compareTo(o2.getFqName());
			}

		});

		return result;

	}
}
