package com.taotao.web.bean;

import java.util.Date;


public class Cart {
    private Long id;
    private Long userId;
    
    private Long itemId;
    
    private String itemTitle;
    
    private String itemImage;
    
    private Long itemPrice;
    
    private Integer num;
    
    private Date created;
    
    private Date updated;

    
    
    public Cart() {
    }

    public Cart(Long id, Long userId, Long itemId, String itemTitle, String itemImage, Long itemPrice,
            Integer num, Date created, Date updated) {
        super();
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.num = num;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Cart [id=" + id + ", userId=" + userId + ", itemId=" + itemId + ", itemTitle=" + itemTitle
                + ", itemImage=" + itemImage + ", itemPrice=" + itemPrice + ", num=" + num + ", created="
                + created + ", updated=" + updated + "]";
    }
    
    
}
