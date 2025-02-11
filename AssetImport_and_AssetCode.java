/**
 * 
 * @author Veera Karthikeyan A
 * Added for requirement#- Fixed Asset	---JAN-2024
 *
 **/
package HarnessErp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import HarnessErp.Add_MaterialDetail.MyTableCellRenderer;
import HarnessErp.Asset_register_all_popups.MouseClick;
import common.Asset_Register_AST_DETAILS;
import common.Asset_register_Fields_and_Flexis;
import common.Asset_register_Image;
import common.Design;
import common.GUIDiffInterface;
import common.GUIDifference;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;
import common.gui.UFrame;

public class AssetImport_and_AssetCode  extends 
JInternalFrame implements InternalFrameListener, GUIDiffInterface, sstFilterEvent {

	OuterFrame outerFrm								= null;
	InnerPanel innerPanel 							= null;
	FinanceController fincon						= null;
	FinanceInterface fInter 						= null;
	AssetRegister_main parent						= null;
	AssetImport_and_AssetCode THIS					= null;
	Asset_register_all_popups all_pops				= null;
	
	Asset_Register_AST_DETAILS[] Asset_register_pojo		  		= null;
	Asset_register_Fields_and_Flexis[] Asset_reg_fieldflexi_pojo	= null;
	Asset_register_Image[] Asset_register_Image_pojo				= null;
	
	
	private JPanel jContentPane 								= null;
	
	private JMenu jm_action										= null;
	private JMenu jm_doc_view									= null;
	
	private JMenuBar jMenuBar 									= null;
	
	private JMenuItem jmi_Save									= null;
	private JMenuItem jmi_Submit								= null;
	private JMenuItem jmi_cancel 								= null;
	private JMenuItem jmi_view_docmt							= null;
	
	private JLabel jl_Stock_room								= null;
	private JLabel jl_MAT_No									= null;
	private JLabel jl_Classification							= null;
	private JLabel jl_Specification								= null;
	private JLabel jl_Depreciation_Amotization_Method			= null;
	private JLabel jl_Acquisition_Date							= null;
	private JLabel jl_Life_time_of_asset						= null;
	private JLabel jl_Department								= null;
	private JLabel jl_Sub_Department							= null;
	private JLabel jl_Service_Department						= null;
	
	
	private JLabel jl_Asset_Category							= null;
	private JLabel jl_Asset_Type								= null;
	private JLabel jl_Spec_no									= null;
	private JLabel jl_Depreciation_Amotization_prt				= null;
	private JLabel jl_Purchase_value_purcur						= null;
	private JLabel jl_Purchase_value_regcur						= null;
	private JLabel jl_Salvage_value								= null;
	private JLabel jl_Ref_No									= null;
	
	public JTextField jtfStock_room								= null;
	public JTextField jtfMAT_No									= null;
	private JTextField jtfClassification						= null;
	private JTextField jtfSpecification							= null;
	private JTextField jtfDepreciation_amotization_method		= null;
	private TDateField jdf_Acquisition_Date						= null;
	private JTextField jtfLife_time_of_asset					= null;
	JComboBox JCB_lifetime_of_asset								= null;
	JComboBox JCB_Department									= null;
	JComboBox JCB_sub_Department								= null;
	JComboBox JCB_Service_Department							= null;
	
	private JTextField jtfAsset_Category						= null;
	private JTextField jtfAsset_Type							= null;
	private JTextField jtfSpec_no								= null;
	private JTextField jtfDepreciation_amotization_ptr			= null;
	private JTextField jtfPurchase_value_purcur					= null;
	private JTextField jtfPurchase_value_regcur					= null;
	private JTextField jtfSalvage_value							= null;
	private JTextField jtfRef_no								= null;
	
	public JButton jbStock_room									= null;
	public JButton jbSelect_stock								= null;
	public JButton JbSave_asset									= null;
	public JButton JbImport_asset								= null;//submit button
	public JButton jbCancel										= null;
	public JButton jbApply_for_selected							= null;
	
	public JScrollPane jsp_AstFLX_fielfSCrpan					= null;
	public JTable Astflx_field_jtable							= null;	
	
/*from add ASSET & add CWIP popup's*/	
	HashMap<Integer,Vector> HM_popup									= new HashMap<Integer,Vector>();
	String Stock_room 													= "";
	Vector popup_data_ 													= new Vector();  /* in here we take and keep the popup selected and 
															     		others entity to load if it takes another open */
	Vector Prepared_data												= new Vector();
	
/*for set image in the pop-up*/		
	AssetReg_MultiImage_Popup multiimage_popup							= null;
	public JButton jbBrowse_image										= null;
	LinkedHashMap<Integer,String> HM_image_path_ig   					= new LinkedHashMap<Integer,String>();
	LinkedHashMap<Integer,String> hm_image_title_ig						= new LinkedHashMap<Integer,String>();	
	LinkedHashMap<Integer,String> hm_imagewith_formate						= new LinkedHashMap<Integer,String>();	
	
	
	JPanel iconPanel 													= null;
	JLabel jlIcon 														= null;
	ImageIcon icon 														= null;
	File   file;
	String imagefile 													= "";
	String filename 													= "";
	String filetype 													= "";
	int    mode															= 0;
	
/*for data preparation for save */		
	Vector grn_related_data_from_popup									= new Vector();

/*For Flexi fields */	
	Vector 	    flexi_Vector											= new Vector();
	Vector 	    Asset_Field_Vector										= new Vector();
	JComboBox JCB_flexi_combobox										= null;
	JComboBox JCB_Ast_combobox											= null;
	
	JComponent 	flexiFields_component[] 			= null;
/*to set table */
	public SortSelectTable sst_Asset_details_table						= null;
	MyTableCellRenderer renderer 										= new MyTableCellRenderer();
	
/*Approval Transaction*/	
	Vector transaction_object						= new Vector();
	
	
	public AssetImport_and_AssetCode(OuterFrame outerFrm,InnerPanel innerPanel,FinanceController fincon,FinanceInterface fInter,AssetRegister_main parent,int mode) {
		super();
		this.parent		= parent;
		this.fincon		= fincon;
		this.fInter		= fInter;
		this.outerFrm	= outerFrm;
		this.THIS		= this;
		this.innerPanel	= innerPanel;
		this.setTitle("Import Asset");
		initialize();
		createMenuBar();
		
	}
	
	private void initialize() {
		this.setLayout(null);
		this.setContentPane(getJContentPane());
		this.setSize(950, 600);
		new Design("Order_Planning").setColors(this);
		addInternalFrameListener(this);

	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			jContentPane.add(getjl_Stock_room(), null);
			jContentPane.add(getjtfStock_room(), null);
			
			jContentPane.add(getjl_MAT_No(), null);
			jContentPane.add(getjtfMAT_No(), null);
			
			jContentPane.add(getjl_Classification(), null);
			jContentPane.add(getjtfClassification(), null);
			
			jContentPane.add(getjl_Specification(), null);
			jContentPane.add(getjtfSpecification(), null);
			
			jContentPane.add(getjl_Depreciation_Amotization_Method(), null);
			jContentPane.add(getjtfDepreciation_amotization_method(), null);
			
			jContentPane.add(getjl_Acquisition_Date(), null);
			jContentPane.add(getjdf_Acquisition_Date(), null);
			
			jContentPane.add(getjl_Life_time_of_asset(), null);
			jContentPane.add(getjtfLife_time_of_asset(), null);
			jContentPane.add(getJCB_lifetime_of_asset(), null);
			
			jContentPane.add(getjl_Department(), null);
			jContentPane.add(getJCB_Department(), null);
			
			jContentPane.add(getjl_Sub_Department(), null);
			jContentPane.add(getJCB_sub_Department(), null);
			
			jContentPane.add(getjl_Service_Department(), null);
			jContentPane.add(getJCB_Service_Department(), null);
			
			
//set2---			
			jContentPane.add(getjl_Asset_Category(), null);
			jContentPane.add(getjtfAsset_Category(), null);
			
			jContentPane.add(getjl_Asset_Type(), null);
			jContentPane.add(getjtfAsset_Type(), null);
			
			jContentPane.add(getjl_Spec_no(), null);
			jContentPane.add(getjtfSpec_no(), null);
			
			
			jContentPane.add(getjl_Depreciation_Amotization_prt(), null);
			jContentPane.add(getjtfDepreciation_amotization_ptr(), null);
			
			jContentPane.add(getjl_Purchase_value_purcur(), null);
			jContentPane.add(getjtfPurchase_value_purcur(), null);
			
			jContentPane.add(getjl_Purchase_value_regcur(), null);
			jContentPane.add(getjtfPurchase_value_regcur(), null);
			
			jContentPane.add(getjl_Salvage_value(), null);
			jContentPane.add(getjtfSalvage_value(), null);
			
			jContentPane.add(getjl_Ref_No(), null);
			jContentPane.add(getjtfRef_no(), null);

		/*set image*/
			jContentPane.add(getJlIcon(), null);
			jContentPane.add(getIconPanel(), null);
			jContentPane.add(getjbBrowse_image(), null);
			
		/*set image*/	
			
			jContentPane.add(getjsp_Ast_flexifield_SCrpan(), null);
			
			jContentPane.add(getsst_Asset_details_table(), null);
			
			jContentPane.add(getJbStock_room(), null);
			jContentPane.add(getJbSelect_stock(), null);
			jContentPane.add(getjbApply_for_selected(), null);	
			jContentPane.add(getJbSave_asset(), null);
			jContentPane.add(getJbImport_asset(), null);
			jContentPane.add(getjbCancel(), null);
			
		}
		return jContentPane;

	}
	
