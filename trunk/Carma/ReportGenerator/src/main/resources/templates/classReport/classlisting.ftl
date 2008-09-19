<html>
<#if ! ingoreExternalTemplates?exists><#include "../header.ftl"></#if>
<body>
<#if ! ingoreExternalTemplates?exists><#include "../breadcrumb.ftl"></#if>
<div class="classListing">
<table>
<thead>
<tr>
<td>Class</td>
<td>Coverage Level</td>
<td>Mutation Count</td>
<td>Survived Mutations Count</td>
<td>Defeated Mutations Count</td>
</tr>
</thead>
<tbody>
<#list classesUnderTest as classUnderTest >
<tr>
<td><a href="${classUnderTest.fqName}.html">${classUnderTest.shortName}</a></td>
<td><#if classUnderTest.coverage?exists>${classUnderTest.coverage * 100} %<#else>n/a</#if></td>
<td>${classUnderTest.numberOfMutants}</td>
<td>${classUnderTest.numberOfSurvivors}</td>
<td>${classUnderTest.numberOfMutants - classUnderTest.numberOfSurvivors}</td>
</tr>
</#list>
</tbody>
</table>
</div>
<#if ! ingoreExternalTemplates?exists><#include "../footer.ftl"></#if>
</body>
</html>
