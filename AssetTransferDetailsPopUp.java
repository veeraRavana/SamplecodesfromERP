package HarnessErp;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
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

import HarnessErp.AssetTransferMain.MyTableCellRenderer;
import common.Design;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;

public class AssetTransferDetailsPopUp extends JDialog  implements ActionListener,InternalFrameListener, ListSelectionListener,sstFilterEvent{
	
	AssetTransferDetailsPopUp THIS       = null;
	AddorModAssetReceipt      parent     = null;
	AssetReceiptMain          parentMain = null;
	InnerPanel                innerPanel = null;
	FinanceInterface          fInter     = null;
	OuterFrame                outerFrm   = null;
	
	public JPanel             jContentPane             = null;
	public SortSelectTable    sst_TransferDetails      = null;
	public SortSelectTable    sst_TransferAssetDetails = null;
	public ListSelectionModel LstItem                  = null;
	
	MyTableCellRenderer renderer = new MyTableCellRenderer();
	private JButton jb_Add         = null;
	private JButton jb_Cancel      = null;
	int mode = -1;

	public AssetTransferDetailsPopUp(AddorModAssetReceipt tHIS, AssetReceiptMain parent, InnerPanel innerPanel,FinanceInterface finter, OuterFrame outFram, int mode) {
		super(outFram, true);
		this.THIS       = this;
		this.parent     = tHIS;
		this.parentMain = parent;
		this.innerPanel = innerPanel;
		this.fInter     = finter;
		this.outerFrm   = outFram;
		this.mode       = mode;
		this.THIS       = this;
		
		initialize();
		LoadAssetTransferMainTab();
	}
	
	
	private void initialize() {

		this.getContentPane().setLayout(null);
		this.setContentPane(getJContentPane());
		this.setLocation(50, 100);
		this.setSize(1300, 600);
		this.setTitle("Select Transfer Details");
		new Design("Order_Planning").setColors(this.getJContentPane());
	}
	
	
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getsst_TransferDetails(), null);
			jContentPane.add(getjb_Cancel(), null);
			jContentPane.add(getjb_Add(), null);
			jContentPane.add(getsst_TransferAssetDetails(), null);
		}
		return jContentPane;
	}
	
	
	private SortSelectTable getsst_TransferDetails() {
		if (sst_TransferDetails == null) {
			sst_TransferDetails = new SortSelectTable();
			sst_TransferDetails.setBounds(new Rectangle(30, 30, 800, 170));
			sst_TransferDetails.setName("AssetReceipt_sst_TransferDetails");
			fillAssetRegister(new Vector());
		}
		return sst_TransferDetails;
	}
	
	public void fillAssetRegister(Vector data) {
		try {
			Vector heading=new Vector();
			heading.add("Transfer ID");
			heading.add("Issue Date");
			heading.add("Transfer Remarks");
			heading.add("Transfer By");
			heading.add("Transfer type");
			heading.add("From Location");
			heading.add("To Location");
			heading.add("Transfer Quantity");
	    	heading.add("Status");
	  
	    	
			sst_TransferDetails.setTable(data, heading, "Order_Planning", "Transfer Details", true);			
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
	public void LoadAssetTransferMainTab() {
		try {
			String to_loc = parent.jc_ReceivingLocation.getSelectedItem().toString();
			Vector data = fInter.LoadVectorwithContents("select * from (select ast.ASSET_TRANS_ID, to_char(ast.TRANSFER_DATE,'DD/Mon/YYYY') as TRANSFER_DATE,ast.REMARKS,AST.CREATED_BY, ast.TRANSFER_TYPE,(select lm.Location_name from "
					+ "location_master lm where lm.location_id=ast.FROM_LOC_ID) as From_Location, (select lm.Location_name from "
					+ "location_master lm where lm.location_id=ast.TO_LOC_ID) as To_Location,AST.TRANSFER_QTY,AST.STATUS "
					+ "from ASSET_TRANSFER ast order by ast.ASSET_TRANS_ID desc) where To_Location = '"+to_loc+"' and STATUS !='Rewind'");
			fillAssetRegister(data);
			fillTransferAssetDetails(new Vector());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public void LoadAssetTransferSubTab(int transfer_id) {
		try {
			String sql ="select AR.ASSET_ID,AR.ASSET_CODE,FM.\"Specs\" as SPEC,FM.CLASSIFICATION_NAME, FM.\"type\"  as ASSET_TYPE,"
					+ "(select distinct DEPT_NAME from DEPARTMENT where DEPT_ID=AR.DEPT_ID) AS DEPT_NAME, (SELECT DISTINCT SUB_DEPT_NAME FROM "
					+ "SUB_DEPARTMENT WHERE DEPT_ID  =AR.DEPT_ID and SUB_DEPT_ID=AR.SUB_DEPT_ID) AS SUB_DEPT_NAME,ar.SUB_LOC_ID,ar.location_id from FA_ASSET_TRANSFER_SUB ATS "
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
				// to location
				String tosubid = fInter.get_String("select TO_SUB_LOC_ID from FA_ASSET_TRANSFER_SUB where ASSET_TRANS_ID ="+transfer_id+" and ASSET_ID ="+assetId);
				if (tosubid != null && tosubid.length() > 0) {
					
					Vector tostockRefVec = fInter.LoadVectorContents("SELECT REGEXP_SUBSTR((SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID="
									+ tosubid + "), '[^:]+', 1, level) AS parts FROM DUAL CONNECT BY REGEXP_SUBSTR((SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID="
									+ tosubid + "), '[^:]+', 1, level) IS NOT NULL");
					if (tostockRefVec != null && tostockRefVec.size() == 3) {
						if (tostockRefVec.get(2) != null && !tostockRefVec.get(2).toString().equalsIgnoreCase("S0")) {
							tosubNm3 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+loc_id+" and SUB_LOC_ID=" + tosubid);
						}
						if (tostockRefVec.get(1) != null && !tostockRefVec.get(1).toString().equalsIgnoreCase("L0")) {
							String ref = tostockRefVec.get(0).toString() + ":" + tostockRefVec.get(1).toString() + ":S0";
							tosubNm2 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+loc_id+" and STOCK_REF='" + ref + "'");
						}
						if (tostockRefVec.get(0) != null && !tostockRefVec.get(0).toString().equalsIgnoreCase("R0")) {
							String ref = tostockRefVec.get(0).toString() + ":L0:S0";
							tosubNm1 = fInter.get_String("SELECT SUB_LOCATION FROM FA_SUB_LOCATION WHERE location_id ="+loc_id+" and STOCK_REF='" + ref + "'");
						}
					}
				}
				temp.remove(0);
				
				temp.add(tosubNm1);
				temp.add(tosubNm2);
				temp.add(tosubNm3);
				
				all_sub_data.add(temp);
			}
			fillTransferAssetDetails(all_sub_data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	private SortSelectTable getsst_TransferAssetDetails() {
		if (sst_TransferAssetDetails == null) {
			sst_TransferAssetDetails = new SortSelectTable();
			sst_TransferAssetDetails.setBounds(new Rectangle(30, 210, 900, 170));
			sst_TransferAssetDetails.setName("AssetReceipt_sst_TransferAssetDetails");
			fillTransferAssetDetails(new Vector());
		}
		return sst_TransferAssetDetails;
	}
	
	public void fillTransferAssetDetails(Vector data) {
		try {
			Vector heading = new Vector();
			heading.add(" Asset Code");
			heading.add("Asset Specification");
			heading.add("Asset Classification");
			heading.add("Asset Type");
			heading.add("Department");
			heading.add("Sub Department");
			
			Vector location_name = fInter.LoadVectorContents("select value from ASSET_CONFIGURATION where PROPERTY in ('Sub Location 1','Sub Location 2','Sub Location 3')");

			
			heading.add(location_name.get(0)==null?"Sub Location 1":""+location_name.get(0).toString());
			heading.add(location_name.get(1)==null?"Sub Location 2":""+location_name.get(1).toString());
			heading.add(location_name.get(2)==null?"Sub Location 3":""+location_name.get(2).toString());
			
			heading.add(location_name.get(0)==null?"To Sub Location 1":"To "+location_name.get(0).toString());
			heading.add(location_name.get(1)==null?"To Sub Location 2":"To "+location_name.get(1).toString());
			heading.add(location_name.get(2)==null?"To Sub Location 3":"To "+location_name.get(2).toString());
			
			Vector hide  = new Vector();
			
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

	    	sst_TransferAssetDetails.setTable(data, heading, "Finance","Transferred Asset Details", false, hide, total, new Vector(), renderer, THIS, editVect, selectAllVec);
			
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
	private JButton getjb_Add() {
		if(jb_Add==null){
			jb_Add=new JButton();
			jb_Add.setBounds(250, 400, 80, 80);
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb_Add) {
			outerFrm.showMsg("");
			if (sst_TransferDetails.table.getCellEditor() != null) {
				sst_TransferDetails.table.getCellEditor().stopCellEditing();
			}
			if(sst_TransferDetails.table.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "Please select the Transfer ID to Add");
				return;
			}
			Vector asset_data = sst_TransferAssetDetails.getDataVector();
			Vector all_data = new Vector();
			for(int i=0;i<asset_data.size();i++) {
				Vector data = new Vector();
				Vector temp = (Vector) asset_data.get(i);
				String transID = sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(),sst_TransferDetails.table.getColumnModel().getColumnIndex("Transfer ID")).toString();
				parent.assetTransID = Integer.parseInt(transID==null?"0":transID.toString());
				data.add(transID==null?"":transID.toString());
				data.add(temp.get(0)==null?"":temp.get(0).toString());
				data.add(temp.get(2)==null?"":temp.get(2).toString());
				data.add(temp.get(3)==null?"":temp.get(3).toString());
				data.add(temp.get(1)==null?"":temp.get(1).toString());
				data.add(temp.get(4)==null?"":temp.get(4).toString());
				data.add(temp.get(5)==null?"":temp.get(5).toString());
				String fromLoc = sst_TransferDetails.table.getValueAt(sst_TransferDetails.table.getSelectedRow(),sst_TransferDetails.table.getColumnModel().getColumnIndex("From Location")).toString();
				data.add(fromLoc==null?"":fromLoc.toString());
				data.add(temp.get(6)==null?"":temp.get(6).toString());
				data.add(temp.get(7)==null?"":temp.get(7).toString());
				data.add(temp.get(8)==null?"":temp.get(8).toString());
				data.add(temp.get(9)==null?"":temp.get(9).toString());
				data.add(temp.get(10)==null?"":temp.get(10).toString());
				data.add(temp.get(11)==null?"":temp.get(11).toString());
				data.add(false);
				all_data.add(data);
			}
			parent.fillAssetTransferAssetDetails(all_data);
			parent.jc_ReceivingLocation.setEnabled(false);
			dispose();
		}else if(e.getSource() == jb_Cancel) {
			outerFrm.showMsg("");
			dispose();
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
		return false;
	}

}
