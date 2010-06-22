package fr.imag.adele.cadse.test.generatorManager

import java.util.Arrays;

import groovy.text.GStringTemplateEngine;

class GenerateManager {
	public void addImports(String ...imports) {
		this.imports.addAll(Arrays.asList (imports));
	}
	
	String cadsePackageName = 'model.CADSE_UI';
	def imports = [ ];
	
	def initParts = [];
	def innerParts = [];
	
	String className = 'It_B';
	String extendsPart = ' extends It_AManager'
	String implmentsPart = ' implements InitAction'
	String commonMethods = 
'''
	/**
		@generated
	*/
	@Override
	public String computeQualifiedName(Item item, String name, Item parent, LinkType lt) {
		StringBuilder sb = new StringBuilder();
		try {
			Object value;
			Item currentItem;
			if (sb.length() != 0) {
				sb.append(".");
			}
			sb.append(name);
			return sb.toString();
		} catch (Throwable e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
		@generated
	*/
	@Override
	public String getDisplayName(Item item) {
		try {
			Object value;
			if (item != null) {
				return item.getName();
			}
			return "";
		} catch (Throwable e) {
			e.printStackTrace();
			return "error";
		}
	}
'''
	String managerTemplate ='''\
package ${cadsePackageName}.managers;

<% for (i in imports) {%>
import ${i}; <%}%>
		
	/**
	    @generated
	*/
	public class ${className}Manager ${extendsPart} ${implmentsPart} {

		/**
		    @generated
		*/
		public ${className}Manager() {
			super();
		}

${commonMethods}

<% for (i in innerParts) {%>
${i.innerPart()} <%}%>

		@Override
		public void init() {
			<% for (i in initParts) {%>
			${i.initPart()} <%}%>
		}

	}
		'''
		
	public void addInnerPart(GeneratePart gi) {
		innerParts.add(gi);
	}
	
	public void addInitPart(GeneratePart gp) {
		initParts.add(gp);
	}
		
	public String classPart() {	
		Set<String> importsLoc = new HashSet<String>(imports);
		
		for(gp in initParts)
			importsLoc.addAll(gp.imports);
		
		for(gp in innerParts)
			importsLoc.addAll(gp.imports);
		
		def binding = [
				cadsePackageName : cadsePackageName,
				imports : importsLoc,
				className : className,
				extendsPart : extendsPart,
				implmentsPart : implmentsPart,
				commonMethods :commonMethods,
				initParts : initParts,
				innerParts:innerParts] 
				
		GStringTemplateEngine engine = new GStringTemplateEngine()
				
		def template = engine.createTemplate(managerTemplate).make(binding)
		return template.toString()
	}	
}
