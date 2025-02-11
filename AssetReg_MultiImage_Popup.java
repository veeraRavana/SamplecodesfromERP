package HarnessErp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Asset_Register_AST_DETAILS;
import common.Asset_register_Fields_and_Flexis;
import common.Asset_register_Image;
import common.Design;
import common.gui.InnerPanel;
import common.gui.OuterFrame;

public class AssetReg_MultiImage_Popup extends  JDialog {

	OuterFrame outerFrm								= null;
	InnerPanel innerPanel 							= null;
	FinanceController Fincon						= null;
	FinanceInterface fInter 						= null;
	AssetRegister_main Parentd						= null;
	AssetReg_MultiImage_Popup THIS					= null;
//	AstRegt_Asset_details_addmod Parent				= null;
	
	/*Parent classes */	
	AstRegt_Asset_details_addmod Asst_Details				= null;
	AssetImport_and_AssetCode  Asstimptandcode				= null;
	
	Asset_register_Image[] AST_REG_Image_pojo		= null;
	
	
	private JPanel jContentPane 					= null;
	
	
	private JButton jb_previous	 					= null;
	private JButton jB_next			 				= null;
	private JButton jb_Update	 					= null;
	private JButton jB_cancel		 				= null;
	
	private JLabel jl_upload_ic						= null;
	private JLabel jl_delete_ic						= null;	
	private JLabel jl_no_of_pic						= null;
	private JLabel jl_Image_Title					= null;
	
	private JTextField jtf_Image_title				= null;
	
	
	JPanel iconPanel 								= null;
	ImageIcon icon 									= null;
	
//	AddorModAssestResgister asttrash 				= null;
	
	LinkedHashMap<Integer,String> HM_image_path    		= new LinkedHashMap<Integer,String>();
	LinkedHashMap<Integer,String> hm_image_title		= new LinkedHashMap<Integer,String>();
	LinkedHashMap<Integer,String> hm_imagewith_formate	= new LinkedHashMap<Integer,String>();
	File file;
	
	Integer image_no								= 0;
	
	
	Asset_register_Image[] asset_register_image_pojo= null; 
	
	String Parent 									= null;
	public AssetReg_MultiImage_Popup(OuterFrame outerFrm,InnerPanel innerPanel,
			FinanceController Fincon,FinanceInterface fInter,
			Object parent ) {
		
		this.outerFrm		= outerFrm;
		this.innerPanel		= innerPanel;
		this.Fincon			= Fincon;
		this.fInter			= fInter;
//		this.Parent			= Ast_reg_detail;
		this.THIS			= this;
		
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		initialize();
		
		if(parent instanceof AssetImport_and_AssetCode) {
			this.Asstimptandcode			= (AssetImport_and_AssetCode) parent;
			Parent							= "GRN IMPORT";
			
				if(Asstimptandcode.HM_image_path_ig.size()>0) {
					try {
						HM_image_path			=  (LinkedHashMap<Integer, String>) deepCopy( Asstimptandcode.HM_image_path_ig	);// image path
						hm_image_title			=  (LinkedHashMap<Integer, String>) deepCopy( Asstimptandcode.hm_image_title_ig	);// image titles
						hm_imagewith_formate	=  (LinkedHashMap<Integer, String>) deepCopy( Asstimptandcode.HM_image_path_ig	);// image path
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				load_image(1,true,false);
				jtf_Image_title.setText( hm_image_title.get(1)==null?"":hm_image_title.get(1).toString() );
				}
			
		}else if(parent instanceof AstRegt_Asset_details_addmod) {
			this.Asst_Details					= (AstRegt_Asset_details_addmod) parent;
			Parent								= "ASSET DETAILS";
			
				if(Asst_Details.HM_image_path_ig.size()>0) {
					try {
						HM_image_path			=  (LinkedHashMap<Integer, String>) deepCopy( Asst_Details.HM_image_path_ig	);// image path
						hm_image_title			=  (LinkedHashMap<Integer, String>) deepCopy( Asst_Details.hm_image_title_ig	);// image titles
						hm_imagewith_formate	=  (LinkedHashMap<Integer, String>) deepCopy( Asst_Details.HM_image_path_ig	);// image with formate
					} catch (Exception e) {
						e.printStackTrace();
					}
				load_image(1,true,false);
				jtf_Image_title.setText( hm_image_title.get(1)==null?"":hm_image_title.get(1).toString() );
				}
	
		}
		
	}
	

	private void initialize() {
		this.setLayout(null);
		this.setSize(500,550);
		this.setLocation(250,150);
		this.setContentPane(getJContentPane());
		new Design("Order_Planning").setColors(this);

	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			jContentPane.add(getjb_previous(),null);
			jContentPane.add(getjB_next(),null);
			jContentPane.add(getjb_Update(),null);
			jContentPane.add(getjB_cancel(),null);
			
			jContentPane.add(getjl_no_of_pic(),null);
			jContentPane.add(getjl_Specification(),null);
			
			jContentPane.add(getjl_upload_ic(),null);	
			jContentPane.add(getjl_delete_ic(),null);	
			
			jContentPane.add(getjtf_Image_title(),null);
			jContentPane.add(getIconPanel(),null);
			
	
		}
		return jContentPane;

	}
	
