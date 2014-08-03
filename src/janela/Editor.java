package janela;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controlador.ControladorDeInstrucao;
import numeredBorder.NumeredBorder;

/**
 *
 * @author Leo Dias
 */
public class Editor extends javax.swing.JFrame {

	private static final long serialVersionUID = 8353895690936684064L;

	/**
	 * CONSTRUTORES
	 */
	
    public Editor() {

    }

    /**
     * MÉTODOS PRINCIPAIS
     */
    
    private void initComponents() {
    	
        jScrollPane1 = new javax.swing.JScrollPane();
        JTextAreaEditor = new javax.swing.JTextArea();
        JMBar = new javax.swing.JMenuBar();
        JMFile = new javax.swing.JMenu();
        JMINewFile = new javax.swing.JMenuItem();
        JMISave = new javax.swing.JMenuItem();
        JMISaveAs = new javax.swing.JMenuItem();
        JMIOpen = new javax.swing.JMenuItem();
        JMIExit = new javax.swing.JMenuItem();
        JMTools = new javax.swing.JMenu();
        JMIRun = new javax.swing.JMenuItem();
        JMHelp = new javax.swing.JMenu();
        JMIGuide = new javax.swing.JMenuItem();
		JFChooser = new javax.swing.JFileChooser();
		fileFilter = new javax.swing.filechooser.FileNameExtensionFilter("SLM File", "SLM");
		
		JMINewFile.addActionListener(new Action());
		JMISave.addActionListener(new Action());
		JMISaveAs.addActionListener(new Action());
		JMIOpen.addActionListener(new Action());
		JMIExit.addActionListener(new Action());
		JMIRun.addActionListener(new Action());
		JMIGuide.addActionListener(new Action());
		JFChooser.setMultiSelectionEnabled(false);
		JFChooser.setCurrentDirectory(new File("codes/"));
		JFChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
		JFChooser.addChoosableFileFilter(fileFilter);
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JTextAreaEditor.setColumns(20);
        JTextAreaEditor.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        JTextAreaEditor.setRows(5);
        JTextAreaEditor.setBorder(new NumeredBorder());
        jScrollPane1.setViewportView(JTextAreaEditor);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JMFile.setText("File");

        JMINewFile.setText("New File");
        JMFile.add(JMINewFile);

        JMISave.setText("Save");
        JMFile.add(JMISave);

        JMISaveAs.setText("Save as...");
        JMFile.add(JMISaveAs);

        JMIOpen.setText("Open");
        JMFile.add(JMIOpen);

        JMIExit.setText("Exit");
        JMFile.add(JMIExit);

        JMBar.add(JMFile);

        JMTools.setText("Tools");

        JMIRun.setText("Run");
        JMIRun.setToolTipText("");
        JMTools.add(JMIRun);
        
        JMBar.add(JMTools);
        
        JMHelp.setText("Help");
        JMIGuide.setText("Guide");
        JMHelp.add(JMIGuide);
        
        JMBar.add(JMHelp);
        setJMenuBar(JMBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        pack();

        setVisible(true);
        setSize(600, 700);
        setTitle("Simpletron Editor");
    	setMinimumSize(new Dimension(400, 300));

    }       

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        Editor editor = new Editor();
        editor.initComponents();
    }
        
	public void menuFileAction(Object object){
		
		if(object == JMINewFile){
			setPath("");
			getJTextAreaEditor().setText("");
			setTitle("Simpletron Editor");
		}
		
		else if(object == JMIOpen){
			if(abreArquivo()){
				setPath(getFileAbsolutePath());
				if(new File(getPath()).exists()){
					getJTextAreaEditor().setText("");
					lerArquivo(path);	
				}
				
			}
		}
		else if(object == JMISave){
			escreverArquivo(path);				
		}
		
		else if(object == JMISaveAs){
			if(JFChooser.showSaveDialog(null) == 0){
				String tempPath = getFileAbsolutePath();
				if(tempPath!=null){
					if(new File(tempPath).exists()){
						if(JOptionPane.showConfirmDialog(null, "There exist a "+getFileName()+" file. Overwrite?") == 0)
							path = tempPath;
					}
					else
						path = tempPath;
				}
				if(path==tempPath)
					menuFileAction(JMISave);
			}
		}
		
		else if(object == JMIExit){
			dispose();
		}
		
		else if(object == JMIRun){
			output = new Output();
			Output.addText("Running: "+path+"\n");
			threadOutput = new Thread(output);
			threadOutput.start();
			CDI = new ControladorDeInstrucao(new File(path));
			threadCDI = new Thread(CDI);
			threadCDI.start();
		}
		
		else if(object == JMIGuide){
			new Help();
		}
	}

