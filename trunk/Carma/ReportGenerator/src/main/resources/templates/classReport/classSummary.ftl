	<h2>Mutation Class Report - ${coverage.fqName}</h2>
		<div class="separator">&nbsp;</div>

		<#assign percKilled = 100 * coverage.coverage >		
		<#assign percSurv = 100 - percKilled >		


		<table class="report">
			<thead>
				<tr>
				  <td  class="titleCell">Classes in this File</td>  
				  <td  class="titleCell" width="20%">Mutation Coverage</td>
				</tr>
			</thead>

			<tr>
				<td><a href="">${coverage.fqName}</a></td>
				<td>
			<table class="bar" cellpadding="0" cellspacing="0" align="right">
				<tr>
					<div class="carmanotcovered" cellpadding="0" cellspacing="0">
						<div class="carmacovered" style="width: ${percKilled}px">
						<span >${percKilled}%</span>
						</div>
					</div>
				</tr>
			</table>
				</td>
			</tr>
		</table>
		
		<p>
		

		<table id="classStatistics">
			<tr>
				<td>Mutants:</td><td>${coverage.numMutants}</td>
				<td>Survivors:</td><td>${coverage.numSurvivors}</td>
				<td>Coverage:</td><td>${percKilled}%</td>
			</tr>
		</table>
		
		<table id="testList">
			<th class="titleCell">Test Names</th>
			<#list clazz.executedTests as test >
			<tr>
				<td class="text">${test}</td>
			</tr>
			</#list>
		</table>

		<table id="mutationList">
			<th class="titleCell">Mutant</th>
			<th class="titleCell">Line No</th>
			<th class="titleCell">Killed</th>
			<th class="titleCell">Killer Tests</th>
			
			<#list clazz.getMutant() as mutant >
				<#assign killed = (! mutant.isSurvived() ) >
			<tr>
				<td class="text">${mutant.name!"NoName"} ${mutant.transition} ${mutant.transitionGroup}</td>
				<td class="text">${mutant.baseSourceLineStart}</td>
				<td class="text"><#if killed>Y<#else>N</#if></td>
				<td class="text">
				<#list mutant.killerTests as test>
					${test}<br/>
				</#list>
				</td>
			</tr>
			</#list>
		</table>
	</p>