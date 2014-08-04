package aspects;


import java.io.File;
import javax.swing.JOptionPane;
import janela.Editor;
import janela.Output;

public aspect Validacoes {
	
	String erroArquivo = "";
	
	/*
	 * Pointcuts
	 */
	pointcut validacoesMenu(Editor e, Object obj): execution (* Editor.menuFileAction(Object)) && target(e) && args(obj);
	pointcut validacaoSaidaOutput(Output out, Object obj): execution (* Output.outputAction(Object)) && target(out) && args(obj);
	
	/*
	 * validacaoSaidaOutput
	 */
	
	void around(Output out, Object obj): validacaoSaidaOutput(out,obj){
    	if(obj==out.getTxtInput() || obj==out.getBtEnviar()){
    		try{
    			Integer.parseInt(out.getTxtInput().getText().replace(" ", ""));
        		proceed(out,obj);
    		}catch(NumberFormatException e){
    			String lastLine = out.getLastLine();
    			Output.addText("Invalid entry!");
    			Output.addText(lastLine);
    		}
    	}
	}
	
	/*
	 * validacoesMenu
	 */
	
	void around(Editor e, Object obj): validacoesMenu(e,obj){
		
		if(obj == e.getJMINewFile()){
			if(e.getJTextAreaEditor().getText().length()>0){
				if(JOptionPane.showConfirmDialog(null, "Save current work?")==0)
					e.menuFileAction(e.getJMISave());
			}
			proceed(e,obj);
		}
		
		else if(obj == e.getJMIOpen()){
			proceed(e,obj);
				if(new File(e.getPath()).exists()){
					if(e.getPath().lastIndexOf("/")!=-1)
						e.setTitle("Simpleton Editor - "+e.getPath().substring(e.getPath().lastIndexOf("/")+1, e.getPath().length()));
					else
						e.setTitle("Simpleton Editor - "+e.getPath().substring(e.getPath().lastIndexOf("\\")+1, e.getPath().length()));
				}
				else {
					if(JOptionPane.showConfirmDialog(null, "This file doesn't exists. Do you want create him now?")==0){
						e.getJTextAreaEditor().setText("");
						e.menuFileAction(e.getJMISave());
					}
					else
						e.menuFileAction(e.getJMIOpen());
				}
		}
		
		else if(obj == e.getJMISave()){
			if(!e.getPath().equals("")){
				if(e.getPath().lastIndexOf(".SLM")!=e.getPath().length()-4){
					e.setPath(e.getPath()+".SLM");
				}
				proceed(e,obj);
				if(e.getPath().lastIndexOf("/")!=-1)	
					e.setTitle("Simpleton Editor - "+e.getPath().substring(e.getPath().lastIndexOf("/")+1, e.getPath().length()));
				else
					e.setTitle("Simpleton Editor - "+e.getPath().substring(e.getPath().lastIndexOf("\\")+1, e.getPath().length()));
			}
			else
				e.menuFileAction(e.getJMISaveAs());
		}
		
		else if(obj == e.getJMISaveAs()){
			proceed(e,obj);
		}
		
		else if(obj == e.getJMIExit()){
			if(e.getJTextAreaEditor().getText().length()>0){
				if(JOptionPane.showConfirmDialog(null, "Save current work?")==0)
					e.menuFileAction(e.getJMISave());
			}
			proceed(e,obj);
		}
		
		else if(obj == e.getJMIRun()){
			if(!e.getJTextAreaEditor().getText().equals(""))
				e.menuFileAction(e.getJMISave());
			else
				e.menuFileAction(e.getJMIOpen());
			if(!e.getPath().equals("")){
				proceed(e,obj);
				if(e.getPath().lastIndexOf("/")!=-1)
					e.getOutput().setWindowTitle(e.getPath().substring(e.getPath().lastIndexOf("/")+1));
				else
					e.getOutput().setWindowTitle(e.getPath().substring(e.getPath().lastIndexOf("\\")+1));
			}
		}
		
		else if(obj == e.getJMIGuide()){
			proceed(e,obj);
		}
	}
}