	/**
	 * DECLARAÇÕES
	 */
	
    private javax.swing.JMenuBar JMBar;
    private javax.swing.JMenu JMFile;
    private javax.swing.JMenuItem JMIExit;
    private javax.swing.JMenuItem JMINewFile;
    private javax.swing.JMenuItem JMIOpen;
    private javax.swing.JMenuItem JMISave;
    private javax.swing.JMenuItem JMISaveAs;
    private javax.swing.JMenu JMTools;
    private javax.swing.JMenuItem JMIRun;
    private javax.swing.JMenu JMHelp;
    private javax.swing.JMenuItem JMIGuide;
    private javax.swing.JTextArea JTextAreaEditor;
    private javax.swing.JScrollPane jScrollPane1;    
    private javax.swing.JFileChooser JFChooser;
    private javax.swing.filechooser.FileNameExtensionFilter fileFilter;
	private static Output output;
	private static Thread threadOutput;
	private static ControladorDeInstrucao CDI;
	private static Thread threadCDI;
	private static String path = "";
	
	
	/**
	 * 
	 * LISTENERS
	 */
	
	public class Action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			menuFileAction(arg0.getSource());
		}
		
	}
	
	/**
	 * MÉTODOS ESTÁTICOS
	 */
	
	@SuppressWarnings("deprecation")
	public static void aguardarEntrada(){
		threadCDI.suspend();
	}
	@SuppressWarnings("deprecation")
	public static void receberEntrada(){
		threadCDI.resume();
	}
	
	/**
	 * GETTERS E SETTERS
	 */
	
	public JTextArea getJTextAreaEditor(){
		return JTextAreaEditor;
	}
	
	public String getFileAbsolutePath(){
		return JFChooser.getSelectedFile().getAbsolutePath();
	}

	public String getFileName(){
		return JFChooser.getSelectedFile().getName();
	}
	
	public javax.swing.JMenuItem getJMIExit() {
		return JMIExit;
	}

	public void setJMIExit(javax.swing.JMenuItem jMIExit) {
		JMIExit = jMIExit;
	}

	public javax.swing.JMenuItem getJMINewFile() {
		return JMINewFile;
	}

	public void setJMINewFile(javax.swing.JMenuItem jMINewFile) {
		JMINewFile = jMINewFile;
	}

	public javax.swing.JMenuItem getJMIOpen() {
		return JMIOpen;
	}

	public void setJMIOpen(javax.swing.JMenuItem jMIOpen) {
		JMIOpen = jMIOpen;
	}

	public javax.swing.JMenuItem getJMISave() {
		return JMISave;
	}

	public void setJMISave(javax.swing.JMenuItem jMISave) {
		JMISave = jMISave;
	}

	public javax.swing.JMenuItem getJMISaveAs() {
		return JMISaveAs;
	}

	public void setJMISaveAs(javax.swing.JMenuItem jMISaveAs) {
		JMISaveAs = jMISaveAs;
	}

	public javax.swing.JMenuItem getJMIRun() {
		return JMIRun;
	}

	public void setJMIRun(javax.swing.JMenuItem jMIRun) {
		JMIRun = jMIRun;
	}

	public javax.swing.JMenuItem getJMIGuide() {
		return JMIGuide;
	}

	public void setJMIGuide(javax.swing.JMenuItem jMIGuide) {
		JMIGuide = jMIGuide;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String Path) {
		path = Path;
	}
	
	public Output getOutput(){
		return output;
	}
	
	/**
	 * GERENCIAMENTO DE ARQUIVOS
	 */
	
    public void lerArquivo(String path){
    	try {
			BufferedReader BReader = new BufferedReader(new FileReader(path));
			try {
				while(BReader.ready())
					JTextAreaEditor.setText(JTextAreaEditor.getText()+BReader.readLine()+"\n");
			} catch (IOException e1) {
				
			}
		} catch (FileNotFoundException e1) {
			
		}
    }
    
    public void escreverArquivo(String path){
    	try {
        	String str = "";
			BufferedWriter BWritter = new BufferedWriter(new FileWriter(new File(path)));
			str = JTextAreaEditor.getText();
			while(str.length()>0){
				if(str.indexOf('\n')!=-1){
					BWritter.write(str.substring(0,str.indexOf('\n')+1));
					str = str.substring(str.indexOf('\n')+1);
				}
				else{
					BWritter.write(str.substring(0,str.length()));
					str = "";
				}
			}
			BWritter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public boolean abreArquivo(){
    	return JFChooser.showOpenDialog(null)==0?true:false;
    }

}