	private JLabel getjl_no_of_pic() {
		if (jl_no_of_pic == null) {
			jl_no_of_pic = new JLabel();
			jl_no_of_pic.setText("");
			jl_no_of_pic.setName("Asset_jl_no_of_pic");
			jl_no_of_pic.setBounds(new Rectangle(215, 350, 100, 30));

		}
		return jl_no_of_pic;
	}
	
	private JLabel getjl_Specification() {
		if (jl_Image_Title == null) {
			jl_Image_Title = new JLabel();
			jl_Image_Title.setText("Image Title");
			jl_Image_Title.setName("Asset_jl_Image_Title");
			jl_Image_Title.setBounds(new Rectangle(95, 400, 100, 30));

		}
		return jl_Image_Title;
	}
	
    private JTextField getjtf_Image_title() {

			if (jtf_Image_title == null) {
				jtf_Image_title = new JTextField();
				jtf_Image_title.setName("Asset_jtfAsset_Code");
				jtf_Image_title.setBounds(160, 400, 175, 80);
				this.add(jtf_Image_title, null);

			}
			return jtf_Image_title;
		}
	 
	private JButton getjb_previous() {
		if (jb_previous == null) {
			jb_previous = new JButton();
			jb_previous.setBounds(new Rectangle(125,350,75,24));
			jb_previous.setText("Previous");
			jb_previous.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						previous_image();

					} catch (Exception e1) {
						e1.printStackTrace();
					}
	        		
				}
			});
		}
		return jb_previous;
	}
	
	private JButton getjB_next() {
		if (jB_next == null) {
			jB_next = new JButton();
			jB_next.setBounds(new Rectangle(300,350,75,24));
			jB_next.setText("Next");
			jB_next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						next_image();

					} catch (Exception e1) {
						e1.printStackTrace();
					}
	        		
				}
			});
		}
		return jB_next;
	}

	private JButton getjb_Update() {
		if (jb_Update == null) {
			jb_Update = new JButton();
			jb_Update.setBounds(new Rectangle(95,450,106,24));
			jb_Update.setText("Update");
			jb_Update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						if(hm_image_title.get(image_no)!=null || hm_image_title.get(image_no).isEmpty()) {
				 			hm_image_title.remove(image_no);
				 			hm_image_title.put(image_no,jtf_Image_title.getText().toString());
				 		}
						
						prepare_image();
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	        		
				}
			});
		}
		return jb_Update;
	}

	private JButton getjB_cancel() {
		if (jB_cancel == null) {
			jB_cancel = new JButton();
			jB_cancel.setBounds(new Rectangle(285,450,106,24));
			jB_cancel.setText("Cancel");
			jB_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						dispose();

					} catch (Exception e1) {
						e1.printStackTrace();
					}
	        		
				}
			});
		}
		return jB_cancel;
	}
	
