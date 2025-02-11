/**
 * 
 * @author Veera Karthikeyan A
 * Added for requirement#- Fixed Asset	---JAN-2024
 *
 **/

package HarnessErp;

import java.awt.Component;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import common.Asset_Register_AST_DETAILS;
import common.Asset_register_Fields_and_Flexis;
import common.Asset_register_Image;
import common.Design;
import common.GUIDiffInterface;
import common.GUIDifference;
import common.Streamline_Connection1;
import common.gui.InnerPanel;
import common.gui.OuterFrame;

public class AssetRegister_allframe_home  extends 
JInternalFrame implements InternalFrameListener, GUIDiffInterface{
	
	OuterFrame outerFrm								= null;
	InnerPanel innerPanel 							= null;
	FinanceController fincon						= null;
	FinanceInterface fInter 						= null;
	AssetRegister_main parent						= null;
	AssetRegister_allframe_home THIS				= null;
	
	private JTabbedPane all_tab_main				= null;
	private JPanel jContentPane 					= null;

	public AstRegt_Asset_details_addmod Asst_dtl_addmod	= null; /* ASSET REGISTER ASSET DETAILS */
	public AstRegt_Transfer_History_tab Asst_Hist_tranz	= null; /* ASSET TRANSFER HISTORY */

	int mode	= 0;

	
	ApprovalController appController 			= null;
	ApprovalInterface aInter 					= null;
	ApproverPanel aPanel 						= null;
	ApproverPanel approverPanel_flag1 			= null;
	InitiatedPanel initiatedPanel_flag3 		= null;
	JPanel Jpanel								= null;
	String ipAddress							= null;
	int trans_id 								= 0;
	int approverId 								= 0;
	int handler_mode 							= 0;
	String trans_name							= "";
	Vector transaction_object					= new Vector();
	int appFlag 								= -1;
	
	
	Asset_Register_AST_DETAILS[] Asset_register_pojo		  		= null;
	Asset_register_Fields_and_Flexis[] Asset_reg_fieldflexi_pojo	= null;
	Asset_register_Image[] Asset_register_Image_pojo				= null;
	Vector all_pojo= new Vector();
	
	public AssetRegister_allframe_home(OuterFrame outerFrm,InnerPanel innerPanel,FinanceController fincon,FinanceInterface fInter,AssetRegister_main parent,int Mode) {
		super();
		this.parent		= parent;
		this.fincon		= fincon;
		this.fInter		= fInter;
		this.outerFrm	= outerFrm;
		this.THIS		= this;
		this.mode		= Mode;

		if(mode==1) {
			Asset_register_pojo			= parent.Asset_register_pojo;
			Asset_register_Image_pojo	= parent.Asset_register_Image_pojo;
		}
		initialize();
		addInternalFrameListener(this);
		addListeners();

		
	}
	
	/**
	 * this constructor is used while approval
	 * */	
		public  AssetRegister_allframe_home (String ipAddr) {
			
		}
		
	/**
	 * this constructor is used in approval action and this calls the 
	 * */	

		
		public  AssetRegister_allframe_home (final int trans_id, int approverId, Vector transObj, String ipAddress,
				int appFlag, JPanel Jpanel, OuterFrame parent, ApprovalController appcontroler){
		
			super();
			
			this.trans_id 				= trans_id;
			this.approverId				= approverId;
			this.transaction_object		= transObj;
			this.handler_mode			= appFlag;
			this.outerFrm	 			= parent;
			this.appController 			= appcontroler;
			this.innerPanel 			= appController.innerPanel;;
	  		this.aPanel					= appController.app.appPanel;
			this.appFlag 				= appFlag;
			this.THIS 					= this;
			this.ipAddress				= ipAddress;
			
			try {
				this.aInter 			= (ApprovalInterface) Naming.lookup("rmi://" + ipAddress + ":1099/" + parent.company_id + "_" + "ApprovalServer");
				this.fInter 			= (FinanceInterface) Naming.lookup("rmi://" + ipAddress + ":1099/" + parent.company_id + "_" + "FinanceServer");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			
			if (trans_name.equalsIgnoreCase("AddAsset")) {
				mode=0;
			}else if(trans_name.equalsIgnoreCase("ModAsset")) {
				mode=1;
			}else if(trans_name.equalsIgnoreCase("DelAsset")) {
				mode=2;
			}
			
			initialize();
			new Design("Order_Planning").setColors(this);
		}
		
	private void initialize() {
		this.setLayout(null);
		this.setContentPane(getJContentPane());
		this.setSize(1100, 600);
		new Design("Order_Planning").setColors(this);
		addInternalFrameListener(this);

	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			getTabbedPane();
			GroupLayout Lay1 = new GroupLayout(jContentPane);
			jContentPane.setLayout(Lay1);
			Lay1.setAutoCreateContainerGaps(true);
			Lay1.setAutoCreateGaps(true);

			Lay1.setHorizontalGroup(
					Lay1.createSequentialGroup().addGroup(Lay1.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGap(0).addComponent(all_tab_main, 1500, 1500, 1500)));
			Lay1.setVerticalGroup(Lay1.createSequentialGroup()
					.addGroup(Lay1.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(all_tab_main)));
			setContentPane(jContentPane);
			this.pack();
			jContentPane.setVisible(true);
			
			}
		return jContentPane;

	}
	
	private JTabbedPane getTabbedPane() {
		if (all_tab_main == null) {
			all_tab_main = new JTabbedPane();
			
			/** implementing if condition for the approval  data set
			 * */
			if(appFlag!=-1) {
				Asst_dtl_addmod = new AstRegt_Asset_details_addmod(trans_id,  approverId,  transaction_object,  ipAddress,
						 					appFlag,  Jpanel,  outerFrm,  appController,this);
				all_tab_main.addTab("Asset Details", Asst_dtl_addmod);
				all_tab_main.setSelectedComponent(Asst_dtl_addmod);
				Asst_dtl_addmod.ApprovalMenuBar();
			}else {
				Asst_dtl_addmod = new AstRegt_Asset_details_addmod(fInter,outerFrm,innerPanel,THIS,mode);
				Asst_Hist_tranz = new AstRegt_Transfer_History_tab(fInter,outerFrm,innerPanel,THIS,mode);
				all_tab_main.addTab("Asset Details", Asst_dtl_addmod);
				all_tab_main.addTab("Transfer History", Asst_Hist_tranz);
				
				all_tab_main.setSelectedComponent(Asst_dtl_addmod);
				Asst_dtl_addmod.createMenuBar();
			}
			
		
		}
		return all_tab_main;
	}
	
	
	private void addListeners() {
		all_tab_main.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {

				if (all_tab_main.getSelectedIndex() == 0) {
//					emp_details.createMenuBar();
				}
				
			}
		});
	}
	
	
    public Integer AddAsset_to_Asset_register(int transid, Vector transobj, Vector data_vec, String company_name, 
    		boolean flag_final_level_approval, Connection con, Streamline_Connection1 scon) {
    	
    	return (new Handler_for_Asset_Register()).AddAsset_to_Asset_register(transid, transobj, data_vec, company_name, flag_final_level_approval, con);
    			
    }

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
