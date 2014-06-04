<%-- by Paolo Ciccarese --%>
<%-- 
Parameters list
 1) item | instance of GroupCreateCommand
Stylesheet
 1) fieldError | background and font color in erroneous text fields
--%>
<div class="sectioncontainer">
	<div class="dialog" >
		<fieldset>
			<legend><span id="groupsTitle">Loading Groups</span> <img id="groupsSpinner" src="${resource(dir:'images',file:'spinner.gif',plugin:'users-module')}" /></legend>
			<div id="userGroupsComponent">
				<div class="list tablescroll">
					<table id="groupsTable" style="border: 1px solid #ddd;">
						<thead>
							<tr>
								<th style="width: 200px">${message(code: 'agentPerson.id.label', default: 'Group Name')}</th>
								<th style="width: 200px">${message(code: 'agentPerson.id.label', default: 'Member Since')}</th>
								<th>${message(code: 'agentPerson.id.label', default: 'User Role')}</th>
								<th>${message(code: 'agentPerson.id.label', default: 'Membership')}</th>
							</tr>
						</thead>
						<tbody id="groupsContent">
						</tbody>
					</table>
					<div class="paginateButtons">
				   		<g:paginate total="1" />
					</div>
				</div>
				<div class="buttons">
					<span class="button">
						<g:link class="edit" controller="dashboard" action="manageUserGroups"  id="${user.id}" style="text-decoration: none;">Manage Groups</g:link>
					</span>
					&nbsp;
					<span class="button">
						<g:link class="add" controller="dashboard" action="addUserGroups" id="${user.id}" style="text-decoration: none;">Enroll in Groups</g:link>
					</span>
				</div>
			</div>
		</fieldset> 
	</div>
</div>
