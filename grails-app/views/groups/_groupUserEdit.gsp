<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupStatus" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultGroupPrivacy" %>
<%@ page import="org.commonsemantics.grails.groups.utils.DefaultUserStatusInGroup" %>
<%@ page import="org.commonsemantics.grails.groups.model.GroupRole" %>
<%-- 
By Dr. Paolo Ciccarese <paolo.ciccarese@gmail.com>
--%>
<div class="sectioncontainer">
	<g:if test="${usergroup!=null}">
		<table>
			<tr class="prop">
				<td valign="top" class="name">
					<label for="userRole">
						<g:message code="org.commonsemantics.grails.users.model.field.role" default="Role"/>
					</label>
				</td>
				<td valign="top" colspan="2" class="value">
					<div>
						<g:each in="${GroupRole.list()}">
							<g:set var="roleFlag" value="false" />
							<g:each in="${usergroup.roles}" var="userRole">
								<g:if test="${it.label==userRole.label}">
									<g:set var="roleFlag" value="true" />
								</g:if>
							</g:each>
							<g:if test="${roleFlag=='true'}">
								<g:checkBox name="${it.label}" value="${true}" /> ${it.label}
							</g:if>
							<g:else>
								<g:checkBox name="${it.label}" /> ${it.label}
							</g:else>
						</g:each>
					</div>
				</td>
				<td></td>
			</tr>
			<tr class="prop">
				<td valign="top"  class="name">
					<label for="userStatus">
						<g:message code="org.commonsemantics.grails.users.model.field.accountStatus" default="Account Status"/>
					</label>
				</td>
				<td valign="top" class="value" colspan="2">
					<div>
						<g:if test="${usergroup.status.value==DefaultUserStatusInGroup.ACTIVE.value()}">
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.ACTIVE.value()}" checked="${true}"/> Active
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.LOCKED.value()}" checked="${false}"/> Locked 
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.SUSPENDED.value()}" checked="${false}"/> Disabled 
						</g:if>
						<g:elseif test="${usergroup.status.value==DefaultUserStatusInGroup.LOCKED.value()}">
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.ACTIVE.value()}" checked="${false}"/> Active 
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.LOCKED.value()}" checked="${true}"/> Locked 
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.SUSPENDED.value()}" checked="${false}"/> Disabled 
						</g:elseif>
						<g:else>
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.ACTIVE.value()}" checked="${false}"/> Active 
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.LOCKED.value()}" checked="${false}"/> Locked
							<g:radio name="userStatus" value="${DefaultUserStatusInGroup.SUSPENDED.value()}" checked="${true}"/> Disabled
						</g:else>
					</div>	
				</td>
				<td></td>
			</tr>
		</table>
	</g:if>
	<g:else>
		<g:render plugin="cs-commons" template="/lenses/error" model="['message':'CsGroup._groupUserEdit: groupUser object cannot be null.']"/>
	</g:else>
</div>