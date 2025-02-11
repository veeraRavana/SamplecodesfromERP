package HarnessErp;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import common.Design;
import common.Utools;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;

public class AssetAuditMain extends JInternalFrame  implements ActionListener,InternalFrameListener, ListSelectionListener{
	
	Design des = new Design("Order_Planning");

	FinanceInterface finter=null;
	FinanceController fc=null;
	OuterFrame parent=null;
	InnerPanel innerPanel=null;
	String[] allnode=null;
	AssetAuditMain  THIS=null;
	
	private JPanel jContentPane = null;
	private JMenuBar jMenuBar = null;
	private JMenu jm_action = null;
	private JMenuItem jmi_add = null;
	private JMenuItem jmi_modify = null;
	private JMenuItem jmi_delete = null;
	private JMenuItem jmi_close = null;
	private JMenuItem jmi_View = null;
	private JMenuItem jmi_Refresh = null;
	private JLabel jl_AuditfrmDate = null;
	private JLabel jl_To = null;
	private JLabel jl_Status = null;
	private JComboBox jc_Status = null;
	private TDateField jdf_AuditDateFrom = null;
    private TDateField jdf_AuditDateTo = null;
	private JButton jb_Load=null;
	public SortSelectTable sst_AuditDetails = null;
	ListSelectionModel LstItem = null;
	public SortSelectTable sst_AssetAuditDetails = null;
    int mode=-1;
	

	public AssetAuditMain(FinanceInterface finter, FinanceController fc, OuterFrame parent, InnerPanel innerPanel,
			String[] allnode) {
		
		
		
		this.finter=finter;
		this.fc=fc;
		this.parent=parent;
		this.innerPanel=innerPanel;
		this.allnode=allnode;
		
		
		initialize();
		createMenuBar();
		
	}
	
	private void initialize() {
		this.setLayout(null);
		this.setContentPane(getJContentPane());
		this.setSize(950, 600);
		new Design("Order_Planning").setColors(this);
		des.setColors(this);

	}

	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jl_AuditfrmDate = new JLabel();
			jl_AuditfrmDate.setBounds(new Rectangle(100, 30, 100, 100));
			jl_AuditfrmDate.setText("Audit Date From");	 
			jl_To = new JLabel();
			jl_To.setBounds(new Rectangle(350, 30, 100, 100));
			jl_To.setText("To");	 
			jl_Status = new JLabel();
			jl_Status.setBounds(new Rectangle(550, 30, 100, 100));
			jl_Status.setText("Status");	
		
			
			
			
			
