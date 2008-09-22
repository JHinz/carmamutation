<div class="descriptionTable">
<table>
<tr><td>ID: ${snippet.mutant.name!"NoName"}</td></tr>
<tr><td>Transition: ${snippet.mutant.transition}</td></tr>
<tr><td>Transition: ${i18nTransition.getString(snippet.mutant.transition)}</td></tr>
<tr><td>Defeating Tests: <#list snippet.mutant.killerTests as test>${test},</#list></td></tr>
</table>
</div>
