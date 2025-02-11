package HarnessErp;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import common.gui.SortSelectTable;

/*
 * Created on Dec 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author bharathi
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AssetDetail extends JPanel implements ListSelectionListener{

	
	/**
	 * @param Inter
	 * @param THIS
	 * @param name
	 */
	
/*	private javax.swing.JScrollPane jsp_ledger_group = null;
	private javax.swing.JTable jtable_ledger_group = null;*/
	
	private SortSelectTable sst_ledger_group=null;
	
	private javax.swing.JButton jb_back = null;
	FinanceInterface Inter = null;
	BalancesheetPanel balance  = null;
	String groupname = null;
	ListSelectionModel LstModel=null;
	String ledgername = "";
	ProfitLossPanel profit = null;
	String name=null;
	AssetDetail THIS = null;
	private double totamount=0;
	private double totamount1=0;
	private double totamount2=0;
	
	int mode=0;
	private JLabel jl_total = null;
	private JLabel jl_total1 = null;
	private JLabel jl_total2 = null;
	private JLabel jl_minus = null;
	TrialBalancePanel trial = null;
	/**
	 * This is the default constructor
	 */
	public AssetDetail(FinanceInterface Inter,BalancesheetPanel balance,String groupname) {
		super();
		this.Inter = Inter;
		this.balance = balance;
		this.groupname = groupname;	
		this.THIS  = this;
		mode=1;
		initialize();
	}
	
	public AssetDetail(FinanceInterface Inter, ProfitLossPanel profit, String name) {
		super();
		
		this.Inter = Inter;
		this.profit = profit;
		this.name = name;	
		this.THIS  = this;
		mode=3;
		initialize();
			// TODO Auto-generated constructor stub
		}
	public AssetDetail(FinanceInterface Inter, TrialBalancePanel trial, String name) {
		super();
		
		this.Inter = Inter;
		this.trial = trial;
		this.name = name;	
		this.THIS  = this;
		mode=10;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jl_minus = new JLabel();
		jl_minus.setBounds(new java.awt.Rectangle(346,218,27,15));
		jl_minus.setText("-");
		jl_total2 = new JLabel();
		jl_total2.setBounds(new java.awt.Rectangle(492,212,109,27));
		jl_total2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		jl_total2.setText("JLabel");
		jl_total1 = new JLabel();
		jl_total1.setBounds(new java.awt.Rectangle(377,214,101,25));
		jl_total1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		jl_total1.setText("JLabel");
		jl_total = new JLabel();
		jl_total.setBounds(new java.awt.Rectangle(237,214,105,25));
		jl_total.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		jl_total.setText("JLabel");
		this.setLayout(null);
	//	this.add(getJsp_ledger_group(), null);
		this.add(getJtable_ledger_group(), null);
		this.add(getJb_back(), null);
		this.setSize(733, 266);
		this.setName("Ledger details");
		this.setLayout(null);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.ORANGE,2));
		this.add(jl_total, null);
		this.add(jl_total1, null);
		this.add(jl_total2, null);
		this.add(jl_minus, null);
		jl_total.setText(totamount+"");
		jl_total1.setText(totamount1+"");
		jl_total2.setText(totamount2+"");
	}
	/**
	 * This method initializes jtable_ledger_group
	 * 
	 * @return javax.swing.JTable
	 */
	private SortSelectTable getJtable_ledger_group() {
		if(sst_ledger_group == null) {
			sst_ledger_group = new SortSelectTable();
			sst_ledger_group.setBounds(99, 29, 524, 168);
			sst_ledger_group.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(sst_ledger_group.table.getSelectedRowCount()>0){
						if(e.getKeyChar()==KeyEvent.VK_ENTER){
							ledgername=sst_ledger_group.table.getValueAt(sst_ledger_group.table.getSelectedRow(),0).toString();
							Vector data1=new Vector();
							try {
								data1 = Inter.getledgernameforassets(ledgername);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(data1.size()>0){
								SubGroupDetails sub = new SubGroupDetails(Inter,THIS,data1,mode);
								if(mode==1)
									balance.parent.setJPanel(sub);
								if(mode==3)
									profit.parent.setJPanel(sub);
								if(mode==10)
									trial.Parent.setJPanel(sub);	
							}
							else{
								LedgerParticulars ledger = new LedgerParticulars(Inter,ledgername,THIS,mode);
								ledger.jc_ledger_name.setEnabled(false);
								if(mode==1)
									balance.parent.setJPanel(ledger);
								if(mode==3)
									profit.parent.setJPanel(ledger);
								if(mode==10)
									trial.Parent.setJPanel(ledger);	
							}
									
						}
					}	
				}
			});
			FillTable();
		}
		return sst_ledger_group;
	}
	/**
	 * This method initializes jsp_ledger_group
	 * 
	 * @return javax.swing.JScrollPane
	 *//*
	private javax.swing.JScrollPane getJsp_ledger_group() {
		if(jsp_ledger_group == null) {
			jsp_ledger_group = new javax.swing.JScrollPane();
			jsp_ledger_group.setBounds(99, 29, 524, 168);
			jsp_ledger_group.setViewportView(getJtable_ledger_group());
		}
		return jsp_ledger_group;
	}*/
	/**
	 * This method initializes jb_back
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJb_back() {
		if(jb_back == null) {
			ImageIcon Icon = new ImageIcon("arrow_leftlast.png");
			jb_back = new javax.swing.JButton(Icon);
			//jb_back = new javax.swing.JButton();
			jb_back.setBounds(106, 223, 43, 22);
			//jb_back.setText("Back");
			jb_back.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				if(mode==3){profit.parent.setJPanel(profit);
				profit.FillTable();
				}
				else if(mode==10){
					trial.Parent.setJPanel(trial);
					trial.FillTable();}
				else{ balance.parent.setJPanel(balance);
				balance.FillAssetsTable();
				balance.FillLiabilitiesTable();
				}
					
				}
			});
			
		}
		return jb_back;
	}
	
	public void FillTable(){
		Vector head = new Vector();
		head.add("Ledger Name");
		head.add("Debit Amount");
		head.add("Credit Amount");
		head.add("Closing Balance");
		Vector data = new Vector();
		try {
			
			if(mode==1)
				data = Inter.getLedgersForAssets(groupname);
			else if(mode==3 || mode==10)
				data = Inter.getLedgersForAssets(name);
		//	sst_ledger_group.table.setModel(new DefaultTableModel(data,head));
			sst_ledger_group.setTable(data, head, "Order_Planning", "", false);

			LstModel = sst_ledger_group.table.getSelectionModel();
			LstModel.addListSelectionListener(this);
			sst_ledger_group.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			sst_ledger_group.repaint();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		settotalAmount(data);
		
	}
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel Lst=(ListSelectionModel) e.getSource();
		Vector data1=new Vector();
		if(Lst==LstModel){
			if(e.getValueIsAdjusting()){
				if(sst_ledger_group.table.getSelectedRowCount()>0){
					ledgername=sst_ledger_group.table.getValueAt(sst_ledger_group.table.getSelectedRow(),0).toString();
					try {
						data1 = Inter.getledgernameforassets(ledgername);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(data1.size()>0){
						SubGroupDetails sub = new SubGroupDetails(Inter,this,data1,mode);
						if(mode==1)
							balance.parent.setJPanel(sub);
						else if(mode==3)
							profit.parent.setJPanel(sub);
						else if(mode==10)
							trial.Parent.setJPanel(sub);
					}
					else{
						LedgerParticulars ledger = new LedgerParticulars(Inter,ledgername,this,mode);
						ledger.jc_ledger_name.setEnabled(false);
						if(mode==1)
							balance.parent.setJPanel(ledger);
						if(mode==3)
							profit.parent.setJPanel(ledger);
						if(mode==10)
							trial.Parent.setJPanel(ledger);
					}
					
					
					/*else
					profit.parent.setJPanel(ledger);*/
					
					
					
				}
				
			}
		}
	}
	
	public class MyModel extends DefaultTableModel
	{
		
		MyModel(Vector data,Vector head)
		{		
			super(data,head);		
		
		}
		public boolean isCellEditable(int row,int cols)
		{			
			return false;
		
		}
	
	}
	
	private void settotalAmount(Vector data1) {
		double amount=0;
		double amount1 = 0;
		double amount2=0;
		for(int i=0;i<data1.size();i++){
			amount+=Double.parseDouble(((Vector)data1.get(i)).get(1).toString() );
			totamount=amount;
			amount1+=Double.parseDouble(((Vector)data1.get(i)).get(2).toString() );
			totamount1=amount1;
			totamount2=amount-amount1;
		}
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="45,-5"
