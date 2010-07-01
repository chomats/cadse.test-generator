package fr.imag.adele.cadse.test.generatorManager

import fr.imag.adele.cadse.core.CadseGCST;
import fr.imag.adele.cadse.core.impl.ui.GroupOfAttributesDescriptor;

class GenerateGroupPart extends GeneratePart {
	public GenerateGroupPart() {
		addImports 'fr.imag.adele.cadse.core.impl.ui.GroupOfAttributesDescriptor', 
		'fr.imag.adele.cadse.cadseg.pages.PageInit'
	}
	String description = 'Group XXXX';
	String groupVar = 'groupVar';
	String label = 'group XXX';
	String column = '1';
	String hasBox = 'true';
	
	String overrideGroup = null;
	String[] attributeCST = null;
	String attachedType;
	
	String template = '''		{
		// ${gp.description}
		GroupOfAttributesDescriptor ${gp.groupVar} = PageInit.createGroup("${gp.label}", ${gp.column}, ${gp.hasBox},
				${gp.attributeCST.join(', ')});
		${gp.attachedType}.addGroupOfAttributes(${gp.groupVar});
<%if (gp.overrideGroup != null) {%>
		  ${gp.groupVar}.setOverWriteGroup(${gp.overrideGroup});<%} %>
		
		}
		'''
		
	
		
		public String initPart() {	
			def binding = [
					gp:this]
			def engine = new groovy.text.GStringTemplateEngine()
			def template = engine.createTemplate(template).make(binding)
			return template.toString()
		}
}