public void prepare_image() {
	try{

		asset_register_image_pojo			= new Asset_register_Image[HM_image_path.size()];

		for(int i=0;i<HM_image_path.size();i++) {
			
			asset_register_image_pojo[i]	= new Asset_register_Image();
				
			file 				= new File(HM_image_path.get(i+1));
			String image_title	= hm_image_title.get(i+1)==null?"":hm_image_title.get(i+1).toString();
			String fname		= file.getName();
			String filenamesa 	= fname.substring(0, fname.indexOf("."));
			String filetype		= fname.substring(fname.indexOf(".") + 1, fname.length());
			Vector temp			= new Vector();
			temp.add(file);
			
			asset_register_image_pojo[i].Image_no			= i+1;
			asset_register_image_pojo[i].Image_Title		= image_title;
			
			System.out.println("before"+(byte[])getBytesFromFile(file) );
			System.out.println("imagetitle"+image_title);
		
			asset_register_image_pojo[i].Imagedata_byte		= (byte[])getBytesFromFile(file);
			
			System.out.println("after==="+temp.get(0));
			asset_register_image_pojo[i].Image_formatext	= hm_imagewith_formate.get(i+1);
			
		}
		if(Parent.equalsIgnoreCase("ASSET DETAILS")) {
			Asst_Details.HM_image_path_ig				= 	 HM_image_path;// image file path
			Asst_Details.hm_image_title_ig		 		=	 hm_image_title;// image titles
			Asst_Details.hm_image_title_ig		 		= 	 hm_imagewith_formate;// image format
			
			Asst_Details.setimage_inpanel();
			Asst_Details.Asset_register_Image_pojo		=	asset_register_image_pojo;
			
		}else if(Parent.equalsIgnoreCase("GRN IMPORT")) {
			Asstimptandcode.HM_image_path_ig			= 	 HM_image_path;// image file path
			Asstimptandcode.hm_image_title_ig			=	 hm_image_title;// image titles
			Asstimptandcode.hm_image_title_ig			= 	 hm_imagewith_formate;// image format
			
			Asstimptandcode.setimage_inpanel();
			Asstimptandcode.Asset_register_Image_pojo	=	asset_register_image_pojo;
		
		}

		
	}catch(Exception e1) {
		e1.printStackTrace();
		
	}
}


