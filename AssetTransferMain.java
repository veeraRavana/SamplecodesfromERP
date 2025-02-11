package HarnessErp;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import HarnessErp.AddorModAssetTransfer.MyTableCellRenderer;
import common.Design;
import common.Utools;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;
import common.gui.UFrame;

public class AssetTransferMain extends JInternalFrame  implements ActionListener,InternalFrameListener, ListSelectionListener,sstFilterEvent{
	
	Design des = new Design("Order_Planning");

	FinanceInterface       fInter                   = null;
	ProductionInterface    productInter             = null;
	FinanceController      fc                       = null;
	OuterFrame             parent                   = null;
	InnerPanel             innerPanel               = null;
	String[]               allnode                  = null;
	AssetTransferMain      THIS                     = null;

	private JPanel         jContentPane             = null;
	public  JMenuBar       jMenuBar                 = null;
	private JMenu          jm_action                = null;
	private JMenuItem      jmi_AssetIssue           = null;
	private JMenuItem      jmi_Rewind               = null;
	private JMenuItem      jmi_View                 = null;
	private JMenuItem      jmi_Refresh              = null;
	
	private JLabel         jl_IssueFrmDate          = null;
	private JLabel         jl_To                    = null;
	private JLabel         jl_Status                = null;
	private JLabel         jl_TransferType          = null;
	private JLabel         jl_FromLocation          = null;
	private JLabel         jl_ToLocation            = null;
	
	private JComboBox      jc_ToLocation            = null;
	private JComboBox      jc_TransferType          = null;
	private JComboBox      jc_FromLocation          = null;
	private JComboBox      jc_Status                = null;
	private TDateField     jdf_IssueDateFrom        = null;
    private TDateField     jdf_ToDate               = null;
	private JButton        jb_Load                  = null;
	public SortSelectTable sst_TransferDetails      = null;
	ListSelectionModel     LstItem                  = null;
	public SortSelectTable sst_AssetTransferDetails = null;
   
	MyTableCellRenderer renderer = new MyTableCellRenderer();
	
	String rewindReason  = "";
	Vector transObj      = new Vector();
	int    mode          = -1;

