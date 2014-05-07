<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupStatus" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy" %>

<div id="request" class="sectioncontainer">
<div class="dialog">
	
<div class="list">
	<g:set var="g" value="${group}"/>
	<table class="tablelist">
		<thead>
			<tr>
				<g:sortableColumn property="name" title="${message(code: 'agentPerson.id.label', default: 'Name')}" />
				<g:sortableColumn property="shortName" title="${message(code: 'agentPerson.id.label', default: 'Short')}" />
				<g:sortableColumn property="description" title="${message(code: 'agentPerson.id.label', default: 'Description')}" />
				<g:sortableColumn property="dateCreated" title="${message(code: 'agentPerson.id.label', default: 'Created on')}" />
				<g:sortableColumn property="lastUpdated" title="${message(code: 'agentPerson.id.label', default: 'Last updated')}" />
				<g:sortableColumn property="status" title="${message(code: 'agentPerson.id.label', default: 'Status')}" />
				<g:sortableColumn property="privacy" title="${message(code: 'agentPerson.id.label', default: 'Privacy')}" />
				<g:sortableColumn property="groupsCount" title="${message(code: 'agentPerson.id.label', default: '#Members')}" />
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<g:if test="${groups.size()==0}">
				<tr>
					<td colspan="8">No Groups have been defined</td>
				</tr>
			</g:if>
			<g:each in="${groups}" status="i" var="group">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		     		<td>
						<g:link action="showGroup" id="${group.id}">
		     				${group.name}
						</g:link>
		     		</td>
		     		<td>
						${group.shortName}
		     		</td>
		     		<td>
						${group.description}
		     		</td>
		     		<td><g:formatDate format="MM/dd/yyyy hh:mm" date="${group.dateCreated}"/></td>
		     		<td><g:formatDate format="MM/dd/yyyy hh:mm" date="${group.lastUpdated}"/></td>
		     		<td>
			     		${GroupsUtils.getStatusLabel(group)}
		     		</td>
		     		<td>
		     			${group.privacy.label}
		     		</td>
		     		<td>
     					<g:link action="listGroupUsers" id="${group.id}">
     						${groupsUsersCount.get(group.id).value}
     					</g:link>

		     		</td>
		     		<td>
			     		<div class="buttons">	
			     			<g:form>						
								<g:hiddenField name="id" value="${group?.id}" /> 
								<g:hiddenField name="redirect" value="listGroups" />
								<span class="button">
									<g:actionSubmit class="edit" action="editGroup" value="${message(code: 'default.button.edit.account.label', default: 'Edit')}" />
								</span>
								<g:if test="${GroupsUtils.getStatusValue(group) == DefaultGroupStatus.LOCKED.value()}">
									<span class="button">
										<g:actionSubmit class="unlock" action="unlockGroup" value="${message(code: 'default.button.unlock.account.label', default: 'Unlock')}" />
									</span>
								</g:if>
								<g:elseif test="${GroupsUtils.getStatusValue(group) != DefaultGroupStatus.LOCKED.value()}">
									<span class="button">
										<g:actionSubmit class="lock" action="lockGroup" value="${message(code: 'default.button.lock.account.label', default: 'Lock')}"
										onclick="return confirm('${message(code: 'default.button.lock.account.confirm.message', default: 'Are you sure you want to lock the group: '+group.shortName+' ?')}');" />
									</span>
								</g:elseif>
								<g:if test="${GroupsUtils.getStatusValue(group) == DefaultGroupStatus.DISABLED.value()}">
									<span class="button">
										<g:actionSubmit class="enable" action="enableGroup" value="${message(code: 'default.button.enable.account.label', default: 'Enable')}" />
									</span>
								</g:if>
								<g:elseif test="${GroupsUtils.getStatusValue(group) != DefaultGroupStatus.DISABLED.value()}">
									<span class="button">
										<g:actionSubmit class="disable" action="disableGroup" value="${message(code: 'default.button.disable.account.label', default: 'Disable')}" 
											onclick="return confirm('${message(code: 'default.button.disable.account.confirm.message', default: 'Are you sure you want to disable the group: '+group.shortName+' ?')}');" />
									</span>
								</g:elseif>
								<%-- 
								<g:if test="${groupsCount[group.id] == 0}">
								<span class="button">
									<g:actionSubmit class="delete" action="deleteGroup" value="${message(code: 'default.button.edit.account.label', default: 'Delete')}"
										onclick="return confirm('${message(code: 'default.button.disable.account.confirm.message', default: 'Are you sure you want to delete the group: '+group.shortName+' ?')}');" />
								</span>
								</g:if>
								--%>
							</g:form>							
						</div>	     		
		     		</td>
		     	</tr>
			</g:each>
		</tbody>
	</table>
	<div class="paginateButtons">
   		<g:paginate total="${groupsTotal}" controller="${controller}" action="${action}"/>
	</div>
</div>
</fieldset>
</div>
</div>