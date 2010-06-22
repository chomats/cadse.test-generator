package fr.imag.adele.cadse.test.generatorManager

class GeneratePart {
	def imports = [];
	def importsMF = [];
	
	
	public void addImports(String ...imports) {
		this.imports.addAll(Arrays.asList (imports));
	}
	
}
