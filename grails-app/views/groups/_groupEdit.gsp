<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupStatus" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy" %>
<%-- 
By Dr. Paolo Ciccarese <paolo.ciccarese@gmail.com>

Parameters list
 1) user | instance of GroupCreateCommand
Stylesheet
 1) fieldError | background and font color in erroneous text fields
--%>
<div class="sectioncontainer">
	<g:if test="${group!=null}">
		<g:hiddenField name="id" value="${group?.id}" /> 
		<table>		
			<tr>
				<td valign="top" >
					<g:if test="${msgError!=null}">
						<g:render plugin="cs-commons" template="/lenses/error" model="['message':msgError]"/>
					</g:if>
					<g:elseif test="${msgWarning!=null}">
						<g:render plugin="cs-commons" template="/lenses/warning" model="['message':msgWarning]"/>
					</g:elseif>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tbody>
							<g:render plugin="cs-groups" template="/groups/groupEntry" model="[
								messageCode:'org.commonsemantics.grails.groups.model.field.name',
								messageDefault:'Name',
								mandatory:GroupsUtils.isGroupFieldRequired(grailsApplication, 'name'),
								variable: 'name',
								value: group?.name,
								caption: '(max 255 ' +  g.message(code: 'org.commonsemantics.grails.general.chars',default:'chars') + ')'
							]" />
						</tbody>
						<tbody>
							<g:render plugin="cs-groups" template="/groups/groupEntry" model="[
								messageCode:'org.commonsemantics.grails.groups.model.field.shortName',
								messageDefault:'Short Name',
								mandatory:GroupsUtils.isGroupFieldRequired(grailsApplication, 'shortName'),
								variable: 'shortName',
								value: group?.shortName,
								caption: '(max 255 ' +  g.message(code: 'org.commonsemantics.grails.general.chars',default:'chars') + ')'
							]" />
						</tbody>
						<tbody>
							<g:render plugin="cs-groups" template="/groups/groupEntry" model="[
								messageCode:'org.commonsemantics.grails.groups.model.field.description',
								messageDefault:'Description',
								mandatory:GroupsUtils.isGroupFieldRequired(grailsApplication, 'description'),
								variable: 'description',
								value: group?.description,
								caption: '(max 255 ' +  g.message(code: 'org.commonsemantics.grails.general.chars',default:'chars') + ')'
							]" />
						</tbody>
						<tr class="prop">
							<td valign="top"  class="name">
								<label for="groupStatus">
									<g:message code="org.commonsemantics.grails.groups.model.field.status" default="Group Status"/>
								</label>
							</td>
							<td valign="top" class="value" colspan="2">
								<div>
									<g:if test="${GroupsUtils.getStatusValue(group)==DefaultGroupStatus.ACTIVE.value()}">
										<g:radio name="groupStatus" value="${DefaultGroupStatus.ACTIVE.value()}" checked="${true}"/> Active
										<g:radio name="groupStatus" value="${DefaultGroupStatus.LOCKED.value()}" checked="${false}"/> Lock 
										<g:radio name="groupStatus" value="${DefaultGroupStatus.DISABLED.value()}" checked="${false}"/> Disable 
									</g:if>
									<g:elseif test="${GroupsUtils.getStatusValue(group)==DefaultGroupStatus.LOCKED.value()}">
										<g:radio name="groupStatus" value="${DefaultGroupStatus.ACTIVE.value()}" checked="${false}"/> Activate 
										<g:radio name="groupStatus" value="${DefaultGroupStatus.LOCKED.value()}" checked="${true}"/> Locked 
										<g:radio name="groupStatus" value="${DefaultGroupStatus.DISABLED.value()}" checked="${false}"/> Disable 
									</g:elseif>
									<g:else>
										<g:radio name="groupStatus" value="${DefaultGroupStatus.ACTIVE.value()}" checked="${false}"/> Activate 
										<g:radio name="groupStatus" value="${DefaultGroupStatus.LOCKED.value()}" checked="${false}"/> Lock 
										<g:radio name="groupStatus" value="${DefaultGroupStatus.DISABLED.value()}" checked="${true}"/> Disabled
									</g:else>
								</div>
							</td>
							<td></td>
						</tr>
						<tr class="prop">
							<td valign="top"  class="name">
								<label for="groupPrivacy">
									<g:message code="org.commonsemantics.grails.groups.model.field.privacy" default="Group Privacy"/>
								</label>
							</td>
							<td valign="top" class="value" colspan="2">
								<div>
									<g:if test="${group.privacy.value==DefaultGroupPrivacy.PRIVATE.value()}">
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PRIVATE.value()}" checked="${true}"/> Private
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.RESTRICTED.value()}" checked="${false}"/> Restricted 
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PUBLIC.value()}" checked="${false}"/> Public 
									</g:if>
									<g:elseif test="${group.privacy.value==DefaultGroupPrivacy.RESTRICTED.value()}">
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PRIVATE.value()}" checked="${false}"/> Private 
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.RESTRICTED.value()}" checked="${true}"/> Restricted 
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PUBLIC.value()}" checked="${false}"/> Public 
									</g:elseif>
									<g:else>
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PRIVATE.value()}" checked="${false}"/> Private 
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.RESTRICTED.value()}" checked="${false}"/> Restricted 
										<g:radio name="groupPrivacy" value="${DefaultGroupPrivacy.PUBLIC.value()}" checked="${true}"/> Public
									</g:else>
								</div>
							</td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</g:if>
	<g:else>
		<g:render plugin="cs-commons" template="/lenses/error" model="['message':'CsGroup._groupEdit: group object cannot be null.']"/>
	</g:else>
</div>