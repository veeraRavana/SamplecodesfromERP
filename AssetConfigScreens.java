package HarnessErp;

import java.awt.Component;
import java.awt.Rectangle;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import common.Design;
import common.gui.OuterFrame;

public class AssetConfigScreens extends JInternalFrame implements InternalFrameListener{
private static final long serialVersionUID = 7240258030763915280L;
	
	private JPanel jContentPane;
	private JTabbedPane jTabbedPane;
	private OuterFrame outerFrame;
	private FinanceInterface fInter;
	String user_name;
	String IpAddress;
	public GeneralConfig general_config=null;
	public CWIPConfig cwip_config = null;
	public AuditAndTransferConfig auditTransConfig =null;
	public DepreciationConfig DepConfig =null;
	public DisposalAndSaleConfig DisSaleConfig =null;
	public ColumnConfig colConfig =null;
	private int currentTabIndex = 0, previousTabIndex = 0;

public AssetConfigScreens(OuterFrame outerFrame, FinanceInterface fInter,String user_name) {
	super("Asset Configuration", true, false, true, true);
	this.outerFrame = outerFrame;
	this.fInter = fInter;
	this.user_name=user_name;
	initialize();
	addInternalFrameListener(this);
	addListeners();
	new Design("Order_Planning").setColors(this);
}
private void initialize() {
	this.setSize(785, 700);
	this.setContentPane(getJContentPane());
}
private JPanel getJContentPane() {
	if (jContentPane == null) {
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		
		jContentPane.add(getJTabEndProductNames(), null);
	}
	return jContentPane;
}
@SuppressWarnings({ "unchecked", "rawtypes"})
private JTabbedPane getJTabEndProductNames() {
	if (jTabbedPane == null) {
		jTabbedPane = new JTabbedPane();
		jTabbedPane.setBounds(new Rectangle(0, 5, 1200, 700));	
		 try {
			 IpAddress = getLocalHostLANAddress();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		
		Vector financeConfigTabVector = new Vector();
		Vector LogSaveData = new Vector();
		LogSaveData.add(user_name);
		LogSaveData.add(IpAddress);
		
		financeConfigTabVector.add("General");
		financeConfigTabVector.add("CWIP");
		financeConfigTabVector.add("Audit & Transfer");
		financeConfigTabVector.add("Depreciation");
		financeConfigTabVector.add("Disposal & Sale");
		financeConfigTabVector.add("Column Config");
		
		if(financeConfigTabVector!=null && financeConfigTabVector.contains("General")) {
			jTabbedPane.addTab("General", getConfigScreens("General" , LogSaveData));
		 
		}
		if(financeConfigTabVector!=null && financeConfigTabVector.contains("CWIP")) {
			jTabbedPane.addTab("CWIP", getConfigScreens("CWIP",LogSaveData));
		 
		}
		if(financeConfigTabVector!=null && financeConfigTabVector.contains("Audit & Transfer")) {
			jTabbedPane.addTab("Audit & Transfer", getConfigScreens("Audit & Transfer",LogSaveData));
		 
		}
		if(financeConfigTabVector!=null && financeConfigTabVector.contains("Depreciation")) {
			jTabbedPane.addTab("Depreciation", getConfigScreens("Depreciation",LogSaveData));
		 
		}
		if(financeConfigTabVector!=null && financeConfigTabVector.contains("Disposal & Sale")) {
			jTabbedPane.addTab("Disposal & Sale", getConfigScreens("Disposal & Sale",LogSaveData));
		 
		}
		if(financeConfigTabVector!=null && financeConfigTabVector.contains("Column Config")) {
			jTabbedPane.addTab("Column Config", getConfigScreens("Column Config",LogSaveData));
		 
		}	 
	}
	return jTabbedPane;
}


private static String getLocalHostLANAddress() throws UnknownHostException {
   
    	Enumeration<NetworkInterface> e;
		String ipAD="";
		try {
			e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements()){
			for (InterfaceAddress f : e.nextElement().getInterfaceAddresses()) {
			if(!f.getAddress().isLoopbackAddress())	{
				if (f.getAddress().isSiteLocalAddress()) {
					ipAD=f.getAddress().toString().substring(1);
					}}
			}
		}
		} catch (SocketException se) {
			se.printStackTrace();
		}
		return ipAD;
}


private Component getConfigScreens(String configScreenName ,Vector LogSaveData) {
	if (configScreenName.equalsIgnoreCase("General")) {
		general_config= new GeneralConfig(outerFrame, fInter , LogSaveData);
		return general_config;
	}
	if (configScreenName.equalsIgnoreCase("CWIP")) {
		cwip_config= new CWIPConfig(outerFrame, fInter,LogSaveData);
		return cwip_config;
	}
	if (configScreenName.equalsIgnoreCase("Audit & Transfer")) {
		auditTransConfig= new AuditAndTransferConfig(outerFrame, fInter,LogSaveData);
		return auditTransConfig;
	}
	if (configScreenName.equalsIgnoreCase("Depreciation")) {
		DepConfig= new DepreciationConfig(outerFrame, fInter,LogSaveData);
		return DepConfig;
	}
	if (configScreenName.equalsIgnoreCase("Disposal & Sale")) {
		DisSaleConfig= new DisposalAndSaleConfig(outerFrame, fInter,LogSaveData);
		return DisSaleConfig;
	}
	if (configScreenName.equalsIgnoreCase("Column Config")) {
		colConfig= new ColumnConfig(outerFrame, fInter,LogSaveData);
		return colConfig;
	}
	return null;
}
private void addListeners() {
	jTabbedPane.addChangeListener(new ChangeListener() {
		 @Override
      public void stateChanged(ChangeEvent c) {
			 boolean show_popup=false;
		        previousTabIndex = currentTabIndex;
             currentTabIndex = jTabbedPane.getSelectedIndex();
             jTabbedPane.removeChangeListener(this);
         	jTabbedPane.setSelectedIndex(previousTabIndex);
         	jTabbedPane.addChangeListener(this);
         	if(previousTabIndex ==0){
         		if(general_config.is_component_accesed){
         			show_popup=true;
         		}else{
         			show_popup=false;
         		}
         		
         	}
         	if(previousTabIndex ==1){
         		if(cwip_config.is_component_accesed){
         			show_popup=true;
         		}else{
         			show_popup=false;
         		}
         	}
         	
         	if(previousTabIndex ==2){
         		if(auditTransConfig.is_component_accesed){
         			show_popup=true;
         		}else{
         			show_popup=false;
         		}
         	}

         	if(previousTabIndex ==3){
         		if(DepConfig.is_component_accesed){
         			show_popup=true;
         		}else{
         			show_popup=false;
         		}

         	}
         	if(previousTabIndex ==4){
         		if(DisSaleConfig.is_component_accesed){
         			show_popup=true;
         		}else{
         			show_popup=false;
         		}

         	}
         	if(previousTabIndex ==5){
         		if(colConfig.is_component_accesed){
         			show_popup=true;
         		}else{
         			show_popup=false;
         		}

         	}
         	
         	if(show_popup){
             int result = JOptionPane.showConfirmDialog(null, "Please make sure the configuration changes get saved. Do you want to Proceed?","",JOptionPane.YES_NO_OPTION);
             if(result ==0){
             	jTabbedPane.removeChangeListener(this);
             	jTabbedPane.setSelectedIndex(currentTabIndex);
             	jTabbedPane.addChangeListener(this);
             }else{
             	jTabbedPane.removeChangeListener(this);
             	jTabbedPane.setSelectedIndex(previousTabIndex);
             	jTabbedPane.addChangeListener(this);
             	currentTabIndex=previousTabIndex;
             }
         	}else{
         		jTabbedPane.removeChangeListener(this);
             	jTabbedPane.setSelectedIndex(currentTabIndex);
             	jTabbedPane.addChangeListener(this);
             	show_popup=false;
         	}

		      }
		});

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
}
