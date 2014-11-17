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
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GUI {

    private JFrame frmInformationRetrievalSystem;
    private JTextField directory;
    private JTextField query;
    private JLabel lblQuery;
    JFileChooser chooser;

    /**
     * Create the application.
     */
    public GUI() {
        initialize();
        this.frmInformationRetrievalSystem.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmInformationRetrievalSystem = new JFrame();
        frmInformationRetrievalSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\gnome_text_x_generic.png"));
        frmInformationRetrievalSystem.setTitle("Information  Retrieval System");
        frmInformationRetrievalSystem.setBounds(100, 100, 700, 600);
        frmInformationRetrievalSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmInformationRetrievalSystem.getContentPane().setLayout(null);

        JLabel lblDirectory = new JLabel("Directory:");
        lblDirectory.setBounds(17, 40, 80, 17);
        lblDirectory.setFont(new Font("Consolas", Font.PLAIN, 14));
        frmInformationRetrievalSystem.getContentPane().add(lblDirectory);

        directory = new JTextField();
        directory.setFont(new Font("Consolas", Font.PLAIN, 14));
        directory.setBounds(96, 33, 530, 30);
        directory.setText("C:\\");
        frmInformationRetrievalSystem.getContentPane().add(directory);
        directory.setColumns(10);		

        final JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(96, 131, 530, 23);
        frmInformationRetrievalSystem.getContentPane().add(progressBar);          

        lblQuery = new JLabel("Query:");
        lblQuery.setFont(new Font("Consolas", Font.PLAIN, 14));
        lblQuery.setBounds(17, 97, 80, 17);
        frmInformationRetrievalSystem.getContentPane().add(lblQuery);

        JButton Browse = new JButton("...");
        Browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {			    
                String choosertitle = "Directory";

                chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(frmInformationRetrievalSystem) == JFileChooser.APPROVE_OPTION) {
                    directory.setText(""+chooser.getSelectedFile());
                    if (Files.exists(chooser.getSelectedFile().toPath())) {
                        directory.setBackground(Color.GREEN);
                    }
                    else{
                        directory.setBackground(Color.RED);
                    }
                }
                else {
//			      System.out.println("No Selection ");
                  }
            }
        });
        Browse.setBounds(636, 33, 32, 30);
        frmInformationRetrievalSystem.getContentPane().add(Browse);

        final JButton searchBtn = new JButton("");
        searchBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    Path path = Paths.get(directory.getText());
                    if (Files.exists(path)) {
                        directory.setBackground(Color.GREEN);
                        if(query.getText().length()>=1){
                            query.setBackground(Color.GREEN);
                            String[] queryTerms = query.getText().split(" ");
                            String filePath = directory.getText();
                            Main.kontroller(queryTerms, filePath);                                   
                        }
                        else{
                            query.setBackground(Color.RED);
                        }
                    }
                    else{
                        directory.setBackground(Color.RED);
                    }
                }
                catch(Exception ex){
                    directory.setBackground(Color.RED);
                }
            }
        });
        searchBtn.setIcon(new ImageIcon("images\\search-button-green-icon.png"));
        searchBtn.setFont(new Font("Consolas", Font.PLAIN, 11));
        searchBtn.setBounds(636, 90, 32, 30);
        frmInformationRetrievalSystem.getContentPane().add(searchBtn);


        final Canvas canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        canvas.setBounds(17, 160, 651, 391);
        frmInformationRetrievalSystem.getContentPane().add(canvas);

        query = new JTextField();
        query.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        try{
                            Path path = Paths.get(directory.getText());
                            if (Files.exists(path)) {
                                directory.setBackground(Color.GREEN);
                                if(query.getText().length()>=1){
                                    query.setBackground(Color.GREEN);
                                    String[] queryTerms = query.getText().split(" ");
                                    String filePath = directory.getText();
                                    Main.kontroller(queryTerms, filePath);                                   
                                }
                                else{
                                    query.setBackground(Color.RED);
                                }
                            }
                            else{
                                directory.setBackground(Color.RED);
                            }
                        }
                        catch(Exception ex){
                            directory.setBackground(Color.RED);
                        }                                
                    }
                }
        });
        query.addMouseListener(new MouseAdapter() {
                @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    Path path = Paths.get(directory.getText());
                    if (Files.exists(path)) {
                        directory.setBackground(Color.GREEN);
                    }
                    else{
                        directory.setBackground(Color.RED);
                    }
                    query.setText("");
                }
                catch(Exception ex){
                    directory.setBackground(Color.RED);
                }
            }
        });

        query.setFont(new Font("Consolas", Font.PLAIN, 14));
        query.setText("the crystalline lens in vertebrates including humans");
        query.setColumns(10);
        query.setBounds(96, 90, 530, 30);
        frmInformationRetrievalSystem.getContentPane().add(query);
    }
}
