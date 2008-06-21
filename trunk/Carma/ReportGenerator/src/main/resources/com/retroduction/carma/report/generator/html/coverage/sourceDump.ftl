<table id="sourceCode" cellspacing="0" cellpadding="0" class="src">
	<#assign mutId = 0 >
	<#list sourceInfo as sourceLine>
	<tr>
		<#if sourceLine.mutantInfo??  >
		<#list sourceLine.mutantInfo as mutant >
		<td class="numLine">${sourceLine.lineNo}</td>
		<td></td>
		<#assign mutId = mutId + 1 >				
		<td>
		<#if mutant.baseSourceColumnStart == 0 || mutant.baseSourceColumnEnd == 0 >
		<#-- JCov disabled -->
			<#if ! mutant.isSurvived() ><span class="srcCovered"><#else><span class="srcUncovered"></#if>
				<a onmouseover="TagToTip('Tooltip${mutId}', BGCOLOR, <#if ! mutant.isSurvived() >'#90ff90'<#else>'#ff9090'</#if>)" >
					${sourceLine.text?html}
				</a>
			</span>
			<span id="Tooltip${mutId}">
				<b>Name:</b> ${mutant.name!"NoName"}:<br/>
				<b>Transition:</b> ${mutant.transition}<br/>
				<b>Description:</b> ${transitionsDescription.getString(mutant.transition)}<br/>
				<b>KillerTests:</b> <#list mutant.killerTests as test>${test}&nbsp;</#list>
			</span>
		<#-- End JCov disabled -->
		<#else>
		<#-- JCov enabled -->
		<#if mutant.baseSourceLineStart == sourceLine.lineNo>
		<#if mutant.baseSourceLineEnd == sourceLine.lineNo>
			${sourceLine.text?substring(0,mutant.baseSourceColumnStart-1)?html}
			<#if ! mutant.isSurvived() ><span class="srcCovered"><#else><span class="srcUncovered"></#if>
				<a onmouseover="TagToTip('Tooltip${mutId}', BGCOLOR, <#if ! mutant.isSurvived() >'#90ff90'<#else>'#ff9090'</#if>)" >
					${sourceLine.text?substring(mutant.baseSourceColumnStart-1, mutant.baseSourceColumnEnd)?html}
				</a>
			</span>
			${sourceLine.text?substring(mutant.baseSourceColumnEnd)?html}
			<#else>
			${sourceLine.text?substring(0,mutant.baseSourceColumnStart-1)?html}
			<#if ! mutant.isSurvived() ><span class="srcCovered"><#else><span class="srcUncovered"></#if>
				<a onmouseover="TagToTip('Tooltip${mutId}', BGCOLOR, <#if ! mutant.isSurvived() >'#90ff90'<#else>'#ff9090'</#if>)" >
					${sourceLine.text?substring(mutant.baseSourceColumnStart)?html}
				</a>
			</span>
			</#if>
 		<#else>
			<#if mutant.baseSourceLineEnd == sourceLine.lineNo>
			<#if ! mutant.isSurvived() ><span class="srcCovered"><#else><span class="srcUncovered"></#if>
				<a onmouseover="TagToTip('Tooltip${mutId}', BGCOLOR, <#if ! mutant.isSurvived() >'#90ff90'<#else>'#ff9090'</#if>)" >
					${sourceLine.text?substring(0, mutant.baseSourceColumnEnd)?html}
				</a>
			</span>
			${sourceLine.text?substring(mutant.baseSourceColumnEnd)?html}
		  	<#else>
		  	<#if ! mutant.isSurvived() ><span class="srcCovered"><#else><span class="srcUncovered"></#if>
		  		<a onmouseover="TagToTip('Tooltip${mutId}', BGCOLOR, <#if ! mutant.isSurvived() >'#90ff90'<#else>'#ff9090'</#if>)" >
		  			${sourceLine.text?html}
		  		</a>
		  	</span>
		  	</#if>
		  	</#if>
			<span id="Tooltip${mutId}">
				<b>Name:</b> ${mutant.name!"NoName"}:<br/>
				<b>Transition:</b> ${mutant.transition}<br/>
				<b>Description:</b> ${transitionsDescription.getString(mutant.transition)}<br/>
				<b>KillerTests:</b> <#list mutant.killerTests as test>${test}&nbsp;</#list>
			</span>

		<#-- End JCov enabled -->
		</#if>
		</tr>
		</#list>
		<#else>
			<td class="numLine">${sourceLine.lineNo}</td>
			<td></td>
			<td class="src">${sourceLine.text?html}</td>
		</#if>		
	</tr>
	</#list>
</table>