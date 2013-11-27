<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="commonsemantics-tests"/>
		<title>${grailsApplication.metadata['app.name']}.${label}</title>
	</head>
	<body>
		<div class="csc-main">
			<h1>${grailsApplication.metadata['app.name']}.${label} ${description}</h1>
					
			<g:render plugin="cs-users" template="/tests/userConfigurationDetails" />
			
			<h3>Group Display Lens (lang=<%=RequestContextUtils.getLocale(request).language %>)*</h3>
			<p>
			* to change the Locale add ?lang=language to the URL of this page
			</p>
			<br/>
			Group id: ${group.id}
				<g:if test="${group.hasProperty('dateCreated')}">dateCreated=<%=group.dateCreated %></g:if>
				<g:if test="${group.hasProperty('lastUpdated')}">lastUpdated=<%=group.lastUpdated %></g:if>
			<br/>
			<g:form method="post" >
				<g:hiddenField name="testId" value="${label}" /> 
				<g:hiddenField name="testDescription" value="After ${description}" /> 
			
				<div class="csc-lens-container">
					<g:hiddenField name="id" value="${group.id}" /> 
					<g:render plugin="cs-groups" template="/groups/groupShow" />
				</div>
				<br/>
				<div class="buttons">
					<span class="button">
						<g:actionSubmit class="save" action="editGroup" value="${message(code: 'org.commonsemantics.grails.users.profile.submit', default: 'Edit Group')}" />
					</span>
					<span class="button">
						<g:actionSubmit class="save" action="listGroups" value="${message(code: 'org.commonsemantics.grails.users.profile.submit', default: 'List Groups')}" />
					</span>
				</div>
			</g:form>
		</div>
	</body>
</html>