package HarnessErp;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

import HarnessErp.AssetTransferMain.MyTableCellRenderer;
import common.Design;
import common.Utools;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;

public class AssetReceiptMain  extends JInternalFrame  implements ActionListener,InternalFrameListener, ListSelectionListener,sstFilterEvent{
	
	Design des = new Design("Order_Planning");
	
	FinanceInterface  fInter          = null;
	ProductionInterface  producInter  = null;
	FinanceController fc              = null;
	OuterFrame        parent          = null;
	InnerPanel        innerPanel      = null;
	String[]          allnode         = null;
	AssetReceiptMain  THIS            = null;
	
	private JPanel    jContentPane    = null;
	public  JMenuBar  jMenuBar        = null;
	private JMenu     jm_action       = null;
	private JMenuItem jmi_addReceipt  = null;
	private JMenuItem jmi_View        = null;
	private JMenuItem jmi_Refresh     = null;
	
	private JLabel     jl_Frm         = null;
	private JLabel     jl_To          = null;
	private TDateField jdf_FromDate   = null;
    private TDateField jdf_ToDate     = null;
	private JButton    jb_Load        = null;
	
	public SortSelectTable    sst_receiptDetails  = null;
	public SortSelectTable    sst_assetDetails    = null;
	public ListSelectionModel LstItem             = null;
	
	MyTableCellRenderer renderer = new MyTableCellRenderer();
	
