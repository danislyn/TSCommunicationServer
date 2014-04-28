package server.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

import database.operations.AdminOperation;
import database.operations.AnnouncementOperation;
import database.operations.FileOperation;
import database.operations.InformationOperation;
import database.operations.MailOperation;
import database.operations.PostOperation;
import database.operations.TeacherOperation;
import database.operations.UserOperation;


@SuppressWarnings("serial")
public class StartGUI extends JFrame {

	/**
	 * @param args
	 */

	static {
		try {
			//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			//LiquidLookAndFeel.setLiquidDecorations(true, "mac");
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			SubstanceLookAndFeel
			         .setSkin(new OfficeBlue2007Skin());
			
			SubstanceLookAndFeel
					.setCurrentButtonShaper(new StandardButtonShaper());

			
			SubstanceLookAndFeel
					.setCurrentBorderPainter(new StandardBorderPainter());
			SubstanceLookAndFeel
					.setCurrentGradientPainter(new StandardGradientPainter());
			
		} catch (Exception e) {
		}
	}

	public StartGUI() {
		this.setLayout(null);
		this.setSize(400, 300);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dimension.width - 500) / 2,
				(dimension.height - 500) / 2);

		this.setIconImage(new ImageIcon("images/icon.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton startServer = new JButton("开启服务器");
		//JButton stopServer = new JButton("关闭服务器");
		this.getContentPane().add(startServer);
		//this.getContentPane().add(stopServer);
		startServer.setBounds(130, 180, 150, 50);
		//stopServer.setBounds(220, 180, 100, 50);
		final JLabel show = new JLabel("点击按钮，开启服务");
		show.setFont(new Font("华文彩云",Font.BOLD,20));
		show.setForeground(Color.WHITE);
		this.getContentPane().add(show);
		show.setBounds(110, 40, 200, 50);
		this.setResizable(false);
		JLabel image=new JLabel();
		image.setBounds(0, 0, 480, 270);
		ImageIcon icon=new ImageIcon("images/sky.jpg");
		Image img2=icon.getImage();
	    img2=img2.getScaledInstance(480, 270,8);
		icon=new ImageIcon(img2);
		image.setIcon(icon);
		this.getContentPane().add(image);

		startServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startServer();
				show.setText("服务器启动");
				repaint();
			}

		});

		/*stopServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CloseServer();

			}

		});*/
	}

	public void startServer() {
		Map<String, Object> interfaceInstance = new HashMap<String, Object>();		
		interfaceInstance.put("server.interfaces.AdminOperationInterface", new AdminOperation());
		interfaceInstance.put("server.interfaces.AnnouncementOperationInterface", new AnnouncementOperation());
		interfaceInstance.put("server.interfaces.InformationOperationInterface", new InformationOperation());
		interfaceInstance.put("server.interfaces.MailOperationInterface", new MailOperation());
		interfaceInstance.put("server.interfaces.PostOperationInterface", new PostOperation());
		interfaceInstance.put("server.interfaces.TeacherOperationInterface", new TeacherOperation());
		interfaceInstance.put("server.interfaces.UserOperationInterface", new UserOperation());		
		interfaceInstance.put("server.interfaces.FileOperationInterface", new FileOperation());
		
		Map<String, String> directoryInstance = new HashMap<String, String>();		
		//directoryInstance.put("uploadPersonalImage", "D:/软工/temp/image/");
		directoryInstance.put("uploadSharedFile", "D:/TSCS/temp/file/");
		
		Server server = new Server(interfaceInstance, directoryInstance);
		server.startService();
	}

	/*public void CloseServer() {
		try {
			Registry reg = LocateRegistry.getRegistry();
			String[] str = reg.list();
			for (int i = 0; i < str.length; i++) {
				// System.out.println(str[i]);
				try {
					reg.unbind(str[i]);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			Runtime.getRuntime().exec("tskill rmiregistry");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.exit(0);
	}*/

	/**
	 * 图形界面模式main函数入口
	 */
	public static void main(String[] args) {
		StartGUI t = new StartGUI();
		t.setVisible(true);
	}

}
