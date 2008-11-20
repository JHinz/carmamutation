<#if snippet.mutant.isSurvived()>
<#assign coverageFlag = "covered_survived"/>
<#else>
<#assign coverageFlag = "covered_killed"/>
</#if>
<table class="codeTable">
<#list codeEntries as codeEntry >
<#-- ---####---- (1) -->
<#if ( ( snippet.mutant.baseSourceLineStart == codeEntry.lineNumber ) && ( codeEntry.lineNumber == snippet.mutant.baseSourceLineEnd ) ) >
<tr>
<td class="${coverageFlag}">
${codeEntry.lineNumber?html}
</td>
<td>
<div class="uncovered">${codeEntry.code?substring(0,snippet.mutant.baseSourceColumnStart-1)?html?replace("  ", "&nbsp;&nbsp;")}</div>
<div class="${coverageFlag}">${codeEntry.code?substring(snippet.mutant.baseSourceColumnStart-1, snippet.mutant.baseSourceColumnEnd-1)?replace("  ", "&nbsp;&nbsp;")}</div>
<div class="uncovered">${codeEntry.code?substring(snippet.mutant.baseSourceColumnEnd-1)?replace("  ", "&nbsp;&nbsp;")}</div>
</td>
</tr>
</#if>
<#-- ---######## (2) -->
<#if ( ( snippet.mutant.baseSourceLineStart == codeEntry.lineNumber ) && ( codeEntry.lineNumber < snippet.mutant.baseSourceLineEnd ) ) >
<tr>
<td class="${coverageFlag}">
${codeEntry.lineNumber?html}
</td>
<td>
<div class="uncovered">${codeEntry.code?substring(0,snippet.mutant.baseSourceColumnStart-1)?replace("  ", "&nbsp;&nbsp;")}</div>
<div class="${coverageFlag}">${codeEntry.code?substring(snippet.mutant.baseSourceColumnStart-1)?replace("  ", "&nbsp;&nbsp;")}</div>
</td>
</tr>
</#if>
<#-- #######---- (3) -->
<#if ( ( snippet.mutant.baseSourceLineStart < codeEntry.lineNumber ) && ( codeEntry.lineNumber == snippet.mutant.baseSourceLineEnd ) ) >
<tr>
<td class="${coverageFlag}">
${codeEntry.lineNumber?html}
</td>
<td>
<#if ( snippet.mutant.baseSourceColumnEnd > 0 ) >
<div class="${coverageFlag}">${codeEntry.code?substring(0, snippet.mutant.baseSourceColumnEnd-1)?replace("  ", "&nbsp;&nbsp;")}</div>
<div class="uncovered">${codeEntry.code?substring(snippet.mutant.baseSourceColumnEnd-1)?replace("  ", "&nbsp;&nbsp;")}</div>
<#else>
<div class="${coverageFlag}">${codeEntry.code?substring(0, snippet.mutant.baseSourceColumnEnd)?replace("  ", "&nbsp;&nbsp;")}</div>
<div class="uncovered">${codeEntry.code?substring(snippet.mutant.baseSourceColumnEnd)?replace("  ", "&nbsp;&nbsp;")}</div>
</#if>
</td>
</tr>
</#if>
<#-- ########### (4) -->
<#if ( ( snippet.mutant.baseSourceLineStart < codeEntry.lineNumber ) && ( codeEntry.lineNumber < snippet.mutant.baseSourceLineEnd ) ) >
<tr>
<td class="${coverageFlag}">
${codeEntry.lineNumber?html}
</td>
<td>
<div class="${coverageFlag}">${codeEntry.code?replace("  ", "&nbsp;&nbsp;")}</div>
</td>
</tr>
</#if>
<#-- ----------- -->
<#if ( ( snippet.mutant.baseSourceLineStart > codeEntry.lineNumber ) || ( codeEntry.lineNumber > snippet.mutant.baseSourceLineEnd ) ) >
<tr>
<td class="uncovered">
${codeEntry.lineNumber?html}
</td>
<td>
<div class="uncovered">${codeEntry.code?replace("  ", "&nbsp;&nbsp;")}</div>
</td>
</tr>
</#if>
</#list>
</table>
