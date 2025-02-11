package HarnessErp;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import common.Design;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;

public class AssetDisposalPopUp extends JDialog implements ActionListener,InternalFrameListener,ListSelectionListener{
	
	AssetDisposalPopUp THIS;
	AssetDisposalorRewind tHIS = null;
	AssetDisposalMain parent = null;
	InnerPanel innerPanel = null;
	FinanceInterface finter = null;
	OuterFrame outFram = null;
	int mode = -1;
	
	
	
	private JPanel jContentPane = null;
	public SortSelectTable sst_AssetDisposalTable = null;
	ListSelectionModel LstItem = null;
	private JButton jb_Add=null;
	private JButton jb_Cancel=null;
 
	 private JComboBox jc_AssetClass = null;
	 private JComboBox jc_AssetType = null;
	 private JLabel jl_Assetclass = null;
	 private JLabel jl_AssetType = null;
	 private JLabel jl_AuditProfile = null;
	 private JComboBox jc_AssetProfile = null;

	 private JButton jc_Load = null;
	
	
	
	
	
	

	public AssetDisposalPopUp(AssetDisposalorRewind tHIS, AssetDisposalMain parent, InnerPanel innerPanel,
			FinanceInterface finter, OuterFrame outFram, int mode) {
		// TODO Auto-generated constructor stub
		
		super(outFram, true);

		this.tHIS = tHIS;
		this.parent = parent;
		this.innerPanel = innerPanel;
		this.finter = finter;
		this.outFram = outFram;
		this.mode = mode;
		this.THIS = this;

			
		initialize();
		
		
	}
	
	private void initialize() {

		this.getContentPane().setLayout(null);
		this.setContentPane(getJContentPane());
		this.setLocation(70, 100);
		this.setSize(1350, 690);
		this.setTitle("Asset Disposal PopUp");

		new Design("Order_Planning").setColors(this.getJContentPane());

	}
	
	
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
		
			jl_Assetclass = new JLabel();
			jl_Assetclass.setBounds(new Rectangle(30, 30, 100, 100));
			jl_Assetclass.setText("Asset Classification");
			jl_AssetType = new JLabel();
			jl_AssetType.setBounds(new Rectangle(280, 30, 100, 100));
			jl_AssetType.setText("Asset Type");
			jl_AuditProfile = new JLabel();
			jl_AuditProfile.setBounds(new Rectangle(480, 30, 100, 100));
			jl_AuditProfile.setText("Audit Profile Name");
			
			
			
			
			
			
			
			jContentPane.add(jl_Assetclass, null);
			jContentPane.add(jl_AssetType, null);
			jContentPane.add(jl_AuditProfile, null);

			jContentPane.add(getjc_AssetClass(), null);
			jContentPane.add(getjc_AssetType(), null);
			jContentPane.add(getjc_AssetProfile(), null);

			jContentPane.add(getjc_Load(), null);
			
			
			
			
			
			
			

			jContentPane.add(getsst_AssetDisposalTable(), null);
			jContentPane.add(getjb_Add(), null);
			jContentPane.add(getjb_Cancel(), null);

		
		}
		return jContentPane;
	}
	
	
	private JComboBox getjc_AssetClass(){
		if (jc_AssetClass == null) {
			jc_AssetClass = new JComboBox();
			jc_AssetClass.setBounds(new Rectangle(150,30,100,30));
			jc_AssetClass.setName("AuditDisposal_jc_AuditBy");
			jc_AssetClass.setEditable(false);
			jc_AssetClass.addActionListener(this);
			Vector AC=new Vector();
			AC.add("");
	
			jc_AssetClass.removeAllItems();
			jc_AssetClass.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_AssetClass.addItem(AC.get(i));

			}
		}
		return jc_AssetClass;
	}
	

	private JComboBox getjc_AssetType(){
		if (jc_AssetType == null) {
			jc_AssetType = new JComboBox();
			jc_AssetType.setBounds(new Rectangle(350,30,100,30));
			jc_AssetType.setName("AuditDisposal_jc_AuditBy");
			jc_AssetType.setEditable(false);
			jc_AssetType.addActionListener(this);
			Vector AC=new Vector();
			AC.add("");
	

			jc_AssetType.removeAllItems();
			jc_AssetType.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_AssetType.addItem(AC.get(i));

			}
		}
		return jc_AssetType;
	}
	
	private JComboBox getjc_AssetProfile(){
		if (jc_AssetProfile == null) {
			jc_AssetProfile = new JComboBox();
			jc_AssetProfile.setBounds(new Rectangle(570,30,100,30));
			jc_AssetProfile.setName("AuditDisposal_jc_AuditBy");
			jc_AssetProfile.setEditable(false);
			jc_AssetProfile.addActionListener(this);
			Vector AC=new Vector();
			AC.add("");
	

			jc_AssetProfile.removeAllItems();
			jc_AssetProfile.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_AssetProfile.addItem(AC.get(i));

			}
		}
		return jc_AssetProfile;
	}
	

	
	private JButton getjc_Load() {
		if(jc_Load==null){
			jc_Load=new JButton();
			jc_Load.setBounds(720, 30, 80, 80);
			jc_Load.setText("load");
			jc_Load.addActionListener(this);
		}
		return jc_Load;
	}
	

	private SortSelectTable getsst_AssetDisposalTable() {
		if (sst_AssetDisposalTable == null) {
			sst_AssetDisposalTable = new SortSelectTable();
			sst_AssetDisposalTable.setBounds(new Rectangle(30, 100, 1000, 150));
			sst_AssetDisposalTable.setName("DescriptionDetails_sst_AssetDisposalTable");
			fillAssetClassification();
		}
		return sst_AssetDisposalTable;
	}
	
	public void fillAssetClassification() {
		try {
			Vector data=new Vector();
			data.add("Schedule Id");
			data.add("Asset Id");
			data.add("Asset Date ");
			data.add("Asset Profile Name ");
			data.add("Asset Code");
			data.add("Asset Classification ");
			data.add("Asset Type ");
			data.add("Asset Specification ");
			data.add("Owership");
			data.add("Department");
			data.add("Sub Department");
			data.add("Location");
			data.add("Area");
			data.add("Floor");
			data.add("sub Location3");
			data.add("Audit Remarks");
			data.add("Presence");
			data.add("Maintance");
			data.add("Rating");
			data.add("Purchase Value");
			data.add("Currency");
			data.add("Depreciated Value");
			data.add("Book Value");
			data.add("Depreciated %");
			data.add("Purchase date");
			data.add("Asset Life");
			data.add("Asset Status");
			data.add("Select");


			sst_AssetDisposalTable.setTable(null, data, "Order_Planning", "Audit Asset Disposal", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());

//			sst_AssetDisposalTable.setTable(null, data, "Order_Planning", "Depreciation Due Details", true);
			LstItem = sst_AssetDisposalTable.table.getSelectionModel();
			sst_AssetDisposalTable.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_AssetDisposalTable.table.getModel().addTableModelListener(acctListener);
			
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
	
	
	
	private JButton getjb_Add() {
		if(jb_Add==null){
			jb_Add=new JButton();
			jb_Add.setBounds(300, 400, 80, 80);
			jb_Add.setText("Add");
			jb_Add.addActionListener(this);
		}
		return jb_Add;
	}
	
	private JButton getjb_Cancel() {
		if(jb_Cancel==null){
			jb_Cancel=new JButton();
			jb_Cancel.setBounds(450, 400, 80, 80);
			jb_Cancel.setText("Cancel");
			jb_Cancel.addActionListener(this);
		}
		return jb_Cancel;
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
		
	}

}
