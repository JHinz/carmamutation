<table class="descriptionTable">
<thead>
<tr>
<td>ID:</td>
<td>Transition ID:</td>
<td>Transition Description:</td>
<td>Defeating Tests:</td>
</tr>
</thead>
<tbody>
<tr>
<td>${snippet.mutant.name!"NoName"}</td>
<td>${snippet.mutant.transition}</td>
<td>${i18nTransition.getString(snippet.mutant.transition)}</td>
<td><#list snippet.mutant.killerTests as test>${test} </#list></td>
</tr>
</tbody>
</table>
