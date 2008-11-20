<#list snippets as snippet>
<#assign codeEntries = snippet.codeEntries >
<#assign mymutant = snippet.mutant >
<div class="snippet">
<#include "snippetDescription.ftl">
<#if extendedJcovInfoAvailable?exists && extendedJcovInfoAvailable><#include "extendedSnippet.ftl"><#else><#include "standardSnippet.ftl"></#if>
</div>
</#list>
