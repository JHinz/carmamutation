<div id="breadcrumb">
<a href="index.html">Project</a>
<#if packageLevelReport?exists><&gt> <a href="packageDetails.html">${packageName}</a></#if>
<#if packageLevelReport?exists && classLevelReport?exists><&gt> <a href="classDetails.html">${className}s</a></#if>
</div>
