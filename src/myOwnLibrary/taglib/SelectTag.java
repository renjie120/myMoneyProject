package myOwnLibrary.taglib;
 
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import flashchart.Lib;
 
/**
 * �Զ���������˵�������.
 * @author renjie120 419723443@qq.com
 *
 */
public class SelectTag extends TagSupport { 
    /**
     * ���캯��.
     * @coustructor method
     */
    public SelectTag() {
        super();
    }

    /**
     * �������id
     */
    private String id = null;

    /**
     * ����������Ϣ.
     */
    private String tagName = null;

    /**
     * �Ƿ��С���ѡ��һ���ӦֵΪ-1
     */
    private String selectFlag = null;

    /**
     * �Ƿ��С�ȫ��ѡ��һ���ӦֵΪ-2
     */
    private String allSelected = null;
    
    /**
     * ѡ��ֵ��
     */
    private String selectedValue = null;
    
    /**
     * Ĭ��ֵ
     */
    private String defaultValue = null; 
    /**
     * ѡ���ֶ�
     */
    private OptionColl selectColl = null;

    /**
     * ��ʾ���  
     */
    private String selectYear = null;

    /**
     * onchange����Ӧ����
     */
    private String onchange;

    /**
     * ���ܸı�
     */
    private String disabled;

    /**
     * �õ�����
     */
    private String onfocus;

    /**
     * ʧȥ����
     */
    private String onblur;

    /**
     * ��ʽ
     */
    private String css;

    /**
     * ����null
     */
    private String returnNull;

    /**
     * ���ؿ�.
     * @return
     */
    public String getReturnNull() {
        return returnNull;
    }

    /**
     * ���÷��ؿ�.
     * @param returnNull
     */
    public void setReturnNull(String returnNull) {
        this.returnNull = returnNull;
    }

    /**
     * �õ�id.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * ����ֵ. 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * �õ�css. 
     * @return
     */
    public String getCss() {
        return css;
    }

    /**
     * ����css. 
     * @param css
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * ��ǩ��������.
     * @return
     * @throws JspException
     */
    public int doEndTag() throws JspException {

        try {
            pageContext.getOut().write(getSelectXML());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return super.doEndTag();
    }

    /**
     * ��ǩ��ʼ����.
     * @return
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    /**
     * ��ǩ����.
     * @return
     */
    private synchronized String getSelectXML() {
        StringBuffer buf = new StringBuffer(); 
        buf.append("<SELECT name=\"" + tagName + "\" id=\"" + tagName + "\"");
        if (onchange != null && !onchange.trim().equals("")) {
            buf.append(" onchange=\"" + onchange + "\"");
        }
        if (id != null && !id.trim().equals("")) {
            buf.append(" id=\"" + id + "\"");
        }
        if (css != null && !css.trim().equals("")) {
            buf.append(" css=\"" + css + "\"");
        }
        if (onfocus != null && !onfocus.trim().equals("")) {
            buf.append(" onfocus=\"" + onfocus + "\"");
        }
        if (onblur != null && !onblur.trim().equals("")) {
            buf.append(" onblur=\"" + onblur + "\"");
        }
        if (onchange != null && !onchange.trim().equals("")) {
            buf.append(" onchange=\"" + onchange + "\"");
        }
        if (disabled != null && !disabled.trim().equals("")
                && disabled.equals("true")) {
            buf.append(" disabled=\"true\"");
        }
        buf.append("> \n");
        try { 
            if (!Lib.isEmpty(selectFlag) 
                    && selectFlag.equals("true")) { 
                    buf
                            .append("<option value='-1'>��ѡ��</option>"); 
            }
            if (!Lib.isEmpty(allSelected) 
                    && allSelected.equals("true")) {
                buf
                        .append("<option value='-2'>ȫ��ѡ��</option>");
            }
            if (selectColl == null) {
                buf.append("<option><value></value></option>");
            }
            else {
            	//���������defaultValue,����selectedValue����û������,��ǿ������selectedValue=defaultValue.
            	 if (!Lib.isEmpty(defaultValue)) {
            		 selectedValue = defaultValue;
				} 
            	 //�������ѭ����������˵�����
                for (int i = 0; i < selectColl.size(); i++) {
                    Option vo = selectColl.get(i);
                    //������selectYear���ҵ�������ѡ��.
                   	if (!Lib.isEmpty(selectedValue)
							&& selectedValue.equals(vo.getId())) {
						buf.append(" <option value=\"" + vo.getId() + "\"");
						buf.append(" selected>");
						buf.append(vo.getName());
					} else {
						buf.append(" <option value=\"" + vo.getId() + "\">");
						buf.append(vo.getName());
					} 
                    buf.append("</option>\n");
                }
            }
            buf.append("</SELECT> \n");
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return buf.toString();
    }

    
    /**
     * ��ǩ����. 
     * @return
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * ��������. 
     * @param tagName
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * �õ���ʾ. 
     * @return
     */
    public String getSelectFlag() {
        return selectFlag;
    }

    /**
     * ���ñ�ʾ. 
     * @param selectFlag
     */
    public void setSelectFlag(String selectFlag) {
        this.selectFlag = selectFlag;
    }

    /**
     * �õ�ȫѡ. 
     * @return
     */
    public String getAllSelected() {
        return allSelected;
    }

    /**
     * ����ȫѡ. 
     * @param allSelected
     */
    public void setAllSelected(String allSelected) {
        this.allSelected = allSelected;
    } 
    /**
     * �õ�ѡ�����.
     * @return
     */
    public String getSelectYear() {
        return selectYear;
    }

    /**
     * ����ѡ�����.
     * @param selectYear
     */
    public void setSelectYear(String selectYear) {
        this.selectYear = selectYear;
    }

    /**
     * �õ��ı䷽��.
     * @return
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * ���øı䷽��.
     * @param onchange
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    /**
     * �õ�������.
     * @return
     */
    public String getDisabled() {
        return disabled;
    }

    /**
     * ���ò�����.
     * @param disabled
     */
    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    /**
     * �õ�foucus����.
     * @return
     */
    public String getOnfocus() {
        return onfocus;
    }

    /**
     * ����foucus����.
     * @param onfocus
     */
    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    /**
     * �õ�ʧȥ���㷽��.
     * @return
     */
    public String getOnblur() {
        return onblur;
    }

    /**
     * ����ʧȥ���㷽�� .
     * @param onblur
     */
    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public OptionColl getSelectColl() {
		return selectColl;
	}

	public void setSelectColl(OptionColl selectColl) {
		this.selectColl = selectColl;
	} 
}