	public AssetTransferMain(FinanceInterface finter,ProductionInterface productInter, FinanceController fc, OuterFrame parent, InnerPanel innerPanel,
			String[] allnode) {
		super("Asset Transfer", true, true, true, true);
		this.fInter       = finter;
		this.fc           = fc;
		this.parent       = parent;
		this.innerPanel   = innerPanel;
		this.allnode      = allnode;
		this.THIS         = this;
		this.productInter = productInter;
		initialize();
		createMenuBar();
		loadassetDetMainTab();
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
			
			jl_TransferType = new JLabel();
			jl_TransferType.setBounds(new Rectangle(100, 30, 100, 100));
			jl_TransferType.setText("Transfer Type");		
			jl_FromLocation = new JLabel();
			jl_FromLocation.setBounds(new Rectangle(310, 30, 100, 100));
			jl_FromLocation.setText("From Location");			
			jl_Status = new JLabel();
			jl_Status.setBounds(new Rectangle(550, 70, 100, 100));
			jl_Status.setText("Status");	
			jl_IssueFrmDate = new JLabel();
			jl_IssueFrmDate.setBounds(new Rectangle(100, 70, 100, 100));
			jl_IssueFrmDate.setText("Issue Date From");	 
			jl_To = new JLabel();
			jl_To.setBounds(new Rectangle(350, 70, 110, 100));
			jl_To.setText("To");	 
			jl_ToLocation = new JLabel();
			jl_ToLocation.setBounds(new Rectangle(530, 30, 100, 100));
			jl_ToLocation.setText("To Location");	 
			
			jContentPane.add(jl_TransferType, null);
			jContentPane.add(jl_FromLocation, null);
			jContentPane.add(jl_IssueFrmDate, null);
			jContentPane.add(jl_To          , null);
			jContentPane.add(jl_Status      , null);
			jContentPane.add(jl_ToLocation  , null);

			jContentPane.add(getjc_TransferType()        , null);
			jContentPane.add(getjc_FromLocation()        , null);
			jContentPane.add(getjdf_IssueDateFrom()      , null);
			jContentPane.add(getjdf_DateTo()             , null);
			jContentPane.add(getjc_Status()              , null);
			jContentPane.add(getjb_Load()                , null);
			jContentPane.add(getjc_ToLocation()          , null);
			jContentPane.add(getsst_TransferDetails()    , null);
			jContentPane.add(getsst_TranferAssetDetails(), null);
		}
		return jContentPane;
	}
	
	public void createMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();
		}
		if (jmi_AssetIssue == null) {
			jmi_AssetIssue = new JMenuItem("AssetIssue");
			jmi_AssetIssue.addActionListener(this);
		}
		if (jmi_Rewind == null) {
			jmi_Rewind = new JMenuItem("Rewind");
			jmi_Rewind.addActionListener(this);
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
			jm_action.add(jmi_AssetIssue);
			jm_action.add(jmi_Rewind);
			jm_action.add(jmi_View);
			jm_action.add(jmi_Refresh);
		}
		jMenuBar.add(jm_action);
		innerPanel.setMenuBar(jMenuBar);
		innerPanel.repaint();
	}
	
	private JComboBox getjc_TransferType() {
		if (jc_TransferType == null) {
			jc_TransferType = new JComboBox();
			jc_TransferType.setBounds(new Rectangle(200, 30, 90, 50));
			jc_TransferType.setName("AssetTransfer_jc_TransferType");
			
			jc_TransferType.removeAllItems();
			jc_TransferType.removeActionListener(this);

			jc_TransferType.addItem("All");
			jc_TransferType.addItem("Internal");
			jc_TransferType.addItem("External");
			
			jc_TransferType.setSelectedItem("All");
			
			jc_TransferType.addActionListener(this);
		}
		return jc_TransferType;
	}

	private JComboBox getjc_FromLocation() {
		if (jc_FromLocation == null) {
			try {
				jc_FromLocation = new JComboBox();
				jc_FromLocation.setBounds(new Rectangle(390, 30, 90, 50));
				jc_FromLocation.setName("AssetTransfer_jc_FromLocation");

				jc_FromLocation.removeAllItems();
				jc_FromLocation.removeActionListener(this);

				jc_FromLocation.addItem("All");
				Vector data = fInter.LoadVectorContents("select distinct LOCATION_NAME from FA_LOCATION_MASTER where STATUS = 'Active'");
				for (int i = 0; i < data.size(); i++) {
					jc_FromLocation.addItem(data.get(i).toString());
				}

				jc_FromLocation.setSelectedItem("All");

				jc_FromLocation.addActionListener(this);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return jc_FromLocation;
	}
		
	private TDateField getjdf_IssueDateFrom() {
		if (jdf_IssueDateFrom == null) {
			jdf_IssueDateFrom = new TDateField(1);
			jdf_IssueDateFrom.setBounds(new Rectangle(200, 70, 100, 30));
			String FDate = null;
			try {
				int dateRange = fInter.get_Int("select value from ASSET_CONFIGURATION where PROPERTY = 'Transfer and Receipt Default Date Range'");
				FDate = fInter.get_String("select to_char(sysdate-" + dateRange + ",'DD-MON-YYYY') as fdate from dual");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			jdf_IssueDateFrom.setText(FDate);
		}
		return jdf_IssueDateFrom;
	}
	
	private TDateField getjdf_DateTo() {
		if (jdf_ToDate == null) {
			jdf_ToDate = new TDateField(1);
			jdf_ToDate.setBounds(new Rectangle(390, 70, 100, 30));
		}
		return jdf_ToDate;
	}
	
	
	private JComboBox getjc_ToLocation() {
		if (jc_ToLocation == null) {
			try {
				jc_ToLocation = new JComboBox();
				jc_ToLocation.setBounds(new Rectangle(600, 30, 90, 50));
				jc_ToLocation.setName("AssetTransfer_jc_ToLocation");
				jc_ToLocation.removeAllItems();
				jc_ToLocation.removeActionListener(this);
				jc_ToLocation.addItem("All");
				Vector data = fInter.LoadVectorContents("select distinct LOCATION_NAME from FA_LOCATION_MASTER where STATUS = 'Active'");
				for (int i = 0; i < data.size(); i++) {
					jc_ToLocation.addItem(data.get(i).toString());
				}
				jc_ToLocation.setSelectedItem("All");

				jc_ToLocation.addActionListener(this);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return jc_ToLocation;
	}

	private JComboBox getjc_Status() {
		if (jc_Status == null) {
			jc_Status = new JComboBox();
			jc_Status.setBounds(new Rectangle(600,70,90, 50));
			jc_Status.setName("AssetTransfer_jc_Status");
			jc_Status.removeAllItems();
			jc_Status.removeActionListener(this);

			jc_Status.addItem("All");
			jc_Status.addItem("Issued");
			jc_Status.addItem("Received");
			
			jc_Status.setSelectedItem("Issued");
			jc_Status.addActionListener(this);
		}
		return jc_Status;
	}

	private JButton getjb_Load() {
		if(jb_Load==null){
			jb_Load=new JButton();
			jb_Load.setBounds(720, 70, 80, 80);
			jb_Load.setText("Load");
			jb_Load.addActionListener(this);
		}
		return jb_Load;
	}
	
	
	private SortSelectTable getsst_TransferDetails() {
		if (sst_TransferDetails == null) {
			sst_TransferDetails = new SortSelectTable();
			sst_TransferDetails.setBounds(new Rectangle(30, 110, 800, 170));
			sst_TransferDetails.setName("AuditDispose_sst_TransferDetails");
			fillAssetDetailsMain(new Vector());
		}
		return sst_TransferDetails;
	}
	
	public void fillAssetDetailsMain(Vector data) {
		try {
			Vector Heading=new Vector();
			Heading.add("Transfer ID");
			Heading.add("Issue Date");
			Heading.add("Transfer type");
			Heading.add("Transfer Remarks");
			Heading.add("From Location");
			Heading.add("To Location");
			Heading.add("Transfer Quantity");
			Heading.add("Transfer By");
			Heading.add("Status");
			
			sst_TransferDetails.setTable(data, Heading, "Order_Planning", "Transfer Details", false);
		}catch(Exception e) {
			e.printStackTrace();
		}
			LstItem = sst_TransferDetails.table.getSelectionModel();
			sst_TransferDetails.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			LstItem.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting()) {
						if (sst_TransferDetails.table.getSelectedRow() != -1) {
							int transfer_id = Integer.parseInt(sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(), 
									sst_TransferDetails.table.getColumnModel().getColumnIndex("Transfer ID")).toString());
							LoadAssetTransferSubTab(transfer_id);
						}
					}
				}

			});
			sst_TransferDetails.table.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					if (sst_TransferDetails.table.getSelectedRow() != -1) {
						int transfer_id = Integer.parseInt(sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(), 
								sst_TransferDetails.table.getColumnModel().getColumnIndex("Transfer ID")).toString());
						if (e.getKeyCode() == 38) {	//up arrow
							LoadAssetTransferSubTab(transfer_id);
						} else if (e.getKeyCode() == 40) {	//down arrow
							LoadAssetTransferSubTab(transfer_id);
						}
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					
				}
			});		
	}

	public Vector loadassetDetMainTab() {
		Vector data = new Vector();
		try {
			
			String condition = "";
			String transType = jc_TransferType.getSelectedItem() == null ? "": jc_TransferType.getSelectedItem().toString();
			String fromLoc   = jc_FromLocation.getSelectedItem() == null ? "": jc_FromLocation.getSelectedItem().toString();
			String toLoc     = jc_ToLocation.getSelectedItem() == null ? "" : jc_ToLocation.getSelectedItem().toString();
			String status    = jc_Status.getSelectedItem() == null ? "" : jc_Status.getSelectedItem().toString();
			// transType
			if (transType != null && !transType.equalsIgnoreCase("All")) {
				if (condition.length() == 0) {
					condition = condition + " Where TRANSFER_TYPE='" + transType + "'";
				} else {
					condition = condition + " and TRANSFER_TYPE='" + transType + "'";
				}
			}
			// from loc
			if (fromLoc != null && !fromLoc.equalsIgnoreCase("All")) {
				if (condition.length() == 0) {
					condition = condition + " Where From_Location='" + fromLoc + "'";
				} else {
					condition = condition + " and From_Location='" + fromLoc + "'";
				}
			}
			// to loc
			if (toLoc != null && !toLoc.equalsIgnoreCase("All")) {
				if (condition.length() == 0) {
					condition = condition + " Where To_Location='" + toLoc + "'";
				} else {
					condition = condition + " and To_Location='" + toLoc + "'";
				}
			}

			if (condition.length() == 0) {
				condition = condition + " Where to_date(TRANSFER_DATE,'DD-MON-YY') between to_date('" + jdf_IssueDateFrom.getText()+ "','DD-MON-YY') and to_date('" + jdf_ToDate.getText() + "','DD-MON-YY')";
			} else {
				condition = condition + " and to_date(TRANSFER_DATE,'DD-MON-YY') between to_date('" + jdf_IssueDateFrom.getText()+ "','DD-MON-YY') and to_date('" + jdf_ToDate.getText() + "','DD-MON-YY')";
			}
			// status
			if (status != null && !status.equalsIgnoreCase("All")) {
				if (condition.length() == 0) {
					condition = condition + " Where status='" + status + "'";
				} else {
					condition = condition + " and status='" + status + "'";
				}
			}
			data = fInter.LoadVectorwithContents("select * from (select ast.ASSET_TRANS_ID, to_char(ast.TRANSFER_DATE,'DD/Mon/YYYY') as TRANSFER_DATE, ast.TRANSFER_TYPE,ast.REMARKS,(select lm.Location_name from "
							+ "FA_LOCATION_MASTER lm where lm.location_id=ast.FROM_LOC_ID) as From_Location, (select lm.Location_name from "
							+ "FA_LOCATION_MASTER lm where lm.location_id=ast.TO_LOC_ID) as To_Location,AST.TRANSFER_QTY,AST.CREATED_BY,AST.STATUS "
							+ "from ASSET_TRANSFER ast where AST.STATUS <> 'Rewind' order by ast.ASSET_TRANS_ID desc) "+condition+"");
			fillAssetDetailsMain(data);
			fillAssetTransferDetails(new Vector());

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void LoadAssetTransferSubTab(int transfer_id) {
		try {
			String sql ="select AR.ASSET_ID,AR.ASSET_CODE,FM.CLASSIFICATION_NAME, FM.\"type\"  as ASSET_TYPE, FM.\"Specs\" as SPEC,"
					+ "(select distinct DEPT_NAME from DEPARTMENT where DEPT_ID=AR.DEPT_ID) AS DEPT_NAME, (SELECT DISTINCT SUB_DEPT_NAME FROM "
					+ "SUB_DEPARTMENT WHERE DEPT_ID  =AR.DEPT_ID and SUB_DEPT_ID=AR.SUB_DEPT_ID) AS SUB_DEPT_NAME,ar.SUB_LOC_ID,ar.location_id from ASSET_TRANSFER_SUB ATS "
					+ "inner join FA_ASSET_REGISTER_DETAILS AR on ATS.ASSET_ID = AR.ASSET_ID inner join FIXED_MATERIAL_VIEW FM ON FM.MAT_NO  "
					+ "=AR.MAT_NO where ats.ASSET_TRANS_ID="+transfer_id+" order by ats.ASSET_ID";
		
			Vector data = fInter.LoadVectorwithContents(sql);
			Vector all_sub_data = new Vector();
			for (int i = 0; i < data.size(); i++) {
				Vector temp = (Vector) data.get(i);
				String subid = temp.get(7) == null ? "" : temp.get(7).toString();
				String subNm1 = "", subNm2 = "", subNm3 = "", tosubNm1 = "", tosubNm2 = "", tosubNm3 = "";
				String loc_id = temp.get(8) == null ? "" : temp.get(8).toString();
				// from location
				if (subid != null && subid.length() > 0) {
					Vector stockRefVec = fInter.LoadVectorContents("SELECT REGEXP_SUBSTR((SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID="
									+ subid + "), '[^:]+', 1, level) AS parts FROM DUAL CONNECT BY REGEXP_SUBSTR((SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID="
									+ subid + "), '[^:]+', 1, level) IS NOT NULL");
					if (stockRefVec != null && stockRefVec.size() == 3) {
						if (stockRefVec.get(2) != null && !stockRefVec.get(2).toString().equalsIgnoreCase("S0")) {
							subNm3 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+loc_id+" and SUB_LOC_ID=" + subid);
						}
						if (stockRefVec.get(1) != null && !stockRefVec.get(1).toString().equalsIgnoreCase("L0")) {
							String ref = stockRefVec.get(0).toString() + ":" + stockRefVec.get(1).toString() + ":S0";
							subNm2 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+loc_id+" and STOCK_REF='" + ref + "'");
						}
						if (stockRefVec.get(0) != null && !stockRefVec.get(0).toString().equalsIgnoreCase("R0")) {
							String ref = stockRefVec.get(0).toString() + ":L0:S0";
							subNm1 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+loc_id+" and STOCK_REF='" + ref + "'");
						}
					}
				}
				temp.remove(8);
				temp.remove(7);
				temp.add(subNm1);
				temp.add(subNm2);
				temp.add(subNm3);
				String assetId = temp.get(0) == null ? "" : temp.get(0).toString();
				String to_loc_id  = fInter.get_String("select TO_LOC_ID from ASSET_TRANSFER where ASSET_TRANS_ID ="+transfer_id);
				// to location
				String tosubid = fInter.get_String("select TO_SUB_LOC_ID from ASSET_TRANSFER_SUB where ASSET_TRANS_ID ="+transfer_id+" and ASSET_ID ="+assetId);
				if (tosubid != null && tosubid.length() > 0) {
					Vector tostockRefVec = fInter.LoadVectorContents("SELECT REGEXP_SUBSTR((SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID="
									+ tosubid + "), '[^:]+', 1, level) AS parts FROM DUAL CONNECT BY REGEXP_SUBSTR((SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID="
									+ tosubid + "), '[^:]+', 1, level) IS NOT NULL");
					if (tostockRefVec != null && tostockRefVec.size() == 3) {
						if (tostockRefVec.get(2) != null && !tostockRefVec.get(2).toString().equalsIgnoreCase("S0")) {
							tosubNm3 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+to_loc_id+" and SUB_LOC_ID=" + tosubid);
						}
						if (tostockRefVec.get(1) != null && !tostockRefVec.get(1).toString().equalsIgnoreCase("L0")) {
							String ref = tostockRefVec.get(0).toString() + ":" + tostockRefVec.get(1).toString() + ":S0";
							tosubNm2 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+to_loc_id+" and STOCK_REF='" + ref + "'");
						}
						if (tostockRefVec.get(0) != null && !tostockRefVec.get(0).toString().equalsIgnoreCase("R0")) {
							String ref = tostockRefVec.get(0).toString() + ":L0:S0";
							tosubNm1 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+to_loc_id+" and STOCK_REF='" + ref + "'");
						}
					}
				}
				temp.remove(0);
				temp.add(tosubNm1);
				temp.add(tosubNm2);
				temp.add(tosubNm3);
				String receipt_by = fInter.get_String("select RECEIPT_BY from ASSET_RECEIPT where ASSET_TRANS_ID ="+transfer_id);
				if(receipt_by != null && receipt_by.trim().length()>0) {
					temp.add(receipt_by);
				}else {
					temp.add("");
				}
				all_sub_data.add(temp);
			}
			fillAssetTransferDetails(all_sub_data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	private SortSelectTable getsst_TranferAssetDetails() {
		if (sst_AssetTransferDetails == null) {
			sst_AssetTransferDetails = new SortSelectTable();
			sst_AssetTransferDetails.setBounds(new Rectangle(30, 290, 950, 170));
			sst_AssetTransferDetails.setName("AuditDispose_sst_AssetTransferDetails");
			fillAssetTransferDetails(new Vector());
		}
		return sst_AssetTransferDetails;
	}
	
	public void fillAssetTransferDetails(Vector data) {
		try {
			Vector heading_ast=new Vector();
			heading_ast.add("Asset Code");
			heading_ast.add("Asset Classification");
			heading_ast.add("Asset Type");
			heading_ast.add("Asset Specification");
			heading_ast.add("Department");
			heading_ast.add("Sub Department");
			
			Vector location_name = fInter.LoadVectorContents("select value from ASSET_CONFIGURATION where PROPERTY in ('Sub Location 1','Sub Location 2','Sub Location 3')");

			
			heading_ast.add(location_name.get(0)==null?"From Sub Location 1":"From "+location_name.get(0).toString());
			heading_ast.add(location_name.get(1)==null?"From Sub Location 2":"From "+location_name.get(1).toString());
			heading_ast.add(location_name.get(2)==null?"From Sub Location 3":"From "+location_name.get(2).toString());
			
			heading_ast.add(location_name.get(0)==null?"To Sub Location 1":"To "+location_name.get(0).toString());
			heading_ast.add(location_name.get(1)==null?"To Sub Location 2":"To "+location_name.get(1).toString());
			heading_ast.add(location_name.get(2)==null?"To Sub Location 3":"To "+location_name.get(2).toString());
			heading_ast.add("Received By");
			
			Vector hide  = new Vector();
			hide.add("Asset Classification");
			
			Vector subDept = fInter.LoadVectorContents("select SHOW_IN_COLUMN from AST_COLUMN_CONFIG where category='Transfer and Asset Receipt' and COLUMN_NAME in ('Sub-Department',"
					+ "'From Sub Location 1','From Sub Location 2','From Sub Location 3','To Sub Location 1','To Sub Location 2','To Sub Location 3')");
			String sDept = subDept.get(0)==null?"N":subDept.get(0).toString();
			String fromloc1  = subDept.get(1)==null?"N":subDept.get(1).toString();
			String fromloc2  = subDept.get(2)==null?"N":subDept.get(2).toString();
			String fromloc3  = subDept.get(3)==null?"N":subDept.get(3).toString();
			String toloc1    = subDept.get(4)==null?"N":subDept.get(4).toString();
			String toloc2    = subDept.get(5)==null?"N":subDept.get(5).toString();
			String toloc3    = subDept.get(6)==null?"N":subDept.get(6).toString();
			if(!fromloc1.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(0)==null?"From Sub Location 1":"From "+location_name.get(0).toString());
			}
			if(!fromloc2.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(1)==null?"From Sub Location 2":"From "+location_name.get(1).toString());
			}
			if(!fromloc3.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(2)==null?"From Sub Location 3":"From "+location_name.get(2).toString());
			}
			if(!toloc1.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(0)==null?"To Sub Location 1":"To "+location_name.get(0).toString());
			}
			if(!toloc2.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(1)==null?"To Sub Location 2":"To "+location_name.get(1).toString());
			}
			if(!toloc3.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(2)==null?"To Sub Location 3":"To "+location_name.get(2).toString());
			}
			Vector editVect = new Vector();
			Vector total    = new Vector();
			Vector selectAllVec  = new Vector();
			
			sst_AssetTransferDetails.setTable(data, heading_ast, "Finance","Transferred Asset Details", false, hide, total, new Vector(), renderer, THIS, editVect, selectAllVec);
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	static class MyTableCellRenderer implements TableCellRenderer 
	{

		public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			return renderer;
		}
	}
	public boolean reason_confirmation() {
		parent.showMsg("");
		rewindReason = JOptionPane.showInputDialog("Please Enter the reason for Rewind.", "");
		if (rewindReason != null) {
			while (rewindReason.trim().isEmpty()) {
				parent.showMsg("Please Enter reason to Rewind");
				rewindReason = JOptionPane.showInputDialog("Please Enter the reason for Rewind.");
				if (rewindReason == null) {
					parent.showMsg("");
					Utools.setMouseNormal(getJContentPane());
					break;
				} else if (rewindReason.trim().isEmpty()) {
					parent.showMsg("Please Enter reason to Rewind");
				} else {
					return true;
				}
			}
		}
		if (rewindReason == null) {
			parent.showMsg("");
			Utools.setMouseNormal(getJContentPane());
			return false;
		} else {
			parent.showMsg("");
			return true;
		}
	}

	private void RewindWithApproval() {
		try {
			transObj= new Vector();
			
			String assetTransID = sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(),
					sst_TransferDetails.table.getColumnModel().getColumnIndex("Transfer ID")).toString();
			int AssettransID = Integer.parseInt(assetTransID);

			Vector data = fInter.LoadVectorContents("select ast.ASSET_TRANS_ID, to_char(ast.TRANSFER_DATE,'DD-MON-YYYY') as TRANSFER_DATE, ast.TRANSFER_TYPE,ast.REMARKS,"
							+ "(select lm.Location_name from FA_LOCATION_MASTER lm where lm.location_id=ast.FROM_LOC_ID) as From_Location, "
							+ "(select lm.Location_name from FA_LOCATION_MASTER lm where lm.location_id=ast.TO_LOC_ID) as To_Location,ast.FROM_LOC_ID,ast.TO_LOC_ID,REWIND_REASON from ASSET_TRANSFER ast where ast.ASSET_TRANS_ID="
							+ AssettransID);
			
			data.remove(8);
			data.add(8,rewindReason);
			
			transObj.add(data);// 0
			
			Vector subdata = fInter.LoadVectorwithContents("select ASSET_ID,FROM_SUB_LOC_ID,TO_SUB_LOC_ID,AST_REMARKS from ASSET_TRANSFER_SUB ast where ast.ASSET_TRANS_ID="
					+ AssettransID);
			
			transObj.add(subdata);// 1
			transObj.add(AssettransID);// 2
			transObj.add(mode);// 3
			String ipAdd = "";
			try {
				 ipAdd = getIpAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			transObj.add(ipAdd);//4
			
			transObj.add(UFrame.uframe_user_id);//5
			
			Vector app_datas = new Vector();
			app_datas.add(UFrame.uframe_user_id); // UserId
			app_datas.add("Transfer_Rewind"); // functionName
			app_datas.add("AddorModAssetTransfer"); // handlerClass
			app_datas.add("Transfer Id"); // Property
			app_datas.add(AssettransID); // identificatin Value
			app_datas.add("TransferRewind");// TransactionName
			app_datas.add("Finance"); // ApplicationName
			app_datas.add(transObj);
			app_datas.add("Rewinded"); // message
			HashMap result = fInter.SaveTmpDatasforApproval(app_datas);
			if (result == null) {
				System.out.println("Error while Saving");
				JOptionPane.showMessageDialog(null, "Error while Saving");

			} else if (result.get("Approval Error") != null) {
				JOptionPane.showMessageDialog(null, "Sorry..! Approval Server ERROR, Contact Admin");

			} else if (result.get("Transaction Id") != null) {
				if(fInter.Execute_update("update ASSET_TRANSFER set APPROVAL_STATUS='Rewind In Approval' where ASSET_TRANS_ID =" + AssettransID)){
					JOptionPane.showMessageDialog(null,"Your transaction is saved and waiting for approval, transaction id is : "+ result.get("Transaction Id"));
					}else {
						JOptionPane.showMessageDialog(null, "Error while status Updating.");
					}
				parent.showMsg("");
				jc_TransferType.setSelectedItem("All");
				jc_FromLocation.setSelectedItem("All");
				jc_ToLocation.setSelectedItem("All");
				try {
					int dateRange = fInter.get_Int("select value from ASSET_CONFIGURATION where PROPERTY = 'Transfer and Receipt Default Date Range'");
					String FDate = fInter.get_String("select to_char(sysdate-" + dateRange + ",'DD-MON-YYYY') as fdate from dual");
					jdf_IssueDateFrom.setText(FDate);
					
					String TDate = fInter.get_String("select to_char(sysdate,'DD-MON-YYYY') as fdate from dual");
					jdf_ToDate.setText(TDate);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				jc_Status.setSelectedItem("Issued");
				loadassetDetMainTab();
				Utools.setMouseNormal(getContentPane());
			}
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	private static String getIpAddress() throws UnknownHostException 
	{  
		Enumeration<NetworkInterface> enumeration;
		String ipAddress="";
		try {
			enumeration = NetworkInterface.getNetworkInterfaces();
		while(enumeration.hasMoreElements())
		{
			for (InterfaceAddress interfaceAddress : enumeration.nextElement().getInterfaceAddresses()) 
			{
			if(!interfaceAddress.getAddress().isLoopbackAddress())	
			{
				if (interfaceAddress.getAddress().isSiteLocalAddress()) 
				{
					ipAddress=interfaceAddress.getAddress().toString().substring(1);
				}
			}
			}
		}
		} catch (SocketException se) {
			se.printStackTrace();
		}
		return ipAddress;
	}
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		if (e.getSource() == jmi_AssetIssue) {
			mode=0;
			parent.showMsg("");	
			
				AddorModAssetTransfer assetIssue = new AddorModAssetTransfer(THIS, innerPanel, fInter,productInter, parent, mode);
				innerPanel.getWindow(allnode).add(assetIssue);
				assetIssue.setVisible(true);
				Utools.setMouseNormal(getContentPane());
			
		}else if (e.getSource() == jmi_View) {
			mode=2;
			parent.showMsg("");	
			if (sst_TransferDetails.table.getSelectedRowCount() == 1) {
				AddorModAssetTransfer assetIssue = new AddorModAssetTransfer(THIS, innerPanel, fInter,productInter, parent, mode);
				innerPanel.getWindow(allnode).add(assetIssue);
				assetIssue.setVisible(true);
				Utools.setMouseNormal(getContentPane());
			} else {
				parent.showMsg("Please select the Transfer details for view");
				return;
			}
		}else if(e.getSource() == jmi_Refresh) {
			parent.showMsg("");
			jc_TransferType.setSelectedItem("All");
			jc_FromLocation.setSelectedItem("All");
			jc_ToLocation.setSelectedItem("All");
			try {
				int dateRange = fInter.get_Int("select value from ASSET_CONFIGURATION where PROPERTY = 'Transfer and Receipt Default Date Range'");
				String FDate = fInter.get_String("select to_char(sysdate-" + dateRange + ",'DD-MON-YYYY') as fdate from dual");
				jdf_IssueDateFrom.setText(FDate);
				
				String TDate = fInter.get_String("select to_char(sysdate,'DD-MON-YYYY') as fdate from dual");
				jdf_ToDate.setText(TDate);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			jc_Status.setSelectedItem("Issued");
			loadassetDetMainTab();
			Utools.setMouseNormal(getContentPane());
		}else if(e.getSource() == jb_Load){
			parent.showMsg("");
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");			
			Date fromDt1 = null;
			Date toDt1 = null;
				try {
					fromDt1 = sdf1.parse(jdf_IssueDateFrom.getText());
					toDt1 = sdf1.parse(jdf_ToDate.getText());
				} 
				catch (java.text.ParseException e1) {
					e1.printStackTrace();
				}
			if (fromDt1.after(toDt1)) {
				parent.showMsg("From Date cannot be greater than To Date.");
				return;
			}
			loadassetDetMainTab();
			Utools.setMouseNormal(getContentPane());
		}else if (e.getSource() == jmi_Rewind) {
			parent.showMsg("");
			if(sst_TransferDetails.table.getSelectedRow()!=-1) {
				try {
					String transfer_id = sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(),sst_TransferDetails.table.getColumnModel().getColumnIndex("Transfer ID")).toString();
					String status      = fInter.get_String("select STATUS from asset_transfer where ASSET_TRANS_ID="+transfer_id);
					if (status != null && !status.equalsIgnoreCase("Issued")) {
						parent.showMsg("Transfer details with Issued Status only allowed for Rewind");
						return;
					}
					String app_status      = fInter.get_String("select APPROVAL_STATUS from asset_transfer where ASSET_TRANS_ID="+transfer_id);
					if (app_status != null && !app_status.equalsIgnoreCase("Approved")) {
						parent.showMsg("Issue is in Rewind Approval");
						return;
					}
					if (fInter.isApprovalEnabled("TransferRewind")) {
						if (JOptionPane.showConfirmDialog(null, "Are you sure ,You want to rewind", "Confirm",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							if(reason_confirmation()) {
								if(rewindReason.length()>100) {
									parent.showMsg("Rewind Reason should not be above 100 characters");
									return;
								}
								RewindWithApproval(); // transObj Data
							}else {
								Utools.setMouseNormal(getJContentPane());
								return;
							}
						}else {
							Utools.setMouseNormal(getJContentPane());
							return;
						}
					}else {
						if (JOptionPane.showConfirmDialog(null,
								"Approval not enabled for the Asset Transfer Rewind. Do you want to Rewind the Asset Transfer without Approval?",
								"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							if(reason_confirmation()) {
								if(rewindReason.length()>100) {
									parent.showMsg("Rewind Reason should not be above 100 characters");
									return;
								}
								String assetTransID = sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(),
										sst_TransferDetails.table.getColumnModel().getColumnIndex("Transfer ID")).toString();
								int AssettransID = Integer.parseInt(assetTransID);
								String issue_id = fInter.get_String("select issue_id from asset_transfer where ASSET_TRANS_ID="+AssettransID);
								boolean issue_result = productInter.rewindIssue(issue_id==null?"0":issue_id.toString());
								if (issue_result) {
									boolean result = fInter.Execute_update("update ASSET_TRANSFER set STATUS ='Rewind', REWIND_REASON = '"+rewindReason+"' where ASSET_TRANS_ID=" + AssettransID);
									if(result) {
									JOptionPane.showMessageDialog(null, "Successfully Rewind");
									jc_TransferType.setSelectedItem("All");
									jc_FromLocation.setSelectedItem("All");
									jc_ToLocation.setSelectedItem("All");
									try {
										int dateRange = fInter.get_Int("select value from ASSET_CONFIGURATION where PROPERTY = 'Transfer and Receipt Default Date Range'");
										String FDate  = fInter.get_String("select to_char(sysdate-" + dateRange + ",'DD-MON-YYYY') as fdate from dual");
										jdf_IssueDateFrom.setText(FDate);

										String TDate = fInter.get_String("select to_char(sysdate,'DD-MON-YYYY') as fdate from dual");
										jdf_ToDate.setText(TDate);
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
									jc_Status.setSelectedItem("Issued");
									loadassetDetMainTab();
									parent.showMsg("");
								}else {
									JOptionPane.showMessageDialog(null, "Error while Rewinding");
									Utools.setMouseNormal(getJContentPane());
									parent.showMsg("");
								}
								} else {
									JOptionPane.showMessageDialog(null, "Error while Rewinding");
									Utools.setMouseNormal(getJContentPane());
									parent.showMsg("");
								}
							}else {
								Utools.setMouseNormal(getJContentPane());
								return;
							}
						}else {
							Utools.setMouseNormal(getJContentPane());
							return;
						}
					}
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}else {
				parent.showMsg("Please select the Transfer details for Rewind");
				return;
			}
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {		
	}

	@Override
	public boolean callFileterEvent() {
		// TODO Auto-generated method stub
		return false;
	}
}
