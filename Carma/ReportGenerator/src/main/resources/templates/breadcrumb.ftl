<div class="breadcrumb">
<#--<#if ! showPackageBreadCrumb?exists && ! showClassBreadCrumb?exists>Project</#if>-->
<#if showPackageBreadCrumb?exists><a href="index.html">Project</a> &gt; ${packageName}</#if>
<#if showClassBreadCrumb?exists><a href="index.html">Project</a> &gt; <a href="${packageName}.html">${packageName}</a> &gt; ${className}</#if>
</div>
