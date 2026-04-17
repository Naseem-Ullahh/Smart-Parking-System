package SmartParkingSystem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// ─── Custom Border ────────────────────────────────────────────────────────────
class RoundedBorder implements Border
{
    private int radius;
    public RoundedBorder(int radius) { this.radius = radius; }

    @Override public Insets getBorderInsets(Component c) { return new Insets(radius+1,radius+1,radius+1,radius+1); }
    @Override public boolean isBorderOpaque() { return false; }
    @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(x, y, w-1, h-1, radius, radius);
    }
}

// ─── Rounded Panel ────────────────────────────────────────────────────────────
class RoundedPanel extends JPanel
{
    private int radius;
    private Color backgroundColor;
    private Color borderColor;
    private float borderThickness;

    public RoundedPanel(int radius) { this(radius, Color.WHITE, Color.GRAY, 2.0f); }
    public RoundedPanel(int radius, Color bg) { this(radius, bg, bg.darker(), 2.0f); }
    public RoundedPanel(int radius, Color bg, Color border, float thickness)
    {
        super(); this.radius=radius; this.backgroundColor=bg; this.borderColor=border; this.borderThickness=thickness; setOpaque(false);
    }

    public void setBackgroundColor(Color c) { backgroundColor=c; repaint(); }
    public void setBorderColor(Color c)     { borderColor=c; repaint(); }
    public void setBorderThickness(float t) { borderThickness=t; repaint(); }
    public void setRadius(int r)            { radius=r; repaint(); }

    @Override protected void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override protected void paintBorder(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));
        int off=(int)Math.ceil(borderThickness/2.0);
        g2.drawRoundRect(off,off,getWidth()-off*2,getHeight()-off*2,radius,radius);
        g2.dispose();
    }
}

// ─── Rounded Button ───────────────────────────────────────────────────────────
class RoundedButton extends JButton
{
    private final int cornerRadius=15;
    private boolean paintCustomBorder=false;

    public RoundedButton(String text) { super(text); init(); }
    public RoundedButton(String text, ImageIcon icon)
    {
        super(text,icon);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
        setIconTextGap(10);
        init();
    }

    private void init()
    {
        setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR)); setMargin(new Insets(10,20,10,20)); setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void setPaintCustomBorder(boolean b) { paintCustomBorder=b; repaint(); }

    @Override protected void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth(),getHeight(),cornerRadius,cornerRadius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override protected void paintBorder(Graphics g)
    {
        if(!paintCustomBorder) return;
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,cornerRadius,cornerRadius);
        g2.dispose();
    }

    @Override public boolean contains(int x, int y)
    {
        Shape s=new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),cornerRadius,cornerRadius);
        return s.contains(x,y);
    }
}

// ─── Rounded TextField ────────────────────────────────────────────────────────
class RoundedTextField extends JTextField
{
    private int radius;
    public RoundedTextField(int cols, int radius) { super(cols); this.radius=radius; setOpaque(false); }

    @Override protected void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override protected void paintBorder(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
        g2.dispose();
    }

    @Override public boolean isOpaque() { return false; }
}

// ─── Rounded PasswordField ────────────────────────────────────────────────────
class RoundedPasswordField extends JPasswordField
{
    private int radius;
    private Color backgroundColor=Color.WHITE;
    private Color borderColor=Color.GRAY;
    private float borderThickness=2.0f;

    public RoundedPasswordField(int radius)
    {
        super(); this.radius=radius; setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        setFont(new Font("Segoe UI",Font.PLAIN,16));
        setForeground(Color.BLACK); setCaretColor(Color.BLACK);
    }

    public void setBackgroundColor(Color c) { backgroundColor=c; repaint(); }
    public void setBorderColor(Color c)     { borderColor=c; repaint(); }
    public void setBorderThickness(float t) { borderThickness=t; repaint(); }

    @Override protected void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override protected void paintBorder(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));
        int off=(int)Math.ceil(borderThickness/2.0);
        g2.drawRoundRect(off,off,getWidth()-off*2,getHeight()-off*2,radius,radius);
        g2.dispose();
    }
}

// ═════════════════════════════════════════════════════════════════════════════
//  MAIN CLASS
// ═════════════════════════════════════════════════════════════════════════════
public class Main
{
    JFrame mainFrame = new JFrame();

    // ── colors used throughout ────────────────────────────────────────────────
    static final Color BLUE   = new Color(34,139,230);
    static final Color GREEN  = new Color(62,192,88);
    static final Color ORANGE = new Color(252,126,18);
    static final Color BG     = new Color(230,237,255);

