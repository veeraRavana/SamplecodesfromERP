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

public class AssetDisposalMain extends JInternalFrame  implements ActionListener,InternalFrameListener, ListSelectionListener{
	
	
	Design des = new Design("Order_Planning");

	FinanceInterface finter=null;
	FinanceController fc=null;
	OuterFrame parent=null;
	InnerPanel innerPanel=null;
	String[] allnode=null;
	AssetDisposalMain  THIS=null;
	
	
	
	private JPanel jContentPane = null;
	private JMenuBar jMenuBar = null;
	private JMenu jm_action = null;
	private JMenuItem jmi_dispose = null;
	private JMenuItem jmi_Rewind = null;
	private JMenuItem jmi_Revoke = null;
	private JMenuItem jmi_View = null;
	private JMenuItem jmi_Refresh = null;
	private JLabel jl_DisposalFrmDate = null;
	private JLabel jl_To = null;
	private JLabel jl_Status = null;
	private JComboBox jc_Status = null;
	private TDateField jdf_DisposalDateFrom = null;
    private TDateField jdf_DisposalDateTo = null;
	private JButton jb_Load=null;
	public SortSelectTable sst_DisposalDetails = null;
	ListSelectionModel LstItem = null;
	public SortSelectTable sst_AssetDisposalDetails = null;
    int mode=-1;
	private JLabel jl_DisposalType = null;
	private JLabel jl_DisposalCategory = null;
	private JComboBox jc_DisposalType = null;
	private JComboBox jc_DisposalCategory = null;

	

