<table class="codeTable">
<#list codeEntries as codeEntry >
<#if (snippet.mutant.baseSourceLineStart <= codeEntry.lineNumber) &&
(snippet.mutant.baseSourceLineEnd >= codeEntry.lineNumber) >
<#if snippet.mutant.survived >
<tr class="covered_survived">
<#else>
<tr class="covered_killed">
</#if>
<#else>
<tr class="uncovered">
</#if>
<td>
${codeEntry.lineNumber?html}
</td>
<td>
${codeEntry.code?html?replace("  ", "&nbsp;&nbsp;")}
</td>
</tr>
</#list>
</table>
