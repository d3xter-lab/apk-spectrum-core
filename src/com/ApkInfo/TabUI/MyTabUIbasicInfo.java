package com.ApkInfo.TabUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.ApkInfo.Resource.Resource;
import com.ApkInfo.UIUtil.JHtmlEditorPane;
import com.ApkInfo.UIUtil.JHtmlEditorPane.HyperlinkClickListener;
import com.ApkInfo.Core.ApkManager.ApkInfo;
import com.ApkInfo.Core.PermissionGroupManager.PermissionGroup;
import com.ApkInfo.Core.AdbWrapper;
import com.ApkInfo.Core.ApkManager;
import com.ApkInfo.Core.PermissionGroupManager;
import com.ApkInfo.Core.PermissionGroupManager.PermissionInfo;

public class MyTabUIbasicInfo extends JComponent implements HyperlinkClickListener
{
	private static final long serialVersionUID = 6431995641984509482L;

	private JHtmlEditorPane apkinform;
	private ApkInfo apkInfo;
	private PermissionGroupManager permGroupManager;

	public MyTabUIbasicInfo() {
    	apkinform = new JHtmlEditorPane();
        apkinform.setEditable(false);

        //Font font = new Font("helvitica", Font.BOLD, 15);
		JLabel label = new JLabel();
	    Font font = label.getFont();

	    // create some css from the label's font
	    StringBuilder style = new StringBuilder("#basic-info, #perm-group {");
	    style.append("font-family:" + font.getFamily() + ";");
	    style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
	    style.append("font-size:" + font.getSize() + "pt;}");
	    style.append("#basic-info a {text-decoration:none; color:black;}");
	    style.append("#perm-group a {text-decoration:none; color:#"+Integer.toHexString(label.getBackground().getRGB() & 0xFFFFFF)+";}");
	    
        apkinform.setStyle(style.toString());
        apkinform.setBackground(Color.white);
        apkinform.setHyperlinkClickListener(this);

        this.setLayout(new GridBagLayout());
        this.add(apkinform);
	}
	
	private void removeData()
	{
		this.apkInfo = null;

        StringBuilder strTabInfo = new StringBuilder("");
		strTabInfo.append("<table width=10000>");
		strTabInfo.append("  <tr>");
        strTabInfo.append("    <td width=170>");
		strTabInfo.append("      <image src=\"" + Resource.IMG_APP_ICON.getPath() + "\" width=150 height=150 />");
		strTabInfo.append("    </td>");
		strTabInfo.append("    <td>");
		strTabInfo.append("<div id=\"basic-info\">");
		strTabInfo.append("  <H1>" + Resource.STR_APP_NAME.getString() + " " + Resource.STR_APP_VERSION.getString() + "</H1>");
		strTabInfo.append("  Using following tools,<br/>");
		strTabInfo.append("  Apktool " + ApkManager.getApkToolVersion() + "<br/>");
		strTabInfo.append("  - <a href=\"http://ibotpeaches.github.io/Apktool/\" title=\"Apktool Project Site\">http://ibotpeaches.github.io/Apktool/</a><br/>");
		strTabInfo.append("  " + AdbWrapper.getVersion() + "<br/>");
		strTabInfo.append("  - <a href=\"http://developer.android.com/tools/help/adb.html\" title=\"Android Developer Site\">http://developer.android.com/tools/help/adb.html</a><br/>");
		strTabInfo.append("  <br/><hr/>");
		strTabInfo.append("  Programmed by <a href=\"mailto:" + Resource.STR_APP_MAKER_EMAIL.getString() + "\" title=\"" + Resource.STR_APP_MAKER_EMAIL.getString() + "\">" + Resource.STR_APP_MAKER.getString() + "</a>, 2015.<br/>");
		strTabInfo.append("</div>");
		strTabInfo.append("    </td>");
        strTabInfo.append("  </tr>");
        strTabInfo.append("  <tr>");
        strTabInfo.append("    <td colspan=2>");
		strTabInfo.append("      <hr/>");
		strTabInfo.append("    </td>");
        strTabInfo.append("  </tr>");
        strTabInfo.append("  <tr>");
        strTabInfo.append("    <td colspan=2 height=10000></td>");
        strTabInfo.append("  </tr>");
        strTabInfo.append("</table>");
        
        apkinform.setBody(strTabInfo.toString());
	}

