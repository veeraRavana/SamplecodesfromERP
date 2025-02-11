/**
 * 
 * @author Sanggetha
 * Added for requirement#- Fixed Asset SN53-CWIP
 *
 **/
package HarnessErp;

import java.awt.Rectangle;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import common.Design;
import common.Utools;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.SortSelectTable;

public class AssetPopup extends JDialog implements ActionListener, sstFilterEvent {
private Design design                       = new Design("Order_Planning");
private static final long serialVersionUID  = 1L;
private AddorModCwip parent                 = null;
private FinanceInterface fInter             = null;
private FinanceController fc                = null;
private AssetPopup THIS                     = null;
InnerPanel innerPanel					    = null;
ListSelectionModel LstItem                  = null;	
private JLabel jl_classification            = null,
			   jl_assettype                 = null;
private JComboBox classification_cb         = null,
                  assettype_cb              = null;
private JButton jb_load			            = null,
			    jb_add                      = null,
			    jb_cancel                   = null;
public SortSelectTable Sst_assetmatdetails  = null;
Vector heading                              = new Vector();
Vector Data                                 = new Vector();
Vector allClass                             = new Vector();
boolean assetcode                           = false;

public AssetPopup(AddorModCwip parent, FinanceInterface fInter, FinanceController fc,boolean assetcode) {
	// TODO Auto-generated constructor stub
	super(parent.outerFrame, true);
	this.parent          = parent;
	this.fInter          = fInter;
	this.fc              = fc;
	this.innerPanel      = fc.innerPanel;
	this.assetcode       = assetcode;
	parent.outerFrame.showMsg("");
	initialize();
	filltable();
}
	
private void initialize() {
	this.getContentPane().setLayout(null);
	this.setSize(650, 400);
	this.setModalityType(ModalityType.APPLICATION_MODAL);
	this.setLocation(250, 190);
	setResizable(false);
	this.getContentPane().add(getJl_classification(), null);
	this.getContentPane().add(getJcb_classification(), null);
	this.getContentPane().add(getJl_assettype(), null);
	this.getContentPane().add(getJcb_assettype(), null);
	this.getContentPane().add(getJb_load(), null);
	this.getContentPane().add(getSst_assetmatdetails(), null);
	this.getContentPane().add(getJb_Add(), null);
	this.getContentPane().add(getJb_Cancel(), null);
	design.setColors(this);
}

private JLabel getJl_classification() {
	if (jl_classification == null) {
		jl_classification = new JLabel();
		jl_classification.setText("Classification");
		jl_classification.setBounds(new Rectangle(30, 35, 110, 30));
	}
	return jl_classification;
}
	  
private JComboBox getJcb_classification() {
	if (classification_cb == null) {
		Vector allClass_1 = new Vector();
		try {
			
			allClass = fInter.LoadVectorContents("select DISTINCT CLASSIFICATION_NAME from FIXED_MATERIAL_VIEW where CLASSIFICATION_NAME is not null and CWIP_ASSET in ('Y','T')");
			allClass_1.addElement("");
			for (int i = 0; i < allClass.size(); i++) {
				allClass_1.addElement(allClass.get(i));
				}
			allClass_1.addElement("All");
			classification_cb = new JComboBox(allClass_1);
			classification_cb.setBounds(new java.awt.Rectangle(100, 35, 130, 30));
			classification_cb.removeActionListener(this);
			classification_cb.setSelectedItem("");
			classification_cb.addActionListener(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	return classification_cb;
}
	  
private JLabel getJl_assettype() {
	if (jl_assettype == null) {
		jl_assettype = new JLabel();
		jl_assettype.setText("Asset Type");
		jl_assettype.setBounds(new Rectangle(280, 35, 110, 30));
	}
	return jl_assettype;
}
		  
private JComboBox getJcb_assettype() {
	if (assettype_cb == null) {
		assettype_cb = new JComboBox();
		assettype_cb.setBounds(new java.awt.Rectangle(350, 35, 130, 30));
	}
	return assettype_cb;
}
	   
private JButton getJb_load() {
	if (jb_load == null) {
		jb_load = new JButton();
		jb_load.setText("Load");
		jb_load.setBounds(new java.awt.Rectangle(500, 35, 80, 30));
		jb_load.addActionListener(this);
	}
	return jb_load;
}
	   
private SortSelectTable getSst_assetmatdetails() {
	if (Sst_assetmatdetails == null) {
		Sst_assetmatdetails = new SortSelectTable();
		Sst_assetmatdetails.setBounds(new Rectangle(20, 80, 600, 210));
	}
	return Sst_assetmatdetails;
}
	    
private JButton getJb_Add() {
	if (jb_add == null) {
		jb_add = new JButton();
		jb_add.setText("Add");
		jb_add.setBounds(new java.awt.Rectangle(180, 300, 80, 30));
		jb_add.addActionListener(this);
	}
	return jb_add;
}
	    
private JButton getJb_Cancel() {
	if (jb_cancel == null) {
		jb_cancel = new JButton();
		jb_cancel.setText("Cancel");
		jb_cancel.setBounds(new java.awt.Rectangle(330, 300, 80, 30));
		jb_cancel.addActionListener(this);
	}
	return jb_cancel;
}
	    
private void filltable() {
	// TODO Auto-generated method stub
	parent.outerFrame.showMsg("");
	Utools.setMouseBusy(innerPanel);
	Vector hide = new Vector();

	heading = new Vector();
	heading.add("Mat No");
	if (assetcode) {
		heading.add("Asset ID");
		heading.add("Asset Code");
	}
	heading.add("Classification");
	heading.add("Type");
	heading.add("Spec");
	heading.add("UOM");
	if (assetcode) {
		heading.add("Status");
		hide.addElement("Mat No");
		hide.addElement("Asset ID");
	}
	Sst_assetmatdetails.setTable(Data, heading, "Order_Planning", "Asset Master Details", true, hide, new Vector(),false);
	Sst_assetmatdetails.repaint();
	LstItem = Sst_assetmatdetails.table.getSelectionModel();
	Sst_assetmatdetails.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

}

private void assetdata() {
	
	int row = Sst_assetmatdetails.table.getSelectedRow();
	String status = "-", asset_code_no, asset = "",asset_id="";
	if (assetcode) {
		asset_id      = Sst_assetmatdetails.table
				.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Asset ID"))
				.toString();
		asset_code_no = Sst_assetmatdetails.table
				.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Asset Code"))
				.toString();
		parent.jtf_assetcode.setText(asset_code_no.toString());
		asset = Sst_assetmatdetails.table
				.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Mat No"))
				.toString();
		parent.jtf_matno.setText(asset.toString());
	} else {
		asset_code_no = Sst_assetmatdetails.table
				.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Mat No"))
				.toString();
		parent.jtf_matno.setText(asset_code_no.toString());
	}
	String classif = Sst_assetmatdetails.table
			.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Classification"))
			.toString();
	parent.jtf_assetclass.setText(classif.toString());
	String type = Sst_assetmatdetails.table
			.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Type")).toString();
	parent.jtf_assettype.setText(type.toString());
	String spec = Sst_assetmatdetails.table
			.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Spec")).toString();
	String uom = Sst_assetmatdetails.table
			.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("UOM")).toString();
	if (assetcode) {
		status = Sst_assetmatdetails.table
				.getValueAt(row, Sst_assetmatdetails.table.getColumnModel().getColumnIndex("Status"))
				.toString();
	}
	parent.asset_data.add(asset);
	parent.asset_data.add(asset_id);
	parent.asset_data.add(asset_code_no);
	parent.asset_data.add(classif);
	parent.asset_data.add(type);
//	parent.asset_data.add(spec);
//	parent.asset_data.add(uom);
//	parent.asset_data.add(status);

}


@Override
public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
	if (e.getSource() == jb_add) {
		if (Sst_assetmatdetails.table.getCellEditor() != null) {
			Sst_assetmatdetails.table.getCellEditor().stopCellEditing();
		}
		parent.asset_data = new Vector();
		if (Sst_assetmatdetails.table.getSelectedRow() >= 0) {
			if (parent.jtf_matno.getText().equalsIgnoreCase("")) {
				assetdata();
				} else {
				if (JOptionPane.showConfirmDialog(null,
						"Changing Link Asset will reset the entire Data. Do you want to proceed?", "Confirm",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					assetdata();
				} else {
					Utools.setMouseNormal(innerPanel);
					this.dispose();
				}
			}
		} else {
			if (assetcode) {
				parent.outerFrame.showMsg("Please select Asset", jb_add);
				return;
			} else {
				parent.outerFrame.showMsg("Please select MAT No", jb_add);
				return;
			}
		}
		dispose();
	} else if (e.getSource() == jb_cancel) {
		Utools.setMouseNormal(innerPanel);
		this.dispose();
	} else if (e.getSource() == jb_load) {
		if(classification_cb.getSelectedItem().toString().trim().equalsIgnoreCase("") || assettype_cb.getSelectedItem().toString().trim().equalsIgnoreCase("") ) {
			parent.outerFrame.showMsg("Please select Classification and Type to proceed");
			return;
		}
		fillDataWithCondition();
		
	}else if(e.getSource() == classification_cb) {
		try {
			Vector allType = new Vector();
			String class_1 = classification_cb.getSelectedItem() + "";
			if (class_1.equalsIgnoreCase("All")) {
				allType = fInter.LoadVectorContents("select DISTINCT \"type\" from FIXED_MATERIAL_VIEW where \"type\" is not null and CWIP_ASSET in ('Y','T')");
			} else {
				allType = fInter.LoadVectorContents("select DISTINCT \"type\" from FIXED_MATERIAL_VIEW where \"type\" is not null and CWIP_ASSET in ('Y','T') and CLASSIFICATION_NAME in ('"
								+ class_1 + "')");
			}
			assettype_cb.removeAllItems();
			assettype_cb.addItem("");
			for (int i = 0; i < allType.size(); i++) {
				assettype_cb.addItem(allType.get(i).toString());
			}
			assettype_cb.addItem("All");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

}

@Override
public boolean callFileterEvent() {
// TODO Auto-generated method stub
	return false;
}
public void fillDataWithCondition(){

	try {
		String condition = "";
		if (classification_cb.getSelectedItem() != null
				&& !classification_cb.getSelectedItem().toString().equalsIgnoreCase("All")) {
			String classfic = classification_cb.getSelectedItem().toString().trim();
			if (!"".equalsIgnoreCase(classfic)) {
				condition += " where CLASSIFICATION ='" + classfic + "'";
			}
		}
		if (assettype_cb.getSelectedItem() != null
				&& !assettype_cb.getSelectedItem().toString().equalsIgnoreCase("All")) {
			String type = assettype_cb.getSelectedItem().toString().trim();
			if (!"".equalsIgnoreCase(type)) {
				if (condition.equalsIgnoreCase("")) {
					condition += " where CWIP_ASSET='Y' and typ ='" + type + "'";
				} else {
					condition += " and typ ='" + type + "' and CWIP_ASSET='Y'";
				}
			}
		}
		if (assetcode) {
			if (condition.equalsIgnoreCase("")) {
				Data = fInter.LoadVectorwithContents("select a.mat_no,a.asset_id,a.asset_code, b.classification_name as classification,b.typ,b.spc,b.uom1,a.status from (select mat_no,asset_id,asset_code,status from asset_register_detail where status='Active') a\n"
						+ "LEFT JOIN (SELECT CLASSIFICATION_ID,classification_name,\"type\" as typ,\"Specs\" as spc,uom1,CWIP_ASSET,mat_no FROM FIXED_MATERIAL_VIEW WHERE cwip_asset ='Y' ) B ON B.mat_no=A.mat_NO");
			} else {
				Data = fInter.LoadVectorwithContents("select mat_no,asset_id,asset_code,classification,typ,spc,uom1,status,CWIP_ASSET from(select a.mat_no,a.asset_id, a.asset_code,b.classification_name as classification,b.typ,b.spc,b.uom1,\n"
						+ "a.status,b.CWIP_ASSET from (select mat_no,asset_id,asset_code, status from asset_register_detail where status='Active') a left join (select classification_id,classification_name,\"type\"  as typ,\n"
						+ "\"Specs\" AS spc,uom1,CWIP_ASSET,mat_no FROM FIXED_MATERIAL_VIEW WHERE CWIP_ASSET ='Y') b ON B.mat_no =A.mat_no)"+condition+"");
				}
		} else {
			if (condition.equalsIgnoreCase("")) {
				Data = fInter.LoadVectorwithContents("SELECT MAT_NO,CLASSIFICATION_NAME,\"type\" as typ,\"Specs\",UOM1 from FIXED_MATERIAL_VIEW where CWIP_ASSET='Y'");
			} else {
				Data = fInter.LoadVectorwithContents("select MAT_NO,CLASSIFICATION, typ,\"Specs\",UOM1 from  (SELECT MAT_NO,CLASSIFICATION_NAME as CLASSIFICATION,\"type\" as typ,\"Specs\",UOM1,CWIP_ASSET from FIXED_MATERIAL_VIEW ) "+ condition + "");
			}
		}
		filltable();
	} catch (RemoteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

}

	
}
