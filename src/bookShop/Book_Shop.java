package booShop;

import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.*;

import net.proteanit.sql.DbUtils;

public class Book_Shop {

	private static Connection con;
	private static PreparedStatement pstmt;
	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTextField txtsearch;
	private ResultSet rest;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Book_Shop window = new Book_Shop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Book_Shop() {
		initialize();
		creatC();
		table_load();
	}

	public static Connection creatC() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/BookShop";
			String user = "root";
			String pass = "V2169@gpatil";

			con = DriverManager.getConnection(url, user, pass);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return con;

	}

	public void table_load() {

		try {
			pstmt = con.prepareStatement("select * from book_library");
			rest = pstmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rest));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 842, 479);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(366, 11, 136, 43);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(46, 65, 394, 273);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(36, 32, 85, 21);
		panel.add(lblNewLabel_1);

		txtbname = new JTextField();
		txtbname.setBounds(171, 34, 169, 30);
		panel.add(txtbname);
		txtbname.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Price");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(36, 125, 85, 21);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Edition");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(36, 78, 85, 21);
		panel.add(lblNewLabel_1_2);

		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(171, 80, 169, 30);
		panel.add(txtedition);

		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(171, 127, 169, 30);
		panel.add(txtprice);

		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(280, 200, 75, 46);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String bname, edition, price;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				String query = "insert into book_library(Bookname, Edition, Price) values(?, ?, ?)";

				try {
					pstmt = con.prepareStatement(query);
					pstmt.setString(1, bname);
					pstmt.setString(2, edition);
					pstmt.setString(3, price);

					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added ..!!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");

					txtbname.requestFocus();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(110, 200, 75, 46);
		panel.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Exit");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1_1.setBounds(195, 200, 75, 46);
		panel.add(btnNewButton_1_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(46, 349, 394, 80);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_3 = new JLabel("Book Id");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(36, 31, 85, 21);
		panel_1.add(lblNewLabel_1_3);

		txtsearch = new JTextField();
		txtsearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String id = txtsearch.getText();
				String query = "select bookname, edition, price from book_library where id =?";
				try {
					pstmt = con.prepareStatement(query);
					pstmt.setString(1, id);
					rest = pstmt.executeQuery();
					
					if(rest.next() == true) {
						String name = rest.getString(1);
						String edition = rest.getString(2);
						String price = rest.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
					}
					else {
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		txtsearch.setColumns(10);
		txtsearch.setBounds(159, 29, 187, 29);
		panel_1.add(txtsearch);

		JButton btnNewButton_1_1_1 = new JButton("Update");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname, edition, price, bid;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid = txtsearch.getText();
				
				String query = "update book_library set bookname=?, edition=?, price=? where id=?";

				try {
					pstmt = con.prepareStatement(query);
					pstmt.setString(1, bname);
					pstmt.setString(2, edition);
					pstmt.setString(3, price);
					pstmt.setString(4, bid);

					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated ..!!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");

					txtbname.requestFocus();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1_1_1.setBounds(501, 365, 75, 46);
		frame.getContentPane().add(btnNewButton_1_1_1);

		JButton btnNewButton_1_1_2 = new JButton("Delete");
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bid = txtsearch.getText();
				
				String query = "delete from book_library where id=?";

				try {
					pstmt = con.prepareStatement(query);
					pstmt.setString(1, bid);

					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted ..!!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");

					txtbname.requestFocus();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1_1_2.setBounds(624, 365, 75, 46);
		frame.getContentPane().add(btnNewButton_1_1_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(450, 65, 366, 280);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
	}
}
