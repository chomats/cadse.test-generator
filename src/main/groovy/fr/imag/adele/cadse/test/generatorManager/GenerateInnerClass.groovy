package fr.imag.adele.cadse.test.generatorManager

import groovy.text.GStringTemplateEngine;

class GenerateInnerClass  extends GeneratePart {
	
	String className = 'MyClass'
		String extendedClassName = 'DTextUI<RuningInteractionController>'
			String body = '';
	String template = '''
		static public class ${className} extends ${extendedClassName} {
		
		$body
	}
		'''
		
		
	public String innerPart() {	
		def binding = [
				className : className,
				extendedClassName : extendedClassName,
				body : body]
		GStringTemplateEngine engine = new GStringTemplateEngine()
		def template = engine.createTemplate(template).make(binding)
		return template.toString()
	}
}
