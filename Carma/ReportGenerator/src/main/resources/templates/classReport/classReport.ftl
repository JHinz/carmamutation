<#if ! ingoreExternalTemplates?exists><#include "../doctype.ftl"></#if>
<html>
<head>
<#if ! ingoreExternalTemplates?exists><#include "../header.ftl"></#if>
</head>
<body>
<#if ! ingoreExternalTemplates?exists><#include "../breadcrumb.ftl"></#if>
<div class="content">
<#include "snippetClassReport.ftl">
</div>
<#if ! ingoreExternalTemplates?exists><#include "../footer.ftl"></#if>
</body>
</html>
