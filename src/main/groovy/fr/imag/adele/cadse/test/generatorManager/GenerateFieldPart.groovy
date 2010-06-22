package fr.imag.adele.cadse.test.generatorManager

class GenerateFieldPart extends GeneratePart {
	
	String uiType = 'CadseGCST.DTEXT';
	String attr = 'CADSE_UICST.IT_A_at_ATTR_';
	String label = 'attr:';
	String pos = 'EPosLabel.left';
	String mcType = 'CadseGCST.MC_INTEGER';
	String mcExtra = null;
	String ic = null;
	String mcClass = null
	String fieldClass = null
	String attachedType ='CADSE_UICST.IT_A';
	
	
	String template = '''
		{
		MC_Descriptor mc = new MC_Descriptor(mcType${mcExtra == null ? '':", $mcExtra"})
		<% if (mcClass != null) {>
		CreatedObjectManager.register(SWTUIPlatform.getPlatform(), mc, ${mcClass}.class);
		<%}>
		UIField field = new UIFieldImpl($uiType, UUID.randomUUID(),
				$attr, "$label", pos, mc, ${ic == null ? 'null': $ic);
		<% if (fieldClass != null) {>
		CreatedObjectManager.register(SWTUIPlatform.getPlatform(), field, ${fieldClass}.class);
		<%}>
		${attachedType}.addField(field);
		
	}
		'''
		
		public String initPart() {	
			def binding = [
					uiType : uiType,
					attr : attr,
					label: label,
					pos : pos,
					mcType: mcType,
					mcExtra : mcExtra,
					ic : ic,                        
					mcClass:mcClass,
					fieldClass : fieldClass,
					attachedType : attachedType]
			def engine = new groovy.text.GStringTemplateEngine()
			def template = engine.createTemplate(template).make(binding)
			return template.toString()
		}
}
