<table>
<#list codeEntries as codeEntry >
<tr>
<td>
${codeEntry.lineNumber?html}
</td>
<td>
<div class="code">
<#if snippet.mutant.isSurvived()>
<#assign coverageFlag = "covered_survived"/>
<#else>
<#assign coverageFlag = "covered_killed"/>
</#if>
<#if snippet.mutant.baseSourceLineStart == codeEntry.lineNumber>
<#if snippet.mutant.baseSourceLineEnd == codeEntry.lineNumber>
<div class="uncovered">
<pre>${codeEntry.code?substring(0,snippet.mutant.baseSourceColumnStart-1)?html}</pre>
</div>
<div class="${coverageFlag}">
<pre>${codeEntry.code?substring(snippet.mutant.baseSourceColumnStart-1, snippet.mutant.baseSourceColumnEnd)?html}</pre>
</div>
<div class="uncovered">
<pre>${codeEntry.code?substring(snippet.mutant.baseSourceColumnEnd)?html}</pre>
</div>
<#else>
<div class="uncovered">
<pre>${codeEntry.code?substring(0,snippet.mutant.baseSourceColumnStart-1)?html}</pre>
</div>
<div class="${coverageFlag}">
<pre>${codeEntry.code?substring(snippet.mutant.baseSourceColumnStart-1)?html}</pre>
</div>
<div class="uncovered">
<pre></pre>
</div>
</#if>
<#else>
<#if snippet.mutant.baseSourceLineEnd == codeEntry.lineNumber>
<div class="uncovered">
<pre></pre>
</div>
<div class="${coverageFlag}">
<pre>${codeEntry.code?substring(0, snippet.mutant.baseSourceColumnEnd)?html}</pre>
</div>
<div class="uncovered">
<pre>${codeEntry.code?substring(snippet.mutant.baseSourceColumnEnd)?html}</pre>
</div>
<#else>
<div class="uncovered">
<pre></pre>
</div>
<div class="${coverageFlag}">
<pre>${codeEntry.code?html}</pre>
</div>
<div class="uncovered">
<pre></pre>
</div>
</#if>
</#if>
</div>
</td>
</tr>
</#list>
</table>
