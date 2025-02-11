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
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import HarnessErp.AssetTypePopUp.MyTableCellRenderer;
import common.Design;
import common.GUIDiffInterface;
import common.GUIDifference;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;

public class AssetClassificationPopUP extends JDialog
		implements ActionListener, ListSelectionListener, sstFilterEvent, GUIDiffInterface {

	AssetClassificationPopUP THIS = null;
	AddorModDepreciationProfile tHIS = null;
	DepreciationProfileMain parent1 = null;
	InnerPanel innerPanel = null;
	FinanceInterface finter = null;
	OuterFrame parent = null;
	int mode = -1;
	int appFlag = -1;

	private JPanel jContentPane = null;
	public SortSelectTable sst_AssetClassification = null;
	ListSelectionModel LstItem = null;
	private JButton jb_Add = null;
	private JButton jb_Cancel = null;
	private JCheckBox jckb_AssetClass = null;
	private MyTableCellRenderer renderer = new MyTableCellRenderer();
	Vector redata = new Vector();
	String key = "";
	int sal = 0;

	public AssetClassificationPopUP(AddorModDepreciationProfile tHIS, DepreciationProfileMain parent1,
			InnerPanel innerPanel, FinanceInterface finter, OuterFrame parent, int mode, int appFlag) {
		// TODO Auto-generated constructor stub

		super(parent, true);

		this.tHIS = tHIS;
		this.parent1 = parent1;
		this.innerPanel = innerPanel;
		this.finter = finter;
		this.parent = parent;
		this.mode = mode;
		this.THIS = this;
		this.appFlag = appFlag;
		Vector finaldata = tHIS.sst_AssetClassification.getDataVector();
		if (finaldata.size() == 0) {
			tHIS.fullPath = new HashMap<>();
		}
		initialize();

	}

	private void initialize() {

		this.getContentPane().setLayout(null);
		this.setContentPane(getJContentPane());
		this.setLocation(400, 200);
		this.setSize(800, 600);
		this.setTitle("Asset Classification Pop-up");

		new Design("Order_Planning").setColors(this.getJContentPane());

	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);

			jContentPane.add(getsst_AssetClassification(), null);
			jContentPane.add(getjb_Add(), null);
			jContentPane.add(getjb_Cancel(), null);
//			jContentPane.add(get_jckb_AssetClass(), null);

		}
		return jContentPane;
	}

	private SortSelectTable getsst_AssetClassification() {
		if (sst_AssetClassification == null) {
			sst_AssetClassification = new SortSelectTable();
			sst_AssetClassification.setBounds(new Rectangle(40, 50, 500, 250));
			sst_AssetClassification.setName("Depreciation_sst_AssetClassification");
//			fillAssetClassification();
			fillclass();
		}
		return sst_AssetClassification;
	}

	public void fillclass() {
		Vector data = new Vector();
		data.add("Asset Classfication");
		data.add("Select");
		Vector finalvector = new Vector();

		Vector exdata = tHIS.sst_AssetClassification.getDataVector();
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
					condition = "('" + temp.get(0).toString() + "')";
				} else {
					condition = condition + "," + "('" + temp.get(0).toString() + "')";
				}
			}
		} else {
			condition = "(' ')";
		}

		String assetReg = "select tab1.CLASSIFICATION_NAME from\n"
				+ "(SELECT DISTINCT CLASSIFICATION_NAME\n"
				+ "FROM FIXED_MATERIAL_VIEW\n"
				+ "WHERE ASSET_CATEGORY = 'Tangible'\n"
				+ "AND CLASSIFICATION_NAME NOT IN (\n"
				+ "    SELECT DISTINCT CLASSIFICATION_NAME\n"
				+ "    FROM fixed_material_view\n"
				+ "    WHERE CLASSIFICATION_id IN (\n"
				+ "        SELECT DISTINCT des.asset_class\n"
				+ "        FROM depreciation_asset_details des\n"
				+ "        INNER JOIN depreciation_profile dp\n"
				+ "        ON dp.profile_id = des.profile_id\n"
				+ "        AND dp.status = 'Active'\n"
				+ "            and dp.profile_id not in ('" + profile_id + "')\n"
				+ "        and dp.app_status != 'Deleted'\n"
				+ "    ) \n"
				+ ")) tab1,\n"
				+ "(SELECT DISTINCT CLASSIFICATION_NAME\n"
				+ "FROM FIXED_MATERIAL_VIEW\n"
				+ "WHERE ASSET_CATEGORY = 'Tangible'\n"
				+ "AND CLASSIFICATION_NAME NOT IN (\n"
				+ "    SELECT DISTINCT CLASSIFICATION_NAME\n"
				+ "    FROM fixed_material_view\n"
				+ "    WHERE CLASSIFICATION_id IN (\n"
				+ "        SELECT DISTINCT des.asset_class\n"
				+ "        FROM a_depreciation_asset_details des\n"
				+ "        INNER JOIN a_depreciation_profile dp\n"
				+ "        ON dp.profile_id = des.profile_id\n"
				+ "        AND dp.status = 'Active'  \n"
				+ "            and dp.profile_id not in ('" + profile_id + "')\n"
				+ "    ) \n"
				+ ")) tab2 where tab1.CLASSIFICATION_NAME =tab2.CLASSIFICATION_NAME and (TAB1.CLASSIFICATION_NAME) not in ("+ condition + ")";
				
				
