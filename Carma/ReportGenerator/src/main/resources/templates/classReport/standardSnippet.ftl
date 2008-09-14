<table>
<#list codeEntries as codeEntry >
<tr>
<td>
<div class="lineNo">
${codeEntry.lineNumber?html}
</div>
</td>
<td>
<#if (snippet.mutant.baseSourceLineStart <= codeEntry.lineNumber) &&
(snippet.mutant.baseSourceLineEnd >= codeEntry.lineNumber) >
<#if snippet.mutant.survived >
<div class="covered_survived">
<#else>
<div class="covered_killed">
</#if>
<#else>
<div class="uncovered">
</#if>
${codeEntry.code?html?replace("  ", "&nbsp;&nbsp;")}
</div>
</td>
</tr>
</#list>
</table>
