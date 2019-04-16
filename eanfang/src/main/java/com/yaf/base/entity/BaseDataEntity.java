package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.alibaba.fastjson.parser.JSONToken.DOT;


/**
 * 基础数据
 * 
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-30 12:44:27
 */

@TableName(value = "sys_base_data" )
public class BaseDataEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	//数据标识
	//@TableField(value = "data_id")
	//数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO	
	@TableId(value = "data_id" , type = IdType.AUTO)
	private Long dataId;
	
	//编码
	//@TableField(value = "data_code")
	private String dataCode;
	
	//数据名
	//@TableField(value = "data_name")
	private String dataName;
	
	//排序号
	//@TableField(value = "sort_num")
	private Integer sortNum;
	
	//备注
	//@TableField(value = "remark_info")
	private String remarkInfo;

	/**
	 * 数据绑定表实体类
     */
	@TableField(exist = false)
	private BaseData2userEntity baseData2userEntity;
	
	/**
	 * 设置：数据标识
	 */
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	/**
	 * 获取：数据标识
	 */
	public Long getDataId() {
		return dataId;
	}
	/**
	 * 设置：编码
	 */
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	/**
	 * 获取：编码
	 */
	public String getDataCode() {
		return dataCode;
	}
	/**
	 * 设置：数据名
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * 获取：数据名
	 */
	public String getDataName() {
		return dataName;
	}
	/**
	 * 设置：排序号
	 */
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	/**
	 * 获取：排序号
	 */
	public Integer getSortNum() {
		return sortNum;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarkInfo() {
		return remarkInfo;
	}
/*手工代码写在下面*/
	
    /**
     * 归属部门
     */

    @TableField(exist = false)
    private Integer dataType;
    public Integer getDataType() {

        if (dataType != null) {
            return dataType;
        }

        if (dataCode == null) {
            return null;
        }
    	
    	int pos = dataCode.indexOf(DOT);
    	
    	if(pos==-1) {
    		dataType = Integer.parseInt(dataCode);
    	} else {
    		dataType = Integer.parseInt(dataCode.substring(0,pos));
    	}
    	return dataType;	
    }
    
    @TableField(exist = false)
    private Long parentId;
    
    @TableField(exist = false)
    private List<BaseDataEntity> children = null;
    
    public void addChild(BaseDataEntity child) {
    	if(children == null) {
    		children=new LinkedList<BaseDataEntity>();
    	}
    	if(!children.contains(child)) {
    		children.add(child);
    	}
    }
    @TableField(exist = false)
    private Integer level;

	public int calculateLevel() {
		if(dataCode == null) {
			return 0;
		}
		int lv = 1;
		for(int i = 0 ; i<dataCode.length() ; i++) {
			if(dataCode.charAt(i) == '.') {
				lv++;
			}
		}
		return lv;
	}
	public Integer getLevel() {
		if(level != null && level > 0) {
			return level;
		}
		level = calculateLevel();
		return level;
	}
    @TableField(exist = false)
    private boolean isLeaf;

	public boolean isLeaf() {
		return children == null;
	}  
    public void setDataType(Integer dataType) {
    	getDataType();
    	return; //什么也不做，保证get方法时根据编码生成。
    }
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataId == null) ? 0 : dataId.hashCode());
		return result;
	}
	@Override	
    public boolean equals(Object other) {
    	if (other instanceof BaseDataEntity) {
    		if(this.dataId == null || other== null) {
                return false;
            }
    		
            return this.dataId.equals(((BaseDataEntity) other).dataId);   
        }   
        return false; 
    }
	@Override
	public BaseDataEntity clone() {
		try {
			return (BaseDataEntity)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

    public BaseData2userEntity getBaseData2userEntity() {
        return this.baseData2userEntity;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public List<BaseDataEntity> getChildren() {
        return this.children;
    }

    public void setBaseData2userEntity(BaseData2userEntity baseData2userEntity) {
        this.baseData2userEntity = baseData2userEntity;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setChildren(List<BaseDataEntity> children) {
        this.children = children;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
