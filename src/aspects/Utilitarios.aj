package aspects;

import janela.Output;

import controlador.ControladorDeArquivo;
import controlador.ControladorDeInstrucao;

public aspect Utilitarios {
	
	String Erro = "";
	boolean finalEncontrado = false;
	int contador = 0;
	
	pointcut limpaString(ControladorDeArquivo CDA): execution(* ControladorDeArquivo.getLine()) && target(CDA);
	pointcut verificaErro(ControladorDeInstrucao CDI): execution(* ControladorDeInstrucao.lerPrograma(java.io.File)) && target(CDI);
	
	String around(ControladorDeArquivo CDA): limpaString(CDA){
		String str = proceed(CDA);
		System.out.println(str);
		str = str.replaceAll(" ", "");
		while(str.contains("#"))
			str = str.substring(0, str.lastIndexOf("#"));
		if(str.equals(""))
			str = "+5100";
		try{
			Integer.parseInt(str);
		}catch(NumberFormatException e){
			Erro += "Invalid caracter at line "+contador+"\n";
		}
		if(str.length()>5)
			Erro += "Too many arguments at line "+contador+"\n";
		if(str.length()<5)
			Erro += "Too few arguments at line "+contador+"\n";
		if(str.contains("+43"))
			finalEncontrado = true;
		contador++;
		return str;
	}
	
	before(ControladorDeInstrucao CDI): verificaErro(CDI){
		contador = 0;
		Erro = "";
		finalEncontrado = false;
	}
	
	@SuppressWarnings("static-access")
	String[] around(ControladorDeInstrucao CDI): verificaErro(CDI){
		String programa[] = proceed(CDI);
		if(!finalEncontrado){
			Erro += "Terminate function is missing\n";
		}
		if(Erro!="")
			Output.addText("Error: \n"+Erro);
		else{
			CDI.setMemoria(programa, contador);
			CDI.setAtivo(true);
		}
		return programa;
	}

}
