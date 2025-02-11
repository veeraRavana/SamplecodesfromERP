package HarnessErp;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
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

import HarnessErp.AddorModPaymentStatementConfig.MyTableCellRenderer;
import common.Design;
import common.GUIDiffInterface;
import common.GUIDifference;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;

public class AssetTypePopUp extends JDialog
		implements ActionListener, InternalFrameListener, sstFilterEvent, GUIDiffInterface {

	AssetTypePopUp THIS = null;
	AddorModDepreciationProfile tHIS = null;
	DepreciationProfileMain parent1 = null;
	InnerPanel innerPanel = null;
	FinanceInterface finter = null;
	OuterFrame parent = null;
	int mode = -1;
	int appFlag = -1;

	private JPanel jContentPane = null;
	public SortSelectTable sst_AssetMainTable = null;
	ListSelectionModel LstItem = null;
	private JButton jb_Add = null;
	private JButton jb_Cancel = null;
	private JCheckBox jckb_AssetType = null;
	private MyTableCellRenderer renderer = new MyTableCellRenderer();

	public AssetTypePopUp(AddorModDepreciationProfile tHIS, DepreciationProfileMain parent1, InnerPanel innerPanel,
			FinanceInterface finter, OuterFrame parent, int mode, int appFlag) {

		super(parent, true);

		this.tHIS = tHIS;
		this.parent1 = parent1;
		this.innerPanel = innerPanel;
		this.finter = finter;
		this.parent = parent;
		this.mode = mode;
		this.THIS = this;
		this.appFlag = appFlag;
		Vector finaldata = tHIS.sst_AssetDetailsTable.getDataVector();
		if (finaldata.size() == 0) {
			tHIS.typePath = new HashMap<>();
		}

		initialize();
	}

	private void initialize() {

		this.getContentPane().setLayout(null);
		this.setContentPane(getJContentPane());
		this.setLocation(400, 200);
		this.setSize(800, 600);
		this.setTitle("Asset Type Pop-Up");

		new Design("Order_Planning").setColors(this.getJContentPane());

	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);

			jContentPane.add(getsst_AssetMainTable(), null);
			jContentPane.add(getjb_Add(), null);
			jContentPane.add(getjb_Cancel(), null);

		}
		return jContentPane;
	}

	private SortSelectTable getsst_AssetMainTable() {
		if (sst_AssetMainTable == null) {
			sst_AssetMainTable = new SortSelectTable();
			sst_AssetMainTable.setBounds(new Rectangle(40, 50, 500, 250));
			sst_AssetMainTable.setName("DepreciationProfile_sst_AssetMainTable");
//			fillDepreciationPeriod();
			fillAsset();
		}
		return sst_AssetMainTable;
	}

	public void fillAsset() {
		Vector data = new Vector();
		data.add("Asset Classfication");
		data.add("Asset Type");
		data.add("Select");
		Vector finalvector = new Vector();

		Vector exdata = tHIS.sst_AssetDetailsTable.getDataVector();
		String profile_id = "";
		try {
			profile_id = finter.get_String("select profile_id from depreciation_profile where profile_name ='"
					+ tHIS.jtf_ProfileName.getText().toString() + "'");
			if (profile_id == null || profile_id.length() == 0) {
				profile_id = finter.get_String("select profile_id from a_depreciation_profile where profile_name ='"
						+ tHIS.jtf_ProfileName.getText().toString() + "'");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (profile_id == null || profile_id.length() == 0) {
			profile_id = "0";
		}
		String condition = "";
		if (exdata != null && exdata.size() > 0) {
			for (int i = 0; i < exdata.size(); i++) {
				Vector temp = (Vector) exdata.get(i);
				if (i == 0) {
					condition = "('" + temp.get(0).toString() + "'" + "," + "'" + temp.get(1).toString() + "')";
				} else {
					condition = condition + "," + "('" + temp.get(0).toString() + "'" + "," + "'"
							+ temp.get(1).toString() + "')";
					;
				}
			}
		} else {
			condition = "(' ',' ')";
		}
		String assetReg = " SELECT tab1.CLASSIFICATION_NAME, tab1.\"type\"\n" + "FROM (\n"
				+ "    SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "    FROM FIXED_MATERIAL_VIEW\n"
				+ "    WHERE ASSET_CATEGORY = 'Tangible'\n" + "    AND (CLASSIFICATION_NAME, \"type\") NOT IN (\n"
				+ "        SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "        FROM fixed_material_view\n"
				+ "        WHERE (CLASSIFICATION_id, \"type\") IN (\n"
				+ "            SELECT DISTINCT des.asset_class, des.asset_type\n"
				+ "            FROM depreciation_asset_details des\n"
				+ "            INNER JOIN depreciation_profile dp\n" + "            ON dp.profile_id = des.profile_id\n"
				+ "            and DP.STATUS = 'Active'\n" + "            and dp.profile_id not in ('" + profile_id + "')\n"
				+ "            AND dp.APP_STATUS != 'Deleted'\n" + "           \n" + "        )\n"
				+ "    ) and  CLASSIFICATION_NAME not in (SELECT DISTINCT CLASSIFICATION_NAME\n"
				+ "    FROM FIXED_MATERIAL_VIEW where CLASSIFICATION_id in (select ASSET_CLASS from depreciation_asset_details asd  INNER JOIN  depreciation_profile dp  ON  dp.profile_id = asd.profile_id   where  ASSET_TYPE is null  AND dp.APP_STATUS != 'Deleted'))\n"
				+ ") tab1, (\n" + "    SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n"
				+ "    FROM FIXED_MATERIAL_VIEW\n" + "    WHERE ASSET_CATEGORY = 'Tangible'\n"
				+ "    AND (CLASSIFICATION_NAME, \"type\") NOT IN (\n"
				+ "        SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "        FROM fixed_material_view\n"
				+ "        WHERE (CLASSIFICATION_id, \"type\") IN (\n"
				+ "            SELECT DISTINCT des.asset_class, des.asset_type\n"
				+ "            FROM A_depreciation_asset_details des\n"
				+ "            INNER JOIN A_depreciation_profile dp\n"
				+ "            ON dp.profile_id = des.profile_id\n" + "            and dp.profile_id not in ('" + profile_id + "')\n"
				+ "            AND dp.status = 'Active'\n" + "        )\n"
				+ "    ) and  CLASSIFICATION_NAME not in (SELECT DISTINCT CLASSIFICATION_NAME\n"
				+ "    FROM FIXED_MATERIAL_VIEW where CLASSIFICATION_id in (select ASSET_CLASS from depreciation_asset_details asd  INNER JOIN  depreciation_profile dp  ON  dp.profile_id = asd.profile_id   where  ASSET_TYPE is null  AND dp.APP_STATUS != 'Deleted'))\n"
				+ ") TAB2 where TAB1.CLASSIFICATION_NAME = TAB2.CLASSIFICATION_NAME and TAB1.\"type\" = TAB2.\"type\" and (TAB1.CLASSIFICATION_NAME,TAB1.\"type\") not in ("+ condition + ")";

//		String assetReg = "\n" + "SELECT tab1.CLASSIFICATION_NAME, tab1.\"type\"\n" + "FROM (\n"
//				+ "    SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "    FROM FIXED_MATERIAL_VIEW\n"
//				+ "    WHERE ASSET_CATEGORY = 'Tangible'\n" + "    AND (CLASSIFICATION_NAME, \"type\") NOT IN (\n"
//				+ "        SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "        FROM fixed_material_view\n"
//				+ "        WHERE (CLASSIFICATION_id, \"type\") IN (\n"
//				+ "            SELECT DISTINCT des.asset_class, des.asset_type\n"
//				+ "            FROM depreciation_asset_details des\n"
//				+ "            INNER JOIN depreciation_profile dp\n" + "            ON dp.profile_id = des.profile_id\n"
//				+ "            and DP.STATUS = 'Active'\n" + "            and dp.profile_id not in ('" + profile_id
//				+ "')\n" + "            AND dp.APP_STATUS != 'Deleted'\n" + "        )\n" + "    )\n" + ") tab1, (\n"
//				+ "    SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "    FROM FIXED_MATERIAL_VIEW\n"
//				+ "    WHERE ASSET_CATEGORY = 'Tangible'\n" + "    AND (CLASSIFICATION_NAME, \"type\") NOT IN (\n"
//				+ "        SELECT DISTINCT CLASSIFICATION_NAME, \"type\"\n" + "        FROM fixed_material_view\n"
//				+ "        WHERE (CLASSIFICATION_id, \"type\") IN (\n"
//				+ "            SELECT DISTINCT des.asset_class, des.asset_type\n"
//				+ "            FROM A_depreciation_asset_details des\n"
//				+ "            INNER JOIN A_depreciation_profile dp\n"
//				+ "            ON dp.profile_id = des.profile_id\n" + "            and dp.profile_id not in ('"
//				+ profile_id + "')\n" + "            AND dp.status = 'Active'\n" + "        )\n" + "    )\n"
//				+ ") TAB2 where TAB1.CLASSIFICATION_NAME = TAB2.CLASSIFICATION_NAME and TAB1.\"type\" = TAB2.\"type\" and (TAB1.CLASSIFICATION_NAME,TAB1.\"type\") not in ("
//				+ condition + ")";

		try {
			Vector dataAssetDetails = finter.LoadVectorwithContentswithBoolean(assetReg);

			for (int i = 0; i < dataAssetDetails.size(); i++) {
				boolean found = false;

				for (int j = 0; j < exdata.size(); j++) {
					Vector predata = (Vector) exdata.get(j);
					Vector dbdata1 = (Vector) dataAssetDetails.get(i);

					// Check if the first element of the current dataAssetDetails entry is already
					// present in exdata
					if (predata.get(0).equals(dbdata1.get(0)) && predata.get(1).equals(dbdata1.get(1))) {
						found = true;
						break; // No need to continue checking, as we found a match
					}
				}

				// If the entry is not found in exdata, add it to finalvector
				if (!found) {
					finalvector.add(dataAssetDetails.get(i));
				}
			}

			Vector edit = new Vector();
			edit.add(data.indexOf("Select") + "");
			Vector Selectall = new Vector();
			Selectall.add("Select");
			sst_AssetMainTable.setTable(finalvector, data, "Order_Planning", "Fixed Asset Type Details", false,
					new Vector(), new Vector(), new Vector(), renderer, THIS, edit, Selectall);

			LstItem = sst_AssetMainTable.table.getSelectionModel();
			sst_AssetMainTable.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			sst_AssetMainTable.table.getModel().addTableModelListener(acctListener);

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	static class MyTableCellRenderer implements TableCellRenderer {
		public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean haParentocus, int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, haParentocus,
					row, column);
			return renderer;
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
		if (jb_Add == null) {
			jb_Add = new JButton();
			jb_Add.setBounds(150, 400, 100, 100);
			jb_Add.setText("Add");
			jb_Add.addActionListener(this);
		}
		return jb_Add;
	}

	private JButton getjb_Cancel() {
		if (jb_Cancel == null) {
			jb_Cancel = new JButton();
			jb_Cancel.setBounds(270, 400, 100, 100);
			jb_Cancel.setText("Cancel");
			jb_Cancel.addActionListener(this);
		}
		return jb_Cancel;
	}

	private JCheckBox get_jckb_AssetType() {
		if (jckb_AssetType == null) {
			jckb_AssetType = new JCheckBox();
			jckb_AssetType.setText("Select All");
			jckb_AssetType.setBounds(450, 30, 100, 30);
			jckb_AssetType.addActionListener(this);
		}
		return jckb_AssetType;
	}

//	public void reloaddata() {
//		Vector finaldata = tHIS.sst_AssetDetailsTable.getDataVector();
//
//		// Assuming typePath is a HashMap<String, Vector>
//		if (tHIS.typePath.size() > 0 && finaldata.size() > 0) {
//			System.out.println("typePath before updating boolean values: " + tHIS.typePath);
//
//			for (int i = 0; i < finaldata.size(); i++) {
//				Vector finalDataRow = (Vector) finaldata.get(i);
//
//				// Get the key (class name) from the first element of the row
//				String key = finalDataRow.get(0).toString();
//
//				// Check if the key exists in the typePath HashMap
//				if (tHIS.typePath.containsKey(key)) {
//					Vector fullPathRow = tHIS.typePath.get(key);
//
//					// Check if finalDataRow and fullPathRow are equal
//					if (finalDataRow.equals(fullPathRow)) {
//						// Set all values in fullPathRow to true
//						for (int k = 1; k < fullPathRow.size(); k++) {
//							fullPathRow.set(k, true);
//						}
//					}
//				}
//			}
//
//			// Now typePath has the boolean values updated
//			System.out.println("typePath after updating boolean values: " + tHIS.typePath);
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb_Cancel) {
			parent.showMsg("");
			dispose();

		}

//		if (e.getSource() == jb_Add) {
//
//			try {
//
//				Vector alldata = sst_AssetMainTable.getDataVector();
//				Vector data_vec = new Vector();
//				for (int i = 0; i < alldata.size(); i++) {
//					if ((Boolean) ((Vector) alldata.get(i))
//							.get(sst_AssetMainTable.table.getColumnModel().getColumnIndex("Select"))) {
//						data_vec.add(alldata.get(i));
//					}
//				}
//
//				if (data_vec != null && data_vec.size() > 0) {
//					Vector parentdata1 = new Vector();
//					parentdata1 = tHIS.sst_AssetDetailsTable.getDataVector();
//
//					for (int i = 0; i < data_vec.size(); i++) {
//						Vector data = new Vector();
//						data.add(((Vector) data_vec.get(i)).get(0).toString());
//						if (parentdata1 != null) {
//							for (int k = 0; k < parentdata1.size(); k++) {
//								Vector ab = (Vector) parentdata1.get(k);
//								if (data.get(0).equals(ab.get(0))) {
//									parent.showMsg("This Classsification " + ab.get(0) + "Is Aleady Added");
//									return;
//								}
//
//							}
//						}
//						data.add(((Vector) data_vec.get(i)).get(1).toString());
//						data.add(false);
//						parentdata1.add(data);
//
//						// Update typePath as well
//						Vector redata = (Vector) data_vec.get(i);
//						String key = redata.get(0).toString();
//						tHIS.typePath.put(key, redata);
//					}
//
//					tHIS.AssetDetails(parentdata1);
//					parent.showMsg("");
//					System.out.println("Full Path after adding: " + tHIS.typePath);
//
//					dispose();
//
//				} else {
//					JOptionPane.showMessageDialog(null, "Select one Classification");
//					return;
//				}
//
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//
//		}

		if (e.getSource() == jb_Add) {

			Vector alldata = sst_AssetMainTable.getDataVector();
			Vector data_vec = new Vector();

			int selectedrow = sst_AssetMainTable.table.getSelectedRowCount();
			// Iterate through all rows in alldata
			for (int i = 0; i < alldata.size(); i++) {
				Vector rowData = (Vector) alldata.get(i);

				// Check if the "Select" column is true for the current row
				if ((Boolean) rowData.get(sst_AssetMainTable.table.getColumnModel().getColumnIndex("Select"))) {
					data_vec.add(rowData);
				}
			}

			if (data_vec != null && data_vec.size() > 0 || selectedrow > 0) {
				Vector parentdata1 = new Vector();
				parentdata1 = tHIS.sst_AssetDetailsTable.getDataVector();
				if (parentdata1 == null) {
					parentdata1 = new Vector();
				}
				// You might want to clear the existing data in parentdata1 based on your logic
				// parentdata1.clear();

				for (int i = 0; i < data_vec.size(); i++) {
					Vector nullex = (Vector) data_vec.get(i);

					Vector data = new Vector();
					if (nullex.get(0) != null && nullex.get(1) != null) {
						// Add only specific columns to the new vector
						data.add(((Vector) data_vec.get(i)).get(0).toString()); // Assuming the first column needs to be
																				// added
						data.add(((Vector) data_vec.get(i)).get(1).toString());
						data.add(false); // Add a default value or specific value for the second column

						parentdata1.add(data);
					}

				}

				tHIS.AssetDetails(parentdata1);

				parent.showMsg("");
				dispose();
//			}

			} else {
				parent.showMsg("Select One Row");
			}

		}

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
	public boolean callFileterEvent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JMenuBar getMenuBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGUIDiffReference(GUIDifference arg0) {
		// TODO Auto-generated method stub

	}

}
