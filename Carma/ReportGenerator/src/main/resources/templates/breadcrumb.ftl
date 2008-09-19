<div id="breadcrumb">
<a href="index.html">Project</a>
<#if showPackageBreadCrumb?exists>&gt; ${packageName}</#if>
<#if showClassBreadCrumb?exists>&gt; <a href="${packageName}.html">${packageName}</a> &gt; ${className}</#if>
</div>
