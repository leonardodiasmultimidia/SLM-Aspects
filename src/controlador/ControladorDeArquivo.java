package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControladorDeArquivo {

	private BufferedReader br = null;
	
	public ControladorDeArquivo(){
		
	}
	
	public void abreArquivo(FileReader arquivo){
		br = new BufferedReader(arquivo);
	}
	
	public void fechaArquivo(){
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean EOF(){
		try {
			return br.ready();
		} catch (IOException e) {
			return true;
		}
	}
	
	public String getLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			return "";
		}
	}
}
