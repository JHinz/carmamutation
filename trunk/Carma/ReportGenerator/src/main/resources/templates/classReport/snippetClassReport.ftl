<#list snippets as snippet>
<#assign codeEntries = snippet.codeEntries >
<#if extendedJcovInfoAvailable?exists && extendedJcovInfoAvailable><#include "extendedSnippet.ftl"><#else><#include "standardSnippet.ftl"></#if>
<#include "snippetDescription.ftl">
</#list>