/************************************
 * screen attributes -STARTS
 ************************************/
	
	private JLabel getjl_Stock_room() {
		if (jl_Stock_room == null) {
			jl_Stock_room = new JLabel();
			jl_Stock_room.setText("Stock Room");
			jl_Stock_room.setName("Asset_jl_Stock_room");
			jl_Stock_room.setBounds(new Rectangle(20, 25, 100, 30));

		}
		return jl_Stock_room;
	}
	
	private JLabel getjl_MAT_No() {
		if (jl_MAT_No == null) {
			jl_MAT_No = new JLabel();
			jl_MAT_No.setText("MAT No");
			jl_MAT_No.setName("Asset_jl_MAT_No");
			jl_MAT_No.setBounds(new Rectangle(20, 70, 100, 30));

		}
		return jl_MAT_No;
	}
	
	private JLabel getjl_Classification() {
		if (jl_Classification == null) {
			jl_Classification = new JLabel();
			jl_Classification.setText("Classification");
			jl_Classification.setName("Asset_jl_Classification");
			jl_Classification.setBounds(new Rectangle(20, 100, 100, 30));

		}
		return jl_Classification;
	}
	
	private JLabel getjl_Specification() {
		if (jl_Specification == null) {
			jl_Specification = new JLabel();
			jl_Specification.setText("Specification");
			jl_Specification.setName("Asset_jl_Specification");
			jl_Specification.setBounds(new Rectangle(20, 130, 100, 30));

		}
		return jl_Specification;
	}
	
	private JLabel getjl_Depreciation_Amotization_Method() {
		if (jl_Depreciation_Amotization_Method == null) {
			jl_Depreciation_Amotization_Method = new JLabel();
			jl_Depreciation_Amotization_Method.setText("Depreciation Method");
			jl_Depreciation_Amotization_Method.setName("jl_Depreciation_Amotization_Method");
			jl_Depreciation_Amotization_Method.setBounds(new Rectangle(20, 160, 100, 30));

		}
		return jl_Depreciation_Amotization_Method;
	}	

	private JLabel getjl_Acquisition_Date() {
		if (jl_Acquisition_Date == null) {
			jl_Acquisition_Date = new JLabel();
			jl_Acquisition_Date.setText("Acquisition Date");
			jl_Acquisition_Date.setName("Asset_jl_Acquisition_Date");
			jl_Acquisition_Date.setBounds(new Rectangle(20, 190, 100, 30));
	
		}
		return jl_Acquisition_Date;
	}
	
	private JLabel getjl_Life_time_of_asset() {
		if (jl_Life_time_of_asset == null) {
			jl_Life_time_of_asset = new JLabel();
			jl_Life_time_of_asset.setText("<html>Life Time of Asset<font color=red> * </font> </html>");
			jl_Life_time_of_asset.setName("Asset_jl_Life_time_of_asset");
			jl_Life_time_of_asset.setBounds(new Rectangle(20, 220, 100, 30));
	
		}
		return jl_Life_time_of_asset;
	}
	
	private JLabel getjl_Department() {
		if (jl_Department == null) {
			jl_Department = new JLabel();
			jl_Department.setText("<html>Department<font color=red> * </font> </html>");
			jl_Department.setName("Asset_jl_Department");
			jl_Department.setBounds(new Rectangle(20, 250, 100, 30));
	
		}
		return jl_Department;
	}

	private JLabel getjl_Sub_Department() {
		if (jl_Sub_Department == null) {
			jl_Sub_Department = new JLabel();
			jl_Sub_Department.setText("Sub Department");
			jl_Sub_Department.setName("Asset_jl_Sub_Department");
			jl_Sub_Department.setBounds(new Rectangle(20,280, 100, 30));
	
		}
		return jl_Sub_Department;
	}
	
	private JLabel getjl_Service_Department() {
		if (jl_Service_Department == null) {
			jl_Service_Department = new JLabel();
			jl_Service_Department.setText("Service Department");
			jl_Service_Department.setName("Asset_jl_Service_Department");
			jl_Service_Department.setBounds(new Rectangle(20, 310, 100, 30));
	
		}
		return jl_Service_Department;
	}	
	
