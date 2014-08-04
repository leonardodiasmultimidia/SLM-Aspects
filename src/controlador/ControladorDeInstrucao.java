package controlador;


import janela.Editor;
import janela.Output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ControladorDeInstrucao implements Runnable{

	/*
	 * Basicos
	 */
	
	private int acumulador = 0;
	private int contadorInstrucao = 0;
	private int registroInstrucao = 0;
	private int codigoOperacao = 0;
	private int operando = 0;
	private int memoria[] = new int[100];
	
	/*
	 * Outros
	 */
	
	private static boolean Ativo = false;
	public static int ValorExterno = -99999;
	private File pathFile;
	
	/*
	 * Construtor
	 */
	
	public ControladorDeInstrucao(File path){
		this.pathFile = path; 
	}
	
	/**
	 * GETTERS E SETTERS
	 */
	public static void setAtivo(boolean bol){
		Ativo = bol;
	}
	
	public static boolean getAtivo(){
		return Ativo;
	}
	
	public void setMemoria(String programa[], int numMax){
		for(int cont = 0; cont < numMax ; cont++)
			memoria[cont] = Integer.parseInt(programa[cont]);		
	}
	
	/**
	 * DEMAIS MÉTODOS
	 */
	
	private String[] lerPrograma(java.io.File Arquivo) throws IOException, FileNotFoundException{
			String[] programa = new String[100];		
			ControladorDeArquivo CDA = new ControladorDeArquivo();
			CDA.abreArquivo(new FileReader(Arquivo));
			int contador = 0;
			while(CDA.EOF()){
				programa[contador++] = CDA.getLine();
			}
			CDA.fechaArquivo();
			return programa;
	}
	
	public void executaPrograma(java.io.File Arquivo) throws FileNotFoundException, IOException{
		while(Ativo){
			proximaInstrucao();
			executaInstrucao();
		}
	}
	
	private void proximaInstrucao(){
		registroInstrucao = memoria[contadorInstrucao];
		codigoOperacao = registroInstrucao/100;
		operando = registroInstrucao%100;
		contadorInstrucao++;
	}
	
	/**
	 * OPERAÇÕES DE ENTRADA E prompt
	 */
	
	private void READ(){
		if(operando < 10)
			Output.addText("0"+operando+" <- ");
		else
			Output.addText(""+operando+" <- ");
			try{
				Editor.aguardarEntrada();
				ValorExterno = Integer.parseInt(Output.getBuffer());
				Output.addText(ValorExterno+"\n");
			}catch(NumberFormatException e){
				memoria[operando] = 0;
				System.out.println("Excessão");
			}
			memoria[operando] = ValorExterno;
	}
	
	private void WRITE(){
		if(operando < 10)
			Output.addText("\n0"+operando+" -> ");
		else
			Output.addText("\n"+operando+" -> ");
		Output.addText(""+memoria[operando]+"\n");
	}
	
	
	/**
	 * OPERAÇÕES DE CARREGAMENTO/ARMAZENAMENTO
	 */
	
	private void LOAD(){
		acumulador = memoria[operando];
	}
	
	private void STORE(){
		memoria[operando] = acumulador;
	}
	
	/**
	 * OPERAÇÕES ARITMÉTICAS
	 */
	
	private void ADD(){
		acumulador += memoria[operando];
	}
	
	private void SUBTRACT(){
		acumulador -= memoria[operando];
	}
	
	private void DIVIDE(){
		acumulador = memoria[operando]/acumulador;
	}
	
	private void MULTIPLY(){
		acumulador *= memoria[operando];
	}
	
	/**
	 * OPERAÇÕES DE TRANSFERÊNCIA DE CONTROLE
	 */
	
	private void BRANCH(){
		contadorInstrucao = operando;
	}
	
	private void BRANCHNEG(){
		if(acumulador<0)
			contadorInstrucao = operando;
	}
	
	private void BRANCHZERO(){
		if(acumulador==0)
			contadorInstrucao = operando;
		
	}
	private void HALT(){
		Ativo = false;
		exibirMemoria();
	}
	
	private void executaInstrucao(){
		switch(codigoOperacao){
			case 10: //READ
				READ();
			break;
			
			case 11: //WRITE
				WRITE();
			break;
			
			case 20: //LOAD
				LOAD();
			break;
			
			case 21: //STORE
				STORE();
			break;
			
			case 30: //ADD
				ADD();
			break;
			
			case 31: //SUBTRACT
				SUBTRACT();
			break;
			
			case 32: //DIVIDE
				DIVIDE();
			break;
			
			case 33: //MULTIPLY
				MULTIPLY();
			break;
			
			case 40: //BRANCH
				BRANCH();
			break;
			
			case 41: //BRANCHNEG
				BRANCHNEG();
			break;
			
			case 42: //BRANCHZERO 
				BRANCHZERO();
			break;
			
			case 43: //HALT
				HALT();
			break;
		}
	}
	
	private void exibirMemoria(){
		int cont = 0;
		Output.addText("|     |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  ");
		for(; cont < memoria.length;cont++){
			if(cont%10==0)
				Output.addText("|\n|  "+cont/10+"  ");
			if(memoria[cont]>=0){
				Output.addText("|+");
				if(memoria[cont]<1000)
					Output.addText("0");
				if(memoria[cont]<100)
					Output.addText("0");
				if(memoria[cont]<10)
					Output.addText("0");
				Output.addText(""+memoria[cont]);
			}
			else{
				Output.addText("|-");
				if(memoria[cont]*(-1)<1000)
					Output.addText("0");
				if(memoria[cont]*(-1)<100)
					Output.addText("0");
				if(memoria[cont]*(-1)<10)
					Output.addText("0");
				Output.addText(""+memoria[cont]*(-1));
			}
		}
		Output.addText("|\n");
	}

	@Override
	public void run() {
		try {
			lerPrograma(pathFile);
			executaPrograma(pathFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
