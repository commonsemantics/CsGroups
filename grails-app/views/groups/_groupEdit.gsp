<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>
<%-- 
By Dr. Paolo Ciccarese <paolo.ciccarese@gmail.com>

Parameters list
 1) user | instance of GroupCreateCommand
Stylesheet
 1) fieldError | background and font color in erroneous text fields
--%>
<div class="sectioncontainer">
	<g:if test="${group!=null}">
		<g:hiddenField name="id" value="${user?.id}" /> 
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
					</table>
				</td>
			</tr>
		</table>
	</g:if>
	<g:else>
		<g:render plugin="cs-commons" template="/lenses/error" model="['message':'CsGroup._groupEdit: group object cannot be null.']"/>
	</g:else>
</div>