//	----set2
	
	private JLabel getjl_Asset_Category() {
		if (jl_Asset_Category == null) {
			jl_Asset_Category = new JLabel();
			jl_Asset_Category.setText("Asset Category");
			jl_Asset_Category.setName("Asset_jl_Asset_Category");
			jl_Asset_Category.setBounds(new Rectangle(290, 70, 100, 30));

		}
		return jl_Asset_Category;
	}
	
	private JLabel getjl_Asset_Type() {
		if (jl_Asset_Type == null) {
			jl_Asset_Type = new JLabel();
			jl_Asset_Type.setText("Asset Type");
			jl_Asset_Type.setName("Asset_jl_Asset_Type");
			jl_Asset_Type.setBounds(new Rectangle(290, 100, 100, 30));

		}
		return jl_Asset_Type;
	}
	
	private JLabel getjl_Spec_no() {
		if (jl_Spec_no == null) {
			jl_Spec_no = new JLabel();
			jl_Spec_no.setText("Spec No");
			jl_Spec_no.setName("Asset_jl_Spec_no");
			jl_Spec_no.setBounds(new Rectangle(290, 130, 100, 30));

		}
		return jl_Spec_no;
	}
	
	private JLabel getjl_Depreciation_Amotization_prt() {
		if (jl_Depreciation_Amotization_prt == null) {
			jl_Depreciation_Amotization_prt = new JLabel();
			jl_Depreciation_Amotization_prt.setText("Depreciation %");
			jl_Depreciation_Amotization_prt.setName("Asset_jl_Depreciation_Amotization_prt");
			jl_Depreciation_Amotization_prt.setBounds(new Rectangle(290, 160, 100, 30));

		}
		return jl_Depreciation_Amotization_prt;
	}
	
	private JLabel getjl_Purchase_value_purcur() {
		if (jl_Purchase_value_purcur == null) {
			jl_Purchase_value_purcur = new JLabel();
			jl_Purchase_value_purcur.setText("Purchase Value");
			jl_Purchase_value_purcur.setName("Asset_jl_Purchase_value_purcur");
			jl_Purchase_value_purcur.setBounds(new Rectangle(290, 190, 100, 30));

		}
		return jl_Purchase_value_purcur;
	}	

	private JLabel getjl_Purchase_value_regcur() {
		if (jl_Purchase_value_regcur == null) {
			jl_Purchase_value_regcur = new JLabel();
			jl_Purchase_value_regcur.setText("Purchase Value");
			jl_Purchase_value_regcur.setName("Asset_jl_Purchase_value_regcur");
			jl_Purchase_value_regcur.setBounds(new Rectangle(290, 220, 100, 30));
	
		}
		return jl_Purchase_value_regcur;
	}
	
	private JLabel getjl_Salvage_value() {
		if (jl_Salvage_value == null) {
			jl_Salvage_value = new JLabel();
			jl_Salvage_value.setText("<html>Salvage value<font color=red> * </font> </html>");
			jl_Salvage_value.setName("Asset_jl_Salvage_value");
			jl_Salvage_value.setBounds(new Rectangle(290, 250, 100, 30));
	
		}
		return jl_Salvage_value;
	}
	
	private JLabel getjl_Ref_No() {
		if (jl_Ref_No == null) {
			jl_Ref_No = new JLabel();
			jl_Ref_No.setText("Ref No");
			jl_Ref_No.setName("Asset_jl_Ref_No");
			jl_Ref_No.setBounds(new Rectangle(290, 280, 100, 30));
	
		}
		return jl_Ref_No;
	}
	//set2 end
	
	
		
	/*tft filed*/
	private JTextField getjtfStock_room() {

			if (jtfStock_room == null) {
				jtfStock_room = new JTextField();
				jtfStock_room.setName("Asset_jtfStock_room");
				jtfStock_room.setBounds(115, 25, 100, 80);
				jtfStock_room.setEditable(false);
			}
			return jtfStock_room;
		}	
	
	 private JTextField getjtfMAT_No() {

			if (jtfMAT_No == null) {
				jtfMAT_No = new JTextField();
				jtfMAT_No.setName("Asset_jtfMAT_No");
				jtfMAT_No.setBounds(115, 70, 100, 80);
				jtfMAT_No.setEditable(false);

			}
			return jtfMAT_No;
		}
	
	 
	 private JTextField getjtfClassification() {

			if (jtfClassification == null) {
				jtfClassification = new JTextField();
				jtfClassification.setName("Asset_jtfClassification");
				jtfClassification.setBounds(115, 100, 100, 80);
				jtfClassification.setEditable(false);
			}
			return jtfClassification;
		}	
	
	 private JTextField getjtfSpecification() {

			if (jtfSpecification == null) {
				jtfSpecification = new JTextField();
				jtfSpecification.setName("Asset_jtfSpecification");
				jtfSpecification.setBounds(115, 130, 100, 80);
				jtfSpecification.setEditable(false);
			}
			return jtfSpecification;
		}
	 
	private JTextField getjtfDepreciation_amotization_method() {

			if (jtfDepreciation_amotization_method == null) {
				jtfDepreciation_amotization_method = new JTextField();
				jtfDepreciation_amotization_method.setName("Asset_jtfDepreciation_amotization_method");
				jtfDepreciation_amotization_method.setBounds(115, 160, 100, 80);
				jtfDepreciation_amotization_method.setEditable(false);
			}
			return jtfDepreciation_amotization_method;
		}	
	
	 private TDateField getjdf_Acquisition_Date() {
			if (jdf_Acquisition_Date == null) {
				Calendar cal = GregorianCalendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, -30);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	
				jdf_Acquisition_Date = new TDateField();
				jdf_Acquisition_Date.setBounds(new Rectangle(115, 190, 100, 30));
				jdf_Acquisition_Date.setText(sdf.format(cal.getTime()));
				jdf_Acquisition_Date.setVisible(true);
			}
			return jdf_Acquisition_Date;
		}
	
	 private JTextField getjtfLife_time_of_asset() {
	
			if (jtfLife_time_of_asset == null) {
				jtfLife_time_of_asset = new JTextField();
				jtfLife_time_of_asset.setName("Asset_jtfLife_time_of_asset");
				jtfLife_time_of_asset.setBounds(115, 220, 70, 80);
				(jtfLife_time_of_asset).addKeyListener(new KeyListener() {
					
					public void keyTyped(KeyEvent e) {
						char ch 	= e.getKeyChar();
							if (!((ch >= '0' && ch <= '9') || ch == '.' || ch == '-' || ch == '\b')){
								outerFrm.showMsg("This is Numeric field and it will accept only numbers");
								e.consume();
							}else{
								outerFrm.showMsg("");
							}
					}
	
					public void keyReleased(KeyEvent e) {
						if(jtfLife_time_of_asset.getText().length() > 5 ) {
							outerFrm.showMsg("maximum 5 charater allowed ");
							jtfLife_time_of_asset.setText("");
							e.consume();
						}else {
							outerFrm.showMsg("");
						}
					}
	
					public void keyPressed(KeyEvent e) {
					}
				});
			}
			return jtfLife_time_of_asset;
		}
	
	 public JComboBox getJCB_lifetime_of_asset() {
			if (JCB_lifetime_of_asset == null) {
				try {
					Vector allUnitNames = new Vector();
					allUnitNames.add("Years");
					allUnitNames.add("Months");
					JCB_lifetime_of_asset = new JComboBox(allUnitNames);
					JCB_lifetime_of_asset.setBounds(185, 220, 70, 70);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return JCB_lifetime_of_asset;
		}
	 
	 public JComboBox getJCB_Department() {
			if (JCB_Department == null) {
				try {
					Vector alldept_Names = fInter.LoadVectorContents("select Dept_Name from Department order by Dept_Name");
					alldept_Names.add(0,"");
					JCB_Department = new JComboBox(alldept_Names);
					JCB_Department.setBounds(115, 250, 120, 70);
					JCB_Department.addActionListener(new java.awt.event.ActionListener() { 
						public void actionPerformed(java.awt.event.ActionEvent e) { 
							String department_name 	= JCB_Department.getSelectedItem().toString();
							
							if(JCB_Department.getSelectedIndex()>0 ){
							    try {
									Vector allsub_dept_Names 	= fInter.LoadVectorContents("Select Sub_Dept_Name From FA_SUB_DEPARTMENT "
																		+ " Where Status= 'Active' And Dept_Id In(Select Dept_Id from "
																		+ "Department where Dept_Name='"+department_name+"')");
			

									JCB_sub_Department.setModel(new DefaultComboBoxModel(allsub_dept_Names));	
			
			
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}					
						}
						});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return JCB_Department;
		}
	 
	 
		
	 public JComboBox getJCB_sub_Department() {
			if (JCB_sub_Department == null) {
				try {
					Vector allsub_dept_Names = fInter.LoadVectorContents("Select Sub_Dept_Name from FA_SUB_DEPARTMENT where Status='Active' order by Sub_Dept_Name");
					allsub_dept_Names.add(0,"");
					JCB_sub_Department = new JComboBox(allsub_dept_Names);
					JCB_sub_Department.setBounds(115, 280, 120, 70);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return JCB_sub_Department;
		}
		

	 public JComboBox getJCB_Service_Department() {
			if (JCB_Service_Department == null) {
				try {
					
					Vector allserv_dep_Names = fInter.LoadVectorContents("select Dept_Name from Department order by Dept_Name");
					allserv_dep_Names.add(0,"");
					JCB_Service_Department = new JComboBox(allserv_dep_Names);
					JCB_Service_Department.setBounds(115, 310, 120, 70);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return JCB_Service_Department;
		}
		
	 //set 2---------
	 private JTextField getjtfAsset_Category() {

			if (jtfAsset_Category == null) {
				jtfAsset_Category = new JTextField();
				jtfAsset_Category.setName("Asset_jtfAsset_Category");
				jtfAsset_Category.setBounds(410, 70, 100, 80);
				jtfAsset_Category.setEditable(false);

			}
			return jtfAsset_Category;
		}
	
	 
	 private JTextField getjtfAsset_Type() {

			if (jtfAsset_Type == null) {
				jtfAsset_Type = new JTextField();
				jtfAsset_Type.setName("Asset_jtfAsset_Type");
				jtfAsset_Type.setBounds(410, 100, 100, 80);
				jtfAsset_Type.setEditable(false);
			}
			return jtfAsset_Type;
		}	
	
	 private JTextField getjtfSpec_no() {

			if (jtfSpec_no == null) {
				jtfSpec_no = new JTextField();
				jtfSpec_no.setName("Asset_jtfSpec_no");
				jtfSpec_no.setBounds(410, 130, 100, 80);
				jtfAsset_Type.setEditable(false);
			}
			return jtfSpec_no;
		}
	 
	private JTextField getjtfDepreciation_amotization_ptr() {

			if (jtfDepreciation_amotization_ptr == null) {
				jtfDepreciation_amotization_ptr = new JTextField();
				jtfDepreciation_amotization_ptr.setName("Asset_jtfDepreciation_amotization_ptr");
				jtfDepreciation_amotization_ptr.setBounds(410, 160, 100, 80);
	
			}
			return jtfDepreciation_amotization_ptr;
		}	
	
	
	 private JTextField getjtfPurchase_value_purcur() {

			if (jtfPurchase_value_purcur == null) {
				jtfPurchase_value_purcur = new JTextField();
				jtfPurchase_value_purcur.setName("Asset_jtfPurchase_value_purcur");
				jtfPurchase_value_purcur.setBounds(410, 190, 100, 80);

			}
			return jtfPurchase_value_purcur;
		}
	
	 
	 private JTextField getjtfPurchase_value_regcur() {

			if (jtfPurchase_value_regcur == null) {
				jtfPurchase_value_regcur = new JTextField();
				jtfPurchase_value_regcur.setName("Asset_jtfPurchase_value_regcur");
				jtfPurchase_value_regcur.setBounds(410, 220, 100, 80);
	
			}
			return jtfPurchase_value_regcur;
		}	
	
	 private JTextField getjtfSalvage_value() {

			if (jtfSalvage_value == null) {
				jtfSalvage_value = new JTextField();
				jtfSalvage_value.setName("Asset_jtfSalvage_value");
				jtfSalvage_value.setBounds(410, 250, 100, 80);

			}
			return jtfSalvage_value;
		}
	 
	private JTextField getjtfRef_no() {

			if (jtfRef_no == null) {
				jtfRef_no = new JTextField();
				jtfRef_no.setName("Asset_jtfRef_no");
				jtfRef_no.setBounds(410, 280, 100, 80);
	
			}
			return jtfRef_no;
		}
	 //set 2---------
	

 /*****
  * SET-image
  * ******/
	
	/*image set*/
	 
	 private JLabel getJlIcon(){
			if(jlIcon == null){
				jlIcon = new JLabel();
				jlIcon.setBounds(675,50,50,30);
				jlIcon.setText("Image File");
				this.add(jlIcon, null);

			}
			return jlIcon;
		}
	 
	 private JPanel getIconPanel(){
			if(iconPanel == null){
				iconPanel = new JPanel();
				iconPanel.setBounds(650, 80, 100,100);
				iconPanel.setOpaque(true); 
				iconPanel.setBackground(Color.WHITE);
				this.add(iconPanel, null);
				iconPanel.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						if(e.getClickCount() == 2 && icon != null){

						}
					}
				});
			}
			return iconPanel;
		}

		public void setimage_inpanel(){
		try {
			file 							= new File(HM_image_path_ig.get(1));
			String fname					= file.getName();
			String filenamesa 				= fname.substring(0, fname.indexOf("."));
			String filetype 				= fname.substring(fname.indexOf(".") + 1, fname.length());
			if (filetype.equals("jpg") || filetype.equals("jpeg") || filetype.equals("gif") || filetype.equals("png")) {
				
				String 	imagefile 			= file.getAbsolutePath();
				BufferedImage set_imgage 	= null;
				set_imgage 					= ImageIO.read(file);
				setImageInPanel(set_imgage);
			}
			
			 }catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
		}
		
		
		
		 public void setImageInPanel( Image images) {
		 		
		 		iconPanel.removeAll();
				Image 		d_images 		= images.getScaledInstance(100,100,Image.SCALE_SMOOTH);
		 		ImageIcon 	sel_image_icon	= new ImageIcon(d_images);
		 		JLabel 		imageLabel 		= new JLabel(sel_image_icon);		
		 		iconPanel.add(imageLabel);	
		 		iconPanel.revalidate();
		 		iconPanel.repaint();
		 				
			}

	 /*****
	  * SET-image
	  * ******/
	 
	 
	 
	 private JScrollPane getjsp_Ast_flexifield_SCrpan() {
			if (jsp_AstFLX_fielfSCrpan == null) {
				jsp_AstFLX_fielfSCrpan = new JScrollPane();
				jsp_AstFLX_fielfSCrpan.setViewportView(getJt_Document_Details());
				jsp_AstFLX_fielfSCrpan.setBounds(new Rectangle(370, 330, 560, 50));
				jsp_AstFLX_fielfSCrpan.setName("doc_view_JSPscrpan");
				jsp_AstFLX_fielfSCrpan.setEnabled(false);
			}	
			return jsp_AstFLX_fielfSCrpan;
		}
			
		private JTable getJt_Document_Details() {
			if(Astflx_field_jtable == null) {
				Astflx_field_jtable = new JTable();
				Astflx_field_jtable.setName("MakePayment_jt_bankDetails");
				if(!jtfMAT_No.getText().toString().isEmpty()) {
					loadTable();
				}
				
			}
			return Astflx_field_jtable;
		}
		


		
/************************************
 * screen attributes -ENDS
 ************************************/	
	 
/************************************************************************************************************** 
 
 /************************************
  * BUTTON ACTION -STARTS
  ************************************/	 
	 private JButton getJbStock_room() {
			if (jbStock_room == null) {
				jbStock_room = new JButton("Stock Room");
				jbStock_room.setBounds(250, 25, 95, 30);
				jbStock_room.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						
						all_pops			= new Asset_register_all_popups(outerFrm,innerPanel,fincon,fInter,THIS,"Select Stock Room");
						all_pops.setName("ast_Stock_room_popup");
						all_pops.setTitle("Select Stock room");
						all_pops.setVisible(true);
						
					}
				});
			}
			return jbStock_room;
		}
	 
	 private JButton getJbSelect_stock() {
			if (jbSelect_stock == null) {
				jbSelect_stock = new JButton("Select Stock");
				jbSelect_stock.setBounds(650, 25, 95, 30);
				jbSelect_stock.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if (!jtfStock_room.getText().toString().isEmpty() ) {
							
							Vector Asset_data	= get_value_validation_to_load_popup();
							if(  Asset_data.size() > 0 ) {
								
								outerFrm.showMsg(" ");
								all_pops			= new Asset_register_all_popups(outerFrm,innerPanel,fincon,fInter,THIS,"SELECT STOCK POPUP");
								all_pops.setName("ast_select_stock_popup");
								all_pops.setTitle("Add Stock Popup");
								all_pops.setVisible(true);	
							}else {
								outerFrm.showMsg(" there is no data to load for "+jtfStock_room.getText().toString()+" Stock Room");
								return;
							}
													
						}else {
							outerFrm.showMsg("kindly select the stock room ");
							return;
						}

					}
				});
			}
			return jbSelect_stock;
		}
	 
	 
	 private JButton getjbApply_for_selected() {
			if (jbApply_for_selected == null) {
				jbApply_for_selected = new JButton("Apply for selected");
				jbApply_for_selected.setBounds(780, 380, 120, 30);
				jbApply_for_selected.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if (sst_Asset_details_table.table.getCellEditor() != null) {
							sst_Asset_details_table.table.getCellEditor().stopCellEditing();
							
						}
						if (Astflx_field_jtable.getCellEditor() != null) {
							Astflx_field_jtable.getCellEditor().stopCellEditing();
							
						}
						
						
						int column_Jtablecount_is		= Astflx_field_jtable.getColumnCount();
						int select_index				= sst_Asset_details_table.table.getColumnModel().getColumnIndex("Select");
						Vector fill_fields_table_value	= new Vector();
						
						for(int i =0;i<column_Jtablecount_is;i++) {
							String entered_value		=	Astflx_field_jtable.getValueAt(0, i)== null ? "" : Astflx_field_jtable.getValueAt(0, i).toString().trim();
							fill_fields_table_value.add(entered_value);
						}
						Vector sst_tabledata			= sst_Asset_details_table.getDataVector();
						
						for(int s=0;s<sst_tabledata.size();s++) {
							Vector temp_sst_tabledata	= (Vector)sst_tabledata.get(s);
							
							if(!temp_sst_tabledata.contains(false)) {
							
								for(int f=0;f<fill_fields_table_value.size();f++){
									fill_fields_table_value.get(f).toString();
									temp_sst_tabledata.set(6+(f+1), fill_fields_table_value.get(f).toString());	/*in here i have taken the default column 
																						value of sort select table which is came as static ( 6th column index )  */
									
								}
	
							}
						}
						fill_sst_Asset_details_table(sst_tabledata);
					}
				});
			}
			return jbApply_for_selected;
		}
	 
	 private JButton getjbBrowse_image() {
			if (jbBrowse_image == null) {
				jbBrowse_image = new JButton("Browse Image");
				jbBrowse_image.setBounds(652, 190, 95, 30);
				this.add(jbBrowse_image, null);
				jbBrowse_image.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						multiimage_popup		= new AssetReg_MultiImage_Popup(outerFrm,innerPanel,fincon,fInter,THIS);
						multiimage_popup.setName("ast_doc_view_popup");
						multiimage_popup.setTitle("Images");
						multiimage_popup.setVisible(true);
					}
				});
			}
			return jbBrowse_image;
		}
	 
	 private JButton getJbSave_asset() {
			if (JbSave_asset == null) {
				JbSave_asset = new JButton("Save");
				JbSave_asset.setBounds(190, 675, 95, 30);
				JbSave_asset.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						
					}
				});
			}
			return JbSave_asset;
		}
	 
	 private JButton getJbImport_asset() {
			if (JbImport_asset == null) {
				JbImport_asset = new JButton("Import Asset");
				JbImport_asset.setBounds(350, 675, 95, 30);
				JbImport_asset.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						submit_action();
					}
				});
			}
			return JbImport_asset;
		}
	 
	 private JButton getjbCancel() {
			if (jbCancel == null) {
				jbCancel = new JButton("Cancel");
				jbCancel.setBounds(510, 675, 95, 30);
				jbCancel.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
							outerFrm.showMsg("");
							parent.fill_asset_register_Table();
							parent.createMenuBar();
							dispose();
					}
				});
			}
			return jbCancel;
		}
	 
 /************************************
  *  BUTTON ACTION -STARTS
  ************************************/	
	 
 /**************************************************************************************************************	 
	

/************************************
 * Methods and Function call -STARTS
 ************************************/	

	 public void createMenuBar() {
			if (jMenuBar == null) {
				jMenuBar = new JMenuBar();

			}
			if (jmi_Save == null) {
				jmi_Save = new JMenuItem("Save");
				jmi_Save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
					}
				});
			}
			if (jmi_Submit == null) {
				jmi_Submit = new JMenuItem("Submit");
				jmi_Submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
			}
			if (jmi_cancel == null) {
				jmi_cancel = new JMenuItem("Cancel");
				jmi_cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancel_action_dispose();
					}
				});
			}

			if (jmi_view_docmt == null) {
				jmi_view_docmt = new JMenuItem("View document");
			}
			
			if (jm_action == null) {
				
				jm_action 	= new JMenu("Action");
				jm_doc_view	= new JMenu("Document");
				
				jm_action.add(jmi_Save);
				jm_action.add(jmi_Submit);
				jm_action.add(jmi_cancel);
				
				jm_doc_view.add(jmi_view_docmt);		
		
			}
			
			jMenuBar.add(jm_action);
			jMenuBar.add(jm_doc_view);
			
			parent.parent.innerPanel.setMenuBar(jMenuBar);
			parent.parent.innerPanel.repaint();
	 }
	 
	 public void cancel_action_dispose() {
		outerFrm.showMsg("");
		parent.fill_asset_register_Table();
		parent.createMenuBar();
		dispose();
	
	 }
	

	
	
	public void value_set_from_assetpopup(Asset_Register_AST_DETAILS[] Asset_register_pojo) {
		try{
			Vector grn_data					= new Vector();
			for(int i =0;i<Asset_register_pojo.length;i++) {
				
				jtfMAT_No.setText( Asset_register_pojo[i].MAT_No.toString());				jtfMAT_No.setEditable(false);
				jtfSpecification.setText(Asset_register_pojo[i].Specification.toString());	jtfSpecification.setEditable(false);
				jtfSpec_no.setText(Asset_register_pojo[i].SPEC_no.toString());				jtfSpec_no.setEditable(false);
				jtfRef_no.setText(Asset_register_pojo[i].REF_no.toString());				jtfRef_no.setEditable(false);
				jtfPurchase_value_purcur.setText(Asset_register_pojo[i].Purchase_Value);	jtfPurchase_value_purcur.setEditable(false);
				jtfPurchase_value_regcur.setText(Asset_register_pojo[i].Purchase_Value);	jtfPurchase_value_regcur.setEditable(false);
				
				grn_data.add( Asset_register_pojo[i].GRN_no.toString());
				grn_data.add( Asset_register_pojo[i].Batch_id.toString());
				grn_data.add( Asset_register_pojo[i].Batch_no1.toString());
				grn_data.add( Asset_register_pojo[i].Batch_no2.toString());
				grn_data.add( Asset_register_pojo[i].Batch_no3.toString());
				grn_data.add( Asset_register_pojo[i].PO_no.toString());
			}
			
			Vector category_type				= fInter.LoadVectorContents("Select Asset_Category,\"type\",Classification_Name "
													+ " From  Fixed_Material_View Where Mat_No="+jtfMAT_No.getText()+"");
			
			jtfAsset_Category.setText(category_type.get(0)==null ? "" :category_type.get(0).toString());
			jtfAsset_Type .setText(category_type.get(1)==null ? "" :category_type.get(1).toString());
			jtfClassification.setText(category_type.get(2)==null ? "" :category_type.get(2).toString());
			
			
			String dep_amot_profile_name 	= "";
			
			if(	(jtfAsset_Category.getText().toString()).equalsIgnoreCase("Intangible")	) {
				dep_amot_profile_name		= "Amortization Profile";
			}else if( (jtfAsset_Category.getText().toString()).equalsIgnoreCase("Tangible")	) {
				dep_amot_profile_name		= "Depreciation Profile";
			}
			
			Vector  depreciation_type		= fInter.LoadVectorContents("SELECT DPR.DEDUCT_METHOD, DPR.DEDUCT_CYCLE, DP.DEDUCTION_PERCENTAGE, DPR.SCHEDULE_TYPE"
													+ " FROM FA_DEPRE_AMORT_AST_DETAILS DSD, FA_DEPRE_AMORT_PERIOD DP, FA_DEPRE_AMORT_PROFILE DPR WHERE "
													+ " DPR.DA_PROFILE_ID = DP.DA_PROFILE_ID AND DP.DA_PROFILE_ID = DSD.DA_PROFILE_ID AND "
													+ " DSD.ASSET_CLASS ='"+jtfClassification.getText().toString()+"' "
													+ "AND DPR.PROFILE_TYPE = '"+dep_amot_profile_name+"' ");

			if(depreciation_type.size()>0 && depreciation_type != null) {
				
				if(	(jtfAsset_Category.getText().toString()).equalsIgnoreCase("Intangible")	) {

					jl_Depreciation_Amotization_Method.setText("Amortization Method");
					jl_Depreciation_Amotization_prt.setText("Amortization %");
					jtfDepreciation_amotization_method.setText(depreciation_type.get(0)==null ? "" :depreciation_type.get(0).toString());jtfDepreciation_amotization_method.setEditable(false);
					jtfDepreciation_amotization_ptr.setText(depreciation_type.get(2)==null ? "" :depreciation_type.get(2).toString());jtfDepreciation_amotization_ptr.setEditable(false);

				}else if( (jtfAsset_Category.getText().toString()).equalsIgnoreCase("Tangible")	) {
				
					jl_Depreciation_Amotization_Method.setText("Depreciation Method");
					jl_Depreciation_Amotization_prt.setText("Depreciation %");
					
					jtfDepreciation_amotization_method.setText(depreciation_type.get(0)==null ? "" :depreciation_type.get(0).toString());jtfDepreciation_amotization_method.setEditable(false);
					jtfDepreciation_amotization_ptr.setText(depreciation_type.get(2)==null ? "" :depreciation_type.get(2).toString());jtfDepreciation_amotization_ptr.setEditable(false);
				

					}
			}
			
		

				grn_related_data_from_popup		= grn_data;

				loadTable();// to load the flexi and asset fields like a single row Table
			
				Vector load_table_date			=	set_valuesto_stock_details_table_frompop_up();
				fill_sst_Asset_details_table(load_table_date);
				
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	
	public Vector set_valuesto_stock_details_table_frompop_up() {
				
			Vector local_Prepared_data			= Prepared_data;
			Vector Asset_field					= get_asset_fields();
			Vector Flexi_field					= get_Asset_flexi_fields();
		
			Vector new_load_date				= new Vector();
			
			Vector tempAsset_fieldVector		= new Vector();
				for(int a=0;a<Asset_field.size();a++) {
					tempAsset_fieldVector.add("");
				}
				Vector tempFlexi_field	= new Vector();
				for(int a=0;a<Flexi_field.size();a++) {
					tempFlexi_field.add("");
				}
			
			for(int i=0;i<local_Prepared_data.size();i++) {
				
				Vector templocal_Prepared_data	= (Vector)local_Prepared_data.get(i);
				tempAsset_fieldVector.stream().forEach(templocal_Prepared_data::add);
				tempFlexi_field.stream().forEach(templocal_Prepared_data::add);
				templocal_Prepared_data.add(Boolean.valueOf(false));// for select
				
				new_load_date.add(templocal_Prepared_data);
				
			}
			
			return new_load_date;
		}
	
	public SortSelectTable getsst_Asset_details_table(){
		if(sst_Asset_details_table == null){
			sst_Asset_details_table = new SortSelectTable();
				sst_Asset_details_table.setBounds(new Rectangle(20, 410, 950, 250));
				sst_Asset_details_table.setName("Asset_Register_sst_asset_register");
				new Design("Order_Planning").setColors(sst_Asset_details_table);
				fill_sst_Asset_details_table(new Vector());
			
		}
		return sst_Asset_details_table;
	}
	
	
	public void fill_sst_Asset_details_table(Vector load_table_date) {
		try {
			
			
			Vector data 		 	= new Vector();
			Vector Heading	 		= new Vector();
			Vector Hide		 		= new Vector();
			Vector selectAllVec  	= new Vector();
			Vector Editable_vect	= new Vector();
			
			
			Heading.add("Mat No");
			Heading.add("PO No");
			Heading.add("GRN date");
			Heading.add("GRN No");
			Heading.add("Batch ID");
			Heading.add("Batch No1");
			Heading.add("Batch No2");
			
			
			Vector Asset_field			= get_asset_fields();
			Vector Flexi_field			= get_Asset_flexi_fields();

			for (int i =0;i< Asset_field.size();i++) {
				Vector temp				= new Vector();
				((Collection) Asset_field.get(i)).stream().forEach(temp::add);/*in this code i have used forEach() 
																				method so the collection of data 
																				would be added into the given vector temp*/
				int edit1= setBlueColor(( temp.get(0)).toString(),Heading);
				Editable_vect.add(edit1+"");
			}
			
			for (int i =0;i< Flexi_field.size();i++) {
				Vector temp				= new Vector();
				((Collection) Flexi_field.get(i)).stream().forEach(temp::add);/*in this code i have used forEach() 
																				method so the collection of data 
																				would be added into the given vector temp*/
				int edit2=setOrangeColor(( temp.get(0)).toString(),Heading);
				Editable_vect.add(edit2+"");
			}
			Heading.add("Select");
			Editable_vect.add( Heading.indexOf("Select") + "");
			selectAllVec.add("Select");
			Hide.add("Mat No");

			sst_Asset_details_table.setTable(load_table_date,Heading,"Order_Planning","Asset Details",
					false, Hide, new Vector(), new Vector(), 
					renderer, THIS, Editable_vect, selectAllVec);
		 
			
			sst_Asset_details_table.table.repaint();
			sst_Asset_details_table.repaint();
			sst_Asset_details_table.setVisible(true);
			sst_Asset_details_table.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			sst_Asset_details_table.table.getModel().addTableModelListener(Table_actions_for_select_stock);

			
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	
	TableModelListener Table_actions_for_select_stock  = new TableModelListener() {
		@Override

		public void tableChanged(TableModelEvent e) {
			if(e.getType() == TableModelEvent.UPDATE && e.getFirstRow()!=-1){
			outerFrm.showMsg("");		
			if(sst_Asset_details_table.table.getSelectedRow() != -1 && e.getFirstRow() != -1){

				Integer selRow 				= sst_Asset_details_table.table.getSelectedRow();
				Vector table_data			= sst_Asset_details_table.getDataVector();
				Vector selected_row 		= new Vector();
				String MAT_No 				= sst_Asset_details_table.table.getValueAt(selRow, sst_Asset_details_table.table.getColumnModel().getColumnIndex("Mat No")).toString();
				boolean removed				= false;
	
					}
	}
		}
	};
	
	public Vector get_value_validation_to_load_popup() {
		Vector Asset_data					= new Vector();
		try{
			String 	sql						= "SELECT Mat.Mat_No,Mat.\"Specs\", Mat.Spec_No,Mat.Color,Mat.Ref_No,Grn.Grn_No,Grn.Receipt_Date,"
												+ " Grn.Batch_No1,Grn.Batch_No2,Grn.Batch_No3,Grn.Qty1,Mat.Uom1 As Uom,Cpm.Rate As Purchaserate,"
												+ " Cpm.Currency As Purchase_Currency,Grn.Rate As Stock_Rate,( Select Currency1 From Exchange_Master "
												+ "Where Currency1=Currency2 )  As Stock_Currency, Grn.Batch_Id AS Grn_Batch_Id,Grn.Po_No,Grn.Receipt_Id "
												+ "AS Grn_Receipt_Id FROM (SELECT Fmv.Mat_No, Fmv.Asset_Category, Fmv.Classification_Id,"
												+ "FMV.Classification_Name, Fmv.\"Category\", Fmv.\"type\",Fmv.\"Specs\", Fmv.Color, Fmv.Spec_No,Fmv.Uom1,"
												+ "Fmv.Ref_No FROM Fixed_Material_View fmv )mat LEFT JOIN (SELECT Bt.Mat_No, Rc.Receipt_Date, Bt.Batch_No1,"
												+ " Bt.Batch_No2, Bt.Batch_No3, Rc.Grn_No, Bt.Batch_Id,Rc.Po_No, Stk.Qty1, Stk.Rate, Rc.Receipt_Id,"
												+ " Rc.Stock_Room_Id FROM Receipt Rc, Receipt_Batch Rb, Batch Bt, Stock Stk WHERE Bt.Batch_Id = Rb.Batch_Id "
												+ " AND Rb.Batch_Id =Stk.Batch_Id AND Rc.Receipt_Id = Stk.Receipt_Id And Rb.Receipt_Id = Rc.Receipt_Id AND "
												+ "Rc.Type IN (SELECT DISTINCT Type FROM Material_View WHERE Category='fixed') And Bt.Po_No=Rc.Po_No )Grn"
												+ " ON Grn.Mat_No=Mat.Mat_No LEFT JOIN (Select Cpm.Po_No, Cpm.Rate, Cpm.Currency From Company_Purchases_Mat "
												+ "Cpm )Cpm ON Grn.Po_No =Cpm.Po_No Where Grn_No Is Not Null And Grn.Stock_Room_Id in(select Stock_Room_Id "
												+ "from Stock_Room where Unit_Name='"+jtfStock_room.getText().toString().trim()+"') order by Mat_No asc";
		
		
			Asset_data						= fInter.LoadVectorContents(sql);
			
			
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		return Asset_data;
	}
	
	static class MyTableCellRenderer implements TableCellRenderer {
		public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			return renderer;
		}
	}
	
	
/************************************
 * Methods and Function call -ENDS
 ************************************/	
	
/***********************************************************************
 * SET Flexi values dynamically in a JTable --Start--
 **********************************************************************/
	public void loadTable() {
		try{
			Vector data_to_load			= new Vector();
			Vector Heading 				= new Vector();
			
			Vector Asset_field			= get_asset_fields();
			Vector Flexi_field			= get_Asset_flexi_fields();

			
			for (int i =0;i< Asset_field.size();i++) {
				Vector temp				= new Vector();
				((Collection) Asset_field.get(i)).stream().forEach(temp::add);
				setBlueColor(( temp.get(0)).toString(),Heading);
			}
			
			for (int i =0;i< Flexi_field.size();i++) {
				Vector temp				= new Vector();
				((Collection) Flexi_field.get(i)).stream().forEach(temp::add);
				setOrangeColor(( temp.get(0)).toString(),Heading);
			}
			
			
			data_to_load.add(new Vector());
			Astflx_field_jtable.setModel(new MyModel(data_to_load, Heading));

			Vector asset_fields				= set_Asset_Fields("fixed",jtfAsset_Type.getText().toString(),Flexi_field);
			Vector Flexi_fields				= set_Flexi_Fields("fixed",jtfAsset_Type.getText().toString(),Asset_field);// hard coded now to be changed 


		}catch (Exception e2) {
			e2.printStackTrace();
		}

	}

/*to get and set the Flexi field from Data-Base*/
	public Vector  set_Flexi_Fields(String category,String Asset_type,Vector Flexi_field_heading){
		Vector flexi_date_values							= new Vector();
		try {
			flexi_Vector					= fInter.get_Flexi_fields_For_Asset_Register(category,Asset_type);
			
			String text 					= "";
			String type 					= "";
			String query 					= "";
			
			for (int i = 0; i < flexi_Vector.size(); i++) {

				text 						= ((Vector) flexi_Vector.get(i)).get(0).toString();
				type 						= ((Vector) flexi_Vector.get(i)).get(1).toString();
					if (((Vector) flexi_Vector.get(i)).get(2) != null) {
						query				= ((Vector) flexi_Vector.get(i)).get(2).toString();
					}
				
				if (!"".equalsIgnoreCase(type) && ("Varchar".equalsIgnoreCase(type) ||
						"Numeric".equalsIgnoreCase(type)) || ("Text".equalsIgnoreCase(type))) {
					
					JTextField j 			= new JTextField();
					setTextBox(Astflx_field_jtable, i+1, j);
					
				}else if (!"".equalsIgnoreCase(type) && ("Date".equalsIgnoreCase(type))) {
				
					setDate(Astflx_field_jtable,i+1);
					
				}else if (!"".equalsIgnoreCase(type) 
						&& ("Combo".equalsIgnoreCase(type))|| !"".equalsIgnoreCase(query)) {
					
					getJCB_flexi_combobox(query);
					setComboBox(Astflx_field_jtable, i+1, JCB_flexi_combobox);
					
				} else if (!"".equalsIgnoreCase(type) 
						&& ("CheckBox".equalsIgnoreCase(type))) {
					
					JCheckBox j 			= new JCheckBox();
					setCheckBox(Astflx_field_jtable, i+1, j);
				}
			
			}
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		return flexi_date_values;
	}
	
/*to get and set the Asset field from Data-Base*/
	public Vector set_Asset_Fields(String category,String Asset_type,Vector Asset_field_heading){
		Vector Asset_date_values					= new Vector();
		try {
			Asset_Field_Vector						= fInter.get_Asset_fields_For_Asset_Register(category,Asset_type);
			String text 							= "";
			String type 							= "";
			String query							= "";
			
			for (int i = 0; i < Asset_Field_Vector.size(); i++) {

				text 								= ((Vector) Asset_Field_Vector.get(i)).get(0).toString();
				type 								= ((Vector) Asset_Field_Vector.get(i)).get(1).toString();
				
				if (((Vector) Asset_Field_Vector.get(i)).get(2) != null) {
						query 						= ((Vector) Asset_Field_Vector.get(i)).get(2).toString();
					}
					
				if (!"".equalsIgnoreCase(type)
						&& ("Varchar".equalsIgnoreCase(type) || "Numeric".equalsIgnoreCase(type))
						|| ("Text".equalsIgnoreCase(type))) {
					
					JTextField j 					= new JTextField();
					AstsetTextBox(Astflx_field_jtable,i+1, j);
					
				}else if (!"".equalsIgnoreCase(type) && ("Date".equalsIgnoreCase(type))) {

					AstsetDate(Astflx_field_jtable,i+1);

				} else if (!"".equalsIgnoreCase(type) && ("Combo".equalsIgnoreCase(type))
						|| !"".equalsIgnoreCase(query)) {

					getJCB_flexi_combobox(query);
					AstsetComboBox(Astflx_field_jtable, i+1, (JComboBox)flexiFields_component[i]);
					
				} else if (!"".equalsIgnoreCase(type) && ("CheckBox".equalsIgnoreCase(type))) {
				
					JCheckBox j 					= new JCheckBox();
					AstsetCheckBox(Astflx_field_jtable, i+1, j);
				}
			
			}
			
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		return Asset_date_values;
	}
	
	

/* this is an constructor injection type in the spring to
  				set the or call a method to load in specifically given operation*/	

class MyModel extends DefaultTableModel{
	MyModel(Vector data, Vector heading){
		super(data, heading);
	}
	public boolean isCellEditable(int row, int col){

			return true;// this is to enable or the fields that are editable or not 
		
	}
	public Class getColumnClass(int cols){
		if(getValueAt(0, cols)!=null && cols>0){
			return (getValueAt(0, cols).getClass());// returns the values
		} else{
			return String.class;
		}
	}
}
/*this is to set the flexi field with their appropriate components 
 					and it field type and it is used for both Asset field and Flexi field*/

	private void setTextBox(JTable excessConfig, int col, JTextField type) {
		TableColumn column = excessConfig.getTableHeader().getColumnModel().getColumn(col);
		column.setCellEditor(new DefaultCellEditor(type));
	}
	private void setCheckBox(JTable excessConfig, int col, JCheckBox type) {
		TableColumn column = excessConfig.getTableHeader().getColumnModel().getColumn(col);
		column.setCellEditor(new DefaultCellEditor(type));
	}
	public void setDate(JTable jtable, int column_index) {
		TableColumn column = jtable.getTableHeader().getColumnModel().getColumn(column_index);
		column.setCellEditor(new MyTableCellEditor());
	}
	public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
		TDateField jdateField = new TDateField(1);

		public Object getCellEditorValue() {

			return ((TDateField) jdateField).getText();
		}

		public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
			return jdateField;
		}
	}
	private void setComboBox(JTable excessConfig, int col, JComboBox type) {
		TableColumn column = excessConfig.getTableHeader().getColumnModel().getColumn(col);
		column.setCellEditor(new DefaultCellEditor(type));
	}

	 public JComboBox getJCB_flexi_combobox(String query) {
			if (JCB_flexi_combobox == null) {
				Vector flexi_combobox_data	= new Vector();
				JCB_flexi_combobox 			= new JComboBox();
				try {
					flexi_combobox_data 	= fInter.LoadVectorContents(query);

					if (flexi_combobox_data != null && flexi_combobox_data.size() > 0) {
						for (int x = 0; x < flexi_combobox_data.size(); x++) {
							JCB_flexi_combobox.addItem(flexi_combobox_data.get(x));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return JCB_flexi_combobox;
		}
	 

	 

		private void AstsetTextBox(JTable excessConfig, int col, JTextField type) {
			TableColumn column = excessConfig.getTableHeader().getColumnModel().getColumn(col);
			column.setCellEditor(new DefaultCellEditor(type));
		}
		private void AstsetCheckBox(JTable excessConfig, int col, JCheckBox type) {
			TableColumn column = excessConfig.getTableHeader().getColumnModel().getColumn(col);
			column.setCellEditor(new DefaultCellEditor(type));
		}
		public void AstsetDate(JTable jtable, int column_index) {
			TableColumn column = jtable.getTableHeader().getColumnModel().getColumn(column_index);
			column.setCellEditor(new MyTableCellEditor());
		}
		public class AstMyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
			TDateField jdateField = new TDateField(1);

			public Object getCellEditorValue() {

				return ((TDateField) jdateField).getText();
			}

			public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
				return jdateField;
			}
		}
		private void AstsetComboBox(JTable excessConfig, int col, JComboBox type) {
			TableColumn column = excessConfig.getTableHeader().getColumnModel().getColumn(col);
			column.setCellEditor(new DefaultCellEditor(type));
		}

	/*	 public JComboBox getJCB_Ast_combobox(String query) {
				if (JCB_Ast_combobox == null) {
					Vector flexi_combobox_data	= new Vector();
					JCB_Ast_combobox 			= new JComboBox();
					try {
						flexi_combobox_data 	= fInter.LoadVectorContents(query);

						if (flexi_combobox_data != null && flexi_combobox_data.size() > 0) {
							for (int x = 0; x < flexi_combobox_data.size(); x++) {
								JCB_Ast_combobox.addItem(flexi_combobox_data.get(x));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return JCB_Ast_combobox;
			}*/
		 
		 
			public JComboBox getCombo(int i,int x,int y,String query){
				flexiFields_component[i]			= new JComboBox();
				flexiFields_component[i].setBounds(new Rectangle(x,y,150,25));
				Vector combo 			= new Vector();
					try {
						combo 			= fInter.LoadContents(query);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				if(combo.size()>0){
					combo.add(0, "Select");
				}
				((JComboBox)flexiFields_component[i]).setModel(new DefaultComboBoxModel(combo));
				return ((JComboBox)flexiFields_component[i]);
			}
			
			

		 public Vector get_asset_fields() {
			Vector Asset_field			= new Vector();
			try {
				Asset_field				= fInter.LoadVectorwithContents("select Afd.Field_Name from FA_Asset_Field_Mapping afm,"
												+ "fa_Ast_Field_Details afd where Afm.ast_Field_id =Afd.ast_Field_id and Afm.Category='fixed'"
												+ " and type='"+jtfAsset_Type.getText().toString()+"'  order by Afm.ast_Field_id asc");
				
			 }catch (Exception e2) {
				e2.printStackTrace();
			 }
			return Asset_field;
		 }
		 
		 public Vector get_Asset_flexi_fields() {
			Vector Flexi_field			= new Vector();
			try {
				Flexi_field 		= fInter.LoadVectorwithContents("select flexi_field_name from flexi_field_material_type  where "
												+ "type ='"+jtfAsset_Type.getText().toString()+"' and category= 'fixed' "
												+ "order by flexi_field_number asc");
			 }catch (Exception e2) {
				 e2.printStackTrace();
			}
			return Flexi_field;
		 }
	 
	 
	 
	 
	 
/***********************************************************************
 * SET Flexi values dynamically in a JTable --END--
 **********************************************************************/

		 
		 
		 
 
 /***********************************************************************
  * Data Preparation to Save & Submit  --STARTS--
  **********************************************************************/
 public Vector prepare_data_for_insert() {
	Vector All_Data_for_save				= new Vector();
	
	try{
		Vector table_data					= sst_Asset_details_table.getDataVector();
		
		Asset_register_pojo					= new Asset_Register_AST_DETAILS[table_data.size()];
		Asset_reg_fieldflexi_pojo			= new Asset_register_Fields_and_Flexis[table_data.size()];
		
		for(int i=0;i<table_data.size();i++) {
			Asset_register_pojo[i]								= new Asset_Register_AST_DETAILS();
			Asset_reg_fieldflexi_pojo[i]						= new Asset_register_Fields_and_Flexis();
			Vector temp_table_data								= (Vector)table_data.get(i);
			
			
			
			Asset_register_pojo[i].Asset_id						= Integer.parseInt(fInter.get_String(" SELECT SEQ_ASSET_ID.NEXTVAL FROM dual ").toString());
			Asset_register_pojo[i].MAT_No						= Integer.parseInt( jtfMAT_No.getText().toString());

			Asset_register_pojo[i].Depreciat_Amotize_Method     = jtfDepreciation_amotization_method.getText().toString();
			Asset_register_pojo[i].Acquisition_Date				= jdf_Acquisition_Date.getText().toString();	
			Asset_register_pojo[i].Life_time_of_Asset_value		= jtfLife_time_of_asset.getText().toString();
			Asset_register_pojo[i].Life_time_of_Asset_period    = JCB_lifetime_of_asset.getSelectedItem().toString();
		
			Asset_register_pojo[i].Department_id				= Integer.parseInt (fInter.get_String("select Dept_Id from Department where"
																		+ " Dept_Name='"+JCB_Department.getSelectedItem().toString()+"'"));
			
			Asset_register_pojo[i].Sub_Department_id			= Integer.parseInt (fInter.get_String("Select SUB_DEPT_ID From FA_Sub_Department where"
																		+ " Sub_Dept_Name='"+JCB_sub_Department.getSelectedItem().toString()+"'"
																		+ " and Dept_Id = '"+Asset_register_pojo[i].Department_id+"'"));
													
			Asset_register_pojo[i].service_Department_id		= Integer.parseInt (fInter.get_String("select Dept_Id from Department where"
																		+ " Dept_Name='"+JCB_Service_Department.getSelectedItem().toString()+"'"));

			Asset_register_pojo[i].Depreciat_Amotize_Percentage	= jtfDepreciation_amotization_ptr.getText().toString();
			Asset_register_pojo[i].Purchase_Value				= jtfPurchase_value_regcur.getText().toString();
			Asset_register_pojo[i].Salvage_Value				= jtfSalvage_value.getText().toString();

			Asset_register_pojo[i].PO_no						= Integer.parseInt( temp_table_data.get(1)==null?"":temp_table_data.get(1).toString() );
			Asset_register_pojo[i].GRN_no						= Integer.parseInt( temp_table_data.get(3)==null?"":temp_table_data.get(3).toString() );
			Asset_register_pojo[i].Batch_id						= Integer.parseInt( temp_table_data.get(4)==null?"":temp_table_data.get(4).toString() );
			Asset_register_pojo[i].Batch_no1					= temp_table_data.get(5)==null?"":temp_table_data.get(5).toString();
			Asset_register_pojo[i].Batch_no2					= temp_table_data.get(6)==null?"":temp_table_data.get(6).toString();
			
			
			Vector Asset_field_Data									= Prepare_Asset_field_to_save(i);
			Vector flexi_field_Data									= Prepare_Flexi_field_to_save(i);

				
			Asset_reg_fieldflexi_pojo[i].Asset_Fields1			= Asset_field_Data.get(0)==null ?"":Asset_field_Data.get(0).toString();
			Asset_reg_fieldflexi_pojo[i].Asset_Fields2			= Asset_field_Data.get(1)==null ?"":Asset_field_Data.get(1).toString();
			Asset_reg_fieldflexi_pojo[i].Asset_Fields3			= Asset_field_Data.get(2)==null ?"":Asset_field_Data.get(2).toString();
			Asset_reg_fieldflexi_pojo[i].Asset_Fields4			= Asset_field_Data.get(3)==null ?"":Asset_field_Data.get(3).toString();
			Asset_reg_fieldflexi_pojo[i].Asset_Fields5			= Asset_field_Data.get(4)==null ?"":Asset_field_Data.get(4).toString();
			Asset_reg_fieldflexi_pojo[i].Asset_Fields6			= Asset_field_Data.get(5)==null ?"":Asset_field_Data.get(5).toString();	

			Asset_reg_fieldflexi_pojo[i].Flexi_Fields1			= flexi_field_Data.get(0)==null?"":flexi_field_Data.get(0).toString();
			Asset_reg_fieldflexi_pojo[i].Flexi_Fields2			= flexi_field_Data.get(1)==null?"":flexi_field_Data.get(1).toString();
			Asset_reg_fieldflexi_pojo[i].Flexi_Fields3			= flexi_field_Data.get(2)==null?"":flexi_field_Data.get(2).toString();
			Asset_reg_fieldflexi_pojo[i].Flexi_Fields4			= flexi_field_Data.get(3)==null?"":flexi_field_Data.get(3).toString();
			Asset_reg_fieldflexi_pojo[i].Flexi_Fields5			= flexi_field_Data.get(4)==null?"":flexi_field_Data.get(4).toString();
			Asset_reg_fieldflexi_pojo[i].Flexi_Fields6			= flexi_field_Data.get(5)==null?"":flexi_field_Data.get(5).toString();


			

		
			
		}
		
	}catch (Exception e2) {
		e2.printStackTrace();
	}
	 return All_Data_for_save;
 }
 
 
 
 public Vector Prepare_Asset_field_to_save(int tableindex ) {
	 Vector Asset_field			= new Vector();
	 try{
		 
			Vector sst_tabledata			= sst_Asset_details_table.getDataVector();
			
			if (Asset_Field_Vector.size() > 0) {
				Vector selected_row			= (Vector)sst_tabledata.get(tableindex);

				for(int i=0;i<Asset_Field_Vector.size();i++) {

					Asset_field.add(selected_row.get(6 + (i+1)));
	
				}
			}

			if(Asset_Field_Vector.size()<6) {
				
				for(int i=0;i<6-Asset_Field_Vector.size();i++) {
					Asset_field.add("");
				}
			}
			
	 }catch (Exception e2) {
			e2.printStackTrace();
	}
	 return Asset_field;
 }
 
 public Vector Prepare_Flexi_field_to_save(int tableindex ) {
	 Vector Flexi_field			= new Vector();
	 try{
		 
			Vector sst_tabledata			= sst_Asset_details_table.getDataVector();
			
			if (flexi_Vector.size() > 0) {
				Vector selected_row			= (Vector)sst_tabledata.get(tableindex);

				for(int i=0;i<flexi_Vector.size();i++) {

					Flexi_field.add(selected_row.get( (6 +Asset_Field_Vector.size()) + (i+1) ));
	
				}
			}

			if(flexi_Vector.size()<6) {
				
				for(int i=0;i<6-flexi_Vector.size();i++) {
					Flexi_field.add("");
				}
			}
			
	 }catch (Exception e2) {
			e2.printStackTrace();
	}
	 return Flexi_field;
 }
		 
 
 
 
 
 public void submit_action() {
		prepare_data_for_insert();
		
		String trans_name= "Import Asset Register";
		try {


					
			if(fInter.isApprovalEnabled(trans_name)) {
				Add_asset_Import_ThroughApproval(trans_name);
			}else {
				if (JOptionPane.showConfirmDialog(null, "Approval not enabled for Add Asset Register transaction. Do you want to Submit the Asset without Approval?",
						"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					boolean is_main_tables		= true;
					int Asset_id				= 0;
	
					Asset_id		=	fInter.save_In_Asset_Register_tables_for_GRN_Import(Asset_register_pojo,Asset_reg_fieldflexi_pojo,Asset_register_Image_pojo,is_main_tables) ;
					if (Asset_id!=0)	{
						JOptionPane.showMessageDialog(null, "Asset ID has been created "+Asset_id);
						cancel_action_dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Error occured while saving.Asset ID not created");
						return;
					}
				}
			}
			
			


		
		} catch (RemoteException |SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error occured while saving.Asset ID not created");
			e1.printStackTrace();
		} finally {
			trans_name		=null;
		}
	}
 
 
 public void Add_asset_Import_ThroughApproval(String trans_name){
	 try {
		 
			String handlerClass				 = "AssetImport_and_AssetCode";
			String functionNameforApproval 	 = "Add_AssetImport_to_Register";
			String identificationProperty 	 = "Import ID";
			String identificationValue		 = "";
			int Import_id 					 = Integer.parseInt(fInter.get_String(" SELECT Import_asset_seq.NEXTVAL FROM dual ").toString());
			String Message					 = "Asset Imported";
			
			transaction_object			= new Vector();
			
			transaction_object.add(Import_id);
			transaction_object.add(Asset_register_pojo);		// all data POJO 
			transaction_object.add(Asset_reg_fieldflexi_pojo);  // Flexi field & asset field data POJO 
			transaction_object.add(Asset_register_Image_pojo);  // Image pop up POJO
			
			
			Vector Approval_Data		= new Vector();
			
			Approval_Data.add(UFrame.uframe_user_id);		// user id
			Approval_Data.add(functionNameforApproval);		// function that do operations
			Approval_Data.add(handlerClass);				// class file that display or operates the methods
			Approval_Data.add(identificationProperty);		// the property are shown in the last field of approval like Asset id
			Approval_Data.add(identificationValue);			// this the seq_id of the Screen 
			Approval_Data.add(trans_name);					// name of the transaction
			Approval_Data.add("Finance");					// application that Belong to
			Approval_Data.add(transaction_object);			// object that has to be inserted 
			Approval_Data.add(Message);
			
			
			Vector transaction_id 		= new Vector();
			HashMap result 				= fInter.Save_AssetRegister_forApproval(Approval_Data);
			
			if (result == null) {
				System.out.println("Error while Saving");
				JOptionPane.showMessageDialog(null, "Error while Saving");

			} else if (result.get("Approval Error") != null) {
				JOptionPane.showMessageDialog(null, "Sorry..! Approval Server ERROR, Contact Admin");

			} else if (result.get("Transaction Id") != null) {
				transaction_id.add(result.get("Transaction Id"));
			
				if (trans_name.equals("Import Asset Register")) {
					boolean is_main_tables		= false;
					if ( fInter.save_In_Asset_Register_tables_for_GRN_Import(Asset_register_pojo,Asset_reg_fieldflexi_pojo,Asset_register_Image_pojo,is_main_tables)== 0 ) {
						JOptionPane.showMessageDialog(null, "Error while Saving");
						return;
					}else {
						JOptionPane.showMessageDialog(null, "Your transaction is saved and waiting for approval, transaction id is : " + result.get("Transaction Id"));
					}
				}
				cancel_action_dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Error while Saving");
				return;
			}
			
	 } catch (RemoteException | HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
 }
 
 
 
 /***********************************************************************
  * Data Preparation to Save & Submit   --ENDS--
  **********************************************************************/
	@Override
	public void setGUIDiffReference(GUIDifference gui) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
//		parent.parent.innerPanel.removeMenuBar();	

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
//		parent.parent.innerPanel.removeMenuBar();	

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
	
	
	public int setBlueColor(String col_name,Vector heading) {
		heading.addElement("<html><b><font color=blue>"+col_name+"</font></b></html>");
		return heading.indexOf("<html><b><font color=blue>" + col_name + "</font></b></html>");/*returning the column values as an int to 
																										find the index of the editable field  */
	}
	public int setOrangeColor(String col_name,Vector heading) {
		heading.addElement("<html><b><font color=#FF7015>"+col_name+"</font></b></html>");
		return heading.indexOf("<html><b><font color=#FF7015>"+col_name+"</font></b></html>");/*returning the column values as an int to 
																										find the index of the editable field  */

	}

	@Override
	public boolean callFileterEvent() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