	public void setData(ApkInfo apkInfo)
	{
		this.apkInfo = apkInfo;
		
		if(apkInfo == null) {
			removeData();
			return;
		}
		
		String sdkVersion = "@SDK Ver.";
        if(!apkInfo.MinSDKversion.isEmpty()) {
        	sdkVersion += apkInfo.MinSDKversion +" (Min)";
        }
        if(!apkInfo.TargerSDKversion.isEmpty()) {
        	if(!apkInfo.MinSDKversion.isEmpty()) {
        		sdkVersion += ", "; 
        	}
        	sdkVersion += apkInfo.TargerSDKversion + " (Target)";
        }
        if(apkInfo.MinSDKversion.isEmpty() && apkInfo.TargerSDKversion.isEmpty()) {
        	sdkVersion += "Unknown"; 
        }
        
        String feature;
        if(apkInfo.isHidden) {
        	feature = makeHyperLink("", Resource.STR_FEATURE_HIDDEN_LAB.getString(), Resource.STR_FEATURE_HIDDEN_DESC.getString(), null);
        } else {
        	feature = makeHyperLink("", Resource.STR_FEATURE_LAUNCHER_LAB.getString(), Resource.STR_FEATURE_LAUNCHER_DESC.getString(), null);
        }
        
        if(!apkInfo.Startup.isEmpty()) {
        	feature += ", " + makeHyperLink("", Resource.STR_FEATURE_STARTUP_LAB.getString(), Resource.STR_FEATURE_STARTUP_DESC.getString(), null);
        }
        if(!apkInfo.ProtectionLevel.isEmpty()) {
        	feature += ", " + makeHyperLink("", Resource.STR_FEATURE_SIGNATURE_LAB.getString(), Resource.STR_FEATURE_SIGNATURE_DESC.getString(), null);
        }
        if(!apkInfo.SharedUserId.isEmpty()) {
        	feature += ", " + makeHyperLink("", Resource.STR_FEATURE_SHAREDUSERID_LAB.getString(), Resource.STR_FEATURE_SHAREDUSERID_DESC.getString(), null);
        }
        
        String permGorupImg = makePermGroup();
		
        int infoHeight = 270;
        if(permGroupManager.getPermGroupMap().keySet().size() > 15) infoHeight = 230;

        StringBuilder strTabInfo = new StringBuilder("");
		strTabInfo.append("<table width=10000>");
		strTabInfo.append("  <tr>");
		strTabInfo.append("    <td width=170 height=" + infoHeight + ">");
		strTabInfo.append("      <image src=\"file:/" + apkInfo.IconPath.replaceAll("^/", "") + "\" width=150 height=150 />");
		strTabInfo.append("    </td>");
		strTabInfo.append("    <td height=" + infoHeight + ">");
		strTabInfo.append("      <div id=\"basic-info\">");
        strTabInfo.append("        <font style=\"font-size:20px; color:#548235; font-weight:bold\">");
        strTabInfo.append("          " + makeHyperLink("", apkInfo.Labelname, "App name", null));
        strTabInfo.append("        </font><br/>");
        strTabInfo.append("        <font style=\"font-size:15px; color:#4472C4\">");
        strTabInfo.append("          [" + makeHyperLink("", apkInfo.PackageName, "Package name", null) +"]");
        strTabInfo.append("        </font><br/>");
        strTabInfo.append("        <font style=\"font-size:15px; color:#ED7E31\">");
        strTabInfo.append("          " + makeHyperLink("", "Ver. " + apkInfo.VersionName +" / " + apkInfo.VersionCode, "VersionName : " + apkInfo.VersionName + "\n" + "VersionCode : " + apkInfo.VersionCode, null));
        strTabInfo.append("        </font><br/>");
        strTabInfo.append("        <br/><br/>");
        strTabInfo.append("        <font style=\"font-size:12px\">");
        strTabInfo.append("          " + makeHyperLink("", apkInfo.ApkSize, "APK size", null) + "<br/>");
        strTabInfo.append("          " + makeHyperLink("", sdkVersion, "Target SDK version", null));
        strTabInfo.append("        </font>");
        strTabInfo.append("        <br/><br/>");
        strTabInfo.append("        <font style=\"font-size:12px\">");
        strTabInfo.append("          [" + Resource.STR_FEATURE_LAB.getString() + "]<br/>");
        strTabInfo.append("          " + feature);
        strTabInfo.append("        </font><br/>");
        strTabInfo.append("      </div>");
        strTabInfo.append("    </td>");
        strTabInfo.append("  </tr>");
        strTabInfo.append("  <tr>");
        strTabInfo.append("    <td colspan=2>");
        strTabInfo.append("      <div id=\"perm-group\">");
        strTabInfo.append("        <hr/>");
        strTabInfo.append("        <font style=\"font-size:12px;color:black;\">[" + Resource.STR_BASIC_PERMISSIONS.getString() + "] - ");
        strTabInfo.append("          " + makeHyperLink("@event","<u>" + Resource.STR_BASIC_PERMLAB_DISPLAY.getString() + "</u>",Resource.STR_BASIC_PERMDESC_DISPLAY.getString(),"display-list"));
        strTabInfo.append("        </font><br/>");
        strTabInfo.append("        <font style=\"font-size:5px\"><br/></font>");
        strTabInfo.append("        " + permGorupImg);
        strTabInfo.append("      </div>");
        strTabInfo.append("    </td>");
        strTabInfo.append("  </tr>");
        strTabInfo.append("  <tr>");
        strTabInfo.append("    <td colspan=2 height=10000></td>");
        strTabInfo.append("  </tr>");
        strTabInfo.append("</table>");
        
        apkinform.setBody(strTabInfo.toString());
	}
	