//				select tab1.CLASSIFICATION_NAME from\n" + "(SELECT DISTINCT CLASSIFICATION_NAME\n"
//				+ "FROM FIXED_MATERIAL_VIEW\n" + "WHERE ASSET_CATEGORY = 'Tangible'\n"
//				+ "AND CLASSIFICATION_NAME NOT IN (\n" + "    SELECT DISTINCT CLASSIFICATION_NAME\n"
//				+ "    FROM fixed_material_view\n" + "    WHERE CLASSIFICATION_id IN (\n"
//				+ "        SELECT DISTINCT des.asset_class\n" + "        FROM depreciation_asset_details des\n"
//				+ "        INNER JOIN depreciation_profile dp\n" + "        ON dp.profile_id = des.profile_id\n"
//				+ "        AND dp.status = 'Active'\n" + "            and dp.profile_id not in ('" + profile_id + "')\n"
//				+ "        and dp.app_status != 'Deleted'\n" + "        AND des.asset_class IS NOT NULL \n"
//				+ "(SELECT DISTINCT CLASSIFICATION_NAME\n" + "FROM FIXED_MATERIAL_VIEW\n"
//				+ "WHERE ASSET_CATEGORY = 'Tangible'\n" + "AND CLASSIFICATION_NAME NOT IN (\n"
//				+ "    SELECT DISTINCT CLASSIFICATION_NAME\n" + "    FROM fixed_material_view\n"
//				+ "    WHERE CLASSIFICATION_id IN (\n" + "        SELECT DISTINCT des.asset_class\n"
//				+ "        FROM a_depreciation_asset_details des\n" + "        INNER JOIN a_depreciation_profile dp\n"
//				+ "        ON dp.profile_id = des.profile_id\n" + "        AND dp.status = 'Active'  \n"
//				+ "            and dp.profile_id not in ('" + profile_id + "')\n"
//				+ ")) tab2 where tab1.CLASSIFICATION_NAME =tab2.CLASSIFICATION_NAME and (TAB1.CLASSIFICATION_NAME) not in ("
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
					if (predata.get(0).equals(dbdata1.get(0))) {
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
			sst_AssetClassification.setTable(finalvector, data, "Order_Planning", "Fixed Asset Classification Details",
					false, new Vector(), new Vector(), new Vector(), renderer, THIS, edit, Selectall);

			LstItem = sst_AssetClassification.table.getSelectionModel();
			sst_AssetClassification.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_AssetClassification.table.getModel().addTableModelListener(acctListener);

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

	private JCheckBox get_jckb_AssetClass() {
		if (jckb_AssetClass == null) {
			jckb_AssetClass = new JCheckBox();
			jckb_AssetClass.setText("Select All");
			jckb_AssetClass.setBounds(450, 30, 100, 30);
			jckb_AssetClass.addActionListener(this);
		}
		return jckb_AssetClass;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb_Cancel) {
			parent.showMsg("");
			dispose();
			THIS.dispose();
		}

		if (e.getSource() == jb_Add) {

			Vector alldata = sst_AssetClassification.getDataVector();
			Vector data_vec = new Vector();
			int selectedrow = sst_AssetClassification.table.getSelectedRowCount();
			for (int i = 0; i < alldata.size(); i++) {
				Vector rowData = (Vector) alldata.get(i);
				if ((Boolean) rowData.get(sst_AssetClassification.table.getColumnModel().getColumnIndex("Select"))) {
					data_vec.add(rowData);
				}
			}

			if (data_vec != null && data_vec.size() > 0 || selectedrow > 0) {
				Vector parentdata1 = new Vector();
				parentdata1 = tHIS.sst_AssetClassification.getDataVector();
				if (parentdata1 == null) {
					parentdata1 = new Vector();
				}
				// You might want to clear the existing data in parentdata1 based on your logic
				// parentdata1.clear();

				for (int i = 0; i < data_vec.size(); i++) {
					Vector nullex = (Vector) data_vec.get(i);
					Vector data = new Vector();
					if (nullex.get(0) != null) {
						// Add only specific columns to the new vector
						data.add(((Vector) data_vec.get(i)).get(0).toString()); // Assuming the first column needs to be
																				// added
						data.add(false); // Add a default value or specific value for the second column
						parentdata1.add(data);
					}

				}

				tHIS.fillAssetClass(parentdata1);

				parent.showMsg("");
				dispose();
//			}

			} else {
				parent.showMsg("Select One Row");
			}

		}

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

	@Override
	public boolean callFileterEvent() {
		// TODO Auto-generated method stub
		return false;
	}

}
