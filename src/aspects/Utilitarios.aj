package aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import janela.Output;
import controlador.ControladorDeArquivo;
import controlador.ControladorDeInstrucao;

public aspect Utilitarios {
	
	String Erro = "";
	boolean finalEncontrado = false;
	int contador = 0;
	
	pointcut limpaString(ControladorDeArquivo CDA): execution(* ControladorDeArquivo.getLine()) && target(CDA);
	pointcut verificaErro(ControladorDeInstrucao CDI): execution(* ControladorDeInstrucao.lerPrograma(java.io.File)) && target(CDI);
	pointcut exibeMemoria(ControladorDeInstrucao CDI, File arquivo): execution(* ControladorDeInstrucao.executaPrograma(File)) && target(CDI) && args(arquivo);
	
	String around(ControladorDeArquivo CDA): limpaString(CDA){
		String str = proceed(CDA);
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
	
	after(ControladorDeInstrucao CDI, File arquivo): exibeMemoria(CDI,arquivo){
		int cont = 0;
		int memoria[] = CDI.getMemoria();
		String path = arquivo.getAbsoluteFile().toString().substring(0, arquivo.getAbsoluteFile().toString().lastIndexOf(".")+1)+"csv";
		BufferedWriter bWriter;
		try{
			bWriter = new BufferedWriter(new FileWriter(path));
			bWriter.write(";0;1;2;3;4;5;6;7;8;9");
			Output.addText("|     |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  ");
			for(; cont < memoria.length;cont++){
				if(cont%10==0){
					Output.addText("|\n|  "+cont/10+"  ");
					bWriter.write("\n"+cont/10);
				}
				if(memoria[cont]>=0){
					Output.addText("|+");
					bWriter.write(";+");
					if(memoria[cont]<1000){
						Output.addText("0");
						bWriter.write("0");
					}
					if(memoria[cont]<100){
						Output.addText("0");
						bWriter.write("0");
					}
					if(memoria[cont]<10){
						Output.addText("0");
						bWriter.write("0");
					}
					Output.addText(""+memoria[cont]);
					bWriter.write(""+memoria[cont]);
				}
				else{
					Output.addText("|-");
					bWriter.write(";-");
					if(memoria[cont]*(-1)<1000){
						Output.addText("0");
						bWriter.write("0");
					}
					if(memoria[cont]*(-1)<100){
						Output.addText("0");
						bWriter.write("0");
					}
					if(memoria[cont]*(-1)<10){
						Output.addText("0");
						bWriter.write("0");
					}
					Output.addText(""+memoria[cont]*(-1));
					bWriter.write(""+memoria[cont]*(-1));
				}
			}
			Output.addText("|\n");
			bWriter.close();
		}
		catch(IOException e){
			
		}
	}

}
