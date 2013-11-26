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
			
			<h2>Group Create Lens (lang=<%=RequestContextUtils.getLocale(request).language %>)*</h3>
			<p>
			* to change the Locale add ?lang=language to the URL of this page
			</p>
			<br/>
			
			<g:form method="post" >
				<g:hiddenField name="testId" value="${label}" /> 
				<g:hiddenField name="testDescription" value="After ${description}" /> 
				
				<div class="csc-lens-container">
					<g:render plugin="cs-groups" template="/groups/groupCreate" />
				</div>
				
				<br/>
				<div class="buttons">
					<span class="button">
						<g:actionSubmit class="save" action="testSaveGroup" value="${message(code: 'org.commonsemantics.grails.users.profile.create', default: 'Create Group')}" />
					</span>
				</div>
			</g:form>
		</div>
	</body>
</html>