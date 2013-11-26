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
			
			<h2>Group Edit Lens (lang=<%=RequestContextUtils.getLocale(request).language %>)*</h3>
			<p>
			* to change the Locale add ?lang=language to the URL of this page
			</p>
			<br/>
			Group id: ${group.id}
				<g:if test="${group.hasProperty('dateCreated')}">dateCreated=<%=group.dateCreated %></g:if>
				<g:if test="${group.hasProperty('lastUpdated')}">lastUpdated=<%=group.lastUpdated %></g:if>
			<br/>
			<g:form method="post" >
				<div class="csc-lens-container">
					<g:render plugin="cs-groups" template="/groups/groupEdit" />
				</div>
				<br/>
				<tr>
					<td valign="top" colspan="2" >
						<div class="buttons">
							<span class="button">
								<g:actionSubmit class="save" action="testUpdateGroup" value="${message(code: 'org.commonsemantics.grails.users.profile.submit', default: 'Update Group')}" />
							</span>
							<span class="button">
								<g:actionSubmit class="cancel" action="testShowGroup" value="${message(code: 'org.commonsemantics.grails.general.cancel', default: 'Cancel')}" />
							</span>
						</div>
					</td>
				</tr>
			</g:form>
		</div>
	</body>
</html>