    int mode=-1;

	
	public AssetReceiptMain(FinanceInterface finter,ProductionInterface productInter, FinanceController fc, OuterFrame parent, InnerPanel innerPanel,
			String[] allnode) {
		super("Asset Receipt", true, true, true, true);
		this.fInter=finter;
		this.fc=fc;
		this.parent=parent;
		this.innerPanel=innerPanel;
		this.allnode=allnode;
		this.producInter=productInter;
		this.THIS = this;
		initialize();
		createMenuBar();
		loadMainTab();
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
		
			jl_Frm = new JLabel();
			jl_Frm.setBounds(new Rectangle(100, 30, 100, 100));
			jl_Frm.setText("Recepit Date From");	 
			jl_To = new JLabel();
			jl_To.setBounds(new Rectangle(350, 30, 100, 100));
			jl_To.setText("To");	 
			
			jContentPane.add(jl_Frm, null);
			jContentPane.add(jl_To, null);

			jContentPane.add(getjdf_receiptDateFrom(), null);
			jContentPane.add(getjdf_receiptToDate(), null);
			jContentPane.add(getjb_Load(), null);
			jContentPane.add(getsst_receiptDetails(), null);
			jContentPane.add(getsst_AssetReceiptDetails(), null);
		}
		return jContentPane;

	}
	

	public void createMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();

		}
		if (jmi_addReceipt == null) {
			jmi_addReceipt = new JMenuItem("Add Receipt");
			jmi_addReceipt.addActionListener(this);
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
			jm_action.add(jmi_addReceipt);
			jm_action.add(jmi_View);
			jm_action.add(jmi_Refresh);

		}
		
		jMenuBar.add(jm_action);

		innerPanel.setMenuBar(jMenuBar);
		innerPanel.repaint();

	}
	private TDateField getjdf_receiptDateFrom() {
		if (jdf_FromDate == null) {
			jdf_FromDate = new TDateField(1);
			jdf_FromDate.setBounds(new Rectangle(200, 30, 100, 30));
			String FDate = null;
			try {
				int dateRange = fInter.get_Int("select value from ASSET_CONFIGURATION where PROPERTY = 'Transfer and Receipt Default Date Range'");
				FDate = fInter.get_String("select to_char(sysdate-" + dateRange + ",'DD-MON-YYYY') as fdate from dual");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			jdf_FromDate.setText(FDate);
		}
		return jdf_FromDate;
	}
	
	private TDateField getjdf_receiptToDate() {
		if (jdf_ToDate == null) {
			jdf_ToDate = new TDateField(1);
			jdf_ToDate.setBounds(new Rectangle(380, 30, 100, 30));
		}
		return jdf_ToDate;
	}
	
	private JButton getjb_Load() {
		if(jb_Load==null){
			jb_Load=new JButton();
			jb_Load.setBounds(550, 30, 70, 50);
			jb_Load.setText("Load");
			jb_Load.addActionListener(this);
		}
		return jb_Load;
	}
	
	
	private SortSelectTable getsst_receiptDetails() {
		if (sst_receiptDetails == null) {
			sst_receiptDetails = new SortSelectTable();
			sst_receiptDetails.setBounds(new Rectangle(30, 80, 700, 170));
			sst_receiptDetails.setName("AssetReceipt_sst_receiptDetails");
			fillReceiptDetails(new Vector());
		}
		return sst_receiptDetails;
	}
	
	public void fillReceiptDetails(Vector data) {
		try {
			Vector heading = new Vector();
			heading.add("Receipt ID");
			heading.add("Receipt Date");
			heading.add("From Location");
			heading.add("To Location");
			heading.add("Remarks");
			heading.add("Receipt By");
			heading.add("Status");
	
			sst_receiptDetails.setTable(data, heading, "Order_Planning", "Receipt Details", false);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		LstItem = sst_receiptDetails.table.getSelectionModel();
		sst_receiptDetails.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		LstItem.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					if (sst_receiptDetails.table.getSelectedRow() != -1) {
						try {
						int Receipt_id = Integer.parseInt(sst_receiptDetails.table.getValueAt(sst_receiptDetails.table.getSelectedRow(), 
								sst_receiptDetails.table.getColumnModel().getColumnIndex("Receipt ID")).toString());
						int transfer_id = fInter.get_Int("select ASSET_TRANS_ID from ASSET_RECEIPT where ASSET_RECEIPT_ID="+Receipt_id);
						LoadSubTab(transfer_id);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}

		});
		sst_receiptDetails.table.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (sst_receiptDetails.table.getSelectedRow() != -1) {
					try {
					int Receipt_id = Integer.parseInt(sst_receiptDetails.table.getValueAt(sst_receiptDetails.table.getSelectedRow(), 
							sst_receiptDetails.table.getColumnModel().getColumnIndex("Receipt ID")).toString());
					int transfer_id = fInter.get_Int("select ASSET_TRANS_ID from ASSET_RECEIPT where ASSET_RECEIPT_ID="+Receipt_id);
					if (e.getKeyCode() == 38) {	//up arrow
						LoadSubTab(transfer_id);
					} else if (e.getKeyCode() == 40) {	//down arrow
						LoadSubTab(transfer_id);
					}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});		
	}
	
	private SortSelectTable getsst_AssetReceiptDetails() {
		if (sst_assetDetails == null) {
			sst_assetDetails = new SortSelectTable();
			sst_assetDetails.setBounds(new Rectangle(30, 270, 900, 170));
			sst_assetDetails.setName("AssetReceipt_sst_AssetReceiptDetails");
			fillAssetDetails(new Vector());
		}
		return sst_assetDetails;
	}
	
	public void fillAssetDetails(Vector data) {
		try {
			Vector heading_ast=new Vector();
			heading_ast.add("Asset Code");
			heading_ast.add("Asset Classification");
			heading_ast.add("Asset Type");
			heading_ast.add("Asset Specification");
			heading_ast.add("Department");
			heading_ast.add("Sub Department");
			heading_ast.add("From Location");
			
			Vector location_name = fInter.LoadVectorContents("select value from ASSET_CONFIGURATION where PROPERTY in ('Sub Location 1','Sub Location 2','Sub Location 3')");

			
			heading_ast.add(location_name.get(0)==null?"From Sub Location 1":"From "+location_name.get(0).toString());
			heading_ast.add(location_name.get(1)==null?"From Sub Location 2":"From "+location_name.get(1).toString());
			heading_ast.add(location_name.get(2)==null?"From Sub Location 3":"From "+location_name.get(2).toString());
			heading_ast.add("To Location");
			heading_ast.add(location_name.get(0)==null?"To Sub Location 1":"To "+location_name.get(0).toString());
			heading_ast.add(location_name.get(1)==null?"To Sub Location 2":"To "+location_name.get(1).toString());
			heading_ast.add(location_name.get(2)==null?"To Sub Location 3":"To "+location_name.get(2).toString());
			
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
				hide.add(location_name.get(0)==null?"Sub Location 1":""+location_name.get(0).toString());
			}
			if(!fromloc2.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(1)==null?"Sub Location 2":""+location_name.get(1).toString());
			}
			if(!fromloc3.equalsIgnoreCase("Y")) {
				hide.add(location_name.get(2)==null?"Sub Location 3":""+location_name.get(2).toString());
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
	
			sst_assetDetails.setTable(data, heading_ast, "Finance","Transferred Asset Details", false, hide, total, new Vector(), renderer, THIS, editVect, selectAllVec);
			sst_assetDetails.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			sst_assetDetails.table.repaint();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Vector loadMainTab() {
		Vector data = new Vector();
		try {

			String condition = "";

			condition = condition + " Where to_date(RECEIPT_DATE,'DD-MON-YY') between to_date('"+ jdf_FromDate.getText() + "','DD-MON-YY') and to_date('" + jdf_ToDate.getText() + "','DD-MON-YY')";

			data = fInter.LoadVectorwithContents("select * from (select AST.ASSET_RECEIPT_ID, TO_CHAR(AST.RECEIPT_DATE,'DD/Mon/YYYY') as RECEIPT_DATE, "
					+ "(select LM.LOCATION_NAME from FA_LOCATION_MASTER LM where LM.LOCATION_ID=(select FROM_LOC_ID from ASSET_TRANSFER where "
					+ "ASSET_TRANS_ID =AST.ASSET_TRANS_ID)) as FROM_LOCATION, (select LM.LOCATION_NAME from FA_LOCATION_MASTER lm where "
					+ "lm.location_id=AST.RECEIVING_LOC_ID) as To_Location,ast.REMARKS,AST.RECEIPT_BY,AST.STATUS from ASSET_RECEIPT ast "
					+ "order by ast.ASSET_RECEIPT_ID desc)" + condition + "");
			fillReceiptDetails(data);
			fillAssetDetails(new Vector());

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return data;
	}
	public void LoadSubTab(int transfer_id) {
		try {
			String sql ="select AR.ASSET_ID,AR.ASSET_CODE,FM.CLASSIFICATION_NAME, FM.\"type\"  as ASSET_TYPE, FM.\"Specs\" as SPEC,"
					+ "(select distinct DEPT_NAME from DEPARTMENT where DEPT_ID=AR.DEPT_ID) AS DEPT_NAME, (SELECT DISTINCT SUB_DEPT_NAME FROM "
					+ "SUB_DEPARTMENT WHERE DEPT_ID  =AR.DEPT_ID and SUB_DEPT_ID=AR.SUB_DEPT_ID) AS SUB_DEPT_NAME,ar.SUB_LOC_ID,ar.location_id from ASSET_TRANSFER_SUB ATS "
					+ "inner join ASSET_REGISTER_DETAIL AR on ATS.ASSET_ID = AR.ASSET_ID inner join FIXED_MATERIAL_VIEW FM ON FM.MAT_NO  "
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
				String fromLoc = sst_receiptDetails.table.getValueAt(sst_receiptDetails.table.getSelectedRow(), 
						sst_receiptDetails.table.getColumnModel().getColumnIndex("From Location")).toString();
				temp.remove(8);
				temp.remove(7);
				temp.add(fromLoc);
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
				String toLoc = sst_receiptDetails.table.getValueAt(sst_receiptDetails.table.getSelectedRow(), 
						sst_receiptDetails.table.getColumnModel().getColumnIndex("To Location")).toString();
				temp.add(toLoc);
				temp.add(tosubNm1);
				temp.add(tosubNm2);
				temp.add(tosubNm3);
				all_sub_data.add(temp);
			}
			fillAssetDetails(all_sub_data);
		} catch (RemoteException e) {
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jmi_addReceipt) {
			mode=0;
			parent.showMsg("");		
			AddorModAssetReceipt assetReceipt=new AddorModAssetReceipt(THIS,innerPanel,producInter,fInter,parent,mode);		
			innerPanel.getWindow(allnode).add(assetReceipt);
			assetReceipt.setVisible(true);
			Utools.setMouseNormal(getContentPane());
		}else if (e.getSource() == jmi_View) {
			mode=2;
			parent.showMsg("");		
			AddorModAssetReceipt assetReceipt=new AddorModAssetReceipt(THIS,innerPanel,producInter,fInter,parent,mode);		
			innerPanel.getWindow(allnode).add(assetReceipt);
			assetReceipt.setVisible(true);
			Utools.setMouseNormal(getContentPane());
		}else if(e.getSource() == jmi_Refresh) {
			parent.showMsg("");
			
			try {
				int dateRange = fInter.get_Int("select value from ASSET_CONFIGURATION where PROPERTY = 'Transfer and Receipt Default Date Range'");
				String FDate = fInter.get_String("select to_char(sysdate-" + dateRange + ",'DD-MON-YYYY') as fdate from dual");
				jdf_FromDate.setText(FDate);
				
				String TDate = fInter.get_String("select to_char(sysdate,'DD-MON-YYYY') as fdate from dual");
				jdf_ToDate.setText(TDate);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			loadMainTab();
			Utools.setMouseNormal(getContentPane());
		}else if(e.getSource() == jb_Load){
			parent.showMsg("");
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");			
			Date fromDt1 = null;
			Date toDt1 = null;
				try {
					fromDt1 = sdf1.parse(jdf_FromDate.getText());
					toDt1 = sdf1.parse(jdf_ToDate.getText());
				} 
				catch (java.text.ParseException e1) {
					e1.printStackTrace();
				}
			if (fromDt1.after(toDt1)) {
				parent.showMsg("From Date cannot be greater than To Date.");
				return;
			}
			loadMainTab();
			Utools.setMouseNormal(getContentPane());
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
