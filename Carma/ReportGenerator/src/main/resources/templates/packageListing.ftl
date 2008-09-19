<table>
<thead>
<tr>
<td>Package</td>
<td>Class Count</td>
<td>Coverage Level</td>
<td>Mutation Count</td>
<td>Survived Mutations Count</td>
<td>Defeated Mutations Count</td>
</tr>
</thead>
<tbody>
<#list packageDetailBeans as packageDetailBean >
<tr>
<td><a href="${packageDetailBean.fqName}.html">${packageDetailBean.fqName}</a></td>
<td>${packageDetailBean.numberOfClasses}</td>
<td><#if packageDetailBean.coverageLevel?exists>${(packageDetailBean.coverageLevel * 100)?string("##")} %<#else>n/a</#if></td>
<td>${packageDetailBean.numberOfSurvivedMutations + packageDetailBean.numberOfDefeatedMutations}</td>
<td>${packageDetailBean.numberOfSurvivedMutations}</td>
<td>${packageDetailBean.numberOfDefeatedMutations}</td>
</tr>
</#list>
</tbody>
</table>