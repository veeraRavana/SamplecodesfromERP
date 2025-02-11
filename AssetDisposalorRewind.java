package HarnessErp;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import common.Design;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;

public class AssetDisposalorRewind extends JInternalFrame
		implements ActionListener, InternalFrameListener, ListSelectionListener {

	AssetDisposalMain tHIS = null;
	InnerPanel innerPanel = null;
	FinanceInterface finter = null;
	OuterFrame parent = null;
	int mode = -1;
	AssetDisposalorRewind THIS;

	private JPanel jContentPane = null;
	private JMenuBar jMenuBar = null;
	private JMenuItem jmi_save = null;
	private JMenuItem jmi_cancel = null;
	private JMenu jm_action = null;

	private JLabel jl_DisposalDcNo = null;
	private JTextField jc_DisposalDc = null;
	private JLabel jl_DisposalDate = null;
	private TDateField jdf_DisposalDate = null;
	private JLabel jl_Disposalcategory = null;
	private JLabel jl_Location = null;
	private JComboBox jc_DisposalCategory = null;
	private JComboBox jc_Location = null;

	private JLabel jl_DisposalType = null;
	private JComboBox jc_DisposalType = null;
	ListSelectionModel LstItem = null;
	private JButton jb_DisposalSchedule = null;
	private JButton jb_frAuditvar = null;
	private JButton jb_AssetReg = null;
	private JLabel jl_DisposalRemarks = null;
	private JTextArea jdf_Remarks = null;
	public SortSelectTable sst_DisposalAssetDetailsTable1 = null;
	private JCheckBox jckb_SelectAll1 = null;
	private JButton jb_Delete = null;
	private JButton jb_Dispose = null;
	private JButton jb_Cancel = null;
	private JLabel jl_ReasonfrRewind = null;
	private JTextArea jdf_ReasonfrRewind = null;
	private JLabel jl_Invoice = null;
	private JCheckBox jckb_Invoice = null;

	public AssetDisposalorRewind(AssetDisposalMain tHIS, InnerPanel innerPanel, FinanceInterface finter,
			OuterFrame parent, int mode) {
		// TODO Auto-generated constructor stub
		this.tHIS = tHIS;
		this.innerPanel = innerPanel;
		this.finter = finter;
		this.parent = parent;
		this.mode = mode;

		initialize();
		createMenuBar();
		if (mode == 1) {
			jl_ReasonfrRewind.setVisible(false);
			jdf_ReasonfrRewind.setVisible(false);
		}

	}

	private void initialize() {
		this.setLayout(null);
		this.setContentPane(getJContentPane());
		this.setSize(950, 600);
		new Design("Order_Planning").setColors(this);
	}

	public JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);

			jl_DisposalDcNo = new JLabel();
			jl_DisposalDcNo.setBounds(new Rectangle(40, 30, 100, 100));
			jl_DisposalDcNo.setText("Disposal Dc No");
			jl_DisposalDate = new JLabel();
			jl_DisposalDate.setBounds(new Rectangle(40, 70, 100, 100));
			jl_DisposalDate.setText("Disposal Date");
			jl_Disposalcategory = new JLabel();
			jl_Disposalcategory.setBounds(new Rectangle(40, 110, 100, 100));
			jl_Disposalcategory.setText("<html>Disposal Category<font color=red>*</font></html>");
			jl_DisposalRemarks = new JLabel();
			jl_DisposalRemarks.setBounds(new Rectangle(40, 150, 100, 100));
			jl_DisposalRemarks.setText("Reason for Disposal");
			jl_DisposalType = new JLabel();
			jl_DisposalType.setBounds(new Rectangle(300, 30, 100, 100));
			jl_DisposalType.setText("<html>Disposal Type<font color=red>*</font></html>");
			jl_Location = new JLabel();
			jl_Location.setBounds(new Rectangle(300, 70, 100, 100));
			jl_Location.setText("<html>Location<font color=red>*</font></html>");
			jl_ReasonfrRewind = new JLabel();
			jl_ReasonfrRewind.setBounds(new Rectangle(40, 450, 100, 100));
			jl_ReasonfrRewind.setText("Reason for Rewind");
			jl_Invoice = new JLabel();
			jl_Invoice.setBounds(new Rectangle(170, 450, 100, 100));
			jl_Invoice.setText("Invoiceable");

			jContentPane.add(jl_DisposalDcNo, null);
			jContentPane.add(jl_DisposalDate, null);
			jContentPane.add(jl_Disposalcategory, null);
			jContentPane.add(jl_DisposalRemarks, null);
			jContentPane.add(jl_DisposalType, null);
			jContentPane.add(jl_Location, null);
			jContentPane.add(jl_ReasonfrRewind, null);

			jContentPane.add(getjc_DisposalDc(), null);
			jContentPane.add(getjdf_DisposalDate(), null);
			jContentPane.add(getjc_DisposalCategory(), null);
			jContentPane.add(getjc_jc_Location(), null);
			jContentPane.add(getjc_DisposalType(), null);
			jContentPane.add(getjb_frAuditvar(), null);
			jContentPane.add(getjb_AssetReg(), null);
			jContentPane.add(getjdf_Remarks(), null);
			jContentPane.add(getsst_DisposalAssetDetailsTable1(), null);
			jContentPane.add(get_jckb_SelectAll1(), null);
			jContentPane.add(getjb_Delete(), null);
			jContentPane.add(getjb_Dispose(), null);
			jContentPane.add(getjb_Cancel(), null);
			jContentPane.add(getjdf_ReasonfrRewind(), null);
			jContentPane.add(getjckb_Invoice(), null);

		}

		return jContentPane;

	}

	public void createMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();
		}
		if (jmi_save == null) {
			jmi_save = new JMenuItem("Save");
			jmi_save.addActionListener(this);
		}

		if (jmi_cancel == null) {
			jmi_cancel = new JMenuItem("Cancel");
			jmi_cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				}
			});
		}

		if (jm_action == null) {
			jm_action = new JMenu("Action");
			jm_action.add(jmi_save);
			jm_action.add(jmi_cancel);

		}
		jMenuBar.add(jm_action);
		innerPanel.setMenuBar(jMenuBar);
		innerPanel.repaint();

	}

	private JTextField getjc_DisposalDc() {
		if (jc_DisposalDc == null) {
			jc_DisposalDc = new JTextField();
			jc_DisposalDc.setBounds(new Rectangle(150, 30, 100, 30));
			jc_DisposalDc.setName("AssetDisposal_jc_DisposalDc");
			jc_DisposalDc.setEditable(false);
			jc_DisposalDc.addActionListener(this);

		}
		return jc_DisposalDc;
	}

	private TDateField getjdf_DisposalDate() {
		if (jdf_DisposalDate == null) {
			jdf_DisposalDate = new TDateField();
			jdf_DisposalDate.setBounds(new Rectangle(150, 70, 100, 30));
		}
		return jdf_DisposalDate;
	}

	private JComboBox getjc_DisposalCategory() {
		if (jc_DisposalCategory == null) {
			jc_DisposalCategory = new JComboBox();
			jc_DisposalCategory.setBounds(new Rectangle(150, 110, 90, 30));
			jc_DisposalCategory.setName("DisposalDisposal_jc_DisposalCategory");
			jc_DisposalCategory.setEditable(false);
			jc_DisposalCategory.addActionListener(this);
			Vector AC = new Vector();
			AC.add("");
			jc_DisposalCategory.removeAllItems();
			jc_DisposalCategory.addItem("");
			for (int i = 0; i < AC.size(); i++) {
				jc_DisposalCategory.addItem(AC.get(i));

			}
			jc_DisposalCategory.setEditable(false);
		}
		return jc_DisposalCategory;
	}

	private JTextArea getjdf_Remarks() {
		if (jdf_Remarks == null) {
			jdf_Remarks = new JTextArea();
			jdf_Remarks.setBounds(new Rectangle(150, 150, 200, 50));
			jdf_Remarks.setEditable(false);
		}
		return jdf_Remarks;
	}

	private JComboBox getjc_DisposalType() {
		if (jc_DisposalType == null) {
			jc_DisposalType = new JComboBox();
			jc_DisposalType.setBounds(new Rectangle(370, 30, 90, 30));
			jc_DisposalType.setName("Disposal_jc_DisposalType");
			jc_DisposalType.setEditable(false);
			jc_DisposalType.addActionListener(this);
			Vector AC = new Vector();
			jc_DisposalType.removeAllItems();
			jc_DisposalType.addItem("");
			for (int i = 0; i < AC.size(); i++) {
				jc_DisposalType.addItem(AC.get(i));

			}
			jc_DisposalType.setEditable(false);
		}
		return jc_DisposalType;
	}

	private JComboBox getjc_jc_Location() {
		if (jc_Location == null) {
			jc_Location = new JComboBox();
			jc_Location.setBounds(new Rectangle(370, 70, 90, 30));
			jc_Location.setName("DisposalDisposal_jc_Location");
			jc_Location.setEditable(false);
			jc_Location.addActionListener(this);
			Vector AC = new Vector();
			AC.add("");
			jc_Location.removeAllItems();
			jc_Location.addItem("");
			for (int i = 0; i < AC.size(); i++) {
				jc_Location.addItem(AC.get(i));

			}
			jc_Location.setEditable(false);
		}
		return jc_Location;
	}

	private TableModelListener acctListener = new TableModelListener() {
		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
			}
		}
	};

	private JButton getjb_frAuditvar() {
		if (jb_frAuditvar == null) {
			jb_frAuditvar = new JButton();
			jb_frAuditvar.setBounds(400, 220, 130, 80);
			jb_frAuditvar.setText("From Audit Variance");
			jb_frAuditvar.addActionListener(this);
		}
		return jb_frAuditvar;
	}

	private JButton getjb_AssetReg() {
		if (jb_AssetReg == null) {
			jb_AssetReg = new JButton();
			jb_AssetReg.setBounds(580, 220, 130, 80);
			jb_AssetReg.setText("Add Asset From Register");
			jb_AssetReg.addActionListener(this);
		}
		return jb_AssetReg;
	}

	private SortSelectTable getsst_DisposalAssetDetailsTable1() {
		if (sst_DisposalAssetDetailsTable1 == null) {
			sst_DisposalAssetDetailsTable1 = new SortSelectTable();
			sst_DisposalAssetDetailsTable1.setBounds(new Rectangle(30, 250, 950, 150));
			sst_DisposalAssetDetailsTable1.setName("DisposalScheduless_sst_DisposalAssetDetailsTable1");
			fillDisposalAssetDetails1();
		}
		return sst_DisposalAssetDetailsTable1;
	}

	public void fillDisposalAssetDetails1() {
		try {
			Vector data = new Vector();
			data.add("Sched ID");
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
			data.add("Purchase Value");
			data.add("Currency");
			data.add("Depreciated Value");
			data.add("Book Value");
			data.add("Depreciated %");
			data.add("Purchase date");
			data.add("Asset Life");
			data.add("Completed Age of Asset");
			data.add("Asset Status");
			data.add("Select");

			Vector editVect = new Vector();
			editVect.add(0, data.indexOf("Select") + "");
//			sst_DisposalAssetDetailsTable1.setTable(null, data, "Order_Planning", "Asset Classification", true);
			sst_DisposalAssetDetailsTable1.setTable(null, data, "Order_Planning", "Disposal Asset Details", true,
					new Vector(), new Vector(), new Vector(), null, null, editVect, new Vector());
			LstItem = sst_DisposalAssetDetailsTable1.table.getSelectionModel();
			sst_DisposalAssetDetailsTable1.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			LstItem.addListSelectionListener(this);
			sst_DisposalAssetDetailsTable1.table.getModel().addTableModelListener(acctListener);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JCheckBox get_jckb_SelectAll1() {
		if (jckb_SelectAll1 == null) {
			jckb_SelectAll1 = new JCheckBox();
			jckb_SelectAll1.setText("Select All");
			jckb_SelectAll1.setBounds(900, 230, 80, 30);
			jckb_SelectAll1.addActionListener(this);
		}
		return jckb_SelectAll1;
	}
	
	private JButton getjb_Delete() {
		if (jb_Delete == null) {
			jb_Delete = new JButton();
			jb_Delete.setBounds(750, 220, 80, 30);
			jb_Delete.setText("Delete");
			jb_Delete.addActionListener(this);
		}
		return jb_Delete;
	}

	private JButton getjb_Dispose() {
		if (jb_Dispose == null) {
			jb_Dispose = new JButton();
			jb_Dispose.setBounds(350, 550, 100, 50);
			jb_Dispose.setText("Dispose");
			jb_Dispose.addActionListener(this);
		}
		return jb_Dispose;
	}

	private JButton getjb_Cancel() {
		if (jb_Cancel == null) {
			jb_Cancel = new JButton();
			jb_Cancel.setBounds(550, 550, 100, 50);
			jb_Cancel.setText("Cancel");
			jb_Cancel.addActionListener(this);
		}
		return jb_Cancel;
	}

	private JTextArea getjdf_ReasonfrRewind() {
		if (jdf_ReasonfrRewind == null) {
			jdf_ReasonfrRewind = new JTextArea();
			jdf_ReasonfrRewind.setBounds(new Rectangle(140, 450, 200, 30));
			jdf_ReasonfrRewind.setEditable(false);
		}
		return jdf_ReasonfrRewind;
	}

	private JCheckBox getjckb_Invoice() {
		if (jckb_Invoice == null) {
			jckb_Invoice = new JCheckBox();
			jckb_Invoice.setText("Invoiceable");
			jckb_Invoice.setBounds(400, 150, 80, 30);
			jckb_Invoice.addActionListener(this);
		}
		return jckb_Invoice;
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
		if (e.getSource() == jb_frAuditvar) {
			AssetDisposalVariancePopup variancePopup = new AssetDisposalVariancePopup(THIS, tHIS, innerPanel, finter, parent, mode);
			variancePopup.setName("Audit Variance PopUp");
			variancePopup.show();

		}
		
		
		if (e.getSource() == jb_AssetReg) {
			AssetDisposalPopUp disposalPopup = new AssetDisposalPopUp(THIS, tHIS, innerPanel, finter, parent, mode);
			disposalPopup.setName("Asset Disposal PopUp");
			disposalPopup.show();

		}
		
		
		
		
	}
}
