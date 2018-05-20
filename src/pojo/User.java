package pojo;

public class User {
	private int id;
	private String username;
	private String password;
	private String anonymousName;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	 /**
     * 获取用户匿名名称
     * @return
     */
    public String getAnonymousName(){
        if (null == username) {
            return null;
        }

        if (username.length() <= 1) {
            return "*";
        }

        if (username.length() == 2) {
            return username.substring(0, 1) + "*";
        }

        char[] nameChars = username.toCharArray();
        for (int i = 1; i < username.length() - 1; i++) {
            nameChars[i] = '*';
        }
        return new String(nameChars);
    }
}
