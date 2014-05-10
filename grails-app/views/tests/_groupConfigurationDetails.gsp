<%@ page import="org.commonsemantics.grails.groups.model.Group" %>
<%@ page import="org.commonsemantics.grails.groups.utils.GroupsUtils" %>

<h2>Configurations (External configuration)</h2>

<h3>Mandatory Fields </h3>
<table class="tabletest">
	<tr align="left">
		<td>org.commonsemantics.grails.groups.model.fields.mandatory = ${GroupsUtils.getGroupConfigurationMandatoryFields(grailsApplication)}</td>
	</tr>
</table>

<h3>All Resulting Mandatory Fields </h3>
<table class="tabletest">
	<tr align="left">
		<td width="80px">Group:</td><td>${GroupsUtils.getGroupDynamicMandatoryFields(grailsApplication)}</td>
	</tr>
</table>

<h3>Flexible Fields </h3>
<table class="tabletest">
	<tr align="left">
		<g:if test="${GroupsUtils.isGroupStaticPropertyExisting('optional')!=null}">
			<td>${Group.optional}</td>
		</g:if>
		<g:else>
			<td>No optional groups's fields defined</td>
		</g:else>
	</tr>
</table>

<%-- Alternative A --%>
<table class="tabletest">
	<tr align="left">
		<th>Field</th><th>Value</th><th>Configuration entry</th>
	</tr>
	<g:each in="${Group.optional}" var="option">
		<tr>	
			<td>${option}</td>
			<td>${grailsApplication.config.org.commonsemantics.grails.groups.model.field[option]}</td>
			<td> org.commonsemantics.grails.groups.model.field.${option}=${grailsApplication.config.org.commonsemantics.grails.groups.model.field[option]}</td>
		</tr>
	</g:each>
</table>


<%-- Alternative B --%>
<%-- 
<table cellpadding=5>
	<tr align="left">
		<th>Field</th><th>Value</th><th>Configuration entry</th>
	</tr>
	<% User.optional.each { option -> %>
         <tr>
         	<td><%="${option}" %></td>
         	<td><%="${grailsApplication.config.org.commonsemantics.grails.users.model.field[option]}" %></td>
         	<td> org.commonsemantics.grails.users.model.field.${option}</td>
         </tr>
    <%}%>
</table>
--%>