	private String makePermGroup()
	{
		StringBuilder permGroup = new StringBuilder("");
		
		for(String pgk: apkInfo.PermissionList) {
			System.out.println(pgk);
		}
		permGroupManager = new PermissionGroupManager(apkInfo.PermissionList.toArray(new String[0]));
		HashMap<String, PermissionGroup> map = permGroupManager.getPermGroupMap();
		Set<String> keys = map.keySet();
		int cnt = 0;
		for(String key: keys) {
			System.out.println("key - " + key);
			PermissionGroup g = map.get(key);
			permGroup.append(makeHyperLink("@event", makeImage(g.icon), g.permSummary, g.permGroup));
			if(++cnt % 15 == 0) permGroup.append("<br/>");
		}
		
		return permGroup.toString();
	}
	
	private String makeHyperLink(String href, String text, String title, String id)
	{
		String attr = "";
		if(title != null) {
			attr += String.format(" title=\"%s\"", title);
		}
		if(id != null) {
			attr += String.format(" id=\"%s\"", id);
		}
		
		return String.format("<a href=\"%s\"%s>%s</a>", href, attr, text);
	}
	
	private String makeImage(String src)
	{
		return "<image src=\"" + src + "\"/>";
	}
	
	@Override
	public void hyperlinkClick(String id) {
		System.out.println("click : "+id);
		if(id.equals("display-list")) {
			showPermList();
		} else {
			showPermDetailDesc(id);
		}
	}
	
	public void showPermList()
	{
		JLabel label = new JLabel();
	    Font font = label.getFont();

		StringBuilder body = new StringBuilder("");
		body.append("<div id=\"perm-list\">");
		body.append(apkInfo.Permissions.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		body.append("</div>");
		
	    // create some css from the label's font
	    StringBuilder style = new StringBuilder("#perm-list {");
	    style.append("font-family:" + font.getFamily() + ";");
	    style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
	    style.append("font-size:" + font.getSize() + "pt;}");
	    style.append("#about a {text-decoration:none;}");

	    // html content
	    JHtmlEditorPane descPane = new JHtmlEditorPane("", "", body.toString().replaceAll("\n", "<br/>"));
	    descPane.setStyle(style.toString());

	    descPane.setEditable(false);
	    descPane.setBackground(label.getBackground());
	    
		JOptionPane.showMessageDialog(null, descPane, Resource.STR_BASIC_PERM_LIST_TITLE.getString(), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showPermDetailDesc(String group)
	{
		HashMap<String, PermissionGroup> map = permGroupManager.getPermGroupMap();
		PermissionGroup g = map.get(group);
		
		if(g == null) return;

		StringBuilder body = new StringBuilder("");
		body.append("<div id=\"perm-detail-desc\">");
		body.append("■ " + g.label + " - " + "[" + group + "]\n");
		body.append(" : " + g.desc + "\n<hr/>\n");
		
		for(PermissionInfo info: g.permList) {
			body.append("▶ " + info.label + " [" + info.permission + "]\n");
			body.append(" : " + info.desc + "\n");
		}
		body.append("</div>");
		
		JLabel label = new JLabel();
	    Font font = label.getFont();

	    // create some css from the label's font
	    StringBuilder style = new StringBuilder("#perm-detail-desc {");
	    style.append("font-family:" + font.getFamily() + ";");
	    style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
	    style.append("font-size:" + font.getSize() + "pt;}");
	    style.append("#about a {text-decoration:none;}");

	    // html content
	    JHtmlEditorPane descPane = new JHtmlEditorPane("", "", body.toString().replaceAll("\n", "<br/>"));
	    descPane.setStyle(style.toString());

	    descPane.setEditable(false);
	    descPane.setBackground(label.getBackground());
	    
		JOptionPane.showMessageDialog(null, descPane, Resource.STR_BASIC_PERM_DISPLAY_TITLE.getString(), JOptionPane.INFORMATION_MESSAGE);
	}

	public void reloadResource() {
		setData(apkInfo);
	}
}