    // =========================================================================
    //  ENTRY POINT
    // =========================================================================
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Main().showMainFrame());
    }

    // =========================================================================
    //  HELPERS
    // =========================================================================

    /** Swaps the right-side content panel inside a dashboard frame */
    private JPanel swapPanel(JFrame frame)
    {
        Component[] comps = frame.getContentPane().getComponents();
        for (int i = comps.length-1; i >= 0; i--)
        {
            Component c = comps[i];
            if (c instanceof JPanel && c.getX() >= 240)
                frame.getContentPane().remove(c);
        }
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);
        p.setBounds(250,0,750,800);
        frame.getContentPane().add(p);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        return p;
    }

    private JLabel makeHeader(String text)
    {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI",Font.BOLD,22));
        l.setBounds(30,25,680,35);
        return l;
    }

    private JSeparator makeSep(int y)
    {
        JSeparator s = new JSeparator();
        s.setBounds(0,y,750,2);
        s.setForeground(new Color(220,220,220));
        return s;
    }

    private JLabel makeFieldLabel(String text, int x, int y)
    {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI",Font.BOLD,13));
        l.setBounds(x,y,350,20);
        return l;
    }

    private RoundedTextField makeField(int x, int y, int w, int h)
    {
        RoundedTextField f = new RoundedTextField(1,12);
        f.setBackground(Color.WHITE);
        f.setBounds(x,y,w,h);
        f.setFont(new Font("Segoe UI",Font.PLAIN,14));
        f.setMargin(new Insets(0,10,0,0));
        return f;
    }

    private RoundedPasswordField makePassField(int x, int y, int w, int h)
    {
        RoundedPasswordField f = new RoundedPasswordField(12);
        f.setBackground(Color.WHITE);
        f.setBounds(x,y,w,h);
        f.setBorderThickness(1f);
        f.setBorderColor(Color.GRAY);
        return f;
    }

    private RoundedButton makeBtn(String text, Color bg, int x, int y, int w, int h)
    {
        RoundedButton b = new RoundedButton(text);
        b.setBounds(x,y,w,h);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI",Font.BOLD,14));
        b.setHorizontalAlignment(JButton.CENTER);
        return b;
    }

    private RoundedPanel makeCard(int x, int y, int w, int h, Color bg)
    {
        RoundedPanel c = new RoundedPanel(18, bg, bg.darker(), 1.2f);
        c.setLayout(null);
        c.setBounds(x,y,w,h);
        return c;
    }

    // =========================================================================
    //  1.  MAIN FRAME  (landing page)
    // =========================================================================
    public void showMainFrame()
    {
        mainFrame.setTitle("Smart Parking Management System");
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1150,800);
        mainFrame.getContentPane().setBackground(BG);
        mainFrame.setVisible(true);

        JLabel heading = new JLabel("Smart Parking Management System");
        heading.setForeground(new Color(44,90,205));
        heading.setFont(new Font("Times New Roman",Font.BOLD,35));
        heading.setSize(heading.getPreferredSize());
        heading.setLocation((mainFrame.getContentPane().getWidth()-heading.getWidth())/2, 120);
        mainFrame.getContentPane().add(heading);

        JLabel sub = new JLabel("Efficient, convenient and secure parking for everyone!");
        sub.setForeground(new Color(116,118,127));
        sub.setFont(new Font("Calibri",Font.ITALIC,16));
        sub.setSize(heading.getWidth(),25);
        sub.setHorizontalAlignment(JLabel.CENTER);
        sub.setLocation((mainFrame.getContentPane().getWidth()-sub.getWidth())/2, 135+heading.getHeight());
        mainFrame.getContentPane().add(sub);

        int lm=150, pw=250, ph=320, ps=34, tm=250+heading.getHeight();

        // ── Admin panel ───────────────────────────────────────────────────────
        RoundedPanel adminPanel = new RoundedPanel(30);
        adminPanel.setLayout(null);
        adminPanel.setSize(pw,ph);
        adminPanel.setBackground(new Color(245,245,245));
        adminPanel.setLocation(lm,tm);
        mainFrame.getContentPane().add(adminPanel);

        JLabel adminTitle = new JLabel("Admin Portal");
        adminTitle.setForeground(Color.BLACK);
        adminTitle.setFont(new Font("Segoe UI",Font.BOLD,24));
        adminTitle.setBounds(0,85,250,30);
        adminTitle.setHorizontalAlignment(JLabel.CENTER);
        adminPanel.add(adminTitle);

        String[] adminFeatures = {"Manages Parking Sites","View Statistics","Generate Reports","Keep Records"};
        for (int i=0; i<adminFeatures.length; i++)
        {
            JLabel l = new JLabel("✔  "+adminFeatures[i]);
            l.setFont(new Font("DEFAULT",Font.ITALIC,15));
            l.setBounds(10,135+i*30,240,20);
            adminPanel.add(l);
        }

        RoundedButton adminBtn = new RoundedButton("Enter Admin Portal");
        adminBtn.setBounds(25,260,200,40);
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        adminBtn.setBackground(BLUE);
        adminPanel.add(adminBtn);
        adminBtn.addActionListener(e -> { mainFrame.setVisible(false); showAdminSignIn(); });

        // ── Regular user panel ────────────────────────────────────────────────
        RoundedPanel userPanel = new RoundedPanel(30);
        userPanel.setLayout(null);
        userPanel.setSize(pw,ph);
        userPanel.setBackground(new Color(245,245,245));
        userPanel.setLocation(lm+pw+ps,tm);
        mainFrame.getContentPane().add(userPanel);

        JLabel userTitle = new JLabel("User Portal");
        userTitle.setFont(new Font("Segoe UI",Font.BOLD,24));
        userTitle.setBounds(0,85,250,30);
        userTitle.setHorizontalAlignment(JLabel.CENTER);
        userPanel.add(userTitle);

        String[] userFeatures = {"Manage Parking Sessions","Keep Payment Records","Ensures Safe Parking","Discounted Prices"};
        for (int i=0; i<userFeatures.length; i++)
        {
            JLabel l = new JLabel("✔  "+userFeatures[i]);
            l.setFont(new Font("DEFAULT",Font.ITALIC,15));
            l.setBounds(10,135+i*30,240,20);
            userPanel.add(l);
        }

        RoundedButton userBtn = new RoundedButton("Enter User Portal");
        userBtn.setBounds(25,260,200,40);
        userBtn.setForeground(Color.WHITE);
        userBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        userBtn.setBackground(GREEN);
        userPanel.add(userBtn);
        userBtn.addActionListener(e -> { mainFrame.setVisible(false); showUserSignUp(); });

        // ── Walk-in panel ─────────────────────────────────────────────────────
        RoundedPanel walkPanel = new RoundedPanel(30);
        walkPanel.setLayout(null);
        walkPanel.setSize(pw,ph);
        walkPanel.setBackground(new Color(245,245,245));
        walkPanel.setLocation(lm+2*(pw+ps),tm);
        mainFrame.getContentPane().add(walkPanel);

        JLabel walkTitle = new JLabel("Walk-In User");
        walkTitle.setFont(new Font("Segoe UI",Font.BOLD,24));
        walkTitle.setBounds(0,85,250,30);
        walkTitle.setHorizontalAlignment(JLabel.CENTER);
        walkPanel.add(walkTitle);

        String[] walkFeatures = {"Quick Check-In & Park","Pay at Check-Out","Secure & Safe Parking","Hassle-Free Parking"};
        for (int i=0; i<walkFeatures.length; i++)
        {
            JLabel l = new JLabel("✔  "+walkFeatures[i]);
            l.setFont(new Font("DEFAULT",Font.ITALIC,15));
            l.setBounds(10,135+i*30,240,20);
            walkPanel.add(l);
        }

        RoundedButton walkBtn = new RoundedButton("Book a Slot Now");
        walkBtn.setBounds(25,260,200,40);
        walkBtn.setForeground(Color.WHITE);
        walkBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        walkBtn.setBackground(ORANGE);
        walkPanel.add(walkBtn);
        walkBtn.addActionListener(e -> { mainFrame.setVisible(false); showWalkInPortal(); });

        JLabel copy = new JLabel("© 2025 — Smart Parking System — Object Oriented Programming");
        copy.setFont(new Font("Calibri",Font.ITALIC,16));
        copy.setHorizontalAlignment(JLabel.CENTER);
        copy.setSize(pw*3+ps*2,25);
        copy.setLocation(lm,tm+ph+60);
        mainFrame.getContentPane().add(copy);

        mainFrame.getContentPane().revalidate();
        mainFrame.getContentPane().repaint();
    }

    // =========================================================================
    //  2.  ADMIN SIGN-IN
    // =========================================================================
    public void showAdminSignIn()
    {
        JFrame f = new JFrame("Admin Portal");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLayout(null);
        f.setSize(1000,800);
        f.getContentPane().setBackground(BG);

        // Left blue panel
        RoundedPanel left = new RoundedPanel(60,BLUE,BLUE,2);
        left.setLayout(null);
        left.setSize(500,765);
        left.setLocation(-50,0);
        f.add(left);

        JLabel ltitle = new JLabel("ADMIN PORTAL");
        ltitle.setForeground(Color.WHITE);
        ltitle.setFont(new Font("Arial",Font.BOLD,35));
        ltitle.setBounds(115,400,300,50);
        ltitle.setHorizontalAlignment(JLabel.CENTER);
        left.add(ltitle);

        JButton backBtn = new JButton("← Back to main menu");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBounds(10,10,250,35);
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        backBtn.setOpaque(false); backBtn.setFocusPainted(false); backBtn.setContentAreaFilled(false);
        left.add(backBtn);

        f.setVisible(true);

        // Right form
        RoundedPanel form = new RoundedPanel(25);
        form.setLayout(null);
        form.setBackground(new Color(245,245,245));
        form.setBounds(530,150,390,450);
        f.add(form);

        JLabel ftitle = new JLabel("Admin Sign In");
        ftitle.setBounds(0,22,390,38);
        ftitle.setHorizontalAlignment(JLabel.CENTER);
        ftitle.setFont(new Font("Segoe UI",Font.BOLD,26));
        form.add(ftitle);

        form.add(makeFieldLabel("Username",30,90));
        RoundedTextField userF = makeField(30,113,330,42);
        form.add(userF);

        form.add(makeFieldLabel("Password",30,170));
        RoundedPasswordField passF = makePassField(30,193,330,42);
        form.add(passF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED);
        errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,248,330,20);
        errLbl.setHorizontalAlignment(JLabel.CENTER);
        form.add(errLbl);

        RoundedButton loginBtn = makeBtn("Login as Admin",BLUE,30,275,330,48);
        form.add(loginBtn);

        form.revalidate(); form.repaint();

        backBtn.addActionListener(e -> { f.dispose(); mainFrame.setVisible(true); });

        ActionListener doLogin = e -> {
            Admin admin = Admin.getInstance();
            if (admin.getUsername().equals(userF.getText().trim()) &&
                admin.getPassword().equals(new String(passF.getPassword())))
            { f.dispose(); showAdminDashboard(admin); }
            else { errLbl.setText("Incorrect username or password."); passF.setText(""); }
        };
        loginBtn.addActionListener(doLogin);
        passF.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){ if(e.getKeyCode()==KeyEvent.VK_ENTER) loginBtn.doClick(); }
        });
    }

    // =========================================================================
    //  3.  ADMIN DASHBOARD
    // =========================================================================
    public void showAdminDashboard(Admin admin)
    {
        JFrame dash = new JFrame("Admin Dashboard");
        dash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dash.setResizable(false);
        dash.setLayout(null);
        dash.setSize(1000,800);
        dash.getContentPane().setBackground(BG);

        // Sidebar
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(BLUE);
        sidebar.setBounds(-20,0,270,800);
        dash.add(sidebar);
        dash.setVisible(true);

        JLabel sTitle = new JLabel("Admin Dashboard");
        sTitle.setBounds(-1,-1,270,75);
        sTitle.setFont(new Font("Segoe UI",Font.BOLD,18));
        sTitle.setForeground(Color.WHITE);
        sTitle.setHorizontalAlignment(JLabel.CENTER);
        sTitle.setBorder(new MatteBorder(0,0,2,0,Color.WHITE));
        sidebar.add(sTitle);

        String[] labels = {
            "Admin Profile","Add Site","Remove Site",
            "Site Status","Search Vehicle","View Users",
            "Sales Report","Settings","Logout"
        };

        int bx=25, bw=220, bh=44, gap=12, sy=82;
        RoundedButton[] btns = new RoundedButton[labels.length];

        for (int i=0; i<labels.length; i++)
        {
            RoundedButton b = new RoundedButton(labels[i]);
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setBorder(BorderFactory.createEmptyBorder(0,18,0,0));
            b.setBackground(BLUE);
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Segoe UI",Font.PLAIN,15));
            b.setBounds(bx, sy+i*(bh+gap), bw, bh);
            if (i==labels.length-1) b.setForeground(new Color(255,200,200));
            btns[i]=b;
            sidebar.add(b);
            final int idx=i;
            b.addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){ b.setBackground(Color.WHITE); b.setForeground(BLUE); b.repaint(); }
                public void mouseExited(MouseEvent e){
                    b.setBackground(BLUE);
                    b.setForeground(idx==labels.length-1 ? new Color(255,200,200) : Color.WHITE);
                    b.repaint();
                }
            });
        }

        // Show profile by default
        showAdminProfile(dash, admin);

        // Wire buttons
        btns[0].addActionListener(e -> showAdminProfile(dash, admin));
        btns[1].addActionListener(e -> showAddSite(dash, admin));
        btns[2].addActionListener(e -> showRemoveSite(dash, admin));
        btns[3].addActionListener(e -> showSiteStatus(dash, admin));
        btns[4].addActionListener(e -> showSearchVehicle(dash, admin));
        btns[5].addActionListener(e -> showViewUsers(dash));
        btns[6].addActionListener(e -> showSalesReport(dash, admin));
        btns[7].addActionListener(e -> showAdminSettings(dash, admin));
        btns[8].addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(dash,"Logout?","Logout",JOptionPane.YES_NO_OPTION);
            if (c==JOptionPane.YES_OPTION){ dash.dispose(); mainFrame.setVisible(true); }
        });
    }

    // ── Admin Profile ──────────────────────────────────────────────────────────
    private void showAdminProfile(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Admin Profile"));
        p.add(makeSep(68));

        RoundedPanel card = makeCard(30,90,680,140,new Color(227,242,253));
        JLabel name = new JLabel("Administrator");
        name.setFont(new Font("Segoe UI",Font.BOLD,26));
        name.setBounds(20,18,500,35);
        card.add(name);
        JLabel role = new JLabel("Role: Administrator  |  Username: "+admin.getUsername());
        role.setFont(new Font("Segoe UI",Font.PLAIN,14));
        role.setForeground(Color.GRAY);
        role.setBounds(20,58,600,22);
        card.add(role);
        p.add(card);

        p.revalidate(); p.repaint();
    }

    // ── Add Site ───────────────────────────────────────────────────────────────
    private void showAddSite(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Add Parking Site"));
        p.add(makeSep(68));

        p.add(makeFieldLabel("Site ID",30,90));
        RoundedTextField siteIdF = makeField(30,113,330,42); p.add(siteIdF);

        p.add(makeFieldLabel("Location",30,168));
        RoundedTextField locF = makeField(30,191,330,42); p.add(locF);

        p.add(makeFieldLabel("Maximum Capacity",30,246));
        RoundedTextField capF = makeField(30,269,180,42); p.add(capF);

        p.add(makeFieldLabel("Hourly Rate (Rs)",30,324));
        RoundedTextField rateF = makeField(30,347,180,42); p.add(rateF);

        p.add(makeFieldLabel("Operational Status",30,402));
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Operational","Not Operational"});
        statusBox.setBounds(30,425,220,42);
        statusBox.setFont(new Font("Segoe UI",Font.PLAIN,14));
        p.add(statusBox);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED);
        errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,480,600,20);
        p.add(errLbl);

        RoundedButton addBtn = makeBtn("Add Site",BLUE,30,508,180,46);
        p.add(addBtn);

        addBtn.addActionListener(e -> {
            String sid=siteIdF.getText().trim(), loc=locF.getText().trim();
            String capT=capF.getText().trim(), ratT=rateF.getText().trim();
            if (sid.isEmpty()||loc.isEmpty()||capT.isEmpty()||ratT.isEmpty()){ errLbl.setText("All fields are required!"); return; }
            int cap; double rate;
            try{ cap=Integer.parseInt(capT); }catch(Exception ex){ errLbl.setText("Capacity must be a whole number."); return; }
            try{ rate=Double.parseDouble(ratT); }catch(Exception ex){ errLbl.setText("Rate must be a number."); return; }
            if(cap<=0){ errLbl.setText("Capacity must be greater than 0."); return; }
            if(rate<0){ errLbl.setText("Rate cannot be negative."); return; }
            if(admin.findSiteIndex(sid)!=-1){ errLbl.setText("A site with this ID already exists!"); return; }
            admin.addSite(sid,cap,loc,rate,statusBox.getSelectedIndex()==0);
            JOptionPane.showMessageDialog(dash,"Site '"+sid+"' added successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
            siteIdF.setText(""); locF.setText(""); capF.setText(""); rateF.setText(""); errLbl.setText("");
        });

        p.revalidate(); p.repaint();
    }

    // ── Remove Site ────────────────────────────────────────────────────────────
    private void showRemoveSite(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Remove Parking Site"));
        p.add(makeSep(68));

        if (admin.hasNoSites())
        {
            JLabel none = new JLabel("No sites added yet.");
            none.setFont(new Font("Segoe UI",Font.ITALIC,14));
            none.setForeground(Color.GRAY);
            none.setBounds(30,100,400,30);
            p.add(none); p.revalidate(); p.repaint(); return;
        }

        // Capture site list
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream oldOut = System.out;
        System.setOut(new java.io.PrintStream(baos));
        admin.displayAllSites();
        System.out.flush(); System.setOut(oldOut);

        JTextArea area = new JTextArea(baos.toString());
        area.setFont(new Font("Segoe UI",Font.PLAIN,13));
        area.setEditable(false);
        area.setBackground(new Color(248,248,252));
        area.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30,85,680,130);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        p.add(scroll);

        p.add(makeFieldLabel("Site ID to Remove",30,235));
        RoundedTextField siteF = makeField(30,258,280,42); p.add(siteF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED);
        errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,312,500,20); p.add(errLbl);

        RoundedButton removeBtn = makeBtn("Remove Site",new Color(210,50,50),30,340,180,46);
        p.add(removeBtn);

        removeBtn.addActionListener(e -> {
            String sid = siteF.getText().trim();
            if (sid.isEmpty()){ errLbl.setText("Please enter a site ID."); return; }
            if (admin.findSiteIndex(sid)==-1){ errLbl.setText("No site found with that ID."); return; }
            int c = JOptionPane.showConfirmDialog(dash,"Remove site '"+sid+"'? This cannot be undone.","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (c==JOptionPane.YES_OPTION)
            { admin.removeParkingSite(sid); JOptionPane.showMessageDialog(dash,"Site removed.","Done",JOptionPane.INFORMATION_MESSAGE); showRemoveSite(dash,admin); }
        });

        p.revalidate(); p.repaint();
    }

    // ── Site Status ────────────────────────────────────────────────────────────
    private void showSiteStatus(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Parking Site Status"));
        p.add(makeSep(68));

        if (admin.hasNoSites())
        {
            JLabel none = new JLabel("No sites added yet.");
            none.setFont(new Font("Segoe UI",Font.ITALIC,14)); none.setForeground(Color.GRAY);
            none.setBounds(30,100,400,30); p.add(none); p.revalidate(); p.repaint(); return;
        }

        p.add(makeFieldLabel("Enter Site ID",30,88));
        RoundedTextField siteF = makeField(30,111,240,42); p.add(siteF);
        RoundedButton viewBtn = makeBtn("View Status",BLUE,288,111,140,42); p.add(viewBtn);

        JTextArea area = new JTextArea("Enter a site ID and click View Status.");
        area.setFont(new Font("Consolas",Font.PLAIN,13));
        area.setEditable(false);
        area.setBackground(new Color(248,248,252));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30,175,680,520);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        p.add(scroll);

        viewBtn.addActionListener(e -> {
            String sid = siteF.getText().trim();
            ParkingSite site = Admin.getInstance().returnSiteById(sid);
            if (site==null){ area.setText("No site found with ID: "+sid); return; }
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream old = System.out;
            System.setOut(new java.io.PrintStream(baos));
            site.showSiteStatus();
            System.out.flush(); System.setOut(old);
            area.setText(baos.toString());
        });

        p.revalidate(); p.repaint();
    }

    // ── Search Vehicle ─────────────────────────────────────────────────────────
    private void showSearchVehicle(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Search Vehicle"));
        p.add(makeSep(68));

        p.add(makeFieldLabel("License Plate Number",30,88));
        RoundedTextField plateF = makeField(30,111,290,42); p.add(plateF);
        RoundedButton searchBtn = makeBtn("Search",BLUE,335,111,110,42); p.add(searchBtn);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED);
        errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,165,600,22); p.add(errLbl);

        RoundedPanel result = makeCard(30,175,680,260,new Color(248,250,255));
        result.setVisible(false);
        p.add(result);

        ActionListener doSearch = e -> {
            String plate = plateF.getText().trim();
            if (plate.isEmpty()){ errLbl.setText("Enter a license plate."); return; }
            User u = User.getUserByVehicleNo(plate);
            if (u==null){ errLbl.setText("No vehicle found with plate: "+plate); result.setVisible(false); p.revalidate(); p.repaint(); return; }
            errLbl.setText(""); result.removeAll(); result.setVisible(true);
            String type   = u instanceof RegularUser ? "Regular User" : "Walk-In User";
            String status = u.isParked ? "Currently Parked" : "Not Parked";
            String loc    = (u.isParked && u.getCurrentParking()!=null)
                ? "Site: "+u.getCurrentParking().getsiteId()+" | Slot: "+u.bookedSlot : "N/A";
            String[][] rows = {{"Name",u.getName()},{"Vehicle Type",u.getVehicleType()},
                {"License Plate",plate},{"User Type",type},{"Status",status},{"Location",loc}};
            for (int i=0; i<rows.length; i++)
            {
                JLabel k = new JLabel(rows[i][0]+":"); k.setFont(new Font("Segoe UI",Font.BOLD,13)); k.setForeground(Color.GRAY); k.setBounds(20,15+i*36,160,22); result.add(k);
                JLabel v = new JLabel(rows[i][1]); v.setFont(new Font("Segoe UI",Font.PLAIN,13)); v.setBounds(185,15+i*36,480,22); result.add(v);
            }
            p.revalidate(); p.repaint();
        };

        searchBtn.addActionListener(doSearch);
        plateF.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){ if(e.getKeyCode()==KeyEvent.VK_ENTER) searchBtn.doClick(); }
        });

        p.revalidate(); p.repaint();
    }

    // ── View Users ─────────────────────────────────────────────────────────────
    private void showViewUsers(JFrame dash)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Registered Regular Users"));
        p.add(makeSep(68));

        if (RegularUser.cnicUserMap==null || RegularUser.cnicUserMap.isEmpty())
        {
            JLabel none = new JLabel("No regular users registered yet.");
            none.setFont(new Font("Segoe UI",Font.ITALIC,14)); none.setForeground(Color.GRAY);
            none.setBounds(30,100,400,30); p.add(none); p.revalidate(); p.repaint(); return;
        }

        String[] cols = {"ID","Name","CNIC","Contact","Vehicle","Plate","Status"};
        java.util.Collection<RegularUser> users = RegularUser.cnicUserMap.values();
        Object[][] data = new Object[users.size()][7];
        int i=0;
        for (RegularUser u : users)
        {
            data[i][0]=u.getUserID(); data[i][1]=u.getName(); data[i][2]=u.getCNIC();
            data[i][3]=u.getContactNo(); data[i][4]=u.getVehicleType();
            data[i][5]=u.getVehicleNo(); data[i][6]=u.isParked?"Parked":"Not Parked";
            i++;
        }

        JTable table = new JTable(data,cols){ public boolean isCellEditable(int r,int c){ return false; } };
        table.setFont(new Font("Segoe UI",Font.PLAIN,13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13));
        table.getTableHeader().setBackground(BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(220,220,220));
        table.setSelectionBackground(new Color(200,230,255));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20,85,705,640);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        p.add(scroll);

        p.revalidate(); p.repaint();
    }

    // ── Sales Report ───────────────────────────────────────────────────────────
    private void showSalesReport(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Sales Report"));
        p.add(makeSep(68));

        p.add(makeFieldLabel("Enter Site ID",30,88));
        RoundedTextField siteF = makeField(30,111,240,42); p.add(siteF);
        RoundedButton genBtn = makeBtn("Generate",BLUE,288,111,140,42); p.add(genBtn);

        JTextArea area = new JTextArea("Enter a site ID and click Generate.");
        area.setFont(new Font("Consolas",Font.PLAIN,13));
        area.setEditable(false);
        area.setBackground(new Color(248,248,252));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30,175,680,520);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        p.add(scroll);

        genBtn.addActionListener(e -> {
            String sid = siteF.getText().trim();
            if (sid.isEmpty()){ area.setText("Please enter a site ID."); return; }
            if (admin.findSiteIndex(sid)==-1){ area.setText("No site found with ID: "+sid); return; }
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream old = System.out;
            System.setOut(new java.io.PrintStream(baos));
            SalesReport.getRegularUsersSalesReport(sid);
            SalesReport.getWalkinUserSalesReport(sid);
            SalesReport.getTotalSales(sid);
            System.out.flush(); System.setOut(old);
            area.setText(baos.toString());
        });

        p.revalidate(); p.repaint();
    }

    // ── Admin Settings ─────────────────────────────────────────────────────────
    private void showAdminSettings(JFrame dash, Admin admin)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Settings"));
        p.add(makeSep(68));

        JLabel sec = new JLabel("Change Login Credentials");
        sec.setFont(new Font("Segoe UI",Font.BOLD,15)); sec.setBounds(30,88,400,25); p.add(sec);

        p.add(makeFieldLabel("Current Username",30,125));
        RoundedTextField oldU = makeField(30,148,330,42); p.add(oldU);

        p.add(makeFieldLabel("Current Password",30,205));
        RoundedPasswordField oldPw = makePassField(30,228,330,42); p.add(oldPw);

        JSeparator sep2 = new JSeparator(); sep2.setBounds(30,285,680,2); sep2.setForeground(new Color(220,220,220)); p.add(sep2);

        p.add(makeFieldLabel("New Username",30,298));
        RoundedTextField newU = makeField(30,321,330,42); p.add(newU);

        p.add(makeFieldLabel("New Password",30,378));
        RoundedPasswordField newPw = makePassField(30,401,330,42); p.add(newPw);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED);
        errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,456,500,20); p.add(errLbl);

        RoundedButton updateBtn = makeBtn("Update Credentials",BLUE,30,484,220,46); p.add(updateBtn);

        updateBtn.addActionListener(e -> {
            String ou=oldU.getText().trim(), op=new String(oldPw.getPassword());
            String nu=newU.getText().trim(), np=new String(newPw.getPassword());
            if(ou.isEmpty()||op.isEmpty()||nu.isEmpty()||np.isEmpty()){ errLbl.setText("All fields required."); return; }
            if(!admin.getUsername().equals(ou)||!admin.getPassword().equals(op)){ errLbl.setText("Current credentials are incorrect."); return; }
            if(np.length()<4){ errLbl.setText("New password must be at least 4 characters."); return; }
            admin.setUsername(nu); admin.setPassword(np);
            JOptionPane.showMessageDialog(dash,"Credentials updated!","Success",JOptionPane.INFORMATION_MESSAGE);
            oldU.setText(""); oldPw.setText(""); newU.setText(""); newPw.setText(""); errLbl.setText("");
        });

        p.revalidate(); p.repaint();
    }

    // =========================================================================
    //  4.  USER SIGN-UP
    // =========================================================================
    public void showUserSignUp()
    {
        JFrame f = new JFrame("User Portal");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false); f.setLayout(null);
        f.setSize(1000,800);
        f.getContentPane().setBackground(BG);

        RoundedPanel left = new RoundedPanel(60,GREEN,GREEN,2);
        left.setLayout(null); left.setSize(500,765); left.setLocation(-50,0);
        f.add(left);

        JLabel ltitle = new JLabel("USER PORTAL");
        ltitle.setForeground(Color.WHITE); ltitle.setFont(new Font("Arial",Font.BOLD,35));
        ltitle.setBounds(115,370,300,50); ltitle.setHorizontalAlignment(JLabel.CENTER);
        left.add(ltitle);

        JLabel ltag = new JLabel("Register to start your parking journey!");
        ltag.setForeground(new Color(220,255,220)); ltag.setFont(new Font("Segoe UI",Font.ITALIC,14));
        ltag.setBounds(80,425,340,25); ltag.setHorizontalAlignment(JLabel.CENTER);
        left.add(ltag);

        JButton backBtn = new JButton("← Back to main menu");
        backBtn.setForeground(Color.WHITE); backBtn.setBounds(10,10,250,35);
        backBtn.setBorderPainted(false); backBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        backBtn.setOpaque(false); backBtn.setFocusPainted(false); backBtn.setContentAreaFilled(false);
        left.add(backBtn);

        JButton switchBtn = new JButton("Already have an account? Sign In →");
        switchBtn.setForeground(new Color(220,255,220)); switchBtn.setBounds(80,470,330,28);
        switchBtn.setBorderPainted(false); switchBtn.setFont(new Font("Segoe UI",Font.PLAIN,13));
        switchBtn.setOpaque(false); switchBtn.setFocusPainted(false); switchBtn.setContentAreaFilled(false);
        left.add(switchBtn);

        f.setVisible(true);

        RoundedPanel form = new RoundedPanel(25);
        form.setLayout(null); form.setBackground(new Color(245,245,245));
        form.setBounds(510,35,415,710); f.add(form);

        JLabel ftitle = new JLabel("Create Account");
        ftitle.setBounds(0,18,415,38); ftitle.setHorizontalAlignment(JLabel.CENTER);
        ftitle.setFont(new Font("Segoe UI",Font.BOLD,26)); form.add(ftitle);

        JLabel fsub = new JLabel("Fill in your details to register");
        fsub.setBounds(0,55,415,20); fsub.setHorizontalAlignment(JLabel.CENTER);
        fsub.setFont(new Font("Segoe UI",Font.PLAIN,13)); fsub.setForeground(Color.GRAY);
        form.add(fsub);

        int fx=30, fw=355, fh=40, sy=85, gap=65;
        String[] lbls = {"Full Name","CNIC (13 digits)","Contact No (03XXXXXXXXX)","Vehicle Type","License Plate No"};
        RoundedTextField[] fields = new RoundedTextField[5];
        for (int i=0; i<5; i++)
        {
            JLabel l = new JLabel(lbls[i]); l.setFont(new Font("Segoe UI",Font.BOLD,13));
            l.setBounds(fx, sy+gap*i, fw, 20); form.add(l);
            fields[i] = makeField(fx, sy+gap*i+22, fw, fh); form.add(fields[i]);
        }

        JLabel passLbl = new JLabel("Password"); passLbl.setFont(new Font("Segoe UI",Font.BOLD,13));
        passLbl.setBounds(fx, sy+gap*5, fw, 20); form.add(passLbl);
        RoundedPasswordField passF = makePassField(fx, sy+gap*5+22, fw, fh); form.add(passF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(fx, sy+gap*6+5, fw, 18); errLbl.setHorizontalAlignment(JLabel.CENTER);
        form.add(errLbl);

        RoundedButton signUpBtn = makeBtn("Create Account",GREEN,fx,sy+gap*6+26,fw,46);
        form.add(signUpBtn);

        form.revalidate(); form.repaint();

        backBtn.addActionListener(e -> { f.dispose(); mainFrame.setVisible(true); });
        switchBtn.addActionListener(e -> { f.dispose(); showUserSignIn(); });

        signUpBtn.addActionListener(e -> {
            String name=fields[0].getText().trim(), cnic=fields[1].getText().trim();
            String contact=fields[2].getText().trim(), vType=fields[3].getText().trim();
            String plate=fields[4].getText().trim(), pass=new String(passF.getPassword());
            if(name.isEmpty()||cnic.isEmpty()||contact.isEmpty()||vType.isEmpty()||plate.isEmpty()||pass.isEmpty())
            { errLbl.setText("All fields are required!"); return; }
            if(!name.matches("[a-zA-Z\\s]+")){ errLbl.setText("Name must contain letters only."); return; }
            if(!RegularUser.isValidCNIC(cnic)){ errLbl.setText("CNIC must be exactly 13 digits."); return; }
            if(RegularUser.cnicUserMap.containsKey(cnic)){ errLbl.setText("A user with this CNIC already exists."); return; }
            if(!RegularUser.isValidPakistaniNumber(contact)){ errLbl.setText("Contact must be 03XXXXXXXXX format."); return; }
            if(pass.length()<4){ errLbl.setText("Password must be at least 4 characters."); return; }
            RegularUser newUser = new RegularUser(name,contact,vType,plate,cnic,pass);
            JOptionPane.showMessageDialog(f,"Account created! Welcome, "+name+"!","Success",JOptionPane.INFORMATION_MESSAGE);
            f.dispose();
            showUserDashboard(newUser);
        });
    }

    // =========================================================================
    //  5.  USER SIGN-IN
    // =========================================================================
    public void showUserSignIn()
    {
        JFrame f = new JFrame("User Sign In");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false); f.setLayout(null);
        f.setSize(1000,800);
        f.getContentPane().setBackground(BG);

        RoundedPanel left = new RoundedPanel(60,GREEN,GREEN,2);
        left.setLayout(null); left.setSize(500,765); left.setLocation(-50,0); f.add(left);

        JLabel ltitle = new JLabel("USER PORTAL");
        ltitle.setForeground(Color.WHITE); ltitle.setFont(new Font("Arial",Font.BOLD,35));
        ltitle.setBounds(115,370,300,50); ltitle.setHorizontalAlignment(JLabel.CENTER); left.add(ltitle);

        JLabel ltag = new JLabel("Welcome back! Sign in to continue.");
        ltag.setForeground(new Color(220,255,220)); ltag.setFont(new Font("Segoe UI",Font.ITALIC,14));
        ltag.setBounds(80,425,340,25); ltag.setHorizontalAlignment(JLabel.CENTER); left.add(ltag);

        JButton backBtn = new JButton("← Back to main menu");
        backBtn.setForeground(Color.WHITE); backBtn.setBounds(10,10,250,35);
        backBtn.setBorderPainted(false); backBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        backBtn.setOpaque(false); backBtn.setFocusPainted(false); backBtn.setContentAreaFilled(false);
        left.add(backBtn);

        JButton switchBtn = new JButton("Don't have an account? Sign Up →");
        switchBtn.setForeground(new Color(220,255,220)); switchBtn.setBounds(80,470,330,28);
        switchBtn.setBorderPainted(false); switchBtn.setFont(new Font("Segoe UI",Font.PLAIN,13));
        switchBtn.setOpaque(false); switchBtn.setFocusPainted(false); switchBtn.setContentAreaFilled(false);
        left.add(switchBtn);

        f.setVisible(true);

        RoundedPanel form = new RoundedPanel(25);
        form.setLayout(null); form.setBackground(new Color(245,245,245));
        form.setBounds(530,205,390,360); f.add(form);

        JLabel ftitle = new JLabel("Welcome Back");
        ftitle.setBounds(0,22,390,38); ftitle.setHorizontalAlignment(JLabel.CENTER);
        ftitle.setFont(new Font("Segoe UI",Font.BOLD,28)); form.add(ftitle);

        JLabel fsub = new JLabel("Sign in with your CNIC and password");
        fsub.setBounds(0,60,390,20); fsub.setHorizontalAlignment(JLabel.CENTER);
        fsub.setFont(new Font("Segoe UI",Font.PLAIN,13)); fsub.setForeground(Color.GRAY); form.add(fsub);

        form.add(makeFieldLabel("CNIC",30,96));
        RoundedTextField cnicF = makeField(30,119,330,42); form.add(cnicF);

        form.add(makeFieldLabel("Password",30,175));
        RoundedPasswordField passF = makePassField(30,198,330,42); form.add(passF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,252,330,18); errLbl.setHorizontalAlignment(JLabel.CENTER); form.add(errLbl);

        RoundedButton signInBtn = makeBtn("Sign In",GREEN,30,278,330,46); form.add(signInBtn);

        form.revalidate(); form.repaint();

        backBtn.addActionListener(e -> { f.dispose(); mainFrame.setVisible(true); });
        switchBtn.addActionListener(e -> { f.dispose(); showUserSignUp(); });

        ActionListener doLogin = e -> {
            String cnic=cnicF.getText().trim(), pass=new String(passF.getPassword());
            if(cnic.isEmpty()||pass.isEmpty()){ errLbl.setText("Both fields are required!"); return; }
            RegularUser user = RegularUser.cnicUserMap.get(cnic);
            if(user!=null && user.validateLogin(cnic,pass)){ f.dispose(); showUserDashboard(user); }
            else { errLbl.setText("Incorrect CNIC or password."); passF.setText(""); }
        };
        signInBtn.addActionListener(doLogin);
        passF.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){ if(e.getKeyCode()==KeyEvent.VK_ENTER) signInBtn.doClick(); }
        });
    }

    // =========================================================================
    //  6.  USER DASHBOARD
    // =========================================================================
    public void showUserDashboard(RegularUser user)
    {
        JFrame dash = new JFrame("User Dashboard — "+user.getName());
        dash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dash.setResizable(false); dash.setLayout(null);
        dash.setSize(1000,800);
        dash.getContentPane().setBackground(BG);

        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(GREEN);
        sidebar.setBounds(-20,0,270,800);
        dash.add(sidebar);
        dash.setVisible(true);

        JLabel sTitle = new JLabel("User Dashboard");
        sTitle.setBounds(-1,-1,270,75);
        sTitle.setFont(new Font("Segoe UI",Font.BOLD,18));
        sTitle.setForeground(Color.WHITE);
        sTitle.setHorizontalAlignment(JLabel.CENTER);
        sTitle.setBorder(new MatteBorder(0,0,2,0,Color.WHITE));
        sidebar.add(sTitle);

        String[] btnLabels = {
            "Overview","Book Slot","Checkout",
            "Parking History","View Bill","Pay Bill",
            "Wallet","Top Up","Change Password","Logout"
        };

        int bx=25, bw=220, bh=42, gap=10, sy=82;
        RoundedButton[] btns = new RoundedButton[btnLabels.length];

        for (int i=0; i<btnLabels.length; i++)
        {
            RoundedButton b = new RoundedButton(btnLabels[i]);
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setBorder(BorderFactory.createEmptyBorder(0,18,0,0));
            b.setBackground(GREEN); b.setForeground(Color.WHITE);
            b.setFont(new Font("Segoe UI",Font.PLAIN,14));
            b.setBounds(bx, sy+i*(bh+gap), bw, bh);
            if(i==btnLabels.length-1) b.setForeground(new Color(255,200,200));
            btns[i]=b; sidebar.add(b);
            final int idx=i;
            b.addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){ b.setBackground(Color.WHITE); b.setForeground(GREEN); b.repaint(); }
                public void mouseExited(MouseEvent e){
                    b.setBackground(GREEN);
                    b.setForeground(idx==btnLabels.length-1?new Color(255,200,200):Color.WHITE);
                    b.repaint();
                }
            });
        }

        showUserOverview(dash, user);

        btns[0].addActionListener(e -> showUserOverview(dash,user));
        btns[1].addActionListener(e -> showBookSlot(dash,user));
        btns[2].addActionListener(e -> showCheckout(dash,user));
        btns[3].addActionListener(e -> showParkingHistory(dash,user));
        btns[4].addActionListener(e -> showViewBill(dash,user));
        btns[5].addActionListener(e -> showPayBill(dash,user));
        btns[6].addActionListener(e -> showWallet(dash,user));
        btns[7].addActionListener(e -> showTopUp(dash,user));
        btns[8].addActionListener(e -> showChangePassword(dash,user));
        btns[9].addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(dash,"Logout?","Logout",JOptionPane.YES_NO_OPTION);
            if(c==JOptionPane.YES_OPTION){ dash.dispose(); mainFrame.setVisible(true); }
        });
    }

    // ── User Overview ──────────────────────────────────────────────────────────
    private void showUserOverview(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Welcome, "+user.getName()+"!"));
        p.add(makeSep(68));

        // Stat cards
        String[] titles = {"Parking Sessions","Wallet Balance","Current Status"};
        String[] vals   = {
            String.valueOf(user.getParkingHistory().size()),
            "Rs "+String.format("%.0f",user.getWalletBalance()),
            user.isParked ? "Parked" : "Not Parked"
        };
        Color[] bgs   = {new Color(232,245,233),new Color(227,242,253),new Color(243,229,245)};
        Color[] fgs   = {new Color(30,130,50),BLUE,new Color(120,40,180)};

        for (int i=0; i<3; i++)
        {
            RoundedPanel card = makeCard(30+i*228,88,210,95,bgs[i]);
            JLabel t = new JLabel(titles[i]); t.setFont(new Font("Segoe UI",Font.PLAIN,12)); t.setForeground(Color.GRAY); t.setBounds(12,12,186,18); card.add(t);
            JLabel v = new JLabel(vals[i]); v.setFont(new Font("Segoe UI",Font.BOLD,20)); v.setForeground(fgs[i]); v.setBounds(12,36,186,30); card.add(v);
            p.add(card);
        }

        // Active session
        JLabel acTitle = new JLabel("Active Session");
        acTitle.setFont(new Font("Segoe UI",Font.BOLD,15)); acTitle.setBounds(30,205,300,25); p.add(acTitle);
        p.add(makeSep(235));

        if (user.getCurrentParking()!=null)
        {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
            RoundedPanel sc = makeCard(30,248,680,75,new Color(255,248,225));
            JLabel si = new JLabel("<html><b>Site:</b> "+user.getCurrentParking().getsiteId()+
                "  &nbsp;  <b>Slot:</b> "+user.bookedSlot+
                "  &nbsp;  <b>Checked in:</b> "+user.getCurrentParking().getParkIn().format(fmt)+"</html>");
            si.setFont(new Font("Segoe UI",Font.PLAIN,13)); si.setBounds(12,12,656,50); sc.add(si); p.add(sc);
        }
        else
        {
            JLabel ns = new JLabel("No active parking session."); ns.setFont(new Font("Segoe UI",Font.ITALIC,13)); ns.setForeground(Color.GRAY); ns.setBounds(30,250,400,25); p.add(ns);
        }

        // Recent history
        JLabel rh = new JLabel("Recent History");
        rh.setFont(new Font("Segoe UI",Font.BOLD,15)); rh.setBounds(30,348,300,25); p.add(rh);
        p.add(makeSep(378));

        ArrayList<ParkingRecord> hist = user.getParkingHistory();
        if (hist.isEmpty())
        {
            JLabel none = new JLabel("No parking history yet."); none.setFont(new Font("Segoe UI",Font.ITALIC,13)); none.setForeground(Color.GRAY); none.setBounds(30,390,400,25); p.add(none);
        }
        else
        {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
            int shown = Math.min(4,hist.size());
            for (int i=0; i<shown; i++)
            {
                ParkingRecord rec = hist.get(hist.size()-1-i);
                RoundedPanel rc = makeCard(30,392+i*72,680,62,new Color(248,250,255));
                JLabel ri = new JLabel("<html><b>Site:</b> "+rec.getsiteId()+"  &nbsp;  <b>In:</b> "+rec.getParkIn().format(fmt)+"  &nbsp;  <b>Duration:</b> "+rec.getDurationInMinutes()+" min  &nbsp;  <b>Bill:</b> Rs "+String.format("%.0f",rec.getSessionBill())+"  &nbsp;  "+(rec.isPaid()?"<font color='green'>Paid</font>":"<font color='orange'>Unpaid</font>")+"</html>");
                ri.setFont(new Font("Segoe UI",Font.PLAIN,13)); ri.setBounds(12,10,656,42); rc.add(ri); p.add(rc);
            }
        }

        p.revalidate(); p.repaint();
    }

    // ── Book Slot ──────────────────────────────────────────────────────────────
    private void showBookSlot(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Book a Parking Slot"));
        p.add(makeSep(68));

        if (user.bookedSlot!=-1)
        {
            JLabel w = new JLabel("You already have an active booking at Slot #"+user.bookedSlot+". Check out first.");
            w.setFont(new Font("Segoe UI",Font.PLAIN,14)); w.setForeground(new Color(180,80,0)); w.setBounds(30,100,680,28); p.add(w);
            p.revalidate(); p.repaint(); return;
        }
        if (Admin.getInstance().hasNoSites())
        {
            JLabel w = new JLabel("No parking sites available. Please contact the admin.");
            w.setFont(new Font("Segoe UI",Font.PLAIN,14)); w.setForeground(Color.RED); w.setBounds(30,100,680,28); p.add(w);
            p.revalidate(); p.repaint(); return;
        }

        p.add(makeFieldLabel("Site ID",30,90));
        RoundedTextField siteF = makeField(30,113,250,42); p.add(siteF);
        RoundedButton lookupBtn = makeBtn("Look Up",BLUE,295,113,110,42); p.add(lookupBtn);

        RoundedPanel siteInfo = makeCard(30,170,655,85,new Color(232,242,255));
        siteInfo.setVisible(false); p.add(siteInfo);
        JLabel siteInfoLbl = new JLabel(); siteInfoLbl.setFont(new Font("Segoe UI",Font.PLAIN,13)); siteInfoLbl.setBounds(12,10,631,65); siteInfo.add(siteInfoLbl);

        p.add(makeFieldLabel("Slot Number",30,272));
        RoundedTextField slotF = makeField(30,295,180,42); p.add(slotF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,348,600,20); p.add(errLbl);

        RoundedButton bookBtn = makeBtn("Confirm Booking",BLUE,30,378,220,48);
        bookBtn.setEnabled(false); p.add(bookBtn);

        final ParkingSite[] sel = {null};

        lookupBtn.addActionListener(e -> {
            String sid = siteF.getText().trim();
            ParkingSite site = Admin.getInstance().returnSiteById(sid);
            if(site==null){ errLbl.setText("Site not found."); siteInfo.setVisible(false); bookBtn.setEnabled(false); return; }
            sel[0]=site; errLbl.setText("");
            siteInfoLbl.setText("<html><b>Location:</b> "+site.getSiteLocation()+"  &nbsp;  <b>Capacity:</b> "+site.getMaxSiteCapacity()+"  &nbsp;  <b>Rate:</b> Rs "+site.getHourlyRate()+"/hr</html>");
            siteInfo.setVisible(true); bookBtn.setEnabled(true); p.revalidate(); p.repaint();
        });

        bookBtn.addActionListener(e -> {
            if(sel[0]==null){ errLbl.setText("Look up a site first."); return; }
            int slotNo;
            try{ slotNo=Integer.parseInt(slotF.getText().trim()); }catch(Exception ex){ errLbl.setText("Enter a valid slot number."); return; }
            if(slotNo<1||slotNo>sel[0].getMaxSiteCapacity()){ errLbl.setText("Slot number out of range."); return; }
            if(!sel[0].isSlotAvailable(slotNo-1)){ errLbl.setText("Slot is already occupied."); return; }
            int booked = sel[0].bookSlotAt(user.getVehiclNo(),sel[0].getSiteID(),slotNo-1);
            if(booked>0){ user.bookedSlot=booked; JOptionPane.showMessageDialog(dash,"Slot #"+booked+" booked successfully!","Booking Confirmed",JOptionPane.INFORMATION_MESSAGE); showUserOverview(dash,user); }
            else if(booked==-1) errLbl.setText("Site is not operational.");
            else errLbl.setText("Booking failed. Try again.");
        });

        p.revalidate(); p.repaint();
    }

    // ── Checkout ───────────────────────────────────────────────────────────────
    private void showCheckout(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Check Out"));
        p.add(makeSep(68));

        if (user.getCurrentParking()==null)
        {
            JLabel l = new JLabel("You don't have an active parking session.");
            l.setFont(new Font("Segoe UI",Font.PLAIN,14)); l.setForeground(Color.GRAY); l.setBounds(30,100,600,28); p.add(l);
            p.revalidate(); p.repaint(); return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss");
        RoundedPanel card = makeCard(30,90,680,160,new Color(255,248,225));
        JLabel info = new JLabel("<html><b>Site:</b> "+user.getCurrentParking().getsiteId()+"<br><b>Slot:</b> "+user.bookedSlot+"<br><b>Checked In:</b> "+user.getCurrentParking().getParkIn().format(fmt)+"<br><b>Duration so far:</b> "+user.getCurrentParking().getDurationInMinutes()+" minutes</html>");
        info.setFont(new Font("Segoe UI",Font.PLAIN,14)); info.setBounds(18,15,644,130); card.add(info); p.add(card);

        JLabel conf = new JLabel("Confirm checkout?");
        conf.setFont(new Font("Segoe UI",Font.BOLD,15)); conf.setBounds(30,275,400,25); p.add(conf);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,310,600,20); p.add(errLbl);

        RoundedButton btn = makeBtn("Confirm Check Out",GREEN,30,340,220,48); p.add(btn);
        btn.addActionListener(e -> {
            ParkingSite site = Admin.getInstance().returnSiteById(user.getCurrentParking().getsiteId());
            if(site!=null && site.releaseSlot(user.getVehiclNo()))
            { user.bookedSlot=-1; JOptionPane.showMessageDialog(dash,"Checked out successfully! Bill added to your account.","Done",JOptionPane.INFORMATION_MESSAGE); showUserOverview(dash,user); }
            else errLbl.setText("Error releasing slot. Try again.");
        });

        p.revalidate(); p.repaint();
    }

    // ── Parking History ────────────────────────────────────────────────────────
    private void showParkingHistory(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Parking History"));
        p.add(makeSep(68));

        ArrayList<ParkingRecord> hist = user.getParkingHistory();
        if (hist.isEmpty())
        {
            JLabel none = new JLabel("No parking history found."); none.setFont(new Font("Segoe UI",Font.ITALIC,14)); none.setForeground(Color.GRAY); none.setBounds(30,100,400,28); p.add(none); p.revalidate(); p.repaint(); return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String[] cols = {"#","Site","Check In","Check Out","Duration","Bill","Status"};
        Object[][] data = new Object[hist.size()][7];
        for (int i=0; i<hist.size(); i++)
        {
            ParkingRecord r = hist.get(i);
            data[i][0]=i+1; data[i][1]=r.getsiteId();
            data[i][2]=r.getParkIn().format(fmt);
            data[i][3]=r.getParkOut()!=null?r.getParkOut().format(fmt):"Active";
            data[i][4]=r.getDurationInMinutes()+" min";
            data[i][5]="Rs "+String.format("%.0f",r.getSessionBill());
            data[i][6]=r.isPaid()?"Paid":"Unpaid";
        }

        JTable table = new JTable(data,cols){ public boolean isCellEditable(int r,int c){ return false; } };
        table.setFont(new Font("Segoe UI",Font.PLAIN,13)); table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13));
        table.getTableHeader().setBackground(GREEN); table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(220,220,220)); table.setSelectionBackground(new Color(200,240,210));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20,85,705,640); scroll.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        p.add(scroll); p.revalidate(); p.repaint();
    }

    // ── View Bill ──────────────────────────────────────────────────────────────
    private void showViewBill(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("View Due Bill"));
        p.add(makeSep(68));

        double total=0;
        ArrayList<ParkingRecord> unpaid = new ArrayList<>();
        for (ParkingRecord r : user.getParkingHistory()) if(!r.isPaid()){ total+=r.getSessionBill(); unpaid.add(r); }

        RoundedPanel tc = makeCard(30,88,260,90,new Color(227,242,253));
        JLabel tl = new JLabel("Total Outstanding"); tl.setFont(new Font("Segoe UI",Font.PLAIN,12)); tl.setForeground(Color.GRAY); tl.setBounds(12,10,236,18); tc.add(tl);
        JLabel tv = new JLabel("Rs "+String.format("%.2f",total)); tv.setFont(new Font("Segoe UI",Font.BOLD,24)); tv.setForeground(BLUE); tv.setBounds(12,34,236,35); tc.add(tv);
        p.add(tc);

        JLabel ut = new JLabel("Unpaid Sessions ("+unpaid.size()+")");
        ut.setFont(new Font("Segoe UI",Font.BOLD,15)); ut.setBounds(30,200,300,25); p.add(ut);
        p.add(makeSep(230));

        if (unpaid.isEmpty())
        { JLabel ok = new JLabel("All dues cleared!"); ok.setFont(new Font("Segoe UI",Font.ITALIC,14)); ok.setForeground(new Color(30,130,50)); ok.setBounds(30,245,300,28); p.add(ok); }
        else
        {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
            for (int i=0; i<Math.min(unpaid.size(),8); i++)
            {
                ParkingRecord rec = unpaid.get(i);
                RoundedPanel rc = makeCard(30,245+i*62,680,52,new Color(255,250,240));
                JLabel rl = new JLabel("<html><b>Site:</b> "+rec.getsiteId()+"  &nbsp;  <b>In:</b> "+rec.getParkIn().format(fmt)+"  &nbsp;  <b>Duration:</b> "+rec.getDurationInMinutes()+" min  &nbsp;  <b>Bill:</b> Rs "+String.format("%.2f",rec.getSessionBill())+"</html>");
                rl.setFont(new Font("Segoe UI",Font.PLAIN,13)); rl.setBounds(12,8,656,36); rc.add(rl); p.add(rc);
            }
        }

        p.revalidate(); p.repaint();
    }

    // ── Pay Bill ───────────────────────────────────────────────────────────────
    private void showPayBill(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Pay Bill"));
        p.add(makeSep(68));

        double total=0;
        for (ParkingRecord r : user.getParkingHistory()) if(!r.isPaid()) total+=r.getSessionBill();

        RoundedPanel tc = makeCard(30,88,260,90,new Color(227,242,253));
        JLabel tl = new JLabel("Total Due"); tl.setFont(new Font("Segoe UI",Font.PLAIN,12)); tl.setForeground(Color.GRAY); tl.setBounds(12,10,236,18); tc.add(tl);
        JLabel tv = new JLabel("Rs "+String.format("%.2f",total)); tv.setFont(new Font("Segoe UI",Font.BOLD,24)); tv.setForeground(BLUE); tv.setBounds(12,34,236,35); tc.add(tv);
        p.add(tc);

        RoundedPanel wc = makeCard(310,88,260,90,new Color(232,245,233));
        JLabel wl = new JLabel("Wallet Balance"); wl.setFont(new Font("Segoe UI",Font.PLAIN,12)); wl.setForeground(Color.GRAY); wl.setBounds(12,10,236,18); wc.add(wl);
        JLabel wv = new JLabel("Rs "+String.format("%.2f",user.getWalletBalance())); wv.setFont(new Font("Segoe UI",Font.BOLD,24)); wv.setForeground(new Color(30,130,50)); wv.setBounds(12,34,236,35); wc.add(wv);
        p.add(wc);

        if (total==0)
        { JLabel ok = new JLabel("All dues cleared! Nothing to pay."); ok.setFont(new Font("Segoe UI",Font.ITALIC,14)); ok.setForeground(new Color(30,130,50)); ok.setBounds(30,205,500,28); p.add(ok); p.revalidate(); p.repaint(); return; }

        JLabel ml = new JLabel("Select Payment Method");
        ml.setFont(new Font("Segoe UI",Font.BOLD,15)); ml.setBounds(30,210,300,25); p.add(ml);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,415,600,20); p.add(errLbl);

        RoundedButton cashBtn   = makeBtn("Pay by Cash",GREEN,30,250,190,52); p.add(cashBtn);
        RoundedButton walletBtn = makeBtn("Pay by Wallet",BLUE,240,250,190,52); p.add(walletBtn);

        double ft = total;
        cashBtn.addActionListener(e -> {
            ArrayList<ParkingRecord> up = new ArrayList<>();
            for (ParkingRecord r : user.getParkingHistory()) if(!r.isPaid()) up.add(r);
            for (ParkingRecord r : up) r.markAsPaid("Cash");
            JOptionPane.showMessageDialog(dash,"Rs "+String.format("%.2f",ft)+" paid via Cash. All dues cleared!","Success",JOptionPane.INFORMATION_MESSAGE);
            showUserOverview(dash,user);
        });
        walletBtn.addActionListener(e -> {
            if(user.getWalletBalance()<ft){ errLbl.setText("Insufficient wallet balance. Top up first."); return; }
            user.withdraw(ft);
            ArrayList<ParkingRecord> up = new ArrayList<>();
            for (ParkingRecord r : user.getParkingHistory()) if(!r.isPaid()) up.add(r);
            for (ParkingRecord r : up) r.markAsPaid("App Pay");
            JOptionPane.showMessageDialog(dash,"Rs "+String.format("%.2f",ft)+" deducted from wallet. All dues cleared!","Success",JOptionPane.INFORMATION_MESSAGE);
            showUserOverview(dash,user);
        });

        p.revalidate(); p.repaint();
    }

    // ── Wallet ─────────────────────────────────────────────────────────────────
    private void showWallet(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("My Wallet"));
        p.add(makeSep(68));

        RoundedPanel card = makeCard(30,88,500,125,BLUE);
        JLabel wt = new JLabel("Current Balance"); wt.setFont(new Font("Segoe UI",Font.PLAIN,13)); wt.setForeground(new Color(200,230,255)); wt.setBounds(18,16,464,20); card.add(wt);
        JLabel wv = new JLabel("Rs "+String.format("%.2f",user.getWalletBalance())); wv.setFont(new Font("Segoe UI",Font.BOLD,36)); wv.setForeground(Color.WHITE); wv.setBounds(18,42,464,50); card.add(wv);
        p.add(card);

        JLabel note = new JLabel("Use your wallet balance to pay for parking bills instantly.");
        note.setFont(new Font("Segoe UI",Font.ITALIC,13)); note.setForeground(Color.GRAY); note.setBounds(30,235,580,22); p.add(note);

        RoundedButton topUp = makeBtn("Top Up Wallet",GREEN,30,270,180,46); p.add(topUp);
        topUp.addActionListener(e -> showTopUp(dash,user));

        p.revalidate(); p.repaint();
    }

    // ── Top Up ─────────────────────────────────────────────────────────────────
    private void showTopUp(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Top Up Wallet"));
        p.add(makeSep(68));

        JLabel bal = new JLabel("Current Balance: Rs "+String.format("%.2f",user.getWalletBalance()));
        bal.setFont(new Font("Segoe UI",Font.PLAIN,14)); bal.setForeground(Color.GRAY); bal.setBounds(30,88,400,25); p.add(bal);

        p.add(makeFieldLabel("Amount to Deposit (Rs)",30,128));
        RoundedTextField amtF = makeField(30,151,240,42); p.add(amtF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,205,500,20); p.add(errLbl);

        RoundedButton depBtn = makeBtn("Add to Wallet",BLUE,30,233,190,46); p.add(depBtn);

        JLabel ql = new JLabel("Quick amounts:");
        ql.setFont(new Font("Segoe UI",Font.PLAIN,13)); ql.setForeground(Color.GRAY); ql.setBounds(30,302,200,20); p.add(ql);

        int[] qamts = {500,1000,2000,5000};
        for (int i=0; i<qamts.length; i++)
        {
            RoundedButton qb = makeBtn("Rs "+qamts[i],GREEN,30+i*160,328,145,38);
            qb.setFont(new Font("Segoe UI",Font.PLAIN,13)); p.add(qb);
            int amt=qamts[i];
            qb.addActionListener(e -> amtF.setText(String.valueOf(amt)));
        }

        depBtn.addActionListener(e -> {
            double amt;
            try{ amt=Double.parseDouble(amtF.getText().trim()); }catch(Exception ex){ errLbl.setText("Enter a valid number."); return; }
            if(amt<=0){ errLbl.setText("Amount must be greater than 0."); return; }
            user.deposit(amt);
            JOptionPane.showMessageDialog(dash,"Rs "+String.format("%.2f",amt)+" added!\nNew balance: Rs "+String.format("%.2f",user.getWalletBalance()),"Top Up Successful",JOptionPane.INFORMATION_MESSAGE);
            showWallet(dash,user);
        });

        p.revalidate(); p.repaint();
    }

    // ── Change Password ────────────────────────────────────────────────────────
    private void showChangePassword(JFrame dash, RegularUser user)
    {
        JPanel p = swapPanel(dash);
        p.add(makeHeader("Change Password"));
        p.add(makeSep(68));

        p.add(makeFieldLabel("Current Password",30,100));
        RoundedPasswordField oldP = makePassField(30,123,330,42); p.add(oldP);

        p.add(makeFieldLabel("New Password",30,182));
        RoundedPasswordField newP = makePassField(30,205,330,42); p.add(newP);

        p.add(makeFieldLabel("Confirm New Password",30,264));
        RoundedPasswordField confP = makePassField(30,287,330,42); p.add(confP);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,340,500,20); p.add(errLbl);

        RoundedButton upBtn = makeBtn("Update Password",BLUE,30,368,210,46); p.add(upBtn);
        upBtn.addActionListener(e -> {
            String op=new String(oldP.getPassword()), np=new String(newP.getPassword()), cp=new String(confP.getPassword());
            if(!op.equals(user.getPassword())){ errLbl.setText("Current password is incorrect."); return; }
            if(np.length()<4){ errLbl.setText("New password must be at least 4 characters."); return; }
            if(!np.equals(cp)){ errLbl.setText("New passwords do not match."); return; }
            user.setPassword(np);
            JOptionPane.showMessageDialog(dash,"Password updated successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
            oldP.setText(""); newP.setText(""); confP.setText(""); errLbl.setText("");
        });

        p.revalidate(); p.repaint();
    }

    // =========================================================================
    //  7.  WALK-IN PORTAL
    // =========================================================================
    public void showWalkInPortal()
    {
        JFrame f = new JFrame("Walk-In Customer Portal");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false); f.setLayout(null);
        f.setSize(1000,800);
        f.getContentPane().setBackground(BG);

        RoundedPanel left = new RoundedPanel(60,ORANGE,ORANGE,2);
        left.setLayout(null); left.setSize(500,765); left.setLocation(-50,0); f.add(left);

        JLabel ltitle = new JLabel("WALK-IN PORTAL");
        ltitle.setForeground(Color.WHITE); ltitle.setFont(new Font("Arial",Font.BOLD,32));
        ltitle.setBounds(80,360,320,50); ltitle.setHorizontalAlignment(JLabel.CENTER); left.add(ltitle);

        JLabel ltag = new JLabel("Quick check-in, pay on exit.");
        ltag.setForeground(new Color(255,230,200)); ltag.setFont(new Font("Segoe UI",Font.ITALIC,14));
        ltag.setBounds(90,415,300,25); ltag.setHorizontalAlignment(JLabel.CENTER); left.add(ltag);

        JButton backBtn = new JButton("← Back to main menu");
        backBtn.setForeground(Color.WHITE); backBtn.setBounds(10,10,250,35);
        backBtn.setBorderPainted(false); backBtn.setFont(new Font("Segoe UI",Font.BOLD,13));
        backBtn.setOpaque(false); backBtn.setFocusPainted(false); backBtn.setContentAreaFilled(false);
        left.add(backBtn);

        f.setVisible(true);

        // Check-In card
        RoundedPanel ciCard = makeCard(520,170,400,170,new Color(255,248,240));
        JLabel cit = new JLabel("Check In"); cit.setFont(new Font("Segoe UI",Font.BOLD,22)); cit.setBounds(18,16,364,30); ciCard.add(cit);
        JLabel cid = new JLabel("<html>Register your vehicle and book<br>a slot at any available site.</html>"); cid.setFont(new Font("Segoe UI",Font.PLAIN,13)); cid.setForeground(Color.GRAY); cid.setBounds(18,50,364,45); ciCard.add(cid);
        RoundedButton ciBtn = makeBtn("Check In Now",ORANGE,18,106,170,38); ciBtn.setFont(new Font("Segoe UI",Font.BOLD,13)); ciCard.add(ciBtn);
        f.add(ciCard);

        // Check-Out card
        RoundedPanel coCard = makeCard(520,380,400,170,new Color(240,255,245));
        JLabel cot = new JLabel("Check Out"); cot.setFont(new Font("Segoe UI",Font.BOLD,22)); cot.setBounds(18,16,364,30); coCard.add(cot);
        JLabel cod = new JLabel("<html>Exit the parking site and pay<br>your bill by cash or card.</html>"); cod.setFont(new Font("Segoe UI",Font.PLAIN,13)); cod.setForeground(Color.GRAY); cod.setBounds(18,50,364,45); coCard.add(cod);
        RoundedButton coBtn = makeBtn("Check Out Now",GREEN,18,106,170,38); coBtn.setFont(new Font("Segoe UI",Font.BOLD,13)); coCard.add(coBtn);
        f.add(coCard);

        backBtn.addActionListener(e -> { f.dispose(); mainFrame.setVisible(true); });
        ciBtn.addActionListener(e -> showWalkInCheckIn(f));
        coBtn.addActionListener(e -> showWalkInCheckOut(f));

        f.revalidate(); f.repaint();
    }

    // ── Walk-In Check-In ───────────────────────────────────────────────────────
    private void showWalkInCheckIn(JFrame parent)
    {
        JFrame f = new JFrame("Walk-In Check In");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false); f.setLayout(null);
        f.setSize(650,730);
        f.getContentPane().setBackground(BG);
        f.setLocationRelativeTo(parent);

        JLabel title = new JLabel("Walk-In Check In");
        title.setFont(new Font("Segoe UI",Font.BOLD,24)); title.setBounds(30,18,400,35); f.add(title);
        JSeparator sep = new JSeparator(); sep.setBounds(0,60,650,2); sep.setForeground(new Color(210,210,210)); f.add(sep);

        if (Admin.getInstance().hasNoSites())
        {
            JLabel w = new JLabel("No parking sites available. Contact admin."); w.setFont(new Font("Segoe UI",Font.ITALIC,14)); w.setForeground(Color.RED); w.setBounds(30,80,560,28); f.add(w); f.setVisible(true); return;
        }

        int fx=30, fw=570, fh=40, sy=75, gap=65;
        String[] lbls = {"Full Name","Contact No (03XXXXXXXXX)","Vehicle Type","License Plate No"};
        RoundedTextField[] fields = new RoundedTextField[4];
        for (int i=0; i<4; i++)
        {
            JLabel l = new JLabel(lbls[i]); l.setFont(new Font("Segoe UI",Font.BOLD,13)); l.setBounds(fx,sy+gap*i,fw,20); f.add(l);
            fields[i] = makeField(fx,sy+gap*i+22,fw,fh); f.add(fields[i]);
        }

        JLabel sl = new JLabel("Site ID"); sl.setFont(new Font("Segoe UI",Font.BOLD,13)); sl.setBounds(fx,sy+gap*4,200,20); f.add(sl);
        RoundedTextField siteF = makeField(fx,sy+gap*4+22,220,fh); f.add(siteF);

        JLabel sll = new JLabel("Slot Number"); sll.setFont(new Font("Segoe UI",Font.BOLD,13)); sll.setBounds(fx,sy+gap*5,200,20); f.add(sll);
        RoundedTextField slotF = makeField(fx,sy+gap*5+22,180,fh); f.add(slotF);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(fx,sy+gap*6+5,fw,20); f.add(errLbl);

        RoundedButton btn = makeBtn("Confirm Check In",ORANGE,fx,sy+gap*6+28,220,46); f.add(btn);
        f.setVisible(true);

        btn.addActionListener(e -> {
            String name=fields[0].getText().trim(), contact=fields[1].getText().trim();
            String vType=fields[2].getText().trim(), plate=fields[3].getText().trim();
            String sid=siteF.getText().trim(), slotTxt=slotF.getText().trim();

            if(name.isEmpty()||contact.isEmpty()||vType.isEmpty()||plate.isEmpty()||sid.isEmpty()||slotTxt.isEmpty())
            { errLbl.setText("All fields are required."); return; }
            if(!name.matches("[a-zA-Z\\s]+")){ errLbl.setText("Name must contain letters only."); return; }
            if(!RegularUser.isValidPakistaniNumber(contact)){ errLbl.setText("Contact must be 03XXXXXXXXX format."); return; }
            if(User.getUserByVehicleNo(plate)!=null){ errLbl.setText("This vehicle is already in the system!"); return; }
            ParkingSite site = Admin.getInstance().returnSiteById(sid);
            if(site==null){ errLbl.setText("Site not found."); return; }
            int slotNo;
            try{ slotNo=Integer.parseInt(slotTxt); }catch(Exception ex){ errLbl.setText("Slot number must be a whole number."); return; }
            if(slotNo<1||slotNo>site.getMaxSiteCapacity()){ errLbl.setText("Slot out of range (1–"+site.getMaxSiteCapacity()+")."); return; }
            if(!site.isSlotAvailable(slotNo-1)){ errLbl.setText("Slot "+slotNo+" is already occupied."); return; }

            WalkInUser wu = new WalkInUser(name,contact,plate,vType,sid);
            int booked = site.bookSlotAt(plate,sid,slotNo-1);
            if(booked>0)
            {
                wu.bookedSlot=booked;
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss");
                JOptionPane.showMessageDialog(f,"Check-in successful!\nSlot: "+booked+"\nSite: "+site.getSiteLocation()+"\nTime: "+wu.getCurrentParking().getParkIn().format(fmt),"Check-In Confirmed",JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
            }
            else if(booked==-1) errLbl.setText("Site is not operational.");
            else errLbl.setText("Booking failed.");
        });
    }

    // ── Walk-In Check-Out ──────────────────────────────────────────────────────
    private void showWalkInCheckOut(JFrame parent)
    {
        JFrame f = new JFrame("Walk-In Check Out");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false); f.setLayout(null);
        f.setSize(650,680);
        f.getContentPane().setBackground(BG);
        f.setLocationRelativeTo(parent);

        JLabel title = new JLabel("Walk-In Check Out");
        title.setFont(new Font("Segoe UI",Font.BOLD,24)); title.setBounds(30,18,400,35); f.add(title);
        JSeparator sep = new JSeparator(); sep.setBounds(0,60,650,2); sep.setForeground(new Color(210,210,210)); f.add(sep);

        JLabel pl = new JLabel("License Plate Number"); pl.setFont(new Font("Segoe UI",Font.BOLD,13)); pl.setBounds(30,80,300,20); f.add(pl);
        RoundedTextField plateF = makeField(30,103,290,42); f.add(plateF);
        RoundedButton lookBtn = makeBtn("Look Up",BLUE,335,103,110,42); f.add(lookBtn);

        JLabel errLbl = new JLabel();
        errLbl.setForeground(Color.RED); errLbl.setFont(new Font("Segoe UI",Font.ITALIC,12));
        errLbl.setBounds(30,158,560,20); f.add(errLbl);

        RoundedPanel invoice = makeCard(30,165,570,270,new Color(255,250,240));
        invoice.setVisible(false); f.add(invoice);

        JLabel invTitle = new JLabel("Invoice");
        invTitle.setFont(new Font("Segoe UI",Font.BOLD,18)); invTitle.setBounds(18,10,534,28); invoice.add(invTitle);
        JLabel invDetails = new JLabel(); invDetails.setFont(new Font("Segoe UI",Font.PLAIN,13)); invDetails.setBounds(18,42,534,195); invoice.add(invDetails);

        JLabel payLbl = new JLabel("Select Payment Method");
        payLbl.setFont(new Font("Segoe UI",Font.BOLD,13)); payLbl.setBounds(30,450,300,20); payLbl.setVisible(false); f.add(payLbl);

        RoundedButton cashBtn  = makeBtn("Cash",GREEN,30,476,130,44); cashBtn.setVisible(false); f.add(cashBtn);
        RoundedButton cardBtn  = makeBtn("Card",BLUE,175,476,130,44); cardBtn.setVisible(false); f.add(cardBtn);

        f.setVisible(true);

        final WalkInUser[] found = {null};

        lookBtn.addActionListener(e -> {
            String plate = plateF.getText().trim();
            if(plate.isEmpty()){ errLbl.setText("Enter a license plate number."); return; }
            User u = User.getUserByVehicleNo(plate);
            if(u==null||!(u instanceof WalkInUser)){ errLbl.setText("No active walk-in session for this plate."); invoice.setVisible(false); cashBtn.setVisible(false); cardBtn.setVisible(false); payLbl.setVisible(false); f.revalidate(); f.repaint(); return; }
            WalkInUser wu=(WalkInUser)u;
            if(!wu.isParked){ errLbl.setText("This vehicle has already checked out."); return; }
            found[0]=wu; errLbl.setText("");
            wu.currentParking.setParkOut(LocalDateTime.now());
            wu.billingCost();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss");
            invDetails.setText("<html><b>Name:</b> "+wu.getName()+"<br><b>Contact:</b> "+wu.getContactNo()+"<br><b>Vehicle:</b> "+wu.getVehicleType()+" — "+wu.getVehiclNo()+"<br><b>Site:</b> "+wu.siteId+"  &nbsp;  <b>Slot:</b> "+wu.bookedSlot+"<br><b>Check-In:</b> "+wu.currentParking.getParkIn().format(fmt)+"<br><b>Check-Out:</b> "+wu.currentParking.getParkOut().format(fmt)+"<br><b>Duration:</b> "+wu.currentParking.getDurationInMinutes()+" minutes<br><b>Total Bill: Rs "+String.format("%.2f",wu.bill)+"</b></html>");
            invoice.setVisible(true); payLbl.setVisible(true); cashBtn.setVisible(true); cardBtn.setVisible(true);
            f.revalidate(); f.repaint();
        });

        ActionListener pay = e -> {
            if(found[0]==null) return;
            WalkInUser wu=found[0];
            String method=(e.getSource()==cashBtn)?"Cash":"Card";
            wu.currentParking.markAsPaid(method);
            wu.parkOut();
            ParkingSite site = Admin.getInstance().returnSiteById(wu.siteId);
            if(site!=null) site.releaseSlot(wu.getVehiclNo());
            wu.bookedSlot=-1;
            JOptionPane.showMessageDialog(f,"Payment of Rs "+String.format("%.2f",wu.bill)+" received via "+method+".\nThank you! Safe drive!","Check-Out Complete",JOptionPane.INFORMATION_MESSAGE);
            f.dispose();
        };
        cashBtn.addActionListener(pay);
        cardBtn.addActionListener(pay);
    }
}