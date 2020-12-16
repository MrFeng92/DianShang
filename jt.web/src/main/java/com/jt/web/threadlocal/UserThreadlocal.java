package com.jt.web.threadlocal;

import com.jt.web.pojo.User;

//用户Threadlocal
public class UserThreadlocal
{
    private static final ThreadLocal<User> USER = new ThreadLocal<User>();

    public static void set(User user)
    {
	USER.set(user);
    }

    public static User get()
    {
	return USER.get();
    }

    public static Long getUserId()
    {
	if (UserThreadlocal.get() != null)
	{
	    return USER.get().getId();
	}
	else
	{
	    return null;
	}
    }

}