public byte[] getBytesFromFile(File newfile) throws IOException {		
	FileInputStream fileinputstream = new FileInputStream(newfile);
	int i 				= fileinputstream.available();
	byte abyte0[] 		= new byte[i];
	int j 				= fileinputstream.read(abyte0, 0, i);
	fileinputstream.close();
	return abyte0;		
}


	
	private JLabel getjl_upload_ic() {
		if (jl_upload_ic == null) {
			jl_upload_ic = new JLabel();
			jl_upload_ic.setText("");
			jl_upload_ic.setName("Asset_jl_upload_ic");
			jl_upload_ic.setBounds(new Rectangle(380, 90, 100, 30));
			try {
				ImageIcon imageIcons 	= new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("quickFills.png")));
				Image img 				= ImageIO.read(getClass().getResource("quickFills.png"));
				imageIcons.setImage(img);
				jl_upload_ic.setIcon(imageIcons);
				jl_upload_ic.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getSource()==jl_upload_ic) {	
							
							JFileChooser file_choser 	= new JFileChooser();
							file_choser.setMultiSelectionEnabled(true);	
							file_choser.showOpenDialog(outerFrm);
							String File_path 					= file_choser.getCurrentDirectory().getAbsolutePath();
							if(file_choser.getSelectedFile()!= null) {
						
								File filename[]				= file_choser.getSelectedFiles();
								Boolean multiple_selection	= false;
	
								if(HM_image_path.size()!=0) {
									multiple_selection= true;
						 			jB_next.setEnabled(true);
									jb_previous.setEnabled(true);
									hm_image_title.remove(image_no);
									hm_image_title.put(image_no,jtf_Image_title.getText().toString());
								}
								for(File f1:filename) {
									image_no++;
									if (File_path != null && f1.getName() != null) {
									
										String full_file_path	= File_path+"/"+f1.getName();

										HM_image_path.put(image_no,full_file_path);
										hm_image_title.put(image_no," ");
										hm_imagewith_formate.put(image_no,f1.getName() );
										
									}

								}
								if(multiple_selection==true) {
									load_image(image_no,false,true);
								}else {
									load_image(1,true,false);
								}
								
							
						
							}
						
						}
						
					}

				});
				
				
			 } catch (Exception e) {	
					e.printStackTrace();
				}
			
		}
		return jl_upload_ic;
	}
	
	private JLabel getjl_delete_ic() {
		if (jl_delete_ic == null) {
			jl_delete_ic = new JLabel();
			jl_delete_ic.setText("");
			jl_delete_ic.setName("Asset_jl_delete_ic");
			jl_delete_ic.setBounds(new Rectangle(380, 120, 100, 30));
			try {
				
				ImageIcon imageIcons 	= new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("arrow_right.png")));
				Image img 				= ImageIO.read(getClass().getResource("arrow_right.png"));
				imageIcons.setImage(img);
				jl_delete_ic.setIcon(imageIcons);
				jl_delete_ic.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getSource()==jl_delete_ic) {
							HM_image_path.remove(image_no);
							hm_image_title.remove(image_no);
							hm_imagewith_formate.remove(image_no );
							
							jtf_Image_title.setText("");
							System.out.println("---------"+HM_image_path);
							System.out.println("---------"+image_no);
							
							
							if(image_no==0) {
								outerFrm.showMsg("no more image to delete");		
							}else {
								
								for(int i =0;i<HM_image_path.size();i++) {
									
									
									if( HM_image_path.get(image_no+1)	!=null) {
										
									System.out.println(""+image_no);	
									HM_image_path.put(image_no,	 ( HM_image_path.get(image_no+1) 	) );
									hm_image_title.put(image_no, ( hm_image_title.get(image_no+1)	) );
									hm_imagewith_formate.put(image_no, ( hm_imagewith_formate.get(image_no+1)	) );
									
									
									jtf_Image_title.setText( ( hm_image_title.get(image_no+1).toString() )	);
									HM_image_path.remove(image_no+1);
									hm_image_title.remove(image_no+1);
									hm_imagewith_formate.remove(image_no+1);
									
									break;
									}else if(HM_image_path.get(image_no-1) !=null){
									System.out.println(""+image_no);
									jtf_Image_title.setText( ( hm_image_title.get(image_no-1).toString() )	);
									image_no --;
									
									break;
									}
									if(image_no<=0) {
										image_no=0;
									}
									
								}
								load_image(image_no,false,false);
							}
							
						}
						
					}

				});
				
			} catch (Exception e) {	
					e.printStackTrace();
				}
		}
		return jl_delete_ic;
	}
	 
	 private JPanel getIconPanel(){
			if(iconPanel == null){
				iconPanel = new JPanel();
				iconPanel.setBounds(75, 50, 300,250);
				iconPanel.setOpaque(true); 
				iconPanel.setBackground(Color.WHITE);
				this.add(iconPanel, null);
			}
			return iconPanel;
		}
	 
