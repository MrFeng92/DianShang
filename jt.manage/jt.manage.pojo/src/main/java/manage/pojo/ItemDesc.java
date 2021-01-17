package manage.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo{
	@Id
	private Long itemId;		//手动设置，它保存的就是商品的id值
	private String itemDesc;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
}
