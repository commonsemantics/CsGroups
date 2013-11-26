<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupStatus" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy" %>

<table>
	<tbody>
		<g:if test="${grailsApplication.config.org.commonsemantics.grails.groups.model.field.name!='hide'}">			
			<g:render plugin="cs-agents" template="/agents/agentPropertyEntry" model="[
				messageCode:'org.commonsemantics.grails.groups.model.field.name',
				messageDefault:'Name',
				mandatory:GroupsUtils.isGroupFieldRequired(grailsApplication, 'name'),
				bean: group,
				variable: 'name',
				value: group?.name,
				caption: '(max 255 ' +  g.message(code: 'org.commonsemantics.grails.general.chars',default:'chars') + ')'
			]" />
		</g:if>
		<g:if test="${grailsApplication.config.org.commonsemantics.grails.groups.model.field.shortName!='hide'}">			
			<g:render plugin="cs-agents" template="/agents/agentPropertyEntry" model="[
				messageCode:'org.commonsemantics.grails.groups.model.field.shortName',
				messageDefault:'Short name',
				mandatory:GroupsUtils.isGroupFieldRequired(grailsApplication, 'shortName'),
				bean: group,
				variable: 'shortName',
				value: group?.shortName,
				caption: '(max 255 ' +  g.message(code: 'org.commonsemantics.grails.general.chars',default:'chars') + ')'
			]" />
		</g:if>
		<g:if test="${grailsApplication.config.org.commonsemantics.grails.groups.model.field.description!='hide'}">			
			<g:render plugin="cs-agents" template="/agents/agentPropertyEntry" model="[
				messageCode:'org.commonsemantics.grails.groups.model.field.description',
				messageDefault:'Description',
				mandatory:GroupsUtils.isGroupFieldRequired(grailsApplication, 'description'),
				bean: group,
				variable: 'description',
				value: group?.description,
				caption: '(max 255 ' +  g.message(code: 'org.commonsemantics.grails.general.chars',default:'chars') + ')'
			]" />
		</g:if>
		<tr class="prop">
			<td valign="top"  class="name">
				<label for="groupStatus">
					<g:message code="org.commonsemantics.grails.groups.model.field.accountStatus" default="Group Status"/>
				</label>
			</td>
			<td valign="top" class="value" colspan="2">
				<div>
					<g:radio name="groupStatus" value="${DefaultGroupStatus.ACTIVE.value()}" checked="${true}"/> Active 
					<g:radio name="groupStatus" value="${DefaultGroupStatus.LOCKED.value()}" checked="${false}"/> Locked 
					<g:radio name="groupStatus" value="${DefaultGroupStatus.DISABLED.value()}" checked="${false}"/> Disabled 
				</div>
			</td>
			<td></td>
		</tr>
		<tr class="prop">
			<td valign="top"  class="name">
				<label for="groupPrivacy">
					<g:message code="org.commonsemantics.grails.groups.model.field.accountPrivacy" default="Group Privacy"/>
				</label>
			</td>
			<td valign="top" class="value" colspan="2">
				<div>
					<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PRIVATE.value()}" checked="${true}"/> Private 
					<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.RESTRICTED.value()}" checked="${false}"/> Restricted 
					<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PUBLIC.value()}" checked="${false}"/> Public 
				</div>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>