/*******************************************************
 * METHOD CALLS-STARTS
 *******************************************************/
	 
	 
	 public void load_image(Integer loc_image_no, boolean new_load,boolean multiple_selection ) {
		 try {
				if(new_load == true) {
					image_no	= 1;
				}else {
					image_no	= loc_image_no;
					
					if(multiple_selection== true) {
						jtf_Image_title.setText("");
					}
				}
				file 				= new File(HM_image_path.get(loc_image_no));
				String fname		= file.getName();
				String filenamesa 	= fname.substring(0, fname.indexOf("."));
				String filetype		= fname.substring(fname.indexOf(".") + 1, fname.length());
//				String filetype		= tempfiletype.substring(0, tempfiletype.indexOf("::"));
				
				if (filetype.equals("jpg") || filetype.equals("jpeg") || filetype.equals("gif") || filetype.equals("png")) {
					String 	imagefile 			= file.getAbsolutePath();
					
		//file size validation			
					double max_file_size 		= 500000;
					double file_size 			= file.length();
						
					if(file_size	>	max_file_size){
							outerFrm.showMsg("File Size should not exceed 500 KB");		
						}
					
					if (!file.exists()) {
							JOptionPane.showMessageDialog(null,"File Does not Exits,Please select an Image.");
						}	
					
					BufferedImage set_imgage 	= null;
					set_imgage 					= ImageIO.read(file);
					setImageInPanel(set_imgage);
				
				}
				
				jl_no_of_pic.setText( image_no.toString() + " of "+HM_image_path.size());
				
				if(HM_image_path.size()==1 && image_no==1 ) {
		 			jb_previous.setEnabled(false);
		 			jB_next.setEnabled(false);
		 		}else if( HM_image_path.size()==image_no ) {
					jB_next.setEnabled(false);
				} else if(image_no<=1) {
					jb_previous.setEnabled(false);
		 		}

		 }catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 }
	 
	 public void setImageInPanel( Image images) {
	 		
	 		iconPanel.removeAll();
	 		
			Image 		d_images 		= images.getScaledInstance(450,350,Image.SCALE_SMOOTH);
	 		ImageIcon 	sel_image_icon	= new ImageIcon(d_images);
	 		JLabel 		imageLabel 		= new JLabel(sel_image_icon);		
	 		iconPanel.add(imageLabel);	
	 		iconPanel.revalidate();
	 		iconPanel.repaint();
	 				
		}

	 	
	 

	 	
	 	public void next_image() {
	 		
	 		
	 		if(hm_image_title.get(image_no)!=null || hm_image_title.get(image_no).isEmpty()) {
	 			hm_image_title.remove(image_no);
	 			hm_image_title.put(image_no,jtf_Image_title.getText().toString());
	 		}
	 		image_no++;
	 		if(image_no>hm_image_title.size()) {
	 			image_no--;
	 		}
	 		if(HM_image_path.size() >0 && image_no <= HM_image_path.size() ) {
	 			jb_previous.setEnabled(true);
				file 		= new File(HM_image_path.get(image_no));	
				
				jtf_Image_title.setText("");
				
				if(hm_image_title.size() > 0 && image_no <= hm_image_title.size()){
					String image_title = hm_image_title.get(image_no);
					jtf_Image_title.setText(image_title== null ? "" : image_title);
				}
				if(HM_image_path.size()==image_no) {
		 			jB_next.setEnabled(false);
		 			
		 		}
	 		}

			if(image_no !=0) {
				load_image(image_no,false,false);
			}

	 	}
	 	
	 	public void previous_image() {
	 		
	 		if(hm_image_title.get(image_no)!=null || hm_image_title.get(image_no).isEmpty()) {
	 			hm_image_title.remove(image_no);
	 			hm_image_title.put(image_no,jtf_Image_title.getText().toString());
	 		}
	 		image_no--;
	 		
	 		if(HM_image_path.size() >0 && image_no > 0 ) {
	 			jB_next.setEnabled(true);
				file 				= new File(HM_image_path.get(image_no));	
				
				if(hm_image_title.size() > 0 && image_no <= hm_image_title.size()){
					String image_title = hm_image_title.get(image_no);
					jtf_Image_title.setText(image_title== null ? "" : image_title);
				}
				if(image_no<=1) {
					jb_previous.setEnabled(false);
		 			
		 		}
				
	 		}

			if(image_no !=0) {
				load_image(image_no,false,false);

			}
	 	}
	 	
	 	/*to have the un touched copy of some vetor or an object  -->*/	
 		static public Object deepCopy(Object oldObj) throws Exception {
 		    ObjectOutputStream oos = null;
 		    ObjectInputStream ois = null;
 		    try {
 		        ByteArrayOutputStream bos = new ByteArrayOutputStream(); // A
 		        oos = new ObjectOutputStream(bos); // B
 		        // serialize and pass the object
 		        oos.writeObject(oldObj); // C
 		        oos.flush(); // D
 		        ByteArrayInputStream bin = new ByteArrayInputStream(
 		                bos.toByteArray()); // E
 		        ois = new ObjectInputStream(bin); // F
 		        // return the new object
 		        return ois.readObject(); // G
 		    } catch (Exception e) {
 		        System.out.println("Exception in ObjectCloner = " + e);
 		        throw (e);
 		    } finally {
 		        oos.close();
 		        ois.close();
 		    }
 		}	
	 	
/*******************************************************
 * METHOD CALLS-ENDS
 *******************************************************/

}