			jContentPane.add(jl_AuditfrmDate, null);
			jContentPane.add(jl_To, null);
			jContentPane.add(jl_Status, null);
			jContentPane.add(getjdf_AuditDateFrom(), null);
			jContentPane.add(getjdf_AuditDateTo(), null);
			jContentPane.add(getjc_Status(), null);
			jContentPane.add(getjb_Load(), null);
			jContentPane.add(getsst_AuditDetails(), null);
			jContentPane.add(getsst_AssetAuditDetails(), null);


	

		}
		return jContentPane;

	}
	

	public void createMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();

		}
		if (jmi_add == null) {
			jmi_add = new JMenuItem("Add");
			jmi_add.addActionListener(this);
		}
		if (jmi_modify == null) {
			jmi_modify = new JMenuItem("Modify");
			jmi_modify.addActionListener(this);
		}

		if (jmi_delete == null) {
			jmi_delete = new JMenuItem("Delete");
			jmi_delete.addActionListener(this);
		}

		if (jmi_View == null) {
			jmi_View = new JMenuItem("View");
			jmi_View.addActionListener(this);
		}

		if (jmi_close == null) {
			jmi_close = new JMenuItem("Close");
			jmi_close.addActionListener(this);
		}

		
		if (jmi_Refresh == null) {
			jmi_Refresh = new JMenuItem("Refresh");
			jmi_Refresh.addActionListener(this);
		}

		
	
		if (jm_action == null) {
			jm_action = new JMenu("Action");
			jm_action.add(jmi_add);
			jm_action.add(jmi_modify);
			jm_action.add(jmi_delete);
			jm_action.add(jmi_close);
			jm_action.add(jmi_View);
			jm_action.add(jmi_Refresh);

		}
		
		jMenuBar.add(jm_action);

		innerPanel.setMenuBar(jMenuBar);
		innerPanel.repaint();

	}
	
	
	private TDateField getjdf_AuditDateFrom() {
		if (jdf_AuditDateFrom == null) {
			jdf_AuditDateFrom = new TDateField();
			jdf_AuditDateFrom.setBounds(new Rectangle(200, 30, 100, 30));
		}
		return jdf_AuditDateFrom;
	}
	
	private TDateField getjdf_AuditDateTo() {
		if (jdf_AuditDateTo == null) {
			jdf_AuditDateTo = new TDateField();
			jdf_AuditDateTo.setBounds(new Rectangle(380, 30, 100, 30));
		}
		return jdf_AuditDateTo;
	}
	
	private JComboBox getjc_Status() {
		if (jc_Status == null) {
			jc_Status = new JComboBox();
			jc_Status.setBounds(new Rectangle(600,30,90, 50));
			jc_Status.setName("AssetAudit_jc_Status");
			jc_Status.addActionListener(this);
			Vector AC=new Vector();
			jc_Status.removeAllItems();
			jc_Status.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_Status.addItem(AC.get(i));

			}

		}
		return jc_Status;
	}


	private JButton getjb_Load() {
		if(jb_Load==null){
			jb_Load=new JButton();
			jb_Load.setBounds(750, 30, 70, 50);
			jb_Load.setText("Load");
			jb_Load.addActionListener(this);
		}
		return jb_Load;
	}
	
	
	private SortSelectTable getsst_AuditDetails() {
		if (sst_AuditDetails == null) {
			sst_AuditDetails = new SortSelectTable();
			sst_AuditDetails.setBounds(new Rectangle(30, 100, 700, 90));
			sst_AuditDetails.setName("AuditScheduless_sst_AuditDetails");
			fillAssetAuditDetails();
		}
		return sst_AuditDetails;
	}
	
	public void fillAssetAuditDetails() {
		try {
			Vector data=new Vector();
			data.add("Audit Id");
			data.add("Audit Due  Date");
			data.add("Audit Type ");
			data.add("Audit Profile");
			data.add("Audit Name");
			data.add("Period");
			data.add("Auditor Name");
			data.add("Audit Department");
			data.add("Total Asset");
			data.add("Audited Asset");
			data.add("Status");
			data.add("Select");

			
	
			sst_AuditDetails.setTable(null, data, "Order_Planning", "Audit Details", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());
//			sst_AuditDetails.setTable(null, data, "Order_Planning", "Audit Details", true);
			LstItem = sst_AuditDetails.table.getSelectionModel();
			sst_AuditDetails.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_AuditDetails.table.getModel().addTableModelListener(acctListener);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private TableModelListener acctListener = new TableModelListener() {
		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
			}
		}
	};
	
	
	private SortSelectTable getsst_AssetAuditDetails() {
		if (sst_AssetAuditDetails == null) {
			sst_AssetAuditDetails = new SortSelectTable();
			sst_AssetAuditDetails.setBounds(new Rectangle(30, 250, 900, 90));
			sst_AssetAuditDetails.setName("AuditScheduless_sst_AssetAuditDetails");
			fillAssetAuditDetails1();
		}
		return sst_AssetAuditDetails;
	}
	
	public void fillAssetAuditDetails1() {
		try {
			Vector data=new Vector();
			data.add("Schedule Id");
			data.add("Asset Code");
			data.add("Asset Classification ");
			data.add("Asset Type ");
			data.add("Department");
			data.add("Sub Department");
			data.add("Location");
			data.add("Area");
			data.add("Floor");
			data.add("sub Location3");
			data.add("Purchase");
			data.add("Currency");
			data.add("Depreciated Value");
			data.add("Book Value");
			data.add("Presence");
			data.add("Audit Flexi1");
			data.add("Audit Flexi2");
			data.add("Condition");
			data.add("Performance");
			data.add("Audit Flexi5");
			data.add("Audit Flexi6");
			data.add("Status");
			data.add("Select");

			
	
//			sst_AssetAuditDetails.setTable(null, data, "Order_Planning", "Audit Details", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());
			sst_AssetAuditDetails.setTable(null, data, "Order_Planning", "Asset Audit Details", true);
			LstItem = sst_AssetAuditDetails.table.getSelectionModel();
			sst_AssetAuditDetails.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_AssetAuditDetails.table.getModel().addTableModelListener(acctListener);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == jmi_add) {
			mode=0;
			parent.showMsg("");		
			AddorModAssetAudit assetAudit=new AddorModAssetAudit(THIS,innerPanel,finter,parent,mode);		
			innerPanel.getWindow(allnode).add(assetAudit);
			assetAudit.setVisible(true);
			Utools.setMouseNormal(getContentPane());
		}
		
		
		
		
	}

}
