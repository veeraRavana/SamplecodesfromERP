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
import common.gui.TDateField;

public class AssetAuditingPopUp extends JDialog implements ActionListener,InternalFrameListener,ListSelectionListener{

	AssetAuditingPopUp THIS;
	AddorModAssetAudit tHIS = null;
	AssetAuditMain parent = null;
	InnerPanel innerPanel = null;
	FinanceInterface finter = null;
	OuterFrame outFram = null;
	int mode = -1;
	
	
	
	private JPanel jContentPane = null;
	public SortSelectTable sst_AssetAuditDetailsTable = null;
	ListSelectionModel LstItem = null;
	private JButton jb_Select=null;
	private JButton jb_Cancel=null;
	 private JLabel jl_AssetClassification = null;
	 private JLabel jl_AssetType = null;
	 private JCheckBox jckb_SelectAll = null;
	 private JComboBox jc_AssetClassification = null;
	 private JComboBox jc_AssetType = null;
	 private JLabel jl_AuditStatus = null;
	 private JLabel jl_AcquisitionDate = null;
	 private JComboBox jc_AuditStatus = null;
	 private TDateField jc_AuditDate = null;
	 private JLabel jl_Location = null;
	 private JLabel jl_To = null;
	 private JComboBox jc_Location = null;
	 private TDateField jdf_ToDate = null;
	 public SortSelectTable sst_NonAuditAssetDetailsTable = null;
	 private JButton jc_Load = null;
	 Vector AC=new Vector();

	
	
	public AssetAuditingPopUp(AddorModAssetAudit tHIS, AssetAuditMain parent, InnerPanel innerPanel,
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
		this.setLocation(100, 100);
		this.setSize(1300, 690);
		this.setTitle("AssetAuditing PopUp");

		new Design("Order_Planning").setColors(this.getJContentPane());

	}
	
	
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jl_AssetClassification = new JLabel();
			jl_AssetClassification.setBounds(new Rectangle(70, 30, 100, 100));
			jl_AssetClassification.setText("Asset Classification");
			jl_AssetType = new JLabel();
			jl_AssetType.setBounds(new Rectangle(320, 30, 100, 100));
			jl_AssetType.setText("Asset Type");			
			jl_AuditStatus = new JLabel();
			jl_AuditStatus.setBounds(new Rectangle(70, 60, 100, 100));
			jl_AuditStatus.setText("Audit Status");	
			jl_AcquisitionDate = new JLabel();
			jl_AcquisitionDate.setBounds(new Rectangle(320, 60, 100, 100));
			jl_AcquisitionDate.setText("Acquistion Date From");
			jl_Location = new JLabel();
			jl_Location.setBounds(new Rectangle(550, 30, 100, 100));
			jl_Location.setText("Location");
			jl_To = new JLabel();
			jl_To.setBounds(new Rectangle(550, 60, 100, 100));
			jl_To.setText("To");
			
			
			
			
			
			
			jContentPane.add(jl_AssetClassification, null);
			jContentPane.add(jl_AssetType, null);
			jContentPane.add(jl_AuditStatus, null);
			jContentPane.add(jl_AcquisitionDate, null);
			jContentPane.add(jl_Location, null);
			jContentPane.add(jl_To, null);

			jContentPane.add(getjc_AssetClassification(), null);
			jContentPane.add(getjc_AssetType(), null);
			jContentPane.add(getjc_AuditStatus(), null);
			jContentPane.add(getjc_AuditDate(), null);
			jContentPane.add(getjc_Location(), null);
			jContentPane.add(getjdf_ToDate(), null);

