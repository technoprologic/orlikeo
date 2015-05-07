package umk.zychu.inzynierka.util;

import java.util.ArrayList;
import com.dhtmlx.planner.data.DHXCollection;

public class Table extends DHXCollection {

	public String name;
	public String key;
	public Integer persons = 5;
	public Boolean smoking = false;
	public Boolean open = true;
	public ArrayList<DHXCollection> children = null;

	public Table(String value, String label, Integer persons, Boolean smoking) {
		super(value, label);
		setKey(value);
		setName(label);
		setPersons(persons);
		setSmoking(smoking);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		render();
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
		render();
	}

	public Integer getPersons() {
		return this.persons;
	}

	public void setSmoking(Boolean smoking) {
		this.smoking = smoking;
		render();
	}

	public Boolean getSmoking() {
		return this.smoking;
	}

	protected void render() {
		setLabel(this.name + " (" + this.persons.toString() + " people, "
				+ (smoking ? "smoking" : "no smoking") + ")");
	}

	public Iterable<DHXCollection> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<DHXCollection> children) {
		this.children = children;
	}

	public void addChild(DHXCollection child) {
		if (children == null)
			children = new ArrayList<DHXCollection>();
		children.add(child);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}
}