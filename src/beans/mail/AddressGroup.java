package beans.mail;

import java.io.Serializable;
import java.util.ArrayList;

public class AddressGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String groupName;
	
	private ArrayList<Address> addressList;
	
	
	/**
	 * 
	 */
	public AddressGroup() {
		addressList = new ArrayList<Address>();
	}
	
	/**
	 * 
	 * @param address
	 */
	public void addAddress(Address address){
		addressList.add(address);
	}
	
	/**
	 * 
	 * @param list
	 */
	public void addAddress(ArrayList<Address> list){		
		//addressList.addAll(list);
		for(int i=0; i<list.size(); i++)
		{
			Address address = list.get(i);
			
			if(contains(address.getUsername()) == false)
				addressList.add(address);
		}
	}
	
	/**
	 * 
	 * @param address
	 */
	public void deleteAddress(Address address){
		//addressList.remove(address);
		for(int i=0; i<addressList.size(); i++)
		{
			Address address2 = addressList.get(i);
			
			if(address2.getUsername().equals(address.getUsername()))
			{
				addressList.remove(address2);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param list
	 */
	public void deleteAddress(ArrayList<Address> list){
		for(int i=0; i<list.size(); i++)
		{
			//addressList.remove(list.get(i));
			Address addressToDelete = list.get(i);
			
			for(int j=0; j<addressList.size(); j++)
			{
				Address address = addressList.get(j);
				
				if(address.getUsername().equals(addressToDelete.getUsername()))
				{
					addressList.remove(address);
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getGroupName(){
		return groupName;
	}
	
	/**
	 * 
	 * @return ArrayList<Address>
	 */
	public ArrayList<Address> getAddressList(){
		return addressList;
	}
	
	/**
	 * 
	 * @param groupName
	 */
	public void setGroupName(String groupName){
		this.groupName=groupName;
	}
	
	/**
	 * 
	 * @param addressList
	 */
	public void setAddressList(ArrayList<Address> addressList){
		this.addressList=addressList;
	}
	
	/**
	 * 判断指定联系人用户名是否已存在列表在
	 * @param username 指定联系人用户名
	 * @return boolean
	 */
	private boolean contains(String username)
	{
		for(int i=0; i<addressList.size(); i++)
		{
			if(addressList.get(i).getUsername().equals(username))
				return true;
		}
		return false;
	}

}
