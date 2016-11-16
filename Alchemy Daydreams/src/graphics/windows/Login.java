package graphics.windows;

import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import org.lwjgl.opengl.Display;

/*
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
//*/
import network.client.Client;
import network.server.Server;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame{
	/*
	public static void main(String[] args) {
		//Display.create();
	}
	//*/
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtIPAddress;
	private JTextField txtPort;
/*
	private double theta = 0;
    private double s = 0;
    private double c = 0;

    /*
    public static void main(String[] args) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        Frame frame = new Frame("AWT Window Test");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addGLEventListener(new Login());

        FPSAnimator animator = new FPSAnimator(canvas, 60);;
        //FPSAnimator ani = new FPSAnimator(canvas, 60);
        //animator.add(canvas);
        animator.start();
    }
    //
    
    public static DisplayMode dm, dm_old;
    private GLU glu = new GLU();
    private float rquad=0.0f;
    @Override
    public void display( GLAutoDrawable drawable ) {
       final GL2 gl = drawable.getGL().getGL2();
       gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );     
       gl.glLoadIdentity();
       gl.glTranslatef( 0f, 0f, -5.0f ); 
       gl.glRotatef( rquad, 1.0f, 1.0f, 1.0f ); // Rotate The Cube On X, Y & Z
       //giving different colors to different sides
       gl.glBegin( GL2.GL_QUADS ); // Start Drawing The Cube
       gl.glColor3f( 1f,0f,0f );   //red color
       gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Top)
       gl.glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
       gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
       gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
       gl.glColor3f( 0f,1f,0f ); //green color
       gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad 
       gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad 
       gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
       gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad 
       gl.glColor3f( 0f,0f,1f ); //blue color
       gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
       gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
       gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad 
       gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 
       gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
       gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
       gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
       gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
       gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)
       gl.glColor3f( 1f,0f,1f ); //purple (red + green)
       gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
       gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
       gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
       gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 
       gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
       gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
       gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad 
       gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad 
       gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad 
       gl.glEnd(); // Done Drawing The Quad
       gl.glFlush();
       rquad -=0.15f;
    }
    
    @Override
    public void dispose( GLAutoDrawable drawable ) {
    // TODO Auto-generated method stub
    }
    @Override
    public void init( GLAutoDrawable drawable ) {
       final GL2 gl = drawable.getGL().getGL2();
       gl.glShadeModel( GL2.GL_SMOOTH );
       gl.glClearColor( 0f, 0f, 0f, 0f );
       gl.glClearDepth( 1.0f );
       gl.glEnable( GL2.GL_DEPTH_TEST );
       gl.glDepthFunc( GL2.GL_LEQUAL );
       gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
    }
    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) {
       final GL2 gl = drawable.getGL().getGL2();
       if( height <=0 )
          height =1;
       final float h = ( float ) width / ( float ) height;
       gl.glViewport( 0, 0, width, height );
       gl.glMatrixMode( GL2.GL_PROJECTION );
       gl.glLoadIdentity();
       glu.gluPerspective( 45.0f, h, 1.0, 20.0 );
       gl.glMatrixMode( GL2.GL_MODELVIEW );
       gl.glLoadIdentity();
    }
    public static void main( String[] args ) {
       final GLProfile profile = GLProfile.get( GLProfile.GL2 );
       GLCapabilities capabilities = new GLCapabilities( profile );
       // The canvas 
       final GLCanvas glcanvas = new GLCanvas( capabilities );
       Login cube = new Login();
       glcanvas.addGLEventListener( cube );
       glcanvas.setSize( 400, 400 );
       final JFrame frame = new JFrame ( " Multicolored cube" );
       frame.getContentPane().add( glcanvas );
       frame.setSize( frame.getContentPane().getPreferredSize() );
       frame.setVisible( true );
       final FPSAnimator animator = new FPSAnimator( glcanvas, 300,true );
       animator.start();
    }

	
	
	
	
	
	
	
	
	
	
	//*/
	
	public Login() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(31, 48, 148, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setLabelFor(txtUsername);
		lblUsername.setBounds(31, 28, 148, 15);
		contentPane.add(lblUsername);
		
		txtIPAddress = new JTextField();
		txtIPAddress.setColumns(10);
		txtIPAddress.setBounds(31, 107, 148, 28);
		contentPane.add(txtIPAddress);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblIp.setBounds(31, 87, 148, 15);
		contentPane.add(lblIp);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(31, 166, 148, 28);
		contentPane.add(txtPort);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(31, 146, 148, 15);
		contentPane.add(lblPort);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txtUsername.getText();
				String address = txtIPAddress.getText();
				int port = 0;
				try {
				port = Integer.parseInt(txtPort.getText());
				} catch(Exception e) {}
				loginToServer(name, address, port);
			}
		});
		btnLogin.setBounds(62, 219, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnCreateServer = new JButton("Create Server");
		btnCreateServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createServer();
			}
		});
		btnCreateServer.setBounds(334, 24, 89, 23);
		contentPane.add(btnCreateServer);
		
		this.setVisible(true);
	}

	protected void loginToServer(String name, String ip, int port) {
		//dispose();
		//System.out.println(name + ", " + ip + ", " + port);
		new Client(name);
	}
	
	protected void createServer() {
		new Server();
	}
	//*/
}
