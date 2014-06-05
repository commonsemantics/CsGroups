<%@ page import="org.commonsemantics.grails.users.model.User" %>
<%@ page import="org.commonsemantics.grails.users.model.UserRole" %>
<%@ page import="org.commonsemantics.grails.users.utils.DefaultUsersRoles" %>
<%@ page import="org.commonsemantics.grails.users.utils.UsersUtils" %>
<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>

<div id="request" class="sectioncontainer">

<div class="dialog">
	
	<div class="list">
	<table class="tablelist">
		<thead>
			<tr>
				<g:sortableColumn property="username" title="${message(code: 'agentPerson.id.label', default: 'Username')}" />
				<g:sortableColumn property="name" title="${message(code: 'agentPerson.id.label', default: 'Name')}" />
				<g:sortableColumn property="roles" title="${message(code: 'agentPerson.id.label', default: 'Roles')}" />
				<g:sortableColumn property="status" title="${message(code: 'agentPerson.id.label', default: 'Membership Status')}" />
				<g:sortableColumn property="dateCreated" title="${message(code: 'agentPerson.id.label', default: 'Member Since')}" />
				<g:sortableColumn property="lastUpdated" title="${message(code: 'agentPerson.id.label', default: 'Last updated')}" />
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${userGroups}" status="i" var="userGroup">
				<g:set var="user" value="${userGroup.user}"/>
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		     		<td><g:link action="showUser" id="${user.id}">${user.username}</g:link></td>
		     		<td>${user.person.lastName} ${user.person.firstName} <g:if test="${user?.person.displayName?.length()>0}">(${user.person.displayName})</g:if></td>

					<g:set var="userObject" value="${User.findByUsername(user.username)}"/>
					<g:set var="userRole" value="${UserRole.findAllByUser(userObject)}"/>
						
		     		<td>
		     			${userGroup.roles.label}
				    </td>
				    <td>
				    	${userGroup.status.label}
				    </td>

		     		<td><g:formatDate format="MM/dd/yyyy hh:mm" date="${userGroup.dateCreated}"/></td>
		     		<td><g:formatDate format="MM/dd/yyyy hh:mm" date="${userGroup.lastUpdated}"/></td>
		     		<td>
		     			<div class="buttons">
							<g:form>
								<g:hiddenField name="id" value="${user?.id}" /> 
								<g:hiddenField name="group" value="${userGroup?.group?.id}" /> 
								<g:hiddenField name="redirect" value="listUsers" />
								<span class="button">
									<g:actionSubmit class="edit"  action="editUserInGroup" value="${message(code: 'default.button.edit.account.label', default: 'Edit')}" />
								</span>
								<span class="button">
									<g:actionSubmit class="edit"  action="unenrollUserFromGroup" value="${message(code: 'default.button.edit.account.label', default: 'Unenroll')}" />
								</span>
								<g:if test="${GroupsUtils.isUserInGroupLocked(userGroup)}">
									<span class="button">
										<g:actionSubmit class="unlock" action="unlockUserInGroup" value="${message(code: 'default.button.unlock.account.label', default: 'Unlock')}" />
									</span>
								</g:if>
								<g:elseif test="${!GroupsUtils.isUserInGroupLocked(userGroup)}">
									<span class="button">
										<g:actionSubmit class="lock" action="lockUserInGroup" value="${message(code: 'default.button.lock.account.label', default: 'Lock')}"
										onclick="return confirm('${message(code: 'default.button.lock.account.confirm.message', default: 'Are you sure you want to lock this account?')}');" />
									</span>
								</g:elseif>
								<g:if test="${GroupsUtils.isUserInGroupEnabled(userGroup)}">
									<span class="button">
										<g:actionSubmit class="disable" action="disableUserInGroup" value="${message(code: 'default.button.disable.account.label', default: 'Disable')}" 
										onclick="return confirm('${message(code: 'default.button.disable.account.confirm.message', default: 'Are you sure you want to disable this user for this group?')}');"/>
									</span>
								</g:if>
								<g:elseif test="${!GroupsUtils.isUserInGroupEnabled(userGroup)}">
									<span class="button">
										<g:actionSubmit class="enable" action="enableUserInGroup" value="${message(code: 'default.button.enable.account.label', default: 'Enable')}" />
									</span>
								</g:elseif>
							</g:form>
						</div>
		     		</td>
		     	</tr>
			</g:each>
		</tbody>
	</table>
	<div class="paginateButtons">
   		<g:paginate total="${usersTotal}" controller="${controller}" action="${action}"/> 
	</div>
</div>
<div class="buttons">
	<span class="button">
		<g:link class="add" controller="dashboard" action="enrollUsersInGroup" id="${group.id}" style="text-decoration: none;">Enroll More Users</g:link>
	</span>
</div>
</div>
</div>