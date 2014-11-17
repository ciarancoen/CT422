package IR;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import java.awt.Canvas;


public class GUI {

	private JFrame frmInformationRetrievalSystem;
	private JTextField directory;
	private JTextField query;
	private JLabel lblQuery;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmInformationRetrievalSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInformationRetrievalSystem = new JFrame();
		frmInformationRetrievalSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\gnome_text_x_generic.png"));
		frmInformationRetrievalSystem.setTitle("Information  Retrieval System");
		frmInformationRetrievalSystem.setBounds(100, 100, 700, 501);
		frmInformationRetrievalSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInformationRetrievalSystem.getContentPane().setLayout(null);
		
		JLabel lblDirectory = new JLabel("Directory:");
		lblDirectory.setBounds(17, 40, 80, 17);
		lblDirectory.setFont(new Font("Consolas", Font.PLAIN, 14));
		frmInformationRetrievalSystem.getContentPane().add(lblDirectory);
		
		directory = new JTextField();
		directory.setFont(new Font("Consolas", Font.PLAIN, 14));
		directory.setBounds(96, 33, 530, 30);
		directory.setText("C:\\Users\\");
		frmInformationRetrievalSystem.getContentPane().add(directory);
		directory.setColumns(10);
		
		query = new JTextField();
		query.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				query.setText("");
				//change directory in main...
			}
		});
		query.setFont(new Font("Consolas", Font.PLAIN, 14));
		query.setText("e.g. the crystalline lens in vertebrates including humans");
		query.setColumns(10);
		query.setBounds(96, 90, 530, 30);
		frmInformationRetrievalSystem.getContentPane().add(query);
		
		lblQuery = new JLabel("Query:");
		lblQuery.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblQuery.setBounds(17, 97, 80, 17);
		frmInformationRetrievalSystem.getContentPane().add(lblQuery);
		
		JButton Browse = new JButton("...");
		Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result;
			    JFileChooser chooser;
			    String choosertitle = "Directory";
			        
			    chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    //    
			    if (chooser.showOpenDialog(frmInformationRetrievalSystem) == JFileChooser.APPROVE_OPTION) { 
//			      System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
//			      System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
			      directory.setText(""+chooser.getSelectedFile());
			      }
			    else {
//			      System.out.println("No Selection ");
			      }
			}
		});
		Browse.setBounds(636, 33, 32, 30);
		frmInformationRetrievalSystem.getContentPane().add(Browse);
		
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(96, 131, 530, 23);
		frmInformationRetrievalSystem.getContentPane().add(progressBar);
		
		JButton searchBtn = new JButton("");
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Start search code...
				progressBar.setValue(progressBar.getValue()+10);
                                String[] queryTerms = query.getText().split(" ");
                                String path = directory.getText();
                                Main.main(queryTerms, path);
			}
		});
		searchBtn.setIcon(new ImageIcon("images\\search-button-green-icon.png"));
		searchBtn.setFont(new Font("Consolas", Font.PLAIN, 11));
		searchBtn.setBounds(636, 90, 32, 30);
		frmInformationRetrievalSystem.getContentPane().add(searchBtn);
		
		Canvas canvas = new Canvas();
		canvas.setBounds(17, 160, 651, 292);
		frmInformationRetrievalSystem.getContentPane().add(canvas);
	}
}
