import { createContext, useEffect, useState, ReactNode } from 'react';
import { getHeaders, Api } from '../services/Api';
import { User } from '../models/user/User';
import toast from 'react-hot-toast';
import { AuthContextModel } from '../models/AuthContext';
import { AxiosError } from 'axios';



export const AuthContext = createContext({} as AuthContextModel);

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider ({ children } : AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const storagedUser = localStorage.getItem("user")
    if (storagedUser) {
      setUser(JSON.parse(storagedUser))
    }
    console.log(storagedUser)
  }, [])

  useEffect(() => {
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    } else {
      localStorage.removeItem('user');
    }
  }, [user])

  const login = async (data: User): Promise<boolean> => {
    
    try{
      const response = await Api.post('users/auth', JSON.stringify(data), getHeaders())
      setUser(response.data)
      console.log("Login Successfully")
      toast.success(`Login Successfully`)
      return true
    } catch(error: AxiosError | any | unknown) {
      console.log("Error Login ", error);
      toast.error(`Login failed. ${error.response.data}`)
      return false
    }
    
      
  };

  const signup = async (data: User): Promise<boolean> => {
    
    try{
      await Api.post('/users/signUp', JSON.stringify(data), getHeaders())
      console.log("SignUp Successfully")
      toast.success(`SignUp Successfully`)
      const success = await login(data)
      return success

    } catch(error: AxiosError | any | unknown) {
      console.log("Error SignUp ", error)
      toast.error(`SignUp failed. ${error.response.data.message}!`)
      return false
    }
  
  };

  const logout = () => {
    setUser(null);
    console.log("Signout completed")
  };

  return (
    <AuthContext.Provider value={{ user, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
