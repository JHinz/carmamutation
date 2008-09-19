<html>
<#if ! ingoreExternalTemplates?exists><#include "./header.ftl"></#if>
<body>
<div class="projectStatistics">
<table>
<thead>
<tr>
<td>Processed Project</td>
<td>Report was created on</td>
</tr>
</thead>
<tbody>
<tr>
<td><#if projectStatisticsBean.projectName?exists>${projectStatisticsBean.projectName}</#if></td>
<td><#if projectStatisticsBean.creationDate?exists>${projectStatisticsBean.creationDate} (<#if projectStatisticsBean.durationInMilli?exists>${projectStatisticsBean.durationInMilli}</#if>)</#if></td>
</tr>
</tbody>
</table>
</div>
<div class="projectCoverageSummary">
<table>
<thead>
<tr>
<td>Class Count</td>
<td>Coverage Level</td>
<td>Mutation Count</td>
<td>Survived Mutations Count</td>
<td>Defeated Mutations Count</td>
</tr>
</thead>
<tbody>
<tr>
<td>${projectDetailBean.numberOfClasses}</td>
<td><#if projectDetailBean.coverageLevel?exists>${(projectDetailBean.coverageLevel * 100)?string("##")} %<#else>n/a</#if></td>
<td>${projectDetailBean.numberOfSurvivedMutations + projectDetailBean.numberOfDefeatedMutations}</td>
<td>${projectDetailBean.numberOfSurvivedMutations}</td>
<td>${projectDetailBean.numberOfDefeatedMutations}</td>
</tr>
</tbody>
</table>
</div>
<#if ! ingoreExternalTemplates?exists><#include "./packageListing.ftl"></#if>
<#if ! ingoreExternalTemplates?exists><#include "./footer.ftl"></#if>
</body>
</html>
