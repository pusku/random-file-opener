	import java.awt.Desktop;
	import java.awt.EventQueue;
	import java.awt.List;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.File;
	import java.util.Random;
	import java.util.Stack;
	import javax.swing.JButton;
	import javax.swing.JFileChooser;
	import javax.swing.JFrame;
	import javax.swing.JOptionPane;
	import javax.swing.JTextField;
	import javax.swing.JTextPane;
	public class RandomFileOpener extends JFrame {
	private JTextField reader;
	/**
	* Launch the application.
	*/
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	public void run() {
	try {
		RandomFileOpener frame = new RandomFileOpener();
	frame.setVisible(true);
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	});
	}
	public Stack<String> listFilesForFolder(final File folder) {
	Stack<String> files = new Stack<String>();
	//String[] files = new String[1000];
	//Integer counter = 0;
	for (final File fileEntry : folder.listFiles()) {
	if (fileEntry.isDirectory()) {
	listFilesForFolder(fileEntry);
	} else {
	files.push(fileEntry.getName());
	}
	}
	return files;
	}
	/**
	* Create the frame.
	*/
	public RandomFileOpener() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 608, 442);
	getContentPane().setLayout(null);
	JTextPane address = new JTextPane();
	address.setBounds(10, 11, 444, 20);
	getContentPane().add(address);
	List list = new List();
	list.setBounds(10, 44, 572, 349);
	getContentPane().add(list);
	JButton btnChoose = new JButton("Choose");
	btnChoose.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent arg0) {
	JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new java.io.File("."));
	chooser.setDialogTitle("Choose a file");
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	//
	// disable the "All files" option.
	//
	chooser.setAcceptAllFileFilterUsed(false);
	//
	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	File selected = chooser.getSelectedFile();
	address.setText(selected.toString());
	Stack<String> files = listFilesForFolder(selected);
	Stack<String> copy = listFilesForFolder(selected);
	while( copy.size() > 0 ){
	list.add(copy.peek().toString());
	copy.pop();
	}
	if( files.size() > 0 ){
	Random rn = new Random();
	int size = files.size();
	int number = rn.nextInt();
	number %= size;
	if( number < 0 )
	number = 0;
	String choosenFile = files.get(number);
	int ask = JOptionPane.showConfirmDialog(null, "Do you want to open " + choosenFile + " ?");
	if( ask == 0 ){
	String choosenFilePathString = selected.toString() + "/" + choosenFile;
	try{
	Desktop.getDesktop().open( new File(choosenFilePathString) );
	} catch (Exception exc ){
	JOptionPane.showMessageDialog(null, "Unable to open this type of file.");
	}}}
	else {
	JOptionPane.showMessageDialog(null, "No File on this directory");
	}
	}
	else {
	System.out.println("No Selection ");
	}
	}
	});
	btnChoose.setBounds(493, 11, 89, 23);
	getContentPane().add(btnChoose);
	}
	}