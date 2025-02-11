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
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

import HarnessErp.AddChainPopUp.MyModel;
import HarnessErp.LCMgtMain.MyTableCellRenderer;
import common.Asset_Register_AST_DETAILS;
import common.Asset_register_Fields_and_Flexis;
import common.Asset_register_Image;
import common.Design;
import common.Utools;
import common.sstFilterEvent;
import common.gui.InnerPanel;
import common.gui.OuterFrame;
import common.gui.SortSelectTable;
import common.gui.TDateField;

public class AssetRegister_main extends JInternalFrame
implements ActionListener, InternalFrameListener, ListSelectionListener {
	
	OuterFrame outerFrm							= null;
	InnerPanel innerPanel 						= null;
	FinanceController parent					= null;
	FinanceInterface fInter 					= null;
	AssetRegister_main THIS						= null;
	String[] allnode 							= null;
	
	AssetRegister_allframe_home astaddmod_alltab		= null;
	Asset_register_DocumentViewPopup ast_doc_viewpopup	= null;
	AssetImport_and_AssetCode  Asstimptandcode			= null;
	
	private JPanel jContentPane 				= null;
	private JMenu jm_action 					= null;
	private JMenu jm_import						= null;
	private JMenu jm_doc_view					= null;
	
	private JMenuBar jMenuBar 					= null;
	
	private JMenuItem jmi_add 					= null;
	private JMenuItem jmi_modify 				= null;
	private JMenuItem jmi_view					= null;
	private JMenuItem jmi_delete 				= null;
	private JMenuItem jmi_refresh 				= null;
	private JMenuItem jmi_grn_import			= null;
	
	private JMenuItem jmi_import_asset			= null;
	
	private JMenuItem jmi_view_docmt			= null;

	private JLabel jl_Acquisition_Date			= null;
	private JLabel jl_to 						= null;
	private TDateField jdf_Acquisition_Date		= null;
	private TDateField jdf_to					= null;
	
	private JLabel jl_Asset_category			= null;
	JComboBox JCB_Asset_category				= null;
	private JLabel jl_Asset_classification		= null;
	JComboBox JCB_Asset_classification			= null;
	private JLabel jl_Asset_type				= null;
	JComboBox JCB_Asset_type					= null;
	private JLabel jl_Asset_department			= null;
	JComboBox JCB_Asset_department				= null;
	private JLabel jl_Asset_sub_department		= null;
	JComboBox JCB_Asset_sub_department			= null;
	private JLabel jl_Asset_location			= null;
	JComboBox JCB_Asset_location				= null;
	private JLabel jl_Asset_status				= null;
	JComboBox JCB_Asset_status					= null;
	private JLabel jl_sub_location_1			= null;
	JComboBox JCB_sub_location_1				= null;
	private JLabel jl_sub_location_2			= null;
	JComboBox JCB_sub_location_2				= null;
	private JLabel jl_sub_location_3			= null;
	JComboBox JCB_sub_location_3				= null;
	
	public JButton jbload_ast					= null;

	public SortSelectTable sst_asset_register 	= null;
	ListSelectionModel lsm 						= null;	
	
	javax.swing.JPanel iconPanel 				= null;
	JLabel jlIcon 								= null;
	ImageIcon icon 								= null;
	Vector iconDetails							= null;
	String fileType 							= "";
	String fileName 							= "";

	
/*added to check if the filters are edited or not */
//	boolean filters_are_edited = false;
	
	Asset_Register_AST_DETAILS[] Asset_register_pojo		  		= null;
	Asset_register_Fields_and_Flexis[] Asset_reg_fieldflexi_pojo	= null;
	Asset_register_Image[] Asset_register_Image_pojo				= null;
	int Asset_id													= 0;
	
//asset main
	public AssetRegister_main(OuterFrame outerFrm,InnerPanel innerPanel ,FinanceController parent,FinanceInterface fInter,String[] allnode ) {
		super();
		this.setTitle("Asset Register");
		this.outerFrm		= outerFrm;
		this.fInter			= fInter;
		this.parent			= parent;
		this.innerPanel		= innerPanel;
		this.THIS			= this;	
		this.allnode		= allnode;
		
		
		initialize();
		createMenuBar();
		
	}
	
	private void initialize() {
		this.setLayout(null);
		this.setContentPane(getJContentPane());
		this.setSize(950, 600);
		new Design("Order_Planning").setColors(this);
		addInternalFrameListener(this);
		sub_location_flexi_condition(true,true);

	}
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			jContentPane.add(getjl_Asset_category(), null);
			jContentPane.add(getJCB_Asset_category(), null);//--ast _category
			jContentPane.add(getjl_classification(), null);
			jContentPane.add(getJCB_Asset_classification(), null);//--ast clasfin
			jContentPane.add(getjl_Asset_type(), null);
			jContentPane.add(getJCB_Asset_type(), null);//--ast type
			jContentPane.add(getjl_Asset_department(), null);
			jContentPane.add(getJCB_Asset_department(), null);//--ast dept
			jContentPane.add(getjl_Asset_sub_department(), null);
			jContentPane.add(getJCB_Asset_sub_department(), null);//--ast sub dept
	
		
			jContentPane.add(getjl_Acquisition_Date(), null);
			jContentPane.add(getjdf_Acquisition_Date(), null);//--frm date
			jContentPane.add(getjl_to(), null);
			jContentPane.add(getjdf_to(), null);//-- to date
			jContentPane.add(getjl_Asset_location(), null);
			jContentPane.add(getJCB_Asset_location(), null);//--location 
			jContentPane.add(getjl_Asset_status(), null);
			jContentPane.add(getJCB_Asset_status(), null);//--ast status
			
			
			jContentPane.add(getjl_sub_location_1(), null);
			jContentPane.add(getJCB_sub_location_1(), null);//--ast sub_location_1
			jContentPane.add(getjl_sub_location_2(), null);
			jContentPane.add(getJCB_sub_location_2(), null);//--ast sub_location_2
			jContentPane.add(getjl_sub_location_3(), null);
			jContentPane.add(getJCB_sub_location_3(), null);//--ast sub_location_3
		
			jContentPane.add(getJBload_ast(), null);
			
			jContentPane.add(getsst_asset_register(), null);//--ast sst table

			jContentPane.add(getIconPanel(), null);//--ast image_icon
			jContentPane.add(getJlIcon(), null);
			
		}
		return jContentPane;

	}
	
	public void createMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();

		}
		
		if (jmi_add == null) {
			jmi_add = new JMenuItem("Add");
			jmi_add.addActionListener(this);
		}
		if (jmi_modify == null) {
			jmi_modify = new JMenuItem("Modify");
			jmi_modify.addActionListener(this);
		}

		if (jmi_delete == null) {
			jmi_delete = new JMenuItem("Delete");
			jmi_delete.addActionListener(this);
		}
		
		if (jmi_view == null) {
			jmi_view = new JMenuItem("view");
			jmi_view.addActionListener(this);
		}

		if (jmi_grn_import == null) {
			jmi_grn_import = new JMenuItem("grn import");
			jmi_grn_import.addActionListener(this);
		}
		
		if (jmi_refresh == null) {
			jmi_refresh = new JMenuItem("Refresh");
			jmi_refresh.addActionListener(this);
		}

		if (jmi_import_asset == null) {
			jmi_import_asset = new JMenuItem("import Asset");
			jmi_import_asset.addActionListener(this);
		}
		
		if (jmi_view_docmt == null) {
			jmi_view_docmt = new JMenuItem("view Document");
			jmi_view_docmt.addActionListener(this);
		}

		if (jm_action == null) {
			
			jm_action 	= new JMenu("Action");
			jm_import 	= new JMenu("Import");
			jm_doc_view	= new JMenu("Document View");
			
			jm_action.add(jmi_add);
			jm_action.add(jmi_modify);
			jm_action.add(jmi_view);
			jm_action.add(jmi_grn_import);
			jm_action.add(jmi_delete);
			jm_action.add(jmi_refresh);
			
			jm_import.add(jmi_import_asset);
			jm_doc_view.add(jmi_view_docmt);
			
		}
