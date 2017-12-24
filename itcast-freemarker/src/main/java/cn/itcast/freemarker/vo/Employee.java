package cn.itcast.freemarker.vo;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {

	private Long id;
	private String name;
	private Boolean gender;
	private Double salary;
	private Date birthday;
	private Integer workAge;
	public Employee(Long id, String name, Boolean gender, Double salary, Date birthday, Integer workAge) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.birthday = birthday;
		this.workAge = workAge;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getWorkAge() {
		return workAge;
	}
	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", salary=" + salary + ", birthday="
				+ birthday + ", workAge=" + workAge + "]";
	}
	
}
