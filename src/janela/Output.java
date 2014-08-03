package janela;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;



/**
 *
 * @author Leonardo Dias
 */

public class Output extends javax.swing.JFrame implements Runnable{

	private static final long serialVersionUID = -7331737535376392890L;

	private static String buffer;
	
    public Output(){
        initComponents();
        setVisible(true);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setMinimumSize(new Dimension(650, 300));
    }
   
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaConsole = new javax.swing.JTextArea();
        txtInput = new javax.swing.JTextField();
        btEnviar = new javax.swing.JButton();

        btEnviar.setText("Enviar");
        txtAreaConsole.setEditable(false);
        
        btEnviar.addActionListener(new Action());
        txtInput.addActionListener(new Action());
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtAreaConsole.setBackground(new java.awt.Color(0, 0, 0));
        txtAreaConsole.setColumns(20);
        txtAreaConsole.setFont(new java.awt.Font("Lucida Console", 1, 13));
        txtAreaConsole.setForeground(new java.awt.Color(240, 240, 240));
        txtAreaConsole.setLineWrap(true);
        txtAreaConsole.setRows(5);
        jScrollPane1.setViewportView(txtAreaConsole);

        txtInput.setBackground(new java.awt.Color(0, 0, 0));
        txtInput.setForeground(new java.awt.Color(240, 240, 240));
        txtInput.setCaretColor(new java.awt.Color(240, 240, 240));
        txtInput.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtInput.setFont(new java.awt.Font("Lucida Console", 1, 13));
        
        btEnviar.setFont(new java.awt.Font("Lucida Console", 0, 13));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtInput)
                        .addGap(1, 1, 1)
                        .addComponent(btEnviar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btEnviar)
                    .addComponent(txtInput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );
        pack();
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Output.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Output.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Output.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Output.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
    }

    private javax.swing.JButton btEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    static private javax.swing.JTextArea txtAreaConsole;
    static private javax.swing.JTextField txtInput;

    @Override
    public void run() {
    	
    }
    
    public static void addText(String s){
    	txtAreaConsole.setText(txtAreaConsole.getText()+s);
    }
    
    public void setBuffer(String s){
    	buffer = s;
    }
    
    public static String getBuffer(){
    	String aux = buffer;
    	buffer = null;
    	return aux;
    }
    
    public void setWindowTitle(String s){
    	setTitle(s);
    }
    
    public JTextField getTxtInput(){
    	return txtInput;
    }
    
    public JButton getBtEnviar(){
    	return btEnviar;
    }
    
    public String getLastLine(){
    	return txtAreaConsole.getText().substring(txtAreaConsole.getText().lastIndexOf("\n"));
    }
    
    public class Action implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent AEvent) {
			outputAction(AEvent.getSource());
		}
    }
    
    public void outputAction(Object obj){
			setBuffer(txtInput.getText());
			txtInput.setText("");
	    	Editor.receberEntrada();
    }
}
