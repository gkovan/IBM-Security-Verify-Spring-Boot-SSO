package com.gkovan.resource.web.dto;

public class FoodDto {
    public FoodDto(final Long id, final String user, final String food) {
		super();
		this.id = id;
		this.user = user;
		this.food = food;
	}

	private Long id;
	
    private String user;

    private String food;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}
}