	public AssetDisposalMain(FinanceInterface finter, FinanceController fc, OuterFrame parent, InnerPanel innerPanel,
			String[] allnode) {
		// TODO Auto-generated constructor stub
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
			
			
			jl_DisposalType = new JLabel();
			jl_DisposalType.setBounds(new Rectangle(100, 30, 100, 100));
			jl_DisposalType.setText("Disposal Type");		
			jl_DisposalCategory = new JLabel();
			jl_DisposalCategory.setBounds(new Rectangle(300, 30, 100, 100));
			jl_DisposalCategory.setText("Disposal Category");			
			jl_Status = new JLabel();
			jl_Status.setBounds(new Rectangle(550, 30, 100, 100));
			jl_Status.setText("Status");	
			jl_DisposalFrmDate = new JLabel();
			jl_DisposalFrmDate.setBounds(new Rectangle(100, 80, 100, 100));
			jl_DisposalFrmDate.setText("Disposal Date From");	 
			jl_To = new JLabel();
			jl_To.setBounds(new Rectangle(350, 80, 100, 100));
			jl_To.setText("To");	 
			
			
			jContentPane.add(jl_DisposalType, null);
			jContentPane.add(jl_DisposalCategory, null);
			jContentPane.add(jl_DisposalFrmDate, null);
			jContentPane.add(jl_To, null);
			jContentPane.add(jl_Status, null);
			
			jContentPane.add(getjc_DisposalType(), null);
			jContentPane.add(getjc_DisposalCategory(), null);
			jContentPane.add(getjdf_DisposalDateFrom(), null);
			jContentPane.add(getjdf_DisposalDateTo(), null);
			jContentPane.add(getjc_Status(), null);
			jContentPane.add(getjb_Load(), null);
			jContentPane.add(getsst_DisposalDetails(), null);
			jContentPane.add(getsst_AssetDisposalDetails(), null);


	

		}
		return jContentPane;

	}
	

	public void createMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();

		}
		if (jmi_dispose == null) {
			jmi_dispose = new JMenuItem("Dispose");
			jmi_dispose.addActionListener(this);
		}
		if (jmi_Rewind == null) {
			jmi_Rewind = new JMenuItem("Rewind");
			jmi_Rewind.addActionListener(this);
		}

		if (jmi_Revoke == null) {
			jmi_Revoke = new JMenuItem("Revoke");
			jmi_Revoke.addActionListener(this);
		}

		if (jmi_View == null) {
			jmi_View = new JMenuItem("View");
			jmi_View.addActionListener(this);
		}

		if (jmi_Refresh == null) {
			jmi_Refresh = new JMenuItem("Refresh");
			jmi_Refresh.addActionListener(this);
		}

		
	
		if (jm_action == null) {
			jm_action = new JMenu("Action");
			jm_action.add(jmi_dispose);
			jm_action.add(jmi_Rewind);
			jm_action.add(jmi_Revoke);
			jm_action.add(jmi_View);
			jm_action.add(jmi_Refresh);

		}
		
		jMenuBar.add(jm_action);

		innerPanel.setMenuBar(jMenuBar);
		innerPanel.repaint();

	}
	
	private JComboBox getjc_DisposalType() {
		if (jc_DisposalType == null) {
			jc_DisposalType = new JComboBox();
			jc_DisposalType.setBounds(new Rectangle(200,30,90, 50));
			jc_DisposalType.setName("AssetDisposal_jc_DisposalType");
			jc_DisposalType.addActionListener(this);
			Vector AC=new Vector();
			jc_DisposalType.removeAllItems();
			jc_DisposalType.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_DisposalType.addItem(AC.get(i));

			}

		}
		return jc_DisposalType;
	}
	private JComboBox getjc_DisposalCategory() {
		if (jc_DisposalCategory == null) {
			jc_DisposalCategory = new JComboBox();
			jc_DisposalCategory.setBounds(new Rectangle(390,30,90, 50));
			jc_DisposalCategory.setName("AssetDisposal_jc_DisposalCategory");
			jc_DisposalCategory.addActionListener(this);
			Vector AC=new Vector();
			jc_DisposalCategory.removeAllItems();
			jc_DisposalCategory.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_DisposalCategory.addItem(AC.get(i));

			}

		}
		return jc_DisposalCategory;
	}
		
	
	
	private TDateField getjdf_DisposalDateFrom() {
		if (jdf_DisposalDateFrom == null) {
			jdf_DisposalDateFrom = new TDateField();
			jdf_DisposalDateFrom.setBounds(new Rectangle(200, 80, 100, 30));
		}
		return jdf_DisposalDateFrom;
	}
	
	private TDateField getjdf_DisposalDateTo() {
		if (jdf_DisposalDateTo == null) {
			jdf_DisposalDateTo = new TDateField();
			jdf_DisposalDateTo.setBounds(new Rectangle(380, 80, 100, 30));
		}
		return jdf_DisposalDateTo;
	}
	
	private JComboBox getjc_Status() {
		if (jc_Status == null) {
			jc_Status = new JComboBox();
			jc_Status.setBounds(new Rectangle(600,30,90, 50));
			jc_Status.setName("AssetDisposal_jc_Status");
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
	
	
	private SortSelectTable getsst_DisposalDetails() {
		if (sst_DisposalDetails == null) {
			sst_DisposalDetails = new SortSelectTable();
			sst_DisposalDetails.setBounds(new Rectangle(30, 150, 700, 100));
			sst_DisposalDetails.setName("AuditDispose_sst_DisposalDetails");
			fillAssetDisposalDetails();
		}
		return sst_DisposalDetails;
	}
	
	public void fillAssetDisposalDetails() {
		try {
			Vector data=new Vector();
			data.add("Dispose Dc No");
			data.add("Dispose  Date");
			data.add("Dispose Category");
			data.add("Audit Profile");
			data.add("Remarks");
			data.add("Location");
			data.add("Disposed");
			data.add("Revoked");
			data.add("status");
			data.add("Approval Status");
		

			
	
			sst_DisposalDetails.setTable(null, data, "Order_Planning", "Disposal Details", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());
//			sst_DisposalDetails.setTable(null, data, "Order_Planning", "Audit Details", true);
			LstItem = sst_DisposalDetails.table.getSelectionModel();
			sst_DisposalDetails.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_DisposalDetails.table.getModel().addTableModelListener(acctListener);
			
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
	
	
	private SortSelectTable getsst_AssetDisposalDetails() {
		if (sst_AssetDisposalDetails == null) {
			sst_AssetDisposalDetails = new SortSelectTable();
			sst_AssetDisposalDetails.setBounds(new Rectangle(30, 350, 900, 90));
			sst_AssetDisposalDetails.setName("AuditDispose_sst_AssetDisposalDetails");
			fillAssetDisposalDetails1();
		}
		return sst_AssetDisposalDetails;
	}
	
	public void fillAssetDisposalDetails1() {
		try {
			Vector data=new Vector();
			data.add("Code");
			data.add("Asset Classification ");
			data.add("Asset Type ");
			data.add("Asset Specification ");
			data.add("OwnerShip ");
			data.add("Department");
			data.add("Sub Department");
			data.add("Area");
			data.add("Floor");
			data.add("sub Location3");
			data.add("Purchase  Value");
			data.add("Currency");
			data.add("Depreciated Value");
			data.add("Book Value");
			data.add("Depreciated %");
			data.add("Purchase Date");
			data.add("Asset Life");	
			data.add("Completed Age");
			data.add("Disposed Date");
			data.add("Disposal Status");
			data.add("Asset Status");
			data.add("Select");

			
	
//			sst_AssetDisposalDetails.setTable(null, data, "Order_Planning", "Audit Details", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());
			sst_AssetDisposalDetails.setTable(null, data, "Order_Planning", "Disposed Audit Details", true);
			LstItem = sst_AssetDisposalDetails.table.getSelectionModel();
			sst_AssetDisposalDetails.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_AssetDisposalDetails.table.getModel().addTableModelListener(acctListener);
			
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
		if (e.getSource() == jmi_dispose) {
			mode=1;
			parent.showMsg("");		
			AssetDisposalorRewind assetDisposal=new AssetDisposalorRewind(THIS,innerPanel,finter,parent,mode);		
			innerPanel.getWindow(allnode).add(assetDisposal);
			assetDisposal.setVisible(true);
			Utools.setMouseNormal(getContentPane());
		}
		
		if (e.getSource() == jmi_Rewind) {
			mode=2;
			parent.showMsg("");		
			AssetDisposalorRewind assetDisposal=new AssetDisposalorRewind(THIS,innerPanel,finter,parent,mode);		
			innerPanel.getWindow(allnode).add(assetDisposal);
			assetDisposal.setVisible(true);
			Utools.setMouseNormal(getContentPane());
		}
		
	}

}
