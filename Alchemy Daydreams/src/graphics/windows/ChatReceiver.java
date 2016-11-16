package graphics.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import network.StreamInformation;
import network.client.Client;

public class ChatReceiver extends JFrame {

	private JPanel contentPane;
	public JTextArea textArea;
	public JTextArea txtrChat;
	
	Client client;
	
	public ChatReceiver(Client client) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 213, 315, 38);
		contentPane.add(textArea);
		
		JButton btnSendChat = new JButton("Send Chat");
		btnSendChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textArea.getText().equalsIgnoreCase("")) {
					sendChat(textArea.getText());
					textArea.setText("");
				}
			}
		});
		btnSendChat.setBounds(335, 213, 89, 38);
		contentPane.add(btnSendChat);
		
		txtrChat = new JTextArea();
		txtrChat.setEditable(false);
		txtrChat.setWrapStyleWord(true);
		txtrChat.setLineWrap(true);
		txtrChat.setBounds(10, 11, 414, 191);
		contentPane.add(txtrChat);
		
		setVisible(true);
		this.client = client;
	}
	
	public void sendChat(String data) {
		client.writeOutStream(StreamInformation.chat + StreamInformation.deliminator + data);
	}
}
