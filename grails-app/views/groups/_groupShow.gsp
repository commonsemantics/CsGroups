<%@ page import="org.commonsemantics.grails.users.utils.UsersUtils" %>

<%-- 
By Dr. Paolo Ciccarese <paolo.ciccarese@gmail.com>

Parameters list
 1) user | instance of GroupCreateCommand
Stylesheet
 1) fieldError | background and font color in erroneous text fields
--%>
<div class="sectioncontainer">
	<g:if test="${group!=null}">
		<table>
			<tbody>
				<%-- 
				<tr>
					<td valign="top" colspan="2"  style="horizontal-align:center;">
						<img src="${resource(dir:'images/dashboard',file:'no_image.gif',plugin:'users-module')}" width="200px" />
					</td>
				</tr>
				--%>
				
				<tr>
					<td valign="top" width="150px"  align="left">
						<g:message code="org.commonsemantics.grails.groups.model.field.name" default="Name"/>
					</td>
					<td valign="top" width="265px" align="left">
						<g:if test="${group.name}">${group.name}</g:if>
						<g:else>-</g:else>
					</td>
				</tr>
				<g:if test="${grailsApplication.config.org.commonsemantics.grails.persons.model.field.shortName!='hide'}">
					<tr>
						<td valign="top" width="150px"  align="left">
							<g:message code="org.commonsemantics.grails.groups.model.field.shortName" default="Short Name"/>
						</td>
						<td valign="top" width="265px" align="left">
							<g:if test="${group.shortName}">${group.shortName}</g:if>
							<g:else>-</g:else>
						</td>
					</tr>
				</g:if>
				<g:if test="${grailsApplication.config.org.commonsemantics.grails.persons.model.field.description!='hide'}">
					<tr>
						<td valign="top" width="150px"  align="left">
							<g:message code="org.commonsemantics.grails.groups.model.field.description" default="Description"/>
						</td>
						<td valign="top" width="265px" align="left">
							<g:if test="${group.description}">${group.description}</g:if>
							<g:else>-</g:else>
						</td>
					</tr>
				</g:if>
				<tr>
					<td valign="top" width="150px"  align="left">
						<g:message code="org.commonsemantics.grails.groups.model.field.createdBy" default="Created By"/>
					</td>
					<td valign="top" width="265px" align="left">
						<g:if test="${group.createdBy}"><g:link action="testShowUser" id="${group.createdBy.id}">${group.createdBy.username}</g:link></g:if>
						<g:else>-</g:else>
					</td>
				</tr>
				<tr>
					<td valign="top" width="150px"  align="left">
						<g:message code="org.commonsemantics.grails.groups.model.field.membersCounter" default="Members Counter"/>
					</td>
					<td valign="top" width="265px" align="left">
						<g:if test="${group.membersCounter}">${group.membersCounter}</g:if>
						<g:else>0</g:else>
					</td>
				</tr>
			</tbody>
		</table>
	</g:if>
	<g:else>
		<g:render plugin="cs-commons" template="/lenses/error" model="['message':'CsGroups._groupShow: group object cannot be null.']"/>
	</g:else>
</div>


