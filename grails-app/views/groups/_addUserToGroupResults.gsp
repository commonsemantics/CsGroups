<div id="results" class="sectioncontainer" style="display:none;">
	<div class="list">
		<table class="tablelist">
			<thead>
				<tr>
					<g:sortableColumn property="username" title="${message(code: 'agentPerson.id.label', default: 'Username')}" />
					<g:sortableColumn property="name" title="${message(code: 'agentPerson.id.label', default: 'Name (Display name)')}" />
					<g:sortableColumn property="isAdmin" title="${message(code: 'agentPerson.id.label', default: 'Adm')}" />
					<g:sortableColumn property="isManager" title="${message(code: 'agentPerson.id.label', default: 'Mgr')}" />
					<g:sortableColumn property="isUser" title="${message(code: 'agentPerson.id.label', default: 'Usr')}" />
					<g:sortableColumn property="createdOn" title="${message(code: 'agentPerson.id.label', default: 'Member Since')}" />
					<g:sortableColumn property="status" title="${message(code: 'agentPerson.id.label', default: 'Status')}" />
				</tr>
			</thead>
			<tbody id="content">
			</tbody>
		</table>
		<div class="paginateButtons">	
		</div>
	</div>
</div>
