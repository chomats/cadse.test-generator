package fr.imag.adele.cadse.test.generatorManager

import groovy.text.GStringTemplateEngine;

class GeneratePage  extends GeneratePart {
	public GeneratePage() {
		addImports 'java.util.UUID'
	}
	
	String cst = null;
	String pageVar ='pageVar';
	String name = 'Hidden attributes'
	String label= 'Hidden attributes'
	String title= 'Hidden attributes'
	String description= 'Hidden attributes'
	def hiddenAttributes = []
	def attributes = []
	def readOnlyAttributes = []    
	def overridePage = []
	String typeAttached = '';
	
	String template ='''\
	{
	// Page ${title}
	IPage ${pageVar} = new PageImpl(UUID.randomUUID(), 
			"${name}", "${label}",
			"${title}", "${description}", false, null);
<% if (hiddenAttributes.size() != 0) {%>
		${pageVar}.addHiddenAttributes(${hiddenAttributes.join(', ')}); <% } %>
<% if (attributes.size() != 0) {%>
		${pageVar}.addLast(${attributes.join(', ')}); <% } %>
<% if (readOnlyAttributes.size() != 0) {%>
		${pageVar}.addReadOnlyAttributes(${readOnlyAttributes.join(', ')}); <% } %>
<% if (overridePage.size() != 0) {%>
		${pageVar}.addOverridePage(${overridePage.join(', ')}); <% } %>
		
	${typeAttached}.addModificationPages(${pageVar});
	${typeAttached}.addCreationPages(${pageVar});
	
	<% if (cst != null) { %>
		${cst} = ${pageVar};
	<% } %>
		
		}
'''
		
	public void addAttribute(String attrCst) {
		attributes.add(attrCst);
	}
	
	public void addOverridePage(String attrCst) {
		overridePage.add(attrCst);
	}
	
	public void addReadOnlyAttributes(String attrCst) {
		readOnlyAttributes.add(attrCst);
	}
	
	public void addHiddenAttribute(String attrCst) {
		hiddenAttributes.add(attrCst);
	}
	
	public String initPart() {	
		def binding = [
				cst : cst,
				pageVar : pageVar,
				name : name,
				label: label,
				title: title,
				description : description,
				hiddenAttributes : hiddenAttributes,                        
				typeAttached:typeAttached,
				readOnlyAttributes:readOnlyAttributes,
				overridePage:overridePage,
				attributes:attributes
				]
		
		GStringTemplateEngine engine = new GStringTemplateEngine()
		
		def template = engine.createTemplate(template).make(binding)
		return template.toString()
	}
}