//		if (jm_import == null) {
//		}
//		
//		if (jm_doc_view == null) {
//			
//		}
		
		jMenuBar.add(jm_action);
		jMenuBar.add(jm_import);
		jMenuBar.add(jm_doc_view);
		
		innerPanel.setMenuBar(jMenuBar);
		innerPanel.repaint();

	}
	
	
	
	
/* ****************************************
 * field declaration 
 * ****************************************
 */	
	
	
	
	private JLabel getjl_Asset_category() {
		if (jl_Asset_category == null) {
			jl_Asset_category = new JLabel();
			jl_Asset_category.setText("Asset category");
			jl_Asset_category.setName("Asset_jlbl_Asset_category");
			jl_Asset_category.setBounds(new Rectangle(20, 10, 100, 30));

		}
		return jl_Asset_category;
	}
	
	public JComboBox getJCB_Asset_category() {
		if (JCB_Asset_category == null) {
			try {
				Vector allUnitNames = fInter.LoadVectorContents("select distinct ast_category_name from asset_category order by ast_category_name desc");
				JCB_Asset_category  = new JComboBox(allUnitNames);
				JCB_Asset_category.setBounds(120, 10, 120, 70);
				JCB_Asset_category.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) { 
						String Asset_category 	= JCB_Asset_category.getSelectedItem().toString();
						
						if(!JCB_Asset_category.getSelectedItem().toString().isEmpty() ){// This will load as per the category selection in the JCB_Asset_category combo box
						    try {
								Vector vecJCB_Asset_classification	= fInter.LoadVectorContents(" select distinct AST_CLASSIFICATION_NAME from FA_AST_CONFIG_CLASSIFICATION where  ASSET_CATEGORY= '"+Asset_category+"' ");
								JCB_Asset_classification.setModel(new DefaultComboBoxModel(vecJCB_Asset_classification));	
								
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
		return JCB_Asset_category;
	}
	
	
	private JLabel getjl_classification() {
		if (jl_Asset_classification == null) {
			jl_Asset_classification = new JLabel();
			jl_Asset_classification.setText("classification");
			jl_Asset_classification.setName("Asset_jlbl_classification");
			jl_Asset_classification.setBounds(new Rectangle(275, 10, 70, 30));

		}
		return jl_Asset_classification;
	}
	
	public JComboBox getJCB_Asset_classification() {
		if (JCB_Asset_classification == null) {
			try {
				Vector allUnitNames = fInter.LoadVectorContents("select distinct AST_classification_name from FA_AST_CONFIG_CLASSIFICATION");
				JCB_Asset_classification = new JComboBox(allUnitNames);
				JCB_Asset_classification.setBounds(340, 10, 120, 70);
				JCB_Asset_classification.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) { 
						String Classification_name 	= JCB_Asset_classification.getSelectedItem().toString();
						
						if(JCB_Asset_classification.getSelectedIndex()>0 ){//this will load the type of the asset as the classification selection in the JCB_Asset_classification combo box
						    try {
								Vector vecJCB_Asset_Classification 	= fInter.LoadVectorContentsAll("Select Distinct \"type\" From Fixed_Material_View"
																			+ " Where Classification_Name= '"+Classification_name+"' ");
								JCB_Asset_type.setModel(new DefaultComboBoxModel(vecJCB_Asset_Classification));	
							
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
		return JCB_Asset_classification;
	}
	
	
	private JLabel getjl_Asset_type() {
		if (jl_Asset_type == null) {
			jl_Asset_type = new JLabel();
			jl_Asset_type.setText("Type");
			jl_Asset_type.setName("Asset_jlbl_Asset_type");
			jl_Asset_type.setBounds(new Rectangle(490, 10, 50, 30));

		}
		return jl_Asset_type;
	}
	
	public JComboBox getJCB_Asset_type() {
		if (JCB_Asset_type == null) {
			try {
				Vector allUnitNames = fInter.LoadVectorContentsAll("Select Distinct \"type\" from Fixed_Material_View ");
				JCB_Asset_type = new JComboBox(allUnitNames);
				JCB_Asset_type.setBounds(550, 10, 120, 70);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JCB_Asset_type;
	}
	
	private JLabel getjl_Asset_department() {
		if (jl_Asset_department == null) {
			jl_Asset_department = new JLabel();
			jl_Asset_department.setText("Department");
			jl_Asset_department.setName("Asset_jlbl_Asset_department");
			jl_Asset_department.setBounds(new Rectangle(700, 10, 70, 30));

		}
		return jl_Asset_department;
	}
	
	public JComboBox getJCB_Asset_department() {
		if (JCB_Asset_department == null) {
			try {
				Vector allUnitNames 	= fInter.LoadVectorContentsAll("Select dept_name from DEPARTMENT order by dept_name");
				JCB_Asset_department 	= new JComboBox(allUnitNames);
				JCB_Asset_department.setBounds(760, 10, 120, 70);
				JCB_Asset_department.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) { 
						String department_name 	= JCB_Asset_department.getSelectedItem().toString();
						
						if(JCB_Asset_department.getSelectedIndex()>0 ){//this set of code load the sub department name according to the department selection in the  JCB_Asset_department combo box
						    try {
								Vector vecJCB_Asset_sub_department 	= fInter.LoadVectorContentsAll("Select sub_dept_name from FA_SUB_DEPARTMENT where dept_id "
																			+ "in( Select dept_id from DEPARTMENT where dept_name='"+department_name+"' ) "
																			+ "order by sub_dept_name ");
								JCB_Asset_sub_department.setModel(new DefaultComboBoxModel(vecJCB_Asset_sub_department));	
							
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
		return JCB_Asset_department;
	}
	
	
	
	
	private JLabel getjl_Asset_sub_department() {
		if (jl_Asset_sub_department == null) {
			jl_Asset_sub_department = new JLabel();
			jl_Asset_sub_department.setText("sub-Department");
			jl_Asset_sub_department.setName("Asset_jlbl_Asset_department");
			jl_Asset_sub_department.setBounds(new Rectangle(900, 10, 70, 30));

		}
		return jl_Asset_sub_department;
	}
	
	public JComboBox getJCB_Asset_sub_department() {
		if (JCB_Asset_sub_department == null) {
			try {
				Vector	allUnitNames 	 = fInter.LoadVectorContentsAll("Select sub_dept_name from FA_SUB_DEPARTMENT order by sub_dept_name ");
				JCB_Asset_sub_department = new JComboBox(allUnitNames);
				JCB_Asset_sub_department.setBounds(975, 10, 130, 70);	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return JCB_Asset_sub_department;
	}
	
//2nd row-----------	
	
	
	private JLabel getjl_Acquisition_Date () {
		if (jl_Acquisition_Date == null) {
			jl_Acquisition_Date = new JLabel();
			jl_Acquisition_Date.setText("Acquisition Date from");
			jl_Acquisition_Date.setName("Asset_jl_Acquisition_Date");
			jl_Acquisition_Date.setBounds(new Rectangle(20, 50, 100, 30));

		}
		return jl_Acquisition_Date;
	}


	private TDateField getjdf_Acquisition_Date() {
		if (jdf_Acquisition_Date == null) {
			try {
				Integer datefrom_ast_config	=  fInter.get_Int("select value from FA_ASSET_CONFIGURATION where property= 'Asset Register Default Date Range'");
				Calendar cal = GregorianCalendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, -datefrom_ast_config);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	
				jdf_Acquisition_Date = new TDateField();
				jdf_Acquisition_Date.setBounds(new Rectangle(120, 50, 100, 30));
				jdf_Acquisition_Date.setText(sdf.format(cal.getTime()));
				jdf_Acquisition_Date.setVisible(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jdf_Acquisition_Date;
	}

	private JLabel getjl_to() {
		if (jl_to == null) {
			jl_to = new JLabel();
			jl_to.setBounds(new Rectangle(275, 50, 50, 30));
			jl_to.setText("To");
			
		}
		return jl_to;
	}
	
	
	private TDateField getjdf_to() {
		if (jdf_to == null) {
			jdf_to = new TDateField();
			jdf_to.setBounds(new Rectangle(340, 50, 100, 30));
			jdf_to.setVisible(true);
		}
		return jdf_to;
	}
	
	
	private JLabel getjl_Asset_location() {
		if (jl_Asset_location == null) {
			jl_Asset_location = new JLabel();
			jl_Asset_location.setText("location");
			jl_Asset_location.setName("Asset_jlbl_Asset_location");
			jl_Asset_location.setBounds(new Rectangle(490, 50, 100, 30));

		}
		return jl_Asset_location;
	}
	
	public JComboBox getJCB_Asset_location() {
		if (JCB_Asset_location == null) {
			try {
				Vector all_loc_Names 				= fInter.LoadVectorContentsAll("select Location_Name from FA_LOCATION_MASTER where Status='Active' order by Location_Id ");//change
				JCB_Asset_location 					= new JComboBox(all_loc_Names);
				JCB_Asset_location.setBounds(550, 50, 120, 70);
				JCB_Asset_location.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) { 
						String loaction_name 		= JCB_Asset_location.getSelectedItem().toString();
						
						if(JCB_Asset_location.getSelectedIndex()>0 ){
						    try {
								
								Vector vecjcbflexi_sub_loc_1 	= fInter.LoadVectorContents("Select Sub_Location From FA_SUB_LOCATION Where Location_Id "
																		+ "In(select Location_Id from FA_LOCATION_MASTER where Location_Name='"+loaction_name+"' )"
																		+ " and sub_Loc_Type='1'");

								Vector vecjcbflexi_sub_loc_2 	= fInter.LoadVectorContents("Select Sub_Location From FA_SUB_LOCATION Where Location_Id "
																		+ "In(Select Location_Id From FA_LOCATION_MASTER Where Location_Name='"+loaction_name+"' ) "
																		+ "And sub_Loc_Type='2'");
								
								Vector vecjcbflexi_sub_loc_3 	= fInter.LoadVectorContents("Select Sub_Location From FA_SUB_LOCATION Where Location_Id "
																		+ "In(select Location_Id from FA_LOCATION_MASTER where Location_Name='"+loaction_name+"' )"
																		+ " and sub_Loc_Type='3'");



								

								JCB_sub_location_1.setModel(new DefaultComboBoxModel(vecjcbflexi_sub_loc_1));	
								JCB_sub_location_2.setModel(new DefaultComboBoxModel(vecjcbflexi_sub_loc_2));	
								JCB_sub_location_3.setModel(new DefaultComboBoxModel(vecjcbflexi_sub_loc_3));	
								
							
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
		return JCB_Asset_location;
	}
	
	
	private JLabel getjl_Asset_status() {
		if (jl_Asset_status == null) {
			jl_Asset_status = new JLabel();
			jl_Asset_status.setText("Status");
			jl_Asset_status.setName("Asset_jlbl_Asset_Status");
			jl_Asset_status.setBounds(new Rectangle(700, 50, 100, 30));

		}
		return jl_Asset_status;
	}
	
	public JComboBox getJCB_Asset_status() {
		if (JCB_Asset_status == null) {
			try {
				Vector allUnitNames = new Vector();
				allUnitNames.add("Active");
				allUnitNames.add("In-Active");
				JCB_Asset_status = new JComboBox(allUnitNames);
				JCB_Asset_status.setSelectedItem("Active");

				JCB_Asset_status.setBounds(760, 50, 120, 70);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JCB_Asset_status;
	}
	
	
	//3rd row--------
	
	
	private JLabel getjl_sub_location_1() {
		if (jl_sub_location_1 == null) {
			jl_sub_location_1 = new JLabel();
			jl_sub_location_1.setText("sub-location 1");
			jl_sub_location_1.setName("Asset_jlbl_Asset_category");
			jl_sub_location_1.setBounds(new Rectangle(20, 80, 100, 30));

		}
		return jl_sub_location_1;
	}
	
	public JComboBox getJCB_sub_location_1() {
		if (JCB_sub_location_1 == null) {
			try {
				Vector allsub_loc_Names 	= fInter.LoadVectorContentsAll("Select Sub_Location From FA_SUB_LOCATION Where sub_Loc_Type='1' ");
			
				JCB_sub_location_1 = new JComboBox(allsub_loc_Names);
				JCB_sub_location_1.setBounds(120, 80, 120, 70);
				JCB_sub_location_1.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) { 
						String loaction_name 				= JCB_Asset_location.getSelectedItem().toString();
						String sub_location					= null ;
					
						if(JCB_sub_location_1.getSelectedIndex()>0 ){
						    sub_location					= "1" ;
							Vector vecjcbflexi_sub_loc_2	= load_sub_locations_(sub_location);
							JCB_sub_location_2.setModel(new DefaultComboBoxModel(vecjcbflexi_sub_loc_2));	

							sub_location					= "2" ;
							Vector vecjcbflexi_sub_loc_3 	= load_sub_locations_(sub_location);
							JCB_sub_location_3.setModel(new DefaultComboBoxModel(vecjcbflexi_sub_loc_3));

							
						}					
					}
					});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JCB_sub_location_1;
	}
	
	
	private JLabel getjl_sub_location_2() {
		if (jl_sub_location_2 == null) {
			jl_sub_location_2 = new JLabel();
			jl_sub_location_2.setText("sub-location 2");
			jl_sub_location_2.setName("Asset_jlbl_Asset_category");
			jl_sub_location_2.setBounds(new Rectangle(275, 80, 100, 30));

		}
		return jl_sub_location_2;
	}
	
	public JComboBox getJCB_sub_location_2() {
		if (JCB_sub_location_2 == null) {
			try {
				Vector allsub_loc_Names = fInter.LoadVectorContentsAll("Select Sub_Location From FA_SUB_LOCATION Where sub_Loc_Type='2'");
				
				JCB_sub_location_2 = new JComboBox(allsub_loc_Names);
				JCB_sub_location_2.setBounds(340, 80, 120, 70);
				JCB_sub_location_2.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) { 
						String loaction_name 	= JCB_sub_location_2.getSelectedItem().toString();
						String sub_location		= null ;
						if(JCB_sub_location_2.getSelectedIndex()>0 ){
						    sub_location					= "2" ;
							Vector vecjcbflexi_sub_loc_3 	= load_sub_locations_(sub_location);
							
							JCB_sub_location_3.setModel(new DefaultComboBoxModel(vecjcbflexi_sub_loc_3));
						}					
					}
					});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JCB_sub_location_2;
	}
	
	

	private JLabel getjl_sub_location_3() {
		if (jl_sub_location_3 == null) {
			jl_sub_location_3 = new JLabel();
			jl_sub_location_3.setText("sub-location 3");
			jl_sub_location_3.setName("Asset_jlbl_Asset_category");
			jl_sub_location_3.setBounds(new Rectangle(490, 80, 100, 30));

		}
		return jl_sub_location_3;
	}
	
	public JComboBox getJCB_sub_location_3() {
		if (JCB_sub_location_3 == null) {
			try {
				Vector allUnitNames = fInter.LoadVectorContentsAll("Select Sub_Location From FA_SUB_LOCATION Where sub_Loc_Type='3'");
				JCB_sub_location_3 = new JComboBox(allUnitNames);
				JCB_sub_location_3.setBounds(550, 80, 120, 70);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JCB_sub_location_3;
	}
	
	 private JButton getJBload_ast() {
			if (jbload_ast == null) {
				jbload_ast = new JButton("Load");
				jbload_ast.setBounds(780, 80, 95, 30);
				jbload_ast.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						String other_query_while_loading	= "";
						try {
							other_query_while_loading		= perpare_selected_component_query_for_main_table();
							Vector load_data			 	= fInter.load_Asset_register_maintable(true,other_query_while_loading);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
			return jbload_ast;
		}
	
	private SortSelectTable getsst_asset_register(){
		if(sst_asset_register == null){
			sst_asset_register = new SortSelectTable();
			sst_asset_register.setBounds(new Rectangle(20,150,845,200));
			sst_asset_register.setName("Asset_Register_sst_asset_register");
			new Design("Order_Planning").setColors(sst_asset_register);
			
			fill_asset_register_Table();
		
		}
		return sst_asset_register;
	}
	
	
	
	
	public void fill_asset_register_Table() {
		try{
			Vector heading		= new Vector();
			Vector load_data	= new Vector();
			Vector hide			= new Vector();
			
			heading.addElement("Asset ID");
			heading.addElement("Asset Code");
			heading.addElement("MAT No");
			heading.addElement("Asset Category");
			heading.addElement("Classification");
			heading.addElement("Asset Type");
			heading.addElement("Specification");
			heading.addElement("Acquisition Date");
			heading.addElement("Department");
			heading.addElement("Sub-Dept");
			
			heading.addElement("Location");
			
			Vector flexi_Column_heading 		= sub_location_flexi_condition(false,true);//to get the sub_location flexi column 
			flexi_Column_heading.stream().forEach(heading::add);

			heading.addElement("Depr%");
			heading.addElement("Acquisition Value");
			heading.addElement("Depreciation Value");
			heading.addElement("Book Value");
			heading.addElement("Salvage Value");
			heading.addElement("Currency");
			heading.addElement("Life time");
			heading.addElement("Age");
			heading.addElement("Status");
			heading.addElement("Approval Status");
			heading.addElement("Batch id");
			heading.addElement("Grn No");
			
//			hide.add("Batch id");
//			hide.add("Grn No");
			flexi_Column_heading.size();
			
			String other_query_while_loading	= "";
			load_data							= fInter.load_Asset_register_maintable(false,other_query_while_loading);
			
			MyTableCellRenderer renderer 		= new MyTableCellRenderer();
//			sst_asset_register.setTable(load_data, heading, "Order_Planning", "", false);
			sst_asset_register.setTable(load_data, heading, "Finance", "Asset Register", false, hide);
			lsm = sst_asset_register.table.getSelectionModel();
			sst_asset_register.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lsm.addListSelectionListener(this);
			
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void refresh(){
		try {	
			outerFrm.showMsg("");		
			Integer datefrom_ast_config	=  fInter.get_Int("select value from FA_ASSET_CONFIGURATION where property= 'Asset Register Default Date Range'");
			Calendar cal 				= GregorianCalendar.getInstance();
			cal.add( Calendar.DAY_OF_YEAR, -datefrom_ast_config);
			SimpleDateFormat sdf 		= new SimpleDateFormat("dd-MMM-yy");
			jdf_Acquisition_Date.setText(sdf.format(cal.getTime()));
			jdf_to = new TDateField();
			refill_combox();
	
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void refill_combox() {
		
		Vector vecJCB_Asset_category 		= new Vector();
		Vector vecJCB_Asset_classification	= new Vector();
		Vector vecJCB_Asset_department		= new Vector();
		Vector vecJCB_Asset_sub_department	= new Vector();
		Vector vecJCB_Asset_location 		= new Vector();
		Vector vecJCB_sub_location_1 		= new Vector();
		Vector vecJCB_sub_location_2 		= new Vector();
		Vector vecJCB_sub_location_3 		= new Vector();
		
		
		try{
			
			vecJCB_Asset_category 			= fInter.LoadVectorContents("select distinct ast_category_name from asset_category order by ast_category_name desc");
			vecJCB_Asset_classification 	= fInter.LoadVectorContents("select distinct AST_CLASSIFICATION_NAME from FA_AST_CONFIG_CLASSIFICATION");
			vecJCB_Asset_department 		= fInter.LoadVectorContentsAll("Select dept_name from DEPARTMENT order by dept_name");
			vecJCB_Asset_sub_department 	= fInter.LoadVectorContentsAll("Select sub_dept_name from FA_SUB_DEPARTMENT order by sub_dept_name ");
			vecJCB_Asset_location			= fInter.LoadVectorContentsAll("select location_name from FA_LOCATION_MASTER order by location_name");
			vecJCB_sub_location_1 			= fInter.LoadVectorContentsAll("Select Sub_Location From FA_SUB_LOCATION Where sub_Loc_Type='1' ");
			vecJCB_sub_location_2 			= fInter.LoadVectorContentsAll("Select Sub_Location From FA_SUB_LOCATION Where sub_Loc_Type='2'");
			vecJCB_sub_location_3 			= fInter.LoadVectorContentsAll("Select Sub_Location From FA_SUB_LOCATION Where sub_Loc_Type='3'");
			
			
			JCB_Asset_category.setModel(new DefaultComboBoxModel(vecJCB_Asset_category));
			JCB_Asset_classification.setModel(new DefaultComboBoxModel(vecJCB_Asset_classification));
			JCB_Asset_department.setModel(new DefaultComboBoxModel(vecJCB_Asset_department));
			JCB_Asset_sub_department.setModel(new DefaultComboBoxModel(vecJCB_Asset_sub_department));
			JCB_Asset_location.setModel(new DefaultComboBoxModel(vecJCB_Asset_location));
			JCB_sub_location_1.setModel(new DefaultComboBoxModel(vecJCB_sub_location_1));	
			JCB_sub_location_2.setModel(new DefaultComboBoxModel(vecJCB_sub_location_2));	
			JCB_sub_location_3.setModel(new DefaultComboBoxModel(vecJCB_sub_location_3));	
			JCB_Asset_status.setSelectedItem("Active");
	
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String perpare_selected_component_query_for_main_table() {
		String condition				="";
		try{

			
				
				if (JCB_Asset_category.getSelectedItem() != null && !JCB_Asset_category.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String Asset_category = JCB_Asset_category.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(Asset_category)) {

						condition += "AND Fmv.Asset_Category ='"+Asset_category+"'";
					}
				}

				if (JCB_Asset_classification.getSelectedItem() != null && !JCB_Asset_classification.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String Asset_classification = JCB_Asset_classification.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(Asset_classification)) {

						condition += "AND Fmv.Classification_Name = '"+Asset_classification+"' ";
					}
				}
//				-------------
				if (JCB_Asset_department.getSelectedItem() != null && !JCB_Asset_department.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String Asset_department = JCB_Asset_department.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(Asset_department)) {

						condition += " AND Dp.Dept_Name =	'" + Asset_department + "'	";
					}
				}

				if (JCB_Asset_sub_department.getSelectedItem() != null && !JCB_Asset_sub_department.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String Asset_sub_department = JCB_Asset_sub_department.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(Asset_sub_department)) {

						condition += "AND Sdp.Sub_Dept_Name      ='"+Asset_sub_department+"'	";
					}
				}
				
//				-------------
				
				String Asset_location= null;
				if (JCB_Asset_location.getSelectedItem() != null && !JCB_Asset_location.getSelectedItem().toString().equalsIgnoreCase("All")) {
					Asset_location = JCB_Asset_location.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(Asset_location)) {

						condition += " AND Lcm.Location_Name = '"+Asset_location+"' ";
					}
				}

				if (JCB_sub_location_1.getSelectedItem() != null && !JCB_sub_location_1.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String sub_location_1		 = JCB_sub_location_1.getSelectedItem().toString().trim();
					String sub_location_id		 = fInter.get_String ("SELECT SUB_LOC_ID FROM FA_SUB_LOCATION WHERE location_id ='"+Asset_location+"' "
															+ "and Sub_Location='"+sub_location_1+"' ");
				
					if (!"".equalsIgnoreCase(sub_location_1)) {

						condition += " and D.dept_name='" + sub_location_1 + "'";
					}
				}
				
				
				if (JCB_sub_location_2.getSelectedItem() != null && !JCB_sub_location_2.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String unit_name = JCB_sub_location_2.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(unit_name)) {

						condition += " and ed.UNIT='" + unit_name + "'";
					}
				}

				if (JCB_sub_location_3.getSelectedItem() != null && !JCB_sub_location_3.getSelectedItem().toString().equalsIgnoreCase("All")) {
					String dept_name = JCB_sub_location_3.getSelectedItem().toString().trim();
					if (!"".equalsIgnoreCase(dept_name)) {

						condition += " and D.dept_name='" + dept_name + "'";
					}
				}
				
				
				String from_date	= jdf_Acquisition_Date.getText().toString();
				String To_date		= jdf_to.getText().toString();
				
				
				
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return condition;
	}
	
	private JPanel getIconPanel(){
		if(iconPanel == null){
			iconPanel = new JPanel();
			iconPanel.setBounds(900, 90, 100,100);
			iconPanel.setOpaque(true); 
			iconPanel.setBackground(Color.WHITE);
		}
		return iconPanel;
	}
	private JLabel getJlIcon(){
		if(jlIcon == null){
			jlIcon = new JLabel();
			jlIcon.setBounds(675,20,50,30);
			jlIcon.setText("Image File");
			this.add(jlIcon, null);

		}
		return jlIcon;
	}
	
   MouseListener ml = new MouseListener(){
		
		public void mouseReleased(MouseEvent e) {}
	
		public void mousePressed(MouseEvent e) {}
	
		public void mouseExited(MouseEvent e) {}
	
		public void mouseEntered(MouseEvent e) {}
	
		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			String strCategory = table.getValueAt(table.getSelectedRow(), 0).toString();
			String strType = table.getValueAt(table.getSelectedRow(), 1).toString();
			String strUom = table.getValueAt(table.getSelectedRow(), 2).toString();

			iconDetails = new Vector();//refer MaterialTypeMasterMain.java for the code
		
			//Bug fixed during approval
			if(iconDetails.size()>0){
			if(iconDetails.get(0) != null && !"".equalsIgnoreCase(iconDetails.get(0).toString())){
				fileName = iconDetails.get(0).toString();
			}
			if(iconDetails.get(1) != null && !"".equalsIgnoreCase(iconDetails.get(1).toString())){
				fileType = iconDetails.get(0).toString();
			}
			if(iconDetails.get(2) != null){
				icon = (ImageIcon) iconDetails.get(2);
			}else{
				icon = null;
			}
			iconPanel.removeAll();
			if(icon != null){
				Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				JLabel label = new JLabel(new ImageIcon(scaledImage));
				iconPanel.removeAll();
				iconPanel.setLayout(new BorderLayout());
				iconPanel.add(label);
			}
			//end
			}
			iconPanel.validate();
			iconPanel.setVisible(true);
			iconPanel.repaint();
		}
	};
		
	public boolean validation(int mode_of_action) {
		String Status 		= "";
		if(sst_asset_register.table.getSelectedRowCount()==1) {
			int row			= sst_asset_register.table.getSelectedRow();
			int colcnt		= sst_asset_register.table.getColumnCount();
			Status			= sst_asset_register.table.getValueAt(sst_asset_register.table.getSelectedRow(),sst_asset_register.table.getColumnModel().getColumnIndex("Category"))+"";
			
			if(mode_of_action == 1) {//modify
				if(Status.contains("in approval")) {
					outerFrm.showMsg("Asset is in Approval cannot be modified");
					return false;
				}
				Vector check= new Vector();
				if(check != null) {// add the query and data field fo the changes
					outerFrm.showMsg("Asset linked in dependant approval - Cannot be modified");
					return false;

				}
				
			}else if(mode_of_action == 3){//delete
				if(Status.contains("in approval")) {
					outerFrm.showMsg("Asset is in Approval cannot be deleted");
					return false;
				}
			}
		}else{
			outerFrm.showMsg("please select atleast one row to "+mode_of_action);

		}

		return false;
	}
	
	public Vector sub_location_flexi_condition(boolean content_set,boolean column_head) {
 		
 		String is1_enable;
 		String is2_enable;
 		String is3_enable;
 		Vector flexi_Column_heading 	= new Vector();
 		try {
 			 is1_enable					= fInter.get_String("select value from FA_ASSET_CONFIGURATION where property='Sub Location 1 Enable'");
			 is2_enable					= fInter.get_String("select value from FA_ASSET_CONFIGURATION where property='Sub Location 2 Enable'");
			 is3_enable					= fInter.get_String("select value from FA_ASSET_CONFIGURATION where property='Sub Location 3 Enable'");
 		
			 
			 if(content_set== true) {

				 if(!is1_enable.equalsIgnoreCase("Y")) {
						jl_sub_location_1.setVisible(false);
						jl_sub_location_2.setVisible(false);
						jl_sub_location_3.setVisible(false);
						JCB_sub_location_1.setVisible(false);
						JCB_sub_location_2.setVisible(false);
						JCB_sub_location_3.setVisible(false);
						
	 			}else if(!is2_enable.equalsIgnoreCase("Y")) {
	 				
	 					jl_sub_location_2.setVisible(false);
	 					jl_sub_location_3.setVisible(false);
						JCB_sub_location_2.setVisible(false);
						JCB_sub_location_3.setVisible(false);

	 			}else if(!is3_enable.equalsIgnoreCase("Y")) {
		 				
	 					jl_sub_location_3.setVisible(false);
						JCB_sub_location_3.setVisible(false);

	 			} 
			 }
			if(column_head== true) {
				if(is3_enable.equalsIgnoreCase("Y")) {
	 				flexi_Column_heading.addElement("Sub-Location1");
	 				flexi_Column_heading.addElement("Sub-Location2");
	 				flexi_Column_heading.addElement("Sub-Location3");
	 				
	 				
				}else if(is2_enable.equalsIgnoreCase("Y")) {
	 				flexi_Column_heading.addElement("Sub-Location1");
	 				flexi_Column_heading.addElement("Sub-Location2");
	 				
				}else if( is1_enable.equalsIgnoreCase("Y")){
	 				flexi_Column_heading.addElement("Sub-Location1");
				}
			}
				
			
 		}catch (Exception e) {
 			e.printStackTrace();
		}finally {
			is1_enable= null;
			is2_enable= null;
			is3_enable= null;
		}
		return flexi_Column_heading;
 	}
	
	public Vector load_sub_locations_(String sub_location) {// this is to load the sub location combo box in the selection request
		
		 Vector data_load 					= new Vector();
		 String Selected_sub_location		= null;
		 String Loc_Type					= null;
		
		
			if(sub_location.equals("1")) {
				Selected_sub_location		= JCB_sub_location_1.getSelectedItem()== null ? "":JCB_sub_location_1.getSelectedItem().toString();
				Loc_Type					= "1";
			}
			else if (sub_location.equals("2")) {
				Selected_sub_location		= JCB_sub_location_2.getSelectedItem()== null ? "":JCB_sub_location_2.getSelectedItem().toString();
				Loc_Type					= "2";
			}
		try {
			Vector stockRefVec_from_sub_loc			= fInter.LoadVectorContents("Select Regexp_Substr(  (Select Stock_Ref From FA_SUB_LOCATION Where SUB_LOC_ID In(Select SUB_LOC_ID "
															+ "From FA_SUB_LOCATION Where Location_Id In(Select Location_Id From FA_LOCATION_MASTER Where Location_Name"
															+ " ='"+JCB_Asset_location.getSelectedItem().toString()+"' ) And sub_Loc_Type='"+Loc_Type+"' And Sub_Location"
															+ " ='"+Selected_sub_location+"')   ), '[^:]+', 1, level) AS parts FROM DUAL Connect By Regexp_Substr( "
															+ " (SELECT STOCK_REF FROM FA_SUB_LOCATION WHERE SUB_LOC_ID in(Select SUB_LOC_ID From FA_SUB_LOCATION Where"
															+ " Location_Id In(select Location_Id from FA_LOCATION_MASTER where Location_Name"
															+ " ='"+JCB_Asset_location.getSelectedItem().toString()+"' ) and sub_Loc_Type='"+Loc_Type+"' and Sub_Location"
															+ "='"+Selected_sub_location+"' )   ), '[^:]+', 1, level) IS NOT NULL  ");
		
			if(stockRefVec_from_sub_loc!=null && stockRefVec_from_sub_loc.size()==3) {
				
				
				if(sub_location.equals("1")) {
					if (stockRefVec_from_sub_loc.get(0) != null && !stockRefVec_from_sub_loc.get(0).toString().equalsIgnoreCase("R0")) {
						
						Vector loadsub_vect_1		= fInter.LoadVectorwithContents("Select Sub_Location,Stock_Ref From FA_SUB_LOCATION Where Location_Id"
															+ " In(Select Location_Id From FA_LOCATION_MASTER Where Location_Name ="
															+ " '"+JCB_Asset_location.getSelectedItem().toString()+"' ) And sub_Loc_Type='2'  ");
						
						
						for(int i=0;i< loadsub_vect_1.size();i++) {
							Vector temp				= (Vector)loadsub_vect_1.get(i);
							String Sub_Location		= temp.get(0).toString();
							String Stock_Ref		= temp.get(1).toString();
							
							if(Stock_Ref.contains(stockRefVec_from_sub_loc.get(0).toString())) {
								data_load.add(Sub_Location);
							}
						}
					}
				}else if(sub_location.equals("2")) {
					
					if (stockRefVec_from_sub_loc.get(1) != null && !stockRefVec_from_sub_loc.get(0).toString().equalsIgnoreCase("L0")) {
						
						
						Vector loadsub_vect_1		= fInter.LoadVectorwithContents("Select Sub_Location,Stock_Ref From FA_SUB_LOCATION Where Location_Id"
															+ " In(Select Location_Id From FA_LOCATION_MASTER Where Location_Name ="
															+ " '"+JCB_Asset_location.getSelectedItem().toString()+"' ) And sub_Loc_Type='3'  ");
						
						
						for(int i=0;i< loadsub_vect_1.size();i++) {
							Vector temp				= (Vector)loadsub_vect_1.get(i);
							String Sub_Location		= temp.get(0).toString();
							String Stock_Ref		= temp.get(1).toString();
							
							if(Stock_Ref.contains(stockRefVec_from_sub_loc.get(0).toString()) 
									&& Stock_Ref.contains(stockRefVec_from_sub_loc.get(1).toString())) {
								data_load.add(Sub_Location);
							}
						}
					}
				}
				

				
			}
			
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		return data_load;
	}
	
	public boolean is_filters_enabled( ){
	
	try {
		String condition	="";
			
			if (JCB_Asset_category.getSelectedItem() != null && !JCB_Asset_category.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String Asset_category = JCB_Asset_category.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(Asset_category)) {

					condition += "AND Fmv.Asset_Category ='"+Asset_category+"'";
				}
			}

			if (JCB_Asset_classification.getSelectedItem() != null && !JCB_Asset_classification.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String Asset_classification = JCB_Asset_classification.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(Asset_classification)) {

					condition += "AND Fmv.Classification_Name = '"+Asset_classification+"' ";
				}
			}
//			-------------
			if (JCB_Asset_department.getSelectedItem() != null && !JCB_Asset_department.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String Asset_department = JCB_Asset_department.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(Asset_department)) {

					condition += " AND Dp.Dept_Name =	'" + Asset_department + "'	";
				}
			}

			if (JCB_Asset_sub_department.getSelectedItem() != null && !JCB_Asset_sub_department.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String Asset_sub_department = JCB_Asset_sub_department.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(Asset_sub_department)) {

					condition += "AND Sdp.Sub_Dept_Name      ='"+Asset_sub_department+"'	";
				}
			}
			
//			-------------
			
			String Asset_location= null;
			if (JCB_Asset_location.getSelectedItem() != null && !JCB_Asset_location.getSelectedItem().toString().equalsIgnoreCase("All")) {
				Asset_location = JCB_Asset_location.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(Asset_location)) {

					condition += " AND Lcm.Location_Name = '"+Asset_location+"' ";
				}
			}

			if (JCB_sub_location_1.getSelectedItem() != null && !JCB_sub_location_1.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String sub_location_1		 = JCB_sub_location_1.getSelectedItem().toString().trim();
				String sub_location_id		 = fInter.get_String ("SELECT SUB_LOC_ID FROM FA_SUB_LOCATION WHERE location_id ='"+Asset_location+"' "
														+ "and Sub_Location='"+sub_location_1+"' ");
			
				if (!"".equalsIgnoreCase(sub_location_1)) {

					condition += " and D.dept_name='" + sub_location_1 + "'";
				}
			}
			
			
			if (JCB_sub_location_2.getSelectedItem() != null && !JCB_sub_location_2.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String unit_name = JCB_sub_location_2.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(unit_name)) {

					condition += " and ed.UNIT='" + unit_name + "'";
				}
			}

			if (JCB_sub_location_3.getSelectedItem() != null && !JCB_sub_location_3.getSelectedItem().toString().equalsIgnoreCase("All")) {
				String dept_name = JCB_sub_location_3.getSelectedItem().toString().trim();
				if (!"".equalsIgnoreCase(dept_name)) {

					condition += " and D.dept_name='" + dept_name + "'";
				}
			}
			
			
			String from_date	= jdf_Acquisition_Date.getText().toString();
			String To_date		= jdf_to.getText().toString();
			
			
			
		}catch (Exception e2) {
			e2.printStackTrace();
		}
			
		return true;
	}
	
	
/* ****************************************
 * field declaration 
 * ****************************************
 */	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == jmi_add) {
			int mode =	0;
			astaddmod_alltab= new AssetRegister_allframe_home(outerFrm, innerPanel, parent, fInter,THIS, mode);
			astaddmod_alltab.setName("add_or_mod_Asset_Register");
			parent.innerPanel.getWindow(allnode).add(astaddmod_alltab);
			astaddmod_alltab.setVisible(true);
		}
		
		if (e.getSource() == jmi_modify) {
			selected_Asset_id();
			int mode =	1;
			astaddmod_alltab= new AssetRegister_allframe_home(outerFrm, innerPanel, parent, fInter,THIS, mode);
			astaddmod_alltab.setName("add_or_mod_Asset_Register");
			parent.innerPanel.getWindow(allnode).add(astaddmod_alltab);
			astaddmod_alltab.setVisible(true);
		}
			
		if (e.getSource() == jmi_delete) {
			int mode =	2;// for deletion 
			
			if(!Validate_for_deletion()) {
				return;
			}
			
			
		}
		
		if (e.getSource() == jmi_refresh) {
			refresh();
		}
		
		if (e.getSource() == jmi_view_docmt) {
			ast_doc_viewpopup=	new Asset_register_DocumentViewPopup(outerFrm,innerPanel,parent,fInter,THIS);
			ast_doc_viewpopup.setName("ast_doc_view_popup");
			ast_doc_viewpopup.setTitle("Document Upload Popup");
			ast_doc_viewpopup.setVisible(true);
		}
		
		
		if (e.getSource() == jmi_grn_import) {
			int mode =	0;
			Asstimptandcode= new AssetImport_and_AssetCode(outerFrm, innerPanel, parent, fInter, THIS, mode);
			Asstimptandcode.setName("import Asset");
			parent.innerPanel.getWindow(allnode).add(Asstimptandcode);
			Asstimptandcode.setVisible(true);
		}
		
	}
	
	public void selected_Asset_id() {
		try {
			int selRow					=  sst_asset_register.table.getSelectedRow();
			String sel_Asset_id			= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Asset ID")).toString());
			String trans_Status       	= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Approval Status")).toString());

			String sel_Type				= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Asset Type")).toString());
			String sel_category			= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Asset Category")).toString());
			String sel_clasification	= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Classification")).toString());
			String sel_Mat_No			= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("MAT No")).toString());
			String sel_Batch_id			= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Batch id")).toString());
			String sel_Grn_No			= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Grn No")).toString());
			String sel_Asset_Code		= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Asset Code")).toString());
			
			Asset_id					= Integer.parseInt(sel_Asset_id);
			boolean is_main	 			= false;
			if ( !(trans_Status.equalsIgnoreCase("Add approval")|| trans_Status.equalsIgnoreCase("Incomplete")) ) {
				is_main			= true ; 
			}
			
			Vector Data_prepare_mod_View		= new Vector();	
			Data_prepare_mod_View				= fInter.LoadVectorwithContents("SELECT MAT.MAT_NO ,  MAT.\"Specs\",  MAT.SPEC_NO,  MAT.REF_NO,  GRN.GRN_NO,  "
													+ "GRN.BATCH_NO1,  GRN.BATCH_NO2,  GRN.BATCH_NO3,  CPM.PRICE_PER_UOM AS PURCHASERATE,  GRN.BATCH_ID AS "
													+ "GRN_BATCH_ID,  GRN.PO_NO, GRN.RECEIPT_ID AS GRN_RECEIPT_ID FROM (SELECT FMV.MAT_NO, FMV.ASSET_CATEGORY,"
													+ " FMV.CLASSIFICATION_ID,    FMV.CLASSIFICATION_NAME, FMV.\"Category\",    FMV.\"type\", FMV.\"Specs\", "
													+ "FMV.COLOR, FMV.SPEC_NO, FMV.UOM1, FMV.REF_NO  FROM FIXED_MATERIAL_VIEW FMV  )MAT LEFT JOIN  (SELECT"
													+ " BT.MAT_NO,    BT.BATCH_NO1, BT.BATCH_NO2, BT.BATCH_NO3, RC.GRN_NO, BT.BATCH_ID,RC.PO_NO,STK.QTY1, "
													+ "STK.RATE, RC.RECEIPT_ID, RC.STOCK_ROOM_ID  FROM RECEIPT RC, RECEIPT_BATCH RB, BATCH BT, STOCK STK "
													+ "WHERE BT.BATCH_ID = RB.BATCH_ID  AND RB.BATCH_ID =STK.BATCH_ID  AND RC.RECEIPT_ID = STK.RECEIPT_ID  "
													+ "AND RB.RECEIPT_ID = RC.RECEIPT_ID  AND RC.TYPE IN (SELECT DISTINCT TYPE FROM MATERIAL_VIEW WHERE "
													+ "CATEGORY='fixed' )  AND BT.PO_NO=RC.PO_NO )GRN ON GRN.MAT_NO=MAT.MAT_NO LEFT JOIN (SELECT CPM.PO_NO,"
													+ "CPM.RATE, PI.PRICE_PER_UOM, CPM.CURRENCY  FROM COMPANY_PURCHASES_MAT CPM , PO_INFO PI  WHERE PI.PO_NO"
													+ "=CPM.PO_NO  )CPM ON GRN.PO_NO =CPM.PO_NO WHERE GRN.GRN_NO IS NOT NULL AND Mat.\"type\" ='"+sel_Type+"' "
													+ "AND MAT.ASSET_CATEGORY = '"+sel_category+"'AND MAT.CLASSIFICATION_NAME ='"+sel_clasification+"'"
													+ " AND mat.MAT_NO ="+sel_Mat_No+" AND GRN.BATCH_ID ="+sel_Batch_id+" AND GRN.GRN_NO ="+sel_Grn_No+"");
			
			Asset_register_pojo						= new Asset_Register_AST_DETAILS[Data_prepare_mod_View.size()];


			for(int i= 0;i<Data_prepare_mod_View.size();i++) {
				Vector tempData_prepare_mod_View		= (Vector)Data_prepare_mod_View.get(i);
				Asset_register_pojo[i]					= new Asset_Register_AST_DETAILS();
				Vector category_type					= fInter.LoadVectorContents("Select Asset_Category,\"type\",Classification_Name  "
																+ "From  Fixed_Material_View Where Mat_No="+sel_Mat_No+"");
				Asset_register_pojo[i].Asset_category	= category_type.get(0)==null ? "" :category_type.get(0).toString();
				Asset_register_pojo[i].Asset_Type		= category_type.get(1)==null ? "" :category_type.get(1).toString();
				Asset_register_pojo[i].Classification	= category_type.get(2)==null ? "" :category_type.get(2).toString();
		
				
				Asset_register_pojo[i].MAT_No			= Integer.parseInt(	tempData_prepare_mod_View.get(0).toString() );
				Asset_register_pojo[i].Specification	= tempData_prepare_mod_View.get(1)==null?"":tempData_prepare_mod_View.get(1).toString();
				Asset_register_pojo[i].SPEC_no			= tempData_prepare_mod_View.get(2)==null?"":tempData_prepare_mod_View.get(2).toString();
				Asset_register_pojo[i].REF_no			= tempData_prepare_mod_View.get(3)==null?"":tempData_prepare_mod_View.get(3).toString();
				Asset_register_pojo[i].GRN_no			= Integer.parseInt(	tempData_prepare_mod_View.get(4).toString() );
				Asset_register_pojo[i].Batch_id			= Integer.parseInt( tempData_prepare_mod_View.get(9)==null?"":tempData_prepare_mod_View.get(9).toString() );
				Asset_register_pojo[i].Batch_no1		= tempData_prepare_mod_View.get(5)==null?"":tempData_prepare_mod_View.get(5).toString();
				Asset_register_pojo[i].Batch_no2		= tempData_prepare_mod_View.get(6)==null?"":tempData_prepare_mod_View.get(6).toString();
				Asset_register_pojo[i].Batch_no3		= tempData_prepare_mod_View.get(7)==null?"":tempData_prepare_mod_View.get(7).toString();
				
				Asset_register_pojo[i].Purchase_Value   = tempData_prepare_mod_View.get(8)==null?"":tempData_prepare_mod_View.get(8).toString();
				Asset_register_pojo[i].PO_no			= Integer.parseInt(	tempData_prepare_mod_View.get(10).toString());
			
				Asset_register_pojo[i].Asset_Code		= sel_Asset_Code	==null ?"": sel_Asset_Code;
				
			}
			
			Vector Image_vect			= fInter.getAssetImageIcon(Asset_id+"",is_main);
			
			Asset_register_Image_pojo 	= new Asset_register_Image[Image_vect.size()];
			
			for(int i =0;i<Image_vect.size();i++) {
				Asset_register_Image_pojo[i]					= new Asset_register_Image();
				Vector tempImage_vect							= (Vector)Image_vect.get(i);
				Asset_register_Image_pojo[i].Image_Title		= tempImage_vect.get(0)==null?"":tempImage_vect.get(0).toString() ;
				Asset_register_Image_pojo[i].Image_formatext	= tempImage_vect.get(1)==null?"":tempImage_vect.get(1).toString() ;
			 	Asset_register_Image_pojo[i].Imagedata_byte		= (byte[])tempImage_vect.get(2);
				Asset_register_Image_pojo[i].Image_no  			= Integer.parseInt( tempImage_vect.get(3)==null?"":tempImage_vect.get(3).toString() );
			}
			ImageIcon imageIcon = new ImageIcon(Asset_register_Image_pojo[0].Imagedata_byte);
			jlIcon.setIcon(imageIcon);
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	 
	
	public boolean Validate_for_deletion() {
	
		int selRow					=  sst_asset_register.table.getSelectedRow();
		String sel_Asset_id			= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Asset ID")).toString());
		String trans_Status       	= (sst_asset_register.table.getValueAt(selRow, sst_asset_register.table.getColumnModel().getColumnIndex("Approval Status")).toString());
		try {
			if( trans_Status.equalsIgnoreCase("Add in Approval")||trans_Status.equalsIgnoreCase("Mod in Approval")||
					trans_Status.equalsIgnoreCase("Del in Approval")) {
				outerFrm.showMsg("Selected Asset id-"+sel_Asset_id+" is in "+ trans_Status+ ",So can be deleted ");
				return false;
			}
			
			Vector is_used_in_Cwip	= fInter.LoadVectorContents("select cwip_id from fa_cwip where asset_id="+sel_Asset_id+"");
			
			if(!is_used_in_Cwip.isEmpty() && is_used_in_Cwip.size()> 0) {
				outerFrm.showMsg("Selected Asset id -"+sel_Asset_id+"is used in CWIP");
				return false;
			}
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
//	public void delete_actio() {
//		try{
//			
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
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


	
}
