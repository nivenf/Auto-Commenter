import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class commentGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int SIZE_WIDTH = 800;
	private final int SIZE_HEIGHT = 1000;
	
	private JTextArea code;
	
	private JTextField header_name;
	private JTextField header_date;
	private JTextField header_class;
	private JTextField header_prof;
	private JTextField header_ta;
	private JTextField header_prog;
	
	private JRadioButton format_c;
	private JRadioButton format_java;
	private ButtonGroup format;
	
	private JRadioButton glossary_yes;
	private JRadioButton glossary_no;
	private ButtonGroup glossary;
	
	private JRadioButton auto_yes;
	private JRadioButton auto_no;
	private ButtonGroup auto;
	
	private JLabel gl;
	
	private JButton generate;
	
	private String file_type = "JAVA";
	private boolean method_glossary = true;
	private boolean autofill_getters = true;
	
	public static void main(String[] args) {
		new commentGUI().setVisible(true);
	}
	
	public commentGUI() {
		initializeGUI();
	}
	
	private void initializeGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(SIZE_WIDTH, SIZE_HEIGHT);
		this.setLayout(new BorderLayout());
		this.setTitle("Auto-Commenter by Niven");
		
		code = new JTextArea();
		code.setSize(new Dimension(SIZE_WIDTH, SIZE_HEIGHT));
		code.setBounds(SIZE_WIDTH/3, 0, SIZE_WIDTH, SIZE_HEIGHT);
		code.setFont(new Font("Courier New", Font.PLAIN, 12));
		code.setBorder(BorderFactory.createLineBorder(Color.black));
		code.setLineWrap(true);
		JScrollPane sp = new JScrollPane(code);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(sp);
		
		JPanel all_options = new JPanel();
		GridLayout grid = new GridLayout(5, 1);
		all_options.setLayout(grid);
		
		//////////////////////////////////////////////////////
		
		header_name = new JTextField(7);
		header_date = new JTextField(7);
		header_class = new JTextField(7);
		header_prof = new JTextField(7);
		header_ta = new JTextField(7);
		header_prog = new JTextField(7);
		
		JPanel options_header = new JPanel();
		options_header.setLayout(new GridLayout(6, 1));
		options_header.add(new JLabel(" Name:"));
		options_header.add(header_name);
		options_header.add(new JLabel(" Date:"));
		options_header.add(header_date);
		options_header.add(new JLabel(" Class:"));
		options_header.add(header_class);
		options_header.add(new JLabel(" Professor:"));
		options_header.add(header_prof);
		options_header.add(new JLabel(" TA:"));
		options_header.add(header_ta);
		options_header.add(new JLabel(" Program:"));
		options_header.add(header_prog);
		all_options.add(options_header);

		//////////////////////////////////////////////////////
		
		auto_yes = new JRadioButton("Yes");
		auto_no = new JRadioButton("No");
		auto = new ButtonGroup();
		auto.add(auto_yes);
		auto.add(auto_no);
		
		JPanel options_auto = new JPanel();
		options_auto.setLayout(new FlowLayout());
		JLabel au = new JLabel("Auto-fill Getters");
		options_auto.add(au);
		options_auto.add(auto_yes);
		options_auto.add(auto_no);
		auto_yes.setSelected(true);
		options_auto.setVisible(false);							//comment for auto getter choice (does not work)
		all_options.add(options_auto);
		
		auto_yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(auto_yes.isSelected())
					autofill_getters = true;
			}
		});
		
		auto_no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(auto_no.isSelected())
					autofill_getters = false;
			}
		});
		
		//////////////////////////////////////////////////////
		
		format_c = new JRadioButton("C");
		format_java = new JRadioButton("JAVA");
		format = new ButtonGroup();
		format.add(format_c);
		format.add(format_java);
		
		JPanel options_format = new JPanel();
		options_format.setLayout(new FlowLayout());
		JLabel fr = new JLabel("File Type");
		options_format.add(fr);
		options_format.add(format_java);
		options_format.add(format_c);
		format_java.setSelected(true);
		all_options.add(options_format);
		
		format_c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(format_c.isSelected()) {
					gl.setText("Function Glossary");
					file_type = "C";
				}
			}
		});
		
		format_java.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(format_java.isSelected()) {
					gl.setText("Method Glossary");
					file_type = "JAVA";
				}
			}
		});
		
		//////////////////////////////////////////////////////
		
		glossary_yes = new JRadioButton("Yes");
		glossary_no = new JRadioButton("No");
		glossary = new ButtonGroup();
		glossary.add(glossary_yes);
		glossary.add(glossary_no);
		
		JPanel options_glossary = new JPanel();
		options_glossary.setLayout(new FlowLayout());
		gl = new JLabel("Method Glossary");
		options_glossary.add(gl);
		options_glossary.add(glossary_yes);
		options_glossary.add(glossary_no);
		glossary_yes.setSelected(true);
		all_options.add(options_glossary);
		
		glossary_yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(glossary_yes.isSelected())
					method_glossary = true;
			}
		});
		
		glossary_no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(glossary_no.isSelected())
					method_glossary = false;
			}
		});
		
		//////////////////////////////////////////////////////
		
		generate = new JButton("Comment me!");
		all_options.add(generate);
		
		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("file_to_comment.txt"));
					bw.write(code.getText());
					bw.close();
					
					new comment(method_glossary, autofill_getters, header_name.getText(), header_date.getText(), header_class.getText(), header_prof.getText(), header_ta.getText(), header_prog.getText(), file_type);
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader("file_commented.txt"));
					String result = "";
					String line = "";
					while((line = br.readLine()) != null) {
						result += line + "\n";
					}
					code.setText(result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		//////////////////////////////////////////////////////
		
		this.add(all_options, BorderLayout.WEST);
	}
}