			jContentPane.add(getjc_Load(), null);
			jContentPane.add(get_jckb_SelectAll(), null);
			jContentPane.add(getsst_AssetAuditDetailsTable(), null);
			jContentPane.add(getjb_Cancel(), null);
			jContentPane.add(getjb_Select(), null);
			jContentPane.add(getsst_NonAuditAssetDetailsTable(), null);


		
		}
		return jContentPane;
	}
	
	private JComboBox getjc_AssetClassification(){
		if (jc_AssetClassification == null) {
			jc_AssetClassification = new JComboBox();
			jc_AssetClassification.setBounds(new Rectangle(160,30,130,30));
			jc_AssetClassification.setName("AuditSchedule_jc_AssetClassification");
			jc_AssetClassification.setEditable(false);
			jc_AssetClassification.addActionListener(this);
			Vector AC=new Vector();
			AC.add("");
	
			jc_AssetClassification.removeAllItems();
			jc_AssetClassification.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_AssetClassification.addItem(AC.get(i));

			}
		}
		return jc_AssetClassification;
	}
	

	private JComboBox getjc_AssetType(){
		if (jc_AssetType == null) {
			jc_AssetType = new JComboBox();
			jc_AssetType.setBounds(new Rectangle(400,30,130,30));
			jc_AssetType.setName("AuditSchedule_jc_AssetType");
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
	
	private JComboBox getjc_AuditStatus(){
		if (jc_AuditStatus == null) {
			jc_AuditStatus = new JComboBox();
			jc_AuditStatus.setBounds(new Rectangle(160,60,130,30));
			jc_AuditStatus.setName("AuditSchedule_jc_AuditStatus");
			jc_AuditStatus.addActionListener(this);
			AC.add("Audited");
			AC.add("Non Audited");
			jc_AuditStatus.removeAllItems();
//			jc_AuditStatus.addItem("Scheduled");
			for(int i=0;i<AC.size();i++) {
				jc_AuditStatus.addItem(AC.get(i));
			}

		}
		return jc_AuditStatus;
	}
	



	private TDateField getjc_AuditDate() {
		if (jc_AuditDate == null) {
			jc_AuditDate = new TDateField();
			jc_AuditDate.setBounds(new Rectangle(430, 60, 100, 30));
		}
		return jc_AuditDate;
	}
	
	
	private JComboBox getjc_Location(){
		if (jc_Location == null) {
			jc_Location = new JComboBox();
			jc_Location.setBounds(new Rectangle(600,30,130,30));
			jc_Location.setName("AuditSchedule_jc_Location");
			jc_Location.setEditable(false);
			jc_Location.addActionListener(this);
			Vector AC=new Vector();
			AC.add("");
	

			jc_Location.removeAllItems();
			jc_Location.addItem("All");
			for(int i=0;i<AC.size();i++) {
				jc_Location.addItem(AC.get(i));

			}
		}
		return jc_Location;
	}
	
	
	private TDateField getjdf_ToDate() {
		if (jdf_ToDate == null) {
			jdf_ToDate = new TDateField();
			jdf_ToDate.setBounds(new Rectangle(600, 60, 100, 30));
		}
		return jdf_ToDate;
	}
	
	
	private JButton getjc_Load() {
		if(jc_Load==null){
			jc_Load=new JButton();
			jc_Load.setBounds(760, 60, 80, 80);
			jc_Load.setText("load");
			jc_Load.addActionListener(this);
		}
		return jc_Load;
	}
	
	private JCheckBox get_jckb_SelectAll() {
		if(jckb_SelectAll==null)
		{
			jckb_SelectAll=new JCheckBox();
			jckb_SelectAll.setText("Select All");
			jckb_SelectAll.setBounds(830,120,100,30);
			jckb_SelectAll.addActionListener(this);
		}
		return jckb_SelectAll;
	}
	
	
	
	
	
	
	
	private SortSelectTable getsst_AssetAuditDetailsTable() {
		if (sst_AssetAuditDetailsTable == null) {
			sst_AssetAuditDetailsTable = new SortSelectTable();
			sst_AssetAuditDetailsTable.setBounds(new Rectangle(30, 140, 900, 150));
			sst_AssetAuditDetailsTable.setName("AuditSchedule_sst_AssetAuditDetailsTable");
			fillAssetRegister();
		}
		return sst_AssetAuditDetailsTable;
	}
	
	public void fillAssetRegister() {
		try {
			Vector data=new Vector();
			data.add("Code");
			data.add("Acquistion Date");
			data.add("Asset Classification");
	    	data.add("Asset Type");
	    	data.add("Asset Specification");
	    	data.add("Owership");
	    	data.add("Department");
	    	data.add("Sub Department");
	    	data.add("Location");
	    	data.add("Area");
	    	data.add("Floor");
	    	data.add("Location4");
	    	data.add("Purchase Value");
	    	data.add("Currency");
	    	data.add("Depreciated Value");
	    	data.add("Book Value");
	    	data.add("Asset Status");
	    	data.add("Select all");

			sst_AssetAuditDetailsTable.setTable(null, data, "Order_Planning", "Asset Details", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());

			LstItem = sst_AssetAuditDetailsTable.table.getSelectionModel();
			sst_AssetAuditDetailsTable.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_AssetAuditDetailsTable.table.getModel().addTableModelListener(acctListener);
			
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
	
	
	private SortSelectTable getsst_NonAuditAssetDetailsTable() {
		if (sst_NonAuditAssetDetailsTable == null) {
			sst_NonAuditAssetDetailsTable = new SortSelectTable();
			sst_NonAuditAssetDetailsTable.setBounds(new Rectangle(30, 140, 900, 150));
			sst_NonAuditAssetDetailsTable.setName("AuditSchedule_sst_NonAuditAssetDetailsTable");
			fillNonSchedule();
		}
		return sst_NonAuditAssetDetailsTable;
	}
	
	public void fillNonSchedule() {
		try {
			Vector data=new Vector();
			data.add("Code");
			data.add("Last Acquistion Date");
			data.add("Asset Classification");
	    	data.add("Asset Type");
	    	data.add("Asset Specification");
        	data.add("Owership");
	    	data.add("Department");
	    	data.add("Sub Department");
	    	data.add("Location");
	    	data.add("Area");
	    	data.add("Floor");
	    	data.add("Sub Location3");
	    	data.add("Linked Audit Name");
	      	data.add("Purchase Value");
	    	data.add("Currency");
	    	data.add("Depreciated Value");
	    	data.add("Book Value");
	    	data.add("Asset Status");
	    	data.add("Select All");

	    	sst_NonAuditAssetDetailsTable.setTable(null, data, "Order_Planning", "Asset Details", true, new Vector(), new Vector(), new Vector(), null, null, new Vector(), new Vector());

			LstItem = sst_NonAuditAssetDetailsTable.table.getSelectionModel();
			sst_NonAuditAssetDetailsTable.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_NonAuditAssetDetailsTable.table.getModel().addTableModelListener(acctListener);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}		
	
	
	private JButton getjb_Select() {
		if(jb_Select==null){
			jb_Select=new JButton();
			jb_Select.setBounds(300, 400, 80, 80);
			jb_Select.setText("Select");
			jb_Select.addActionListener(this);
		}
		return jb_Select;
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
		
       if(e.getSource()==jc_AuditStatus) {
			
			if(jc_AuditStatus.getSelectedItem().equals("Non Audited")&&sst_AssetAuditDetailsTable!=null) {
				sst_AssetAuditDetailsTable.setVisible(false);

			}else {
				if(sst_AssetAuditDetailsTable!=null)
					sst_AssetAuditDetailsTable.setVisible(true);

			}
			
		
		
		
	}

}
	}
